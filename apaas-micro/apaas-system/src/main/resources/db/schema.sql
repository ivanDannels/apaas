-- 数据字典表
CREATE TABLE IF NOT EXISTS data_dictionary (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '字典名称',
    code VARCHAR(100) NOT NULL COMMENT '字典编码',
    type INT NOT NULL DEFAULT 0 COMMENT '字典类型：0-系统字典，1-业务字典',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    description VARCHAR(255) COMMENT '描述',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除',
    UNIQUE KEY uk_code_tenant (code, tenant_id)
) COMMENT='数据字典表';

-- 数据字典项表
CREATE TABLE IF NOT EXISTS data_dictionary_item (
    id BIGSERIAL PRIMARY KEY,
    dictionary_id BIGINT NOT NULL COMMENT '字典ID',
    code VARCHAR(100) NOT NULL COMMENT '字典项编码',
    name VARCHAR(100) NOT NULL COMMENT '字典项名称',
    value VARCHAR(255) COMMENT '字典项值',
    sort INT NOT NULL DEFAULT 0 COMMENT '排序',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    description VARCHAR(255) COMMENT '描述',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除',
    UNIQUE KEY uk_dictionary_code_tenant (dictionary_id, code, tenant_id),
    CONSTRAINT fk_dictionary_id FOREIGN KEY (dictionary_id) REFERENCES data_dictionary(id)
) COMMENT='数据字典项表';

-- 初始数据
INSERT INTO data_dictionary (name, code, type, status, description, tenant_id, create_by, create_time, update_by, update_time) 
VALUES ('用户状态', 'user_status', 0, 0, '系统用户状态字典', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO data_dictionary_item (dictionary_id, code, name, value, sort, status, description, tenant_id, create_by, create_time, update_by, update_time) 
VALUES ((SELECT id FROM data_dictionary WHERE code = 'user_status' AND tenant_id = 1), 'active', '激活', '0', 1, 0, '用户账号已激活', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO data_dictionary_item (dictionary_id, code, name, value, sort, status, description, tenant_id, create_by, create_time, update_by, update_time) 
VALUES ((SELECT id FROM data_dictionary WHERE code = 'user_status' AND tenant_id = 1), 'inactive', '未激活', '1', 2, 0, '用户账号未激活', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO data_dictionary_item (dictionary_id, code, name, value, sort, status, description, tenant_id, create_by, create_time, update_by, update_time) 
VALUES ((SELECT id FROM data_dictionary WHERE code = 'user_status' AND tenant_id = 1), 'locked', '锁定', '2', 3, 0, '用户账号已锁定', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO data_dictionary (name, code, type, status, description, tenant_id, create_by, create_time, update_by, update_time) 
VALUES ('性别', 'gender', 0, 0, '性别字典', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO data_dictionary_item (dictionary_id, code, name, value, sort, status, description, tenant_id, create_by, create_time, update_by, update_time) 
VALUES ((SELECT id FROM data_dictionary WHERE code = 'gender' AND tenant_id = 1), 'male', '男', '0', 1, 0, '男性', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO data_dictionary_item (dictionary_id, code, name, value, sort, status, description, tenant_id, create_by, create_time, update_by, update_time) 
VALUES ((SELECT id FROM data_dictionary WHERE code = 'gender' AND tenant_id = 1), 'female', '女', '1', 2, 0, '女性', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);