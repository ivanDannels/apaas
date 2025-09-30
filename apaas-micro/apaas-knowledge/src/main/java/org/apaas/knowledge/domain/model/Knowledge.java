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
package org.apaas.knowledge.domain.model;

import lombok.Data;

import java.util.Objects;

/**
 * 文档模型，表示知识库中的单个文档
 * @author ivan
 */
@Data
public class Knowledge {
    
    // Getters and setters
    private String id;
    private String content;
    private String fileName;
    private long fileSize;
    private long lastModifiedTime;
    private boolean selected;
    
    public Knowledge() {
    }
    
    public Knowledge(String content, String fileName, long fileSize, long lastModifiedTime) {
        this.content = content;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.lastModifiedTime = lastModifiedTime;
        this.selected = false;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Knowledge knowledge = (Knowledge) o;
        return Objects.equals(fileName, knowledge.fileName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}