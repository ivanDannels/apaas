package org.apaas.flow.execution.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.flow.execution.domain.entity.ActivityInstance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityInstanceMapper extends BaseMapper<ActivityInstance> {
}