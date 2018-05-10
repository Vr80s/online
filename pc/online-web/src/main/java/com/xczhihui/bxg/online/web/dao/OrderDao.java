package com.xczhihui.bxg.online.web.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.web.base.utils.RandomUtil;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.vo.MyConsumptionRecords;
import com.xczhihui.bxg.online.web.vo.OrderVo;

/**
 * 订单底层实现累
 *
 * @author rongcai Kang
 */
@Repository
public class OrderDao extends SimpleHibernateDao {

    @Autowired
    private  CourseDao  courseDao;

    /**
     * 获取用户全部订单信息
     * @param orderStatus 0:未支付 1:已支付 2:已关闭
     * @param timeQuantum 0:全部  1:近三个月  2:今年内  3:今年之前的
     * @return 所有订单信息
     */
    public Page<OrderVo>  getMyAllOrder(Integer  orderStatus,Integer timeQuantum,Integer pageNumber, Integer pageSize,String userId  ){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 400 : pageSize;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("orderStatus", orderStatus);
        StringBuffer  strBf=new StringBuffer();
        strBf.append(" select o.id,o.order_no,o.preferenty_money,o.actual_pay,o.purchaser,o.order_status,o.create_time,( actual_pay+preferenty_money) as original_cost");
        strBf.append(" from oe_order o  where  o.user_id=:userId");
        //订单状态不等于空，说明需要更具订单状态和时间段进行搜索
        if(orderStatus != null && orderStatus !=-1) {
            strBf.append(" and  o.order_status =:orderStatus ");
        }
        //近三个月订单
        if(timeQuantum == 1){
            strBf.append(" and  o.create_time  BETWEEN   (now() - INTERVAL  3  MONTH )  and  now()   ");
        }else  if(timeQuantum == 2){ //今年内
            strBf.append(" and  SUBSTRING(o.create_time,1,4) =SUBSTRING(CURDATE(),1,4 )  ");
        }else  if(timeQuantum == 3){ //2017年以前的订单
            strBf.append(" and  SUBSTRING(o.create_time,1,4) < SUBSTRING(CURDATE(),1,4 )  ");
        }
        strBf.append("  order by o.create_time desc");
        Page<OrderVo> page= this.findPageBySQL(strBf.toString(), paramMap, OrderVo.class, pageNumber, pageSize);
        if (page.getItems().size()>0){
            //循环订单，获取每个订单中包含的商品
            for (OrderVo  orderVo : page.getItems()){
                paramMap = new HashMap<>();
                paramMap.put("order_id",orderVo.getId());
                String sql = "select \n" +
                        "  c.id,\n" +
                        "  c.smallimg_path,\n" +
                        "  c.grade_name,\n" +
                        "  c.course_length,\n" +
                        "  c.type,\n" +
                        "  c.direct_id,\n" +
                        "  c.course_type,\n" +
                        "  od.price,\n" +
                        "  od.actual_pay actualPay,\n" +
                        "  c.`lecturer`\n" +
                        "from\n" +
                        "  oe_order_detail od \n" +
                        "  join oe_course c \n" +
                        "    on od.course_id = c.id \n" +
                        "  where od.order_id = :order_id ";
                List<Map<String,Object>> courses = this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
                orderVo.setOrderDetail(courses);
            }
        }

        return page;
    }

    /**
     * 返回当前订单支付状态
     */
    public Integer getOrderStatus(String orderNo) {
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append("select order_status from oe_order where order_no =:orderNo");
        paramMap.put("orderNo", orderNo);
        List<OrderVo> list = this.findEntitiesByJdbc(OrderVo.class, sql.toString(), paramMap);
        return list.size()>0 ? list.get(0).getOrder_status() : null;
    }

    /**
     * 返回当前订单支付状态
     */
    public Integer getOrderStatusById(String orderId) {
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append("select order_status from oe_order where id =:orderId");
        paramMap.put("orderId", orderId);
        List<OrderVo> list = this.findEntitiesByJdbc(OrderVo.class, sql.toString(), paramMap);
        return list.size()>0 ? list.get(0).getOrder_status() : null;
    }

