package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.web.vo.BannerVo;

import java.util.List;

/**
 *   BannerService:Banner业务层接口类
 * * @author Rongcai Kang
 */
public interface BannerService {

        /**
         * 获取Banner列表数据,分页查询,每页12
         * @param pageNumber 当前是第几页，默认1
         * @param pageSize 每页显示多少行，默认20
         * @return  分页列表数据
         */
        public List<BannerVo> list(Integer pageNumber,Integer pageSize);

        public void updateClickCount(Integer id);


}
