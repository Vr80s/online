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
	window.hospitalId = getQueryString('hospitalId');
	window.name =searchText?searchText:"";
	window.type =getQueryString('type')?getQueryString('type'):"";
	window.departmentId=getQueryString('departmentId')?getQueryString('departmentId'):"";
	getHostipalList(current,size,name,type,departmentId);
	console.log(hospitalId);
	
	//顶部搜索列表title变化
//	if(type == 1){
//		$('.doctor_search_top > h3').text('中青年名中医列表')
//	}else if(type == 2){
//		$('.doctor_search_top > h3').text('名老中医列表')
//	}else if(type == 3){
//		$('.doctor_search_top > h3').text('少数民族中医列表')
//	}else if(type == 4){
//		$('.doctor_search_top > h3').text('国医大师列表')
//	}else if(type == 5){
//		$('.doctor_search_top > h3').text('家传中医列表')
//	}

	console.log(decodeURI(name))
//	渲染到所搜栏中
	if(decodeURI(name)){
		$('.doctor_search_ipt > input').val(decodeURI(name));
	}else{
		$('.doctor_search_ipt > input').val('');
	}
	
	
	//渲染医师列表方法
	function getHostipalList(current,size,name,type,departmentId){
	    RequestService("/medical/doctor/getDoctors","GET",{ 
	    	current:current,
	    	size:size,
	    	name:name,
	    	type:type,
	    	departmentId:departmentId,
	    	hospitalId:hospitalId
	    },function(data){
	    	$('.search_more').css('display','block')
	        if(data.resultObject.records.length == 0){
	        	//没有数据处理 
//	           alert("没有数据/搜索失败")
				$('#doctor_list').html('<div style="padding-top:100px;text-align:center"><img src="/web/images/nosearch.png" alt="" /><p style="font-size:16px;color:#999">抱歉，没有找到“<span style="color:#00BC12">'+name+'</span>”相关医师</p></div>');
				$('.search_more').css('display','none')
				$('#search_num').text('共找到'+data.resultObject.total+'位名医')
	        }else{
	        	//获取到数据渲染
	        	//创建一个盒子
//	        	$('#doctor_list').html('');
	        	if(data.resultObject.pages == current){
	        		$('.search_more').css('display','none')
	        	}
	        	$('#search_num').text('共找到'+data.resultObject.total+'位名医')
	           $('#doctor_list').append(template('doctorListTpl',data.resultObject));
	        }
	    });
	}
	
	//搜索功能
	$('.doctor_search_ipt > button').click(function(e){
		 current = 1;
//		if(e.keyCode==13){
		$('#doctor_list').html('');
		  var name =$('.doctor_search_ipt > input').val();
		  var departmentId; 
		  if($('#doctor_search_condition2').hasClass('hide')){
		  	departmentId = '';
		  }else{
		  	departmentId = $('#doctor_search_condition2 span').attr('data-id');
		  }
		  
		  var type;
		  if($('#doctor_search_condition1').hasClass('hide')){
		  	type = '';
		  }else{
		  	type = $('#doctor_search_condition1 span').attr('data-type');
		  }
		  
//		  console.log(name)
		  getHostipalList(current,size,name,type,departmentId);
//		}
		});

	    
	    //点击更多
	    $('.more_doctor').click(function(){
	    	current +=1;
	    	console.log(current)
	    	getHostipalList(current,size,name,type,departmentId);
	    })
	    
	    
	    //名师推荐部分
	     RequestService("/medical/doctor/getRecDoctors","GET",null,function(data){
	       if(data.resultObject == null || data.resultObject.length == 0){
	       	$('.about_doctor').addClass('hide');
	       }else{
	       	$('#doc_rec').html(template('doc_recTpl',{doc:data.resultObject}));
	       }
	        console.log(data);
	       
	        
	      });
