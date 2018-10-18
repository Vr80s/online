package com.xczh.consumer.market.controller.medical;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;

import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.vo.ProductVO;

/**
 * Description：医师控制器
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:48
 **/
@Controller
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsController.class);
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Autowired
    private GoodsService goodsService;
    @Value("${returnOpenidUri}")
    private String returnOpenidUri;
    private static String order_Details_Url = "/xcview/html/shop/commodity_details.html?productId=";

    /**
     * 医师动态列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsList(@Account(optional = true) Optional<String> accountIdOpt, @RequestParam("pageNumber") Integer pageNumber,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam(required = false) Integer type, @RequestParam("doctorId") String doctorId) {
        String userId = accountIdOpt.isPresent() ? accountIdOpt.get() : "";
        Page<MedicalDoctorPosts> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        Page<MedicalDoctorPosts> list = medicalDoctorPostsService.selectMedicalDoctorPostsPage(page, type, doctorId, userId);
        List<MedicalDoctorPosts> listMDP = list.getRecords();
        for (int i=0;i<listMDP.size();i++){
            if(listMDP.get(i).getProductId() != null){
            	try {
					
            		 ProductVO p = (ProductVO)goodsService.findProductById(listMDP.get(i).getProductId());
                     listMDP.get(i).setProductTitle(p.getName());
                     listMDP.get(i).setProductPrice(p.getPrice());
                     listMDP.get(i).setProductIsMarketable(p.getIsMarketable());
                     if(p.getProductImages() != null){
                         listMDP.get(i).setProductImages(p.getProductImages().get(0).getThumbnail());
                     }
                    listMDP.get(i).setDetailsUrl(returnOpenidUri+order_Details_Url+listMDP.get(i).getProductId());
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
        list.setRecords(listMDP);
        //过滤ios师承直播
//        List<MedicalDoctorPosts> list1 = new ArrayList<MedicalDoctorPosts>();
//        list.getRecords().forEach(medicalDoctorPosts -> {
//            if(Integer.valueOf(HeaderInterceptor.CLIENT.get()) == ClientType.IOS.getCode()){
//                if(!medicalDoctorPosts.getTeaching()){
//                    list1.add(medicalDoctorPosts);
//                }
//            } else {
//                list1.add(medicalDoctorPosts);
//            }
//
//        });
//        list.setRecords(list1);
        return ResponseObject.newSuccessResponseObject(list);
    }

}