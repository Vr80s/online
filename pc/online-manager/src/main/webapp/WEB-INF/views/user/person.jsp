<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
    
    <link rel="stylesheet" href="/css/usercenter.css" />
    <script type="text/javascript" src="/js/corpbox/cropbox.js"></script>
    <script>
    		try {
			var scripts = [ null, null ];
			$('.page-content-area').ace_ajax('loadScripts', scripts,
					function() {
					});
		} catch (e) {
		}
        $(function () {
            $(".ulnav").on("click", "li", function () {
                $(".ulnav .linav").removeClass("liactive");
                $(this).addClass("liactive");
                $(".divcontainer .divtab").fadeOut();
                var liidx = $(this).attr("id").charAt(3);
                $("#divtab" + liidx).fadeIn();
                $(".imageBox").css("background-position","50px 50px");
            });

            //使用时 应根据父级容器调整$(".divcontainer")高度
            $(".divcontainer").height(550);

            //头像裁剪部分
            var options =
            {
                thumbBox: '.thumbBox',
                spinner: '.spinner',
                //imgSrc: '${user.bigHeadPhoto}'
                imgSrc: ''
            };
            var cropper = $('.imageBox').cropbox(options);
            $('#file').on('change', function () {
        		if(!validateImg(this.value)){
        			alertInfo("请上传格式为jpeg、jpg、png、gif、bmp、svg的图片");
        			this.value="";
        			return;
        		}
                var reader = new FileReader();
                reader.onload = function (e) {
                    options.imgSrc = e.target.result;
                    cropper = $('.imageBox').cropbox(options,cutphoto);
                };
                reader.readAsDataURL(this.files[0]);
                this.files = [];
            });


            $(".btnfile").on("click", function () {
                $("#file").trigger("click");
            });
            function cutphoto(){
                var img = cropper.getDataURL();
                $('#imageId').val(img);
                //$('.cropped').append('<img src="' + img + '">');
                $(".avatarlarge").empty().append('<img src="' + img + '">');
                $(".avatarsmall").empty().append('<img src="' + img + '">');
            }
            <%--$('#btnCrop').on('click', function () {--%>
                <%--if($('.imageBox').css("background-image")!="none"){--%>
                <%--var img = cropper.getDataURL();--%>
                <%--$('#imageId').val(img);--%>
                <%--//$('.cropped').append('<img src="' + img + '">');--%>
                <%--$(".avatarlarge").empty().append('<img src="' + img + '">');--%>
                <%--$(".avatarsmall").empty().append('<img src="' + img + '">');--%>
                <%--}else{--%>
                    <%--alertInfo("请选择头像！")--%>
                <%--}--%>
            <%--})--%>


            $('#btnZoomIn').on('click', function () {
            //alertInfo($('.imageBox').css("background-image"));
                if($('.imageBox').css("background-image")!="none"){
                    cropper.zoomIn();
                    cutphoto();
                }else{
                alertInfo("请选择头像！")
                    }
            });

            $('#btnZoomOut').on('click', function () {
                if($('.imageBox').css("background-image")!="none"){
                    cropper.zoomOut();
                    cutphoto();
                }else{
    alertInfo("请选择头像！")
                    }
            });
            //alertInfo($(".imageBox").css("background-position"));
            
        });
    </script>
    
