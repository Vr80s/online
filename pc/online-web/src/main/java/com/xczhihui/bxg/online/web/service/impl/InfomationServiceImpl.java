package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.online.web.dao.InfomationDao;
import com.xczhihui.bxg.online.web.service.InfomationService;
import com.xczhihui.bxg.online.web.vo.InfomationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态资讯相关
 * @author Haicheng Jiang
 */
@Service
public class InfomationServiceImpl implements InfomationService{

        @Autowired
        private InfomationDao dao;
        @Override
        public List<InfomationVo> list(Integer sum) {
            return dao.list(sum);
        }
        @Override
        public void updateClickCount(Integer id) {
            dao.updateClickCount(id);
        }

    }
