

/**
 * 进入购买成功后的页面
 */
var courseId = getQueryString("courseId");
// var watchState = getQueryString("watchState");

/**
 * 根据课程id得到课程简单信息   watchState
 */

var type ="";
var lineState="";
var collection="";
requestService("/xczh/course/details",{courseId:courseId}, function(data) {
	 if (data.success) {
         var result = data.resultObject;
         // var results = data.resultObject.watchState;
         // var results = data.watchState;

         $(".purchase_details_title").find(".span1").html(result.gradeName);
         $(".purchase_details_title").find(".span0").html(result.name);


         if(result.watchState==1){
            $(".purchase_details_money").find(".span").html('免费');
            $(".purchase_details_money .span_fize").hide();
            $(".purchase_details_money .span").css("font-size","0.28rem");
         }else{
            $(".purchase_details_money").find(".span").html(result.currentPrice);
         };

         $("#smallImgPath").attr("src",result.smallImgPath + '?imageView2/2/w/212' );
         
         type= result.type;
         lineState = result.lineState;
         collection=result.collection;
         
         /**
          * 判断是不是线下课
          */
         if(type == 4){ //线下课
        	  $(".hint").show();
        	  /**
        	   * 查询这个信息
        	   */
        	  requestGetService("/xczh/apply/applyInfo",{courseId:courseId}, function(data) {
              	 if (data.success) {
                       var result = data.resultObject.applyInfo;
                       $(".hint_name_right").text(result.realName);
                       $(".hint_tel_right").text(result.mobile);
                       $(".hint_wx_right").text(result.wechatNo);
                       if(result.sex==0){
                    	   $(".hint_sex_right").html("女");
                       }else if(result.sex==1){
                    	   $(".hint_sex_right").html("男");
                       }else{
                    	   $(".hint_sex_right").html("未知");
                       }
                  }
              },false)
         }
         
         /**
          * 猜你喜欢
          */
         requestService("/xczh/course/guessYouLike",{courseId:courseId}, function(data) {
        	 if (data.success) {
                 var result = data.resultObject.records;
                 $(".lectures").html(template('lectures',{items:result}))
             }
        },false)
     }
},false)

/**
 * 去直播
 * @param id
 */
function jump(){	
	var id = courseId;
	if(type ==3 && (lineState ==1 || lineState ==3 || lineState ==4)){ //直播间  
		 //增加一条学习记录
		 requestService("/xczh/history/add",
	               {courseId:id,recordType:2} ,function(data) {
	     }) 
		 location.href="details.html?courseId="+id
	}else if(type ==3 && (lineState ==2 || lineState ==5)){ //预告的、回放的
		location.href="live_play.html?my_study="+id
	}else if((type ==1 || type ==2) && !collection){ //课程页面
		location.href="live_audio.html?my_study="+id
	}else if(type ==4){								 //线下培训班
		location.href="live_class.html?my_study="+id
	}else if((type ==1 || type ==2) && collection){
		location.href="live_select_album.html?course_id="+id
	}
}

/**
 * 去学习中心
 */
$(".check_one span").click(function(){
	
	location.href="/xcview/html/my_study.html";
})
/**
 * 学堂
 */
$(".check_two span,.header_return").click(function(){
	  sessionStorage.setItem("school_index",1);	
	location.href="/xcview/html/home_page.html";
})






