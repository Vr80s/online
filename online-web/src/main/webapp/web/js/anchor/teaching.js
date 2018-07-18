$(function () {
    initMenu();
    initEditor();
    $(".teachingTab").click(function(){courseList(1)});
    $(".course_search").click(function(){
        courseList(1);
    })
//	我的弟子
	$(".teaching-disciple").click(function(){myDiscipleList(1)});

//  疑惑解答
	$(".teaching-answer").click(function(){questionDisabuse(1)});
	$(".answer-list-search select").on("change",function(){
		editSelectStatus=$(this).val();
		questionDisabuse(1,editSelectStatus);
	})
	
});

/**
 * Description：课程列表
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/2 0002 下午 9:09
 **/

function courseList(current) {
    var url = "/anchor/course/getCourseApplyList?size=10&teaching=1&current=" + current;
    var courseName = $('#course_name').val();
    if (courseName != null) {
        url += "&title=" + courseName;
    }
    RequestService(url, "get", null, function (data) {
        if (!data.resultObject || !data.resultObject.records || data.resultObject.records.length == 0) {
            $('#live-list').html('<div style="padding-top:40px;text-align:center"><img src="/web/images/other_noResult.png" alt="" /><p style="font-size:16px;color:#999;margin-top:35px">暂无课程</p></div>');
            $('#live-list').css({"border":"0"})
            $('#live-list').removeClass('hide')
        } else {
			$('#live-list').css({"border":"1px solid #dedede"})
            var str = '<thead><tr><th>封面图</th><th>课程名称</th><th>价格</th><th>审核状态</th><th>课程状态</th><th>参与弟子</th><th>操作</th></tr></thead><tbody id="course_list"></tbody>'
            $('#live-list').html(str)
            $('#live-list').removeClass('hide')
        }
        $("#course_list").html(template('live_list_tpl', data.resultObject));
//
        //每次请求完数据就去渲染分页部分
        if (data.resultObject.pages > 1) { //分页判断
            $(".not-data").remove();
            $(".live_pages").css("display", "block");
            $(".live_pages .searchPage .allPage").text(data.resultObject.pages);
            $("#pagination_live").pagination(data.resultObject.pages, {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page: current - 1,
                callback: function (page) {
                    //翻页功能
                    courseList(page + 1);
                }
            });
        } else {
            $(".live_pages").css("display", "none");
        }
    });
}

/**
 * Description：新增课程
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 下午 5:56
 **/
function saveCourse() {
    var course = getCourseData();
    if (verifyCourse(course)) {
        if (course.id == null || course.id == '') {
            addCourse(course);
        } else {
            updateCourse(course);
        }
    }
}

function addCourse(course) {
    $.ajax({
        type: "post",
        url: bath + "/anchor/course/teaching/saveCourseApply",
        data: JSON.stringify(course),
        contentType: "application/json",
        async: false,
        success: function (data) {
            console.log(data);
            if (data.success === true) {
                showTip(data.resultObject);
                resetCourseForm(true);
                setTimeout(function () {
                    courseList(1);
                    $(".change-status").click();
                }, 2000);
            } else {
                showTip(data.errorMessage)
            }
        }
    });
}

function updateCourse(course) {
    $.ajax({
        type: "post",
        url: bath + "/anchor/course/teaching/updateCourseApply",
        data: JSON.stringify(course),
        contentType: "application/json",
        async: false,
        success: function (data) {
            console.log(data);
            if (data.success === true) {
                showTip(data.resultObject);
                resetCourseForm(false);
                setTimeout(function () {
                    courseList(1);
                    $(".change-status").click();
                }, 2000);
            } else {
                showTip(data.errorMessage)
            }
        }
    });
}

function editCourse(caiId, passEdit) {
    $(".change-status").click();
    if (echoCourse(caiId, passEdit)) {
        $(".curriculum_two").hide();
        $(".curriculum_one").show();
    } else {
        showTip("课程发生变化了，请刷新列表");
    }
}

function deleteCourse(caiId) {
    var title = "删除";
    var content = "确认删除该课程？";
    confirmBox.open(title, content, function (closefn) {
        RequestService("/anchor/course/deleteCourseApplyById?caiId=" + caiId, "get", null, function (data) {
            closefn();
            if (data.success) {
                showTip(data.resultObject);
                courseList(1);
            } else {
                showTip(data.errorMessage);
            }
        });
    });
}

