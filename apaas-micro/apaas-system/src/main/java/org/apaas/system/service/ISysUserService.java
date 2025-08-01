package org.apaas.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.system.domain.dto.UserQueryDTO;
import org.apaas.system.domain.entity.SysUser;

/**
 * 用户服务接口
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getUserByUsername(String username);

    /**
     * 分页查询用户列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 用户列表
     */
    Page<SysUser> getUserPage(Page<SysUser> page, UserQueryDTO query);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean addUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean updateUser(SysUser user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    boolean deleteUser(Long userId);

    /**
     * 重置密码
     *
     * @param userId   用户ID
     * @param password 新密码
     * @return 结果
     */
    boolean resetPassword(Long userId, String password);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 结果
     */
    boolean changeStatus(Long userId, Integer status);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    String[] getUserPermissions(Long userId);

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param ip       IP地址
     * @return 结果
     */
    boolean recordLoginInfo(String username, String ip);
}