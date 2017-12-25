/**
 * Created by admin on 2017/1/4.
 */
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
        var articleId = arr[0];
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
    template.helper('bxsImg',function(obj){
        if(obj!=null && obj!=""){
            return '<div class="img"><img  src="'+obj+'" /></div>';
        }else{
            return '<div class="aimg" style="background-image:none;"><img   src="/web/images/defaultHeadImg.jpg"/></div>';
        }
    })
    /*相关课程*/
    var emptyDefaul =
        "<div class='page-no-result'>" +
        "<img src='../images/personcenter/my_nodata.png'>" +
        "<div class='no-title'>暂无数据</div>" +
        "</div>";
        
        
    var articleDetail='<div class="forum-detail">'+
            '<div class="forum-detail-title">{{title}}</div>'+
            '<div class="forum-info-tags">'+
//          '<i class="iconfont icon-biaoqian"></i>{{#tagGroup(items.tag,items.tagId)}}'+
            '<span>{{author}}&nbsp;&nbsp;&nbsp;{{dataSub(createTime)}}</span>'+
//          '<span>阅读({{items.browse_sum}})</span>'+
//          '<span>点赞(<span class="articleHitCoun">{{items.praise_sum}}</span>)</span>'+
//          '<span>评论(<span class="articleCommCoun">{{items.comment_sum}}</span>)</span>'+
            '</div></div>'+
//          '<div><img src={{imgPath}} alt="" / style="width:100%"></div>'+
            '<div class="forum-detail-content">{{#content}}</div>';
            //去除点赞区域
//          '<div class="forum-hitzanBox">'+
//          '{{if praiseSum==0}}'+
//          '<div class="forum-hitzan">'+
//          '<i class="iconfont icon-zan"></i>'+
//          '<span>{{praiseSum}}</span>'+
//          '<div class="forum-hitzan-plus">+1</div>'+
//          '</div>' +
//          '{{else}}'+
//          '<div class="yiZan">'+
//          '<i class="iconfont icon-zan"></i>'+
//          '<span>{{praiseSum}}</span>'+
//          '<div class="forum-hitzan-plus">+1</div>'+
//          '</div>' +
//          '{{/if}}'+
//          '</div>';
        
