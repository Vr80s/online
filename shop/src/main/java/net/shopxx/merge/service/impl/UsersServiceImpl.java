package net.shopxx.merge.service.impl;

import static net.shopxx.entity.User.FREE_SECRET_NAME;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xczhihui.common.util.CodeUtil;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.shopxx.dao.BusinessDao;
import net.shopxx.entity.Member;
import net.shopxx.merge.entity.UsersRelation;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.service.UsersService;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.UserService;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Inject
    private UserService userService;
    @Inject
    private MemberRankService memberRankService;
    @Autowired
    private BusinessDao businessDao;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<String> getBusinessUsernameByDoctorId(String doctorId) {
        return businessDao.findUsernameByDoctorId(doctorId);
    }

    @Override
    public String createFreeSecretKey(String username) {
        String secret = CodeUtil.generateRandomString(20);
        Ehcache ehcache = cacheManager.getEhcache(FREE_SECRET_NAME);
        Element element = new Element(username, secret, System.currentTimeMillis());
        element.setTimeToLive(60 * 1000);
        ehcache.put(element);
        return secret;
    }

    @Override
    @Transactional
    public Long register(String username, String password, String email, String mobile) {
        Member member = new Member();
        member.removeAttributeValue();

        member.setUsername(username);
        member.setPassword(password);
        member.setEmail(email);
        member.setMobile(mobile);
        member.setPoint(0L);
        member.setBalance(BigDecimal.ZERO);
        member.setFrozenAmount(BigDecimal.ZERO);
        member.setAmount(BigDecimal.ZERO);
        member.setIsEnabled(true);
        member.setIsLocked(false);
        member.setLockDate(null);
        member.setLastLoginIp(null);
        member.setLastLoginDate(new Date());
        member.setSafeKey(null);
        member.setMemberRank(memberRankService.findDefault());
        member.setDistributor(null);
        member.setCart(null);
        member.setOrders(null);
        member.setPaymentTransactions(null);
        member.setMemberDepositLogs(null);
        member.setCouponCodes(null);
        member.setReceivers(null);
        member.setReviews(null);
        member.setConsultations(null);
        member.setProductFavorites(null);
        member.setProductNotifies(null);
        member.setSocialUsers(null);
        member.setPointLogs(null);
        userService.register(member);
        return member.getId();
    }

}