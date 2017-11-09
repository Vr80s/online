/**
 *   首页
 */
define(['template',
		'jquery',
		'text!tplUrl/home/home.html',
		'ckplayer'
	],
	function (template,$,
	          homeTpl ) {
		function createPage() {
			document.title = "熊猫中医·院校-教师端考试中心-组织试卷";


			$("#app").html(template.compile( homeTpl)({

			}));
			$("#ti").html('<div id="nowTime">当前播放的时间点是(此值精确到小数点后三位，即毫秒)：0</div>');

			function loadedHandler(){
				if(CKobject.getObjectById('ckplayer_a1').getType()){
					CKobject.getObjectById('ckplayer_a1').addListener('time',timeHandler);
				}
				else{
					CKobject.getObjectById('ckplayer_a1').addListener('time','timeHandler');
				}
			}
			function timeHandler(t){
				if(t>-1){
					CKobject._K_('nowTime').innerHTML='当前播放的时间点是(此值精确到小数点后三位，即毫秒)：'+t;
				}
			}
			var flashvars={
				f:'http://movie.ks.js.cn/flv/other/2014/06/20-2.flv',
				c:0,
				p:2,
				b:0,
				loaded:'loadedHandler'
			};
			CKobject.embed('../assets/videoplayer/ckplayer.swf','a1','ckplayer_a1','100%','100%',false,flashvars);





		};



		return {
			createPage: createPage
		}
	});