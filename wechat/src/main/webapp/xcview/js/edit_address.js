
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
   //新的省市区
	var getProviceId="";
	var getCityId="";
	var getQuId="";
	var urlProvice="/xczh/shop/area?parentId="+getProviceId;
//	省
	requestService(urlProvice,null, function(data) {
		var str;
		var provice=data.resultObject;
			for(var i=0; i<provice.length;i++){
				str+="<option value="+provice[i].value+">"+provice[i].name+"</option>";
			}
		$(".input-group .sheng").html('<option value="0">请选择</option>'+str);
		$(".input-group .shi").addClass("hide");
		$(".input-group .qu").addClass("hide");
	})
//	省变化时
	$(".input-group .sheng").on("change",function(){
		 	getProviceId=$(".input-group .sheng option:selected").val();			
//	省市变化时,清空市和区的id,并隐藏区
			$(".input-group .qu").addClass("hide");
			getCityId="";
			getQuId="";	
//	如果省的value不为请选择时
		if(getProviceId != 0){
			requestService("/xczh/shop/area?parentId="+getProviceId,null, function(data) {
				var str;
				var city=data.resultObject;
				if(city != null && city.length != 0){
					$(".input-group .shi").removeClass("hide");
					for(var i=0; i<city.length;i++){
						str+="<option value="+city[i].value+">"+city[i].name+"</option>";
					}
					$(".input-group .shi").html('<option value="0">请选择</option>'+str);
				}else{
					$(".input-group .shi").addClass("hide");
				}
				
			})
		}else{
			$(".input-group .shi").addClass("hide");
		}
	})
//	市变化时
	$(".input-group .shi").on("change",function(){
		 	getCityId=$(".input-group .shi option:selected").val();	
//		市发生改变时都让他为空
		 	getQuId="";
			requestService("/xczh/shop/area?parentId="+getCityId,null, function(data) {
				var str;
				var qu=data.resultObject;
				if (qu.length == 0 || getCityId==0) {
					$(".input-group .qu").addClass("hide");
					getQuId="";
				} else{
					$(".input-group .qu").removeClass("hide");
					for(var i=0; i<qu.length;i++){
						str+="<option value="+qu[i].value+">"+qu[i].name+"</option>";
					}
						$(".input-group .qu").html('<option value="0">请选择</option>'+str);
				}

			})
	})
