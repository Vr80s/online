package com.xczhihui.bbs.service;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.utils.TableVo;

public interface BBSRestrictionService {

    TableVo list(TableVo tableVo);

    ResponseObject updateGags(String userId, boolean gags);

    ResponseObject updateBlacklist(String userId, boolean blacklist);
}
