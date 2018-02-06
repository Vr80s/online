/**
 * Created by admin on 2017/1/3.
 */
$(function () {
	//顶部医馆字体变色
	$('.forum').css('color','#000');
	$('.path .hospital').addClass('select');
	
	//登入之后进行判断 右侧医师入驻入口是否有
	  RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
	  	if(data.success == true){
	  		//判断
	  		if(data.resultObject == 2 ){
	  			//医馆认证成功
	  			$('.forum-hosJoin').addClass('hide');
	  		}else{
	  			$('.forum-hosJoin').removeClass('hide');
	  		}
	  	}else if(data.success == false && data.errorMessage == "请登录！" ){
	  		$('.forum-hosJoin').removeClass('hide');
	  	}
	  });
	
	
	
    /*相关课程*/
    $(".path a").each(function(){
        if($(this).text()=="博学社"){
            $(this).addClass("select").siblings().removeClass("select");
        }
    });
    template.helper("selector", function (index) {
        var str;
        if (index == 0) {
            str = $(".selector").append("<span class='cur'></span>");
        } else {
            str = $(".selector").append("<span></span>");
        }
        return str;
    });
    template.helper("plainContent",function(content){
        return $(content).text();
    });
    template.helper("tagGroup",function(tagName,tagId){
        tagName=JSON.parse(tagName);
        tagId=JSON.parse(tagId);
        var str="";
        for(var i=0;i<tagName.length;i++){
            if(i==tagName.length-1){
                str+='<a href="/web/html/forumBiaoqian.html?tagId='+tagId[i]+'" target="_blank">'+tagName[i]+'</a>';
            }else{
                str+='<a href="/web/html/forumBiaoqian.html?tagId='+tagId[i]+'" target="_blank">'+tagName[i]+'</a>'+"<span style='color:#999;margin:0 3px'>,</span>";
            }
        }
        return str;
    });
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
    var articleBanner = '{{each articleBanner}}' +
            '{{if $index==0}}'+
        '<li style="z-index: 2">' +
        '<a href="{{$value.imgHref}}" target="_blank" style="background:url({{$value.imgPath}})no-repeat top center">' +
        // '<div class="banner-info">' +
        // '<span class="banner-type">{{$value.name}}</span>' +
        // '<span class="banner-title">{{$value.title}}</span>' +
        // '</div>' +
        '<div class="image-overlay"></div>'+
        '</a>' +
        '</li>' +
        '{{else}}'+
        '<li>' +
        '<a href="{{$value.imgHref}}" target="_blank" style="background:url({{$value.imgPath}})no-repeat top center">' +
        // '<div class="banner-info">' +
        // '<span class="banner-type">{{$value.name}}</span>' +
        // '<span class="banner-title">{{$value.title}}</span>' +
        // '</div>' +
        '<div class="image-overlay"></div>'+
        '</a>' +
        '</li>' +
            '{{/if}}'+
        '{{#selector($index)}}' +
        '{{/each}}';
    var hotArticle = '{{each hotArticle}}' +
        '{{if $index<=2}}' +
        '<li>' +
        '<a href="/web/html/clinicDetails.html?Id={{$value.id}}" ><em class="select">{{$index+1}}</em>{{$value.city}}&nbsp;&nbsp;{{$value.name}}</a>' +
        '</li>' +
        '{{else}}' +
        '<li><a href="/web/html/clinicDetails.html?Id={{$value.id}}" ><em>{{$index+1}}</em>{{$value.city}}&nbsp;&nbsp;{{$value.name}}</li></a>' +
        '{{/if}}' +
        '{{/each}}';
    var articleType='{{each articleType}}'+
            '{{if $index==0}}'+
            '<li class="select" data-articleId="{{$value.id}}"><em class="select"></em>{{$value.name}}</li>'+
            '{{else}}'+
            '<li data-articleId="{{$value.id}}"><em class="select1"></em>{{$value.name}}</li>'+
            '{{/if}}'+
            '{{/each}}';
    var articlePaper='{{each articlePaper}}'+
            '<div class="forum-info clearfix">'+
            '<a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank"><img class="forum-info-left" src="{{$value.img_path}}" alt=""/></a>'+
            '<div class="forum-info-right">'+
            '<div class="forum-info-title"><a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank">{{$value.title}}</a></div>'+
            '<div class="forum-info-content dot-ellipsis">{{change($value.content)}}</div>'+
            '<div class="forum-info-tags">'+
            '<i class="iconfont icon-biaoqian"></i>{{#tagGroup($value.tag,$value.tagId)}}'+
            '<span>{{$value.name}}<em></em>{{dataSub($value.create_time)}}</span>'+
            '</div></div></div>'+
            '{{/each}}';
    var hotTag='{{each hotTag}}'+
            '<li><a href="/web/html/clinicListing.html?name=&field={{$value.id}}" target="_blank">{{$value.name}}</a></li>'+
            '{{/each}}';
    var relativeCourse = '{{each item as $value i}}' +
        "<li>" +
        '{{#indexHref($value.description_show,$value.is_free,$value.id,$value.name)}}' +
        '{{#qshasImg($value.smallimg_path)}}' +
        '{{#online($value.name)}}' +
        '<div class="detail">' +
        '<p class="title" data-text="{{$value.courseName}}" title="{{$value.courseName}}">{{$value.courseName}}</p>' +
        '<p class="info clearfix">' +
        '<span>' +
        '{{if $value.is_free==true}}' +
        '<span class="pricefree">免费</span>' +
        '{{else}}' +
        '<i>￥</i><span class="price">{{$value.current_price}}</span><del><i class="price1">￥</i>{{$value.original_cost}}</del>' +
        '{{/if}}' +
        '</span>' +
        '<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learnd_count}}</span></span>' +
        '</p>' +
        '</div>' +
        '</a>' +
        "</li>" +
        '{{/each}}';
    //请求轮播图部分banner
    RequestService("/banner/getBannerList", "GET", {type:7}, function (data) {
        $(".slider").html(template.compile(articleBanner)({
            articleBanner: data.resultObject
        }));
        if (data.resultObject.length == 1) {
            $("#left,#right,#selector").hide();
        }
        init();
    });
    //优秀医馆部分 hot-article
    RequestService("/medical/hospital/getRecHospitals", "GET", null, function (data) {
        if(data.resultObject.length==0){
            $(".hot-article-list").html(template.compile(emptyDefaul));
        }else{
        	console.log(data);
            $(".hot-article-list").html(template.compile(hotArticle)({
                hotArticle: data.resultObject
            }));
        }
    });
    //文章
    var list={
        pageNumber:1,
        pageSize:6
    };
    var articleTypeHeight;
