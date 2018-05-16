package com.wetcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
	public void aaaa() throws SQLException, IOException{
		
		List<Map<String, Object>> list = cityService.getAllProvinceCityCounty();
		
		JSONArray json = JSONArray.fromObject(list);     
		
        String path = "D:\\city_zdy.json";
        File file =new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos =new FileOutputStream(file);
        //获得输出流
        OutputStreamWriter bos =new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(bos);
        //遍历输出
        String a = "";
       /* for (int i=0;i<list.size();i++) {
            Map map=(Map)list.get(i);
            a+=map.toString();
        }*/
        
        System.out.println(json);
        bw.write(json.toString());
        bw.flush();
        bw.close();
		
//		DirectoryInfo di = new DirectoryInfo(path);
//		FileInfo fi = new FileInfo(path);
//		if (fi.Exists) fi.Delete();
//		StreamWriter swList = File.CreateText(path);                
//		foreach(Map<String, Object> a in list)
//		{
//			swList.WriteLine(a);
//		}
//		swList.Close();
		
		
	}
	

}