package org.apaas.flow.engine.application.assembler;

import org.apaas.flow.engine.domain.model.FlowDefinition;
import org.apaas.flow.engine.application.dto.FlowDefinitionDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlowDefinitionAssemblerTest {
    
    private final FlowDefinitionAssembler assembler = Mappers.getMapper(FlowDefinitionAssembler.class);
    
    @Test
    void testToDTO() {
        // 创建实体对象
        FlowDefinition entity = FlowDefinition.builder()
                .id(1L)
                .name("测试流程")
                .description("测试流程描述")
                .version(1)
                .definition("流程定义内容")
                .status(1)
                .createdBy("admin")
                .createdTime(LocalDateTime.now())
                .updatedBy("admin")
                .updatedTime(LocalDateTime.now())
                .build();
        
        // 转换为DTO
        FlowDefinitionDTO dto = assembler.toDTO(entity);
        
        // 验证转换结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getVersion(), dto.getVersion());
        assertEquals(entity.getDefinition(), dto.getDefinition());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getCreatedBy(), dto.getCreatedBy());
    }
    
    @Test
    void testToEntity() {
        // 创建DTO对象
        FlowDefinitionDTO dto = new FlowDefinitionDTO();
        dto.setId(1L);
        dto.setName("测试流程");
        dto.setDescription("测试流程描述");
        dto.setVersion(1);
        dto.setDefinition("流程定义内容");
        dto.setStatus(1);
        dto.setCreatedBy("admin");
        dto.setCreatedTime(LocalDateTime.now());
        dto.setUpdatedBy("admin");
        dto.setUpdatedTime(LocalDateTime.now());
        
        // 转换为实体
        FlowDefinition entity = assembler.toEntity(dto);
        
        // 验证转换结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getVersion(), entity.getVersion());
        assertEquals(dto.getDefinition(), entity.getDefinition());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getCreatedBy(), entity.getCreatedBy());
    }
    
    @Test
    void testToDTOList() {
        // 创建实体列表
        List<FlowDefinition> entities = Arrays.asList(
                FlowDefinition.builder().id(1L).name("流程1").build(),
                FlowDefinition.builder().id(2L).name("流程2").build()
        );
        
        // 转换为DTO列表
        List<FlowDefinitionDTO> dtos = assembler.toDTOList(entities);
        
        // 验证转换结果
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getId(), dtos.get(0).getId());
        assertEquals(entities.get(1).getId(), dtos.get(1).getId());
    }
    
    @Test
    void testToEntityList() {
        // 创建DTO列表
        List<FlowDefinitionDTO> dtos = Arrays.asList(
                new FlowDefinitionDTO() {{ setId(1L); setName("流程1"); }},
                new FlowDefinitionDTO() {{ setId(2L); setName("流程2"); }}
        );
        
        // 转换为实体列表
        List<FlowDefinition> entities = assembler.toEntityList(dtos);
        
        // 验证转换结果
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).getId(), entities.get(0).getId());
        assertEquals(dtos.get(1).getId(), entities.get(1).getId());
    }
}