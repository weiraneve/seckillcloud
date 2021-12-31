package com.weiran.uaa.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.uaa.entity.User;
import com.weiran.uaa.manager.UserManager;
import com.weiran.uaa.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl extends ServiceImpl<UserMapper, User> implements UserManager {

}
