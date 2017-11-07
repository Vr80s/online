window.onload = function () {
//    $(".header_left .path a").each(function () {
//        if ($(this).text() == "问答精灵") {
//            $(this).addClass("select");
//        } else {
//            $(this).removeClass("select");
//        }
//    });
	addSelectedMenu();
    template.helper('href', function (num) {
        if (num != "") {
            return '' + bath + '/web/qusDetail/' + num;
        } else {
            return 'javascript:;';
        }
    });
	template.helper('qqchange',function(num){
		num="<p>"+num+"</p>";
		value=$(num).text()
	    value=value.replace(/\s/g,"");
		return  value;
//		if($(num).text()!=""&&$(num).text()){
//			var reg=/ */g;
//	        num=num.replace(reg,"");
//	        return $(num).text();	
//		}else{
//			return num;
//		}
    })
    var ansAndQusRight =
        /*"<div class='ansAndQusRight-top'>" +
        "<div class='ansAndQusRight-top-back'>" +
        "<p><img src='../images/ansandqus/ask.png'/></p>" +
        "<p>找不到想要的答案？</p>" +
        "<a href='javascript:;' class='goQuiz'>我要提问</a>" +
        "</div>" +
        "</div>" +*/
       /* "<div class='ansAndQusRight-bottom'>" +
        "<div class='ansAndQusRight-hot'>" +
        "<p class='ansAndQusRight-top-title'>热门问答</p>" +
        "<div class='ansAndQusRight-top-title-noDate'>" +
        "<img src='../images/ansandqus/my_nodata.png'/>" +
        "<p>暂无数据</p>" +
        "</div>" +*/
        "{{each data as h}}" +
        "<div>" +
        "<a href='{{#href(h.id)}}' data-id='{{h.id}}' target='_blank'><span class='ansAndQusRight-top-body' data-text='{{h.title}}'title='{{h.title}}'>{{h.title}}</span>" +
        "</a>" +
        "<p class='ansAndQusRight-top-sum' data-sum='{{h.answer_sum}}'>回答({{h.answer_sum}})</p>"+
        "</div>" +
        "{{/each}}"
        /*"</div>" +*/
    /*    "<div class='weekly'>" +
        "<p class='weekly-title'>周回答排行榜</p>" +
        "<div class='weeklyChart'>" +
        "</div>" +
        "</div>" +*/
       /* "<div class='weeklyChartNoDate'>" +
        "<img src='../images/ansandqus/my_nodata.png'/>" +
        "<p>暂无数据</p>" +
        "</div>"+
        "</div>";*/
    var weekly =
        "{{each data as w}}" +
        "<div class='weekly-body'>" +
        "{{if w.small_head_photo == '/web/images/defaultHeadImg.jpg'}}" +
        "<img src='" + bath + "{{w.small_head_photo}}'/>" +
        "{{else}}" +
        "<img src='{{w.small_head_photo}}'/>" +
        "{{/if}}" +
        "<div class='weekly-body-right'>" +
        "<p class='weekly-body-name' title='{{w.nike_name}}'>{{w.nike_name}}</p>" +
        "<p class='weekly-body-sum'>{{w.answer_sum}}个回答</p>" +
        "</div>" +
        "</div>" +
        "{{/each}}";
    var kongbai = "<div class='kongbai'>" +
        "<img src='../images/ansandqus/noask.png'/>" +
        "<p>暂无问答</p>" +
        "</div>";
    var searchkongbai = "<div class='kongbai'>" +
        "<img src='../images/ansandqus/nosearch.png' style='margin-top:188px;'/>" +
        "<p>抱歉，没有找到<span class='searchkongbaiBody'></span>相关内容</p>" +
        "<div class='returnAnsAndQus'>返回问答首页</div>"+
        "</div>";

    var ansAndQusLeftList = "{{each data.items as d}}" +
        "<div class='listansAndQuse'>" +
        "<div class='list-questions'>" +
        "<div class='list-questions-left'>" +
        "<div class='list-questions-left-img'>" +
        "{{if d.create_head_img == '/web/images/defaultHeadImg.jpg'||d.create_head_img==null}}" +
        "<img src='" + bath + "/web/images/defaultHeadImg.jpg''/>" +
        "{{else}}" +
        "<img src='{{d.create_head_img}}'/>" +
        "{{/if}}" +
        "</div>" +
        "<p class='list-questions-left-name' title='{{d.create_nick_name}}'>{{d.create_nick_name}}</p>" +
        "</div>" +
        "<div class='list-questions-right'>" +
        "<p class='list-questions-right-subject'>来自学科：<span class='subject-table' data-id='{{d.ment_id}}'>{{d.name}}</span></p>" +
        "<a href='{{#href(d.id)}}' target='_blank' class='golist' data-id='{{d.id}}'><span class='list-questions-right-title' title='{{d.title}}'>{{d.title}}</span></a>" +
        "<div class='list-questions-right-text list-questions-right-textshenglue dot5'>{{qqchange(d.content)}}</div>" +
        "<p class='list-questions-right-timeAndTag'>" +
        "{{each d.tag as t}}" +
        "<span class='list-questions-right-tag'>{{t}}</span>" +
        "{{/each}}" +
        "<span class='list-questions-right-time'>{{d.strTime}}</span>" +
        "<span class='list-questions-right-look' data-sum='{{d.browse_sum}}'>浏览数({{d.browse_sum}})</span>" +
        "</p>" +
        "</div>" +
        "</div>" +
        "{{if d.askAnswerVo != null}}" +
        "<div class='list-Answer'>" +
        "<div class='list-Answer-left'>" +
        "<div class='list-Answer-left-btn'>" +
        "<p class='list-Answer-left-btn-top'>回答</p>" +
        "<p class='list-Answer-left-btn-sum'>{{d.answer_sum}}</p>" +
        "</div>" +
        "</div>" +
        "<div class='list-Answer-right'>" +
        "<p class='list-Answer-right-name'>{{d.askAnswerVo.create_nick_name}}：</p>" +
        "<p class='list-Answer-right-text dot5'>{{qqchange(d.askAnswerVo.content)}}</p>" +
	        "{{if d.askAnswerVo.answer_type==false}}"+
	        "<p class='list-Answer-right-sum'>" +
	        "{{if d.askAnswerVo.praise == false}}" +
	        "<span class='praise-good' data-pariseId='{{d.askAnswerVo.id}}'>" +
	        "<i class='iconfont icon-zan' style='color:#999'></i>" +
	        "<span class='dianzan'>点赞(</span>" +
	        "<span class='dianzanshu'>{{d.askAnswerVo.praise_sum}})</span>" +
	        "{{else}}" +
	        "<span class='praise-good' data-pariseId='{{d.askAnswerVo.id}}'>" +
	        "<i class='iconfont icon-zan' style='color:#2cb82c'></i>" +
	        "<span class='dianzan' style='color:#2cb82c'>已赞(</span>" +
	        "<span class='dianzanshu' style='color:#2cb82c'>{{d.askAnswerVo.praise_sum}})</span>" +
	        "{{/if}}" +
	        "</span>" +
	        "{{if d.askAnswerVo.copyright == true}}" +
	        "<span class='biaoliuquanli'><img src='../images/ansandqus/sakdetial_equal.png'>作者保留权利</span>" +
	        '<div class="rights-reserved">' +
	        "<div class='sanjiao'><img src='../images/ansandqus/sanjiao02.png'/></div>" +
	        '用户在熊猫中医上发表的全部原创内容（包括但不限于提问，问答和评论），著作权均归用户本人所有。用户可授权第三方以任何方式使用，不需要得到熊猫中医的同意。</div>' +
	        "{{/if}}" +
	        "</p>" +	
	        "{{/if}}"+
        "</div>" +
        "</div>" +
        "{{/if}}" +
        "</div>" +
        "{{/each}}";

	 //获取学科列表
    RequestService("/online/menutag/getMenuTags", "GET", "", function (data) {
        $(".select-type").append(template("select-con", {
            menu: data.resultObject
        }));
        if(list.tag == null || list.tag == ""){
		if(list.menuId != "" || list.menuId != null){
			if(list.menuId== "-1"){
				$(".select-type .menu-title").text("全部分类");
				$("<li data-id='-1' data-type='-1' class='ans' title='全部分类'><span data-id='-1' data-type='-1' >全部分类 </span></li>").prependTo($(".select-type-ul")).css({"background":"0","display":"block"});
		        if($(".control-bar .select-type .menu-title").text() == "全部分类"){
		            $(".select-type .select-type-ul li:first").css("display","none");
		        }else{
		            $(".select-type .select-type-ul li:first").css("display","block");
		        }
            	$(".ans").css("display","none");
            }else{
            	$("<li data-id='-1' data-type='-1' class='ans' title='全部分类'><span data-id='-1' data-type='-1' >全部分类 </span></li>").prependTo($(".select-type-ul")).css({"background":"0","display":"block"});
            	$(".ans").css("display","block");
            	$(".select-type-ul li").each(function (i) {
                if ($(this).find("span").text() == "全部") {
                    $(this).css({
                        background: "0",
                        display: "none"
                    }).attr("data-id", "").find(".select-type-ul-dv").remove();
                }
        	});
            	 $(".select-type-ul li").each(function (i) {
                    if ($(this).data("id") == "") {
                        $(this).css({
                            display: "none"
                        });
                    }
                    if($(this).attr("data-id") == list.menuId){
                    	$(".select-type .menu-title").text($(this).find("span").text()).attr({"data-id":list.menuId,"title":$(this).find("span").text()});
                    	$(".link-bar .btn-link").css("color", "#333");
                        $(".link-bar .btn-link-all").css("color", "#2db82d");
//                      $(this).remove();
                    }
               });
            }
		}
		}else{
            $("<li data-id='-1' data-type='-1' class='ans' title='全部分类'><span data-id='-1' data-type='-1' >全部分类 </span></li>").prependTo($(".select-type-ul")).css({"background":"0","display":"block"});
            $(".ans").css("display","block");
			$(".select-type .menu-title").text(list.tag).attr({"title":list.tag}).attr("data-type", "1");
            $(".link-bar .btn-link").css("color", "#333");
            $(".link-bar .btn-link-all").css("color", "#2db82d");
            $(".select-type-ul li").each(function (i) {
                if ($(this).find("span").text() == "全部") {
                    $(this).css({
                        background: "0",
                        display: "none"
                    }).attr("data-id", "").find(".select-type-ul-dv").remove();
                }
        	});
		}
		
        $(".select-type-ul li").each(function (i) {
            if ($(this).attr("data-id") == "0") {
                $(this).css({
                    background: "0",
                    display: "none"
                }).attr("data-id", "").find(".select-type-ul-dv").remove();
            }
        });
        $(".menu-title").click(function(){
        	if($(this).attr("data-id") != null && $(this).attr("data-id") != ""){
        		list.menuId = $(this).attr("data-id");
                list.title = "";
                list.tag = "";
                list.pageNumber = 1;
            	location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=-1");
        	}else if($(this).attr("data-type") == 1){
        		list.menuId = -1;
                list.title = "";
                list.tag = encodeURIComponent($.trim($(this).text()));
                list.pageNumber = 1;
            	location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=-1");
        	}else{
        		list.menuId = -1;
                list.title = "";
                list.tag = "";
                list.pageNumber = 1;
            	location.href = "ansAndQus.html?page=" + list.pageNumber + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=-1";
        	}
        	$(".ansAndQus-Title span:first").click();
        })
        $(".select-type-ul").click(function (evt) {
            var target = $(evt.target);
            if(target.text()=="全部分类 "){
            	$(".ans").css("display","none");
            } else if(target.hasClass("select-type-ul-dv-d")||target.hasClass("select-type-ul-dv-p") ||target.hasClass("select-type-ul-dv")){
            }else{
            	$(".ans").css("display","block");
            }
            if (target.hasClass("menuItem")) {
                $(".select-type-ul").css("display", "none");
                $(".select-type-ul-dv").css("display", "none");
                setTimeout(function () {
                    $(".select-type-ul").removeAttr("style");
                    $(".select-type-ul-dv").removeAttr("style");
                }, 100);
                urlParam.menuId = target.attr("data-id");
                if (urlParam.menuId != null) {
                    list.menuId = -1;
                    list.title = "";
                    list.tag = encodeURIComponent($.trim(target.text()));
                    list.pageNumber = 1;
            		location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=-1");
                }
            } else if(target.hasClass("select-type-ul-dv-d") ){
            	return false;
            }else {
                $(".select-type-ul").css("display", "none");
                setTimeout(function () {
                    $(".select-type-ul").removeAttr("style");
                }, 100);
                target = target;
                if (target.attr("data-id") == undefined) {
                    list.menuId = ""
                } else {
                    list.menuId = target.attr("data-id");
                }
                if (list.menuId != null && list.menuId != "") {
                    if (target.attr("data-id") != undefined) {
                        list.menuId = target.attr("data-id");
                    } else {
                        list.menuId = target.parent().attr("data-id");
                    }
                    list.title = "";
                    list.tag = "";
                    list.pageNumber = 1;
                    $(".search-ansAndQus input").val("");
            	location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=-1");
                }
            }
        });
        $(".link-bar .btn-link-all").css("color", "#2db82d");
        $(".link-bar").click(function (evt) {
            $(evt.target).hasClass("btn-link-all") ? function () {
                type = "";
                menuId = $(".menu-title").attr("data-id");
                $(".link-bar .btn-link").css("color", "#333");
                $(evt.target).css("color", "#2db82d");
            }() : function () {
                type = 5;
                menuId = $(".menu-title").attr("data-id");
                $(".link-bar .btn-link").css("color", "#333");
                $(evt.target).css("color", "#2db82d");
            }();
            //$(".select-type .menu-title").text("全部分类");
            urlParam.type = type;
            urlParam.pageNumber = 1;
            urlParam.menuId = menuId;
            list.title = "";
            location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=-1");

        });
    });
    RequestService("/online/questionlist/getHotAnswer", "GET", list, function (data) {
        $(".ansAndQusRight-hot").append(template.compile(ansAndQusRight)({
            data: data.resultObject
        }));
        if(data.resultObject.length!=0){
            $(".ansAndQusRight-top-title-noDate").css("display","none");
        }else{
            $(".ansAndQusRight-top-title-noDate").css("display","block");
        }
        $(".ansAndQusRight-top-body").parent().on("click",function(){
            RequestService("/online/questionlist/updateBrowseSum", "POST", {id:$(this).attr("data-id")}, function (data) {

            });
        });
        $(".goQuiz").on("click", function () {
            RequestService("/online/user/isAlive", "GET", null, function (data) { ///online/user/isAlive
                if (data.success) {
//					$(".goQuiz").attr("href","quiz.html");
                    location.href = "quiz.html"
//					$(".goQuiz").attr("href","javascript:;");
                } else {
                    var pathS = window.location.href;
                    localStorage.username = null;
                    localStorage.password = null;
                    $(".login").css("display", "none");
                    $(".logout").css("display", "block");
                    $("#login").modal("show");
                }
            });
        })
        RequestService("/ask/answer/findAnswersWeekRankingList", "GET", {
	        sum: 8
	    }, function (data1) {
	    	$(".weeklyChartNoDate").css("display","none");
	       if(data1.resultObject=="" || data1.resultObject==null){
	           $(".weeklyChartNoDate").css("display","block");
	       }else{
	           $(".weeklyChartNoDate").css("display","none");
	       		$(".weeklyChart").html(template.compile(weekly)({
		            data: data1.resultObject
		        }));
	       }
	    });
    });
    //获取地址栏参数
	$.getUrlParam = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
    var menuId = "",
        type = "",
        urlParam = {
            menuId: menuId,
            type: type,
            pageSize: 20,
            pageNumber: 1
        },
        requstUrl;
    var arrcolor = ["#f8b551", "#91b3d5", "#ec6941"];
    var list = {
        pageNumber: 1,
        pageSize: 7,
        menuId: -1,
        status: -1,
        tag: "",
        title: "",
        text: "",
        content: ""
    }
    //分页参数
    if($.getUrlParam("page") == null || $.getUrlParam("page") == ""){
		list.pageNumber = 1;
	}else{
		list.pageNumber = $.getUrlParam("page");
	}
	//标签名参数
	if($.getUrlParam("tag") == null || $.getUrlParam("tag") == ""){
		list.tag = "";
	}else{
		list.tag = decodeURI($.getUrlParam("tag"));
	}
	//学科id参数
	if($.getUrlParam("menuId") == null || $.getUrlParam("menuId") == ""){
		list.menuId = -1;
	}else{
		list.menuId = $.getUrlParam("menuId");
	}
	//标题参数
	if($.getUrlParam("title") == null || $.getUrlParam("title") == ""){
		list.title = "";
	}else{
		list.title = decodeURIComponent($.getUrlParam("title"));
	}
	//状态参数
	if($.getUrlParam("status") == null || $.getUrlParam("status") == ""){
		list.status = -1;
	}else{
		list.status = $.getUrlParam("status");
	}
    //搜索点击事件
    $(".search-ansAndQus input").focus(function () {
        $(".search-ansAndQus").css("border","1px solid #2cb82c");
        $(this).parent().find("span").addClass("hide");
        $(document).keydown(function (e) {
            if (e.which == 13) {
                if ($.trim($(".search-ansAndQus input").val()) != "") {
                	location.href = encodeURI("ansAndQus.html?page=1" + "&title=" + encodeURIComponent($.trim($(".search-ansAndQus input").val())) + "&menuId=-1" + "&tag=" + "" + "&status=-1");
                   
                }
            }
        });
    });
    $(".search-ansAndQus input").blur(function () {
        $(".search-ansAndQus").css("border","1px solid #e4e4e4");
        if ($(this).val() == "") {
            $(this).parent().find("span").removeClass("hide");
        }
    });
    //获取问题列表
    if(window.localStorage.biaoqian != "" && window.localStorage.biaoqian != null && window.localStorage.biaoqian != undefined){
        list.title = "";
        list.menuId = -1;
        list.tag = window.localStorage.biaoqian;
        list.pageNumber = 1;
        $(".content-list-qus .control-bar .select-type .menu-title").text(window.localStorage.biaoqian);
        if($(".select-type-ul li:first").css("display") == "none"){
            $(".select-type-ul li:first").css("display","block");
        }
        getQuestion();
    }else if(window.localStorage.xuekeid != "" && window.localStorage.xuekeid != null && window.localStorage.xuekeid != undefined){
        list.type = "";
        list.pageNumber = 1;
        list.menuId = window.localStorage.xuekeid;
        list.tag = "";
        list.title = "";
        $(".content-list-qus .control-bar .select-type .menu-title").text(window.localStorage.xueke);
        if($(".select-type-ul li:first").css("display") == "none"){
            $(".select-type-ul li:first").css("display","block");
        }

        getQuestion();
    }else{
        getQuestion();
    }
    
    function getQuestion() {
        RequestService("/online/questionlist/getQuestionList", "GET", list, function (data) {
            window.localStorage.biaoqian = "";
            window.localStorage.xueke = "";
            window.localStorage.ment_id = "";
            window.localStorage.xuekeid = "";
            if(list.title != "" && list.title != null){
            	$(".search-ansAndQus input").val(list.title);
            }else{
                $(".search-ansAndQus span").removeClass("hide");
            }
           
            if (data.resultObject.totalPageCount > 1) { //分页判断
                $(".not-data").remove();
                $(".content .pages").css("display", "block");
                $(".content .pages .searchPage .allPage").text(data.resultObject.totalPageCount);
                if (data.resultObject.currentPage == 1) {
                    $("#Pagination").pagination(data.resultObject.totalPageCount, {
                        callback: function (page) { //翻页功能
                        	location.href = encodeURI("ansAndQus.html?page=" + (page + 1) + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=" + list.status);
                        }
                    });
				
                }else{
                	$("#Pagination").pagination(data.resultObject.totalPageCount, {
                        callback: function (page) { //翻页功能
                        	location.href = encodeURI("ansAndQus.html?page=" + (page + 1) + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=" + list.status);
                        }
                  });
                }
				
            } else {
                $(".content .pages").css("display", "none");
                $(".ansAndQus-left-list").css("margin-bottom","45px");
            }
            
            if (data.resultObject.items.length == 0) {
                if ($.trim($(".search-ansAndQus input").val()) != "") {
                    $(".ansAndQus-left-list").html(template.compile(searchkongbai));
                    $(".searchkongbaiBody").html(list.title);
                    $(".returnAnsAndQus").click(function(){
                    location.href = "ansAndQus.html?page=1" + "&title=" + "" + "&menuId=-1" + "&tag=" + "" + "&status=-1";
                    })
                } else {
                    $(".ansAndQus-left-list").html(template.compile(kongbai));
                }
            } else {
                $(".ansAndQus-left-list").html(template.compile(ansAndQusLeftList)({
                    data: data.resultObject
                }));
                $(".list-questions-right-title").parent().on("click",function(){
                    var $this = $(this)
                    RequestService("/online/questionlist/updateBrowseSum ", "POST", {id:$(this).attr("data-id")}, function (data) {
                        var num = parseInt($this.parent().find(".list-questions-right-timeAndTag").find(".list-questions-right-look").attr("data-sum"))+1;
                        $this.parent().find(".list-questions-right-timeAndTag").find(".list-questions-right-look").text("浏览数(" + num + ")");
                    });
                });
                
               /* $(".golist").click(function () {
                    RequestService("/online/questionlist/updateBrowseSum", "POST", {
                        id: $(this).attr("data-id")
                    }, function (data) {
                        location.reload();
                    });
                });*/
                $(".list-questions-right-tag").click(function () {
                    location.href = encodeURI("ansAndQus.html?page=1" + "&title=" + "" + "&menuId=-1" + "&tag=" + encodeURIComponent($(this).text()) + "&status=-1");
                });
                $(".subject-table").click(function () {
                    if ($(this).attr("data-id") == 0) {
                        list.menuId = -1;
                    } else {
                        list.menuId = $(this).attr("data-id");
                    }
                    list.title = "";
                    list.tag = "";
                    list.status = -1;
                    list.pageNumber = 1;
                    location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + list.title + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=" + list.status);
                    
                });
                $(".ansAndQus-left-list").closeloading();
                //点赞
                $(".praise-good").click(function () {
                    var $this = $(this);
                    RequestService("/online/user/isAlive", "GET", "", function (data) {
                        if (!data.success) {
                            localStorage.username = null;
                            localStorage.password = null;
                            if ($(".login").css("display") == "block") {
                                $(".login").css("display", "none");
                                $(".logout").css("display", "block");
                                $('#login').modal('show');
                                $("html").css({
                                    "overflow-x": "hidden",
                                    "overflow-y": "hidden"
                                });
                            } else {
                                $('#login').modal('show');
                                $("html").css({
                                    "overflow-x": "hidden",
                                    "overflow-y": "hidden"
                                });
                            }
                            return;
                        } else {
                            RequestService('/ask/answer/praiseAnswer', 'GET', {
                                answer_id: $this.attr("data-pariseId")
                            }, function (data) {
                                var nums = parseInt($this.find(".dianzanshu").text());
                                if (data.resultObject.praise == true) {
                                    $this.find(".dianzan").text("已赞(");
                                    $this.find(".dianzanshu").text(data.resultObject.sum + ")");
                                    $this.find("i").css("color","#2cb82c");
                                    $this.find("span").css("color", "#2cb82c");
                                } else {
                                    $this.find(".dianzan").text("点赞(");
                                    $this.find(".dianzanshu").text(data.resultObject.sum + ")");
                                    $this.find("i").css("color","#999")
                                    $this.find("span").css("color", "#999");
                                }
                            });
                        }
                    });

                });
                //保留作者权力
                $(".biaoliuquanli").on("click", function (event) {
                    event.stopPropagation();
                    $(this).parent().parent().find(".rights-reserved").toggle();
                })
                $(document).click(function () {
                    $(".rights-reserved").css("display", "none");
                })
                var $dot5 = $('.dot5');
                $dot5.each(function () {
                    if ($(this).height() > 40) {
                        $(this).attr("data-txt", $(this).attr("data-text"));
                        $(this).height(40);
                        $(this).append('<span class="qq" style="margin-right:60px"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">显示全部</span><span class="closes">收起</span></a></span>');
                    }
                    var $dot4 = $(this);

                    function createDots() {
                        $dot4.dotdotdot({
                            after: 'span.qq'
                        });
                    }

                    function destroyDots() {
                        $dot4.trigger('destroy');
                    }

                    createDots();
                    $dot4.on(
                        'click',
                        'a.toggle',
                        function () {
                            $dot4.toggleClass('opened');

                            if ($dot4.hasClass('opened')) {
                                destroyDots();
                            } else {
                                createDots();
                            }
                            return false;
                        }
                    );
                });
            }
            $(".ansAndQus-Title span").each(function(){
            	if($(this).attr("data-status") == list.status){
            		$(this).addClass("selectedBtn");
            	}else{
            		$(this).removeClass("selectedBtn");
            	}
            });
             
        });
    }

    $(".ansAndQus-Title span").click(function () {
        if ($(this).hasClass("selectedBtn")) {

        } else {
            $(this).parent().find("span").removeClass("selectedBtn");
            $(this).addClass("selectedBtn");
            list.status = $(this).attr("data-status");
            list.pageNumber = 1;
            location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + encodeURIComponent(list.title) + "&menuId=" + list.menuId + "&tag=" + encodeURIComponent(list.tag) + "&status=" + list.status);
        }
    });
   
    //点击列表展示内容也可搜索
//  $(".menu-title").click(function(){
//      list.type = "";
//      list.pageNumber = 1;
//      list.menuId = window.localStorage.xuekeid;
//      list.tag = "";
//      list.title = "";
//      getQuestion();
//  })
    //搜索
    $(".search-ansAndQus i").off().on("click",function () {
    	if($.trim($(".search-ansAndQus input").val()) == ""){
    		return false;
    	}
    	
        if ($.trim($(".search-ansAndQus input").val()) != "") {
            list.tag = "",
            list.menuId = -1,
            list.title = $(".search-ansAndQus input").val();
            list.pageNumber = 1;
           	location.href = encodeURI("ansAndQus.html?page=" + list.pageNumber + "&title=" + encodeURIComponent(list.title) + "&menuId=" + list.menuId + "&tag=" + list.tag + "&status=-1");
        }
    });
    //点击标签搜索
    $(".search-ansAndQus span").click(function () {
        list.title = "";
        list.menuId = -1;
        list.tag = $(this).text();
        list.pageNumber = 1;
       	location.href = encodeURI("ansAndQus.html?page=1" + "&title=" + "" + "&menuId=-1" + "&tag=" + encodeURIComponent(list.tag) + "&status=-1");
                   
    });
}


function addSelectedMenu(){
	$(".ansAndQus").addClass("select");
}