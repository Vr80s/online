$(function(){
	 var orderNum = getQueryString("orderId");//201809141010104  一个订单 201809251011414多个订单
	 

//	评论订单列表
	   requestGetService("/xczh/shop/order/detail", {
	        sn: orderNum
	    }, function (data) {
	        if(data.success ){
	            var obj =  data.resultObject.orderItems;
	            $(".evaluate-wrap").html(template('evaluate-template',{items:obj}));
	        }
	        startEvaluate();
			var carera=new $.Pgater($(".btn-upload"),callBack);//上传照片传参
	    });
//	商品星星评价
	function startEvaluate(){
		$('.select-impress img').each(function(index){  
	        var star='/xcview/images/xing1.png';    //普通灰色星星图片的存储路径  
	        var starRed='/xcview/images/xing.png';     //红色星星图片存储路径  
	        var prompt=['非常差','差','一般','很好','非常好'];   //评价提示语  
	        index = index%5;
	        this.id=index;      //遍历img元素，设置单独的id
	        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
	           $(this).siblings('img').attr('src',star).removeClass("active");
	//         $('.select-impress img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
	            $(this).attr('src',starRed).addClass("active");        //设置鼠标当前所在图片为打星颜色图  
	            $(this).prevAll().attr('src',starRed).addClass("active");  //设置鼠标当前的前面星星图片为打星颜色图  
	            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值  
	        });
	    }); 
	//  删除图片
		$('.save-pic ul').on('click',"p",function(){
			var that=$(this)
			var liBtn=that.parent().parent().find("li");
				liBtn.each(function(){					
				if(liBtn.length>=7){
					liBtn.parent().siblings(".btn-upload").css('display','none')
				}else{
					liBtn.parent().siblings(".btn-upload").css('display','block')
	
				}
			})		
			that.parent().remove();
		}); 		
	}
 
//	点击发布评论
	var sellerStart, //卖家印象
		logisticsStart, //物流服务
		myReviewEntryList=[]; //评价的整体
		myScore,	//描述相符
		myOrderItemId,  //商品ID
		myContent,	//商品内容
		myImages=[]; 	//图片数组
	$(".news-wrap").click(function(){
		arrangeData()
		var data=({
			"postdata":{"logistics":logisticsStart,"seller":sellerStart,"reviewEntryList":myImages}
		})
		if(checkEvaluate()){
			console.log("通过啦")
		}

	})
//传参处理
	function arrangeData(){
		
	}
	
	
	
//	验证卖家服务、物流服务
	function checkEvaluate(){
		var sellerLength=$(".seller-service img");
		var logisticsLength=$(".logistics-service img");

		if(sellerLength.hasClass("active")){
			sellerStart=$(".seller-service .active").length;
		}else{
			webToast("请评价卖家服务","middle",1500);
			return false;
		}

		if(logisticsLength.hasClass("active")){
			logisticsStart=$(".logistics-service .active").length;
		}else{
			webToast("请评价物流服务","middle",1500);
			return false;
		}
		return true;
	}


	
})
