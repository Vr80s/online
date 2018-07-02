
$(function(){
	// var id = 675;
	var id = getQueryString("articleId");
 	requestGetService("/xczh/article/view",{
 		// id:385
 		id:id
 	},function (data) {
	    if (data.success == true) {
		  $('#consilia_main_center').html(template('consilia_main_center_id', {items: data.resultObject}));
	    }
	});

 	// 加载先出现评价信息
	refresh();

	// 评价信息定义方法--刷新
	function refresh(){
		requestGetService("/xczh/article/appraise/list",{
			articleId: id,
	        pageNumber: 1,
	        pageSize: 20000
	 	},function (data) {
		    if (data.success == true) {
			$('.wrap_all_returned').html(template('broadcastroom_id', {items: data.resultObject}));
		    
		    //	回复弹窗
	        $(".wrap_returned_btn .btn_littleReturn").click(function () {
	        	// alert(55555);
	            //评价id
	            criticize_id = this.id;
	            $(".bg_userModal").show();
	            $(".wrapLittle_comment").show();
	            $("#littlt_return").focus();
	        });

		    // 点赞
		    $(".btn_click_zan").click(function () {
		        //评价id
		        criticize_id = $(this).attr("data-id");
		        criticize_id=$(this).attr("data-id");
		        var p = $(this).find('span').html();
		        var src = $(this).find('img').attr('src');
		        if(src.indexOf("zan001")>-1){
		            $(this).find('img').attr('src','/xcview/images/zan01.png');
		            $(this).find('span').html(parseInt(p)-1);
		            updatePraise(criticize_id,false);
		        }else{
		            $(this).find('img').attr('src','/xcview/images/zan001.png');
		            $(this).find('span').html(parseInt(p)+1);
		            updatePraise(criticize_id,true);
		        }
		    });
		    }
		});

	};

	



//点赞、取消点赞
function updatePraise(id, praised) {
    requestService("/xczh/article/appraise/praise", {
        praised: praised,
        criticizeId: id
    }, function (data) {
        
    });
}



$(".report_btn").click(function(){
	// alert(11111);
	reportComment();
	
});

//评价
function reportComment(){
    var comment_detailed = $('#comment_detailed').val();

    //内容是否不为空
    /*var opacity = $(".report_btn").css("opacity");
    if (opacity != 1) {
        return false;
    }*/

    requestService("/xczh/article/appraise", {
        content: comment_detailed,
        articleId: id
    }, function (data) {
        
        if (data.success == true) {
            webToast("评价成功", "middle", 1500);
            
            $(".wrapAll_comment").hide();
            $(".bg_modal").hide();
            document.getElementById("comment_detailed").value = "";
            refresh();
            //评价数加1
            var cc = $('#criticizeCount').text();
            $('#criticizeCount').text(parseInt(cc) + 1);

        } else {
            webToast("评价失败", "middle", 1500);
        }
    });
}


$(".return_btn").click(function(){
	// alert(11111);
	replyComment();
	
});

//回复评价
function replyComment() {
    var comment_detailed = $('#littlt_return').val();

    /*if (comment_detailed == "") {
        webToast("内容不能为空","middle",1500);
        return false;
    }*/

    requestService("/xczh/article/appraise", {

        content: comment_detailed,
        articleId: id,
        replyId: criticize_id
    }, function (data) {
        //	课程名称/等级/评价
        if (data.success == true) {
            webToast("回复成功", "middle", 1500);
            //	直播时间/主播名字
            $(".bg_userModal").hide();
            $(".wrapLittle_comment").hide();
            document.getElementById("littlt_return").value = "";
            refresh();
            //评价数加1
            var cc = $('#criticizeCount').text();
            $('#criticizeCount').text(parseInt(cc) + 1);
        } else {
            webToast("回复失败", "middle", 1500);
        }
    });
}


});