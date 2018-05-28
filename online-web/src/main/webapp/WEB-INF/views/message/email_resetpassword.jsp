<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>重置密码</title>
    <script src="<%=request.getContextPath()%>/web/js/jquery-1.12.1.js"></script>
    <script src="<%=request.getContextPath()%>/web/layer-v2.1/layer/layer.js"></script>
    <script src="<%=request.getContextPath()%>/web/js/md5.js"></script>
    <style>
        html,body{
            margin: 0;
            padding: 0;
        }
        .header{
            width: 100%;
            height: 89px;
            background-color: #f8f8f8;
        }
        .header_body{
            width: 1200px;
            margin: 0 auto;
        }
        .header_body img{
            border:none;
            outline:none;
            margin-right: 30px;
            float: left;
            margin-top: -9px;
        }
        .header_body span{
            font-size: 16px;
            padding-left: 30px;
            float: left;
            border-left: 1px solid #ccc;
            color: #333;
            padding: 5px 0px 5px 30px;
            margin-top: 30px;
        }
        .divcontainer{
            /*border:1px solid red;*/
            margin: 0 auto;
            width: 100%;
            border-radius: 10px;
            background-color: #fff;
            text-align: center;
            margin-top: 100px;
        }
        .divcontainer table{
            /*border: 1px solid red;*/
            margin: 0 auto;
            height:84px;
        }
        .divcontainer table input{
            border: 1px solid #ccc;
            border-radius: 2px;
            height: 22px;
            ime-mode: disabled;
            width: 288px;
            padding: 0px 5px;
        }
        .divcontainer table td{
            color:#666;
        }
        .btnsub{
            width: 298px;
            background-color: #2cb82c;
            border: none;
            color: #fff;
            letter-spacing: 2px;
            margin-left: 132px;
            border-radius: 2px;
            margin-top: 22px;
            height: 40px;
            cursor: pointer;
        }
        .erreyPwd1,.erreyPwd2{
            display: block;
            height: 24px;;line-height: 24px;
        }
        .erreyPwd1 img,.erreyPwd2 img{
            vertical-align: middle;
        }
    </style>
    <script>
        var error = 0;
        $(function () {
            $(".btnsub").on("click", function (e) {
               if($(".pwd1").val().trim()==""&&$(".pwd2").val().trim()==""){
                    $(".erreyPwd2").html("");
                    $(".erreyPwd1").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>密码不能为空哦");
                    e.preventDefault();
                    return false;
              }else if($(".pwd1").val().trim().length<6||$(".pwd1").val().trim().length>18){
                    $(".erreyPwd2").html("");
                    $(".erreyPwd1").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>请输入6-18位密码哦!");
                    e.preventDefault();
                    return false;
               }else if($(".pwd2").val().trim()==""&&$(".pwd1").val().trim().length>5&&$(".pwd1").val().trim().length<19){
                    $(".erreyPwd1").html("");
                    $(".erreyPwd2").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>密码不能为空哦");
                    e.preventDefault();
                    return false;
                 }else if($(".pwd1").val().trim()!==$(".pwd2").val().trim()){
                    $(".erreyPwd1").html("");
                    $(".erreyPwd2").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>两次密码不一致!");
                    e.preventDefault();
                    return false;
                }else{
                    $(".erreyPwd1").html("");
                    $(".erreyPwd2").html("");
                    $('.pwd1').val($('.pwd1').val());
                    $("#form1").submit();
                	
                }
            });

            $(".pwd1").on("blur", function () {
                if($(this).val().trim()==""){
                    $(".erreyPwd2").html("");
                    $(".erreyPwd1").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>密码不能为空哦!");
                }else if($(this).val().trim().length<6||$(this).val().trim().length>18){
                    $(".erreyPwd2").html("");
                    $(".erreyPwd1").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>请输入6-18位密码哦!");
                }else{
                    $(".erreyPwd1").html("");
                }
            });

            $(".pwd2").on("blur", function () {
                if($(this).val()==""){
                    $(".erreyPwd1").html("");
                    $(".erreyPwd2").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>密码不能为空哦!");
                }else if($(this).val()!=$(".pwd1").val()){
                    $(".erreyPwd1").html("");
                    $(".erreyPwd2").html("<img src='<%=request.getContextPath()%>/web/images/alter02.png' style='margin: 0px 5px 0px 10px'/>两次密码不一致!");
                }else{
                    $(".erreyPwd2").html("");
                }

            });

        });
    </script>
</head>
<body>
<div class="header">
    <div class="header_body">
        <a href="/index.html"><img src="<%=request.getContextPath()%>/web/images/boxuegulogo-email.png"></a>
        <span>重置密码</span>
    </div>

</div>
<div class="divcontainer">
    <form id="form1" method="post" action="<%=request.getContextPath()%>/online/user/resetPasswordByEmail">
        <input type="hidden" name="vcode" value="${param.vcode}">
        <table>
            <tr>
                <td style="text-align: right">新密码：</td>
                <td> <input maxlength="18" onpaste="return false" name="password" type="password" placeholder="6-18位密码" class="pwd1"><br></td>
                <td>
                    <span class="erreyPwd1" style="color:red;position: absolute;top: 198px;font-size: 12px;"></span></td>
            </tr>
            <tr>
                <td style="text-align: right">再次输入新密码：</td>
                <td> <input maxlength="18" onpaste="return false" name="password2" type="password" placeholder="6-18位密码" class="pwd2"><br></td>
                <td>
                    <span class="erreyPwd2" style="color:red;position: absolute;top: 240px;font-size: 12px;"></span></td>
            </tr>
        </table>
        <input class="btnsub" type="button" value="确定重置">
    </form>
</div>

</body>
</html>
<script src="//cdn.bootcss.com/jquery-placeholder/2.3.1/jquery.placeholder.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('input').placeholder();
    });
</script>