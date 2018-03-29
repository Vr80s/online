<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>密码重置成功</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育，海口中医养生，国粹，传承，中医，中药，心承，熊猫中医"/>
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。"/>
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
        <p class="text">
            密码重置成功!
        </p>
        <p class="link">
            千万小伙伴想成为你的同学，正在<span class="tiaozhuang"><span class="tiaozhuang">自动跳转<span id="time">4</span>秒...</span>
        </p>
        <p class="indexHref">
           	 如果没有跳转，请点击链接：<a  class="path" href="/">http://www.ipandatcm.com</a>
        </p>
    </div>

</div>
</body>
</html>

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