function getCourse4Update(caiId) {
    var course;
    RequestService("/anchor/course/getCourseApplyById?caiId=" + caiId, "get", null, function (data) {
        course = data.resultObject;
    }, false);
    return course;
}

function echoCourse(caiId, passEdit) {
    var course = getCourse4Update(caiId);
    //若该申请已通过，且点进方法的页面显示未通过，给出提示  //暂时关闭该校验
    if (course.status == 1 && !passEdit) return false;
    $('#caiId').val(caiId);
    $('.course_title').val(course.title);
    $('.course_subtitle').val(course.subtitle);
    $('.disciple-wrap-img').html('<img src="" style="width: 100%;height: 100%" >');
    $('.disciple-wrap-img img').attr('src', course.imgPath);
    $('.course_lecturer ').val(course.lecturer);
    if (course.lecturerDescription) {
        UE.getEditor('editor_lecturer').setContent(course.lecturerDescription);
    }
    // $("input[name='course_form']:checked").val();
    $("input:radio[name=course_form][value=" + course.courseForm + "]").prop("checked", true);
    $('#menu_select').val(course.courseMenu);
    $('.course_price').val(course.price);
    $('.course_originalCost').val(course.originalCost);
    if (course.courseDetail) {
        UE.getEditor('editor_cd').setContent(course.courseDetail);
    }

    $('.course_start_time').val(course.startTime);
    return true;
}
function initEditor(){
    UE.getEditor('editor_lecturer', {
        toolbars: [['source', //源代码
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'forecolor', //字体颜色
            'backcolor', //背景色
            'indent', //首行缩进
            'removeformat',//清除格式
            'formatmatch', //格式刷
            'blockquote', //引用
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'superscript', //上标
            'subscript', //下标
            'touppercase', //字母大写
            'tolowercase', //字母小写
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'justifyjustify',//两端对齐
            'link', //超链接
            'unlink', //取消链接
            'simpleupload', //单图上传
            // 'insertimage', //多图上传
            'emotion', //表情
            'lineheight', //行距
            'fullscreen'
        ]],
        initialFrameWidth: 540,
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 3000       //允许的最大字符数
    });
    UE.getEditor('editor_cd', {
        toolbars: [['source', //源代码
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'forecolor', //字体颜色
            'backcolor', //背景色
            'indent', //首行缩进
            'removeformat',//清除格式
            'formatmatch', //格式刷
            'blockquote', //引用
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'superscript', //上标
            'subscript', //下标
            'touppercase', //字母大写
            'tolowercase', //字母小写
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'justifyjustify',//两端对齐
            'link', //超链接
            'unlink', //取消链接
            'simpleupload', //单图上传
            // 'insertimage', //多图上传
            'emotion', //表情
            'lineheight', //行距
            'fullscreen'
        ]],
        initialFrameWidth: 540,
        initialFrameHeight: 220,
        elementPathEnabled: false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave: false,
        imagePopup: false,
        maximumWords: 3000       //允许的最大字符数
    });
}
function resetCourseForm(sp) {
    //时间插件调用
    var data = new Date();
    var year = data.getFullYear();
    var month = data.getMonth() + 1;
    var day = data.getDate();

    $("#classOpenTiem").jeDate({
        format: "YYYY-MM-DD hh:mm",
        minDate: ""+year+"-"+month+"-"+day+" 00:00"
    });

    initEditor()

   $("#caiId").val("");
    $('.course_title').val("");
    $('.course_subtitle').val("");
    $('.disciple-wrap-img').html('<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p><p style="text-align: center;color: #999;font-size: 14px;">点击上传封面图片</p>');
    $('.course_lecturer ').val("");
    // $('#menu_select').val("");
    $('.course_price').val("");
    $('.course_originalCost').val("");
    $('.course_start_time').val("");
    if (sp) {
        showPersonInf();
    }
}
function numberCk(data) {
    var reg = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
    if (!reg.test(data)) {
        return false;
    }
    return true;
}
//课程展示主播信息
function showPersonInf() {
    RequestService("/anchor/info", "get", null, function (data) {
        $('.course_lecturer').val(data.resultObject.name);
        UE.getEditor('editor_lecturer').setContent(data.resultObject.detail);
    }, false);
    RequestService("/template/course_detail.html", "get", null, function (data) {
        UE.getEditor('editor_cd').setContent(data);
    }, false);
}

/**
 * Description：获取新增课程所有参数
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 下午 5:54
 **/
