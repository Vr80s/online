$(function(){
	//获取url中参数值的方法
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return (r[2]); 
    }
    return null;
	};

	var productId=getQueryString("productId"); //10152
	 RequestService("/xczh/shop/good/detail", "GET", {
	 	"productId":productId
	 } , function (data) {
     	if (data.success==true) {
 			var obj=data.resultObject;
			var bannerData=obj.productImages;
			var anchorsData=obj.posts;
			var evaluateData=obj.reviewvs;
	//  轮播图
	 		
			if(bannerData.length==0){
				$(".banner-wrap").addClass("hide");
			}else{
//				轮播图个数
				for(var i=0;i<bannerData.length; i++){
	                if(i == 0){
	                    $(".banner-indicators").append("<li data-target='#carousel-example-generic' data-slide-to='0'  class='active'></li>")
	                }else{
	                    $(".banner-indicators").append("<li data-target='#carousel-example-generic' data-slide-to='"+i+"'></li>")
	                }
	            }
//				轮播图张数
				for(var i=0; i<bannerData.length;i++){
				  	if(i == 0) {
                        $(".banner-carousel").append('<div class="item active">'+
			    		'<img src="'+bannerData[i].source+'" alt="广告图">'+
			  			'</div>'
			  			);
                    } else {
                        $(".banner-carousel").append('<div class="item">'+
			    		'<img src="'+bannerData[i].source+'" alt="广告图">'+
			  			'</div>'
			  			);
                   }

				}
			}
//		商品名/价格/已售
			$(".shop-detail-wrap").html(template("detail-template",{item:obj}))
//		医师推荐
			if(anchorsData.length== 0 || anchorsData== ""){
				$(".physician-main").addClass("hide");
			}else{
				$("#physician-main").html(template("physician-template",{item:obj.posts[0]}))
			}
//		评价
			if( evaluateData== null || evaluateData==""){
				$(".ware-evaluate").addClass("hide");
			}else{
				$(".ware-evaluate").html(template("evaluate-template",{item:obj.reviewvs[0]}))
			}
//		商品详情
			if(obj.introduction==null || obj.introduction == ""){	
				$(".ware-detail").addClass("hide");
			}else{
				$(".ware-detail-img").html(obj.introduction);				
			}
     	} else{
     		showTip("errorMessage")
     	}
	 })
	 
	 
//	 生成二维码
	var linkUrl="/xczh/shop/detail/url/"+productId
	$(".wechat-data").qrcode({
		render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
		text : linkUrl,    //扫描了二维码后的内容显示,在这里也可以直接填一个网址，扫描二维码后
		width : "158",               //二维码的宽度
		height : "158",              //二维码的高度
		background : "#ffffff",       //二维码的后景色
		foreground : "#000000",        //二维码的前景色
		src: '/web/images/yrx.png'             //二维码中间的图片
	});
})
