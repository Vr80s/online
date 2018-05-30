package com.xczhihui.bxg.online.web.controller.index;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.service.LiveService;
import com.xczhihui.bxg.online.web.service.MenuService;
import com.xczhihui.bxg.online.web.service.OtherlinkService;
import com.xczhihui.bxg.online.web.service.StudentStoryService;
import com.xczhihui.bxg.online.web.vo.OpenCourseVo;

@Controller
@RequestMapping(value = "/index")
public class HomeController extends AbstractFtlController {

	@Autowired
	private BannerService  bannerService;
	
	@Autowired
    private LiveService liveService;
	
	@Autowired
	private StudentStoryService studentStoryService;
	
	@Autowired
	private OtherlinkService otherlinkService;
	
	 @Autowired
     private MenuService  menuService;


	
	@Value("${web.url}")
	private String  webUrl;

	
	/**
	 * 推荐页面
	 * 
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 */
	@RequestMapping(value = "recommendation", method = RequestMethod.POST)
	public ModelAndView recommendation() throws InvocationTargetException, IllegalAccessException {
		
		ModelAndView view = new ModelAndView("school/list/school_index");
		
		//截取等号后边的
		/**
		 * banner图
		 */
		 //2.主页banner3.头条banner4.创业banner5.海外banner
		 view.addObject("bannerList",bannerService.list(null,null,2));
		
		 //最近的一次直播
		 view.addObject("indexLive",liveService.getIndexLive());
	
		 //直播活动
		 OnlineUser user = getCurrentUser();
         String userId = null;
         if (user != null) {
            userId = user.getId();
         }
	     List<OpenCourseVo> openCourses = liveService.getOpenCourse(null, userId);
	     view.addObject("liveActivity",openCourses);
	     
	     //查询分类
	     view.addObject("menuAll",menuService.getAllMenu(null,null,"1"));
	     
	     
	     //查询此分类下的课程
	     
	     
	     //查询线下课列表
	     
	     
	     
	     //学员故事
	     view.addObject("studentStory",studentStoryService.findListByIndex());
	     
	     //友情连接
	     view.addObject("otherlink", otherlinkService.getOtherLink(null,null));
	     
	     
	     
		
		return view;
	}
}
