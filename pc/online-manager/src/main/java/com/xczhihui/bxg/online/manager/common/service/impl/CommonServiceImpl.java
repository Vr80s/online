package com.xczhihui.bxg.online.manager.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.common.dao.OeCommonDao;
import com.xczhihui.bxg.online.manager.common.domain.Common;
import com.xczhihui.bxg.online.manager.common.service.CommonService;
import com.xczhihui.bxg.online.manager.common.util.ImportExcelUtil;
import com.xczhihui.bxg.online.manager.common.vo.KeyValVo;
import com.xczhihui.bxg.online.manager.utils.CommonGroupEnum;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;

/**
 * 对应OE_COMMON表数据的处理服务
 * 
 * @author yuanziyang
 * @date 2016年4月5日 下午3:42:45
 */
@Service
public class CommonServiceImpl extends OnlineBaseServiceImpl implements CommonService {

	@Autowired
	private OeCommonDao commonDao;
	
	@Autowired
	private SystemVariateService systemVariateService;
	

	@Override
	public List<KeyValVo> getAllTargetList() {
		return commonDao.getCommonDataList(CommonGroupEnum.TARGET.getName());
	}

	@Override
	public List<KeyValVo> getAllOccupationList() {
		return commonDao.getCommonDataList(CommonGroupEnum.OCCUPATION.getName());
	}

	@Override
	public void addCommon(Common common) {
		if (common != null) {
            commonDao.save(common);
        }
	}

	@Override
	public void updateCommon(Common common) {
		if (common != null){
			Common commonSrc = commonDao.get(common.getId(), Common.class);
			if(commonSrc == null) {
                return;
            }
			if(StringUtils.hasText(common.getKey())) {
                commonSrc.setKey(common.getKey());
            }
			if(StringUtils.hasText(common.getGroup())) {
                commonSrc.setGroup(common.getGroup());
            }
			commonSrc.setVal(common.getVal());
			commonSrc.setSort(common.getSort());
			commonDao.update(commonSrc);
		}
	}

	@Override
	public void deleteCommon(String id, boolean isLogic) {
		if (StringUtils.hasText(id)) {
			if(isLogic) {
                commonDao.deleteLogic(id, Common.class);
            } else {
                commonDao.delete(id, Common.class);
            }
		}

	}
	
	@Override
	public void deleteBatchCommon(String[] ids, boolean isLogic) {
		if (ids != null && ids.length>0) {
			for(String id:ids){
				this.deleteCommon(id, isLogic);
			}
		}

	}

	@Override
	public PageVo queryPageCommon(Groups groups,PageVo page) {
		return commonDao.findCommonPage(groups, page);
	}

	@Override
	public PageVo queryPageGroup(Groups groups, PageVo page) {
		return commonDao.findCommonGroupPage(groups, page);
	}

	@Override
	public void updateStatusByGroup(boolean isStartUse, String groupName) {
		commonDao.updateOneColByGroup("status", isStartUse, groupName);
	}

	@Override
	public void logicDeleteByGroup(String groupName) {
		commonDao.updateOneColByGroup("is_delete", true, groupName);
	}
	
	@Override
	public void updateGroupNameByGroup(String newGroupName,String groupName) {
		commonDao.updateOneColByGroup("`group`", newGroupName, groupName);
	}


	@Override
	public List<KeyValVo> findListByGroup(String group) {
		return	commonDao.getCommonDataList(group);
	}

	@Override
	public Map<String, Object> updateImportExcel(InputStream inputStream,
			String originalFilename) {
		
		ImportExcelUtil excelUtil = new ImportExcelUtil();
		List<List<Object>> listob = null;
		try {
			listob = excelUtil.getBankListByExcel(inputStream, originalFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (List<Object> obj : listob) {
//			System.out.println(obj.toString());
//		}
		List<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();
		System.out.println("listob.size():"+listob.size());
		for (int i = 0; i < listob.size(); i++) {
			if(i == listob.size()-1){
				break;
			}
			List<Object> obj = listob.get(i);
			List<Object> obj1 = listob.get(i+1);
			Map<String,Object> map= new HashMap<String, Object>();
			String problemIndexK= obj.get(0).toString();
			String answerIndexK= obj1.get(0).toString();
			if(problemIndexK.contains("问题") && answerIndexK.contains("答")){   //如果包含这个问题，先一个就是问题名称
				//problemList.add(strIndex1);
				String problemIndexV= obj.get(1).toString();
				map.put("problem", problemIndexV);
				
				String answerIndexV= obj1.get(1).toString();
				map.put("answer", answerIndexV);
			}
			allList.add(map);
		}
		SystemVariate systemVariate =systemVariateService.getSystemVariateByName("common_problems");
		if(systemVariate!=null){
			systemVariate.setDescription("============");
			dao.update(systemVariate);
		}else{
			 systemVariate = new SystemVariate();
			 BxgUser bu = UserHolder.getCurrentUser();
			 systemVariate.setCreatePerson(bu.getId());
			 systemVariate.setCreateTime(new Date());
			 systemVariate.setValue("common_problems");
			 systemVariate.setName("common_problems");
			 systemVariate.setDescription("+++++++++++");
		     systemVariateService.addSystemVariate(systemVariate);
		}
		System.out.println("{}{}{}{}"+listob.toString());
		System.out.println("{}{}{}{}"+allList.toString());
		return null;
	}
}
