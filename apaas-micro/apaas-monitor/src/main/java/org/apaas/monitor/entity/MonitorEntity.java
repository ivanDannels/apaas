package org.apaas.monitor.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("monitor_entities")
public class MonitorEntity {
    @Id
    private Long id;
    private String name;
    private String metrics;
    
    public MonitorEntity() {}
    
    public MonitorEntity(String name, String metrics) {
        this.name = name;
        this.metrics = metrics;
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
    
    public String getMetrics() {
        return metrics;
    }
    
    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }
}