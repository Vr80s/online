package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.MessageDao;
import com.xczhihui.bxg.online.web.dao.OrderDao;
import com.xczhihui.bxg.online.web.service.OrderService;
import com.xczhihui.bxg.online.web.utils.*;
import com.xczhihui.bxg.online.web.vo.MessageShortVo;
import com.xczhihui.bxg.online.web.vo.OrderVo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单业务层接口实现类
 *
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
@Service
public class OrderServiceImpl  extends OnlineBaseServiceImpl implements OrderService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderDao   orderDao;
	@Autowired
	private CourseDao  courseDao;
	@Autowired
	private MessageDao  messageDao;
	
	@Value("${share.course.id:191}")
	private String shareCourseId;

	
    /**
     * 提交订单的时候，生成订单
     * @param ids
     * @param request
     */
    public Map<String,String> saveOrder(String orderNo,String ids,HttpServletRequest request){
        return orderDao.saveOrder(orderNo,ids,request);
    }

    /**
     * 获取用户全部订单信息
     * @return 所有订单信息
     */
    public Page<OrderVo> getMyAllOrder(Integer  orderStatus,Integer timeQuantum,Integer pageNumber, Integer pageSize,HttpServletRequest request){
        OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");
        if(u != null){
             return  orderDao.getMyAllOrder(orderStatus,timeQuantum,pageNumber, pageSize,u.getId());
        }
        return  null;
    }

    /**
     * 姜海成16-12-11，将支付成功的业务整合在一个方法。
     */
    @Override
    public void addPaySuccess(String orderNo,Integer payType,String transaction_id) {
    	String sql = "";
    	String id = "";
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	//查未支付的订单
    	sql = "select od.actual_pay,od.course_id,o.user_id,o.create_person,od.class_id from oe_order o,oe_order_detail od "
    			+ " where o.id = od.order_id and  o.order_no='"+orderNo+"' and order_status=0 ";
    	List<OrderVo> orders = orderDao.getNamedParameterJdbcTemplate().query(sql, new BeanPropertyRowMapper<OrderVo>(OrderVo.class));
    	if (orders.size() > 0) {
    		
    		/*
    		 * 查询这个订单下的课程，如果
    		 */
    		
    		//更新订单表
			sql = "update oe_order set order_status=1,pay_type="+payType+",pay_time=now(),pay_account='"+transaction_id+"' where order_no='"+orderNo+"' ";
			orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    		
    		//写用户报名信息表，如果有就不写了
			String apply_id = UUID.randomUUID().toString().replace("-", "");
			sql = "select a.id from oe_apply a where  a.user_id='"+orders.get(0).getUser_id()+"' ";
			List<Map<String, Object>> applies = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			if (applies.size() > 0) {
				apply_id = applies.get(0).get("id").toString();
			} else {
				sql = "insert into oe_apply(id,user_id,create_time,is_delete,create_person) "
						+ " values ('"+apply_id+"','"+orders.get(0).getUser_id()+"',now(),0,'"+orders.get(0).getCreate_person()+"')";
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
			}
			
			//更新用户is_apply为true（报过课）
			sql = "update oe_user set is_apply=1 where id='"+orders.get(0).getUser_id()+"' and is_apply=0";
			orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    		
			for (OrderVo order : orders){
				int  gradeId = 0;
				String qqno="";
				//区分课程是微课还是职业课
				sql="select course_type,class_template,grade_student_sum,ifnull(grade_qq,0) grade_qq from oe_course  where id="+order.getCourse_id();
				List<Map<String, Object>> courses = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
				//如果微课，获取已报名人数少于额定人数的班级，如果没有此种班级，自动生成班级
				if(Integer.valueOf(courses.get(0).get("course_type").toString())==1){
					qqno=courses.get(0).get("grade_qq").toString();
					sql="select id from oe_grade where is_delete=0 and grade_status = 1 and course_id ='"+order.getCourse_id()+"' and curriculum_time is null  and "+
						"  ifnull(student_count+default_student_count,0)< student_amount order by create_time  limit 1 ";
					List<Map<String, Object>> grades = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
					//存在报名未结束的班级
					if (grades.size() > 0) {
						gradeId = Integer.valueOf(grades.get(0).get("id").toString());
					}else {
						//查看上一个班级是第几期，要在这期上面加1
						sql="select count(id)+1 number,(select AUTO_INCREMENT gradeId FROM information_schema.TABLES WHERE  TABLE_NAME ='oe_grade' and TABLE_SCHEMA='online') id  from oe_grade  where course_id="+order.getCourse_id();
						List<Map<String, Object>> gradeInfos= orderDao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
						Map<String, Object> gradeInfo=gradeInfos.get(0);
						//生成班级
						sql=" insert into oe_grade (create_time,is_delete,course_id,name,qqno,status,sort,grade_status,student_amount) " +
							" values (now(),0,"+order.getCourse_id()+",'"+courses.get(0).get("class_template").toString()+Integer.valueOf(gradeInfo.get("number").toString())+"期','"+
							 courses.get(0).get("grade_qq")+"',1,1,1,"+courses.get(0).get("grade_student_sum")+")";
						orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
						gradeId=Integer.valueOf(gradeInfo.get("id").toString());
					}
				}else{
					String class_id = orders.get(0).getClass_id();//报名可以选择班级
					if (class_id == null || "".equals(class_id.trim())) {
						//如果是职业课，获取报名人数少于额定人数而且当前时间小于报名截止日期，如果没有此班级就将学院挂在虚拟班级(班级id=0)
						sql="select id,ifnull(qqno,0) qqno  from oe_grade where is_delete=0 and grade_status = 1 and course_id ='"+order.getCourse_id()+"' and "+
								" unix_timestamp(now()) <= unix_timestamp(curriculum_time) and  ifnull(student_count+default_student_count,0)<student_amount order by curriculum_time limit 1 ";
						List<Map<String, Object>> grades = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
						if (grades.size() > 0) {
							gradeId = Integer.valueOf(grades.get(0).get("id").toString());
							qqno=grades.get(0).get("qqno").toString();
						}
					} else {
						gradeId = Integer.valueOf(class_id.trim());
					}
				}

				//写用户、报名、课程中间表
				id = UUID.randomUUID().toString().replace("-", "");
				sql = "select (ifnull(max(cast(student_number as signed)),'0'))+1 from apply_r_grade_course where grade_id="+gradeId;
				Integer no = orderDao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
				String sno = no < 10 ? "00"+no : (no < 100 ? "0"+no : no.toString());
				sql = "insert into apply_r_grade_course (id,course_id,grade_id,apply_id,is_payment,create_person,user_id,create_time,cost,student_number)"
						+ " values('"+id+"',"+order.getCourse_id()+","+gradeId+",'"+apply_id+"',2,'"+order.getCreate_person()+"','"+order.getUser_id()+"',now(),"+order.getActual_pay()+","
								+ " '"+sno+"')";
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);

				//写用户视频表
				sql = "insert into user_r_video (id,create_person,sort,video_id,user_id,apply_id,course_id,status) "
						+ " select uuid(),'"+order.getCreate_person()+"',sort,id,'"+order.getUser_id()+"','"+apply_id+"',course_id,status "
								+ "from oe_video where course_id="+order.getCourse_id()+" and is_delete=0 ";
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);

				//更新班级报名人数
				if (gradeId != 0) {
					sql = "update oe_grade o set o.student_count=ifnull(o.student_count,0)+1 where o.id="+gradeId;
					orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
				}

				//写入用户课程关卡信息
				sql= "insert into oe_barrier_user (id,user_id,barrier_id,lock_status,course_id) "
						+ " select  replace(uuid(),'-',''),'"+order.getUser_id()+"',t.id,if(t.parent_id is null or t.parent_id='',1,0), "
								+ " course_id  from oe_barrier t where t.course_id='"+order.getCourse_id()+"' and t.status=1 ";
				orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);

				//如果是限时免费课程，不参与分销，业务到此结束
				if(Double.valueOf(order.getActual_pay())==0){
					continue;
				}

				//如果是购买大使课，成为分享大使
				if (shareCourseId.trim().equals(order.getCourse_id().toString().trim())) {
					id = UUID.randomUUID().toString().replace("-", "");
					orderDao.getNamedParameterJdbcTemplate().update("update oe_user set share_code='"+id+"' "
							+ "where share_code is null and id='"+order.getUser_id()+"' ", paramMap);
				}

				//如果是微课参与分销，如果购买人有上级，此订单更新为分销订单
