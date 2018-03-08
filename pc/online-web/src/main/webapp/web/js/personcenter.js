/**
 * Created by Administrator on 2016/8/13.
 */
$(function() {
	addSelectedMenu();
	$(".path a").removeClass("select");
	//意见反馈模板
	window.onload = function() {
		RequestService("/online/user/isAlive", "get", null, function(data) {
			if(data.success == true) {
				var result = data.resultObject;
				localStorage.userid = result.id;
				var path;
				//头像预览
				if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
					path = data.resultObject.smallHeadPhoto;
				} else {
					path = bath + data.resultObject.smallHeadPhoto
				}
				$(".intro").html(template('namePic', {
					img: path,
					name: result.name ? result.name : "博小白",
					info: result.info ? result.info : "说点什么来彰显你的个性吧……"
				}));
				if(window.localStorage.personcenter!=""&&window.localStorage.personcenter!="undefined") {
					$(".personcenterPage .left-nav ." + window.localStorage.personcenter).click();
					document.title = $(".left-nav ." + window.localStorage.personcenter).text() + " - 熊猫中医云课堂";
				} else {
					//先打开课程
					$(".left-nav .mynews").parent().addClass("selected");
					$(".left-nav .mynews").parent().find(".yes-click").show();
					$(".left-nav .mynews").parent().find(".not-click").hide();
					document.title = "我的消息" + " - 熊猫中医云课堂";
					$(".my-personcenter-nav-2 a").text("我的消息");
					$(".view-content-content").empty();
					$(".personcenterPage .left-nav .mynews").click();
				};
				//面包屑导航的个人中心按钮
				$(".hehe").click(function() {
					return $(".personcenterPage .left-nav .mynews").click();
				});
//				$(".left-nav .intro .pic").hover(function() { //头像hover效果
//					$(".left-nav .intro .picModal").show();
//				}, function() {
//					$(".left-nav .intro .picModal").hide();
//				});
//				$(".left-nav .intro .picModal").hover(function() { //头像hover效果
//					$(".left-nav .intro .picModal").show();
//				}, function() {
//					$(this).hide();
//				});
				//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓更换头像↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓//

				$(".left-nav .intro .picModal,.left-nav .intro .pic").on("click", function() {
					$(".mask").css("display", "block");
					$("#headImg").css("display", "block");
					$("body").css("overflow", "hidden");
					//清空右侧小图片
					//						$('.cropped-con').html('');
					RequestService("/online/user/isAlive", "get", null, function(data) {
						var path;
						//头像预览
						if(data.resultObject.smallHeadPhoto) {
							if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
								path = data.resultObject.smallHeadPhoto;
							} else {
								path = bath + data.resultObject.smallHeadPhoto
							}
						};
						$('.cropped-con').html('');
						$('.cropped-con').append('<img src="' + path + '" align="absmiddle" style="width:80px;height:80px;margin-top:28px;border-radius:80px;" class="img01"><p style="font-size:12px;color:#666;margin-top:6px;">80px*80px</p>');
						$('.cropped-con').append('<img src="' + path + '" align="absmiddle" style="width:40px;height:40px;margin-top:28px;border-radius:40px;"><p style="font-size:12px;color:#666;margin-top:6px;">40px*40px</p>');
						img()
							//新插件
					});
				})

				function img() {
					//清空文件
					function clearFileInput(file) {
						var form = document.createElement('form');
						document.body.appendChild(form);
						//记住file在旧表单中的的位置
						var pos = file.nextSibling;
						form.appendChild(file);
						form.reset();
						pos.parentNode.insertBefore(file, pos);
						document.body.removeChild(form);
					}
					$(".imgClose,.btn-quit").click(function() {
						$('.cropped-con').html('');
						$(".img-box1").css("display", "block");
						$(".imageBox").css("display", "none");
						$(".tc").css("display", "none");
						$(".mask").css("display", "none");
						$("#headImg").css("display", "none");
						$("body").css("overflow", "auto");
						var file = document.getElementById("upload-file");
						clearFileInput(file);
						$(".btn-upload").attr("data-img","");
						$(".imageBox").css("background-image", "");
					})
					var options = {
						thumbBox: '.thumbBox',
						spinner: '.spinner',
						imgSrc: ""
					}
					var cropper = $('.imageBox').cropbox(options);
					var img = "";
					$('#upload-file').on('change', function() {
						var filepath = $(this).val();
						if(filepath==""){
							return false;
						}
						var extStart = filepath.lastIndexOf(".");
						var ext = filepath.substring(extStart, filepath.length).toUpperCase();
						if(ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
							//							layer.msg("图片限于bmp,png,gif,jpeg,jpg格式", {
							//								icon: 2
							//							});
							$(".rrrrTips").text("图片限于bmp,png,gif,jpeg,jpg格式").css("display", "block");
							var file = document.getElementById("upload-file");
							clearFileInput(file);
							setTimeout(function() {
								$(".rrrrTips").css("display", "none");
							}, 1500);
						} else if(($("#upload-file").get(0).files[0].size / 1024 / 1024) > 1) {
							$(".rrrrTips").text("图片大小不超过1M").css("display", "block");
							var file = document.getElementById("upload-file")
							clearFileInput(file);
							setTimeout(function() {
								$(".rrrrTips").css("display", "none");
							}, 1500);
						} else {
							if(filepath) {
								$(".img-box1").css("display", "none");
								$(".imageBox").css("display", "block");
								$(".tc").css("display", "block");
								var reader = new FileReader();
								reader.onload = function(e) {
									options.imgSrc = e.target.result;
									cropper = $('.imageBox').cropbox(options);
									getImg();
								}
								reader.readAsDataURL(this.files[0]);
								this.files = [];
								getImg();
								//								return $(".imageBox").click(function() {
								//									getImg();
								//								});
							}
						}
					})

					function getImg() {
						img = cropper.getDataURL();
						$('.cropped-con').html('');
						$('.cropped-con').append('<img src="' + img + '" align="absmiddle" style="width:80px;height:80px;margin-top:28px;border-radius:80px;" class="img01"><p style="font-size:12px;color:#666;margin-top:6px;">80px*80px</p>');
						$('.cropped-con').append('<img src="' + img + '" align="absmiddle" style="width:40px;height:40px;margin-top:28px;border-radius:40px;"><p style="font-size:12px;color:#666;margin-top:6px;">40px*40px</p>');
						$(".btn-upload").attr("data-img",img);
					}
					$(".imageBox").on("mousemove mouseup", function() {
						getImg()
					})

					//							$('#btnZoomIn').on('click', function() {
					//								cropper.zoomIn();
					//							})
					//							$('#btnZoomOut').on('click', function() {
					//									cropper.zoomOut();
					//							})
				}
				//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑更换头像↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑//

			} else {
//				alert("personcenter.js jmp index.html");
				location.href = "../../index.html";
				localStorage.username = null;
				localStorage.password = null;
				$(".login").css("display", "none");
				$(".logout").css("display", "block");
				
			}
		});

		$(".left-nav  img").click(function() {
			var aa = $(this).parent().find("a").get(0).className;
			window.localStorage.personcenter = aa;
			return $(this).parent().find("a").click();
		})
		$(".left-nav ul").click(function(evt) {
			window.localStorage.personcenter = $(evt.target).parent().find("a").attr("class");
			if(evt.target.tagName !== "A") {
				return;
			}
			document.title = $(".left-nav ." + window.localStorage.personcenter).text() + " - 熊猫中医云课堂";
			$(".view-content-notbodys").html("");
			$(".left-nav ul li").removeClass("selected");
			var li = $(evt.target).parent();
			li.addClass("selected").siblings().removeClass("selected");
			$(".left-nav ul li .not-click").show();
			$(".left-nav ul li .yes-click").hide();
			li.find(".yes-click").show();
			li.find(".not-click").hide();
			var currentViewName = $(evt.target).attr("class");
			$(".pages").css("display", "block");
			$(".pages").css("display", li.data("pages") === "none" ? "none" : "block");
			//意见反馈功能及模板加载
			if($(evt.target).hasClass("idea")) {
				$(".my-personcenter-nav-2 a").text("意见反馈");
				$(".view-content-content").html(template.compile(ideaModal));
				$(".view-stack-idea").css("display", "block");
				$(".idea-title").focus(function() {
					$(".text-title").css("border-color", "#ccc");
					$(".error-msg").hide();
				});
				$(".idea-title").blur(function() {
					var title = $(".text-title").val().trim();
					if(title.length > 50) {
						$(".text-title").css("border-color", "red");
						$(".error-msg").show();
						return false;
					} else if(title == "") {
						$(".text-title").css("border-color", "red");
						$(".error-msg span").text("请输入标题");
						$(".error-msg").show();
						return false;
					} else if(title.length < 5) {
						$(".text-title").css("border-color", "red");
						$(".error-msg span").text("标题5~50字之间");
						$(".error-msg").show();
						return false;
					}
				});
				$(".textarea-miaoshu").focus(function() {
					$(".textarea-miaoshu").css("border-color", "#2cb82c");
					$(".error-msg2").hide();
				});
				$(".textarea-miaoshu").blur(function() {
					var miaoshu = $(".textarea-miaoshu").val().trim();
					if(miaoshu.length > 1000) {
						$(".textarea-miaoshu").css("border-color", "red");
						$(".error-msg2").show();
					} else {
						$(".textarea-miaoshu").css("border-color", "#eee");
					}
				})
				$(".feedback-icon .btn").click(function(evt) {
					RequestService("/online/user/isAlive", "get", null, function(data) {
						if(!data.success) { //登录判断
							$('#login').modal('show');$('#login').css("display", "block");
							localStorage.username = null;
							localStorage.password = null;
							$(".login").css("display", "none");
							$(".logout").css("display", "block");
						} else { //验证
							var title = $(".text-title").val().trim(),
								miaoshu = $(".textarea-miaoshu").val().trim();
							if(title == "") {
								$(".text-title").css("border-color", "red");
								$(".error-msg span").text("请输入标题");
								$(".error-msg").show();
								return false;
							} else if(title.length < 5) {
								$(".text-title").css("border-color", "red");
								$(".error-msg span").text("标题5~50字之间");
								$(".error-msg").show();
								return false;
							} else if(title.length > 4 && title.length < 51) {
								var data = {
									userId: localStorage.id,
									title: title,
									describe: miaoshu
								};
								$.post(bath + "/online/message/addFeedBack", data, function(data) {
									$(".mask,#ideaColl").css("display", "block");
									$("#ideaColl .ideaCloser").click(function() {
										$(".mask,#ideaColl").css("display", "none");
										$(".left-nav .idea").click();
									});
									$(".ideaGo").click(function() {
										$(".mask,#ideaColl").css("display", "none");
										$(".left-nav .idea").click();
									});
								})
							}
						}
					});

				});
			}

			if($(evt.target).hasClass("mydata")) {
				$(".my-personcenter-nav-2 a").text("我的资料");
				$(".view-content-content").empty();
				$(".view-content-content").html(template.compile(mydata));
				createData();
				initAddressBind();
			}
			if($(evt.target).hasClass("mynews")) {
				$(".view-content-content").empty();
				$(".view-content-content").html(template.compile(mynews));
				$(".my-personcenter-nav-2 a").text("我的消息");
				createNews();
			}
			if($(evt.target).hasClass("mylesson")) {
				$(".my-personcenter-nav-2 a").text("我的课程");
				$(".view-content-content").empty();
				createLesson();
			}
			
			if($(evt.target).hasClass("mymoney")) {
				$(".my-personcenter-nav-2 a").text("我的资产");
				$(".view-content-content").empty();
				RequestService("/online/user/isAlive", "get", null, function(data) {
					
					$(".view-content-content").html(template.compile(mymoney)({
		                item: data.resultObject.isLecturer
		            }));
					//如果是讲师就显示 提现和可提现金额
					if(data.resultObject.isLecturer==1){
//						console.log($('#toCash'))
						$('#toCash').css({"display":"inline-block"})
						$('.canTocash').css({"display":"inline-block"})
					}else{
						$('#toCash').css({"display":"none"})
						$('.canTocash').css({"display":"none"})
					}
				},false);
				initBind();
				
//				createLesson();
			}
		});
	};

		
	$(".view-content-notbodys").html("");


	var ideaModal = "<div class='tabnavigator idea'>" +
		"<div class='tabbar'>" +
		"<div class='btn-item color2cb' data-type='view-stack-idea'>意见反馈</div>" +
