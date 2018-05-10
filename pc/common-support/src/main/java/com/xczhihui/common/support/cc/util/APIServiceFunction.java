package com.xczhihui.common.support.cc.util;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 * 功能：api接口公用函数
 * 详细：该页面是请求接口生成参数字符串的公用函数处理文件，不需要修改
 * 版本：1.0
 * 修改日期：2010-12-20
 * 作者：chu
 */
public class APIServiceFunction {

	/**
	 * 功能：将一个Map按照Key字母升序构成一个QueryString. 并且加入时间混淆的hash串。
	 * 
	 * @param queryMap
	 *            query内容
	 * @param time
	 *            加密时候，为当前时间；解密时，为从querystring得到的时间；
	 * @param salt
	 *            加密salt
	 * @return
	 */
	public static String createHashedQueryString(Map<String, String> queryMap,
			long time, String salt) {

		Map<String, String> map = new TreeMap<String, String>(queryMap);
		String qs = createQueryString(map);
		if (qs == null) {
			return null;
		}
		time = time / 1000;

		String hash = Md5Encrypt.md5(String.format("%s&time=%d&salt=%s", qs,
				time, salt));
		hash = hash.toLowerCase();
		String htqs = String.format("%s&time=%d&hash=%s", qs, time, hash);

		return htqs;
	}

	/**
	 * 功能：用一个Map生成一个QueryString，参数的顺序不可预知。
	 * 
	 * @param queryMap
	 * @return
	 */
	public static String createQueryString(Map<String, String> queryMap) {

		if (queryMap == null) {
			return null;
		}

		try {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : queryMap.entrySet()) {
				if (entry.getValue() == null) {
					continue;
				}
				String key = entry.getKey().trim();
				String value = URLEncoder.encode(entry.getValue().trim(),
						"utf-8");
				sb.append(String.format("%s=%s&", key, value));
			}
			return sb.substring(0, sb.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 功能：远程触发接口，返回响应结果
	 * 
	 * @param triggerURL
	 * @return
	 */
	public static String HttpRetrieve(String triggerURL) {
		StringBuffer document = new StringBuffer();
		try {
			URL url = new URL(triggerURL);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(),"utf-8"));
//					conn.getInputStream(),"utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				document.append(line);
			}
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document.toString();
	}

	//获取文件的MD5值
	public static String getMd5File(File file){
		String md5 = null;
		try {
			FileInputStream fis= new FileInputStream(file);
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
			IOUtils.closeQuietly(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return md5;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}


	public static byte[] readChunk(File file) throws IOException {
		if(file == null) {
			throw new IOException("The file does not exist");
		}
		long fileLength = file.length();
		long chunkStart=0;
		if(chunkStart >= fileLength) {
			throw new IOException("Start position > file length");
		}
		RandomAccessFile accessFile = null;
		try {
			long chunkEnd = file.length()-1;
			int chunksLen = (int) (chunkEnd - chunkStart + 1);
			byte[] chunks = new byte[chunksLen];
			accessFile = new RandomAccessFile(file, "r");
			accessFile.seek(chunkStart);
			int readLength = accessFile.read(chunks, 0, chunksLen);
			System.out.println("read Length: " + readLength);
			accessFile.close();
			return chunks;
		} finally {
			if(accessFile != null) {
				try {
					accessFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/* url为/servlet/uploadchunk?ccvid=&format= */
	/* chunkStart为chunk起始位置*/
	/* chunkEnd为chunk结束位置*/
	/* file为文件*/
	/* bufferOut为实际文件输出二进制内容*/

	public static String uploadchunk(String url, int chunkStart, int chunkEnd, File file) {
		byte[] bufferOut = null;
		try {
			bufferOut = readChunk(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(bufferOut == null) {
			System.out.println("---------------read file chunk error-----------------");
			return "read file error";
		}
		HttpURLConnection conn = null;
		try {
			String BOUNDARY = "---------CCHTTPAPIFormBoundaryEEXX" + new Random().nextInt(65536); // 定义数据分隔线
			URL openUrl = new URL(url);
			conn = (HttpURLConnection)openUrl.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// content-range
			conn.setRequestProperty("Content-Range", "bytes " + chunkStart + "-" + chunkEnd + "/" + file.length());
			System.out.println("bytes " + chunkStart + "-" + chunkEnd + "/" + file.length());

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			StringBuilder sb = new StringBuilder();
			sb.append("--").append(BOUNDARY).append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file" + file.getName() + "\";filename=\"" + file.getName()
					+ "\"\r\n");
			sb.append("Content-Type: application/octet-stream\r\n");
			sb.append("\r\n");
			byte[] data = sb.toString().getBytes();
			out.write(data);
			out.write(bufferOut);
			out.write("\r\n".getBytes());
			// 定义最后数据分隔线
			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();
			out.write(end_data);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer resultBuf = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				resultBuf.append(line);
			}
			reader.close();
			conn.disconnect();
			return resultBuf.toString();
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return null;
	}

}
