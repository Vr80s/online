/**
 * Created by admin on 2017/1/3.
 */
$(function(){
    $(".path a").each(function(){
        if($(this).text()=="博学社"){
            $(this).addClass("select").siblings().removeClass("select");
        }
    });
    //解析url地址
    var ourl = document.location.search;
    var apams = ourl.substring(1).split("&");
    var arr = [];
    for (i = 0; i < apams.length; i++) {
        var apam = apams[i].split("=");
        arr[i] = apam[1];
        var tagId = arr[0];
    };
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
    /*相关课程*/
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
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
    var hotArticle = '{{each hotArticle}}' +
        '{{if $index<=2}}' +
        '<li>' +
        '<a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank"><em class="select">{{$index+1}}</em><span title="{{$value.title}}">{{$value.title}}</span></a>' +
        '</li>' +
        '{{else}}' +
        '<li><a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank"><em>{{$index+1}}</em><span title="{{$value.title}}">{{$value.title}}</span></li></a>' +
        '{{/if}}' +
        '{{/each}}';
    var hotTag='{{each hotTag}}'+
        '<li data-tagId={{$value.id}}><a href="/web/html/forumBiaoqian.html?tagId={{$value.id}}">{{$value.name}}</a></li>'+
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
    var list={
        pageNumber:1,
        pageSize:10,
        tagId:tagId
    };
    biaoqianArticle();
    function biaoqianArticle(){
        RequestService("/bxs/article/getPaperArticle",'GET',list,function(data){
            if(data.resultObject.items.length==0){
                $(".forum-content-info").html(template.compile(emptyDefaul))
            }else{
                $(".forum-content-info").html(template.compile(articlePaper)({
                    articlePaper:data.resultObject.items
                }));
                if(data.resultObject.totalPageCount > 1){
                    $(".pages").css("display", "block");
                    if(data.resultObject.currentPage == 1) {
                        $("#Pagination").pagination(data.resultObject.totalPageCount, {
                            callback: function(page) { //翻页功能
                                list.pageNumber = (page + 1);
                                biaoqianArticle();
                            }
                        });
                    }
                }else{
                    $(".pages").css("display", "none");
                }
            }
            //省略号
            $('.forum-info-content').dotdotdot();
        });
    }
    //hot-article
    RequestService("/bxs/article/getHotArticle", "GET", null, function (data) {
        if(data.resultObject.length==1){
            $(".hot-article-list").html(template.compile(emptyDefaul));
        }else{
            $(".hot-article-list").html(template.compile(hotArticle)({
                hotArticle: data.resultObject
            }));
        }
    });
    //热门标签
    RequestService("/bxs/article/getHotTags","GET",null,function(data){
        if(data.resultObject.length==0){
            $(".forum-hot-tagGround").html(template.compile(emptyDefaul))
        }else{
            $(".forum-hot-tagGround").html(template.compile(hotTag)({
                hotTag:data.resultObject
            }));
            $(".forum-hot-tagGround li").each(function(){
                var tag_id=$(this).attr("data-tagid");
                if(tagId==tag_id){
                    $(".forum-nav-origin").attr("href","/web/html/forumBiaoqian.html?tagId="+tag_id);
                    var tagName=$(this).text();
                    $(".forum-nav-origin").text(tagName);
                }
            })
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
        }

        function pre() {
            $(".currentLunbo").html($index + 1);
            $(".boxContent li").eq($index).stop(true, true).
                css("left", "-100%").animate({"left": "0"});
            $(".boxContent li").eq($exdex).stop(true, true).
                css("left", "0").animate({"left": "100%"});
        }
    });
});