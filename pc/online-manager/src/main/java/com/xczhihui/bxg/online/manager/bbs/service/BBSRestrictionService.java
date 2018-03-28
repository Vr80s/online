package com.xczhihui.bxg.online.manager.bbs.service;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.utils.TableVo;

public interface BBSRestrictionService {

    TableVo list(TableVo tableVo);

    ResponseObject updateGags(String userId, boolean gags, BxgUser loginUser);

    ResponseObject updateBlacklist(String userId, boolean blacklist, BxgUser loginUser);
}
