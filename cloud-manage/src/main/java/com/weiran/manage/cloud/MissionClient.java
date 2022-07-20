package com.weiran.manage.cloud;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.common.pojo.dto.OrderDTO;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.common.pojo.vo.WelcomeVO;
import com.weiran.manage.cloud.fallback.MissionClientFallback;
import com.weiran.manage.config.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@FeignClient(name = "cloud-mission", fallback = MissionClientFallback.class, configuration = FeignInterceptor.class)
public interface MissionClient {

    // 分页查询goods
    @GetMapping("/cloud/goods")
    Result<PageInfo<GoodsDTO>> goodsIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                          @RequestParam(required = false) String goodsName);

    // 新增goods
    @PostMapping("/cloud/goods")
    Result<Object> goodsCreate(@RequestBody @Valid GoodsDTO goodsDTO);

    // 修改goods
    @PutMapping("/cloud/goods")
    Result<Object> goodsUpdate(@RequestBody @Valid GoodsDTO goodsDTO);

    // 选择单个goods
    @GetMapping("/cloud/goods/{id}/edit")
    Result<GoodsDTO> goodsEdit(@PathVariable("id") Long id);

    // 修改是否可用
    @GetMapping("/cloud/goods/updateUsing/{id}")
    Result<Object> goodsUsing(@PathVariable("id") Long id);

    // 单个删除goods
    @DeleteMapping("/cloud/goods/{id}")
    Result<Object> goodsDelete(@PathVariable("id") Long id);

    // 批量删除
    @DeleteMapping("/cloud/goods/deletes")
    Result<Object> goodsDeletes(@RequestParam String ids);

    // 分页查询订单
    @GetMapping("/cloud/order/findByOrders")
    Result<PageInfo<OrderDTO>> findByOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long id);
    // 分页查询秒杀商品
    @GetMapping("/cloud/seckillGoods")
    Result<PageInfo<SeckillGoodsDTO>> seckillGoods(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long goodsId);

    // 统计查询
    @GetMapping("/cloud/welcome")
    Result<WelcomeVO> welcome();
    
    // 上传图片
    @PostMapping(value = "/cloud/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<Object> uploadPic(@RequestPart("file") MultipartFile file, @RequestParam("type") Integer type);
        
    // 删除图片
    @DeleteMapping("/cloud/upload")
    Result<Object> deletePic(@RequestParam("key") String key);
    
    // 批量删除图片
    @DeleteMapping("/cloud/upload/deletes")
    Result<Object> deletesPic(@RequestParam("keys") String[] keys);
    
}
