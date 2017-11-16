/**
 * Created by fanwenqiang on 2016/11/2.
 */
$(function(){
    //各模板
    //头像判断
    template.helper("headImg",function(num){
    	if(num!= "/web/images/defaultHeadImg.jpg") {
			return num;
		} else {
			return  bath + num
		}
    })
	//目录列表模板
	//var videoMemu =
	//    '{{each items as m}}' +
	//    '<p class="menuList" style="overflow:hidden;"><span style="max-width:233px;float:left;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;">{{m.name}}</span>' +
	//    '<img src="../../web/images/video/video_detial_down.png" style="margin-left: 5px;margin-top:-4px;" class="hide xia"/>' +
	//    '<img src="../../web/images/video2/shang.png" style="margin-left: 5px;margin-top:-4px;" class="shang"/></p>' +
	//    '<div class="freeTable-list">' +
	//        '{{each m.videos as v}}' +
	//        '{{if v.isLearn == true}}' +
	//        '<div class="yesMoney" data-videoId="{{v.videoId}}" data-vId="{{v.id}}" data-chapterId="{{v.chapterId}}" data-sectionId="{{v.sectionId}}">' +
	//        '<p style="float:left;width: 211px;padding:0;margin:0;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;" title="{{v.videoName}}">{{v.videoName}}</p>' +
	//        '<span class="freeTable">试学</span>' +
	//        '<img src="../../web/images/video/icon_09.png" class="hide playing" />' +
	//        '<img style="position:absolute;right:40px;margin-top:5px" src="../../web/images/video2/shi1.png" class="hide playing"/></div>' +
	//        '{{else}}' +
	//        '<div class="overMoney" data-videoId="{{v.videoId}}" data-vId="{{v.id}}" data-chapterId="{{v.chapterId}}" data-sectionId="{{v.sectionId}}">' +
	//        '<p style="float:left;width: 211px;padding:0;margin:0;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;" title="{{v.videoName}}">{{v.videoName}}</p>' +
	//        '<img src="../../web/images/video/icon_09.png" class="hide playing" />' +
	//        '<img style="position:absolute;right:40px;margin-top:5px" src="../../web/images/video2/shi1.png" class="hide playing"/></div>' +
	//        '{{/if}}' +
	//        '{{/each}}' +
	//    '</div>' +
	//    '{{/each}}';

	//目录列表模板  康荣彩
	var videoMemu =
		'{{each items as m n}}' +//循环章
		'<p class="menuList" title="{{m.name}}" style="overflow:hidden;"><span style="max-width:188px;font-size:14px;float:left;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;">第{{n+1}}章{{m.name}}</span>' +
		'<img src="../../web/images/video/video_detial_down.png" style="margin-left: 10px;margin-top:-4px;" class=" xia"/>' +
		'<img src="../../web/images/video2/shang.png" style="margin-left: 10px;margin-top:-4px;" class="shang hide"/>' +
		'{{if m.isLearn==true}}' +
		'<img  style="width:12px;height:12px;margin-left:10px" src="../../web/images/video2/shi2.png" /></div>' +
		'{{/if}}' +
		'</p>' +
		'<div class="freeTable-list hide">' +
		'{{each m.chapterSons as j ch}}' +//寻环节
		'<div  data-vId="{{j.id}}">' +
		'<p style="float:left;width: 200px;padding:0;margin:0;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;" title="{{j.name}}">第{{ch+1}}节{{j.name}}</p></div>' +
		'{{each j.chapterSons as z zsd}}' +//循环知识点
		'<div data-vId="{{z.id}}">' +
		'<p style="float:left;width: 200px;padding-left:20px;margin:0;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;" title="{{z.name}}">{{zsd+1}}-{{z.name}}</p></div>' +
		'{{each z.videos as v}}' +//循环视频
		'{{if v.isLearn==true}}' +
		'<div class="yesMoney" shi="true" data-videoId="{{v.videoId}}" data-vId="{{v.id}}" data-chapterId="{{v.chapterId}}" data-sectionId="{{v.sectionId}}">' +
		'{{else}}'+
		'<div class="yesMoney" shi="false" data-videoId="{{v.videoId}}" data-vId="{{v.id}}" data-chapterId="{{v.chapterId}}" data-sectionId="{{v.sectionId}}">' +
		'{{/if}}'+
		'<span class="sj"><i class="iconfont icon-shipin"></i></span>'+
		'<p style="float:left;max-width: 150px;padding-left:10px;margin:0;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;padding-right:10px" title="{{v.videoName}}">{{v.videoName}}</p>' +
		'{{if v.isLearn==true}}' +
		'<img style="float:left;margin-top:10px;width:12px;" src="../../web/images/video2/shi2.png"/>' +
		'{{/if}}' +
		'<img src="../../web/images/video/icon_09.png" class="hide playing" /></div>' +
		'{{/each}}' +
		'{{/each}}' +
		'{{/each}}' +
		'</div>' +
		'{{/each}}';
	//星星
    template.helper('stars', function(num) {
        var stars = "";
        for(var i = 0; i < num; i ++) {
            stars += "<img src='../../web/images/video2/xxx.png' style='margin-right:5px;'/>";
        }
        for(var i = 0;i< 5-num; i ++){
        	stars += "<img src='../../web/images/video2/xxxv.png' style='margin-right:5px;'/>"
        }
        return stars;
    });
	//去掉时间的秒
	template.helper("removeSecond",function(num){
		if(num!=""){
			var arr=num.split(" ");
			var arr1=arr[1].split(":");
			return ""+arr[0]+" "+arr1[0]+":"+arr1[1];
		}
	});
    //评价列表模板
    var evaluateList = 
    '{{each items as e}}' +
    '<div class="videoBody-bottom-listRelease">' +
		'<div class="videoBody-bottom-listRelease-left">' +
			'<img src="{{e.smallPhoto}}"/>' +
			'<p title="{{e.userName}}">{{e.userName}}</p>' +
		'</div>' +
		'<div class="videoBody-bottom-listRelease-right">' +

			'<p class="releaseText" style="word-wrap: break-word;">{{e.content}}</p>' +
			'<div class="releaseGood clearfix" data-criticizeId="{{e.id}}" data-isPraise="{{e.isPraise}}">' +
			'<span class="releaseTime">{{removeSecond(e.createTime)}}</span>' +
			'<p class="wqz">'+
			'{{if e.isPraise == 0}}' +
			'<img src="../../web/images/video/good_normal.png" style="padding-right:5px;margin-top:-3px;"/>' +
			'{{else}}' +
			'<img src="../../web/images/video/good_click.png" style="padding-right:5px;margin-top:-3px;"/>' +
			'{{/if}}' +
			'<span>{{e.praiseSum}}</span></p></div>' +
			//官方回复
			'{{if e.response!=""&&e.response!=null}}'+
			'<div class="releaseOffice">' +
			'<span class="office_a">熊猫中医回复：</span>' +
			'<p class="office_b">{{e.response}}</p>' +
			'<span class="office_c">{{removeSecond(e.response_time)}}</span>' +
			'</div>' +
			'{{/if}}'+
			//~~~~~
		'</div>' +
	'</div>' +
	'{{/each}}';
	//评价缺省页
	var freeNull = 
	'<div class="freeNull"><img src="../../web/images/video2/nullpl.png" /><p>亲，快点来抢沙发哦~~</p></div>';
	//购买缺省页
	var haveNull =
	'<div class="haveNull"><img src="../../web/images/video2/nullSchool.png" /><p>还没有小伙伴来学习哦~~</p></div>';
	//已购买
	var Studesing = 
	'{{each items as s}}' +
	'<div class="videoBody-bottom-right-list">' +
	'<img src="{{s.smallPhoto}}"/>' +
	'<p class="videoBody-bottom-right-list-title" title="{{s.userName}}">{{s.userName}}</p>' +
	'<p class="videoBody-bottom-right-list-text"><span>刚刚</span>购买了课程</p>' +
	'</div>' +
	'{{/each}}';
	$.getUrlParam = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
	/*var video_id = "AAFE78E9862A37F49C33DC5901307461";
	var chapterId = 1;
	var courseId = 2;*/
	var courseId = $.getUrlParam("courseId");
	var pageNumber = 1;
	var pageSize = 15;
	//跳转支付
	$(".goMoney").click(function(){
		window.location.href = "/web/html/order.html?courseId=" + courseId;
	});
	$(".header .gg i").on("click",function(){
		$(".header .gg").hide();
	});
	//获取课程名字和讲师姓名
	RequestService("/online/live/getOpenCourseById", "get", {
		courseId: courseId
	}, function(data) {
		document.title = data.resultObject.courseName + " - 年轻人的在线IT课堂";
		$(".headerBody .name").html(data.resultObject.courseName).attr("title", data.resultObject.courseName);
		$(".headerBody .mess .kc").html(data.resultObject.courseName).attr("title", data.resultObject.courseName);
		if(data.resultObject.teacherName!=undefined){
			$(".headerBody .mess .js i").html(data.resultObject.teacherName);
		}else{
			$(".headerBody .mess .js i").html("暂无讲师");
		}
		menuid = data.resultObject.menu_id;
	}, false);
   //获取视频目录
   // RequestService("/video/getVideos", "GET", {
   //     courseId:courseId,
   //     isTryLearn: 1
   // }, function(data) {
   //     $(".videoBody-list").html(template.compile(videoMemu)({
   //         items: data.resultObject
   //     }));
   //     $(".freeTable-list div").attr("data-courseId",courseId);
   //     if($.getUrlParam("videoId") != null && $.getUrlParam("videoId")!= undefined){
   //     	var video_id = $.getUrlParam("videoId");
   //     	var vId = $.getUrlParam("vId");
   //     	playVideo(video_id);
   //     	givecriticize(video_id);
   //
	//	    //点击播放下一课时
	//	    $(".nextSchool").click(function(){
	//	    	$(".freeTable-list div").each(function(){
	//	    		if($(this).attr("data-vId") == vId){
	//	    			if($(this).next().length == 0 && $(this).parent().next().length == 0) {
   //
	//					} else if($(this).parent().next().next().find("div").children().hasClass("freeTable")&& $(this).next().children().hasClass("freeTable") == false) {
	//						$(this).parent().next().next().children(":first").click();
	//					} else {
	//						$(this).next().click();
	//					}
	//	    		}
	//	    	})
	//	    })
   //     }else{
	//		if(data.resultObject[0].videos){
	//			var video_id = data.resultObject[0].videos[0].videoId;
	//			var vId = data.resultObject[0].videos[0].id;
	//			playVideo(video_id);
	//			givecriticize(video_id);
	//		}else{
	//			//没有视频
	//		}
   //
	//	    //点击播放下一课时
	//	    $(".nextSchool").click(function(){
	//	    	$(".freeTable-list div").each(function(){
	//				if($(this).attr("data-vId") == vId){
	//					if($(this).next().length == 0 && $(this).parent().next().length == 0) {
   //
	//					} else if($(this).parent().next().next().find("div").children().hasClass("freeTable")&& $(this).next().children().hasClass("freeTable") == false) {
	//						$(this).parent().next().next().children(":first").click();
	//					} else {
	//						$(this).next().click();
	//					}
	//				}
	//	    	})
	//	    })
   //     }
   //
   //     $(".freeTable-list div").each(function(){
   //
   //     	if($(this).attr("data-vId") == vId){
   //
   //     		$(this).find(".playing").removeClass("hide");
   //     		$(this).parent().prev().find(".shang").removeClass("hide");
   //     		$(this).parent().prev().find(".xia").addClass("hide");
   //     		$(this).parent().removeClass("hide");
   //     		$(this).addClass("hoverBorder");
   //     		chapterId = $(this).attr("data-chapterid");
   //     		$(this).find(".freeTable").addClass("hide");
   //     	}
   //     })
   //     $(".videoBody-list .menuList").on("click",function(){
   //         if($(this).next().hasClass("hide")){
   //             $(this).next().removeClass("hide");
   //     		$(this).find(".shang").removeClass("hide");
   //     		$(this).find(".xia").addClass("hide");
   //         }else{
   //             $(this).next().addClass("hide");
   //     		$(this).find(".shang").addClass("hide");
   //     		$(this).find(".xia").removeClass("hide");
   //         }
   //     });
   //
	//   //点击课程
	//   $(".yesMoney").click(function(){
	//   		location.href = "freeAudition.html?vId="+$(this).attr("data-vId")+"&courseId="+$(this).attr("data-courseId")+"&videoId="+$(this).attr("data-videoId");
	//   });
	//   $(".overMoney").click(function(){
	//   		$(".videomodal1").removeClass("hide");
	//		$(".backgrounds1").removeClass("hide");
	//   })
   // });







	//获取视频目录   康荣彩代码
	RequestService("/video/getvideos", "GET", {
		courseId:courseId,
		isTryLearn: 1
	}, function(data) {
		$(".videoBody-list").html(template.compile(videoMemu)({
			items: data.resultObject
		}));
		$(".freeTable-list div").attr("data-courseId",courseId);
		if($.getUrlParam("videoId") != null && $.getUrlParam("videoId")!= undefined){
			var video_id = $.getUrlParam("videoId");
			var vId = $.getUrlParam("vId");
			playVideo(video_id);
			givecriticize(vId);

			//点击播放下一课时
			$(".nextSchool").click(function(){
				$(".freeTable-list div").each(function(){
					if($(this).attr("data-vId") == vId){
						if($(this).next().length == 0 && $(this).parent().next().length == 0) {

						} else if($(this).parent().next().next().find("div").children().hasClass("freeTable")&& $(this).next().children().hasClass("freeTable") == false) {
							$(this).parent().next().next().children(":first").click();
						} else {
							$(this).next().click();
						}
					}
				})
			})
		}else{
			if( data.resultObject[0].chapterSons[0].chapterSons[0].videos){
				if(data.resultObject[0].chapterSons[0].chapterSons[0].videos.length!=0){
					var video_id = data.resultObject[0].chapterSons[0].chapterSons[0].videos[0].videoId;
					var vId =data.resultObject[0].chapterSons[0].chapterSons[0].videos[0].id;
					if(data.resultObject[0].chapterSons[0].chapterSons[0].videos[0].isLearn==false){
						$(".videomodal1").removeClass("hide");
						$(".backgrounds1").removeClass("hide");
					}else{
						playVideo(video_id);
						givecriticize(vId);
					}
				}
			}else{
				//没有视频
			}

			//点击播放下一课时
			$(".nextSchool").click(function(){
				$(".freeTable-list div").each(function(){
					if($(this).attr("data-vId") == vId){
						if($(this).next().length == 0 && $(this).parent().next().length == 0) {

						} else if($(this).parent().next().next().find("div").children().hasClass("freeTable")&& $(this).next().children().hasClass("freeTable") == false) {
							$(this).parent().next().next().children(":first").click();
						} else {
							$(this).next().click();
						}
					}
				})
			})
		};
//		setTimeout(function() {
			$(".shadowJiaZai,.loadImg").css("display", "none");
//		}, 80);
		$(".freeTable-list div").each(function(){
			if($(this).attr("data-vId") == vId){
				// alert(vId);
				$(this).find(".playing").removeClass("hide");
				$(this).parent().prev().find(".shang").removeClass("hide");
				$(this).parent().prev().find(".xia").addClass("hide");
				$(this).parent().removeClass("hide");
				$(this).addClass("hoverBorder");
				$(this).parent().removeClass("hide");
				chapterId = $(this).attr("data-chapterid");
				$(this).find(".freeTable").addClass("hide");
			}

		})
		$(".videoBody-list .menuList").on("click",function(){
			if($(this).next().hasClass("hide")){
				$(this).next().removeClass("hide");
				$(this).find(".shang").removeClass("hide");
				$(this).find(".xia").addClass("hide");
			}else{
				$(this).next().addClass("hide");
				$(this).find(".shang").addClass("hide");
				$(this).find(".xia").removeClass("hide");
			}
		});

		//点击课程
		$(".yesMoney").click(function(){
			if($(this).attr("shi")!="true"){
				$(".videomodal1").removeClass("hide");
				$(".backgrounds1").removeClass("hide");
			}else{
				location.href = "freeAudition.html?vId="+$(this).attr("data-vId")+"&courseId="+$(this).attr("data-courseId")+"&videoId="+$(this).attr("data-videoId");
			}
		});
		//$(".overMoney").click(function(){
		//	$(".videomodal1").removeClass("hide");
		//	$(".backgrounds1").removeClass("hide");
		//})
	});











	
	//购买学员
	 RequestService("/video/getPurchasedUser", "GET", {
	        courseId:courseId
	  }, function(data) {
	   		if(data.resultObject.length == 0){
	   			$(".videoBody-bottom-right").append(template.compile(haveNull));
	   		}else{
	   			$(".videoBody-bottom-right").append(template.compile(Studesing)({
	            	items: data.resultObject
	        	}));
	   		}
	    });
	
	//视频播放窗口加载
	//视频播放窗口加载
	var allwidth=parseInt($(".videoBody-top").width());
	var awidth=parseInt($(".videoBody-top").width()-290);
	var aheight=parseInt($(window).height()-$(".header").height()-50);
	$(".videoBody-top").height(aheight);
	$(".videoBody-menuList").height(aheight);
	$(".videoBody-video").height(aheight);
	$(".loadImg").css("display","block");
	function playVideo(video_id){
		RequestService("/online/vedio/getVidoInfo","GET", {
        video_id:video_id,
        width:awidth,
        height:aheight,
        autoPlay:false
	   }, function(data) {
			var scr = data.resultObject.playCode;
			$(".videoBody-video").append(scr);
			$(".headerBody-title").html(data.resultObject.title);
	    });
	}
   //点击目录事件
	$(".videoBody-menuButton").click(function() {
		if($(".videoBody-list").hasClass("wqhide")) {
			$(".videoBody-list").removeClass("wqhide");
			$(".videoBody-menuList").animate({
				"right": "0px"
			});
			$(".mess").animate({
				"right": "0px"
			});
			$(".gg").animate({
				"marginLeft": "-390px"
			});
			$(".videoBody-video").animate({
				"paddingRight": "290px"
			});
			$(".getNextPlay").animate({"right":"520px"});
			$(".loadImg").animate({"marginLeft":"-170px"})
		} else {
			$(".mess").animate({
				"right": "-290px"
			});
			$(".gg").animate({
				"marginLeft": "-245px"
			});
			$(".videoBody-list").addClass("wqhide");
			$(".videoBody-menuList").animate({
				"right": "-290px"
			});
			$(".videoBody-video").animate({
				"paddingRight": "0px"
			});
			$(".getNextPlay").animate({"right":"230px"});
			$(".loadImg").animate({"marginLeft":"-25px"})
		}
	});
    function givecriticize(vId,pageNumber,pageSize){
    	//获取评价列表
	    RequestService("/video/getVideoCriticize", "GET", {
	        videoId:vId,
	        pageNumber:pageNumber,
	        pageSize:pageSize
	    }, function(data) {
	        
	        if(data.resultObject.totalPageCount> 1) { //分页判断
				/* $(".not-data").remove();*/
				$(".videoBody-bottom-left-list").html(template.compile(evaluateList)({
	            	items: data.resultObject.items
	        	}));
				$(".videoBody-bottom-left .pages").css("display", "block");
				$(".videoBody-bottom-left .pages .searchPage .allPage").text(data.resultObject.totalPageCount);
				if(data.resultObject.currentPage == 1) {
					$("#Pagination").pagination(data.resultObject.totalPageCount, {
						callback: function(page) { //翻页功能
							pageNumber = (page + 1);
							pageSize = 15;
							givecriticize(vId,pageNumber,pageSize);
						}
					});
				}
			}else if(data.resultObject.totalPageCount == 0){
				$(".videoBody-bottom-left .pages").css("display", "none");
				 $(".videoBody-bottom-left-list").html(template.compile(freeNull));
			} else {
				$(".videoBody-bottom-left-list").html(template.compile(evaluateList)({
	            	items: data.resultObject.items
	        	}));
				$(".videoBody-bottom-left .pages").css("display", "none");
			}
		    //点赞
//		    $(".releaseGood .wqz").off().on("click",function(){
//		    	var $this = $(this).parent();
//		    	RequestService("/online/user/isAlive", "GET", null, function (data) {///online/user/isAlive
//		            if (data.success === true) {
//		                var criticizeId = $this.attr("data-criticizeId");
//				    	if($this.attr("data-isPraise") == 0){
//				    		var updatePraise = true;
//				    		RequestService("/video/updatePraise", "POST", {
//				    		isPraise:updatePraise,
//					        criticizeId:criticizeId
//						    }, function(data) {
//						    	$this.attr("data-isPraise","1");
//						    	$this.find(".wqz span").html(data.resultObject.praiseSum);
//						    	$this.find(".wqz img").attr("src","../../web/images/video/good_click.png");
//						    });
//				    	}else{
//				    		var updatePraise = false;
//				    		RequestService("/video/updatePraise", "POST", {
//				    		isPraise:updatePraise,
//					        criticizeId:criticizeId
//						    }, function(data) {
//						    	$this.attr("data-isPraise","0");
//						    	$this.find(".wqz span").html(data.resultObject.praiseSum);
//						    	$this.find(".wqz img").attr("src","../../web/images/video/good_normal.png");
//						    });
//				    	}
//		            }
//		            else {
//		                $('#login').modal("show");
//		                $(".loginGroup .logout").css("display", "block");
//		                $(".loginGroup .login").css("display", "none");
//		            }
//		        });
//		    	
//		    	
//		    });
	    });
    }
	function on_player_ready(){
//		$(".shadowJiaZai,.loadImg").css("display","none");
	}
    //弹窗内操作
    $(".videomodal1 .nopurchase-close img").click(function(){
    	$(".videomodal1").addClass("hide");
		$(".backgrounds1").addClass("hide");
    });
    $(".videomodal2 .nopurchase-close img").click(function(){
    	$(".videomodal2").addClass("hide");
		$(".backgrounds2").addClass("hide");
    });
});