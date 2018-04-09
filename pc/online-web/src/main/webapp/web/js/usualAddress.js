/**
 * Created by suixin on 2017/9/19.
 */
//$(function(){



//个人信息市数据渲染
function getCity1(){
	var pid = $('.Province1 option:selected') .val()
	RequestService("/address/getAllPCC", "get", {}, function(data){
				console.log(data);
				if(data.success == true){
					//市
					 $(".City1").html(template("cit1",{item:data.resultObject[pid-1].cityList}));
					 
					 //<option value="volvo">--选择市--</option>
					//县
					 $(".District1").html(template("dis",{item:null}));
					 
				}
			})
	}

//区县渲染
function getDistrict1(){
	var pid = $('.Province1 option:selected').val()
	var cid =  $('.City1 option:selected').val()
	console.log(cid)
	RequestService("/address/getAllPCC", "get", {}, function(data){
		if(data.success == true){
			//区县
			 $(".District1").html(template("dis",{item:data.resultObject[pid-1].cityList[cid].disList}));
		}
	})
}


//区县渲染
function getDistrict(){
	var pid = $('.Province option:selected').val()
	var cid =  $('.City option:selected').val()
	console.log(cid)
	RequestService("/address/getAllPCC", "get", {}, function(data){
		if(data.success == true){
			//区县
			 $(".District").html(template("dis",{item:data.resultObject[pid-1].cityList[cid].disList}));
		}
	})
}



//市数据渲染
function getCity(){
	var pid = $('.Province option:selected') .val()
	RequestService("/address/getAllPCC", "get", {}, function(data){
				console.log(data);
				if(data.success == true){
					//市
					 $(".City").html(template("cit",{item:data.resultObject[pid-1].cityList}));
					 
					//县
					 $(".District").html(template("dis",{item:null}));
				}
			})
	}




//请求常用地址数据渲染到页面中
function getAddressList(){
	RequestService("/address/getAddressAll", "get", {}, function(data){
		//请求成功渲染
		if(data.success == true){
			console.log(data);
			 $(".address-list").html(template("addressTem",{item:data.resultObject}));
			 var addNum = data.resultObject.length
			 //已经创建的地址数量显示
			 $('.addNum').text(addNum)
		}
	})
}


//清空添加表单
function clearAddressList(){
	$('.add-address #choosePro').val('-1');
	$('.add-address #citys').val('-1');
	$('.add-address #county').val('-1');
	$('.add-address #choosePro  option:selected').text('请选择省');
	$('.add-address .detailedAddress').val('');
	$('.add-address .postalCode').val('');
	$('.add-address .phone').val('');
	$('.add-address .consignee').val('');
}




//点击保存地函数
function submitAddress_add(){
	$('.add-address').addClass('hide');
	$('.address-list').removeClass('hide');
		//获取当前的表单填入信息
		RequestService("/address/saveAddress", "post", {
			provinces:$('.add-address #choosePro  option:selected').text(),
			city:$('.add-address #citys  option:selected').text(),
			county:$('.add-address #county  option:selected').text(),
			detailedAddress:$('.add-address .detailedAddress').val(),
			postalCode:$('.add-address .postalCode').val(),
			consignee:$('.add-address .consignee').val(),
			phone:$('.add-address .phone').val()
		}, function(data){
			//请求成功渲染到页面之中
			if(data.success == true){		
				//保存成功之后重新刷新表单
				getAddressList();
			}
		})	
		
}

function submitAddress_editor(id){
	$('.add-address').addClass('hide');
	$('.address-list').removeClass('hide');
		RequestService("/address/updateAddress", "post", {
				id:id,
				provinces:$('.add-address #choosePro  option:selected').text(),
				city:$('.add-address #citys  option:selected').text(),
				county:$('.add-address #county  option:selected').text(),
				detailedAddress:$('.add-address .detailedAddress').val(),
				postalCode:$('.add-address .postalCode').val(),
				consignee:$('.add-address .consignee').val(),
				phone:$('.add-address .phone').val()
				
			}, function(data){
			//请求成功渲染
			if(data.success == true){
				//再次获取渲染
				getAddressList();
			}
		})	
}



