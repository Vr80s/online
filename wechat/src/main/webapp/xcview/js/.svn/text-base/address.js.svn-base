

	
	/**
	 * 新增地址或修改地址返回
	 */
	$("#update_address_return").click(function(){
		
		location.href ='address.html';
	})
	
	/**
	 * 点击保存 跳转到编辑页面
	 */
	$(".site_newly").click(function(){
		$(".site_edit").show();
		$(".site_call").hide();
		$(".attention").hide();
		
		$("#consignee").val("");
	    $("#phone").val("");
	    $("#cityP").text("请选择");
		$("#detailed_address").val("");
		$("#postal_code").val("");
		$("#address_id").val("");
	});
	
	 var addressId = getQueryString("id");
	 if(stringnull(addressId)){
		 
		 editAddress(addressId);
	 }
	 
	 /**
	  * 修改地址
	  */
	 function editAddress(addressId){
		 requestService("/bxg/city/findAddressById", 
					{id:addressId}, function(data) {
				if (data.success) {
					var umv = data.resultObject;
					$("#address_id").val(umv.id);
				    $("#consignee").val(umv.consignee);
				    $("#phone").val(umv.phone);
				    var cityp = umv.provinces+" "+ umv.city+" "+umv.county
				    $("#cityP").text(cityp);
					$("#detailed_address").val(umv.detailedAddress);
				/*	$("#postal_code").val(umv.postalCode);*/
				} else {
				    alert("获取数据有误！");
					return false;
				}
		});
	 }
	
	/**
	 * 保存地址
	 */
	$("#address_save").click(function(){
		var consignee = $("#consignee").val();
		var consigneeLength = consignee.length;
		if(!stringnull(consignee) && (consigneeLength<3 || consigneeLength >20) ){  
			$("#errorMsg").html("<p>收货人不能为空</p><p>收货人长度0-20</p>");
			$("#errorMsg").show();
			return false;
		}
		var phone =$("#phone").val();
		if (!stringnull(phone)) {
			$("#errorMsg").text("手机号不能为空");
			$("#errorMsg").show();
			return false;
		}
		if (!(/^1[34578]\d{9}$/.test(phone))) {
			$("#errorMsg").text("手机号格式不正确");
			$("#errorMsg").show();
			return false;
		}
		
		var detailed_address =$("#detailed_address").val();
		if(!stringnull(detailed_address) || (detailed_address.length >50)){  
			$("#errorMsg").html("<p>收货人不能为空</p><p>详细地址不能大于50</p>");
			$("#errorMsg").show();
			return false;
		}
		var cityP =$("#cityP").text();
		if(!stringnull(cityP) || cityP == "请选择"){
			$("#errorMsg").html("<p>请选择省市区</p>");
			$("#errorMsg").show();
			return false;
		}
		var provinces ="";var city = "";var county ="";
		if(stringnull(cityP)){
			var arr = cityP.split(" ");
			provinces = arr[0];
			city = arr[1];
			county = arr[2];
		}
		/*var numberReg=/^\d{5}$/;//纯数字验证
		var postalCode = $("#postal_code").val();
		
		if(numberReg.test(postal_code)){
			$("#errorMsg").html("<p>邮政编码只能是5位数字</p>");
			$("#errorMsg").show();
			return false;
		}*/
		
		var urlparm = {
			provinces : provinces,
			city : city,
			county : county,
			detailedAddress:detailed_address,
			/*postalCode:postalCode,*/
			consignee:consignee,
			phone:phone
		}
		
		var addressId  = $("#address_id").val();
		var alertStr = "新增成功";
		
		var url_address = "/bxg/city/saveAddress";
		if(stringnull(addressId)){
			urlparm.id = addressId;
			url_address = "/bxg/city/updateAddress";
			alertStr = "修改成功";
		}
		/**
		 * 保存地址
		 */
		requestService(url_address, 
				urlparm, function(data) {
			if (data.success) {
				
				alert(alertStr);
				/**
				 * 添加后返回到list页面：
				 */
				 location.href ='address.html';
			} else {
				$("#errorMsg").html(data.errorMessage);
				$("#errorMsg").show();
				return false;
			}
		});
	})
	
	
	/* 地址管理结束 */
	function addressList(){
		requestService("/bxg/city/getAddressAll", 
				null, function(data) {
			if (data.success) {
				//$("#address_list").html("");
				//更改完手机号后，需要把session中的这个东西换下呢？
				var results = data.resultObject;	
				var str ="";
				for (var int = 0; int < results.length; int++) {
					var result = results[int];
					/**
					 * 目前暂时不显示街道
					 */
					//var a_all = result.provinces + result.city+ result.county + result.street +result.detailedAddress;
					var a_all = result.provinces + result.city+ result.county  +result.detailedAddress;

					/*
					 * 是否默认地址
					 */
					var isAcquiesStr = "";
					if(result.isAcquiescence == 1 || results.length==1){//是默认地址
						isAcquiesStr+="<div class='sit_bg site_bg01'></div><span class=''>默认地址</span>";
					}else{
						isAcquiesStr+="<div class='site_bg1 sit_bg'></div><span class=''>设为默认</span>";
					}
					str += "<div class='site'>"+
					"<div class='site_div'>"+
						"<div class='site_div_cen'>"+
							"<div class='site_div_cen_top'><span class='site_cen1'>"+result.consignee+"</span><span class='site_cen2'>"+result.phone+"</span></div>"+
							"<div class='site_div_text'>"+a_all+"</div>"+
							/*"<div class='site_div_text'>"+result.postalCode+"</div>"+*/
							"<div class='site_bto'>"+
								"<div class='site_bto_left' id='"+result.id+"' >"+isAcquiesStr+"</div>"+
								"<div class='site_bto_right'>"+
									"<div class='site_bto_right01 edit_go' name=''  title='"+result.id+"'>"+
										"<div class='site_bg001'></div>"+
										"<span>编辑</span>"+
									"</div>"+
									"<div class='site_bto_right02'  name='' title='"+result.id+"'>"+
										"<div class='site_bg002'></div>"+
										"<span >删除</span>"+
									"</div>"+
								"</div>"+
							"</div>"+
						"</div>"+
					"</div>"+
					"</div>";
				}
				$("#address_list").html(str);
			} else {
				alert("请求列表有误");
			}
		},false);
		
		 /*点击默认开始*/
		 var aBtn6=$('.site_bto_left');
		 
		 if(aBtn6.length > 1){
			 for(i=0;i<aBtn6.length;i++){
		          $(aBtn6[i]).click(function(){
		          	for(i=0;i<aBtn6.length;i++){ 
		          		$(aBtn6[i]).find('.sit_bg').addClass('site_bg1');
		                $(aBtn6[i]).find('.sit_bg').removeClass('site_bg01');
		            }
		            $(this).find('.sit_bg').addClass('site_bg01');
		            $(this).find('.sit_bg').removeClass('site_bg1');
		            //然后点击的时候呢，需要判断
		            var newId = $(this)[0].id;
		        	/**
		        	 * 保存地址
		        	 */
		        	requestService("/bxg/city/updateIsAcquies", 
		        			{newId:newId}, function(data) {
		        		if (data.success) {
		        			console.log("设置默认成功");
		        		} else {
		        			console.log("设置默认失败");
		        		}
		        	});
		            
		          })
		     }
		 }

		 
		 /**
		   * 点击修改跳转到编辑页面
			 */
			$(".edit_go").click(function(){
				/*$(".site_edit").show();
				$(".site_call").hide();
				$(".attention").hide();*/
				
				
				var id = $(this)[0].title;
				
				location.href="edit_address.html?id="+id;

			});
			/**
			 * 删除这个地址啦
			 */
			$(".delete_go").click(function(){
				var id = $(this)[0].title;
				//查询下 -- 填充到form表单中
				/**
				 * 根据id查询地址
				 */
				requestService("/bxg/city/deleteAddressById", 
						{id:id}, function(data) {
					if (data.success) {
						 /*alert("删除数据成功！");*/
						 addressList();
					} else {
					    alert("获取数据有误！");
						return false;
					}
				});
			});
		 
		
	}
/**
 * 
 * @param str
 * @param minLength
 * @param maxLength
 */
function checkLengthInterval(str,minLength,maxLength){
	var strlength = str.length;
	if(strlength<minLength || maxLength >18){
		$("#errorMsg").html("<p>新密码不能为空</p><p>6-18位英文大小写字母或阿拉伯数字</p>");
		$("#errorMsg").show();
		return false;
	}
}

