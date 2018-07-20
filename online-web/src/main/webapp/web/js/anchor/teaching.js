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
//	师承管理
	$(".teaching-manage").click(function(){
		manageList(1)
		if($(".change-namage-reyurn").text()=="返回"){
			$(".change-namage-reyurn").click();
		}
	});
//	省市区三联动初始化
	$(".comment-right-float").iProvincesSelect("init",null);
//	远程诊疗
	$(".teaching-range").click(function(){
		rangeList(1);
		if($(".long-range-btn").text()=="返回"){
			$(".long-range-btn").click();
		}
	});

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
 * @author name：牛男 <br>email: wangxingchuan@ixincheng.com
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
 * @author name：牛男 <br>email: wangxingchuan@ixincheng.com
 * @Date: 2018/7/17 0003 上午 20:07
 **/
//	我的弟子列表
	var disciple;
	function myDiscipleList(pages,type,status) {	
		var discipleData={}
			discipleData.page=pages,
	    	discipleData.size=10;
	    if(type!=null && type!=""){
			discipleData.type=type;
		};
		if(status!=null && status!=""){
			discipleData.status=status;
		};
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

//	筛选
	var disciplaStatus,
		disciplaType;
	$(".myself-disciple-search button").click(function(){
			disciplaStatus=$(".myself-select-haedeer").val();
			disciplaType=$(".myself-select-right").val();
			myDiscipleList(1,disciplaType,disciplaStatus);
	})

//	点击查看弟子
	$(".myself-disciple-list").on("click",".see-disciple-btn",function(){
		var index=$(this).attr("data-index");
		var previewDisciple=disciple[index];
	//	是否审核通过
		if(previewDisciple.apprentice==1){
			$(".pass-through").addClass("hide");
		}else{
			$(".pass-through").removeClass("hide");
		}
	//	性别
		if(previewDisciple.sex==1){
			previewDisciple.sex="男";
		}else if(previewDisciple.sex==0){
			previewDisciple.sex="女";
		}else if(previewDisciple.sex==2){
			previewDisciple.sex="未知";
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
		 		closeDisciple();
		 		myDiscipleList(1,disciplaType,disciplaStatus);
		 	} else{
		 		showTip("操作失败");
		 		closeDisciple();
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

/**
 * Description：收徒设置
 * creed: Talk is cheap,show me the code
 * @author name：牛男 <br>email: wangxingchuan@ixincheng.com
 * @Date: 2018/7/18 0003 上午 09:38
 **/
	function clearApprentice(){

		$(".how-apprentice").val("");
		$(".apprentice-welfare").val("");
		$(".price").val("");
	}
	function provingApprentice(apprentice){
		var reg = /^[0-9]+.?[0-9]*$/;
		if(apprentice.requirement==""){
			$(".error-set").removeClass("hide");
			return false;
		}else{
			$(".error-set").addClass("hide");
		}if(apprentice.welfare==""){
			$(".error-welfare").removeClass("hide");
			return false;
		}else{
			$(".error-welfare").addClass("hide");
		}
		if(apprentice.cost != null){
			if(reg.test(apprentice.cost)==true){
				$(".error-price").addClass("hide");
			}else{
				$(".error-price").removeClass("hide");
				return false;
			}
		}else{
			$(".error-price").addClass("hide");
		}
		return true;
	}

	$(".comment-apprentice button").click(function(){
		var that=$(this);
		var apprentice={			
			 "requirement": $.trim($(".how-apprentice").val()),
             "welfare": $.trim($(".apprentice-welfare").val()),
             "cost":$.trim($(".comment-apprentice .price").val())
		};
		if (provingApprentice(apprentice)) {
			that.attr("disabled","disabled");
			RequestJsonService("/doctor/apprentice/settings","POST",JSON.stringify(apprentice), function (data) {
				if(data.success==true){
					showTip("保存成功");
					$(".comment-apprentice button").removeAttr("disabled");
				}else{
					showTip("保存失败");
					$(".comment-apprentice button").removeAttr("disabled");
				}
			})
			
		} 		
	})
//	收徒设置回显
		RequestService("/doctor/apprentice/settings","GET",null, function (data) {
		 	if (data.success==true) {
		 		var echoData=data.resultObject
				$(".how-apprentice").val(echoData.requirement);
				$(".apprentice-welfare").val(echoData.welfare);
				$(".comment-apprentice .price").val(echoData.cost);

		 	}
	   	})

/**
 * Description：师承管理
 * creed: Talk is cheap,show me the code
 * @author name：牛男 <br>email: wangxingchuan@ixincheng.com
 * @Date: 2018/7/18 0003 上午 09:38
 **/
//	师承管理列表
	var manageData;
	function manageList(pages){
    RequestService("/doctor/enrollmentRegulations?page="+pages+"&size=10", "get",null, function (data) {
      	if(data.success==true){
      		manageData=data.resultObject.records;
      		if(manageData==null || manageData.length==0){
      			$(".manage-null").removeClass("hide");
      			$("#manage-list-wrap").addClass("hide");
      		}else{
      			$(".manage-null").addClass("hide");
      			$("#manage-list-wrap").removeClass("hide");
      			$("#manage-list-wrap").html(template("template-manage",{items:manageData}))
      		}
     // 分页
              	 if (data.resultObject.pages > 1) { //分页判断
                    $(".not-data").remove();
                    $(".manage_pages").removeClass("hide");
                    $(".manage_pages .searchPage .allPage").text(data.resultObject.pages);  //传的页数的参数
                    $("#Pagination_manage").pagination(data.resultObject.pages, {			//传的页数的参数
                        num_edge_entries: 1, //边缘页数
                        num_display_entries: 4, //主体页数
                        current_page: pages - 1,  //共几页
                        callback: function (page) {
                            //翻页功能
                            manageList(page + 1);
                        }
                    });
                } else {
                    $(".manage_pages").addClass("hide");
                }
      	}else{
      		showTip("获取问答疑惑数据失败");
      	}
    });
}

//	上下架
	$(".namage-list-table").on("click",".ecruit-fluctuate",function(){
		var id=$(this).attr("data-id"),
			status=$(this).attr("data-status");
			RequestService("/doctor/enrollmentRegulations/"+id+"/"+status,"PUT",null, function (data) {
				if (data.success==true) {
					showTip("操作成功");
					manageList(1);
				} else{
					showTip("操作失败");
				}
      	})
			
	})

//	查看弹框
	$(".namage-list-table").on("click",".btn-see",function(){
		var index=$(this).attr("data-index"),
			seeData=manageData[index];
		$(".see-details-wrap .see-name").text(seeData.name);
		$(".see-details-wrap .see-title").text(seeData.title);
		$(".see-details-wrap .cover-map-namage img").attr("src",seeData.coverImg);
		$(".see-details-wrap .see-zhaosheng").text(seeData.countLimit);
		$(".see-details-wrap .see-stop-time").text(seeData.deadline);
		$(".see-details-wrap .see-study-time").text(seeData.startTime+"至"+seeData.endTime);
		$(".see-details-wrap .see-address").text(seeData.studyAddress);
		$(".see-details-wrap .see-about").html(seeData.ceremonyAddress);
		$(".see-details-wrap .see-general-rules").html(seeData.regulations);
		$(".download-file a").attr("href",seeData.entryFormAttachment);
		
		
		$(".see-namage-modal").removeClass("hide");
		$("#mask").removeClass("hide");
	})
//	关闭查看弹框
	$(".see-namage-top img").click(function(){
		$(".see-namage-modal").addClass("hide");
		$("#mask").addClass("hide");
	})
//	编辑
	$(".namage-list-table").on("click",".edit-manage",function(){
		var index=$(this).attr("data-index");
		echoManage(index);					//回显
		$(".edit-save").removeClass("hide");//保存按钮显示
		
		$(".namage-list-table").addClass("hide"); //列表切换
		$(".recruit-students").removeClass("hide");	//列表切换
		$(".teacher-name").val(anchors);			//医师名字
		$(".recruit-text-up").addClass("hide");     //保存/发布按钮
		$(".namage-top button").text("返回");		//返回
	})
	function echoManage(index){
		var echoManageData=manageData[index];
		$("#save-manageId").val(echoManageData.id);
		$(".recruit-students .recruit-title").val(echoManageData.title);
		$(".recruit-students .mamage-wrap-img").html("<img src="+echoManageData.coverImg+" />")
		$(".recruit-students .personal-number").val(echoManageData.countLimit);
		$("#sign-up-time").val(echoManageData.deadline);
		$("#study-start-time").val(echoManageData.startTime);
		$("#study-end-time").val(echoManageData.endTime);
	
//		$(".recruit-students .address-text").val(echoManageData.endTime);
		UE.getEditor('about-introduce').setContent(echoManageData.ceremonyAddress);
		UE.getEditor('introduction-enrolment').setContent(echoManageData.regulations);		
		$(".enclosure-text").html(echoManageData.attachmentName)
		fileUrl=echoManageData.entryFormAttachment;
//	省市区		
		var  addressSplit= echoManageData.studyAddress.split("-");
		var provinces = {
                            province: addressSplit[0],
                            city: addressSplit[1],
                            district: addressSplit[2]
        			};
        $(".comment-right-float").iProvincesSelect("init",provinces);
//  详细地址
        var detailAddress=echoManageData.studyAddress.split("-")[3];
		$(".address-text").val(detailAddress);
	}

//	编辑保存
	$(".edit-save").click(function(){
			saveFileName=$(".enclosure-text").text();
			addressText=$.trim($(".address-text").val());			
		var provinceName=$(".comment-right-float .province").val(),
			cityName=$(".comment-right-float .city").val(),
			districtName=$(".comment-right-float .district").val(),
			id=$("#save-manageId").val();
			
		var establishDate={
			"title":$.trim($(".recruit-title").val()),			//标题
			"coverImg":$(".mamage-wrap-img img").attr("src"),	//封面图
			"countLimit":$.trim($(".personal-number").val()),	//招生人数
			"deadline":$.trim($("#sign-up-time").val()),		//报名截止时间
			"startTime":$.trim($("#study-start-time").val()),	//学习时间
			"endTime":$.trim($("#study-end-time").val()),		//结束时间
			"studyAddress":provinceName+"-"+cityName+"-"+districtName+"-"+addressText,
			"ceremonyAddress":UE.getEditor('about-introduce').getContent(),  //相关介绍
			"regulations":UE.getEditor('introduction-enrolment').getContent(), //招生简章
			"entryFormAttachment":fileUrl,
			"attachmentName":saveFileName	
		}	
		if(testRecruit(establishDate)){	
			$(".edit-save").attr("disabled","disabled");
			RequestJsonService("/doctor/enrollmentRegulations/"+id,"PUT",JSON.stringify(establishDate), function (data) {
				if(data.success==true){
					$(".edit-save").removeAttr("disabled");
					showTip("保存成功");
					clearRecruit();
					setTimeout(function(){
						$(".teaching-manage").click();
					},2000);
				}else{
					showTip("保存失败");
					$(".edit-save").removeAttr("disabled");
				}
			})
		}
	})


//	创建招生简章  校验
	var addressText;     //学习详细地址
	function testRecruit(establishDate){
		var reg = /^[0-9]+.?[0-9]*$/;
//	标题
	if(establishDate.title==""){
		$(".title-null").removeClass("hide");
		return false;
	}else{
		$(".title-null").addClass("hide");
	}
//	封面图
	if($(".mamage-wrap-img img").length==0){
		$(".fengmian-null").removeClass("hide");
		return false;
	}else{
		$(".fengmian-null").addClass("hide");
	}
//	招生人数	
	if(establishDate.countLimit==""){
		$(".personal-null").removeClass("hide");
		return false;
	}else{
		$(".personal-null").addClass("hide");
	}
	if(reg.test(establishDate.countLimit)==false){
		$(".personal-alb-null").removeClass("hide");
		return false;
	}else{
		$(".personal-alb-null").addClass("hide");
	}
//	报名截止时间	
	if(establishDate.deadline==""){
		$(".sign-up-null").removeClass("hide");
		return false;
	}else{
		$(".sign-up-null").addClass("hide");
	}
//	学习时间	
	if(establishDate.startTime=="" || establishDate.endTime==""){
		$(".studyTime-null").removeClass("hide");
		return false;
	}else{
		$(".studyTime-null").addClass("hide");
	}	
//	开始时间不能大于结束时间
	if(establishDate.startTime>establishDate.endTime){
		showTip("开始时间不能大于结束时间");
		return false;
	}
//	开始时间不能小于报名截止时间
	if(establishDate.startTime<establishDate.deadline){
		showTip("开始时间不能小于报名截止时间");
		return false;
	}
//  学习地址
	if($.trim($(".address-text").val())==""){
		$(".studyAddress-null").removeClass("hide");
		return false;			
	}else{				
		$(".studyAddress-null").addClass("hide");
	}
//	相关介绍	
	if(establishDate.ceremonyAddress==""){
		$(".about-introduce-null").removeClass("hide");
		return false;
	}else{
		$(".about-introduce-null").addClass("hide");
	}	
//	招生简介
	if(establishDate.regulations==""){
		$(".introduction-null").removeClass("hide");
		return false;
	}else{
		$(".introduction-null").addClass("hide");
	}
//	招生简章附件
	if($(".enclosure-text").text()==""){
		$(".enclosure-null").removeClass("hide");
		return false;
	}else{
		$(".enclosure-null").addClass("hide");
	}
	return true;
}
//	点击保存
	$(".recruit-text-up").click(function(){
		addressText=$.trim($(".address-text").val());
	var provinceName=$(".comment-right-float .province").val(),
		cityName=$(".comment-right-float .city").val(),
		districtName=$(".comment-right-float .district").val(),
		saveStatus=$(this).attr("data-status");
		
	var establishDate={
		"title":$.trim($(".recruit-title").val()),			//标题
		"coverImg":$(".mamage-wrap-img img").attr("src"),	//封面图
		"countLimit":$.trim($(".personal-number").val()),	//招生人数
		"deadline":$.trim($("#sign-up-time").val()),		//报名截止时间
		"startTime":$.trim($("#study-start-time").val()),	//学习时间
		"endTime":$.trim($("#study-end-time").val()),		//结束时间
		"studyAddress":provinceName+"-"+cityName+"-"+districtName+"-"+addressText,
		"ceremonyAddress":UE.getEditor('about-introduce').getContent(),  //相关介绍
		"regulations":UE.getEditor('introduction-enrolment').getContent(), //招生简章
		"entryFormAttachment":fileUrl,
		"attachmentName":saveFileName,
		"status":saveStatus
	}	
	if(testRecruit(establishDate)){	
		$(".recruit-text-up").attr("disabled","disabled");
		RequestJsonService("/doctor/enrollmentRegulations","POST",JSON.stringify(establishDate), function (data) {
			if(data.success==true){
				$(".recruit-text-up").removeAttr("disabled");
				showTip("添加成功");
				clearRecruit();
				setTimeout(function(){
					$(".teaching-manage").click();
				},2000);
			}else{
				showTip("添加失败");
				$(".recruit-text-up").removeAttr("disabled");
			}
		})
	}
	
})

//	选择附件上传 
   var fileUrl;  //需要的url
   var saveFileName;
   $('#file-input').change(function(){ 
     //如果文件为空 
     if($(this).val() == ''){ 
       return false; 
     }else{
     	saveFileName=$(this).val().slice(12);
     	$(".enclosure-text").text(saveFileName);
     }
     $('#submitFile').ajaxSubmit({ 
       type:'post', 
       dataType:'json', 
       success:function(result){ 
         //请求成功后的操作 
  			fileUrl=result.url;
         //并且清空原文件，不然选择相同文件不能再次传 
//       $('#file-input').val(''); 
       }, 
       error:function(){ 
         //并且清空原文件，不然选择相同文件不能再次传 
//       $('#file-input').val(''); 
       } 
     }); 
})

//	清空招生简章数据
	var clearFengmian='<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p>'+
						'<p style="text-align: center;color: #999;font-size: 14px;">点击上传封面图片</p>'
								
								
	function clearRecruit(){
		$(".recruit-title").val("");
		$(".mamage-wrap-img").html(clearFengmian);
		$(".personal-number").val("");
		$("#sign-up-time").val("");
		$("#study-start-time").val("");
		$("#study-end-time").val("");
		$(".address-text").val("");
		UE.getEditor('about-introduce').setContent("");
		UE.getEditor('introduction-enrolment').setContent("");
		$(".enclosure-text").text("");
		$("#manage_picIpt").val("");
		$('#file-input').val(""); 
		$(".warning-manage").addClass("hide");
	}
/**
 * Description：远程诊疗
 * creed: Talk is cheap,show me the code
 * @author name：牛男 <br>email: wangxingchuan@ixincheng.com
 * @Date: 2018/7/20 0003 上午 09:52
 **/	
 rangeList(1)
 var rangeData;
function rangeList(pages){	
	RequestService("/doctor/treatment","GET",{
		"pages":pages,
		"size":10
	}, function (data) {
		if (data.success==true) {
			rangeData=data.resultObject.records;
			if (rangeData==null || rangeData.length==0) {
				$(".range-null").removeClass("hide");
				$("#long-range-list").addClass("hide");
			} else{
				$(".range-null").addClass("hide");
				$("#long-range-list").removeClass("hide");
				$("#long-range-list").html(template("long-range-template",{items:rangeData}))
			}
								 // 分页
          	 if (data.resultObject.pages > 1) { //分页判断
                $(".not-data").remove();
                $(".range_pages").removeClass("hide");
                $(".range_pages .searchPage .allPage").text(data.resultObject.pages);  //传的页数的参数
                $("#Pagination_range").pagination(data.resultObject.pages, {			//传的页数的参数
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    current_page: pages - 1,  //共几页
                    callback: function (page) {
                        //翻页功能
                        rangeList(page + 1);
                    }
                });
            } else {
                $(".range_pages").addClass("hide");
            }
		} else{
			showTip("获取诊疗列表失败");
		}
    })
}

//删除预约
var deleteRangeId;
$(".long-range-table").on("click",".appointment-delete",function(){
		deleteRangeId=$(this).attr("data-id");	
			confirmBox.open("删除预约时间","删除后用户将无法进行该时间段的预约，是否确定删除？",function(closefn){
				RequestJsonService("/doctor/treatment/"+deleteRangeId,"DELETE",null, function (data) {
					if(data.success==true){
						showTip("删除成功");
						rangeList(1);
					}else{
						showTip("删除失败");
					}
				})	
					closefn();    //关闭弹窗
				});
			})
//编辑预约
$(".long-range-table").on("click",".edit-range-btn",function(){
	var index=$(this).attr("data-index"),
		editRange=rangeData[index];
		rangeEcho(editRange);	
		$(".ruturn-edit-range").removeClass("hide");
//		切换页面
		$(".new-range-btn").addClass("hide");
		$(".long-range-table").addClass("hide");
		$(".establish-time-wrap").removeClass("hide");
		$(".long-range-top .long-range-btn").text("返回");
		$(".long-range-top span").text("预约时间");
})
function rangeEcho(editRange){
//	把年月日去掉
	var reg = /[\u4e00-\u9fa5]/g,
		newDate=editRange.date.replace(reg,"");			
	$(".comment-establish .time-set").val(newDate);
	$(".comment-establish .start-establish-time").val(editRange.startTime+":00");
	$(".comment-establish .end-establish-time").val(editRange.endTime+":00");
	$("#savaRangeId").val(editRange.id);  //存放ID
}
//编辑后保存时间
	$(".ruturn-edit-range").click(function(){		
		var id=$("#savaRangeId").val(),
		 	longRange={};
			longRange.date=$(".time-set").val();
			longRange.startTime=$(".start-establish-time").val();
			longRange.endTime=$(".end-establish-time").val();
		if(checkRange(longRange)){
			$(".ruturn-edit-range").attr("disabled","disabled");
			RequestJsonService("doctor/treatment/"+id,"PUT",JSON.stringify(longRange), function (data) {
				if(data.success==true){
					showTip("编辑成功");
					$(".teaching-range").click();
					$(".ruturn-edit-range").removeAttr("disabled");
				}else{
					showTip("编辑失败");
					$(".ruturn-edit-range").removeAttr("disabled");
				}
			})
		}
	})
//	点击审核	
	$(".long-range-table").on("click",".to-examine",function(){
		var index=$(this).attr("data-index")
			$(".long-range-table").addClass("hide");
			$(".appointment-details").removeClass("hide");
			$(this).parents().find(".long-range-table").siblings(".long-range-top").find("span").text("预约详情");
			$(this).parents().find(".long-range-table").siblings(".long-range-top").find("button").text("返回");
			echoAppointment(index)
	})
	function echoAppointment(index){
		var appointmentData=rangeData[index];
//		接受/不接受ID
		$("#save-appointment-id").val(appointmentData.id);
//		查看弟子ID
		$("#save-reservations-id").val(appointmentData.apprenticeId);
		$(".appointment-right .appointment-date").html(appointmentData.date.replace(/[-]/g,"")+" "+appointmentData.week+" "+appointmentData.startTime+"-"+appointmentData.endTime)
		$(".appointment-right .appointment-name").html(appointmentData.name);
		$(".appointment-right .appointment-phone-number").html(appointmentData.tel);
		$(".appointment-right .appointment-question").html(appointmentData.question);	
	}
//接受预约	
var appointmentStatus,
	appointmentId;
	$(".appointment-right button").click(function(){
			appointmentStatus=$(this).attr("data-status");
			appointmentId=$("#save-appointment-id").val();
			
			if (appointmentStatus=="false") {
				confirmBox.open("拒绝接受","若拒绝接受，则该预约人的申请资料将不可恢复",function(closefn){
					RequestJsonService("/doctor/treatment/"+appointmentId+"/"+appointmentStatus,"PUT",null, function (data) {
						if(data.success==true){
							showTip("操作成功");
							$(".teaching-range").click();

						}else{
							showTip("操作失败");
						}
					})	
					closefn();    //关闭弹窗
				});
			} 
			else{
				$(".appointment-right button").attr("disabled","disabled");
				RequestJsonService("/doctor/treatment/"+appointmentId+"/"+appointmentStatus,"PUT",null, function (data) {
						if(data.success==true){
							showTip("操作成功");
							$(".teaching-range").click();
							$(".appointment-right button").removeAttr("disabled");
						}else{
							showTip("操作失败");
							$(".appointment-right button").removeAttr("disabled");
						}
					})
			}

	})
	
//	点击查看
	$(".long-range-table").on("click",".see-inf-modal",function(){
		var index=$(this).attr("data-index");
		$(".see-btn-modal").removeClass("hide");
		$("#mask").removeClass("hide");		
		echoDisciple(index);
	})	
	function echoDisciple(index){
		var eachDiscipleData=rangeData[index];
		$("#save-reservations-id").val(eachDiscipleData.apprenticeId); //保存查看弟子ID
		$(".see-disciple-scroll .echo-see-date").html(eachDiscipleData.date.replace(/[-]/g,"")+" "+eachDiscipleData.week+" "+eachDiscipleData.startTime+"-"+eachDiscipleData.endTime)
		$(".see-disciple-scroll .appointment-name").html(eachDiscipleData.name);
		$(".see-disciple-scroll .appointment-number").html(eachDiscipleData.tel);
		$(".see-disciple-scroll .see-appointment-question").html(eachDiscipleData.question);
		discipleInformation(eachDiscipleData.apprenticeId);
}	
//	取消预约
	var reservationId;
	$(".long-range-table").on("click",".cancel-reservation",function(){
		reservationId=$(this).attr("data-id");
		confirmBox.open("取消预约","是否确定取消该名弟子的预约？",function(closefn){
				RequestJsonService("/doctor/treatment/cancel/"+reservationId,"PUT",null, function (data) {
					if(data.success==true){
						showTip("取消预约成功");
						rangeList(1);
					}else{
						showTip("取消失败");
					}
				})	
					closefn();    //关闭弹窗
		});
		
	})
//	弟子报名信息弹窗	
	$(".little_box").on("click",".appointment-name",function(){
		$(".disciple-inf-modal").removeClass("hide");
		$("#mask").removeClass("hide");
		$(".see-btn-modal").addClass("hide");  //关闭查看
		var id=$("#save-reservations-id").val();
		discipleInformation(id)
	})
	function discipleInformation(id){
		RequestService("/doctor/apprentice/"+id,"GET",null, function (data) {
			if(data.success==true){
				$("#disciple-wrap-inf").html(template("template-disciple-wrap",{items:data.resultObject}))
			}else{
				showTip("获取弟子信息失败");
			}
		})	
		
	}
//	关闭弟子报名信息	
	$(".disciple-inf-top span").click(function(){
		$(".disciple-inf-modal").addClass("hide");
		$("#mask").addClass("hide");
	})
	
	
//创建预约时间
function checkRange(longRange){
	if(longRange.date==""){
		$(".error-data").removeClass("hide");
		return false;
	}else{
		$(".error-data").addClass("hide");
	}
	if(longRange.startTime=="" || longRange.endTime == ""){
		$(".error-hour").removeClass("hide");
		return false;
	}else{
		$(".error-hour").addClass("hide");
	}
	if(longRange.startTime > longRange.endTime){
		showTip("开始时间不能大于结束时间");
		return false;
	}
	return true
}
$(".comment-establish .new-range-btn").click(function(){
	var longRange={};
		longRange.date=$(".time-set").val();
		longRange.startTime=$(".start-establish-time").val();
		longRange.endTime=$(".end-establish-time").val();		
		if (checkRange(longRange)) {	
			$(".comment-establish .new-range-btn").attr("disabled","disabled");
			RequestJsonService("doctor/treatment","POST",JSON.stringify(longRange), function (data) {				
				if(data.success==true){
					showTip("创建成功");
					clearRangeTime();
					$(".teaching-range").click();
					$(".comment-establish .new-range-btn").removeAttr("disabled");
				}else{
					showTip("创建失败");
					$(".comment-establish .new-range-btn").removeAttr("disabled")
				}
			})
		}
})

//清空创建时间
function clearRangeTime(){
	$(".comment-establish input").val("");
}
