//省份证--正

function previewImage(file){
      var MAXWIDTH  = 90; 
      var MAXHEIGHT = 90;
      var div = document.getElementById('preview');
      if (file.files && file.files[0])
      {
          div.innerHTML ='<img id=imghead class=img_imghead onclick=$("#previewImg").click()>';
          var img = document.getElementById('imghead');
          img.onload = function(){
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            img.width  =  rect.width;
            img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
            img.style.marginTop = rect.top+'px';
          }
          var reader = new FileReader();
          reader.onload = function(evt){img.src = evt.target.result;}
          reader.readAsDataURL(file.files[0]);
      }
      else //兼容IE
      {
        var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
        file.select();
        var src = document.selection.createRange().text;
        div.innerHTML = '<img id=imghead class=img_imghead>';
        var img = document.getElementById('imghead');
        img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
        div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
      }
    }
    function clacImgZoomParam( maxWidth, maxHeight, width, height ){
        var param = {top:0, left:0, width:width, height:height};
        if( width>maxWidth || height>maxHeight ){
            rateWidth = width / maxWidth;
            rateHeight = height / maxHeight;
            
            if( rateWidth > rateHeight ){
                param.width =  maxWidth;
                param.height = Math.round(height / rateWidth);
            }else{
                param.width = Math.round(width / rateHeight);
                param.height = maxHeight;
            }
        }
        param.left = Math.round((maxWidth - param.width) / 2);
        param.top = Math.round((maxHeight - param.height) / 2);
        return param;
    }



//身份证--反
function previewImage0(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview0');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead0 class=img_imghead onclick=$("#previewImg0").click()>';
        var img = document.getElementById('imghead0');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead0 class=img_imghead>';
	    var img = document.getElementById('imghead0');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead0 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}


//医师资格证
function previewImage1(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview1');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead1 class=img_imghead onclick=$("#previewImg1").click()>';
        var img = document.getElementById('imghead1');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead1 class=img_imghead>';
	    var img = document.getElementById('imghead1');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead1 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}




//职业资格证
function previewImage2(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview2');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead2 class=img_imghead onclick=$("#previewImg2").click()>';
        var img = document.getElementById('imghead2');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead2 class=img_imghead >';
	    var img = document.getElementById('imghead2');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead2 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}


//医馆认证
//职业资格证
function previewImage4(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview4');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead4 class=img_imghead onclick=$("#previewImg4").click()>';
        var img = document.getElementById('imghead4');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead4 class=img_imghead >';
	    var img = document.getElementById('imghead4');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead4 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}

//药品经营许可证
function previewImage2(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview2');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead2 class=img_imghead onclick=$("#previewImg2").click()>';
        var img = document.getElementById('imghead2');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead2 class=img_imghead >';
	    var img = document.getElementById('imghead2');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead2 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}


//医馆认证
//职业资格证
function previewImage5(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview5');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead5 class=img_imghead onclick=$("#previewImg5").click()>';
        var img = document.getElementById('imghead5');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead5 class=img_imghead >';
	    var img = document.getElementById('imghead5');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead5 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}


//个人信息
//上传头像
function previewImage3(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview3');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead3 class=img_imgheads onclick=$("#previewImg3").click()>';
        var img = document.getElementById('imghead3');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead3 class=img_imgheads >';
	    var img = document.getElementById('imghead3');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead3 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}




//课程第二页   新课程
//药品经营许可证
function previewImage6(file){
    var MAXWIDTH  = 90; 
    var MAXHEIGHT = 90;
    var div = document.getElementById('preview6');
    if (file.files && file.files[0]){
        div.innerHTML ='<img id=imghead6 class=img_imghead onclick=$("#previewImg6").click()>';
        var img = document.getElementById('imghead6');
        img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
    }
    	var reader = new FileReader();
      	reader.onload = function(evt){img.src = evt.target.result;}
      	reader.readAsDataURL(file.files[0]);
  	}
  		else //兼容IE
  	{
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	    file.select();
	    var src = document.selection.createRange().text;
	    div.innerHTML = '<img id=imghead6 class=img_imghead >';
	    var img = document.getElementById('imghead6');
	    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
	    div.innerHTML = "<div id=divhead6 style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
	  }
	}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else{
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}
