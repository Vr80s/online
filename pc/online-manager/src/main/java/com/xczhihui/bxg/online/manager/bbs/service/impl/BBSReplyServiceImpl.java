package com.xczhihui.bxg.online.manager.bbs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.bbs.dao.BBSPostDao;
import com.xczhihui.bxg.online.manager.bbs.dao.BBSReplyDao;
import com.xczhihui.bxg.online.manager.bbs.service.BBSReplyService;
import com.xczhihui.bxg.online.manager.bbs.vo.BBSReplyVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Service
public class BBSReplyServiceImpl implements BBSReplyService {

    private static final Object DELETE_LOCK = new Object();

    @Autowired
    private BBSReplyDao bbsReplyDao;
    @Autowired
    private BBSPostDao bbsPostDao;

    @Override
    public TableVo list(TableVo tableVo) {
        Map<String, Object> params = new HashMap<>();
        Groups groups = Tools.filterGroup(tableVo.getsSearch());
        Group id = groups.findByName("id");
        if (id != null && id.getPropertyValue1() != null) {
            params.put("id", Integer.parseInt(id.getPropertyValue1().toString()));
        } else {
            params.put("id", null);
        }
        Group userId = groups.findByName("userId");
        if (userId != null && userId.getPropertyValue1() != null) {
            params.put("userId", userId.getPropertyValue1().toString());
        } else {
            params.put("userId", null);
        }
        Group isDelete = groups.findByName("isDelete");
        if (isDelete != null && isDelete.getPropertyValue1() != null) {
            params.put("isDelete", Boolean.parseBoolean(isDelete.getPropertyValue1().toString()));
        } else {
            params.put("isDelete", false);
        }
        Group content = groups.findByName("content");
        if (content != null && content.getPropertyValue1() != null) {
            params.put("content", "%" + content.getPropertyValue1().toString() + "%");
        } else {
            params.put("content", null);
        }
        Page<BBSReplyVo> bbsReplyVoPage = bbsReplyDao.list(params, tableVo.getCurrentPage(), tableVo.getiDisplayLength());
        return tableVo.fetch(bbsReplyVoPage);
    }

    @Override
    public int updateDeleteStatus(List<Integer> ids, boolean deleted) {
        synchronized (DELETE_LOCK) {
            int count = bbsReplyDao.changeDeleteStatus(ids, deleted);
            if (ids.size() != count) {
                throw new IllegalStateException("回复删除状态异常");
            }
            if (deleted) {
                bbsPostDao.reduceReplyCount(ids);
            } else {
                bbsPostDao.addReplyCount(ids);
            }
            return count;
        }
    }
}
