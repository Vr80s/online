/**
 * Created by admin on 2016/9/19.
 */
/**
 * Created by admin on 2016/9/19.
 */
window.onload = function() {
    String.prototype.getQuery = function(name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = this.substr(this.indexOf("\?")+1).match(reg);
        if (r != null) return unescape(r[2]);
        return  '';
    }

    var ln=window.location.toString().getQuery('ln');
    var qid=window.location.toString().getQuery('qid');

	// var ourl = document.location.search;
	// var apams = ourl.substring(1).split("&");
	// var arr = [];
	// for(var i = 0; i < apams.length; i++) {
	// 	var apam = apams[i].split("=");
	// 	arr[i] = apam[1];
	// 	debugger
	// 	var qid = arr[0];
	// };
	var self = false; //是否本人提问
	var pageCount = 0; //自定义总页数
	var relative_mentId; //相关课程的Id
	var tags;//相似问题的标签
	var lastAnsNoData = "<div class='lastAnsNoData'>" +
		"<img src='../images/ansandqus/my_nodata.png'/>" +
		"<p>暂无数据</p>" +
		"</div>";
	var myAnswer =
		' <div class="quesImg">' +
		'<img src="{{#headImg(items.create_head_img)}}" alt=""/>' +
		'<p title="{{items.create_nick_name}}">{{items.create_nick_name}}</p>' +
		'</div>' +
		'<div class="quesTitle-left">' +
		//		'{{if items.collectStatu==true}}' +
		//		'<div class="shouBox" style="border:1px solid #6acd6a">' +
		//		'<img class="shoucangImg" src="../images/ansandqus/star.png"/>' +
		//		'<span class="shoucang colorGr">已收藏</span>' +
		//		'</div>' +
		//		'{{else}}' +
		//		'<div class="shouBox">' +
		//		'<img class="shoucangImg" src="../images/ansandqus/star_normal.png"/>' +
		//		'<span class="shoucang">收藏</span>' +
		//		'</div>' +
		//		'{{/if}}' +
		'<div class="questTitle-middle clearfix">' +
		'<div class="questTitle-middle-left">' +
		'<div data-questId="{{items.id}}" class="questId">' +
		'<span class="studentName" data-studentId="{{items.ment_id}}">来自学科：<span class="studentNameTiaozhuang">{{items.name}}</span></span>' +
		'<p class="answer-title">{{items.title}}</p>' +
		'<div class="answer-indrouce">{{#items.content}}</div>' +
		'</div>' +
		'<div class="questTitle-tips clearfix">' +
		'<div class="course-tips-left">' +
		'{{each items.tag as i}}' +
		'<span class="biaoqian">{{i}}</span>' +
		'{{/each}}' +
		'</div>' +
		'<div class="courset-tips-right">' +
//		'{{if items.myself == true}}' +
		'<span class="complaint ansTopRight qingjian" data-id="{{items.id}}">' +
		'<img src="../images/ansandqus/delete_normal.png"/>删除</span><span class="fengexian"></span>' +
//		'{{/if}}' +
//		'{{if items.accused == false}}' +
//		'<span class="complaint ansTopRight wenqiang" data-type="0" data-id="{{items.id}}">' +
//		'<img src="../images/ansandqus/sakdetial_bad.png"/>投诉</span><span class="fengexian"></span>' +
//		'{{else}}' +
//		'<span class="complaint ansTopRight" style="cursor: auto" data-type="0">' +
//		'<img src="../images/ansandqus/sakdetial_bad.png" style="cursor: auto"/>已投诉</span><span class="fengexian"></span>' +
//		'{{/if}}' +
		'<span class="ansTopRight"><img src="../images/ansandqus/liulan.png" alt=""/>浏览({{items.browse_sum}})</span><span class="fengexian"></span>' +
		'<span class="ansTopRight"><em></em>{{timeTypeChange(items.create_time)}}</span>' +
		'</div>' +
		'</div>' +
		'</div></div>' +
		'<div class="richText clearfix">' +
		//		'<form method="post" id="xzform">' +
		//		'<input type="hidden" name="question_id"  value="{{items.id}}" />' +
		//		'<textarea id="editor" placeholder="在这里发表你的见解吧" autofocus maxlength="7000" name="content" style="width:746px;height:243px"></textarea>' +
		//		'<input type="hidden" name="text" id="formtext" value="" />' +
		//		'<input type="hidden" name="copyright" id="formright" value="false" />' +
		//		'<div class="quanli">' +
		//		'<div class="warning text-warn">请输入回答</div><div class="holdRright "></div><span>保留作者权利</span><img class="holdRightImg" src="../images/ansandqus/holdRightImg.png" alt=""/>' +
		//		'<div class="rights-reserved-content1"><img src="../images/personcenter/baoliuTip.png" class="jian">用户在熊猫中医上发表的全部原创内容（包括但不限于提问，问答和评论），著作权均归用户本人所有。用户可授权第三方以任何方式使用，不需要得到熊猫中医的同意。</div>' +
		//		'</div>' +
		//		'<button class="releaseAns" data-id="{{items.id}}" type="button" style="border:0;">发布回答</button>' +
		//		'</form>' +
		'</div>' +
		'</div>';

	/*精彩回答*/
	var good_answer = '{{each items as $value i}}' +
		'<div class="good-answer-content clearfix">' +
		'<div class="good-Answer-content-left">' +
		'<img src="{{#headImg($value.create_head_img)}}" alt=""/>' +
		'</div>' +
		'<div class="good-Answer-content-right">' +
		'<div data-goodId="{{$value.id}}" class="goodId">' +
		'<div class="nickname">{{$value.create_nick_name}}</div>' +
		'<div class="answer">{{#$value.content}}</div>' +
		'</div>' +
		'<div class="good-answer-label clearfix">' +
		'<span class="anwTime">{{#timeTypeChange($value.create_time)}}' +
		'<span class="sanjiao02"><img src="../images/ansandqus/sanjiao02.png"/></span>' +
		'</span>' +
		'<span class="answer-comment" data-pinglunid="{{$value.id}}" data-sum="{{$value.comment_sum}}">评论({{$value.comment_sum}})' +
		'</span>' +
//		'{{if $value.praise == false}}' +
//		'<span class="hit-zan"  data-pariseId="{{$value.id}}"><span class="xz1" style="margin-right:0px;">点赞(</span><span class="praise-sum">{{$value.praise_sum}}</span><span class="houzhui">)</span></span>' +
//		'</span>' +
//		'{{else}}' +
//		'<span class="hit-zan colorgren colorbg" data-pariseId="{{$value.id}}" ><span class="xz1" style="margin-right:0px;">已赞(</span><span class="praise-sum">{{$value.praise_sum}}</span><span class="houzhui">)</span></span>' +
//		'{{/if}}' +
//		'{{if $value.accused == false}}' +
//		'<span class="tousuBox xiuzhu" data-id="{{$value.id}}"><img src="../images/ansandqus/sakdetial_bad.png"/><span class="complaint" data-type="1">投诉</span></span>' +
//		'{{else}}' +
//		'<span data-id="{{$value.id}}" style="cursor: auto"><img style="cursor: auto" src="../images/ansandqus/sakdetial_bad.png"/><span style="cursor: auto" class="complaint " data-type="1">已投诉</span></span>' +
//		'{{/if}}' +
		'{{if $value.accepted == true}}' +
		'<span class="bestAnsBox box3"><span class="best-answer colorgren box3" style="margin-right:0px;" data-id="{{$value.id}}">已采纳为最佳回答</span></span></span>' +
		'{{/if}}' +
		'{{if $value.copyright==true}}' +
		'<span class="reserve"><span class="rights-reserved"><img src="../images/ansandqus/sakdetial_equal.png" alt=""/>作者保留权利</span>' +
		'<div class="rights-reserved-content"><img src="../images/personcenter/baoliuTip.png" class="jian">用户在熊猫中医上发表的全部原创内容（包括但不限于提问，问答和评论），著作权均归用户本人所有。用户可授权第三方以任何方式使用，不需要得到熊猫中医的同意。</div>' +
		'{{/if}}' +
		'</span>' +
//		'{{if $value.creator}}' +
		'<span class="complaint ansTopRight qingjian1" data-delectId="{{$value.id}}" data-create="{{$value.create_person}}">' +
		'<img src="../images/ansandqus/delete_normal.png"/>删除</span>' +
//		'{{/if}}' +
		'<div class="good-answer-comment" style="display:none;">' +
		'</div>' +
		'</div>' +
		'</div></div></div>' +
		'{{/each}}';

	var comments =
		'<div class="comment-top">' +
		'{{each items as $val j}}' +
		'<div data-commentId="{{$val.id}}" class="commentId clearfix">' +
		'<div  class="replyComments wrapComment">' +
		'{{if $val.target_nike_name!=null}}' +
		'<span class="user-nickname">{{$val.create_nick_name}}</span><span class="a1">回复</span>' +
		'<span class="user-nickname">{{$val.target_nike_name}}</span><span class="a1">:</span>' +
		'<span class="user-content">{{$val.content}}</span>' +
		'{{else}}' +
		'<span class="user-nickname">{{$val.create_nick_name}}</span><span class="a1">:</span>' +
		'<span class="user-content">{{#$val.content}}</span>' +
		'{{/if}}' +
		'<p class="user-content-line">' +
		'<span class="comment-time">{{timeTypeChange($val.create_time)}}</span>' +
//		'{{if $val.praise==true}}' +
//		'<span class="hit-zan colorgren colorbg"  data-pariseId="{{$val.id}}"><span class="xz1" style="margin-right:0px;">已赞(</span><span class="praise-sum">{{$val.praise_sum}}</span><span class="houzhui">)</span></span>' +
//		'{{else}}' +
//		'<span class="hit-zan"  data-pariseId="{{$val.id}}"><span class="xz1" style="margin-right:0px;">点赞(</span><span class="praise-sum">{{$val.praise_sum}}</span><span class="houzhui">)</span></span>' +
//		'{{/if}}' +
//		'<span class="reply-btn" data-create_nick_name="{{create_nick_name}}">回复' +
//		'<span class="sanjiaoInput"><img src="../images/ansandqus/sanjia01.png"/></span>' +
//		'</span>' +
//		'{{if $val.delete_button}}' +
		'<span class="reply-delete" data-deleteId="{{$val.id}}" data-create="{{$val.create_person}}">删除</span>' +
//		'{{/if}}' +
		'</p>' +
		'<div class="reply clearfix">' +
		'<input class="writeReply" type="text" placeholder="回复 {{$val.create_nick_name}}："/>' +
		'<span class="reply-ok" data-praise_login_names="{{$val.praise_login_names}}" data-answer_id="{{$val.answer_id}}" data-target_person="{{$val.create_person}}" data-target_nick_name="{{$val.create_nick_name}}">回复</span>' +
		'<span class="reply-cancle">取消</span>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'{{/each}}' +
		'</div>' +
		'<div class="comment-bottom clearfix">' +
//		'<input class="writeComment" type="text" placeholder="写下你的评论..."/>' +
//		'<div class="comment-btn">评论</div>' +
		'</div>'

	/*相似问题*/
	template.helper('glhref', function(num) {
		if(num != "") {
			return '' + bath + '/web/html/qusAndAnsDetailGuanLi.html?qid=' + num + "&ln=" + ln;
		} else {
			return 'javascript:;';
		}
	});
	var simliar = ' <p class="rel-course">相似问题</p>' +
		"<div class='relAnsNoData'>" +
		"<img src='../images/ansandqus/my_nodata.png'/>" +
		"<p>暂无数据</p>" +
		"</div>" +
		'{{each items as i}}' +
		'<div class="similarity-answer clearfix">' +
		'<div class="similarity-answer-top">' +
		'<a href="{{#glhref(i.id)}}" target="_blank" class="similarHover"><p class="rel-content" title="{{i.title}}">{{i.title}}</p></a>' +
		'</div>' +
		'<div class="similarity-answer-tips">' +
		'<span class="ansCount">回答({{i.answer_sum}})</span>' +
		'</div>' +
		'</div>' +
		'{{/each}}';

	/*相关课程*/
	var relativeCourse =
		'<div class="relative-course-top clearfix">' +
		'<span class="Rcour">相关课程</span>' +
		'<span class="by-the-arrow">' +
		'<span class="curCount currentLunbo">1</span>' +
		'<span class="curCount">/</span>' +
		'<span class="curCount allLunbo">1</span>' +
		'<span class="prev" id="prev"></span>' +
		'<span class="next" id="next"></span>' +
		'</span>' +
		'</div>' +
		"<div class='relativeAnsNoData'>" +
		"<img src='../images/ansandqus/my_nodata.png'/>" +
		"<p>暂无数据</p>" +
		"</div>" +
		'<div class="relative-course-bottom slide-box clearfix">' +
		'<div id="box" class="slideBox clearfix">' +
		'<ul class="course boxContent clearfix">' +
		'{{each items as $value i}}' +
		"<li>" +
		'{{#qshasImg($value.smallImgPath)}}' +
		'{{#online2($value.scoreName)}}' +
		'<div class="detail">' +
		'<p class="title" data-text="{{$value.courseName}}" title="{{$value.courseName}}">{{$value.courseName}}</p>' +
		'<p class="info clearfix">' +
		'<span>' +
		'{{if $value.free == true}}' +
       		 '<span class="pricefree">免费</span>' +
		'{{else}}' +
		'<i>￥</i><span class="price">{{$value.currentPrice}}</span><del><i class="price1">￥</i>{{$value.originalCost}}</del>' +
		'{{/if}}' +
		'</span>' +
		'<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learndCount}}</span></span>' +
		'</p>' +
		'</div>' +
		"</li>" +
		'{{/each}}' +
		'</ul>' +
		'</div></div>';
		
	var list = {
			pageNumber: 1,
			pageSize: 5
		}
		//我的回答
	/*tags = localStorage.getItem("tags");
	tags = $.makeArray(tags)
	menuId = localStorage.getItem("ment_id");*/
	questId = $(".questId").attr("data-questId");
	var myanswerStatus = ""; //回答状态
	var titlequestion = 1;
	//如果已被删除信息
	dele();
	function show(){
		$(".reDelete").css("display","block");
		$(".zhezhao").css("display","block");
	}
	function dele(){
		$(".reFooter span,.reClose img").off().on("click",function(){
			$(".reDelete").css("display","none");
			$(".zhezhao").css("display","none");
			window.location.reload();
		})
	}
	
	
	RequestService("/online/questionlist/findAdminQuestionById?questionId=" + qid, "GET", null, function(data) {
		window.localStorage.tags = data.resultObject.tag;
		window.localStorage.ment_id = data.resultObject.ment_id;
		relative_mentId = data.resultObject.ment_id;
		tags= $.makeArray(data.resultObject.tag);
		//相关课程
		RequestService('/online/questionlist/getCourseByMenuId', 'GET', {
				menuId: relative_mentId
			}, function(data) {
				$(".relative-course").html(template.compile(relativeCourse)({
					items: data.resultObject
				}))
				if(data.resultObject == "" || data.resultObject == null) {
					$(".by-the-arrow").css("display", "none");
					$(".by-the-arrow").css("display", "none");
					$(".slide-box").css("display", "none");
				} else {
					$(".relativeAnsNoData").css("display", "none");
					$(".boxContent li").eq(0).addClass("diyiye");
					$(".allLunbo").html(data.resultObject.length);
					var $index = 0;
					var $exdex = 0;
					$("#next").click(function() {
						if($index + 1 >= $(".allLunbo").text()) {
							return false;
						} else {
							$index++;
						}
						next();
						return $exdex = $index;
					})
					$("#prev").click(function() {
						if($index - 1 < 0) {
							return false;
						} else {
							$index--;
						}
						pre();
						return $exdex = $index;
					})
				}

				function next() {
					$(".currentLunbo").html($index + 1);
					$(".boxContent li").eq($index).stop(true, true).
					css("left", "100%").animate({
						"left": "0"
					});
					$(".boxContent li").eq($exdex).stop(true, true).
					css("left", "0").animate({
						"left": "-100%"
					});
				}

				function pre() {
					$(".currentLunbo").html($index + 1);
					$(".boxContent li").eq($index).stop(true, true).
					css("left", "-100%").animate({
						"left": "0"
					});
					$(".boxContent li").eq($exdex).stop(true, true).
					css("left", "0").animate({
						"left": "100%"
					});
				}
			})
			//相似问题
		RequestService('/online/questionlist/getSameProblem?tags=' + tags + '&menuId=' + relative_mentId +'&qid='+qid+'', "GET", null, function(m) {
				if(m.resultObject!=""){
					$(".relAnsNoData").css("display","none");
					$(".simliar").html(template.compile(simliar)({
						items: m.resultObject
					}));
				}else{
					$(".simliar").html(template.compile(simliar)({
						items: m.resultObject
					}));
					$(".relAnsNoData").css("display","block");
				}
			})
			//相关课程
		RequestService('/online/questionlist/getCourseByMenuId', 'GET', {
			menuId: relative_mentId
		}, function(n) {
			$(".relative-course").html(template.compile(relativeCourse)({
				items: n.resultObject
			}))
			if(n.resultObject == "" || n.resultObject == null) {
				$(".by-the-arrow").css("display", "none");
				$(".by-the-arrow").css("display", "none");
				$(".slide-box").css("display", "none");
				$(".relativeAnsNoData").css("display", "block");
			} else {
				$(".relativeAnsNoData").css("display", "none");
				$(".boxContent li").eq(0).addClass("diyiye");
				$(".allLunbo").html(n.resultObject.length);
				var $index = 0;
				var $exdex = 0;
				$("#next").click(function() {
					if($index + 1 >= $(".allLunbo").text()) {
						return false;
					} else {
						$index++;
					}
					next();
					return $exdex = $index;
				})
				$("#prev").click(function() {
					if($index - 1 < 0) {
						return false;
					} else {
						$index--;
					}
					pre();
					return $exdex = $index;
				})
			}

			function next() {
				$(".currentLunbo").html($index + 1);
				$(".boxContent li").eq($index).stop(true, true).
				css("left", "100%").animate({
					"left": "0"
				});
				$(".boxContent li").eq($exdex).stop(true, true).
				css("left", "0").animate({
					"left": "-100%"
				});
			}

			function pre() {
				$(".currentLunbo").html($index + 1);
				$(".boxContent li").eq($index).stop(true, true).
				css("left", "-100%").animate({
					"left": "0"
				});
				$(".boxContent li").eq($exdex).stop(true, true).
				css("left", "0").animate({
					"left": "100%"
				});
			}
		})
		$(".curr").html(data.resultObject.name);
		if(data.resultObject.status == 2) {
			myanswerStatus = 2;
		}
		if(data.resultObject.myself == true) {
			self = true;
			$(".qingjian1").each(function() {
				$(this).css("display", "inline");
			});
		} else {
			self = false;
		}
		$(".qingjian1").each(function() {
			var local = window.localStorage.username;
			if($(this).attr("data-create" == name)) {
				$(this).css("display", "inline");
			} else {
				$(this).css("display", "none");
			}
		})
		$(".quesDetail-left-first").html(template.compile(myAnswer)({
			items: data.resultObject
		}));
		if(data.resultObject.status == 2) {
			$(".richText").css("display", "none");
			$(".quesDetail-left-first").css({
				"borderBottom": "1px solid #eee",
				"paddingBottom": "18px"
			})
		} else {
			$(".richText").css("display", "block");
			$(".quesDetail-left-first").css({
				"borderBottom": "",
				"paddingBottom": "0px"
			})
		}
//		$(".studentNameTiaozhuang").click(function() {
//			var tag = $(this).html();
//			window.localStorage.xueke = tag;
//			window.localStorage.xuekeid = relative_mentId;
//			window.open(bath + "/web/html/ansAndQus.html");
//		});

//		$(".biaoqian").click(function() {
//				var tag = $(this).html();
//				window.localStorage.biaoqian = tag;
//				window.open(bath + '/web/html/ansAndQus.html');
//			})
			//保留作者权利
		$(".tipMessage").click(function() {
				$(".rights-reserved-content1").toggle()
			})
			//		$(".holdRright").click(function(){
			//			$(".holdRightImg").toggle();
			//		})
		$(".holdRightImg").click(function(event) {
			event.stopPropagation();
			$(".rights-reserved-content1").toggle()
		})
		$(document).click(function() {
				//			var e=e||event;
				//			if(e.target.className!="holdRightImg"){
				//			}else{
				$(".rights-reserved-content1").hide()
					//			}
			})
			//删除
		$(".qingjian").unbind().click(function() {
			var $this = $(this);
			$("#quxiaoshoucang").paymentModal("qingjian");
			$(".tipType").text("确定删除吗？");
			$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
				var id = $this.attr("data-id");
				RequestService('/online/questionlist/deleteQuestionById', "POST", {
					questionId: id,
                    ln:ln
				}, function(data) {
					$(".payment-modal-close").trigger("click");
//					location.href = "/web/html/ansAndQus.html"
					if(data.resultObject=="操作成功！"){
						window.location.reload();
					}else{
						show();dele();
					}
				});
			})
			$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
				$(".payment-modal-close").trigger("click");
			})

		});
		jingcai(self, myanswerStatus);
		lasteAnaswer();
		//投诉
