//出现黑色提示弹窗方法

var msgTip='<div id="blackTip" style="display: none;padding: 8px 32px;'+
			'background-color: #fff;border-radius: 4px;background-color: #000;'+
			'opacity: .5;position: fixed;color: #fff;top: 50%;left: 50%;transform: translate(-50%,-50%);'+
			'z-index: 1000;">保存失败</div>';
function showTip(contant,fn){
	$("body").append(msgTip);
    $('#blackTip').text(contant).show();
    setTimeout(function(){
        $('#blackTip').text(contant).hide();
        $("#blackTip").remove()
        if(fn!=null)fn();
    },2000)
}	




//确认取消框
var comfirmBox = {
	init : function(){
		var confirmStr ='<div class="wxc-bgcolor"></div>'+
						'<div id="wxc-wrap">'+
							'<h4 class="wxc-header-title"></h4>'+
							'<p class="wxc-conetent-text"></p>'+
							'<span class="wxc-confirm">确认</span>'+
							'<span class="wxc-remove">取消</span>'+
						'</div>';
		$("body").append(confirmStr);	
		$("#wxc-wrap").css({
		"display":"none",
		"width":"400px",
		"padding":"30px 32px",
		"background-color":"#fff",
		"border-radius":"4px",
		"top":"50%",
		"left":"50%",
		"transform":"translate(-50%,-50%)",
		"z-index":"1000",
		"position": "fixed"
		});
		$(".wxc-header-title").css({
			"text-align":"center",
		    "font-size":"20px",
		    "color": "#333333",
		    "margin-bottom": "20px"
		});
		$(".wxc-conetent-text").css({
				"text-align": "center",
    		    "font-size": "14px",
			    "color": "#666666",
			    "margin-bottom": "40px"
		});
		$(".wxc-confirm").css({
			"display":"block",
    		"float": "left",
    		    "border":"1px solid #DEDEDE",
			   "border-radius": "4px",
			    "width":"120px",
			    "height":"36px",
			    "text-align":"center",
			    "line-height": "36px",
				"margin-left":"30px",
			    "background-color": "#fff",
			   "color": "#000"
		});
	
		$(".wxc-remove").css({
			"display": "block",
    		"float": "right",
    		 "border": "1px solid #DEDEDE",
			    "border-radius": "4px",
			   "width" : "120px",
			    "height": "36px",
			    "text-align": "center",
			    "line-height": "36px",
			    "outline": "none",
			    "background-color": "#fff",
			    "color": "#000",
				"margin-right": "30px"
		});
		$(".wxc-bgcolor").css({
			"position":"fixed",
		    "top": "0px",
		    "left": "0px",
		    "width": "100%",
		    "height": "100%",
		    "background": "rgb(0, 0, 0)",
		    "opacity": "0.3",
		   "z-index": "888",
		    "display": "none"
		})	
		$(".wxc-confirm").hover(function(){
			$(".wxc-confirm").css({
				"background-color":"#00BC12",
				"cursor":"pointer",
	    		"color": "#fff"
			});
		},function(){
			$(".wxc-confirm").css({
				"background-color":"#fff",
	    		"color": "#000"
			});
		})
		$(".wxc-remove").hover(function(){
			$(".wxc-remove").css({
				"background-color":"#00BC12",
				"cursor":"pointer",
	    		"color": "#fff"
			});
		},function(){
			$(".wxc-remove").css({
				"background-color":"#fff",
	    		"color": "#000"
			});
		})

		},
	//确认框打开
	open : function (title,content,fn){
		    $(".wxc-header-title").html(title);
		    $(".wxc-conetent-text").html(content);
		    $(".wxc-confirm").click(function(){
		        fn(comfirmBox.close);
		        $(".wxc-confirm").unbind("click")
		    })
		    $('#wxc-wrap').show();
			$(".wxc-bgcolor").show();
			$("body").on("click",".wxc-remove",function(){
				comfirmBox.close();
			})
	},
	//确认框关闭
	close : function (){
	    $('#wxc-wrap').hide();
		$(".wxc-bgcolor").hide()
	}
}
//用前先初始化
comfirmBox.init()
//comfirmBox.open("标题","内容",function(closefn){
////	alert("abc");  执行的逻辑
//	closefn();    关闭弹窗
//});

