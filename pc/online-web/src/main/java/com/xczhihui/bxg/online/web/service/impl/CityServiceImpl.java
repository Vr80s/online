package com.xczhihui.bxg.online.web.service.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.api.service.CityService;
import com.xczhihui.bxg.online.api.vo.UserAddressManagerVo;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;

@Service
public class CityServiceImpl  extends OnlineBaseServiceImpl implements CityService {

	@Override
	public List<Map<String, Object>> getNation() throws SQLException {
		//String sql = "select province_id,province_name from place_province";
		String sql = "select cid,name,code from ht_location where level = 2";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		return dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
	}
	
	@Override
	public List<Map<String, Object>> getProvince(int parseInt) throws SQLException {
		//String sql = "select city_id,city_name from place_city where province_id = ?";
		String sql = "select cid,name,code from ht_location where level = 3 and lin = :pi";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pi",parseInt);
		return dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
	}
	@Override
	public List<Map<String, Object>> getCity(int parseInt) throws SQLException {
		//String sql = "select city_id,city_name from place_city where province_id = ?";
		String sql = "select cid,name,code from ht_location where level = 4 and lin = ? ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pi",parseInt);
		return dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
	}

	@Override
	public List<Map<String, Object>> getAllProvinceCity() throws SQLException{
		 /*
		  * sql 一下字查完。然后在进行拼接，得到中国下的省份。
		  */
		 String sql1 = "select cid,lin,name from ht_location where level = 3 and lin = 7";
		 List<Map<String, Object>> listProven = dao.getNamedParameterJdbcTemplate().queryForList(sql1,new HashMap<>());
		 
		 String sql2 = "select cid,lin,name from ht_location where level = 4";
		 List<Map<String, Object>> listCity =  dao.getNamedParameterJdbcTemplate().queryForList(sql2,new HashMap<>());
		 for (Map<String, Object> mapProven : listProven) {
			 List<Map<String, Object>> listCityC = new ArrayList<Map<String,Object>>(); 
			 String objPcid  = mapProven.get("cid").toString();
			 for (Map<String, Object> mapCity : listCity) {
				 String objPclin  = mapCity.get("lin").toString();
				 if(objPcid.equals(objPclin)){
					 listCityC.add(mapCity);
				 }
			 }
			 mapProven.put("cityList", listCityC);
		 }
		 return listProven;
	}
	
	
	@Override
	public List<Map<String, Object>> getAllProvinceCityCounty()
			throws SQLException {
		// TODO Auto-generated method stub
		 /*
		  * sql 一下字查完。然后在进行拼接，得到中国下的省份。
		  */
		 String sql1 = "select province_id as pid,province_name as pname from place_province";
		 List<Map<String, Object>> listProven = dao.getNamedParameterJdbcTemplate().queryForList(sql1,new HashMap<>());
		 
		 String sql2 = "select city_id as cid,city_name as cname,province_id as pid from place_city";
		 List<Map<String, Object>> listCity =  dao.getNamedParameterJdbcTemplate().queryForList(sql2,new HashMap<>());
		 
		 String sql3 = "select district_id as did,district_name as dname,city_id as  cid from place_district";
		 List<Map<String, Object>> listDistrict =  dao.getNamedParameterJdbcTemplate().queryForList(sql3,new HashMap<>());
		 
		 /**
		  * 先循环小的，在循环大的
		  */
		 for (Map<String, Object> mapCity : listCity) {
			 List<Map<String, Object>> listCityC = new ArrayList<Map<String,Object>>(); 
			 String objPcid  = mapCity.get("cid").toString();
			 for (Map<String, Object> mapDistrict : listDistrict) {
				 String objPclin  = mapDistrict.get("cid").toString();
				 if(objPcid.equals(objPclin)){
					 listCityC.add(mapDistrict);
				 }
			 }
			 mapCity.put("disList", listCityC);
	     }
		 /**
		  * 在循环大的
		  */
		 for (Map<String, Object> mapProven : listProven) {
			 List<Map<String, Object>> listCityC = new ArrayList<Map<String,Object>>(); 
			 String objPcid  = mapProven.get("pid").toString();
			 for (Map<String, Object> mapCity : listCity) {
				 String objPclin  = mapCity.get("pid").toString();
				 if(objPcid.equals(objPclin)){
					 listCityC.add(mapCity);
				 }
			 }
			 mapProven.put("cityList", listCityC);
		 }
		 return listProven;
	}
	
	
	@Override
	public Integer []  getCodeByName(String proince,String city) throws SQLException {
		 /*
		  * sql 一下字查完。然后在进行拼接，得到中国下的省份。
		  */
		 String sql = "select cid,lin from ht_location where level = 3 and lin = 7 and name like '"+proince+"%'";
		 Map<String,Object>  map = dao.getNamedParameterJdbcTemplate().queryForMap(sql,new HashMap<>());
		 String sqlCity = "select cid,lin from ht_location where level = 4 and lin =:cid and name like '"+city+"%'";
		 
		 int provicenId = Integer.parseInt(map.get("cid").toString());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("cid",provicenId);
		 Map<String,Object>  map1 = dao.getNamedParameterJdbcTemplate().queryForMap(sql,paramMap);
		 int cityId= 0;
		 if(map1!=null){
			 cityId = Integer.parseInt(map1.get("cid").toString());
		 }else{
			 cityId = provicenId;
		 }
		return new Integer []{provicenId,cityId};
	}
	
	@Override
	public List<UserAddressManagerVo> getAddressAll(String id) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select id,provinces,city,county,street,consignee,phone,detailed_address as detailedAddress"
				+ ",is_acquiescence as isAcquiescence,postal_code as postalCode,user_id as userId from "
				+ " user_address_manager where user_id = '"+id+"' ORDER BY is_acquiescence DESC,create_time DESC";
		return dao.getNamedParameterJdbcTemplate().query(sql,new HashMap<>(),BeanPropertyRowMapper.newInstance(UserAddressManagerVo.class));
	}
  	@Override
	public void saveAddress(UserAddressManagerVo udm) throws SQLException {
		udm.setIsAcquiescence(0);
		List<UserAddressManagerVo> uams = getAddressAll(udm.getUserId());
		if(uams.size()<1){
			udm.setIsAcquiescence(1);
		}else if(uams.size()>=20){
			throw new RuntimeException("常用地址个数不能超过20个！");
		}
  		udm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
  		udm.setCreateTime(new Date());
		StringBuilder sql = new StringBuilder();
		if(isNull(udm.getConsignee())||isNull(udm.getPhone())||isNull(udm.getProvinces())||isNull(udm.getCity())||isNull(udm.getCounty())||isNull(udm.getDetailedAddress())){
			throw new RuntimeException("缺少必要参数");
		}
		sql.append(" insert into user_address_manager(id,user_id,provinces,city,county,street,phone,"
				+ "consignee,detailed_address,postal_code,create_time,is_acquiescence)");
		sql.append(" values (:id,:userId,:provinces,:city,:county,:street,:phone,:consignee,"
				+ ":detailedAddress,:postalCode,:createTime,:isAcquiescence) ");
		KeyHolder kh=new GeneratedKeyHolder();
		dao.getNamedParameterJdbcTemplate().update(sql.toString(),new BeanPropertySqlParameterSource(udm),kh );
		
	}

	public boolean isNull(String string){
		if(string==null||string.length()<1){
			return true;
		}
		return false;
	}
	@Override
	public UserAddressManagerVo findAddressById(String id) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select id,user_id as userId,provinces,city,county,street,consignee,phone,detailed_address as detailedAddress"
				+ ",is_acquiescence as isAcquiescence, postal_code as postalCode,create_time as createTime from "
				+ " user_address_manager where id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		UserAddressManagerVo um= dao.getNamedParameterJdbcTemplate().queryForObject(sql,params,new BeanPropertyRowMapper<UserAddressManagerVo>(UserAddressManagerVo.class));
		return um;
	}
	
	@Override
	public UserAddressManagerVo findAddressByUserIdAndAcq(String userId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select id,provinces,city,county,street,consignee,phone,detailed_address as detailedAddress"
				+ ",is_acquiescence as isAcquiescence,postal_code as postalCode,user_id as userId from "
				+ " user_address_manager where user_id = :uid and is_acquiescence = 1";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("uid", userId);
		//UserAddressManagerVo uad = dao.getNamedParameterJdbcTemplate().queryForObject(sql,params,UserAddressManagerVo.class);
		UserAddressManagerVo uad= dao.getNamedParameterJdbcTemplate().queryForObject(sql,params,new BeanPropertyRowMapper<UserAddressManagerVo>(UserAddressManagerVo.class));
		return uad;
	}
	@Override
	public void updateAddress(UserAddressManagerVo udm) throws SQLException {
		// TODO Auto-generated method stub
		UserAddressManagerVo orginUdm = findAddressById(udm.getId());
		
		StringBuilder sql = new StringBuilder();
		sql.append(" update user_address_manager set ");
		
		if (StringUtils.isNotBlank(udm.getProvinces()) && !orginUdm.getProvinces().equals(udm.getProvinces())) {
			sql.append(" provinces ='" + udm.getProvinces() + "',");
		}
		if (StringUtils.isNotBlank(udm.getCity()) && !orginUdm.getCity().equals(udm.getCity())) {
			sql.append(" city ='" + udm.getCity() + "',");
		}
		if (StringUtils.isNotBlank(udm.getCounty()) && !orginUdm.getCounty().equals(udm.getCounty())) {
			sql.append(" county ='" + udm.getCounty() + "',");
		}
		if (StringUtils.isNotBlank(udm.getStreet()) && !orginUdm.getStreet().equals(udm.getStreet())) {
			sql.append(" street ='" + udm.getStreet() + "',");
		}
		if (StringUtils.isNotBlank(udm.getPhone()) && !orginUdm.getPhone().equals(udm.getPhone())) {
			sql.append(" phone ='" + udm.getPhone() + "',");
		}	
		if (StringUtils.isNotBlank(udm.getConsignee()) && !orginUdm.getConsignee().equals(udm.getConsignee())) {
			sql.append(" consignee ='" + udm.getConsignee() + "',");
		}
		if (StringUtils.isNotBlank(udm.getDetailedAddress()) && !orginUdm.getDetailedAddress().equals(udm.getDetailedAddress())) {
			sql.append(" detailed_address ='" + udm.getDetailedAddress() + "',");
		}
		/*if (StringUtils.isNotBlank(udm.getPostalCode()) && !orginUdm.getPostalCode().equals(udm.getPostalCode())) {
			sql.append(" postal_code ='" + udm.getPostalCode() + "',");
		}*/
		String sb = sql.toString();
		if (sb.indexOf(",") != -1) {
			sb = sb.substring(0, sb.length() - 1);
			sb += " where id = :id";
			System.out.println("udm.getId:"+udm.getId());
			System.out.println("user center update " + sb);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id",udm.getId());
			dao.getNamedParameterJdbcTemplate().update(sb,params);
		}
	}
	@Override
	public void updateIsAcquies(String newId,String userId) throws SQLException {
		/*
		 * 更改两个，把一个搞成默认的，把另一个搞成不默认的了
		 */
		/**
		 * 通过用户id得到原来默认的啦
		 */
//		UserAddressManagerVo orginUdm  = findAddressByUserIdAndAcq(userId);
//		if(orginUdm !=null){
			String sql1 = " update user_address_manager set is_acquiescence = 0 where user_id = :userId ";
			Map<String, Object> params1 = new HashMap<String, Object>();
			params1.put("userId",userId);
			dao.getNamedParameterJdbcTemplate().update(sql1,params1);
//		}
		String sql2 = " update user_address_manager set is_acquiescence = 1 where id = :id ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",newId);
		dao.getNamedParameterJdbcTemplate().update(sql2,params);
	}
	@Override
	public void deleteAddressById(String id, String userId) throws SQLException {
		// TODO Auto-generated method stub
		String sql1 = " delete from  user_address_manager where user_id =:userId and  id = :id ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		params.put("userId",userId);
		dao.getNamedParameterJdbcTemplate().update(sql1,params);
	}
	
	@Override
	public Map<String,Object> getSingProvinceByCode(String code) throws SQLException{
		 if(StringUtils.isBlank(code)){
			 return null;
		 }
		 String sql = "select cid,name,code from ht_location where level = 2 and code = :code ";
		 Map<String,Object> paramMap = new HashMap<String,Object>();
		 paramMap.put("code",code);
		 return dao.getNamedParameterJdbcTemplate().queryForMap(sql,paramMap);
	}
	@Override
	public Map<String,Object> getSingCityByCodeAndPid(String code,int pid) throws SQLException{
		 if(StringUtils.isBlank(code)){
			 return null;
		 }
		 String sql = "select cid,name,code from ht_location where level = 3 and lin = :pid and name_en = :code ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pid",pid);
		paramMap.put("code",code);
		return dao.getNamedParameterJdbcTemplate().queryForMap(sql,paramMap);
	}
	@Override
	public Map<String,Object> getSingDistrictByCodeAndPid(String code,int pid) throws SQLException{
		 if(StringUtils.isBlank(code)){
			 return null;
		 }
		 String sql = "select cid,name,code from ht_location where level = 4 and lin = :pid and name_en = :code ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pid",pid);
		paramMap.put("code",code);
		return dao.getNamedParameterJdbcTemplate().queryForMap(sql,paramMap);
	}

	@Override
	public UserAddressManagerVo findAcquiescenceAddressById(String id) {
		// TODO Auto-generated method stub
		String sql = "select id,user_id as userId,provinces,city,county,street,consignee,phone,detailed_address as detailedAddress"
				+ ",is_acquiescence as isAcquiescence, postal_code as postalCode,create_time as createTime from "
				+ " user_address_manager where user_id = '"+id+"' and is_acquiescence = 1 limit 1";
		/*Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);*/
		
		List<UserAddressManagerVo>  listUam =  dao.getNamedParameterJdbcTemplate().query(sql,new HashMap<>(),BeanPropertyRowMapper.newInstance(UserAddressManagerVo.class));
		
		if(listUam!=null &&listUam.size()>0){
			return listUam.get(0);
		}else{
			return null;
		}
		//UserAddressManagerVo um= dao.getNamedParameterJdbcTemplate().queryForObject(sql,params,new BeanPropertyRowMapper<UserAddressManagerVo>(UserAddressManagerVo.class));
	}

}
