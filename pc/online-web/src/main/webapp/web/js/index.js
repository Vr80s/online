/**
 * Created by admin on 2016/7/27.
 */

/*学员故事*/
$(function () {
	function get_cookie(Name) {
	   var search = Name + "="//查询检索的值
	   var returnvalue = "";//返回值
	   if (document.cookie.length > 0) {
	     sd = document.cookie.indexOf(search);
	     if (sd!= -1) {
	        sd += search.length;
	        end = document.cookie.indexOf(";", sd);
	        if (end == -1)
	         end = document.cookie.length;
	         //unescape() 函数可对通过 escape() 编码的字符串进行解码。
	        returnvalue=unescape(document.cookie.substring(sd, end))
	      }
	   } 
	   return returnvalue;
	}
	addSelectedMenu();

    /*学员故事模板*/
    var studentStory =
        '{{if item.success==true}}' +
        '{{each item.resultObject}}' +
        '<li data-storyId="{{$value.id}}"><div class="rr">' + '<div class="student-img">' +
        '<img src="{{#changeImg($value.headImg)}}" alt=""/>' +
        '{{if $value.useOtherName == true}}' +
        '<p>{{$value.otherName}}</p>' +
        '{{else}}' +
        '<p>{{$value.name}}</p>' +
        '{{/if}}' +
        '</div>' +
        '<div class="student-info">' +
        '<p class="info_top">' +
        ' </p>' +
        '<p class="brief">' +
        '<span><em>“</em>{{$value.introduce}}<em>”</em></span>' +
        '</p>' +
        '</div>' +
        '</div></li>' +
        '{{/each}}' +
        '{{/if}}';
    /*学员故事模板*/
    RequestService("/studentStory/listByIndex", "GET", null, function (data) {
        var boxContent = $(".boxContent");
        boxContent.html(template.compile(studentStory)({item: data}));
        setTimeout(function () {
            jQuery(".slide-box").slide({
                mainCell: ".slideBox ul",
                autoPage: true,
                effect: "leftLoop",
                autoPlay: true,
                vis: 3,
                easing: "swing",
                interTime: '4000',
                delayTime: '300'
            });
            $(".boxContent li .rr").off().on("click", function () {
                var name = "storyDetail";
                var storyId = $(this).parent().attr("data-storyId");
                window.open(bath + '/web/storyDetail/' + storyId);
            })
        }, 8)
    },false);
});

//友情链接
/*友情链接模板*/
var friendLink =
    '<h3>友情链接</h3>' +
    '{{each friend}}' +
    '<a href={{$value.url}} target="_blank">{{$value.name}}</a>' +
    '{{/each}}';
/*友情链接模板*/

RequestService("/otherlink/getOtherLink", "GET", "", function (data) {
    $("#friendLink").html(template.compile(friendLink)({friend: data.resultObject}));
});

//测试环境只能点击ID为1
template.helper('href', function (num) {
    if (num != "") {
        return '' + bath + '/web/courseDetail/' + num;
    } else {
        return 'javascript:;';
    }
});

var strcourse =
    '{{each item}}' +
    '<div class="course clearfix">' +
    '{{if $value.isRecommend == true}}' +
    '<img style="position:absolute;width: 16%;top:-2px;left:-2px" src="/web/images/recommend2.png" ></img>'+
    '{{/if}}' +
    '{{#indexHref($value.description_show,$value.free,$value.id,$value.courseType,$value.type,$value.direct_id,null,$value.coursePwd)}}'+
    '{{#hasImg($value.smallImgPath)}}' +
    '{{#online($value.multimediaType)}}' +
    '<div class="detail">' +
    '<p class="title" data-text="{{$value.gradeName}}" title="{{$value.gradeName}}">{{$value.gradeName}}</p>' +
    '<p class="timeAndTeac">' +
    '<span>{{#timeChange($value.courseLength)}}</span><i>|</i>' +
    '<span>讲师：<span class="teacher">{{$value.name}}</span></span>' +
    '</p>' +
    '<p class="info clearfix">' +
    '<span>' +
    '{{if $value.free == true}}' +
    '{{if $value.coursePwd == 1}}' +
    '<span class="pricefree">加密</span>' +
    '{{else}}' +
    '<span class="pricefree">免费</span>' +
    '{{/if}}' +
    '{{else}}' +
    '<i>￥</i><span class="price">{{$value.currentPrice}}</span><del><i class="price1">￥</i>{{$value.originalCost}}</del>' +
    '{{/if}}' +
    '</span>' +
    '<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learnd_count==null?0:$value.learnd_count}}</span></span>' +