//		"<div class='pointer'></div>" +
		"</div>" +
		"<div class='form-horizontal view-stack view-stack-idea'>" +
		"<div class='form-group '>" +
		"<label class='fl control-label'><span class='required_01'>*</span>标题</label>" +
		"<div class='col-sm-7'>" +
		"<input  type='text' class='view_01 text-title  require idea-title' placeholder='5-50字之间' maxlength='50'  id='title'>" +
		"</div>" +
		"<label for='miaoshu' class='control-label error-msg'><img src='../images/tanhao.png'><span>标题5~50字之间</span></label>" +
		"</div>" +
		"<div class='form-group'>" +
		"<label for='miaoshu' class='control-label fl'><span class='required_01' style='padding: 0px 13px;'></span>描述</label>" +
		"<div class='col-sm-9'>" +
		"<textarea class='view_01 textarea-miaoshu'   maxlength='1000' rows='13' id='miaoshu' placeholder='1000字以内'></textarea>" +
		"<div class='feedback-icon'>" +
		"<button class='btn'>提交</button>" +
		"</div></div>" +
		"<label for='miaoshu' class='control-label error-msg2'><img src='../images/tanhao.png'>描述不能为空</label>" +
		"</div></div>" +
		"<div class='mask'></div>" +
		"<div class='ideaColl' id='ideaColl'>" +
		"<div class='ideaCloser'></div>" +
		"<div class='ideaSure'>提交成功，感谢您的反馈！</div>" +
		"<a class='ideaGo' style='cursor:pointer'>确定</a>" +
		"</div></div><div id='rqj-idea'></div>" +
		"<script type='text/javascript'>" +
		"$('input').placeholder(); $('textarea').placeholder();" +
		"</script>";

	//name = "mynote";
	$(".view-container").empty();

	var mydata =
		'<div class="tabnavigator my-data">' +
		'<div class="tabbar">' +
		'<div class="btn-item color2cb">个人信息</div>' +
		'<div class="btn-item kc"  >课程报名信息</div>' +
