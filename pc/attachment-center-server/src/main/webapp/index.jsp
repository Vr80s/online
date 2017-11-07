<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>欢迎访问附件中心！</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>
<script type="text/javascript">
	function upl(){
		if(!$('#imgfile').val()){
			alert('请上传文件！');
			return;
		}
		$.ajaxFileUpload({
			url : '/attachment/upload?projectName='+$('#projectName').val()+'&fileType=2',
			secureuri : false,
			fileElementId : 'imgfile', //文件上传域的ID
			dataType : "text",
			success : function(data) {//服务器成功响应处理函数
				$('#returnjson').val(data);
			},
			error : function(data, status, e) {
				$('#returnjson').val("error:" + e);
			}
		});
	}
</script>
</head>
<body>
	<div>
		说明：返回结果：error（0为成功，1为失败）、message（如果失败存错误信息）。
	</div>
	<div>
		来源：
		<select id="projectName" name="projectName">
			<option value="dual">双元</option>
			<option value="univ">院校</option>
			<option value="online">在线</option>
		</select>
	</div>
	<div>
		文件:<input name="attachment" id="imgfile" type="file"><br> 
	</div>
	<div>
		<button onclick="upl();">提交</button>
	</div>
	<div>
		返回：<br>
		<textarea rows="20" cols="50" id="returnjson"></textarea>
	</div>
</body>
</html>