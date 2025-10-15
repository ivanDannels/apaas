package org.apaas.job.infrastructure.repository;

import org.apaas.application.service.AbstractApplicationService;
import org.apaas.job.application.assembler.JobAssembler;
import org.apaas.job.application.dto.JobDTO;
import org.apaas.job.domain.model.JobEntity;
import org.apaas.job.domain.repository.JobRepository;
import org.apaas.job.application.service.JobService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
// 修改类声明，正确指定所有泛型参数
public class JobServiceImpl extends AbstractApplicationService<JobEntity, JobDTO, Long, JobRepository> implements JobService {
    
    // 移除重复定义的repository和jobAssembler字段，因为它们已经在父类中定义了
    // private final JobRepository repository;
    // private final JobAssembler jobAssembler = JobAssembler.INSTANCE;
    
    // 修改构造函数，正确传递repository和assembler参数
    public JobServiceImpl(JobRepository repository, JobAssembler assembler) {
        super(repository, assembler);
    }
    
    // 移除在父类中已实现的方法，只保留JobService特有的方法
    /*
    @Override
    public Flux<JobDTO> findAll() {
        return repository.findAll().map(jobAssembler::toDTO);
    }
    
    @Override
    public Mono<JobDTO> findById(Long id) {
        return repository.findById(id).map(jobAssembler::toDTO);
    }
    
    @Override
    public Mono<JobDTO> save(JobDTO jobDTO) {
        JobEntity entity = jobAssembler.toEntity(jobDTO);
        entity.setUpdatedTime(LocalDateTime.now());
        return repository.save(entity).map(jobAssembler::toDTO);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
    */
    
    @Override
    public Mono<Void> enableJob(Long id) {
        return domainService.findById(id).flatMap(entity -> {
            entity.setStatus(0); // 0-启用
            entity.setUpdatedTime(LocalDateTime.now());
            return domainService.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> disableJob(Long id) {
        return domainService.findById(id).flatMap(entity -> {
            entity.setStatus(1); // 1-禁用
            entity.setUpdatedTime(LocalDateTime.now());
            return domainService.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> triggerJob(Long id) {
        return domainService.findById(id).flatMap(entity -> {
            // 记录手动触发日志
            entity.setLastTriggerTime(LocalDateTime.now());
            entity.setUpdatedTime(LocalDateTime.now());
            return domainService.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> pauseJob(Long id) {
        return domainService.findById(id).flatMap(entity -> {
            entity.setStatus(2); // 2-暂停
            entity.setUpdatedTime(LocalDateTime.now());
            return domainService.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> resumeJob(Long id) {
        return domainService.findById(id).flatMap(entity -> {
            entity.setStatus(0); // 0-启用
            entity.setUpdatedTime(LocalDateTime.now());
            return domainService.save(entity);
        }).then();
    }
}