//		'<div class="btn-item zh" >老学员认证</div>' +
		'<div class="btn-item address" >常用地址管理</div>' +
		'</div>' +
		'<div class="my-data-1">' +
		//事先隐藏的提示框
		'<div class="rTips" style=" display: none;"></div>'+
		//个人信息代码
		'<div class="geren">' +
		
		'<div class="cy-myprofile-myfom-dv-1">' +
		'<div class="buer"><span class="red">*</span>用户名:</div>' +
		'<input type="text" maxlength="15" class="firsname ipt"/>' +
		'<span class="nick-warn-name warning">用户名不能为空</span>' +
		'</div>' +
		
		'<div>' +
		'<div class="buer cy-myprofile-myfom-dv-span">个性签名:</div>' +
		'<textarea class="mycytextarea" style="overflow:hidden" maxlength="15" placeholder="说点什么来彰显你的个性吧……" onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)"></textarea>' +
		'<span class="text-warn warning">个性签名不得超过30个字符</span>' +
		'</div>' +
		'<div>' +
		'<div class="buer">帐号:</div>' +
		'<input type="text" disabled="disabled" readonly="readonly"  class="username ipt"/>' +
		'<a href="/web/html/resetPassword.html" class="mypassword-g">修改密码&nbsp;></a>' +
		' </div>' +
		
		'<div class="cy-myprofile-myfom-dv-1">' +
		'<div class="buer"><span class="red"></span>身份信息:</div>' +
		'<select class="shenFen" style="width:155px"> ' +
	        '<option value="volvo" >--选择身份信息--</option> ' +
//	        '<option value="saab">中医爱好者</option> ' +
//	        '<option value="opel">中医爱好者</option> ' +
//	        '<option value="audi">中医爱好者</option> ' +
	        '</select> ' +
		'<span class="nick-warn-shenfen warning" style="margin-left:0px">身份信息不能为空</span>' +
		'</div>' +
		
		'<div class="cy-myprofile-myfom-dv-1">' +
		'<div class="buer"><span class="red"></span>职业信息:</div>' +
		'<input type="text" maxlength="20" class="zhiYe ipt"/>' +
		'<span class="nick-warn warning">职业信息不能为空</span>' +
		'</div>' +
		
		'<div class="cy-myprofile-myfom-dv-1">' +
		// '<div class="buer"><span class="red"></span>微信:</div>' +
		// '<a  href="javascript:;" style="text-decoration:none;color:#2cb82c">进行绑定>></a>' +
		'</div>' +
		
		
		'<div class="cy-myprofile-myfom-dv-radio-zu">' +
		'<div class="buer"><i class="red"></i>性别:</div>' +
		'<label class="kecheng-man1"><div class="radio-cover"><em class="active"></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="1" id="myradio1"/></input><span>男</span></label>' +
		'<label class="kecheng-woman1"><div class="radio-cover"><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="0" id="myradio2"/></input><span>女</span></label>' +
//		'<span class="sex-warn warning" style="display:none">请选择性别</span>' +
		'</div>' +
		
		//三级联动
		 '<div class="address-info2 cy-myprofile-myfom-dv-1"> ' +
	        '<p class="buer"><span></span>所在地区:</p> ' +
	        //省
	        '<select class="Province1" onchange="getCity1()"> ' +
	        '<option value="volvo">--选择省--</option> ' +
//	        '<option value="saab">Saab</option> ' +
//	        '<option value="opel">Opel</option> ' +
//	        '<option value="audi">Audi</option> ' +
	        '</select> ' +
	        //市
	        '<select class="City1" onchange="getDistrict1()"> ' +
	        '<option value="volvo" >--选择市--</option> ' +
//	        '<option value="saab">Saab</option> ' +
//	        '<option value="opel">Opel</option> ' +
//	        '<option value="audi">Audi</option> ' +
	        '</select> ' +
	        
	        //区
	        '<select class="District1"> ' +
	        '<option value="volvo">--选择区/县--</option> ' +
//	        '<option value="saab">Saab</option> ' +
//	        '<option value="opel">Opel</option> ' +
//	        '<option value="audi">Audi</option> ' +
	        '</select> ' +
	        '<p class="address_warn  warning" style="display:none;color:red">请填写所在地区信息</p>'+
	        '</div> ' +
		
		
		
