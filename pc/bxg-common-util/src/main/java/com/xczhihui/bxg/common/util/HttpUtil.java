package com.xczhihui.bxg.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * 和HTTP相关的操作
 * 
 * @author liyong
 *
 */
public class HttpUtil {

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
	
}
