$(function() {
	//名言
	$(".header_left .path a").each(function() {
		if($(this).text() == "问答精灵") {
			$(this).addClass("select");
		} else {
			$(this).removeClass("select");
		}
	});
	$.ajax({
		type: "get",
		url: "mingyan.txt",
		success: function(data) {
			var data = eval(data);
			var a = parseInt(Math.random() * data.length);
			$(".mingyan span").html(data[a].span);
		}
	});
	//富文本编辑器
	var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code', 'link', 'image'];
	Simditor.locale = 'zh-CN'
	var editor = new Simditor({
		textarea: $('#editor'),
		toolbar: sm_toolbar,
		defaultImage: 'http://attachment-center.ixincheng.com:58000/data/picture/online/2016/09/23/17/853ba435fc894781b16310ebc5142704.png',
		pasteImage: true,
		toolbarHidden: false,
		toolbarFloat: false,
		placeholder: '',
		upload: {
			url: bath+"/online/util/upload4Simditor",
			params: null,
			fileKey: 'attachment',
			connectionCount: 3,
			leaveConfirm: '正在上传文件，如果离开上传会自动取消'
		},
		success:function(data){
			
		}
	});
	//下拉框的信息填入
	RequestService("/online/menutag/getQuestionMenuTags","get",null,function(data){
		if(data.success==true){
			$(".xialadown").html("");
			for(i=0;i<data.resultObject.length;i++){
				var $span=$("<span data-id='"+data.resultObject[i].menu.id+"'>"+data.resultObject[i].menu.name+"</span>")
				$(".xialadown").append($span);
				if(data.resultObject[i].tag!=undefined){
					var $div=$("<div class='clearfix caidan' id=caidan"+data.resultObject[i].menu.id+"></div>");
					for(m=0;m<data.resultObject[i].tag.length;m++){
						var $span1=$("<span data-id='"+data.resultObject[i].tag[m].id+"'>"+data.resultObject[i].tag[m].name+"</span>");
						$div.append($span1);
					}
					$(".erjiRight").append($div);
				}
			}
			$(".xialadown >span").on("click", function() {
				var text = $(this).html();
				$(this).parent().siblings().html(text);
				$(".xialadown").css("display", "none");
				if($(this).attr("data-id") != undefined) {
					$(this).parent().siblings().attr({
						"data-id": $(this).attr("data-id"),
						"title": $(this).html()
					});
					$("#formment_id").val($(this).attr("data-id"));
					$("#formname").val($(this).html());
				};
				if($(this).attr("data-id") != 0){
					$(".caidan").css("display","none");
					$(".xialaerji").css("display","block");
					$("#caidan"+$(this).attr("data-id")).css("display","block");
					$(".caidan span").removeClass("active");
				}else{
					$(".caidan").css("display","none");
					$(".xialaerji").css("display","none");
				}
			})

			$(".caidan span").on("click",function(){
				$(".tag-warn").css("visibility","hidden");
				if($(this).hasClass("active")){
					$(this).removeClass("active")
				}else{
					if($(".caidan .active").length<2){
						$(this).addClass("active");
					};
				}
				var str='';
				$(".caidan .active").each(function(){
					str=str+","+$(this).text();
				})
				str=str.substring(1);
				$("#formtags").val(str);
			})
		}
	})
	//下拉框
	$(".select").on("click",function(event){
		event.stopPropagation();
		$("#formtags").val("");
		if($(".xialadown").css("display")=="none"){
			$(".xialadown").css("display","block");
		}else{
			$(".xialadown").css("display","none");
		}
	})
	$(document).click(function(){
		$(".xialadown").css("display","none");
	})
	
	//标题输入近似问题表单验证
	$(".qusTitle").on("focus",function(){
		$(".title-warn").css("display","none");
		$(this).css("border","1px solid #6acd6a");
	})
	$(".qusTitle").on("blur",function(){
		$(this).css("border","1px solid #e4e4e4");
	})
	$(".qusTitle").on("blur",function(){
		$(this).css("border","1px solid #e4e4e4");
		if($.trim($(".qusTitle").val())==""){
			$(".title-warn").html("问题标题不能为空").css("display","block");
		}else if($.trim($(".qusTitle").val()).length<5||$.trim($(".qusTitle").val()).length>50){
			$(".title-warn").html("问题标题介于5-50个字之间").css("display","block");
		}else{
			$(".title-warn").css("display","none");
		}
	})
	$(".simditor-body").click(function(){
		$(".text-warn").css("display","none");
	})
	$(document).click(function(e){
		if(e.target.className!="sameQues"||e.target.parent().className!='sameQues'){
			$(".sameQues").css("display","none");
		}
	})
	$(".qusTitle").on("input propertychange",function(){
		if($(this).val().length<6){
			RequestService("/online/questionlist/findSimilarProblemByTitle","get",{
				title:$(this).val()
			},function(data){
				$(".sameQues").html("");
				if(data.resultObject!=""){
					$(".sameQues").css("display","block");
					if(data.resultObject!=null){
						for(i=0;i<data.resultObject.length;i++){
							var title=data.resultObject[i].title;
							if(title.indexOf($(".qusTitle").val()!=-1)){
								title=title.replace($(".qusTitle").val(),"<span style='color:#2cb82c;'>"+$(".qusTitle").val()+"</span>");
							}
							var url=''+bath+'/web/qusDetail/' +data.resultObject[i].id;
							var $p=$("<p  data-qusId='"+data.resultObject[i].id+"' data-url='"+url+"'><span class='sameT'>"+title+"</span><span class='sameA'>"+data.resultObject[i].answer_sum+"个回答</span></p>")
							$(".sameQues").append($p);
						}
						$(".sameQues p").click(function(){
							$(".sameQues").css("display","block");
							window.open($(this).attr("data-url"))
						})
					}else{
						$(".sameQues").css("display","none");
					}
					
				}else{
					$(".sameQues").css("display","none");
				}
			})
		}else{
			$(".sameQues").css("display","none");
		}
	})

	//提交问题
	$(".update").off().on("click",function(){
		isAlive0();
		$(".tag-warn").css("visibility","hidden");
		$(".title-warn,.text-warn").css("display","none");
		var title=true;
		var text=true;
		var tag=true;
		if($.trim($(".qusTitle").val())==""){
			$(".title-warn").html("问题标题不能为空").css("display","block");
			title=false;
		}else if($.trim($(".qusTitle").val()).length<5||$.trim($(".qusTitle").val()).length>50){
			$(".title-warn").html("问题标题介于5-50个字之间").css("display","block");
			title=false;
		}else{
			$(".title-warn").css("display","none");
		}
		/*if($(".simditor-body").html()=="<p><br></p>"){
			$(".text-warn").html("问题描述不能为空").css("display","block");
			text=false;
		}else{
			if($.trim($(".simditor-body").text())==""&&$(".simditor-body").html().indexOf("img")==-1){
				$(".text-warn").html("问题描述不能为空").css("display","block");
				text=false;
			}else{
				$("#formtext").val($(".simditor-body").text());
			}		
		}*/
		if($("#formtags").val()==""){
			$(".tag-warn").html("请选择学科标签").css("visibility","visible");
			tag=false;
		}
		if(title&&text&&tag){
			$("#form").attr("action",bath+"/online/questionlist/saveQuestion");
			$("#form").submit(function(){
				$("#form").ajaxSubmit(function(data){
					if(data.success==true){
						location.href=''+bath+"/web/html/ansAndQus.html";
					}
					return false;
				})
				return false;
			})
		}else{
			return false;
		}
//		RequestService("/online/questionlist/saveQuestion","post",{
//			//学科名字
//			name:"UI",
//			//标签
//			tags:"java",
//	        //学科ID
//	        ment_id: 0,
//	        //问题标题
//	        title:$.trim($(".qusTitle").val()),
//	        //不带样式内容
//	        text:"不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容不带样式标签的内容",
//	        content:"存在样式标签的内容"
//		},function(data){
//
//		})
	})
})