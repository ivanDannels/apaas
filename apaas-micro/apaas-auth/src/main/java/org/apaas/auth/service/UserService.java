package org.apaas.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.authorization.domain.dto.UserDTO;
import org.apaas.authorization.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);

    /**
     * 用户登录
     */
    String login(String username, String password);

    /**
     * 用户注册
     */
    boolean register(User user);

    /**
     * 重置密码
     */
    boolean resetPassword(Long id, String newPassword);

    /**
     * 分页查询用户
     */
    IPageResult<User> selectPage(UserDTO query);

    /**
     * 更新用户状态
     */
    boolean changeStatus(Long id, Integer status);

    /**
     * 关联用户角色
     */
    boolean associateUserRole(Long userId, List<Long> roleIds);

    /**
     * 根据用户ID查询角色列表
     */
    List<String> getRolesByUserId(Long userId);

    /**
     * 根据用户ID查询权限列表
     */
    List<String> getPermissionsByUserId(Long userId);
}