//		' <div class="cy-myprofile-myfom-dv-radio-zu2">' +
//		'    <div class="buer">职业:</div>' +
//		'   <label style="margin-left:-3px"><div class="radio-cover"><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio myradio0" name="job" value="19"/><span>学生</span></input></label>' +
//		'  <label><div class="radio-cover "><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio myradio1" name="job" value="20"/><span>程序员</span></input></label>' +
//		' <label><div class="radio-cover "><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio myradio2" name="job" value="21"/><span>创业者</span></input></label>' +
//		'<label><div class="radio-cover "><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio myradio3" name="job" value="22"/><span>待业者</span></input></label>' +
//		'<label><div class="radio-cover "><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio myradio4" name="job" value="23"/><span>讲师</span></input></label>' +
//		'<label><div class="radio-cover "><em></em></div><input type="radio" class="cy-myprofile-myfom-dv-radio myradio5" name="job" value="24"/><span>其他</span></input>' +
//		'  <!--<input type="text" maxlength="15" class="myjob ipt1 myradioipt"/>-->' +
//		' </label>' +
//		'</div>' +
		
//		'<div>' +
//		'<div class="buer" style="margin-top:10px">工作年限:</div>' +
//		'<span name="food2" class="cy-myprofile-myfom-dv-select-4" >' +
//		'	<p class="select-xiala" id="food4">请选择</p>' +
//		'<div class="xiala q-1">' +
//		'<span>请选择</span>' +
//		'<span>在校生</span>' +
//		'<span>应届生</span>' +
//		'<span>1年以下</span>' +
//		'<span>1~3年</span>' +
//		'<span>3~5年</span>' +
//		'<span>5年以上</span>' +
//		'</div>' +
//		'</span>' +
//		'</div>' +
		//                  '<div>'+
		//                      '<div class="buer">公司/学校</div>'+
		//                      '<input type="text" maxlength="30" class="mycompany ipt"/>'+
		//                  '</div>'+
		
//		'<div>' +
//		'<div class="buer">学习方向:</div>' +
//		'<span name="food2" class="cy-myprofile-myfom-dv-select-5" >' +
//		'<p class="select-xiala" id="food5">请选择</p>' +
//		'<div class="xiala q-1" id="food5xl">' +
//		'</div>' +
//		'  </span>' +
//		'</div>' +
		
		
		
		//三级联动
//		 '<div class="address">' +
//		 '<div class="buer">现居住地:</div>     ' +
//		 '<div class="address-right">' +
//		 '<div>' +
//		 '	<p  id="s_province" class="select-xiala">请选择</p>' +
//		 '	<div class="xiala yearxl" id="shengxl">                ' +
//		 '	</div>' +
//		 '</div>' +
//		 '<div>' +
//		 '	<p id="s_city" class="select-xiala">请选择</p>' +
//		 '	<div class="xiala monthxl" id="shixl"> ' +
//		 '	</div>' +
//		 '</div>' +
//		 '<div>' +
//		 '<p id="s_county" class="select-xiala">请选择</p>' +
//		 '<div class="xiala dayxl" id="xianxl">        ' +
//		 '</div>' +
//		 '</div>' +
//		 ' </div>' +
//		 '</div>' +

		


		'<div>' +
		'<div class="buer"></div>' +
		// '<textarea class="menpaihao" maxlength="29" onchange="this.value=this.value.substring(0, 29)" onkeydown="this.value=this.value.substring(0, 29)" onkeyup="this.value=this.value.substring(0, 29)" placeholder="请填写详细地址，例如街道名称等"></textarea>' +
		'</div>' +
		'<div>' +
		'  <div class="buer"></div>' +
		' <button class="btn1" id="geren">保存</button>' +
		//		'<button class="btn2" id="cancel">取消</button>' +
		'</div>' +
		'</div>' +
		//第二模块
//		'<div class="zhanghu">'+
//			'<div class="yanzheng">'+
//				'<div class="idenContent">'+
//					'<p class="tip">如果您是法律在线（面授、双元、熊猫中医职业课程）的学员，请在证件号中输入您的身份证号验证您的身份（针对老学员会有优惠）；</p>'+
//					'<div class="name clearfix">'+
//						'<p class="fL"><i>*</i><span>真实姓名：</span></p>'+
//						'<p class="fR"><input type="text" name="" id="" value="" placeholder="请输入真实姓名" class="truename" / maxlength="7">'+
//							'<span class="warning name-warn">真实姓名不能为空</span>'+
//						'</p>'+
//					'</div>'+
//					'<div class="shenfen clearfix">'+
//						'<p class="fL"><i>*</i><span>身份证号：</span></p>'+
//						'<p class="fR"><input type="text" name="" id="" value="" placeholder="请输入身份证号" class="truecard" / maxlength="18">'+
//							'<span class="warning shenfen-warn">身份证号格式不正确</span>'+
//						'</p>'+
//					'</div>'+
//					'<span class="startIden">'+
//					'开始认证'+
//				'</span>'+
//				'</div>'+
//			'</div>'+
//			'<div class="yiyanzheng">'+
//				'<div class="symbol">恭喜您已经成功认证为老学员身份</div>'+
//				'<div class="findMess">'+
//					'<p>帮您找回以下信息</p>'+
//					'<p class="xueke">学科：<span>UI学科</span></p>'+
//					'<p class="banji">班级：<span>（面授）-UI设计就业班</span></p>'+
//				'</div>'+
//				'<div class="tt">'+
//				'下一次我们将为您找回共同经历过的小伙伴，一同回忆，一起怀念！'+
//				'</div>'+
//			'</div>'+
//		'</div>'+
//		
		
		
		
		
		//第三模块
		
		'<div class="kecheng">' +
//		'<p class="warn">职业课程需要填写报名信息，只需填写一次，适应于其他所有课程；</br>此信息不公开显示，只是为了方便老师通知课程信息，提供优质课程服务；</br>如果您是法律在线（面授、双元、熊猫中医职业课程）的学员，请在证件号中输入您的身份证号验证您的身份（针对老学员会有优惠）；</p>' +
		'<div class="cy-myprofile-myfom-dv-1" style="margin-bottom:12px">' +
		'<div class="buer"><i class="red">*</i>姓名:</div>' +
		'<input type="text" maxlength="15" class="truename ipt"/>' +
		'<span class="true-warn warning">真实姓名不能为空</span>' +
		'</div>' +
		
		
