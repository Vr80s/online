package com.xczhihui.cloudClass.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Chapter;
import com.xczhihui.bxg.online.common.domain.Video;
import com.xczhihui.cloudClass.vo.LibraryVo;
import com.xczhihui.cloudClass.vo.TreeNode;
import com.xczhihui.cloudClass.vo.VideoResVo;

public interface VideoResService {

	public Page<VideoResVo> findPage(VideoResVo searchVo, int currentPage,
			int pageSize);

	public List<TreeNode> getChapterTree(String courseId, String courseName);

	public List<TreeNode> getChapterTreeCriticize(String courseId,
			String courseName);

	public List<TreeNode> getChapterTreeNotes(String courseId, String courseName);

	public List<TreeNode> getChapterTreeBarrier(String courseId,
			String courseName);

	public List<TreeNode> getChapterTreeBarrierAdd(String courseId,
			String courseName);

	public List<TreeNode> getChapterTreeExamPaperAdd(String courseId,
			String courseName);

	public void saveChapter(Chapter chapter);

	public void updateChapter(TreeNode treeNode);

	public void deleteChapter(String id);

	public void addVideo(Video video);

	public void updateVideo(Video video);

	public void deleteVideoById(String id);

	public void deletes(String[] _ids);

	public String updateNodeSort(LibraryVo treeNode);

	public void updateSortUp(String id);

	public void updateSortDown(String id);

	public List<Chapter> findVideoUse(String NodeId, Integer courseId);

	/**
	 * 视频处理成功的回调
	 * 
	 * @param duration
	 * @param image
	 * @param status
	 * @param videoid
	 * @param time
	 * @param hash
	 */
	// public void uploadSuccessCallback(String duration,String image,String
	// status,String videoid,String time,String hash);
	/**
	 * 创建CC视频分类
	 * 
	 * @param id
	 * @param courseId
	 * @param name
	 */
	// public void createCCCategory(String id, Integer courseId,String name);
	/**
	 * 修改CC视频分类
	 * 
	 * @param id
	 * @param oldname
	 * @param newname
	 */
	// public void updateCCCategory(String id,String oldname,String newname);

	public void updateStatus(String id);

	public List<Video> saveCopyTree(String level, String courentTreeId,
			Integer courseId, String[] kpos);

	public void addVideoToUerVideo(Integer courseId, Video video);

	public void updateStatusEnable(String id);

	public void updateStatusDisable(String id);

}
