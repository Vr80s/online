var my_impression1="";
var my_impression2="";
var my_impression3="";
var userLecturerId ="";
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
	var courseId = getQueryString('userLecturerId');
    userLecturerId = courseId;

    refresh(num,10,'down');
});

//刷新评论列表
function refresh(pageNumber,pageSize,downOrUp){
    requestService("/xczh/criticize/getCriticizeList",{
        userId : userLecturerId,
        pageNumber:pageNumber,
        pageSize:pageSize
    },function(data) {

        //	判断是下拉刷新还是上拉加载
        if(downOrUp=='down'){
            //  	判断有无评论显示默认图片
            if(data.resultObject.items.length==0){
                $(".quie_pic").show()
            }else{
                $(".quie_pic").hide()
            }
            $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));
            mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
            mui('#refreshContainer').pullRefresh().refresh(true);
            mui("#refreshContainer").off();
        }else if(data.resultObject.items.length==0){
            mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
            mui("#refreshContainer").off();
        }else {
            $(".wrap_all_returned").append(template('wrap_people_comment',{items:data.resultObject.items}));
            mui("#refreshContainer").off();
            mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
        }
        //	回复弹窗

            mui("#refreshContainer").on('tap', '.btn_littleReturn', function (event) {
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
        mui("#refreshContainer").on('tap', '.btn_click_zan', function (event) {
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
        //评论内容为空时，按钮为灰色
        if($('#comment_detailed').val()==""){
            $(".report_btn").css("opacity","0.3");
        }else{
            $(".report_btn").css("opacity","1");
        }
        //回复内容为空时，按钮为灰色
        if($('#littlt_return').val()==""){
            $(".return_btn").css("opacity","0.3");
        }else{
            $(".return_btn").css("opacity","1");
        }
        //控制回复按钮颜色
        $('#comment_detailed').keyup(function(){
            if($('#comment_detailed').val()==""){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
        })
        //控制回复按钮颜色
        $('#littlt_return').keyup(function(){
            if($('#littlt_return').val()==""){
                $(".return_btn").css("opacity","0.3");
            }else{
                $(".return_btn").css("opacity","1");
            }
        })

    });
}
//评论
function reportComment() {
    var comment_detailed = $('#comment_detailed').val();
    if(comment_detailed==""){
        return false;
    }
     //正则表达式
//	 var reg = new RegExp("^[A-Za-z0-9\u4e00-\u9fa5]+$");	 
	 //判断输入框中有内容
	 /*if(!reg.test(comment_detailed))
	 {
		webToast("仅支持中文、英文、数字","middle",3000);*/
	 //输入非法字符，清空输入框
	 /*$("#comment_detailed").val("");
	 return false;
	 }*/
    requestService("/xczh/criticize/saveCriticize",{
        content:comment_detailed,
        userId : userLecturerId
    },function(data) {
        //	课程名称/等级/评论
        if(data.success==true){
            webToast("评论成功","middle",1500);
            $(".wrapAll_comment").hide();
            $(".bg_modal").hide();
            document.getElementById("comment_detailed").value="";
            refresh(1,10,'down')
        }else{
            webToast("评论失败","middle",1500);
        }
    });
}

//回复评论
function replyComment() {
    var comment_detailed = $('#littlt_return').val();
    if(comment_detailed==""){
        //webToast("内容不能为空","middle",1500);
        return
    }
        //正则表达式
//	 var reg = new RegExp("^[A-Za-z0-9\u4e00-\u9fa5]+$");
	 
	 //判断输入框中有内容
	 /*if(!reg.test(comment_detailed))
	 {
		webToast("仅支持中文、英文、数字","middle",3000);*/
	 //输入非法字符，清空输入框
	 /*$("#comment_detailed").val("");
	 return false;
	 }*/
    requestService("/xczh/criticize/saveReply",{

        content:comment_detailed,
        criticizeId : criticize_id
    },function(data) {
        //	课程名称/等级/评论
        if(data.success==true){
            webToast("回复成功","middle",1500);
            $(".bg_userModal").hide();
            $(".wrapLittle_comment").hide();
            document.getElementById("littlt_return").value="";
            refresh(1,10,'down')
        }else {
            webToast("回复失败","middle",1500);
        }
    });
}

//点赞、取消点赞
function updatePraise(id,isPraise) {
    requestService("/xczh/criticize/updatePraise", {
        praise: isPraise,
        criticizeId: id
    }, function (data) {
        //	课程名称/等级/评论
    });
}


/**
 * ************************************ 页面刷新下刷新事件
 * **************************************************
 */
var num = 1;

mui.init();
mui.init({
    pullRefresh: {
        container: '#refreshContainer',
        down: {
            callback: pulldownRefresh
        },
        up: {
            contentrefresh: '正在加载...',
            callback: pullupRefresh
        }
    }
});
/*mui('.mui-scroll-wrapper').scroll({
	 deceleration: 0.0005, //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
	 indicators: false //是否显示滚动条
});
*/
/**
 * 下拉刷新
 */
function pulldownRefresh() {
    num = 1;
    setTimeout(function() {
        refresh(num,10,'down');
        mui('#refreshContainer').pullRefresh().endPulldownToRefresh(); //refresh completed
    }, 500);
};
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
    num++;
    setTimeout(function() {
        refresh(num,10,'up');
    }, 500);
}


function on_cc_h5player_init(){
	var oV = document.getElementsByTagName('video')[0];
	oV.setAttribute("x5-playsinline","");
}