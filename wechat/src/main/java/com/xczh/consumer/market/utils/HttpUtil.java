package com.xczh.consumer.market.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

/**
 * 和HTTP相关的操作
 * 
 * @author Alex Wang
 *
 */
public class HttpUtil {

	//判断手机型号用
	static Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");   
	/**
	 * 发送post请求。
	 * 
	 * @param reqUrl
	 * @param parameters
	 *            请求的参数。
	 * @return
	 */
	public static String sendPostRequest(String reqUrl, Map<String, String> parameters) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendPost(reqUrl, parameters);
			String responseContent = getContent(urlConn);
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
	 *            除了文件之外的参数。
	 * @param fileParamName
	 *            文件参数名字，后台获取参数时使用的名字。
	 * @param filename
	 *            上传的文件名
	 * @param contentType
	 *            文件类型
	 * @param data
	 *            文件内容
	 * @return
	 */
	public static String uploadFile(String reqUrl, Map<String, String> parameters, String fileParamName,
			String filename, String contentType, byte[] data) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendFormdata(reqUrl, parameters, fileParamName, filename, contentType, data);
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

	private static HttpURLConnection sendFormdata(String reqUrl, Map<String, String> parameters, String fileParamName,
			String filename, String contentType, byte[] data) {
		HttpURLConnection urlConn = null;
		try {
			URL url = new URL(reqUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(15000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(15000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);

			urlConn.setRequestProperty("Connection", "keep-alive");

			String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
			urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			boundary = "--" + boundary;
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
					String name = iter.next();
					String value = parameters.get(name);
					params.append(boundary + "\r\n");
					params.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
					// params.append(URLEncoder.encode(value, "UTF-8"));
					params.append(value);
					params.append("\r\n");
				}
			}

			StringBuilder sb = new StringBuilder();
			// sb.append("\r\n");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\"" + filename
					+ "\"\r\n");
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

	private static String getContent(HttpURLConnection urlConn) {
		try {
			String responseContent = null;
			InputStream in = urlConn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
			return responseContent;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static byte[] getBytes(HttpURLConnection urlConn) {
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

	public static HttpURLConnection sendPost(String reqUrl, Map<String, String> parameters) {
		HttpURLConnection urlConn = null;
		try {
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				boolean isFirst = true;
				for (Map.Entry<String, String> param : parameters.entrySet()) {
					if (!isFirst) {
						params.append("&");
					} else {
						isFirst = false;
					}
					String name = param.getKey();
					String value = param.getValue();
					if (value == null || value.trim().length() < 1) {
						continue;
					}
					params.append(name + "=");
					params.append(URLEncoder.encode(value, "UTF-8"));
				}
			}

			URL url = new URL(reqUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(15000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(15000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			urlConn.getOutputStream().write(b, 0, b.length);
			urlConn.getOutputStream().flush();
			urlConn.getOutputStream().close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return urlConn;
	}

	/**
	 * 发送GET请求
	 * 
	 * @param link
	 * @param charset
	 * @return
	 */
	public static String sendGetRequest(String link, String charset) {
		byte[] buf = sendGetRequestBytes(link);
		try {
			return new String(buf, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static String sendDataRequest(String link,String contentType,byte[] b) {
		byte[] buf = sendGetRequestBytes(link,contentType,b);
		try {
			return new String(buf, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 发送GET请求
	 * 
	 * @param link
	 * @param charset
	 * @return
	 */
	public static byte[] sendGetRequestBytes(String link) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(link);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Java_BXG");
			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int i = 0; (i = in.read(buf)) > 0;) {
				out.write(buf, 0, i);
			}
			out.flush();
			out.close();
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	public static byte[] sendGetRequestBytes(String link,String contentType,byte[] b) {
		HttpURLConnection urlConn = null;
		try {
			URL url = new URL(link);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestProperty("Content-type", "application/xml");
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(15000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(15000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);
			urlConn.getOutputStream().write(b, 0, b.length);
			urlConn.getOutputStream().flush();
			urlConn.getOutputStream().close();
			BufferedInputStream in = new BufferedInputStream(urlConn.getInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int i = 0; (i = in.read(buf)) > 0;) {
				out.write(buf, 0, i);
			}
			out.flush();
			out.close();
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
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
	 * 发送一个get请求，用UTF-8编码解析返回的字符串。
	 * 
	 * @param link
	 * @return
	 */
	public static String sendGetRequest(String link) {
		return sendGetRequest(link, "UTF-8");
	}

	/**
	 * 用get方式请求一个参数，并将返回结果转换成一个整数。
	 * 
	 * @param link
	 * @return
	 */
	public static int getIntResponse(String link) {
		String str = sendGetRequest(link);
		return Integer.parseInt(str.trim());
	}
	
	
	/**
	 * =============================以下方法增加了cookie与header===============================
	 */
	
	/**
	 * 
	 * @param link
	 * @param charset
	 * @param cookies
	 * @return
	 */
	public static String sendGetRequestWithCookies(String link, String charset,Map<String, String> cookies) {
		byte[] buf = sendGetRequestBytesWithCookieHeader(link, cookies, null);
		try {
			return new String(buf, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static String sendPostRequestWithCookies(String reqUrl, Map<String, String> parameters,Map<String, String> cookies) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendPostWithCookieHeader(reqUrl, parameters,cookies,null);
			String responseContent = getContent(urlConn);
			return responseContent.trim();
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
	}
	
	public static String uploadFileWithCookieHeader(String reqUrl, Map<String, String> parameters, String fileParamName,
			String filename, String contentType, byte[] data,Map<String, String> cookies,Map<String, String> headers) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendFormdataWithCookieHeader(reqUrl, parameters, fileParamName, filename, contentType, data,cookies,headers);
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
	
	private static HttpURLConnection sendFormdataWithCookieHeader(String reqUrl, Map<String, String> parameters, String fileParamName,
			String filename, String contentType, byte[] data,Map<String, String> cookies,Map<String, String> headers) {
		HttpURLConnection urlConn = null;
		try {
			URL url = new URL(reqUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(15000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(15000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Connection", "keep-alive");
			
			setCookieHeader(urlConn,cookies,headers);

			String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
			urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			boundary = "--" + boundary;
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
					String name = iter.next();
					String value = parameters.get(name);
					params.append(boundary + "\r\n");
					params.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
					// params.append(URLEncoder.encode(value, "UTF-8"));
					params.append(value);
					params.append("\r\n");
				}
			}

			StringBuilder sb = new StringBuilder();
			// sb.append("\r\n");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\"" + filename
					+ "\"\r\n");
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

	private static HttpURLConnection sendPostWithCookieHeader(String reqUrl, Map<String, String> parameters,Map<String, String> cookies,Map<String, String> headers) {
		HttpURLConnection urlConn = null;
		try {
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				boolean isFirst = true;
				for (Map.Entry<String, String> param : parameters.entrySet()) {
					if (!isFirst) {
						params.append("&");
					} else {
						isFirst = false;
					}
					String name = param.getKey();
					String value = param.getValue();
					if (value == null || value.trim().length() < 1) {
						continue;
					}
					params.append(name + "=");
					params.append(URLEncoder.encode(value, "UTF-8"));
				}
			}

			URL url = new URL(reqUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(15000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(15000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);
			
			setCookieHeader(urlConn,cookies,headers);
			
			byte[] b = params.toString().getBytes();
			urlConn.getOutputStream().write(b, 0, b.length);
			urlConn.getOutputStream().flush();
			urlConn.getOutputStream().close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return urlConn;
	}
	
	public static byte[] sendGetRequestBytesWithCookieHeader(String link,Map<String, String> cookies,Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(link);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Java_BXG");
			
			setCookieHeader(conn,cookies,headers);
			
			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int i = 0; (i = in.read(buf)) > 0;) {
				out.write(buf, 0, i);
			}
			out.flush();
			out.close();
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static void setCookieHeader(HttpURLConnection urlConn,Map<String, String> cookies,Map<String, String> headers){
		if (cookies != null && cookies.entrySet().size() > 0) {
			String cookie = "";
			for (String key : cookies.keySet()) {
				if (key != null && cookies.get(key) != null) {
					cookie+=(";"+key+"="+cookies.get(key));
				}
			}
			if (cookie.length() > 0) {
				urlConn.setRequestProperty("Cookie", cookie.substring(1));
			}
		}
		if (headers != null && headers.entrySet().size() > 0) {
			for (String key : headers.keySet()) {
				if (key != null && cookies.get(key) != null) {
					urlConn.addRequestProperty(key, headers.get(key));
				}
			}
		}
	}
	
	
	public static Map<String,String> getClientInformation(HttpServletRequest req){
		
		Map<String,String> map = new HashMap<String,String>();
		
		UserAgent userAgent = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));  
        Browser browser = userAgent.getBrowser();  
        OperatingSystem os = userAgent.getOperatingSystem();
        System.out.println("browser.getName():"+browser.getName());//浏览器名字   
        System.out.println("os.getName():"+os.getName()); //操作系统名字
       /* Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/"); 
        Matcher matcher =pattern.matcher(req.getHeader("User-Agent"));    */
        String model =getMobileType(req.getHeader("User-Agent"));  
        System.out.println("model:"+model);//手机型号    
        /*if(matcher.find()) {    
            model = matcher.group(1).trim();   
            System.out.println("model:"+model);//手机型号    
        }  
        System.out.println("IP地址:"+HttpUtil.getIpAddress(req));*/
        map.put("browserName", browser.getName());
        map.put("systemName", os.getName());
        map.put("model", model);
        map.put("ipAddress", HttpUtil.getIpAddress(req));
		return map;
	}
    /**  
     * 获取手机类型  
     * @param agent  
     * @return  
     */  
    public static String getMobileType(String agent){  
        if(agent.indexOf("iPhone") != -1){  
            return "iPhone";  
        }  
        Matcher matcher = pattern.matcher(agent);    
        String model = null;    
        if (matcher.find()) {    
            model = matcher.group(1).trim();    
            return model;  
        }    
        return "";  
    }  
	

	public static String getIpAddress(HttpServletRequest request) {

		String ipAddress = request.getHeader("x-forwarded-for");

		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknow".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();

			if ("127.0.0.1".equals(ipAddress)
					|| "0:0:0:0:0:0:0:1".equals(ipAddress)) {
				// 根据网卡获取本机配置的IP地址
				InetAddress inetAddress = null;
				try {
					inetAddress = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inetAddress.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
		if (null != ipAddress && ipAddress.length() > 15) {
			// "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	
}