//    RequestService('/bxs/article/getArticleType',"GET",null,function(data){
//        $(".forum-content-tag").html(template.compile(articleType)({
//            articleType:data.resultObject
//        }));
//        articleTypeHeight=$(".forum-content-tag").offset().top;
//        $(document).scroll(function () {
//            if ($(document).scrollTop() > articleTypeHeight) {
////                $(".forum-content-tag").addClass("articleFloat");
//            } else {
//                $(".forum-content-tag").removeClass("articleFloat");
//            }
//        });
//        $(".forum-content-tag li").click(function(){
//            $(this).addClass("select").siblings().removeClass("select");
//            $(".forum-content-tag li").find("em").addClass("select1");
//            $(this).find("em").removeClass("select1").addClass("select");
//            list.type=$(this).attr("data-articleId");
//            $(".pages").css("display", "none");
//            paperArticle();
//        });
//        $(".forum-content-tag li").eq(0).click();
//        var bxsTagName=localStorage.getItem("bxsArticleType");
//        $(".forum-content-tag li").each(function(){
//            if($(this).text()==bxsTagName){
//                $(this).addClass("select").siblings().removeClass("select");
//                $(".forum-content-tag li").find("em").addClass("select1");
//                $(this).find("em").removeClass("select1").addClass("select");
//                $(this).click();
//                localStorage.bxsArticleType=null;
//            }
//        })
//    });
    //医馆搜索中的热门标签
    RequestService("/medical/hospital/getHotField","GET",null,function(data){
        if(data.resultObject.length==0){ 
            $(".forum-hot-tagGround").html(template.compile(emptyDefaul))
        }else{
            $(".forum-hot-tagGround").html(template.compile(hotTag)({
                hotTag:data.resultObject
            }))
        }
    });
    
    
    
     //医馆坐诊医生招募
    RequestService("/medical/hospitalRecruit/getRecHospitalRecruits","GET",null,function(data){
        if(data.resultObject.length == 0){ 
            $('#doctor_recruit_list').addClass('hide');
        }else{
        	console.log(data);
          $('#doctor_recruit_list').html(template('doctor_recruit',{doctor:data.resultObject}));
        }
    });
    //相关课程
