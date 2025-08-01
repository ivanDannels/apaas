package org.apaas.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.system.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知Mapper接口
 */
public interface NotificationMapper extends BaseMapper<Notification> {

    /**
     * 查询用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    Integer countUnreadByUserId(@Param("userId") Long userId);

    /**
     * 查询用户通知列表
     *
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 通知列表
     */
    List<Notification> selectByUserId(@Param("userId") Long userId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    /**
     * 批量更新通知阅读状态
     *
     * @param ids 通知ID列表
     * @param readStatus 阅读状态
     * @return 更新数量
     */
    Integer batchUpdateReadStatus(@Param("ids") List<Long> ids, @Param("readStatus") Integer readStatus);

    /**
     * 根据业务ID和类型查询通知
     *
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 通知列表
     */
    List<Notification> selectByBusinessInfo(@Param("businessId") String businessId, @Param("businessType") String businessType);
}