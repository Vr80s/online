package com.xczhihui.bxg.online.manager.bbs.service;

import java.util.List;

import com.xczhihui.bxg.online.common.domain.BBSLabel;
import com.xczhihui.bxg.online.manager.utils.TableVo;

public interface BBSLabelService {

    TableVo list(TableVo tableVo);

    int delete(List<Integer> ids);

    boolean create(BBSLabel label);

    boolean update(BBSLabel label);

    int updateStatus(List<Integer> ids);
}
