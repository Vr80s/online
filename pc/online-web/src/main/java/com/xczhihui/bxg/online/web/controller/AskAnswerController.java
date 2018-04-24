package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.AskAnswerService;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 回答相关
 * 
 * @author Haicheng Jiang
 */
@RestController
@RequestMapping(value = "/ask/answer")
public class AskAnswerController {

	@Autowired
	private AskAnswerService service;


    /**
     * 添加问题的官方回答
     * @param askAnswerVo
     * @param s
     * @return
     */
    @RequestMapping(value = "/addOfficialAnswer",method = RequestMethod.POST)
    public ResponseObject addOfficialAnswer(AskAnswerVo askAnswerVo,HttpSession s) {
        OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
        if(u!=null) {
            askAnswerVo.setCreate_person(u.getLoginName());
			askAnswerVo.setUser_id(u.getId());
        }
        service.addOfficialAnswer(askAnswerVo,u);
        return ResponseObject.newSuccessResponseObject("添加成功！");
    }
	/**
	 * 查询问题的官方回答
	 * @param question_id
	 * @return
	 */
	@RequestMapping(value = "/findOfficialAnswer")
	public ResponseObject findOfficialAnswer(String question_id,String menu_id,HttpSession s) {
        OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		String userId= u!=null ? u.getId() : "";
		return ResponseObject.newSuccessResponseObject(service.findOfficialAnswer(question_id,menu_id,userId));

	}
	/**
	 * 修改问题的官方回答
	 * @param id
	 * @param content
	 */
	@RequestMapping(value = "/updateOfficialAnswer",method = RequestMethod.POST)
	public ResponseObject updateOfficialAnswer(String id,String content) {
        service.updateOfficialAnswer(id,content);
		return ResponseObject.newSuccessResponseObject("修改成功！");
	}
	/**
	 * 查找精彩回答
	 * @param question_id
	 * @return
	 */
	@RequestMapping(value = "/findNiceAnswers")
	public ResponseObject findNiceAnswers(String question_id,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.findNiceAnswers(u,question_id));
	}
	/**
	 * 查找最近回答
	 * @param vo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findLatestAnswers")
	public ResponseObject findLatestAnswers(AskAnswerVo vo,Integer pageNumber,Integer pageSize,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.findAnswers(u, vo, "create_time", "desc", pageNumber, pageSize));
	}
	/**
	 * 周回答排行榜
	 * @param sum
	 * @return
	 */
	@RequestMapping(value = "/findAnswersWeekRankingList")
	public ResponseObject findAnswersWeekRankingList(Integer sum) {
		return ResponseObject.newSuccessResponseObject(service.findAnswersWeekRankingList(sum));
	}
	/**
	 * 新增回答
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/addAnswer")
	public ResponseObject addAnswer(AskAnswerVo vo,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");

		vo.setCreate_person(u.getLoginName());
		vo.setCreate_head_img(u.getSmallHeadPhoto());
		vo.setCreate_nick_name(u.getName());
		vo.setCopyright(vo.getCopyright() == null ? false : vo.getCopyright());
		service.addAnswer(vo,u);
		return ResponseObject.newSuccessResponseObject("回答成功！");
	}
	/**
	 * 采纳/取消采纳为最佳答案
	 * @param answer_id
	 * @return
	 */
	@RequestMapping(value = "/acceptAnswer")
	public ResponseObject  acceptAnswer(String answer_id,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.addAcceptAnswer(answer_id,u.getId()));
	}
	/**
	 * 点赞/取消点赞
	 * @param answer_id
	 * @return
	 */
	@RequestMapping(value = "/praiseAnswer")
	public ResponseObject  praiseAnswer(String answer_id,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.addPraiseAnswer(answer_id, u.getLoginName()));
	}
	/**
	 * 收藏/取消收藏
	 * @param question_id
	 * @return
	 */
	@RequestMapping(value = "/collection")
	public ResponseObject collection(String question_id,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.addCollection(question_id, u.getLoginName(),u.getId()));
	}


	/**
	 * 删除回答信息
	 * @param answerId 回答信息的id号
	 * @return
	 */
	@RequestMapping(value = "/deleteAnswerById",method= RequestMethod.GET)
	public ResponseObject deleteAnswerById(String  answerId,HttpServletRequest request) {
		//获取当前登录用户信息
		OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
		return ResponseObject.newSuccessResponseObject(service.deleteAnswerById(request, u, answerId));
	}


	/**
	 * 查找点赞数最多的回答以及回答的问题信息
	 * 重点：为院校项目准备的接口  不要随便改动
	 * @return
	 */
	@RequestMapping(value = "/findMaxPraiseAnswer",method= RequestMethod.GET)
	public ResponseObject  findMaxPraiseAnswer(Integer pageNumber, Integer pageSize){
		return ResponseObject.newSuccessResponseObject(service.findMaxPraiseAnswer(pageNumber, pageSize));
	}
}
