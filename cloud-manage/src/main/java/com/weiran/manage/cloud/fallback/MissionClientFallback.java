package com.weiran.manage.cloud.fallback;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.common.pojo.dto.OrderDTO;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.common.pojo.vo.WelcomeVO;
import com.weiran.manage.cloud.MissionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class MissionClientFallback implements MissionClient {

    @Override
    public Result<PageInfo<GoodsDTO>> goodsIndex(Integer page, Integer pageSize, String goodsName) {
        log.warn("=============== goodsIndex方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> goodsCreate(GoodsDTO goodsDTO) {
        log.warn("=============== goodsCreate方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> goodsUpdate(GoodsDTO goodsDTO) {
        log.warn("=============== goodsUpdate方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<GoodsDTO> goodsEdit(Long id) {
        log.warn("=============== goodsEdit方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> goodsUsing(Long id) {
        log.warn("=============== goodsUsing方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> goodsDelete(Long id) {
        log.warn("=============== goodsDelete方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> goodsDeletes(String ids) {
        log.warn("=============== goodsDeletes方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<PageInfo<OrderDTO>> findByOrders(Integer page, Integer pageSize, Long id) {
        log.warn("=============== findByOrders方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<PageInfo<SeckillGoodsDTO>> seckillGoods(Integer page, Integer pageSize, Long goodsId) {
        log.warn("=============== seckillIndex方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<WelcomeVO> welcome() {
        log.warn("=============== welcome方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> uploadPic(MultipartFile file, Integer type) {
        log.warn("=============== upload方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> deletePic(String key) {
        log.warn("=============== delete方法调用失败，服务被降级");
        return null;
    }

    @Override
    public Result<Object> deletesPic(String[] keys) {
        log.warn("=============== deletes方法调用失败，服务被降级");
        return null;
    }
}
