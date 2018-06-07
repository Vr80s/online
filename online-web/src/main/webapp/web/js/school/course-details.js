



var loginUserId = "";
var loginStatus = true;
var smallHeadPhoto ="";
 RequestService("/online/user/isAlive", "GET", null, function (data) {
    if (data.success) {
    	loginUserId = data.resultObject.id;
    	smallHeadPhoto = data.resultObject.smallHeadPhoto;
    	loginStatus = true;
    }else{
    	loginStatus = false;
    }
},false)

//<div class="path" style="width:95%"><a href="/courses/recommendation" 
//	class="recommend" target="_blank">推荐</a><a href="/courses/real" 
//		class="under" target="_blank" style="color: rgb(0, 188, 18);">线下课</a>
//		<a href="/courses/live" class="broadcast" target="_blank">直播</a>
//		<a href="/courses/listen" class="listen" target="_blank">听课</a><
//		a href="/App.html" class="download-app" target="_blank">下载APP</a></div>

$(function(){

	var index = 0;
	/**
	 * 
	 */
	if(collection == 1 &&  watchState ==0){ //专辑付费，删除选集、显示大纲
		$(".buy_tab").remove();
		$(".no_buy_tab").removeClass("hide");
		
		if(type == "info"){
			$(".wrap-sidebar ul li").eq(0).addClass("active-footer");
		}
		
	}else if(collection == 1 &&  watchState !=0){  //专辑免费或已购买，显示选集、删除大纲
		$(".no_buy_tab").remove();
		$(".buy_tab").removeClass("hide");
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

	//课程类型 1：视频 2：音频 3：直播 4：线下培训班
	if(courseType == 4){
		//$(".sidebar-content").addClass("hide").eq(index).removeClass("hide");
		
		$(".under").css("color","rgb(0, 188, 18)");
	}else if(courseType == 3){
		$(".broadcast").css("color","rgb(0, 188, 18)");
	}else if(courseType == 1 || courseType == 2){
		$(".listen").css("color","rgb(0, 188, 18)");
	}
	
	
	
	
	
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
		 
		 if(watchState == 1 && type == 4){ //免费未报名
			
			 
		 }
		 
		 if(watchState == 0 && type == 4){ //收费未报名
			
			 
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


//判断进入条	
	
	/**
	 * 得到这个记录
	 */
	var key = loginUserId + courseId;
	var recordingList = localStorage.getItem(key);
	/**
	 * 播放进度条
	 */
	if(collection == 0 && recordingList!=null ){  //单个课程
		//秒转换为分钟
		var lookRecord  = parseFloat(recordingList)/60
		var progressBar  = (lookRecord/courseLength)*100;
		
		if(progressBar>100){
			progressBar=100;
		}
		$(".progress-bar-success").css("width",progressBar+"%");
	}else if(recordingList!=null){				//专辑
		var key = loginUserId + courseId;
		
		if(recordingList!=null || recordingList!=undefined ){
			var re = new RegExp("%","i");
			var fristArr = recordingList.split(re);
			var arr = [];
			for(var i =0; i<fristArr.length; i++){
				var arrI = fristArr[i];
				if(arrI!=""){
					var  obj ={}
					var lalaArr = arrI.split("=");
				    obj[lalaArr[0]] = lalaArr[1];
					arr.push(obj);
				}
			}
		    console.log(arr);
		   //进行循环啦
			$(".wrap-anthology .left").each(function(){
				var $this = $(this);
		        var courseId =$this.attr("data-courseId");
		        //分钟
		        var timeLength =$this.attr("data-timeLength");
		        var recording =0;
		        for (var i = 0; i < arr.length; i++) {
		        	var json = arr[i];
		        	for (var key in json) {
		 	        	if(courseId == key){
		 	        		recording = json[key];
		 	        		break;
		 	        	}
		 	        }
				}
		        if(recording <=0){
		        	return true; //结束本地
		        }
		        var lookRecord  = parseFloat(recording)/60
				var percent  = (lookRecord/timeLength)*100;
		        
		        if(percent>100){
		        	percent = 100;
		        }
		        if (percent > 100) {
	 	    		percent = 0;
	 	    		$this.parent().removeClass('clip-auto');
	 	    		$this.next().addClass('wth0');
	 	    	} else if (percent > 50) {
	 	    		$this.parent().addClass('clip-auto');
	 	    		$this.next().removeClass('wth0');
	 	    	}
	 	    	$this.css("-webkit-transform", "rotate(" + (18 / 5) * percent + "deg)");
			})
		}
	}
	

	
});
