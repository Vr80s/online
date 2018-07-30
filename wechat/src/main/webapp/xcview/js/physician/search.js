

var curriculum_blck = getQueryString("search_back");

sessionStorage.setItem("search_back",curriculum_blck);

/**
 * 
 */
function goto_back(){
	var back_falg  = sessionStorage.getItem("search_back");
	if(back_falg == 1){
		location.href='/xcview/html/physician/index.html';
	}else if(back_falg == 2){
		location.href='/xcview/html/physician/physician_list.html';
	}
}

$(function(){
//搜索历史开始
requestService("/xczh/bunch/hotDoctorSearch",null, 
    function(data) {
		if(data.success==true){
            if(data.resultObject.hotSearch == 0 || data.resultObject.hotSearch == '' || data.resultObject.hotSearch == null){
            	$(".search_hot").hide();
            }else{
            	 //检索列表
	 	    	$(".search_hot_main").html(template('search1',{items:data.resultObject.hotSearch}))
            };
// 	    	<!--给inpiu默认值-->
 	    	$(".div_span_input").html(template('shipin',{items:data.resultObject.defaultSearch}))
 	    	
			if(data.resultObject.defaultSearch!=null&&data.resultObject.defaultSearch.length>0){
				localStorage.setItem("defaultKey_doctor", data.resultObject.defaultSearch[0].name);
			}

			// 点击搜索按钮
		$(".header_cancel").click(function(){
			if ($(".div_span_input").html() == "" && $("#header_input").val() == ""){
	        		jqtoast("请输入搜索关键字");
	        }else{
	        	var search_val=$("#header_input").val();
				if($("#header_input").val() != ""){
					$(".header_seek_main").css("display","none");
					//头部input搜索框开始
						initHistoryList();
	                        var keyValue = $('#header_input').val();
	                        //判断该记录是否已存在
							if($.inArray(keyValue, arr)!=-1){
	                            removeByValue(arr, keyValue);
	                            arr.unshift($('#header_input').val());
	                            localStorage.setItem(hisArr,arr);
	                            window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
							}else{
	                            arr.unshift($('#header_input').val())
	                            localStorage.setItem(hisArr,arr);
	                            window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
							}
				}else{
					var keyword = $(".keyword").html();
					search_val = keyword;
					// $(".header_seek_main").css("display","block");
					$(".header_seek_main").css("display","none");
					//头部input搜索框开始
					initHistoryList();
                    var keyValue = $('#header_input').val();
                    //判断该记录是否已存在
					if($.inArray(keyValue, arr)!=-1){
                        removeByValue(arr, keyValue);
                        arr.unshift($('#header_input').val());
                        localStorage.setItem(hisArr,arr);
                        window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
					}else{
                        arr.unshift($('#header_input').val())
                        localStorage.setItem(hisArr,arr);
                        window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
					}
				};
	        }
		});

		// 点击回车
		$("#header_input").keyup(function(){

			if ($(".div_span_input").html() == "" && $("#header_input").val() == ""){
        		jqtoast("请输入搜索关键字");
        	}else{
        		var search_val=$(this).val()
        	
				if($("#header_input").val() != ""){
					$(".header_seek_main").css("display","none");
					//头部input搜索框开始
					document.onkeyup=function(event){
						
						initHistoryList();
						
				        var e = event || window.event || arguments.callee.caller.arguments[0];          
				        if(e && e.keyCode==13){ // enter 键
	                        var keyValue = $('#header_input').val();
	                        //判断该记录是否已存在
							if($.inArray(keyValue, arr)!=-1){
	                            removeByValue(arr, keyValue);
	                            arr.unshift($('#header_input').val());
	                            localStorage.setItem(hisArr,arr);
	                            window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
							}else{
	                            arr.unshift($('#header_input').val())
	                            localStorage.setItem(hisArr,arr);
	                            window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
							}

				        } 
				   }; 
				}else{
					var keyword = $(".keyword").html();
					search_val = keyword;
					// $(".header_seek_main").css("display","block");
					$(".header_seek_main").css("display","none");
					//头部input搜索框开始
					initHistoryList();
                    var keyValue = $('#header_input').val();
                    //判断该记录是否已存在
					if($.inArray(keyValue, arr)!=-1){
                        removeByValue(arr, keyValue);
                        arr.unshift($('#header_input').val());
                        localStorage.setItem(hisArr,arr);
                        window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
					}else{
                        arr.unshift($('#header_input').val())
                        localStorage.setItem(hisArr,arr);
                        window.location.href="/xcview/html/physician/physician_list.html?queryKey="+search_val+"&curriculum_blck=2";
					}
				};
        	}
        	
			
		});


		}
},false) 
//搜索历史结束

	//点击热门搜索跳转
	$(".search_hot_main_one").click(function(){
		var btn_write=$(this).text()
//		arr.unshift(btn_write);
		 //判断该记录是否已存在
						if($.inArray(btn_write, arr)!=-1){
                            removeByValue(arr, btn_write);
                            arr.unshift(btn_write);
                            localStorage.setItem(hisArr,arr);
						}else{
                            arr.unshift(btn_write)
                            localStorage.setItem(hisArr,arr);
						}
//		localStorage.hisArr = arr;
		window.location.href="physician_list.html?queryKey="+btn_write+""
	})

})

//清空历史
function clearAll() {
	if(localStorage.getItem(hisArr)!=undefined){
        localStorage.setItem(hisArr,"");
        $(".search_history_list").empty();
        $(".search_history").hide();
        arr = [];
	}
}

// 点击取消调用方法
function physician() {
    var back = document.referrer;
    if (isNotBlank(back) && back.indexOf("wx_share.html") == -1) {
        window.location.href = back;
    } else {
        window.location.href = "index.html";
    }
}