function getCourseData() {
    var course = {};
    course.id = $("#caiId").val();
    course.courseForm = 1;
    course.title = $.trim($('.course_title').val());
    course.subtitle = $.trim($('.course_subtitle').val());
    course.imgPath = $.trim($('.disciple-wrap-img img').attr('src'));
    course.lecturer = $.trim($('.course_lecturer ').val());
    course.lecturerDescription = UE.getEditor('editor_lecturer').getContent();
    course.courseMenu = $.trim($('#menu_select').val());
    course.price = $.trim($('.course_price').val());
    course.originalCost = $.trim($('.course_originalCost').val());
    course.courseDetail = UE.getEditor('editor_cd').getContent();
    course.courseLength = $.trim($('.course_length').val());
    course.startTime = $.trim($('.course_start_time').val());
    return course;
}

/**
 * Description：校验新增课程参数
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/2 0002 下午 9:36
 **/
function verifyCourse(course) {
    $(".warning").addClass('hide');
    //课程标题
    if (course.title == '') {
        $('.warning_course_title').removeClass('hide');
        return false;
    }
    if (course.title.length > 30) {
        $('.warning_course_title_length').removeClass('hide');
        return false;
    }
    //副标题
    if (course.subtitle == '') {
        $('.warning_course_subtitle').removeClass('hide');
        return false;
    }
    if (course.subtitle.length > 30) {
        $('.warning_course_subtitle_length').removeClass('hide');
        return false;
    }
    //封面图
    if (course.imgPath == '') {
        $('.warning_course_imgPath ').removeClass('hide');
        return false;
    }
    //主播姓名
    if (course.lecturer == '') {
        $('.warning_course_lecturer').removeClass('hide');
        return false;
    }
    //主播介绍
    if (course.lecturerDescription == '') {
        $('.warning_course_lecturer_description').removeClass('hide');
        return false;
    }
    //请选择开课时间
    if (course.startTime == '') {
        $('.warning_course_start_time').removeClass('hide');
        return false;
    }
    //价格
    if (course.price == '') {
        $('.warning_course_price').removeClass('hide');
        return false;
    }
    //价格数值校验
    if (!numberCk(course.price)) {
        $('.warning_course_price_Illegal').removeClass('hide');
        return false;
    }
    //原价
    if (course.originalCost != '' && parseInt(course.originalCost) < parseInt(course.price)) {
        $('.warning_course_originalCost').removeClass('hide');
        return false;
    }
    //课程详情
    if (course.courseDetail == '') {
        $('.warning_course_details').removeClass('hide');
        return false;
    }
    return true;
}


function confirmCourseSale(state, courseApplyId, courseId) {
    var title = "课程上架";
    var content = "确认上架该课程？";
    if (state == 0) {
        title = "课程下架";
        content = "确认下架该课程？";
    }
    confirmBox.open(title, content, function (closefn) {
        $.ajax({
            type: "post",
            url: bath + "/anchor/course/changeSaleState",
            data: "courseApplyId=" + courseApplyId + "&courseId=" + courseId + "&state=" + state,
            async: false,
            success: function (data) {
                closefn();
                console.log(data);
                if (data.success == true) {
                    courseList(1);
                    showTip(data.resultObject);
                } else {
                    showTip(data.errorMessage);
                }
            }
        });
    });
}

/**
 * Description：初始化菜单
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/3 0003 上午 11:07
 **/
function initMenu() {
    RequestService("/menu/getAllMenu?type=1", "get", null, function (data) {
        var menus = data.resultObject;
        var str = "";
        for (var i = 0; i < menus.length; i++) {
            if (menus[i].id != 1) {
                str += "<option value='" + menus[i].id + "'>" + menus[i].name + "</option>"
            }
        }
        $("#menu_select").html(str);
    });
}

//	选择弟子弹出框
function openSelectPupil(courseId){
    RequestService("/anchor/course/teaching/apprentices/"+courseId, "get", null, function (data) {
        var users = data.resultObject;
        var str = "";
        for (var i = 0; i < users.length; i++) {
            str += '<li><div class="set-lable"><p>';
            if(users[i].selected){
                str += '<img src="/web/images/btn-ondown.png" alt="选择" data-userId="'+users[i].userId+'" class="active">';
            }else{
                str += '<img src="/web/images/btn-ondown.png" alt="选择" data-userId="'+users[i].userId+'">';
            }
            str += '</p><span>'+users[i].name+'</span></div></li>';
        }
        $(".sava-pupil ul").html(str);
        cheackSelectAll();
        $(".sava-pupil ul").attr("data-courseId",courseId);
        //	单个点击弟子
        $(".sava-pupil li .set-lable").click(function(){
            var thatImg=$(this).find("img");
            thatImg.toggleClass("active");
            cheackSelectAll();
        });

        $(".pupil-modal-wrap").removeClass("hide");
        $("#mask").removeClass("hide");
    });
}

