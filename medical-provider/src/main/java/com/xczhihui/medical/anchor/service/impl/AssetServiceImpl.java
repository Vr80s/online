package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.enums.EnchashmentDismissal;
import com.xczhihui.medical.anchor.mapper.CourseApplyInfoMapper;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.service.IAssetService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
@Service
public class AssetServiceImpl  implements IAssetService {

    @Autowired
    private CourseApplyInfoMapper courseApplyInfoMapper;
    @Autowired
    private IAnchorInfoService anchorInfoService;

    @Override
    public Page<Map> getCoinTransactionPage(Page<Map> page, String userId) {
        anchorInfoService.validateAnchorPermission(userId);
        List<Map> records = courseApplyInfoMapper.selectCoinTransactionPage(page, userId);
        page.setRecords(records);
        return page;
    }

    @Override
    public Page<Map> getRmbTransactionPage(Page<Map> page, String userId) {
        anchorInfoService.validateAnchorPermission(userId);
        List<Map> records;
        records = courseApplyInfoMapper.selectRmbTransactionPage(page, userId);
        for(int i= 0;i<records.size();i++){
            if(records.get(i).containsKey("dismissal")){
                String dismissal = records.get(i).get("dismissal").toString();
                String value = EnchashmentDismissal.getDismissal(Integer.parseInt(dismissal));
                records.get(i).put("dismissal",value);
            }
        }
        page.setRecords(records);
        return page;
    }
}
