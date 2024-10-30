package com.weiran.mission.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weiran.mission.pojo.entity.SeckillNotification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("goods")
public interface SeckillNotificationMapper extends BaseMapper<SeckillNotification> {

    /**
     * 根据userId查询SeckillNotification
     */
    List<SeckillNotification> findSeckillNotificationByUserId(@Param("userId") Long userId);
}