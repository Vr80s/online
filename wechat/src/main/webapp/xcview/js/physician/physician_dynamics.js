	
var doctorId = getQueryString("doctor");
var loginUserId="";
var loginUserName="";
var getPostsIdByComment="";
var postsCommentId="";
var postsCommentUserName="";
$(function(){
    loginUserId = localStorage.getItem("userId");
    loginUserName = localStorage.getItem("name");
    requestGetService("/doctor/posts", {
        pageNumber: 1,
        pageSize:10,
        doctorId:"b8e9430bd4334d749f06d2f0050dd66e"
    }, function (data) {
        var obj = data.resultObject.records;
        var now = new Date();
        now.setDate(now.getDate()+1);
        for(var i=0;i<obj.length;i++){
            var likes ="";
            //定义时间
        	obj[i].now=now;
        	//判断是否点赞
            obj[i].isPraise=false;
            //封装点赞列表
            var likeList = obj[i].doctorPostsLikeList;
            if(likeList!=null&&likeList.length>0){
            	for(var j=0;j<likeList.length;j++){
            		if(loginUserId!=undefined&&loginUserId==likeList[j].userId){
                        obj[i].isPraise=true;
					}
                    likes += likeList[j].userName+",";
				}
                obj[i].likes = likes.substr(0,likes.length-1);
			}
			//封装图片
        	if(obj[i].pictures!=null&&obj[i].pictures!=""){
                var pics=obj[i].pictures.split(",");
                obj[i].pics=pics;
			}
		}
        $(".rests_nav").html(template('wrap_doctor_dynamics',{items:obj}));

		//点赞
        $('.zan_img').click(function() {
            var src = $(this).attr('src');
            var postsId = $(this).attr('data-id');
            if(src.indexOf("zan001")>-1){

                delPostsLike(this,postsId);
            }else{

                postsLike(this,postsId);
            }
        })
		//评论
        $(".rests_nav .evaluate_img").click(function(){
            $(".face").attr("src","/xcview/images/face.png");
            $("#page_emotion").css("bottom","-2.8rem");
            $(".comment").show();
            getPostsIdByComment = $(this).attr('data-id');
            postsCommentId = "";
        });
		// 点击其他内容区域隐藏评论区域
        $(".comment_hide").click(function(){
            $(".face").attr("src","/xcview/images/face.png");
            $("#page_emotion").css("bottom","-2.8rem");
            $(".comment").hide();
        });

        // 回复/删除
        $(".evaluateDiv").click(function(){
            postsCommentId = $(this).attr('data-id');
            postsCommentUserName = $(this).attr('data-userName');
            getPostsIdByComment = $(this).attr('data-postsId');
            var userId = $(this).attr('data-userId');
            var replyUserId = $(this).attr('data-replyUserId');
            if(replyUserId!=undefined&&replyUserId!=""){
                userId=replyUserId;
            }
            if(userId!=loginUserId){
                $(".face").attr("src","/xcview/images/face.png");
                $("#page_emotion").css("bottom","-2.8rem");
                $(".comment").show();
            }else {
                alert("删除");
            }

        });

    });





});
/**
 * 评论
 */
function sendComment(){
    var article = $("#form_article").html();
    if($("#form_article").html()==""){
        alert("内容不能为空");
        return false;
    }
    requestService("/doctor/posts/"+getPostsIdByComment+"/comment",{
        postsId:getPostsIdByComment,
        commentId:postsCommentId,
        content:article
    },function(data) {
        if(data.success==true){
            var evaluatePostsId = "evaluate"+getPostsIdByComment;
            if(postsCommentId==""){
                $("."+evaluatePostsId+"").prepend("<div class='evaluateDiv' data-id='' data-postsId="+getPostsIdByComment+" data-userId="+loginUserId+" >" +
                    "<span class='name'>"+loginUserName+"：</span><span class=\"evaluate_cen\">"+article+"</span></div><div class=\"both\"></div>");
            }else {
                $("."+evaluatePostsId+"").prepend("<div class=\"both\"></div><div class='response evaluateDiv' data-id="+postsCommentId+"" +
                    " data-postsId="+getPostsIdByComment+" data-replyUserId="+loginUserId+"> " +
                    "<span class=\"my\">"+loginUserName+"</span> <span class=\"response_cen\">回复</span> " +
                    "<span class=\"she\">"+postsCommentUserName+"：</span> <span class=\"response_center\">"+article+"</span>" +
                    "</div>");
            }

            // 回复/删除
            $(".evaluateDiv").click(function(){
                postsCommentId = $(this).attr('data-id');
                getPostsIdByComment = $(this).attr('data-postsId');
                var userId = $(this).attr('data-userId');
                var replyUserId = $(this).attr('data-replyUserId');
                if(replyUserId!=undefined&&replyUserId!=""){
                    userId=replyUserId;
                }
                if(userId!=loginUserId){
                    $(".face").attr("src","/xcview/images/face.png");
                    $("#page_emotion").css("bottom","-2.8rem");
                    $(".comment").show();
                }else {
                    alert("删除");
                }

            });
            alert(data.resultObject);
        }else{
            alert(data.errorMessage);
        }
    });
}

/**
 * 点赞
 */
function postsLike(obj,postsId) {
    requestService("/doctor/posts/"+postsId+"/like/"+1,{
        postsId:postsId
    },function(data) {
        if(data.success==true){
            $(obj).attr('src','/xcview/images/zan001.png');
            $("#"+postsId+"").children("div").find("img").attr('src','/xcview/images/zan001.png');
            //重新获取点赞列表
            getPostsLikeList(postsId,data.resultObject.list);
            alert(data.resultObject);
        }else{
            alert(data.errorMessage);
        }
    });
}
/**
 * 取消点赞
 */
function delPostsLike(obj,postsId) {
    requestService("/doctor/posts/"+postsId+"/like/"+0,{
        postsId:postsId
    },function(data) {
        if(data.success==true){
            $(obj).attr('src','/xcview/images/zan01.png');
            $("#"+postsId+"").children("div").find("img").attr('src','/xcview/images/zan01.png');
            //重新获取点赞列表
            getPostsLikeList(postsId,data.resultObject.list);

            alert(data.resultObject);
        }else{
            alert(data.errorMessage);
        }
    });
}
//获取点赞列表
function getPostsLikeList(postsId,list) {

    var likes = list;
    var like="";
    if(likes!=null&&likes.length>0){
        for(var j=0;j<likes.length;j++){
            like += likes[j].userName+",";
        }
        $("#"+postsId+"").children("div").find(".likes").html(like.substr(0,like.length-1));
    }else {
        $("#"+postsId+"").children("div").find(".likes").html("");
    }
}