//    '<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learnd_count}}</span></span>' +
    '</p>' +
    '</div>' +
    '</a>' +
    '</div>' +
    '{{/each}}';

var strcourse_xxpxb =
	'{{each item}}' +
	'<div class="course xxpxb clearfix">' +
	'{{#indexHref($value.description_show,$value.free,$value.id,$value.courseType,$value.type,$value.direct_id,1)}}'+
	'{{#hasImg($value.smallImgPath)}}' +
//	'{{#online($value.courseType)}}' +
	'<div class="detail">' +
	'<p class="title" data-text="{{$value.gradeName}}" title="{{$value.gradeName}}">{{$value.gradeName}}</p>' +
	'<p class="timeAndTeac">' +
	'<span>讲师：<span class="teacher">{{$value.name}}</span></span>' +
	'</p>' +
	'<p class="timeAndTeac">' +
	'<span>地址：<span class="teacher">{{$value.address}}</span></span>' +
	'</p>' +
	'<p class="timeAndTeac">' +
	'<span>时间：<span class="teacher">{{$value.startTime}}-{{$value.endTime}}</span></span>' +
	'</p>' +
	'<p class="info clearfix">' +
	'<span>' +
	'</p>' +
	'</div>' +
	'</a>' +
	'</div>' +
	'{{/each}}';

var livingCourse='{{each items}}'+
    '<li>' +
    	'{{if $value.free==true}}'+
	    	'{{if $value.coursePwd==1}}'+
	    		'<a style="cursor:pointer"  data-url="/web/html/encryptOpenCourseDetailPage.html?id={{$value.id}}&direct_id={{$value.direct_id}}" target="_blank" >'+
    		'{{else}}'+
    			'<a style="cursor:pointer"  data-url="/web/html/freeOpenCourseDetailPage.html?id={{$value.id}}&direct_id={{$value.direct_id}}" target="_blank" >'+
			'{{/if}}'+
		'{{else}}'+
			'<a style="cursor:pointer"  data-url="/web/html/payOpenCourseDetailPage.html?id={{$value.id}}&direct_id={{$value.direct_id}}" target="_blank" >'+
		'{{/if}}'+
    '{{#hasImg($value.smallimg_path)}}' +
        '<div class="public-class-live-detail">'+
        '<div class="detailCourseInfo clearfix">'+
        '<div class="detailCourseName" title="{{$value.courseName}}">{{$value.courseName}}</div>'+
        '<div class="detailTeacher">讲师：{{$value.teacherName}}</div>'+
        '</div>'+
        '{{if $value.broadcastState==1}}'+
        '<div class="detailLiveInfo clearfix">'+
        '<div class="detailLiveDate">最近直播：{{$value.formatStartTime}}</div>'+
        '<div class="detailLiving zhiboStart">'+
        '<span class="enter-livingClass" href="/web/livepage/{{$value.id}}/{{$value.direct_id}}/null" target="_blank">进入教室</span>'+
        '<img src="/web/images/zhibo.gif" alt=""/>'+
        '<span class="living">直播中</span>'+
        '</div></div></div>'+
        '{{else}}'+
        '<div class="detailLiveInfo clearfix">'+
        '<div class="detailLiveDate">最近直播：{{$value.formatStartTime}}</div>'+
        '<div class="detailLiving">'+
        '<img src="/web/images/zhiboNoStart.png" alt=""/>'+
        '<span class="noStart">直播未开始</span>'+
        '{{/if}}'+
        '</div></div></div>'+
        '</a></li>'+
        '{{/each}}';


var liveTrailerTemplate='{{each items}}' +' <li>\n' +
	'<img src="web/images/yugao/082305_03.png" alt="" class="li_img" />'+
    '<span class="box">\n' +
//    '<span class="time"><strong></strong>&nbsp;{{$value.showFormatDateString}}</span>\n' +
    '{{#liveTrailer($value.broadcastState,$value.free,$value.coursePwd,$value.isSubscribe,$value.id,$value.direct_id,$value.smallimg_path,$value.courseName,$value.teacherName,$value.description,$value.showFormatDateString)}}' +
    '</span>\n' +
    '</li>'+
    '{{/each}}';

