package com.xczhihui.bxg.online.web.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.Constant;
import com.xczhihui.bxg.online.web.dao.UserCenterDao;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.user.center.service.VerificationCodeService;
import com.xczhihui.bxg.online.web.vo.RegionVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.ImageUtil;
import com.xczhihui.common.util.VhallUtil;
import com.xczhihui.common.util.enums.UserOrigin;
import com.xczhihui.common.util.enums.VCodeType;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.OeUserVO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    public UserCenterDao userCenterDao;
    @Autowired
    @Qualifier("simpleHibernateDao")
    private SimpleHibernateDao dao;
    @Autowired
    private AttachmentCenterService attachmentCenterService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Value("${web.url}")
    private String weburl;

    @Override
    public void addPhoneRegist(HttpServletRequest req, String username, String password, String code, String nikeName) {
        verificationCodeService.checkCode(username, VCodeType.RETISTERED, code);
        userCenterService.regist(username, password, nikeName, UserOrigin.PC);
    }

    /**
     * 重置用户密码
     *
     * @param username
     * @param password
     * @param code
     * @return
     */
    @Override
    public void updateUserPassword(String username, String password, String code) {
        verificationCodeService.checkCode(username, VCodeType.FORGOT_PASSWORD, code);
        //更新用户密码
        userCenterService.resetPassword(username, password);
    }

    /**
     * 获取用户资料
     *
     * @return
     */
    @Override
    public UserDataVo getUserData(OnlineUser loginUser) {
        UserDataVo vo = new UserDataVo();
        if (StringUtils.hasText(loginUser.getId())) {
            vo = userCenterDao.getUserData(loginUser.getId());
            if (!StringUtils.hasText(vo.getNickName())) {
                vo.setNickName(Constant._NICKNAME);
            }
            vo.setBirthdayStr(DateUtil.formatDate(vo.getBirthday(), DateUtil.FORMAT_DAY));
            //设置职位
            vo.setJob(userCenterDao.getJob("occupation"));

            // 获取用户报名信息
            Apply app = dao.findOneEntitiyByProperty(Apply.class, "userId", loginUser.getId());
            if (app != null) {
                vo.setUid(app.getUserId());
                vo.setQq(app.getQq());
                vo.setMobile(app.getMobile());
                vo.setBirthdayStr(app.getBirthday() != null ? DateUtil.formatDate(app.getBirthday(), DateUtil.FORMAT_DAY) : "");
                vo.setApplyProvince(app.getProvince());
                vo.setAppCity(app.getCity());
                vo.setRealName(app.getRealName());
                vo.setSchoolId(app.getSchoolId());
                vo.setEducationId(app.getEducationId());
                vo.setMajorId(app.getMajorId());
                vo.setApplyId(app.getId());
                vo.setIdCardNo(app.getIdCardNo());
            }
        }
        return vo;
    }

    /**
     * 检查当前用户昵称是否存在，存在返回：true   不存在返回：false
     *
     * @param nickName 昵称
     * @return
     */
    @Override
    public Boolean checkNickName(String nickName, OnlineUser u) {
        String sql = "SELECT name FROM oe_user WHERE name = ? ";
        List<UserDataVo> ou = dao.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
                BeanPropertyRowMapper.newInstance(UserDataVo.class), nickName);
        if (u == null && ou.size() > 0) {
            return true;
        }
        if (ou.size() > 0 && u != null && !nickName.equals(u.getName())) {
            return true;
        }
        return false;

    }

    @Override
    public void updateHeadPhoto(String userId, byte[] image) throws IOException {

        OeUserVO oeUserVO = new OeUserVO();
        InputStream imageInputStream = new ByteArrayInputStream(image);
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();

        ImageUtil.scaleImage(imageInputStream, imageOutputStream, "png", 80, 80);
        image = imageOutputStream.toByteArray();

        Attachment smallattr = this.attachmentCenterService.addAttachment(
                userId, AttachmentType.ONLINE, userId + "_small.png", image,
                StringUtils.getFilenameExtension(userId + "_small.png"));

        oeUserVO.setId(userId);
        oeUserVO.setSmallHeadPhoto(smallattr.getUrl());
        userCenterService.update(oeUserVO);

		/*更新微吼账户信息*/
        VhallUtil.updateUser(oeUserVO.getId(), oeUserVO.getName(), oeUserVO.getSmallHeadPhoto(), null);
    }


    @Override
    public OnlineUser isAlive(String loginName) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
        u.setIsPerfectInformation(true);
        u.setIsOldUser(0);
        if (u != null) { //查看用户真实资料是否填写完成
            paramMap.put("userId", u.getId());
            String sql1 = " SELECT is_old_user FROM  oe_apply  WHERE user_id=:userId ";
            String sql2 = " SELECT is_old_user FROM  oe_apply  WHERE user_id=:userId  AND (real_name IS NULL  OR real_name='' OR mobile IS NULL OR mobile='' OR id_card_no IS NULL OR id_card_no = '' ) ";
            List<Map<String, Object>> applys1 = dao.getNamedParameterJdbcTemplate().queryForList(sql1, paramMap);
            List<Map<String, Object>> applys2 = dao.getNamedParameterJdbcTemplate().queryForList(sql2, paramMap);
            if (applys1.size() < 1 || applys2.size() > 0) {
                u.setIsPerfectInformation(false);
            }
        }
        return u;
    }

    @Override
    public List<RegionVo> listProvinces() {
        return dao.getNamedParameterJdbcTemplate().query("select * from oe_region where parent_id='0' ",
                new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
    }

    @Override
    public List<RegionVo> listCities(String provinceId) {
        Map<String, Object> ps = new HashMap<String, Object>();
        ps.put("provinceId", provinceId);
        return dao.getNamedParameterJdbcTemplate().query("SELECT * FROM oe_region WHERE parent_id= :provinceId ", ps,
                new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
    }

    @Override
    public OnlineUser findUserByLoginName(String loginName) {
        return dao.findByHQLOne("from OnlineUser where loginName=?", loginName);
    }

    @Override
    public OnlineUser findUserById(String userId) {
        return dao.findByHQLOne("from OnlineUser where id=?", userId);
    }

    @Override
    public Boolean isAnchor(String loginName) {
        StringBuilder sql = new StringBuilder();
        List<OnlineUser> users;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loginName", loginName);
        sql.append("SELECT \n" +
                "  ou.`name`," +
                " ca.status caStatus " +
                "FROM\n" +
                "  `course_anchor` ca \n" +
                "  JOIN `oe_user` ou \n" +
                "    ON ca.`user_id` = ou.id \n" +
                "WHERE ou.`login_name` = :loginName ");
        users = dao.findEntitiesByJdbc(OnlineUser.class, sql.toString(), paramMap);
        if (users.size() != 1) {
            return false;
        } else {
            return users.get(0).getCaStatus();
        }
    }
}

