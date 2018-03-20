package com.xczhihui.bxg.online.manager.system.dao;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Otherlink;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import com.xczhihui.bxg.online.manager.system.vo.OtherLinkVo;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;

/**
 * link dao
 * @author duanqh
 *
 */
@Repository
public class LinkDao extends HibernateDao<Otherlink>{
	
	/**
	 * 根据条件查询一页数据
	 * @param groups
	 * @param page
	 * @return
	 */
	public PageVo findPagevideo(Groups groups, PageVo page){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id as id,"
				+ "a.name as orgname,"
				+ "a.url as url,"
				+ "a.logo as logo,"
				+ "a.status as status,"
				+ "a.is_delete as isDelete,"
				+ "a.create_person as createPerson,"
				+ "a.create_time as createTime,"
				+ "a.sort as sort from oe_otherlink a");
		page = findPageByGroups(groups, page, sql.toString());
		return page;
	}
	
	/**
	 * 根据id伪删除一条数据
	 * @param id
	 */
	public void deleteById(Integer id){
		Otherlink link = new Otherlink();
		link = super.get(id, Otherlink.class);
		link.setDelete(true);
		super.delete(link);
	}
	
	/**
	 * 根据id批量删除
	 * @param ids
	 * @return
	 */
	public void deleteBatch(String... ids) {
		for(String id:ids){
			String hqlPre="from Otherlink where  isDelete=0 and id = ?";
			Otherlink otherlink= this.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
            if(otherlink !=null){
            	otherlink.setDelete(true);
                 this.update(otherlink);
            }
        }
	}
	/**
	 * 查找当前数据的上一行
	 * 
	 * @param link 友情链接
	 * @return 响应实体
	 */

	public Otherlink getPreEntity(Otherlink link) {
		String sql="from Otherlink where sort < ? order by sort desc";
		return this.findByHQLOne(sql, new Object[] {link.getSort()});
	}
	
	/**
	 * 查找当前数据的下一行
	 * 
	 * @param link 友情链接
	 * @return 响应实体
	 */
	public Otherlink getNextEntity(Otherlink link) {
		String sql="from Otherlink where sort > ? order by sort asc";
		return this.findByHQLOne(sql, new Object[] {link.getSort()});
	}
	
	/**
	 * 查询友情链接（分页）。<p>
	 * 
	 * @param paramMap 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<OtherLinkVo> findOtherLinkPage(Map<String, Object> paramMap, int pageNumber, int pageSize) {
		StringBuffer sbSql = new StringBuffer();
		String endStr = "";
		sbSql.append(" select id,orgname,url,logo,is_delete,create_person,create_time,sort,status");
		sbSql.append(" from oe_otherlink");
		sbSql.append(" where is_delete = false");
		if(paramMap != null && paramMap.size()>0){
			for(Entry<String, Object> entry:paramMap.entrySet()){
				Object paramValue = entry.getValue();
				if(paramValue != null && StringUtils.hasText(paramValue.toString())){
					if("urlOrcreatPer".equals(entry.getKey())) {
                        sbSql.append(" AND (oe_otherlink.create_person like '%" + entry.getValue() + "%' OR oe_otherlink.url like '%" + entry.getValue() + "%')");
                    }
					if("timeEnd".equals(entry.getKey())) {
                        endStr = " AND '" + entry.getValue() + "')";
                    }
					if("timeStart".equals(entry.getKey())) {
                        sbSql.append(" AND (oe_otherlink.create_time BETWEEN '" + entry.getValue() + "'");
                    }
					
				}
			}
		}
		sbSql.append(endStr);
		sbSql.append(" order by sort asc");
		
		return this.findPageBySQL(sbSql.toString(), null, OtherLinkVo.class, pageNumber, pageSize);
	}

}
