package test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.medical.anchor.service.IUserBankService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalService;


public class Test1 extends BaseJunit4Test{

//    @Autowired //自动注入
//    private IUserService iUserService;

    @Autowired //自动注入
    private IMedicalHospitalService iMedicalHospitalService;
    
    @Autowired //自动注入
    private   IUserBankService userBankService;

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
      /*  System.out.println("测试iMedicalHospitalService");
        List<MedicalHospital> medicalHospitals = iMedicalHospitalService.selectList(null);
        for (MedicalHospital medicalHospital : medicalHospitals) {
            System.out.println(medicalHospital.toString());
        }*/
    	

//-- 43312319791130094X
//select * from medical_doctor_apply where user_id ="2c9aec356231d035016232f93eb50002"
//
//select * from oe_user_bank_card where user_id ="2c9aec356231d035016232f93eb50002"
//
//-- 460004199308110663
    	
    	Integer devCode =  userBankService.validateBankInfo("2c9aec356231d035016232f93eb50002","孙林萍",
    			"6228480158245248774","410822199210062015","95599",0);
	
    	System.out.println(devCode);
		//userBankService.addUserBank(user.getId(),acctName,acctPan,certId,tel);
    }
}