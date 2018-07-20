package com.xczhihui.bxg.online.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.consts.MultiUrlHelper;

/**
 * BannerService:Banner业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class BannerServiceImpl extends OnlineBaseServiceImpl implements BannerService {

    
    
    
    /**
     * 查询Banner全部列表
     *
     * @return page.getItems()
     */
    @Override
    public List<BannerVo> list(Integer pageNumber, Integer pageSize, Integer type) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", type);
        Page<BannerVo> page = dao.findPageBySQL("select id, img_path as imgPath, description, img_href as imgHref, route_type as routeType, link_param as linkParam " +
                " from oe_banner2 where is_delete=0 and type = :type and status=1 order by sort ", paramMap, BannerVo.class, pageNumber, pageSize);
        page.getItems().forEach(bannerVo -> {
            bannerVo.setImgHref(MultiUrlHelper.getUrl(bannerVo.getRouteType(), MultiUrlHelper.URL_TYPE_WEB, bannerVo.getLinkParam()));
        });
        return page.getItems();
    }

    @Override
    public void updateClickCount(Integer id) {
        
        
        String sql = "update oe_banner2 set click_count=(if (click_count is null,1,click_count+1)) where id=?";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, id);

    }

}
