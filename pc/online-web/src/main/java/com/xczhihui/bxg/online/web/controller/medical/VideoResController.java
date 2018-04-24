package com.xczhihui.bxg.online.web.controller.medical;


import com.alibaba.fastjson.JSONObject;
import com.xczhihui.common.support.cc.config.Config;
import com.xczhihui.common.support.cc.util.APIServiceFunction;
import com.xczhihui.common.support.cc.util.Md5Encrypt;
import com.xczhihui.common.support.config.OnlineConfig;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.VideoResService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 5:06 2018/1/20 0020<br>
 */
@RestController
@RequestMapping("videoRes")
public class VideoResController extends AbstractController{

    @Autowired
    private VideoResService videoResService;
    @Autowired
    private OnlineConfig onlineConfig;

    private static String categoryid = "7C85F5F633435474";

    /**
     * 获得上传地址
     * @param request
     * @return
     */
    @RequestMapping(value = "getUploadUrl", method = RequestMethod.GET)
    public String getUploadUrl(HttpServletRequest request,String title) {
        OnlineUser loginUser = getOnlineUser(request);
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("userid", onlineConfig.ccuserId);
        paramsMap.put("title", title);
        paramsMap.put("description", TimeUtil.getCCtitleTimeStr());
        paramsMap.put("tag", loginUser.getLoginName());
        paramsMap.put("categoryid", categoryid);
        long time = System.currentTimeMillis();
        String url = Config.api_updateVideo + "?" + APIServiceFunction.createHashedQueryString(paramsMap, time, onlineConfig.ccApiKey);
        url += "&categoryid="+categoryid;
        return url;
    }

    /**
     * 视频处理完成的回调
     *
     * @param videoid
     * @param status
     * @param duration
     * @param image
     * @throws IOException
     */
    @RequestMapping(value = "uploadSuccessCallback", method = RequestMethod.GET)
    public void uploadSuccessCallback(HttpServletResponse res, String duration, String image, String status,
                                      String videoid, String time, String hash) throws IOException {
        videoResService.uploadSuccessCallback(duration, image, status, videoid, time, hash);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/xml; charset=utf-8");
        res.getWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><video>OK</video>");
    }


