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

-- 文件表
CREATE TABLE IF NOT EXISTS files (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL COMMENT '文件名称',
    file_url VARCHAR(500) NOT NULL COMMENT '文件URL',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_size BIGINT COMMENT '文件大小',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_md5 VARCHAR(32) COMMENT '文件MD5值',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='文件表';

-- 国际化表
CREATE TABLE IF NOT EXISTS internationalization (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL COMMENT '国际化编码',
    message VARCHAR(1000) NOT NULL COMMENT '国际化消息',
    language VARCHAR(10) COMMENT '语言',
    country VARCHAR(10) COMMENT '国家',
    region VARCHAR(10) COMMENT '地区',
    locale VARCHAR(20) COMMENT '区域设置',
    time_zone VARCHAR(50) COMMENT '时区',
    currency VARCHAR(10) COMMENT '货币',
    currency_symbol VARCHAR(10) COMMENT '货币符号',
    currency_code VARCHAR(10) COMMENT '货币编码',
    currency_symbol_position VARCHAR(10) COMMENT '货币符号位置',
    currency_decimal_separator VARCHAR(1) COMMENT '货币小数分隔符',
    currency_grouping_separator VARCHAR(1) COMMENT '货币分组分隔符',
    currency_grouping_size INT COMMENT '货币分组大小',
    currency_grouping_count INT COMMENT '货币分组计数',
    currency_format VARCHAR(50) COMMENT '货币格式',
    currency_format_symbols VARCHAR(100) COMMENT '货币格式符号',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除',
    UNIQUE KEY uk_code_language_tenant (code, language, tenant_id)
) COMMENT='国际化表';

-- 通知表
CREATE TABLE IF NOT EXISTS notification (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL COMMENT '通知标题',
    content TEXT COMMENT '通知内容',
    receiver_id BIGINT COMMENT '接收人ID',
    receiver_name VARCHAR(100) COMMENT '接收人名称',
    sender_id BIGINT COMMENT '发送人ID',
    sender_name VARCHAR(100) COMMENT '发送人名称',
    type VARCHAR(50) COMMENT '通知类型',
    channel VARCHAR(50) COMMENT '通知渠道',
    business_id VARCHAR(100) COMMENT '关联业务ID',
    business_type VARCHAR(50) COMMENT '关联业务类型',
    read_status INT NOT NULL DEFAULT 0 COMMENT '阅读状态 0:未读 1:已读',
    read_time TIMESTAMP COMMENT '阅读时间',
    send_status INT NOT NULL DEFAULT 0 COMMENT '发送状态 0:未发送 1:已发送 2:发送失败',
    send_time TIMESTAMP COMMENT '发送时间',
    fail_reason VARCHAR(500) COMMENT '失败原因',
    template_id BIGINT COMMENT '模板ID',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='通知表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS sys_config (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '参数名称',
    config_key VARCHAR(100) NOT NULL COMMENT '参数键',
    code VARCHAR(100) NOT NULL COMMENT '参数编码',
    value VARCHAR(500) COMMENT '参数值',
    type INT NOT NULL DEFAULT 0 COMMENT '参数类型：0-系统参数，1-业务参数',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    description VARCHAR(255) COMMENT '描述',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除',
    UNIQUE KEY uk_config_key_tenant (config_key, tenant_id)
) COMMENT='系统配置表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS sys_oper_log (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) COMMENT '操作模块',
    business_type INT COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    method VARCHAR(255) COMMENT '方法名称',
    request_method VARCHAR(10) COMMENT '请求方式',
    operator_type VARCHAR(20) COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    oper_name VARCHAR(50) COMMENT '操作人员',
    dept_name VARCHAR(50) COMMENT '部门名称',
    oper_url VARCHAR(255) COMMENT '请求URL',
    oper_ip VARCHAR(128) COMMENT '主机地址',
    oper_location VARCHAR(255) COMMENT '操作地点',
    oper_param VARCHAR(2000) COMMENT '请求参数',
    json_result VARCHAR(2000) COMMENT '返回参数',
    status INT COMMENT '操作状态（0正常 1异常）',
    error_msg VARCHAR(2000) COMMENT '错误消息',
    oper_time TIMESTAMP COMMENT '操作时间',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='操作日志表';

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

-- 系统配置初始数据
INSERT INTO sys_config (name, config_key, code, value, type, status, description, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('主框架页-默认皮肤样式名称', 'sys.index.skinName', 'sys_index_skin_name', 'skin-blue', 0, 0, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO sys_config (name, config_key, code, value, type, status, description, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('用户管理-账号初始密码', 'sys.user.initPassword', 'sys_user_init_password', '123456', 0, 0, '初始化密码 123456', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO sys_config (name, config_key, code, value, type, status, description, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('主框架页-侧边栏主题', 'sys.index.sideTheme', 'sys_index_side_theme', 'theme-dark', 0, 0, '深色主题theme-dark，浅色主题theme-light', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO sys_config (name, config_key, code, value, type, status, description, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'sys_account_register_user', 'false', 0, 0, '是否开启注册用户功能（true开启，false关闭）', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- 国际化初始数据
INSERT INTO internationalization (code, message, language, country, region, locale, time_zone, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('sys.login.title', 'APaaS Platform', 'en', 'US', '', 'en_US', 'UTC', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO internationalization (code, message, language, country, region, locale, time_zone, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('sys.login.title', 'APaaS平台', 'zh', 'CN', '', 'zh_CN', 'Asia/Shanghai', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO internationalization (code, message, language, country, region, locale, time_zone, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('sys.login.username', 'Username', 'en', 'US', '', 'en_US', 'UTC', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO internationalization (code, message, language, country, region, locale, time_zone, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('sys.login.username', '用户名', 'zh', 'CN', '', 'zh_CN', 'Asia/Shanghai', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO internationalization (code, message, language, country, region, locale, time_zone, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('sys.login.password', 'Password', 'en', 'US', '', 'en_US', 'UTC', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO internationalization (code, message, language, country, region, locale, time_zone, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('sys.login.password', '密码', 'zh', 'CN', '', 'zh_CN', 'Asia/Shanghai', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);