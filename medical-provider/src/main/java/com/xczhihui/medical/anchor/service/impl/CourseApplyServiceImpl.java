package com.xczhihui.medical.anchor.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.support.cc.util.CCUtils;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.enums.ApplyStatus;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.common.util.enums.Multimedia;
import com.xczhihui.medical.anchor.mapper.CollectionCourseApplyMapper;
import com.xczhihui.medical.anchor.mapper.CollectionCourseApplyUpdateDateMapper;
import com.xczhihui.medical.anchor.mapper.CourseApplyInfoMapper;
import com.xczhihui.medical.anchor.mapper.CourseApplyResourceMapper;
import com.xczhihui.medical.anchor.model.CollectionCourseApply;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;
import com.xczhihui.medical.exception.AnchorWorkException;

/**
 * <p>
 * 主播课程相关服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
@Service
public class CourseApplyServiceImpl extends ServiceImpl<CourseApplyInfoMapper, CourseApplyInfo> implements ICourseApplyService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CourseApplyInfoMapper courseApplyInfoMapper;
    @Autowired
    private CourseApplyResourceMapper courseApplyResourceMapper;
    @Autowired
    private CollectionCourseApplyMapper collectionCourseApplyMapper;
    @Autowired
    private ICourseApplyService courseApplyService;
    @Autowired
    private IAnchorInfoService anchorInfoService;
    @Autowired
    private CollectionCourseApplyUpdateDateMapper collectionCourseApplyUpdateDateMapper;
    @Autowired
    private CCUtils CCUtils;

    /**
     * Description：分页获取主播课程列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:21 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyInfoVO> selectCourseApplyPage(Page<CourseApplyInfoVO> page, String userId, Integer courseForm, Integer multimediaType, String title) {
        anchorInfoService.validateAnchorPermission(userId);
        List<CourseApplyInfoVO> records = courseApplyInfoMapper.selectCourseApplyPage(page, userId, courseForm, multimediaType, title);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：分页获取主播专辑列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:21 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyInfoVO> selectCollectionApplyPage(Page<CourseApplyInfoVO> page, String userId, Integer multimediaType, String title) {
        anchorInfoService.validateAnchorPermission(userId);
        List<CourseApplyInfoVO> records = courseApplyInfoMapper.selectCollectionApplyPage(page, userId, multimediaType, title);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：分页获取主播直播列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 11:21 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyInfoVO> selectLiveApplyPage(Page<CourseApplyInfoVO> page, String userId, String title) {
        anchorInfoService.validateAnchorPermission(userId);
        List<CourseApplyInfoVO> records = courseApplyInfoMapper.selectLiveApplyPage(page, userId, title);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：分页获取课程资源
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:33 2018/1/19 0019
     **/
    @Override
    public Page<CourseApplyResourceVO> selectCourseResourcePage(Page<CourseApplyResourceVO> page, String userId) {
        anchorInfoService.validateAnchorPermission(userId);
        List<CourseApplyResourceVO> records = courseApplyResourceMapper.selectCourseResourceByPage(page, userId);
        page.setRecords(records);
        return page;
    }

    /**
     * Description：添加课程
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:23 2018/1/19 0019
     **/
    @Override
    public void saveCourseApply(CourseApplyInfo courseApplyInfo) {
        anchorInfoService.validateAnchorPermission(courseApplyInfo.getUserId());
        courseApplyService.saveCourseApply4Lock(courseApplyInfo.getUserId(), courseApplyInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "addCourseApply")
    public void saveCourseApply4Lock(String lockKey, CourseApplyInfo courseApplyInfo) {
        validateCourseApply(courseApplyInfo);
        //将价格由熊猫币转化为人民币
        courseApplyInfo.setPrice(courseApplyInfo.getPrice() / 10);
        //当课程为点播视频时
        if (courseApplyInfo.getCourseForm() == CourseForm.VOD.getCode()) {
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

    /**
     * Description：校验课程名称
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/5 0005 上午 11:13
     **/
    private void validateCourseName(CourseApplyInfo courseApplyInfo) {
        Integer caiCount = courseApplyInfoMapper.selectCourseApplyForValidate(courseApplyInfo.getTitle(), courseApplyInfo.getOldApplyInfoId());
        if (caiCount > 0) {
            throw new AnchorWorkException("课程标题被占用");
        } else {
            List<Integer> ids = new ArrayList<>();
            ids.add(courseApplyInfo.getId());
            getApplyIdsByChildId(courseApplyInfo.getId(), ids);
            Integer cCount = courseApplyInfoMapper.selectCourseForValidate(courseApplyInfo.getTitle(), ids);
            if (cCount > 0) {
                throw new AnchorWorkException("课程标题被占用");
            }
        }
    }

    private void getApplyIdsByChildId(Integer id, List<Integer> ids) {
        Integer parentId = courseApplyInfoMapper.getParentIdByChildId(id);
        if (parentId != null) {
            ids.add(parentId);
            getApplyIdsByChildId(parentId, ids);
        }
    }

    /**
     * Description：添加专辑
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:24 2018/1/19 0019
     **/
    @Override
    public void saveCollectionApply(CourseApplyInfo courseApplyInfo) {
        anchorInfoService.validateAnchorPermission(courseApplyInfo.getUserId());
        courseApplyService.saveCollectionApply4Lock(courseApplyInfo.getUserId(), courseApplyInfo);
        addCollectionUpdateDate(courseApplyInfo);
    }

    private void addCollectionUpdateDate(CourseApplyInfo courseApplyInfo) {
        //专辑的更新时间
        if (courseApplyInfo.getUpdateDates() != null && !courseApplyInfo.getUpdateDates().isEmpty()) {
            for (int date : courseApplyInfo.getUpdateDates()) {
                collectionCourseApplyUpdateDateMapper.insertModel(courseApplyInfo.getId(), date);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "addCollectionApply")
    public void saveCollectionApply4Lock(String lockKey, CourseApplyInfo courseApplyInfo) {
        validateCollectionApply(courseApplyInfo);
        //将价格由熊猫币转化为人民币
        courseApplyInfo.setPrice(courseApplyInfo.getPrice() / 10);
        courseApplyInfo.setCreateTime(new Date());
        courseApplyInfoMapper.insert(courseApplyInfo);

        //当专辑为点播视频时
        for (CourseApplyInfo applyInfo : courseApplyInfo.getCourseApplyInfos()) {
            CollectionCourseApply collectionCourseApply = new CollectionCourseApply();
            collectionCourseApply.setCourseApplyId(applyInfo.getId());
            collectionCourseApply.setCollectionApplyId(courseApplyInfo.getId());
            collectionCourseApply.setCollectionCourseSort(applyInfo.getCollectionCourseSort());
            collectionCourseApplyMapper.insert(collectionCourseApply);
        }
    }

    /**
     * Description：获取所有资源列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:21 2018/1/19 0019
     **/
    @Override
    public List<CourseApplyResourceVO> selectAllCourseResources(String userId, Integer multimediaType) {
        return courseApplyResourceMapper.selectAllCourseResources(userId, multimediaType);
    }

    /**
     * Description：新增课程资源
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 6:34 2018/1/19 0019
     **/
    @Override
    public void saveCourseApplyResource(CourseApplyResource courseApplyResource) {
        anchorInfoService.validateAnchorPermission(courseApplyResource.getUserId());
        courseApplyService.saveCourseApplyResource4Lock(courseApplyResource.getUserId(), courseApplyResource);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "addCourseApplyResource")
    public void saveCourseApplyResource4Lock(String lockKey, CourseApplyResource courseApplyResource) {
        validateCourseApplyResource(courseApplyResource);
        courseApplyResource.setCreateTime(new Date());
        courseApplyResource.setUpdateTime(new Date());
        courseApplyResourceMapper.insert(courseApplyResource);
    }


    @Override
    public String selectCourseResourcePlayerById(String userId, Integer resourceId) {
        anchorInfoService.validateAnchorPermission(userId);
        CourseApplyResource car = new CourseApplyResource();
        car.setUserId(userId);
        car.setId(resourceId);
        CourseApplyResource courseApplyResource = courseApplyResourceMapper.selectOne(car);
        String audioStr = "";
        if (courseApplyResource.getMultimediaType() == Multimedia.AUDIO.getCode()) {
            audioStr = "_2";
        }

        String playCode = CCUtils.getPlayCode(courseApplyResource.getResource(), audioStr, "600", "490");
        return playCode;
    }

    @Override
    public void updateSaleState(String userId, String courseApplyId, Integer state) {
        anchorInfoService.validateAnchorPermission(userId);
        
        Integer falg =  courseApplyInfoMapper.getIsStatusChange(userId, courseApplyId, state);
        if(falg.equals(1) && state.equals(1)) {
            throw new AnchorWorkException("已经上架啦");
        }else if(falg.equals(1) && state.equals(0)) {
            throw new AnchorWorkException("已经下架啦");
        }
        
        int i = courseApplyInfoMapper.updateSaleState(userId, courseApplyId, state);
        if (i < 1) {
            throw new AnchorWorkException("更新课程上架状态失败");
        }
    }

    @Override
    public void updateCourseApplyResource() {
        List<CourseApplyResource> CourseApplyResources = courseApplyResourceMapper.selectAllCourseResourcesForUpdateDuration();


        for (CourseApplyResource courseApplyResource : CourseApplyResources) {
            try {
                LocalDateTime today = LocalDateTime.now();
                LocalDateTime birthDate = LocalDateTime.ofInstant(courseApplyResource.getCreateTime().toInstant(), ZoneId.systemDefault());
                Duration d = java.time.Duration.between(birthDate, today);
                if (d.toHours() > 24) {
                    logger.info("资源{}-{}转码超时", courseApplyResource.getId(), courseApplyResource.getTitle());
                    courseApplyResource.setLength(-1 + "");
                } else {
                    String duration = CCUtils.getVideoLength(courseApplyResource.getResource());
                    courseApplyResource.setLength(duration);
                }
                logger.info("资源id:" + courseApplyResource.getResource());
                logger.info("课程时长:" + courseApplyResource.getLength());


                /**
                 * 更改这个课程的时长
                 */
                List<Integer> list = courseApplyResourceMapper.selectCourseListByVideoRecourse(courseApplyResource.getResource());
                if (list.size() > 0) {
                    courseApplyResourceMapper.updateBatchCourseLength(courseApplyResource.getLength(), list);
                }


                /**
                 * 通过这个视频Id查找这个对应的课程
                 */
                courseApplyResourceMapper.updateById(courseApplyResource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteCourseApplyResource(String userId, String resourceId) {
        anchorInfoService.validateAnchorPermission(userId);
        courseApplyResourceMapper.deleteCourseApplyResource(userId, resourceId);
    }

    @Override
    public List<CourseApplyInfoVO> selectAllCourses(String userId, Integer multimediaType) {
        anchorInfoService.validateAnchorPermission(userId);
        return courseApplyInfoMapper.selectAllCourses(userId, multimediaType);
    }

    @Override
    public CourseApplyInfo selectCourseApplyById(String userId, Integer caiId) {
        anchorInfoService.validateAnchorPermission(userId);
        CourseApplyInfo courseApplyInfo = courseApplyInfoMapper.selectCourseApplyById(userId, caiId);
        if (courseApplyInfo == null) {
            throw new AnchorWorkException("课程不存在");
        }
        if (courseApplyInfo.getCollection()) {
            List<CourseApplyInfo> courseApplyInfos = courseApplyInfoMapper.selectCourseApplyByCollectionId(courseApplyInfo.getId());
            courseApplyInfo.setCourseApplyInfos(courseApplyInfos);
            courseApplyInfo.setUpdateDates(collectionCourseApplyUpdateDateMapper.listDatesByCollectionApplyId(courseApplyInfo.getId()));
        }
        return courseApplyInfo;
    }

    @Override
    public void updateCourseApply(CourseApplyInfo courseApplyInfo) {
        anchorInfoService.validateAnchorPermission(courseApplyInfo.getUserId());
        CourseApplyInfo cai = selectCourseApplyById(courseApplyInfo.getUserId(), courseApplyInfo.getId());
        if (cai == null) {
            throw new AnchorWorkException("课程申请不存在");
        }
        if (courseApplyInfo.getPrice() == 0 && cai.getPrice() > 0) {
            throw new AnchorWorkException("课程不可由付费变为免费");
        }
        if (courseApplyInfo.getPrice() > 0 && cai.getPrice() == 0) {
            throw new AnchorWorkException("课程不可由免费变为付费");
        }
        if (cai.getStatus() == ApplyStatus.PASS.getCode()) {
            String status = courseApplyInfoMapper.selectCourseStastusByApplyId(courseApplyInfo.getId());
            if ("1".equals(status)) {
                throw new AnchorWorkException("课程上架状态下，不能进行修改操作");
            }
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
        if (cai == null) {
            throw new AnchorWorkException("操作的申请记录不存在");
        }
        //仅通过的申请不删除
        if (cai.getStatus() == ApplyStatus.PASS.getCode()) {
            throw new AnchorWorkException("该条申请记录暂不允许删除");
        }
        List<CourseApplyInfo> courseApplyInfos = courseApplyInfoMapper.selectCollectionApplyByCourseApplyId(caiId);
        if (courseApplyInfos.size() > 0) {
            throw new AnchorWorkException("该课程正在被其他专辑引用，暂时不可删除");
        }
        courseApplyInfoMapper.deleteCourseApplyById(caiId);
    }

    @Override
    public void updateCollectionApply(CourseApplyInfo collectionApplyInfo) {
        Integer collectionApplyInfoId = collectionApplyInfo.getId();
        CourseApplyInfo collection = selectCourseApplyById(collectionApplyInfo.getUserId(), collectionApplyInfoId);
        if (collection == null) {
            throw new AnchorWorkException("专辑不存在");
        }
        /*else if(cai.getStatus()!= ApplyStatus.UNTREATED.getCode()){
            //防止后台管理操作与主播同时操作该课程出现问题
            throw new AnchorWorkException("课程审核状态已经发生变化");
        }*/
        //删除之前申请
        courseApplyInfoMapper.deleteCourseApplyById(collectionApplyInfo.getId());
        //删除更新时间
        collectionCourseApplyUpdateDateMapper.deleteByCollectionApplyId(collectionApplyInfo.getId());

        //记录原申请id
        collectionApplyInfo.setOldApplyInfoId(collectionApplyInfoId);
        collectionApplyInfo.setId(null);
        courseApplyService.saveCollectionApply(collectionApplyInfo);
    }

    @Override
    public String getCollectionUpdateDateText(Integer collectionId) {
        return collectionCourseApplyUpdateDateMapper.listDatesByCollectionId(collectionId)
                .stream().map(DateUtil::getDayOfWeek).collect(Collectors.joining(""));
    }

    /**
     * Description：新增资源校验
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 10:54 2018/1/24 0024
     **/
    private void validateCourseApplyResource(CourseApplyResource courseApplyResource) {
        if (StringUtils.isBlank(courseApplyResource.getTitle())) {
            throw new AnchorWorkException("资源标题不可为空");
        } else if (courseApplyResource.getTitle().length() > 32) {
            throw new AnchorWorkException("资源标题长度不可超过32个字");
        }
        if (StringUtils.isBlank(courseApplyResource.getResource())) {
            throw new AnchorWorkException("资源不可为空");
        }
        if (courseApplyResource.getMultimediaType() == null || (courseApplyResource.getMultimediaType() != Multimedia.AUDIO.getCode() && courseApplyResource.getMultimediaType() != Multimedia.VIDEO.getCode())) {
            throw new AnchorWorkException("媒体类型参数有误");
        }
        validateCourseApplyResourceName(courseApplyResource);
    }

    private void validateCourseApplyResourceName(CourseApplyResource courseApplyResource) {
        CourseApplyResource car = new CourseApplyResource();
        car.setTitle(courseApplyResource.getTitle());
        car.setDelete(false);
        car.setMultimediaType(courseApplyResource.getMultimediaType());
        car.setUserId(courseApplyResource.getUserId());
        courseApplyResource = courseApplyResourceMapper.selectOne(car);
        if (courseApplyResource != null) {
            throw new AnchorWorkException("已存在同名媒体资源");
        }
    }

    /**
     * Description：新增专辑校验
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 10:37 2018/1/24 0024
     **/
    private void validateCollectionApply(CourseApplyInfo courseApplyInfo) {
        if (!courseApplyInfo.getCollection()) {
            throw new AnchorWorkException("该方法仅支持专辑课程");
        }
        if (StringUtils.isBlank(courseApplyInfo.getTitle())) {
            throw new AnchorWorkException("专辑标题不可为空");
        } else if (courseApplyInfo.getTitle().length() > 32) {
            throw new AnchorWorkException("专辑标题长度不可超过32");
        }
        if (StringUtils.isBlank(courseApplyInfo.getSubtitle())) {
            throw new AnchorWorkException("专辑副标题不可为空");
        } else if (courseApplyInfo.getSubtitle().length() > 32) {
            throw new AnchorWorkException("专辑副标题长度不可超过32");
        }
        if (StringUtils.isBlank(courseApplyInfo.getLecturer())) {
            throw new AnchorWorkException("主播不可为空");
        } else if (courseApplyInfo.getLecturer().length() > 30) {
            throw new AnchorWorkException("主播名称长度不可超过30");
        }
        if (StringUtils.isBlank(courseApplyInfo.getLecturerDescription())) {
            throw new AnchorWorkException("主播介绍不可为空");
        }

        if (StringUtils.isBlank(courseApplyInfo.getCourseMenu())) {
            throw new AnchorWorkException("课程分类不可为空");
        }
        if (courseApplyInfo.getCourseNumber() == null) {
            throw new AnchorWorkException("总集数不可为空");
        }
        if (courseApplyInfo.getCourseForm() == null) {
            throw new AnchorWorkException("课程形式不可为空");
        }
        if (courseApplyInfo.getPrice() == null) {
            throw new AnchorWorkException("专辑总价不可为空");
        }
        if (courseApplyInfo.getPrice() < 0) {
            throw new AnchorWorkException("专辑单价不可小于0");
        }
        if (courseApplyInfo.getPrice() != courseApplyInfo.getPrice().intValue()) {
            throw new AnchorWorkException("专辑单价必须为整数");
        }

        //当专辑为点播视频时
        if (courseApplyInfo.getCourseForm() != CourseForm.VOD.getCode()) {
            throw new AnchorWorkException("暂不支持点播以外的专辑课程");
        }
        validateCourseName(courseApplyInfo);
    }

    /**
     * Description：新增课程校验
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 10:37 2018/1/24 0024
     **/
    private void validateCourseApply(CourseApplyInfo courseApplyInfo) {
        validateCourseUsed(courseApplyInfo);
        if (StringUtils.isBlank(courseApplyInfo.getUserId())) {
            throw new AnchorWorkException("用户id不可为空");
        }
        if (StringUtils.isBlank(courseApplyInfo.getTitle())) {
            throw new AnchorWorkException("课程标题不可为空");
        } else if (courseApplyInfo.getTitle().length() > 30) {
            throw new AnchorWorkException("课程标题长度不可超过30");
        }
        if (StringUtils.isBlank(courseApplyInfo.getSubtitle())) {
            throw new AnchorWorkException("课程副标题不可为空");
        } else if (courseApplyInfo.getSubtitle().length() > 30) {
            throw new AnchorWorkException("课程副标题长度不可超过30");
        }
        if (StringUtils.isBlank(courseApplyInfo.getLecturer())) {
            throw new AnchorWorkException("主播不可为空");
        } else if (courseApplyInfo.getLecturer().length() > 30) {
            throw new AnchorWorkException("主播名称长度不可超过30");
        }
        if (StringUtils.isBlank(courseApplyInfo.getCourseMenu())) {
            throw new AnchorWorkException("课程分类不可为空");
        }
        if (courseApplyInfo.getCourseForm() == null) {
            throw new AnchorWorkException("课程形式不可为空");
        }
        if (courseApplyInfo.getPrice() == null) {
            throw new AnchorWorkException("课程单价不可为空");
        }
        if (courseApplyInfo.getPrice() < 0) {
            throw new AnchorWorkException("课程单价不可小于0");
        }
        if (courseApplyInfo.getPrice() != courseApplyInfo.getPrice().intValue()) {
            throw new AnchorWorkException("课程单价必须为整数");
        }

        //当课程为点播视频时
        if (courseApplyInfo.getCourseForm() == CourseForm.VOD.getCode()) {
            if (courseApplyInfo.getMultimediaType() == null) {
                throw new AnchorWorkException("媒体类型不为空");
            }
            Integer resourceId = courseApplyInfo.getResourceId();
            if (resourceId == null) {
                throw new AnchorWorkException("课程资源不存在");
            }
            CourseApplyResource resource = new CourseApplyResource();
            resource.setId(resourceId);
            resource.setDelete(false);
            resource.setUserId(courseApplyInfo.getUserId());
            CourseApplyResource courseApplyResource = courseApplyResourceMapper.selectOne(resource);
            if (courseApplyResource == null || StringUtils.isBlank(courseApplyResource.getResource())) {
                throw new AnchorWorkException("课程未发现视频资源");
            }
        } else if (courseApplyInfo.getCourseForm() == CourseForm.OFFLINE.getCode()) {
            if (StringUtils.isBlank(courseApplyInfo.getAddress())) {
                throw new AnchorWorkException("授课地址不为空");
            }
            if (StringUtils.isBlank(courseApplyInfo.getCity())) {
                throw new AnchorWorkException("授课地址不为空");
            }
            if (courseApplyInfo.getStartTime() == null) {
                throw new AnchorWorkException("开课时间不为空");
            }
            if (courseApplyInfo.getEndTime() == null) {
                throw new AnchorWorkException("结课时间不为空");
            }
        } else if (courseApplyInfo.getCourseForm() == CourseForm.LIVE.getCode()) {
            if (courseApplyInfo.getStartTime() == null) {
                throw new AnchorWorkException("开课时间不为空");
            }
        } else {
            throw new AnchorWorkException("课程形式有误");
        }
        validateCourseName(courseApplyInfo);
    }

    private void validateCourseUsed(CourseApplyInfo courseApplyInfo) {
        if ((courseApplyInfo.getCollection() == null || !courseApplyInfo.getCollection()) && courseApplyInfo.getOldApplyInfoId() != null) {
            List<CourseApplyInfo> courseApplyInfos = courseApplyInfoMapper.selectCollectionApplyByCourseApplyId(courseApplyInfo.getOldApplyInfoId());
            if (courseApplyInfos.size() > 0) {
                throw new AnchorWorkException("该课程正在被其他专辑引用，暂时不可更新");
            }
        }
    }
    
}
