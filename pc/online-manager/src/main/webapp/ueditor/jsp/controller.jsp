<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.*,java.util.*"
    pageEncoding="UTF-8"%>
    
<%
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	BufferedReader br = new BufferedReader(
			new FileReader(new File(session.getServletContext()
					.getRealPath("/ueditor")+File.separator+"jsp"+File.separator+"config.json")));
	String s = null;
	while((s = br.readLine()) != null){
		out.write(s);
	}
	
%>