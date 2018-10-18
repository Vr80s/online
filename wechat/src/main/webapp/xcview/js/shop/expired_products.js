
$(function () {

    $('.message').on('click', function () {
        jqtoast('客服休息中,稍后报道~');
    })
	
});
initRecommendProduct();

function initRecommendProduct() {
    requestGetService("/xczh/shop/goods/list", {
        'pageNumber': 1,
        'pageSize': 4,
        'orderType': 'RECOMMEND_DESC'
    }, function (data) {
        if (data.success) {
            $('#shop_recommend_product_ul').html(template('shop_recommend_product_tmpl',{resultObject: data.resultObject} ));
            $(".list li").click(function () {
                var id = $(this).attr("data-id");
                window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
            })
            
            $(".list li").each(function(){
			    var d = $(this).find("span").html();
				if(/^\d+$/.test(d)){
				d = d + ".00";
				}else if(/^(\d+\.)(\d+)$/.test(d)){
					var i = RegExp.$1;
					var t = RegExp.$2;
					if(t.length == 1)
					d = d + "0";
					else if(t.length > 2)
					d = i + t.substring(0,2);
				}
				//	alert(v);
				$(this).find("span").html(d);
			});
            
            
        }
    });
}


// 点击分享按钮
$('.share_class').click(function () {
    $('.weixin_ceng').show();
});

//点击分享提示背景色
$('.weixin_img').click(function () {
    $('.weixin_ceng').hide();
});




