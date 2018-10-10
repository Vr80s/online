
    /**
     * 可能是来自填写报名信息页面的
     */
    /*var userId = localStorage.getItem("userId");
    $(".address_return").click(function(){
    	var person = sessionStorage.getItem("address_back");
    	if(person=="personalfor.html"){
    		location.href ='personalfor.html?userId='+userId;
    	}else{
    		location.href ='persons.html';
    	}
    })*/
   
   /*可能来自确认订单页面*/
   	function isLoginJump() {
	    var userId = localStorage.userId;
	    if (isNotBlank(userId)) {
	        /*
	         * 判断这上个地址是否来自我们的浏览器啦。如果是的就返回上一页，如果不是的话，那么就返回首页吧。
	         */
	        var before_address = document.referrer;
	        if (before_address.indexOf("page/index") != -1 ||
	            before_address.indexOf("/xcview/html/shop/confirm_order.html") != -1 ||   //确认订单页
	            before_address.indexOf("edit_address.html") != -1 ||   //确认订单页
	            before_address.indexOf("persons.html") != -1) {  //资料设置页
	
	            window.history.back();
	        }
	    } else {
	        //登录页面
	        location.href = "home_page.html";
	        
	    }
	}
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
	 if(isNotBlank(addressId)){
		 
		 editAddress(addressId);
	 }
	 
	 /**
	  * 修改地址
	  */
	 var isTrue=true;
	 
	 function editAddress(addressId){
		 requestGetService("/xczh/shop/receiver?receiverId="+addressId,null, function(data) {
				if (data.success) {
					var umv = data.resultObject;
					$("#address_id").val(umv.id);
				    $("#consignee").val(umv.consignee);
				    $("#phone").val(umv.phone);
				    $("#zip-code").val(umv.zipCode);
				    isTrue=umv.isDefault;
				    //
				    var quId=data.resultObject.id;
				    var provice=umv.area;
				    var arrProvice=umv.area.treePath.split(',')
//				   	 省
				    var proviceDom=$(".input-group .sheng option");
				    if(arrProvice.length==4){
				    	$(".sheng").removeClass("hide");
				    	$(".shi").removeClass("hide");
				    	$(".qu").removeClass("hide");
				    	arrProvice[1]
				    	requestService("/xczh/shop/area?parentId="+arrProvice[1],null, function(data) {
				    		for(var i=1; i<proviceDom.length; i++){
				    			if(proviceDom[i].getAttribute("data-id")==arrProvice[1]){
				    				$(".input-group .sheng option").eq(i).attr("selected","selected");
				    			}
				    		}
				    	})
//				    	市先获取数据加载后回显
				    	arrProvice[1]
				    	requestService("/xczh/shop/area?parentId="+arrProvice[1],null, function(data) {
				    		var str;
							var city=data.resultObject;
							for(var i=0; i<city.length;i++){
								str+="<option data-id="+city[i].value+">"+city[i].name+"</option>";
							}
							$(".input-group .shi").html('<option data-id="0">请选择</option>'+str);
//							市回显
							var cityDom=$(".input-group .shi option");
				    		for(var i=0; i<cityDom.length; i++){
				    			if(cityDom[i].getAttribute("data-id")==arrProvice[2]){
				    				$(".input-group .shi option").eq(i).attr("selected","selected");
				    			}
				    		}
				    	})
//				    	区先获取数据加载后回显
						arrProvice[2]
				    	requestService("/xczh/shop/area?parentId="+arrProvice[2],null, function(data) {
				    		var str;
							var city=data.resultObject;
							for(var i=0; i<city.length;i++){
								str+="<option data-id="+city[i].value+">"+city[i].name+"</option>";
							}
							$(".input-group .qu").html('<option data-id="0">请选择</option>'+str);
//							市回显
							var quDom=$(".input-group .qu option");
							
				    		for(var i=0; i<quDom.length; i++){
				    			if(quDom[i].getAttribute("data-id")==provice.id){
				    				$(".input-group .qu option").eq(i).attr("selected","selected");
				    			}
				    		}
				    		getQuId=provice.id;
				    	})

				    }else if(arrProvice.length==3){
				    	$(".sheng").removeClass("hide");
				    	$(".shi").removeClass("hide");
				    	$(".qu").addClass("hide");
				    	arrProvice[1]
				    	requestService("/xczh/shop/area?parentId="+arrProvice[1],null, function(data) {
				    		for(var i=1; i<proviceDom.length; i++){
				    			if(proviceDom[i].getAttribute("data-id")==arrProvice[1]){
				    				$(".input-group .sheng option").eq(i).attr("selected","selected");
				    			}
				    		}
				    	})
//				    	市先获取数据加载后回显
				    	arrProvice[1]
				    	requestService("/xczh/shop/area?parentId="+arrProvice[1],null, function(data) {
				    		var str;
							var city=data.resultObject;
							for(var i=0; i<city.length;i++){
								str+="<option data-id="+city[i].value+">"+city[i].name+"</option>";
							}
							$(".input-group .shi").html('<option data-id="0">请选择</option>'+str);
//							市回显
							var cityDom=$(".input-group .shi option");
				    		for(var i=0; i<cityDom.length; i++){
				    			if(cityDom[i].getAttribute("data-id")==provice.id){
				    				$(".input-group .shi option").eq(i).attr("selected","selected");
				    			}
				    		}
				    		getQuId=provice.id;
				    	})
				    }
				 
				    //var cityp = umv.provinces+" "+ umv.city

					$("#detailed_address").val(umv.address);
				/*	$("#postal_code").val(umv.postalCode);*/
				} else {
//				    alert("获取数据有误！");
					$(".vanish0").show();
					setTimeout(function(){$(".vanish0").hide();},1500);
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
		if(isBlank(consignee) && (consigneeLength<3 || consigneeLength >20) ){
//			$("#errorMsg").html("<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>请填写收货人</div></div></div>");
			/*$("#errorMsg").show();*/
//			setTimeout(function(){$(".vanish").hide();},900000);
			webToast("请填写收货人","middle",1500);
			return false;
			
		}
		$("#vanishs").hide();
//		手机号
		var phone =$("#phone").val();
		if (isBlank(phone)) {
			/*$("#errorMsg").html("<div class='vanish0'><div class='vanish0_bg'></div><div class='vanish0_cen'><div class='vanish0_size'>请填写手机号</div></div></div>");
			$("#errorMsg").show();
			setTimeout(function(){$(".vanish0").hide();},1500);*/
			webToast("请填写手机号","middle",1500);
			return false;
		}
		if (!(/^1[345678]\d{9}$/.test(phone))) {
			/*$("#errorMsg").html("<div class='vanish1'><div class='vanish1_bg'></div><div class='vanish1_cen'><div class='vanish1_size'>手机号格式不正确</div></div></div>");
			$("#errorMsg").show();
			setTimeout(function(){$(".vanish1").hide();},1500);*/
			webToast("请输入正确的手机号","middle",1500);
			return false;
		}
//		邮编
		var zipCode =$("#zip-code").val();
		if(isBlank(zipCode)){
//			$("#errorMsg").html("<div class='vanish3'><div class='vanish3_bg'></div><div class='vanish3_cen'><div class='vanish3_size'>请选择省市区</div></div></div>");
//			$("#errorMsg").show();
//			setTimeout(function(){$(".vanish3").hide();},1500);
			webToast("请填写邮编","middle",1500);
			return false;
		}
		if (!(/^\d+$/.test(zipCode))) {
			/*$("#errorMsg").html("<div class='vanish1'><div class='vanish1_bg'></div><div class='vanish1_cen'><div class='vanish1_size'>手机号格式不正确</div></div></div>");
			$("#errorMsg").show();
			setTimeout(function(){$(".vanish1").hide();},1500);*/
			webToast("请输入正确的邮编","middle",1500);
			return false;
		}
//		省市区
//		var cityP =$("#cityP").text();
//		if(isBlank(cityP) || cityP == "请选择"){
//			webToast("请选择省市区","middle",1500);
//			return false;
//		}
//		省市区,判断是新增地址的ID 还是回显地址的ID
		var areaId=getQuId;
		if($(".sheng").val()=="请选择"){
			webToast("请完善所在地区","middle",1500);
			return false;
		}else if($(".shi").val()=="请选择"){
			webToast("请完善所在地区","middle",1500);
			return false;
		}else if($(".qu").hasClass("hide")){
			areaId=getCityId;
		}else if($(".qu").hasClass("hide")==false && $(".qu").val()=="请选择"){
			webToast("请完善所在地区","middle",1500);
			return false;
		}
		
		
//		详细地址
		var detailed_address =$("#detailed_address").val();
		if(isBlank(detailed_address) || (detailed_address.length >50)){
			/*$("#errorMsg").html("<div class='vanish2'><div class='vanish2_bg'></div><div class='vanish2_cen'><div class='vanish2_size'>收货人不能为空,详细地址不能大于50</div></div></div>");
			$("#errorMsg").show();
			setTimeout(function(){$(".vanish2").hide();},1500);*/
//			$("")
			// webToast("收货人不能为空,详细地址不能大于50","middle",1500);
			webToast("请输入详细地址","middle",1500);
			// $(".web_toast").css("left","50%");
			// $(".web_toast").css("margin-left","-2.1rem");
			return false;
		}
		
//		var provinces ="";var city = "";var county ="";
//		if(isNotBlank(cityP)){
//			var arr = cityP.split(" ");
//			provinces = arr[0];
//			city = arr[1];
//			county = arr[2];
//		}
		/*var numberReg=/^\d{5}$/;//纯数字验证
		var postalCode = $("#postal_code").val();
		
		if(numberReg.test(postal_code)){
			$("#errorMsg").html("<p>邮政编码只能是5位数字</p>");
			$("#errorMsg").show();
			return false;
		}*/

		var urlparm = {
//			provinces : provinces,
//			city : city,
//			county : county,
			areaId:areaId,
			zipCode : zipCode,
			address:detailed_address,
			/*postalCode:postalCode,*/
			consignee:consignee,
			phone:phone,
			isDefault:isTrue
		}
	
	var addressId  = $("#address_id").val();
		$(".person_prosperity").show();
		$(".prosperity_cen_top").text("新增成功");
		
		var url_address = "/xczh/shop/receiver/add";
		if(isNotBlank(addressId)){
			urlparm.receiverId = addressId;
			url_address = "/xczh/shop/receiver/update";
			$(".person_prosperity").show();
			$(".prosperity_cen_top").text("修改成功");
		}
		/**
		 * 保存地址
		 */
		requestService(url_address, 
				urlparm, function(data) {
			if (data.success) {
//				$(".person_prosperity").show();
//				$(".prosperity_cen_top").text("新增成111功");
				/*alert(alertStr);*/
				
				/**
				 * 添加后返回到list页面：
				 */
/*				 location.href ='address.html';*/
			} else {
				$("#errorMsg").html(data.errorMessage);
				$("#errorMsg").show();
//				$(".prosperity_cen_top").text("新增成功");


				/*$(".person_prosperity").show();
				$(".prosperity_cen_top").text("新增成11111功");*/
				return false;
			}
		});
	})
	
	
	
	
	
	/* 地址管理结束 */
	function addressList(){
		requestGetService("/xczh/shop/receiver/list",null, function(data) {
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
//					var a_all = result.provinces + result.city+ result.county + result.street +result.detailedAddress;
					var a_all = "";
				    if(isNotBlank(result.areaName)){
				    	a_all+=result.areaName+"";
				    }
//				    if(isNotBlank(result.city)){
//				    	a_all+=result.city+"";
//				    }
//				    if(isNotBlank(result.county)){
//				    	a_all+=result.county+" ";
//				    }
				    if(isNotBlank(result.address)){
				    	a_all+=result.address;
				    }
					/*
					 * 是否默认地址
					 */
					var isAcquiesStr = "";
					if(result.isDefault == true || results.length==1){//是默认地址
						isAcquiesStr+="<div class='sit_bg site_bg01'></div><span class=''>默认地址</span>";
					}else{
						isAcquiesStr+="<div class='site_bg1 sit_bg'></div><span class='moren_span' style='color: #666;'>设为默认地址</span>";
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
									"<div class='site_bto_right02' name='' title='"+result.id+"'>"+
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
				$(".vanish5").show();
				setTimeout(function(){$(".vanish5").hide();},1500);
				/*alert("请求列表有误");*/
			}
		},false);
		
		 /*点击默认开始*/
		 var aBtn6=$('.site_bto_left');
		 
		 if(aBtn6.length > 1){
			 for(i=0;i<aBtn6.length;i++){
		          $(aBtn6[i]).click(function(){
		          	for(i=0;i<aBtn6.length;i++){ 
		          		/*$(aBtn6[i]).find('.sit_bg').addClass('site_bg1');
		                $(aBtn6[i]).find('.sit_bg').removeClass('site_bg01');*/
		                var obj = $(aBtn6[i]).find('.sit_bg');
		                var objClassName = obj.attr("class");
		                if(objClassName.indexOf("site_bg01")!=-1){
		                	obj.removeClass('site_bg01');
		                	obj.addClass('site_bg1');
		                	var span =  obj.next();
		                	span.addClass("moren_span");
		                	span.text("设置为默认地址");
		                	break;
		                }
		            }
		          	var this_Obj = $(this).find('.sit_bg');
		          	this_Obj.addClass('site_bg01');
		          	this_Obj.removeClass('site_bg1');
		          	var this_span = this_Obj.next();
		          	this_span.text("默认地址");
		          	this_span.removeClass("moren_span");
		            //然后点击的时候呢，需要判断
		            var newId = $(this)[0].id;
		        	/**
		        	 * 保存地址
		        	 */
		        	requestService("/xczh/shop/receiver/setDefaultReceiver",
		        		{
		        			"receiverId":newId,
		        			"isDefault":true
		        		}, function(data) {
		        		if (data.success) {
		        			
		        			addressList();
		        			
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
				var id = $(this)[0].title;
				location.href="edit_address.html?id="+id;
			});
			/**
			 * 删除这个地址啦
			 */
			$(".site_bto_right02").click(function(){
				var id = $(this)[0].title;
				$(".history_bg_bto2").attr("title",id);
				$(".history_bg").show();
				//deleteAddress(this);
			});
	}
/**
 * 隐藏确定删除的弹框
 */	
$(".history_bg_bto1").click(function(){
	$(".history_bg").hide();
});
$(".history_bg_bto2").click(function(){
	deleteAddress(this);
	$(".history_bg").hide();
});
function deleteAddress(obj){
	var addressId =  $(obj).attr("title");
	if(isNotBlank(addressId)){
		//查询下 -- 填充到form表单中
		/**
		 * 根据id查询地址
		 */
		requestService("/xczh/shop/receiver/delete?receiverId="+addressId,null,function(data) {
			if (data.success) {
				 $(obj).attr("title","");
				webToast("删除成功","middle",1500);

				 addressList();
			} else {
//			    alert("获取数据有误！");
				$(".vanish1").show();
				setTimeout(function(){$(".vanish1").hide();},1500);
				return false;
			}
		});
	}
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

