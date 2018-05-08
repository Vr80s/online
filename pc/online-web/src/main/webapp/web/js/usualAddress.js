/**
 * Created by suixin on 2017/9/19.
 */
var addressList;
//请求常用地址数据渲染到页面中
function getAddressList(){
	RequestService("/address/getAddressAll", "get", {}, function(data){
		//请求成功渲染
		if(data.success == true){
			console.log(data);
			addressList = data.resultObject;
			 $(".address-list").html(template("addressTem",{item:addressList}));
			 var addNum = data.resultObject.length
			 //已经创建的地址数量显示
			 $('.addNum').text(addNum)
		}
	})
}


//清空添加表单
function clearAddressList(){
	// $('.add-address #choosePro').val('-1');
	// $('.add-address #citys').val('-1');
	// $('.add-address #county').val('-1');
	// $('.add-address #choosePro  option:selected').text('请选择省');
	debugger
    $(".add-address").iProvincesSelect("init");
	$('.add-address .detailedAddress').val('');
	$('.add-address .postalCode').val('');
	$('.add-address .phone').val('');
	$('.add-address .consignee').val('');
}

//点击保存地函数
function submitAddress_add(){
    var pcd = $(".add-address").iProvincesSelect("val");
	$('.add-address').addClass('hide');
	$('.address-list').removeClass('hide');
		//获取当前的表单填入信息
		RequestService("/address/saveAddress", "post", {
			provinces:pcd.province,
			city:pcd.city,
			county:pcd.district,
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
	//渲染常用地址列表
	getAddressList();
	
	//添加常用地址
	$('.add-addressBtn').click(function(){
		$('.add-address input').attr('placeholder','')
		//警告清空
		$('.address_warn').css('display','none');
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
		$(this).parent().parent().find('span').prop('contenteditable','true')
		  
		$('.address-list').addClass('hide');
		$('.add-address').removeClass('hide');
		$('.style_title').text('编辑地址')
		//将内容填写到表中
        var address = addressList[$(this).attr("data-index")];
		//收货人
		$('.add-address .consignee').val(address.consignee);
		//详细地址
		$('.add-address .detailedAddress').val(address.detailedAddress);
		//手机号码
		$('.add-address .phone').val(address.phone);
		//邮编
		$('.add-address .postalCode').val(address.postalCode);
		var pcd = {};
		pcd.province=address.provinces;
		pcd.city=address.city;
		pcd.district=address.county;
        $(".add-address").iProvincesSelect("init",pcd);
		
		//此时生成id值
	    id = $(this).parent().parent().attr('data-id');

	})
	
	
	//删除地址
	$('.address-list').on('click','.address-main-close',function(){
		$('#delTip').removeClass('hide');
		$('.mask').css('display','block');

//点击确认删除
	id = $(this).parent().attr('data-id');
	$('.delAddress').click(function(){
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