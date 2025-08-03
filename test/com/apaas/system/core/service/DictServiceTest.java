package com.apaas.system.core.service;

import com.apaas.system.core.domain.DictType;
import com.apaas.system.core.domain.DictItem;
import com.apaas.system.core.mapper.DictTypeMapper;
import com.apaas.system.core.mapper.DictItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DictServiceTest {

    @Mock
    private DictTypeMapper dictTypeMapper;

    @Mock
    private DictItemMapper dictItemMapper;

    @InjectMocks
    private DictServiceImpl dictService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDictType() {
        // Given
        DictType dictType = new DictType();
        dictType.setTypeCode("TEST_TYPE");
        dictType.setTypeName("测试字典类型");
        dictType.setStatus(1);

        // When
        when(dictTypeMapper.insertDictType(dictType)).thenReturn(1);
        boolean result = dictService.createDictType(dictType);

        // Then
        assertTrue(result);
        verify(dictTypeMapper, times(1)).insertDictType(dictType);
    }

    @Test
    void testCreateDictTypeWithExistingCode() {
        // Given
        DictType dictType = new DictType();
        dictType.setTypeCode("EXISTING_TYPE");
        dictType.setTypeName("已存在的字典类型");
        dictType.setStatus(1);

        // When
        when(dictTypeMapper.selectDictTypeByCode("EXISTING_TYPE")).thenReturn(new DictType());
        boolean result = dictService.createDictType(dictType);

        // Then
        assertFalse(result);
        verify(dictTypeMapper, never()).insertDictType(any(DictType.class));
    }

    @Test
    void testGetDictTypeById() {
        // Given
        Long id = 1L;
        DictType expected = new DictType();
        expected.setId(id);
        expected.setTypeCode("TEST_TYPE");
        expected.setTypeName("测试字典类型");

        // When
        when(dictTypeMapper.selectDictTypeById(id)).thenReturn(expected);
        DictType result = dictService.getDictTypeById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("TEST_TYPE", result.getTypeCode());
        assertEquals("测试字典类型", result.getTypeName());
    }

    @Test
    void testGetDictTypeByNonExistingId() {
        // Given
        Long id = 999L;

        // When
        when(dictTypeMapper.selectDictTypeById(id)).thenReturn(null);
        DictType result = dictService.getDictTypeById(id);

        // Then
        assertNull(result);
    }

    @Test
    void testUpdateDictType() {
        // Given
        DictType dictType = new DictType();
        dictType.setId(1L);
        dictType.setTypeCode("TEST_TYPE");
        dictType.setTypeName("更新后的字典类型");
        dictType.setStatus(1);

        // When
        when(dictTypeMapper.updateDictType(dictType)).thenReturn(1);
        boolean result = dictService.updateDictType(dictType);

        // Then
        assertTrue(result);
        verify(dictTypeMapper, times(1)).updateDictType(dictType);
    }

    @Test
    void testDeleteDictType() {
        // Given
        Long id = 1L;

        // When
        when(dictTypeMapper.deleteDictType(id)).thenReturn(1);
        boolean result = dictService.deleteDictType(id);

        // Then
        assertTrue(result);
        verify(dictTypeMapper, times(1)).deleteDictType(id);
    }

    @Test
    void testDeleteDictTypeWithItems() {
        // Given
        Long id = 1L;
        DictItem dictItem = new DictItem();
        dictItem.setId(1L);
        dictItem.setDictTypeId(id);
        List<DictItem> items = Arrays.asList(dictItem);

        // When
        when(dictItemMapper.selectDictItemsByTypeId(id)).thenReturn(items);
        when(dictItemMapper.deleteDictItem(1L)).thenReturn(1);
        when(dictTypeMapper.deleteDictType(id)).thenReturn(1);
        boolean result = dictService.deleteDictType(id);

        // Then
        assertTrue(result);
        verify(dictItemMapper, times(1)).deleteDictItem(1L);
        verify(dictTypeMapper, times(1)).deleteDictType(id);
    }

    @Test
    void testListDictTypes() {
        // Given
        DictType dictType1 = new DictType();
        dictType1.setId(1L);
        dictType1.setTypeCode("TYPE1");

        DictType dictType2 = new DictType();
        dictType2.setId(2L);
        dictType2.setTypeCode("TYPE2");

        List<DictType> expected = Arrays.asList(dictType1, dictType2);

        // When
        when(dictTypeMapper.selectDictTypeList(any())).thenReturn(expected);
        List<DictType> result = dictService.listDictTypes(new DictType());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TYPE1", result.get(0).getTypeCode());
        assertEquals("TYPE2", result.get(1).getTypeCode());
    }

    @Test
    void testCreateDictItem() {
        // Given
        DictItem dictItem = new DictItem();
        dictItem.setDictTypeId(1L);
        dictItem.setItemCode("ITEM1");
        dictItem.setItemName("测试字典项");
        dictItem.setItemValue("TEST_VALUE");
        dictItem.setStatus(1);

        // When
        when(dictItemMapper.insertDictItem(dictItem)).thenReturn(1);
        boolean result = dictService.createDictItem(dictItem);

        // Then
        assertTrue(result);
        verify(dictItemMapper, times(1)).insertDictItem(dictItem);
    }

    @Test
    void testUpdateDictItem() {
        // Given
        DictItem dictItem = new DictItem();
        dictItem.setId(1L);
        dictItem.setDictTypeId(1L);
        dictItem.setItemCode("ITEM1");
        dictItem.setItemName("更新后的字典项");
        dictItem.setItemValue("UPDATED_VALUE");
        dictItem.setStatus(1);

        // When
        when(dictItemMapper.updateDictItem(dictItem)).thenReturn(1);
        boolean result = dictService.updateDictItem(dictItem);

        // Then
        assertTrue(result);
        verify(dictItemMapper, times(1)).updateDictItem(dictItem);
    }

    @Test
    void testDeleteDictItem() {
        // Given
        Long id = 1L;

        // When
        when(dictItemMapper.deleteDictItem(id)).thenReturn(1);
        boolean result = dictService.deleteDictItem(id);

        // Then
        assertTrue(result);
        verify(dictItemMapper, times(1)).deleteDictItem(id);
    }

    @Test
    void testGetDictItemsByTypeId() {
        // Given
        Long typeId = 1L;
        DictItem item1 = new DictItem();
        item1.setId(1L);
        item1.setDictTypeId(typeId);
        item1.setItemCode("ITEM1");

        DictItem item2 = new DictItem();
        item2.setId(2L);
        item2.setDictTypeId(typeId);
        item2.setItemCode("ITEM2");

        List<DictItem> expected = Arrays.asList(item1, item2);

        // When
        when(dictItemMapper.selectDictItemsByTypeId(typeId)).thenReturn(expected);
        List<DictItem> result = dictService.getDictItemsByTypeId(typeId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ITEM1", result.get(0).getItemCode());
        assertEquals("ITEM2", result.get(1).getItemCode());
    }

    @Test
    void testDisableDictType() {
        // Given
        Long id = 1L;
        DictType dictType = new DictType();
        dictType.setId(id);
        dictType.setStatus(1);

        // When
        when(dictTypeMapper.selectDictTypeById(id)).thenReturn(dictType);
        when(dictTypeMapper.updateDictTypeStatus(id, 0)).thenReturn(1);
        boolean result = dictService.disableDictType(id);

        // Then
        assertTrue(result);
        assertEquals(0, dictType.getStatus());
        verify(dictTypeMapper, times(1)).updateDictTypeStatus(id, 0);
    }

    @Test
    void testEnableDictType() {
        // Given
        Long id = 1L;
        DictType dictType = new DictType();
        dictType.setId(id);
        dictType.setStatus(0);

        // When
        when(dictTypeMapper.selectDictTypeById(id)).thenReturn(dictType);
        when(dictTypeMapper.updateDictTypeStatus(id, 1)).thenReturn(1);
        boolean result = dictService.enableDictType(id);

        // Then
        assertTrue(result);
        assertEquals(1, dictType.getStatus());
        verify(dictTypeMapper, times(1)).updateDictTypeStatus(id, 1);
    }
}