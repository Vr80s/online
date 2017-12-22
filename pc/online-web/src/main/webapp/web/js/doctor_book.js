$(function(){
	
	//右侧名医推荐部分
	     RequestService("/medical/doctor/getRecDoctors","GET",null,function(data){
	       
	        console.log(data);
	        $('#doc_rec').html(template('doc_recTpl',{doc:data.resultObject}));
	        
	      });
	
	
	
})
