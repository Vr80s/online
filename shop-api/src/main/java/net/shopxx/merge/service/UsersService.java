package net.shopxx.merge.service;

public interface UsersService {

    void saveUserRelation(String ipandatcmUserId);

    /**
     * 通过doctorId获取商家后台用户名
     *
     * @param doctorId 医师id
     * @return
     */
    String getBusinessUsernameByDoctorId(String doctorId);

    /**
     * 生成免密登录秘钥
     *
     * @param username 用户名
     * @return 秘钥
     */
    String createFreeSecretKey(String username);
}
