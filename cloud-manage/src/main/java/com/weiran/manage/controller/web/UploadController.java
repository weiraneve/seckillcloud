package com.weiran.manage.controller.web;

import com.weiran.manage.response.ResultVO;
import com.weiran.manage.service.web.ImageService;
import com.weiran.manage.utils.qiniu.ImageKit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final ImageService imageService;

    /**
     * 上传图片
     */
    @PostMapping
    public ResultVO upload(@RequestParam("file") MultipartFile file, @RequestParam("type") Integer type) {
        ImageKit image = imageService.upload(file, type);
        return ResultVO.success(image);
    }

    /**
     * 删除图片
     */
    @DeleteMapping
    public ResultVO delete(@RequestParam("key") String key) {
        imageService.delete(key);
        return ResultVO.success();
    }

    /**
     * 批量删除图片
     */
    @DeleteMapping("/deletes")
    public ResultVO deletes(@RequestParam("keys") String[] keys) {
        imageService.deletes(keys);
        return ResultVO.success();
    }
}