    /**
     * 根据订单号查找订单
     * @param orderNo  订单号
     * @return
     */
    public OrderVo findOrderByOrderNo(String orderNo){
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("orderNo", orderNo);
        sql.append("select o.id, o.actual_pay,GROUP_CONCAT(c.grade_name) as course_name,o.order_status,o.order_from,o.order_no,GROUP_CONCAT(c.id) as course_id,o.user_id from oe_order o,oe_order_detail od,oe_course c  ");
        sql.append(" where o.id=od.order_id and od.course_id=c.id and o.order_status=0 and o.order_no=:orderNo GROUP BY o.id");
        List<OrderVo> listOrder = this.findEntitiesByJdbc(OrderVo.class, sql.toString(), paramMap);
        return listOrder.size()>0 ? listOrder.get(0) : null;
    }
    
    /**
     * 根据订单号查找订单
     * @param orderNo  订单号
     * @return
     */
    public OrderVo findOrderByOrderNoAndStatus(String orderNo,Integer orderStatus){
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("orderNo", orderNo);
        sql.append("select o.id, o.actual_pay,GROUP_CONCAT(c.grade_name) as course_name,o.order_status,o.order_from,o.order_no,GROUP_CONCAT(c.id) as course_id,o.user_id from oe_order o,oe_order_detail od,oe_course c  ");
        sql.append(" where o.id=od.order_id and od.course_id=c.id and o.order_no=:orderNo  ");
        if(orderStatus!=null){
        	  paramMap.put("orderStatus", orderStatus);
        	  sql.append(" and o.order_status=:orderStatus ");
        }
        sql.append("  GROUP BY o.id ");
        List<OrderVo> listOrder = this.findEntitiesByJdbc(OrderVo.class, sql.toString(), paramMap);
        return listOrder.size()>0 ? listOrder.get(0) : null;
    }
    

    public OrderVo findOrderByOrderId(String orderId){
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("orderId", orderId);
        sql.append("select o.actual_pay,GROUP_CONCAT(c.grade_name) as course_name,o.order_no,GROUP_CONCAT(c.id) as course_id,o.user_id from oe_order o,oe_order_detail od,oe_course c  ");
        sql.append(" where o.id=od.order_id and od.course_id=c.id and o.order_status=0 and o.id=:orderId GROUP BY o.id");
        List<OrderVo> listOrder = this.findEntitiesByJdbc(OrderVo.class, sql.toString(), paramMap);
        return listOrder.size()>0 ? listOrder.get(0) : null;
    }

   /**
     * 没半个小时执行一次，将数据库中已超过24小时
     * 未支付的订单关闭
     */
    public  void  updateStatus(){
        Map<String,Object> paramMap = new HashMap<>();
        String  sql = " update  oe_order set order_status=2  where   create_time  <  (now() - INTERVAL  1  DAY)  and  order_status=0 ";
        this.getNamedParameterJdbcTemplate().update(sql, paramMap);
        
        //优惠码
        sql="update oe_fcode set status=2,used_user_id=null,used_course_id=null,use_order_no=null,use_time=null where use_order_no in "
        		+ " (select id from oe_order  where   create_time  <  (now() - INTERVAL  1  DAY)  and  order_status=0) ";
        this.getNamedParameterJdbcTemplate().update(sql,paramMap);
    }

    /**
     * 产生佣金订单
     * @param
     */
    public void addShareOrder(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String sql = "select t1.user_id,t1.order_no,d.course_id,t2.grade_name,d.actual_pay,d.price from oe_order t1,oe_order_detail d,oe_course t2 "
                + " where t1.id=d.order_id and d.course_id=t2.id and t1.order_status=1 "
                + " and t1.is_count_brokerage=0 and t1.order_from=1 and d.actual_pay > 0";
//        + " and t1.is_count_brokerage=0 and t1.order_from=1 and t2.course_type=1 and d.actual_pay > 0";
        List<Map<String, Object>> lst = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);

