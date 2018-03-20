var my_impression1="";
var my_impression2="";
var my_impression3="";
var course_id ="";
var criticize_id = "";
var LecturerId="";
var commentCode ="";
$(function(){

    	//获取课程ID跳转相应页面页面
	//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
	var courseId = getQueryString('courseId');
    course_id = courseId;
    var Lecturer = getQueryString('LecturerId');
    LecturerId = Lecturer;
	refresh(1,10,'down');
});

//刷新评论列表
function refresh(pageNumber,pageSize,downOrUp){
    requestService("/xczh/criticize/getCriticizeList",{
        courseId : course_id,
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
        }else{
            $(".wrap_all_returned").append(template('wrap_people_comment',{items:data.resultObject.items}));
            mui("#refreshContainer").off();
            mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);

        }

        //判断是否是第一次评论
        $(".wrapAll_comment").html(template('id_show_xingxing',{items:data.resultObject.commentCode}));
        commentCode = data.resultObject.commentCode;
        //	回复弹窗
            mui("#refreshContainer").on('tap', '.btn_littleReturn', function (event) {
            //评论id
            criticize_id=this.id;
            $(".bg_userModal").show();
            $(".wrapLittle_comment").show();
            $("#littlt_return").focus()
        });
        $(".bg_userModal").on('click',function(){
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
        //判断浮层是否已选
            var list=document.getElementsByClassName("active_color");
            if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0||$('#comment_detailed').val()==""){
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
        //控制评论按钮颜色
            $('.my_impression1').click(function(){
                var list=document.getElementsByClassName("active_color");
                if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0||$('#comment_detailed').val()==""){
                    $(".report_btn").css("opacity","0.3");
                }else{
                    $(".report_btn").css("opacity","1");
                }
            })
            $('.my_impression2').click(function(){
                var list=document.getElementsByClassName("active_color");
                if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0||$('#comment_detailed').val()==""){
                    $(".report_btn").css("opacity","0.3");
                }else{
                    $(".report_btn").css("opacity","1");
                }
            })
            $('.my_impression3').click(function(){
                var list=document.getElementsByClassName("active_color");
                if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0||$('#comment_detailed').val()==""){
                    $(".report_btn").css("opacity","0.3");
                }else{
                    $(".report_btn").css("opacity","1");
                }
            })
            $('.select_lable').click(function(){
                var list=document.getElementsByClassName("active_color");
                if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0||$('#comment_detailed').val()==""){
                    $(".report_btn").css("opacity","0.3");
                }else{
                    $(".report_btn").css("opacity","1");
                }
            })
            $('#comment_detailed').keyup(function(){
                if(commentCode==1){
                    var list=document.getElementsByClassName("active_color");
                    if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0||$('#comment_detailed').val()==""){
                        $(".report_btn").css("opacity","0.3");
                    }else{
                        $(".report_btn").css("opacity","1");
                    }
                }else{
                    if($('#comment_detailed').val()==""){
                        $(".report_btn").css("opacity","0.3");
                    }else{
                        $(".report_btn").css("opacity","1");
                        
                    }
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
    //判断浮层是否已选，内容是否不为空
        var opacity = $(".report_btn").css("opacity");
        if(opacity!=1){
            return false;
        }
// 手机自带表情添加判断
    //正则表达式
//	 var reg = new RegExp("^[A-Za-z0-9\u4e00-\u9fa5]+$");
	 //判断输入框中有内容
	 /*if(!reg.test(comment_detailed))
	 {
		webToast("仅支持中文、英文、数字","middle",3000);*/
	 //输入非法字符，清空输入框
	 /*$("#comment_detailed").val("");
     $(".return_btn").css("opacity","0.3");
	 return false;
	 }*/
// 手机自带表情添加判断结束
    var arr=new Array();
    var list=document.getElementsByClassName("active_color");
    for (var i = 0; i < list.length; i++) {
        arr.push(list[i].value);
    }
    var str=arr.join(",");

    var overallLevel=0;
    if(my_impression1!=""){
        overallLevel = parseInt(my_impression1)+1
    }
    var deductiveLevel=0;
    if(my_impression2!=""){
        deductiveLevel = parseInt(my_impression2)+1
    }
    var contentLevel=0;
    if(my_impression3!=""){
        contentLevel = parseInt(my_impression3)+1
    }
    requestService("/xczh/criticize/saveCriticize",{
        overallLevel:overallLevel,
        deductiveLevel:deductiveLevel,
        contentLevel:contentLevel,
        criticizeLable:str,
        content:comment_detailed,
        courseId : course_id,
        userId:LecturerId
    },function(data) {
        //	课程名称/等级/评论
        if(data.success==true){
            webToast("评论成功","middle",1500);
            $(".wrapAll_comment").hide();
            $(".bg_modal").hide();
            document.getElementById("comment_detailed").value="";
            del();
            refresh(1,10,'down')
        }else{
            webToast("评论失败","middle",1500);
        }

    },false);
}

//回复评论
function replyComment() {
    var comment_detailed = $('#littlt_return').val();

    if(comment_detailed==""){
        //webToast("内容不能为空","middle",1500);
        return false;
    }
// 手机自带表情添加判断
    //正则表达式
//	 var reg = new RegExp("^[A-Za-z0-9\u4e00-\u9fa5]+$");
	 //判断输入框中有内容
	 /*if(!reg.test(comment_detailed))
	 {
		webToast("仅支持中文、英文、数字","middle",3000);*/
	 //输入非法字符，清空输入框
//	 $("#comment_detailed").val("");
//   $(".return_btn").css("opacity","0.3");
//	 return false;
//	 }
// 手机自带表情添加判断结束
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
            del();
            refresh(1,10,'down')
        }else {
            webToast("回复失败","middle",1500);
        }

    },false);
}

//点赞、取消点赞
function updatePraise(id,isPraise) {
    requestService("/xczh/criticize/updatePraise", {
        praise: isPraise,
        criticizeId: id
    }, function (data) {
        //	课程名称/等级/评论
    },false);
}

//清空评论状态
function del(){
    //星星
    var star='../images/xing1.png';
    $('.my_impression1 img').attr('src',star);
    //主播演绎
    var star1='../images/face0.png';
    $('.my_impression2 img').attr('src',star1);
    //节目内容
    var star2='../images/face0.png';
    $('.my_impression3 img').attr('src',star2);
    //很赞
    $(".select_lable li").removeClass("active_color");
    my_impression1="";
    my_impression2="";
    my_impression3=""

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
