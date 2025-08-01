-- 表单定义表
CREATE TABLE IF NOT EXISTS form_definition (
    id BIGINT NOT NULL COMMENT '表单ID',
    name VARCHAR(255) NOT NULL COMMENT '表单名称',
    code VARCHAR(64) NOT NULL COMMENT '表单编码',
    type TINYINT NOT NULL DEFAULT 0 COMMENT '表单类型（0-普通表单，1-流程表单，2-统计表单）',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '表单状态（0-草稿，1-已发布，2-已停用）',
    config_json TEXT COMMENT '表单配置JSON',
    items_json TEXT NOT NULL COMMENT '表单项JSON',
    data_source_id BIGINT COMMENT '数据源ID',
    flow_id BIGINT COMMENT '关联流程ID',
    version INT NOT NULL DEFAULT 1 COMMENT '版本号',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单定义表';

-- 初始化数据
INSERT INTO form_definition (id, name, code, type, status, config_json, items_json, data_source_id, flow_id, version, is_default, tenant_id, create_by, create_time, update_by, update_time)
VALUES (1, '请假申请表', 'leave_application', 1, 1, '{"width":"100%","labelWidth":"120px"}', '{"items":[{"type":"text","label":"申请人","name":"applicant","required":true},{"type":"date","label":"请假开始时间","name":"startDate","required":true},{"type":"date","label":"请假结束时间","name":"endDate","required":true},{"type":"textarea","label":"请假原因","name":"reason","required":true}]}', null, 1, 1, true, 1, 'admin', NOW(), 'admin', NOW());