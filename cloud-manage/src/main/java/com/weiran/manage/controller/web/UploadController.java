package com.weiran.manage.controller.web;

import com.weiran.common.obj.Result;
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
    public Result<Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("type") Integer type) {
        ImageKit image = imageService.upload(file, type);
        return Result.success(image);
    }

    /**
     * 删除图片
     */
    @DeleteMapping
    public Result<Object> delete(@RequestParam("key") String key) {
        imageService.delete(key);
        return Result.success();
    }

    /**
     * 批量删除图片
     */
    @DeleteMapping("/deletes")
    public Result<Object> deletes(@RequestParam("keys") String[] keys) {
        imageService.deletes(keys);
        return Result.success();
    }
}
