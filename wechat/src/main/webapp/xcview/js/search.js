

var curriculum_blck = getQueryString("search_back");
sessionStorage.setItem("search_back",curriculum_blck);

/**
 * 
 */
function goto_back(){
	var back_falg  = sessionStorage.getItem("search_back");
	if(back_falg == 1){
		location.href='home_page.html';
	}else if(back_falg == 2){
		location.href='curriculum_table.html';
	}
}



$(function(){


//搜索历史开始

requestService("/xczh/bunch/hotSearch",null, 
    function(data) {
		
		if(data.success==true){
//			检索列表
 	    	$(".search_hot_main").html(template('search1',{items:data.resultObject.hotSearch}))
// 	    	<!--给inpiu默认值-->
 	    	$(".div_span_input").html(template('shipin',{items:data.resultObject.defaultSearch}))
			
 	    	localStorage.setItem("defaultKey", data.resultObject.defaultSearch);
		}
},false) 
//搜索历史结束

	//点击热门搜索跳转
	$(".search_hot_main_one").click(function(){
		var btn_write=$(this).text()
//		arr.unshift(btn_write);
		 //判断该记录是否已存在
						if($.inArray(btn_write, arr)!=-1){
                            removeByValue(arr, btn_write);
                            arr.unshift(btn_write);
                            localStorage.hisArr = arr;

						}else{
                            arr.unshift(btn_write)
                            localStorage.hisArr = arr;

						}
//		localStorage.hisArr = arr;
		window.location.href="curriculum_table.html?queryKey="+btn_write+""
	})

})

//清空历史
function clearAll() {
	if(localStorage.hisArr!=undefined){
        localStorage.hisArr="";
        $(".search_history_list").empty();
        $(".search_history").hide();
        arr = [];
	}


}