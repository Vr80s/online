$(function(){
	$('.path .doctor').addClass('select');
	//获取url中参数值的方法
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
    var searchText =decodeURI(getQueryString("name"));   //decodeURI解码  
//  console.log(searchText);

	//初始化请求信息
	window.current = 1;
	window.size = 8;
	window.name =searchText?searchText:"";
	window.type =getQueryString('type')?getQueryString('type'):"";
	window.field=getQueryString('field')?getQueryString('field'):"";
	getHostipalList(current,size,name,type,field);
	
	
	//顶部搜索列表title变化
	if(type == 1){
		$('.doctor_search_top > h3').text('名青年中医列表')
	}else if(type == 2){
		$('.doctor_search_top > h3').text('名老中医列表')
	}else if(type == 3){
		$('.doctor_search_top > h3').text('少数民族中医列表')
	}else if(type == 4){
		$('.doctor_search_top > h3').text('国医大师列表')
	}else if(type == 5){
		$('.doctor_search_top > h3').text('古中医列表')
	}
	
	console.log(decodeURI(name))
	//渲染医师列表方法
	function getHostipalList(current,size,name,type,field){
	    RequestService("/medical/doctor/getDoctors","GET",{ 
	    	current:current,
	    	size:size,
	    	name:name,
	    	type:type,
	    	field:field
	    },function(data){
	        if(data.resultObject.records.length == 0){
	        	//没有数据处理 
//	           alert("没有数据/搜索失败")
				$('#doctor_list').html('<h3>暂无数据</h3>');
				$('.search_more').css('display','none')
	        }else{
	        	//获取到数据渲染
	        	//创建一个盒子
//	        	$('#doctor_list').html('');
	        	if(data.resultObject.pages == current){
	        		$('.search_more').css('display','none')
	        	}
	           $('#doctor_list').append(template('doctorListTpl',data.resultObject));
	        }
	    });
	}
	
	//搜索功能
	$('.doctor_search_ipt > button').click(function(e){
//		if(e.keyCode==13){
		$('#doctor_list').html('');
		  var name =$('.doctor_search_ipt > input').val();
		  console.log(name)
		  getHostipalList(current,size,name,type,field);
//		}
		});

	    
	    //点击更多
	    $('.more_doctor').click(function(){
	    	current +=1;
	    	console.log(current)
	    	getHostipalList(current,size,name,type,field);
	    })
	    
	    
	    //名师推荐部分
	     RequestService("/medical/doctor/getRecDoctors","GET",null,function(data){
	       
	        console.log(data);
	        $('#doc_rec').html(template('doc_recTpl',{doc:data.resultObject}));
	        
	      });
//	    });
	    
	    
	    
})
