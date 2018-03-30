package com.xczhihui.bxg.online.manager.bbs.service;

import java.util.List;

import com.xczhihui.bxg.online.manager.utils.TableVo;

public interface BBSReplyService {

    TableVo list(TableVo tableVo);

    int updateDeleteStatus(List<Integer> ids, boolean deleted);
}
