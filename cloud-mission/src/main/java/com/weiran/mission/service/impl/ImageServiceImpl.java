package com.weiran.mission.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.weiran.common.enums.ImageDirEnum;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.validation.CustomValidation;
import com.weiran.mission.service.ImageService;
import com.weiran.mission.utils.EnumUtil;
import com.weiran.mission.utils.qiniu.ImageKit;
import com.weiran.mission.utils.qiniu.ImageScalaKit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageScalaKit imageScalaKit;

    /**
     * 上传图片
     * @param file  前端上传的文件
     * @param dir  上传到那个文件
     * @return ImageKit
     */
    @Override
    public ImageKit upload(MultipartFile file, Integer dir) {
        ImageDirEnum dirEnum = EnumUtil.getByCode(dir, ImageDirEnum.class);
        CustomValidation.isInvalid(ObjectUtil.isNull(dirEnum), ResponseEnum.IMAGE_ENUM_NOT_FOUND);
        return imageScalaKit.upload(file, dirEnum.getMsg());
    }

    /**
     * 删除图片
     * @param key 图片地址或者图片的key
     */
    @Override
    public void delete(String key) {
        imageScalaKit.delete(key);
    }

    @Override
    public void deletes(String[] keys) {
        for (String key : keys) {
            imageScalaKit.delete(key);
        }
    }
}