//	    });
	    
	    
	      //名医筛选条件的科室
	    RequestService("/medical/doctor/apply/listDepartment/0","GET",null,function(data){
	       if(data.resultObject == null || data.resultObject.records.length == 0){
	   			alert('未获取到筛选中的科室')
	       }else{
	       	$('#doctor_search_keshi').html(template('doctor_search_keshi_Tpl',{item:data.resultObject.records}));
	       }
	    },false);
	    
	    
	    //名医筛选条件的分类
	    RequestService("/medical/doctor/getDoctorType","GET",null,function(data){
	       if(data.resultObject == null || data.resultObject.length == 0){
	       		alert('未获取到筛选中的分类')
	       }else{
	       	$('#doctor_search_class').html(template('doctor_search_class_Tpl',{item:data.resultObject}));
//	       	$('#doctor_search_class li a[data-type = '+type+']').addClass('color');
			if(type){
				fenleiClick(type)
			}
			if(departmentId){
				keshiClick(departmentId)
			}
	       
	       }
	    });
	    
	    
		
	    
	   
	    
	    
	    //分类
	    function fenleiClick(type){
	    	$('#doctor_search_class li a ').removeClass('color');
			$('#doctor_search_class li a[data-type = '+type+']').addClass('color');
			//筛选条件部分变化
			$('#doctor_search_condition3').addClass('hide')
			$('#doctor_search_condition1').removeClass('hide')
			$('#doctor_search_condition1 span').text( 	$('#doctor_search_class li a[data-type = '+type+']').text());
			$('#doctor_search_condition1 span').attr('data-type', 	$('#doctor_search_class li a[data-type = '+type+']').attr('data-type'))
			if( $('#doctor_search_class li a[data-type = '+type+']').text() == '全部'){
				$('#doctor_search_condition1').addClass('hide');
				if($('#doctor_search_condition2').hasClass('hide')){
	    			$('#doctor_search_condition3').removeClass('hide')
	    		}
			}
	    }
	    
	    //科室
	    function keshiClick(departmentId){
	    	$('#doctor_search_keshi li a ').removeClass('color');
			$('#doctor_search_keshi li a[data-id='+departmentId+']').addClass('color');
			//筛选条件部分变化
			$('#doctor_search_condition3').addClass('hide')
			$('#doctor_search_condition2').removeClass('hide')
			$('#doctor_search_condition2 span').text($('#doctor_search_keshi li a[data-id='+departmentId+']').text());
			$('#doctor_search_condition2 span').attr('data-id',	departmentId)
			if($(this).text() == '全部'){
				$('#doctor_search_condition2').addClass('hide');
				if($('#doctor_search_condition1').hasClass('hide')){
	    			$('#doctor_search_condition3').removeClass('hide')
	    		}
			}
	    }
	    
	    //筛选部分选中变化
	    
//			if(type == 1){
//				$('#doctor_search_class li a[data-type = 1]').click();
//			}else if(type == 2){
//				$('#doctor_search_class li a[data-type = 2]').trigger("click");
//			}else if(type == 3){
//				$('#doctor_search_class li a[data-type = 3]').click();
//			}else if(type == 4){
//				$('#doctor_search_class li a[data-type = 4]').click();
//			}else if(type == 5){
//				$('#doctor_search_class li a[data-type = 5]').click();
//			}
	
	    
		//名医分类筛选效果
		$('#doctor_search_class').on('click','a',function(){
			$('#doctor_search_class li a ').removeClass('color');
			$(this).addClass('color');
			//筛选条件部分变化
			$('#doctor_search_condition3').addClass('hide')
			$('#doctor_search_condition1').removeClass('hide')
			$('#doctor_search_condition1 span').text($(this).text());
			$('#doctor_search_condition1 span').attr('data-type',$(this).attr('data-type'))
			if($(this).text() == '全部'){
				$('#doctor_search_condition1').addClass('hide');
				if($('#doctor_search_condition2').hasClass('hide')){
	    			$('#doctor_search_condition3').removeClass('hide')
	    		}
			}
				//触发搜索功能
			$('.doctor_search_ipt > button').click()
		})
	    
	    //名医科室筛选效果
	    $('#doctor_search_keshi').on('click','a',function(){
			$('#doctor_search_keshi li a ').removeClass('color');
			$(this).addClass('color');
			//筛选条件部分变化
			$('#doctor_search_condition3').addClass('hide')
			$('#doctor_search_condition2').removeClass('hide')
			$('#doctor_search_condition2 span').text($(this).text());
			$('#doctor_search_condition2 span').attr('data-id',$(this).attr('data-id'))
			if($(this).text() == '全部'){
				$('#doctor_search_condition2').addClass('hide');
				if($('#doctor_search_condition1').hasClass('hide')){
	    			$('#doctor_search_condition3').removeClass('hide')
	    		}
			}
			//触发搜索功能
		$('.doctor_search_ipt > button').click()
		})
	    
	    //分类筛选条件删除效果
	    $('#doctor_search_condition1 a').click(function(){
	    	$(this).parent().addClass('hide');
	    	$('#doctor_search_class li a ').removeClass('color');
	    	$('#doctor_search_class li:first-child a ').addClass('color');
	    	if($('#doctor_search_condition2').hasClass('hide')){
	    		$('#doctor_search_condition3').removeClass('hide')
	    	}
	    	$('.doctor_search_ipt > button').click()
	    })
	    
	    //可是筛选条件删除效果
	    $('#doctor_search_condition2 a').click(function(){
	    	$(this).parent().addClass('hide');
	    	$('#doctor_search_keshi li a ').removeClass('color');
	    	$('#doctor_search_keshi li:first-child a').addClass('color');
	    	if($('#doctor_search_condition1').hasClass('hide')){
	    		$('#doctor_search_condition3').removeClass('hide')
	    	}
	    	$('.doctor_search_ipt > button').click()
	    })
	    
})
