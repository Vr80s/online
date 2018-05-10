
$(function(){
//	分类
	var selectKind='<dd id="alldAdd">'+
						'<p class="wrap-border">'+
							'<span>分类 : </span>'+
							'<span class="select-text">筛选条件</span>'+
							'<span class="select-close" onclick="deleteKin()">x</span>'+
						'</p>'+
					'</dd>'		
	$("#select-kind dd").click(function () {
            var copyThisA = $(this).text();		
        	$(this).addClass("selected").siblings().removeClass("selected"); 
            if ($("#kindAdd").length > 0) {
                $("#kindAdd .select-text").html($(this).text());
            } else {
                $("#select-condition").append(selectKind);
                $("#alldAdd").attr("id","kindAdd");
                $("#kindAdd .select-text").html(copyThisA);
            }
    });  
//	类型  
	var selectStyle='<dd id="allAdd">'+
						'<p class="wrap-border">'+
							'<span>类型 : </span>'+
							'<span class="select-text">筛选条件</span>'+
							'<span class="select-close" onclick="deleteStyle()">x</span>'+
						'</p>'+
					'</dd>'	
	$("#select-style dd").click(function () {
        var copyThisA = $(this).text();		
    	$(this).addClass("selected").siblings().removeClass("selected"); 
        if ($("#styleAdd").length > 0) {
            $("#styleAdd .select-text").html($(this).text());
        } else {
            $("#select-condition").append(selectStyle);
            $("#allAdd").attr("id","styleAdd");
            $("#styleAdd .select-text").html(copyThisA)
        }
	}); 
//	状态  
	var selectStatus='<dd id="allAdd">'+
						'<p class="wrap-border">'+
							'<span>状态 : </span>'+
							'<span class="select-text">筛选条件</span>'+
							'<span class="select-close" onclick="deleteStatus()">x</span>'+
						'</p>'+
					'</dd>'	
	$("#select-status dd").click(function () {
        var copyThisA = $(this).text();		
    	$(this).addClass("selected").siblings().removeClass("selected"); 
        if ($("#statusAdd").length > 0) {
            $("#statusAdd .select-text").html($(this).text());
        } else {
            $("#select-condition").append(selectStatus);
            $("#allAdd").attr("id","statusAdd");
            $("#statusAdd .select-text").html(copyThisA)
        }
	});
//	收费  
	var selectPrice='<dd id="allAdd">'+
						'<p class="wrap-border">'+
							'<span>收费 : </span>'+
							'<span class="select-text">筛选条件</span>'+
							'<span class="select-close" onclick="deletePrice()">x</span>'+
						'</p>'+
					'</dd>'	
	$("#select-price dd").click(function () {
        var copyThisA = $(this).text();		
    	$(this).addClass("selected").siblings().removeClass("selected"); 
        if ($("#priceAdd").length > 0) {
            $("#priceAdd .select-text").html($(this).text());
        } else {
            $("#select-condition").append(selectPrice);
            $("#allAdd").attr("id","priceAdd");
            $("#priceAdd .select-text").html(copyThisA)
        }
	}); 
//	城市
	var selectAddress='<dd id="allAdd">'+
						'<p class="wrap-border">'+
							'<span>城市 : </span>'+
							'<span class="select-text">筛选条件</span>'+
							'<span class="select-close" onclick="deleteAddress()">x</span>'+
						'</p>'+
					'</dd>'	
	$("#select-address dd").click(function () {
        var copyThisA = $(this).text();		
    	$(this).addClass("selected").siblings().removeClass("selected"); 
        if ($("#addressAdd").length > 0) {
            $("#addressAdd .select-text").html($(this).text());
        } else {
            $("#select-condition").append(selectAddress);
            $("#allAdd").attr("id","addressAdd");
            $("#addressAdd .select-text").html(copyThisA)
        }
	}); 
	
//	综合排序,最新,人气价格	
	$(".wrap-tab li").click(function(){
		if($(this).hasClass("selected")){
			return;
		}else{
			$(".wrap-tab li").removeClass("selected").eq($(this).index()).addClass("selected");
			$(".tab-price").removeClass("selected");
		}

	});
	$(".tab-price").click(function(){
			$(".tab-price").addClass("selected")

	});
	
	
});
//删除分类选项
function deleteKin(){
	$("#kindAdd").remove();
	$("#select-kind dd").removeClass("selected");
}
//删除类型选项
function deleteStyle(){
	$("#styleAdd").remove();
	$("#select-style dd").removeClass("selected");
}
//删除状态选项
function deleteStatus(){
	$("#statusAdd").remove();
	$("#select-status dd").removeClass("selected");
}
//删除收费选项
function deletePrice(){
	$("#priceAdd").remove();
	$("#select-price dd").removeClass("selected");
}
//删除收费选项
function deleteAddress(){
	$("#addressAdd").remove();
	$("#select-address dd").removeClass("selected");
}