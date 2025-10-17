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

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ivan
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {
    
    // 用于存储读取到的Excel数据对象列表
    private List<T> dataList = new ArrayList<>();
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        // 每读取一行数据，就将其添加到dataList中
        dataList.add(t);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 当所有数据读取完成后，可以在这里进行一些后续操作，如打印读取到的数据数量
        System.out.println("读取完成，共读取了 " + dataList.size() + " 条数据");
    }
    // 提供一个方法用于获取存储数据的列表
    public List<T> getDataList() {
        return dataList;
    }
}