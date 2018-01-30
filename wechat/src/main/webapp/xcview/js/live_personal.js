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
    //获取ID跳转相应页面页面
//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
	var userLecturerId = getQueryString('userLecturerId');

//传ID courseId为接口ID
requestService("/xczh/host/hostPageInfo",{
	lecturerId : '23908ae85dad4541ba7ecf53fc52aab2'
},function(data) {
//	直播头像/主播名字
	$(".personal_bg").html(template('personal_header',data.resultObject));
//<!--主播名字/粉丝数量-->
	$("#wrap_wrapPersonal").html(template('data_number',data.resultObject));
// 打开页面判断是否已关注
	if(data.resultObject.isFocus == 1){
		$(".add_follow").html(btn_follow);
		
	}else{
			$(".add_follow").html(not_follow);
	}		
			
//直播时间截取	
		data.resultObject.recentCourse.startTime= data.resultObject.recentCourse.startTime.substring(0,10); //截取日期
		$("#personal_status").html(template('data_status',data.resultObject.recentCourse));
//医师精彩致辞
	if(data.resultObject.lecturerInfo.video==''||data.resultObject.lecturerInfo.video==null){
		$("#wrap_vedio").hide()
	}else{
		$("#wrap_vedio").html(template('data_vedio',data.resultObject.lecturerInfo));
	
	}

//介绍
		if(data.resultObject.lecturerInfo.detail==''||data.resultObject.lecturerInfo.detail==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject.lecturerInfo.detail);
		}
//坐诊医馆及时间
		$("#sure_address").html(template('data_address',data.resultObject));
		







});

})
	
//	点击关注判断
	var btn_follow='<img src="../images/append1_icon.png"/>'+
					'<p class="aaa" style="margin-left: 0.6rem;">已关注</p>'	
	var not_follow='<img src="../images/append1_icon.png"/>'+
					'<p class="aaa"  style="margin-left: 0.6rem;">加关注</p>'	
	function my_follow(){
				var bbb = $(".aaa").text();
				var adfol=document.getElementById('add_follow')
				var followed = adfol.getAttribute("data-lecturerId");
				if(bbb=="已关注"){
					requestService("/xczh/myinfo/updateFocus",{
						lecturerId : followed,
						type:2
					},function(data){
							$(".add_follow").html(not_follow);
					})
				}else{			
					requestService("/xczh/myinfo/updateFocus",{
						lecturerId : followed,
						type:1
					},function(data){
							$(".add_follow").html(btn_follow);
					})
		
				}			
			}
