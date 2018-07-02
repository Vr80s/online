package com.xczhihui.order.service;

import java.lang.reflect.InvocationTargetException;

import com.xczhihui.bxg.online.common.domain.EnchashmentApplyInfo;
import com.xczhihui.common.util.bean.Page;

public interface EnchashmentService {

    public Page<EnchashmentApplyInfo> findEnchashmentPage(
            EnchashmentApplyInfo orderVo, Integer pageNumber, Integer pageSize);

    /**
     * Description：处理提现申请
     *
     * @param ea
     * @return void
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @author name：yuxin <br>
     * email: yuruixin@ixincheng.com
     **/
    public void updateHandleEnchashment(EnchashmentApplyInfo ea);

}
