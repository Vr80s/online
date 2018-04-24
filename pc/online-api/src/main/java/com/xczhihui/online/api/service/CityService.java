package com.xczhihui.online.api.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xczhihui.online.api.vo.UserAddressManagerVo;

/**
 * 关注service
 * ClassName: FocusService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月15日<br>
 */
public interface CityService {
    /**
     * 
     * Description：得到所有国家
     * @return
     * @throws SQLException
     * @return List<Map<String,Object>>
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	public	List<Map<String, Object>> getNation()throws SQLException;
	/**
	 * 
	 * Description：通过国家id得到下面的省份（地区）
	 * @param parseInt
	 * @return
	 * @throws SQLException
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public	List<Map<String, Object>> getProvince(int parseInt)throws SQLException;
	/**
	 * 
	 * Description：通过省份id得到下面的市区
	 * @param parseInt
	 * @return
	 * @throws SQLException
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public List<Map<String, Object>> getCity(int parseInt)throws SQLException;


	/**
	 * 
	 * Description：得到中国下面的所有省份、市
	 * @return
	 * @throws SQLException
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public List<Map<String, Object>> getAllProvinceCity()throws SQLException;
	/**
	 * 
	 * Description：得到中国下面的所有省、市、区
	 * @return
	 * @throws SQLException
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public	List<Map<String, Object>> getAllProvinceCityCounty()throws SQLException;
	
	/**
	 * 
	 * Description：
	 * @return
	 * @return List<UserAddressManagerVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public List<UserAddressManagerVo> getAddressAll(String id)throws SQLException;

	/**
	 * 保存地址
	 * Description：
	 * @param udm
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void saveAddress(UserAddressManagerVo udm) throws SQLException;
    /**
     * 修改地址
     * Description：
     * @param udm
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	public void updateAddress(UserAddressManagerVo udm)throws SQLException;
    /**
     * 更改此地址为默认地址
     * Description：
     * @param udm
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public void updateIsAcquies(String newId, String userId)throws SQLException;

	/**
	 * 
	 * Description：通过id得到地址信息
	 * @param id
	 * @return
	 * @throws SQLException
	 * @return UserAddressManagerVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public UserAddressManagerVo findAddressById(String id)throws SQLException;
	/**
	 * 通过用户id 得到单个默认的地址
	 * Description：
	 * @param id
	 * @return
	 * @throws SQLException
	 * @return UserAddressManagerVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public UserAddressManagerVo findAddressByUserIdAndAcq(String userId) throws SQLException;

	/**
	 * 通过用户id和地址id删除这个记录
	 * Description：
	 * @param id
	 * @param userId
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public void deleteAddressById(String id, String userId) throws SQLException;

	

	public Map<String, Object> getSingProvinceByCode(String code) throws SQLException;

	public Map<String, Object> getSingCityByCodeAndPid(String code, int pid)
			throws SQLException;

	public Map<String, Object> getSingDistrictByCodeAndPid(String code, int pid)
			throws SQLException;
	
	public Integer []  getCodeByName(String proince, String city)throws SQLException;
	/**
	 * 
	 * Description：通过用户id得到用户的默认地址
	 * @param id
	 * @return
	 * @return UserAddressManagerVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public UserAddressManagerVo findAcquiescenceAddressById(String id);

}
