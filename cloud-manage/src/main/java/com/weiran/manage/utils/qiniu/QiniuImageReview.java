package com.weiran.manage.utils.qiniu;

import com.weiran.manage.config.qiniu.QiNiuProperties;
import com.weiran.common.exception.CustomizeException;
import com.weiran.manage.response.CensorResp;
import com.weiran.manage.response.DiscriminateResp;
import com.weiran.common.enums.ResponseEnum;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片对比以及图片鉴黄
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QiniuImageReview {

    //图片审核地址
    private final String censorUrl = "http://ai.qiniuapi.com/v3/image/censor";
    //人脸对比
    private final String simUri = "http://ai.qiniuapi.com/v1/face/sim";
    private final QiNiuProperties qiNiuProperties;
    private Client client = new Client();
    private final Gson gson = new Gson();
    private final Auth auth;

    /**
     * 人脸对比
     *
     * @param realUrl
     * @param photoUrl
     * @return
     * @throws QiniuException
     */
    public boolean discriminate(String realUrl, String photoUrl) throws QiniuException {
        Map<String, Object> uri1 = new HashMap<>();
        uri1.put("uri", qiNiuProperties.getDomain().concat("/").concat(realUrl));
        Map<String, Object> uri2 = new HashMap<>();
        uri2.put("uri", qiNiuProperties.getDomain().concat("/").concat(photoUrl));
        Map[] data = new Map[2];
        data[0] = uri1;
        data[1] = uri2;
        Map params = new HashMap();
        params.put("data", data);
        String json = gson.toJson(params);
        StringMap headers = new StringMap();
        headers.put("Authorization", qiniuToken(simUri, json));
        DiscriminateResp discriminateResp = null;
        try {
            Response resp = client.post(simUri, json.getBytes(), headers, Client.JsonMime);
            discriminateResp = gson.fromJson(resp.bodyString(), DiscriminateResp.class);
        } catch (QiniuException e) {
            log.info("人脸识别异常：{}", e.getLocalizedMessage());
        }
        return discriminateResp != null && discriminateResp.getResult().isSame();
    }

    /**
     * 鉴黄
     *
     * @param imageUrl
     * @return
     * @throws QiniuException
     */
    public CensorResp.ResultBean.ScenesBean.PulpBean.DetailsBean imageReview(String imageUrl) throws QiniuException {
        String json = "{\"data\": {\"uri\": \"" + qiNiuProperties.getDomain() + "/" + imageUrl + "\"}, \"params\": {\"scenes\":[\"pulp\"]}}";
        StringMap headers = new StringMap();
        headers.put("Authorization", qiniuToken(censorUrl, json));
        Response resp = client.post(censorUrl, json.getBytes(), headers, Client.JsonMime);
        CensorResp censorResp = gson.fromJson(resp.bodyString(), CensorResp.class);
        if (censorResp.getCode() == 200) {
            return censorResp.getResult().getScenes().getPulp().getDetails().get(0);
        } else {
            throw new CustomizeException(ResponseEnum.IMAGE_VIOLATION_FAIL);
        }
    }

    //获得token
    String qiniuToken(String url, String json) {
        String authorization = (String) auth.authorizationV2(url, "POST", json.getBytes(), Client.JsonMime).get("Authorization");
        return authorization;
    }

}
