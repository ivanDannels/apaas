package org.apaas.system.service.impl;

import org.apaas.system.entity.Notification;
import org.apaas.system.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReactiveNotificationServiceImplTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private ReactiveNotificationServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testQueryPage() {
        // 准备测试数据
        Notification notification1 = Notification.builder()
                .id(1L)
                .title("通知1")
                .content("通知内容1")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        Notification notification2 = Notification.builder()
                .id(2L)
                .title("通知2")
                .content("通知内容2")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.findAll()).thenReturn(Flux.just(notification1, notification2));

        // 执行测试
        Flux<Notification> result = service.queryPage(null);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(notification1)
                .expectNext(notification2)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        // 准备测试数据
        Notification notification = Notification.builder()
                .id(1L)
                .title("通知1")
                .content("通知内容1")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.save(any(Notification.class))).thenReturn(Mono.just(notification));

        // 执行测试
        Mono<Boolean> result = service.create(notification);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).save(notification);
    }

    @Test
    void testUpdate() {
        // 准备测试数据
        Notification notification = Notification.builder()
                .id(1L)
                .title("更新的通知1")
                .content("更新的通知内容1")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.save(any(Notification.class))).thenReturn(Mono.just(notification));

        // 执行测试
        Mono<Boolean> result = service.update(notification);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).save(notification);
    }

    @Test
    void testDelete() {
        // 模拟Repository行为
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        // 执行测试
        Mono<Boolean> result = service.delete(1L);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testMarkAsRead() {
        // 准备测试数据
        Notification notification = Notification.builder()
                .id(1L)
                .title("通知1")
                .content("通知内容1")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        Notification updatedNotification = Notification.builder()
                .id(1L)
                .title("通知1")
                .content("通知内容1")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(1)
                .readTime(LocalDateTime.now())
                .sendStatus(1)
                .sendTime(notification.getSendTime())
                .build();

        // 模拟Repository行为
        when(repository.findById(1L)).thenReturn(Mono.just(notification));
        when(repository.save(any(Notification.class))).thenReturn(Mono.just(updatedNotification));

        // 执行测试
        Mono<Boolean> result = service.markAsRead(1L);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Notification.class));
    }

    @Test
    void testCountUnreadByUserId() {
        // 准备测试数据
        Notification notification1 = Notification.builder()
                .id(1L)
                .title("通知1")
                .content("通知内容1")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        Notification notification2 = Notification.builder()
                .id(2L)
                .title("通知2")
                .content("通知内容2")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.findByReceiverIdAndReadStatus(1L, 0)).thenReturn(Flux.just(notification1, notification2));

        // 执行测试
        Mono<Integer> result = service.countUnreadByUserId(1L);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(2)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findByReceiverIdAndReadStatus(1L, 0);
    }

    @Test
    void testGetByUserId() {
        // 准备测试数据
        Notification notification1 = Notification.builder()
                .id(1L)
                .title("通知1")
                .content("通知内容1")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        Notification notification2 = Notification.builder()
                .id(2L)
                .title("通知2")
                .content("通知内容2")
                .receiverId(1L)
                .receiverName("接收者1")
                .senderId(2L)
                .senderName("发送者1")
                .type("系统通知")
                .readStatus(0)
                .sendStatus(1)
                .sendTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.findByReceiverId(1L)).thenReturn(Flux.just(notification1, notification2));

        // 执行测试
        Flux<Notification> result = service.getByUserId(1L, 1, 10);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(notification1)
                .expectNext(notification2)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findByReceiverId(1L);
    }
}