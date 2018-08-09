package com.xczh.consumer.market.controller.course;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 为前端解决canvas跨域问题
 */
@Controller
@RequestMapping
public class ImgProxyController {

    @RequestMapping(value = "imgProxy", method = RequestMethod.GET)
    public void imgProxy(String imgURL, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建URL
        URL url = new URL(imgURL);
        // 创建链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if(conn.getResponseCode()==200){
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);

            response.reset();
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
