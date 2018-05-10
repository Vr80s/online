package com.xczhihui.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.MobileHotSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：热门搜索
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/5/10 10:14
 **/
public interface MobileHotSearchMapper extends BaseMapper<MobileHotSearch> {

	 List<MobileHotSearch> HotSearchList( @Param("searchType") Integer searchType);

}