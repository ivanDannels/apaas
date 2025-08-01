package org.apaas.core.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class BaseEntityTest {

    @Test
    void testBaseEntityFields() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(100L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setDeleted(0);
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 验证基础字段
        assertEquals(1L, entity.getId());
        assertEquals(100L, entity.getTenantId());
        assertEquals("testUser", entity.getCreator());
        assertNotNull(entity.getCreatedTime());
        assertEquals("testUser", entity.getUpdater());
        assertNotNull(entity.getUpdatedTime());
        assertEquals(0, entity.getDeleted());
        
        // 验证实体特定字段
        assertEquals("Test Entity", entity.getName());
        assertEquals("Test Description", entity.getDescription());
    }
}