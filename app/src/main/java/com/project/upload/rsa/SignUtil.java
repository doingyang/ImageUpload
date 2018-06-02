package com.project.upload.rsa;

import android.util.Base64;
import android.util.Log;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 新建微信群发---参数签名工具
 * @date 2018/1/2 17:53
 */
public class SignUtil {

    /**
     * 签名后生成sign参数
     * 添加sign参数
     * 移除不要的参数
     * 返回结果为接口请求参数
     */
    public static Map<String, String> startSign(Map<String, String> params) {
        String signPaper = getSignCheckAPIInterfaceContent(params);
        Log.i("TAG", "signPaper: " + signPaper);
        // 对拼接的字符串使用Hash函数MD5生成签名摘要
        String md5Sign = MD5Util.md5Encode(signPaper, null);
        Log.i("TAG", "md5Sign: " + md5Sign);
        try {
//            PublicKey publicKey = RSAUtil.loadPublicKey(WeChatConstant.getPublicKey());
            PublicKey publicKey = RSAUtil.loadPublicKey("");
            byte[] bytes = md5Sign.getBytes();
            byte[] encryptData = RSAUtil.encryptData(bytes, publicKey);
            String sign = Base64.encodeToString(encryptData, Base64.NO_WRAP);
            Log.i("TAG", "sign: " + sign);
            params.put("sign", sign);
            // 移除不要的参数
            params.remove("charset");
            params.remove("version");
            params.remove("appid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 生成待签名的参数map（所有的参数）
     */
    public static Map<String, String> createParamsMap(String timeStamp, String content, String openIDs) {
        Map<String, String> map = new HashMap<>();
//        map.put("charset", WeChatConstant.getCharset());
//        map.put("version", WeChatConstant.getVersion());
//        map.put("appid", WeChatConstant.getAppId());
        map.put("timestamp", timeStamp);
        map.put("content", content);
        map.put("openids", openIDs);
//        map.put("thirdpart_id", MyApplication.getApp().getUser().getOrgCode());
        // 第三方机构的id（000001表示公司自己所有公众号）
        return map;
    }

    /**
     * 待签名参数进行ASCII升序拼接成字符串
     */
    public static String getSignCheckAPIInterfaceContent(Map<String, String> params) {
        if (params == null) {
            return null;
        }
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }
        return content.toString();
    }

}