//		'<div id="birthday_container">' +
//		'<div class="buer"><i class="red">*</i>出生年月:</div>' +
//		'<div class="birth-right">' +
//		'<div>' +
//		'<p name="year" id="year" class="select-xiala"></p>' +
//		'<div class="xiala yearxl" id="yearxl">' +
//		'</div>' +
//		'</div>' +
//		'<div>' +
//		'<p name="month" id="month" class="select-xiala">1月</p>' +
//		'<div class="xiala monthxl" id="monthxl">' +
//		'</div>' +
//		'</div>' +
//		'<div>' +
//		'<p name="day" id="day" class="select-xiala">1日</p>' +
//		'<div class="xiala dayxl" id="dayxl">' +
//		'</div>' +
//		'</div>' +
//		'</div>' +
//		'<span class="birthday-warn warning" style="float:right;margin-right:210px">请选择出生年月</span>' +
//		'<script>$("#birthday_container").birthday();</script>' +
//		'</div>' +
		
		
		
		
		
		'<div>' +
		'<div class="buer"><i class="red">*</i>手机号:</div>' +
		'<input type="text" maxlength="11" class="phonenumber ipt"/>' +
		'<span class="phone-warn warning">手机号不得为空</span>' +
		'</div>' +
		
		'<div class="111">' +
		'<div class="buer"><i class="red">*</i>微信号:</div>' +
		'<input type="text" maxlength="15" class="WeChat ipt"/>' +
		'<span class=" WeChat-warn warning">微信不能为空</span>' +
		'</div>' +
		
		
		'<div>' +
		'<div class="buer"><i class="red">*</i>身份证号:</div>' +
		'<input type="text"  maxlength="18" class="cardNumber ipt"/>' +
		'<span class="card-warn warning">身份证号不得为空</span>' +
		'<span class="cardMark"><img src="/web/images/personcenter/cardProving.png" />已认证为老学员！</span>' +
		'</div>' +
		
		
		'<div class="cy-myprofile-myfom-dv-radio-zu">' +
		'<div class="buer"><i class="red"></i>性别:</div>' +
		'<label class="kecheng-man"><div class="radio-cover"><em class="active"></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="1" id="myradio1"/></input><span>男</span></label>' +
		'<label class="kecheng-woman"><div class="radio-cover"><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="0" id="myradio2"/></input><span>女</span></label>' +
//		'<span class="sex-warn warning" style="display:none">请选择性别</span>' +
		'</div>' +
		

		'<div class="333">' +
		'<div class="buer"><i class="red"></i>QQ号:</div>' +
		'<input type="text" maxlength="15" class="QQnumber ipt"/>' +
		'<span class="QQ-warn warning">QQ不得为空</span>' +
		'</div>' +
		
		
		
		
		
		'<div>' +
		'<div class="buer"><i class="red"></i>邮箱:</div>' +
		'<input type="text"  class="emailname ipt"/>' +
		'<span class="email-warn warning">邮箱不能为空</span>' +
		'</div>' +
		
		
		
		

//		'<div class="111">' +
//		'<div class="bue"><i class="red">*</i>学历:</div>' +
//		'<span name="food2" class="rq-college" >' +
//		'<p class="select-xiala" id="record">请选择</p>' +
//		'<div class="xiala" id="recordxl" style="top:37px">' +
//
//		'</div>' +
//		'</span>' +
//		'<span class="college-warn warning">请选择学历</span>' +
//		'</div>' +
		
		

//		'<div class="222">' +
//		'<div class="buer">毕业院校:</div>' +
//		//      		'<input type="text" maxlength="18" class="daxue ipt"/>'+
//		'<div class="address-right">' +
//		'<div>' +
//		'	<p  id="rq_province" class="select-xiala">请选择</p>' +
//		'	<div class="xiala yearxl" id="rqsxl">                ' +
//		'	</div>' +
//		'</div>' +
//		'<div>' +
//		'	<p id="rq_city" class="select-xiala">请选择</p>' +
//		'	<div class="xiala monthxl" id="rqcxl"> ' +
//		'	</div>' +
//		'</div>' +
//		'<div>' +
//		'<p id="rq_college" class="select-xiala">请选择</p>' +
//		'<div class="xiala dayxl" id="rqdxxl">        ' +
//		'</div>' +
//		'</div>' +
//		' </div>' +
//		'</div>' +
//
//		'<div class="000">' +
//		'<div class="bue">专业:</div>' +
//		'<span name="food2" class="rq-college" >' +
//		'<p class="select-xiala" id="major">请选择</p>' +
//		'<div class="xiala" id="majorxl" style="top:37px">' +
//		'</div>' +
//		'</span>' +
//		'</div>' +
		
		'<div class="000">' +
		'<div class="buer"><i class="red"></i>职业:</div>' +
		'<input type="text" maxlength="15" class="profession ipt"/>' +
		'<span class="profession warning">职业不能为空</span>' +
		'</div>' +
		
		
		'<div class="cy-myprofile-myfom-dv-radio-zu">'+
		'<div class="buer"><i class="red"></i>是否首次参加:</div>'+
		'<label class="kecheng_first"><div class="radio-cover"><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="1" id="myradio1"/></input><span>是</span></label>'+
		'<label class="kecheng_notfirst"><div class="radio-cover"><em></em></div><input type="radio"  class="cy-myprofile-myfom-dv-radio" name="gender" value="0" id="myradio2"/></input><span>否</span></label>'+
//		'<span class="sex-warn warning" style="display:none">请选择是否首次参加</span>'+
		'</div>'+
		
		'<div class="000">' +
		'<div class="buer"><i class="red"></i>课程介绍人:</div>' +
		'<input type="text" maxlength="15" class="introduceName ipt"/>' +
		'<span class="introduceName warning">介绍人不能为空</span>' +
		'</div>' +
		
		

		'<div>' +
		'<div class="buer"></div>' +
		'<button class="btn1" id="kecheng">保存</button>' +
		//		'<button class="btn2" id="cancel2">取消</button>' +
		'</div>' +
		'</div>' +
		
		
		//添加常用地址
		'<div class="usAddress" style="display: none;">'+
		 '<div class="address-title clearfix" style="margin-top:20px">' +
	        '<button class="add-addressBtn">+常用地址添加</button>' +
	        '<p>您已创建 <span class="addNum">1</span>个收货地址，最多可以创建<span>20</span>个</p> ' +
	        '</div> ' +
	        // <!--常用地址列表-->
	        '<div class="address-list"> ' +
	        '<div class="address-main"> ' +
	        '<span class="address-main-close">X</span> ' +
	        '<div class="address-maim-top clearfix"><p>天天&nbsp;&nbsp;</p></div> ' +
	        '<div class="clearfix"><p>收货人: <span>天天</span></p><p>手机: <span>139****6940</span></p></div> ' +
	        '<div class="clearfix"><p>所在地区: <span>海南海口市美兰区演丰镇</span></p><p>详细地址: <span>心承志会大厦201</span></p></div> ' +
	        '<div class="clearfix"><p>邮编: <span>115100</span></p></div> ' +
	        '<div class="clearfix"><a href="javascript:;">编辑</a><a href="javascript:;">设为默认</a></div> ' +
	        '</div> ' +
	        '</div> ' +
	        // <!--隐藏的添加地址的输入列表-->

	        '<div class="add-address hide"> ' +
	        '<div class="add-address-title"><h5 class="style_title"></h5><span class="add-address-close">x</span></div> ' +
	        '<div class="address-info1"> ' +
	        '<p><span>*</span>收货人:</p> ' +
	        '<input type="text" class="consignee" maxlength="15">' +
	        '</div> ' +
	        
	        '<div class="address-info2"> ' +
	        '<p><span>*</span>所在地区:</p> ' +
	        //省
	        '<select class="Province" onchange="getCity()"> ' +
	        '<option value="volvo">--选择省--</option> ' +