//    RequestService("/bxs/article/getHotCourses", "GET",null, function (data) {
//        if (data.resultObject.length == 0) {
//            $(".course").html(template.compile(emptyDefaul))
//        } else {
//            $(".course").html(template.compile(relativeCourse)({
//                item: data.resultObject
//            }));
//        }
//        if (data.resultObject == "" || data.resultObject == null) {
//            $(".by-the-arrow").css("display", "none");
//            $(".by-the-arrow").css("display", "none");
//            $(".slide-box").css("display", "none");
//        } else {
//            $(".boxContent li").eq(0).addClass("diyiye");
//            $(".allLunbo").html(data.resultObject.length);
//            var $index = 0;
//            var $exdex = 0;
//            $("#next").click(function () {
//                if ($index + 1 >= $(".allLunbo").text()) {
//                    return false;
//                } else {
//                    $index++;
//                }
//                next();
//                return $exdex = $index;
//            });
//            $("#prev").click(function () {
//                if ($index - 1 < 0) {
//                    return false;
//                } else {
//                    $index--;
//                }
//                pre();
//                return $exdex = $index;
//            })
//        }
//        function next() {
//            $(".currentLunbo").html($index + 1);
//            $(".boxContent li").eq($index).stop(true, true).
//                css("left", "100%").animate({"left": "0"});
//            $(".boxContent li").eq($exdex).stop(true, true).
//                css("left", "0").animate({"left": "-100%"});
//            
////            $(".boxContent li").eq($index).stop(true, true).
////            css("opacity", "0").animate({"opacity": "1"});
////        $(".boxContent li").eq($exdex).stop(true, true).
////            css("opacity", "1").animate({"opacity": "0"});
//        }
//
//        function pre() {
//            $(".currentLunbo").html($index + 1);
//            $(".boxContent li").eq($index).stop(true, true).
//                css("left", "-100%").animate({"left": "0"});
//            $(".boxContent li").eq($exdex).stop(true, true).
//                css("left", "0").animate({"left": "100%"});
//        }
//    });
    
    
    
    //获取文章列表
    function paperArticle(){
        RequestService("/bxs/article/getPaperArticle",'GET',list,function(data){
            if(data.resultObject.items.length==0){
                $(".forum-content-info").html(template.compile(emptyDefaul))
            }else{
                $(".forum-content-info").html(template.compile(articlePaper)({
                    articlePaper:data.resultObject.items
                }));
                if(data.resultObject.totalPageCount > 1){
                    $(".pages").css("display", "block");
                    if(data.resultObject.currentPage>1){
                        $(document).scrollTop(articleTypeHeight);
                    }
                    if(data.resultObject.currentPage == 1) {
                        $("#Pagination").pagination(data.resultObject.totalPageCount, {
                            callback: function(page) { //翻页功能
                                list.pageNumber = (page + 1);
                                paperArticle();
                            }
                        });
                    }
                }
                //省略号
                $('.forum-info-content').dotdotdot();
            }
        });
    }
    //banner
    function init() {
        var $sliders = $('#slider li');
        var $selector = $('#selector');
        var $selectors = $('#selector span');
        var $left = $('#left');
        var $right = $('#right');
        //自动切换
        var step = 0;

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
        });
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
    addSelectedMenu();
    
    
    
    
    
    
    
    
    //医馆列表渲染
    //初始化请求信息
	window.current = 1;
	window.size = 9;
	window.name = "";
	getHostipalList(current,size,name);
	
	var pages;
	//渲染医馆列表方法
	function getHostipalList(current,size,name){
	    RequestService("/medical/hospital/getHospitals","GET",{
	    	current:current,
	    	size:size,
	    	name:name
	    },function(data){
	        if(data.resultObject.records.length == 0){
	        	//没有数据处理
//	           alert("没有数据/搜索失败")
	            $('#hospital_list').html('<h3>暂无数据</h3>');
	        }else if(current == data.resultObject.pages){
	        	 $('#hospital_list').append(template('hospitalTpl',{hospital:data.resultObject.records}));
	        	 $('.more_hospital').addClass('hide');
	        } else{
	        	//获取到数据渲染
	        	//创建一个盒子
	        	console.log(data.resultObject.records.length)
	        	if(data.resultObject.pages > 1){
	        		$('.more_hospital').removeClass('hide');
	        	}
	           $('#hospital_list').append(template('hospitalTpl',{hospital:data.resultObject.records}));
	        }
	    });
	}
	
	//搜索功能
	$('.search_hos_btn').click(function(e){
//		if(e.keyCode==13){
//		  var name = $('.search_hos').val();
//		  console.log(name)
//		  getHostipalList(current,size,name);  	
    	//编码
    	
    	var name = jQuery.trim($('.search_hos').val());
    	var searchUrl =encodeURI('/web/html/clinicListing.html?name='+name);  
//  	window.open('/web/html/doctor_list.html?name='+name); 
  		window.location.href =searchUrl;
//		}
		});
		
	    
	    //点击更多
	    $('.more_hospital>button').click(function(){
	    	current +=1;
	    	console.log(current)
	    	getHostipalList(current,size,name);
	    })
	    
	    
	    
	    
	    //医师入驻跳转页面
