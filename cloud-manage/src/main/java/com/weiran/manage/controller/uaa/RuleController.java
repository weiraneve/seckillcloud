package com.weiran.manage.controller.uaa;

import com.weiran.manage.entity.uaa.Rule;
import com.weiran.manage.entity.web.Goods;
import com.weiran.manage.enums.ResponseEnum;
import com.weiran.manage.response.ResultVO;
import com.weiran.manage.service.uaa.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 筛选规则
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rule")
public class RuleController {

    private final RuleService ruleService;

    /**
     * 查询rule
     */
    @GetMapping
    public ResultVO ruleIndex() {
        List<Rule> rule = ruleService.findRule();
        if (rule == null) {
            return ResultVO.fail("查询rule失败");
        }
        return ResultVO.success(rule);
    }

    /**
     * 修改rule
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @PutMapping
    public ResultVO ruleUpdate(@RequestBody Rule rule) {
        boolean updateSuccess = ruleService.update(rule);
        return updateSuccess ? ResultVO.success() : ResultVO.fail(ResponseEnum.RULE_UPDATE_FAIL);
    }

}
