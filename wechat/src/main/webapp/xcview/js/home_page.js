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


//轮播/大师课/名医渲染
requestService("/bxg/bunch/recommendTop",null, 
    function(data) {
		if(data.success){
//大师课
    	$("#slide_one").html(template('nav_list',{items:data.resultObject.project}))
//名医
    	$("#phy_box").html(template('wrap_phy',{items:data.resultObject.doctorList.records}))
		
//轮播
			var result = data.resultObject.banner;
			var str ="";
			for (var int = 0; int < result.length; int++) {
				var wb = result[int];
				str+="<li class='sw-slide'>"+
		            "<img src='"+wb.imgUrl+"' alt='Concept for children game'>" +
		          "</li>";
			}
			$("#sw-slides").html(str);
		}else{
			alert("网络异常");
		};
},false)

})

