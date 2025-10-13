-- 元数据表
CREATE TABLE IF NOT EXISTS metadata (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '元数据名称',
    type VARCHAR(50) NOT NULL COMMENT '元数据类型',
    content TEXT COMMENT '元数据内容',
    description VARCHAR(255) COMMENT '描述',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='元数据表';

-- 数据模型表
CREATE TABLE IF NOT EXISTS model (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '模型名称',
    type VARCHAR(50) NOT NULL COMMENT '模型类型',
    content TEXT COMMENT '模型内容',
    description VARCHAR(255) COMMENT '描述',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='数据模型表';

-- 代码模板表
CREATE TABLE IF NOT EXISTS code_template (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '模板名称',
    content TEXT COMMENT '模板内容',
    language VARCHAR(50) COMMENT '编程语言',
    description VARCHAR(255) COMMENT '描述',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='代码模板表';

-- 数据源配置表
CREATE TABLE IF NOT EXISTS data_source (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '数据源名称',
    type VARCHAR(50) NOT NULL COMMENT '数据源类型',
    url VARCHAR(500) NOT NULL COMMENT '连接URL',
    username VARCHAR(100) COMMENT '用户名',
    password VARCHAR(100) COMMENT '密码',
    driver_class VARCHAR(100) COMMENT '驱动类',
    description VARCHAR(255) COMMENT '描述',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='数据源配置表';

-- API定义表
CREATE TABLE IF NOT EXISTS api_definition (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT 'API名称',
    path VARCHAR(200) NOT NULL COMMENT 'API路径',
    method VARCHAR(10) NOT NULL COMMENT 'HTTP方法',
    description VARCHAR(255) COMMENT '描述',
    sql_script TEXT COMMENT 'SQL脚本',
    data_source_id BIGINT COMMENT '数据源ID',
    request_params TEXT COMMENT '请求参数',
    response_structure TEXT COMMENT '响应结构',
    version VARCHAR(20) COMMENT '版本号',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='API定义表';

-- 代码生成器表
CREATE TABLE IF NOT EXISTS code_generator (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '生成器名称',
    description VARCHAR(255) COMMENT '描述',
    template_id BIGINT COMMENT '模板ID',
    metadata_id BIGINT COMMENT '元数据ID',
    output_path VARCHAR(255) COMMENT '输出路径',
    type INT NOT NULL DEFAULT 0 COMMENT '生成类型：0-Java代码，1-前端代码，2-全栈代码',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='代码生成器表';

-- 数据同步任务表
CREATE TABLE IF NOT EXISTS data_sync_task (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '任务名称',
    source_data_source_id BIGINT NOT NULL COMMENT '源数据源ID',
    target_data_source_id BIGINT NOT NULL COMMENT '目标数据源ID',
    sync_sql TEXT COMMENT '同步SQL脚本',
    cron_expression VARCHAR(100) COMMENT '同步周期（Cron表达式）',
    last_execute_time TIMESTAMP COMMENT '最后执行时间',
    next_execute_time TIMESTAMP COMMENT '下次执行时间',
    execute_status INT NOT NULL DEFAULT 0 COMMENT '执行状态：0-待执行，1-执行中，2-执行成功，3-执行失败',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    fail_reason VARCHAR(500) COMMENT '失败原因',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='数据同步任务表';

-- 数据可视化表
CREATE TABLE IF NOT EXISTS visualization (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '可视化名称',
    type INT NOT NULL DEFAULT 0 COMMENT '可视化类型：0-折线图，1-柱状图，2-饼图，3-散点图，4-热力图',
    data_source_id BIGINT COMMENT '数据源ID',
    query_sql TEXT COMMENT '查询SQL',
    config TEXT COMMENT '配置信息（JSON格式）',
    description VARCHAR(255) COMMENT '描述',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='数据可视化表';

-- API实体表
CREATE TABLE IF NOT EXISTS api_entity (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT 'API名称',
    path VARCHAR(200) NOT NULL COMMENT 'API路径',
    method VARCHAR(10) NOT NULL COMMENT 'HTTP方法',
    description VARCHAR(255) COMMENT '描述',
    sql_script TEXT COMMENT 'SQL脚本',
    version VARCHAR(20) COMMENT '版本号',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-停用',
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-未删除，1-已删除'
) COMMENT='API实体表';

-- 初始数据
-- 默认代码模板
INSERT INTO code_template (name, content, language, description, status, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('Java实体类模板', 'public class ${className} {
${fields}
}', 'java', '生成Java实体类的默认模板', 0, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO code_template (name, content, language, description, status, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('Vue页面模板', '<template>
  <div>
    ${content}
  </div>
</template>', 'vue', '生成Vue页面的默认模板', 0, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- 默认数据源配置
INSERT INTO data_source (name, type, url, username, password, driver_class, description, status, tenant_id, create_by, create_time, update_by, update_time)
VALUES ('默认PostgreSQL数据库', 'postgresql', 'jdbc:postgresql://localhost:5432/apaas', 'apaas', 'apaas123', 'org.postgresql.Driver', '系统默认PostgreSQL数据库配置', 0, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);