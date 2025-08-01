CREATE TABLE flow_instance (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    process_id BIGINT NOT NULL,
    business_key VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    starter BIGINT NOT NULL,
    creator BIGINT NOT NULL,
    updater BIGINT,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_flow_instance_tenant_id ON flow_instance(tenant_id);
CREATE INDEX idx_flow_instance_process_id ON flow_instance(process_id);
CREATE INDEX idx_flow_instance_business_key ON flow_instance(business_key);
CREATE INDEX idx_flow_instance_status ON flow_instance(status);
CREATE INDEX idx_flow_instance_starter ON flow_instance(starter);