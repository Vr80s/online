/**
 * Created by admin on 2016/8/22.
 */
$(function() {
	
	//解析url地址
	/*var ourl = document.location.search;
	var apams = ourl.substring(1).split("&");
	var arr = [];
	for (i = 0; i < apams.length; i++) {
	    var apam = apams[i].split("=");
	    arr[i] = apam[1];
	    var storyId = arr[0];
	};*/
	/*$(".header_left .path a").each(function() {
		if($(this).text() == "学堂") {
			$(this).addClass("select");
		} else {
			$(this).removeClass("select");
		}
	});*/
	var studentDetail =
		'<div class="detailTop" data-id="{{id}}">' +
		'<p class="title">{{title}}</p>' +
		'<span class="name">{{name}}</span>' +
		'<span class="createTime">{{createTime}}</span></br>' +
		'{{if useOtherName == true}}' +
		'<span class="secName">姓名：{{otherName}}</span>' +
		'{{else}}' +
		'<span class="secName">姓名：{{name}}</span>' +
		'{{/if}}' +
//		'<p class="sCourse">所学课程：{{menu}}</p>' +
//		'<p class="company">入职公司：{{company}}</p>' +
//		'<p class="salary">薪资：{{salary}}</p>' +
		'</div>' +
		'<div class="detailBottom"><!--{{text}}--></div>';
//3>返回值说明：name：学员名  otherName:化名  headImg:学员头像   
 //    Company：公司  salary:薪资  introduce:故事简介    
 //     useOtherName:true:显示化名    false：显示学员名
	var yxStudent =
		'<div class="yx">'+
		'{{each items as $value i}}'+
		'<div class="student" data-id="{{$value.id}}">' +
		'<div class="d1 clearfix">' +
		' 		<span class="d1-img"><img src="{{#changeImg($value.headImg)}}"/></span>' +
		' 	<div class="d1-mess">' +
		'{{if $value.useOtherName==true}}'+
		' 	<p class="d1-name">{{$value.otherName}}</p>' +
		'{{else}}'+
		' 	<p class="d1-name">{{$value.name}}</p>' +
		'{{/if}}'+
//		' 	<p class="d1-job"><span>{{$value.company}}</span>&薪资<span>{{$value.salary}}</span></p>' +
		' </div>' +
		' 		</div>' +
		' 	<div class="d2 clearfix" data-storyId="{{$value.id}}" style="cursor: pointer;">' +
		' 	<div class="d2-posi clearfix">' +
		' 	<div class="d2-posi-ab">' +
		' 	<span class="d2-img"><img src="{{#changeImg($value.headImg)}}"/></span>' +
		'{{if $value.useOtherName==true}}'+
		' 	<p>{{$value.otherName}}</p>' +
		'{{else}}'+
		' 	<p>{{$value.name}}</p>' +
		'{{/if}}'+
		' 	    				</div>' +
		'     			</div>' +
		'   			<div class="d2-right">' +
//		' 				<p class="d2-job"><span>{{$value.company}}</span>&薪资<span>{{$value.salary}}</span></p>' +
		' 			<div class="d2-descr">' +
		' 			<em>“</em><span>{{$value.introduce}}</span><em>”</em>' +
		' 	</div>' +
		' </div>' +
		'  		</div>' +
		' 	</div>'+
		'{{/each}}'+
		'</div>';
	RequestService("/studentStory/detail/" + g_id, "GET", null, function(data) { //g_idstoryId
		$("#studentDetail").html(template.compile(studentDetail)({
			title: data.resultObject.title,
			name: data.resultObject.name,
			createTime: data.resultObject.createTime,
			menu: data.resultObject.menu,
			company: data.resultObject.company,
			salary: data.resultObject.salary,
			id:data.resultObject.id
		}));
		$(".detailBottom").html(data.resultObject.text);
	})
	RequestService("/studentStory/listByIndex", "GET", null, function (data) {
		$("#student-right").append(template.compile(yxStudent)({
			items:data.resultObject
		}));
		$(".student").each(function(){
			if($(this).attr("data-id") == g_id){
				$(this).remove();
			}
		})
		$(".d1").each(function(){
			$(this).mouseover(function(){
				$(".d1").css("display","block");
				$(".d2").css("display","none");
				$(this).siblings().css({"position":"fixed","top":"100%","display":"block"});
				var ht=$(this).siblings().find(".d2-right").height();
				$(this).siblings().find(".d2-posi").height(ht);
				$(this).siblings().css({"position":"static","display":"none"});
				$(this).css("display","none").siblings().css({"display":"block"});
			})
		});
		$(".yx").mouseleave(function(){
			$(".d1").css("display","block");
			$(".d2").css("display","none");
		})
		$(".d2").on("click",function(){
			var storyId =$(this).attr("data-storyId");
			if(storyId != $(".detailTop").attr("data-id")){
				window.open(bath+'/web/storyDetail/' + storyId);
			}
		});
	});
})