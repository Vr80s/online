<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="./jstl_taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>熊猫中医在线</title>
<style>
.error {
	width: 512px;
	height: 202px;
	margin : 10% auto;
	margin-top : 10px;
}

.error dl dt {
	display: inline-block;
	vertical-align: top;
}

.error dl dd {
	display: inline-block;
}

.error dl dd h3 {
	font-size: 30px;
	padding: 0px;
	margin: 0px;
	margin-top: 30px;
	color: #444;
}

.error dl dd p {
	font-size: 16px;
	color: #999;
	padding: 0px;
	margin: 0px;
	margin-top: 14px;
}

.error dl dd button {
	border: 1px solid #00c1a1;
	width: 136px;
	height: 43px;
	font-size: 16px;
	border-radius: 50px;
	color: #00c1a1;
	background: #fff;
	margin-top: 51px;
	outline: none;
	cursor: pointer;
}
</style>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
	}
</script>
</head>
<body>
	<div class="error">
		<dl>
			<dt>
				<img src="/images/error_y.png" alt="">
			</dt>
			<dd>
				<h3>哎呀 , 出错了</h3>
				<p>发送错误编码至熊猫中医禅道：</p>
				<p><a href="http://zentao.ixincheng.com:58000/zentao/www/index.php" target="_blank">http://zentao.ixincheng.com:58000/zentao/www/index.php</a></p>
				<c:if test="${!empty msg}">
					<p>错误编号：${msg}</p> 
				</c:if>
				<button type="button" onclick="window.location.href='/index.html';">返回首页</button>
			</dd>
		</dl>
	</div>
</body>
</html>