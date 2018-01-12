package com.xczhihui.bxg.online.manager.common.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.common.dao.OeCommonDao;
import com.xczhihui.bxg.online.manager.common.domain.Common;
import com.xczhihui.bxg.online.manager.common.service.CommonService;
import com.xczhihui.bxg.online.manager.common.vo.KeyValVo;
import com.xczhihui.bxg.online.manager.utils.CommonGroupEnum;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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
}
