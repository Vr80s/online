package xczh.test.server.course;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.ShareInfoVo;

import test.BaseJunit4ServerTest;

/**
 * 医馆入驻测试类
 */
public class CourseMapperTest extends BaseJunit4ServerTest {

	@Autowired
	public ICourseService  courseService;
	
	
	@Test
	public void ddd(){
		
		ShareInfoVo sv = 
				courseService.selectShareInfoByType(4,"8356a18d548e400d8e8b9b8aaa9d03db");
	    
		System.out.println(sv.toString());
	}
	
	

}