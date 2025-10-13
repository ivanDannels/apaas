package org.apaas.integration.application.assembler;

import org.apaas.integration.domain.model.IntegrationEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationAssemblerTest {
    
    private final IntegrationAssembler assembler = Mappers.getMapper(IntegrationAssembler.class);
    
    @Test
    void testToEntity() {
        // 创建实体对象
        IntegrationEntity entity = new IntegrationEntity();
        entity.setId(1L);
        entity.setName("测试集成");
        entity.setType("DATABASE");
        
        Map<String, Object> config = new HashMap<>();
        config.put("url", "jdbc:postgresql://localhost:5432/test");
        config.put("username", "testuser");
        config.put("password", "testpass");
        entity.setConfig(config);
        
        entity.setStatus(1);
        entity.setCreatedBy("admin");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdatedBy("admin");
        entity.setUpdatedTime(LocalDateTime.now());
        
        // 转换实体
        IntegrationEntity result = assembler.toEntity(entity);
        
        // 验证转换结果
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getType(), result.getType());
        assertEquals(entity.getConfig(), result.getConfig());
        assertEquals(entity.getStatus(), result.getStatus());
        assertEquals(entity.getCreatedBy(), result.getCreatedBy());
    }
    
    @Test
    void testToDTO() {
        // 创建实体对象
        IntegrationEntity entity = new IntegrationEntity();
        entity.setId(1L);
        entity.setName("测试集成");
        entity.setType("DATABASE");
        
        Map<String, Object> config = new HashMap<>();
        config.put("url", "jdbc:postgresql://localhost:5432/test");
        config.put("username", "testuser");
        config.put("password", "testpass");
        entity.setConfig(config);
        
        entity.setStatus(1);
        entity.setCreatedBy("admin");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdatedBy("admin");
        entity.setUpdatedTime(LocalDateTime.now());
        
        // 转换为DTO
        IntegrationEntity dto = assembler.toDTO(entity);
        
        // 验证转换结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getConfig(), dto.getConfig());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getCreatedBy(), dto.getCreatedBy());
    }
}