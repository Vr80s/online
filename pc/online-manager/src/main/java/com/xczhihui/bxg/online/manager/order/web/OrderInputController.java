package com.xczhihui.bxg.online.manager.order.web;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.manager.order.service.OrderInputService;
import com.xczhihui.bxg.online.manager.order.vo.OrderInputVo;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.xczhihui.user.center.utils.CodeUtil;

/**
 * 线下订单录入
 *
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping(value = "/order/input")
public class OrderInputController {

    @Value("${online.web.url:http://www.ixincheng.com}")
    private String weburl;

    @Autowired
    private OrderInputService service;

    //@RequiresPermissions("input:order")
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/order/input");
        return mav;
    }

    //@RequiresPermissions("input:order")
    @RequestMapping(value = "/find")
    @ResponseBody
    public TableVo find(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group startTimeGroup = groups.findByName("create_time_start");
        Group endTimeGroup = groups.findByName("create_time_end");
        Group keyTypeGroup = groups.findByName("key_type");
        Group keyValueGroup = groups.findByName("key_value");
        OrderInputVo searchVo = new OrderInputVo();
        if (startTimeGroup != null) {
            searchVo.setCreate_time_start(startTimeGroup.getPropertyValue1().toString());
        }
        if (endTimeGroup != null) {
            searchVo.setCreate_time_end(endTimeGroup.getPropertyValue1().toString());
        }
        if (keyTypeGroup != null) {
            searchVo.setKey_type(keyTypeGroup.getPropertyValue1().toString());
        }
        if (keyValueGroup != null) {
            searchVo.setKey_value(keyValueGroup.getPropertyValue1().toString());
        }
        Page<OrderInputVo> page = service.findOrderInputPage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    //@RequiresPermissions("input:order")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(OrderInputVo vo, HttpServletRequest req) throws Exception {
        vo.setCreate_person(ManagerUserUtil.getId());
        //生成用户
        service.addUser(vo.getLogin_name());
        //生成订单
        service.addOrder(vo);
//		this.docallback(order_no);
        return ResponseObject.newSuccessResponseObject(null);
    }

    //@RequiresPermissions("input:order")
    @RequestMapping(value = "/importOrder", method = RequestMethod.POST)
    @ResponseBody
    public void importOrder(OrderInputVo vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            XSSFWorkbook book = new XSSFWorkbook(vo.getExcelFile().getInputStream());
            XSSFSheet sheet = book.getSheetAt(0);
            int last = sheet.getLastRowNum();
            OrderInputVo v = null;
            List<OrderInputVo> lv = new ArrayList<OrderInputVo>();
            for (int i = 1; i < last + 1; i++) {
                Row row = sheet.getRow(i);
                v = new OrderInputVo();
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                v.setLogin_name(row.getCell(0).getStringCellValue());

                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                v.setCourse_id(row.getCell(1).getStringCellValue());

                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                v.setClass_id(row.getCell(2).getStringCellValue());

                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                Integer of = Integer.valueOf(row.getCell(3).getStringCellValue());
                if (of != OrderFrom.OFFLINE.getCode() && of != OrderFrom.WORKER.getCode() && of != OrderFrom.GIVE.getCode()) {
                    throw new RuntimeException("订单类型必须为5(下线订单)或6(工作人员)或0(赠送)");
                }
                v.setOrder_from(Integer.valueOf(row.getCell(3).getStringCellValue()));
                service.checkOrderInput(v);
                v.setCreate_person(ManagerUserUtil.getId());
                lv.add(v);
            }
            for (OrderInputVo ov : lv) {
//				this.add(ov, req);
                service.addUser(ov.getLogin_name());
                Thread.sleep(100);
            }
            service.addOrders(lv);
            book.close();
            Gson g = new GsonBuilder().create();
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html;charset=utf-8");
            res.getWriter().print(g.toJson(ResponseObject.newSuccessResponseObject(null)));
        } catch (Exception e) {
            e.printStackTrace();
            Gson g = new GsonBuilder().create();
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html;charset=utf-8");
            res.getWriter().print(g.toJson(ResponseObject.newErrorResponseObject(e.getMessage())));
        }

    }

    private void docallback(String order_no) throws Exception {
        //生成课程
        String s = "out_trade_no=" + order_no + "&result_code=SUCCESS&key=" + OnlineConfig.WECHAT_API_KEY;
        String mysign = CodeUtil.MD5Encode(s).toLowerCase();

        String resXml =
                "<xml>"
                        + "<out_trade_no><![CDATA[" + order_no + "]]></out_trade_no>"
                        + "<result_code><![CDATA[SUCCESS]]></result_code>"
                        + "<sign><![CDATA[" + mysign + "]]></sign>"
                        + " </xml> ";

//		URL url = new URL(weburl+"/web/weixin_pay_notify");
        URL url = new URL(weburl + "/web/pay_notify_wechat");
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestProperty("Content-type", "application/xml");
        urlConn.setRequestMethod("POST");
        urlConn.setConnectTimeout(15000);// （单位：毫秒）jdk
        urlConn.setReadTimeout(15000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
        urlConn.setDoOutput(true);
        byte[] b = resXml.toString().getBytes();
        urlConn.getOutputStream().write(b, 0, b.length);
        urlConn.getOutputStream().flush();
        urlConn.getOutputStream().close();
        urlConn.getInputStream();
    }
}
