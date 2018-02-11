var mobile = "";
$(function(){
    //获取手机号
    mobile = localStorage.getItem("name");
    $("#mobileShow").html(mobile);

    
    /*
     * 获取用户绑定的第三方用户信息
     */
	requestService("/xczh/bind/userBindingInfo", null, function(data) {
		if (data.success) {
			var item = data.resultObject;
    
			$("#weixin_bind").attr("data-title",item.wxUnionId);   
			$("#weixin_bind").html(item.wxName);   
		}
	})	
    
	
	$("#weixin_bind").click(function(){
		
		var unionId = $(this).attr("data-title");
		if(stringnull(unionId)){
			
			//去调用 微信第三方登录
			
			
		}else{
			 if(confirm("确认解除绑定吗吗")){
					//调用解除绑定的方法
					requestService("/xczh/bind/removeBinding", {
						type:1,
						unionId:unionId
					}, function(data) {
						if (data.success) {
							var item = data.resultObject;
				    
							$("#weixin_bind").attr("data-title",item.wxUnionId);   
							$("#weixin_bind").html(item.wxName);   
						}
					})	
		     }else{
		    	 
			   alert("no")
			   return;
			 }
		}
	})
	

});

