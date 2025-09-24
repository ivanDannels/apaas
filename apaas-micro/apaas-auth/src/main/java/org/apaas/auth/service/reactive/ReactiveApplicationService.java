package org.apaas.auth.service.reactive;

import org.apaas.auth.entity.Application;
import org.apaas.domain.service.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveApplicationService extends BaseService<Application, Long> {

    /**
     * 根据应用名称获取应用
     *
     * @param name 应用名称
     * @return 应用信息
     */
    Mono<Application> getApplicationByName(String name);

    /**
     * 根据应用编码获取应用
     *
     * @param code 应用编码
     * @return 应用信息
     */
    Mono<Application> getApplicationByCode(String code);

    /**
     * 获取所有应用列表
     *
     * @return 应用列表
     */
    Flux<Application> getAllApplications();

    /**
     * 更新应用状态
     *
     * @param id     应用ID
     * @param status 应用状态
     * @return 是否成功
     */
    Mono<Boolean> updateApplicationStatus(Long id, Integer status);
}