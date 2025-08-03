package com.apaas.system.core.service;

import com.apaas.system.core.domain.Notification;
import com.apaas.system.core.domain.NotificationChannel;
import com.apaas.system.core.domain.NotificationTemplate;
import com.apaas.system.core.domain.NotificationReceiver;
import com.apaas.system.core.domain.UserNotificationConfig;
import com.apaas.system.core.mapper.NotificationMapper;
import com.apaas.system.core.mapper.NotificationChannelMapper;
import com.apaas.system.core.mapper.NotificationTemplateMapper;
import com.apaas.system.core.mapper.NotificationReceiverMapper;
import com.apaas.system.core.mapper.UserNotificationConfigMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private NotificationChannelMapper channelMapper;

    @Mock
    private NotificationTemplateMapper templateMapper;

    @Mock
    private NotificationReceiverMapper receiverMapper;

    @Mock
    private UserNotificationConfigMapper configMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotificationChannel() {
        // Given
        NotificationChannel channel = new NotificationChannel();
        channel.setChannelCode("email");
        channel.setChannelName("邮件通知");
        channel.setStatus(1);
        channel.setConfig("{\"host\":\"smtp.example.com\",\"port\":465}");

        // When
        when(channelMapper.insertNotificationChannel(channel)).thenReturn(1);
        boolean result = notificationService.createNotificationChannel(channel);

        // Then
        assertTrue(result);
        verify(channelMapper, times(1)).insertNotificationChannel(channel);
    }

    @Test
    void testCreateNotificationChannelWithExistingCode() {
        // Given
        NotificationChannel channel = new NotificationChannel();
        channel.setChannelCode("sms");
        channel.setChannelName("短信通知");

        // When
        when(channelMapper.selectNotificationChannelByCode("sms")).thenReturn(new NotificationChannel());
        boolean result = notificationService.createNotificationChannel(channel);

        // Then
        assertFalse(result);
        verify(channelMapper, never()).insertNotificationChannel(any(NotificationChannel.class));
    }

    @Test
    void testGetNotificationChannelById() {
        // Given
        Long id = 1L;
        NotificationChannel expected = new NotificationChannel();
        expected.setId(id);
        expected.setChannelCode("email");
        expected.setChannelName("邮件通知");

        // When
        when(channelMapper.selectNotificationChannelById(id)).thenReturn(expected);
        NotificationChannel result = notificationService.getNotificationChannelById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("email", result.getChannelCode());
        assertEquals("邮件通知", result.getChannelName());
    }

    @Test
    void testUpdateNotificationChannel() {
        // Given
        NotificationChannel channel = new NotificationChannel();
        channel.setId(1L);
        channel.setChannelCode("email");
        channel.setChannelName("电子邮件通知");
        channel.setStatus(1);

        // When
        when(channelMapper.updateNotificationChannel(channel)).thenReturn(1);
        boolean result = notificationService.updateNotificationChannel(channel);

        // Then
        assertTrue(result);
        verify(channelMapper, times(1)).updateNotificationChannel(channel);
    }

    @Test
    void testDeleteNotificationChannel() {
        // Given
        Long id = 1L;

        // When
        when(channelMapper.deleteNotificationChannel(id)).thenReturn(1);
        boolean result = notificationService.deleteNotificationChannel(id);

        // Then
        assertTrue(result);
        verify(channelMapper, times(1)).deleteNotificationChannel(id);
    }

    @Test
    void testListNotificationChannels() {
        // Given
        NotificationChannel channel1 = new NotificationChannel();
        channel1.setId(1L);
        channel1.setChannelCode("email");

        NotificationChannel channel2 = new NotificationChannel();
        channel2.setId(2L);
        channel2.setChannelCode("sms");

        List<NotificationChannel> expected = Arrays.asList(channel1, channel2);

        // When
        when(channelMapper.selectNotificationChannelList(any())).thenReturn(expected);
        List<NotificationChannel> result = notificationService.listNotificationChannels(new NotificationChannel());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("email", result.get(0).getChannelCode());
        assertEquals("sms", result.get(1).getChannelCode());
    }

    @Test
    void testDisableNotificationChannel() {
        // Given
        Long id = 1L;
        NotificationChannel channel = new NotificationChannel();
        channel.setId(id);
        channel.setStatus(1);

        // When
        when(channelMapper.selectNotificationChannelById(id)).thenReturn(channel);
        when(channelMapper.updateNotificationChannelStatus(id, 0)).thenReturn(1);
        boolean result = notificationService.disableNotificationChannel(id);

        // Then
        assertTrue(result);
        assertEquals(0, channel.getStatus());
        verify(channelMapper, times(1)).updateNotificationChannelStatus(id, 0);
    }

    @Test
    void testEnableNotificationChannel() {
        // Given
        Long id = 1L;
        NotificationChannel channel = new NotificationChannel();
        channel.setId(id);
        channel.setStatus(0);

        // When
        when(channelMapper.selectNotificationChannelById(id)).thenReturn(channel);
        when(channelMapper.updateNotificationChannelStatus(id, 1)).thenReturn(1);
        boolean result = notificationService.enableNotificationChannel(id);

        // Then
        assertTrue(result);
        assertEquals(1, channel.getStatus());
        verify(channelMapper, times(1)).updateNotificationChannelStatus(id, 1);
    }

    @Test
    void testCreateNotificationTemplate() {
        // Given
        NotificationTemplate template = new NotificationTemplate();
        template.setTemplateCode("USER_REGISTER");
        template.setTemplateName("用户注册通知");
        template.setChannelCode("email");
        template.setContent("亲爱的{username}，欢迎注册我们的系统！");
        template.setSubject("注册成功通知");
        template.setStatus(1);

        // When
        when(templateMapper.insertNotificationTemplate(template)).thenReturn(1);
        boolean result = notificationService.createNotificationTemplate(template);

        // Then
        assertTrue(result);
        verify(templateMapper, times(1)).insertNotificationTemplate(template);
    }

    @Test
    void testUpdateNotificationTemplate() {
        // Given
        NotificationTemplate template = new NotificationTemplate();
        template.setId(1L);
        template.setTemplateCode("USER_REGISTER");
        template.setContent("亲爱的{username}，欢迎注册我们的系统！您的账号已成功创建。");

        // When
        when(templateMapper.updateNotificationTemplate(template)).thenReturn(1);
        boolean result = notificationService.updateNotificationTemplate(template);

        // Then
        assertTrue(result);
        verify(templateMapper, times(1)).updateNotificationTemplate(template);
    }

    @Test
    void testGetNotificationTemplateByCode() {
        // Given
        String code = "USER_REGISTER";
        NotificationTemplate expected = new NotificationTemplate();
        expected.setTemplateCode(code);
        expected.setContent("亲爱的{username}，欢迎注册我们的系统！");

        // When
        when(templateMapper.selectNotificationTemplateByCode(code)).thenReturn(expected);
        NotificationTemplate result = notificationService.getNotificationTemplateByCode(code);

        // Then
        assertNotNull(result);
        assertEquals(code, result.getTemplateCode());
        assertEquals("亲爱的{username}，欢迎注册我们的系统！", result.getContent());
    }

    @Test
    void testSendNotification() {
        // Given
        Notification notification = new Notification();
        notification.setTemplateCode("USER_REGISTER");
        notification.setReceiverId(1L);
        notification.setSenderId(0L);
        notification.setSendTime(LocalDateTime.now());
        notification.setStatus(0); // 未发送

        Map<String, String> params = new HashMap<>();
        params.put("username", "张三");
        notification.setParams(params);

        NotificationTemplate template = new NotificationTemplate();
        template.setTemplateCode("USER_REGISTER");
        template.setChannelCode("email");
        template.setContent("亲爱的{username}，欢迎注册我们的系统！");
        template.setSubject("注册成功通知");
        template.setStatus(1);

        UserNotificationConfig config = new UserNotificationConfig();
        config.setUserId(1L);
        config.setChannelCode("email");
        config.setStatus(1);
        config.setEmail("zhangsan@example.com");

        // When
        when(templateMapper.selectNotificationTemplateByCode("USER_REGISTER")).thenReturn(template);
        when(configMapper.selectUserNotificationConfigByUserIdAndChannel(1L, "email")).thenReturn(config);
        when(notificationMapper.insertNotification(notification)).thenReturn(1);
        when(notificationService.sendNotification(notification)).thenReturn(true);

        boolean result = notificationService.sendNotification(notification);

        // Then
        assertTrue(result);
        verify(notificationMapper, times(1)).insertNotification(notification);
    }

    @Test
    void testGetUserNotifications() {
        // Given
        Long userId = 1L;
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setReceiverId(userId);
        notification1.setTitle("通知标题1");
        notification1.setReadStatus(0);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setReceiverId(userId);
        notification2.setTitle("通知标题2");
        notification2.setReadStatus(1);

        List<Notification> expected = Arrays.asList(notification1, notification2);

        // When
        when(notificationMapper.selectUserNotifications(userId, null)).thenReturn(expected);

        List<Notification> result = notificationService.getUserNotifications(userId, null);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("通知标题1", result.get(0).getTitle());
        assertEquals("通知标题2", result.get(1).getTitle());
    }

    @Test
    void testDeleteNotification() {
        // Given
        Long notificationId = 1L;

        // When
        when(notificationMapper.deleteNotification(notificationId)).thenReturn(1);
        boolean result = notificationService.deleteNotification(notificationId);

        // Then
        assertTrue(result);
        verify(notificationMapper, times(1)).deleteNotification(notificationId);
    }

    @Test
    void testMarkNotificationAsRead() {
        // Given
        Long notificationId = 1L;

        // When
        when(notificationMapper.updateNotificationReadStatus(notificationId, 1)).thenReturn(1);
        boolean result = notificationService.markNotificationAsRead(notificationId);

        // Then
        assertTrue(result);
        verify(notificationMapper, times(1)).updateNotificationReadStatus(notificationId, 1);
    }

    @Test
    void testMarkAllNotificationsAsRead() {
        // Given
        Long userId = 1L;

        // When
        when(notificationMapper.updateAllNotificationsReadStatus(userId, 1)).thenReturn(2);
        boolean result = notificationService.markAllNotificationsAsRead(userId);

        // Then
        assertTrue(result);
        verify(notificationMapper, times(1)).updateAllNotificationsReadStatus(userId, 1);
    }

    @Test
    void testSaveUserNotificationConfig() {
        // Given
        UserNotificationConfig config = new UserNotificationConfig();
        config.setUserId(1L);
        config.setChannelCode("email");
        config.setStatus(1);
        config.setEmail("user@example.com");

        // When
        when(configMapper.insertUserNotificationConfig(config)).thenReturn(1);
        boolean result = notificationService.saveUserNotificationConfig(config);

        // Then
        assertTrue(result);
        verify(configMapper, times(1)).insertUserNotificationConfig(config);
    }

    @Test
    void testUpdateUserNotificationConfig() {
        // Given
        UserNotificationConfig config = new UserNotificationConfig();
        config.setId(1L);
        config.setUserId(1L);
        config.setChannelCode("email");
        config.setStatus(0);
        config.setEmail("updated@example.com");

        // When
        when(configMapper.updateUserNotificationConfig(config)).thenReturn(1);
        boolean result = notificationService.updateUserNotificationConfig(config);

        // Then
        assertTrue(result);
        verify(configMapper, times(1)).updateUserNotificationConfig(config);
    }

    @Test
    void testGetUserNotificationConfig() {
        // Given
        Long userId = 1L;
        UserNotificationConfig config = new UserNotificationConfig();
        config.setId(1L);
        config.setUserId(userId);
        config.setChannelCode("email");
        config.setStatus(1);

        List<UserNotificationConfig> expected = Arrays.asList(config);

        // When
        when(configMapper.selectUserNotificationConfigByUserId(userId)).thenReturn(expected);
        List<UserNotificationConfig> result = notificationService.getUserNotificationConfig(userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("email", result.get(0).getChannelCode());
    }
        notification1.setReceiverId(userId);
        notification1.setTitle("通知标题1");

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setReceiverId(userId);
        notification2.setTitle("通知标题2");

        List<Notification> expected = Arrays.asList(notification1, notification2);

        // When
        when(notificationMapper.selectUserNotifications(userId, anyInt(), anyInt())).thenReturn(expected);
        when(notificationMapper.countUserNotifications(userId)).thenReturn(2);

        Map<String, Object> result = notificationService.getUserNotifications(userId, 1, 10);

        // Then
        assertNotNull(result);
        assertEquals(2, result.get("total"));
        assertEquals(expected, result.get("notifications"));
    }

    @Test
    void testMarkNotificationAsRead() {
        // Given
        Long notificationId = 1L;
        Long userId = 1L;

        // When
        when(notificationService.markNotificationAsRead(notificationId, userId)).thenReturn(true);
        boolean result = notificationService.markNotificationAsRead(notificationId, userId);

        // Then
        assertTrue(result);
        verify(notificationMapper, times(1)).updateNotificationReadStatus(notificationId, userId);
    }

    @Test
    void testMarkAllNotificationsAsRead() {
        // Given
        Long userId = 1L;

        // When
        when(notificationService.markAllNotificationsAsRead(userId)).thenReturn(true);
        boolean result = notificationService.markAllNotificationsAsRead(userId);

        // Then
        assertTrue(result);
        verify(notificationMapper, times(1)).updateAllNotificationReadStatusByUserId(userId);
    }

    @Test
    void testDeleteNotification() {
        // Given
        Long notificationId = 1L;
        Long userId = 1L;

        // When
        when(notificationService.deleteNotification(notificationId, userId)).thenReturn(true);
        boolean result = notificationService.deleteNotification(notificationId, userId);

        // Then
        assertTrue(result);
        verify(notificationMapper, times(1)).deleteNotification(notificationId, userId);
    }

    @Test
    void testSaveUserNotificationConfig() {
        // Given
        UserNotificationConfig config = new UserNotificationConfig();
        config.setUserId(1L);
        config.setChannelCode("email");
        config.setStatus(1);
        config.setEmail("zhangsan@example.com");

        // When
        when(configMapper.insertUserNotificationConfig(config)).thenReturn(1);
        boolean result = notificationService.saveUserNotificationConfig(config);

        // Then
        assertTrue(result);
        verify(configMapper, times(1)).insertUserNotificationConfig(config);
    }

    @Test
    void testUpdateUserNotificationConfig() {
        // Given
        UserNotificationConfig config = new UserNotificationConfig();
        config.setId(1L);
        config.setUserId(1L);
        config.setChannelCode("email");
        config.setStatus(0);

        // When
        when(configMapper.updateUserNotificationConfig(config)).thenReturn(1);
        boolean result = notificationService.updateUserNotificationConfig(config);

        // Then
        assertTrue(result);
        verify(configMapper, times(1)).updateUserNotificationConfig(config);
    }

    @Test
    void testGetUserNotificationConfig() {
        // Given
        Long userId = 1L;
        UserNotificationConfig expected = new UserNotificationConfig();
        expected.setUserId(userId);
        expected.setChannelCode("email");
        expected.setStatus(1);

        // When
        when(configMapper.selectUserNotificationConfigByUserIdAndChannel(userId, "email")).thenReturn(expected);
        UserNotificationConfig result = notificationService.getUserNotificationConfig(userId, "email");

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("email", result.getChannelCode());
        assertEquals(1, result.getStatus());
    }
}