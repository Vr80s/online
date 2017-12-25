$(function(){
	
	//获取url中参数值的方法
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return (r[2]); 
    }
    return null;
	}
	
	var writingsId = getQueryString('id');
	
	
	//渲染著作详情数据
	     RequestService("/medical/doctor/getWritingsByWritingsId","GET",{
	     	writingsId:writingsId
	     },function(data){
	       
	       if(data.success == false ||data.resultObject == null){
	       	  $('#book_inf').addClass('hide');
	       	  $('#book_main').addClass('hide');
	       }else{
	       	  //顶部书籍简介
	        $('#book_inf').html(template('book_infTpl',data.resultObject));
	        
	        //内容渲染
	         $('#book_main').html(template('book_mianTpl',data.resultObject));
	         
	         
	         
	          //作者渲染
	         if(data.resultObject.medicalDoctors ==null){
	         	$('.work_right_author').addClass('hide');
	         }else{
	         	 $('#book_author').html(template('book_authorTpl',{author:data.resultObject.medicalDoctors}));
	         }
	       }
	      
	         
	        
	      });
	      
	      
	      
	      
	      //渲染右侧推荐书籍列表
	        RequestService("/medical/doctor/getRecentlyWritings","GET",null,function(data){
				console.log(data);
				if(data.success == false || data.resultObject == null){
					$('.work_dtails_book').addClass('hdie');
				}else{
					 $('#book_list').html(template('book_listTpl',{book:data.resultObject}));
				}
	
		});
})
