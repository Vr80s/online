//搜索历史结果开始
$(function(){
requestService("/xczh/recommend/queryAllCourse",null, 
    function(data) {
		
		if(data.success==true){
			
//			检索列表
 	    	$(".all_classify_mainss").html(template('all_classify_main',{items:data.resultObject}))		
		}
		
},false) 	


requestService("/xczh/classify/listScreen",null, 
    function(data) {
		
		if(data.success==true){
//			点击筛选--分类
 	    	$(".all_right_type_ones").html(template('all_right_type_ones',{items:data.resultObject[0]}))
// 	    	点击筛选--城市
 	    	$(".all_right_type_twos").html(template('all_right_type_twos',{items:data.resultObject[0]}))
				
				
				
		}
		
},false)
})




//搜索历史结果结束










