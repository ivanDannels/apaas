package org.apaas.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.authorization.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户角色关联Mapper接口
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 批量插入用户角色关联
     */
    boolean insertBatch(@Param("userRoles") List<UserRole> userRoles);
}