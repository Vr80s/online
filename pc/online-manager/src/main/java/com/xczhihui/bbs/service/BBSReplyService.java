package com.xczhihui.bbs.service;

import java.util.List;

import com.xczhihui.utils.TableVo;

public interface BBSReplyService {

    TableVo list(TableVo tableVo);

    int updateDeleteStatus(List<Integer> ids, boolean deleted);
}
