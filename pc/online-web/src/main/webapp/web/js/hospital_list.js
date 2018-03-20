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
	
	//	渲染到所搜栏中
	if(searchText){
		$('.search_hos_box > input').val(searchText);
	}else{
		$('.search_hos_box > input').val('');
	}
	
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
//	           $('#hospital_list').html('<h3>暂无数据<h3>');
				if(name){
	           $('#hospital_list').html('<div style="padding-top:100px;text-align:center"><img src="/web/images/nosearch.png" alt="" /><p style="font-size:16px;color:#999">抱歉，没有找到“<span style="color:#00BC12">'+name+'</span>”相关医馆</p></div>')	
				}else{
	           $('#hospital_list').html('<div style="padding-top:100px;text-align:center"><img src="/web/images/nosearch.png" alt="" /><p style="font-size:16px;color:#999">抱歉，没有找到相关医馆</p></div>')
				}

//	           $('.more_hospital').css('display','none');
				$('.more_hospital').addClass('hide');
	        }else if(current == data.resultObject.pages){
	        	if( current == 1){
	         	$('#hospital_list').html('')
	        	$('#hospital_list').append(template('hospitalNumTpl',data.resultObject));
	        		
	        	}


	        	$('#hospital_list').append(template('hospitalTpl',{hospital:data.resultObject.records}));
	        	$('.more_hospital').addClass('hide');
	        }else{
	        	if( current == 1){
	        		$('#hospital_list').html('')
	        	$('#hospital_list').append(template('hospitalNumTpl',data.resultObject));
	        		
	        	}
	        		
	        	//获取到数据渲染
	        	if(data.resultObject.pages > 1){
	        		$('.more_hospital').removeClass('hide');
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
//		  页码变成1
		current=1;
		$('#doctor_list').html('');
		  console.log(name)
//		   RequestService("/medical/hospital/getHospitals","GET",{
//	    	current:current,
//	    	size:size,
//	    	name:name,
//	    	field:field
//	    },function(data){
//	        if(data.resultObject.records.length == 0){
//	        	//没有数据处理 
////	           alert("没有数据/搜索失败")
//	            $('#hospital_list').html('<h3>暂无数据<h3>');
//	        }else{
//	        	//获取到数据渲染
//	        	//创建一个盒子
//	           $('#hospital_list').html(template('hospitalTpl',{hospital:data.resultObject.records}));
//	        }
//	    });

   		  var field; 
		  if($('#hos_search_condition1').hasClass('hide')){
		  	field = '';
		  }else{
		  	field = $('#hos_search_condition1 span').attr('data-fileid');
		  }
		  
			getHostipalList(current,size,name,field);
//		}
		});
		
	    
	    //点击更多
	    $('.more_hospital>button').click(function(){
	    	current +=1;
	    	console.log(current)
			var name = $('.search_hos').val();
	    	var field; 
		    if($('#hos_search_condition1').hasClass('hide')){
		  	  field = '';
		    }else{
		  	  field = $('#hos_search_condition1 span').attr('data-fileid');
		    	}
	    	getHostipalList(current,size,name,field);
	    })
	    
	    
	    //医馆筛选领域列表渲染
	    RequestService("/medical/hospital/getFields/0","GET",null,function(data){
	       if(data.resultObject == null || data.resultObject.records.length == 0){
	       		alert('未获取到筛选中的分类')
	       }else{
	       	console.log(data)
	       	$('#hos_search_area').html(template('hos_search_area_Tpl',{item:data.resultObject.records}));
//	       	$('#doctor_search_class li a[data-type = '+type+']').addClass('color');
//			if(type){
//				fenleiClick(type)
//			}
//			if(departmentId){
//				keshiClick(departmentId)
//			}
			   if(field){
			   	 filedClick(field)
			   }
	       }
	    });
	    
	      
	    //医馆领域筛选效果
	    $('#hos_search_area').on('click','a',function(){
			$('#hos_search_area li a ').removeClass('color');
			$(this).addClass('color');
			//筛选条件部分变化
			$('#hos_search_condition3').addClass('hide')
			$('#hos_search_condition1').removeClass('hide')
			$('#hos_search_condition1 span').text($(this).text());
			$('#hos_search_condition1 span').attr('data-fileid',$(this).attr('data-fileid'))
			if($(this).text() == '全部'){
				$('#hos_search_condition1').addClass('hide');
				$('#hos_search_condition3').removeClass('hide')
			}
			//触发搜索功能
			$('#hospital_list').html('')
			$('.search_hos_btn ').click()
		})
	    
	    function filedClick(field){
			$('#hos_search_area li a ').removeClass('color');
			$('#hos_search_area li a[data-fileid='+field+']').addClass('color');
			//筛选条件部分变化
			$('#hos_search_condition3').addClass('hide')
			$('#hos_search_condition1').removeClass('hide')
			$('#hos_search_condition1 span').text($('#hos_search_area li a[data-fileid='+field+']').text());
			$('#hos_search_condition1 span').attr('data-fileid',$('#hos_search_area li a[data-fileid='+field+']').attr('data-fileid'))
			if($('#hos_search_area li a[data-fileid='+field+']').text() == '全部'){
				$('#hos_search_condition1').addClass('hide');
				$('#hos_search_condition3').removeClass('hide')
			}
			//触发搜索功能
			$('#hospital_list').html('')
			$('.search_hos_btn ').click()
	    }
	    
	    
	    //医馆领域筛选删除效果
	    $('#hos_search_condition1 a').click(function(){
	    	$(this).parent().addClass('hide');
	    	$('#hos_search_area li a ').removeClass('color');
	    	$('#hos_search_area li:first-child a').addClass('color');
	    	$('#hos_search_condition3').removeClass('hide')
	    	$('.search_hos_btn ').click()
	    })
	    
	    var areaStatus = 1;
	    $('.more_areaBtn').click(function(){
	    	areaStatus *= -1;
	    	if(areaStatus < 0){
	    		$(this).text('收起')
	    		$('#hos_search_area').attr('style','height:auto')
	    		$(this).attr('style','background:url(/web/images/up_arrow.png) 55px center no-repeat')
	    	}else{
	    		$(this).text('更多')
	    		$('#hos_search_area').attr('style','height:73px;overflow: hidden;')
	    		$(this).attr('style','background:url(/web/images/down_arr.png) 55px center no-repeat')
	    	}
	    	
	    })
})
