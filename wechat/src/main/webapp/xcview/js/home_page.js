var openId = getQueryString("openId");
if(stringnull(openId)){
    localStorage.setItem("openid",openId);
}




/**
 * 根据不同的tab进行学堂的分类查询
 */
function schoolQuery(begin){
    if(begin == 0){  //分类
        typeSchool();
    }else if(begin == 1){ //推荐
        recommendSchool()
    }else if(begin == 2){ // 线下班
        lineWork();
    }else if(begin == 3){ //直播
        liveSchool();
    }else if(begin == 4){ //听课
        listenSchool();
    }
}





//分类  模块开始 ----------------------------------------

function typeSchool(){

//	分类渲染
    var noNumber='<p style="font-size:15px;text-aline:center;">暂无数据</p>'
    requestService("/xczh/classify/schoolClass",null,function(data){
        if(data.success==true){
//    	课程分类
        	if(data.resultObject[0].length==0 ||data.resultObject[0].length==null){
        		$(".classify_course").hide();
        	}else{
           	 	$('#classify_course_type').html(template('my_data0',{items:data.resultObject[0]}));
        	}
//		专题课程
			if(data.resultObject[1].length==0 ||data.resultObject[1].length==null){
        		$(".classify_special").hide();
        	}else{
            	$('#classify_special_type').html(template('my_data1',{items:data.resultObject[1]}));
        	}
//		课程分类
			if(data.resultObject[2].length==0 ||data.resultObject[2].length==null){
        		$(".classify_mold").hide();
        	}else{
            	$('#classify_mold_type').html(template('my_data2',{items:data.resultObject[2]}));
        	}
        }
        else{
            $("#classify_mold_type").html(template.compile(noNumber))
        }
   })
}
//分类  模块结束 ============================================




//推荐模块开始 ----------------------------------------

function recommendSchool(){
    //推荐模块开始
    //轮播/大师课/名医渲染
    requestService("/xczh/recommend/recommendTop",null,
        function(data) {
            if(data.success){
                //大师课 //判断课程类型为空
                if(data.resultObject.project.records.length==0 || data.resultObject.project.records.length==""){
               		$(".swiper_box0").hide()
                }else{
             		$("#slide_one").html(template('nav_list',{items:data.resultObject.project.records}))
                }
               //名医
                if(data.resultObject.doctorList.length=='' || data.resultObject.doctorList.length==0){
                    $("#doctor_follow").hide()
                }
                $("#phy_box").html(template('wrap_phy',{items:data.resultObject.doctorList}))
                //swiper轮播开始
                var result = data.resultObject.banner.records;
                var str ="";
                for (var int = 0; int < result.length; int++) {
                    var wb = result[int];
                    str+="<div class='swiper-slide swiper-banner swiper-banner-btn'>"+
                        "<img src='"+wb.imgPath+"' alt='Concept for children game' data_id='"+wb.id+"' data_img='"+wb.url+"'>"+
                        "</div>";
                }
                $("#wrapper-box").html(str);
                var mySwiper = new Swiper('#swiper-container-box',{
                    pagination : '#swiper-banner-list',
                    loop:true,
                    autoplay : 3000,
                    autoplayDisableOnInteraction : false,
                    //pagination : '#swiper-pagination1',
                })
            }else{
                alert("网络异常");
            };
            //轮播图跳转

            $(".swiper-banner-btn").click(function(){
                var  data_id=$(this).find("img").attr("data_id");
                clickBanner(data_id);
                var  data_img=$(this).find("img").attr("data_img");
                location.href=data_img;
            })
            //swiper轮播结束
            //小白班跳转
            $(".slide_one_div a").click(function(){
                var  data_one_div=$(this).find("img").attr("data-div");
                location.href=data_one_div;
            })

            //swiper学堂小白课
            var swiper = new Swiper('.swiper-containers', {
                slidesPerView: 5,
                paginationClickable: true,
                spaceBetween: 10
            });


            //swiper医师滑动
            var swiper = new Swiper('.swiper-container', {
                slidesPerView: 5,
                paginationClickable: true,
                spaceBetween: 10
            });

        })
    //精品课程
    requestService("/xczh/recommend/recommendCourse",null,function(data) {
        if(data.success==true){

//				console.log(data)
            $(".first_box").html(template('shipin',{items:data.resultObject}))
            $(".careful_class").click(function(){
                var data_num=$(this).attr("menuType");
                window.location.href="/xcview/html/curriculum_table.html?menuType="+data_num+"";
            })
            var myHeight=$(".tjks").height();
            $(".gieTa").height(myHeight);

        }
    })
    //推荐模块结束

}

