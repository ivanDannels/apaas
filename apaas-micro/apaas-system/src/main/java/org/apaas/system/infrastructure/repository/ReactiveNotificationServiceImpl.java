package org.apaas.system.infrastructure.repository;

import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.system.domain.model.Notification;
import org.apaas.system.domain.repository.NotificationRepository;
import org.apaas.system.application.service.ReactiveNotificationService;
import org.apaas.system.application.dto.NotificationDTO;
import org.apaas.system.application.assembler.NotificationAssembler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 响应式通知服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveNotificationServiceImpl extends AbstractApplicationService<Notification, Long, NotificationRepository> implements ReactiveNotificationService {
    

    
    public ReactiveNotificationServiceImpl(NotificationRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<NotificationDTO> queryPage(NotificationDTO notificationDTO) {
        // 实现分页查询逻辑
        if (notificationDTO != null) {
            // 根据查询条件过滤数据
            return repository.findAll()
                .filter(notification -> {
                    boolean match = true;
                    if (notificationDTO.getTitle() != null && !notificationDTO.getTitle().isEmpty()) {
                        match = notification.getTitle() != null && notification.getTitle().contains(notificationDTO.getTitle());
                    }
                    if (match && notificationDTO.getContent() != null && !notificationDTO.getContent().isEmpty()) {
                        match = notification.getContent() != null && notification.getContent().contains(notificationDTO.getContent());
                    }
                    if (match && notificationDTO.getReceiverId() != null) {
                        match = notification.getReceiverId() != null && notification.getReceiverId().equals(notificationDTO.getReceiverId());
                    }
                    if (match && notificationDTO.getType() != null && !notificationDTO.getType().isEmpty()) {
                        match = notification.getType() != null && notification.getType().equals(notificationDTO.getType());
                    }
                    if (match && notificationDTO.getReadStatus() != null) {
                        match = notification.getReadStatus() != null && notification.getReadStatus().equals(notificationDTO.getReadStatus());
                    }
                    if (match && notificationDTO.getSendStatus() != null) {
                        match = notification.getSendStatus() != null && notification.getSendStatus().equals(notificationDTO.getSendStatus());
                    }
                    return match;
                })
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
        } else {
            // 无查询条件时返回所有通知
            return repository.findAll()
                    .map(NotificationAssembler.INSTANCE::convertEntityToDto);
        }
    }
    
    @Override
    public Mono<NotificationDTO> getDetail(Long id) {
        return repository.findById(id)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Flux<NotificationDTO> getAllByIds(List<Long> ids) {
        return repository.findAllById(ids)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Boolean> create(NotificationDTO notificationDto) {
        Notification notification = NotificationAssembler.INSTANCE.convertDtoToEntity(notificationDto);
        return repository.save(notification).map(savedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> update(NotificationDTO notificationDto) {
        Notification notification = NotificationAssembler.INSTANCE.convertDtoToEntity(notificationDto);
        return repository.save(notification).map(updatedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> delete(Long id) {
        return repository.deleteById(id).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> batchDelete(List<Long> ids) {
        return Flux.fromIterable(ids).flatMap(id -> repository.deleteById(id)).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> markAsRead(Long id) {
        return repository.findById(id).flatMap(notification -> {
            notification.setReadStatus(1);
            notification.setReadTime(LocalDateTime.now());
            return repository.save(notification);
        }).map(updatedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> batchMarkAsRead(List<Long> ids) {
        return Flux.fromIterable(ids).flatMap(id -> repository.findById(id)).flatMap(notification -> {
            notification.setReadStatus(1);
            notification.setReadTime(LocalDateTime.now());
            return repository.save(notification);
        }).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Integer> countUnreadByUserId(Long userId) {
        // 实现查询用户未读通知数量的逻辑
        return repository.findByReceiverIdAndReadStatus(userId, 0)
            .count()
            .map(Long::intValue);
    }
    
    @Override
    public Flux<NotificationDTO> getByUserId(Long userId, Integer pageNum, Integer pageSize) {
        // 实现分页查询用户通知的逻辑
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNum - 1);
        return repository.findByReceiverId(userId)
            .skip(pageable.getOffset())
            .take(pageable.getPageSize())
            .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Boolean> send(NotificationDTO notificationDto) {
        Notification notification = NotificationAssembler.INSTANCE.convertDtoToEntity(notificationDto);
        notification.setSendStatus(1);
        notification.setSendTime(LocalDateTime.now());
        return repository.save(notification).map(savedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> batchSend(List<NotificationDTO> notificationDtos) {
        List<Notification> notifications = NotificationAssembler.INSTANCE.convertDtoListToEntityList(notificationDtos);
        return Flux.fromIterable(notifications).doOnNext(notification -> {
            notification.setSendStatus(1);
            notification.setSendTime(LocalDateTime.now());
        }).flatMap(notification -> repository.save(notification)).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, NotificationDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=notification.xlsx");
        
        // 实现Excel导出逻辑
        Flux<Notification> notificationFlux;
        if (query != null) {
            // 根据查询条件过滤数据
            notificationFlux = repository.findAll()
                .filter(notification -> {
                    boolean match = true;
                    if (query.getTitle() != null && !query.getTitle().isEmpty()) {
                        match = notification.getTitle() != null && notification.getTitle().contains(query.getTitle());
                    }
                    if (match && query.getContent() != null && !query.getContent().isEmpty()) {
                        match = notification.getContent() != null && notification.getContent().contains(query.getContent());
                    }
                    if (match && query.getReceiverId() != null) {
                        match = notification.getReceiverId() != null && notification.getReceiverId().equals(query.getReceiverId());
                    }
                    if (match && query.getType() != null && !query.getType().isEmpty()) {
                        match = notification.getType() != null && notification.getType().equals(query.getType());
                    }
                    if (match && query.getReadStatus() != null) {
                        match = notification.getReadStatus() != null && notification.getReadStatus().equals(query.getReadStatus());
                    }
                    if (match && query.getSendStatus() != null) {
                        match = notification.getSendStatus() != null && notification.getSendStatus().equals(query.getSendStatus());
                    }
                    return match;
                });
        } else {
            // 无查询条件时导出所有数据
            notificationFlux = repository.findAll();
        }
        
        // 这里需要实现Excel导出逻辑
        // 暂时返回空响应
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(new byte[0]);
        return response.writeWith(Mono.just(dataBuffer));
    }
    
    @Override
    public Mono<Boolean> importExcel(byte[] fileData) {
        // 实现Excel导入逻辑
        // 暂时返回true
        return Mono.just(true);
    }
    
    @Override
    public Flux<Notification> saveBatch(Flux<Notification> dtoList) {
        return super.saveBatch(dtoList);
    }
    
    @Override
    public Flux<Notification> updateBatch(Flux<Notification> notifications) {
        return super.updateBatch(notifications);
    }
    
    @Override
    public Flux<NotificationDTO> saveBatch(Flux<NotificationDTO> notificationDtos) {
        Flux<Notification> notifications = notificationDtos.map(NotificationAssembler.INSTANCE::convertDtoToEntity);
        return super.saveBatch(notifications)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Flux<NotificationDTO> updateBatch(Flux<NotificationDTO> notificationDtos) {
        Flux<Notification> notifications = notificationDtos.map(NotificationAssembler.INSTANCE::convertDtoToEntity);
        return super.updateBatch(notifications)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    

}