package com.weiran.mission.utils.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;

import java.io.IOException;


@Data
public class QiNiuUploadKit {

    private Long expireTime = System.currentTimeMillis() / 1000;
    private String bucket;
    private UploadManager uploadManager;
    private BucketManager bucketManager;
    private String upToken;
    private Auth auth;

    DefaultPutRet upload(byte[] bytes, String key) throws IOException {
        generateUpToken();
        Response response = uploadManager.put(bytes, key, upToken);
        return new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    }

    void delete(String key) throws QiniuException {
        Response response = bucketManager.delete(bucket, key);
        new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    }

    private void generateUpToken() {
        this.expireTime = System.currentTimeMillis() / 1000 + 3600;
        this.upToken = auth.uploadToken(bucket);
    }

}
