package com.xczhihui.common.util.vhallyun;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.xczhihui.common.util.EmailUtil;
import com.xczhihui.common.util.vhallyun.result.VhallYunResult;

public class VhallUtil {

    public static Properties pro = new Properties();
    static {
        try {
            InputStream in = EmailUtil.class.getResource("/config.properties").openStream();
            pro.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 由微吼云颁发，可通过控制台->设置->应用信息中获取
     */
    public static String APP_ID = pro.getProperty("vhallyun.appid");
    public static String SECRET_KEY = pro.getProperty("vhallyun.secretkey");

    /**
     * 计算签名
     *
     * @return string
     */
    public static String signature(HashMap<String, String> param) {
        param.put("signed_at", (System.currentTimeMillis() + "").substring(0, 10));
        TreeMap treeMap = new TreeMap(param);
        String signatrue = SECRET_KEY;
        Iterator var3 = treeMap.keySet().iterator();

        while (var3.hasNext()) {
            Object o = var3.next();
            if (!o.toString().equals("document")) {
                signatrue = signatrue + o.toString() + treeMap.get(o).toString();
            }
        }

        signatrue = signatrue + SECRET_KEY;
        return parseMd5(signatrue);
    }

    public static String parseMd5(String str) {
        String reStr = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            byte[] var5 = bytes;
            int var6 = bytes.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                byte b = var5[var7];
                int bt = b & 255;
                if (bt < 16) {
                    stringBuffer.append(0);
                }

                stringBuffer.append(Integer.toHexString(bt));
            }

            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
        }

        return reStr;
    }


    /**
     * 计算最终请求数据
     *
     * @return mixed
     */
    public static HashMap createRealParam(HashMap<String, String> params) throws NoSuchAlgorithmException {
        if ((params.get("sign")) != null) {
            return params;
        }
        params.put("app_id", APP_ID);
        // 计算签名
        params.put("sign", signature(params));
        return params;
    }

    public static String sendPost(String url, HashMap params) throws Exception {
        String result = formUpload(url, params);
        Boolean success = JsonPath.read(result, "$.code", new Predicate[0]).toString().equals("200");
        if (!success.booleanValue()) {
            throw new Exception(JsonPath.read(result, "$.msg", new Predicate[0]).toString());
        } else {
            return result;
        }
    }

    public static VhallYunResult sendPostAndRetResult(String url, HashMap<String, String> params) {
        String result = formUpload(url, params);
        return JSONObject.parseObject(result, VhallYunResult.class);
    }

    public static String formUpload(String urlStr, Map<String, String> textMap) {
        String res = "";
        String contentType = "";
        HttpURLConnection conn = null;
        Map<String, Object> fileMap = new HashMap();
        Boolean existDocument = textMap.containsKey("document");
        if (existDocument.booleanValue() && !textMap.get("document").equals("")) {
            fileMap.put("document", textMap.get("document"));
            textMap.remove("document");
        }

        String BOUNDARY = "---------------------------123821742118716";

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (server with java api sdk)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 设置字符编码
            conn.setRequestProperty("Accept-Charset","utf-8");
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            Map.Entry entry;
            String inputName;
            String inputValue;

            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();

                while (iter.hasNext()) {
                    entry = (Map.Entry) iter.next();
                    inputName = (String) entry.getKey();
                    inputValue = (String) entry.getValue();
                    if (inputValue != null) {
                        strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }
                }

                out.write(strBuf.toString().getBytes());
            }

            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();

                label219:
                while (true) {
                    do {
                        if (!iter.hasNext()) {
                            break label219;
                        }

                        entry = (Map.Entry) iter.next();
                        inputName = (String) entry.getKey();
                        inputValue = (String) entry.getValue();
                    } while (inputValue == null);

                    File file = new File(inputValue);
                    String filename = file.getName();
                    contentType = (new MimetypesFileTypeMap()).getContentType(file);
                    if (!"".equals(contentType)) {
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        } else if (!filename.endsWith(".jpg") && !filename.endsWith(".jpeg") && !filename.endsWith(".jpe")) {
                            if (filename.endsWith(".gif")) {
                                contentType = "image/gif";
                            } else if (filename.endsWith(".ico")) {
                                contentType = "image/image/x-icon";
                            }
                        } else {
                            contentType = "image/jpeg";
                        }
                    }

                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    byte[] bufferOut = new byte[1024];

                    int bytes;
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }

                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            inputValue = null;

            while ((inputValue = reader.readLine()) != null) {
                strBuf.append(inputValue).append("\n");
            }

            res = strBuf.toString();
            reader.close();
            entry = null;
        } catch (Exception var23) {
            System.out.println("发送POST请求出错。" + urlStr);
            var23.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }

        }

        return res;
    }


    public static String downUrlFile(String fileUrl, String downPath) {
        File savePath = new File(downPath);
        if (!savePath.exists()) {
            savePath.mkdir();
        }
        String[] urlname = fileUrl.split("/");
        int len = urlname.length - 1;
        String uname = urlname[len];// 获取文件名
        try {
            File file = new File(savePath + "/" + uname);// 创建新文件
            if (file != null && !file.exists()) {
                file.createNewFile();
            }
            OutputStream oputstream = new FileOutputStream(file);
            URL url = new URL(fileUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);// 设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            InputStream iputstream = uc.getInputStream();
            byte[] buffer = new byte[4 * 1024];
            int byteRead = -1;
            while ((byteRead = (iputstream.read(buffer))) != -1) {
                oputstream.write(buffer, 0, byteRead);
            }
            oputstream.flush();
            iputstream.close();
            oputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePath + "/" + uname;
    }

    public static String getCallbackSign(Map<String, String> params) {
        TreeMap<String, String> treeMap = new TreeMap<>(params);
        StringBuilder sb = new StringBuilder();
        String md5SecretKey = parseMd5(SECRET_KEY);
        for (String key : treeMap.keySet()) {
            sb.append(key).append("|").append(md5SecretKey).append("|").append(treeMap.get(key));
        }
        return parseMd5(sb.toString());
    }

}
