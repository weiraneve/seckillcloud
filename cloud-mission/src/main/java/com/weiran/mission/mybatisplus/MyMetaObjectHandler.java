package com.weiran.mission.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充字段创建时间和修改时间
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("lastLoginDate", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("lastLoginDate", LocalDateTime.now(), metaObject);
    }
}