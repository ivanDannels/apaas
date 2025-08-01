package org.apaas.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.authorization.entity.RoleResource;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色资源关联Mapper接口
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {
    /**
     * 批量插入角色资源关联
     */
    boolean insertBatch(@Param("roleResources") List<RoleResource> roleResources);
}