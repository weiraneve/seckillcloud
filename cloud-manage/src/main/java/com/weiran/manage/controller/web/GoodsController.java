package com.weiran.manage.controller.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.web.GoodsDTO;
import com.weiran.manage.enums.ResponseEnum;
import com.weiran.manage.response.ResultVO;
import com.weiran.manage.service.web.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 商品列表
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    /**
     * 分页查询goods
     */
    @GetMapping
    public ResultVO goodsIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, String goodsName) {
        PageInfo<GoodsDTO> goods = goodsService.findGoods(page, pageSize, goodsName);
        return ResultVO.success(goods);
    }

    /**
     * 新增goods
     */
    @PostMapping
    public ResultVO goodsCreate(@RequestBody GoodsDTO goodsDTO) {
        boolean createSuccess = goodsService.create(goodsDTO);
        return createSuccess ? ResultVO.success() : ResultVO.fail(ResponseEnum.Goods_CREATE_FAIL);
    }

    /**
     * 修改goods
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @PutMapping
    public ResultVO goodsUpdate(@RequestBody GoodsDTO goodsDTO) {
        boolean updateSuccess = goodsService.update(goodsDTO);
        return updateSuccess ? ResultVO.success() : ResultVO.fail(ResponseEnum.Goods_CREATE_FAIL);
    }

    /**
     * 选择单个goods
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @GetMapping("/{id}/edit")
    public ResultVO goodsEdit(@PathVariable("id") Long id) {
        GoodsDTO goodsDTO = goodsService.selectById(id);
        return ResultVO.success(goodsDTO);
    }

    /**
     * 修改是否可用
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @GetMapping("/updateUsing/{id}")
    public ResultVO goodsUsing(@PathVariable("id") Long id) {
        goodsService.updateUsingById(id);
        return ResultVO.success();
    }

    /**
     * 单个删除
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_DELETE_USER','ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResultVO goodsDelete(@PathVariable("id") Long id) {
        goodsService.delete(id);
        return ResultVO.success();
    }

    /**
     * 批量删除
     */
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_DELETE_USER','ROLE_SUPER_ADMIN')")
    @DeleteMapping("/deletes")
    public ResultVO goodsDeletes(@RequestParam String ids) {
        goodsService.deletes(ids);
        return ResultVO.success();
    }

}
