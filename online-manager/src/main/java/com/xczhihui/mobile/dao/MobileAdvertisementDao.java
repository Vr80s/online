package com.xczhihui.mobile.dao;

import com.xczhihui.bxg.online.common.domain.Advertisement;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.mobile.vo.MobileAdvertisementVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("mobileAdvertisementDao")
public class MobileAdvertisementDao extends HibernateDao<MobileAdvertisementVo> {

    public Page<MobileAdvertisementVo> findMobileAdvertisementPage(
            MobileAdvertisementVo mobileAdvertisementVo, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select oma.*,ou. NAME createPerson from oe_mobile_advertisement oma left JOIN user ou ON (oma.create_person = ou.login_name) where 1 = 1  ");
        /*if (mobileSearchVo.getSearchType() != null
                && !"".equals(mobileSearchVo.getSearchType())) {
            sql.append(" and search_type = :type ");
            paramMap.put("type", mobileSearchVo.getSearchType());
        }*/
        sql.append(" order by  status desc,create_time desc ");
        return this.findPageBySQL(sql.toString(), paramMap,
                MobileAdvertisementVo.class, pageNumber, pageSize);
    }



    public Advertisement findById(String parseInt) {
        StringBuilder sql = new StringBuilder(
                "select * from oe_mobile_advertisement where id=:id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", parseInt);
        List<Advertisement> vos = this
                .getNamedParameterJdbcTemplate()
                .query(sql.toString(), params,
                        BeanPropertyRowMapper.newInstance(Advertisement.class));
        if (vos.size() > 0) {
            return vos.get(0);
        }
        return null;
    }

    public String deleteById(String id) {
        String s = "";
        String deleteSql = " update oe_mobile_search set is_delete=1 where  id = :id ";
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("id", id);
        this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
        s = "删除成功";
        return s;
    }

}
