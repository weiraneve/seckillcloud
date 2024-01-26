package com.weiran.manage.controller.web;

import com.weiran.common.obj.Result;
import com.weiran.manage.cloud.MissionClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Api("后台系统")
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final MissionClient missionClient;

    @ApiOperation("上传图片")
    @PostMapping
    public Result<Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("type") Integer type) {
        return missionClient.uploadPic(file, type);
    }

    @ApiOperation("删除图片")
    @DeleteMapping
    public Result<Object> delete(@RequestParam("key") String key) {
        return missionClient.deletePic(key);
    }

    @ApiOperation("批量删除图片")
    @DeleteMapping("/deletes")
    public Result<Object> deletes(@RequestParam("keys") String[] keys) {
        return missionClient.deletesPic(keys);
    }
}
