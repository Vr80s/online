<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var param_page = <%=request.getAttribute("page")%>;
	var courseId = <%=request.getAttribute("courseId")%>;
</script>
<%@ include file="../../Views/h5/mycourse_video.html" %>
