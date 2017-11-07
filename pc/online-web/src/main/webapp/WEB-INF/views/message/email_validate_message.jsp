<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册-熊猫中医云课堂</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育，海口中医养生，国粹，传承，中医，中药，心承，熊猫中医"/>
    <meta name="description" content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。"/>
    <link rel="stylesheet" href="/web/css/emailpage.css">
    <style>
        .page-email .article .text {
            text-align: left;
        }
    </style>
</head>
<body>
<div class="page-email">
    <div class="header">
        <p class="p1">熊猫中医</p>
        <p class="p2">ixincheng.com</p>
    </div>
    <div class="article">
		<%
			String msg = String.valueOf(request.getAttribute("msg"));
			if("ok".equals(msg)){  %>
				<p class="text">
					注册成功!
		        </p>
		        <p class="link">
					千万小伙伴想成为你的同学，正在<span class="tiaozhuang">自动跳转<span id="time">4</span>秒...</span>
		        </p>
		        <p class="indexHref">
					如果没有跳转，请点击链接：<a  class="path" href="/">http://www.ixincheng.com</a>
		        </p>
		        <script>
				    var eTime=document.getElementById("time");
				    var oA=document.getElementsByClassName("path")[0];
				    var timer=setInterval(function(){
				        eTime.innerHTML-=1;
				        if(eTime.innerHTML==1){
				            window.clearInterval(timer);
				            window.location=oA.getAttribute("href");
				        }
				    },1000);
				</script>
		<%	} else {  %>
				<p class="text">
					<%=msg %>
				</p>
				<p class="link">
					千万小伙伴想成为你的同学，<a href="/">返回主页</a>
				</p>
		<%	} %>
    </div>
</div>
</body>
</html>