<script>
  var lecturerId = '<%=request.getAttribute("lecturerId")%>';
  var userId = '<%=request.getAttribute("userId")%>';
  var guId = '<%=request.getAttribute("guId")%>';
  var guPwd = '<%=request.getAttribute("guPwd")%>';
  var course_id='<%=request.getAttribute("courseId")%>';
  var page = '<%=request.getAttribute("page")%>';
  var description = '<%=request.getAttribute("description")%>';
  var env = '<%=request.getAttribute("env")%>';
  var host = '<%=request.getAttribute("host")%>';
  var rate = '<%=request.getAttribute("rate")%>';
  var ROOM_JID='<%=request.getAttribute("roomJId")%>';
  var BOSH_SERVICE='<%=request.getAttribute("boshService")%>';
  var sendTime='<%=request.getAttribute("now")%>';
  
  var liveStatus='<%=request.getAttribute("liveStatus")%>';
  var room_id='<%=request.getAttribute("roomId")%>';
  var plan_id='<%=request.getAttribute("planId")%>';

  var email = '<%=request.getAttribute("email")%>';
  var name = '<%=request.getAttribute("name")%>';
  var k = '<%=request.getAttribute("k")%>';
  
  var vhallName = '<%=request.getAttribute("vhallName")%>';
  var appid = '<%=request.getAttribute("appid")%>';

</script>
<%@include file="../../web/html/liveVideo4Vhallyun.html" %>