var flag;
function initAddressBind(){

	//个人信息请求三级联动数据
	RequestService("/address/getAllPCC", "get", {}, function(data){
		//请求成功渲染
		if(data.success == true){
			//省
			$(".Province1").html(template("pro1",{item:data.resultObject}));
		}
	})
	
	
	//常用地址请求三级联动数据
	RequestService("/address/getAllPCC", "get", {}, function(data){
		//请求成功渲染
		if(data.success == true){
			//省
			 $(".Province").html(template("pro",{item:data.resultObject}));
		}
	})
		
	//渲染常用地址列表
	getAddressList();
	
	
	//添加常用地址
	$('.add-addressBtn').click(function(){
		$('.add-address input').attr('placeholder','')
		//警告清空
		$('.address_warn').css('display','none');
//		console.log($('.address-list').children().length)
		if($('.address-list').children().length == 20){
			$('.mask2').html("常用地址数量已满").fadeIn(400,function(){
				setTimeout(function(){
					$('.mask2').fadeOut()
				},1000)
			});
			return false;
		}
		flag = 0;
		$('.address-list').addClass('hide');
		$('.add-address').removeClass('hide');
		clearAddressList();
		$('.style_title').text('添加地址')
		
	})
	
	//点击添加列表关闭
	$('.add-address-close').click(function(){
		$('.add-address').addClass('hide');
		$('.address-list').removeClass('hide');
		clearAddressList();
	})
	
	
	var id;
	//点击保存
	$('.submit-address').click(function(){
		//获取需要验证的信息 先进行验证
		var name = $(".add-address .consignee").val();
		var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;
		var detailedAddress = $('.add-address .detailedAddress').val();
		var phone = $('.add-address .phone').val();
		var phone_pass = /^1[3,4,5,7,8]\d{9}$/gi;
		var postalCode = $('.add-address .postalCode').val();
		var postalCode_pass = /^[1-9][0-9]{5}$/;
		var sheng = $('.add-address #choosePro  option:selected').text();
		var shi = $('.add-address #citys  option:selected').text();
		var qu = $('.add-address #county  option:selected').text();
		if(flag==0){
			//姓名验证
			if($.trim(name) == ""){
				$(".add-address .consignee").attr('placeholder','姓名不能为空')
				return false;
			}
			if(!name_pass.test(name)){
				$(".add-address .consignee").val('')
				$(".add-address .consignee").attr('placeholder','姓名格式不正确')
				return false;
			}
			//地址验证
			console.log(sheng)
			if(sheng == '--选择省--'||shi == '--选择市--'||qu == '--选择区/县--'){
				$('.address_warn').css('display','block')
				console.log(7777)
				return false;
			}else{
				$('.address_warn').css('display','none')
			}
			//详细地址验证
			if(!detailedAddress){
				$(".add-address .detailedAddress").attr('placeholder','详细地址不能为空')
				return false;
			}
			//手机验证
			if($.trim(phone) == ""){
				$(".add-address .phone").attr('placeholder','手机号不能为空')
				return false;
			}
			if(!phone_pass.test(phone)){
				$('.add-address .phone').val('')
				$(".add-address .phone").attr('placeholder','手机号格式不正确')
				return false;
			}
			
			//邮编验证
			if($.trim(postalCode) == ""){
				$(".add-address .postalCode").attr('placeholder','邮编不能为空')
				return false;
			}
			if(!postalCode_pass.test(postalCode)){
				$('.add-address .postalCode').val('')
				$(".add-address .postalCode").attr('placeholder','邮编格式不正确')
				return false;
			}
			
			console.log('添加')
			submitAddress_add();
		}else{
			//姓名验证
			if($.trim(name) == ""){
				$(".add-address .consignee").attr('placeholder','姓名不能为空')
				return false;
			}
			if(!name_pass.test(name)){
				$(".add-address .consignee").val('')
				$(".add-address .consignee").attr('placeholder','姓名格式不正确')
				return false;
			}
			//详细地址验证
			if(!detailedAddress){
				$(".add-address .detailedAddress").attr('placeholder','详细地址不能为空')
				return false;
			}
			//手机验证
			if($.trim(phone) == ""){
				$(".add-address .phone").attr('placeholder','手机号不能为空')
				return false;
			}
			if(!phone_pass.test(phone)){
				$('.add-address .phone').val('')
				$(".add-address .phone").attr('placeholder','手机号格式不正确')
				return false;
			}
			//地址验证
			console.log(sheng)
			if(sheng == '请选择省'||shi == '请选择市'||qu == '请选择区/县'){
				$('.address_warn').css('display','block')
				console.log(7777)
				return false;
			}
			//邮编验证
			if($.trim(postalCode) == ""){
				$(".add-address .postalCode").attr('placeholder','邮编不能为空')
				return false;
			}
			if(!postalCode_pass.test(postalCode)){
				$('.add-address .postalCode').val('')
				$(".add-address .postalCode").attr('placeholder','邮编格式不正确')
				return false;
			}
			
			console.log('编辑')
			submitAddress_editor(id);
		}
	})
	
	
	//点击取消按钮
	$('.cancel').click(function(){
		$('.add-address').addClass('hide');
		$('.address-list').removeClass('hide');
		clearAddressList();
	})
	
	//点击编辑按钮
	$('.address-list').on('click','.editor',function(){
		
		flag = 1;
//		$(this).css('color','red')
		$(this).parent().parent().find('span').prop('contenteditable','true')
		  
		$('.address-list').addClass('hide');
		$('.add-address').removeClass('hide');
		$('.style_title').text('编辑地址')
		//将内容填写到表中
		console.log($(this).parent().parent().find('.consignee').text())
		//收货人
		$('.add-address .consignee').val($(this).parent().parent().find('.consignee').text());
		//详细地址
		$('.add-address .detailedAddress').val($(this).parent().parent().find('.detailedAddress').text());
		//手机号码
		$('.add-address .phone').val($(this).parent().parent().find('.phone').text());
		//邮编
		$('.add-address .postalCode').val($(this).parent().parent().find('.postalCode').text());
		//省
		$('.add-address #choosePro  option:selected').text($(this).parent().parent().find('.sheng').text())
		//市
		$('.add-address #citys  option:selected').text($(this).parent().parent().find('.shi').text())
		//区
		$('.add-address #county  option:selected').text($(this).parent().parent().find('.xiang').text())
		
		
		//此时生成id值
	    id = $(this).parent().parent().attr('data-id');

	})
	
	
	//删除地址
	$('.address-list').on('click','.address-main-close',function(){
		$('#delTip').removeClass('hide');
		$('.mask').css('display','block');
		
//		if(confirm("确定删除该项地址么？")){
//		 id = $(this).parent().attr('data-id');
//		console.log(id);
//		RequestService("/address/deleteAddressById", "get", {
//			id:id
//		}, function(data){
//			if(data.success == true){
//				$(this).parent().remove();	
//				//重新渲染页面				
//				getAddressList();
//
//			}
//		})
//	}	

//点击确认删除
	id = $(this).parent().attr('data-id');
	$('.delAddress').click(function(){
		
		console.log(id);
		RequestService("/address/deleteAddressById", "get", {
			id:id
		}, function(data){
			if(data.success == true){
				$(this).parent().remove();	
				//重新渲染页面				
				getAddressList();

			}
		})
		$('#delTip').addClass('hide');
		$('.mask').css('display','none');
	})
	})
	
	//取消删除
	$(".cancelDelAddress").click(function(){
		$('#delTip').addClass('hide');
		$('.mask').css('display','none');
	})
	$("#closeAddredddel").click(function(){
		$('#delTip').addClass('hide');
		$('.mask').css('display','none');
	})
	
	
	//设置为默认地址
	$('.address-list').on('click','.setAsaddress',function(){
		id = $(this).parent().parent().attr('data-id');
		console.log(id);
		RequestService("/address/updateIsAcquies", "post", {
			newId:id
		}, function(data){
			console.log(data);
			if(data.success == true){
				//重新渲染页面
				getAddressList();
			}
		})
		
	})
	
	
	
		
}






