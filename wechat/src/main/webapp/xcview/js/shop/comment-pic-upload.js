//$.Pgater=(function(){
//	return function(target,callBack){
////		console.log(File);
//		for(var i=0;i<target.length;i++){
//			this.ele=getFileDom(i);
//			this.parent=$(target[i]);
//			this.parent.append(this.ele);
//			this.bindClk(this.parent,this.ele[0]);
//			this.bindFuc(this.ele,callBack);			
//		}
//	};
//})();
//function getFileDom(index){
//	var agent=navigator.userAgent.toLowerCase();
//	var iswx=agent.indexOf('qqbrowser') >= 0;
//	var File
//	if(iswx){
//		File=$("<input type='file' class='csl_gater_file"+index+"' accept='image/*' capture='camera' multiple='multiple'>");
//	}else{
//		File=$("<input type='file' class='csl_gater_file"+index+"' accept='image/*' multiple='multiple'>");
//	};
//	File.css('display','none');
//	return File;
//}
//var save;
//$.Pgater.prototype.bindFuc=function(ele,callBack){
//	ele.on("change",function(){		
//		 save=$(this).parent().parent().find("ul");   //图片插入到当前UL里面
//		var all=ele[0].files;
//		var reader = new FileReader();
//		var album=[];
//		console.log(all.length);
//		var length=all.length;
//		var i=0;
//		var recur=function(){
//			reader.readAsDataURL(all[i]);
//			var One=all[i];
//			reader.onload=function(e){
//				One.data=this.result;				
//				album.push(One);
//				i++;
//				if(i<length){
//					recur();
//				}else{
//					ele.value = '';
//					callBack(album,img,save);
//				};
//			};
//		};
//		recur();
//	});
//};
//$.Pgater.prototype.bindClk=function(ele,tar){
//	ele.on('click',function(){
//		tar.click();
//	});
//};
//	



$.Pgater=(function(){
	return function(target,callBack){
//		console.log(File);
		for(var i=0;i<target.length;i++){
			this.ele=getFileDom(i);
			this.parent=$(target[i]);
			this.parent.append(this.ele);
			this.bindClk(this.parent,this.ele[0]);
			this.bindFuc(this.ele,callBack);			
		}
	};
})();
function getFileDom(index){
	var agent=navigator.userAgent.toLowerCase();
	var iswx=agent.indexOf('qqbrowser') >= 0;//判断是否为苹果      //agent.indexOf('qqbrowser') >= 0;
	var File;
	if(iswx==true){
		File=$("<input type='file' class='csl_gater_file"+index+"' accept='image/*'>");// multiple='multiple'
	}else{
		File=$("<input type='file' class='csl_gater_file"+index+"' accept='image/*' capture='camera'>"); // multiple='multiple'
	};
	File.css('display','none');
	return File;
}
var save;
$.Pgater.prototype.bindFuc=function(ele,callBack){
	ele.on("change",function(){		
		 save=$(this).parent().parent().find("ul");   //图片插入到当前UL里面
		var album=[];
		 var form = new FormData();
        form.append("image", this.files[0]);
        var reader = new FileReader();
        reader.onload = function (e) {    
        $.ajax({
            type: 'post',
            url: "/xczh/common/upload",
            data: form,
            cache: false,
            processData: false,
            contentType: false
        }).success(function (data) {
        	album.push(data.resultObject)
             		ele.value = '';
					callBack(album,img,save);
        	});   
        }
        reader.readAsDataURL(this.files[0])
	});
};
$.Pgater.prototype.bindClk=function(ele,tar){
	ele.on('click',function(){
		tar.click();
	});
};








      
