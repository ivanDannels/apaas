package org.apaas.core.enums;

/**
 * 业务操作类型
 */
public enum BusinessType {
    
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,
    
    /**
     * 清空数据
     */
    CLEAN,

    /**
     * 登录
     */
    LOGIN,

    /**
     * 登出
     */
    LOGOUT,

    /**
     * 审批
     */
    APPROVE,

    /**
     * 驳回
     */
    REJECT,

    /**
     * 转办
     */
    TRANSFER,

    /**
     * 委派
     */
    DELEGATE,

    /**
     * 流程启动
     */
    FLOW_START,

    /**
     * 流程终止
     */
    FLOW_TERMINATE
}