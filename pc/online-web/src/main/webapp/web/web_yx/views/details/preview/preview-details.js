/**
 *   预习
 *    http://onlineweb.ixincheng.com:58000/web/web_yx/views/index.html#/preview/details/2c90819159054a7c0159056c6ce5000f
 */
define(['template',
		'jquery',
		'layui',
		'layer',
		'laypage',
		'text!tplUrl/details/preview/preview-details.html',
		'text!tplUrl/details/review/review-details-box.html',
		'text!tplUrl/details/review/list-details-video-1.html',
		'text!tplUrl/details/review/list-details-audio-2.html',
		'text!tplUrl/details/review/list-details-ppt-3.html',
		'text!tplUrl/details/review/list-details-plan-4.html',
		'text!tplUrl/details/review/list-details-pic-5.html',
		'text!tplUrl/details/review/list-details-text-6.html',
		'text!tplUrl/details/review/list-details-download-7.html',
		'text!tplUrl/details/review/list-details-problem-8.html',
		'text!tplUrl/details/review/login.html',
		'common',
		'config',
		'audiojs',
		'jquery.fs.boxer',
		'css!font-awesome',
		'css!cssUrl/review-details',
		'css!webUrl/mylogin'
	],
	function (template,$,layui,layer,laypage,
	          reviewTpl,
	          reviewBoxTpl,
	          listVideoTpl,
	          listAudioTpl,
	          listPPTTpl,
	          listPlanTpl,
	          listPicTpl,
	          listTextTpl,
	          listDownloadTpl,
	          listProblemTpl,
	          loginTpl,
	          common ,config,audiojs) {

		function createPage( reviewId ) {
			document.title = "熊猫中医云课堂 - 预习";

			var treeId = '',
				typeId = '',
				COURSERESOURCE_APPLY = 0,  // 课程资源--使用类型  0预习，1课堂，2复习
				COURSERESOURCE_PAGENUMBER = 1,  //  课程资源--第几页
				COURSERESOURCE_PAGESIZE = 6,  // 课程资源--每页显示条数---通用(除了常见问题与图片每个tab只有一个对象)
				COURSERESOURCE_TEXT_PAGESIZE = 3,  // 课程资源-- 常见问题每页面显示条数
				COURSERESOURCE_PIC_PAGESIZE = 4,  // 课程资源-- 常见问题每页面显示条数
				COURSERESOURCE_TYPEID = 0;     // 默认显示视频的知识点下的资源

			var watchedDuration = 0;  // 更新视频的时间
			var endTime; // 定时器
			//获取复习的树节点
			// id 1aa6873aeac6428ea644bf17e0fbc2cf
			// id: 13952632145  123456
			//获取学生信息
			var getUserDB = getUser();
			if(!getUserDB.success) {
				layer.alert( getUserDB.errorMessage ||"请登录!" );
				setTimeout(function(){
					window.close();
				},1000);
			}
			var loginName = getUserDB.resultObject.loginName;

			var BATHPATH = new Config().base;

			var startTreeDB = getStartTree( BATHPATH,loginName, reviewId);
			if(!startTreeDB.success) {
				layer.alert( startTreeDB.errorMessage ||"服务器异常!" );
			};

			// 获取资源
			var ResourceDB = findCourseResource( BATHPATH ,reviewId,COURSERESOURCE_TYPEID, COURSERESOURCE_APPLY, COURSERESOURCE_PAGENUMBER, COURSERESOURCE_PAGESIZE);
			if(!ResourceDB.success) {
				layer.alert( ResourceDB.errorMessage ||"服务器异常!" );
			};


			// 实例化对象
			var R = new ReviewDetails();
			R.initPage();

			$('#nav-top').html('预习列表<span class="layui-box">&gt;</span>');

			/**
			 *  创建复习详情对象
			 * @constructor
			 */
			function ReviewDetails( redb ){
				var _seft = this;

				this.initPage = function(){
					$("#app").html(template.compile( reviewTpl)({
						treeDB:startTreeDB,
						resourceDB: ResourceDB
					}));

					_seft.details(ResourceDB);
					_seft.requerTab();
					_seft.interaction();
					$('.J-pic-preview img').boxer({
						requestKey: 'abc123'
					});
				};


				this.details = function (redb) {
					$("#details-box").html(template.compile( reviewBoxTpl)({
						resourceDB: redb
					}));
				};






				this.interaction = function(){

					// 一级选择
					$(".layui-tree .J-li-1 .layui-tree-spread").unbind().on('click', function(){

						if ($(".layui-tree .J-li-2").hasClass("layui-show")) {
							// 收缩
							$(".layui-tree .J-ul-2").removeClass("layui-show");
							$(".layui-tree .J-ul-2 .J-li-2").removeClass("layui-show");
							$(".layui-tree .J-ul-2 .J-li-2 .J-ul-3").removeClass("layui-show");
							$(this).html('<i class="layui-icon layui-tree-spread"></i>');
						}else {
							//展开
							$(".layui-tree .J-ul-2").addClass("layui-show");
							$(".layui-tree .J-ul-2 .J-li-2").addClass("layui-show");
							$(".layui-tree .J-ul-2 .J-li-2 .J-ul-3").addClass("layui-show");
							$(this).html('<i class="layui-icon layui-tree-spread"></i>');
						};
					});


					// 二级选择
					$(".layui-tree .J-li-2 .layui-tree-spread").unbind().on('click', function(){
						if ( $( $(this).parent().parent().children(".J-ul-3") ).hasClass("layui-show") ) {
							// 收缩
							$(this).parent().parent().children(".J-ul-3").removeClass("layui-show");
							$(this).html('<i class="layui-icon layui-tree-spread"></i>');
						}else {
							//展开
							$(this).parent().parent().children(".J-ul-3").addClass("layui-show");
							$(this).html('<i class="layui-icon layui-tree-spread"></i>');
						};
					});


					// 三级选择
					$(".layui-tree .J-li-3 .layui-tree-spread").unbind().on('click', function(){
						if ( $( $(this).parent().parent().children(".J-ul-4") ).hasClass("layui-show") ) {
							// 收缩
							$(this).parent().parent().children(".J-ul-4").removeClass("layui-show");
							$(this).html('<i class="layui-icon layui-tree-spread"></i>');
						}else {
							//展开
							$(this).parent().parent().children(".J-ul-4").addClass("layui-show");
							$(this).html('<i class="layui-icon layui-tree-spread"></i>');
						};
					});

					var Boo = false; // 判断是否有点击
					var ArrLi = new Array();  // 存储之前的这定时器


					// 当前的选中发的请求数据
					$(".J-request").unbind().on('click', function(){
						$(".layui-tree .li-box").removeClass("active");
						// 选中
						$(this).parent().addClass("active");



						/**
						 * 点击树时，都切换到tab的第一个选项中
						 */
							// tab先全部移除
						$('.layui-tab-title li').removeClass('layui-this');
						$('.layui-tab-content .layui-tab-item').removeClass('layui-show');
						// tab移动到第一个tab标签上
						$('.layui-tab-title .video-tab-title').addClass('layui-this');
						$('.layui-tab-content .video-tab-item').addClass('layui-show');


						// 获取当前的treeid
						treeId = $(this).data('id');

						if(treeId != undefined ) {
							// 切换为视频时，播放从当前的播放点开始播放
							var findPointLastWatchedDurationDB = findPointLastWatchedDuration( BATHPATH ,loginName, reviewId, treeId);
							if(!findPointLastWatchedDurationDB.success) {
								layer.alert( findPointLastWatchedDurationDB.errorMessage ||"服务器异常!" );
							};
							var Time = findPointLastWatchedDurationDB.resultObject.watchedDuration;


							// 播放当前的资源视频
							var ResourceDB = findCourseResource( BATHPATH ,treeId,COURSERESOURCE_TYPEID, COURSERESOURCE_APPLY, COURSERESOURCE_PAGENUMBER, COURSERESOURCE_PAGESIZE);
							if(!ResourceDB.success) {
								layer.alert( ResourceDB.errorMessage ||"服务器异常!" );
							};
							_seft.allToLoadMore( COURSERESOURCE_TYPEID, ResourceDB, Time);


							/**
							 * 当切换知识点树时，须要保存这个知识点的观看时间
							 */
							// 发送请求时间（观看时间）
							setInterval(function(){
								_seft.getVideoTime();
							},60000);


							/**
							 * 播放进度实时更新
							 * @type {*|jQuery}
							 */
							if ( $(window.frames["viewVideo"]) != undefined ) {
								if ( $(window.frames["viewVideo"].document) != undefined ){
									//if (watchedDuration > 0 ) {
									// 获取视频的总时长时间
									var tatVideo = $(this).data('vttime');
									//var _seft_j_ = $(this);
									//封装一个函数来对this指向问题
									function setInteFun( _this_ ){

										var treeSetInt = setInterval(function(){
											// 获取当前视频的播放时间
											var watchedDuration = parseInt($(window.frames["viewVideo"].document).find("#nowTime").html());
											if (tatVideo != 0 || tatVideo != undefined ) {
												if( watchedDuration == 0 || watchedDuration == undefined ) {
													_this_.parent().children('.rate').html('0%');
												} else {
													_this_.parent().children('.rate').html( Math.round((watchedDuration/tatVideo)*100)+'%' );
												}
											}
										},1000);


										return (treeSetInt);

									}



									// 当播放完时，更新一下进度，调用接口
									endTime = setInterval(function(){
										// 获取当前视频的播放时间
										var watchedDuration = parseInt($(window.frames["viewVideo"].document).find("#nowTime").html());
										if (tatVideo == watchedDuration) {
											// 每秒去获取当前播放的时长
											_seft.getVideoTime();
											clearInterval(endTime);
										}
									},1000);

									//}
								}
							}
						}


						/**
						 * 作用： 清除上一次的定时器
						 * 原理： 利用数据来存储之前的定时器，删除之前的定时器
						 */
						var F = setInteFun($(this));
						ArrLi.unshift(F);
						if(Boo) {
							var Num = ArrLi.splice(1,ArrLi.length-1);
							clearInterval( Num );
						}

						Boo = true;

					});


					// 触发视频观看时间接口： 当窗口关闭时，当切换树时，刷新页面时，视频正常结束时
					/*
					window.onbeforeunload = onbeforeunload_handler;
					window.onunload = onunload_handler;
					function onbeforeunload_handler(){
						// 发送请求时间（观看时间）
						_seft.getVideoTime();
						var warning="确认退出?";
						return warning;
					}

					function onunload_handler(){
						var warning="谢谢光临";
						alert(warning);
						// 发送请求时间（观看时间）
						_seft.getVideoTime();
					}
					*/

					// 控制展开与收缩
					_seft.showRight();
					_seft.hideRight();

				};


				/**
				 * 加载更多
				 * @param re_type   资源类型
				 */
				this.allToLoadMore = function( re_type, redb ,Time ){
					// 分页
					if( re_type == 0 ){
						$('#video-view').html(template.compile( listVideoTpl)({
							resourceDB: redb
						}));
						//发送更新请求
						_seft.videoPlay(redb, Time);
					}
					if( re_type == 1 ){
						$('#audio-view').html(template.compile( listAudioTpl)({
							resourceDB: redb
						}));
						audiojs.events.ready(function() {
							var as = audiojs.createAll();
						});
					}
					if( re_type == 2 ){
						$('#ppt-view').html(template.compile( listPPTTpl)({
							resourceDB: redb
						}));
					}
					if( re_type == 3 ){
						$('#plan-view').html(template.compile( listPlanTpl)({
							resourceDB: redb
						}));
					}
					if( re_type == 4 ){
						laypage({
							cont: $('#pic-page'), //容器。值支持id名、原生dom对象，jquery对象,
							pages:  redb.resultObject.totalPageCount, //总页数
							groups: 0, //连续分数数0
							first: false,
							last: false,
							jump: function(obj, first){
								var ResourceDB = findCourseResource( BATHPATH ,treeId,re_type, COURSERESOURCE_APPLY, obj.curr, COURSERESOURCE_PIC_PAGESIZE);
								$('#pic-view').html(template.compile( listPicTpl)({
									resourceDB: ResourceDB
								}));

								//图片
								$('.J-pic-preview').on('click', function(){
									var url = $(this).data('url');
									//iframe层-父子操作
									layer.open({
										type: 1,
										title: false,
										scrollbar: false,
										skin: 'layui-layer-rim', //加上边框
										area:  ['1200px', '800px'],
										content: '<img src="'+url+'" style="width: 100%;height: 100%;" />'
									});
								});
							}
						});
					}
					if( re_type == 5 ){
						$('#text-view').html(template.compile( listTextTpl)({
							resourceDB: redb
						}));
					}
					if( re_type == 6 ){
						$('#download-view').html(template.compile( listDownloadTpl)({
							resourceDB: redb
						}));
					}
					if( re_type == 7 ){
						laypage({
							cont: $('#problem-page'), //容器。值支持id名、原生dom对象，jquery对象,
							pages:  redb.resultObject.totalPageCount, //总页数
							groups: 0, //连续分数数0
							first: false,
							last: false,
							jump: function(obj, first){
								var ResourceDB = findCourseResource( BATHPATH ,treeId,re_type, COURSERESOURCE_APPLY, obj.curr, COURSERESOURCE_PIC_PAGESIZE);
								$('#problem-view' ).html(template.compile( listProblemTpl)({
									resourceDB: ResourceDB
								}));
							}
						});
					}
					/**
					 * 分页
					 * @param pageClass
					 * @param viewClass
					 * @param domid
					 */
					//function pageing ( domid, totalPage){
					//	laypage({
					//		cont: $(domid), //容器。值支持id名、原生dom对象，jquery对象,
					//		pages: totalPage, //总页数
					//		groups: 0, //连续分数数0
					//		first: false,
					//		last: false,
					//		jump: function(obj, first){
					//
					//			//判断是否是图片还是常见问题还是其它的
					//			if ( re_type == 7 ){
					//				var ResourceDB = findCourseResource( BATHPATH ,treeId,re_type, COURSERESOURCE_APPLY, obj.curr, COURSERESOURCE_TEXT_PAGESIZE);
					//				$('#problem-view' ).html(template.compile( listProblemTpl)({
					//					resourceDB: ResourceDB
					//				}));
					//			}
					//			if( re_type == 4 ){
					//				var ResourceDB = findCourseResource( BATHPATH ,treeId,re_type, COURSERESOURCE_APPLY, obj.curr, COURSERESOURCE_PIC_PAGESIZE);
					//				$('#pic-view').html(template.compile( listPicTpl)({
					//					resourceDB: ResourceDB
					//				}));
					//			};
					//			if(!ResourceDB.success) {
					//				layer.alert( ResourceDB.errorMessage ||"服务器异常!" );
					//			};
					//			//if(obj.curr === ResourceDB.resultObject.totalPageCount){
					//			//	this.next = '没有更多了';
					//			//}
					//
					//		}
					//	});
					//};
				};


				/**
				 * tab切换请求数据
				 */
				this.requerTab = function(){
					// tab切换请求数据
					$('.layui-tab-title li').unbind().on('click', function(){
						// 获取当前的类型
						typeId = $(this).data('type');

						_seft.getResource(typeId);

						// 当切换TAB标签时，清除视频的定时器
						clearInterval(endTime);
					});
				};


				this.getResource = function ( re_type ) {
					//判断是否是图片还是常见问题还是其它的
					if ( re_type == 7 ){
						var ResourceDB = findCourseResource( BATHPATH,treeId,re_type, COURSERESOURCE_APPLY, COURSERESOURCE_PAGENUMBER, COURSERESOURCE_TEXT_PAGESIZE);
					}else if( re_type == 4 ){
						var ResourceDB = findCourseResource( BATHPATH,treeId,re_type, COURSERESOURCE_APPLY, COURSERESOURCE_PAGENUMBER, COURSERESOURCE_PIC_PAGESIZE);
					}else {
						var ResourceDB = findCourseResource( BATHPATH,treeId,re_type, COURSERESOURCE_APPLY, COURSERESOURCE_PAGENUMBER, COURSERESOURCE_PAGESIZE);
					};
					if(!ResourceDB.success) {
						layer.alert( ResourceDB.errorMessage ||"服务器异常!" );
					};

					_seft.allToLoadMore(typeId, ResourceDB, '0');
				};

				// 控制展开与收缩
				this.hideRight = function(){
					$(".J-btn-shrink").unbind().on('click', function(){
						$(".details-box").css({"padding-right":"80px"});
						$(".details-right").css({"width":"0px"});
						$(this).removeClass("J-btn-shrink");
						$(this).addClass("J-btn-unfold");
						$('.details-right-btn').css({'background':'#000000'});
						$(this).html('<i class="fa fa-angle-left" aria-hidden="true"></i>');
						_seft.showRight();
					});
				};

				this.showRight = function(){
					$(".J-btn-unfold").unbind().on('click', function(){
						$(".details-box").css({"padding-right":"369px"});
						$(".details-right").css({"width":"277px"});
						$(this).removeClass("J-btn-unfold");
						$(this).addClass("J-btn-shrink");
						$('.details-right-btn').css({'background':'#363636'});
						$(this).html('<i class="fa fa-angle-right" aria-hidden="true"></i>');
						_seft.hideRight();
					});
				};



				this.videoPlay = function ( redb , time){

					if ( redb.success ) {
						if ( redb.resultObject.items != '' ) {
							$('#video-view').html('<iframe id="viewVideo" name="viewVideo" src="./video.html?video='+redb.resultObject.items[0].url+'&time='+time+'" style="border:0;width:100%;height:100%;"></iframe>');
						}else {
							$('#video-view').html('<div class="nodeta"><span>没有可观看的视频资源</span></div>')
						}
					}


					//function loadedHandler(){
					//	console.log("进入load方法了!<br/>")
					//	if(CKobject.getObjectById('ckplayer_a1').getType()){
					//		CKobject.getObjectById('ckplayer_a1').addListener('time',timeHandler);
					//	}
					//	else{
					//		CKobject.getObjectById('ckplayer_a1').addListener('time','timeHandler');
					//	}
					//}
					//function timeHandler(t){
					//	if(t>-1){
					//		CKobject._K_('nowTime').innerHTML='当前播放的时间点是(此值精确到小数点后三位，即毫秒)：'+t;
					//
					//		var updateProgressDB = updateProgress( BATHPATH, loginName, reviewId, treeId, t);
					//		if(!updateProgressDB.success) {
					//			layer.alert( updateProgressDB.errorMessage ||"服务器异常!" );
					//		};
					//	}
					//}
					//var flashvars={
					//	f: 'http://movie.ks.js.cn/flv/other/2014/06/20-2.flv',
					//	c:1,
					//	p:2,
					//	b:0,
					//	loaded:'loadedHandler'
					//};
					//CKobject.embed('../assets/videoplayer/ckplayer.swf','a1','ckplayer_a1','100%','100%',false,flashvars);
				};


				/**
				 * // 触发视频观看时间接口： 当窗口关闭时，当切换树时，刷新页面时，视频正常结束时
				 */
				this.getVideoTime = function () {
					/**
					 *
					 * 设置当iframe加载完成后执行对应的操作
					 */

					//setTimeout(function(){
					if ( $(window.frames["viewVideo"]) != undefined ) {
						if ($(window.frames["viewVideo"].document) != undefined) {
							watchedDuration = parseInt($(window.frames["viewVideo"].document).find("#nowTime").html());
							if (watchedDuration > 0 || watchedDuration != undefined) {
								// 获取iframe 的当前播放时间

								console.log('当前播放时间是：', watchedDuration);

								var updateProgressDB = updateProgress( BATHPATH, loginName, reviewId, treeId, watchedDuration);
								if (!updateProgressDB.success) {
									//layer.alert( updateProgressDB.errorMessage ||"服务器异常!" );
									return false;
								}
								;
							}
						}
					}


					//},3000);
				};

			};

		};

		//获取学生信息
		var getUser = function() {
			return common.requestService('../../../../online/user/isAlive','get');
		};

		//获取预习树节点
		var getStartTree = function(thirdUrl,loginName, courseId) {
			return common.requestService('../../../../api/callThirdPost','post', {
				thirdUrl: thirdUrl + '/bxg_stu/preview/previewStart',
				loginName: loginName,
				courseId: courseId
			});
		};


		// 查询课程资源
		var findCourseResource = function(thirdUrl,treeId, type, apply, pageNumber, pageSize) {
			return common.requestService('../../../../api/callThirdPost','post', {
				thirdUrl: thirdUrl + '/bxg_anon/courseResource/findPage',
				treeId: treeId,
				type: type,
				apply: apply,
				pageNumber: pageNumber,
				pageSize: pageSize
			});
		};


		// 更新进度------预习
		var updateProgress = function(thirdUrl,loginName, courseId, pointId, watchedDuration) {
			return common.requestService('../../../../api/callThirdPost','post', {
				thirdUrl: thirdUrl + '/bxg_stu/preview/updatePreviewStudentPoint',
				loginName: loginName,
				courseId: courseId,
				pointId: pointId,
				watchedDuration: watchedDuration
			});
		};


		//  获取某个知识点视频的已看时长 -- 预习
		var findPointLastWatchedDuration = function(thirdUrl,loginName, courseId, pointId) {
			return common.requestService('../../../../api/callThirdPost','post', {
				thirdUrl: thirdUrl + '/bxg_stu/preview/findPointLastWatchedDuration',
				loginName: loginName,
				courseId: courseId,
				pointId: pointId
			});
		};

		return {
			createPage: createPage
		}
	});