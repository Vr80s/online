package com.xczhihui.wechat.course.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.vo.RankingUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface GiftMapper {

	/**
	 * Description：获取直播课程下的排行榜
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 4:47 2018/1/22 0022
	 **/
	List<RankingUserVO> selectRankingList(@Param("page") Page page, @Param("liveId") String liveId);
}