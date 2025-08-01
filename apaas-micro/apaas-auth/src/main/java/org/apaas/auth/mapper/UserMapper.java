package org.apaas.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.authorization.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 根据角色ID查询用户列表
     */
    List<User> selectByRoleId(@Param("roleId") Long roleId);
}