
var my_impression1="";
var my_impression2="";
var my_impression3="";
var course_id ="";

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

            my_impression1=this.id;
            //my_impression1+=1;
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

            //parseInt(my_impression2)+1;
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
           // parseInt(my_impression3)+1;
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
//	if(data.resultObject.watchState==1){
//		$(".wrap_all_returned").css({"margin-bottom":"0"})
//	}
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
    requestService("/xczh/criticize/getCriticizeList",{
        courseId : courseId
    },function(data) {
        //	课程名称/等级/评论
        $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));
        //	直播时间/主播名字

        /*$(".wrap_all_returned").html(template.compile(relativeCourse)({
            items:data.resultObject.items
        }));*/


    });
})

function reportComment() {

    var arr=new Array();

    var list=document.getElementsByClassName("active_color");
    for (var i = 0; i < list.length; i++) {
        arr.push(list[i].value);
    }
    var str=arr.join(",");

    //var s = $('.active_color').val();
    var comment_detailed = $('#comment_detailed').val();
    alert(comment_detailed);
    alert(str);

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
//点击购买后的接口
var courseId = getQueryString('course_id');
function btn_buy(){
	requestService("/xczh/order/save",{
		courseId:courseId,
		orderFrom:2
	},function(data){

		window.location.href="purchase.html?courseId="+data.resultObject.orderId+"";
	});
	
}