<div class="usercenter">
    <ul class="ulnav">
        <li id="nav1" class="linav liactive">基本信息</li>
        <li id="nav2" class="linav">头像设置</li>
        <li id="nav3" class="linav">修改密码</li>
    </ul>
    <div class="divcontainer">
        <div id="divtab1" class="divtab divactive">
        	<form action="person/update/info" method="post" class="form-horizontal" role="form" id="person-info-form">
            <table class="tabinfo">
                <tr>
                    <td class="tdleft">登录名</td>
                    <td class="tdright">${user.loginName}</td>
                </tr>
                <tr>
                    <td class="tdleft">学历</td>
                    <td class="tdright">${user.education}</td>
                </tr>
                <tr>
                    <td class="tdleft"><i class="text-danger">*</i> 姓名</td>
                    <td class="tdright"><input type="text" name="name" id="name" value="${user.name}" maxlength="18" class="col-xs-10 col-sm-8 {required:true,maxlength:18,minlength:2}"></td>
                </tr>
                <tr>
                    <td class="tdleft"><i class="text-danger">*</i> 手机号</td>
                    <td class="tdright"><input type="text" name="mobile" value="${user.mobile}"  id="mobile" class="col-xs-10 col-sm-12 {required:true,phone:[]}"></td>
                </tr>
                <tr>
                    <td class="tdleft">邮箱</td>
                    <td class="tdright">
                    <input type="text" name="email" id="email" maxlength="32" class="col-xs-10 col-sm-12 {email:true}"
                    value="${user.email}" />
                    </td>
                </tr>
                <tr>
                    <td class="tdleft">QQ</td>
                    <td class="tdright"><input type="text" name="qq" value="${user.qq}" id="qq" maxlength="12" class="col-xs-10 col-sm-12 {qq:[]}"></td>
                </tr>
                <tr>
                    <td colspan="2" class="tdleft butBox" style="text-align: center">
	                    <input type="button" class="btncommon" onclick="updatePersonInfo()" name="保存" value="确定保存">
	                    <input type="reset" class="btncommon btngray" name="取消" value="取消">
                    </td>
                </tr>
            </table>
          </form>
        </div>
        
        <div id="divtab2" class="divtab">
        	  <form action="person/update/head" method="post" class="form-horizontal" role="form" id="person-head-form">
        	  	<input type="hidden" name="image" value="" id="imageId">
            <div class="divavatar">
                <div>
                    <div class="btnfile uploadBtn">选择上传图片</div>
                    <input type="file"  id="file" style="display: none;"/>
                </div>
                <div class="divtip">您上传的头像会自动生成小尺寸头像,请注意小尺寸的头像是否清晰</div>
                <div class="imageBox">
                    <!--<img src="images/defaultavatar.png" alt="">-->
                    <div class="thumbBox"></div>
                    <div class="spinner" style="display: none">
                        <%--<p>请选择你要上传的头像</p>--%>
                        <!--<p>上传图片大小不能超过5M</p>-->
                        <!--<p>格式只能是jpg,jpeg,gif,png的</p>-->
                    </div>
                </div>
                <div class="cropped">
                    <div class="avatarlarge">
                        <img src="/images/defaultavatar.png">
                    </div>
                    <p>大尺寸头像</p>
                    <p>180×180</p>
                    <p>(自动生成)</p>
                </div>
                <div class="cropped csmall">
                    <div class="avatarsmall">
                        <img src="/images/defaultavatar.png">
                    </div>
                    <p>小尺寸头像</p>
                    <p>45×45</p>
                    <p>(自动生成)</p>
                </div>
                <div class="action">
                    <%--<input type="button" class="btncrop btncommon" id="btnCrop" value="裁剪" >--%>
                    <input type="button" class="btnzoomin btncommon" id="btnZoomIn" value="+" >
                    <input type="button" class="btnzoomout btncommon" id="btnZoomOut" value="-" >
                </div>
                <div class="divsave ">
                    <input type="button" class="btncommon saveBtn" onclick="updateHeadPhoto()" name="保存" value="保存">
	                <input type="reset" class="btncommon btngray" onclick="removePhoto()" name="取消" value="取消">
                </div>
            </div>
            </form>
        </div>
        
        <div id="divtab3" class="divtab">
        		<form action="person/update/password" method="post" class="form-horizontal" role="form1" id="person-password-form">
	            <table class="tabinfo">
	                <tr>
	                    <td class="tdleft"><i class="text-danger">*</i> 原密码</td>
	                    <td class="tdright">
	                    		<input type="password" name="oldpassword" id="oldpassword" maxlength="16" placeholder="请输入原密码" class="col-xs-10 col-sm-12 {required:true,maxlength:16,minlength:2}">
	                    	</td>
	                </tr>
	                <tr>
	                    <td class="tdleft"><i class="text-danger">*</i> 新密码</td>
	                    <td class="tdright"><input type="password" name="newpassword" id="newpassword"  maxlength="16" placeholder="请输入6-16位以字母、数字或下划线组成的密码" class="col-xs-10 col-sm-12 {required:true,ENNUMUNDERLINE:[],maxlength:16,minlength:6}"></td>
	                </tr>
	                <tr>
	                    <td class="tdleft"><i class="text-danger">*</i> 再次输入</td>
	                    <td class="tdright"><input type="password" name="repassword" id="repassword"  maxlength="16" class="col-xs-10 col-sm-12 {required:true,ENNUMUNDERLINE:[],maxlength:16,minlength:6}"></td>
	                </tr>
	                <tr>
	                    <td colspan="2" class="tdleft butBox" style="text-align: center">
	                       <input type="button" class="btncommon" onclick="updatePassword()" name="保存" value="确定保存">
	                    	   <input type="reset" class="btncommon btngray" name="取消" value="取消">
	                    </td>
	                </tr>
	            </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/user/person.js?v=ipandatcm_1.3"></script>