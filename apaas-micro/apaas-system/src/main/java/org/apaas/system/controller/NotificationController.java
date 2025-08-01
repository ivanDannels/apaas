package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.system.domain.dto.NotificationDTO;
import org.apaas.system.entity.Notification;
import org.apaas.system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 通知控制器
 */
@RestController
@RequestMapping("/notification")
@Tag(name = "通知管理", description = "通知管理API")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 分页查询通知列表
     *
     * @param notificationDTO 查询参数
     * @return 通知列表
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询通知列表", description = "分页查询通知列表")
    public Mono<List<Notification>> list(NotificationDTO notificationDTO) {
        return Mono.just(notificationService.queryPage(notificationDTO));
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
        return Mono.just(notificationService.getDetail(id));
    }

    /**
     * 创建通知
     *
     * @param notification 通知实体
     * @return 创建结果
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建通知", description = "创建通知")
    public Mono<Boolean> create(@RequestBody Notification notification) {
        return Mono.just(notificationService.create(notification));
    }

    /**
     * 更新通知
     *
     * @param notification 通知实体
     * @return 更新结果
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新通知", description = "更新通知")
    public Mono<Boolean> update(@RequestBody Notification notification) {
        return Mono.just(notificationService.update(notification));
    }

    /**
     * 删除通知
     *
     * @param id 通知ID
     * @return 删除结果
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除通知", description = "删除通知")
    @Parameter(name = "id", description = "通知ID", required = true)
    public Mono<Boolean> delete(@PathVariable Long id) {
        return Mono.just(notificationService.delete(id));
    }

    /**
     * 批量删除通知
     *
     * @param ids 通知ID列表
     * @return 删除结果
     */
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量删除通知", description = "批量删除通知")
    public Mono<Boolean> batchDelete(@RequestBody List<Long> ids) {
        return Mono.just(notificationService.batchDelete(ids));
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
        return Mono.just(notificationService.markAsRead(id));
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
        return Mono.just(notificationService.batchMarkAsRead(ids));
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
        return Mono.just(notificationService.countUnreadByUserId(userId));
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
    public Mono<List<Notification>> getUserNotifications(@RequestParam Long userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return Mono.just(notificationService.getByUserId(userId, pageNum, pageSize));
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
        Notification notification = notificationService.getDetail(id);
        if (notification == null) {
            return Mono.just(false);
        }
        return Mono.just(notificationService.send(notification));
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
        List<Notification> notifications = notificationService.listByIds(ids);
        return Mono.just(notificationService.batchSend(notifications));
    }
}