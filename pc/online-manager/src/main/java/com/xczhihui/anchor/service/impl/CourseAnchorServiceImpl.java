package com.xczhihui.anchor.service.impl;

import com.xczhihui.anchor.dao.AnchorDao;
import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.anchor.vo.AnchorIncomeVO;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.vhall.VhallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *   CourseServiceImpl:课程业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class CourseAnchorServiceImpl extends OnlineBaseServiceImpl implements AnchorService {

    @Autowired
    private AnchorDao anchorDao;

    @Override
	public Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor, int pageNumber, int pageSize) {
    	Page<CourseAnchor> page = anchorDao.findCourseAnchorPage(courseAnchor, pageNumber, pageSize);
    	return page;
	}

    @Override
	public Page<CourseAnchor> findCourseAnchorRecPage(CourseAnchor courseAnchor, int pageNumber, int pageSize) {
    	Page<CourseAnchor> page = anchorDao.findCourseAnchorRecPage(courseAnchor, pageNumber, pageSize);
    	return page;
	}

	@Override
	public CourseAnchor findCourseAnchorById(Integer id) {
		return anchorDao.findCourseAnchorById(id);
	}

	@Override
	public CourseAnchor findCourseAnchorByUserId(String userId) {
		return anchorDao.findCourseAnchorByUserId(userId);
	}

	@Override
	public void updateCourseAnchor(CourseAnchor courseAnchor) {
		CourseAnchor ca = dao.findOneEntitiyByProperty(CourseAnchor.class, "id", courseAnchor.getId());
		ca.setLiveDivide(courseAnchor.getLiveDivide());
		ca.setVodDivide(courseAnchor.getVodDivide());
		ca.setOfflineDivide(courseAnchor.getOfflineDivide());
		ca.setGiftDivide(courseAnchor.getGiftDivide());
		dao.update(ca);
	}

	@Override
	public void updatePermissions(Integer id) {
		CourseAnchor ca = dao.findOneEntitiyByProperty(CourseAnchor.class, "id", id);
		OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "id", ca.getUserId());
		if(!ca.getStatus()){
			//设置微吼子帐号权限
			VhallUtil.changeUserPower(u.getVhallId(), "1", "0");
		}else{
			VhallUtil.changeUserPower(u.getVhallId(), "0", "0");
		}
		ca.setStatus(!ca.getStatus());
		dao.update(ca);
	}

	@Override
	public Page<AnchorIncomeVO> findCourseAnchorIncomePage(CourseAnchor searchVo, int currentPage, int pageSize) {
		Page<AnchorIncomeVO> page = anchorDao.findCourseAnchorIncomePage(searchVo, currentPage, pageSize);
		return page;
	}

	@Override
	public void updateRec(String[] ids,int isRecommend) {
		List<String> ids2 = new ArrayList();
		//如果是要推荐 那么就验证 推荐数量是否大于4
		if(isRecommend == 1){
			//校验是否被引用
			String hqlPre="from CourseAnchor where deleted=0 and isRecommend = 1";
			List<CourseAnchor> list= dao.findByHQL(hqlPre);
			if(list.size() > 0){
				for(int i = 0;i<ids.length;i++){
					int j = 0;
					Iterator<CourseAnchor> iterator = list.iterator();
					while(iterator.hasNext()){
						//剔除本次推荐的与已经推荐的重复的
						CourseAnchor courseAnchor = iterator.next();
						//如果存在就把他剔除掉从list中
						if(courseAnchor.getId() == Integer.parseInt(ids[i])){
							j =1;
						}
					}
					if(j == 0){
						ids2.add(ids[i]);
					}
				}
			}else{
				for(int i=0;i<ids.length;i++){
					ids2.add(ids[i]);
				}
			}
//			//已经存在的数量 +  即将添加的数量
//			if((list.size()+ids2.size()) > 12){
//				//取消推荐数目限制
////            	return false;
//			}
		}else{//如果是取消推荐
			for(int i=0;i<ids.length;i++){
				ids2.add(ids[i]);
			}
		}
		String sql="select ifnull(max(recommend_sort),0) from course_anchor where  deleted=0 and is_recommend = 1";
		//最小的排序
		int i = dao.queryForInt(sql,null);
		for(String id:ids2){
			if(id == "" || id == null){
				continue;
			}
			i = i +1;
			String hqlPre="from CourseAnchor where  deleted = 0 and id = ?";
			CourseAnchor courseAnchor= dao.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
			if(courseAnchor !=null){
				courseAnchor.setIsRecommend(isRecommend);
				if(isRecommend==1){
					courseAnchor.setRecommendSort(i);
				}
				dao.update(courseAnchor);
			}
		}
	}
	@Override
	public void updateSortUpRec(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre="from CourseAnchor where deleted=0 and id = ?";
		CourseAnchor courseAnchorPre= dao.findByHQLOne(hqlPre,new Object[] {id});
		Integer courseAnchorPreSort=courseAnchorPre.getRecommendSort();

		String hqlNext="from CourseAnchor where recommendSort > (select recommendSort from CourseAnchor where id= ? )  and deleted=0 and isRecommend = 1 order by recommendSort asc";
		CourseAnchor courseAnchorNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		Integer courseAnchorNextSort=courseAnchorNext.getRecommendSort();

		courseAnchorPre.setRecommendSort(courseAnchorNextSort);
		courseAnchorNext.setRecommendSort(courseAnchorPreSort);

		dao.update(courseAnchorPre);
		dao.update(courseAnchorNext);

	}

	@Override
	public void updateSortDownRec(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre="from CourseAnchor where deleted=0 and id = ?";
		CourseAnchor courseAnchorPre= dao.findByHQLOne(hqlPre,new Object[] {id});
		Integer courseAnchorPreSort=courseAnchorPre.getRecommendSort();

		String hqlNext="from CourseAnchor where recommendSort < (select recommendSort from CourseAnchor where id= ? ) and isRecommend = 1 and deleted=0 order by recommendSort desc";
		CourseAnchor courseAnchorNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		Integer courseAnchorNextSort=courseAnchorNext.getRecommendSort();

		courseAnchorPre.setRecommendSort(courseAnchorNextSort);
		courseAnchorNext.setRecommendSort(courseAnchorPreSort);

		dao.update(courseAnchorPre);
		dao.update(courseAnchorNext);
	}
}