//		$(".wenqiang").off().on("click", function() {
//			var $this = $(this);
//
//			window.localStorage.username = data.resultObject.loginName;
//			$("#tousu1 .comment-content").val("");
//			$("#tousu1").find(".radio-cover").removeClass("active");
//			$(".errorInfo").css("display", "none");
//			$("#tousu1").paymentModal("wenqiang");
//			var targetId = $this.attr("data-id");
//			var accuse_type = 5;
//			$("#tousu1 .modalBody label").unbind().on("click", function() {
//				$(".errorInfo").css("display", "none");
//				$(".radio-cover").removeClass("active");
//				$(this).find(".radio-cover").addClass("active");
//				var value = $(this).find("span").text();
//				if(value == "广告营销等垃圾信息") {
//					accuse_type = 0
//				} else if(value == "抄袭内容") {
//					accuse_type = 1
//				} else if(value == "辱骂等不文明语言的人身攻击") {
//					accuse_type = 2
//				} else if(value == "色情或反动的违法信息") {
//					accuse_type = 3
//				} else {
//					accuse_type = 4;
//				}
//			})
//			$("#tousu1 .modalFooter a").unbind().click(function() {
//				var data3;
//				var content = $("#tousu1 .comment-content").val();
//				if(accuse_type == 4) {
//					data3 = {
//						target_type: 0,
//						target_id: targetId,
//						accuse_type: 4,
//						content: content //其他信息
//					}
//				} else {
//					data3 = {
//						target_type: 0,
//						target_id: targetId,
//						accuse_type: accuse_type
//					};
//				}
//				if(accuse_type == 5) {
//					$("#tousu1 .errorInfo").css("display", "block");
//					return;
//				} else if(content == "" && accuse_type == 4) {
//					$("#tousu1 .errorInfo").css("display", "block");
//				} else {
//					$("#tousu1 .errorInfo").css("display", "none");
//					RequestService('/online/menutag/saveAccuse', 'POST', data3, function(data) {
//						if(data.success = true) {
//							$(".payment-modal .payment-modal-close").trigger("click");
//							location.reload();
//						}
//					})
//				}
//			})
//		});
		/*$(".holdRright").click(function(){
		 $(".tipMessage").toggle();
		 })*/
		//富文本编辑器
		//		var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code', 'link', 'image'];
		//		Simditor.locale = 'zh-CN'
		//		var editor = new Simditor({
		//			textarea: $('#editor'),
		//			toolbar: sm_toolbar,
		//			defaultImage: '',
		//			//http://attachment-center.ixincheng.com:58000/data/picture/online/2016/09/23/17/853ba435fc894781b16310ebc5142704.png
		//			pasteImage: true,
		//			toolbarHidden: false,
		//			toolbarFloat: false,
		//			placeholder: '',
		//			upload: {
		//				url: bath+"/online/util/upload4Simditor",
		//				params: null,
		//				fileKey: 'attachment',
		//				connectionCount: 3,
		//				leaveConfirm: '正在上传文件，如果离开上传会自动取消'
		//			},
		//			success: function(data) {
		//
		//			}
		//		});
		$(".writeReply").focus(function() {
			$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput").css("display", "block");
			$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia03.png");
		})
		$(".writeReply").blur(function() {
				$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia01.png");
			})
			//表单提交
		$(".quanli div").click(function() {
			if($(this).hasClass("select")) {
				$(this).removeClass("select");
				$("#formright").val("false");
			} else {
				$(this).addClass("select");
				$("#formright").val("true");
			}
		})
		$(".simditor-body").click(function() {
			$(".warning").css("display", "none");
		})
		$(".simditor-placeholder").css("top", "40px");
		$(".releaseAns").unbind().click(function() {
			$(".text-warn").css("display", "none");
			window.localStorage.username = data.resultObject.loginName;
			if($(".simditor-body").html() == "<p><br></p>") {
				$(".warning").css("display", "block");
				return false;
			} else {
				$("#formtext").val($(".simditor-body").text());
			}
			$(".simditor-body").on("input", function() {
				$(".warning").css("display", "none");
			})
			if($(".quanli div").hasClass("select")) {
				$("#formright").val("true");
				$(".tipMessage").css("display", "block");
			} else {
				$("#formright").val("false");
				$(".tipMessage").css("display", "none");
			}
			$("#xzform").attr("action", bath + "/ask/answer/addAnswer");
			$("#xzform").unbind().submit(function() {
				$("#xzform").ajaxSubmit(function(data) {
					if(data.success == true) {
						//					location.href=''+bath+"/web/html/ansAndQus.html";
						$(".simditor-body").html("<p><br></p>");
						$(".simditor-placeholder").css("display", "block");
						$(".holdRright").removeClass("select");
						lasteAnaswer();
						jingcai(self, myanswerStatus);
					}
					return false;
				})
				return false;
			})
			$("#xzform").submit();
			/*window.location.reload();*/
		})

		//收藏
		$(".shouBox").on("click", function() {
			var sc = $(".shoucang");

			if(sc.text() == "已收藏") {
				$("#quxiaoshoucang").paymentModal("shoucang");
				$(".tipType").text("确定要取消收藏吗？");
				$("#quxiaoshoucang .modalFooter .yesBtn").click(function() {
					window.localStorage.username = data.resultObject.loginName;
					RequestService('/ask/answer/collection?question_id=' + qid, "POST", null, function(data) {
						if(data.resultObject == false) {
							sc.text("收藏");
							$(".shoucang").removeClass("colorGr");
							$(".shouBox").css("border", "1px solid #e4e4e4");
							$(".shoucangImg").attr("src", "../images/ansandqus/star_normal.png")
							$(".payment-modal-close").trigger("click");
						}
					});
				});
				$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
					$(".payment-modal-close").trigger("click");
				})
			} else {
				window.localStorage.username = data.resultObject.loginName;
				RequestService('/ask/answer/collection?question_id=' + qid, "POST", null, function(data) {
					if(data.resultObject == true) {
						sc.text("已收藏");
						$(".shoucangImg").attr("src", "../images/ansandqus/star.png")
						$(".shoucang").addClass("colorGr");
						$(".shouBox").css("border", "1px solid #6acd6a");
					}
				});
			}
		});

		return self;
	})

	/*精彩回答*/
	function jingcai(self, myanswerStatus) {
		RequestService("/ask/answer/findNiceAnswers?question_id=" + qid, "GET", null, function(data) {

			//精彩回答
			var items = $.makeArray(data.resultObject)
			// $(".goodAnswercontent").html(template.compile(good_answer)({
			// 	items: items
			// }));
			$(".answer-comment").each(function(){
				if($(this).attr("data-sum")==0){
					$(this).attr("style","cursor:default");
				}
			})
			if(data.resultObject.length == 0) {
				$(".quesDetail-left-second").css("display", "none");
				$(".good-Answer").css("display", "none");
			} else {
				$(".quesDetail-left-second").css("display", "block");
				$(".good-Answer").css("display", "block");
				$(".nice_good_count").text(data.resultObject.length + "个");
			}
			if(myanswerStatus == 2) {
				$(".box2").each(function() {
					$(this).css("display", "none")
				});
				if(self == false) {
					$(".box2").each(function() {
						$(this).css("display", "none")
					})
				}
			} else {
				if(self == true) {
					$(".box2").each(function() {
						$(this).css("display", "inline")
					})
				} else {
					$(".box2").each(function() {
						$(this).css("display", "none")
					})
				}
			}
			$(".quesDetail-left-second .qingjian1").unbind().on("click", function() {
				var $this = $(this);

				window.localStorage.username = data.resultObject.loginName;
				$("#quxiaoshoucang").paymentModal("qingjian1");
				$(".tipType").text("确定要删除吗？");
				$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
					RequestService('/ask/answer/deleteAnswerById?answerId=' + $this.attr("data-delectId")+"&ln="+ln, "GET", "", function(data) {
						$(".payment-modal-close").trigger("click");
						if(data.resultObject=="操作成功"){
							$this.parents(".good-answer-content").remove();
							jingcai();
							lasteAnaswer();
						}else{
							show();dele();
						}
					});
				});
				$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
					$(".payment-modal-close").trigger("click");
				})
			});
			//投诉11
