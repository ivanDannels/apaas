CREATE TABLE workflow_task (
    id BIGSERIAL PRIMARY KEY,
    activity_instance_id BIGINT NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    task_type VARCHAR(50) NOT NULL,
    assignee BIGINT,
    candidate_users TEXT,
    candidate_groups TEXT,
    status VARCHAR(50) NOT NULL,
    priority INTEGER DEFAULT 0,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    due_time TIMESTAMP,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    duration BIGINT,
    form_data_id BIGINT,
    creator BIGINT NOT NULL,
    updater BIGINT,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (activity_instance_id) REFERENCES activity_instance(id)
);

CREATE INDEX idx_workflow_task_activity_instance_id ON workflow_task(activity_instance_id);
CREATE INDEX idx_workflow_task_assignee ON workflow_task(assignee);
CREATE INDEX idx_workflow_task_status ON workflow_task(status);
CREATE INDEX idx_workflow_task_priority ON workflow_task(priority);