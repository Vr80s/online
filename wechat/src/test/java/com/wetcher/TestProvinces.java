package com.wetcher;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.online.api.service.CityService;

import net.sf.json.JSONArray;

/**
 * 医馆入驻测试类
 */
public class TestProvinces extends BaseJunit4Test {

    @Autowired
    public CityService cityService;

    @Test
    public void aaaa() throws SQLException, IOException {

        List<Map<String, Object>> list = cityService.getAllProvinceCityCounty();

        JSONArray json = JSONArray.fromObject(list);

        String path = "D:\\city_zdy.json";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        //获得输出流
        OutputStreamWriter bos = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(bos);
        bw.write(json.toString());
        bw.flush();
        bw.close();
    }

}