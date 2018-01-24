$(function(){
	//	回复弹窗
$(".wrap_returned_btn .btn_littleReturn").click(function(){
	$(".bg_userModal").show();
	$(".wrapLittle_comment").show();
	$("#littlt_return").focus()
});
$(".bg_userModal").click(function(){
	$(".bg_userModal").hide();
	$(".wrapLittle_comment").hide();
});
//	评论弹窗
	$(".wrap_input").on('click',function(){
		$(".bg_modal").show();
		$(".wrapAll_comment").show();
	})
	$(".bg_modal").on('click',function(){
		$(".bg_modal").hide();
		$(".wrapAll_comment").hide();
	})
//	标签选中变色
	 $(".select_lable li").click(function(){
  		 $(this).toggleClass("active_color"); 
 	 });
 	 //星星五星好评
    $('.my_impression1 img').each(function(index){  
        var star='../images/xing1.png';    //普通灰色星星图片的存储路径  
        var starRed='../images/xing.png';     //红色星星图片存储路径  
        var prompt=['1分','2分','3分','4分','5分'];   //评价提示语  
        this.id=index;      //遍历img元素，设置单独的id  
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
            $('.my_impression1 img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值  
        });
    });  
//主播演绎好评
    $('.my_impression2 img').each(function(index){  
        var star='../images/face0.png';    //普通灰色星星图片的存储路径  
        var starRed='../images/face1.png';     //红色星星图片存储路径  
        var prompt=['一般','一般','好','好','很好'];   //评价提示语  
        this.id=index;      //遍历img元素，设置单独的id  
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
            $('.my_impression2 img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值  
        });
    });
//节目内容好评
    $('.my_impression3 img').each(function(index){  
        var star='../images/face0.png';    //普通灰色星星图片的存储路径  
        var starRed='../images/face1.png';     //红色星星图片存储路径  
        var prompt=['一般','一般','好','好','很好'];   //评价提示语  
        this.id=index;      //遍历img元素，设置单独的id  
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
            $('.my_impression3 img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色  
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值  
        });
    });

})