//    var html =
//        '<div class="address-title clearfix">' +
//        '<button class="add-addressBtn">常用地址添加</button>' +
//        '<p>您已常创建 <span>1</span>个收货地址，最多可以常见 <span>20</span>个</p> ' +
//        '</div> ' +
//        // <!--常用地址列表-->
//        '<div class="address-list"> ' +
//        '<div class="address-main"> ' +
//        '<span class="address-main-close">X</span> ' +
//        '<div class="address-maim-top clearfix"><p>天天</p></div> ' +
//        '<div class="clearfix"><p>收货人: <span>天天</span></p><p>邮编: <span>115100</span></p></div> ' +
//        '<div class="clearfix"><p>所在地址: <span>海南海口市美兰区演丰镇</span></p><p>地址: <span>心承志会大厦201</span></p></div> ' +
//        '<div class="clearfix"><p>手机: <span>139****6940</span></p></div> ' +
//        '<div class="clearfix"><a href="javascript:;">编辑</a><a href="javascript:;">设为默认</a></div> ' +
//        '</div> ' +
//        '</div> ' +
//        // <!--隐藏的添加地址的输入列表-->
//
//        '<div class="add-address hide"> ' +
//        '<div class="add-address-title"><h5>常用地址添加</h5><span class="add-address-close">x</span></div> ' +
//        '<div class="address-info1"> ' +
//        '<p><span>*</span>收货人:</p> ' +
//        '<input type="text"> ' +
//        '</div> ' +
//        '<div class="address-info2"> ' +
//        '<p><span>*</span>所在地区:</p> ' +
//        '<select> ' +
//        '<option value="volvo">Volvo</option> ' +
//        '<option value="saab">Saab</option> ' +
//        '<option value="opel">Opel</option> ' +
//        '<option value="audi">Audi</option> ' +
//        '</select> ' +
//        '</div> ' +
//        '<div class="address-info3"> ' +
//        '<p><span>*</span>详细地址:</p> ' +
//        '<input type="text"> ' +
//        '</div> ' +
//        '<div class="address-info4 clearfix"> ' +
//        '<div class="address-info4-left"> ' +
//        '<p><span>*</span>手机号码:</p> ' +
//        '<input type="text"> ' +
//        '</div> ' +
//        '<div class="address-info4-right"> ' +
//        '<p>邮编:</p> ' +
//        '<input type="text"> ' +
//        '</div> ' +
//        '</div> ' +
//        '<button class="submit-address">保存收货地址</button> ' +
//        '</div>'

//})