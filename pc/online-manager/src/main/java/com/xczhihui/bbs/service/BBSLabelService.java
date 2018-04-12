package com.xczhihui.bbs.service;

import java.util.List;

import com.xczhihui.bxg.online.common.domain.BBSLabel;
import com.xczhihui.utils.TableVo;

public interface BBSLabelService {

	TableVo list(TableVo tableVo);

	int delete(List<Integer> ids);

	boolean create(BBSLabel label);

	boolean update(BBSLabel label);

	int updateStatus(List<Integer> ids);
}
