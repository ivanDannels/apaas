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
package org.apaas.core.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页参数对象
 *
 * @author ivan
 */
@Data
public class PageRequest implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -7974662876755267901L;
    
    /**
     * 每页最大记录数，参数传入
     */
    public final static int MAX_PAGE_SIZE = 20000;
    
    /**
     * 每页默认记录数，参数传入
     */
    public final static int PAGE_SIZE = 20;
    
    /**
     * 默认第几页，参数传入
     */
    public final static int PAGE_NUMBER = 1;
    
    /**
     * 当前第几页，参数传入
     **/
    private int pageNumber = PAGE_NUMBER;
    
    /**
     * 每页的最大记录数，固定值，或者参数传入
     **/
    private int pageSize = PAGE_SIZE;
    
    /**
     * 开始的记录数
     **/
    private int startItems;
    
    /**
     * 结束的记录数
     **/
    private int endItems;
    
    public PageRequest() {
        reloadItems();
    }
    
    /**
     * 根据pageSize、pageNumber、totalRecord计算开始行、结束行和总页数
     **/
    public PageRequest(int pageNumber, int pageSize) {
        
        int currentPageSize = Math.min(pageSize, MAX_PAGE_SIZE);
        
        // 当前查询的开始行
        this.startItems = currentPageSize * (pageNumber - 1) + 1;
        
        // 当前查询的结束行
        this.endItems = currentPageSize * pageNumber;
        
        this.pageNumber = pageNumber;
        
        this.pageSize = currentPageSize;
    }
    
    public PageRequest(int pageNumber, int pageSize, int startItems, int endItems) {
        this.pageNumber = pageNumber;
        this.pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
        this.startItems = startItems;
        this.endItems = endItems;
    }
    
    public static PageRequest of() {
        return new PageRequest();
    }
    
    private void reloadItems() {
        // 当前查询的开始行
        this.startItems = getPageSize() * (getPageNumber() - 1) + 1;
        // 当前查询的结束行
        this.endItems = pageSize * pageNumber;
    }
    
    /**
     * 根据pageSize、pageNumber、totalRecord计算开始行、结束行和总页数
     **/
    public void setPageable(PageRequest pageRequest) {
        
        int currentPageSize = Math.min(pageRequest.getPageSize(), MAX_PAGE_SIZE);
        
        // 当前查询的开始行
        int firstItem = currentPageSize * (pageRequest.getPageNumber() - 1) + 1;
        
        // 当前查询的结束行
        int lastItem = currentPageSize * pageRequest.getPageNumber();
        
        pageRequest.setStartItems(firstItem);
        pageRequest.setEndItems(lastItem);
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        reloadItems();
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
        reloadItems();
    }
    
    public int getStartItems() {
        setStartItems(pageSize * (pageNumber - 1));
        return startItems;
    }
    
    public int getEndItems() {
        setEndItems(pageSize * pageNumber);
        return endItems;
    }
    
    public PageRequest addPageNumber(int pageNumber) {
        setPageNumber(pageNumber);
        return this;
    }
    
    public PageRequest addPageSize(int pageSize) {
        setPageSize(pageSize);
        return this;
    }
    
}
