$(function(){
	
	//头部的医师变色
	$('.path .forum').addClass('select');
	
	
	//设置初始化数据
	window.current = 1;
	window.size = 7;
	window.doctorId = getQueryString('doctorId')?getQueryString('doctorId'):'';
	
	
	
	
	//获取url中参数值的方法
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return (r[2]); 
    }
    return null;
	}
	
	var doctorId =  getQueryString('doctorId');
	
	
	    // 报道详情页面数据请求
	    RequestService("/doctor/getSpecialColumnsByPage","GET",{
	    	current:current,
	    	size:size,
	    	doctorId:doctorId
	    },function(data){
	        if(data.success==false ||data.resultObject==null|| data.resultObject.length == 0){
	           $('#doctor_book_list').addClass('hide')
	        }else{
	        	//获取到数据渲染
	           $('#doctor_book_list').html(template('zhuanlan_listTpl',{inf:data.resultObject.records}));
	           
	        
	          
	        }
	    });
	    
	    
	    
	     // 获取热门专栏作者
	    RequestService("/doctor/getHotSpecialColumnAuthor","GET",null,function(data){
	        if(data.success==false ||data.resultObject==null|| data.resultObject.length == 0){
	           $('#doctor_book_list').addClass('hide')
	        }else{
	        	//获取到数据渲染
	           $('#about_doctor_list').html(template('about_doctorTpl',{inf:data.resultObject}));
	        }
	    });
	    
	    
	    //获取作者医师信息
	     RequestService("/doctor/getDoctorById","GET",{
	     	id:doctorId
	     },function(data){
	    	console.log(data);
	        if(data.success==false ||data.resultObject==null){
	           $('.doctor_book_top').addClass('hide')
	        }else{
	        	//获取到数据渲染
	        	console.log(data)
	           $('#doctor_inf').html(template('doctor_infTpl',data.resultObject));
	        }
	    });
	    
	    
	    
})
