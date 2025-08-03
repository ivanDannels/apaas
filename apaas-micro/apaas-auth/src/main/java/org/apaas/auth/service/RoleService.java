package org.apaas.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.auth.domain.dto.RoleDTO;
import org.apaas.auth.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService extends IService<Role> {
    /**
     * 分页查询角色
     */
    IPageResult<Role> selectPage(RoleDTO query);

    /**
     * 创建角色
     */
    boolean create(Role role);

    /**
     * 更新角色
     */
    boolean update(Role role);

    /**
     * 删除角色
     */
    boolean delete(Long id);

    /**
     * 更新角色状态
     */
    boolean changeStatus(Long id, Integer status);

    /**
     * 关联角色资源
     */
    boolean associateRoleResource(Long roleId, List<Long> resourceIds);

    /**
     * 根据角色ID查询资源列表
     */
    List<String> getPermissionsByRoleId(Long roleId);

    /**
     * 根据用户ID查询角色列表
     */
    List<Role> getRolesByUserId(Long userId);

    /**
     * 获取角色的数据范围
     */
    String getDataScopeByRoleId(Long roleId);

    /**
     * 更新角色的数据范围
     */
    boolean updateDataScope(Long roleId, String dataScopeType, List<Long> customDeptIds);
}