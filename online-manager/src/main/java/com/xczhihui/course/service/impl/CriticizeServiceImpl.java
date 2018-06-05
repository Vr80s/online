package com.xczhihui.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.dao.CriticizeDao;
import com.xczhihui.course.service.CriticizeService;
import com.xczhihui.course.vo.CriticizeVo;

/**
 * @author hejiwei
 */
@Service
public class CriticizeServiceImpl implements CriticizeService {

    @Autowired
    private CriticizeDao criticizeDao;

    @Override
    public Page<CriticizeVo> list(String keyword, Integer pageNumber, Integer pageSize) {
        return criticizeDao.list(keyword, pageNumber, pageSize);
    }

    @Override
    public void deletes(List<String> ids) {
        criticizeDao.deleteByIds(ids);
    }
}
