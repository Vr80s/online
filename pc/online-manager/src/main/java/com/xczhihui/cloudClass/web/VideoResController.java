package com.xczhihui.cloudClass.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.cloudClass.vo.TreeNode;
import com.xczhihui.cloudClass.vo.VideoResVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Chapter;
import com.xczhihui.bxg.online.common.domain.Video;
import com.xczhihui.cloudClass.service.VideoResService;
import com.xczhihui.cloudClass.vo.LibraryVo;

/**
 * 视屏资源控制层实现类
 *
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/videores")
public class VideoResController {
    @Autowired
    private VideoResService videoResService;

    /**
     * 获取视屏资源列表信息，根据课程ID号查找
     *
     * @return
     */
    @RequestMapping(value = "/findVideoList")
    @ResponseBody
    public TableVo findVideoList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group courseIdSearch = groups.findByName("courseId");
        Group chapterIdSearch = groups.findByName("chapterId");

        VideoResVo searchVo = new VideoResVo();
        searchVo.setCourseId(Integer.valueOf(courseIdSearch.getPropertyValue1().toString()));
        if (chapterIdSearch != null) {
            searchVo.setChapterId(chapterIdSearch.getPropertyValue1().toString());
        }
        Page<VideoResVo> page = videoResService.findPage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    @RequestMapping(value = "addVideo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addVideo(Video video) {
        ResponseObject responseObj = new ResponseObject();
        video.setCreatePerson(ManagerUserUtil.getName());
        video.setCreateTime(new Date());
        video.setDelete(false);
        video.setStatus(0);
        if (video.getIsTryLearn() == null) {
            video.setIsTryLearn(false);
        }
        videoResService.addVideo(video);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("新增视频成功");
        return responseObj;
    }

    @RequestMapping(value = "editVideo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject editVideo(Video video) {
        ResponseObject responseObj = new ResponseObject();
        if (video.getIsTryLearn() == null) {
            video.setIsTryLearn(false);
        }
        videoResService.updateVideo(video);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改视频成功");
        return responseObj;
    }

    @RequestMapping(value = "deleteVideoById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteVideoById(String id) {
        ResponseObject responseObj = new ResponseObject();

        videoResService.deleteVideoById(id);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("删除成功");
        return responseObj;
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            videoResService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功!");
        return responseObject;
    }

    @RequestMapping(value = "enbaleStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject enbaleStatus(String ids) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            for (String id : _ids) {
                videoResService.updateStatusEnable(id);
            }
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("操作成功!");
        return responseObject;
    }

    @RequestMapping(value = "disableStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject disableStatus(String ids) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            for (String id : _ids) {
                videoResService.updateStatusDisable(id);
            }
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("操作成功!");
        return responseObject;
    }


    /**
     * 上移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(String id) {
        ResponseObject responseObj = new ResponseObject();
        videoResService.updateSortUp(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    /**
     * 下移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "downMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMove(String id) {
        ResponseObject responseObj = new ResponseObject();
        videoResService.updateSortDown(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    // ******************* 左侧章节树的增、删、改、查 *******************
    @RequestMapping(value = "showTree")
    @ResponseBody
    public List<TreeNode> showTree(HttpServletRequest request, String courseId, String courseName, String currentNodePid) {

        List<TreeNode> list = videoResService.getChapterTree(courseId, courseName);
        String str = "";
        for (TreeNode treeNode : list) {
            treeNode.setNocheck(true);
            if (treeNode.getId().equals(currentNodePid)) {
                treeNode.setOpen(true);
                str = treeNode.getpId();
            }
        }
        if (!"".equals(str)) {
            for (TreeNode treeNode : list) {
                if (treeNode.getId().equals(str)) {
                    treeNode.setOpen(true);
                }
            }
        }

        return list;
    }

    @RequestMapping(value = "showTreeCriticize")
    @ResponseBody
    public List<TreeNode> showTreeCriticize(HttpServletRequest request, String courseId, String courseName) {

        List<TreeNode> list = videoResService.getChapterTreeCriticize(courseId, courseName);
        for (TreeNode treeNode : list) {
            treeNode.setNocheck(true);
        }
        return list;
    }

    @RequestMapping(value = "showTreeNotes")
    @ResponseBody
    public List<TreeNode> showTreeNotes(HttpServletRequest request, String courseId, String courseName) {

        List<TreeNode> list = videoResService.getChapterTreeNotes(courseId, courseName);
        for (TreeNode treeNode : list) {
            treeNode.setNocheck(true);
        }
        return list;
    }

    @RequestMapping(value = "showTreeBarrier")
    @ResponseBody
    public List<TreeNode> showTreeBarrier(HttpServletRequest request, String courseId, String courseName) {

        List<TreeNode> list = videoResService.getChapterTreeBarrier(courseId, courseName);
        for (TreeNode treeNode : list) {
            treeNode.setNocheck(true);
            if ("5".equals(treeNode.getContenttype())) {
                treeNode.setIcon("/css/zTreeStyle/img/diy/zzsj.png");
            }
        }
        return list;
    }

    @RequestMapping(value = "showTreeBarrierAdd")
    @ResponseBody
    public List<TreeNode> showTreeBarrierAdd(HttpServletRequest request, String courseId, String courseName) {
        return videoResService.getChapterTreeBarrierAdd(courseId, courseName);
    }

    @RequestMapping(value = "showTreeExamPaperAdd")
    @ResponseBody
    public List<TreeNode> showTreeExamPaperAdd(HttpServletRequest request, String courseId, String courseName) {
        return videoResService.getChapterTreeExamPaperAdd(courseId, courseName);
    }

    @RequestMapping(value = "getTreeByCourse")
    @ResponseBody
    public List<TreeNode> getTreeByCourse(HttpServletRequest request, String courseId, String courseName, String level) {
        List<TreeNode> list = videoResService.getChapterTree(courseId, courseName);
        for (TreeNode treeNode : list) {
            treeNode.setNocheck(false);
        }
        return list;
    }


    @RequestMapping(value = "checkname")
    @ResponseBody
    public Boolean checkname(String name, String id, String ksystemId) {

        return true;
    }

    @RequestMapping(value = "checkVideoUse")
    @ResponseBody
    public Boolean checkVideoUse(String id, Integer courseId) {

        List<Chapter> chapters = videoResService.findVideoUse(id, courseId);
        if (chapters.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

    @RequestMapping(value = "saveCopyTree")
    @ResponseBody
    public ResponseObject saveCopyTree(String level, String courentTreeId, Integer courseId, String[] kpos) {
        ResponseObject responseObj = new ResponseObject();
        List<Video> videos = videoResService.saveCopyTree(level, courentTreeId, courseId, kpos);
        if (null != videos && videos.size() > 0) {
            for (Video v : videos) {
                videoResService.addVideoToUerVideo(courseId, v);
            }
        }
        responseObj.setSuccess(true);
        return responseObj;
    }

    @RequestMapping(value = "addNode", method = RequestMethod.POST)
    @ResponseBody
    public TreeNode addNode(HttpServletRequest request, TreeNode treeNode) {

        Chapter chapter = new Chapter();
        chapter.setCourseId(Integer.valueOf(treeNode.getCourseId()));

        chapter.setParentId(treeNode.getpId());
        chapter.setCreatePerson(ManagerUserUtil.getName());
        chapter.setLevel(Integer.valueOf(treeNode.getContenttype()) + 1);
        chapter.setName(treeNode.getName());
        chapter.setStatus(1);
        chapter.setCreateTime(new Date());
        chapter.setDelete(false);

        videoResService.saveChapter(chapter);
        treeNode.setId(chapter.getId());
        treeNode.setContenttype(chapter.getLevel().toString());

//		if (chapter.getLevel() == 2) {
//			try {
//				this.videoResService.createCCCategory(chapter.getId(), chapter.getCourseId(),treeNode.getName());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

        return treeNode;
    }

    @RequestMapping(value = "updateNode", method = RequestMethod.POST)
    @ResponseBody
    public TreeNode updateNode(HttpServletRequest request, TreeNode treeNode) {
        videoResService.updateChapter(treeNode);
        return treeNode;
    }

    @RequestMapping(value = "deleteNode", method = RequestMethod.POST)
    @ResponseBody
    public String deleteNode(HttpServletRequest request, String id, String contenttype) {
        videoResService.deleteChapter(id);
        return "1";
    }

    /**
     * 改变节点顺序
     */
    @RequestMapping(value = "updateNodeSort", method = RequestMethod.POST)
    @ResponseBody
    public String updateNodeSort(HttpServletRequest request, LibraryVo libraryVo) {
        return videoResService.updateNodeSort(libraryVo);
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
//	@RequestMapping(value = "uploadSuccessCallback", method = RequestMethod.GET)
//	public void uploadSuccessCallback(HttpServletResponse res, String duration, String image, String status,
//			String videoid, String time, String hash) throws IOException {
//		videoResService.uploadSuccessCallback(duration, image, status, videoid, time, hash);
//		res.setCharacterEncoding("UTF-8");
//		res.setContentType("text/xml; charset=utf-8");
//		res.getWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><video>OK</video>");
//	}


    /**
     * 禁用or启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            videoResService.updateStatus(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

}
