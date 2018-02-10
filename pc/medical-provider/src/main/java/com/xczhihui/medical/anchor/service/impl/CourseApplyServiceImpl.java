package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.online.common.enums.ApplyStatus;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.bxg.online.common.enums.Multimedia;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import com.xczhihui.bxg.online.common.utils.cc.util.CCUtils;
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
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedissonUtil redissonUtil;

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
        RLock redissonLock = redissonUtil.getRedisson().getLock("saveCourseApply"+courseApplyInfo.getUserId());
        boolean res = false;
        try {
            //等待3秒，等待10秒
            res = redissonLock.tryLock(3, 10, TimeUnit.SECONDS);
            if(res){
                System.out.println("得到锁"+res);
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
                courseApplyInfo.setCreateTime(new Date());
                courseApplyInfoMapper.insert(courseApplyInfo);
            }
        }catch (RuntimeException e){
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("网络错误，请重试");
        } finally {
            if(res){
                System.out.println("关闭锁");
                redissonLock.unlock();
            }else{
                System.out.println("没有抢到锁");
            }
        }
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
        }else if(courseApplyInfo.getTitle().length()>30){
            throw new RuntimeException("课程标题长度不可超过30");
        }
        if(StringUtils.isBlank(courseApplyInfo.getSubtitle())){
            throw new RuntimeException("课程副标题不可为空");
        }else if(courseApplyInfo.getSubtitle().length()>30){
            throw new RuntimeException("课程副标题长度不可超过30");
        }
        if(StringUtils.isBlank(courseApplyInfo.getLecturer())){
            throw new RuntimeException("主播不可为空");
        }else if(courseApplyInfo.getLecturer().length()>30){
            throw new RuntimeException("主播名称长度不可超过30");
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
        if(courseApplyInfo.getPrice()==null){
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
            if(StringUtils.isBlank(courseApplyInfo.getCity())){
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
        validateCourseName(courseApplyInfo);
    }

    /**
     * Description：校验课程名称
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/5 0005 上午 11:13
     **/
    private void validateCourseName(CourseApplyInfo courseApplyInfo) {
        Integer caiCount = courseApplyInfoMapper.selectCourseApplyForValidate(courseApplyInfo.getTitle(),courseApplyInfo.getOldApplyInfoId());
        if(caiCount>0){
            throw new RuntimeException("课程标题被占用");
        }else{
            Integer cCount = courseApplyInfoMapper.selectCourseForValidate(courseApplyInfo.getTitle());
            if(cCount>0){
                throw new RuntimeException("课程标题被占用");
            }
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
        RLock redissonLock = redissonUtil.getRedisson().getLock("saveCollectionApply"+courseApplyInfo.getUserId());
        boolean res = false;
        try {
            //等待3秒，等待10秒
            res = redissonLock.tryLock(3, 10, TimeUnit.SECONDS);
            if(res){
                System.out.println("saveCollectionApply得到锁"+res);
                validateCollectionApply(courseApplyInfo);
                courseApplyInfo.setCreateTime(new Date());
//                courseApplyInfo.setVersion(BeanUtil.getUUID());
                courseApplyInfoMapper.insert(courseApplyInfo);
                //当合辑为点播视频时
                for(CourseApplyInfo applyInfo :courseApplyInfo.getCourseApplyInfos()){
                    CollectionCourseApply collectionCourseApply = new CollectionCourseApply();
                    collectionCourseApply.setCourseApplyId(applyInfo.getId());
                    collectionCourseApply.setCollectionApplyId(courseApplyInfo.getId());
                    collectionCourseApply.setCollectionCourseSort(applyInfo.getCollectionCourseSort());
                    collectionCourseApplyMapper.insert(collectionCourseApply);
                }
            }
        }catch (RuntimeException e){
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("网络错误，请重试");
        } finally {
            if(res){
                System.out.println("关闭锁");
                redissonLock.unlock();
            }else{
                System.out.println("没有抢到锁");
            }
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
        }else if(courseApplyInfo.getLecturer().length()>30){
            throw new RuntimeException("主播名称长度不可超过30");
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
        validateCourseName(courseApplyInfo);
    }


    /**
     * Description：获取所有资源列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:21 2018/1/19 0019
     **/
    @Override
    public List<CourseApplyResourceVO> selectAllCourseResources(String userId,Integer multimediaType) {
        return courseApplyResourceMapper.selectAllCourseResources(userId,multimediaType);
    }

    /**
     * Description：新增课程资源
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:34 2018/1/19 0019
     **/
    @Override
    public void saveCourseApplyResource(CourseApplyResource courseApplyResource) {
        RLock redissonLock = redissonUtil.getRedisson().getLock("saveCourseApplyResource"+courseApplyResource.getUserId());
        boolean res = false;
        try {
            //等待3秒，等待10秒
            res = redissonLock.tryLock(3, 10, TimeUnit.SECONDS);
            if(res){
                System.out.println("saveCourseApplyResource得到锁"+res);
                validateCourseApplyResource(courseApplyResource);
                courseApplyResource.setCreateTime(new Date());
                courseApplyResource.setUpdateTime(new Date());
                courseApplyResourceMapper.insert(courseApplyResource);
            }
        }catch (RuntimeException e){
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("网络错误，请重试");
        } finally {
            if(res){
                System.out.println("关闭锁");
                redissonLock.unlock();
            }else{
                System.out.println("没有抢到锁");
            }
        }
    }

    @Override
    public String selectCourseResourcePlayerById(String userId, Integer resourceId) {
        CourseApplyResource car = new CourseApplyResource();
        car.setUserId(userId);
        car.setId(resourceId);
        CourseApplyResource courseApplyResource = courseApplyResourceMapper.selectOne(car);
        String audioStr="";
        if(courseApplyResource.getMultimediaType()==Multimedia.AUDIO.getCode()){
            audioStr = "_2";
        }
        String src = "https://p.bokecc.com/flash/single/"+ OnlineConfig.CC_USER_ID+"_"+courseApplyResource.getResource()+"_false_"+OnlineConfig.CC_PLAYER_ID+"_1"+audioStr+"/player.swf";
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String playCode = "";
        playCode+="<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ";
        playCode+="		codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" ";
        playCode+="		width=\"600\" ";
        playCode+="		height=\"490\" ";
        playCode+="		id=\""+uuid+"\">";
        playCode+="		<param name=\"movie\" value=\""+src+"\" />";
        playCode+="		<param name=\"allowFullScreen\" value=\"true\" />";
        playCode+="		<param name=\"allowScriptAccess\" value=\"always\" />";
        playCode+="		<param value=\"transparent\" name=\"wmode\" />";
        playCode+="		<embed src=\""+src+"\" ";
        playCode+="			width=\"600\" height=\"490\" name=\""+uuid+"\" allowFullScreen=\"true\" ";
        playCode+="			wmode=\"transparent\" allowScriptAccess=\"always\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" ";
        playCode+="			type=\"application/x-shockwave-flash\"/> ";
        playCode+="	</object>";
        return playCode;
    }

    @Override
    public void updateSaleState(String userId, String courseApplyId, Integer state) {
        int i = courseApplyInfoMapper.updateSaleState(userId,courseApplyId,state);
        if(i<1){
            throw new RuntimeException("更新课程上架状态失败");
        }
    }

    @Override
    public void updateCourseApplyResource() {
        List<CourseApplyResource> CourseApplyResources = courseApplyResourceMapper.selectAllCourseResourcesForUpdateDuration();
        for (CourseApplyResource courseApplyResource : CourseApplyResources) {
            try {
                String duration = CCUtils.getVideoLength(courseApplyResource.getResource());
                courseApplyResource.setLength(duration);
                courseApplyResourceMapper.updateById(courseApplyResource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteCourseApplyResource(String userId, String resourceId) {
        courseApplyResourceMapper.deleteCourseApplyResource(userId,resourceId);
    }

    @Override
    public List<CourseApplyInfoVO> selectAllCourses(String userId, Integer multimediaType) {
        return courseApplyInfoMapper.selectAllCourses(userId,multimediaType);
    }

    @Override
    public CourseApplyInfo selectCourseApplyById(String userId, Integer caiId) {
        CourseApplyInfo courseApplyInfo = courseApplyInfoMapper.selectCourseApplyById(userId, caiId);
        if(courseApplyInfo == null){
            throw new RuntimeException("课程不存在");
        }
        if(courseApplyInfo.getCollection()){
            List<CourseApplyInfo> courseApplyInfos = courseApplyInfoMapper.selectCourseApplyByCollectionId(courseApplyInfo.getId());
            courseApplyInfo.setCourseApplyInfos(courseApplyInfos);
        }
        return courseApplyInfo;
    }

    @Override
    public void updateCourseApply(CourseApplyInfo courseApplyInfo) {
        CourseApplyInfo cai = selectCourseApplyById(courseApplyInfo.getUserId(), courseApplyInfo.getId());
        if(cai==null){
            throw new RuntimeException("课程不存在");
        }
        //删除之前申请
        courseApplyInfoMapper.deleteCourseApplyById(courseApplyInfo.getId());
        //记录原申请id
        courseApplyInfo.setOldApplyInfoId(courseApplyInfo.getId());
        courseApplyInfo.setId(null);
        saveCourseApply(courseApplyInfo);
    }

    @Override
    public void deleteCourseApplyById(String userId, Integer caiId) {
        CourseApplyInfo cai = selectCourseApplyById(userId, caiId);
        if(cai==null){
            throw new RuntimeException("操作的申请记录不存在");
        }
        //仅未通过的申请可删除
        if(cai.getStatus()!=ApplyStatus.NOT_PASS.getCode()){
            throw new RuntimeException("该条申请记录暂不允许删除");
        }
        courseApplyInfoMapper.deleteCourseApplyById(caiId);
    }

    @Override
    public void updateCollectionApply(CourseApplyInfo collectionApplyInfo) {
        CourseApplyInfo collection = selectCourseApplyById(collectionApplyInfo.getUserId(), collectionApplyInfo.getId());
        if(collection==null){
            throw new RuntimeException("专辑不存在");
        }
        /*else if(cai.getStatus()!= ApplyStatus.UNTREATED.getCode()){
            //防止后台管理操作与主播同时操作该课程出现问题
            throw new RuntimeException("课程审核状态已经发生变化");
        }*/
        //删除之前申请
        courseApplyInfoMapper.deleteCourseApplyById(collectionApplyInfo.getId());
        //记录原申请id
        collectionApplyInfo.setOldApplyInfoId(collectionApplyInfo.getId());
        collectionApplyInfo.setId(null);
        saveCollectionApply(collectionApplyInfo);
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
        if(courseApplyResource.getMultimediaType()==null || (courseApplyResource.getMultimediaType()!= Multimedia.AUDIO.getCode() && courseApplyResource.getMultimediaType()!=Multimedia.VIDEO.getCode())){
            throw new RuntimeException("媒体类型参数有误");
        }
    }
}
