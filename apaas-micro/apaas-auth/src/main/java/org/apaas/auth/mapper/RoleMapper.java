package org.apaas.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.authorization.entity.Role;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色Mapper接口
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户ID查询角色列表
     */
    List<Role> selectByUserId(@Param("userId") Long userId);
}