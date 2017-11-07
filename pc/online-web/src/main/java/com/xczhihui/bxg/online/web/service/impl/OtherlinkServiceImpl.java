package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.service.OtherlinkService;
import com.xczhihui.bxg.online.web.vo.OtherlinkVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   OtherlinkServiceImpl:友情链接业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class OtherlinkServiceImpl extends OnlineBaseServiceImpl implements OtherlinkService {
    @Override
    public List<OtherlinkVo> getOtherLink(Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 50 : pageSize;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer and = new StringBuffer();
        Page<OtherlinkVo> page = dao.findPageByHQL("from Otherlink where 1=1 and status=1  and isDelete=0  order by sort", paramMap, pageNumber, pageSize);
        return page.getItems();
    }
}
