package com.weiran.mission.service;

import com.weiran.mission.utils.qiniu.ImageKit;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {

    /**
     * 上传图片
     */
    ImageKit upload(MultipartFile file, Integer dir);

    /**
     * 删除图片
     */
    void delete(String key);

    /**
     * 批量删除图片
     */
    void deletes(String[] keys);

}