//			$(".tousuBox").off().on("click", function() {
//				var $this = $(this);
//				var targetId = $this.attr("data-id");
//				window.localStorage.username = data.resultObject.loginName;
//				$("#tousu .comment-content").val("");
//				$("#tousu").find(".radio-cover").removeClass("active");
//				$("#tousu").paymentModal("tousuBox");
//				$(".errorInfo").css("display", "none");
//				var accuse_type = 5;
//				$("#tousu .modalBody label").unbind().on("click", function() {
//					$(".errorInfo").css("display", "none");
//					$(".radio-cover").removeClass("active");
//					$(this).find(".radio-cover").addClass("active");
//					var value = $(this).find("span").text();
//					if(value == "广告营销等垃圾信息") {
//						accuse_type = 0
//					} else if(value == "抄袭内容") {
//						accuse_type = 1
//					} else if(value == "辱骂等不文明语言的人身攻击") {
//						accuse_type = 2
//					} else if(value == "色情或反动的违法信息") {
//						accuse_type = 3
//					} else {
//						accuse_type = 4;
//					}
//				})
//				$("#tousu .modalFooter a").click(function() {
//					var data3;
//					var content = $("#tousu .comment-content").val();
//					if(accuse_type == 4) {
//						data3 = {
//							target_type: 1,
//							target_id: targetId,
//							accuse_type: 4,
//							content: content //其他信息
//						}
//					} else {
//						data3 = {
//							target_type: 1,
//							target_id: targetId,
//							accuse_type: accuse_type
//						};
//					}
//					if(accuse_type == 5) {
//						$("#tousu .errorInfo").css("display", "block");
//					} else if(content == "" && accuse_type == 4) {
//						$("#tousu .errorInfo").css("display", "block");
//					} else {
//						$("#tousu .errorInfo").css("display", "none");
//						RequestService('/online/menutag/saveAccuse', 'POST', data3, function(data) {
//							if(data.success = true) {
//								$(".payment-modal .payment-modal-close").trigger("click");
//								$this.find("span").text("已投诉");
//								$this.find("img").css("cursor", "auto");
//								$this.find("span").css("cursor", "auto");
//								$this.unbind("click");
//							}
//						})
//					}
//				})
//			});
			//评论
			$(".answer-comment").off().on("click", function() {
				var $this = $(this);
				//如果为空直接不执行以下步骤
				if($(this).attr("data-sum")==0){
					return false;
				}
				window.localStorage.username = data.resultObject.loginName;
				var answer_id = $this.attr("data-pinglunid");
				var cos = $this;
				var answerList = {
					answer_id: answer_id,
					pageNumber: 1,
					pageSize: 5
				}
				$(".good-answer-comment").empty();

				var ansComment = $this.parent().find(".good-answer-comment");
				var datasum = $this.attr("data-sum");
				if(ansComment.css("display") == "none") {
					$(".answer-comment").each(function() {
						$(this).text("评论(" + $(this).attr("data-sum") + ")");
						$(this).parent().find(".good-answer-comment").css("display", "none");
						$(this).prev().children(".sanjiao02").css({"display":"none"});
					});
					var aleft = parseInt($this.offset().left) - parseInt($this.prev().offset().left);
					$this.prev().children(".sanjiao02").css({
						"display": "block",
						"left": aleft
					});
					$this.parent().find(".answer-comment").text("收起评论");
					goAnswer();

				} else {
					var aleft = parseInt($this.offset().left) - parseInt($this.prev().offset().left);
					$this.prev().children(".sanjiao02").css({
						"display": "none",
						"left": aleft
					});
					$this.parent().find(".answer-comment").text("评论(" + datasum + ")");
				}

				function goAnswer() {
					RequestService('/ask/comment/findComments', 'GET', answerList, function(data) {
						ansComment.html(template.compile(comments)({
							items: data.resultObject.items
						}));

						ansComment.find(".comment-bottom").before("<div class='pinglunPage'></div>");
						var maxPageNumber = data.resultObject.totalPageCount;
						var maxcengter = data.resultObject.totalCount;
						var thisPage = data.resultObject.currentPage;
						mypage(maxPageNumber, thisPage);

						$(".good-answer-comment .hit-zan").unbind().click(function() {
							var $this = $(this);
							var quest_id = $this.attr("data-pariseId");

							window.localStorage.username = data.resultObject.loginName;
							RequestService('/ask/comment/praiseComment', 'GET', {
								comment_id: quest_id
							}, function(data) {
								var nums = parseInt($this.parent().find(".praise-sum").text());
								if(data.resultObject.praise == true) {
									$this.find(".xz1").text("已赞(");
									$this.parent().find(".praise-sum").text(nums + 1);
									/*$this.parent().find(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
									$this.addClass("colorgren colorbg");
									$this.parent().find(".praise-sum").addClass("colorgren");
									$this.parent().find(".houzhui").addClass("colorgren");
									$(".hit-zan").each(function() {
										if($(this).attr("data-pariseId") == quest_id) {
											$(this).find(".xz1").text("已赞(");
											$(this).children(".praise-sum").text(data.resultObject.sum);
											/*$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
											$(this).addClass("colorgren colorbg");
											$(this).children(".praise-sum").addClass("colorgren");
											$(this).parent().children(".houzhui").addClass("colorgren");
										}
									})

								} else {
									$this.find(".xz1").text("点赞(");
									$this.parent().find(".praise-sum").text(nums - 1);
									/*$this.parent().find(".hitImg").attr("src", "../images/ansandqus/sakdetial_good.png")*/
									$this.removeClass("colorgren colorbg");
									$this.parent().find(".praise-sum").removeClass("colorgren");
									$this.parent().find(".houzhui").removeClass("colorgren");
									$(".hit-zan").each(function() {
										if($(this).attr("data-pariseId") == quest_id) {
											$(this).find(".xz1").text("点赞(");
											$(this).children(".praise-sum").text(data.resultObject.sum);
											/*$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good.png")*/
											$(this).removeClass("colorgren colorbg");
											$(this).children(".praise-sum").removeClass("colorgren");
											$(this).parent().children(".houzhui").removeClass("colorgren");
										}
									})
								}
							})
						});
						ansComment.css("display", "block");
						var comment_id = $(".commentId").attr("data-commentId");
						//回复
						$(".reply-btn").click(function() {
							var $this = $(this);
							var target_nick_name = $(this).parent().parent().parent().find(".user-nickname").html();
							$(this).parent().find(".writeReply").attr("placeholder", "回复 " + target_nick_name);

							window.localStorage.username = data.resultObject.loginName;
							if($this.parents().next(".reply").css("display") == "block") {
								$this.parents().next(".reply").css("display", "none");
								$this.find(".sanjiaoInput").css("display", "none");
							} else {
								$(".reply").css("display", "none");
								$this.find(".sanjiaoInput").css("display", "block");
								$this.parents().next(".reply").toggle();
							}

						})
						$(".reply-btn").hover(function() {
							$(this).css("background", "url(../images/ansandqus/replay_hover.png) no-repeat left center");
							$(this).css("color", "#2cb82c");
						}, function() {
							$(this).css("background", "url(../images/ansandqus/reply.png) no-repeat left center");
							$(this).css("color", "#999");
						})
						$(".writeReply").focus(function() {
							$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput").css("display", "block");
							$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia03.png");
						})
						$(".writeReply").blur(function() {
								$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia01.png");
							})
							//回复评论
						$(".reply-ok").unbind().click(function() {
							var target_person = $(this).attr("data-target_person");
							var target_nick_name = $(this).attr("data-target_nick_name");
							var praise_login_names = $(this).attr("data-praise_login_names")
							var $this = $(this);
							var writeReply = $(this).parent().find(".writeReply").val();
							var currentTime = new Date;
							var $grfa = $(this).parent().parent().parent().parent().parent().parent();
							var datasums = $grfa.children(".answer-comment").attr("data-sum");
							$(this).parent().find(".writeReply").attr("placeholder", "回复 " + target_nick_name + ":");

							window.localStorage.username = data.resultObject.loginName;
							var answer_id = $this.attr("data-answer_id");
							var data = {
								answer_id: answer_id,
								content: writeReply,
								target_person: target_person,
								target_nike_name: target_nick_name
							}
							if(writeReply == "") {
								$(".writeReply").focus();
							} else {
								RequestService('/ask/comment/addComment', "POST", data, function(data) {
									if(data.success = true) {
										$(".writeReply").val("");
										$this.parent().css("display", "none");
									}
									if(maxcengter % 5 == 0) {
										answerList.pageNumber = maxPageNumber + 1;
									} else {
										answerList.pageNumber = maxPageNumber;
									}

									goAnswer();
									datasums++;
									$grfa.children(".answer-comment").attr("data-sum", datasums);
									var pinglunid = $grfa.children(".answer-comment").attr("data-pinglunid");
									$(".answer-comment").each(function() {
										if($(this).attr("data-pinglunid") == pinglunid && $(this).text() != "收起评论") {
											$(this).html("评论(" + datasums + ")").attr("data-sum", datasums);
										}
									})
								})
							}

						});
						$(".reply-cancle").unbind().click(function() {
							$(".reply").css("display", "none");
							$(this).parents().next(".reply").toggle();
						});
						//发表评论
						$(".comment-btn").unbind().click(function() {
								var $grfa = $(this).parent().parent().parent().parent().parent().parent();
								var $fa = $(this).parent().parent().parent().parent().parent();
								$grfa.each(function() {
									var $index = $fa.index();
								})
								var $my = $(this).parents(".good-Answer-content-right").find(".goodId").attr("data-goodid");
								var writeComment = $(this).parent().find(".writeComment").val();
								var datasums = $this.parent().parent().parent().find(".answer-comment").attr("data-sum");

								window.localStorage.username = data.resultObject.loginName;
								var data = {
									answer_id: $my,
									content: writeComment
								}
								if(writeComment == "") {
									$(".writeComment").focus();
								} else {
									RequestService('/ask/comment/addComment', "POST", data, function(data) {
										if(data.success = true) {
											$(".writeComment").val("");
											if(maxcengter % 5 == 0) {
												answerList.pageNumber = maxPageNumber + 1;
											} else {
												answerList.pageNumber = maxPageNumber;
											}
											goAnswer();
											//														jingcai();
											//														lasteAnaswer();
											datasums++;
											var pinglunid = $this.parent().parent().parent().find(".answer-comment").attr("data-pinglunid");
											$this.parent().parent().parent().find(".answer-comment").attr("data-sum", datasums);
											$(".answer-comment").each(function() {
													if($(this).attr("data-pinglunid") == pinglunid && $(this).text() != "收起评论") {
														$(this).html("评论(" + datasums + ")").attr("data-sum", datasums);
													}
												})
												//														setTimeout(function(){
												//															$fa.children(".answer-comment").click();
												////															$grfa.find(".good-answer-comment").css("display","block");
												//														},300)
										}
									});
								}
							})
							//删除评论或回复
						$(".reply-delete").unbind().click(function() {
							var $this = $(this);
							var $grfa = $this.parent().parent().parent().parent().parent().parent();
							var datasums = $grfa.children(".answer-comment").attr("data-sum");
							window.localStorage.username = data.resultObject.loginName;
							$("#quxiaoshoucang").paymentModal("reply-delete");
							$(".tipType").text("确定删除吗？");
							$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
								RequestService('/ask/comment/deleteComment', "POST", {
									comment_id: $this.attr("data-deleteId"),
									ln:ln
								}, function(data) {
									$(".payment-modal-close").trigger("click");
									$this.parent().parent().parent().remove();
									datasums--;
									$grfa.children(".answer-comment").attr("data-sum", datasums);
									var pinglunid = $grfa.children(".answer-comment").attr("data-pinglunid");
									$(".answer-comment").each(function() {
										if($(this).attr("data-pinglunid") == pinglunid && $(this).text() != "收起评论") {
											$(this).html("评论(" + datasums + ")").attr("data-sum", datasums);
										}
									})
									if(datasums==0){
										$grfa.children(".anwTime").find(".sanjiao02").css("display","none");
										$grfa.children(".answer-comment").html("评论(" + datasums + ")").attr("data-sum", datasums);
										$grfa.children(".good-answer-comment").css("display","none");
									}
								});
							})
							$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
								$(".payment-modal-close").trigger("click");
							})
						})
					});
				}

				function mypage(maxPageNumber, thisPage) {
					if(maxPageNumber > 1) {
						$(".pinglunPage").append("<a class='shangyiye'>上一页</a><div class='zhongjianye'></div><a class='xiayiye'>下一页</a>");

						$(".xiayiye").on("click", function() {
							if(thisPage != maxPageNumber) {
								answerList.pageNumber = thisPage + 1;
								goAnswer();
							} else {
								answerList.pageNumber = maxPageNumber;
							}
						});

						$(".shangyiye").on("click", function() {
							if(thisPage != 1) {
								answerList.pageNumber = thisPage - 1;
								goAnswer();
							} else {
								answerList.pageNumber = 1;
							}
						});
						if(thisPage < 6 && maxPageNumber < 6) {
							for(i = 1; i < maxPageNumber + 1; i++) {
								$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
							}
						} else if(thisPage > maxPageNumber - 3) {
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
							for(i = maxPageNumber - 5; i < maxPageNumber + 1; i++) {
								$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
							}
						} else if(thisPage < 4 && maxPageNumber > 6) {
							for(i = 1; i < maxPageNumber + 1; i++) {
								if(i < 6) {
									$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
								}
							}
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
						} else {
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
							for(i = 1; i < maxPageNumber + 1; i++) {
								if(i > thisPage - 3 && i < thisPage + 3) {
									$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
								}
							}
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
						}
						$(".zhongjianye a").each(function() {
							if($(this).text() == thisPage) {
								$(this).addClass("current");
							} else {
								$(this).hover(function() {
									$(this).addClass("current");
								}, function() {
									$(this).removeClass("current");
								});
							}
						});
						$(".zhongjianye a").on("click", function() {
							if(thisPage != $(this).text()) {
								answerList.pageNumber = $(this).text();
								goAnswer();
							} else {
								answerList.pageNumber = $(this).text();
							}
						});
					}
				}

				$this.parent().find(".good-answer-comment").toggle();
			});
			//点击空白处关闭保留作者权力下拉框
			$(document).click(function() {
					$(".rights-reserved-content").css("display", "none");
				})
				//保留作者权利
			$(".rights-reserved").unbind().click(function(event) {
				event.stopPropagation();
				$(this).next(".rights-reserved-content").toggle();
			});

			//点赞
			$(".hit-zan").unbind().click(function() {
				var $this = $(this);
				var quest_id = $this.attr("data-pariseId");
				RequestService('/ask/answer/praiseAnswer', 'GET', {
					answer_id: quest_id
				}, function(data) {
					var nums = parseInt($this.parent().find(".praise-sum").text());
					if(data.resultObject.praise == true) {
						$this.find(".xz1").text("已赞(");
						$this.children(".praise-sum").text(data.resultObject.sum);
						/*$this.parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
						$this.addClass("colorgren colorbg");;
						$this.parent().children(".praise-sum").addClass("colorgren");
						$this.parent().children(".houzhui").addClass("colorgren");
						$(".hit-zan").each(function() {
								if($(this).attr("data-pariseId") == quest_id) {
									$(this).find(".xz1").text("已赞(");
									$(this).children(".praise-sum").text(data.resultObject.sum);
									/*$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
									$(this).addClass("colorgren colorbg");
									$(this).children(".praise-sum").addClass("colorgren");
									$(this).parent().children(".houzhui").addClass("colorgren");
								}
							})
							//								lasteAnaswer();
							//								jingcai();
					} else {
						$this.find(".xz1").text("点赞(");
						$this.parent().children(".praise-sum").text(data.resultObject.sum);
						/*$this.parent().children(".hitImg").attr("src", "../images/amsandqus/sakdetial_good.png")*/
						$this.removeClass("colorgren colorbg");
						$this.children(".praise-sum").removeClass("colorgren");
						$this.parent().children(".houzhui").removeClass("colorgren");
						$(".hit-zan").each(function() {
								if($(this).attr("data-pariseId") == quest_id) {
									$(this).find(".xz1").text("点赞(");
									$(this).children(".praise-sum").text(data.resultObject.sum);
									/*$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good.png")*/
									$(this).removeClass("colorgren colorbg");
									$(this).children(".praise-sum").removeClass("colorgren");
									$(this).parent().children(".houzhui").removeClass("colorgren");
								}
							})
							//								lasteAnaswer();
							//								jingcai();
					}
				})
			});

		})
	}

	//最近回答

	function lasteAnaswer() {
		list.pageSize = 10;
		RequestService("/ask/answer/findLatestAnswers?question_id=" + qid, "GET", list, function(data) {
			/*  if(data.resultObject.currentPage!=1){
			 list.pageSize=10;
			 }*/
			$(".latest-answer-middle").html(template.compile(good_answer)({
				items: data.resultObject.items
			}));
			$(".answer-comment").each(function(){
							if($(this).attr("data-sum")==0){
								$(this).attr("style","cursor:default");
							}
						})
			$(".quesDetail-left-third").css("display", "block");
			if(data.resultObject.items.length == 0) {
				$(".latest-answer-middle").html(template.compile(lastAnsNoData))
				$(".pages").css("display", "none");
				$(".last_good_count").text("");
			}
			if(data.resultObject.length == 0) {
				$(".last_good_count").text("");
			} else {
				$(".last_good_count").text(data.resultObject.totalCount + "个");
				$(".pages").css("display", "block");
			};
			if(myanswerStatus == 2) {
				$(".box2").each(function() {
					$(this).css("display", "none")
				});
				if(self == false) {
					$(".box2").each(function() {
						$(this).css("display", "none")
					})
				}
			} else {
				if(self == true) {
					$(".box2").each(function() {
						$(this).css("display", "inline")
					})
				} else {
					$(".box2").each(function() {
						$(this).css("display", "none")
					})
				}
			}

			$(".quesDetail-left-third .qingjian1").unbind().on("click", function() {
				var $this = $(this);

				$("#quxiaoshoucang").paymentModal("qingjian1");
				$(".tipType").text("确定删除吗？");
				$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
					RequestService('/ask/answer/deleteAnswerById?answerId=' + $this.attr("data-delectId")+"&ln="+ln, "GET", "", function(data) {
						$(".payment-modal-close").trigger("click");
						if(data.resultObject=="操作成功"){
							$this.parents(".good-answer-content").remove();
							jingcai();
							lasteAnaswer();
						}else{
							show();dele();
						}
					});
				})
				$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
					$(".payment-modal-close").trigger("click");
				})
			});
			//投诉11
