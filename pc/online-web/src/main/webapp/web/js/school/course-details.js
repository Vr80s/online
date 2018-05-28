$(function(){
	
	var index = 0;
	//表名是专辑
	if(collection == 1){ //
		$(".buy_tab").removeClass("hide");
		$(".no_buy_tab").remove();
		if(type == "info"){
			$(".wrap-sidebar ul li").eq(1).addClass("active-footer");
			index =1;
		}
	}else{
		$(".no_buy_tab").removeClass("hide");
		$(".buy_tab").remove();
		if(type == "info"){
			$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
		}
	}
	//type对应显示
	//outline  comment    info   aq    selection
	if(type == "selection"){
		$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
	}else if(type == "outline"){
		$(".wrap-sidebar ul li").eq(1).addClass("active-footer");
		index =1;
	}else if(type == "comment"){
		$(".wrap-sidebar ul li").eq(2).addClass("active-footer");
		index =2;
	}else if(type == "aq"){
		$(".wrap-sidebar ul li").eq(3).addClass("active-footer");
		index =3;
	}
	$(".sidebar-content").addClass("hide").eq(index).removeClass("hide")
	
//	详情/评价/常见问题	选项卡
    $(".wrap-sidebar ul li").click(function () {
        $(".wrap-sidebar ul li").removeClass("active-footer");
        $(this).addClass("active-footer");
        $(".sidebar-content").addClass("hide").eq($(this).index()).removeClass("hide")
    })
  
    
//购买课程
    $('.J-course-buy').on('click', function(e) {
       var $this = $(this);
       RequestService("/online/user/isAlive", "GET", null, function(data) {
           if(!data.success) {
               $('#login').modal('show');
           } else {
               var id = $this.data('id');
               $this.attr("disabled", "disabled");
               if (!id) {
                   showTip("无法获取课程id");
               }
               RequestService("/order/" + id, "POST", null, function(data){
                   if (data.success) {
                       window.location.href = "/order/pay?orderId=" + data.resultObject;
                   } else {
                       showTip(data.errorMessage);
                   }
               });
           }
       });
    });
	
//	点击立即学习时，需要判断是否登录了
	$(".learning_immediately").click(function(){
		
		 var $this = $(this);
		 var watchState = $this.attr("data-watchState");
		 var type = $this.attr("data-type");
		 var collection = $this.attr("data-collection");
		 var realCourseId = $this.attr("data-realCourseId");
		 var collectionCourseId = $this.attr("data-collectionCourseId");
		 
		 if(watchState == 2 && type == 4){ //已报名
			 return;
		 }
		 /**
		  * 判断是否登录了
		  */
		 RequestService("/online/user/isAlive", "GET", null, function(data) {
	           if(!data.success) {
	               $('#login').modal('show');
	           } else {
	        	   if(type == 3){ //已报名
	        		   window.location.href = "/web/livepage/"+realCourseId;
	      		   }else if(type == 1 || type == 2){
	      			   if(collection == 1){
	      				  window.location.href = "/web/html/ccvideo/liveVideoAlbum.html?collectionId="+realCourseId+"&courseId="+collectionCourseId+"&ljxx=ljxx"
	      			   }else{
	      				  window.location.href = "/web/html/ccvideo/video.html?courseId="+realCourseId;
	      			   }
	      		   }
	           }
	     });
	})
	
	
	
});
