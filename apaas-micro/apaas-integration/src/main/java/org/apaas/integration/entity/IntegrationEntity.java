package org.apaas.integration.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("integration_entities")
public class IntegrationEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    
    public IntegrationEntity() {}
    
    public IntegrationEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}