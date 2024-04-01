package com.weiran.mission.controller;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.common.pojo.dto.OrderDTO;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.common.pojo.vo.WelcomeVO;
import com.weiran.mission.service.*;
import com.weiran.mission.utils.qiniu.ImageKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api("Feign接口")
@RequestMapping("/cloud")
public class CloudController {

    private final GoodsService goodsService;
    private final OrderService orderService;
    private final SeckillGoodsService seckillGoodsService;
    private final WelcomeService welcomeService;
    private final ImageService imageService;
    private final SeckillService seckillService;

    @ApiOperation("分页查询goods")
    @GetMapping("/goods")
    public Result<PageInfo<GoodsDTO>> goodsIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                 @RequestParam(required = false) String goodsName) {
        PageInfo<GoodsDTO> goods = goodsService.findGoods(page, pageSize, goodsName);
        return Result.success(goods);
    }

    @ApiOperation("新增goods")
    @PostMapping("/goods")
    public Result<Object> goodsCreate(@RequestBody @Valid GoodsDTO goodsDTO) {
        goodsService.create(goodsDTO);
        seckillService.updateSeckillLocalMap(goodsDTO);
        return seckillGoodsService.create(goodsDTO);
    }

    @ApiOperation("修改goods")
    @PutMapping("/goods")
    public Result<Object> goodsUpdate(@RequestBody @Valid GoodsDTO goodsDTO) {
        goodsService.update(goodsDTO);
        seckillService.updateSeckillLocalMap(goodsDTO);
        return seckillGoodsService.update(goodsDTO);
    }

    /**
     * 选择单个goods
     */
    @ApiOperation("获得具体的商品细节")
    @GetMapping("/goods/{id}/edit")
    public Result<GoodsDTO> goodsEdit(@PathVariable("id") Long id) {
        GoodsDTO goodsDTO = goodsService.selectById(id);
        return Result.success(goodsDTO);
    }

    @ApiOperation("修改是否可用")
    @GetMapping("/goods/updateUsing/{id}")
    public Result<Object> goodsUsing(@PathVariable("id") Long id) {
        goodsService.updateUsingById(id);
        return Result.success();
    }

    @ApiOperation("单个删除goods")
    @DeleteMapping("/goods/{id}")
    public Result<Object> goodsDelete(@PathVariable("id") Long id) {
        goodsService.delete(id);
        seckillGoodsService.delete(id);
        return Result.success();
    }

    @ApiOperation("批量删除goods")
    @DeleteMapping("/goods/deletes")
    public Result<Object> goodsDeletes(@RequestParam String ids) {
        goodsService.deletes(ids);
        seckillGoodsService.deletes(ids);
        return Result.success();
    }

    @ApiOperation("分页查询订单")
    @GetMapping("/order/findByOrders")
    public Result<PageInfo<OrderDTO>> findByOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long id) {
        PageInfo<OrderDTO> order = orderService.findByOrders(page, pageSize, id);
        return Result.success(order);
    }

    @ApiOperation("分页查询秒杀商品")
    @GetMapping("/seckillGoods")
    public Result<PageInfo<SeckillGoodsDTO>> seckillIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long goodsId) {
        PageInfo<SeckillGoodsDTO> seckill = seckillGoodsService.findSeckill(page, pageSize, goodsId);
        return Result.success(seckill);
    }

    @ApiOperation("统计")
    @GetMapping("/welcome")
    public Result<WelcomeVO> welcome() {
        WelcomeVO welcomeVO = welcomeService.welcomeCount();
        return Result.success(welcomeVO);
    }

    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public Result<Object> uploadPic(@RequestParam("file") MultipartFile file, @RequestParam("type") Integer type) {
        ImageKit image = imageService.upload(file, type);
        return Result.success(image);
    }

    @ApiOperation("删除图片")
    @DeleteMapping("/upload")
    public Result<Object> deletePic(@RequestParam("key") String key) {
        imageService.delete(key);
        return Result.success();
    }

    @ApiOperation("批量删除图片")
    @DeleteMapping("/upload/deletes")
    public Result<Object> deletesPic(@RequestParam("keys") String[] keys) {
        imageService.deletes(keys);
        return Result.success();
    }

}
