$(function(){
//	商品星星评价
	$('.select-impress img').each(function(index){  
        var star='/xcview/images/xing1.png';    //普通灰色星星图片的存储路径  
        var starRed='/xcview/images/xing.png';     //红色星星图片存储路径  
        var prompt=['非常差','差','一般','很好','非常好'];   //评价提示语  
        index = index%5;
        this.id=index;      //遍历img元素，设置单独的id
        $(this).on("mouseover click",function(){    //设置鼠标滑动和点击都会触发事件  
         console.log($(this))
           $(this).siblings('img').attr('src',star)
//         $('.select-impress img').attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
            $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图  
            $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图  
            $(this).siblings('span').text(prompt[this.id]);     //根据id的索引值作为数组的索引值  
        });
    }); 
//  删除图片
	$('.save-pic ul').on('click',"p",function(){
		var that=$(this)
		var liBtn=that.parent().parent().find("li");

			liBtn.each(function(){					
			if(liBtn.length>=7){
				liBtn.parent().siblings(".btn-upload").css('display','none')
			}else{
				liBtn.parent().siblings(".btn-upload").css('display','block')

			}
		})		
		that.parent().remove();
	});
})