    /**
     * Description：cc上传文件
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/4/9 20:23
     **/
    @RequestMapping(value = "/uploadFile",method= RequestMethod.POST)
    public ResponseObject uploadFile(@RequestParam("file") MultipartFile mfile,String fileSize,
                                     String filemd5,String fileName,String first,String ccid,
                                     String metaUrl,String chunkUrl,Integer start){


        CommonsMultipartFile cf= (CommonsMultipartFile)mfile;
        DiskFileItem fi = (DiskFileItem)cf.getFileItem();

        File file = fi.getStoreLocation();
        String videoid="";
        String servicetype="";
        String metaurl="";
        String chunkurl="";
        if(first.equals("1")){
            //创建视频上传信息
            Map<String, String> treeMap = new TreeMap<String, String>();
            //查询参数输入
            String key="K45btKhytR527yfTAjEp6z4fb3ajgu66";
            treeMap.put("userid", "B5E673E55C702C42");
            treeMap.put("title", fileName);
            treeMap.put("description", fileName);
            treeMap.put("filename", fileName);
            treeMap.put("filesize", fileSize);
            treeMap.put("categoryid", categoryid);
            treeMap.put("format", "json");
            String qs = APIServiceFunction.createQueryString(treeMap);
            //生成时间片
            long time = new Date().getTime() / 1000;
            //生成HASH码值
            String hash = Md5Encrypt.md5(String.format("%s&time=%s&salt=%s", qs, time, key));

            String str =APIServiceFunction.sendGet("http://spark.bokecc.com/api/video/create",
                    qs+"&time="+time+"&hash="+hash);

            JSONObject strJson = JSONObject.parseObject(str);
            String uploadinfo = strJson.get("uploadinfo").toString();
            JSONObject uploadinfoJson = JSONObject.parseObject(uploadinfo);
            videoid = uploadinfoJson.get("videoid").toString();
            servicetype = uploadinfoJson.get("servicetype").toString();
            metaurl = uploadinfoJson.get("metaurl").toString();
            chunkurl = uploadinfoJson.get("chunkurl").toString();

            //创建视频上传信息
            String up = uploadmeta(videoid,first,fileName,fileSize,servicetype,metaurl,filemd5);
            JSONObject upJson = JSONObject.parseObject(up);
            String upResultinfo = upJson.get("result").toString();
            String upMsginfo = upJson.get("msg").toString();
            if(!upResultinfo.equals("0")){
                return ResponseObject.newErrorResponseObject(upMsginfo);
            }
            //上传视频文件块CHUNK
            String result =APIServiceFunction.uploadchunk(chunkurl+"?ccvid="+videoid+"&format=json", 0, (Integer.parseInt(String.valueOf(file.length())) - 1), file);

            JSONObject resultJson = JSONObject.parseObject(result);
            String resultinfo = resultJson.get("result").toString();
            String msginfo = resultJson.get("msg").toString();
            if(resultinfo.equals("0")){
                Object[]obj=new Object[3];
                obj[0]=videoid;
                obj[1]=metaurl;
                obj[2]=chunkurl;
                return ResponseObject.newSuccessResponseObject(obj);
            }else {
                return ResponseObject.newErrorResponseObject(msginfo);
            }
        }else {
            //创建视频上传信息
            String uploadmeta = uploadmeta(ccid,first,fileName,fileSize,servicetype,metaUrl,"");
            JSONObject uploadmetaJson = JSONObject.parseObject(uploadmeta);
            String receivedinfo = uploadmetaJson.get("received").toString();
            String uploadmetaResultinfo = uploadmetaJson.get("result").toString();
            String uploadmetaMsginfo = uploadmetaJson.get("msg").toString();
            if(!uploadmetaResultinfo.equals("0")){
                return ResponseObject.newErrorResponseObject(uploadmetaMsginfo);
            }
            //上传视频文件块CHUNK
            String result =APIServiceFunction.uploadchunk(chunkUrl+"?ccvid="+ccid+"&format=json", start, (int)(file.length()+start-1), file);
            JSONObject resultJson = JSONObject.parseObject(result);
            String resultinfo = resultJson.get("result").toString();
            String msginfo = resultJson.get("msg").toString();
            if(resultinfo.equals("0")){
                Object[]obj=new Object[3];
                obj[0]=ccid;
                obj[1]=metaUrl;
                obj[2]=chunkUrl;
                return ResponseObject.newSuccessResponseObject(obj);
            }else {
                return ResponseObject.newErrorResponseObject(msginfo);
            }
        }

    }


    //上传视频META信息
    public String uploadmeta(String videoid,String first,String filename,String fileSize,String servicetype,String metaurl,String filemd5){
        Map<String, String> treeMap1 = new TreeMap<String, String>();
        String key="K45btKhytR527yfTAjEp6z4fb3ajgu66";

        //第二步
        treeMap1.put("ccvid", videoid);
        treeMap1.put("first", first);
        if(!filemd5.equals("")){
            treeMap1.put("uid", "B5E673E55C702C42");
            treeMap1.put("filename", filename);
            treeMap1.put("filesize",fileSize);
            treeMap1.put("servicetype", servicetype);
            treeMap1.put("md5", filemd5);
        }

        treeMap1.put("format", "json");

        String qs1 = APIServiceFunction.createQueryString(treeMap1);
        //生成时间片
        long time1 = new Date().getTime() / 1000;
        //生成HASH码值
        String hash1 = Md5Encrypt.md5(String.format("%s&time=%s&salt=%s", qs1, time1, key));
        String str1=APIServiceFunction.sendGet(metaurl,
                qs1+"&time="+time1+"&hash="+hash1);
        return str1;
    }





}
