$(function(){
	
	
	//初始化请求信息
	window.current = 1;
	window.size = 20;
	window.name = "";
	getHostipalList(current,size,name);
	
	
	//渲染医馆列表方法
	function getHostipalList(current,size,name){
	    RequestService("/getHospitals","GET",{
	    	current:current,
	    	size:size,
	    	name:name
	    },function(data){
	        if(data.resultObject.records.length == 0){
	        	//没有数据处理
	           alert("没有数据/搜索失败")
	        }else{
	        	//获取到数据渲染
	        	//创建一个盒子
	           $('#hospital_list').append(template('hospitalTpl',{hospital:data.resultObject.records}));
	        }
	    });
	}
	
	//搜索功能
	$('.search_hos').keydown(function(e){
		if(e.keyCode==13){
		  var name = $('.search_hos').val();
		  console.log(name)
		  getHostipalList(current,size,name);
		}
		});
		
	    
	    //点击更多
	    $('.more_hospital').click(function(){
	    	current +=1;
	    	console.log(current)
	    	getHostipalList(current,size,name);
	    })
	    
	    
	    
	    
})
