function createLesson() {
	//测试环境只能点击ID为1
template.helper('href', function (num) {
    if (num != "") {
        return ''+bath+'/web/courseDetail/' + num;
    } else {
        return 'javascript:;';
    }
});
	//课程结构
	var mylesson =
		'<div class="tabbar">' +
		'{{each nav as $value i}}' +
		'{{if i==0}}' +
		'<div class="btn-item cur" data-secondId="{{$value.id}}">{{$value.name}}</div>' +
		'{{else}}' +
		'<div class="btn-item" data-secondId="{{$value.id}}">{{$value.name}}</div>' +
		'{{/if}}' +
		'{{/each}}' +
		'</div>' +
		'<div class="lessonBox clearfix">' +
		'</div>';
	var nolesson=
		'<div class="nolessonBox">' +
		'<img src="../images/personcenter/my_nodata.png"/ class="noLesson">' +
		'<p class="noLesson-mess">暂无数据</p>' +
		'</div>' ;
	var lesson =
		'{{each Lesson}}' +
		'<div class="lesson">' +
		'<a href="{{#href($value.id)}}" target="_blank">' +
		'<div class="lessonImg"><img src="{{$value.smallImgPath}}"/></div>' +
		'<div class="lessonNews">' +
		'<p class="lessonTitle" title="{{$value.courseName}}">{{$value.courseName}}</p>' +
		'<div class="lessonMess clearfix">' +
		'<span class="MessLeft "><img src="../images/personcenter/teacher.png"/><span>{{$value.teacherNames}}</span></span>' +
		'<span class="MessRight "><img src="../images/personcenter/time.png"/><span>{{#timeChange($value.courseLength)}}</span></span>' +
		'</div>' +
		'</div>' +
		'</a>' +
		'</div>' +
		'{{/each}}';

	//初始化加载title导航
	RequestService("/userCourse/menus", "get", null, function(data) {
		if(data.resultObject.length!=0){
			$(".view-content-content").html(template.compile(mylesson)({
				nav: data.resultObject
			}));
			//切换选项卡
			$(".tabbar .btn-item").each(function() {
				$(this).on("click", function() {
					$(this).addClass("cur").siblings().removeClass("cur");
					second($(this).attr("data-secondId"));
				})
			})
			second($(".tabbar .btn-item").eq(0).attr("data-secondId"));
		}else{
			$(".view-content-content").html(nolesson);
		}
	});

	function second(id) {
		RequestService("/userCourse/courses/" + id, "get", {
			pageNumber: 1,
			pageSize: 12
		}, function(data) {
			$(".lessonBox").html("").append(template.compile(lesson)({
				Lesson: data.resultObject.items
			}));
			if(data.resultObject.totalPageCount < 2) {
				$(".pages").css("display", "none");
			} else {
				$(".pages").css("display", "block");
				$(".searchPage .allPage").text(data.resultObject.totalPageCount);
				$("#Pagination").pagination(data.resultObject.totalPageCount, {
					callback: function(page) {
						var pageParam = {
							pageNumber: (page + 1),
							pageSize: "12"
						};
						RequestService("/userCourse/courses/" + id, "GET", pageParam, function(data) {
							if(data.resultObject.items.length === 0) {
								$(".view-content-content").html(nolesson);
								$(".pages").css({
									"display": "none"
								})
							} else {
								$(".pages").css({
									"display": "block"
								});
								$(".lessonBox").html("").append(template.compile(lesson)({
									Lesson: data.resultObject.items
								}));
							}
						})
					}
				});
			}
		});
	}
	
}