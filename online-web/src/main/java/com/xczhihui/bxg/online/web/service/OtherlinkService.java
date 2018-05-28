package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.web.vo.OtherlinkVo;

import java.util.List;

/**
 *   OtherlinkService:友情链接业务层接口类
 * * @author Rongcai Kang
 */
public interface OtherlinkService {

    public List<OtherlinkVo> getOtherLink(Integer pageNumber,Integer pageSize);
}
