CREATE TABLE activity_instance (
    id BIGSERIAL PRIMARY KEY,
    flow_instance_id BIGINT NOT NULL,
    node_id VARCHAR(255) NOT NULL,
    node_name VARCHAR(255) NOT NULL,
    node_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    duration BIGINT,
    creator BIGINT NOT NULL,
    updater BIGINT,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (flow_instance_id) REFERENCES flow_instance(id)
);

CREATE INDEX idx_activity_instance_flow_instance_id ON activity_instance(flow_instance_id);
CREATE INDEX idx_activity_instance_node_id ON activity_instance(node_id);
CREATE INDEX idx_activity_instance_node_type ON activity_instance(node_type);
CREATE INDEX idx_activity_instance_status ON activity_instance(status);