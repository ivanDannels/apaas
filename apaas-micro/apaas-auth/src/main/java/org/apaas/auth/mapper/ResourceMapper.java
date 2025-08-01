package org.apaas.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.authorization.entity.Resource;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 资源Mapper接口
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 根据角色ID查询资源列表
     */
    List<Resource> selectByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户ID查询资源列表
     */
    List<Resource> selectByUserId(@Param("userId") Long userId);
}