package courseApply;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.course.service.CourseApplyService;

import common.BaseJunit4Test;

public class CourseApply  extends BaseJunit4Test {

	@Autowired
	private CourseApplyService courseApplyService;
	
	public SimpleHibernateDao dao;
	
	@Test
	public void test() {
		
		courseApplyService.saveCollectionUpdateCollectionId(1000, 1000);
		
		System.out.println("这是一首简单的小情歌");
	}

}
