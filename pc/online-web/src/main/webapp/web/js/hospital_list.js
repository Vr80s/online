$(function(){
	
	$('.path .hospital').addClass('select');
	
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return (r[2]); 
    }
    return null;
	}
	
	
	//解码
	var searchUrl =window.location.href;  
    var searchData =searchUrl.split("=");        //截取 url中的“=”,获得“=”后面的参数  
    var  searchText =decodeURI(getQueryString("name"));   //decodeURI解码  
	
	//初始化请求信息
	window.current = 1;
	window.size = 20;
	window.name =searchText?searchText:"";
	window.field=getQueryString('field')?getQueryString('field'):"";
	console.log(field)
	getHostipalList(current,size,name,field); 
	
	
	
	
	
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
//	           alert("没有数据/搜索失败")
	           $('#hospital_list').html('<h3>暂无数据<h3>');
	           $('.more_hospital').css('display','none');
	        }else{
	        	//获取到数据渲染
	        	if(data.resultObject.pages == current){
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
//	           alert("没有数据/搜索失败")
	            $('#hospital_list').html('<h3>暂无数据<h3>');
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