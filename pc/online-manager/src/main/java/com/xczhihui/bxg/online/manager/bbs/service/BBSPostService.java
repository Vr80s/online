package com.xczhihui.bxg.online.manager.bbs.service;

import java.util.List;

import com.xczhihui.bxg.online.manager.utils.TableVo;

public interface BBSPostService {

    TableVo list(TableVo tableVo);

    int updateGoodStatus(List<Integer> ids);

    int updateTopStatus(List<Integer> ids);

    int updateDeleteStatus(List<Integer> ids, boolean deleted);

    int updateHotStatus(List<Integer> ids);
}