//	选择弟子弹出框
function saveCourseTeaching(){
    var userIds = [];
    var courseId = $(".sava-pupil ul").attr("data-courseId");
    $(".sava-pupil ul .active").each(function(){
        userIds.push($(this).attr("data-userId"));
    })

    RequestService("/anchor/course/teaching/apprentices/"+courseId, "post", {apprenticeIds:userIds.join(",")}, function (data) {
        if(data.success){
            showTip(data.resultObject);
            courseList(1);
            $(".pupil-modal-wrap").addClass("hide");
            $("#mask").addClass("hide");
        }else{
            showTip(data.errorMessage);
        }
    });
}

function cheackSelectAll(){
    if($(".sava-pupil ul .active").length > 0 && $(".sava-pupil ul .active").length == $(".sava-pupil ul img").length){
        $(".all-select img").addClass("active");
    }else{
        $(".all-select img").removeClass("active");
    }
}

/**
 * Description：问答解惑
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: wangxingchuan@ixincheng.com
 * @Date: 2018/7/17 0003 上午 14:50
 **/
//	疑问解答列表
	var questionData
	var editSelectStatus
	function questionDisabuse(pages,isAnswer) {	
		var customData={}
			customData.current=pages,
	    	customData.size=10;
	  
	  	if(isAnswer!=null && isAnswer!=""){
			customData.isAnswer=isAnswer;
		};	
	    RequestService("/doctor/question/list", "get",customData, function (data) {
	      	if(data.success==true){
	      		questionData=data.resultObject.records;
	      		if(questionData==null || questionData.length==0){
	      			$(".question-null").removeClass("hide");
	      			$(".answer-list-table").addClass("hide");
	      		}else{
	      			$(".question-null").addClass("hide");
	      			$(".answer-list-table").removeClass("hide");
	      			$("#template-question-list").html(template("template-question",{items:data.resultObject.records}))
	      		}
	     // 分页
	              	 if (data.resultObject.pages > 1) { //分页判断
	                    $(".not-data").remove();
	                    $(".ans_que_pages").removeClass("hide");
	                    $(".ans_que_pages .searchPage .allPage").text(data.resultObject.pages);  //传的页数的参数
	                    $("#Pagination_ans_que").pagination(data.resultObject.pages, {			//传的页数的参数
	                        num_edge_entries: 1, //边缘页数
	                        num_display_entries: 4, //主体页数
	                        current_page: pages - 1,  //共几页
	                        callback: function (page) {
	                            //翻页功能
	                            questionDisabuse(page + 1,editSelectStatus);
	                        }
	                    });
	                } else {
	                    $(".ans_que_pages").addClass("hide");
	                }
	      	}else{
	      		showTip("获取问答疑惑数据失败");
	      	}
	    });
	}

//	0编辑/1回复  
	$(".answer-list-table").on("click",".edit-question",function(){
		var editStatus=$(this).attr("data-status"),
			index=$(this).attr("data-index"),
			questionShow=questionData[index];
		if(editStatus==0){		
			$(".querst-list textarea").val(questionShow.answer);
		}else if(editStatus==1){
			$(".querst-list textarea").val("");
		}
		$(".savaReplyId").val(questionShow.id)
		$(".querst-list .all-question-text").html(questionShow.question);			
		$("#mask").removeClass("hide");
		$(".answer-edit-replay").removeClass("hide");
	})
//	确认修改编辑或回复
	$(".answer-edit-replay").on("click","button",function(){		
		var replyData=$(this).parent().siblings(".querst-list").find("textarea").val();		
		var answerId=$(".savaReplyId").val();
		if($.trim(replyData)==""){
			$(".error-reply-null").removeClass("hide");
			return
		}else{
			$(".error-reply-null").addClass("hide");
			RequestService("/doctor/question/update", "get",{
				answer:replyData,
				id:answerId
			}, function (data) {
				if(data.success==true){
					closeReplyText();
					showTip("提交成功");
					questionDisabuse(1,editSelectStatus)
				}else{
					showTip("提交失败");
				}
			})
			
		}
	})
