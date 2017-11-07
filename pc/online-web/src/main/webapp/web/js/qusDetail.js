window.onload = function() {
	//解析url地址
	var ourl = document.location.search;
	var apams = ourl.substring(1).split("&");
	var arr = [];
	for(i = 0; i < apams.length; i++) {
		var apam = apams[i].split("=");
		arr[i] = apam[1];
		var qid=arr[0];
	};
	var allTl = {
		as: "{{each items as $value i}}" +
			"<div class='answer-item'>" +
			"<div class='pic inline' style='background: url" + "({{#pictures($value.answerHeadImg)}}) no-repeat;background-size: 100% 100%'></div>" +
			"<div class='content inline'>" +
			"<div class='title'>{{$value.userName}}" +
			"{{#hasTeacher($value.type)}}" +
			"<div class='txt'>{{$value.context}}</div>" +
			"{{each $value.imgList}}" +
			"{{if $value.path}}" +
			"<div class='contro-img'>" +
			"<img src='{{$value.path}}'>" +
			"</div>" +
			"{{else}}" +
			"{{/if}}" +
			"{{/each}}" +
			"<div class='control-bottom'> <span class='hour'>{{$value.dayStr}}</span>" +
			"{{#getAcceptButton(items[i])}}" +
			"{{#getAcceptiLaud(items[i])}}" +
			"{{#getAcceptiOppose(items[i])}}" +
			"{{if iForumAnswer}}" +
			"{{else}}" +
			"<span class='glyphicon my-glyphicon-ts' aria-hidden='true'></span><span class='gap complain' data-id='{{$value.id}}'> 投诉</span>" +
			"{{/if}}" +
			"</div>" +
			"</div><hr/>" +
			"</div>" +
			"</div>" +
			"{{/each}}",
		imgList: "{{each imgList}}" +
			"<img src='{{$value.path}}'>" +
			"{{/each}}",
		assist: "{{each forumAssistImgList}}" +
			"<img src='{{$value.path}}'>" +
			"{{/each}}"
	};
	var currentPage = 1;
	var clickType; //事件点击类型, 1:问题修改 2:问题补充
	var assistId; //问题补充编号
	RequestService("/online/forum/getForumSimpleDetail", "GET", {
			forumId: qid
		},
		function(data) {
			//			$(".view-container").html(Handlebars.compile(template)(data.resultObject));
			//			$(".content-img").html(Handlebars.compile(allTl.imgList)(data.resultObject));
			//			$(".assist-img").html(Handlebars.compile(allTl.assist)(data.resultObject));
			$("#con-box").append(template("con-box1", {
				id: data.resultObject.id
			}));
			$(".nav-bread").append(template("qus_bread", {
				title: data.resultObject.title
			}));
			$(".container").append(template("qus_container", {
				data:data.resultObject
			}));
			$(".content-img").html(template.compile(allTl.imgList)({
				imgList: data.resultObject.imgList
			}));
			$(".assist-img").html(template.compile(allTl.assist)({
				forumAssistImgList: data.resultObject.forumAssistImgList
			}));
			layer.closeAll('loading');
			RequestService("/online/forum/addForumPageView", "POST", {
				forumId: qid
			}, function(t) {

			});
			assistId = data.resultObject.forumAssistId;
			//问题投诉
			$(".tousu,.my-glyphicon-tousu").click(function() {
				$("#answerForm .cymyanswerlogo").text("投诉");
				$("#answerForm").attr("data-modalzt", "投诉");
				$("#answerForm .myanswer-lo1").text("投诉内容");
				$("#answerForm").modal("show");
				$("#answerForm .interlocution-bottom-1").empty();
				$("#answerForm .answerFormtxt").val("");
				$(".interlocution-bottom").css("display", "none");
				$(".other-page").css("padding-right", "17px");
				$('#answerForm').on('hidden.bs.modal', function() {
					$(".other-page").css("padding-right", "0px");
				});
			});

			$('#bzh_modal_tform').unbind("modal.t.commit");
			//修改或补充提交
			$("#bzh_modal_tform").bind("modal.t.commit", function(evt, data) {
				evt.preventDefault();
				var arr2 = [];
				if(clickType == 1) { //问题修改
					if($(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1").length != 0||$(".tiForm-bottom .txt").length!=0) {
						if($(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1").length == 0) {
							RequestService("/online/forum/updateForum", "POST", {
								forumId: qid,
								title: data.result.title,
								context: data.result.txt
							}, function(data) {
								//								createPage(qid);
								createAnswer(true);
								$("#bzh_modal_tform .tiFormlogo").text("问题修改");
								$("#bzh_modal_tform .tiForm-lo").css("display", "block");
								$("#bzh_modal_tform .tiForm-lo1").css("top", "60");
//								$("#bzh_modal_tform .title").css("display", "block");
								location.reload();

							});
						} else {
							for(var k = 0; k < $(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1").length; k++) {
								arr2.push($(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1:eq(" + k + ")").attr("data-vid"))
							}
							RequestService("/online/forum/updateForum", "POST", {
								forumId: qid,
								title: data.result.title,
								context: data.result.txt,
								imgIds: arr2.join()
							}, function(data) {
								//								createPage(qid);
								createAnswer(true);
								$("#bzh_modal_tform .tiFormlogo").text("问题修改");
								$("#bzh_modal_tform .tiForm-lo").css("display", "block");
								$("#bzh_modal_tform .tiForm-lo1").css("top", "60");
//								$("#bzh_modal_tform .title").css("display", "block");
								location.reload();
							});
						}
					} else {

						layer.msg("保存失败，问题描述不能为空", {
							icon: 2
						});
					}
				} else {
					if($(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1").length != 0||$(".tiForm-bottom .txt").length!=0) {

						if($(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1").length == 0) {
							RequestService("/online/forumAssist/save", "POST", {
								forumId: qid,
								context: data.result.txt,
								id: assistId
							}, function(data) {
								//								createPage(qid);
								createAnswer(true);
								$("#bzh_modal_tform .tiFormlogo").text("问题补充");
								$("#bzh_modal_tform .tiForm-lo").css("display", "block");
								$("#bzh_modal_tform .tiForm-lo1").css("top", "60");
//								$("#bzh_modal_tform .title").css("display", "block");
								location.reload();
							});
						} else {

							arr2 = [];
							for(k = 0; k < $(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1").length; k++) {
								arr2.push($(".tiForm-bottom-f-1 .tiForm-bottom-f-dv-1:eq(" + k + ")").attr("data-vid"))
							}
							RequestService("/online/forumAssist/save", "POST", {
								forumId: qid,
								context: data.result.txt,
								id: assistId,
								imgIds: arr2.join()
							}, function(data) {
//															createPage(qid);
								createAnswer(true);
								$("#bzh_modal_tform .tiFormlogo").text("问题补充");
								$("#bzh_modal_tform .tiForm-lo").css("display", "block");
								$("#bzh_modal_tform .tiForm-lo1").css("top", "60");
//								$("#bzh_modal_tform .title").css("display", "block");
								location.reload();
							});
						}
					} else {

						layer.msg("保存失败，问题描述不能为空", {
							icon: 2
						});
					}
				}
			});

			//弹出问题修改
			$(".link-bar .btn-qus-xg").click(function(evt) {
				clickType = 1;
				RequestService("/online/user/isAlive", "POST", null, function(data) {
					if(!data.success) {
						localStorage.username = null;
						localStorage.password = null;
						if($(".login").css("display") == "block") {
							$(".login").css("display", "none");
							$(".logout").css("display", "block");
							$('#login').modal('show');
						} else {
							$('#login').modal('show');
						}
						return;
					} else {
						$("#bzh_modal_tform").modal("show");
						$("#bzh_modal_tform .tiFormlogo").text("问题修改");
						$("#bzh_modal_tform .tiForm-lo").css("display", "none");
						$("#bzh_modal_tform .tiForm-lo1").text("问题描述");
						$("#bzh_modal_tform .title").css("display", "none");
						$(".tiForm-bottom .tiForm-bottom-f-1").empty();
						RequestService("/online/forum/getForumSimpleDetail/", "GET", {
								forumId: qid
							},
							function(data) {
								if(data.resultObject.imgList.length == 0) {
									initTFormModal(data.resultObject.title, data.resultObject.context);
								} else {
									for(var i = 0; i < data.resultObject.imgList.length; i++) {
										if(i == 4) {
											$(".file2_t").css("display", "block");
										}
										$(".tiForm-bottom-f-1").append("<div class='tiForm-bottom-f-dv-1' data-vid=" + data.resultObject.imgList[i].path + ">" +
											"<img src='" + data.resultObject.imgList[i].path + "'>" +
											"<div class='tiForm-bottom-dv-1-1-y' style='display: none'>" + data.resultObject.imgList.length + "/5</div>" +
											"<div class='tiForm-bottom-f-dv-1-1'>" +
											"<div class='tiForm-bottom-f-dv-1-1-x' onclick='myquiz_bottom_dv(event)'></div></div></img>");
									}
									$(".tiForm-bottom-f-dv-1 .tiForm-bottom-dv-1-1-y:last").css("display", "block");
									initTFormModal(data.resultObject.title, data.resultObject.context);

								}
							});

					}
				})
			});
			//问题补充
			$(".link-bar .btn-qus-bc").click(function(evt) {
				clickType = 2;
				RequestService("/online/user/isAlive", "POST", null, function(data) {
					if(!data.success) {
						localStorage.username = null;
						localStorage.password = null;
						if($(".login").css("display") == "block") {
							$(".login").css("display", "none");
							$(".logout").css("display", "block");
							$('#login').modal('show');
						} else {
							$('#login').modal('show');
						}
						return;
					} else {
						$("#bzh_modal_tform").modal("show");
						$("#bzh_modal_tform .tiFormlogo").text("问题补充");
						$("#bzh_modal_tform .tiForm-lo").css("display", "none");
						$("#bzh_modal_tform .tiForm-lo1").css("top", "0");
						$("#bzh_modal_tform .title").css("display", "none");
						$(".tiForm-bottom .tiForm-bottom-f-1").empty();
						RequestService("/online/forum/getForumSimpleDetail/", "GET", {
								forumId: qid
							},
							function(data) {
								if(data.resultObject.forumAssistImgList.length == 0) {
									initTFormModal(data.resultObject.forumAssistTitle, data.resultObject.forumAssistContext);

								} else {
									for(var i = 0; i < data.resultObject.forumAssistImgList.length; i++) {
										$(".tiForm-bottom-f-1").append("<div class='tiForm-bottom-f-dv-1' data-vid=" + data.resultObject.forumAssistImgList[i].path + ">" +
											"<img src='" + data.resultObject.forumAssistImgList[i].path + "'>" +
											"<div class='tiForm-bottom-dv-1-1-y' style='display: none'>" + data.resultObject.forumAssistImgList.length + "/5</div>" +
											"<div class='tiForm-bottom-f-dv-1-1'>" +
											"<div class='tiForm-bottom-f-dv-1-1-x' onclick='myquiz_bottom_dv(event)'></div></div></img>");
										$(".tiForm-bottom-f-dv-1 .tiForm-bottom-dv-1-1-y:last").css("display", "block");
										$(".tiForm-bottom-f-dv-1-1-x").click(function(eve) {
											$(eve.target).parent().parent().remove();
										});
									}
									initTFormModal(data.resultObject.forumAssistTitle, data.resultObject.forumAssistContext);

								}
							});
					}
				})
			});

			//我来回答
			$(".link-bar .btn-qus-hd").click(function() {
				var eve = $(this);
				RequestService("/online/user/isAlive", "POST", null, function(data) {
					if(!data.success) {
						localStorage.username = null;
						localStorage.password = null;
						if($(".login").css("display") == "block") {
							$(".login").css("display", "none");
							$(".logout").css("display", "block");
							$('#login').modal('show');
						} else {
							$('#login').modal('show');
						}
						return;
					} else {
						if(eve.attr("data-modalzt") == "详情回答") {
							$("#answerForm").attr("data-modalzt", eve.attr("data-modalzt")).attr("data-vid", eve.attr("data-id"));
							$("#answerForm .cymyanswerlogo").text("我来回答");
							$("#answerForm .myanswer-lo1").text("答案内容");
							$("#answerForm .interlocution-bottom-1").empty();
							$("#answerForm").modal("show");
						}

					}
				})

			});
			//修改答案
			$(".link-bar .btn-qus-xg2").click(function() {
				var eve = $(this);
				RequestService("/online/user/isAlive", "POST", null, function(data) {
					if(!data.success) {
						localStorage.username = null;
						localStorage.password = null;
						if($(".login").css("display") == "block") {
							$(".login").css("display", "none");
							$(".logout").css("display", "block");
							$('#login').modal('show');
						} else {
							$('#login').modal('show');
						}
						return;
					} else {
						if(eve.attr("data-modalzt") == "博问答修改答案") {
							$("#answerForm").attr("data-modalzt", eve.attr("data-modalzt")).attr("data-tid", eve.attr("data-id"));
							$("#answerForm .cymyanswerlogo").text("修改答案");
							$("#answerForm .myanswer-lo1").text("修改答案内容");
							$("#answerForm .interlocution-bottom-1").empty();
							RequestService("/online/forum/findForumAnswer", "GET", {
								forumId: eve.attr("data-id")
							}, function(data) {
								if(data.resultObject.imgList.length == 0) {
									$(".answerFormtxt").val(data.resultObject.context);

								} else {
									for(var i = 0; i < data.resultObject.imgList.length; i++) {
										$(".interlocution-bottom-1").append("<div class='interlocution-bottom-dv-1' data-vid=" + data.resultObject.imgList[i] + ">" +
											"<img src='" + data.resultObject.imgList[i] + "'>" +
											"<div class='interlocution-bottom-dv-1-1-y'  style='display: none'>" + data.resultObject.imgList.length + "/5</div>" +
											"<div class='interlocution-bottom-dv-1-1'>" +
											"<div class='interlocution-bottom-dv-1-1-x' onclick='myquiz_bottom_dv(event)'></div></div></img>");
										$(".interlocution-bottom-dv-1-1-x").click(function(eve) {
											$(eve.target).parent().parent().remove();
										});
										if(i == 4) {
											$(".file2_t").css("display", "block");
										}
									}
									$(".interlocution-bottom-1 .interlocution-bottom-dv-1-1-y:last").css("display", "block");
									$(".answerFormtxt").val(data.resultObject.context)

								}
							});
							$("#answerForm").modal("show");
						}

					}
				})

			});
			//关闭问题补充还原
			$("#bzh_modal_tform .tiFormclose").click(function() {
				$("#bzh_modal_tform .tiForm-lo").css("display", "block");
				$("#bzh_modal_tform .title").css("display", "block");
			});

			//判断当前人是否还存在会话
			RequestService("/online/user/isAlive", "POST", null, function(data) {
				if(!data.success) {
					$(".my-glyphicon-sc").css("background-image", "url(../images/ansandqus/shoucang1.png)");

				} else {
					//收藏的星星

					RequestService("/online/forum/checkForumIsCollect", "GET", {
						forumId: qid
					}, function(data) {

						if(data.resultObject === false) {
							$(".my-glyphicon-sc").css("background-image", "url(../images/ansandqus/shoucang1.png)");
						} else {
							$(".my-glyphicon-sc").css("background-image", "url(../images/ansandqus/shoucang2.png)");
						}
					});
				}
			});

			//采纳为最佳答案
			$(".answer-content").click(function(evt) {
				var target = $(evt.target);
				if(target.hasClass("my-glyphicon-zj") || target.hasClass("good_answer")) {
					RequestService("/online/forum/isForumAuthor/" + $(".content-article").data("id"), "GET", {}, function(data) {
						if(data.success === true) {

							RequestService("/online/forum/acceptForumAnswer", "GET", {
									forumId: $(".content-article").data("id"),
									forumAnswerId: target.data("id")
								},
								function(data) {

									if(data.success === true) {
										createAnswer(true);
										layer.msg("已采纳成功", {
											icon: 1
										});
									} else {
										layer.msg("已有最佳答案", {
											icon: 2
										});
									}
								});
						} else {

							layer.msg("不是当前提问人，不能采纳为最佳答案", {
								icon: 2
							});
						}

					});

				}
			});

			//赞同反对
			$(".answer-content").click(function(evt) {
				/*var target = $(evt.target);
				 var type = "";
				 if(target.hasClass("laud") || target.hasClass("oppose")) {
				 RequestService("/online/forum/checkForumIsApplaudOrOppose", "POST",
				 {forumId:target.data("id")}, function (data) {
				 if(data.success === true){
				 $(".answer-content").unbind();
				 }
				 });

				 }*/
				var target = $(evt.target);
				var type = "";
				if(target.hasClass("laud") || target.hasClass("oppose")) {
					target.hasClass("laud") ? type = 1 : type = 2;
					RequestService("/online/forum/applaudOrOppose", "POST", {
						forumId: target.data("id"),
						type: type
					}, function(data) {
						if(data.success === true) {
							createAnswer(true);

						} else {
							if(localStorage.username === "null") {
								//layer.msg("请登录",{icon:2});
								$('#login').modal('show');
								return;
							} else {
								layer.msg(data.errorMessage, {
									icon: 2
								});
							}

						}
					});
				}

			});

			//收藏问题
			$(".btn-qus-sc").click(function(evt) {
				RequestService("/online/user/isAlive", "POST", null, function(data) {
					if(!data.success) {
						localStorage.username = null;
						localStorage.password = null;
						if($(".login").css("display") == "block") {
							$(".login").css("display", "none");
							$(".logout").css("display", "block");
							$('#login').modal('show');
						} else {
							$('#login').modal('show');
						}
						return;
					} else {
						RequestService("/online/forum/checkForumIsCollect", "GET", {
							forumId: qid
						}, function(data) {
							if(data.resultObject === false) {
								RequestService("/online/forum/collectForum", "POST", {
									forumId: qid
								}, function(data) {
									$(".my-glyphicon-sc").css("background-image", "url(../images/ansandqus/shoucang2.png)");

									layer.msg("已收藏成功", {
										icon: 1
									});
								})
							} else {
								$(".mask,.qxColl").css("display", "block");
								$(".qxCloser,.qxQuit").click(function() {
									$(".mask,.qxColl").css("display", "none");
								});
								$(".qxSure").click(function() {
									$(".mask,.qxColl").css("display", "none");
									RequestService("/online/forum/cancelCollectForum", "POST", {
										forumId: qid
									}, function(data) {
										$(".my-glyphicon-sc").css("background-image", "url(../images/ansandqus/shoucang1.png)");

										layer.msg("已取消收藏", {
											icon: 2
										});
									})
								})
							}
						})
					}
				})
			});
			createAnswer(true);
		});

	function createAnswer(pinging) {
		RequestService("/online/forum/getForumAnswers", "GET", {
				forumId: qid,
				pageNumber: currentPage,
				pageSize: 10
			},
			function(data) {
				$(".answer-content").html(template.compile(allTl.as)({
					items: data.resultObject.items
				}));
				showHtml()
				$(".answer-content .answer-item hr:last").remove();
				if($(".control-bottom .my-glyphicon-zj").hasClass("selected")) {
					$(".link-bar .btn-qus-bc").addClass("hides");
					$(".link-bar .gap").addClass("hides");
					$(this).css("background-image", "url('../images/ansandqus')");
				} else {
					$(".link-bar .btn-qus-bc").removeClass("hides");
					$(".link-bar .gap").removeClass("hides");
				}

				if(pinging) {
					$(".searchPage .allPage").text(data.resultObject.totalPageCount);

					$("#Pagination").pagination(data.resultObject.totalPageCount, {
						callback: function(num) {

							currentPage = num + 1;
							createAnswer();
						}
					});

				}
				//弹出投诉窗口
				$(".complain").click(function() {
					RequestService("/online/user/isAlive", "POST", null, function(data) {
						if(!data.success) {
							localStorage.username = null;
							localStorage.password = null;
							if($(".login").css("display") == "block") {
								$(".login").css("display", "none");
								$(".logout").css("display", "block");
								$('#login').modal('show');
							} else {
								$('#login').modal('show');
							}
							return;
						} else {
							$("#answerForm .cymyanswerlogo").text("投诉");
							$("#answerForm").attr("data-modalzt", "投诉");
							$(".myanswer-lo1").text("投诉内容");
							$("#answerForm").modal("show");
							$("#answerForm .interlocution-bottom-1").empty();
							$("#answerForm .answerFormtxt").val("");
							$(".interlocution-bottom").css("display", "none");
						}
					})

				});
				//图片缩放
				$(".assist-img img").click(function() {
					var src = $(this).attr("src");
					$(this).showlargeimg();
				});
				//图片缩放
				$(".content-img img").click(function() {
					var src = $(this).attr("src");
					$(this).showlargeimg();
				});
				$(".contro-img img").click(function() {
					var src = $(this).attr("src");
					$(this).showlargeimg();
				});
			})

	}
}