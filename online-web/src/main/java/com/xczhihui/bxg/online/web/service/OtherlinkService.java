package com.xczhihui.bxg.online.web.service;

import java.util.List;

import com.xczhihui.bxg.online.web.vo.OtherlinkVo;

/**
 * OtherlinkService:友情链接业务层接口类
 * * @author Rongcai Kang
 */
public interface OtherlinkService {

    public List<OtherlinkVo> getOtherLink(Integer pageNumber, Integer pageSize);
}
