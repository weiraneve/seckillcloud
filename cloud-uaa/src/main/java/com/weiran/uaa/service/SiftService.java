package com.weiran.uaa.service;

import com.weiran.common.obj.Result;

public interface SiftService {

    /**
     * 通过对客户状态表对查询，判断是否有准入资格，并写入筛选表
     */
    Result findByStatus(long userId);

}
