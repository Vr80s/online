package com.xczhihui.operate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.CourseAnchor;
import com.xczhihui.bxg.online.common.domain.MobileBanner;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.operate.dao.MobileBannerDao;
import com.xczhihui.operate.service.MobileBannerService;
import com.xczhihui.operate.vo.MobileBannerVo;

@Service
public class MobileBannerServiceImpl extends OnlineBaseServiceImpl implements
        MobileBannerService {

    @Autowired
    private MobileBannerDao mobileBannerDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private AnchorService anchorService;

    @Override
    public Page<MobileBannerVo> findMobileBannerPage(
            MobileBannerVo mobileBannerVo, Integer pageNumber, Integer pageSize) {
        Page<MobileBannerVo> page = mobileBannerDao.findMobileBannerPage(
                mobileBannerVo, pageNumber, pageSize);
        page.getItems().forEach(vo -> {
            Map<String, String> linkData = getLinkData(vo.getRouteType(), vo.getLinkParam());
            vo.setLinkDesc(linkData.get("linkDesc"));
            vo.setMenuId(linkData.get("menuId"));
        });
        return page;
    }

    @Override
    public void addMobileBanner(MobileBannerVo mobileBannerVo) {
        String sql = "SELECT ifnull(min(seq),0) FROM oe_course_mobile_banner ";
        int sort = dao.queryForInt(sql, null) - 1;
        mobileBannerVo.setClickSum(0);
        mobileBannerVo.setStatus(0);
        ;
        sql = "INSERT INTO oe_course_mobile_banner  ( id ,  name ,  url ,  click_sum ,  "
                + "create_person ,  create_time ,  status ,  seq ,  img_path , link_type  ,banner_type, route_type, link_param) "
                + "VALUES (REPLACE(UUID(),'-',''), ?, ?, ?, ?, now(), ?, ?, ?,?,?,?,?)";
        dao.getNamedParameterJdbcTemplate()
                .getJdbcOperations()
                .update(sql,
                        new Object[]{mobileBannerVo.getName(),
                                mobileBannerVo.getUrl(),
                                mobileBannerVo.getClickSum(),
                                mobileBannerVo.getCreatePerson(),
                                mobileBannerVo.getStatus(), sort,
                                mobileBannerVo.getImgPath(),
                                mobileBannerVo.getLinkType(),
                                mobileBannerVo.getBannerType(),
                                mobileBannerVo.getRouteType(), mobileBannerVo.getLinkParam()});
    }

    @Override
    public void updateMobileBanner(MobileBannerVo mobileBannerVo) {
        String sql = "UPDATE oe_course_mobile_banner "
                + "SET name = ?, url = ?, img_path = ? ,link_type = ?, route_type = ?, link_param = ? "
                + "WHERE " + "	id = ? ";
        dao.getNamedParameterJdbcTemplate()
                .getJdbcOperations()
                .update(sql,
                        new Object[]{mobileBannerVo.getName(),
                                mobileBannerVo.getUrl(),
                                mobileBannerVo.getImgPath(),
                                mobileBannerVo.getLinkType(),
                                mobileBannerVo.getRouteType(),
                                mobileBannerVo.getLinkParam(),
                                mobileBannerVo.getId()});
    }

    @Override
    public boolean updateStatus(MobileBannerVo mobileBannerVo) {
        String sql = "SELECT count(1) FROM oe_course_mobile_banner t WHERE t.status = 1 ";
        /*
         * if(dao.queryForInt(sql) >= 5){ throw new
		 * RuntimeException("最多启用五个banner!"); }
		 */
        sql = " UPDATE oe_course_mobile_banner "
                + " SET status = abs(status - 1) " + " WHERE " + "	id = ? ";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations()
                .update(sql, new Object[]{mobileBannerVo.getId()});
        return true;
    }

    @Override
    public void deletes(String[] ids) {
        for (String id : ids) {
            String sql = "DELETE FROM oe_course_mobile_banner WHERE id = ? ";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations()
                    .update(sql, new Object[]{id});
        }
    }

    @Override
    public void updateSortUp(String id) {
        /*String sqlPre = "select seq from oe_course_mobile_banner where status = 1 and id = ? ";// 先取出他自己的顺序
		Integer mobileBannerPreSort = dao.queryForInt(sqlPre,
				new Object[] { id });

		String sqlNext = "select seq,id from oe_course_mobile_banner where status = 1 and seq > ? order by seq desc limit 1 ";// 然后取出他相邻的顺序
		Map map = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForMap(sqlNext, new Object[] { mobileBannerPreSort });
		Integer mobileBannerNextSort = Integer.valueOf(map.get("seq")
				.toString());
		String sql = "update oe_course_mobile_banner set seq = ? where id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[] { mobileBannerNextSort, id });
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update(sql,
						new Object[] { mobileBannerPreSort,
								map.get("id").toString() });*/


        String hqlPre = "from MobileBanner where  status=1 and id = ?";
        MobileBanner Pre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer PreSort = Pre.getSeq();
        Integer bannerType = Pre.getBannerType();
        String hqlNext = "from MobileBanner where seq > (select seq from MobileBanner where id= ? )  and status=1 and bannerType = ? order by seq asc";
        MobileBanner courseNext = dao.findByHQLOne(hqlNext, new Object[]{id, bannerType});
        Integer courseNextSort = courseNext.getSeq();
        Pre.setSeq(courseNextSort);
        courseNext.setSeq(PreSort);

        dao.update(Pre);
        dao.update(courseNext);
    }

    @Override
    public void updateSortDown(String id) {
		/*String sqlPre = "select seq from oe_course_mobile_banner where status = 1 and id = ? ";// 先取出他自己的顺序
		Integer mobileBannerPreSort = dao.queryForInt(sqlPre,
				new Object[] { id });

		String sqlNext = "select seq,id from oe_course_mobile_banner where status = 1 and seq < ? order by seq asc limit 1 ";// 然后取出他相邻的顺序
		Map map = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForMap(sqlNext, new Object[] { mobileBannerPreSort });
		Integer mobileBannerNextSort = Integer.valueOf(map.get("seq")
				.toString());
		String sql = "update oe_course_mobile_banner set seq = ? where id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[] { mobileBannerNextSort, id });
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update(sql,
						new Object[] { mobileBannerPreSort,
								map.get("id").toString() });*/


        String hqlPre = "from MobileBanner where  status=1 and id = ?";
        MobileBanner Pre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer PreSort = Pre.getSeq();
        Integer bannerType = Pre.getBannerType();
        String hqlNext = "from MobileBanner where seq < (select seq from MobileBanner where id= ? )  and status=1 and bannerType = ? order by seq desc";
        MobileBanner courseNext = dao.findByHQLOne(hqlNext, new Object[]{id, bannerType});
        Integer courseNextSort = courseNext.getSeq();
        Pre.setSeq(courseNextSort);
        courseNext.setSeq(PreSort);

        dao.update(Pre);
        dao.update(courseNext);
    }

    @Override
    public void updateOldData() {
        MobileBannerVo mobileBannerVo = new MobileBannerVo();
        Page<MobileBannerVo> mobileBannerPage = mobileBannerDao.findMobileBannerPage(mobileBannerVo, 1, 3000);
        List<MobileBannerVo> items = mobileBannerPage.getItems();
        for (MobileBannerVo vo : items) {
            if (vo.getRouteType() == null) {
                String url = vo.getUrl();
                Integer linkType = vo.getLinkType();
                String routeType = RouteTypeEnum.NONE.name();
                String linkParam = null;

                if (linkType != null && vo.getRouteType() == null) {
                    if (linkType == 3) {
                        routeType = RouteTypeEnum.COMMON_COURSE_DETAIL_PAGE.name();
                        if (url != null) {
                            String[] split = url.split("=");
                            if (split.length > 1) {
                                linkParam = split[1];
                            }
                        }
                    } else if (linkType == 4) {
                        routeType = RouteTypeEnum.ANCHOR_INDEX.name();
                        if (url != null) {
                            String[] split = url.split("=");
                            if (split.length > 1) {
                                linkParam = split[1];
                            }
                        }
                    } else if (linkType == 5) {
                        routeType = RouteTypeEnum.PUBLIC_COURSE_LIST_PAGE.name();
                        linkParam = url;
                    }
                    mobileBannerDao.update(vo.getId(), routeType, linkParam);
                }
            }
        }
    }

    @Override
    public Map<String, String> getLinkData(String routeType, String linkParam) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(routeType)) {
            if (StringUtils.isNotBlank(linkParam)) {
                if (routeType.equals(RouteTypeEnum.COMMON_COURSE_DETAIL_PAGE.name())) {
                    Course course = courseDao.getCourseById(Integer.parseInt(linkParam));
                    if (course != null) {
                        map.put("linkDesc", course.getGradeName());
                        map.put("menuId", String.valueOf(course.getMenuId()));
                    }
                } else if (routeType.equals(RouteTypeEnum.ANCHOR_INDEX.name())) {
                    CourseAnchor courseAnchor = anchorService.findByUserId(linkParam);
                    if (courseAnchor != null) {
                        map.put("linkDesc", courseAnchor.getName());
                    }
                } else {
                    map.put("linkDesc", linkParam);
                }
            }
        }
        return map;
    }
}
