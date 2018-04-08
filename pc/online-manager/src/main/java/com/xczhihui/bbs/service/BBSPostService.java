package com.xczhihui.bbs.service;

import java.util.List;

import com.xczhihui.utils.TableVo;

public interface BBSPostService {

    TableVo list(TableVo tableVo);

    int updateGoodStatus(List<Integer> ids);

    int updateTopStatus(List<Integer> ids);

    int updateDeleteStatus(List<Integer> ids, boolean deleted);

    int updateHotStatus(List<Integer> ids);
}
