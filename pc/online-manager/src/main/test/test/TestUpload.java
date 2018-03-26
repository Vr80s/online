package test;

public class TestUpload {

	
    	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access
	    public static final String UPLOAD_IMAGE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";// 上传多媒体文件
	    public static final String UPLOAD_FODDER_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	    public static final String APP_ID = "wxa549b28c24cf341e";
	    public static final String SECRET = "78d8a8cd7a4fa700142d06b96bf44a37";



	    public static void main(String[] args) throws Exception
	    {
	    	
//	        String accessToken = getToken(GET_TOKEN_URL, APP_ID, SECRET);// 获取token在微信接口之一中获取
//	        if (accessToken != null)// token成功获取
//	        {
//	            System.out.println(accessToken);
//	            File file = new File("f:" + File.separator + "2000.JPG"); // 获取本地文件
//	            String id = uploadImage(UPLOAD_IMAGE_URL, accessToken, "image",
//	                    file);// java微信接口之三—上传多媒体文件 可获取
//	            if (id != null)
//	            { 
//	                //构造数据
//	                Map map = new HashMap();
//	                map.put("thumb_media_id", id);
//	                map.put("author", "wxx");
//	                map.put("title", "标题");
//	                map.put("content", "测试fdsfdsfsdfssfdsfsdfsdfs");
//	                map.put("digest", "digest");
//	                map.put("show_cover_pic", "0");
//
//	                Map map2 = new HashMap();
//	                map2.put("thumb_media_id", id);
//	                map2.put("author", "wxx");
//	                map2.put("content_source_url", "www.google.com");
//	                map2.put("title", "标题");
//	                map2.put("content", "测试fdsfdsfsdfssfdsfsdfsdfs");
//	                map2.put("digest", "digest");
//
//	                Map map3 = new HashMap();
//	                List<Map> list = new ArrayList<Map>();
//	                list.add(map);
//	                list.add(map2);
//	                map3.put("articles", list);
//
//	                Gson gson = new Gson();
//	                String result = gson.toJson(map3);//转换成json数据格式
//	                String mediaId = uploadFodder(UPLOAD_FODDER_URL, accessToken,
//	                        result);
//	                if (mediaId != null)
//	                {
//	                    System.out.println(mediaId);
//	                }
//	            }
//	        }
	    	
	    	
	    }
	
	
	
}
