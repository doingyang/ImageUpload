package com.project.upload.mvp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * **************************************************
 * 文件名称 : Test
 * 作    者 : Created by ydy
 * 创建时间 : 2018/5/18 16:40
 * 文件描述 :
 * 注意事项 :
 * 修改历史 : 2018/5/18 1.00 初始版本
 * **************************************************
 */
public class Test {

    public void testFile() {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL("http://192.168.11.76:7004/up.jsp?id=12346&no=1&len=19456&serverId=01&url=&userId=4334&appId=11&tocken=518DB6ADC91B30F7&downAppId=&downTocken=&fileAccessRight=0&blockFlag=false&isBothDownUrl=0");
            // 发送HTTP请求
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setRequestProperty("contentType", "UTF-8");
            urlConn.setRequestProperty("Attach-Name", URLEncoder.encode("123.xlsx", "UTF-8"));
            urlConn.setRequestProperty("Content-Length", "19456");
            InputStream inputStream = null;

            try {
                inputStream = new FileInputStream(new File("d:/123.xlsx"));
                OutputStream outputStream = urlConn.getOutputStream();

                int len = 0;
                byte buf[] = new byte[1024];
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                while ((len = bis.read(buf)) != -1) {
                    stream.write(buf, 0, len);
                }
                bis.close();
                outputStream.write(stream.toByteArray());
                outputStream.flush();
                outputStream.close();
                stream.close();
                inputStream.close();
                //------------------------
                InputStream inputStream1 = null;
                StringBuilder sb = null;
                int state = 0;
                try {
                    state = urlConn.getResponseCode();
                    // 判断请求是否成功
                    if (state != HttpURLConnection.HTTP_OK) {
                        System.out.println("链接错误");
                    } else {
                        inputStream1 = urlConn.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream1));
                        sb = new StringBuilder();
                        String line = null;
                        while ((line = rd.readLine()) != null) {
                            sb.append(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                } finally {
                    try {
                        if (inputStream1 != null) {
                            inputStream1.close();
                        }
                    } catch (IOException e) {

                    }
                }
                System.out.println(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
