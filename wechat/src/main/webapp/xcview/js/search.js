$(function(){


//搜索历史开始

requestService("/xczh/bunch/hotSearch",null, 
    function(data) {
		
		if(data.success==true){
			
//			检索列表
 	    	$(".search_hot_main").html(template('search1',{items:data.resultObject.hotSearch}))
 	    	
// 	    	<!--给inpiu默认值-->
 	    	$(".div_span_input").html(template('shipin',{items:data.resultObject.defaultSearch}))
				/*var myHeight=$(".tjks").height();
	
				$(".gieTa").height(myHeight);*/
				
		}
		
		
		
},false) 
//搜索历史结束

//点击热门搜索跳转
$(".search_hot_main_one").click(function(){
	var btn_write=$(this).text()
	window.location.href="curriculum_table.html?search="+btn_write+""
})






})

