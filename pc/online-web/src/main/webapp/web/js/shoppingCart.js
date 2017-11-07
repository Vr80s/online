$(function(){
	var shopping =
		'{{each items as shopping}}' + 
		'<div class="shoppingList">' +
			'<ul>' +
			'{{#shopClick(shopping)}}'+
//				'<li class="w485">' +
////				'<li class="w485" {{#shopClick(shopping.course_id)}}>' +
//					'<div class="aloneYes">' +
//						'<div class="radio-cover" data-money="{{shopping.currentPrice}}">' +
//							'<em data-id="{{shopping.id}}" data-courseId="{{shopping.course_id}}"></em>' +
//						'</div>' +
//						'<input type="radio" style="display:none;"/>' +
//					'</div>' +
//					'<img src="{{shopping.smallImgPath}}"/>' +
//					'<p class="floatLeft">{{shopping.courseName}}</p>' +
//				'</li>' +
				'<li class="w255">' +
					'<p class="tcetner">1年</p>' +
				'</li>' +
				'<li class="w270">' +
					'<p class="tcetner"><span class="colorRed" data-money="{{shopping.currentPrice}}">￥<span>{{shopping.currentPrice}}</span></span></p>' +
				'</li>' +
				'<li class="removeLi">' +
					'<img src="../images/remove2.png" alt="" class="removeShopping" data-id="{{shopping.id}}"/>' +
				'</li>' +
			'</ul>' +
		'</div></a>' +
		
		'{{/each}}';
		
		var removeCourse = 
		'<div class="mask"></div>' +
		'<div class="del-sure shoppinggo">' +
		'<div class="del-header"><i class="iconfont icon-guanbi qxCloser"></i></div>' +
		'<p class="qxIntro"><i class="iconfont icon-tanhao"></i><span>你确定要删除该课程?</span></p>' +
		'<div class="qxBtn">' +
		'<span class="qxQuit" id="qxQuit">取消</span>' +
		'<span class="qxSure" id="qxSure">确定</span>' +
		'</div>' +
		'</div>' +
		'<div class="del-sure nullshopping">' +
		'<div class="del-header"><i class="iconfont icon-guanbi qxCloser"></i></div>' +
		'<p class="qxIntro"><i class="iconfont icon-tanhao"></i><span>请选择您要购买的课程</span></p>' +
		'<div class="qxBtn">' +
		'<span class="qxSure" id="qxSureNull" style="float:inherit;margin:0 auto;">确定</span>' +
		'</div>';
		var noBody = "<div class='noBody'><img src='../images/personcenter/nonotes.png'><p>暂无数据哦</p></div>";
	getShoppingList();
	$("body").append(template.compile(removeCourse));
	var money = 0;
	function getShoppingList(){
		RequestService("/shoppingCart/lists", "get", '', function(data) {
			for(var i in data.resultObject){
				if(data.resultObject[i].smallImgPath!=null && data.resultObject[i].smallImgPath!=""){
					data.resultObject[i].smallImgPath = data.resultObject[i].smallImgPath.split("dxg")[0];
				}
			}
			if(data.resultObject.length == 0){
				$(".shoppingTableTitle").hide();
				$(".shoppingBody").html(template.compile(noBody));
				$(".shoppingCart").css("min-height","480px");
				$(".numenber").css("display","none");
			}else{
				$(".shoppingBody").html(template.compile(shopping)({
					items: data.resultObject
				}));
				$(".numenber").css("display","inline-block");
				$(".shoppingFooter").css("display","block");
			}
			$("#shoppingNumber").text(data.resultObject.length);
			$(".qxCloser").click(function(){
				$(".qxSure").attr("data-id","");
				$(".mask").css("display","none");
				$(".del-sure").css("display","none");
			});
			
			//单选
			$(".aloneYes .radio-cover").on("click",function(){
				if($(this).find("em").hasClass("active") == true){
					$(this).find("em").removeClass("active");
					$(".allYes em").removeClass("active");
					money = money*1-$(this).attr("data-money")*1; 
					$(".moneyNum span").attr("data-money",money).text(money.toFixed(2));
					if($("em").hasClass("active") == true){
						$(".removeAllLi").css("display","inline-block");
						$(".goMoneyBtn").addClass("clickColor");
					}else{
						$(".removeAllLi").css("display","none");
						$(".goMoneyBtn").removeClass("clickColor");
					}
					$(".allYes em").removeClass("active");
				}else{
					$(this).find("em").addClass("active");
					$(".removeAllLi").css("display","inline-block");
					money = money*1+$(this).attr("data-money")*1;
					$(".moneyNum span").attr("data-money",money).text(money.toFixed(2));
					
					$(".goMoneyBtn").addClass("clickColor");
					var allString = false;
					$(".shoppingBody em").each(function(){
						if($(this).hasClass("active") == false){
							allString = false;
							return false;
						}else{
							allString = true;
						}
					})
					if(allString == true){
						$(".allYes em").addClass("active");
					}else{
						$(".allYes em").removeClass("active");
					}
				}
			});
			//删除课程
			$(".removeShopping").click(function(){
				$(".mask").css("display","inline-block");
				$(".shoppinggo").css("display","inline-block");
				$(".qxIntro span").text("你确定要删除该课程?");
				$("#qxSure").attr("data-id",$(this).attr("data-id"));
				//取消删除
				$(".qxQuit").click(function(){
					$("#qxSure").attr("data-id","");
					$(".mask").css("display","none");
					$(".shoppinggo").css("display","none");
				});
				//确定单个删除
				$("#qxSure").click(function(){
					RequestService("/shoppingCart/delete", "post", {
						idStrs:$(this).attr("data-id")
					}, function(data) {
						if(data.success == true){
							$(".mask").css("display","none");
							$(".shoppinggo").css("display","none");
							window.location.reload();
						}
					});
				});
			});
			
			$(".removeAllLi").click(function(){
				if($(".aloneYes em").hasClass("active") == true){
					$(".mask").css("display","inline-block");
					$(".shoppinggo").css("display","inline-block");
					$(".qxIntro span").text("你确定要删除所选课程?");
					var removAllList = [];
					$(".aloneYes em").each(function(){
						if($(this).hasClass("active") == true){
							removAllList.push($(this).attr("data-id"));
						}
					});
					$("#qxSure").attr("data-id",$.makeArray(removAllList));
					$("#qxSure").click(function(){
						RequestService("/shoppingCart/delete", "post", {
							idStrs:$(this).attr("data-id")
						}, function(data) {
							if(data.success == true){
								$(".mask").css("display","none");
								$(".shoppinggo").css("display","none");
								window.location.reload();
							}
						});
					});
					$(".qxQuit").click(function(){
						$("#qxSure").attr("data-id","");
						$(".mask").css("display","none");
						$(".shoppinggo").css("display","none");
					});
				}
				
			});
		})
	}
	
	
	//全选按钮
	$(".allYes").off().on("click",function() {
		if($(this).find("em").hasClass("active") == true){
			$(".allYes").find("em").removeClass("active");
			$(".shoppingBody em").removeClass("active");
			$(".moneyNum span").attr("data-money","0").text("0.00");
			money = 0;
			$(".removeAllLi").css("display","none");
			$(".goMoneyBtn").removeClass("clickColor");
			
		}else{
			$(".allYes").find("em").addClass("active");
			$(".shoppingBody em").addClass("active");
			money = 0;
			$(".colorRed").each(function(){
				money = money*1+$(this).attr("data-money")*1;
			});
			$(".moneyNum span").attr("data-money",money).text(money.toFixed(2));
			$(".removeAllLi").css("display","inline-block");
			$(".goMoneyBtn").addClass("clickColor");
		}
	});
	//结算按钮
	$(".goMoneyBtn").click(function(){
		var shoppingArr = [];
		if($(".shoppingBody em").hasClass("active") == true){
			$(".aloneYes .active").each(function(){
				shoppingArr.push($(this).attr("data-courseId"));
			});
			shoppingArr = $.makeArray(shoppingArr);
			window.location.href = "order.html?courseId=" + shoppingArr;
		}else{
			
		}
		
	});
	addSelectedMenu();
})

