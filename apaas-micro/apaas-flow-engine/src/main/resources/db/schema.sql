-- 流程定义表
CREATE TABLE IF NOT EXISTS flow_definition (
    id BIGINT NOT NULL COMMENT '流程ID',
    name VARCHAR(255) NOT NULL COMMENT '流程名称',
    code VARCHAR(64) NOT NULL COMMENT '流程编码',
    category VARCHAR(64) COMMENT '流程分类',
    version INT NOT NULL DEFAULT 1 COMMENT '流程版本',
    description TEXT COMMENT '流程描述',
    flow_json TEXT COMMENT '流程JSON定义',
    form_id BIGINT COMMENT '表单ID',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '流程状态（0-草稿，1-已发布，2-已停用）',
    is_default BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否为默认版本',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(64) COMMENT '创建人',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code_version (code, version, tenant_id),
    INDEX idx_tenant_status (tenant_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程定义表';

-- 流程实例表
CREATE TABLE IF NOT EXISTS flow_instance (
    id BIGINT NOT NULL COMMENT '实例ID',
    definition_id BIGINT NOT NULL COMMENT '流程定义ID',
    definition_name VARCHAR(255) NOT NULL COMMENT '流程定义名称',
    definition_code VARCHAR(64) NOT NULL COMMENT '流程定义编码',
    definition_version INT NOT NULL COMMENT '流程定义版本',
    business_key VARCHAR(64) COMMENT '业务主键ID',
    business_data TEXT COMMENT '业务表单数据',
    current_node_id VARCHAR(64) COMMENT '当前节点ID',
    current_node_name VARCHAR(255) COMMENT '当前节点名称',
    status TINYINT NOT NULL COMMENT '流程状态（0-运行中，1-已完成，2-已终止，3-已暂停）',
    start_user_id BIGINT NOT NULL COMMENT '发起人ID',
    start_user_name VARCHAR(64) NOT NULL COMMENT '发起人名称',
    start_time DATETIME NOT NULL COMMENT '发起时间',
    end_time DATETIME COMMENT '结束时间',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(64) COMMENT '创建人',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
    PRIMARY KEY (id),
    INDEX idx_definition_id (definition_id),
    INDEX idx_tenant_status (tenant_id, status),
    INDEX idx_start_user (start_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程实例表';

-- 流程任务表
CREATE TABLE IF NOT EXISTS flow_task (
    id BIGINT NOT NULL COMMENT '任务ID',
    instance_id BIGINT NOT NULL COMMENT '流程实例ID',
    instance_name VARCHAR(255) NOT NULL COMMENT '流程实例名称',
    definition_id BIGINT NOT NULL COMMENT '流程定义ID',
    definition_version INT NOT NULL COMMENT '流程定义版本',
    node_id VARCHAR(64) NOT NULL COMMENT '节点ID',
    node_name VARCHAR(255) NOT NULL COMMENT '节点名称',
    node_type TINYINT NOT NULL COMMENT '节点类型（0-开始节点，1-审批节点，2-条件节点，3-并行节点，4-结束节点）',
    status TINYINT NOT NULL COMMENT '任务状态（0-未开始，1-处理中，2-已完成，3-已终止，4-已退回）',
    priority TINYINT NOT NULL DEFAULT 0 COMMENT '任务优先级（0-普通，1-紧急，2-非常紧急）',
    assignee_id BIGINT COMMENT '处理人ID',
    assignee_name VARCHAR(64) COMMENT '处理人名称',
    candidate_ids TEXT COMMENT '候选处理人IDs',
    candidate_names TEXT COMMENT '候选处理人Names',
    create_time DATETIME NOT NULL COMMENT '任务创建时间',
    start_time DATETIME COMMENT '任务开始时间',
    handle_time DATETIME COMMENT '任务处理时间',
    complete_time DATETIME COMMENT '任务完成时间',
    handle_user_id BIGINT COMMENT '处理人ID',
    handle_user_name VARCHAR(64) COMMENT '处理人名称',
    comment TEXT COMMENT '任务意见',
    variables TEXT COMMENT '任务变量',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(64) COMMENT '创建人',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
    PRIMARY KEY (id),
    INDEX idx_instance_id (instance_id),
    INDEX idx_assignee_id (assignee_id),
    INDEX idx_tenant_status (tenant_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程任务表';

-- 初始化数据
INSERT INTO flow_definition (id, name, code, category, version, description, flow_json, form_id, status, is_default, tenant_id, create_by, create_time, update_by, update_time)
VALUES (1, '请假流程', 'leave_process', '人事管理', 1, '员工请假申请流程', '{"nodes":[{"id":"start","name":"开始节点","type":0},{"id":"approve","name":"经理审批","type":1},{"id":"end","name":"结束节点","type":4}]}', null, 1, true, 1, 'admin', NOW(), 'admin', NOW());