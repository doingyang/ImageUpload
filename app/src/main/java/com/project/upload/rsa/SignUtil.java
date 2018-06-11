package com.project.upload.rsa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author ydy
 */
public class SignUtil {

    /**
     * 待签名参数进行ASCII升序拼接成字符串
     */
    public static String getSignContent(Map<String, String> params) {
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
