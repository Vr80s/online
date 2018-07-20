package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.StringLegalUtil;
import com.xczhihui.common.util.enums.DoctorPostsType;
import com.xczhihui.medical.common.bean.PictureSpecification;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsLikeMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.*;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.utils.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description：医师动态 服务实现类
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:35
 **/
@Service
public class MedicalDoctorPostsServiceImpl extends ServiceImpl<MedicalDoctorPostsMapper, MedicalDoctorPosts> implements IMedicalDoctorPostsService {

    @Autowired
    private MedicalDoctorPostsMapper medicalDoctorPostsMapper;
    @Autowired
    private MedicalDoctorPostsLikeMapper medicalDoctorPostsLikeMapper;
    @Autowired
    private IMedicalDoctorPostsCommentService medicalDoctorPostsCommentService;
    @Autowired
    private IMedicalDoctorAccountService medicalDoctorAccountService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;

    @Override
    public Page<MedicalDoctorPosts> selectMedicalDoctorPostsPage(Page<MedicalDoctorPosts> page, Integer type, String doctorId, String accountId) {
        List<MedicalDoctorPosts> list = medicalDoctorPostsMapper.selectMedicalDoctorPostsPage(page, type, doctorId);
        //评论列表和点赞列表
        list.forEach(medicalDoctorPosts -> {
            //去除文章中的标签
            medicalDoctorPosts.setArticleContent(HtmlUtil.getTextFromHtml(medicalDoctorPosts.getArticleContent()));
            medicalDoctorPosts.setContent(HtmlUtil.getTextFromHtml(medicalDoctorPosts.getContent()));
            Integer postsId = medicalDoctorPosts.getId();
            List<MedicalDoctorPostsComment> commentList = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId, accountId);
            List<MedicalDoctorPostsLike> likeList = medicalDoctorPostsLikeMapper.getMedicalDoctorPostsLikeList(postsId);
            likeList.forEach(medicalDoctorPostsLike -> {
                String userId = medicalDoctorPostsLike.getUserId();
                if (userId.equals(accountId)) {
                    medicalDoctorPosts.setPraise(true);
                }
                medicalDoctorPostsLike.setUserName(StringLegalUtil.isPhoneLegal(medicalDoctorPostsLike.getUserName()));
            });
            medicalDoctorPosts.setDoctorPostsCommentList(commentList);
            medicalDoctorPosts.setDoctorPostsLikeList(likeList);
            Date d = medicalDoctorPosts.getCreateTime();
            Date currentDate = new Date();
            Date createTime = medicalDoctorPosts.getCreateTime();
            long second = (currentDate.getTime() - d.getTime())/1000;
            long min = (currentDate.getTime() - d.getTime())/1000/60+1;
            long hour = (currentDate.getTime() - d.getTime())/1000/60/60+1;
            if(second<60){
                medicalDoctorPosts.setDateStr("刚刚");
            } else if (min<=60){
                medicalDoctorPosts.setDateStr(min+"分钟前");
            } else if (hour<=12){
                medicalDoctorPosts.setDateStr(hour+"小时前");
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateString = formatter.format(createTime);
                medicalDoctorPosts.setDateStr(dateString);
            }

            //封装图片
            if(medicalDoctorPosts.getPictures() != null && !medicalDoctorPosts.getPictures().equals("")){
                String imgStr[] =medicalDoctorPosts.getPictures().split("@#\\$%&\\*!");
                List<PictureSpecification> psList = new ArrayList<>();
                for(int i=0;i<imgStr.length;i++){
                    PictureSpecification ps = new PictureSpecification();
                    Map<String, String> m = urlSplit(imgStr[i]);
                    ps.setImgUrl(UrlPage(imgStr[i]));
                    ps.setWidth(Integer.parseInt(m.get("w")));
                    ps.setHeight(Integer.parseInt(m.get("h")));
                    psList.add(ps);
                }
                medicalDoctorPosts.setImgStr(psList);
            }
        });
        page.setRecords(list);
        return page;
    }

    /**
     * 解析出url请求的路径，包括页面
     * @param strURL url地址
     * @return url路径
     */
    public static String UrlPage(String strURL)
    {
        String strPage=null;
        String[] arrSplit=null;

        strURL=strURL.trim().toLowerCase();

        arrSplit=strURL.split("[?]");
        if(strURL.length()>0)
        {
            if(arrSplit.length>1)
            {
                if(arrSplit[0]!=null)
                {
                    strPage=arrSplit[0];
                }
            }
        }

        return strPage;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String TruncateUrlPage(String strURL){
        String strAllParam=null;
        String[] arrSplit=null;
        strURL=strURL.trim().toLowerCase();
        arrSplit=strURL.split("[?]");
        if(strURL.length()>1){
            if(arrSplit.length>1){
                for (int i=1;i<arrSplit.length;i++){
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL){
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit=null;
        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null){
            return mapRequest;
        }
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit){
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");
            //解析出键值
            if(arrSplitEqual.length>1){
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            }else{
                if(arrSplitEqual[0]!=""){
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    @Override
    public void addMedicalDoctorPosts(MedicalDoctorPosts medicalDoctorPosts) {
        medicalDoctorPostsMapper.addMedicalDoctorPosts(medicalDoctorPosts);
    }

    @Override
    public void updateMedicalDoctorPosts(MedicalDoctorPosts medicalDoctorPosts) {
        medicalDoctorPostsMapper.updateMedicalDoctorPosts(medicalDoctorPosts);
    }

    @Override
    public void deleteMedicalDoctorPosts(Integer id) {
        medicalDoctorPostsMapper.deleteMedicalDoctorPosts(id);
    }

    @Override
    public void updateStickMedicalDoctorPosts(Integer id, Boolean stick) {
        medicalDoctorPostsMapper.updateStickMedicalDoctorPosts(id, stick);
    }

    @Override
    public MedicalDoctorPosts getMedicalDoctorPostsById(Integer id) {
        MedicalDoctorPosts mdp = medicalDoctorPostsMapper.getMedicalDoctorPostsById(id);
        return mdp;
    }

    @Override
    public List<MedicalDoctorPosts> getMedicalDoctorPostsByCourseId(Integer courseId) {
        return medicalDoctorPostsMapper.getMedicalDoctorPostsByCourseId(courseId);
    }

    @Override
    public void addDoctorPosts(String userId, Integer courseId, Integer articleId, String courseName, String subtitle) {
        MedicalDoctorAccount mha = medicalDoctorAccountService.getByUserId(userId);
        if(mha != null){
            MedicalDoctorPosts mdp = new MedicalDoctorPosts();
            if(courseId != null){
                if(subtitle == null || subtitle.equals("")){
                    mdp.setContent(courseName);
                }else {
                    mdp.setContent(courseName+","+subtitle);
                }
                mdp.setType(DoctorPostsType.COURSEPOSTS.getCode());
                mdp.setTitle(courseName);
                mdp.setDoctorId(mha.getDoctorId());
                mdp.setCourseId(courseId);
                addMedicalDoctorPosts(mdp);
            } else {
                String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
                OeBxsArticleVO oba = medicalDoctorArticleService.getSpecialColumn(articleId.toString());
                mdp.setType(DoctorPostsType.ARTICLEPOSTS.getCode());
                if(oba.getTypeId().equals("8")){
                    //截取医案
                    String htmlText = HtmlUtil.delHTMLTag(oba.getContent());
                    if(htmlText.length()>100){
                        mdp.setContent(htmlText.substring(0,100)+"...");
                    } else {
                        mdp.setContent(htmlText);
                    }
                }else {
                    mdp.setContent(oba.getTitle());
                }
                mdp.setDoctorId(doctorId);
                mdp.setArticleId(articleId);
                mdp.setArticleContent(oba.getContent());
                mdp.setArticleImgPath(oba.getImgPath());
                mdp.setArticleTitle(oba.getTitle());
                addMedicalDoctorPosts(mdp);
            }

        }
    }

}
