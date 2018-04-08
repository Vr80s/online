package com.xczhihui.ask.service.impl;/**
 * Created by admin on 2016/8/29.
 */

import java.util.*;

import com.xczhihui.ask.dao.TagDao;
import com.xczhihui.ask.service.TagService;
import com.xczhihui.ask.vo.TagVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.AskTag;

/**
 * 标签管理层的实现类
 *
 * @author 王高伟
 * @create 2016-10-16 16:41:27
 */
@Service
public class TagServiceImpl extends OnlineBaseServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<TagVo> findTagVo(TagVo tagVo) {
        return tagDao.findTag(tagVo);
    }

    @Override
    public Page<TagVo> findPage(TagVo searchVo, int currentPage, int pageSize) {
        // TODO Auto-generated method stub
        Page<TagVo> page = tagDao.findTagPage(searchVo, currentPage, pageSize);
        return page;

    }

    @Override
    public String addTag(TagVo tagVo) {
        // TODO Auto-generated method stub

        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT MAX(seq) as seq FROM oe_ask_tag ";
        List<AskTag> temp = tagDao.findEntitiesByJdbc(AskTag.class, sql, params);
        int sort;
        if (temp.size() > 0 && temp.get(0).getSeq() != null) {

            sort = temp.get(0).getSeq().intValue() + 1;
        } else {
            sort = 1;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();

        if (tagVo.getName() != null && !"".equals(tagVo.getName()) && tagVo.getName().indexOf(",") != -1) {
            String[] nameArray = tagVo.getName().split(",");
            Set<String> comp = new HashSet<String>();

            for (int i = 0; i < nameArray.length; i++) {
                //判断单个标签不能超过10个字符
                if (nameArray[i].length() > 20) {
                    return ("单个标签不能超过20个字符！");
                }
                comp.add(nameArray[i].trim());
            }
            if (nameArray.length != comp.size()) {
                return "标签名称重复！";
            }

            if (0 == nameArray.length) {
                return "标签名称不能为空！";
            }

            for (int i = 0; i < nameArray.length; i++) {

                AskTag askTag = new AskTag();
                List<AskTag> TagfindByNames = tagDao.findByHQL("from AskTag where menuId=? and name=? and is_delete = 0 ", Integer.parseInt(tagVo.getMenuId()), nameArray[i].trim());
                if (TagfindByNames.size() > 0) {
                    return "标签已存在！";
                }

                if ("".equals(nameArray[i].trim())) {
                    return "标签名称不能为空！";
                }

                askTag.setName(nameArray[i].trim());
                askTag.setSeq(sort + i);
                askTag.setDelete(false);
                askTag.setCreatePerson(ManagerUserUtil.getUsername()); //当前登录人
                askTag.setCreateTime(new Date()); //当前时间
                askTag.setMenuId(Integer.parseInt(tagVo.getMenuId()));
                askTag.setStatus(0);
                tagDao.save(askTag);
            }
        } else {

            String TagfindByNameSql = "SELECT * FROM oe_ask_tag where is_delete = 0 and menu_id = " + tagVo.getMenuId() + " and name = '" + tagVo.getName() + "'";
            AskTag askTag = new AskTag();
            List<AskTag> TagfindByNames = tagDao.findEntitiesByJdbc(AskTag.class, TagfindByNameSql, paramMap);
            if (tagVo.getName().length() > 20) {
                return ("单个标签不能超过20个字符！");
            }
            if (TagfindByNames.size() > 0) {
                return "标签已存在！";
            }
            if ("".equals(tagVo.getName())) {
                return "标签名称不能为空！";
            }
            askTag.setName(tagVo.getName());
            askTag.setSeq(sort);
            askTag.setDelete(false);
            askTag.setCreatePerson(ManagerUserUtil.getUsername()); //当前登录人
            askTag.setCreateTime(new Date()); //当前时间
            askTag.setMenuId(Integer.parseInt(tagVo.getMenuId()));
            askTag.setStatus(0);
            tagDao.save(askTag);
        }

        return "新增成功！";

    }

    @Override
    public String deletes(String[] ids) {
        // TODO Auto-generated method stub
        String msg = "";
        for (String id : ids) {
            msg = tagDao.deleteById(id);
        }
        return msg;
    }

    @Override
    public void updateStatus(String id) {
        // TODO Auto-generated method stub
        AskTag askTag = tagDao.findOneEntitiyByProperty(AskTag.class, "id", id);
        if (askTag.getStatus() != null && askTag.getStatus() == 1) {
            askTag.setStatus(0);
        } else {
            askTag.setStatus(1);
        }
        tagDao.update(askTag);
    }

    @Override
    public void updateDirectionUp(String id) {
        // TODO Auto-generated method stub
        AskTag preAskTag = tagDao.updateDirectionUp(id);
        AskTag me = tagDao.findOneEntitiyByProperty(AskTag.class, "id", id);
        Integer meSort = me.getSeq();
        me.setSeq(preAskTag.getSeq());
        preAskTag.setSeq(meSort);
        tagDao.update(preAskTag);
        tagDao.update(me);

    }

    @Override
    public void updateDirectionDown(String id) {
        // TODO Auto-generated method stub
        AskTag downAskTag = tagDao.updateDirectionDown(id);
        AskTag me = tagDao.findOneEntitiyByProperty(AskTag.class, "id", id);
        Integer meSort = me.getSeq();
        me.setSeq(downAskTag.getSeq());
        downAskTag.setSeq(meSort);
        tagDao.update(downAskTag);
        tagDao.update(me);


    }

    @Override
    public void updateTag(TagVo tagVo) {
        // TODO Auto-generated method stub
        Map<String, Object> paramMap = new HashMap<String, Object>();
        AskTag me = tagDao.findOneEntitiyByProperty(AskTag.class, "id", tagVo.getId());
        if (me != null) {
            String TagfindByNameSql = "SELECT * FROM oe_ask_tag where menu_id = " + me.getMenuId() + " and is_delete = 0 and name = '" + tagVo.getName() + "' and id!='" + me.getId() + "'";
            List<AskTag> TagfindByNames = tagDao.findEntitiesByJdbc(AskTag.class, TagfindByNameSql, paramMap);
            if (tagVo.getName().length() > 20) {
                throw new IllegalArgumentException("单个标签不能超过20个字符！");
            }

            if (TagfindByNames.size() > 0) {
                throw new IllegalArgumentException("标签名字重复！");
            }

            me.setName(tagVo.getName());
            tagDao.update(me);
        } else {
            throw new IllegalArgumentException("不存在记录");
        }

    }

}