//				if("1".equals(courses.get(0).get("course_type").toString())){
					sql = "update oe_order o set o.order_from=1 where o.order_no='"+orderNo+"' "
							+ " and o.user_id=(select id from oe_user where parent_id is not null and parent_id != '' and id = o.user_id);";
					orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
//				}
			}
		}
	}

	/**
	 * 为购买用户发送消息通知
	 */
	public  void  savePurchaseNotice(String  basePath,String orderNo){
		//为购买用户发送消息通知
		List<Map<String, Object>> courses=courseDao.findNewestCourse(orderNo);
		if(courses.size() > 0) {
			for(Map<String,Object>  course : courses){
				String msg_id = UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
				String msg_link = basePath+"/course/courses?courseId=" + course.get("course_id").toString();
				String content = "恭喜您成功报名课程<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\"><p>"+course.get("course_name")+"</p></a>";
				//"快去加入班级QQ群："+course.get("qqno")+"~";
				MessageShortVo messageShortVo = new MessageShortVo();
				messageShortVo.setUser_id(course.get("user_id").toString());
				messageShortVo.setId(msg_id);
				messageShortVo.setContext(content);
				messageShortVo.setCreate_person(course.get("user_id").toString());
				messageShortVo.setType(1);
				messageDao.saveMessage(messageShortVo);
			}
		}
	}
    
    @Override
    public Integer getOrderStatus(String orderNo) {
        return orderDao.getOrderStatus(orderNo);
    }
    
    @Override
	public Map<String, Object> checkPayInfo(String orderNo) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	String sql = "select o.actual_pay,GROUP_CONCAT(c.grade_name) as course_name,o.order_no,GROUP_CONCAT(c.id) as course_id,o.user_id from oe_order o,oe_order_detail od,oe_course c  "
    			+ "where o.id=od.order_id and od.course_id=c.id and o.order_status=0 and o.order_no=:order_no GROUP BY o.id";
    	paramMap.put("order_no", orderNo);
    	List<Map<String, Object>> lst = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
    	if (lst != null && lst.size() > 0) {
			/** 在加入购物车的时候已经做了是否已经购买过课程的判断 */
    		String courseIds = (String) lst.get(0).get("course_id");
			String user_id = (String) lst.get(0).get("user_id");
			sql = "select count(t1.id) from oe_order t1,oe_order_detail t2 " +
					"where t1.id=t2.order_id and t2.course_id in ("+courseIds+") and t1.order_status=1 and t1.user_id=?";
			if (orderDao.queryForInt(sql,user_id) > 0) {
				lst.get(0).put("error_msg", "当前订单内有课程已经购买");
			}
			return lst.get(0);
		}
    	throw new RuntimeException("找不到订单信息！");
	}

    /**
     * 根据订单号查找订单
     * @param orderNo  订单号
     * @return
     */
    public OrderVo findOrderByOrderNo(String orderNo){
         return  orderDao.findOrderByOrderNo(orderNo);
    }

	public OrderVo findOrderByOrderId(String orderId){
		return  orderDao.findOrderByOrderId(orderId);
	}


    public Map<String,Object>  findOrderByCourseId(String ids,String userId,String orderNo){
        return orderDao.findOrderByCourseId(ids, userId,orderNo);
    }

	/**
	 * 获取购买课程现价总和
	 * @param ids  课程id号
	 * @return
	 */
	public Boolean findCourseIsFree(String ids){
		return   orderDao.findCourseIsFree(ids);
	}

	@Override
	public Map<String, Object> addWeixinPayUnifiedorder(String body,String orderNo,String productId,int pay,String attach) {
		Map<String, Object> returnmap = new HashMap<String, Object>();
		try {
			SortedMap<Object, Object> packageParams = new TreeMap<>();
			// APP_ID
			packageParams.put("appid", OnlineConfig.APP_ID);
			// 商品描述，课程名称
			packageParams.put("body", body);
			// 商业号
			packageParams.put("mch_id", OnlineConfig.MCH_ID);
			// 随机字符串
			packageParams.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
			// 通知回调地址
			packageParams.put("notify_url",OnlineConfig.NOTIFY_PAY);
			// 订单号
			packageParams.put("out_trade_no", orderNo);
			//附加参数
			packageParams.put("attach",attach);
			// 商品id，课程id
			packageParams.put("product_id", productId);
			// 发起电脑IP
			packageParams.put("spbill_create_ip", PayCommonUtil.getIPLocal());
			packageParams.put("total_fee", pay);
			// 交易类型，取值如下：JSAPI，NATIVE，APP， 默认NATIVE，详细说明见
			packageParams.put("trade_type", "NATIVE");

			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 1);
			packageParams.put("time_expire",new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime()));

			// 签名
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, OnlineConfig.API_KEY);
			packageParams.put("sign", sign);
			
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(PayConfigUtil.UNIFIEDORDER_URL, requestXML);
			Map<String, Object> map = XMLUtil.doXMLParse(resXml);
			String return_code = String.valueOf(map.get("return_code"));
			if (map.containsKey("err_code")) {
				returnmap.put("errorMsg", map.get("err_code_des"));
				return returnmap;
			} 
			if ("SUCCESS".equalsIgnoreCase(return_code)) {
				returnmap.put("codeimg", encodeQrcode(String.valueOf(map.get("code_url"))));
				logger.info("微信预支付下单，订单号：{}，回调地址：{}", orderNo,OnlineConfig.NOTIFY_PAY);
				return returnmap;
			} else {
				returnmap.put("errorMsg", "微信预支付下单失败,错误信息：" + map.get("return_msg"));
				logger.warn("微信预支付下单失败,错误信息：{}", map.get("return_msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnmap.put("errorMsg", "微信预支付下单失败,错误信息：" + e.getMessage());
		}
		
		return returnmap;
	}
	
	private String encodeQrcode(String urlCode) throws WriterException {
		int width = 300; // 二维码图片宽度
		int height = 300; // 二维码图片高度

		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());// 内容所使用字符集编码

		BitMatrix bitMatrix = new MultiFormatWriter().encode(urlCode, BarcodeFormat.QR_CODE, width, height, hints);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

		ByteArrayOutputStream out = null;
		// 输出二维码图片流
		try {
			out = new ByteArrayOutputStream();
			ImageIO.write(image, "png", Base64.getEncoder().wrap(out));
			return "data:image/png;base64,"+out.toString(StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	@Override
	public String checkOrder(String orderNo) {
		return orderDao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList("select id from oe_order where order_no = ? and order_status = 1", orderNo)
				.size() > 0 ? "yes" : "no"; 
	}

	/**
	 * 取消订单或删除订单
	 * @param type:0删除订单 1:取消订单
	 * @param orderNo
	 */
	public void  updateOrderStatu(Integer type,String orderNo,OnlineUser user){
		String sql="";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id",user.getId());
		paramMap.put("order_no", orderNo);
		if (type==0){ //删除失效订单
			sql ="select id from oe_order where order_no=:order_no and order_status =2 and user_id=:user_id";
			String orderId=orderDao.getNamedParameterJdbcTemplate().queryForObject(sql,paramMap,String.class);
			//删除订单表
			sql=" delete from oe_order where order_no=:order_no and user_id=:user_id and order_status=2";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
            //删除订单详情表
			sql=" delete from oe_order_detail where order_id='"+orderId+"'";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
		}else {
			sql=" update oe_order set order_status=2 where order_no=:order_no and user_id=:user_id and order_status=0";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
			
			sql="update oe_fcode set status=2,used_user_id=null,used_course_id=null,use_order_no=null,use_time=null where use_order_no=:order_no ";
			orderDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
		}
	}


	/**
	 *查询目前购买的课程能参与的活动,以及每种活动下的购买课程信息
	 * @param ids 购买课程的id数组
	 * @return
	 */
	public List<Map<String,Object>> findActivityOrder(HttpServletRequest request,String ids){
		return  orderDao.findActivityOrder(request,ids);
	}

	/**
	 * 获取用户购买课程所享受的所有活动，以及每种活动下能参与的课程
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>>  findActivityCourse(String ids){
		return orderDao.findActivityCourse(ids);
	}

	/**
	 * 根据课程id查询课程
	 * @param idArr  课程id号
	 * @return 返回对应的课程对象
	 */
	public  List<Map<String,Object>>   findNotActivityOrder(String  idArr,String orderNo,HttpServletRequest request){
		return  orderDao.findNotActivityOrder(idArr,orderNo,request);
	}

	@Override
	public Object consumptionList(String userId, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return orderDao.consumptionList(userId, pageNumber,pageSize);
	}

	@Override
	public void updateOrderNo(String orderNo, String orderId) {
		orderDao.updateOrderNo(orderNo,orderId);
	}
}