/*轮播图*/
$(function () {
    RequestService("/banner/getBannerList?type=2", "GET", "", function (data) {
        if (data.resultObject.length === 1) {
            $.each(data.resultObject, function (index, item) {
                $("#left,#right").css("display", "none");
                if (index === 0) {
                    var imgPath = item.imgPath;
                    $('#slider').append($('<li data-indexId='+data.resultObject[index].id+' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
                    $("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
                } else {
                    var imgPath = item.imgPath;
                    $('#slider').append($('<li data-indexId='+data.resultObject[index].id+' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
                    $("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
                }
            })
        } else if (data.resultObject.length > 1 && data.resultObject.length <= 6) {
            $.each(data.resultObject, function (index, item) {
                if (index === 0) {
                    var imgPath = item.imgPath;
                    $('#slider').append($('<li data-indexId='+data.resultObject[index].id+' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
                    $("#aImg" + index).css("background", "url('" + imgPath + "')  no-repeat top center");
                    $('#selector').append($('<span class="cur"></span>'));
                } else {
                    var imgPath = item.imgPath;
                    $('#slider').append($('<li data-indexId='+data.resultObject[index].id+' class="cur" style="display:none;" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
                    $("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
                    $('#selector').append($('<span></span>'));
                }
            });
        } else if (data.resultObject.length > 6) {
            $.each(data.resultObject.slice(0), function (index, item) {
                if (index === 0) {
                    var imgPath = item.imgPath;
                    $('#slider').append($('<li data-indexId='+data.resultObject[index].id+' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
                    $("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
                    $('#selector').append($('<span class="cur"></span>'));
                } else {
                    var imgPath = item.imgPath;
                    $('#slider').append($('<li data-indexId='+data.resultObject[index].id+' class="cur" style="display:none;" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
                    $("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
                    $('#selector').append($('<span></span>'));
                }
            });
        }
        init();
        $("#slider li").on("click",function(){
            var indexId=$(this).attr("data-indexId");
            RequestService("/banner/updateClickCount","POST",{id:indexId},function(){

            })
        })
    });
});

function init() {
    var $sliders = $('#slider li');
    var $selector = $('#selector');
    var $selectors = $('#selector span');
    var $left = $('#left');
    var $right = $('#right');
    var arg = $selector.width() / 2;
    $selector.css("left", "50%");
    $selector.css("marginLeft", -arg);
    //自动切换
    var step =0;

    function autoChange() {
        if (step === $sliders.length) {
            step = 0;
        };
        $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
        $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
        step++;
    }

    var timer = window.setInterval(autoChange, 2000);

    //点击圆圈切换
    $selector.on('click', function (e) {
        var $target = $(e.target);
        if ($target.get(0).tagName === 'SPAN') {
            window.clearInterval(timer);
            $target.addClass('cur').siblings().removeClass('cur');
            step = $target.index();
            $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
            timer = window.setInterval(autoChange, 2000);
        }
    });

    //点击左右按钮切换
    $left.on('click', function () {
        window.clearInterval(timer);
        step--;
        if (step < 0) {
            step = $sliders.length - 1;
            $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
            $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
        } else {
            $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
            $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
        }
        timer = window.setInterval(autoChange, 2000);
    })
    $right.on('click', function () {
        window.clearInterval(timer);
        step++;
        if (step > $sliders.length - 1) {
            step = 0;
            $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
            $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
        } else {
            $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
            $selectors.eq(step).addClass('cur').siblings().removeClass('cur');
        }
        timer = window.setInterval(autoChange, 2000);
    })
}

//获取一个直播
RequestService("/online/live/getLive","GET",{num:4},function(data){
	var url="";
    if(data.resultObject.free==true){
        if(data.resultObject.coursePwd==1){
            url="/web/html/encryptOpenCourseDetailPage.html?id="+data.resultObject.id+"&direct_id="+data.resultObject.direct_id+"";
        }else{
            url="/web/html/freeOpenCourseDetailPage.html?id="+data.resultObject.id+"&direct_id="+data.resultObject.direct_id+"";
        }
    }else{
    url="/web/html/payOpenCourseDetailPage.html?id="+data.resultObject.id+"&direct_id="+data.resultObject.direct_id+"";
    }

	$(".video_div").click(function(){
	    window.open(url);
	});

    $("#liveImg").attr("src",data.resultObject.smallimg_path);
    if(data.resultObject.learnd_count==null||data.resultObject.learnd_count==0){
        $(".video_div_bottom_bg2").html("暂无报名");
    }else{
        $("#liveLearndCount").html(data.resultObject.learnd_count);
    }
    //未开始
    if(data.resultObject.broadcastState==2){
        $("#liveStatus").html("直播未开始");
    }

});

function subscribeInit(){
	$(".dianwo").click(function(){
		var that = this;
		RequestService("/online/user/isAlive", "GET", null, function (data) {
            if(data.success==false){
            	 window.localStorage.myStudyCenter=null;
                $("#login").modal("show");
            }else{
		        $(".phone").css("border", "1px solid #2cb82c");
		        $(".phone").val("");
                $("#subscribeId").val($(that).attr("subscribeId"));
                $("#yyStart").html("直播将在 "+$(that).attr("startTime")+" 开始");
				$(".popover_order").css('display','block');
				$(".shouy_dl").css('display','block');
	            $("#tips").hide();
            }
        })
	});
	}

//直播预告
RequestService("/online/live/getLiveTrailer","GET",{num:4},function(data){
    $(".w_ul_ul").html(template.compile(liveTrailerTemplate)({
        items:data.resultObject
    }))
    subscribeInit();
});


//加载一、二级导航
//课程列表请求数据
firstAjax(1,"_zyxx");
// firstAjax(2,"_sxxy");
// firstAjax(3,"_jfyl");

function firstAjax(type,typeName) {
    RequestService("/menu/getAllMenu?type="+type, "GET", "", function (data) {
        var $container = $('#tabFirst'+typeName).empty();
        var $odiv = $('#tabSecond'+typeName).empty();

        $.each(data.resultObject, function (index, item) {
            if (index === 0) {
                $container.append('<li class="select" data-number="' + item.id + '" ><span>' + item.name + '</span></li>');
            } else if (index < 12) {
                $container.append('<li data-number="' + item.id + '"><span>' + item.name + '</span></li>');
            }
        })
     var param = {
 		pageSize: 8,
         pageNumber: 1,
         type : type,
         typeName : typeName
     };
     // secondAjax(0, 0, param);
        //给一级导航绑定单击事件
        $container.find("li").on('click', function (e) {
//      	$("html,body").scrollTop($("#main").offset().top);
            $(this).addClass('select').siblings().removeClass('select');
            var param = {
                pageSize: 8,
                pageNumber: 1,
                type : type,
                typeName : typeName
            }
            // secondAjax($(this).attr("data-number"), $(this).attr("data-type"), param);
            
            console.log($(this).attr("data-number"))
            menuId = $(this).attr("data-number");
            getData(menuId,1);
        })
    });
}

function secondAjax(i, a, param) {
//	console.info(param);
    RequestService("/course/getPageCourseByMenuId", "GET", {
        menuId: i ? i : 0,
        couseTypeId: a ? a : 0,
        pageNumber: param.pageNumber ? param.pageNumber : 1,
        pageSize: param.pageSize ? param.pageSize : 8,
        type: param.type ? param.type : 1
//        type : 1

    }, function (data) {
        $("#log"+param.typeName+" .pages").css({"display": "none", "text-align": "right"});
        if (data.resultObject.items.length == 0) {
            $('#content'+param.typeName).empty();
            $("#emptyTitle").css("display", "block");
        } else {
            $("#emptyTitle").css("display", "none");
            //课程列表
            if(a==4)str = strcourse_xxpxb;
            else str = strcourse;
            $("#content"+param.typeName).html(template.compile(str)({
                item: data.resultObject.items
            }));
            lazyCkeck();
            $(".searchPage").css("display","none");
            if (data.resultObject.totalPageCount > 1) { //分页判断
                $(".not-data").remove();
                $("#log"+param.typeName+" .pages").css({"display": "block", "text-align": "right"});
                $("#log"+param.typeName+" .pages .searchPage .allPage").text(data.resultObject.totalPageCount);
                if (data.resultObject.currentPage == 1&&data.resultObject.pageSize==4) {
                    $("#Pagination").pagination(data.resultObject.totalPageCount, {
                        callback: function (page) { //翻页功能
                        	console.log(page);
                            var pageParam = {
                                pageNumber: (page + 1),
                                pageSize: 4,
//      						pageNumber: 3,
        						type : 4,
        						typeName : "_xxpxb"
                            };
                            console.log(data);
                            console.log(pageParam.pageNumber);
                            secondAjax(i, 4, pageParam);
                        }
                    });
                }
                $(".view-content-notbodys").html("");
            } else if (data.resultObject.totalPageCount = 1 && data.resultObject.totalCount > 0) {
                $("#log"+param.typeName+" .pages").css({"display": "none", "text-align": "right"});
                $(".view-content-notbodys").html("");
            }
        }
        //给二级导航绑定单击事件
        $('#tabSecond').off().on('click', function (e) {
//      	$("html,body").scrollTop($("#main").offset().top);
            var $target = $(e.target);
            $($target).addClass('cur').siblings().removeClass('cur');
            if ($target.get(0).tagName == "LI") {
                $('#content'+param.typeName).empty();
                var $odiv = $('#content'+param.typeName);
                var a = $($target).index();
                var param = {
            		pageSize: 8,
                    pageNumber: 1,
                    type : type,
                    typeName : typeName
                }
                secondAjax($target.attr("data-number"), $target.attr("data-type"), param);
				
            } else {
                return false;
            }
        })

    });
}

function rTips(errorMessage){
//  $(".rTips").text(errorMessage);
    $(".rTips").css("display","block");
    $(".rTips-bg").css("display","block");
    
	$(".rTips span").click(function(){
    	$(".rTips-bg").css("display","none");
        $(".rTips").css("display","none");	
	})


}

function addSelectedMenu(){
	$(".home").addClass("select");
}

window.pageNum = 1;
window.menuId = 0;
getData(1,1);
function fenye(currentPage,numberOfPages,totalPages){
			if(numberOfPages>5){
				numberOfPages = 5;
			}
			//分页结构结合数据渲染部分
			$('.zhongyi .pagination').bootstrapPaginator({
	            /*通过参数配置功能*/
	            /*Bootstrap 是2.X 使用div元素，3.X使用ul元素*/
	            bootstrapMajorVersion:3,
	            /*显示小的分页按钮*/
	            size:'small',
	            /*当前页码*/
	            currentPage:currentPage,
	            /*显示多少个按钮*/
	            numberOfPages:numberOfPages,
	            /*一共有多少页*/
	            totalPages:totalPages,
	            /*绑定点击事件*/
	            onPageClicked:function (event, originalEvent, type,page) {
	                console.log(page)    
	                /*page  当前点击的页面*/
	                window.pageNumber = page;
	                //点击分页部分控制数据请求
	                getData(menuId,pageNumber)
	            }
	        });
		}
//
function getData(menuId,pageNumber){
			//请求消费记录数据
			RequestService("/course/getPageCourseByMenuId?menuId="+menuId+"&couseTypeId=0&pageNumber="+pageNumber+"&pageSize=8&type=1", "get", null, function(data) {
				console.log(data)
				if(data.resultObject.totalPageCount>1){
					$('.zhongyi .pagination').css({'display':'block'});
				}else{
					$('.zhongyi .pagination').css({'display':'none'});
				}
					//渲染到页面中
//					 $("#xfjl").html(template("list1",{item:data.resultObject.items}));
// 					 str = strcourse;
					  $("#content_zyxx").html(template.compile(strcourse)({
                		item: data.resultObject.items
            			}));
					//每次请求完数据就去渲染分页部分
					fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					
			})
		}

window.pageNum2 = 1;
getData2(pageNum2)
function fenye2(currentPage,numberOfPages,totalPages){
			if(numberOfPages>5){
				numberOfPages = 5;
			}
			//分页结构结合数据渲染部分
			$('.peixun .pagination').bootstrapPaginator({
	            /*通过参数配置功能*/
	            /*Bootstrap 是2.X 使用div元素，3.X使用ul元素*/
	            bootstrapMajorVersion:3,
	            /*显示小的分页按钮*/
	            size:'small',
	            /*当前页码*/
	            currentPage:currentPage,
	            /*显示多少个按钮*/
	            numberOfPages:numberOfPages,
	            /*一共有多少页*/
	            totalPages:totalPages,
	            /*绑定点击事件*/
	            onPageClicked:function (event, originalEvent, type,page) {
	                console.log(page)    
	                /*page  当前点击的页面*/
	                window.pageNumber2 = page;
	                //点击分页部分控制数据请求
	                getData2(pageNumber2)
	            }
	        });
		}
function getData2(pageNumber){
			//请求消费记录数据
			RequestService("/course/getPageCourseByMenuId?menuId=0&couseTypeId=4&pageNumber="+pageNumber+"&pageSize=4&type=4", "get", null, function(data) {
				console.log(data)
				if(data.resultObject.totalPageCount==0){
					$('.peixun .pagination').css({'display':'none'});
				}else{
					//渲染到页面中
//					 $("#xfjl").html(template("list1",{item:data.resultObject.items}));
					 str = strcourse_xxpxb;
					  $("#content_xxpxb").html(template.compile(str)({
                		item: data.resultObject.items
            			}));
					//每次请求完数据就去渲染分页部分
					fenye2(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					
				}
			})
		}