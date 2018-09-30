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

	var productId=getQueryString("productId");
	 RequestService("/xczh/shop/good/detail", "GET", {
	 	"productId":10152
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
				var str="";
				for(var i=0; i<bannerData.length;i++){
					str+='<div class="item">'+
				    		'<img src="'+bannerData[i].source+'" alt="广告图">'+
				  		'</div>'
				}
				$(".banner-carousel").html(str)
			}
//		商品名/价格/已售
			$(".shop-detail-wrap").html(template("detail-template",{item:obj}))
//		医师推荐
			if(anchorsData.length==0){
				$(".physician-main").addClass("hide");
			}else{
				$("#physician-main").html(template("physician-template",{item:obj.posts[0]}))
			}
//		评价
			if(evaluateData.length==0){
				$(".ware-evaluate").addClass("hide");
			}else{
				$(".ware-evaluate").html(template("evaluate-template",{item:obj.reviewvs[0]}))
			}











     	} else{
     		showTip("errorMessage")
     	}
	 })
})
