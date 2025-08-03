package com.apaas.system.core.service;

import com.apaas.system.core.domain.ParamType;
import com.apaas.system.core.domain.ParamItem;
import com.apaas.system.core.mapper.ParamTypeMapper;
import com.apaas.system.core.mapper.ParamItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParamServiceTest {

    @Mock
    private ParamTypeMapper paramTypeMapper;

    @Mock
    private ParamItemMapper paramItemMapper;

    @InjectMocks
    private ParamServiceImpl paramService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateParamType() {
        // Given
        ParamType paramType = new ParamType();
        paramType.setTypeCode("TEST_PARAM_TYPE");
        paramType.setTypeName("测试参数类型");
        paramType.setStatus(1);

        // When
        when(paramTypeMapper.insertParamType(paramType)).thenReturn(1);
        boolean result = paramService.createParamType(paramType);

        // Then
        assertTrue(result);
        verify(paramTypeMapper, times(1)).insertParamType(paramType);
    }

    @Test
    void testCreateParamTypeWithExistingCode() {
        // Given
        ParamType paramType = new ParamType();
        paramType.setTypeCode("EXISTING_TYPE");
        paramType.setTypeName("已存在的参数类型");
        paramType.setStatus(1);

        // When
        when(paramTypeMapper.selectParamTypeByCode("EXISTING_TYPE")).thenReturn(new ParamType());
        boolean result = paramService.createParamType(paramType);

        // Then
        assertFalse(result);
        verify(paramTypeMapper, never()).insertParamType(any(ParamType.class));
    }

    @Test
    void testGetParamTypeById() {
        // Given
        Long id = 1L;
        ParamType expected = new ParamType();
        expected.setId(id);
        expected.setTypeCode("TEST_PARAM_TYPE");
        expected.setTypeName("测试参数类型");

        // When
        when(paramTypeMapper.selectParamTypeById(id)).thenReturn(expected);
        ParamType result = paramService.getParamTypeById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("TEST_PARAM_TYPE", result.getTypeCode());
        assertEquals("测试参数类型", result.getTypeName());
    }

    @Test
    void testUpdateParamType() {
        // Given
        ParamType paramType = new ParamType();
        paramType.setId(1L);
        paramType.setTypeCode("TEST_PARAM_TYPE");
        paramType.setTypeName("更新后的参数类型");
        paramType.setStatus(1);

        // When
        when(paramTypeMapper.updateParamType(paramType)).thenReturn(1);
        boolean result = paramService.updateParamType(paramType);

        // Then
        assertTrue(result);
        verify(paramTypeMapper, times(1)).updateParamType(paramType);
    }

    @Test
    void testDeleteParamType() {
        // Given
        Long id = 1L;
        ParamItem paramItem1 = new ParamItem();
        paramItem1.setId(1L);
        paramItem1.setParamTypeId(id);
        paramItem1.setParamCode("CODE1");

        ParamItem paramItem2 = new ParamItem();
        paramItem2.setId(2L);
        paramItem2.setParamTypeId(id);
        paramItem2.setParamCode("CODE2");

        List<ParamItem> paramItems = Arrays.asList(paramItem1, paramItem2);

        // When
        when(paramItemMapper.selectParamItemsByTypeId(id)).thenReturn(paramItems);
        when(paramItemMapper.deleteParamItem(1L)).thenReturn(1);
        when(paramItemMapper.deleteParamItem(2L)).thenReturn(1);
        when(paramTypeMapper.deleteParamType(id)).thenReturn(1);
        boolean result = paramService.deleteParamType(id);

        // Then
        assertTrue(result);
        verify(paramItemMapper, times(1)).deleteParamItem(1L);
        verify(paramItemMapper, times(1)).deleteParamItem(2L);
        verify(paramTypeMapper, times(1)).deleteParamType(id);
    }

    @Test
    void testListParamTypes() {
        // Given
        ParamType paramType1 = new ParamType();
        paramType1.setId(1L);
        paramType1.setTypeCode("TYPE1");

        ParamType paramType2 = new ParamType();
        paramType2.setId(2L);
        paramType2.setTypeCode("TYPE2");

        List<ParamType> expected = Arrays.asList(paramType1, paramType2);

        // When
        when(paramTypeMapper.selectParamTypeList(any())).thenReturn(expected);
        List<ParamType> result = paramService.listParamTypes(new ParamType());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TYPE1", result.get(0).getTypeCode());
        assertEquals("TYPE2", result.get(1).getTypeCode());
    }

    @Test
    void testCreateParamItem() {
        // Given
        ParamItem paramItem = new ParamItem();
        paramItem.setParamTypeId(1L);
        paramItem.setParamKey("TEST_KEY");
        paramItem.setParamCode("TEST_CODE");
        paramItem.setParamName("测试参数项");
        paramItem.setParamValue("TEST_VALUE");
        paramItem.setStatus(1);

        // When
        when(paramItemMapper.insertParamItem(paramItem)).thenReturn(1);
        boolean result = paramService.createParamItem(paramItem);

        // Then
        assertTrue(result);
        verify(paramItemMapper, times(1)).insertParamItem(paramItem);
    }

    @Test
    void testUpdateParamItem() {
        // Given
        ParamItem paramItem = new ParamItem();
        paramItem.setId(1L);
        paramItem.setParamTypeId(1L);
        paramItem.setParamKey("TEST_KEY");
        paramItem.setParamCode("TEST_CODE");
        paramItem.setParamName("更新后的参数项");
        paramItem.setParamValue("UPDATED_VALUE");
        paramItem.setStatus(1);

        // When
        when(paramItemMapper.updateParamItem(paramItem)).thenReturn(1);
        boolean result = paramService.updateParamItem(paramItem);

        // Then
        assertTrue(result);
        verify(paramItemMapper, times(1)).updateParamItem(paramItem);
    }

    @Test
    void testDeleteParamItem() {
        // Given
        Long id = 1L;

        // When
        when(paramItemMapper.deleteParamItem(id)).thenReturn(1);
        boolean result = paramService.deleteParamItem(id);

        // Then
        assertTrue(result);
        verify(paramItemMapper, times(1)).deleteParamItem(id);
    }

    @Test
    void testGetParamItemsByTypeId() {
        // Given
        Long typeId = 1L;
        ParamItem item1 = new ParamItem();
        item1.setId(1L);
        item1.setParamTypeId(typeId);
        item1.setParamCode("CODE1");

        ParamItem item2 = new ParamItem();
        item2.setId(2L);
        item2.setParamTypeId(typeId);
        item2.setParamCode("CODE2");

        List<ParamItem> expected = Arrays.asList(item1, item2);

        // When
        when(paramItemMapper.selectParamItemsByTypeId(typeId)).thenReturn(expected);
        List<ParamItem> result = paramService.getParamItemsByTypeId(typeId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CODE1", result.get(0).getParamCode());
        assertEquals("CODE2", result.get(1).getParamCode());
    }

    @Test
    void testDisableParamType() {
        // Given
        Long id = 1L;
        ParamType paramType = new ParamType();
        paramType.setId(id);
        paramType.setStatus(1);

        // When
        when(paramTypeMapper.selectParamTypeById(id)).thenReturn(paramType);
        when(paramTypeMapper.updateParamTypeStatus(id, 0)).thenReturn(1);
        boolean result = paramService.disableParamType(id);

        // Then
        assertTrue(result);
        assertEquals(0, paramType.getStatus());
        verify(paramTypeMapper, times(1)).updateParamTypeStatus(id, 0);
    }

    @Test
    void testEnableParamType() {
        // Given
        Long id = 1L;
        ParamType paramType = new ParamType();
        paramType.setId(id);
        paramType.setStatus(0);

        // When
        when(paramTypeMapper.selectParamTypeById(id)).thenReturn(paramType);
        when(paramTypeMapper.updateParamTypeStatus(id, 1)).thenReturn(1);
        boolean result = paramService.enableParamType(id);

        // Then
        assertTrue(result);
        assertEquals(1, paramType.getStatus());
        verify(paramTypeMapper, times(1)).updateParamTypeStatus(id, 1);
    }

    @Test
    void testGetParamByCode() {
        // Given
        String paramCode = "TEST_CODE";
        ParamItem expected = new ParamItem();
        expected.setParamCode(paramCode);
        expected.setParamValue("TEST_VALUE");

        // When
        when(paramItemMapper.selectParamItemByCode(paramCode)).thenReturn(expected);
        ParamItem result = paramService.getParamByCode(paramCode);

        // Then
        assertNotNull(result);
        assertEquals(paramCode, result.getParamCode());
        assertEquals("TEST_VALUE", result.getParamValue());
    }

    @Test
    void testUpdateParamValueByCode() {
        // Given
        String paramCode = "TEST_CODE";
        String newValue = "NEW_VALUE";

        // When
        when(paramItemMapper.updateParamValueByCode(paramCode, newValue)).thenReturn(1);
        boolean result = paramService.updateParamValueByCode(paramCode, newValue);

        // Then
        assertTrue(result);
        verify(paramItemMapper, times(1)).updateParamValueByCode(paramCode, newValue);
    }

    @Test
    void testDisableParamItem() {
        // Given
        Long id = 1L;
        ParamItem paramItem = new ParamItem();
        paramItem.setId(id);
        paramItem.setStatus(1);

        // When
        when(paramItemMapper.selectParamItemById(id)).thenReturn(paramItem);
        when(paramItemMapper.updateParamItemStatus(id, 0)).thenReturn(1);
        boolean result = paramService.disableParamItem(id);

        // Then
        assertTrue(result);
        assertEquals(0, paramItem.getStatus());
        verify(paramItemMapper, times(1)).updateParamItemStatus(id, 0);
    }

    @Test
    void testEnableParamItem() {
        // Given
        Long id = 1L;
        ParamItem paramItem = new ParamItem();
        paramItem.setId(id);
        paramItem.setStatus(0);

        // When
        when(paramItemMapper.selectParamItemById(id)).thenReturn(paramItem);
        when(paramItemMapper.updateParamItemStatus(id, 1)).thenReturn(1);
        boolean result = paramService.enableParamItem(id);

        // Then
        assertTrue(result);
        assertEquals(1, paramItem.getStatus());
        verify(paramItemMapper, times(1)).updateParamItemStatus(id, 1);
    }
}