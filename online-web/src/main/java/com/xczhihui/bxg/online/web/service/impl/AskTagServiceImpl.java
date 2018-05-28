package com.xczhihui.bxg.online.web.service.impl;/**
 * Created by admin on 2016/9/19.
 */

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.AskTagDao;
import com.xczhihui.bxg.online.web.service.AskTagService;
import com.xczhihui.bxg.online.web.vo.AskTagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签业务层接口实现类
 *
 * @author 康荣彩
 * @create 2016-09-19 16:26
 */
@Service
public class AskTagServiceImpl extends OnlineBaseServiceImpl implements AskTagService {

    @Autowired
    private AskTagDao  askTagDao;

    /**
     * 根据学科ID号查找对应的标签
     * @param menuId
     * @return
     */
    @Override
    public List<AskTagVo> getTagsByMenuId(Integer menuId) {
        return askTagDao.getTagsByMenuId(menuId);
    }
}
