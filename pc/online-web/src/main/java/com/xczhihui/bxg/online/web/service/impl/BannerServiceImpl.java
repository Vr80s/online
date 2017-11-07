package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BannerService:Banner业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class BannerServiceImpl extends OnlineBaseServiceImpl implements BannerService {

    /**
     * 查询Banner全部列表
     * @return page.getItems()
     */
    @Override
    public List<BannerVo> list(Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Page<BannerVo> page = dao.findPageByHQL("from Banner where 1=1 and isDelete=0 and status=1 order by sort ", paramMap, pageNumber, pageSize);
        return page.getItems();
    }

    public void updateClickCount(Integer id) {
            String sql = "update oe_banner2 set click_count=(if (click_count is null,1,click_count+1)) where id=?";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, id);

    }

}
