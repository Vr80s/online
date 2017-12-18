package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

import java.util.List;

/**
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 6:08 2017/12/9 0009<br>
 */
public class MedicalHospitalTest  extends BaseJunit4Test {

//    @Autowired //自动注入
//    private IUserService iUserService;

    @Autowired //自动注入
    private IMedicalHospitalBusinessService iMedicalHospitalBusinessService;

    @Test
//    @Transactional   //标明此方法需使用事务
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test1(){
        System.out.println("测试iMedicalHospitalService");
        List<MedicalHospital> medicalHospitals = iMedicalHospitalBusinessService.selectRecHospital();
        for (MedicalHospital medicalHospital : medicalHospitals) {
            System.out.println(medicalHospital.getName());
            System.out.println(medicalHospital.getId());
        }
    }

    @Test
    public void test11(){
        System.out.println("测试遍历查询");
//        long startTime = System.currentTimeMillis(); // 获取开始时间
//        for (int i = 0; i < 10; i++) {
//            MedicalHospital medicalHospitals = iMedicalHospitalBusinessService.selectHospitalById("88ebc1ee4e264ce7a81c89abd06de12e");
////            System.out.println(medicalHospitals.getName());
//        }
//        long endTime = System.currentTimeMillis(); // 获取结束时间
//        long consuming = endTime - startTime;
        int a = 10;
        int b = 5000;
        long byIdTime = 0;
        for (int i = 0; i < a; i++) {
            byIdTime += getByIdTime(b,iMedicalHospitalBusinessService);
        }
        System.out.println("测试遍历查询----平均用时用时"+byIdTime+"ms,平均用时"+byIdTime/(a*b)+"ms");

    }

    public long getByIdTime(int count,IMedicalHospitalBusinessService iMedicalHospitalBusinessService){
        long startTime = System.currentTimeMillis(); // 获取开始时间
        for (int i = 0; i < count; i++) {
            MedicalHospital medicalHospitals = iMedicalHospitalBusinessService.selectHospitalById("85d9ca35f56e431d9de4570985972401");
//            System.out.println(medicalHospitals.getName());
        }
        long endTime = System.currentTimeMillis(); // 获取结束时间
        long consuming = endTime - startTime;
        System.out.println(consuming);
        return consuming;
    }


    @Test
    public void test111(){
        System.out.println("测试一次查询");
        int a = 10;
        int b = 5000;
        long byIdTime = 0;
        for (int i = 0; i < a; i++) {
            byIdTime += getByIdTestTime(b,iMedicalHospitalBusinessService);
        }
        System.out.println("测试一次查询----平均用时用时"+byIdTime+"ms,平均用时"+byIdTime/(a*b)+"ms");
    }


    public long getByIdTestTime(int count,IMedicalHospitalBusinessService iMedicalHospitalBusinessService){
        long startTime = System.currentTimeMillis(); // 获取开始时间
        for (int i = 0; i < count; i++) {
            MedicalHospital medicalHospitals = iMedicalHospitalBusinessService.selectHospitalById("88ebc1ee4e264ce7a81c89abd06de12e");
//            System.out.println(medicalHospitals.getName());
        }
        long endTime = System.currentTimeMillis(); // 获取结束时间
        long consuming = endTime - startTime;
        System.out.println(consuming);
        return consuming;
    }

    @Test
//    @Transactional   //标明此方法需使用事务
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test2(){
        System.out.println("测试iMedicalHospitalService");
        Page<MedicalHospital> page = new Page<>();
        page.setSize(100);
        page.setCurrent(0);
        Page<MedicalHospital> page1 = iMedicalHospitalBusinessService.selectHospitalPage(page, null, null);
        List<MedicalHospital> medicalHospitals = page1.getRecords();
        for (MedicalHospital medicalHospital : medicalHospitals) {
            System.out.println(medicalHospital.toString());
        }
    }


    @Test
//    @Transactional   //标明此方法需使用事务
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test3(){
//        System.out.println("测试iMedicalHospitalService");
//        Page<MedicalHospital> page = new Page<>();
//        page.setCurrent(1);
//        page.setSize(5);
//        Page<MedicalHospital> medicalHospitalPage = iMedicalHospitalBusinessService.selectHospitalPage(page,"www", "");
//        for (MedicalHospital medicalHospital : medicalHospitalPage.getRecords()) {
//            System.out.println(medicalHospital.toString());
//        }
    }
}