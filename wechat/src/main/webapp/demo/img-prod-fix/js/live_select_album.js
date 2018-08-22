/**
 *  医师页面默认到那个tab使用
 *   当到此页面时，默认到动态
 *   全部/直播间/师承/介绍
 *   li_datal/li_course/li_evaluate//li_prose_origin
 */
sessionStorage.setItem("physiciansPage","")
sessionStorage.setItem("li_data","");

var my_impression1="";
var my_impression2="";
var my_impression3="";
var course_id ="";
var criticize_id = "";
var LecturerId="";
var commentCode ="";

var select_jump_id="";
var select_directId="";
var select_collectionId="";
var name_title="";

//分享的信息
var gradeName = "";
var smallImgPath ="";
var description ="";
$(function(){
	function stripHTML(str){
	var reTag = /<(?:.|\s)*?>/g;
	return str.replace(reTag,"");
}
        	//获取课程ID跳转相应页面页面
	//引入comment.j后调用方法获取ID，course_id为html里的a链接后面的ID
	var courseId = getQueryString('course_id');
    course_id = courseId;
	//传ID courseId为接口的课程ID
	requestService("/xczh/course/liveDetails",{
		courseId : courseId	
	},function(data) {
			//分享的信息展示
		gradeName = data.resultObject.gradeName;
		smallImgPath = data.resultObject.smallImgPath;
		if(data.resultObject.description==null || data.resultObject.description==''){
			
		}else{
			description = data.resultObject.description.stripHTML();
		}
	//详情页的banner
	var school_img = document.createElement("img");
	school_img.src = data.resultObject.smallImgPath + '?imageView2/2/w/750';
	$(".play_video").append(school_img);
        //获取讲师id
        LecturerId=data.resultObject.userLecturerId;
	//	课程名称/等级/评价
        $("#speak_people").html(template('data_people',data.resultObject));
		$(".data_name").html(template('data_name',data.resultObject));
       	$(".all_returned_num span").html(data.resultObject.criticizeCount);
       	name_title=$(".speak_title").text()
		
	//	直播时间/主播名字
		// $("#wrap_playTime").html(template('data_name',data.resultObject));

	//	简介/内容
		if(data.resultObject.description == null || data.resultObject.description == ''){
			$(".no_data").show();
			$(".btn").hide()
			$(".zhezhao").hide()
		}else{
			$(".wrap .car_p1").html(data.resultObject.description)
		}
	//	主讲人
		if(data.resultObject.lecturerDescription == null || data.resultObject.lecturerDescription == ''){
			$(".no_data1").show();
			$(".btn1").hide();
			$(".zhezhao1").hide();
		}else{
			$(".wrap1 .car_p2").html(data.resultObject.lecturerDescription)
		}
				//判断简介的字长度
		var h2=$(".wrap").height();
		if(h2>200){
			$(".zhezhao").hide()
			$(".btn").show()
			$(".line_xian").hide()
//			$(".wrap").css({"height":"2rem","overflow":"hidden"})
		}else{
			$(".zhezhao").hide()
			$(".btn").hide()
		}
				
		var h=$(".wrap1").height();
		if(h>200){
			$(".zhezhao1").hide()
			$(".btn1").show()
//			$(".wrap1").css({"height":"2rem","overflow":"hidden"})
		}else{
			$(".zhezhao1").hide()
			$(".btn1").hide()
		}
		
		
	},false);
//详情以及选集选项卡
$(".my_details li").click(function(){
	$(".my_details li span").removeClass("spanActive");
	$(this).find("span").addClass("spanActive");
	$(".my_details_content").hide().eq($(this).index()).show();
    $(".hide_discuss").hide().eq($(this).index()).show();
	
})
//评价刷新
    refresh();


	
})
//判断普通浏览器时,去点微信分享  
    if(is_weixin()){
    	$(".share_to_one").show()
    }else{
    	$(".share_to_one").hide()
    }
//JQ预加载分界线

var courseId = getQueryString('course_id');
var course='';
var collections="";
	requestService("/xczh/course/getCoursesByCollectionId",{
		collectionId : courseId	
	},function(data) {

		if(data.success && data.resultObject.length>0){
			for(var i in data.resultObject){
                data.resultObject[i].no=getNo(i);
				select_id=data.resultObject[i].id;
				select_directId=data.resultObject[i].directId;
				data.resultObject[i].collectionId=courseId;
			}
			course=data.resultObject[0];   //获取专辑id第一个
            collections=data.resultObject;  //获取所有专辑
			$("#select_album").html(template('data_select_album',{items:data.resultObject}));		
		}

        //获取上次观看课程名
        var lookCourseName = localStorage.getItem("courseName"+courseId);
		if(lookCourseName!=null && lookCourseName!=""){
            $('.play_top_size').html('上次播放至：'+lookCourseName);
        }else {
            // $('.play_top_size').html('上次播放至：'+"01 "+course.gradeName);
            $('.play_top_size').html('上次播放至：'+course.gradeName);
        }
	})
