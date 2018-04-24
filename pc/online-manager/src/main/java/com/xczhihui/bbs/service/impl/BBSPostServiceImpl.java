package com.xczhihui.bbs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xczhihui.bbs.dao.BBSLabelDao;
import com.xczhihui.bbs.dao.BBSPostDao;
import com.xczhihui.bbs.service.BBSPostService;
import com.xczhihui.bbs.vo.BBSPostVo;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.BBSPost;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

@Service
public class BBSPostServiceImpl implements BBSPostService {
    private static final Object POST_DELETE_LOCK = new Object();

    @Autowired
    private BBSPostDao bbsPostDao;
    @Autowired
    private BBSLabelDao bbsLabelDao;

    @Override
    public TableVo list(TableVo tableVo) {
        Map<String, Object> params = new HashMap<>();
        Groups groups = Tools.filterGroup(tableVo.getsSearch());
        Group id = groups.findByName("id");
        if (id != null && id.getPropertyValue1() != null) {
            params.put("id",
                    Integer.parseInt(id.getPropertyValue1().toString()));
        } else {
            params.put("id", null);
        }
        Group nickname = groups.findByName("nickname");
        if (nickname != null && nickname.getPropertyValue1() != null) {
            params.put("nickname", "%"
                    + nickname.getPropertyValue1().toString() + "%");
        } else {
            params.put("nickname", null);
        }
        Group isDelete = groups.findByName("isDelete");
        if (isDelete != null && isDelete.getPropertyValue1() != null) {
            params.put("isDelete", Boolean.parseBoolean(isDelete
                    .getPropertyValue1().toString()));
        } else {
            params.put("isDelete", false);
        }
        Group title = groups.findByName("title");
        if (title != null && title.getPropertyValue1() != null) {
            params.put("title", "%" + title.getPropertyValue1().toString()
                    + "%");
        } else {
            params.put("title", null);
        }
        Group type = groups.findByName("type");
        if (type != null && type.getPropertyValue1() != null) {
            params.put("type", type.getPropertyValue1().toString());
        } else {
            params.put("type", null);
        }
        Page<BBSPostVo> bbsReplyVoPage = bbsPostDao.list(params,
                tableVo.getCurrentPage(), tableVo.getiDisplayLength());
        return tableVo.fetch(bbsReplyVoPage);
    }

    @Override
    public int updateGoodStatus(List<Integer> ids) {
        return bbsPostDao.changeGoodStatus(ids);
    }

    @Override
    public int updateTopStatus(List<Integer> ids) {
        return bbsPostDao.changeTopStatus(ids);
    }

    @Override
    public int updateDeleteStatus(List<Integer> ids) {
        synchronized (POST_DELETE_LOCK) {
            int updateCnt = bbsPostDao.changeDeleteStatus(ids);
            updateLabelPostCount(ids);
            return updateCnt;
        }
    }

    @Async
    protected void updateLabelPostCount(List<Integer> ids) {
        for (int id : ids) {
            BBSPost bbsPost = bbsPostDao.findOneEntitiyByProperty(
                    BBSPost.class, "id", id);
            Integer labelId = bbsPost.getLabelId();
            bbsLabelDao.updatePostCount(labelId,
                    bbsPostDao.countByLabelId(labelId));
        }
    }

    @Override
    public int updateHotStatus(List<Integer> ids) {
        return bbsPostDao.changeHotStatus(ids);
    }
}
