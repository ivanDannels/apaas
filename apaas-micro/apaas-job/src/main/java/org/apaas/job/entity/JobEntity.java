package org.apaas.job.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("job_entities")
public class JobEntity {
    @Id
    private Long id;
    private String name;
    private String cronExpression;
    
    public JobEntity() {}
    
    public JobEntity(String name, String cronExpression) {
        this.name = name;
        this.cronExpression = cronExpression;
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
    
    public String getCronExpression() {
        return cronExpression;
    }
    
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}