//推荐模块结束  =====================================================


//线下课开始-------------------------------------------------------------------

function lineWork(){

    //线下课开始-------------------------------------------------------------------
    requestService("/xczh/bunch/offLine",null,
        function(data) {
            if(data.success){
                //各省城市                                                        											//跟参数
                $("#xx_slide_one").html(template('xx_nav_list',{items:data.resultObject.cityList.records}))
                
                
                
                
                //线下课程
                $(".acupunctures").html(template('acupunctures',{items:data.resultObject.allCourseList}))
                //swiper轮播开始
                if(data.resultObject.banner.records.length==0 || data.resultObject.banner.records==null){
                	$("#swiper-container-class").hide()
                }else{
	                var result_class = data.resultObject.banner.records;
	                var str_class ="";
	                for (var int = 0; int < result_class.length; int++) {
	                    var wb_class = result_class[int];
	                    str_class+="<div class='swiper-slide swiper-banner swiper-banner-class'>"+
	                        "<img src='"+wb_class.imgPath+"' alt='Concept for children game' data_id='"+wb_class.id+"' data_class='"+wb_class.url+"'>"+
	                        "</div>";
	                }
	                $("#wrapper-box-class").html(str_class);
	                var mySwiper = new Swiper('#swiper-container-class',{
	                    pagination : '#swiper-banner-list-class',
	                    loop:true,
	                    autoplay : 3000,
	                    autoplayDisableOnInteraction : false,
	                    //pagination : '#swiper-pagination1',
	                })
                }
            }else{
                alert("网络异常");
            };
//				轮播图跳转
            $(".swiper-banner-class").click(function(){
                var  data_id=$(this).find("img").attr("data_id");
                clickBanner(data_id);
                var  data_class=$(this).find("img").attr("data_class");
                location.href=data_class;
            })
            //swiper轮播结束
            //swiper线下课省滑动
            var swiper = new Swiper('#swiper1', {
                slidesPerView: 5,
                paginationClickable: true,
                spaceBetween: 10
            });
        },false)
    //线下课banner下的城市点击
    $(".go_search").click(function(){
        var city_class=$(this).find("span").text();
        window.location.href="/xcview/html/curriculum_table.html?city="+city_class+"";
    })
    //线下课标题点击
    $(".all_class").click(function(){
        var all_class=$(this).text();
//			if(all_class=='全国课程'){
//				all_class='';
//			}
        
        window.location.href="/xcview/html/curriculum_table.html?city="+all_class.trim()+"";
    })

}
//线下课结束 ================================================



//直播开始-----------------------------------------------------------

function liveSchool(){

    requestService("/xczh/live/onlineLive",null,
        function(data) {
            if(data.success){

                //swiper轮播开始
                var result_play = data.resultObject.banner.records;
                var str_play ="";
                for (var int = 0; int < result_play.length; int++) {
                    var wb_play = result_play[int];
                    str_play+="<div class='swiper-slide swiper-banner swiper-banner-play'>"+
                        "<img src='"+wb_play.imgPath+"' alt='Concept for children game' data_id='"+wb_play.id+"' data_play='"+wb_play.url+"'>"+
                        "</div>";
                }
                $("#wrapper-box-play").html(str_play);
                var mySwiper = new Swiper('#swiper-container-play',{
                    pagination : '#swiper-banner-list-play',
                    loop:true,
                    autoplay : 3000,
                    autoplayDisableOnInteraction : false,
                    //pagination : '#swiper-pagination1',
                })
            }else{
                alert("网络异常");
            };
//				轮播图跳转
            $(".swiper-banner-play").click(function(){
                var  data_id=$(this).find("img").attr("data_id");
                clickBanner(data_id);
                var  data_play=$(this).find("img").attr("data_play");
                location.href=data_play;
            })
            //swiper轮播结束
            if(data.success==true){
                $(".newests").html(template('newests',{items:data.resultObject.allCourseList}))
                /*var myHeight=$(".tjks").height();
                $(".gieTa").height(myHeight);*/

                $(".newest_title").click(function(){

                    var lineState=$(this).attr("lineState");

                    window.location.href="/xcview/html/curriculum_table.html?lineState="+lineState+"";
                })


            }



        })
}

