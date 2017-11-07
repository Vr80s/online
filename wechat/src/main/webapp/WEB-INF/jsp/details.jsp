<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
     var course_id = <%=request.getAttribute("course_id")%>;
     var page = <%=request.getAttribute("page")%>;

     var guId = "<%=request.getAttribute("guId")%>";
     var guPwd = "<%=request.getAttribute("guPwd")%>";
     var room_id="<%=request.getAttribute("roomId")%>";






     var host = '<%=request.getAttribute("host")%>';
     var BOSH_SERVICE='<%=request.getAttribute("boshService")%>';
     var ROOM_JID='<%=request.getAttribute("roomJId")%>';



</script>

<%@ include file="../../xcviews/html/details.html"%>

