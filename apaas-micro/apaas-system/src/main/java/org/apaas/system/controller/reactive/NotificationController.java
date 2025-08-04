package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.core.web.controller.ReactiveBaseController;
import org.apaas.system.entity.Notification;
import org.apaas.system.service.reactive.ReactiveNotificationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
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
public class NotificationController extends ReactiveBaseController<Notification, Long, ReactiveNotificationService> {
    
    public NotificationController(ReactiveNotificationService service) {
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
    public Mono<PageResult<Notification>> list(@RequestBody Query query) {
        return service.selectPage(query);
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
    public Mono<Notification> detail(@PathVariable Long id) {
        return super.get(id);
    }

    /**
     * 创建通知
     *
     * @param notification 通知实体
     * @return 创建结果
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建通知", description = "创建通知")
    public Mono<Notification> create(@Validated @RequestBody Notification notification) {
        return super.add(notification);
    }

    /**
     * 更新通知
     *
     * @param notification 通知实体
     * @return 更新结果
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新通知", description = "更新通知")
    public Mono<Notification> update(@PathVariable Long id, @Validated @RequestBody Notification notification) {
        notification.setId(id);
        return super.update(notification);
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
    @Override
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量新增通知", description = "批量新增通知")
    public Flux<Notification> addBatch(@RequestBody Flux<Notification> notifications) {
        return service.saveBatch(notifications);
    }
    
    /**
     * 批量更新通知
     */
    @Override
    @PutMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量更新通知", description = "批量更新通知")
    public Flux<Notification> updateBatch(@RequestBody Flux<Notification> notifications) {
        return service.updateBatch(notifications);
    }
    
    /**
     * 导出通知
     */
    @Override
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导出通知", description = "导出通知")
    public Mono<byte[]> export(@RequestBody Query query) {
        return service.export(query);
    }
    
    /**
     * 导入通知
     */
    @Override
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入通知", description = "导入通知")
    public Mono<Void> importData(@RequestBody byte[] data) {
        return service.importData(data);
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
    public Mono<PageResult<Notification>> getUserNotifications(@RequestParam Long userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return service.getByUserId(userId, pageNum, pageSize).collectList().map(PageResult::of);
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