//点击视频默认第一个视频ID	
	function btn_album_page(){                       
        var courseIndex = localStorage.getItem("course"+courseId);
        if (courseIndex==null||courseIndex =="") {
            courseIndex=0
        }  /*默认第一条专辑*/
        localStorage.setItem('course'+courseId, courseIndex);  /*取缓存列表*/
        var collection = collections[courseIndex];                                                                                                                             //判断跳转添加
		//location.replace("live_album.html?course_id="+collection.id+"&direct_id="+collection.directId+"&collection_id="+courseId+"&name_title="+name_title+"&index="+courseIndex+"&type=2");
		location.href = "live_album.html?course_id="+collection.id+"&direct_id="+collection.directId+"&collection_id="+courseId+"&name_title="+name_title+"&index="+courseIndex+"&type=2";
	}
//选集视频跳转
function jump_album_my(e,selectId,selectDirectId,courseName){
	//alert(e.target)
	var index = e.parentNode.value
    localStorage.setItem('course'+courseId, index);
    var sortNo=getNo(index);
	localStorage.setItem('courseName'+courseId, courseName);                                                                                                      //判断跳转添加
//	location.replace("live_album.html?course_id="+selectId+"&direct_id="+selectDirectId+"&collection_id="+courseId+"&name_title="+name_title+"&index="+index+"&type=2");
	location.href = "live_album.html?course_id="+selectId+"&direct_id="+selectDirectId+"&collection_id="+courseId+"&name_title="+name_title+"&index="+index+"&type=2";
}
function refresh(){
    requestService("/xczh/criticize/getCriticizeList",{
        courseId : course_id,
        pageNumber:1,
        pageSize:6
    },function(data) {
    	
    	if(data.success){
    		
    		
    		//  	判断有无评价显示默认图片
    		if(data.resultObject.items.length==0){
    			$(".quie_pic").show()
    		}else{
    			$(".quie_pic").hide()
    		}
    		//  	判断有无评价显示默认图片结束
            //	课程名称/等级/评价
            $(".wrap_all_returned").html(template('wrap_people_comment',{items:data.resultObject.items}));

           //判断是否是第一次评价
            $(".wrapAll_comment").html(template('id_show_xingxing',{items:data.resultObject.commentCode}));
            commentCode = data.resultObject.commentCode;
            //	回复弹窗
            $(".wrap_returned_btn .btn_littleReturn").click(function(){
                //评价id
                criticize_id=this.id;
                //跳转到评价列表页
                btn_allComment();
            });
            $(".bg_userModal").click(function(){
                $(".bg_userModal").hide();
                $(".wrapLittle_comment").hide();
            });
            //	评价弹窗
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
            $(".btn_click_zan").click(function(){
                //评价id
                criticize_id=$(this).attr("data-id");
                //跳转到评价列表页
                btn_allComment();
            });
            //判断浮层是否已选
            var list=document.getElementsByClassName("active_color");
            if(my_impression1==""||my_impression2==""||my_impression3==""||list.length<=0||$('#comment_detailed').val()==""){
                $(".report_btn").css("opacity","0.3");
            }else{
                $(".report_btn").css("opacity","1");
            }
            //控制评价按钮颜色
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
    		
    		
    	}

    });
}

function getNo(i){
        i++;
        if(i<10){
            return "0"+i;
        }
        return i;
    }
//评价
function reportComment() {
    //判断浮层是否已选
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

    var comment_detailed = $('#comment_detailed').val();
    if(comment_detailed==""){
        //webToast("请输入评价内容","middle",1500);
        return
    }
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
        userId:LecturerId,
        collectionId:course_id
    },function(data) {
        //	课程名称/等级/评价
        if(data.success==true){
            webToast("评价成功","middle",1500);
            //	直播时间/主播名字
            $(".wrapAll_comment").hide();
            $(".bg_modal").hide();
            document.getElementById("comment_detailed").value="";
            del();
            refresh();
            //评价数加1
            var criticizeCount = $('#criticizeCountId').text();
            $('#criticizeCountId').text(parseInt(criticizeCount)+1);
            var cc = $('#criticizeCount').text();
            $('#criticizeCount').text(parseInt(cc)+1);
        }else{
            webToast("评价失败","middle",1500);
        }
    });
}

//回复评价
function replyComment() {
    var comment_detailed = $('#littlt_return').val();
    if(comment_detailed==""){
//      webToast("内容不能为空","middle",1500);
        return
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
        criticizeId : criticize_id,
        collectionId:course_id
    },function(data) {
        if(data.success==true){
            webToast("回复成功","middle",1500);
            //	直播时间/主播名字
            $(".bg_userModal").hide();
            $(".wrapLittle_comment").hide();
            document.getElementById("littlt_return").value="";
            del();
            refresh();
            //评价数加1
            var criticizeCount = $('#criticizeCountId').text();
            $('#criticizeCountId').text(parseInt(criticizeCount)+1);
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
        //	课程名称/等级/评价
    });
}
//点击所有评价跳转
function btn_allComment(){
	
    window.location.href="all_comment.html?courseId="+course_id+"&LecturerId="+LecturerId+""+"&collection_id="+course_id;
}


//删除评价状态
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