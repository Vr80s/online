 <script>
  var userId = '<%=request.getAttribute("userId")%>';
  var guId = '<%=request.getAttribute("guId")%>';
  var guPwd = '<%=request.getAttribute("guPwd")%>';
  var course_id='<%=request.getAttribute("courseId")%>';
  var page = '<%=request.getAttribute("page")%>';
  var description = '<%=request.getAttribute("description")%>';
  var liveStatus = '<%=request.getAttribute("liveStatus")%>';
  
  var env = '<%=request.getAttribute("env")%>';
  var host = '<%=request.getAttribute("host")%>';
  var rate = '<%=request.getAttribute("rate")%>';
  var ROOM_JID='<%=request.getAttribute("roomJId")%>';
  var BOSH_SERVICE='<%=request.getAttribute("boshService")%>';
  var sendTime='<%=request.getAttribute("now")%>';
  
  
</script>
<%@include file="../../web/html/liveVideoOther.html" %>
<script>
  var room_id='<%=request.getAttribute("roomId")%>';
  var plan_id='<%=request.getAttribute("planId")%>';
  var email = '<%=request.getAttribute("email")%>';
  var name = '<%=request.getAttribute("name")%>';
  var k = '<%=request.getAttribute("k")%>';

</script>
 