$(function(){
	
	//啦啦啦啦，我是卖报的小画家
	//这个伟大的type我在这里可以用。啦啦啦啦啦啦
	
	//active-footer
	//alert(watchState);
	
	
	var index = 0;
	
	//选集显示
	if(watchState == 1 || watchState == 2){ //
		$(".buy_tab").removeClass("hide");
		$(".no_buy_tab").remove();
		if(type == "info"){
			$(".wrap-sidebar ul li").eq(1).addClass("active-footer");
			index =1;
		}
	//大纲显示啦	
	}else if(watchState == 0){
		$(".no_buy_tab").removeClass("hide");
		$(".buy_tab").remove();
		if(type == "info"){
			$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
		}
	}
	
	//type对应显示
	
	//outline  comment    info   aq    selection
	if(type == "selection"){
		$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
	}else if(type == "outline"){
		$(".wrap-sidebar ul li").eq(1).addClass("active-footer");
		index =1;
	}else if(type == "comment"){
		$(".wrap-sidebar ul li").eq(2).addClass("active-footer");
		index =2;
	}else if(type == "aq"){
		$(".wrap-sidebar ul li").eq(3).addClass("active-footer");
		index =3;
	}
	
	$(".sidebar-content").addClass("hide").eq(index).removeClass("hide")
	
//	详情/评价/常见问题	选项卡
	$(".wrap-sidebar ul li").click(function(){
		$(".wrap-sidebar ul li").removeClass("active-footer");
		$(this).addClass("active-footer");
		$(".sidebar-content").addClass("hide").eq($(this).index()).removeClass("hide")
	})	
//	点击我要评价
	$(".want-evaluate").click(function(){
		$(".bg-modal").removeClass("hide");
		$(".wrap-modal").removeClass("hide");
	});
	$(".close-impression").click(function(){
		$(".bg-modal").addClass("hide");
		$(".wrap-modal").addClass("hide");
	});
//	回复评论和点赞按钮加颜色
	$(".operation-reply li").click(function(){		
		if($(this).hasClass("selected")){
			$(this).removeClass("selected");
		}else{
			$(this).addClass("selected");
		}
	})
//	点击回复图标出现输入框
	$(".reply-icon").click(function(){
		if($(this).hasClass("selected")){
			$(this).parents().siblings(".wrap-input").removeClass("hide");
		}else{
			$(this).parents().siblings(".wrap-input").addClass("hide");
		}
	})
//星星五星好评
	$('.impression-star img').each(function(index){
        var star='../../images/star-dim.png';    //普通灰色星星图片的存储路径  
        var starRed='../../images/star-light.png';     //红色星星图片存储路径  
        var prompt=['1分','2分','3分','4分','5分'];   //评价提示语  
        this.id=index;      //遍历img元素，设置单独的id  
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
            $('.impression-star img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
        });
    });
//节目内容
	$('.impression-face img').each(function(index){
        var star='../../images/gs.png';    //普通灰色星星图片的存储路径  
        var starRed='../../images/rs.png';     //红色星星图片存储路径  
        var prompt=['一般','一般','好','好','很好'];   //评价提示语  
        this.id=index;      //遍历img元素，设置单独的id  
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
            $('.impression-face img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
        });
    });
//主播演绎
	$('.impression-show img').each(function(index){
	    var star='../../images/gs.png';    //普通灰色星星图片的存储路径  
	    var starRed='../../images/rs.png';     //红色星星图片存储路径  
	    var prompt=['一般','一般','好','好','很好'];   //评价提示语  
	    this.id=index;      //遍历img元素，设置单独的id  
	    $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
	        $('.impression-show img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
	        $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
	        $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
	        $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值
	    });
	});
//  很赞,干货很多
	$(".impression-setlist li").click(function(){
		if($(this).hasClass("impression-active")){
			$(this).removeClass("impression-active");		
		}else{
			$(this).addClass("impression-active");					
		}
	})
//	点击发表评论
	$(".submission").click(function(){
		$(".bg-modal").addClass("hide");
		$(".wrap-modal").addClass("hide");
	})
})
