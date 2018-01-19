package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.medical.anchor.mapper.CourseApplyInfoMapper;
import com.xczhihui.medical.anchor.mapper.CourseApplyResourceMapper;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveCourseApply(CourseApplyInfo courseApplyInfo){
        if(courseApplyInfo.getUserId()==null){
            throw new RuntimeException("未发现用户id");
        }
        //当课程未点播视频时
        if(courseApplyInfo.getCourseForm()== CourseForm.VOD.getCode()){
            Integer resourceId = courseApplyInfo.getResourceId();
            if(resourceId == null){
                throw new RuntimeException("点播课程不存在！");
            }
            CourseApplyResource resource = new CourseApplyResource();
            resource.setId(resourceId);
            resource.setDelete(false);
            resource.setUserId(courseApplyInfo.getUserId());
            CourseApplyResource courseApplyResource = courseApplyResourceMapper.selectOne(resource);
            if(courseApplyResource==null || courseApplyResource.getResource()==null){
                throw new RuntimeException("点播课程未发现视频资源！");
            }
            //将资源放入课程
            courseApplyInfo.setResourceId(courseApplyResource.getId());
            courseApplyInfo.setCourseResource(courseApplyResource.getResource());
        }else if(courseApplyInfo.getCourseForm()== CourseForm.LIVE.getCode()){
            //TODO 参数校验待补充
            if(courseApplyInfo.getAddress()==null){
                throw new RuntimeException("课程信息不完整");
            }

        }

        courseApplyInfoMapper.insert(courseApplyInfo);
//        courseApplyInfoMapper.insertAllColumn(courseApplyInfo);
    }


    @Override
    public void saveCollectionApply(CourseApplyInfo courseApplyInfo){
        if(courseApplyInfo.getUserId()==null){
            throw new RuntimeException("未发现用户id");
        }
        if(!courseApplyInfo.getCollection()){
            throw new RuntimeException("该方法仅支持专辑课程");
        }
        //当合辑为点播视频时
        if(courseApplyInfo.getCourseForm()== CourseForm.VOD.getCode()){
            for(CourseApplyInfo course :courseApplyInfo.getCourseApplyInfos()){
                CourseApplyResource courseApplyResource = new CourseApplyResource();

                courseApplyResourceMapper.insert(courseApplyResource);
            }
        }else {
            throw new RuntimeException("暂不支持点播以外的专辑课程");
        }

        courseApplyInfoMapper.insert(courseApplyInfo);
//        courseApplyInfoMapper.insertAllColumn(courseApplyInfo);
    }

}
