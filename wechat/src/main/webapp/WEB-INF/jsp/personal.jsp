<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var param_code = '<%=(request.getAttribute("code") == null || request.getAttribute("code").equals("null"))
		? "" : request.getAttribute("code")%>';
	var param_openId = '<%=(request.getAttribute("openId") == null || request.getAttribute("openId").equals("null"))
			? "" : request.getAttribute("openId")%>';
</script>
<%@ include file="../../Views/h5/personal_1.html" %>
