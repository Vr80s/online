package com.xczhihui.user.center.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.common.util.Md5Encrypt;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.RandomUtil;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.common.util.VhallUtil;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.UserStatus;
import com.xczhihui.user.center.exception.LoginRegException;
import com.xczhihui.user.center.mapper.OeUserMapper;
import com.xczhihui.user.center.mapper.UserCoinMapper;
import com.xczhihui.user.center.model.OeUser;
import com.xczhihui.user.center.model.UserCoin;
import com.xczhihui.user.center.service.CacheService;
import com.xczhihui.user.center.service.IOeUserService;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.SaltUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.Token;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/15 0015-上午 9:35<br>
 */
@Service("userCenterService")
public class UserCenterServiceImpl implements UserCenterService {

    private static final Logger logger = LoggerFactory.getLogger(UserCenterServiceImpl.class);

    private static String DEFAULT_HEAD = "https://file.ipandatcm.com//image/jpg/defaultHead.png";
    @Autowired
    private IOeUserService iOeUserService;
    @Autowired
    private OeUserMapper oeUserMapper;
    @Autowired
    private UserCoinMapper userCoinMapper;

    private CacheService cacheService;

    private TokenManager tokenManager;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
        this.tokenManager = new TokenManager(this.cacheService);
    }

    @Override
    public void regist(String loginName, String password, String nikeName, ClientType origin, Boolean visitor) {
        if(origin == null){
            origin = ClientType.OTHER;
        }
        if (!StringUtils.hasText(loginName) || !StringUtils.hasText(password)) {
            throw new LoginRegException("用户名、密码不允许为空！");
        }
        OeUserVO u = this.getUserVO(loginName);
        if (u != null) {
            throw new LoginRegException("用户名'" + u.getLoginName() + "'已被注册");
        }
        OeUser oeUser = new OeUser();
        oeUser.setId(CodeUtil.getRandomUUID());
        oeUser.setSmallHeadPhoto(DEFAULT_HEAD);
        oeUser.setLoginName(loginName);
        oeUser.setPassword(password);
        this.proccessPassword(oeUser, true);
        oeUser.setName(nikeName);
        oeUser.setOrigin(origin.getCode() + "");
        oeUser.setStatus(0);
        oeUser.setDelete(false);
        oeUser.setCreateTime(new Date());
        oeUser.setVisitor(visitor);
        iOeUserService.addOeUser(oeUser);
        saveUserCoin(oeUser.getId());
        updateVhallInfo(oeUser);
    }

    @Override
    public void regist(String loginName, String password, String nikeName, ClientType origin) {
        regist(loginName, password, nikeName, origin, Boolean.FALSE);
    }

    private void updateVhallInfo(OeUser oeUser) {
        String vhallPassword = RandomUtil.getCharAndNumr(6);
        String vhallId = VhallUtil.createUser(oeUser.getId(), oeUser.getName(), oeUser.getSmallHeadPhoto(), vhallPassword);
        oeUser.setVhallId(vhallId);
        oeUser.setVhallPass(vhallPassword);
        oeUser.setVhallName(oeUser.getId());
        this.oeUserMapper.updateById(oeUser);
    }

    private void saveUserCoin(String userId) {
        UserCoin userCoin = new UserCoin();
        userCoin.setUserId(userId);
        userCoin.setBalance(BigDecimal.ZERO);
        userCoin.setBalanceGive(BigDecimal.ZERO);
        userCoin.setBalanceRewardGift(BigDecimal.ZERO);
        userCoin.setRmb(BigDecimal.ZERO);
        userCoin.setDeleted(false);
        userCoin.setCreateTime(new Date());
        userCoin.setStatus(true);
        userCoin.setVersion(CodeUtil.getRandomUUID());
        userCoinMapper.insert(userCoin);
    }

    private void proccessPassword(OeUser oeUser, boolean genSalt) {
        String salt = oeUser.getSalt();
        if (!StringUtils.hasText(salt) && genSalt) {
            salt = SaltUtil.generateRandomSalt();
            oeUser.setSalt(salt);
        }
        logger.info("password:'{}', salt:'{}'", oeUser.getPassword(), salt);
        String encPassord = SaltUtil.encodePassword(oeUser.getPassword(), salt);
        oeUser.setPassword(encPassord);
    }

    @Override
    public void deleteUserLogic(String userId) {
        this.updateStatus(userId, UserStatus.DISABLE.getValue());
    }

    @Override
    public void update(OeUserVO oeUserVO) {
        OeUser oeUser = new OeUser();
        BeanUtils.copyProperties(oeUserVO, oeUser);
        this.iOeUserService.updateOeUser(oeUser);
    }

    @Override
    public void updateStatus(String userId, int status) {
        if (!UserStatus.isValid(status)) {
            throw new IllegalArgumentException("status参数错误！");
        }
        iOeUserService.updateStatus(userId, status);
    }

    @Override
    public void updatePassword(String loginName, String oldPassword, String newPassword) {
        OeUser user = this.oeUserMapper.selectByLoginName(loginName);
        if (user == null) {
            throw new LoginRegException("用户不存在");
        }
        if (oldPassword != null && newPassword != null && oldPassword.equals(newPassword)) {
            logger.info("newPassword:{},oldPassword:{}", newPassword, oldPassword);
            throw new LoginRegException("新密码不能与旧密码相同");
        }

        if (StringUtils.hasText(oldPassword)) {
            String expect = user.getPassword();
            String actual = SaltUtil.encodePassword(oldPassword, user.getSalt());
            if (!expect.equals(actual)) {
                throw new LoginRegException("原密码错误");
            }
        } else {
            logger.warn("重置密码");
        }

        user.setPassword(newPassword);
        this.proccessPassword(user, false);
        this.iOeUserService.updatePassword(user.getId(), user.getPassword());
    }

    @Override
    public void resetPassword(String loginName, String newPassword) {
        OeUser user = this.oeUserMapper.selectByLoginName(loginName);
        if (user == null) {
            throw new LoginRegException("用户不存在");
        }

        user.setPassword(newPassword);
        this.proccessPassword(user, false);
        this.iOeUserService.updatePassword(user.getId(), user.getPassword());
    }

    @Override
    public void updateLoginName(String oldLoginName, String newLoginName) {
        if (oldLoginName.equals(newLoginName)) {
            return;
        }
        OeUserVO user = this.getUserVO(newLoginName);
        if (user != null) {
            throw new LoginRegException("登录名'" + newLoginName + "'已存在.");
        }
        this.iOeUserService.updateLoginName(oldLoginName, newLoginName);
    }

    private void updateLastLoginDate(OeUser user) {
        user.setLastLoginDate(new Date());
        user.setVisitSum(user.getVisitSum() + 1);
        this.oeUserMapper.updateById(user);
    }

    @Override
    public OeUserVO getUserVO(String loginName) {
        return this.oeUserMapper.getUserVOByLoginName(loginName);
    }

    @Override
    public Token reflushTicket(String ticket, TokenExpires tokenExpires) {
        return this.tokenManager.reflushTicket(ticket, tokenExpires.getExpires());
    }

    @Override
    public Token loginThirdPart(String username, TokenExpires tokenExpires) throws Exception {
        OeUser user = this.oeUserMapper.selectByLoginName(username);
        if (user == null) {
            throw new LoginRegException("用户不存在");
        }
        if (user.getStatus() == UserStatus.DISABLE.getValue()) {
            throw new LoginRegException("帐号被禁用");
        }

        this.updateLastLoginDate(user);
        return this.tokenManager.createToken(user, tokenExpires.getExpires());
    }

    @Override
    public Token loginMobile(String loginName, String password, TokenExpires tokenExpires) {
        if (StringUtils.hasText(loginName) && StringUtils.hasText(password)) {
            OeUser user = this.oeUserMapper.selectByLoginName(loginName);
            if (user == null) {
                throw new LoginRegException("手机号暂未注册");
            }
            if (user.getStatus() == UserStatus.DISABLE.getValue()) {
                throw new LoginRegException("账号被禁用");
            }
            String salt = user.getSalt();
            if (salt == null) {
                salt = "";
            }
            String expect = SaltUtil.encodePassword(password, salt);
            String actual = user.getPassword();
            if (!expect.equals(actual)) {
                throw new LoginRegException("用户名或密码错误");
            }
            this.updateLastLoginDate(user);

            return this.tokenManager.createToken(user, tokenExpires.getExpires());
        }
        return null;
    }

    @Override
    public Token getToken(String ticket) {
        return this.tokenManager.getToken(ticket);
    }

    @Override
    public void deleteToken(String ticket) {
        this.tokenManager.deleteTicket(ticket);
    }

    @Override
    public void updateTokenInfo(String userId, String ticket) {
        OeUser oeUser = oeUserMapper.selectById(userId);
        Token tokenModel = new Token();
        tokenModel.setTicket(ticket);
        tokenModel.setLoginName(oeUser.getLoginName());
        tokenModel.setUserId(oeUser.getId());
        tokenModel.setOrigin(oeUser.getOrigin());
        long time = System.currentTimeMillis() + TokenExpires.TenDay.getExpires() * 1000;
        tokenModel.setExpires(time);
        tokenModel.setMobile(oeUser.getMobile());
        tokenModel.setEmail(oeUser.getEmail());
        tokenModel.setNickName(oeUser.getName());
        tokenModel.setPassWord(oeUser.getPassword());
        tokenModel.setUuid(oeUser.getId());
        tokenModel.setHeadPhoto(oeUser.getSmallHeadPhoto());
        this.tokenManager.refreshToken(ticket, tokenModel, TokenExpires.TenDay.getExpires());
    }

    @Override
    public boolean isDisabled(String userId) {
        String val = cacheService.get(RedisCacheKey.USER_DISABLE_PREFIX + RedisCacheKey.REDIS_SPLIT_CHAR + userId);
        return org.apache.commons.lang3.StringUtils.isNotBlank(val);
    }

    @Override
    public Token login4visitor(String username) {
        if (username == null || username.length() != 32) {
            throw new LoginRegException("提供的uuid不合法", true);
        }
        OeUserVO userVO = this.getUserVO(username);
        if (userVO == null) {
            this.regist(username, username, "游客", ClientType.IOS, Boolean.TRUE);
        }
        return loginMobile(username, username, TokenExpires.TenDay);
    }


    @Override
    public Token fastLogin(String loginName, String token, TokenExpires tokenExpires) {
        if (StringUtils.hasText(loginName) && StringUtils.hasText(token)) {
            OeUser user = this.oeUserMapper.selectByLoginName(loginName);
            if (user == null) {
                throw new LoginRegException("用户不存在");
            }
            String fastLoginToken = Md5Encrypt.getFastLoginToken(loginName, user.getPassword());
            if (!fastLoginToken.equals(token)) {
                throw new LoginRegException("token验证失败");
            }
            return this.tokenManager.createToken(user, tokenExpires.getExpires());
        }
        return null;
    }

}
