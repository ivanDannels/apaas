package org.apaas.form.engine.service.reactive.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.query.PageResult;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.core.utils.SecurityUtils;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.form.domain.entity.FormData;
import org.apaas.form.engine.repository.FormDataRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormDataService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 响应式表单数据服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveFormDataServiceImpl extends BaseServiceImpl<FormData, Long, FormDataRepository> implements ReactiveFormDataService {

    private final ObjectMapper objectMapper;

    public ReactiveFormDataServiceImpl(FormDataRepository repository, RedisDomainEventPublisher<EntityChangedEvent<FormData>> eventPublisher, ObjectMapper objectMapper) {
        super(repository, eventPublisher);
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<PageResult<FormData>> selectFormDataPage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPageNum() - 1, query.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime"));
        
        return repository.findByTenantIdAndDeleted(SecurityUtils.getTenantId(), 0, pageRequest)
                .collectList()
                .zipWith(repository.countByTenantIdAndDeleted(SecurityUtils.getTenantId(), 0))
                .map(tuple -> {
                    List<FormData> content = tuple.getT1();
                    long total = tuple.getT2();
                    return new PageResult<>(content, total, query.getPageNum(), query.getPageSize());
                });
    }

    @Override
    public Flux<FormData> getFormDataByFormCode(String formCode) {
        return repository.findByFormCode(formCode);
    }

    @Override
    public Flux<FormData> getFormDataByFormCodeAndVersion(String formCode, String version) {
        return repository.findByFormCodeAndVersion(formCode, version);
    }

    @Override
    public Mono<FormData> getFormDataByBusinessKey(String businessKey) {
        return repository.findByBusinessKey(businessKey);
    }

    @Override
    public Mono<Long> saveFormData(FormData formData) {
        // 设置草稿状态
        formData.setStatus(0);
        return prepareAndSave(formData);
    }

    @Override
    public Mono<Long> submitFormData(FormData formData) {
        // 设置已提交状态
        formData.setStatus(1);
        return prepareAndSave(formData);
    }

    private Mono<Long> prepareAndSave(FormData formData) {
        // 设置租户ID和用户信息
        formData.setTenantId(SecurityUtils.getTenantId());
        formData.setSubmitUserId(SecurityUtils.getUserId());
        formData.setSubmitUserName(SecurityUtils.getUsername());
        formData.setSubmitDeptId(SecurityUtils.getDeptId());
        formData.setSubmitDeptName(SecurityUtils.getDeptName());
        
        // 设置创建和更新信息
        LocalDateTime now = LocalDateTime.now();
        if (formData.getId() == null) {
            formData.setCreator(SecurityUtils.getUsername());
            formData.setCreatedTime(now);
        }
        formData.setUpdater(SecurityUtils.getUsername());
        formData.setUpdatedTime(now);
        formData.setDeleted(0);
        
        return save(formData).map(FormData::getId);
    }

    @Override
    public Mono<Boolean> updateFormData(FormData formData) {
        return findById(formData.getId())
                .flatMap(existingData -> {
                    // 检查是否已提交或已审核的数据
                    if (existingData.getStatus() > 0) {
                        return Mono.error(new RuntimeException("已提交或已审核的表单数据不允许修改"));
                    }
                    
                    // 更新数据
                    formData.setUpdater(SecurityUtils.getUsername());
                    formData.setUpdatedTime(LocalDateTime.now());
                    return save(formData).thenReturn(true);
                })
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Boolean> deleteFormData(Long[] ids) {
        return Flux.fromArray(ids)
                .flatMap(this::findById)
                .flatMap(formData -> {
                    formData.setDeleted(1);
                    formData.setUpdater(SecurityUtils.getUsername());
                    formData.setUpdatedTime(LocalDateTime.now());
                    return save(formData);
                })
                .then(Mono.just(true))
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Boolean> importFormData(String formCode, String dataJson) {
        try {
            // 解析JSON数据
            List<Map<String, Object>> dataList = objectMapper.readValue(dataJson, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            
            return Flux.fromIterable(dataList)
                    .flatMap(data -> {
                        try {
                            // 将Map转换为JSON字符串
                            String json = objectMapper.writeValueAsString(data);
                            
                            // 创建表单数据对象
                            FormData formData = new FormData();
                            formData.setFormCode(formCode);
                            formData.setDataJson(json);
                            formData.setStatus(1); // 已提交状态
                            formData.setDataSource(1); // 接口导入
                            
                            return prepareAndSave(formData);
                        } catch (JsonProcessingException e) {
                            log.error("导入表单数据解析JSON失败", e);
                            return Mono.error(e);
                        }
                    })
                    .then(Mono.just(true))
                    .defaultIfEmpty(false);
        } catch (JsonProcessingException e) {
            log.error("导入表单数据解析JSON失败", e);
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Object> getFormDataStatistics(String formCode) {
        // 根据表单编码统计各状态的数据量
        return repository.findByFormCode(formCode)
                .collectMultimap(FormData::getStatus)
                .map(map -> {
                    Map<String, Object> result = Map.of(
                            "total", map.values().stream().mapToLong(list -> list.size()).sum(),
                            "draft", map.getOrDefault(0, List.of()).size(),
                            "submitted", map.getOrDefault(1, List.of()).size(),
                            "approved", map.getOrDefault(2, List.of()).size()
                    );
                    return result;
                });
    }

    @Override
    public Mono<Boolean> validateFormData(FormData formData) {
        // 这里可以实现表单数据验证逻辑
        // 简单实现，后续可以扩展更复杂的验证规则
        if (formData.getFormCode() == null || formData.getDataJson() == null) {
            return Mono.just(false);
        }
        
        try {
            // 验证JSON格式是否正确
            objectMapper.readTree(formData.getDataJson());
            return Mono.just(true);
        } catch (JsonProcessingException e) {
            log.error("表单数据JSON格式验证失败", e);
            return Mono.just(false);
        }
    }
}