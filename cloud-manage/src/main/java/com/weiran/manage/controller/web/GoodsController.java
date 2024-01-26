package com.weiran.manage.controller.web;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.manage.cloud.MissionClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("商品列表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final MissionClient missionClient;

    @ApiOperation("分页查询goods")
    @GetMapping
    public Result<PageInfo<GoodsDTO>> goodsIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                 @RequestParam(required = false) String goodsName) {
        return missionClient.goodsIndex(page, pageSize, goodsName);
    }

    @ApiOperation("新增goods")
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @PostMapping
    public Result<Object> goodsCreate(@RequestBody @Valid GoodsDTO goodsDTO) {
        return missionClient.goodsCreate(goodsDTO);
    }

    @ApiOperation("修改goods")
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @PutMapping
    public Result<Object> goodsUpdate(@RequestBody @Valid GoodsDTO goodsDTO) {
        return missionClient.goodsUpdate(goodsDTO);
    }

    @ApiOperation("选择单个goods")
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @GetMapping("/{id}/edit")
    public Result<GoodsDTO> goodsEdit(@PathVariable("id") Long id) {
        return missionClient.goodsEdit(id);
    }

    @ApiOperation("修改是否可用")
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_UPDATE_USER','ROLE_SUPER_ADMIN')")
    @GetMapping("/updateUsing/{id}")
    public Result<Object> goodsUsing(@PathVariable("id") Long id) {
        return missionClient.goodsUsing(id);
    }

    @ApiOperation("单个删除goods")
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_DELETE_USER','ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public Result<Object> goodsDelete(@PathVariable("id") Long id) {
        return missionClient.goodsDelete(id);
    }

    @ApiOperation("批量删除")
    @PreAuthorize("hasAnyAuthority('SETTING_NORMAL_DELETE_USER','ROLE_SUPER_ADMIN')")
    @DeleteMapping("/deletes")
    public Result<Object> goodsDeletes(@RequestParam String ids) {
        return missionClient.goodsDeletes(ids);
    }

}
