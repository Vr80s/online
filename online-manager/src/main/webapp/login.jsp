<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./WEB-INF/views/common/jstl_taglib.jsp"%>

<%@ page import="org.apache.shiro.subject.Subject" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="org.apache.shiro.session.Session" %>
<%
	Subject subject = SecurityUtils.getSubject();
    Session s = subject.getSession();
    s.removeAttribute("shiroSavedRequest");
%>
    
<!DOCTYPE>
<html lang="en">
<head>
    <meta charset="UTF-8">
     <link rel="shortcut icon" href="/images/logo.ico">
    <title>熊猫中医直播教育管理系统</title>
    <style>
    body{
        padding: 0px;
        margin: 0px;
    }
    .login{
    	background:url("${contextPath}/images/login-bg.jpg") no-repeat center;
    	background-size: cover;
    	height: 100%;
    }
        .logheader{
             width: 269px;
            height: 64px;
            /* background: url("${contextPath}/images/logo.png") no-repeat; */
        }
        .logban{
            width: 100%;
            height: 471px;

        }
        .logbanner{
           width: 100%;
            height: 457px;

        }
        .banimg{
            margin: 0 auto;width:1339px;
            height: 457px;
            background: url("${contextPath}/images/dl.png") no-repeat;
            position: relative;
            background-size: 100%;
        }
        .logform{
            width: 347px;
            height: 338px;
            background: #fff;
            position: absolute;
            z-index: 3;
            top:160px;
            left: 910px;
        }
        .logform h3{
            margin: 23px 20px;
            text-align: center;
        }
        .loginm div{
            height: 38px;
            width: 300px;
            border-radius: 6px;
            border: 1px #c0c0c0 solid;
            margin: 25px 0 0 20px ;
            position: relative;
        }
        .loginm div input{
            position: absolute;
            left: 40px;
            height: 35px;
            width: 220px;
            border: none;
            outline:none;
        }
        .person{
            background: url("${contextPath}/images/person_03.png") no-repeat;
            width: 36px;
            height: 27px;

        }
        .loginm  img{
            float: right;
            margin-right: 5px;cursor: pointer;
        }
        .mima{
            background: url("${contextPath}/images/mima_06.png") no-repeat;
            width: 36px;
            height: 27px;

        }

        .loginBtn{
             background:#72a571;
            border: none;
            border-radius: 6px;
            width: 300px;
            margin: 25px 0 0 20px ;
            color: #fff;
            font-size: 24px;line-height: 40px;
            cursor: pointer;
            
        }

        .foot{
           	 font-size: 15px;
                position: fixed;
			    left: 50%;
			    margin-left: -100px;
			    bottom: 14%;
        }
        
        .errorInfo{
        		margin-left:18px;
        		color:red;
        }
    </style>
</head>
<body>
<div class="login">
    <!--<p class="logheader">
    	<img  src="/images/logo_1.png" style="height: 75;">
    </p>-->
    <div class="logban">
        <div class="logbanner" >
           <div class="banimg">
             <div class="logform">
                <h3>熊猫中医直播教育管理系统</h3>
				<span class="errorInfo"><c:if test="${shiroLoginFailure != null}">用户名密码错误或账户被禁用</c:if></span>
                 <form action="" id="loginForm" class="loginm" method="post">
                     <div class="person">   
                     <input type="text" name="username" id="username" value=""/><img src="${contextPath}/images/cz_06.png" class="usename" alt=""/></div>
                     <div class="mima"><input type="password" id="password" name="password" value=""/><img src="${contextPath}/images/cz_06.png" class="password1" alt=""/></div>
                  	<input class="loginBtn" type="button" value="登录" onclick="submitForm();">
                 </form>
             </div>
           </div>
        </div>
    </div>
    <p class="foot" >熊猫中医(海口)健康科技有限公司   主办</p>
</div>
<script type="text/javascript" src="/js/jquery-1.5.2.js"></script>
<script src="/js/jquery.validate.min.js"></script>
<script src="/js/jquery.metadata.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/messages_cn.js"></script>
<script src="/js/mask.js" type="text/javascript" ></script>
<script>
    $(function(){
    $(".usename").click(function(){

    $("#username").val("");


    })
    $(".password1").click(function(){
       // console.log(1)
    $("#password").val("");


    })

    })
function isnull(data) {
	if (data == null || data == undefined || data == "") {
		return true;
	} else {
		return false;
	}
}

$(function(){
	document.onkeydown=function(event){
		 var e = event || window.event || arguments.callee.caller.arguments[0];
		 if(e && e.keyCode==13){// enter 键
			 submitForm();
		 }
	 };
});
	function checkLoginForm(loginForm){
	}
	
	function submitForm(){
		
		var username = $("#username").val();
		if(isnull(username)){
			 $(".errorInfo").html("用户名不能为空");
			 return;
		}
		var password = $("#password").val();
		if(isnull(password)){
			 $(".errorInfo").html("密码不能为空");
			 return;
		}
		$(".login").mask();
		$("#loginForm").submit();
	 }

</script>
</body>
</html>