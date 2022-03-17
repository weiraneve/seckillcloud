package com.weiran.manage.controller.uaa;

import com.weiran.manage.dto.uaa.RuleDTO;
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
        List<RuleDTO> ruleDTO = ruleService.findRule();
        if (ruleDTO == null) {
            return ResultVO.fail("查询rule失败");
        }
        return ResultVO.success(ruleDTO);
    }

    /**
     * 修改rule
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @PutMapping
    public ResultVO ruleUpdate(@RequestBody RuleDTO ruleDTO) {
        boolean updateSuccess = ruleService.update(ruleDTO);
        return updateSuccess ? ResultVO.success() : ResultVO.fail(ResponseEnum.RULE_UPDATE_FAIL);
    }

}