function addSelectedMenu(){
	$(".shoppingCar").css("color","#2cb82c");
}

//购物车列表点击事件
template.helper('shopClick', function (shopping) {
	var a = "";
	if(shopping.onlineCourse == 1){
		a = '<a style="cursor:pointer;color=#333;display: block;"  href="/web/html/payRealCourseDetailPage.html?id='+shopping.courseId+'"  target="_blank">';
	}else if(shopping.type == 1){
		a = '<a style="cursor:pointer;color=#333;display: block;" href="/web/html/payOpenCourseDetailPage.html?id='+shopping.courseId+'&direct_id='+shopping.direct_id+'"  target="_blank">';
	}else{
		a = '<a style="cursor:pointer;color=#333;display: block;" href="/web/html/payCourseDetailPage.html?id='+shopping.courseId+'&courseType='+shopping.course_type+'&free=0" target="_blank">';
	}
    console.info(a);
	return '<li class="w485">' +
		'<div class="aloneYes">' +
			'<div class="radio-cover" data-money="'+shopping.currentPrice+'">' +
				'<em data-id="'+shopping.id+'" data-courseId="'+shopping.course_id+'"></em>' +
			'</div>' +
			'<input type="radio" style="display:none;"/>' +
		'</div>' +
		a+'<img src="'+shopping.smallImgPath+'"/></a>' +
		'<p class="floatLeft" style="float: left;">'+shopping.courseName+'</p>' +
	'</li>';
});