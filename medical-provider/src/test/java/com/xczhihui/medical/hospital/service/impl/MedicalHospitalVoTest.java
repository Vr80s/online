package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

/**
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 6:08 2017/12/9 0009<br>
 */
public class MedicalHospitalVoTest extends BaseJunit4Test {

//    @Autowired //自动注入
//    private IUserService iUserService;

    @Autowired //自动注入
    private IMedicalHospitalBusinessService iMedicalHospitalBusinessService;

//    @Test
//    @Transactional   //标明此方法需使用事务
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
//    public void test1(){
//        System.out.println("测试Spring整合Junit4进行单元测试");
//        List<User> users = iUserService.selectList(null);
//        for (User user : users) {
//            System.out.println(user.getName());
//        }
//    }

    @Test
//    @Transactional   //标明此方法需使用事务
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test2(){
//        System.out.println("测试iMedicalHospitalService");
//        List<MedicalHospitalVo> medicalHospitals = iMedicalHospitalBusinessService.selectList(null);
//        for (MedicalHospitalVo medicalHospital : medicalHospitals) {
//            System.out.println(medicalHospital.toString());
//        }
    }


    @Test
//    @Transactional   //标明此方法需使用事务
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test3(){
        System.out.println("测试iMedicalHospitalService");
        Page<MedicalHospitalVo> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        Page<MedicalHospitalVo> medicalHospitalPage = iMedicalHospitalBusinessService.selectHospitalPage(page,"www", "");
        for (MedicalHospitalVo medicalHospital : medicalHospitalPage.getRecords()) {
            System.out.println(medicalHospital.toString());
        }
    }
}