//	        '<option value="saab">Saab</option> ' +
//	        '<option value="opel">Opel</option> ' +
//	        '<option value="audi">Audi</option> ' +
	        '</select> ' +
	        //市
	        '<select class="City"  onchange="getDistrict()"> ' +
	        '<option value="volvo" >--选择市--</option> ' +
//	        '<option value="saab">Saab</option> ' +
//	        '<option value="opel">Opel</option> ' +
//	        '<option value="audi">Audi</option> ' +
	        '</select> ' +
	        
	        //区
	        '<select class="District"> ' +
	        '<option value="volvo">--选择区/县--</option> ' +
//	        '<option value="saab">Saab</option> ' +
//	        '<option value="opel">Opel</option> ' +
//	        '<option value="audi">Audi</option> ' +
	        '</select> ' +
	        '<p class="address_warn" style="display:none;color:red">请填写所在地区信息</p>'+
	        '</div> ' +
	        '<div class="address-info3"> ' +
	        '<p><span>*</span>详细地址:</p> ' +
	        '<input type="text" class="detailedAddress"> ' +
	        '</div> ' +
	        '<div class="address-info4 clearfix"> ' +
	        '<div class="address-info4-left"> ' +
	        '<p><span>*</span>手机号码:</p> ' +
	        '<input type="text" maxlength="11"  class="phone"> ' +
	        '</div> ' +
	        '<div class="address-info4-right"> ' +
	        '<p><span>*</span>邮编:</p> ' +
	        '<input type="text" class="postalCode"> ' +
	        '</div> ' +
	        '</div> ' +
	        '<button class="submit-address">保存地址</button> ' +
	        '<button class="cancel">取消</button> ' +
	        '</div>'
		
		'</div>'+
		
		
		
		
		'</div>' +
		'</div>' +
		"<div class='mask'></div>" +
		"<div class='ideaColl' id='personMess'>" +
		"<div class='ideaCloser'></div>" +
		"<div class='ideaSure'>保存成功</div>" +
		"<span class='ideaGo' style='cursor:pointer'>确定</span>" +
		"</div></div><div id='rqj-idea'></div>" +
		'<div class="rrTips">系统繁忙，请稍候再试!</div>' +
		'<div class="rrrTips">保存成功</div>' +
		"<script type='text/javascript'>" +
		"$('input').placeholder(); $('textarea').placeholder();" +
		"</script>";

	var mynews = '<div class="rTips">未选中任何消息</div>' +
		'<div class="mask"></div>' +
		'<div class="del-sure">' +
		'<div class="del-header"><i class="iconfont icon-guanbi qxCloser"></i></div>' +
		'<p class="qxIntro"><i class="iconfont icon-tanhao"></i><span>你确定要删除消息?</span></p>' +
		'<div class="qxBtn">' +
		'<span class="qxQuit" id="qxQuit">取消</span>' +
		'<span class="qxSure" id="qxSure">确定</span>' +
		'</div>' +
		'</div>' +
		'<div class="tabnavigator my-news">' +
			'<div class="tabbar">' +
			'<div class="btn-item color2cb" data-type=""><span>全部消息</span></div>' +
			'<div class="btn-item " data-type="1"><span>动态消息</span><i>99</i></div>' +
			'<div class="btn-item " data-type="3"><span>问答消息</span><i>99</i></div>' +
			'<div class="btn-item " data-type="4"><span>评论消息</span><i>99</i></div>' +
			'<div class="btn-item " data-type="0"><span>系统消息</span><i>99</i></div>' +
	//		'<span class="re-bt re-mark">标为已读</span> <span class="re-bt re-del">删除</span>' +
			'</div>' +
			'<span class="read-all" data-type="">全部标为已读</span>'+
		'<div style="width:950px;margin-left:-30px;">' +
		'<ul class="cy-data">' +
		'</ul>' +
		'</div>' +
		'</div>' +
		'<script type="text/javascript" src="../js/mynews.js" ></script>';
	
	
	var mymoney = 
	'<div id="main">'+
		'<div class="main-content">'+
		
		'<div id="main-right">'+
    //<!--左侧选中标题-->
    '<div class="main-right-top">'+
        '<span>我的资产</span>'+
   ' </div>'+
    //<!--充值体现部分-->
    '<div class="save-cash">'+
        '<div>'+
            '<span display="inline-block"><img src="/web/images/myResetMoney.png" alt=""></span>'+
            '<strong  style="margin-left: 10px;">账户余额：<span class="restMoney" style="color:#2cb82c"></span>熊猫币</strong>&nbsp;&nbsp;'+
            '<button class="save-cash-recharge " id="recharge">充值</button>'+
            // '&nbsp;&nbsp;'+
            // '<button calss="save-cash-tocash" id="toCash";>提现</button>'+
        '</div>'+
        // '<p class="canTocash" style="position: absolute;bottom: 75px;height: 24px;font-size:12px;left:27px;font-weight:700" >可提现金额：￥<span class="toCashMoney"></span>&nbsp;<a href="javascript:;" style="color:red;display:inline"  class="detail">详情</a></p>'+
    '</div>'+

    //<!--提现模态框-->
    '<div class="modal-content" id="cash-model" style="display: none;">'+
        //<!--模态框头部-->
       ' <div class="modal-header">'+
            '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" class="cash-close">×</span>'+
            '</button>'+
           ' <h4 class="modal-title" id="gridSystemModalLabel "  style="font-weight: 700"><span class="glyphicon glyphicon-euro"></span>提现</h4>'+       
        '</div>'+
        //<!--内部结构-->
       ' <form action="">'+
            '<div><label>姓&nbsp;&nbsp;&nbsp;名:<input placeholder="请输入姓名" type="text" class="userName cashipt" ></label></div>'+
            '<span class="userName-warn cashwarn"></span>'+
            '<div><label>手机号:<input type="text" maxlength="11"  class="telNumber cashipt" ></label></div>'+
            '<span class="telNumber-warn cashwarn"></span>'+
            '<div>支付方式<span style="border: 2px solid #2cb82c !important;margin-left: 25px;padding: 5px 10px;border-radius: 10px;">支付宝</span></div>'+
            '<div><label>支付宝账号<input type="text" class="eaccount cashipt" placeholder="请输入支付宝账号"></label></div>'+
            '<span class="eaccount-warn cashwarn"></span>'+
            '<div><label>提现金额:<input type="text" placeholder="" class="totalMoney cashipt"></label></div>'+
            '<span class="totalMoney-warn cashwarn"></span>'+
        '</form>'+
        //<!--模态框底部-->
        '<div class="modal-footer">'+
