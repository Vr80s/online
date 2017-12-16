package test;

import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class Test1 extends BaseJunit4Test{

//    @Autowired //自动注入
//    private IUserService iUserService;

    @Autowired //自动注入
    private IMedicalHospitalService iMedicalHospitalService;

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
        System.out.println("测试iMedicalHospitalService");
        List<MedicalHospital> medicalHospitals = iMedicalHospitalService.selectList(null);
        for (MedicalHospital medicalHospital : medicalHospitals) {
            System.out.println(medicalHospital.toString());
        }
    }
}