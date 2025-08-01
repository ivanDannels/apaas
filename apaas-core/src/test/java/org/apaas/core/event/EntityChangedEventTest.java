package org.apaas.core.event;

import org.apaas.core.domain.BaseEntity;
import org.junit.jupiter.api.Test;

public class EntityChangedEventTest {
    
    @Test
    public void testEntityChangedEventCreation() {
        // Create a mock entity
        BaseEntity entity = new BaseEntity();
        
        // Create an EntityChangedEvent
        EntityChangedEvent<BaseEntity> event = new EntityChangedEvent<>(EntityChangedEvent.OperationType.CREATE, entity);
        
        // Verify the event was created successfully
        assert event.getOperationType() == EntityChangedEvent.OperationType.CREATE;
        assert event.getEntity() == entity;
    }
}