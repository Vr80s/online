package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.Criticize;
import com.xczhihui.wechat.course.model.Reply;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/4/17 0017-上午 10:40<br>
 */
public class CriticizeMapperTest  extends BaseJunit4Test {

    @Autowired
    private CriticizeMapper criticizeMapper;

    @Test
    public void selectCourseCriticize(){
        List<Criticize> criticizes = criticizeMapper.selectCourseCriticize(new Page<>(1, 10), 607,"");
        criticizes.forEach(criticize -> criticize.toString());
    }

    @Test
    public void selectReplyByCriticizeId(){
        List<Reply> criticizes = criticizeMapper.selectReplyByCriticizeId("4460e437f30b4a39841512b23b543f6d");
        criticizes.forEach(criticize -> criticize.toString());
    }
}