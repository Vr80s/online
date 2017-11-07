package com.xczhihui.bxg.online.manager.common.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.manager.utils.CourseTreeVo;
import com.xczhihui.bxg.online.manager.utils.ZtreeVo;

@Controller
@RequestMapping("menu")
public class MenuController {
	
	@RequestMapping(value="index", method=RequestMethod.GET)
    public String index(HttpServletRequest request) {
		return "test/menu";
	}
	
	@RequestMapping(value = "findDataForTree")
	@ResponseBody
	public List<ZtreeVo> findDataForTree(ZtreeVo ztree, String courseId)
	{
		List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
		ztree = new ZtreeVo();
		String name = "";		
		
		if(courseId != null)
		{
			if("1".equals(courseId))
			{
				name = "Java";
			}
			else if("2".equals(courseId))
			{
				name = "PHP";
			}
			else if("3".equals(courseId))
			{
				name = "Android";
			}
			else
			{
				return ztreeVos;
			}
		}
		else{
			return ztreeVos;
		}
		String id = UUID.randomUUID().toString();
		if(ztree.getId() == null)
		{
			ztree = new ZtreeVo();
			ztree.setId("3e4r5g");
			ztree.setName(name+"程序设计基础教程");
			ztree.setIsParent(true);
			ztree.setType("1");
			ztree.setOpen(true);
			
			ZtreeVo ztree2 = new ZtreeVo();
			List<ZtreeVo> list = new ArrayList<ZtreeVo>();
			List<ZtreeVo> list2 = new ArrayList<ZtreeVo>();
			
			ztree2 = new ZtreeVo();
			ztree2.setId("3e4t5i");
			ztree2.setName("第二章 "+name+"基本语法");
			ztree2.setIsParent(true);
			ztree2.setOpen(true);
			ztree2.setType("2");
			
			list2 = new ArrayList<ZtreeVo>();
			for(int i=4;i<9;i++)
			{
				id = "3e4t5i"+i;
				ZtreeVo ztree3 = new ZtreeVo();
				ztree3.setId(id);
				ztree3.setName(name+"第"+i+"节");
				ztree3.setpId(ztree2.getId());
				ztree3.setType("3");
				list2.add(ztree3);
			}
			ztree2.setChildren(list2);
//			ztree2.setpId(ztree.getId());
//			ztreeVos.add(ztree2);
			list.add(ztree2);
			
			ztree2 = new ZtreeVo();
			ztree2.setId("3e4t5h");
			ztree2.setName("第一章 "+name+"开篇");
			ztree2.setIsParent(true);
			ztree2.setType("2");
			ztree2.setOpen(true);
			
			list2 = new ArrayList<ZtreeVo>();
			for(int i=1;i<3;i++)
			{
				id = "3e4t5y"+i;
				ZtreeVo ztree3 = new ZtreeVo();
				ztree3.setId(id);
				ztree3.setName(name+"第"+i+"节");
				ztree3.setpId(ztree2.getId());
				ztree3.setOpen(true);
				ztree3.setType("3");
				
				List<ZtreeVo> list3 = new ArrayList<ZtreeVo>();
				for(int j=16;j<18;j++)
				{
					id = i+"3e4t5i"+j;
					ZtreeVo ztree4 = new ZtreeVo();
					ztree4.setId(id);
					ztree4.setName(name+"技术介绍"+j);
					ztree4.setpId(ztree3.getId());
					ztree4.setType("4");
					list3.add(ztree4);
				}
				ztree3.setChildren(list3);
				list2.add(ztree3);
			}
			ztree2.setChildren(list2);
			list.add(ztree2);
			
			
			
			ztree.setChildren(list);
			ztreeVos.add(ztree);			
		}

		return ztreeVos;
	}
	
	@RequestMapping(value = "findDataForTreeEdit")
	@ResponseBody
	public List<CourseTreeVo> findDataForTreeEdit(ZtreeVo ztree)
	{
		List<CourseTreeVo> courseTreeVos = new ArrayList<CourseTreeVo>();
		for(int x=1;x<3;x++)
		{
			String name = "";
			if(x==1)
			{
				name = "Java";
			}
			else if(x==2)
			{
				name = "PHP";
			}
			CourseTreeVo courseTreeVo = new CourseTreeVo();
			List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
			ztree = new ZtreeVo();
			ztree.setId("3e4r5g");
			ztree.setName(name+"程序设计基础教程");
			ztree.setIsParent(true);
			ztree.setOpen(true);
			ztree.setNocheck(true);
			
			List<ZtreeVo> list = new ArrayList<ZtreeVo>();
			
			ZtreeVo ztree2 = new ZtreeVo();
			ztree2 = new ZtreeVo();
			ztree2.setId("3e4t5h");
			ztree2.setName("第一章 "+name+"开篇");
			ztree2.setIsParent(true);
			ztree2.setOpen(true);
			ztree2.setNocheck(true);			
			List<ZtreeVo> list2 = new ArrayList<ZtreeVo>();
			for(int i=1;i<3;i++)
			{
				String id = "3e4t5y"+i;
				ZtreeVo ztree3 = new ZtreeVo();
				ztree3.setId(id);
				ztree3.setName(name+"第"+i+"节");
				ztree3.setpId(ztree2.getId());
				ztree3.setType("3");
				ztree3.setNocheck(true);
				ztree3.setOpen(true);
				
				List<ZtreeVo> list3 = new ArrayList<ZtreeVo>();
				for(int j=16;j<18;j++)
				{
					id = i+"3e4t5i"+j;
					ZtreeVo ztree4 = new ZtreeVo();
					ztree4.setId(id);
					ztree4.setName(name+"技术介绍"+j);
					ztree4.setpId(ztree3.getId());
					ztree4.setType("4");
					ztree4.setNocheck(true);
					list3.add(ztree4);
				}
				ztree3.setChildren(list3);
				list2.add(ztree3);
			}
			ztree2.setChildren(list2);			
			
			list.add(ztree2);
			ztree.setChildren(list);
			ztreeVos.add(ztree);			
			courseTreeVo.setId(x+"");			
			courseTreeVo.setZtreeVos(ztreeVos);			
			courseTreeVos.add(courseTreeVo);
		}
		
		return courseTreeVos;
	}
	
