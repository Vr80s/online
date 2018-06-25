	
/*var openId = getQueryString("openId");
if(stringnull(openId)){
    localStorage.setItem("openid",openId);
}*/

$(function(){

    requestGetService("/xczh/medical/doctorPostsList", {
        pageNumber: 1,
        pageSize:10,
        doctorId:"b8e9430bd4334d749f06d2f0050dd66e"
    }, function (data) {
        var obj =  data.resultObject;
        $(".rests_nav").html(template('wrap_doctor_dynamics',{items:data.resultObject.records}));


    });



});
/**
 * 返回上一页
 */
function goto_back(){

	//去中医师主页
	location.href="/xcview/html/physician/index.html";
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





