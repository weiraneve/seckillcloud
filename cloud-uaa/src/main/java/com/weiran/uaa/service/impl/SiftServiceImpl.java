package com.weiran.uaa.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.uaa.entity.Sift;
import com.weiran.uaa.entity.Status;
import com.weiran.uaa.entity.User;
import com.weiran.uaa.manager.SiftManager;
import com.weiran.uaa.manager.StatusManager;
import com.weiran.uaa.service.SiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiftServiceImpl implements SiftService {

    final StatusManager statusManager;
    final SiftManager siftManager;

    // 通过对客户状态表对查询，判断是否有准入资格，并写入筛选表
    @Override
    public Result<User> findByStatus(long userId) {
        Status status = statusManager.getOne(Wrappers.<Status>lambdaQuery().eq(Status::getUserId, userId));
        // 贷款逾期记录(拒绝3年内逾期2次以上，金额小于 1000 元，3 天内还清除外)
        int exceedRecord = status.getExceedRecord();
        // 客户工作状态(拒绝无业、失业)
        String workStatus = status.getWorkStatus();
        // 客户是否被列入失信名单(拒绝被列入名单)
        int dishonest = status.getDishonest();
        // 客户年龄(拒绝小于18岁)
        int age = status.getAge();
        Sift sift = new Sift();
        sift.setUserId(userId);
        sift.setIdentityCardId(status.getIdentityCardId());
        Result<User> result = new Result<>();
        User user = new User();
        user.setId(userId);
        result.setData(user);
        if (exceedRecord > 2 || workStatus.equals("无业") || workStatus.equals("失业")
                || dishonest == 1 || age < 18) {
            sift.setSiftPass(0); // 不通过
            changeSift(sift);
            result.setCode(CodeMsg.No_SIFT_PASS.getCode());
            return result;
        }
        sift.setSiftPass(1); // 通过
        changeSift(sift);
        result.setCode(CodeMsg.SUCCESS.getCode());
        return result;
    }

    // 通过传入的对象改动筛选表
    private void changeSift(Sift sift) {
        long userId = sift.getUserId();
        Sift findSift = siftManager.getOne(Wrappers.<Sift>lambdaQuery().eq(Sift::getUserId, userId));
        if (findSift == null) {
            siftManager.save(sift);
        } else {
            siftManager.update(sift, Wrappers.<Sift>lambdaQuery().eq(Sift::getUserId, userId));
        }
    }
}