        if (lst.size() > 0) {

            //用户id
            String user_id = lst.get(0).get("user_id").toString();
            //购买订单号
            String order_no = lst.get(0).get("order_no").toString();
            //主键
            String id = null;
            //分销订单号
            String shareOrderNo = null;
            //保存数据sql语句
            String insertsql = null;

            //查询往上三级用户的id
            sql = "select p.id pid,pp.id ppid,ppp.id pppid from oe_user m "
                    + " left join oe_user p on m.parent_id = p.id"
                    + " left join oe_user pp on p.parent_id = pp.id "
                    + " left join oe_user ppp on pp.parent_id = ppp.id "
                    + "  where m.id='"+user_id+"' ";
            Map<String,Object> userids = this.getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);

            for(Map<String, Object> c : lst){
                //课程id
                String course_id = c.get("course_id").toString();
                //课程名称
                String grade_name = c.get("grade_name").toString();
                //实际支付
                String actual_pay = c.get("actual_pay").toString();

                //一级佣金
                if (userids.get("pid") != null) {
                    id = UUID.randomUUID().toString().replace("-", "");
                    shareOrderNo = "share_"+TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
                    insertsql = "insert into oe_share_order ("
                            + " id,buy_user_id,order_no,share_order_no,target_user_id,course_id,course_name,actual_pay,`level`,subsidies,sort)"
                            + " values( '"+id+"','"+user_id+"','"+order_no+"','"+shareOrderNo+"','"+userids.get("pid").toString()+"',"
                            + " "+course_id+",'"+grade_name+"',"+actual_pay+",0,"+actual_pay+"*0.10,0 ) ";
                    this.getNamedParameterJdbcTemplate().update(insertsql, paramMap);
                }
               /* //二级佣金
                if (userids.get("ppid") != null) {
                    id = UUID.randomUUID().toString().replace("-", "");
                    shareOrderNo = "share_"+TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
                    insertsql = "insert into oe_share_order ("
                            + " id,buy_user_id,order_no,share_order_no,target_user_id,course_id,course_name,actual_pay,`level`,subsidies,sort)"
                            + " values( '"+id+"','"+user_id+"','"+order_no+"','"+shareOrderNo+"','"+userids.get("ppid").toString()+"',"
                            + " "+course_id+",'"+grade_name+"',"+actual_pay+",1,"+actual_pay+"*0.08,0 ) ";
                    this.getNamedParameterJdbcTemplate().update(insertsql, paramMap);
                }
                //级佣金
                if (userids.get("pppid") != null) {
                    id = UUID.randomUUID().toString().replace("-", "");
                    shareOrderNo = "share_"+TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
                    insertsql = "insert into oe_share_order ("
                            + " id,buy_user_id,order_no,share_order_no,target_user_id,course_id,course_name,actual_pay,`level`,subsidies,sort)"
                            + " values( '"+id+"','"+user_id+"','"+order_no+"','"+shareOrderNo+"','"+userids.get("pppid").toString()+"',"
                            + " "+course_id+",'"+grade_name+"',"+actual_pay+",2,"+actual_pay+"*0.05,0 ) ";
                    this.getNamedParameterJdbcTemplate().update(insertsql, paramMap);
                }*/
            }
            //将订单是否计算佣金状态改成1(不计算)
            sql="update oe_order set is_count_brokerage=1 where order_no='"+order_no+"' ";
            this.getNamedParameterJdbcTemplate().update(sql,paramMap);
        }

    }

    /**
     *查询目前购买的课程能参与的活动,以及每种活动下的购买课程信息
     * @param idArr 购买课程的id数组
     * @return
     */
    public List<Map<String,Object>>  findActivityOrder(HttpServletRequest request,String idArr){
    	Object uo = UserLoginUtil.getLoginUser(request);
    	String userId = uo == null ? null : UserLoginUtil.getLoginUser(request).getId();
    	Map<String,Object[]> check = new HashMap<String,Object[]>();
        String[] ids = idArr.split(",");
        Map<String,Object> paramMap = new HashMap<>();
        Map<String,String> courseRule = new HashMap<>(); //存放所买课程参与的活动规则id，以课程id为key
        paramMap.put("ids", Arrays.asList(ids));
        String sql= "select * from ( SELECT  ard.rule_id,ar.name,ar.url,ard.reach_money,ard.minus_money,ard.id from oe_activity_rule ar,oe_activity_rule_detail ard " +
                    " where ar.id=ard.rule_id and  ard.course_id in (:ids) and now() BETWEEN ard.start_time " +
                    " and ard.end_time  order by ard.reach_money asc) a group by a.rule_id ";
      /*  String sql=" SELECT  ard.rule_id,ar.name,ar.url,ard.reach_money,ard.minus_money from oe_activity_rule ar,oe_activity_rule_detail ard " +
                   " where ar.id=ard.rule_id and  ard.course_id in (:ids) and now() BETWEEN ard.start_time  and ard.end_time  order by ard.reach_money asc limit 1 ";*/
        List<Map<String,Object>> activitys=this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
       
        Double  totalAmount=0.00d;  //活动后总支付
        Double fcodeMoney = 0.00d;  //优惠码优惠总金额
        String fcodestr = "";
        
        //用户购买的课程能参与的活动
        if(activitys.size() > 0 ){
            for (Map<String,Object> activity : activitys){
                String ruleContent="";  //规则内容
                //循环，获取参与每种活动的并被用户购买的课程信息,并计算此次活动购买的课程所满足的规则
                //1、获取参与此活动并且用户购买的课程
                sql= " select c.current_price priceCount, c.id ,c.course_type, c.grade_name as courseName ,c.smallimg_path as smallImgPath,c.original_cost as originalCost,c.current_price as currentPrice, now() as create_time, " +
                        " 0 as preferentyMoney,ard.id rule_detailId from oe_course c,oe_activity_rule_detail ard where c.id=ard.course_id  and ard.rule_id='"+activity.get("rule_id")+"'"+
                        " and c.id in (:ids) group by c.id ";
                List<Map<String,Object>> courses=this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
                activity.put("course",courses);
                
                //查询用户购买的这些课程可用的优惠码
                if (userId != null) {
                	for (Map<String, Object> course : courses) {
                		String courseId = course.get("id").toString();
                		Double price = Double.valueOf(course.get("currentPrice").toString());
                		List<Map<String,Object>> fcodes = this.getNamedParameterJdbcTemplate().queryForList
                				("select t1.fcode from oe_fcode_user t1,"
                						+ "oe_fcode t2,oe_fcode_lot t3,oe_fcode_activity t4,oe_fcode_activity_course t5 "
                						+ " where t1.fcode=t2.fcode and t2.lot_no=t3.lot_no and t3.lot_no=t4.lot_no and "
                						+ " t4.id=t5.activity_id and t1.user_id='"+userId+"' "
                						+ " and t3.expiry_time > now() and t2.`status`=2 and t5.course_id="+course.get("id"),paramMap);
                		if (fcodes.size() > 0) {
                			String fcode = fcodes.get(0).get("fcode").toString();
                			//避免多个课程使用一个优惠码的情况，如果出现这种情况，给金额大的使用
                			if (!check.containsKey(fcode) || (Double)check.get(fcode)[1] < price) {
                				check.put(fcode, new Object[]{courseId,price});
                			}
                		}
                	}
                	for (Map<String, Object> course : courses) {
                		for (Map.Entry<String,Object[]> ck : check.entrySet()) {
                			if (course.get("id").toString().equals(ck.getValue()[0])) {
                				course.put("fcode", ck.getKey());
                				course.put("priceCount", Double.valueOf("0.00"));
                				fcodeMoney += (Double)ck.getValue()[1];
                				fcodestr += (","+course.get("id").toString()+"-"+ck.getKey());
                			}
                		}
                	}
                	if (fcodestr.length() > 0 && fcodestr.startsWith(",")) {
                		fcodestr = fcodestr.substring(1);
                	}
				}
                
                //2、计算购买课程满足此次活动的哪条规则
                //1)此次购买参与活动课程的总计
                sql=" select sum(a.current_price) as current_price from (select  c.current_price   from oe_course c,oe_activity_rule_detail ard  " +
                        " where c.id = ard.course_id  and c.id in (:ids) and  now() BETWEEN ard.start_time  and ard.end_time and ard.rule_id='"+activity.get("rule_id")+"' group by c.id ) a ";
                List<Map<String,Object>>  courseTotalPrice = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
                String totalPrice= courseTotalPrice.get(0).get("current_price").toString();  //购买课程总价钱
                //使用优惠码
                Double tt = Double.valueOf(totalPrice) - fcodeMoney;
                totalPrice = tt < 0 ? "0" : tt.toString();
                
                //2)获取购买课程参与的活动满足的规则(当总价钱大于规则中某条的活动金额)
                sql=" SELECT * from  oe_activity_rule_detail  where rule_id='"+activity.get("rule_id")+"' and "+totalPrice+" >= reach_money and course_id in (:ids)   ORDER BY  reach_money desc limit "+courses.size();
                List<Map<String,Object>> activityRule = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
                Double minusMoney=0.00d; //此次活动优惠金额
                if(activityRule.size() > 0 ){
                    ruleContent="已购满"+String.format("%.2f", activityRule.get(0).get("reach_money"))+"元,已减"+String.format("%.2f",activityRule.get(0).get("minus_money"))+"元";
                    activity.put("subtotal",Double.valueOf(totalPrice)-Double.valueOf(activityRule.get(0).get("minus_money").toString()));
                    activity.put("priceDifference",activityRule.get(0).get("minus_money"));
                    totalAmount=totalAmount + (Double.valueOf(totalPrice)-Double.valueOf(activityRule.get(0).get("minus_money").toString()));
                    minusMoney=Double.valueOf(activityRule.get(0).get("minus_money").toString());
                    //循环此活动下课程,将课程参与的活动规则存在map集合中
                    for (Map<String,Object> ruleDetai: activityRule){
                        courseRule.put(ruleDetai.get("course_id").toString(),ruleDetai.get("id").toString()+"-"+minusMoney);
                    }
                }else{
                    //2)获取购买课程参与的活动满足的规则(总价钱小于规则中最低的活动金额)
                    Double priceDifference=Double.valueOf(activity.get("reach_money").toString())-Double.valueOf(totalPrice);
                    ruleContent="购满"+String.format("%.2f",activity.get("reach_money"))+"元减"+String.format("%.2f",activity.get("minus_money"))+"元,还差"+String.format("%.2f",priceDifference)+"元";
                    activity.put("subtotal",totalPrice);
                    activity.put("priceDifference",0);
                    totalAmount=totalAmount+Double.valueOf(totalPrice);
                    minusMoney=Double.valueOf(activity.get("minus_money").toString());
                }
                activity.put("ruleContent",ruleContent);
                activity.put("totalPrice",totalPrice);
            }
            //活动后需要支付的金额存在session里面,key值为订单号
            String orderNo=TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
            activitys.get(0).put("orderNo",orderNo);
            request.getSession().setAttribute(orderNo, totalAmount + "#" + idArr + "#" + fcodestr);
            request.getSession().setAttribute(orderNo+"_fcode", check);
        }
        //存入session中
        request.getSession().setAttribute("courseRule", courseRule);
        return  activitys;
    }

    /**
     * 根据课程id查询课程
     * @param idArr  课程id号
     * @return 返回对应的课程对象
     */
    public  List<Map<String,Object>>   findNotActivityOrder(String  idArr,String orderNo,HttpServletRequest request){
    	Object uo = UserLoginUtil.getLoginUser(request);
    	String userId = uo == null ? null : UserLoginUtil.getLoginUser(request).getId();
        List<Map<String,Object>> courses=null;
        Double totalAmount=0.00d;  //此订单总金额
        String[] ids = idArr.split(",");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ids", Arrays.asList(ids));
        //获取购买的课程中所有活动课程id
        Set<String> courseIds = new HashSet<>();
        String sql ="select  course_id from oe_activity_rule_detail  where course_id in (:ids) and NOW() BETWEEN  start_time and end_time group by course_id";
        List<Map<String,Object>> cours=this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        for (Map<String,Object> cour : cours){
            courseIds.add(cour.get("course_id").toString());
        }
        sql =" SELECT c.current_price priceCount,c.id ,c.course_type, c.grade_name as courseName ,c.smallimg_path as smallImgPath," +
        		"c.type,direct_id,"+
                " c.original_cost as originalCost, c.current_price as currentPrice,now() as create_time,0 as preferentyMoney " +
                " from oe_course c where c.id in (:ids) ";
        if(courseIds.size()>0){
            paramMap.put("courseIds",courseIds);
            sql += " and c.id not in (:courseIds)";
        }
        courses=this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
        Double subTotal=0.00d;
        //循环累加非活动课程的当前价格
        for(Map<String,Object> course : courses){
        	subTotal+=Double.valueOf(course.get("currentPrice").toString());
        }
        
        //查询用户购买的这些课程可用的优惠码
        Double fcodeMoney = 0.00d;  //优惠码优惠总金额
        String fcodestr = "";
        if (orderNo != null && !"".equals(orderNo) && request.getSession().getAttribute(orderNo) != null) {
        	if (request.getSession().getAttribute(orderNo).toString().split("#").length >= 3) {
        		fcodestr = request.getSession().getAttribute(orderNo).toString().split("#")[2];
			}
		}
        Map<String,Object[]> check = new HashMap<String,Object[]>();
        if (orderNo != null && !"".equals(orderNo) && request.getSession().getAttribute(orderNo+"_fcode") != null) {
        	check = (Map<String,Object[]>)request.getSession().getAttribute(orderNo+"_fcode");
		}
        
        if (userId != null) {
        	for(Map<String,Object> course : courses){
        		String courseId = course.get("id").toString();
        		Double price = Double.valueOf(course.get("currentPrice").toString());
        		List<Map<String,Object>> fcodes = this.getNamedParameterJdbcTemplate().queryForList
        				("select t1.fcode from oe_fcode_user t1,"
        						+ "oe_fcode t2,oe_fcode_lot t3,oe_fcode_activity t4,oe_fcode_activity_course t5 "
        						+ " where t1.fcode=t2.fcode and t2.lot_no=t3.lot_no and t3.lot_no=t4.lot_no "
        						+ " and t4.id=t5.activity_id and t1.user_id='"+userId+"' "
        						+ " and t3.expiry_time > now() and t2.`status`=2 and t5.course_id ="+courseId,paramMap);
        		if (fcodes.size() > 0) {
        			String fcode = fcodes.get(0).get("fcode").toString();
        			//避免多个课程使用一个优惠码的情况，如果出现这种情况，给金额大的使用
        			if (!check.containsKey(fcode)) {
        				check.put(fcode, new Object[]{courseId,price});
        			}
        		}
        	}
        	for (Map<String, Object> course : courses) {
        		for (Map.Entry<String,Object[]> ck : check.entrySet()) {
        			if (course.get("id").toString().equals(ck.getValue()[0])) {
        				course.put("fcode", ck.getKey());
        				course.put("priceCount", Double.valueOf("0.00"));
        				fcodeMoney += (Double)ck.getValue()[1];
        				fcodestr += (","+course.get("id").toString()+"-"+ck.getKey());
        			}
        		}
        	}
        	if (fcodestr.length() > 0 && fcodestr.startsWith(",")) {
        		fcodestr = fcodestr.substring(1);
        	}
		}
       

        //计算不能参与活动的课程总价
      /*  sql=  " select IFNULL(sum(a.current_price),0) current_price from (select c.id,c.current_price,ard.rule_id from oe_course c " +
                " left join oe_activity_rule_detail ard on c.id=ard.course_id " +
                " where c.id in (:ids) and rule_id is null) a ";
        Double subTotal=this.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Double.class);*/
        if(orderNo == null || "".equals(orderNo)){
            //活动后需要支付的金额存在session里面,key值为订单号
            orderNo=TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
        }else{
            String[] params=request.getSession().getAttribute(orderNo).toString().split("#");
            totalAmount=Double.valueOf(params[0]);
        }
        if( courses.size() > 0){
            courses.get(0).put("orderNo",orderNo);
        }

        totalAmount=totalAmount+subTotal;
        //优惠码优惠
        totalAmount = totalAmount - fcodeMoney;
        totalAmount = totalAmount < 0 ? 0 : totalAmount;
        request.getSession().setAttribute(orderNo, totalAmount + "#" + idArr + "#"+fcodestr);

        return  courses;
    }



    /**
     * 获取用户购买课程所享受的所有活动，以及每种活动下能参与的课程
     * @param idArr
     * @return
     */
    public List<Map<String,Object>>  findActivityCourse(String idArr){
        String[] ids = idArr.split(",");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ids",Arrays.asList(ids));
        //1、获取用户购买课程所能参与的活动
        String sql=" select * from (SELECT  ard.rule_id,ar.name,ar.url from oe_activity_rule ar,oe_activity_rule_detail ard  " +
                " where ar.id=ard.rule_id and  ard.course_id in (:ids) and now() BETWEEN ard.start_time " +
                " and ard.end_time  order by ar.create_time asc, ard.reach_money asc ) a group by a.rule_id ";
        List<Map<String,Object>> activitys=this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
        //2、有活动，将活动循环，获取每种活动的课程范围
        if(activitys.size() >0 ){
            for (Map<String,Object>  activity : activitys){
                paramMap.put("rule_id",activity.get("rule_id"));
                sql="  SELECT c.id, c.grade_name courseName,c.smallimg_path,c.current_price,c.is_free,c.is_recommend,t.`name` from oe_course c,teach_method t," +
                        "  oe_activity_rule_detail ard  where c.id= ard.course_id and c.courseType=t.id and  ard.rule_id=:rule_id  GROUP BY ard.course_id ";
                List<Map<String,Object>> courses= this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
                activity.put("listCouse",courses);
            }
        }
        return activitys;
    }


	/** 
	 * Description：获取用户消费记录
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object consumptionList(String userId, Integer pageNumber, Integer pageSize) {
		String sql="SELECT r.* FROM ( SELECT apr.out_trade_no orderNO, apr. SUBJECT remark, apr.gmt_create gmtCreate,"
				+ " apr.total_amount price,apr.gmt_payment time,'支付宝' AS payType FROM alipay_payment_record apr WHERE apr.user_id =:userId "
				+ "UNION SELECT apr.out_trade_no orderNO, apr. SUBJECT remark, apr.gmt_create gmtCreate, apr.total_amount price,apr.gmt_payment time,'支付宝' AS payType "
				+ "FROM alipay_payment_record_h5 apr WHERE apr.user_id =:userId "

                + "UNION SELECT ii.order_no orderNO, ii.subject remark, ii.create_time gmtCreate, ii.actual_price price, ii.create_time TIME, 'applePay' AS payType  "
				+ "FROM `iphone_iap` ii WHERE ii.user_id = :userId "


				+ "UNION SELECT apr.out_trade_no orderNO, apr. SUBJECT remark, apr.time_end gmtCreate,  truncate((apr.total_fee/100),2)  price,apr.time_end time,'微信' AS payType FROM wxcp_pay_flow apr WHERE user_id =:userId ) r ORDER BY r.gmtCreate DESC";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        Page<MyConsumptionRecords> page = this.findPageBySQL(sql.toString(), paramMap, MyConsumptionRecords.class, pageNumber, pageSize);
		return page;
	}

	public int updateOrderNo(String orderNo,String orderId){
	    String sql="update oe_order set order_no=:orderNo where id=:orderId ";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("orderNo",orderNo);
        paramMap.put("orderId",orderId);
     return   this.getNamedParameterJdbcTemplate().update(sql,paramMap);
    }

}
