/**
 * Created by admin on 2016/9/19.
 */
/**
 * Created by admin on 2016/9/19.
 */


window.onload = function() {
	
	
	

	
	function tousuTrue() {
		$(".tousuTrue").css("display", "block");
		setTimeout(function() {
			$(".tousuTrue").css("display", "none");
		}, 1000)
	}
	//我的问答
	$(".header_left .path a").each(function() {
		if($(this).text() == "问答精灵") {
			$(this).addClass("select");
		} else {
			$(this).removeClass("select");
		}
	});
	/*	var qid="bcec9822daaa431eaca37c7db59ea9e5";*/
	var self = false; //是否本人提问
	var pageCount = 0; //自定义总页数
	var relative_mentId; //相关课程的Id
	var tags; //相似问题的标签
	var lastAnsNoData = "<div class='lastAnsNoData'>" +
		"<img src='../images/ansandqus/my_nodata.png'/>" +
		"<p>暂无数据</p>" +
		"</div>"
	var myAnswer =
		'<div class="fwb">' +
		'<div class="gfhf-title">' +
		'	<p>官方回复<i class="iconfont icon-guanbi"></i></p>' +
		'</div>' +
		'<div class="gfhf-con">' +
		'<div class="question">问题：{{items.title}}' +
		'</div>' +
		'<div class="question1 clearfix">{{#items.content}}' +
		'</div>' +
		'<form id="gfhf" method="post">' +
		'<div class="answer">' +
		'<input type="hidden" name="question_id" value={{items.id}}>' +
		'<input type="hidden" name="methodType" value="0" id="methodType">' +
		'<input type="hidden" name="content" class="conall">'+
		'	<textarea class="simid" ></textarea>' +

		'</div>' +
		'<div class="bt-sub">' +
			'<p class="warning1">请输入回复内容</p>'+
		'	<span class="officialBtn-yes">确定</span>' +
		'	<span class="officialBtn-no">取消</span>' +
		'	</div>' +
		'</div>' +
		'</form>' +
		'</div>' +
		' <div class="quesImg">' +
		'<img src="{{#headImg(items.create_head_img)}}" alt=""/>' +
		'<p title="{{items.create_nick_name}}">{{items.create_nick_name}}</p>' +
		'</div>' +
		'<div class="quesTitle-left">' +
		'{{if items.collectStatu==true}}' +
		'<div class="shouBox colorGr" style="border:1px solid #6acd6a">' +
//		'<img class="shoucangImg" src="../images/ansandqus/star.png"/>' +
		'<i class="iconfont icon-shoucang"></i>'+
		'<span class="shoucang">已收藏</span>' +
		'</div>' +
		'{{else}}' +
		'<div class="shouBox">' +
//		'<img class="shoucangImg" src="../images/ansandqus/star_normal.png"/>' +
		'<i class="iconfont icon-shoucang"></i>'+
		'<span class="shoucang">收藏</span>' +
		'</div>' +
		'{{/if}}' +
		'<div class="questTitle-middle clearfix">' +
		'<div class="questTitle-middle-left">' +
		'<div data-questId="{{items.id}}" class="questId">' +
		'<span class="studentName" data-studentId="{{items.ment_id}}">来自学科：<span class="studentNameTiaozhuang">{{items.name}}</span></span>' +
		'<p class="answer-title">{{items.title}}</p>' +
		'<div class="answer-indrouce"><p style="margin:0">{{#items.content}}</p></div>' +
		'</div>' +
		'<div class="questTitle-tips clearfix">' +
		'<div class="course-tips-left">' +
		'{{each items.tag as i}}' +
		'<span class="biaoqian">{{i}}</span>' +
		'{{/each}}' +
		'</div>' +
		'<div class="courset-tips-right">' +
		'<span class="ansTopRight"><i class="iconfont icon-yulan"></i>浏览({{items.browse_sum}})</span><span class="fengexian"></span>' +
		'{{if items.accused == false && items.myself != true}}' +
		'<span class="complaint ansTopRight wenqiang" data-type="0" data-id="{{items.id}}">' +
		'<i class="iconfont icon-tousu"></i>投诉</span><span class="fengexian"></span>' +
		'{{else if items.myself != true}}' +
		'<span class="complaint ansTopRight" style="cursor: auto" data-type="0">' +
		'<i class="iconfont icon-tousu"></i>已投诉</span><span class="fengexian"></span>' +
		'{{/if}}' +
		'{{if items.myself == true}}' +
		'<span class="complaint ansTopRight qingjian" data-id="{{items.id}}">' +
		'<i class="iconfont icon-shanchu"></i>删除</span><span class="fengexian"></span>' +
		'{{/if}}' +
		'<span class="ansTopRight"><em></em>{{timeTypeChange(items.create_time)}}</span>' +
		'</div>' +
		'</div>' +
		'</div></div>' +
		'<div class="richText clearfix">' +
		'<form method="post" id="xzform">' +
		'<input type="hidden" name="question_id"  value="{{items.id}}" />' +
		'<textarea id="editor" placeholder="在这里发表你的见解吧" autofocus maxlength="7000" name="content" style="width:746px;height:243px"></textarea>' +
		'<input type="hidden" name="text" id="formtext" value="" />' +
		'<input type="hidden" name="copyright" id="formright" value="false" />' +
		'<div class="quanli">' +
		'<div class="warning text-warn">请输入回答</div><div class="holdRright "></div><span>保留作者权利</span><img class="holdRightImg" src="../images/ansandqus/holdRightImg.png" alt=""/>' +
		'<div class="rights-reserved-content1"><img src="../images/personcenter/baoliuTip.png" class="jian">用户在熊猫中医上发表的全部原创内容（包括但不限于提问，问答和评论），著作权均归用户本人所有。用户可授权第三方以任何方式使用，不需要得到熊猫中医的同意。</div>' +
		'</div>' +
		'<button class="releaseAns" data-id="{{items.id}}" type="button" style="border:0;">发布回答</button>' +
		'</form>' +
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
		'{{if $value.praise == false}}' +
		'<span class="hit-zan"  data-pariseId="{{$value.id}}"><span class="xz1" style="margin-right:0px;">点赞(</span><span class="praise-sum">{{$value.praise_sum}}</span><span class="houzhui">)</span></span>' +
		'</span>' +
		'{{else}}' +
		'<span class="hit-zan colorgren colorbg" data-pariseId="{{$value.id}}" ><span class="xz1" style="margin-right:0px;">已赞(</span><span class="praise-sum">{{$value.praise_sum}}</span><span class="houzhui">)</span></span>' +
		'{{/if}}' +
		'{{if $value.accused == false &&  $value.creator != true}}' +
		'<span class="tousuBox xiuzhu" data-id="{{$value.id}}"><img src="../images/ansandqus/sakdetial_bad.png"/><span class="complaint" data-type="1">投诉</span></span>' +
		'{{else if  $value.creator != true}}' +
		'<span data-id="{{$value.id}}" style="cursor: auto"><img style="cursor: auto" src="../images/ansandqus/sakdetial_bad.png"/><span style="cursor: auto" class="complaint " data-type="1">已投诉</span></span>' +
		'{{/if}}' +
		'{{if $value.accepted == true}}' +
		'<span class="bestAnsBox box3"><span class="best-answer colorgren box3" style="margin-right:0px;cursor:pointer;" data-id="{{$value.id}}">已采纳为最佳回答</span></span></span>' +
		'{{else}}' +
		'<span class="bestAnsBox box2"><span class="best-answer box2" style="margin-right:0px;cursor:pointer;" data-id="{{$value.id}}">采纳为最佳回答</span></span></span>' +
		'{{/if}}' +
		'{{if $value.copyright==true}}' +
		'<span class="reserve"><span class="rights-reserved"><img src="../images/ansandqus/sakdetial_equal.png" alt=""/>作者保留权利</span>' +
		'<div class="rights-reserved-content"><img src="../images/personcenter/baoliuTip.png" class="jian">用户在熊猫中医上发表的全部原创内容（包括但不限于提问，问答和评论），著作权均归用户本人所有。用户可授权第三方以任何方式使用，不需要得到熊猫中医的同意。</div>' +
		'{{/if}}' +
		'</span>' +
		'{{if $value.creator}}' +
		'{{if $value.accepted == true}}' +
		'<span class="complaint ansTopRight qingjian1" data-delectId="{{$value.id}}" data-isaccept="1" data-create="{{$value.create_person}}">' +
		'<img src="../images/ansandqus/sakdetial_delect.png"/>删除</span>' +
		'{{else}}' +
		'<span class="complaint ansTopRight qingjian1" data-delectId="{{$value.id}}" data-isaccept="0" data-create="{{$value.create_person}}">' +
		'<img src="../images/ansandqus/sakdetial_delect.png"/>删除</span>' +
		'{{/if}}' +
		'{{/if}}' +
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
		'<span class="user-content">{{$val.content}}</span>' +
		'{{/if}}' +
		'<p class="user-content-line">' +
		'<span class="comment-time">{{timeTypeChange($val.create_time)}}</span>' +
		'{{if $val.praise==true}}' +
		'<span class="hit-zan colorgren colorbg"  data-pariseId="{{$val.id}}"><span class="xz1" style="margin-right:0px;">已赞(</span><span class="praise-sum">{{$val.praise_sum}}</span><span class="houzhui">)</span></span>' +
		'{{else}}' +
		'<span class="hit-zan"  data-pariseId="{{$val.id}}"><span class="xz1" style="margin-right:0px;">点赞(</span><span class="praise-sum">{{$val.praise_sum}}</span><span class="houzhui">)</span></span>' +
		'{{/if}}' +
		'<span class="reply-btn" data-targetId="{{$val.user_id}}" data-create_nick_name="{{$val.create_nick_name}}">回复' +
		'<span class="sanjiaoInput"><img src="../images/ansandqus/sanjia01.png"/></span>' +
		'</span>' +
		'{{if $val.delete_button}}' +
		'<span class="reply-delete" data-deleteId="{{$val.id}}" data-create="{{$val.create_person}}">删除</span>' +
		'{{/if}}' +
		'</p>' +
		'<div class="reply clearfix">' +
		'<p class="replyTipNohidden">回复 {{$val.create_nick_name}}：</p>' +
		'<input class="writeReply" type="text"/>' +
		'<span class="reply-ok" data-praise_login_names="{{$val.praise_login_names}}"  data-targetId="{{$val.user_id}}" data-answer_id="{{$val.answer_id}}" data-target_person="{{$val.create_person}}" data-target_nick_name="{{$val.create_nick_name}}">回复</span>' +
		'<span class="reply-cancle">取消</span>' +
		'<div class="emptyHit1"><i class="iconfont icon-tanhao"></i>请输入内容</div>'+
		'</div>' +
		'</div>' +
		'</div>' +
		'{{/each}}' +
		'</div>' +
		'<div class="comment-bottom clearfix">' +
		'<input class="writeComment" type="text" placeholder="写下你的评论..."/>' +
		'<div class="emptyHit"><i class="iconfont icon-tanhao"></i>请输入内容</div>'+
		'<div class="comment-btn">评论</div>' +
		'</div>';
	/*未购买*/
	var notPurchase =
		'<div class="notPurchase">' +
		'<div class="notPurchase_body">' +
		'<img src="/web/images/icon_detial.png">' +
		'<div>' +
		'{{if item.answer_sum == 0}}' +
		'<p>此问题下面的回答，只有该问题学科下的付费课程用户才可以查看哦~</p>' +
		'{{else}}' +
		'<p>此问题下面有{{item.answer_sum}}条回答，只有该问题学科下的付费课程用户才可以查看哦~</p>' +
		'{{/if}}' +
		'<p>你可以<a href="http://crm2.qq.com/page/portalpage/wpa.php?uin=1078329360&aty=2&a=2&curl=&ty=1" target="_blank" style="color:#2cb82c">点击此处咨询</a>或者浏览此学科下的推荐课程</p>' +
		'</div>' +
		'</div>' +
		'</div>';

	/*相似问题*/
	template.helper('ahref', function(num) {
		if(num != "") {
			return '' + bath + '/web/qusDetail/' + num;
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
		'<a href="{{#ahref(i.id)}}" target="_blank" class="similarHover"><p class="rel-content" title="{{i.title}}">{{i.title}}</p></a>' +
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
		'{{#indexHref($value.description_show,$value.free,$value.id,$value.scoreName)}}' +
		'{{#qshasImg($value.smallImgPath)}}' +
		'{{#online($value.multimediaType)}}' +
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
		'</a>' +
		"</li>" +
		'{{/each}}' +
		'</ul>' +
		'</div></div>';
	//推荐课程图片
	var strcourses =
		'{{each item as $value index}}' +
		'{{if index == 1}}' +
		'<div class="course clearfix" style="margin:0px 16px;">' +
		'{{#indexHref($value.description_show,$value.free,$value.id,$value.courseType)}}' +
		'{{#hasImg($value.smallImgPath)}}' +
		'{{#online($value.multimediaType)}}' +
		'<div class="detail">' +
		'<p class="title" data-text="{{$value.gradeName}}" title="{{$value.gradeName}}">{{$value.gradeName}}</p>' +
		'<p class="timeAndTeac">' +
		'<span>{{#timeChange($value.courseLength)}}</span><i>|</i>' +
		'<span>讲师：<span class="teacher">{{$value.name}}</span></span>' +
		'</p>' +
		'<p class="info clearfix">' +
		'<span>' +
		'{{if $value.free == true}}' +
		'<span class="pricefree">免费</span>' +
		'{{else}}' +
		'<i>￥</i><span class="price">{{$value.currentPrice}}</span><del><i class="price1">￥</i>{{$value.originalCost}}</del>' +
		'{{/if}}' +
		'</span>' +
		'<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learnd_count}}</span></span>' +
		'</p>' +
		'</div>' +
		'</a>' +
		'</div>' +
		'{{else}}' +
		'<div class="course clearfix">' +
		'{{#indexHref($value.description_show,$value.free,$value.id,$value.courseType)}}' +
		'{{#hasImg($value.smallImgPath)}}' +
		'{{#online($value.multimediaType)}}' +
		'<div class="detail">' +
		'<p class="title" data-text="{{$value.gradeName}}" title="{{$value.gradeName}}">{{$value.gradeName}}</p>' +
		'<p class="timeAndTeac">' +
		'<span>{{#timeChange($value.courseLength)}}</span><i>|</i>' +
		'<span>讲师：<span class="teacher">{{$value.name}}</span></span>' +
		'</p>' +
		'<p class="info clearfix">' +
		'<span>' +
		'{{if $value.free == true}}' +
		'<span class="pricefree">免费</span>' +
		'{{else}}' +
		'<i>￥</i><span class="price">{{$value.currentPrice}}</span><del><i class="price1">￥</i>{{$value.originalCost}}</del>' +
		'{{/if}}' +
		'</span>' +
		'<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learnd_count}}</span></span>' +
		'</p>' +
		'</div>' +
		'</a>' +
		'</div>' +
		'{{/if}}' +
		'{{/each}}';
	template.helper('text', function(num) {
		return text(num);
	});
	var officialBody =
		"{{if data.askAnswer == null}}" +
		"<p class='nullOfficial'>暂无回复" +
		"{{if data.hasRight == true}}" +
		"/<span class='addOfficial' type='0'>添加</span>" +
		"{{/if}}" +
		"</p>" +
		"{{else}}" +
		"{{if data.hasRight == true}}" +
		"<span class='bianji' type='1' bianjiId={{data.askAnswer.id}}><img src='../images/video2/bianji.png'/>编辑</span>" +
		"<span class='pre bj1'>{{#data.askAnswer.content}}</span>" +
		"{{else}}" +
		"<span class='pre bj2'>{{#data.askAnswer.content}}</span>" +
		"{{/if}}" +
		"{{/if}}";
	var addOfficials =
		"<textarea type='text' style='width:100%;font-size:14px;color:#333;resize:none;min-height:60px;line-height:18px;'>{{data}}</textarea>";
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

	function findOffcial() {
		RequestService('/ask/answer/findOfficialAnswer', 'GET', {
			menu_id: relative_mentId,
			question_id: qid
		}, function(data) {
			if(data.success == false) {
				$(".official").css("display", "none");
				$(".redErrey").css("display", "none");
			} else {
				$(".redErrey").css("display", "none");
				$(".officialBtn").css("display", "none");
				$(".bianjiOfficialBtn").css("display", "none");
				$(".officialBody").html(template.compile(officialBody)({
					data: data.resultObject
				}));

				var reg = new RegExp("<br>", "g");
				//添加官方回复
				$(".addOfficial").click(function() {
					$(".zhezao").show();
					$("body").css("overflow-y","hidden");
					$(".fwb").show();
					$(".officialBtn-yes").attr("type",0);
					shenglve();
				});
				$(".bianji").off().on("click", function() {
					var txt=$(".pre").html();
					$(".zhezao").show();$(".fwb").show();
					$("body").css("overflow-y","hidden");
					$(".fwb .simditor-body").html(txt);
					$(".officialBtn-yes").attr({"type":1,"bianjiId":$(this).attr("bianjiId")});
					shenglve();
				});

				$(".fwb .officialBtn-no,.fwb .gfhf-title i").unbind("click").click(function() {
					//					findOffcial();
					$(".zhezao").hide();
					$("body").css("overflow-y","auto");
					$(".fwb").hide();
				});
			}

		});
	};
	function shenglve(){
		//省略号和富文本
			var $dot5 = $('.fwb .question1');
                $dot5.each(function () {
                	if($(this).attr("all")!="1"){
                		$(this).attr("all","1");
                	}else{
                		return false;
                	}
                    if ($(this).height() > 54) {
                        $(this).attr("data-txt", $(this).attr("data-text"));
                        $(this).height(54);
                        $(this).append('<span class="qq" > <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">显示全部</span><span class="closes">收起</span></a></span>');
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
	RequestService("/online/questionlist/findQuestionById?questionId=" + qid, "GET", null, function(data) {
			window.localStorage.tags = data.resultObject.tag;
			window.localStorage.ment_id = data.resultObject.ment_id;
			relative_mentId = data.resultObject.ment_id;
			tags = $.makeArray(data.resultObject.tag);

			//相关课程
			RequestService('/online/questionlist/getCourseByMenuId', 'GET', {
					menuId: relative_mentId
				}, function(data) {
					$(".relative-course").html(template.compile(relativeCourse)({
						items: data.resultObject
					}))
					if(data.resultObject == "" || data.resultObject == null) {
						$(".relativeAnsNoData").css("display", "block");
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
			RequestService('/online/questionlist/getSameProblem?tags=' + tags + '&menuId=' + relative_mentId + '&qid=' + qid + '', "GET", null, function(m) {
					if(m.resultObject != "") {
						$(".relAnsNoData").css("display", "none");
						$(".simliar").html(template.compile(simliar)({
							items: m.resultObject
						}));
					} else {
						$(".simliar").html(template.compile(simliar)({
							items: m.resultObject
						}));
						$(".relAnsNoData").css("display", "block");
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
			$(".bianjiOfficialBtn .bianjiOfficialBtn-yes").unbind("click").click(function() {
				if($.trim($(".officialBody textarea").val()) != "") {
					RequestService('/ask/answer/updateOfficialAnswer', 'POST', {
						id: qid,
						content: $.trim($(".officialBody textarea").val())
					}, function(data) {
						if(data.success) {
							findOffcial();
						}
					});
				} else {
					$(".bianjiOfficialBtn .redErrey").css("display", "block");
				}

			});
			//提交官方回复
			$(".fwb .officialBtn-yes").unbind("click").click(function() {
				$("#methodType").val($(this).attr("type"));
				if($(this).attr("bianjiId")){
					$(".fwb input[name=question_id]").val($(this).attr("bianjiId"));
				};
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						if($(".fwb .simditor-body").html() == "<p><br></p>") {
							$(".fwb  .warning1").css("display", "block");
							return false;
						} else {
							if($.trim($(".fwb  .simditor-body").text()) == "" && $(".fwb  .simditor-body").html().indexOf("img") == -1) {
								$(".fwb  .warning1").css("display", "block");
								return false;
							} else {
								$(".fwb  .simditor-body pre").each(function() {
									if($.trim($(this).text()) == "" || $(this).text() == "<br>") {
										$(this).attr({
											"style": "display:none!important"
										});
									}
								})
								$(".fwb .conall").val($(".fwb .simditor-body").html());
							}
						}
						$(".fwb  .simditor-body").on("input", function() {
							$(".fwb .warning1").css("display", "none");
						});
						$("#gfhf").attr("action", bath + "/ask/answer/addOfficialAnswer");
						$("#gfhf").unbind().submit(function() {
							$("#gfhf").ajaxSubmit(function(data) {
								if(data.success == true) {
									//					location.href=''+bath+"/web/html/ansAndQus.html";
									$(".fwb  .simditor-body").html("<p><br></p>");
									$(".fwb  .simditor-placeholder").css("display", "block");
									$(".fwb .warning1").css("display", "none");
									$(".fwb").hide();$(".zhezao").hide();
									$("body").css("overflow-y","auto");
									findOffcial();
								}
								return false;
							})
							return false;
						});
						$("#gfhf").submit();
						/*window.location.reload();*/
					}
				});
			});
			
			//富文本编辑器
			var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code', 'link', 'image'];
			Simditor.locale = 'zh-CN'
			var editor1 = new Simditor({
				textarea: $('.fwb .answer .simid'),
				toolbar: sm_toolbar,
				pasteImage: false,
				toolbarHidden: false,
				toolbarFloat: false,
				placeholder: '',
				upload: {
					url: bath + "/online/util/upload4Simditor",
					params: null,
					fileKey: 'attachment',
					connectionCount: 3,
					leaveConfirm: '正在上传文件，如果离开上传会自动取消'
				},
				success: function(data) {

				}
			});
			//省略号---end-------------
			if(data.resultObject.status == 2) {
				$(".richText").css("display", "none");
				$(".quesDetail-left-first").css({
					"borderBottom": "1px solid #eee",
					"paddingBottom": "20px"
				})
			} else {
				$(".richText").css("display", "block");
				$(".quesDetail-left-first").css({
					"borderBottom": "",
					"paddingBottom": "0px"
				})
			}
			$(".studentNameTiaozhuang").click(function() {
				var tag = $(this).html();
				window.localStorage.xueke = tag;
				window.localStorage.xuekeid = relative_mentId;
				window.open(bath + "/web/html/ansAndQus.html");
			});
			$(".biaoqian").click(function() {
					var tag = $(this).html();
					window.localStorage.biaoqian = tag;
					window.open(bath + '/web/html/ansAndQus.html');
				})
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
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					window.localStorage.username = data.resultObject.loginName;
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						$("#quxiaoshoucang").paymentModal("qingjian");
						$(".tipType").text("确定删除吗？");
						$("#quxiaoshoucang .modalFooter .yesBtn").click(function() {
							var id = $this.attr("data-id");
							RequestService('/online/questionlist/deleteQuestionById', "POST", {
								questionId: id
							}, function(data) {
								$(".payment-modal-close").trigger("click");
								location.href = "/web/html/ansAndQus.html"
							});
						})
						$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
							$(".payment-modal-close").trigger("click");
						})
					}
				});
			});
			//无权限页面
			if(data.resultObject.showAnswer == false) {
				$(".tuijian").html(template.compile(notPurchase)({
					item: data.resultObject
				}));
				RequestService("/course/getRecommendedCourse", "GET", {
					menuId: data.resultObject.ment_id,
					showAnswer: false
				}, function(data) {
					if(data.resultObject == "" || data.resultObject == null) {
						$("#log1").css("display", "none");
					} else {
						$("#contents").html(template.compile(strcourses)({
							item: data.resultObject
						}));
					}
				});
				$(".quesDetail-left-first").css({
					"borderBottom": "",
					"paddingBottom": "0px"
				})
				$(".tuijian").css("display", "block");
				$("#log1").css("display", "block");
				$(".richText").css("display", "none");
				$(".quesDetail-left-second").css("display", "none");
				$(".quesDetail-left-third").css("display", "none");
				$(".pages").css("display", "none");
			} else {
				$(".tuijian").css("display", "none");
				$("#log1").css("display", "none");
				$(".official").css("display", "block");
//				$(".quesDetail-left-second").css("display", "block");//先全部隐藏掉
				$(".quesDetail-left-second").css("display", "none");
				$(".quesDetail-left-third").css("display", "block");
				jingcai(self, myanswerStatus);
				lasteAnaswer();
				findOffcial();
			}
			//投诉
			$(".wenqiang").off().on("click", function() {
				var $this = $(this);
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						window.localStorage.username = data.resultObject.loginName;
						$("#tousu1 .comment-content").val("");
						$("#tousu1").find(".radio-cover").removeClass("active");
						$(".errorInfo").css("display", "none");
						$("#tousu1").paymentModal("wenqiang");
						var targetId = $this.attr("data-id");
						var accuse_type = 5;
						$("#tousu1 .modalBody label").unbind().on("click", function() {
							$(".errorInfo").css("display", "none");
							$(".radio-cover").removeClass("active");
							$(this).find(".radio-cover").addClass("active");
							var value = $(this).find("span").text();
							if(value == "广告营销等垃圾信息") {
								accuse_type = 0
							} else if(value == "抄袭内容") {
								accuse_type = 1
							} else if(value == "辱骂等不文明语言的人身攻击") {
								accuse_type = 2
							} else if(value == "色情或反动的违法信息") {
								accuse_type = 3
							} else {
								accuse_type = 4;
							}
						})
						$("#tousu1 .modalFooter a").unbind().click(function() {
							var data3;
							var content = $.trim($("#tousu1 .comment-content").val());
							if(accuse_type == 4) {
								data3 = {
									target_type: 0,
									target_id: targetId,
									accuse_type: 4,
									content: content //其他信息
								}
							} else {
								data3 = {
									target_type: 0,
									target_id: targetId,
									accuse_type: accuse_type
								};
							}
							if(accuse_type == 5) {
								$("#tousu1 .errorInfo").css("display", "block");
								return;
							} else if(content == "" && accuse_type == 4) {
								$("#tousu1 .errorInfo").css("display", "block");
							} else {
								$("#tousu1 .errorInfo").css("display", "none");
								RequestService('/online/menutag/saveAccuse', 'POST', data3, function(data) {
									if(data.success = true) {
										$(".payment-modal .payment-modal-close").trigger("click");
										$(".wenqiang").html("<img src='../images/ansandqus/sakdetial_bad.png'/>已投诉");
										tousuTrue();
										$(".wenqiang").find("img").css("cursor", "auto");
										$(".wenqiang").css("cursor", "auto");
										$(".wenqiang").unbind("click");
									}
								})
							}
						})
					}
				});
			});
			/*$(".holdRright").click(function(){
			 $(".tipMessage").toggle();
			 })*/
			//富文本编辑器
			var sm_toolbar = ['italic', 'bold', 'underline', 'strikethrough', '|', 'blockquote', 'code', 'link', 'image'];
			Simditor.locale = 'zh-CN'
			var editor = new Simditor({
				textarea: $('#editor'),
				toolbar: sm_toolbar,	
				pasteImage: true,
				toolbarHidden: false,
				toolbarFloat: false,
				placeholder: '',
				upload: {
					url: bath + "/online/util/upload4Simditor",
					params: null,
					fileKey: 'attachment',
					connectionCount: 3,
					leaveConfirm: '正在上传文件，如果离开上传会自动取消'
				},
				success: function(data) {

				}
			});
			$(".simditor-body").on("input", function() {
				//			if($(this).text()!=0){
				$(".simditor-placeholder").css("display", "none")
					//			}else{
					//				$(".simditor-placeholder").css("display","block")
					//			}
			})
			$(".writeReply").focus(function() {
				var replyNoHiddenLength = $(this).prev().innerWidth() + 10;
				$(this).css("paddingLeft", replyNoHiddenLength + "px");
				$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput").css("display", "block");
				$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia03.png");
			})
			$(".writeReply").blur(function() {
					$(this).parent("").siblings(".user-content-line").find(".sanjiaoInput img").attr("src", "../images/ansandqus/sanjia01.png");
				})
				//表单提交
			$(".quanli .holdRright,.quanli span").click(function() {
				if($(".quanli .holdRright").hasClass("select")) {
					$(".quanli .holdRright").removeClass("select");
					$("#formright").val("false");
				} else {
					$(".quanli .holdRright").addClass("select");
					$("#formright").val("true");
				}
			})
			$(".simditor-body").click(function() {
				$(".warning").css("display", "none");
			})
			$(".simditor-placeholder").css("top", "40px");
			//提交表单
			$(".releaseAns").unbind().click(function() {
					$(".text-warn").css("display", "none");
					RequestService("/online/user/isAlive", "GET", null, function(data) {
						if(data.success == false) {
							$('#login').modal('show');
						} else {
							window.localStorage.username = data.resultObject.loginName;
							if($(".richText .simditor-body").html() == "<p><br></p>") {
								$(".richText .warning").css("display", "block");
								return false;
							} else {
								if($.trim($(".richText .simditor-body").text()) == "" && $(".richText .simditor-body").html().indexOf("img") == -1) {
									$(".richText .warning").css("display", "block");
									return false;
								} else {
									$(".richText .simditor-body pre").each(function() {
										if($.trim($(this).text()) == "" || $(this).text() == "<br>") {
											$(this).attr({
												"style": "display:none!important"
											});
										}
									})
									$("#formtext").val($("#xzform .simditor-body").text());
								}
							}
							$(".richText .simditor-body").on("input", function() {
								$(".richText .warning").css("display", "none");
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
										$(".richText .simditor-body").html("<p><br></p>");
										$(".richText .simditor-placeholder").css("display", "block");
										$(".holdRright").removeClass("select");
										lasteAnaswer();
										jingcai(self, myanswerStatus);
									}
									return false;
								})
								return false;
							});
							$("#xzform").submit();
							/*window.location.reload();*/
						}
					});
				})
				//收藏
			$(".shouBox").on("click", function() {
				var sc = $(".shoucang");
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						if(sc.text() == "已收藏") {
							$("#quxiaoshoucang").paymentModal("shoucang");
							$(".tipType").text("确定要取消收藏吗？");
							$("#quxiaoshoucang .modalFooter .yesBtn").click(function() {
								window.localStorage.username = data.resultObject.loginName;
								RequestService('/ask/answer/collection?question_id=' + qid, "POST", null, function(data) {
									if(data.resultObject == false) {
										sc.text("收藏");
										$(".shouBox").removeClass("colorGr");
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
									$(".shouBox").addClass("colorGr");
									$(".shouBox").css("border", "1px solid #6acd6a");
								}
							});
						}
					}
				});

			});
			return self;
		})
		/*精彩回答*/
	function jingcai(self, myanswerStatus) {
		RequestService("/ask/answer/findNiceAnswers?question_id=" + qid, "GET", null, function(data) {
			//精彩回答
			var items = $.makeArray(data.resultObject)
			$(".goodAnswercontent").html(template.compile(good_answer)({
				items: items
			}));
			if(self == false) {
				$(".best-answer").css("cursor", "auto");
			}
			if(data.resultObject.length == 0) {
				$(".quesDetail-left-second").css("display", "none");
				$(".good-Answer").css("display", "none");
			} else {
//				$(".quesDetail-left-second").css("display", "block");//先全部隐藏掉
				$(".quesDetail-left-second").css("display", "none");
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
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						window.localStorage.username = data.resultObject.loginName;
						$("#quxiaoshoucang").paymentModal("qingjian1");
						$(".tipType").text("确定要删除吗？");
						$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
							RequestService('/ask/answer/deleteAnswerById?answerId=' + $this.attr("data-delectId"), "GET", "", function(data) {
								if($this.attr("data-isaccept") == 1) {
									$(".richText").css("display", "block");
									$(".box2").css("display", "block");
									myanswerStatus = 1;
								}
								$(".payment-modal-close").trigger("click");
								$this.parents(".good-answer-content").remove();
								jingcai();
								lasteAnaswer();
							});
						});
						$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
							$(".payment-modal-close").trigger("click");
						})
					}
				});

			});
			//投诉11
			$(".tousuBox").off().on("click", function() {
				var $this = $(this);
				var targetId = $this.attr("data-id");
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						window.localStorage.username = data.resultObject.loginName;
						$("#tousu .comment-content").val("");
						$("#tousu").find(".radio-cover").removeClass("active");
						$("#tousu").paymentModal("tousuBox");
						$(".errorInfo").css("display", "none");
						var accuse_type = 5;
						$("#tousu .modalBody label").unbind().on("click", function() {
							$(".errorInfo").css("display", "none");
							$(".radio-cover").removeClass("active");
							$(this).find(".radio-cover").addClass("active");
							var value = $(this).find("span").text();
							if(value == "广告营销等垃圾信息") {
								accuse_type = 0
							} else if(value == "抄袭内容") {
								accuse_type = 1
							} else if(value == "辱骂等不文明语言的人身攻击") {
								accuse_type = 2
							} else if(value == "色情或反动的违法信息") {
								accuse_type = 3
							} else {
								accuse_type = 4;
							}
						})
						$("#tousu .modalFooter a").click(function() {
							var data3;
							var content = $.trim($("#tousu .comment-content").val());
							if(accuse_type == 4) {
								data3 = {
									target_type: 1,
									target_id: targetId,
									accuse_type: 4,
									content: content //其他信息
								}
							} else {
								data3 = {
									target_type: 1,
									target_id: targetId,
									accuse_type: accuse_type
								};
							}
							if(accuse_type == 5) {
								$("#tousu .errorInfo").css("display", "block");
							} else if(content == "" && accuse_type == 4) {
								$("#tousu .errorInfo").css("display", "block");
							} else {
								$("#tousu .errorInfo").css("display", "none");
								RequestService('/online/menutag/saveAccuse', 'POST', data3, function(data) {
									if(data.success = true) {
										$(".payment-modal .payment-modal-close").trigger("click");
										tousuTrue();
										$(".tousuBox").each(function() {
											if($(this).attr("data-id") == $this.attr("data-id")) {
												$(this).find("span").text("已投诉");
												$(this).find("img").css("cursor", "auto");
												$(this).find("span").css("cursor", "auto");
												$(this).unbind("click");
											}
										});
									}
								})
							}
						})
					}
				})
			});
			//评论
			$(".answer-comment").off().on("click", function() {
				var $this = $(this);
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
						return false;
					} else {
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
								$(this).prev().children(".sanjiao02").css({
									"display": "none"
								});
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
									RequestService("/online/user/isAlive", "GET", null, function(data) {

										if(data.success == false) {
											$('#login').modal('show');
										} else {
											window.localStorage.username = data.resultObject.loginName;
											RequestService('/ask/comment/praiseComment', 'GET', {
												comment_id: quest_id
											}, function(data) {
												var nums = parseInt($this.parent().find(".praise-sum").text());
												if(data.resultObject.praise == true) {
													$this.find(".xz1").text("已赞(");
													$this.parent().find(".praise-sum").text(data.resultObject.sum);
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
													$this.parent().find(".praise-sum").text(data.resultObject.sum);
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
										}
									});
								});
								ansComment.css("display", "block");
								var comment_id = $(".commentId").attr("data-commentId");
								//回复
								$(".reply-btn").click(function() {
									var $this = $(this);
									$(".emptyHit1").css("display","none");
									var target_nick_name = $(this).parent().parent().parent().find(".user-nickname").html();
									$(this).parent().find(".writeReply").attr("placeholder", "回复 " + target_nick_name);
									RequestService("/online/user/isAlive", "GET", null, function(data) {

										if(data.success == false) {
											$('#login').modal('show');
										} else {
											window.localStorage.username = data.resultObject.loginName;
											if($this.parents().next(".reply").css("display") == "block") {
												$this.parents().next(".reply").css("display", "none");
												$this.find(".sanjiaoInput").css("display", "none");
											} else {
												$(".reply").css("display", "none");
												$this.find(".sanjiaoInput").css("display", "block");
												$this.parents().next(".reply").toggle();
											}
										}
									});

								})
								$(".reply-btn").hover(function() {
									$(this).css("background", "url(../images/ansandqus/replay_hover.png) no-repeat left center");
									$(this).css("color", "#2cb82c");
								}, function() {
									$(this).css("background", "url(../images/ansandqus/reply.png) no-repeat left center");
									$(this).css("color", "#999");
								})
								$(".writeReply").focus(function() {
									var replyNoHiddenLength = $(this).prev().innerWidth() + 10;
									$(".emptyHit1").css("display","none");
									$(this).css("paddingLeft", replyNoHiddenLength + "px");
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
									var target_user_id = $(this).attr("data-targetId");
									var $this = $(this);
									var writeReply = $(this).parent().find(".writeReply").val();
									var currentTime = new Date;
									var $grfa = $(this).parent().parent().parent().parent().parent().parent();
									var datasums = $grfa.children(".answer-comment").attr("data-sum");
									//									$(this).parent().find(".writeReply").attr("placeholder","回复 "+target_nick_name+":");
									RequestService("/online/user/isAlive", "GET", null, function(data) {

										if(data.success == false) {
											$('#login').modal('show');
										} else {
											window.localStorage.username = data.resultObject.loginName;
											var answer_id = $this.attr("data-answer_id");
											var data = {
												answer_id: answer_id,
												content: writeReply,
												target_person: target_person,
												target_nike_name: target_nick_name,
												target_user_id: target_user_id
											};
											if(writeReply == "") {
												$this.parent().find("emptyHit1").css("display","block");
												$(".writeReply").focus(function() {
													$this.parent().find("emptyHit1").css("display","none");
													var replyNoHiddenLength = $(this).prev().innerWidth() + 10;
													$(this).css("paddingLeft", replyNoHiddenLength + "px");
												});
											} else {
												$this.parent().find("emptyHit1").css("display","none");
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
										}
									})
								});
								$(".reply-cancle").unbind().click(function() {
									$(".reply").css("display", "none");
									$(this).parent().parent().find(".sanjiaoInput").css("display", "none");
									$(this).parents().next(".reply").toggle();
									$(".writeReply").val("");
								});
								//发表评论
								$(".comment-btn").unbind().click(function() {
										var $val = $(this).parent().find(".writeComment").val();
										var $grfa = $(this).parent().parent().parent().parent().parent().parent();
										var $fa = $(this).parent().parent().parent().parent().parent();
										$grfa.each(function() {
											var $index = $fa.index();
										})
										var $my = $(this).parents(".good-Answer-content-right").find(".goodId").attr("data-goodid");
										var writeComment = $(this).parent().find(".writeComment").val();
										var datasums = $this.parent().parent().parent().find(".answer-comment").attr("data-sum");
										RequestService("/online/user/isAlive", "GET", null, function(data) {
											if(data.success == false) {
												$('#login').modal('show');
											} else {
												window.localStorage.username = data.resultObject.loginName;
												var data = {
													answer_id: $my,
													content: writeComment
												}
												if(writeComment == "") {
													$(".writeComment").focus();
													$(".emptyHit").css("display","block");
												} else {
													$(".emptyHit").css("display","none");
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
											}
										})

									})
									//删除评论或回复
								$(".reply-delete").unbind().click(function() {
									var $this = $(this);
									var $grfa = $this.parent().parent().parent().parent().parent().parent();
									var datasums = $grfa.children(".answer-comment").attr("data-sum");
									RequestService("/online/user/isAlive", "GET", null, function(data) {
										if(data.success == false) {
											$('#login').modal('show');
										} else {
											window.localStorage.username = data.resultObject.loginName;
											$("#quxiaoshoucang").paymentModal("shoucang");
											$(".tipType").text("确定删除吗？");
											$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
												RequestService('/ask/comment/deleteComment', "POST", {
													comment_id: $this.attr("data-deleteId")
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
												});
											})
											$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
												$(".payment-modal-close").trigger("click");
											})
										}
									});

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
					}
				});
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
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
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
					}
				});
			});

		})
	}

	//最近回答

	function lasteAnaswer(i) {
		/*	//loading加载提示
		$(".latest-answer-middle").css("height","580px").showloading({

		})*/
		list.pageSize = 10;
		RequestService("/ask/answer/findLatestAnswers?question_id=" + qid, "GET", list, function(data) {
			/*  if(data.resultObject.currentPage!=1){
			 list.pageSize=10;
			 }*/
			$(".latest-answer-middle").html(template.compile(good_answer)({
				items: data.resultObject.items
			}));
			if(self == false) {
				$(".best-answer").css("cursor", "auto");
			}
			$(".latest-answer-middle").css("height", "auto").closeloading();
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
			if(i == 1) {
				$(".box2").each(function() {
					$(this).css({
						"display": "inline!important",
						"background": "red"
					});
				});
			}
			$(".quesDetail-left-third .qingjian1").unbind().on("click", function() {
				var $this = $(this);
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						$("#quxiaoshoucang").paymentModal("qingjian1");
						$(".tipType").text("确定删除吗？");
						$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
							RequestService('/ask/answer/deleteAnswerById?answerId=' + $this.attr("data-delectId"), "GET", "", function(data) {
								if($this.attr("data-isaccept") == 1) {
									$(".richText").css("display", "block");
									$(".box2").css("display", "block");
									myanswerStatus = 1;
								}
								$(".payment-modal-close").trigger("click");
								$this.parents(".good-answer-content").remove();
								lasteAnaswer();
								jingcai();
							});
						})
						$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
							$(".payment-modal-close").trigger("click");
						})
					}
				});

			});
			//投诉11
			$(".tousuBox").off().on("click", function() {
				var $this = $(this);
				var targetId = $this.attr("data-id");
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
						window.localStorage.username = data.resultObject.loginName;
						$("#tousu .comment-content").val("");
						$("#tousu").find(".radio-cover").removeClass("active");
						$("#tousu").paymentModal("tousuBox");
						$(".errorInfo").css("display", "none");
						var accuse_type = 5;
						$("#tousu .modalBody label").unbind().on("click", function() {
							$(".errorInfo").css("display", "none");
							$(".radio-cover").removeClass("active");
							$(this).find(".radio-cover").addClass("active");
							var value = $(this).find("span").text();
							if(value == "广告营销等垃圾信息") {
								accuse_type = 0
							} else if(value == "抄袭内容") {
								accuse_type = 1
							} else if(value == "辱骂等不文明语言的人身攻击") {
								accuse_type = 2
							} else if(value == "色情或反动的违法信息") {
								accuse_type = 3
							} else {
								accuse_type = 4;
							}
						})
						$("#tousu .modalFooter a").click(function() {
							var data3;
							var content = $.trim($("#tousu .comment-content").val());
							if(accuse_type == 4) {
								data3 = {
									target_type: 1,
									target_id: targetId,
									accuse_type: 4,
									content: content //其他信息
								}
							} else {
								data3 = {
									target_type: 1,
									target_id: targetId,
									accuse_type: accuse_type
								};
							}
							if(accuse_type == 5) {
								$("#tousu .errorInfo").css("display", "block");
							} else if(content == "" && accuse_type == 4) {
								$("#tousu .errorInfo").css("display", "block");
							} else {
								$("#tousu .errorInfo").css("display", "none");
								RequestService('/online/menutag/saveAccuse', 'POST', data3, function(data) {
									if(data.success = true) {
										$(".payment-modal .payment-modal-close").trigger("click");
										tousuTrue();
										$(".tousuBox").each(function() {
											if($(this).attr("data-id") == $this.attr("data-id")) {
												$(this).find("span").text("已投诉");
												$(this).find("img").css("cursor", "auto");
												$(this).find("span").css("cursor", "auto");
												$(this).unbind("click");
											}
										});
									}
								})
							}
						})
					}
				})
			});
			//评论
			$(".answer-comment").off().on("click", function() {
				var $this = $(this);
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
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
								$(this).prev().children(".sanjiao02").css({
									"display": "none"
								});
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
									RequestService("/online/user/isAlive", "GET", null, function(data) {
										if(data.success == false) {
											$('#login').modal('show');
										} else {
											RequestService('/ask/comment/praiseComment', 'GET', {
												comment_id: quest_id
											}, function(data) {
												var nums = parseInt($this.parent().find(".praise-sum").text());
												if(data.resultObject.praise == true) {
													$this.find(".xz1").text("已赞(");
													$this.parent().find(".praise-sum").text(data.resultObject.sum);
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
													$this.parent().find(".praise-sum").text(data.resultObject.sum);
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
										}
									});

								});

								ansComment.css("display", "block");
								var comment_id = $(".commentId").attr("data-commentId");
								//回复
								$(".reply-btn").click(function(event) {
									var $this = $(this);
									$(".emptyHit1").css("display","none");
									var target_nick_name = $(this).parent().parent().find(".user-nickname").html();
									$(this).parent().find(".writeReply").attr("placeholder", "回复 " + target_nick_name);
									RequestService("/online/user/isAlive", "GET", null, function(data) {
										if(data.success == false) {
											$('#login').modal('show');
										} else {
											if($this.parent().next(".reply").css("display") == "block") {
												$this.parent().next(".reply").css("display", "none");
												$this.find(".sanjiaoInput").css("display", "none");
											} else {
												$(".reply").css("display", "none");
												$this.find(".sanjiaoInput").css("display", "block");
												$this.parent().next(".reply").toggle();
											}
										}
									});
								})
								$(".reply-btn").hover(function() {
									$(this).css("background", "url(../images/ansandqus/replay_hover.png) no-repeat left center");
									$(this).css("color", "#2cb82c");
								}, function() {
									$(this).css("background", "url(../images/ansandqus/reply.png) no-repeat left center");
									$(this).css("color", "#999");
								})
								$(".writeReply").focus(function() {
									var replyNoHiddenLength = $(this).prev().innerWidth() + 10;
									$(".emptyHit1").css("display","none");
									$(this).css("paddingLeft", replyNoHiddenLength + "px");
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
									var target_user_id = $(this).attr("data-targetId");
									var $this = $(this);
									var writeReply = $(this).parent().find(".writeReply").val();
									var currentTime = new Date;
									var $grfa = $(this).parent().parent().parent().parent().parent().parent();
									var datasums = $grfa.children(".answer-comment").attr("data-sum");
									//									$(this).parent().find(".writeReply").attr("placeholder","回复 "+target_nick_name+":");
									RequestService("/online/user/isAlive", "GET", null, function(data) {
										window.localStorage.username = data.resultObject.loginName;
										if(data.success == false) {
											$('#login').modal('show');
										} else {
											var answer_id = $this.attr("data-answer_id");
											var data = {
												answer_id: answer_id,
												content: writeReply,
												target_person: target_person,
												target_nike_name: target_nick_name,
												target_user_id: target_user_id
											}
											if(writeReply == "") {
												$this.parent().find(".emptyHit1").css("display","block");
												$(".writeReply").focus(function() {
													$this.parent().find(".emptyHit1").css("display","none");
													var replyNoHiddenLength = $(this).prev().innerWidth() + 10;
													$(this).css("paddingLeft", replyNoHiddenLength + "px");
												});
											} else {
												$this.parent().find(".emptyHit1").css("display","none");
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
										}
									})
								});
								$(".reply-cancle").unbind().click(function() {
									$(".reply").css("display", "none");
									$(this).parent().parent().find(".sanjiaoInput").css("display", "none");
									$(this).parents().next(".reply").toggle();
									$(".writeReply").val("");
								});
								//发表评论
								$(".comment-btn").unbind().click(function() {
										var $val = $(this).parent().find(".writeComment").val();
										var $my = $(this).parents(".good-Answer-content-right").find(".goodId").attr("data-goodid");
										var writeComment = $(this).parent().find(".writeComment").val();
										var $grfa = $(this).parent().parent().parent().parent().parent().parent();
										var $fa = $(this).parent().parent().parent().parent().parent();
										$grfa.each(function() {
											var $index = $fa.index();
										});
										var datasums = $this.parent().parent().parent().find(".answer-comment").attr("data-sum");
										RequestService("/online/user/isAlive", "GET", null, function(data) {
											if(data.success == false) {
												$('#login').modal('show');
											} else {
												window.localStorage.username = data.resultObject.loginName;
												var data = {
													answer_id: $my,
													content: writeComment
												};
												if(writeComment == "") {
													$(".writeComment").focus(function(){
														$(".emptyHit").css("display","none");
													});
													$(".emptyHit").css("display","block");
												} else {
													$(".emptyHit").css("display","none");
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
											}
										})

									})
									//删除评论或回复
								$(".reply-delete").unbind().click(function() {
									var $this = $(this);
									var $grfa = $this.parent().parent().parent().parent().parent().parent();
									var datasums = $grfa.children(".answer-comment").attr("data-sum");
									RequestService("/online/user/isAlive", "GET", null, function(data) {
										if(data.success == false) {
											$('#login').modal('show');
										} else {
											$("#quxiaoshoucang").paymentModal("shoucang");
											$(".tipType").text("确定删除吗？");
											$("#quxiaoshoucang .modalFooter .yesBtn").unbind().click(function() {
												$(".payment-modal-close").trigger("click");
												$this.parent().parent().parent().remove();
												RequestService('/ask/comment/deleteComment', "POST", {
													comment_id: $this.attr("data-deleteId")
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
												});
											})
											$("#quxiaoshoucang .modalFooter .notBtn").click(function() {
												$(".payment-modal-close").trigger("click");
											})
										}
									});

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
					}
				});
			});
			$(".hit-zan").unbind().click(function() {
				var $this = $(this);
				var quest_id = $this.attr("data-pariseId");
				RequestService("/online/user/isAlive", "GET", null, function(data) {
					if(data.success == false) {
						$('#login').modal('show');
					} else {
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
					}
				});
			});

			//采纳为最佳回答
			if(self == true) {
				$(".best-answer").unbind().click(function() {
					var value = $(this).parent().find(".best-answer");
					var $this = $(this);
					RequestService("/online/user/isAlive", "GET", null, function(data) {
						if(data.success == false) {
							$('#login').modal('show');
						} else {
							RequestService('/ask/answer/acceptAnswer', 'GET', {
								answer_id: $this.attr("data-id")
							}, function(data) {
								if(data.resultObject == true) {
									$(".richText").css("display", "none");
									$(".quesDetail-left-first").css({
										"borderBottom": "1px solid #eee",
										"paddingBottom": "20px"
									})
									$this.text('已采纳为最佳回答').addClass("colorgren box3");
									$this.css("cursor", "pointer");
									$this.parent().find().addClass("box3").removeClass("box2");
									$this.parent().addClass("box3").removeClass("box2");
									$this.parent().find("img").attr("src", "../images/ansandqus/sakdetial_right_click.png");
									$this.parent().parent().children(".qingjian1").attr("data-isaccept", "1");
									$(".best-answer").each(function() {
										if($this.attr("data-id") == $(this).attr("data-id")) {
											$(this).text('已采纳为最佳回答');
											$(this).addClass("colorgren box3");
											$(this).parent().find().addClass("box3");
											$(this).parent().removeClass("box2");
											$(this).removeClass("box2");
											$(this).parent().addClass("box3").children().css({
												"display": "inline",
												"cursor": "pointer"
											});
											$(this).parent().find("img").attr("src", "../images/ansandqus/sakdetial_right_click.png");
											$(this).parent().css("display", "inline");
											$(this).parent().parent().children(".qingjian1").attr("data-isaccept", "1");

										} else {
											$(this).parent().find().addClass("box2").removeClass("box3");
											$(this).parent().addClass("box2").removeClass("box3");
											$(this).removeClass("box3");
											$(this).parent().parent().children(".qingjian1").attr("data-isaccept", "0");
											$(".box2").css("display", "none");
										}
									});
									myanswerStatus = 2;
								} else {
									$(".richText").css("display", "block");
									$(".quesDetail-left-first").css({
										"borderBottom": "",
										"paddingBottom": "0px"
									})
									$this.text('采纳为最佳回答').removeClass("colorgren box3");;
									$this.parent().find().addClass("box2").removeClass("box3");
									$this.parent().addClass("box2").removeClass("box3");
									$this.parent().find("img").attr("src", "../images/ansandqus/sakdetial_right.png");
									$this.parent().parent().children(".qingjian1").attr("data-isaccept", "0");
									$(".best-answer").each(function() {
										if($this.attr("data-id") == $(this).attr("data-id")) {
											$(this).text('采纳为最佳回答').removeClass("colorgren box3");
											$(this).parent().find().addClass("box2").removeClass("box3");
											$(this).parent().addClass("box2").removeClass("box3");
											$(this).parent().find("img").attr("src", "../images/ansandqus/sakdetial_right.png");
											$(this).parent().parent().children(".qingjian1").attr("data-isaccept", "0");
										}
									});
									$(".box2").css("display", "inline");
								}
							})
						}
					});
				})
			}
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
				var replyNoHiddenLength = $(this).prev().innerWidth() + 10;
				$(this).css("paddingLeft", replyNoHiddenLength + "px");
				$("emptyHit").css("display","none");
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
								$(".quesDetail-left-second").css("display", "none");
//								$(".quesDetail-left-second").css("display", "block");//先全部隐藏掉
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


//顶部医师字体变色
	$('.forum').css('color','#000');
	$('.path .ansAndQus').addClass('select');
}