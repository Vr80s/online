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
           










});