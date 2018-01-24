package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.medical.anchor.mapper.CollectionCourseApplyMapper;
import com.xczhihui.medical.anchor.mapper.CourseApplyInfoMapper;
import com.xczhihui.medical.anchor.mapper.CourseApplyResourceMapper;
import com.xczhihui.medical.anchor.model.CollectionCourseApply;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  主播课程相关服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
@Service
public class CourseApplyServiceImpl extends ServiceImpl<CourseApplyInfoMapper, CourseApplyInfo> implements ICourseApplyService {

    @Autowired
    private CourseApplyInfoMapper courseApplyInfoMapper;
    @Autowired
    private CourseApplyResourceMapper courseApplyResourceMapper;
    @Autowired
    private CollectionCourseApplyMapper collectionCourseApplyMapper;

    /**
     * Description：分页获取主播课程列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:21 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyInfoVO> selectCourseApplyPage(Page<CourseApplyInfoVO> page,String userId,Integer courseForm, Integer multimediaType, String title) {
        List<CourseApplyInfoVO> records = courseApplyInfoMapper.selectCourseApplyPage(page, userId,courseForm,multimediaType, title);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：分页获取主播合辑列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:21 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyInfoVO> selectCollectionApplyPage(Page<CourseApplyInfoVO> page, String userId, Integer multimediaType, String title) {
        List<CourseApplyInfoVO> records = courseApplyInfoMapper.selectCollectionApplyPage(page,userId,multimediaType,title);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：分页获取主播直播列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:21 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyInfoVO> selectLiveApplyPage(Page<CourseApplyInfoVO> page, String userId, String title) {
        List<CourseApplyInfoVO> records = courseApplyInfoMapper.selectLiveApplyPage(page,userId,title);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：分页获取课程资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:33 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyResourceVO> selectCourseResourcePage(Page<CourseApplyResourceVO> page, String userId) {
        List<CourseApplyResourceVO> records = courseApplyResourceMapper.selectCourseResourceByPage(page,userId);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：添加课程
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:23 2018/1/19 0019
     **/
    @Override
    public void saveCourseApply(CourseApplyInfo courseApplyInfo){
        validateCourseApply(courseApplyInfo);
        //当课程为点播视频时
        if(courseApplyInfo.getCourseForm()== CourseForm.VOD.getCode()){
            Integer resourceId = courseApplyInfo.getResourceId();
            CourseApplyResource resource = new CourseApplyResource();
            resource.setId(resourceId);
            resource.setDelete(false);
            resource.setUserId(courseApplyInfo.getUserId());
            CourseApplyResource courseApplyResource = courseApplyResourceMapper.selectOne(resource);
            //将资源放入课程
            courseApplyInfo.setResourceId(courseApplyResource.getId());
            courseApplyInfo.setCourseResource(courseApplyResource.getResource());
        }

        courseApplyInfoMapper.insert(courseApplyInfo);
    }

    /**
     * Description：新增课程校验
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 10:37 2018/1/24 0024
     **/
    private void validateCourseApply(CourseApplyInfo courseApplyInfo) {
        if(StringUtils.isBlank(courseApplyInfo.getUserId())){
            throw new RuntimeException("用户id不可为空");
        }
        if(StringUtils.isBlank(courseApplyInfo.getTitle())){
            throw new RuntimeException("课程标题不可为空");
        }else if(courseApplyInfo.getTitle().length()>32){
            throw new RuntimeException("课程标题长度不可超过32");
        }
        if(StringUtils.isBlank(courseApplyInfo.getSubtitle())){
            throw new RuntimeException("课程副标题不可为空");
        }else if(courseApplyInfo.getSubtitle().length()>32){
            throw new RuntimeException("课程副标题长度不可超过32");
        }
        if(StringUtils.isBlank(courseApplyInfo.getLecturer())){
            throw new RuntimeException("主播不可为空");
        }else if(courseApplyInfo.getLecturer().length()>32){
            throw new RuntimeException("主播名称长度不可超过32");
        }
//        if(StringUtils.isBlank(courseApplyInfo.getLecturerDescription())){
//            throw new RuntimeException("主播介绍不可为空");
//        }
        if(StringUtils.isBlank(courseApplyInfo.getCourseMenu())){
            throw new RuntimeException("课程分类不可为空");
        }
//        if(StringUtils.isBlank(courseApplyInfo.getCourseLength())){
//            throw new RuntimeException("时长不可为空");
//        }
        if(courseApplyInfo.getCourseForm()==null){
            throw new RuntimeException("课程形式不可为空");
        }
        if(courseApplyInfo.getCourseForm()==null){
            throw new RuntimeException("课程单价不可为空");
        }

        //当课程为点播视频时
        if(courseApplyInfo.getCourseForm()== CourseForm.VOD.getCode()){
            if(courseApplyInfo.getMultimediaType()==null){
                throw new RuntimeException("媒体类型不为空");
            }
            Integer resourceId = courseApplyInfo.getResourceId();
            if(resourceId == null){
                throw new RuntimeException("课程资源不存在");
            }
            CourseApplyResource resource = new CourseApplyResource();
            resource.setId(resourceId);
            resource.setDelete(false);
            resource.setUserId(courseApplyInfo.getUserId());
            CourseApplyResource courseApplyResource = courseApplyResourceMapper.selectOne(resource);
            if(courseApplyResource==null || StringUtils.isBlank(courseApplyResource.getResource())){
                throw new RuntimeException("课程未发现视频资源");
            }
        }else if(courseApplyInfo.getCourseForm()== CourseForm.OFFLINE.getCode()){
            if(StringUtils.isBlank(courseApplyInfo.getAddress())){
                throw new RuntimeException("授课地址不为空");
            }
            if(courseApplyInfo.getStartTime()==null){
                throw new RuntimeException("开课时间不为空");
            }
            if(courseApplyInfo.getEndTime()==null){
                throw new RuntimeException("结课时间不为空");
            }
        }else if(courseApplyInfo.getCourseForm()== CourseForm.LIVE.getCode()){
            if(courseApplyInfo.getStartTime()==null){
                throw new RuntimeException("开课时间不为空");
            }
        }else{
            throw new RuntimeException("课程形式有误");
        }
    }

