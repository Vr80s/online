/**
 * Created by Administrator on 2016/8/13.
 */
$(function() {
	window.onload = function() {
		$(".left-nav ul").click(function(evt) {
			if($(evt.target).hasClass("mydata")) {
				$(".my-personcenter-nav-2 a").text("我的资料");
				$(".view-content-content").empty();
				$(".view-content-content").html(template.compile(mydata));
				initAddressBind();
				createData();
			}
		});

	};


	var mydata =
		'<div class="tabnavigator my-data">' +
		'<div class="tabbar">' +
		'<div class="btn-item color2cb">个人信息</div>' +
		'<div class="btn-item address" >常用地址管理</div>' +
		'</div>' +
		'<div class="my-data-1">' +
		//事先隐藏的提示框
		'<div class="rTips" style=" display: none;"></div>'+
		//个人信息代码
		'<div class="geren">' +
		
		'<div class="cy-myprofile-myfom-dv-1">' +
		'<div class="buer"><span class="red">*</span>用户名:</div>' +
		'<input type="text" maxlength="20" class="firsname ipt"/>' +
		'<span class="nick-warn-name warning">用户名不能为空</span>' +
		'</div>' +

		'<div>' +
		'<div class="buer">帐号:</div>' +
		'<input type="text" disabled="disabled" readonly="readonly"  class="username ipt"/>' +
		'<a href="/web/html/resetPassword.html" class="mypassword-g">修改密码&nbsp;></a>' +
		' </div>' +
		
		'<div class="cy-myprofile-myfom-dv-1">' +
		'<div class="buer"><span class="red"></span>身份信息:</div>' +
		'<select class="shenFen" style="width:155px"> ' +
	        '<option value="volvo" >--选择身份信息--</option> ' +
//	        '<option value="saab">中医爱好者</option> ' +
//	        '<option value="opel">中医爱好者</option> ' +
//	        '<option value="audi">中医爱好者</option> ' +
	        '</select> ' +
		'<span class="nick-warn-shenfen warning" style="margin-left:0px">身份信息不能为空</span>' +
		'</div>' +
		
		'<div class="cy-myprofile-myfom-dv-1">' +
		'<div class="buer"><span class="red"></span>职业信息:</div>' +
		'<input type="text" maxlength="20" class="zhiYe ipt"/>' +
		'<span class="nick-warn warning">职业信息不能为空</span>' +
		'</div>' +

		
		
		'<div class="cy-myprofile-myfom-dv-radio-zu">' +
		'<div class="buer"><i class="red"></i>性别:</div>' +
		'<label class="kecheng-man1"><div class="radio-cover"><em class="active"></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="1" id="myradio1"/></input><span>男</span></label>' +
		'<label class="kecheng-woman1"><div class="radio-cover"><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="0" id="myradio2"/></input><span>女</span></label>' +
//		'<span class="sex-warn warning" style="display:none">请选择性别</span>' +
		'</div>' +
		
		//三级联动
		 '<div class="address-info2 cy-myprofile-myfom-dv-1"> ' +
	        '<p class="buer"><span></span>所在地区:</p> ' +
	        
	        //省
	        '<select class="province"> ' +
	        '<option value="volvo">--选择省--</option> ' + 
	        '</select> ' +
	        //市
	        '<select class="city"> ' +
	        '<option value="volvo" >--选择市--</option> ' +
	        '</select> ' +
	        
	        //区
	        '<select class="district"> ' +
	        '<option value="volvo">--选择区/县--</option> ' +
	        '</select> ' +

	        
	        '<p class="address_warn  warning" style="display:none;color:red">请填写所在地区信息</p>'+
	        '</div> ' +
	        
		'<div>' +
		'<div class="buer"></div>' +
		'</div>' +
		'<div>' +
		'  <div class="buer"></div>' +
		' <button class="btn1" id="geren">保存</button>' +
		//		'<button class="btn2" id="cancel">取消</button>' +
		'</div>' +
		'</div>' +

	
		
		//添加常用地址
		'<div class="usAddress" style="display: none;">'+
		 '<div class="address-title clearfix" style="margin-top:20px">' +
	        '<button class="add-addressBtn">+常用地址添加</button>' +
	        '<p>您已创建 <span class="addNum">1</span>个收货地址，最多可以创建<span>20</span>个</p> ' +
	        '</div> ' +
	        // <!--常用地址列表-->
	        '<div class="address-list"> ' +
	        '<div class="address-main"> ' +
	        '<span class="address-main-close">X</span> ' +
	        '<div class="address-maim-top clearfix"><p>天天&nbsp;&nbsp;</p></div> ' +
	        '<div class="clearfix"><p>收货人: <span></span></p><p>手机: <span></span></p></div> ' +
	        '<div class="clearfix"><p>所在地区: <span></span></p><p>详细地址: <span></span></p></div> ' +
	        '<div class="clearfix"><p>邮编: <span></span></p></div> ' +
	        '<div class="clearfix"><a href="javascript:;">编辑</a><a href="javascript:;">设为默认</a></div> ' +
	        '</div> ' +
	        '</div> ' +
	        // <!--隐藏的添加地址的输入列表-->

	        '<div class="add-address hide"> ' +
	        '<div class="add-address-title"><h5 class="style_title"></h5><span class="add-address-close">x</span></div> ' +
	        '<div class="address-info1"> ' +
	        '<p><span>*</span>收货人:</p> ' +
	        '<input type="text" class="consignee" maxlength="15">' +
	        '</div> ' +
	        
	        '<div class="address-info2"> ' +
	        '<p><span>*</span>所在地区:</p> ' +
	        

			'<select class="province">'+
			'</select>'+
			'<select class="city" >'+
			'</select>'+
			'<select class="district">'+
			'</select>'+
	        
	        
	        
	        '<p class="address_warn" style="display:none;color:red">请填写所在地区信息</p>'+
	        '</div> ' +
	        '<div class="address-info3"> ' +
	        '<p><span>*</span>详细地址:</p> ' +
	        '<input type="text" class="detailedAddress"> ' +
	        '</div> ' +
	        '<div class="address-info4 clearfix"> ' +
	        '<div class="address-info4-left"> ' +
	        '<p><span>*</span>手机号码:</p> ' +
	        '<input type="text" maxlength="11"  class="phone"> ' +
	        '</div> ' +
	        '<div class="address-info4-right"> ' +
	        '<p><span>*</span>邮编:</p> ' +
	        '<input type="text" class="postalCode"> ' +
	        '</div> ' +
	        '</div> ' +
	        '<button class="submit-address">保存地址</button> ' +
	        '<button class="cancel">取消</button> ' +
	        '</div>'
		
		'</div>'+
'<div id="mask" style="display: none;position:fixed;top:0;left:0;width:100%;height:100%;background:#000;opacity:0.3;z-index:888;filter:alpha(opacity=30);">'
	
});

//点击直播课程详情弹窗结束








