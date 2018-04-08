package com.xczhihui.common.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.common.dao.OeCommonDao;
import com.xczhihui.common.util.ImportExcelUtil;
import com.xczhihui.utils.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.common.domain.Common;
import com.xczhihui.common.service.CommonService;
import com.xczhihui.common.vo.KeyValVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.CommonGroupEnum;
import com.xczhihui.utils.PageVo;

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
        if (common != null) {
            Common commonSrc = commonDao.get(common.getId(), Common.class);
            if (commonSrc == null) {
                return;
            }
            if (StringUtils.hasText(common.getKey())) {
                commonSrc.setKey(common.getKey());
            }
            if (StringUtils.hasText(common.getGroup())) {
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
            if (isLogic) {
                commonDao.deleteLogic(id, Common.class);
            } else {
                commonDao.delete(id, Common.class);
            }
        }

    }

    @Override
    public void deleteBatchCommon(String[] ids, boolean isLogic) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                this.deleteCommon(id, isLogic);
            }
        }

    }

    @Override
    public PageVo queryPageCommon(Groups groups, PageVo page) {
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
    public void updateGroupNameByGroup(String newGroupName, String groupName) {
        commonDao.updateOneColByGroup("`group`", newGroupName, groupName);
    }


    @Override
    public List<KeyValVo> findListByGroup(String group) {
        return commonDao.getCommonDataList(group);
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

        Map<String, Object> map = new HashMap<String, Object>();

        if (listob != null && listob.size() <= 0) {
            map.put("excel_error", "未获取到excel文件中内容");
            return map;
        }
        System.out.println("listob.size():" + listob.size());

        Integer falg = 0;
        String errorFalg = "";

        //清除xls中的空行 wys
        for (int i = 0; i < listob.size(); i++) {
            if (listob.get(i).size() <= 0) {
                listob.remove(i);
            }
        }
        for (int i = 0; i < listob.size(); i++) {
            if (i == listob.size() - 1) {
                break;
            }
            List<Object> obj = listob.get(i);
            List<Object> obj1 = listob.get(i + 1);
            String problemIndexK = obj.get(0).toString();
            String answerIndexK = obj1.get(0).toString();
            if (problemIndexK.contains("问题") && answerIndexK.contains("答")) {   //如果包含这个问题，先一个就是问题名称
                if (obj.size() < 2) {
                    map.put("excel_error", "问答的   excel 格式有问题！");
                    break;
                }
                if (obj1.size() < 2) {
                    map.put("excel_error", "问答的   excel 格式有问题！");
                    break;
                }
                String problemIndexV = obj.get(1).toString();
                String answerIndexV = obj1.get(1).toString();
                System.out.println(problemIndexV + "=========" + answerIndexV);
                if (!org.apache.commons.lang.StringUtils.isNotBlank(problemIndexV)
                        || problemIndexV.length() > 500) {
                    map.put("excel_error", "第:" + i + "个问题问题字符在0---500字");

                    falg++;
                }
                if (!org.apache.commons.lang.StringUtils.isNotBlank(answerIndexV)
                        || answerIndexV.length() > 500) {
                    map.put("excel_error", "第:" + i + "个答案答案字符在0---500字");

                    falg++;
                }
            }
        }
        if (falg > 0 && errorFalg.length() > 0) {
            map.put("excel_error", errorFalg);
            return map;
        }
        System.out.println("falg:" + falg);

        List<SystemVariate> listOld = systemVariateService.getSystemVariatesByName("common_problems");
        /*
         * 删除原来的
		 */
        for (SystemVariate systemVariate : listOld) {
            systemVariateService.deleteSystemVariate(systemVariate.getId());
        }
        /**
         * 添加新的
         */
        for (int i = 0; i < listob.size(); i++) {
            if (i == listob.size() - 1) {
                break;
            }
            List<Object> obj = listob.get(i);
            List<Object> obj1 = listob.get(i + 1);
            String problemIndexK = obj.get(0).toString();
            String answerIndexK = obj1.get(0).toString();
            if (problemIndexK.contains("问题") && answerIndexK.contains("答")) {   //如果包含这个问题，先一个就是问题名称
                String problemIndexV = obj.get(1).toString();
                String answerIndexV = obj1.get(1).toString();
                //保存一个
                SystemVariate systemVariate = new SystemVariate();
                systemVariate.setCreatePerson(ManagerUserUtil.getId());
                systemVariate.setCreateTime(new Date());
                systemVariate.setName("common_problems");
                systemVariate.setValue(problemIndexV);     //这个存问题
                systemVariate.setDescription(answerIndexV);//这个存答案
                systemVariateService.addSystemVariate(systemVariate);
            }
        }
        map.put("excel_error", "更新成功");
        return map;
    }
}
