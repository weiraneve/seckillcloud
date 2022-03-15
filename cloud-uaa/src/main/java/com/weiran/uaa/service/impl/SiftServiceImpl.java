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

    // 通过对客户状态表对查询，判断是否有准入资格，并写入筛选表；这一块的逻辑完成度不高的原因是，没有对应的每个用户的被筛选的数据，空有筛选的规则。
    @Override
    public Result<User> findByStatus(User user) {
        Result<User> result = new Result<>();
        result.setData(user);
        Sift sift = new Sift();
        sift.setUserId(user.getId());
        sift.setIdentityCardId(user.getIdentityCardId());
        Status status = statusManager.getOne(Wrappers.<Status>lambdaQuery().eq(Status::getUserId, user.getId()));
        if (status == null) { // 暂且在这里写死，没有数据就直接返回筛选正常
            result.setCode(CodeMsg.SUCCESS.getCode());
            sift.setSiftPass(1); // 通过
            changeSift(sift);
            return result;
        }
        // 贷款逾期记录(0为正常，1为有)
        int exceedRecord = status.getExceedRecord();
        // 客户工作状态(0为正常，1为无业、失业的拒绝情况)
        int workStatus = status.getWorkStatus();
        // 客户是否被列入失信名单(0为正常，1为被列入名单)
        int dishonest = status.getDishonest();
        // 客户年龄(0为正常，1为非正常)
        int age = status.getAge();
        if (exceedRecord == 1 || workStatus == 1 || dishonest == 1 || age == 1) {
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
