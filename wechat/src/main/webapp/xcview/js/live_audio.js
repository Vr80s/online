
var my_impression1="";
var my_impression2="";
var my_impression3="";
var course_id ="";
var criticize_id = "";
$(function(){

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

            my_impression1=this.id;
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
            my_impression2=this.id;

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
            my_impression3=this.id;
        });
    });
    	//获取课程ID跳转相应页面页面
	//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
	var courseId = getQueryString('course_id');
    course_id = courseId;
	//传ID courseId为接口的课程ID
	requestService("/xczh/course/details",{
		courseId : courseId	
	},function(data) {
		//	若是免费则输入框显现
	if(data.resultObject.watchState==1){
		$(".wrap_all_returned").css({"margin-bottom":"0"})
	}
	//	课程名称/等级/评论
		$("#speak_people").html(template('data_people',data.resultObject));
	//	直播时间/主播名字
		$("#wrap_playTime").html(template('data_name',data.resultObject));
	//	是否购买
		$("#sure_isBuy").html(template('data_isBuy',data.resultObject));
	//	简介/内容
		if(data.resultObject.description == null || data.resultObject.description == ''){
			$(".no_data").show();
			$(".btn").hide()
			$(".zhezhao").hide()
		}else{
			$(".wrap p").html(data.resultObject.description)
		}
	//	主讲人
		if(data.resultObject.lecturerDescription == null || data.resultObject.lecturerDescription == ''){
			$(".no_data1").show();
			$(".btn1").hide();
			$(".zhezhao1").hide();
		}else{
			$(".wrap1 p").html(data.resultObject.lecturerDescription)
		}
	});

    //传ID courseId为接口的课程ID，评论列表
    refresh();
})
//刷新评论列表
function refresh(){
    requestService("/xczh/criticize/getCriticizeList",{
        courseId : course_id
    },function(data) {
        //	课程名称/等级/评论
        $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));
        //	回复弹窗
        $(".wrap_returned_btn .btn_littleReturn").click(function(){
            //评论id
            criticize_id=this.id;
            $(".bg_userModal").show();
            $(".wrapLittle_comment").show();
            $("#littlt_return").focus()
        });
        $(".bg_userModal").click(function(){
            $(".bg_userModal").hide();
            $(".wrapLittle_comment").hide();
        });
        //点赞
        $(".btn_click_zan").click(function(){
            //评论id
            criticize_id=$(this).attr("data-id");
            var p = $(this).find('span').html();

            var src = $(this).find('img').attr('src');
            if(src.indexOf("zan001")>-1){
                $(this).find('img').attr('src','../images/zan01.png');

                $(this).find('span').html(parseInt(p)-1);
                updatePraise(criticize_id,false);
            }else{
                $(this).find('img').attr('src','../images/zan001.png');
                $(this).find('span').html(parseInt(p)+1);
                updatePraise(criticize_id,true);
            }
        });

    });
}
//评论
function reportComment() {

    var arr=new Array();

    var list=document.getElementsByClassName("active_color");
    for (var i = 0; i < list.length; i++) {
        arr.push(list[i].value);
    }
    var str=arr.join(",");

    //var s = $('.active_color').val();
    var comment_detailed = $('#comment_detailed').val();

    requestService("/xczh/criticize/saveCriticize",{
        overallLevel:parseInt(my_impression1)+1,
        contentLevel:parseInt(my_impression3)+1,
        deductiveLevel:parseInt(my_impression2)+1,
        criticizeLable:str,
        content:comment_detailed,
        courseId : course_id
    },function(data) {
        //	课程名称/等级/评论

        alert(data.resultObject);
        //	直播时间/主播名字
        //$("#wrap_playTime").html(template('data_name',data.resultObject));
        $(".wrapAll_comment").hide();

        requestService("/xczh/criticize/getCriticizeList",{
            courseId : course_id
        },function(data) {
            //	课程名称/等级/评论
            $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));

        });
    });
}
//回复评论
function replyComment() {
    var comment_detailed = $('#littlt_return').val();

    requestService("/xczh/criticize/saveReply",{

        content:comment_detailed,
        criticizeId : criticize_id
    },function(data) {
        //	课程名称/等级/评论

        alert(data.resultObject);
        //	直播时间/主播名字
        $(".bg_userModal").hide();
        $(".wrapLittle_comment").hide();
        document.getElementById("littlt_return").value="";
        refresh();
    });
}

//点赞、取消点赞
function updatePraise(id,isPraise) {

    requestService("/xczh/criticize/updatePraise",{
        praise:isPraise,
        criticizeId : id
    },function(data) {
        //	课程名称/等级/评论



    });
}