//直播结束  ========================================



//听课开始   --------------------------------------------------

function listenSchool(){

    //听课开始

    requestService("/xczh/bunch/listenCourse",null,
        function(data) {
            if(data.success){

                //swiper轮播开始
                var result_listen = data.resultObject.banner.records;
                var str_listen ="";
                for (var int = 0; int < result_listen.length; int++) {
                    var wb_listen = result_listen[int];
                    str_listen+="<div class='swiper-slide swiper-banner swiper-banner-listen'>"+
                        "<img src='"+wb_listen.imgPath+"' alt='Concept for children game' data_id='"+wb_listen.id+"' data_listen='"+wb_listen.url+"'>"+
                        "</div>";
                }
                $("#wrapper-box-listen").html(str_listen);
                var mySwiper = new Swiper('#swiper-container-listen',{
                    pagination : '#swiper-banner-list-listen',
                    loop:true,
                    autoplay : 3000,
                    autoplayDisableOnInteraction : false,
                    //pagination : '#swiper-pagination1',
                })
            }else{
                alert("网络异常");
            };
//			轮播图跳转
            $(".swiper-banner-listen").click(function(){
                var  data_id=$(this).find("img").attr("data_id");
                clickBanner(data_id);
                var  data_listen=$(this).find("img").attr("data_listen");
                location.href=data_listen;
            })
            //swiper轮播结束


            $(".lecturess").html(template('lectures',{items:data.resultObject.listenCourseList}))

            $(".lectures_title").click(function(){
                window.location.href="/xcview/html/curriculum_table.html?courseType=2";
            })

        })

}

//听课结束 ====================================================

//$(function(){
////
////	//swiper学堂小白课
////	var swiper = new Swiper('.swiper-containers', {
////	    slidesPerView: 5,
////	    paginationClickable: true,
////	    spaceBetween: 10
////	});
////
////
////	//swiper医师滑动
////	var swiper = new Swiper('.swiper-container', {
////	    slidesPerView: 5,
////	    paginationClickable: true,
////	    spaceBetween: 10
////	});
//	//swiper线下课省滑动
//	var swiper = new Swiper('#swiper1', {
//	    slidesPerView: 5,
//	    paginationClickable: true,
//	    spaceBetween: 10
//	});
//});



