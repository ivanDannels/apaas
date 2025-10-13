package org.apaas.system.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.interfaces.rest.BaseRest;
import org.apaas.system.domain.model.Notification;
import org.apaas.system.application.service.ReactiveNotificationService;
import org.apaas.system.application.dto.NotificationDTO;
import org.apaas.system.application.assembler.NotificationAssembler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 通知控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/notifications")
@Tag(name = "通知管理", description = "通知管理API")
public class NotificationRest extends BaseRest<Notification, Long, ReactiveNotificationService> {
    
    public NotificationRest(ReactiveNotificationService service) {
        super(service);
    }
    
    /**
     * 分页查询通知列表
     *
     * @param query 查询参数
     * @return 通知列表
     */
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询通知列表", description = "分页查询通知列表")
    public Mono<PageResult<NotificationDTO>> list(@RequestBody Query query) {
        return service.selectPage(query)
                .map(pageResult -> {
                    PageResult<NotificationDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(NotificationAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    /**
     * 获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取通知详情", description = "获取通知详情")
    @Parameter(name = "id", description = "通知ID", required = true)
    public Mono<NotificationDTO> detail(@PathVariable Long id) {
        return super.get(id)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    /**
     * 创建通知
     *
     * @param notificationDto 通知DTO
     * @return 创建结果
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建通知", description = "创建通知")
    public Mono<NotificationDTO> create(@Validated @RequestBody NotificationDTO notificationDto) {
        Notification notification = NotificationAssembler.INSTANCE.convertDtoToEntity(notificationDto);
        return super.add(notification)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    /**
     * 更新通知
     *
     * @param notificationDto 通知DTO
     * @return 更新结果
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新通知", description = "更新通知")
    public Mono<NotificationDTO> update(@PathVariable Long id, @Validated @RequestBody NotificationDTO notificationDto) {
        Notification notification = NotificationAssembler.INSTANCE.convertDtoToEntity(notificationDto);
        notification.setId(id);
        return super.update(notification)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    /**
     * 删除通知
     *
     * @param id 通知ID
     * @return 删除结果
     */
    @Override
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除通知", description = "删除通知")
    @Parameter(name = "id", description = "通知ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }
    
    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @return 标记结果
     */
    @PutMapping(value = "/{id}/read", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "标记通知为已读", description = "标记通知为已读")
    @Parameter(name = "id", description = "通知ID", required = true)
    public Mono<Boolean> markAsRead(@PathVariable Long id) {
        return service.markAsRead(id);
    }
    
    /**
     * 批量标记通知为已读
     *
     * @param ids 通知ID列表
     * @return 标记结果
     */
    @PutMapping(value = "/batch/read", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量标记通知为已读", description = "批量标记通知为已读")
    public Mono<Boolean> batchMarkAsRead(@RequestBody List<Long> ids) {
        return service.batchMarkAsRead(ids);
    }
    
    /**
     * 批量新增通知
     */
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量新增通知", description = "批量新增通知")
    public Flux<NotificationDTO> addBatchNotifications(@RequestBody Flux<NotificationDTO> notificationDtos) {
        Flux<Notification> notifications = notificationDtos.map(NotificationAssembler.INSTANCE::convertDtoToEntity);
        return service.saveBatch(notifications)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    /**
     * 批量更新通知
     */
    @PutMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量更新通知", description = "批量更新通知")
    public Flux<NotificationDTO> updateBatchNotifications(@RequestBody Flux<NotificationDTO> notificationDtos) {
        Flux<Notification> notifications = notificationDtos.map(NotificationAssembler.INSTANCE::convertDtoToEntity);
        return service.updateBatch(notifications)
                .map(NotificationAssembler.INSTANCE::convertEntityToDto);
    }
    
    /**
     * 导出通知
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "导出通知", description = "导出通知")
    public Mono<Void> exportExcel(ServerWebExchange exchange, @RequestBody(required = false) NotificationDTO query) {
        return service.exportExcel(exchange, query);
    }
    
    /**
     * 导入通知
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入通知", description = "导入通知")
    public Mono<Boolean> importExcel(@RequestBody byte[] data) {
        return service.importExcel(data);
    }
    
    /**
     * 查询用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    @GetMapping(value = "/unread/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "查询用户未读通知数量", description = "查询用户未读通知数量")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public Mono<Integer> countUnread(@RequestParam Long userId) {
        return service.countUnreadByUserId(userId);
    }
    
    /**
     * 查询用户通知列表
     *
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 通知列表
     */
    @GetMapping(value = "/user/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "查询用户通知列表", description = "查询用户通知列表")
    @Parameter(name = "userId", description = "用户ID", required = true)
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    public Mono<PageResult<NotificationDTO>> getUserNotifications(@RequestParam Long userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return service.getByUserId(userId, pageNum, pageSize).collectList()
                .map(notifications -> {
                    PageResult<Notification> pageResult = PageResult.of(notifications);
                    PageResult<NotificationDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(NotificationAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    /**
     * 发送通知
     *
     * @param id 通知ID
     * @return 发送结果
     */
    @PutMapping(value = "/{id}/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发送通知", description = "发送通知")
    @Parameter(name = "id", description = "通知ID", required = true)
    public Mono<Boolean> send(@PathVariable Long id) {
        Notification notification = service.getDetail(id).block();
        if (notification == null) {
            return Mono.just(false);
        }
        return service.send(notification);
    }
    
    /**
     * 批量发送通知
     *
     * @param ids 通知ID列表
     * @return 发送结果
     */
    @PutMapping(value = "/batch/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量发送通知", description = "批量发送通知")
    public Mono<Boolean> batchSend(@RequestBody List<Long> ids) {
        List<Notification> notifications = service.getAllByIds(ids).collectList().block();
        return service.batchSend(notifications);
    }
}