//	区变化时
	$(".input-group .qu").on("change",function(){
		getQuId=$(".input-group .qu option:selected").val();
		if(getQuId == 0){
			getQuId="";
		}

	})

	
	 var addressId = getQueryString("id");
	 if(isNotBlank(addressId)){
		 
		 editAddress(addressId);
	 }
	 
	 /**
	  * 修改地址
	  */
	 $(".update_address_return").click(function(){
//		alert(location.href);
//		location.href ='address.html';
//		history.go(-1);
		
		function getQueryString(name){
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r!=null) return r[2];else return'';
        }
		
        var type = getQueryString('type');
        var types = getQueryString('types');
        if(type=='2'){
        	
        	location.href ='address.html?type='+ type+"&types=4";
        }else if(type=='3'){
            location.replace("/xcview/html/persons.html");
        }
        getQueryString('name');
		
		
		
		
	})
	 
	 var isTrue=true;

	 function editAddress(addressId){
		 requestGetService("/xczh/shop/receiver?receiverId="+addressId,null, function(data) {
				if (data.success) {
					var umv = data.resultObject;
					$("#address_id").val(umv.id);
				    $("#consignee").val(umv.consignee);
				    $("#phone").val(umv.phone);
//				    $("#zip-code").val(umv.zipCode);
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
						$(".input-group .sheng").val(arrProvice[1]);


//				    	市先获取数据加载后回显

				    	requestService("/xczh/shop/area?parentId="+arrProvice[1],null, function(data) {
				    		var str;
							var city=data.resultObject;
							for(var i=0; i<city.length;i++){
								str+="<option value="+city[i].value+">"+city[i].name+"</option>";
							}
							$(".input-group .shi").html('<option value="0">请选择</option>'+str);
//							市回显
							$(".input-group .shi").val(arrProvice[2]);
					
				    	})
//				    	区先获取数据加载后回显
						arrProvice[2]
				    	requestService("/xczh/shop/area?parentId="+arrProvice[2],null, function(data) {
				    		var str;
							var city=data.resultObject;
							for(var i=0; i<city.length;i++){
								str+="<option value="+city[i].value+">"+city[i].name+"</option>";
							}
							$(".input-group .qu").html('<option value="0">请选择</option>'+str);
//							市回显
							$(".input-group .qu").val(provice.id);
//							传的值
				    		getQuId=provice.id;
				    	})

				    }else if(arrProvice.length==3){
				    	$(".sheng").removeClass("hide");
				    	$(".shi").removeClass("hide");
				    	$(".qu").addClass("hide");


						$(".input-group .sheng").val(arrProvice[1]);
				    	

//				    	市先获取数据加载后回显

				    	requestService("/xczh/shop/area?parentId="+arrProvice[1],null, function(data) {
				    		var str="";
							var city=data.resultObject;
							for(var i=0; i<city.length;i++){
								str+="<option value="+city[i].value+">"+city[i].name+"</option>";
							}
							$(".input-group .shi").html('<option value="0">请选择</option>'+str);
//							市回显
							$(".input-group .shi").val(provice.id);
	
				    		getCityId=provice.id;
				    	})
				    }else if(arrProvice.length==2){
				    	$(".sheng").removeClass("hide");
				    	$(".shi").addClass("hide");
				    	$(".qu").addClass("hide");
						$(".input-group .sheng").val(provice.id);
						getProviceId=provice.id;
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
//		var zipCode =$("#zip-code").val();
//		if(isBlank(zipCode)){
//			webToast("请填写邮编","middle",1500);
//			return false;
//		}
//		if (!(/^\d+$/.test(zipCode))) {
//			webToast("请输入正确的邮编","middle",1500);
//			return false;
//		}
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
		}else if($(".shi").val()=="请选择" && $(".shi").hasClass("hide")==false){
			webToast("请完善所在地区","middle",1500);
			return false;
		}else if($(".qu").hasClass("hide")==false && $(".qu").val()=="请选择"){
			webToast("请完善所在地区","middle",1500);
			return false;
		}else if($(".qu").hasClass("hide")==true && $(".shi").hasClass("hide")==false){
			areaId=getCityId;
		}else if($(".qu").hasClass("hide")==true && $(".shi").hasClass("hide")==true){
			areaId=getProviceId;
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
			zipCode : 000000,
			address:detailed_address,
			/*postalCode:postalCode,*/
			consignee:consignee,
			phone:phone,
			isDefault:isTrue
		}
	
	var addressId  = $("#address_id").val();
//		$(".person_prosperity").show();
//		$(".prosperity_cen_top").text("新增成功");
		
		var url_address = "/xczh/shop/receiver/add";
		if(isNotBlank(addressId)){
			urlparm.receiverId = addressId;
			url_address = "/xczh/shop/receiver/update";
//			$(".person_prosperity").show();
//			$(".prosperity_cen_top").text("修改成功");
		}
		/**
		 * 保存地址
		 */
		requestService(url_address, 
				urlparm, function(data) {
			if (data.success) {
				$(".person_prosperity").show();
				if (isNotBlank(addressId)) {
					$(".prosperity_cen_top").text("修改成功");
					setTimeout(function(){
						var type = getQueryString("type");
						location.href ='address.html?type='+ type+"&types=4";
//						history.go(-1);  
//						window.history.back();
					},1500)
				} else{
					$(".prosperity_cen_top").text("新增成功");
					setTimeout(function(){
						var type = getQueryString("type");
						location.href ='address.html?type='+ type+"&types=4";
//						history.go(-1);  
//						window.history.back();
					},1500)
				}
				
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
	
	
	
	