//})
//JQ预加载分界线----------------------------------------------------------------
//学堂/推荐/课程跳转
//function jump(id){
//	requestService("/xczh/course/details?courseId="+id,null,function(data) {
//
//		var course = data.resultObject;
//		if(course.watchState == 0||course.watchState == 1){
//			if(course.type==1||course.type==2){
////				视频音频购买
//				location.href="school_audio.html?course_id="+id
//			}else if(course.type==3){
////				直播购买
//				location.href="school_play.html?course_id="+id
//			}else{
////				线下课购买
//				location.href="school_class.html?course_id="+id
//			}
//		}else if(course.watchState == 2||course.watchState == 3){
//			if(course.type==1||course.type==2){
//				if(course.collection){
////					专辑视频音频播放页
//				location.href="live_select_album.html?course_id="+id
//				}else{
////					单个视频音频播放
//				location.href="live_audio.html?my_study="+id
//				}
//			}else if(course.type==3){
////					播放页面
//				location.href="live_play.html?my_study="+id
//			}else{
////					线下课页面
//				location.href="live_class.html?my_study="+id
//			}
//		}
//	})
//}
//学堂
//学堂/直播课程跳转
var url_adress=window.location.href;
function jump_play(id){
   requestService("/xczh/course/details?courseId="+id,null,function(data) {
      var userPlay=data.resultObject;
      var falg =authenticationCooKie();       	       
//付费的直播和即将直播未购买跳购买页    
         if(userPlay.watchState==0 && userPlay.lineState==1){
            location.href="school_play.html?course_id="+id 
         }else if(userPlay.watchState==0 && userPlay.lineState==4){
            location.href="school_play.html?course_id="+id          
         }
//免费的直播和即将直播跳直播间      
         else if(userPlay.watchState==1 && userPlay.lineState==1){
            if (falg==1002){
            	localStorage.save_adress=url_adress;
            location.href ="/xcview/html/cn_login.html";      
            }else if (falg==1005) {
            	localStorage.save_adress=url_adress;
               location.href ="/xcview/html/evpi.html";
            }else{
            requestService("/xczh/history/add",
               {courseId:id}
               ,function(data) {
      
               }) 
            location.href="details.html?courseId="+id
            }
         }else if(userPlay.watchState==1 && userPlay.lineState==4){
            if (falg==1002){
            	localStorage.save_adress=url_adress;
                  location.href ="/xcview/html/cn_login.html";      
               }else if (falg==1005) {
               	localStorage.save_adress=url_adress;
                  location.href ="/xcview/html/evpi.html";
               }else{
                  requestService("/xczh/history/add",
                     {courseId:id}
                     ,function(data) {
            
                     }) 
                  location.href="details.html?courseId="+id  
               }
         }
//购买后的直播和即将直播跳直播间
         else if(userPlay.watchState==2 && userPlay.lineState==1){
            requestService("/xczh/history/add",
               {courseId:id}
               ,function(data) {
      
               }) 
            location.href="details.html?courseId="+id           
         }else if(userPlay.watchState==2 && userPlay.lineState==4){
            requestService("/xczh/history/add",
               {courseId:id}
               ,function(data) {
      
               }) 
            location.href="details.html?courseId="+id  
           }
//主播本人自己的直播和即将直播跳直播间			
			else if(userPlay.watchState==3 && userPlay.lineState==1){
				requestService("/xczh/history/add",
					{courseId:id}
					,function(data) {
		
					})	
				location.href="details.html?courseId="+id				
			}else if(userPlay.watchState==3 && userPlay.lineState==4){
				requestService("/xczh/history/add",
					{courseId:id}
					,function(data) {
		
					})	
				location.href="details.html?courseId="+id				
			}
			else{
				location.href="school_play.html?course_id="+id				
			}

 
 
})
   }
//学堂/推荐/课程跳转结束
//学堂/线下课课程跳转
//function jump_class(id){
//	requestService("/xczh/course/details?courseId="+id,null,function(data) {
//			var userClass=data.resultObject;
//		if(userClass.watchState==0 || userClass.watchState==1){
//			location.href="school_class.html?course_id="+id
//		}else if(userClass.watchState==2 || userClass.watchState==3){
//			location.href="live_class.html?my_study="+id
//
//		}
//
//	})
//}


//学堂/直播课程跳转
//function jump_play(id){
//	requestService("/xczh/course/details?courseId="+id,null,function(data) {
//			var userPlay=data.resultObject;
//		if(userPlay.watchState==0 || userPlay.watchState==1){
//			location.href="school_play.html?course_id="+id
//		}else if(userPlay.watchState==2 || userPlay.watchState==3){
//			location.href="live_play.html?my_study="+id
//
//		}
//
//	})
//}

//学堂/听课课程跳转
//function jump_listen(id){
//	requestService("/xczh/course/details?courseId="+id,null,function(data) {
//			var userListen=data.resultObject;
//		if(userListen.watchState==0 || userListen.watchState==1){
//			location.href="school_audio.html?course_id="+id
//		}else if(userListen.watchState == 2||userListen.watchState == 3){
//			if(userListen.collection){
//				location.href="live_select_album.html?course_id="+id
//			}else{
//				location.href="live_audio.html?my_study="+id
//			}
//		}
//	})
//}

//搜索历史开始
$(function(){
    requestService("/xczh/bunch/hotSearch",null,
        function(data) {

            if(data.success==true){
// 	    	<!--给头部inpiu默认值-->
                $(".header_seek_main_span").html(template('header_seek_main',{items:data.resultObject.defaultSearch}))

            }
        })
//搜索历史结束

})

//学堂/直播课程跳转
function clickBanner(id){
    requestService("/xczh/recommend/clickBanner",{
        id:id
    },function(data) {

    });
}


//点击学习判断游客
var falg =authenticationCooKie();
function go_study(){
    if (falg==1002){
    	localStorage.save_adress=url_adress;
        location.href ="/xcview/html/cn_login.html";
    }else if (falg==1005) {
    	localStorage.save_adress=url_adress;
        location.href ="/xcview/html/evpi.html";
    }else{
        location.href ="/xcview/html/my_study.html";
    }
}

