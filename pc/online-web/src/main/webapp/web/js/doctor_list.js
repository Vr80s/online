$(function(){
	//获取url中参数值的方法
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]); 
    }
    return null;
	}
	var type = getQueryString('type');


	//初始化请求信息
	window.current = 1;
	window.size = 8;
	window.name = "";
	window.type =getQueryString(type)?getQueryString(type):"";
	getHostipalList(current,size,name,type);
	
	
	//渲染医师列表方法
	function getHostipalList(current,size,name,type){
	    RequestService("/medical/doctor/getDoctors","GET",{ 
	    	current:current,
	    	size:size,
	    	name:name,
	    	type:type
	    },function(data){
	        if(data.resultObject.records.length == 0){
	        	//没有数据处理 
	           alert("没有数据/搜索失败")
	        }else{
	        	//获取到数据渲染
	        	//创建一个盒子
	           $('#doctor_list').append(template('doctorListTpl',{doctor:data.resultObject.records}));
	        }
	    });
	}
	
	//搜索功能
	$('.doctor_search_ipt > button').click(function(e){
//		if(e.keyCode==13){
		  var name =$('.doctor_search_ipt > input').val();
		  console.log(name)
		  getHostipalList(current,size,name);
//		}
		});
		
	    
	    //点击更多
	    $('.more_doctor').click(function(){
	    	current +=1;
	    	console.log(current)
	    	getHostipalList(current,size,name);
	    })
	    
	    
	    
	    
})