//        '<textarea name="" id="" cols="30" rows="2" style="resize:none;outline: none;"></textarea>'+
            '<a href="javascript:;" style="float: right" class="totalTocash">全部提现</a>'+
            '<button type="button" class="btn btn-primary" id="sure-put">确定提交</button>'+
            //<!--<button type="button" class="btn btn-primary" id='sure-put' data-toggle="modal" data-target=".bs-example-modal-lg">确定提交</button>-->
       '</div>'+
   '</div>'+


    //<!--提现成功模态框-->
    '<div class="modal-content " id="cash-success" style="display: none;">'+
        //<!--模态框头部-->
        '<div class="modal-header">'+
            '<button type="button" class="close" data-dismiss="modal" aria-label="Close" id="cash-success-close">'+
                '<span aria-hidden="true" class="cash-close">×</span></button>'+
            '<h4 class="modal-title" id="gridSystemModalLabel1"><span class="glyphicon glyphicon-euro"></span>提现 </h4>'+
        '</div>'+
        //<!--内容-->
        '<img src="../images/pay-success.png" style="margin: 25px" alt="">'+
        //<!-- <div><b>提现成功了</b></div> -->
        '<p>提现申请成功,我们将在1-7个工作日内与您</p>'+
        '<p>取得联系,请保持通信畅通</p>'+
        //<!--模态框底部-->
        '<div class="modal-footer">'+
            '<button type="button" class="btn btn-primary" id="continue">继续</button>'+
            //<!--<button type="button" class="btn btn-primary" id='sure-put' data-toggle="modal" data-target=".bs-example-modal-lg">确定提交</button>-->
       '</div>'+
    '</div>'+
    
    
    
    
    '<div id="rmbTopanda" style="display:none">'+
    '<span class="closeTip">X</span>'+
    '<p style="text-align:center;font-weight:700">兑换比例</p>'+
    '<table>'+
    '<caption>目前熊猫币兑换人民币的比例为10：1</caption>'+
    '<tr><th style="color:white;background: #ccc url(../images/rmb.png) no-repeat  158px -12px;">人民币</th><th style="color:white;background:rgb(126,220,135) url(../images/pandaMoney.png) no-repeat 158px -12px;">熊猫币</th></tr>'+
    '<tr><td>1</td><td>10</td></tr>'+
    '<tr><td>10</td><td>100</td></tr>'+
    '<tr><td>50</td><td>500</td></tr>'+
    '<tr><td>100</td><td>1000</td></tr>'+
    '</table>'+
    '</div>'+

    //<!--右侧table栏部分-->
    '<div>'+

        //<!-- Nav tabs -->
        '<ul class="nav nav-tabs" role="tablist">'+
        
            '<li role="presentation" class="active"><a class="home" href="#home"  aria-controls="home" role="tab" data-toggle="tab" aria-expanded="false">现金消费记录</a></li>'+
            '<li role="presentation" class=""><a class="panda" href="#panda"  aria-controls="panda" role="tab" data-toggle="tab" aria-expanded="false">熊猫币消费记录</a></li>'+
            '<li role="presentation" class=""><a class="profile" href="#profile" aria-controls="profile" role="tab" data-toggle="tab" aria-expanded="false">充值记录</a></li>'+
           //  '{{if item == 1}}' +
           //  '<li role="presentation" class=""><a class="messages" href="#messages" aria-controls="messages" role="tab" data-toggle="tab" aria-expanded="false">提现记录</a></li>'+
           //  '<li role="presentation" class=""><a class="settings" href="#settings" aria-controls="settings" role="tab" data-toggle="tab" aria-expanded="false">收到礼物</a></li>'+
           //  '<li role="presentation" class=""><a class="reward" href="#reward " aria-controls="reward" role="tab" data-toggle="tab" aria-expanded="true">收到打赏</a></li>'+
           // 	'<li role="presentation" class=""><a class="lecturer" href="#lecturer " aria-controls="lecturer" role="tab" data-toggle="tab" aria-expanded="true">直播课程列表</a></li>'+
           //
           // '{{/if}}' +

           '</ul>'+
        

        //<!-- Tab panes -->
        '<div class="tab-content">'+
            '<div class="tab-pane  active" id="home">'+
                '<ul id="xfjl">'+
                    '<li><span>订单号</span><span>用途</span><span>金额</span><span>时间</span><span>支付方式</span></li>'+
//                    '<li><span>125064050</span><span>购买课程《解读常见身体信号值头疼》</span><span>-300</span><span>2017.08.28</span><span>支付宝</span><span>支付失败</span></li>'+
               ' </ul>'+
            '</div>'+
            
            '<div class="tab-pane " id="panda">'+
            '<ul id="pandaMondy">'+
                '<li><span style="flex: 3;">订单号</span><span style="flex: 3;">用途</span><span style="flex: 2;">数量</span><span style="flex: 2;">熊猫币</span><span style="flex: 3;">时间</span></li>'+
//                '<li><span>125064050</span><span>购买课程《解读常见身体信号值头疼》</span><span>-300</span><span>2017.08.28</span><span>支付宝</span><span>支付失败</span></li>'+
           ' </ul>'+
        '</div>'+
            
            
            '<div role="tabpanel" class="tab-pane " id="profile">'+
                '<ul id="payMoney">'+
                   '<li><span style="flex: 3;">订单号</span><span style="flex: 2;">金额</span><span style="flex: 3;">时间</span><span style="flex: 2;">充值方式</span></li>'+
