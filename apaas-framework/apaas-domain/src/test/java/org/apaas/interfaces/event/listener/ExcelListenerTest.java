/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.interfaces.event.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cn.idev.excel.context.AnalysisContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ExcelListener 测试类
 * @author ivan
 */
class ExcelListenerTest {
    
    private ExcelListener<TestData> excelListener;
    
    @BeforeEach
    public void setUp() {
        excelListener = new ExcelListener<>();
    }
    
    @Test
    void testInvoke() {
        // 准备测试数据
        TestData testData = new TestData("test1", 1);
        
        // 创建模拟的分析上下文
        AnalysisContext context = mock(AnalysisContext.class);
        
        // 执行测试
        excelListener.invoke(testData, context);
        
        // 验证结果
        List<TestData> dataList = excelListener.getDataList();
        assertNotNull(dataList);
        assertEquals(1, dataList.size());
        assertEquals("test1", dataList.get(0).getName());
        assertEquals(1, dataList.get(0).getValue());
    }
    
    @Test
    void testDoAfterAllAnalysed() {
        // 创建模拟的分析上下文
        AnalysisContext context = mock(AnalysisContext.class);
        
        // 执行测试
        excelListener.doAfterAllAnalysed(context);
        
        // 验证结果 - 这个方法主要是打印日志，所以我们验证它不会抛出异常
        assertTrue(true); // 如果没有异常就是成功的
    }
    
    @Test
    void testGetDataList() {
        // 验证初始状态
        List<TestData> dataList = excelListener.getDataList();
        assertNotNull(dataList);
        assertTrue(dataList.isEmpty());
        
        // 添加一些数据
        TestData testData1 = new TestData("test1", 1);
        TestData testData2 = new TestData("test2", 2);
        
        AnalysisContext context = mock(AnalysisContext.class);
        excelListener.invoke(testData1, context);
        excelListener.invoke(testData2, context);
        
        // 验证结果
        dataList = excelListener.getDataList();
        assertNotNull(dataList);
        assertEquals(2, dataList.size());
        assertEquals("test1", dataList.get(0).getName());
        assertEquals("test2", dataList.get(1).getName());
    }
    
    /**
     * 测试用的数据类
     */
    static class TestData {
        
        private String name;
        private int value;
        
        public TestData() {
        }
        
        public TestData(String name, int value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }
}