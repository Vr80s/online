
$(function() {
//左右侧边栏mune功能
	$('#accordion > li').click(function(){
		    $('#right-content > div').addClass('hide');
			$('#right-content > div').eq($(this).index()).removeClass('hide');
	})
	$('#accordion > li >.submenu >li').click(function(e){
		$('#accordion > li >.submenu >li a').removeClass("mune-active")
		$(this).find("a").addClass("mune-active")
		e.stopPropagation();
		
		var index = $(this).parent().parent().index();
		$('#right-content > div:eq('+index+') > div').addClass('hide')
		$('#right-content > div:eq('+index+') > div').eq($(this).index()).removeClass('hide');
	})
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
	}

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();
//			判断若是有下拉子菜单,则给第一个菜单加点击
			if($(this).siblings(".submenu").find("li").length>0){
				$(this).siblings(".submenu").find("li").first().click();
				$next.slideToggle();
//				小箭头方向
//				$el.find('.submenu').not($next).parent().find("span").removeClass('glyphicon-triangle-bottom');			
//				$this.parent().find("span").toggleClass('glyphicon-triangle-bottom');
			}else{
				$el.find('.submenu').not($next).parent().find("span").removeClass('glyphicon-triangle-bottom');			
			}
			$this.parent().toggleClass('open');
			if (!e.data.multiple) {
				$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
//				子菜单加颜色
				$el.find('.submenu').not($next).slideUp().parent().find(".submenu").find("a").removeClass("mune-active")
				
			};
	}	
	var accordion = new Accordion($('#accordion'), false);

//--------------------------------------左右侧边栏mune功能结束--学习中心开始--------------------------------------------

//暂无记录背景图
var noDataImg= '<div class="no-data-img">'+
					'<img src="../../images/icon-nodata.png"/>'+
				'</div>'+
				'<p>暂无记录</p>';
				
//已购/结束课程/历史记录选项卡
$(".my-class-nav li").click(function(){
	$(".my-class-nav li").removeClass("class-active");
	$(this).addClass("class-active");
	$(".save-class").addClass("hide").eq($(this).index()).removeClass("hide");
})

//--------------------------------------学习中心结束--问答论坛开始--------------------------------------------

//我的提问  我的回答选项卡
$(".question-forum li").click(function(){
	$(".question-forum li").removeClass("mune-active");
	$(this).addClass("mune-active");
	$(".question-wrap").addClass("hide").eq($(this).index()).removeClass("hide");
})

//点击收起,隐藏则字体
//		我的提问
		$("#show-set").removeClass("hide")
//		我的回答
		$("#answer").removeClass("hide")
			var $dot5 = $('.dot5');
                $dot5.each(function () {
                    if ($(this).height() > 40) {
                        $(this).attr("data-txt", $(this).attr("data-text"));
                        $(this).height(40);
                        $(this).append('<span class="qq" style="margin-right:60px"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">显示全部</span><span class="closes">收起</span></a></span>');
                    }
                    var $dot4 = $(this);

                    function createDots() {
                        $dot4.dotdotdot({
                            after: 'span.qq'
                        });
                    }

                    function destroyDots() {
                        $dot4.trigger('destroy');
                    }

                    createDots();
                    $dot4.on(
                        'click',
                        'a.toggle',
                        function () {
                            $dot4.toggleClass('opened');

                            if ($dot4.hasClass('opened')) {
                                destroyDots();
                            } else {
                                createDots();
                            }
                            return false;
                        }
                    );
                });
		$("#show-set").addClass("hide")
		$("#answer").addClass("hide")
		


//回复点赞功能
	$(".reply-user-text .select-down").click(function(){
		if($(this).hasClass("select-active")){
			$(this).removeClass("select-active")
		}else{
			$(this).addClass("select-active")
			
		}
	})

//我的帖子/我的回复选项卡
	$(".forum-wrap li").click(function(){
		$(".forum-wrap li").removeClass("mune-active");
		$(this).addClass("mune-active");
		$(".reply-post-wrap").addClass("hide").eq($(this).index()).removeClass("hide");
	})

//--------------------------------------问答论坛结束    我的订单开始--------------------------------------------
//我的订单选项卡
	$(".order-box li").click(function(){
		$(".order-box li").removeClass("mune-active");
		$(this).addClass("mune-active");
		$(".all-order-wrap").addClass("hide").eq($(this).index()).removeClass("hide");
	})
	//全部订单/近三月订单筛选	
	$(".all-order").hover(function(){
		$(".all-change").stop().slideDown();
		$(".triangle-bottom").addClass("glyphicon-triangle-top")
	},function(){
		$(".all-change").stop().slideUp();
		$(".triangle-bottom").removeClass("glyphicon-triangle-top")	
	})
	$(".all-order span").click(function(){
		var spanText=$(this).text();
		$(".select-write").text(spanText);
	})

//-------------------------------------- 我的订单结束    我的钱包开始--------------------------------------------
//点击充值切换页面
$(".main-wallet-top button").click(function(){
	$(".wallet-trade").addClass("hide");
	$(".recharge-wrap").removeClass("hide");
//	给选择金额一个点击
	$(".select-pay-money li:first").click();
//	给充值方式一个点击
	$(".main-bottom ul li:first").click();
})
//点击我的钱包回到第一个页面
$("#wallet-btn").click(function(){
	if($(".wallet-trade").hasClass("hide")){
		$(".wallet-trade").removeClass("hide");
		$(".recharge-wrap").addClass("hide");	
	}
})
//选择充值金额
$(".select-pay-money li").click(function(){
	$(".select-pay-money li").removeClass("select-money");
	$(this).addClass("select-money");
})
//选择支付方式
	$(".main-bottom ul li").click(function(){
		$(".main-bottom ul li").removeClass("select-confirm").find("span").addClass("hide");
		$(this).addClass("select-confirm").find("span").removeClass("hide");
	})


//-------------------------------------- 我的钱包结束,我的资料开始--------------------------------------------

//男女单选框
	$(".data-mune label").click(function() {
		$(this).find(".radio-cover em").addClass("active").parent().parent().siblings().find(".radio-cover em").removeClass("active");
	})
	$("#data-btn").click(function(){
		$(".address-info").iProvincesSelect("init",null)	
	})



//-------------------------------------- 我的资料结束,消息开始--------------------------------------------


});