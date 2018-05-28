package com.xczhihui.order.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.common.support.config.OnlineConfig;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.order.service.OrderInputService;
import com.xczhihui.order.vo.OrderInputVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
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
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;

/**
 * 线下订单录入
 *
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping(value = "/order/input")
public class OrderInputController {

    @Value("${web.url}")
    private String weburl;

    @Autowired
    private OrderInputService service;
    @Autowired
    private OnlineConfig onlineConfig;

    // @RequiresPermissions("input:order")
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/order/input");
        return mav;
    }

    // @RequiresPermissions("input:order")
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
            searchVo.setCreate_time_start(startTimeGroup.getPropertyValue1()
                    .toString());
        }
        if (endTimeGroup != null) {
            searchVo.setCreate_time_end(endTimeGroup.getPropertyValue1()
                    .toString());
        }
        if (keyTypeGroup != null) {
            searchVo.setKey_type(keyTypeGroup.getPropertyValue1().toString());
        }
        if (keyValueGroup != null) {
            searchVo.setKey_value(keyValueGroup.getPropertyValue1().toString());
        }
        Page<OrderInputVo> page = service.findOrderInputPage(searchVo,
                currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    // @RequiresPermissions("input:order")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(OrderInputVo vo, HttpServletRequest req)
            throws Exception {
        vo.setCreate_person(ManagerUserUtil.getId());
        // 生成用户
        service.addUser(vo.getLogin_name());
        // 生成订单
        service.addOrder(vo);
        // this.docallback(order_no);
        return ResponseObject.newSuccessResponseObject(null);
    }

    // @RequiresPermissions("input:order")
    @RequestMapping(value = "/importOrder", method = RequestMethod.POST)
    @ResponseBody
    public void importOrder(OrderInputVo vo, HttpServletRequest req,
                            HttpServletResponse res) throws Exception {
        try {
            XSSFWorkbook book = new XSSFWorkbook(vo.getExcelFile()
                    .getInputStream());
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
                Integer of = Integer.valueOf(row.getCell(3)
                        .getStringCellValue());
                if (of != OrderFrom.OFFLINE.getCode()
                        && of != OrderFrom.WORKER.getCode()
                        && of != OrderFrom.GIVE.getCode()) {
                    throw new RuntimeException("订单类型必须为5(下线订单)或6(工作人员)或0(赠送)");
                }
                v.setOrder_from(Integer.valueOf(row.getCell(3)
                        .getStringCellValue()));
                v.setCreate_person(ManagerUserUtil.getId());
                lv.add(v);
            }
            for (OrderInputVo ov : lv) {
                // this.add(ov, req);
                service.addUser(ov.getLogin_name());
                Thread.sleep(100);
            }
            service.checkOrderInput(v);
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
            res.getWriter().print(
                    g.toJson(ResponseObject.newErrorResponseObject(e
                            .getMessage())));
        }

    }

}
