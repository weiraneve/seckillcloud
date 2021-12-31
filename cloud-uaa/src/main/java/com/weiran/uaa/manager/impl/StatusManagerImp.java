package com.weiran.uaa.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.uaa.manager.StatusManager;
import com.weiran.uaa.mapper.StatusMapper;
import com.weiran.uaa.entity.Status;
import org.springframework.stereotype.Service;

/**
 * 客户状态表 服务实现类
 */
@Service
public class StatusManagerImp extends ServiceImpl<StatusMapper, Status> implements StatusManager {

}
