package com.xczhihui.bxg.online.manager.homework.web;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeDetailVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeVo;
import com.xczhihui.bxg.online.manager.homework.service.HomeworkService;
import com.xczhihui.bxg.online.manager.homework.vo.ClassPaperVo;
import com.xczhihui.bxg.online.manager.homework.vo.PaperVo;
import com.xczhihui.bxg.online.manager.homework.vo.ReadOverPaperQuestionTypeInfoVo;
import com.xczhihui.bxg.online.manager.homework.vo.UserPaperVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 作业管理模块控制层代码
 * @Author Fudong.Sun【】
 * @Date 2017/2/28 9:26
 */
@Controller
@RequestMapping(value = "/homework")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("homework/grade");
        mav.addObject("courses", courseService.list("0"));
        return mav;
    }

    /**
     * 获取职业课班级列表信息
     * @return
     */
    @RequestMapping(value = "/findGradeList")
    @ResponseBody
    public TableVo findGradeList(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group courseName = groups.findByName("courseName");
        Group courseId = groups.findByName("courseId");

        GradeDetailVo searchVo=new GradeDetailVo();

        if(courseName!=null){
            searchVo.setName(courseName.getPropertyValue1().toString());
        }
        if(courseId!=null){
            searchVo.setCourse_id(Integer.parseInt(courseId.getPropertyValue1().toString()));
        }

        Page<GradeVo> page= homeworkService.findGradeList(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 班级发布试卷列表页面
     * @param classId
     * @param className
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/classPapers")
    public ModelAndView classPapers(String classId,String className,String courseId){
        ModelAndView mav = new ModelAndView("homework/papers");
        mav.addObject("courseId",courseId);
        mav.addObject("classId",classId);
        mav.addObject("className",className);
        return mav;
    }

    /**
     * 获取班级下试卷列表，根据班级id
     * @param tableVo
     * @return
     */
    @RequestMapping(value = "/findPaperList")
    @ResponseBody
    public TableVo findPaperList(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group paperName = groups.findByName("classPaperName");
        Group difficulty = groups.findByName("difficulty");
        Group status = groups.findByName("status");
        Group startTime = groups.findByName("start_time");
        Group endTime = groups.findByName("end_time");
        Group classId = groups.findByName("classId");

        ClassPaperVo searchVo=new ClassPaperVo();

        if(paperName!=null){
            searchVo.setPaperName(paperName.getPropertyValue1().toString());
        }
        if(difficulty!=null){
            searchVo.setDifficulty(difficulty.getPropertyValue1().toString());
        }
        if(status!=null){
            searchVo.setStatus(Integer.parseInt((String) status.getPropertyValue1()));
        }
        if(startTime!=null){
            searchVo.setStart_time(DateUtil.parseDate(startTime.getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        if(endTime!=null){
            searchVo.setEnd_time(DateUtil.parseDate(endTime.getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        if(classId!=null){
            searchVo.setClass_id(classId.getPropertyValue1().toString());
        }

        Page<ClassPaperVo> page= homeworkService.findPaperList(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 布置试卷
     * @return
     */
    @RequestMapping(value = "/makeHomework", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject makeHomework(ClassPaperVo classPaper){
        homeworkService.addHomework(classPaper);
        return ResponseObject.newSuccessResponseObject("布置试卷成功");
    }

    /**
     * 发布作业
     * @return
     */
    @RequestMapping(value = "/publishHomework", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject publishHomework(String id){
        return homeworkService.savePublishHomework(id);
    }

    /**
     * 删除作业卷
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteHomework", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteHomework(String id){
        homeworkService.deleteHomework(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 查询试卷列表数据
     *
     * @author yuanziyang
     * @date 2016年5月25日 下午5:48:22
     * @param tableVo
     * @return
     */
    @RequestMapping(value = "queryHomeworkList")
    @ResponseBody
    public TableVo queryHomeworkList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int pageNumber = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group paperName = groups.findByName("paperName");
        Group difficulty = groups.findByName("difficult");
        Group courseId = groups.findByName("courseId");

        PaperVo searchVo=new PaperVo();

        if(paperName!=null){
            searchVo.setPaperName(paperName.getPropertyValue1().toString());
        }
        if(difficulty!=null){
            searchVo.setDifficult(difficulty.getPropertyValue1().toString());
        }
        if(courseId!=null){
            searchVo.setCourse_id(courseId.getPropertyValue1().toString());
        }
        Page<PaperVo> homeworkListVoPage = homeworkService.queryHomeworkList(
                searchVo, pageNumber, pageSize);
        tableVo.setAaData(homeworkListVoPage.getItems());
        tableVo.setiTotalDisplayRecords(homeworkListVoPage.getTotalCount());
        tableVo.setiTotalRecords(homeworkListVoPage.getTotalCount());
        return tableVo;
    }

    /**
     * 学员列表页
     * @param classPaperId
     * @param className
     * @param paperName
     * @return
     */
    @RequestMapping(value = "/studentHomework")
    public ModelAndView studentHomework(String classPaperId,String classId,String className,String paperName,String courseId){
        ModelAndView mav = new ModelAndView("homework/students");
        mav.addObject("classPaperId",classPaperId);
        mav.addObject("className",className);
        mav.addObject("classId", classId);
        mav.addObject("paperName", paperName);
        mav.addObject("courseId", courseId);
        return mav;
    }

    /**
     * 查询学员列表
     * @param tableVo
     * @return
     */
    @RequestMapping(value = "queryStudentList")
    @ResponseBody
    public TableVo queryStudentList(TableVo tableVo, HttpServletRequest request) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int pageNumber = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group classPaperId = groups.findByName("classPaperId");
        Group status = groups.findByName("status");
        Group studentName = groups.findByName("studentName");
        Group classId = groups.findByName("classId");

        String sortType = tableVo.getsSortDir_0();// 升序还是降序
        int col = tableVo.getiSortCol_0();
        String sortName = request.getParameter("mDataProp_" + col);// 要排序 字段

        UserPaperVo searchVo=new UserPaperVo();
        searchVo.setSortName(sortName);searchVo.setSortType(sortType);
        if(classPaperId!=null){
            searchVo.setClass_paper_id(classPaperId.getPropertyValue1().toString());
        }
        if(status!=null){
            searchVo.setStatus(Integer.parseInt((String) status.getPropertyValue1()));
        }
        if(studentName!=null){
            searchVo.setStudentName(studentName.getPropertyValue1().toString());
        }
        if(classId!=null){
            searchVo.setClassId(classId.getPropertyValue1().toString());
        }
        Page<UserPaperVo> homeworkListVoPage = homeworkService.queryStudentList(
                searchVo, pageNumber, pageSize);
        tableVo.setAaData(homeworkListVoPage.getItems());
        tableVo.setiTotalDisplayRecords(homeworkListVoPage.getTotalCount());
        tableVo.setiTotalRecords(homeworkListVoPage.getTotalCount());
        return tableVo;
    }

    /**
     * 为学生保存成绩
     * @param userPaperIds 学员试卷id用逗号隔开的字符串
     * @return
     */
    @RequestMapping(value = "saveUserResult", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject saveUserResult(String userPaperIds,String classPaperId){
        if(StringUtils.isBlank(userPaperIds) && StringUtils.isEmpty(classPaperId)){
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        String resultMsg = homeworkService.saveUserResult(userPaperIds);
        homeworkService.saveClassPublishStatus(classPaperId);//修改班级试卷表状态
        return  ResponseObject.newSuccessResponseObject(resultMsg);

    }

    /**
     * 批阅作业卷页面
     * @param classPaperId 班级试卷id
     * @param classId
     * @param className
     * @param paperName
     * @param courseId
     * @param userId
     * @param studentName
     * @param userPaperId 学员试卷id
     * @param type
     * @return
     */
    @RequestMapping(value = "/readOverPaper")
    public ModelAndView readOverPaper(String classPaperId,String classId,String className,String paperName,String courseId,String userId,String studentName,String userPaperId,String type) throws Exception {
        ModelAndView mav = new ModelAndView("homework/pyHomeworkPaper");
        mav.addObject("classPaperId",classPaperId);
        mav.addObject("className",className);
        mav.addObject("classId",classId);
        mav.addObject("paperName",paperName);
        mav.addObject("courseId",courseId);
        mav.addObject("userId",userId);
        mav.addObject("studentName",studentName);
        mav.addObject("userPaperId",userPaperId);
        Map<Integer, ReadOverPaperQuestionTypeInfoVo> value =
                homeworkService.getQuestionsByPaperId(userPaperId,"user");//用户试卷
        if(value!=null && value.size()>0){
            for(Map.Entry<Integer, ReadOverPaperQuestionTypeInfoVo> entry : value.entrySet()){
                mav.addObject("questionType_"+entry.getKey(), entry.getValue());
            }
        }
        mav.addObject("comment",homeworkService.findCommentById(userPaperId));
        mav.addObject("type",type);
        return mav;
    }

    /**
     * 给主观题打分
     * @param questionId 试题id
     * @param totleScore 总分
     * @param score 评分
     * @return
     */
    @RequestMapping(value = "readOverQuestion", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject readOverQuestion(String questionId,Double totleScore, Double score){
        homeworkService.saveReadOverQuestion(questionId,totleScore,score);
        return ResponseObject.newSuccessResponseObject("打分成功");
    }

    /**
     * 判断试卷是否还有未批阅的试题
     * @param userPaperId
     * @return false：有未批阅的试题，true：没有未批阅的试题
     */
    @RequestMapping(value = "judgeReadOverAllQuestions")
    @ResponseBody
    public ResponseObject judgeReadOverAllQuestions(String userPaperId){
        return homeworkService.judgeReadOverAllQuestions(userPaperId);
    }

    /**
     * 批阅整个试卷，打分，修改状态
     * @param userPaperId
     * @param comment
     * @param classPaperId
     * @return
     */
    @RequestMapping(value = "readOverStudentPaper", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject readOverStudentPaper(String userPaperId,String comment,String classPaperId){
        homeworkService.saveReadOverStudentPaper(userPaperId,comment);
        homeworkService.saveClassReadOverStatus(classPaperId);//修改班级试卷表状态
        return ResponseObject.newSuccessResponseObject("批阅完成");
    }

    /**
     * @param classPaperId
     * @param classId
     * @param className
     * @param paperName
     * @param courseId
     * @param userPaperId
     * @param type
     * @return
     */
    @RequestMapping(value = "/toPyNextStudentPage")
    public ModelAndView toPyNextStudentPage(String classPaperId,String classId,String className,String paperName,String courseId,String userPaperId,String type) throws Exception {
        ModelAndView mav = new ModelAndView("homework/pyHomeworkPaper");
        mav.addObject("classPaperId",classPaperId);
        mav.addObject("className",className);
        mav.addObject("classId",classId);
        mav.addObject("paperName",paperName);
        mav.addObject("courseId",courseId);
        //查找班级中下一个未批阅的学员试卷
        String nextUserPaperId = homeworkService.findNextUnReadOverPaper(classPaperId,userPaperId);
        mav.addObject("userPaperId",nextUserPaperId);
        //组装试卷数据
        Map<Integer, ReadOverPaperQuestionTypeInfoVo> value =
                homeworkService.getQuestionsByPaperId(nextUserPaperId,"user");//用户试卷
        if(value!=null && value.size()>0){
            for(Map.Entry<Integer, ReadOverPaperQuestionTypeInfoVo> entry : value.entrySet()){
                mav.addObject("questionType_"+entry.getKey(), entry.getValue());
            }
        }
        mav.addObject("comment",homeworkService.findCommentById(nextUserPaperId));
        mav.addObject("type",type);
        return mav;
    }

    /**
     * 判断是否还有未批阅的学员试卷
     * @param classPaperId
     * @param userPaperId
     * @return
     */
    @RequestMapping(value = "judgeHasNextUnReadOverPaper")
    @ResponseBody
    public ResponseObject judgeHasNextUnReadOverPaper(String classPaperId,String userPaperId){
        return homeworkService.judgeHasNextUnReadOverPaper(classPaperId,userPaperId);
    }

    /**
     * 预览试卷
     * @param paperName
     * @param paperId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reviewPaper")
    public ModelAndView reviewPaper(String paperName,String paperId) throws Exception {
        ModelAndView mav = new ModelAndView("homework/showPaper");
        mav.addObject("paperName",paperName);
        Map<Integer, ReadOverPaperQuestionTypeInfoVo> value =
                homeworkService.getQuestionsByPaperId(paperId,"exam");//课程试卷
        if(value!=null && value.size()>0){
            for(Map.Entry<Integer, ReadOverPaperQuestionTypeInfoVo> entry : value.entrySet()){
                mav.addObject("questionType_"+entry.getKey(), entry.getValue());
            }
        }
        return mav;
    }


}
