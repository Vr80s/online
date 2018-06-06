package com.xczhihui.filter;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

 
/**
 * HTTP访问过滤器
 */
public class PageVisitFilter implements Filter {
 
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageVisitFilter.class);
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	//性能监控
    	long startTime = System.currentTimeMillis();
    			
    	HttpServletRequest request = (HttpServletRequest)req;
    	HttpServletResponse response = (HttpServletResponse)res;
    	
    	String uri = request.getRequestURI();
    	String ext = FilenameUtils.getExtension(uri);
    	
//    	LOGGER.info("欢迎欢迎，静态资源："+ext);
    	try{
//			这里的配置不让有浏览器缓存，不能这样的    		
//			response.setHeader("Pragma", "No-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setDateHeader("Expires", -1);
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
//			response.setHeader("renderer", "webkit"); 
//			response.setHeader("viewport", "width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0 user-scalable=no"); 
	    	if(isGZipEncoding(request)){
	    		//需要过滤的扩展名：.htm,.html,.jsp,.js,.ajax,.css
	    		String gzippPattern=",.htm,.html,.jsp,.js,.css,";
	    		if(StringUtils.indexOf(gzippPattern, ",."+ext+",")!=-1){
	                BufferedResponse gzipResponse = new BufferedResponse(response);
	                
	                chain.doFilter(request, gzipResponse);
	                byte[] srcData = gzipResponse.getResponseData();
	                byte[] outData = null;
	                if(srcData.length > 512){
	                	
						//byte[] gzipData = ZipUtil.toGzipBytes(srcData);
						ByteArrayOutputStream bout = new ByteArrayOutputStream();
				        //压缩输出流中的数据
				        GZIPOutputStream gout = new GZIPOutputStream(bout);
				        gout.write(srcData);
				        gout.close();

				        byte gzip[] = bout.toByteArray();
						
						response.addHeader("Content-Encoding", "gzip");
						response.setContentLength(gzip.length);
						outData = gzip;
	                } else {
	                	outData = srcData;
	                }
	                ServletOutputStream output = response.getOutputStream();
	                output.write(outData);
	                output.flush();    			
	    		} else {
	    			chain.doFilter(request, response);
	    		}
	    		return;
	    	}
	    	
	        chain.doFilter(request, response);
    	}catch(Exception e){
        
    	}finally{
    		//AcwsMonitorLog.warnHttpVisit(startTime, request);
    	}
    }
 
    @Override
    public void destroy() {
    }
    
    /**
     * 判断浏览器是否支持GZIP
     * @param request
     * @return 
     */
    private boolean isGZipEncoding(HttpServletRequest request){
      boolean flag=false;
      String encoding=request.getHeader("Accept-Encoding");
      if(encoding.indexOf("gzip")!=-1){
        flag=true;
      }
      return flag;
    } 
}
