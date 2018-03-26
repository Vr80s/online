package com.xczh.consumer.market.wxpay.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.wxpay.IServiceRequest;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.*;
import java.util.Iterator;
import java.util.Map;

public class HttpsRequest implements IServiceRequest {

    public interface ResultListener {
    	
        public void onConnectionPoolTimeoutError();
        
    }

    private static Log log = new Log(LoggerFactory.getLogger(HttpsRequest.class));

    //表示请求器是否已经做了初始化工作
    private boolean hasInit = false;

    //连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    public HttpsRequest() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        init();
    }

    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        httpClient = HttpClients.createDefault();
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    @Override
    public String sendPost(String url,
                           Object xmlObj)
    		throws IOException, 
    		KeyStoreException, 
    		UnrecoverableKeyException,
    		NoSuchAlgorithmException, 
    		KeyManagementException {

        if (!hasInit) {
            init();
        }

        String result = null;
        HttpPost httpPost = new HttpPost(url);

        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        Util.log("API，POST过去的数据是：");
        Util.log(postDataXML);
        
        System.out.println("HttpsRequest->sendPost->0\r\n\t"+postDataXML);
        postDataXML = postDataXML.replace("com.xczh.consumer.market.wxpay.entity.PayInfo", "xml");
        
        System.out.println("HttpsRequest->sendPost->0\r\n\t"+postDataXML);
//        
        System.out.println("HttpsRequest->sendPost->1\r\n\t"+postDataXML);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);
        Util.log("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
            log.e("http get throw ConnectionPoolTimeoutException(wait time out)");
        } catch (ConnectTimeoutException e) {
            log.e("http get throw ConnectTimeoutException");
        } catch (SocketTimeoutException e) {
            log.e("http get throw SocketTimeoutException");
        } catch (Exception e) {
            log.e("http get throw Exception");
        } finally {
            httpPost.abort();
        }

        return result;
    }

	private static final String APPLICATION_JSON = "application/json";    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json;charset=UTF-8";
    
    /**
     * 通过Https往API post json数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String sendPost2(String url, 
    		Object jsonObj) 
    		throws IOException, 
    		KeyStoreException, 
    		UnrecoverableKeyException,
    		NoSuchAlgorithmException, 
    		KeyManagementException {

        if (!hasInit) {
            init();
        }

        String result = null;
        HttpPost httpPost = new HttpPost(url);

        /*??
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
	
        Util.log("API，POST过去的数据是：");
        Util.log(postDataXML);

        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
		??*/
        
        String sParam = jsonObj.toString();
		if (sParam.length()>0) {
			//??sParam = sParam.substring(0,sParam.length()-1);
			//??sParam = "{" + sParam + "}";					
			StringEntity se = new StringEntity(sParam,"UTF-8");
	        se.setContentType(CONTENT_TYPE_TEXT_JSON);//se.setContentEncoding("UTF-8");
	        se.setContentEncoding(new org.apache.http.message.BasicHeader(org.apache.http.protocol.HTTP.CONTENT_TYPE, APPLICATION_JSON));
	        httpPost.setEntity(se);					
		}
		System.out.println("sendPost2=\r\n" + sParam);
		
        Util.log("API，POST过去的数据是：");
        Util.log(sParam);
		
        //设置请求器的配置
        httpPost.setConfig(requestConfig);
        Util.log("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
            log.e("http get throw ConnectionPoolTimeoutException(wait time out)");
        } catch (ConnectTimeoutException e) {
            log.e("http get throw ConnectTimeoutException");
        } catch (SocketTimeoutException e) {
            log.e("http get throw SocketTimeoutException");
        } catch (Exception e) {
            log.e("http get throw Exception");
        } finally {
            httpPost.abort();
        }

        return result;
    }
    
    /**
     * 发起请求
     * @author 周铭株
     * at 2016年6月23日 下午1:32:25
     * @param requestUrl
     * @param requestMethod
     * @param output
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     * @throws MalformedURLException
     * @throws IOException
     * @throws ProtocolException
     * @throws UnsupportedEncodingException
     */
    public static StringBuffer httpsRequest(String requestUrl, 
    		String requestMethod, 
    		String output)
			throws NoSuchAlgorithmException, 
			NoSuchProviderException, 
			KeyManagementException, 
			MalformedURLException,
			IOException, 
			ProtocolException, 
			UnsupportedEncodingException {
    	
		URL url = new URL(requestUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod(requestMethod);

		if (null != output) {
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(output.getBytes());
			outputStream.close();
		}
		
		// 从输入流读取返回内容
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
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

    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        requestConfig = requestConfig;
    }
    
    public static String sslPost(String url,String data){
    	
        StringBuffer message = new StringBuffer();
        
        try {
        	String apiclient_cert= "";
        	if(true) {
        		String strRootDir = System.getProperty("user.dir").replace("bin", "webapps");		
        		if(strRootDir.charAt(strRootDir.length()-1) != File.separatorChar) {
                    strRootDir += File.separator;
                }
        		System.out.println("sslPost->strRootDir->" + strRootDir );
        		String path=strRootDir + "ROOT/WEB-INF/classes" + File.separator + "config.properties";
        		System.out.println("sslPost->path->" + path );
        		ConfigUtil cfg = new ConfigUtil(path);
        		apiclient_cert= cfg.getConfig("apiclient_cert");
        		System.out.println("sslPost->apiclient_cert->" + apiclient_cert );
        	}
        	if(apiclient_cert==null || apiclient_cert.trim().length() == 0) {
                return "";
            }
        	
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(/*"D:/certs/apiclient_cert.p12"*/apiclient_cert));
            keyStore.load(instream, WxPayConst.gzh_mchid.toCharArray());
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, WxPayConst.gzh_mchid.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httpost = new HttpPost(url);

            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            System.out.println("executing request" + httpost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
            
        } catch (Exception e1) {
            e1.printStackTrace();
        } 

        return message.toString();
    }
    
    /** 
     * 发送 get请求 
     */  
    public static void get() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet("http://www.ixincheng.com/api/chapter/findChapterInfo?courseId=299&userId=2c9aec345d59c9f6015d59caa6440000");  
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                System.out.println("--------------------------------------");  
                // 打印响应状态    
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    
    /**
	 * 
	 * Description：微信https 上传文件。包括图片、视频、音频等
	 * 
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
	public static String uploadHttpsFile(String reqUrl, 
			Map<String, String> parameters,
			String fileParamName,
			String filename,
			byte[] data) {
		HttpsURLConnection urlConn = null;
		try {
			urlConn = httpsUploadFile(reqUrl,parameters, fileParamName, filename,data);
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
    
    public static void main(String[] args) {
    	get();
	}
}
