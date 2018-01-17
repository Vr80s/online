$(function(){
//	分类渲染
	var noNumber='<p style="font-size:15px;text-aline:center;">暂五数据</p>'
requestService("/bxg/bunch/schoolClass",null,function(data){
	if(data.success==true){
		$('#classify_course_type').html(template('my_data0',{items:data.resultObject[0]}));
		$('#classify_special_type').html(template('my_data1',{items:data.resultObject[1]}));
		$('#classify_mold_type').html(template('my_data2',{items:data.resultObject[2]}));
	}
	else{
		$("#classify_mold_type").html(template.compile(noNumber))
	}
})
//轮播渲染
requestService("/bxg/bunch/recommendTop",null,function(data){
		if(data.success==true){
			$("#sw-slidesss").html(template('data_banner',{items:data.resultObject.banner}))
		}

})




})

