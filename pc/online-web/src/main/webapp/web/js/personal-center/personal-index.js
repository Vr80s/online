$(function() {

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
			}
			$this.parent().toggleClass('open');
			if (!e.data.multiple) {
				$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
				$el.find('.submenu').not($next).slideUp().parent().find(".submenu").find("a").removeClass("mune-active")
				
			};
	}	
	var accordion = new Accordion($('#accordion'), false);
});