//	关闭编辑回复
	function closeReplyText(){
		$("#mask").addClass("hide");
		$(".answer-edit-replay").addClass("hide");
	}
	$(".edit-replay-top").click(function(){
		closeReplyText()
	})
/**
 * Description：我的弟子
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: wangxingchuan@ixincheng.com
 * @Date: 2018/7/17 0003 上午 20:07
 **/
//我的弟子列表
	var disciple;
	function myDiscipleList(pages) {	
		var discipleData={}
			discipleData.page=pages,
	    	discipleData.size=10;

	    RequestService("/doctor/apprentice", "get",discipleData, function (data) {
	      	if(data.success==true){
	      		disciple=data.resultObject.records;
	      		if(disciple==null || disciple.length==0){
	      			$(".disciple-null").removeClass("hide");
	      			$(".myself-disciple-list").addClass("hide");
	      		}else{
	      			$(".disciple-null").addClass("hide");
	      			$(".myself-disciple-list").removeClass("hide");
	      			$("#disciple-table").html(template("disciple-template",{items:disciple}))
	      		}
	     // 分页
	              	 if (data.resultObject.pages > 1) { //分页判断
	                    $(".not-data").remove();
	                    $(".disciple_pages").removeClass("hide");
	                    $(".disciple_pages .searchPage .allPage").text(data.resultObject.pages);  //传的页数的参数
	                    $("#Pagination_disciple").pagination(data.resultObject.pages, {			//传的页数的参数
	                        num_edge_entries: 1, //边缘页数
	                        num_display_entries: 4, //主体页数
	                        current_page: pages - 1,  //共几页
	                        callback: function (page) {
	                            //翻页功能
	                            myDiscipleList(page + 1);
	                        }
	                    });
	                } else {
	                    $(".disciple_pages").addClass("hide");
	                }
	      	}else{
	      		showTip("获取问答疑惑数据失败");
	      	}
	    });
	}
//	点击查看弟子
	$(".myself-disciple-list").on("click",".see-disciple-btn",function(){
		var index=$(this).attr("data-index");
		var previewDisciple=disciple[index];
	//	是否审核通过
		if(previewDisciple.applied==true){
			$(".pass-through").addClass("hide");
		}else{
			$(".pass-through").removeClass("hide");
		}
	//	性别
		if(previewDisciple.sex==1){
			previewDisciple.sex="男";
		}else{
			previewDisciple.sex="女";
		}
	//	学历
		if(previewDisciple.education==0){
			previewDisciple.education="无";
		}else if(previewDisciple.education==5){
			previewDisciple.education="本科";
		}else if(previewDisciple.education==6){
			previewDisciple.education="硕士";
		}else if(previewDisciple.education==7){
			previewDisciple.education="博士";
		}
		$(".see-modal-scroll .name").text(previewDisciple.name);
		$(".see-modal-scroll .number").text(previewDisciple.tel);
		$(".see-modal-scroll .age").text(previewDisciple.age);
		$(".see-modal-scroll .sex").text(previewDisciple.sex);
		$(".see-modal-scroll .nativePlace").text(previewDisciple.nativePlace);
		$(".see-modal-scroll .education").text(previewDisciple.education);
		$(".see-modal-scroll .educationExperience").text(previewDisciple.educationExperience);
		$(".see-modal-scroll .medicalExperience").text(previewDisciple.medicalExperience);
		$(".see-modal-scroll .goal").text(previewDisciple.goal);
		
		$(".save-id-disciple").val(previewDisciple.id)
		$("#mask").removeClass("hide");
		$(".see-disciple-modal").removeClass("hide");
	})
//	审核是否通过
	$(".see-disciple-modal").on("click",".adopt-disciple",function(){
		var status=$(this).attr("data-apprentice");
		var id=$(".save-id-disciple").val();
		RequestService("/doctor/apprentice/"+id+"/"+status+"","PUT",null, function (data) {
		 	if (data.success==true) {
		 		showTip("操作成功");
		 		closeDisciple()
		 	} else{
		 		showTip("操作失败");
		 		closeDisciple()
		 	}
	   })
	})
	
	
//	关闭模态框
	function closeDisciple(){
		$("#mask").addClass("hide")
		$(".see-disciple-modal").addClass("hide");
	}
	$(".see-modal-top span img").click(function(){
		closeDisciple()
	})

