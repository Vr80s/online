package com.twinkling.stream.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.xczhihui.attachment.center.server.service.AttachmentService;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.twinkling.stream.util.IoUtil;

/**
 * FormData Uploading reserved servlet, mainly reading the request parameter and
 * its file part, stored it.
 * PS: use the `streaming api`, this will not store it in a temporary file.
 * {@link http://commons.apache.org/proper/commons-fileupload/streaming.html }
 */
public class FormDataServlet extends HttpServlet {
    private static final long serialVersionUID = -1905516389350395696L;
    static final String FILE_FIELD = "file";
    /**
     * when the has read to 10M, then flush it to the hard-disk.
     */
    public static final int BUFFER_LENGTH = 1024 * 1024 * 10;
    static final int MAX_FILE_SIZE = 1024 * 1024 * 100;
    
    @Value("${attachment.path}")
	private String rootPath = "";
	@Value("${picture.path}")
	private String picturePath = "";
	
    @Autowired
	private AttachmentService attachmentService;
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doOptions(req, resp);
        /** flash @ windows bug */
        req.setCharacterEncoding("utf8");
        
//		final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
//		final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);

        final PrintWriter writer = resp.getWriter();
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            writer.println("ERROR: It's not Multipart form.");
            return;
        }
        JSONObject json = new JSONObject();
//        long start = 0;
        boolean success = true;
        String message = "";

        ServletFileUpload upload = new ServletFileUpload();
        InputStream in = null;
        String token = null;
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String> result = new HashMap<String,String>();
        try {
            String filename = null;
            FileItemIterator iter = upload.getItemIterator(req);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                in = item.openStream();
                if (item.isFormField()) {
                    String value = Streams.asString(in);
                    if (TokenServlet.TOKEN_FIELD.equals(name)) {
                        token = value;
                        /** TODO: validate your token. */
                    }
                    params.put(name, value);
                    System.out.println(name + ":" + value);
                } else {
                    if (token == null || token.trim().length() < 1)
                        token = req.getParameter(TokenServlet.TOKEN_FIELD);
                    /** TODO: validate your token. */

                    // 这里不能保证token能有值
                    filename = item.getName();
                    if (token == null || token.trim().length() < 1)
                        token = filename;
                    
                    String base_dir = "2".equals(params.get("fileType")) ? rootPath : picturePath;
                    result = IoUtil.streaming(in, token, filename,base_dir,params.get("fileType"),params.get("projectName"));
                }
            }

            System.out.println("Form Saved : " + filename);
        } catch (FileUploadException fne) {
            success = false;
            message = "Error: " + fne.getLocalizedMessage();
        } finally {
            try {
                if (success)
                    json.put(StreamServlet.START_FIELD, result.get("size"));
				
				AttachmentType type = AttachmentType.valueOf(params.get("projectName").toUpperCase());
				
				Attachment a = null;
    			a = attachmentService.saveAttachmentOnUpload(params.get("fileType") ,params.get("userId"),
						type, params.get("Filename"), result.get("storeName"), result.get("path")+"", Long.valueOf(result.get("size")));
    			 
                if("".equals(message)){
            	    json.put(TokenServlet.SUCCESS, success);
   	                json.put(TokenServlet.MESSAGE, a.getId());
                }else{
	                json.put(TokenServlet.SUCCESS, success);
	                json.put(TokenServlet.MESSAGE, message);
                }
            } catch (JSONException e) {
            }

            writer.write(json.toString());
            IoUtil.close(in);
            IoUtil.close(writer);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