//  var articleDetail = '<h1>{{title}}</h1>';
            
    var commentInfo='{{each items}}'+
            '<div class="forum-community-content">'+
            '<div class="forum-comment-content clearfix">'+
            '{{#bxsImg($value.small_head_photo)}}' +
            '<div class="forum-comment-right">'+
            '<div class="comment-personAndTime">'+
            '<span class="comment-person">{{$value.name}}</span><em></em><span class="comment-time">{{$value.create_time}}</span>'+
            '</div>'+
            '{{if $value.nickName!="" && $value.nickName!=null}}'+
            '<div class="comment-info"><span>回复&nbsp;&nbsp;<span class="replyName">{{$value.nickName}}：</span></span>{{$value.content}}</div>'+
            '{{else}}'+
            '<div class="comment-info">{{$value.content}}</div>'+
            '{{/if}}'+
            '<div class="reply-comment"><i class="iconfont icon-huifu"></i>回复</div>'+
            '{{if $value.mySelf==true}}'+
            '<div class="reply-delete" data-commentId="{{$value.id}}"><i class="iconfont icon-shanchu"></i>删除</div>'+
            '{{/if}}'+
            '<div class="replay-box">'+
            '<p class="replyPerson">回复 {{$value.name}}：</p>'+
            '<img class="pinglunSanjiao" src="../images/ansandqus/sanjiao02.png">'+
            '<input class="reply-input"/>'+
            '<div class="emptyHit"><i class="iconfont icon-tanhao"></i>请输入内容</div>'+
            '<div class="reply-btn">'+
            '<div class="cancle">取消</div>'+
            '<div class="reply" data-targetId="{{$value.user_id}}">回复</div>'+
            '</div></div></div></div></div>'+
            '{{/each}}';
    var relativeArticle = '{{each hotArticle}}' +
        '{{if $index<=2}}' +
        '<li>' +
        '<a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank"><em class="select">{{$index+1}}</em><span title="{{$value.title}}">{{$value.title}}</span></a>' +
        '</li>' +
        '{{else}}' +
        '<li><a href="/web/html/forumDetail.html?articleId={{$value.id}}" target="_blank"><em>{{$index+1}}</em><span title="{{$value.title}}">{{$value.title}}</span></li></a>' +
        '{{/if}}' +
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
    //文章详情
    RequestService("/medical/doctor/getSpecialColumnDetailsById",'GET',{articleId:articleId},function(data){
    	
    	//左侧报道内容渲染
        $(".forum-detailInfo").html(template.compile(articleDetail)(data.resultObject));
        
        //右侧报道名医渲染
        if(data.resultObject.medicalDoctors.length == 0){
					 $('#report_doctor_list').addClass('hide');
				}else{
					$('#report_doctor_list').html(template('report_docTpl',{inf:data.resultObject.medicalDoctors}));
				}
        
        
        $(".myClassName").text(data.resultObject.typeName);
        $(".myClassName").click(function(){
            localStorage.bxsArticleType=data.resultObject.typeName;
        });
        var praiseSum=data.resultObject.praise_sum;
        //点赞
        $(".forum-hitzan").click(function(){
            RequestService("/online/user/isAlive", "GET", null, function(data) {
                if (data.success == false) {
                    $('#login').modal('show');
                } else {
                    RequestService("/bxs/article/updatePraiseSum","GET",{articleId:articleId,praiseSum:praiseSum},function(data){
                        if(data.success==true){
                            $(".articleHitCoun").text(data.resultObject.praiseSum);
                            $(".forum-hitzanBox span").text(data.resultObject.praiseSum);
                            $(".forum-hitzan-plus").show().animate({top:"-20px"},800,function(){$(this).hide().css("top","20px")});
                            $(".forum-hitzan").unbind("click");
                            $(".forum-hitzan").removeClass("forum-hitzan").addClass("yiZan");
                        }
                    });
                }
            });
        });
    });
    
    
    	//右侧热门作者
	    RequestService("/bxs/article/getHotArticle","GET",null,function(data){
	        if(data.success==false || data.resultObject.length == 0){
	           $('.report_right_read').addClass('hide')
	        }else{
	        	//获取到数据渲染
	        	console.log(data)
	           $('#RecommendRead').html(template('RecommendReadTpl',{inf:data.resultObject}));
	        }
	    });
    
    
    
    //评论
    RequestService("/online/user/isAlive", "GET", null, function(data) {
        if (data.success == false) {
            $(".forum-community-loginHit").css("display","block");
        } else {
            $(".forum-community-loginHit").css("display","none");
        }
    });
    $(".forum-community-lgoin").click(function(){
        RequestService("/online/user/isAlive", "GET", null, function(data) {
            if (data.success == false) {
                $("#login").modal("show");
            } else {
                $(".community-submit").bind("click");
                $(".forum-community-loginHit").css("display","none");
            }
        });
    });
    $(".forum-community-textarea").focus(function(){
        $(".forum-community .emptyHit").css("display","none");
    });
    $(".community-submit").click(function(){
        RequestService("/online/user/isAlive", "GET", null, function(data) {
            if (data.success == false) {
                $("#login").modal("show");
            }else{
                var commentCon=$(".forum-community-textarea").val().trim();
                if(commentCon==""){
                    $(".forum-community .emptyHit").css("display","block");
                }else{
                    $(".forum-community .emptyHit").css("display","none");
                    RequestService('/bxs/article/saveAppraise','POST',{
                        article_id:articleId,
                        content:commentCon
                    },function(data){
                        if(data.resultObject=="评论成功!"){
                            var commentCount=$(".articleCommCoun").text();
                            commentCount++;
                            $(".articleCommCoun").text(commentCount);
                            $(".forum-community-textarea").val("");
                            allComment();
                        }
                    })
                }
            }
        });
    });
    //all comment
    var  commentList={
        articleId:articleId,
        pageNumber:1,
        pageSize:10
    };
    allComment();
    function allComment(){
        RequestService("/bxs/article/getAppraiseByArticleId","GET",commentList,function(data){
            commentList.pageNumber=1;
            if(data.resultObject.items.length==0){
                $(".forum-communitybox").html(template.compile(emptyDefaul));
            }else{
                $(".forum-communitybox").html(template.compile(commentInfo)({
                    items:data.resultObject.items
                }));
                bxgReply();
                delArticle();
                $(".community-count").text(data.resultObject.totalCount+"条");
                if(data.resultObject.totalPageCount > 1){
                    $(".pages").css("display", "block");
                    if(data.resultObject.currentPage == 1) {
                        $("#Pagination").pagination(data.resultObject.totalPageCount, {
                            callback: function(page) { //翻页功能
                                commentList.pageNumber = (page + 1);
                                allComment();
                            }
                        });
                    }
                }else{
                    $(".pages").css("display", "none");
                }
            }
        });
    }
    
    
  
   
    //删除
    function delArticle(){
        $(".reply-delete").each(function(){
            $(this).click(function(){
                $("#quxiaoshoucang").paymentModal("reply-delete");
                $(".tipType").text("确定要删除吗？");
                var commentId = $(this).attr("data-commentId");
                $("#quxiaoshoucang .modalFooter .yesBtn").off().click(function() {
                    RequestService('/bxs/article/deleteAppraiseId', "POST", {
                        appraiseId: commentId
                    }, function(data) {
                        if(data.success == true) {
                            $(".payment-modal-close").trigger("click");
                            var commentCount=$(".articleCommCoun").text();
                            commentCount--;
                            $(".articleCommCoun").text(commentCount);
                            allComment();
                        }
                    });
                });
                $("#quxiaoshoucang .modalFooter .notBtn").click(function() {
                    $(".payment-modal-close").trigger("click");
                })
            })
        });
    }
    //回复
    function bxgReply(){
        $(".reply-comment").each(function(){
            $(this).click(function(){
                var _rThis=$(this);
                $(".replay-box .emptyHit").hide();
                if(_rThis.parent().find(".replay-box").css("display") == "block") {
                    _rThis.parent().find(".replay-box").css("display", "none");
                } else {
                    $(".replay-box").css("display","none");
                    _rThis.parent().find(".replay-box").toggle();
                }
            })
        });
        $(".cancle").each(function(){
            $(this).click(function(){
                $(this).parent().parent().hide();
            })
        });
        $(".reply-input").focus(function(){
            var indentLength=$(this).parent().find(".replyPerson").width();
            $(".reply-input").css("textIndent",indentLength+"px");
            $(".replay-box .emptyHit").hide();
        });
        $(".reply").each(function(){
            var forumThs=$(this);
            forumThs.click(function(){
                RequestService("/online/user/isAlive", "GET", null, function(data) {
                    if (data.success == false) {
                        $('#login').modal('show');
                    } else {
                        var replayCon=forumThs.parent().parent().find(".reply-input").val().trim();
                        var targetId=forumThs.attr("data-targetid");
                        if(replayCon==""){
                            $(".replay-box .emptyHit").show();
                        }else{
                           RequestService("/bxs/article/saveAppraise","POST",{
                               article_id:articleId,
                               content:replayCon,
                               target_user_id:targetId
                           },function(data){
                               if(data.resultObject=="评论成功!"){
                                   var commentCount=$(".articleCommCoun").text();
                                   commentCount++;
                                   $(".articleCommCoun").text(commentCount++);
                                   allComment();
                               }
                           })
                        }
                    }
                });
            })
        });
    }
});