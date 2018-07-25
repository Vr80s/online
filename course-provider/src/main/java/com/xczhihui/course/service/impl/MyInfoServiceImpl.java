package com.xczhihui.course.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.UserSex;
import com.xczhihui.course.exception.UserInfoException;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.FocusMapper;
import com.xczhihui.course.mapper.MyInfoMapper;
import com.xczhihui.course.model.OnlineUser;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.OnlineUserVO;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MyInfoServiceImpl extends ServiceImpl<MyInfoMapper, OnlineUser> implements IMyInfoService {

    @Autowired
    private MyInfoMapper myInfoMapper;
    
    @Autowired
    private FocusMapper focusMapper;
    
    @Autowired
    private CourseMapper courseMapper;
    
    
    @Override
    public List<BigDecimal> selectCollegeCourseXmbNumber(String userId) {
        List<BigDecimal> list = myInfoMapper.selectCollegeCourseXmbNumber(userId);
        return list;
    }

    @Override
    public List<Map<String, Object>> selectSettlementList(Integer pageNumber, Integer pageSize, String userId) {
        return myInfoMapper.selectSettlementList(pageNumber, pageSize, userId);
    }

    @Override
    public List<Map<String, Object>> selectWithdrawalList(Integer pageNumber, Integer pageSize, String userId) {
        return myInfoMapper.selectWithdrawalList(pageNumber, pageSize, userId);
    }

    @Override
    public void updateUserSetInfo(OnlineUserVO user) {

        if (user.getSex() != null && !UserSex.isValid(user.getSex())) {
            throw new UserInfoException("性别不合法,0 女  1男   2 未知");
        }

        //过滤掉可能出现的表情字符
        if (StringUtils.isNotBlank(user.getName())) {
            String name = user.getName();
            user.setName(name);
        }

        if (StringUtils.isNotBlank(user.getName()) && XzStringUtils.checkNickName(user.getName())
                && (user.getName().length() > 20)) {
            throw new UserInfoException("昵称最多允许输入20个字符");
        }

        if (StringUtils.isNotBlank(user.getEmail()) &&
                (user.getEmail().length() > 32 || user.getEmail().length() < 5)) {
            throw new UserInfoException("邮件长度在5-32之间");
        }

        if (StringUtils.isNotBlank(user.getEmail()) &&
                !XzStringUtils.checkEmail(user.getEmail())) {
            throw new UserInfoException("邮箱格式有误");
        }
        myInfoMapper.updateUserSetInfo(user);
    }

    @Override
    public Integer getUserHostPermissions(String userId) {
        return myInfoMapper.getUserHostPermissions(userId);
    }

    @Override
    public List<Map<String, Object>> hostInfoRec() {
        List<Map<String, Object>> list = myInfoMapper.hostInfoRec();
        
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
        
            //医师的
            if(map!=null && map.get("type")!=null && "1".equals(map.get("type").toString())&& 
                    map.get("userId")!=null) {
                String userId = map.get("userId").toString();
                Map<String, String> mapHeadProtrait = myInfoMapper.selectDoctorHeadPortraitAndByUserId(userId);
                map.putAll(mapHeadProtrait);
            }
        }
        return myInfoMapper.hostInfoRec();
    }

    @Override
    public Page<Map<String, Object>> findUserWallet(
            Page<Map<String, Object>> page, String id) {
        List<Map<String, Object>> page1 = myInfoMapper.findUserWallet(page, id);
        return page.setRecords(page1);
    }

    @Override
    public Map<String, String> findHostInfoById(String userId) {
        
        return myInfoMapper.findHostInfoById(userId);
    }

    @Override
    public Map<String, String> findHostInfoByIdProbablyPhysician(String userId) {
        
        Map<String, String> mapHostInfo = myInfoMapper.findHostInfoById(userId);

        if(mapHostInfo!=null && mapHostInfo.get("type").equals("1")){
            Map<String, String> mapDoctorInfo =   myInfoMapper.
                    selectDoctorHeadPortraitAndTitleByUserId(userId);
            //过滤下坐诊时间
            if (mapDoctorInfo != null && mapDoctorInfo.get("workTime") != null) {
                mapDoctorInfo.put("workTime", XzStringUtils.workTimeScreen(mapDoctorInfo.get("workTime")));
            }
            //过滤详情中的外部连接
            if(mapDoctorInfo != null && mapDoctorInfo.get("detail") != null) {
                mapDoctorInfo.put("detail", XzStringUtils.formatA(mapDoctorInfo.get("detail")));
            }        
            mapHostInfo.putAll(mapDoctorInfo);
        }
        return mapHostInfo;
    }
    
    
    

    @Override
    public Map<String, Object> findDoctorInfoById(String userId) {
        Map<String, Object> doctorInfo = myInfoMapper.findDoctorInfoById(userId);
        if (doctorInfo != null) {
            doctorInfo.put("user_id", userId);
        }
        return doctorInfo;
    }

    @Override
    public Map<String, Object> findDoctorInfoByDoctorId(String doctorId) {
        return myInfoMapper.findDoctorInfoByDoctorId(doctorId);
    }

    @Override
    public  Map<String,Object> findHostTypeByUserId(String id) {
        return myInfoMapper.findHostTypeByUserId(id);
    }
    


    @Override
    public Map<String, Object> selectUserHomePageData(String userId, String lecturerId, Boolean boolean1) {
        
        Map<String, Object> mapAll = new HashMap<String, Object>();
        
        List<Integer> listff = focusMapper.selectFocusOrFansCountOrCriticizeCount(lecturerId);
        mapAll.put("fansCount", listff.get(0));           //粉丝总数
        mapAll.put("focusCount", listff.get(1));           //关注总数
        mapAll.put("criticizeCount", listff.get(2));       //总数评论总数
        /**
         * 判断用户是否已经关注了这个主播
         */
        if (lecturerId == null) {
            mapAll.put("isFours", 0);
        } else {
            Integer isFours = focusMapper.isFoursLecturer(userId, lecturerId);
            mapAll.put("isFours", isFours);
        }
        /**
         * 此主播最近一次的直播
         */
        CourseLecturVo cv = courseMapper.selectLecturerRecentCourse(lecturerId,boolean1);
        mapAll.put("recentCourse", cv);
        
        return mapAll;
    }
    
    
    public static void main(String[] args) {
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("haha", "1");
        map.put("lala", "1");
        map.put("haha1", "1");
        map.put("lala1", "1");
        
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("haha1", "2");
        map1.put("lala2", "2");
        
        map.putAll(map1);
        
        System.out.println(map.toString());
        
    }
    
}