//			$(".tousuBox").off().on("click", function() {
//				var $this = $(this);
//				var targetId = $this.attr("data-id");
//
//				window.localStorage.username = data.resultObject.loginName;
//				$("#tousu .comment-content").val("");
//				$("#tousu").find(".radio-cover").removeClass("active");
//				$("#tousu").paymentModal("tousuBox");
//				$(".errorInfo").css("display", "none");
//				var accuse_type = 5;
//				$("#tousu .modalBody label").unbind().on("click", function() {
//					$(".errorInfo").css("display", "none");
//					$(".radio-cover").removeClass("active");
//					$(this).find(".radio-cover").addClass("active");
//					var value = $(this).find("span").text();
//					if(value == "广告营销等垃圾信息") {
//						accuse_type = 0
//					} else if(value == "抄袭内容") {
//						accuse_type = 1
//					} else if(value == "辱骂等不文明语言的人身攻击") {
//						accuse_type = 2
//					} else if(value == "色情或反动的违法信息") {
//						accuse_type = 3
//					} else {
//						accuse_type = 4;
//					}
//				})
//				$("#tousu .modalFooter a").click(function() {
//					var data3;
//					var content = $("#tousu .comment-content").val();
//					if(accuse_type == 4) {
//						data3 = {
//							target_type: 1,
//							target_id: targetId,
//							accuse_type: 4,
//							content: content //其他信息
//						}
//					} else {
//						data3 = {
//							target_type: 1,
//							target_id: targetId,
//							accuse_type: accuse_type
//						};
//					}
//					if(accuse_type == 5) {
//						$("#tousu .errorInfo").css("display", "block");
//					} else if(content == "" && accuse_type == 4) {
//						$("#tousu .errorInfo").css("display", "block");
//					} else {
//						$("#tousu .errorInfo").css("display", "none");
//						RequestService('/online/menutag/saveAccuse', 'POST', data3, function(data) {
//							if(data.success = true) {
//								$(".payment-modal .payment-modal-close").trigger("click");
//								$this.css("cursor", "auto");
//								$this.find("span").text("已投诉");
//								$this.find("img").css("cursor", "auto");
//								$this.find("span").css("cursor", "auto");
//								$this.unbind("click");
//							}
//						})
//					}
//				})
//			});
			//评论
			$(".answer-comment").off().on("click", function() {
				var $this = $(this);
				//如果为空直接不执行以下步骤
				if($(this).attr("data-sum")==0){
					return false;
				}
				var answer_id = $this.attr("data-pinglunId");

				var answerList = {
					answer_id: answer_id,
					pageNumber: 1,
					pageSize: 5
				}
				$(".good-answer-comment").empty();
				var ansComment = $this.parent().find(".good-answer-comment");
				var datasum = $this.attr("data-sum");
				var aleft = parseInt($this.offset().left) - parseInt($this.prev().offset().left);
				$this.prev().children(".sanjiao02").css({
					"display": "block",
					"left": aleft
				});
				if(ansComment.css("display") == "none") {
					$(".answer-comment").each(function() {
						$(this).text("评论(" + $(this).attr("data-sum") + ")");
						$(this).parent().find(".good-answer-comment").css("display", "none");
						$(this).prev().children(".sanjiao02").css({"display":"none"});
					})
					$this.prev().children(".sanjiao02").css({
						"display": "block",
						"left": aleft
					});
					$this.parent().find(".answer-comment").text("收起评论");
					ansComment.css("display", "block");
					goAnswers();
				} else {
					var aleft = parseInt($this.offset().left) - parseInt($this.prev().offset().left);
					$this.prev().children(".sanjiao02").css({
						"display": "none",
						"left": aleft
					});
					$this.parent().find(".answer-comment").text("评论(" + datasum + ")");
				}

				function goAnswers() {
					RequestService('/ask/comment/findComments', 'GET', answerList, function(data) {
						ansComment.html(template.compile(comments)({
							items: data.resultObject.items
						}));
						ansComment.find(".comment-bottom").before("<div class='pinglunPage'></div>")
						var maxPageNumber = data.resultObject.totalPageCount;
						var maxcengter = data.resultObject.totalCount;
						var thisPage = data.resultObject.currentPage;
						mypage(maxPageNumber, thisPage);
						$(".good-answer-comment .hit-zan").unbind().click(function() {
							var $this = $(this);
							var quest_id = $this.attr("data-pariseId");

							RequestService('/ask/comment/praiseComment', 'GET', {
								comment_id: quest_id
							}, function(data) {
								var nums = parseInt($this.parent().find(".praise-sum").text());
								if(data.resultObject.praise == true) {
									$this.find(".xz1").text("已赞(");
									$this.parent().find(".praise-sum").text(nums + 1);
									/*$this.parent().find(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
									$this.addClass("colorgren colorbg");;
									$this.parent().find(".praise-sum").addClass("colorgren");
									$this.parent().find(".houzhui").addClass("colorgren");
									$(".hit-zan").each(function() {
											if($(this).attr("data-pariseId") == quest_id) {
												$(this).find(".xz1").text("已赞(");
												$(this).children(".praise-sum").text(data.resultObject.sum);
												/*$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
												$(this).addClass("colorgren colorbg");
												$(this).children(".praise-sum").addClass("colorgren");
												$(this).parent().children(".houzhui").addClass("colorgren");
											}
										})
										//													lasteAnaswer();
										//													jingcai();
								} else {
									$this.find(".xz1").text("点赞(");
									$this.parent().find(".praise-sum").text(nums - 1);
									/*$this.parent().find(".hitImg").attr("src", "../images/ansandqus/sakdetial_good.png")*/
									$this.removeClass("colorgren colorbg");
									$this.parent().find(".praise-sum").removeClass("colorgren");
									$this.parent().find(".houzhui").removeClass("colorgren");
									$(".hit-zan").each(function() {
											if($(this).attr("data-pariseId") == quest_id) {
												$(this).find(".xz1").text("点赞(");
												$(this).children(".praise-sum").text(data.resultObject.sum);
												/*$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good.png")*/
												$(this).removeClass("colorgren colorbg");
												$(this).children(".praise-sum").removeClass("colorgren");
												$(this).parent().children(".houzhui").removeClass("colorgren");
											}
										})
										//													lasteAnaswer();
										//													jingcai();
								}
							})
						});

						ansComment.css("display", "block");
						var comment_id = $(".commentId").attr("data-commentId");
						//回复
						$(".reply-btn").click(function(event) {
							var $this = $(this);
							var target_nick_name = $(this).parent().parent().find(".user-nickname").html();
							$(this).parent().find(".writeReply").attr("placeholder", "回复 " + target_nick_name);

							if($this.parent().next(".reply").css("display") == "block") {
								$this.parent().next(".reply").css("display", "none");
								$this.find(".sanjiaoInput").css("display", "none");
							} else {
								$(".reply").css("display", "none");
								$this.find(".sanjiaoInput").css("display", "block");
								$this.parent().next(".reply").toggle();
							}
						})
						$(".reply-btn").hover(function() {
							$(this).css("background", "url(../images/ansandqus/replay_hover.png) no-repeat left center");
							$(this).css("color", "#2cb82c");
						}, function() {
							$(this).css("background", "url(../images/ansandqus/reply.png) no-repeat left center");
							$(this).css("color", "#999");
						})
						$(".writeReply").focus(function() {
							$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput").css("display", "block");
							$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia03.png");
						})
						$(".writeReply").blur(function() {
								$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia01.png");
							})
							//回复评论
						$(".reply-ok").unbind().click(function() {
							var target_person = $(this).attr("data-target_person");
							var target_nick_name = $(this).attr("data-target_nick_name");
							var praise_login_names = $(this).attr("data-praise_login_names");
							var $this = $(this);
							var writeReply = $(this).parent().find(".writeReply").val();
							var currentTime = new Date;
							var $grfa = $(this).parent().parent().parent().parent().parent().parent();
							var datasums = $grfa.children(".answer-comment").attr("data-sum");
							$(this).parent().find(".writeReply").attr("placeholder", "回复 " + target_nick_name + ":");

							var answer_id = $this.attr("data-answer_id");
							var data = {
								answer_id: answer_id,
								content: writeReply,
								target_person: target_person,
								target_nike_name: target_nick_name
							}
							if(writeReply == "") {
								$(".writeReply").focus();
							} else {
								RequestService('/ask/comment/addComment', "POST", data, function(data) {
									if(data.success = true) {
										$(".writeReply").val("");
										$this.parent().css("display", "none");
									}
									if(maxcengter % 5 == 0) {
										answerList.pageNumber = maxPageNumber + 1;
									} else {
										answerList.pageNumber = maxPageNumber;
									}
									goAnswers();
									datasums++;
									$grfa.children(".answer-comment").attr("data-sum", datasums);
									var pinglunid = $grfa.children(".answer-comment").attr("data-pinglunid");
									$(".answer-comment").each(function() {
										if($(this).attr("data-pinglunid") == pinglunid && $(this).text() != "收起评论") {
											$(this).html("评论(" + datasums + ")").attr("data-sum", datasums);
										}
									})
								})
							}
						});
						$(".reply-cancle").unbind().click(function() {
							$(".reply").css("display", "none");
							$(this).parents().next(".reply").toggle();
						});
						//发表评论
						$(".comment-btn").unbind().click(function() {
								var $my = $(this).parents(".good-Answer-content-right").find(".goodId").attr("data-goodid");
								var writeComment = $(this).parent().find(".writeComment").val();
								var $grfa = $(this).parent().parent().parent().parent().parent().parent();
								var $fa = $(this).parent().parent().parent().parent().parent();
								$grfa.each(function() {
									var $index = $fa.index();
								})
								var datasums = $this.parent().parent().parent().find(".answer-comment").attr("data-sum");

								window.localStorage.username = data.resultObject.loginName;
								var data = {
									answer_id: $my,
									content: writeComment
								}
								if(writeComment == "") {
									$(".writeComment").focus();
								} else {
									RequestService('/ask/comment/addComment', "POST", data, function(data) {
										if(data.success = true) {
											$(".writeComment").val("");
											if(maxPageNumber % 5 == 0) {
												answerList.pageNumber = maxPageNumber + 1;
											} else {
												answerList.pageNumber = maxPageNumber;
											}

											goAnswers();
											//														jingcai();
											//														lasteAnaswer();
											datasums++;
											$this.parent().parent().parent().find(".answer-comment").attr("data-sum", datasums);
											var pinglunid = $this.parent().parent().parent().find(".answer-comment").attr("data-pinglunid");
											$(".answer-comment").each(function() {
													if($(this).attr("data-pinglunid") == pinglunid && $(this).text() != "收起评论") {
														$(this).html("评论(" + datasums + ")").attr("data-sum", datasums);
													}
												})
												//														setTimeout(function(){
												//															$grfa.children(".answer-comment").click();
												////															$grfa.find(".good-answer-comment").css("display","block");
												//														},300)
										}
									});
								}
							})
							//删除评论或回复
						$(".reply-delete").unbind().click(function() {

							var $this = $(this);
							var $grfa = $this.parent().parent().parent().parent().parent().parent();
							var datasums = $grfa.children(".answer-comment").attr("data-sum");
							$("#quxiaoshoucang").paymentModal("reply-delete");
							$(".tipType").text("确定删除吗？");
							$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
								$(".payment-modal-close").trigger("click");
								$this.parent().parent().parent().remove();
								RequestService('/ask/comment/deleteComment', "POST", {
									comment_id: $this.attr("data-deleteId"),
									ln:ln
								}, function(data) {
									$(".payment-modal-close").trigger("click");
									$this.parent().parent().parent().remove();
									datasums--;
									$grfa.children(".answer-comment").attr("data-sum", datasums);
									var pinglunid = $grfa.children(".answer-comment").attr("data-pinglunid");
									$(".answer-comment").each(function() {
										if($(this).attr("data-pinglunid") == pinglunid && $(this).text() != "收起评论") {
											$(this).html("评论(" + datasums + ")").attr("data-sum", datasums);
										}
									})
									if(datasums==0){
										$grfa.children(".anwTime").find(".sanjiao02").css("display","none");
										$grfa.children(".answer-comment").html("评论(" + datasums + ")").attr("data-sum", datasums);
										$grfa.children(".good-answer-comment").css("display","none");
									}
								});
							})
							$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
								$(".payment-modal-close").trigger("click");
							})
						})
					});
				}

				function mypage(maxPageNumber, thisPage) {
					if(maxPageNumber > 1) {
						$(".pinglunPage").append("<a class='shangyiye'>上一页</a><div class='zhongjianye'></div><a class='xiayiye'>下一页</a>");

						$(".xiayiye").on("click", function() {
							if(thisPage != maxPageNumber) {
								answerList.pageNumber = thisPage + 1;
								goAnswers();
							} else {
								answerList.pageNumber = maxPageNumber;
							}
						});

						$(".shangyiye").on("click", function() {
							if(thisPage != 1) {
								answerList.pageNumber = thisPage - 1;
								goAnswers();
							} else {
								answerList.pageNumber = 1;
							}
						});
						if(thisPage < 6 && maxPageNumber < 6) {
							for(i = 1; i < maxPageNumber + 1; i++) {
								$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
							}
						} else if(thisPage > maxPageNumber - 3) {
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
							for(i = maxPageNumber - 5; i < maxPageNumber + 1; i++) {
								$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
							}
						} else if(thisPage < 4 && maxPageNumber > 6) {
							for(i = 1; i < maxPageNumber + 1; i++) {
								if(i < 6) {
									$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
								}
							}
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
						} else {
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
							for(i = 1; i < maxPageNumber + 1; i++) {
								if(i > thisPage - 3 && i < thisPage + 3) {
									$(".pinglunPage .zhongjianye").append("<a>" + i + "</a>");
								}
							}
							$(".pinglunPage .zhongjianye").append("<span style='cursor: auto'>...</span>");
						}
						$(".zhongjianye a").each(function() {
							if($(this).text() == thisPage) {
								$(this).addClass("current");
							} else {
								$(this).hover(function() {
									$(this).addClass("current");
								}, function() {
									$(this).removeClass("current");
								});
							}
						});
						$(".zhongjianye a").on("click", function() {
							if(thisPage != $(this).text()) {
								answerList.pageNumber = $(this).text();
								goAnswers();
							} else {
								answerList.pageNumber = $(this).text();
							}
						});
					}
				}

				$this.parent().find(".good-answer-comment").toggle();
			});
			$(".hit-zan").unbind().click(function() {
				var $this = $(this);
				var quest_id = $this.attr("data-pariseId");

				RequestService('/ask/answer/praiseAnswer', 'GET', {
					answer_id: quest_id
				}, function(data) {
					var nums = parseInt($this.parent().find(".praise-sum").text());
					if(data.resultObject.praise == true) {
						$this.find(".xz1").text("已赞(");
						$this.parent().children(".praise-sum").text(data.resultObject.sum);
						/*$this.parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
						$this.addClass("colorgren colorbg");;
						$this.parent().children(".praise-sum").addClass("colorgren");
						$this.parent().children(".houzhui").addClass("colorgren");
						$(".hit-zan").each(function() {
								if($(this).attr("data-pariseId") == quest_id) {
									$(this).find(".xz1").text("已赞(");
									$(this).children(".praise-sum").text(data.resultObject.sum);
									/*	$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good_click.png")*/
									$(this).addClass("colorgren colorbg");
									$(this).children(".praise-sum").addClass("colorgren");
									$(this).parent().children(".houzhui").addClass("colorgren");
								}
							})
							//								lasteAnaswer();
							//								jingcai();
					} else {
						$this.find(".xz1").text("点赞(");
						$this.parent().children(".praise-sum").text(data.resultObject.sum);
						/*$this.parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good.png")*/
						$this.removeClass("colorgren colorbg");
						$this.parent().children(".praise-sum").removeClass("colorgren");
						$this.parent().children(".houzhui").removeClass("colorgren");
						$(".hit-zan").each(function() {
								if($(this).attr("data-pariseId") == quest_id) {
									$(this).find(".xz1").text("点赞(");
									$(this).children(".praise-sum").text(data.resultObject.sum);
									/*$(this).parent().children(".hitImg").attr("src", "../images/ansandqus/sakdetial_good.png")*/
									$(this).removeClass("colorgren colorbg");
									$(this).children(".praise-sum").removeClass("colorgren");
									$(this).parent().children(".houzhui").removeClass("colorgren");
								}
							})
							//								lasteAnaswer();
							//								jingcai();
					}
				})
			});

			
			//点击空白处关闭保留作者权力下拉框
			$(document).click(function() {
					$(".rights-reserved-content").css("display", "none");
				})
				//保留作者权力
			$(".rights-reserved").unbind().click(function(event) {
				event.stopPropagation();
				$(this).next(".rights-reserved-content").toggle();
			});
			//评论
			$(".writeReply").focus(function() {
				$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput").css("display", "block");
				$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia03.png");
			})
			$(".writeReply").blur(function() {
					$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia01.png");
				})
				//计算总页数
			if(data.resultObject.totalPageCount > 1) { //分页判断
				/* $(".not-data").remove();*/
				$(".quesDetail-left .pages").css("display", "block");
				$(".quesDetail-left .pages .searchPage .allPage").text(data.resultObject.totalPageCount);
				if(data.resultObject.currentPage == 1) {
					$("#Pagination").pagination(data.resultObject.totalPageCount, {
						callback: function(page) { //翻页功能
							list.pageNumber = (page + 1);
							list.pageSize = 10;
							if(list.pageNumber != 1) {
								$(".quesDetail-left-second").css("display", "none");
							} else {
								$(".quesDetail-left-second").css("display", "block");
							}
							lasteAnaswer();
						}
					});
				}
			} else {
				$(".quesDetail-left .pages").css("display", "none");
			}
		})
	}

}