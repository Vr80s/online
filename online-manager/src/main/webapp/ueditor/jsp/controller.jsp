<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.*,java.util.*"
    pageEncoding="UTF-8"%>
    
<%
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	String ss = session.getServletContext().getRealPath("/ueditor")+File.separator+"jsp"+File.separator+"config.json";
	BufferedReader br = new BufferedReader(
			new FileReader(new File(ss)));
	String s = null;
	while((s = br.readLine()) != null){
		out.write(s);
	}
	
%>