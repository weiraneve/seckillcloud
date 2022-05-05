package com.weiran.manage.controller.uaa;

import com.weiran.manage.dto.uaa.RuleDTO;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
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
    public Result<List<RuleDTO>> ruleIndex() {
        List<RuleDTO> ruleDTOList = ruleService.findRule();
        if (ruleDTOList == null) {
            return Result.error(ResponseEnum.RULE_FIND_FAIL);
        }
        return Result.success(ruleDTOList);
    }

    /**
     * 修改rule
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @PutMapping
    public Result<Object> ruleUpdate(@RequestBody RuleDTO ruleDTO) {
        boolean updateSuccess = ruleService.update(ruleDTO);
        return updateSuccess ? Result.success() : Result.error(ResponseEnum.RULE_UPDATE_FAIL);
    }

}
