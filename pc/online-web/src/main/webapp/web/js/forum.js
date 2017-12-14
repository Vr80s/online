/**
 * Created by admin on 2017/1/3.
 */
$(function () {
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
        '<a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank" style="background:url({{$value.banner_path}})no-repeat top center">' +
        '<div class="banner-info">' +
        '<span class="banner-type">{{$value.name}}</span>' +
        '<span class="banner-title">{{$value.title}}</span>' +
        '</div>' +
        '<div class="image-overlay"></div>'+
        '</a>' +
        '</li>' +
            '{{/if}}'+
        '{{#selector($index)}}' +
        '{{/each}}';
    var hotArticle = '{{each hotArticle}}' +
        '{{if $index<=2}}' +
        '<li>' +
        '<a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank"><em class="select">{{$index+1}}</em><span title="{{$value.title}}">{{$value.title}}</span></a>' +
        '</li>' +
        '{{else}}' +
        '<li><a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank"><em>{{$index+1}}</em><span title="{{$value.title}}">{{$value.title}}</span></li></a>' +
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
            '<span>{{dataSub($value.create_time)}}</span>'+
            '</div></div></div>'+
            '{{/each}}';
    var hotTag='{{each hotTag}}'+
            '<li><a href="/web/html/forumBiaoqian.html?tagId={{$value.id}}" target="_blank">{{$value.name}}</a></li>'+
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
    //banner
    // RequestService("/bxs/article/getArticleBanner", "GET", null, function (data) {
    RequestService("/banner/getBannerList?type=3", "GET", null, function (data) {
        $(".slider").html(template.compile(articleBanner)({
            articleBanner: data.resultObject
        }));
        if (data.resultObject.length == 1) {
            $("#left,#right,#selector").hide();
        }
        init();
    });
    //hot-article
    RequestService("/bxs/article/getHotArticle", "GET", null, function (data) {
        if(data.resultObject.length==0){
            $(".hot-article-list").html(template.compile(emptyDefaul));
        }else{
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
    RequestService('/bxs/article/getArticleType',"GET",null,function(data){
        $(".forum-content-tag").html(template.compile(articleType)({
            articleType:data.resultObject
        }));
        articleTypeHeight=$(".forum-content-tag").offset().top;
        $(document).scroll(function () {
            if ($(document).scrollTop() > articleTypeHeight) {
                $(".forum-content-tag").addClass("articleFloat");
            } else {
                $(".forum-content-tag").removeClass("articleFloat");
            }
        });
        $(".forum-content-tag li").click(function(){
            $(this).addClass("select").siblings().removeClass("select");
            $(".forum-content-tag li").find("em").addClass("select1");
            $(this).find("em").removeClass("select1").addClass("select");
            list.type=$(this).attr("data-articleId");
            $(".pages").css("display", "none");
            paperArticle();
        });
        $(".forum-content-tag li").eq(0).click();
        var bxsTagName=localStorage.getItem("bxsArticleType");
        $(".forum-content-tag li").each(function(){
            if($(this).text()==bxsTagName){
                $(this).addClass("select").siblings().removeClass("select");
                $(".forum-content-tag li").find("em").addClass("select1");
                $(this).find("em").removeClass("select1").addClass("select");
                $(this).click();
                localStorage.bxsArticleType=null;
            }
        })
    });
    //热门标签
    RequestService("/bxs/article/getHotTags","GET",null,function(data){
        if(data.resultObject.length==0){
            $(".forum-hot-tagGround").html(template.compile(emptyDefaul))
        }else{
            $(".forum-hot-tagGround").html(template.compile(hotTag)({
                hotTag:data.resultObject
            }))
        }
    });
    //相关课程
    RequestService("/bxs/article/getHotCourses", "GET",null, function (data) {
        if (data.resultObject.length == 0) {
            $(".course").html(template.compile(emptyDefaul))
        } else {
            $(".course").html(template.compile(relativeCourse)({
                item: data.resultObject
            }));
        }
        if (data.resultObject == "" || data.resultObject == null) {
            $(".by-the-arrow").css("display", "none");
            $(".by-the-arrow").css("display", "none");
            $(".slide-box").css("display", "none");
        } else {
            $(".boxContent li").eq(0).addClass("diyiye");
            $(".allLunbo").html(data.resultObject.length);
            var $index = 0;
            var $exdex = 0;
            $("#next").click(function () {
                if ($index + 1 >= $(".allLunbo").text()) {
                    return false;
                } else {
                    $index++;
                }
                next();
                return $exdex = $index;
            });
            $("#prev").click(function () {
                if ($index - 1 < 0) {
                    return false;
                } else {
                    $index--;
                }
                pre();
                return $exdex = $index;
            })
        }
        function next() {
            $(".currentLunbo").html($index + 1);
            $(".boxContent li").eq($index).stop(true, true).
                css("left", "100%").animate({"left": "0"});
            $(".boxContent li").eq($exdex).stop(true, true).
                css("left", "0").animate({"left": "-100%"});
            
//            $(".boxContent li").eq($index).stop(true, true).
//            css("opacity", "0").animate({"opacity": "1"});
//        $(".boxContent li").eq($exdex).stop(true, true).
//            css("opacity", "1").animate({"opacity": "0"});
        }

        function pre() {
            $(".currentLunbo").html($index + 1);
            $(".boxContent li").eq($index).stop(true, true).
                css("left", "-100%").animate({"left": "0"});
            $(".boxContent li").eq($exdex).stop(true, true).
                css("left", "0").animate({"left": "100%"});
        }
    });
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

        var timer = window.setInterval(autoChange, 5000);

        //点击圆圈切换
        $selector.on('click', function (e) {
            var $target = $(e.target);
            if ($target.get(0).tagName === 'SPAN') {
                window.clearInterval(timer);
                $target.addClass('cur').siblings().removeClass('cur');
                step = $target.index();
                $sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
                timer = window.setInterval(autoChange, 5000);
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
            timer = window.setInterval(autoChange, 5000);
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
            timer = window.setInterval(autoChange, 5000);
        })
    }
    addSelectedMenu();
});

function addSelectedMenu(){
	$(".forum").addClass("select");
}