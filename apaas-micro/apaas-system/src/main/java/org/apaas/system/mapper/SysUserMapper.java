package org.apaas.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apaas.system.domain.dto.UserQueryDTO;
import org.apaas.system.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 用户列表
     */
    Page<SysUser> selectUserPage(Page<SysUser> page, @Param("query") UserQueryDTO query);

    /**
     * 根据用户ID查询用户角色
     *
     * @param userId 用户ID
     * @return 角色ID数组
     */
    Long[] selectUserRoleIds(@Param("userId") Long userId);
}