    /**
     * Description：添加专辑
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:24 2018/1/19 0019
     **/
    @Override
    public void saveCollectionApply(CourseApplyInfo courseApplyInfo){
        validateCollectionApply(courseApplyInfo);
        //当合辑为点播视频时
        for(CourseApplyInfo applyInfo :courseApplyInfo.getCourseApplyInfos()){
            CollectionCourseApply collectionCourseApply = new CollectionCourseApply();
            collectionCourseApply.setCourseApplyId(applyInfo.getId());
            collectionCourseApply.setCollectionApplyId(courseApplyInfo.getId());
            collectionCourseApplyMapper.insert(collectionCourseApply);
        }
    }

    /**
     * Description：新增专辑校验
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 10:37 2018/1/24 0024
     **/
    private void validateCollectionApply(CourseApplyInfo courseApplyInfo) {
        if(!courseApplyInfo.getCollection()){
            throw new RuntimeException("该方法仅支持专辑课程");
        }
        if(StringUtils.isBlank(courseApplyInfo.getTitle())){
            throw new RuntimeException("合辑标题不可为空");
        }else if(courseApplyInfo.getTitle().length()>32){
            throw new RuntimeException("合辑标题长度不可超过32");
        }
        if(StringUtils.isBlank(courseApplyInfo.getSubtitle())){
            throw new RuntimeException("合辑副标题不可为空");
        }else if(courseApplyInfo.getSubtitle().length()>32){
            throw new RuntimeException("合辑副标题长度不可超过32");
        }
        if(StringUtils.isBlank(courseApplyInfo.getLecturer())){
            throw new RuntimeException("主播不可为空");
        }else if(courseApplyInfo.getLecturer().length()>32){
            throw new RuntimeException("主播名称长度不可超过32");
        }
        if(StringUtils.isBlank(courseApplyInfo.getLecturerDescription())){
            throw new RuntimeException("主播介绍不可为空");
        }

        if(StringUtils.isBlank(courseApplyInfo.getCourseMenu())){
            throw new RuntimeException("课程分类不可为空");
        }
        if(courseApplyInfo.getCourseNumber()==null){
            throw new RuntimeException("总集数不可为空");
        }
        if(courseApplyInfo.getCourseForm()==null){
            throw new RuntimeException("课程形式不可为空");
        }
        if(courseApplyInfo.getPrice()==null){
            throw new RuntimeException("合辑总价不可为空");
        }

        //当合辑为点播视频时
        if(courseApplyInfo.getCourseForm() != CourseForm.VOD.getCode()){
            throw new RuntimeException("暂不支持点播以外的专辑课程");
        }
    }


    /**
     * Description：获取所有资源列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:21 2018/1/19 0019
     **/
    @Override
    public List<CourseApplyResourceVO> selectAllCourseResources(String userId) {
        return courseApplyResourceMapper.selectAllCourseResources(userId);
    }

    /**
     * Description：新增课程资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:34 2018/1/19 0019
     **/
    @Override
    public void saveCourseApplyResource(CourseApplyResource courseApplyResource) {
        validateCourseApplyResource(courseApplyResource);
        courseApplyResource.setCreateTime(new Date());
        courseApplyResource.setUpdateTime(new Date());
        courseApplyResourceMapper.insert(courseApplyResource);
    }

    /**
     * Description：新增资源校验
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 10:54 2018/1/24 0024
     **/
    private void validateCourseApplyResource(CourseApplyResource courseApplyResource) {
        if(StringUtils.isBlank(courseApplyResource.getTitle())){
            throw new RuntimeException("资源标题不可为空");
        }else if(courseApplyResource.getTitle().length()>32){
            throw new RuntimeException("资源标题长度不可超过32个字");
        }
        if(StringUtils.isBlank(courseApplyResource.getResource())){
            throw new RuntimeException("资源不可为空");
        }
        if(courseApplyResource.getMultimediaType()==null || (courseApplyResource.getMultimediaType()!=1 && courseApplyResource.getMultimediaType()!=2)){
            throw new RuntimeException("媒体类型参数有误");
        }
    }
}
