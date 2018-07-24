package com.xczhihui.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.common.exception.IpandaTcmException;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.mobile.dao.MobileSearchDao;
import com.xczhihui.mobile.service.MobileSearchService;
import com.xczhihui.mobile.vo.MobileSearchVo;

/**
 * 题库service实现类
 *
 * @author snow
 */
@Service("mobileSearchService")
public class MobileSearchServiceImpl extends OnlineBaseServiceImpl implements
        MobileSearchService {
    @Autowired
    private MobileSearchDao mobileSearchDao;

    @Override
    public Page<MobileSearchVo> findMobileSearchPage(
            MobileSearchVo mobileSearchVo, int pageNumber, int pageSize) {
        Page<MobileSearchVo> page = mobileSearchDao.findMobileSearchPage(
                mobileSearchVo, pageNumber, pageSize);
        return page;
    }

    @Override
    public MobileSearchVo findMobileSearchByNameAndByType(String name,
                                                          Integer type) {
        DetachedCriteria dc = DetachedCriteria.forClass(MobileSearchVo.class);
        dc.add(Restrictions.eq("name", name));
        dc.add(Restrictions.eq("searchType", type));
        return mobileSearchDao.findEntity(dc);
    }

    @Override
    public int getMaxSort() {
        return mobileSearchDao.getMaxSort();
    }

    @Override
    public void save(MobileSearchVo mobileSearchVo) {
        mobileSearchDao.save(mobileSearchVo);
    }

    @Override
    public void update(MobileSearchVo mobileSearchVo) {
        mobileSearchDao.update(mobileSearchVo);
    }

    @Override
    public MobileSearchVo findById(String parseInt) {
        return mobileSearchDao.findById(parseInt);
    }

    @Override
    public boolean exists(MobileSearchVo existsEntity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String deletes(String[] _ids) {
        String msg = "";
        for (String id : _ids) {
            msg = mobileSearchDao.deleteById(id);
        }
        return msg;
    }

    @Override
    public void updateStatus(String id) {
        MobileSearchVo scoreType = mobileSearchDao.findById(id);
        if (scoreType.getStatus() != null && scoreType.getStatus() == 1) {  //禁用
            scoreType.setStatus(0);
        } else {                      //启用
            scoreType.setStatus(1);
        }
        mobileSearchDao.update(scoreType);
    }
    
    
    @Override
    public Integer updateStatus1(String id) {
        MobileSearchVo scoreType = mobileSearchDao.findById(id);
        if (scoreType.getStatus() != null && scoreType.getStatus() == 1) {  //禁用
            scoreType.setStatus(0);
        } else {                      //启用
            if(scoreType.getSearchType().equals(1) || scoreType.getSearchType().equals(3)) {
                //查看下是否已经有启用的啦
                String hqlPre = "from MobileSearchVo where  searchType = ? and isDelete=0 and status = 1 and id != ?";
                MobileSearchVo ms = dao.findByHQLOne(hqlPre,new Object[]{scoreType.getSearchType(),Integer.parseInt(id)});
                if(ms!=null) {
                    return 1000;
                }
            }
            scoreType.setStatus(1);
        }
        mobileSearchDao.update(scoreType);
        
        return 1001;
    }
    
    @Override
    public void updateStatus2(String id) {
        
        MobileSearchVo scoreType = mobileSearchDao.findById(id);
        if(scoreType ==null) {
            throw new IpandaTcmException("获取信息有误");
        }
        if(scoreType.getStatus().equals(1)) {
            throw new IpandaTcmException("已经是启用状态了啊");
        }
        Integer searchType = scoreType.getSearchType();
        //更改同类型的为禁用
        String sql = " UPDATE  oe_mobile_search  SET status=0 WHERE search_type = :searchType";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("searchType", searchType);
        dao.getNamedParameterJdbcTemplate().update(sql, params);
        
        //更改自己的为启用
        String sql1 = " UPDATE  oe_mobile_search  SET status=1 WHERE id = :id";
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("id", id);
        dao.getNamedParameterJdbcTemplate().update(sql1, params1);
        
    }
    

    @Override
    public void updateSortUp(Integer id) {
        String hqlPre = "from MobileSearchVo where  isDelete=0 and status = 1 and id = ?";
        MobileSearchVo ProjectPre = dao.findByHQLOne(hqlPre,
                new Object[]{id});
        Integer ProjectPreSort = ProjectPre.getSeq();

        String hqlNext = "from MobileSearchVo where seq < (select seq from MobileSearchVo where id= ? )  and isDelete=0 and status = 1 order by seq desc";
        MobileSearchVo ProjectNext = dao.findByHQLOne(hqlNext,
                new Object[]{id});
        Integer ProjectNextSort = ProjectNext.getSeq();

        ProjectPre.setSeq(ProjectNextSort);
        ProjectNext.setSeq(ProjectPreSort);

        dao.update(ProjectPre);
        dao.update(ProjectNext);
    }

    @Override
    public void updateSortDown(Integer id) {
        String hqlPre = "from MobileSearchVo where  isDelete=0 and status = 1 and id = ?";
        MobileSearchVo ProjectPre = dao.findByHQLOne(hqlPre,
                new Object[]{id});
        Integer ProjectPreSort = ProjectPre.getSeq();
        String hqlNext = "from MobileSearchVo where seq > (select seq from MobileSearchVo where id= ? )  and isDelete=0 and status = 1 order by seq asc";
        MobileSearchVo ProjectNext = dao.findByHQLOne(hqlNext,
                new Object[]{id});
        Integer ProjectNextSort = ProjectNext.getSeq();

        ProjectPre.setSeq(ProjectNextSort);
        ProjectNext.setSeq(ProjectPreSort);

        dao.update(ProjectPre);
        dao.update(ProjectNext);
    }



}
