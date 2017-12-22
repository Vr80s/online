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
	
	var articleId =  getQueryString('id');
	
	
	    // 报道详情页面数据请求
	    RequestService("/medical/doctor/getNewsReportByArticleId","GET",{
	    	articleId:articleId
	    },function(data){
	        if(data.success==false || data.resultObject.length == 0){
	           $('.teacher_books').addClass('hide')
	        }else{
	        	//获取到数据渲染
	        	console.log(data)
	           $('#report_box').html(template('report_detailTpl',data.resultObject));
	           
	        

				if(data.resultObject.medicalDoctors.length == 0){
					 $('#report_doctor_list').addClass('hide');
				}else{
					$('#report_doctor_list').html(template('report_docTpl',{inf:data.resultObject.medicalDoctors}));
				}
	          
	        }
	    });
	    
	    
	    
	    
})