//                    '<li><span>125064050</span><span>-300</span><span>2017.08.28</span><span>支付宝</span><span>支付失败</span></li>'+
                '</ul>'+
            '</div>'+
            '<div role="tabpanel" class="tab-pane" id="messages">'+
                '<ul id="txjl">'+
                    '<li><span style="flex: 3;">订单号</span><span style="flex: 2;">金额</span><span style="flex: 3;">时间</span><span style="flex: 1;">提现方式</span><span style="flex: 2;">支付结果</span></li>'+
//                    '<li><span>125064033</span><span>-3600</span><span>2017.08.28</span><span>支付宝</span><span>转账失败</span></li>'+
//                    '<li> <span>125064033</span><span>-3600</span><span>2017.08.28</span><span>支付宝</span><span>已到账</span></li>'+
               ' </ul>'+
            '</div>'+
            '<div role="tabpanel" class="tab-pane" id="settings">'+
                '<ul id="sdlw">'+
                   ' <li><span>订单号</span><span>礼物</span><span>礼物数量</span><span>熊猫币</span><span style="flex: 3;">时间</span><span>赠送人</span></li>'+
//                    '<li><span>125064022</span><span>一个礼物</span><span>+3000</span><span>2017.08.28</span><span>我就是我不一样的烟火</span></li>'+
                '</ul>'+
            '</div>'+
            '<div role="tabpanel" class="tab-pane " id="reward">'+
                '<ul id="dsjl" >'+
                    '<li><span>订单号</span><span>打赏金额</span><span>熊猫币</span><span>时间</span><span>赠送人</span></li>'+
//                    '<li><span>125064050</span><span>-300</span><span>2017.08.28</span><span>我就是我不一样的烟火</span></li>'+
               ' </ul>'+
            '</div>'+
             '<div role="tabpanel" class="tab-pane " id="lecturer">'+
                '<ul id="jiangshi" >'+
                    '<li><span>直播课程名称</span><span>直播开始时间</span><span>直播报名人数</span><span>直播报名总金额</span><span>单价</span><span>操作</span></li>'+
//                     '<li><span>黄飞虎</span><span>赵日天</span><span>元婴期</span><span>赵日天</span><span>2017-12-05</span><span onclick="btn_details()" style="color:green; cursor: pointer;">详情</span></li>'+
               ' </ul>'+
            '</div>'+
        '</div>'+
        '</div>'+
    	

    //<!--分页部分-->
    '<div class="device-page">'+
        '<nav>'+
            '<ul class="pagination" style="float:right">'+
            	//分页
            '</ul>'+
        '</nav>'+
   ' </div>'+
'</div>'+
'</div>'+
'</div>'+

'<div id="mask" style="display: none;position:fixed;top:0;left:0;width:100%;height:100%;background:#000;opacity:0.3;z-index:888;filter:alpha(opacity=30);">'
	
});
//点击直播课程详情弹窗
function btn_details(t){
	var bb=$(t).attr("data-id");
	RequestService("/gift/getLiveCourseUsersById", "POST", {
				pageNumber:1,
				pageSize:10000,
				id:bb
	}, function(data){
			for (var i =0;i<data.resultObject.items.length;i++) {
				data.resultObject.items[i].createTime = data.resultObject.items[i].createTime.substring(0,19);
			}
				 $(".my_details").html(template("my_data",{item:data.resultObject.items}));
			})
	$(".bg_03").fadeIn(200);
	$(".my_details").fadeIn(200);
}
function my_cloce(){
	$(".bg_03").fadeOut(200);
	$(".my_details").fadeOut(200);
}
//点击直播课程详情弹窗结束





function fileClick() {
	return $("#upload-file").click();
}
$(".btn-upload").click(function(evt) {
		evt.preventDefault();
		if($(".btn-upload").attr("data-img")!=undefined&&$(".btn-upload").attr("data-img")!=""){			
		}else{
			$(".rrrrrTips").text("请选择图片").css("display", "block");
			setTimeout(function() {
				$(".rrrrrTips").css("display", "none");
			}, 1500);
			return false;
		}
		$(".btn-upload").css("color", "white");
		//	if($(".upload_pictures_bottom_upload").attr("data-id") && $(".upload_pictures_bottom_upload").attr("data-id") != '/webview/images/usershow/defaultHeadImg.jpg') {
		RequestService("/online/user/updateHeadPhoto", "post", {
				image: $(".btn-upload").attr("data-img"),
			}, function(data) {
				if(data.success == true) {
					RequestService("/online/user/isAlive", "get", null, function(t) {
						var path;
						if(t.resultObject.smallHeadPhoto) {
							if(t.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
								path = t.resultObject.smallHeadPhoto;
							} else {
								path = bath + t.resultObject.smallHeadPhoto
							}
							$(".userPic").css({
								background: "url(" + path + ") no-repeat",
								backgroundSize: "100% 100%"
							});
							$(".personcenterPage .left-nav .intro .pic").css({
								background: "url(" + path + ") no-repeat",
								backgroundSize: "100% 100%"
							});
							var file = document.getElementById("upload-file")
								//清空文件
							function clearFileInput(file) {
								var form = document.createElement('form');
								document.body.appendChild(form);
								//记住file在旧表单中的的位置
								var pos = file.nextSibling;
								form.appendChild(file);
								form.reset();
								pos.parentNode.insertBefore(file, pos);
								document.body.removeChild(form);
							}
							clearFileInput(file);
							$('.cropped-con').html('');
							$(".img-box1").css("display", "block");
							$(".imageBox").css("display", "none");
							$(".tc").css("display", "none");
							$(".mask").css("display", "none");
							$("#headImg").css("display", "none");
							location.reload();
						}

					})

				}
			})
			//	} else {
			//
			//		layer.msg("请上传头像", {
			//			icon: 2
			//		});
			//	}
		$(".btn-upload").css("color", "white");
	})
	//function addEvent(obj,xEvent,fn) {
	//  if(obj.attachEvent){
	//    obj.attachEvent('on'+xEvent,fn);
	//  }else{
	//    obj.addEventListener(xEvent,fn,false);
	//  }
	//}
	
	
function addSelectedMenu(){
//	$(".message").css("color","#2cb82c");
}




