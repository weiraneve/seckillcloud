package com.weiran.manage.controller.uaa;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.entity.uaa.SiftDTO;
import com.weiran.manage.response.ResultVO;
import com.weiran.manage.service.uaa.SiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sift")
@RequiredArgsConstructor
public class SiftController {

    private final SiftService siftService;

    /**
     * 分页查询
     */
    @GetMapping
    public ResultVO siftIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, Long userId) {
        PageInfo<SiftDTO> sift = siftService.findSift(page, pageSize, userId);
        return ResultVO.success(sift);
    }
}