	@RequestMapping(value = "findDataForTree2")
	@ResponseBody
	public List<ZtreeVo> findDataForTree2(ZtreeVo ztree)
	{
		List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
		if(ztree.getId() == null)
		{
//			ztree = new ZtreeVo();
//			ztree.setId(UUID.randomUUID().toString());
//			ztree.setName("PHP程序设计基础教程");
//			ztree.setIsParent(true);
//			ztreeVos.add(ztree);
			String name = "java";
			ztree = new ZtreeVo();
			ztree.setId("3e4r5g");
			ztree.setName(name+"程序设计基础教程");
			ztree.setIsParent(true);
			ztree.setType("1");
			ztree.setOpen(true);
			ztree.setNocheck(true);
			
			List<ZtreeVo> list = new ArrayList<ZtreeVo>();
			
			ZtreeVo ztree2 = new ZtreeVo();
			ztree2.setId("3e4t5h");
			ztree2.setName("第一章 "+name+"开篇");
			ztree2.setIsParent(true);
			ztree2.setType("2");
			ztree2.setOpen(true);
			ztree2.setNocheck(true);
			
			String id = "";
			List<ZtreeVo> list2 = new ArrayList<ZtreeVo>();
			for(int i=1;i<3;i++)
			{
				id = "3e4t5y"+i;
				ZtreeVo ztree3 = new ZtreeVo();
				ztree3.setId(id);
				ztree3.setName(name+"第"+i+"节");
				ztree3.setpId(ztree2.getId());
				ztree3.setOpen(true);
				ztree3.setType("3");
				ztree3.setNocheck(true);
				
				List<ZtreeVo> list3 = new ArrayList<ZtreeVo>();
				for(int j=16;j<18;j++)
				{
					id = i+"3e4t5i"+j;
					ZtreeVo ztree4 = new ZtreeVo();
					ztree4.setId(id);
					ztree4.setName(name+"技术介绍"+j);
					ztree4.setpId(ztree3.getId());
					ztree4.setType("4");
					ztree4.setNocheck(true);
					list3.add(ztree4);
				}
				ztree3.setChildren(list3);
				list2.add(ztree3);
			}
			ztree2.setChildren(list2);
			list.add(ztree2);
			
			ztree.setChildren(list);
			ztreeVos.add(ztree);
		}
		else
		{
			ZtreeVo ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("web技术介绍");
			ztreeVos.add(ztree2);
			
			ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("JAVA技术介绍");
			ztreeVos.add(ztree2);
		}
		return ztreeVos;
	}
	
	@RequestMapping(value = "findDataForTree3")
	@ResponseBody
	public List<ZtreeVo> findDataForTree3(ZtreeVo ztree)
	{
		List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
		String name = "java";
		if(ztree.getId() == null)
		{
			ztree = new ZtreeVo();
			ztree.setId("3e4r5g");
			ztree.setName(name+"知识体系");
			ztree.setIsParent(true);
			ztree.setNocheck(true);
//			ztree.setOpen(true);
			ztree.setType("1");
			ztreeVos.add(ztree);
		}
		else if("1".equals(ztree.getType()))
		{
			ZtreeVo ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("第一章 web技术介绍");
			ztree2.setIsParent(true);
			ztree2.setNocheck(true);
			ztree2.setType("2");
			ztreeVos.add(ztree2);
			
			ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("第二章 JAVA技术介绍");
			ztree2.setIsParent(true);
			ztree2.setNocheck(true);
			ztree2.setType("2");
			ztreeVos.add(ztree2);
		}
		else if("2".equals(ztree.getType()))
		{
			ZtreeVo ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("第1节 什么是web");
			ztree2.setIsParent(true);
			ztree2.setNocheck(true);
			ztree2.setType("3");
			ztreeVos.add(ztree2);
			
			ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("第2节 web的含义");
			ztree2.setIsParent(true);
			ztree2.setNocheck(true);
			ztree2.setType("3");
			ztreeVos.add(ztree2);
		}
		else
		{
			ZtreeVo ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("web知识点");
			ztree2.setNocheck(true);
			ztree2.setType("4");
			ztreeVos.add(ztree2);
			
			ztree2 = new ZtreeVo();
			ztree2.setpId(ztree.getId());
			ztree2.setId(UUID.randomUUID().toString());
			ztree2.setName("android知识点");
			ztree2.setNocheck(true);
			ztree2.setType("4");
			ztreeVos.add(ztree2);
		}
		return ztreeVos;
	}

}
