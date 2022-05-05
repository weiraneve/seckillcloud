package com.weiran.manage.controller.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.web.SeckillGoodsDTO;
import com.weiran.common.obj.Result;
import com.weiran.manage.service.web.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seckill")
public class SeckillController {

    private final SeckillService seckillService;

    /**
     * 分页查询
     */
    @GetMapping
    public Result<PageInfo<SeckillGoodsDTO>> seckillIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, Long goodsId) {
        PageInfo<SeckillGoodsDTO> seckill = seckillService.findSeckill(page, pageSize, goodsId);
        return Result.success(seckill);
    }

}
