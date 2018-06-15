<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("base", basePath);
%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/home" />
</head>
<body>
</body>
</html>
