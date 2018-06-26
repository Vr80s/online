	
/*var openId = getQueryString("openId");
if(stringnull(openId)){
    localStorage.setItem("openid",openId);
}*/

$(function(){
    var loginUserId = localStorage.getItem("userId");
    requestGetService("/xczh/medical/doctorPostsList", {
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


    });

    $('.zan_img').each(function(){
        $(this).click(function() {
            var src = $(this).find('img').attr('src');
            var postsId = $(this).attr('data-id');
            if(src.indexOf("zan001")>-1){
                $(this).find('img').attr('src','../images/zan01.png');
                updatePraise(criticize_id,false);
            }else{
                $(this).find('img').attr('src','../images/zan001.png');
                updatePraise(criticize_id,true);
            }
        })
    })

});
/**
 * 返回上一页
 */
function goto_back(){

	//去中医师主页
	location.href="/xcview/html/physician/index.html";
}

/**
 * 点赞
 */
function postsLike(postsId) {
    requestService("/xczh/medical/addDoctorPostsLike",{
        postsId:postsId
    },function(data) {
        if(data.success==true){
            $(this).find('img').attr('src','../images/zan001.png');
            alert(data.resultObject);
        }else{
            alert(data.errorMessage);
        }
    });
}
/**
 * 取消点赞
 */
function postsLike(postsId) {
    requestService("/xczh/medical/addDoctorPostsLike",{
        postsId:postsId
    },function(data) {
        if(data.success==true){
            $(this).find('img').attr('src','../images/zan001.png');
            alert(data.resultObject);
        }else{
            alert(data.errorMessage);
        }
    });
}




function queryDataByParams(params,data_type){

	requestService("/xczh/doctors/list",params,function(data){

		     var id = "#draw_all_query_list";
		     if(data.success==true){

		    	if(stringnull(data_type)){
				   id = "#query_list"+data_type;
				}
		    	var data1 ="";
				$(id).html(data1);

		    	// 判断有无数据显示隐藏背景图
				var index = $(".find_nav_cur a").attr("data-title");
				if(data.resultObject.length<=0){
					$(".li_list_main").css("background","#f8f8f8");
					$(".no_class").hide();
					$(".no_class"+index).show();
				}else{
					$(".li_list_main").css("background","#fff");
					$(".no_class").hide();
				}



				for (var int = 0; int < data.resultObject.length; int++) {
					var item = data.resultObject[int];

					data1+="<div class='li_list_div' >"+
				       "<div class='li_list_one' data-courseId = "+item.id+" >"+
					       "<div class='li_list_one_left'>" +
					         "<img src='"+item.headPortrait+"?imageView2/2/w/212' class='one' />"  +
					      "</div>" +
				           "<div class='li_list_one_right'>" +
					           "<p class='p00'>" +
					           "<span class='span'>"+item.name+"</span>" +
					           "<span class='duty'>"+(item.title = item.title==null ? "" : item.title)+"</span></p>" +
					           "<p class='site'>"+(item.hospitalAddress = item.hospitalAddress==null ? "" : item.hospitalAddress)+"</p>"+
				            "</div>" +
				         "</div>" +
				     "</div>";
				}

			 $(id).html(data1);

			 /**
			  * 点击页面进行跳转
			  */
			 $(".li_list_div .li_list_one").click(function(){


			})
		}else{
			$(".no_class").show();
			$(".li_list_main").css("background","#f8f8f8");
			alert("查询数据结果error!");
		}
	})
}





