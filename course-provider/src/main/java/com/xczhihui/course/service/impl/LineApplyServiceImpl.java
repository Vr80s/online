package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.common.util.SLEmojiFilter;
import com.xczhihui.common.util.enums.UserSex;
import com.xczhihui.course.exception.LineApplyException;
import com.xczhihui.course.mapper.LineApplyMapper;
import com.xczhihui.course.model.LineApply;
import com.xczhihui.course.service.ILineApplyService;
import com.xczhihui.course.util.XzStringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class LineApplyServiceImpl extends ServiceImpl<LineApplyMapper,LineApply> implements ILineApplyService {

	
	@Autowired
	private LineApplyMapper lineApplyMapper;

	@Override
	public LineApply findLineApplyByUserId(String userId) {
		return lineApplyMapper.findLineApplyByUserId(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "addOrUpdateLineApplyLock",waitTime = 5,effectiveTime = 8)
	public void saveOrUpdate(String lockId,LineApply lineApply) {
		/**
		 * 校验数据准确性
		 */
		validateUserBank(lineApply);
		LineApply  lineApplyOld= this.findLineApplyByUserId(lineApply.getUserId());
		if(lineApplyOld!=null) { //原来已经存在，更新操作
			lineApplyOld.setUpdateTime(new Date());
			lineApplyOld.setRealName(lineApply.getRealName());
			lineApplyOld.setSex(lineApply.getSex());
			lineApplyOld.setMobile(lineApply.getMobile());
			lineApplyOld.setWechatNo(lineApply.getWechatNo());
			lineApplyMapper.updateById(lineApplyOld);
		}else {
			String id = UUID.randomUUID().toString().replace("-", "");
			lineApply.setId(id);
			lineApply.setDelete(false);
			lineApply.setCreateTime(new Date());
			lineApplyMapper.insert(lineApply);
		}
	}
	
	private void validateUserBank(LineApply lineApply) {
		
		if (StringUtils.isBlank(lineApply.getUserId())) {
			throw new LineApplyException("用户id不可为空");
		}
		String name = lineApply.getRealName();
		//过滤掉可能出现的表情字符
		if(StringUtils.isNotBlank(name)) {
			lineApply.setRealName(SLEmojiFilter.filterEmoji(name));
		}
		name = lineApply.getRealName();
		if(!StringUtils.isNotBlank(name) || (name.length()>20)){
			throw new LineApplyException("昵称最多允许输入20个字符");
		}
		
		if (!XzStringUtils.checkPhone(lineApply.getMobile())) {
			throw new LineApplyException("请输入正确的手机号");
	    }
		
		if(!StringUtils.isNotBlank(lineApply.getWechatNo()) ||
				 !XzStringUtils.checkWechatNo(lineApply.getWechatNo())) {
			throw new LineApplyException("微信账号仅支持6-20个字母、数字、下划线或减号，以字母开头");
		}
	
		if(lineApply.getSex()!=null && !UserSex.isValid(lineApply.getSex())){
			throw new LineApplyException("性别不合法,0 女  1男   2 未知");
		}
	}
	
    public static void weixin(String str) {
        //String str = "s12345123451234512345";
         // 校验微信号正则
        String judge = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
        Pattern pat = Pattern.compile(judge);
        Matcher mat = pat.matcher(str);
        System.out.println("是否是正确的微信号:" + mat.matches());
    }
    
    /**
     * 微信账号验证
     * @param args
     */
	public static void main(String[] args) {
		weixin("a45369975");
	}
	
	
}
