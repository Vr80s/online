package com.xczhihui.common.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * 和HTTP相关的操作
 *
 * @author
 *
 */
public class HttpUtil {

    /**
     * 发送post请求
     *
     * @param reqUrl
     * @param parameters
     * @return
     */
    public static String doPost(String reqUrl, Map<String, String> parameters) {
        HttpURLConnection urlConn = null;
        try {
            urlConn = _sendPost(reqUrl, parameters);
            String responseContent = _getContent(urlConn);
            return responseContent.trim();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    /**
     * 上传文件
     *
     * @param reqUrl
     * @param parameters
     * @param fileParamName
     * @param filename
     * @param contentType
     * @param data
     * @return
     */
    public static String doUploadFile(String reqUrl,
                                      Map<String, String> parameters, String fileParamName,
                                      String filename, String contentType, byte[] data) {
        HttpURLConnection urlConn = null;
        try {
            urlConn = _sendFormdata(reqUrl, parameters, fileParamName,
                    filename, contentType, data);
            String responseContent = new String(_getBytes(urlConn));
            return responseContent.trim();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    private static HttpURLConnection _sendFormdata(String reqUrl,
                                                   Map<String, String> parameters, String fileParamName,
                                                   String filename, String contentType, byte[] data) {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(5000);// （单位：毫秒）jdk
            urlConn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlConn.setDoOutput(true);

            urlConn.setRequestProperty("Connection", "keep-alive");

            String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
            urlConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            boundary = "--" + boundary;
            StringBuffer params = new StringBuffer();
            if (parameters != null) {
                for (Iterator<String> iter = parameters.keySet().iterator(); iter
                        .hasNext();) {
                    String name = iter.next();
                    String value = parameters.get(name);
                    params.append(boundary + "\r\n");
                    params.append("Content-Disposition: form-data; name=\""
                            + name + "\"\r\n\r\n");
                    // params.append(URLEncoder.encode(value, "UTF-8"));
                    params.append(value);
                    params.append("\r\n");
                }
            }

            StringBuilder sb = new StringBuilder();
            // sb.append("\r\n");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"" + fileParamName
                    + "\"; filename=\"" + filename + "\"\r\n");
            sb.append("Content-Type: " + contentType + "\r\n\r\n");
            byte[] fileDiv = sb.toString().getBytes();
            byte[] endData = ("\r\n" + boundary + "--\r\n").getBytes();
            byte[] ps = params.toString().getBytes();

            OutputStream os = urlConn.getOutputStream();
            os.write(ps);
            os.write(fileDiv);
            os.write(data);
            os.write(endData);

            os.flush();
            os.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }

    private static String _getContent(HttpURLConnection urlConn) {
        InputStream in = null;
        BufferedReader rd = null;
        String responseContent = null;
        try {
            in = urlConn.getInputStream();
            rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            return responseContent;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (rd != null) {
                    rd.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage(), e2);
            }
        }
    }

    private static byte[] _getBytes(HttpURLConnection urlConn) {
        try {
            InputStream in = urlConn.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = in.read(buf)) > 0;) {
                os.write(buf, 0, i);
            }
            in.close();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static HttpURLConnection _sendPost(String reqUrl,
                                               Map<String, String> parameters) {
        HttpURLConnection urlConn = null;
        try {
            String params = urlEncode(parameters);

            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(10000);// （单位：毫秒）jdk
            urlConn.setReadTimeout(10000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlConn.setDoOutput(true);
            byte[] b = params.getBytes();
            urlConn.getOutputStream().write(b, 0, b.length);
            urlConn.getOutputStream().flush();
            urlConn.getOutputStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }

    /**
     * 将parameters中的value进行URL Encode
     *
     * @param parameters
     * @return
     */
    public static String urlEncode(Map<String, String> parameters) {
        StringBuffer params = new StringBuffer();
        if (parameters != null) {
            for (Iterator<String> iter = parameters.keySet().iterator(); iter
                    .hasNext();) {
                String name = iter.next();
                String value = parameters.get(name);
                if (value == null) {
                    value = "";
                }
                params.append(name + "=");
                try {
                    params.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                if (iter.hasNext()) {
                    params.append("&");
                }
            }
        }
        return params.toString();
    }

    /**
     * 发送GET请求
     *
     * @param link
     * @param charset
     * @return
     */
    public static String doGet(String link, String charset) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedInputStream in = new BufferedInputStream(
                    conn.getInputStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = in.read(buf)) > 0;) {
                out.write(buf, 0, i);
            }
            out.flush();
            out.close();
            String s = new String(out.toByteArray(), charset);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 获取链接的http响应代码。
     *
     * @param link
     * @return
     */
    public static int getHttpResponseCode(String link) {
        HttpURLConnection conn = null;
        int httpCode = 0;
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            httpCode = conn.getResponseCode();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return httpCode;
    }

    /**
     * UTF-8编码
     *
     * @param link
     * @return
     */
    public static String doGet(String link) {
        return doGet(link, "UTF-8");
    }

    /**
     * 发送一个get请求，并将响应结果转成int。
     *
     * @param link
     * @return
     */
    public static int getIntResponse(String link) {
        String str = doGet(link);
        return Integer.parseInt(str.trim());
    }

    public static StringBuffer httpsRequest(String requestUrl,
                                            String requestMethod, String output) throws IOException {

        /**
         * 类 URL 表示一个统一资源定位符，它是指向互联网“资源的指针”。资源可以是简单的文件或者目录也可以是对更为复杂的而对象的引用。
         */
        URL url = new URL(requestUrl);

        HttpsURLConnection connection = (HttpsURLConnection) url
                .openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod(requestMethod);

        // 从输入流读取返回内容
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        connection.disconnect();
        return buffer;
    }


    private static byte[] getBytes(HttpsURLConnection urlConn) {
        try {
            InputStream in = urlConn.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = in.read(buf)) > 0;) {
                os.write(buf, 0, i);
            }
            in.close();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    /**
     *
     * Description：
     * @param reqUrl		请求的url
     * @param parameters    要传递的参数
     * @param fileParamName 文件的参数名 ： 接收端要识别的
     * @param filename		名字后缀名
     * @param data			文件转换的二进制数组
     * @return
     * @return String
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
    public static String uploadFile(String reqUrl,
                                    Map<String, String> parameters,
                                    String fileParamName,
                                    String filename,
                                    byte[] data) {
        HttpsURLConnection urlConn = null;
        try {
            urlConn = httpsUploadFile(reqUrl,
                    parameters,
                    fileParamName,
                    filename,
                    data);

            String responseContent = new String(getBytes(urlConn));
            return responseContent.trim();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }
    /**
     *
     * Description：上传文件
     * @param requestUrl
     * @param requestMethod
     * @param output
     * @return
     * @throws IOException
     * @return StringBuffer
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
    public static HttpsURLConnection httpsUploadFile(String requestUrl,
                                                     Map<String, String> parameters,
                                                     String attachment,String filename,byte [] data) throws IOException {
        /**
         * 类 URL 表示一个统一资源定位符，它是指向互联网“资源的指针”。资源可以是简单的文件或者目录也可以是对更为复杂的而对象的引用。
         */
        HttpsURLConnection connection = null;
        try {

            URL url = new URL(requestUrl);

            connection = (HttpsURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            // 定义数据分隔线
            String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符

            // 设置请求头数据
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            boundary = "--" + boundary;

            StringBuffer params = new StringBuffer();
            if (parameters != null) {
                for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
                    String name = iter.next();
                    String value = parameters.get(name);
                    params.append(boundary + "\r\n");
                    params.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
                    params.append(value);
                    params.append("\r\n");
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"" + attachment + "\";  filename=\"" + filename
                    + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] fileDiv = sb.toString().getBytes();

            byte[] endData = ("\r\n" + boundary + "--\r\n").getBytes();
            byte[] ps = params.toString().getBytes();

            OutputStream os = connection.getOutputStream();
            os.write(ps);
            os.write(fileDiv);
            os.write(data);
            os.write(endData);

            os.flush();
            os.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return connection;
    }


    /**
     * 发送post请求
     *
     * @param reqUrl
     * @param parameters
     * @return
     */
    public static String doHttpsPost(String reqUrl, String parameters) {
        HttpsURLConnection urlConn = null;
        try {
            urlConn = _sendHttpsPost(reqUrl, parameters);
            String responseContent = _getHttpsContent(urlConn);
            return responseContent.trim();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }
    private static HttpsURLConnection _sendHttpsPost(String reqUrl,
                                                     String parameters) {
        HttpsURLConnection urlConn = null;
        try {
            //String params = urlEncode(parameters);
            URL url = new URL(reqUrl);
            urlConn = (HttpsURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(10000);// （单位：毫秒）jdk
            urlConn.setReadTimeout(10000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlConn.setDoOutput(true);
            byte[] b = parameters.getBytes();
            urlConn.getOutputStream().write(b, 0, b.length);
            urlConn.getOutputStream().flush();
            urlConn.getOutputStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }
    private static String _getHttpsContent(HttpsURLConnection urlConn) {
        InputStream in = null;
        BufferedReader rd = null;
        String responseContent = null;
        try {
            in = urlConn.getInputStream();
            rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            return responseContent;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (rd != null) {
                    rd.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage(), e2);
            }
        }
    }

}