//  $('#toHosJoin').click(function(){
//  	  RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
//	       if(data.success == true ){
//	       	if($('.logout').css('display') == 'block' && data.resultObject.indexOf(2) == -1 && data.resultObject.indexOf(3) == -1 && data.resultObject.indexOf(5) == -1){
//	       		window.location.href = "/web/html/ResidentHospital.html";
//	       	}else if(data.resultObject.indexOf(2) != -1){
//	       		//医馆认证成功
//	       		window.location.href = "/web/html/ResidentHospital.html";
//	       	}else if(data.resultObject.indexOf(3) != -1 || data.resultObject.indexOf(5) != -1){
//	       		window.location.href = "/web/html/ResidentHospital.html";
//	       	}else if(data.resultObject.indexOf(1) != -1){
//	       		//医馆认证成功 提示不能进行医师认证
////	       		alert('您已完成了医馆注册，不能进行医师注册！')
//				$('#tip').text('您已完成了医师注册，不能进行医馆注册！');
//	       		$('#tip').toggle();
//	       		setTimeout(function(){
//	       			$('#tip').toggle();
//	       		},1000)
//	       	}else if(data.resultObject.indexOf(7) != -1 || data.resultObject.indexOf(4) != -1 || data.resultObject.indexOf(6) != -1){
//	       		//都没有注册过 进入注册页面
//	       		window.location.href = "/web/html/hospitalRegister.html";
//	       	}
//	       }else if(data.errorMessage == "请登录！"){
//	       	
//	       	window.location.href = "/web/html/hospitalRegister.html";
//	       	
//	       }else{
//	       	
//	       	$('#tip').text('服务器繁忙');
//	       		$('#tip').toggle();
//	       		setTimeout(function(){
//	       			$('#tip').toggle();
//	       		},1000)
//	       }
//	    });
//  })
//  
    
    
     //医师页面的医师入驻入口点击跳转效果
    $('#toHosJoin').click(function(){
    	 RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
    	 	if(data.success == true){
    	 		//请求数据成功进行判断 
    	 		if($('.login').css('display') == 'block' && data.resultObject == 1 ){
    	 			//登录并且入驻了医师了
    	 			$('#tip').text('您已完成了医师注册，不能进行医馆注册！');
	       			$('#tip').toggle();
	       			setTimeout(function(){
	       				$('#tip').toggle();
	       			},2000)
    	 		}else if($('.login').css('display') == 'block' && data.resultObject == 2 ){
    	 			//注册医馆成功
    	 			window.location.href = "/web/html/ResidentHospital.html";
    	 		}else if($('.login').css('display') == 'block' && data.resultObject == 7 ){
    	 			//登录了并且都没有注册过
    	 			window.location.href = "/web/html/ResidentHospital.html";
    	 		}else if($('.login').css('display') == 'block' && data.resultObject == 3 || data.resultObject == 4  || data.resultObject == 5  || data.resultObject == 6 ){
    	 			//登录了 并且注册了没有通过的
    	 			window.location.href = "/web/html/ResidentHospital.html";
    	 		}
    	 	}else if(data.success == false && data.errorMessage == "请登录！"){
    	 		window.location.href = "/web/html/hospitalRegister.html";
    	 	}else{
    	 		//请求数据有误
    	 		$('#tip').text('服务器繁忙');
	       		$('#tip').toggle();
	       		setTimeout(function(){
	       			$('#tip').toggle();
	       		},2000)
    	 	}
    	 });
    })
    
	    
	    
});

function addSelectedMenu(){
	$(".forum").addClass("select");
}