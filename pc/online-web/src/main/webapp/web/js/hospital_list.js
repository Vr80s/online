$(function(){
	
	$('.path .hospital').addClass('select');
	//初始化请求信息
	window.current = 1;
	window.size = 20;
	window.name = "";
	window.field=getQueryString('field')?getQueryString('field'):"";
	console.log(field)
	getHostipalList(current,size,name,field); 
	
	
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]); 
    }
    return null;
	}
	//是否有参数
	var url=location.search; 
	if(url.indexOf("?")!=-1){
		$('.hospital_title').text('搜索结果')
	};
	
	//渲染医馆列表方法
	function getHostipalList(current,size,name,field){
	    RequestService("/medical/hospital/getHospitals","GET",{
	    	current:current,
	    	size:size,
	    	name:name,
	    	field:field
	    },function(data){
	        if(data.resultObject.records.length == 0){
	        	//没有数据处理 
	           alert("没有数据/搜索失败")
	        }else{
	        	//获取到数据渲染
	        	if(data.resultObject.records.length < 20){
	        		$('.more_hospital > button').css('display','none');
	        	}
	        	//创建一个盒子
	           $('#hospital_list').append(template('hospitalTpl',{hospital:data.resultObject.records}));
	        }
	    });
	}
	
	//搜索功能
	$('.search_hos_btn').click(function(e){
//		if(e.keyCode==13){
		  var name = $('.search_hos').val();
		  console.log(name)
		   RequestService("/medical/hospital/getHospitals","GET",{
	    	current:current,
	    	size:size,
	    	name:name,
	    	field:field
	    },function(data){
	        if(data.resultObject.records.length == 0){
	        	//没有数据处理 
	           alert("没有数据/搜索失败")
	        }else{
	        	//获取到数据渲染
	        	//创建一个盒子
	           $('#hospital_list').html(template('hospitalTpl',{hospital:data.resultObject.records}));
	        }
	    });
//		}
		});
		
	    
	    //点击更多
	    $('.more_hospital').click(function(){
	    	current +=1;
	    	console.log(current)
	    	getHostipalList(current,size,name);
	    })
	    
	    
	    
	    
})
