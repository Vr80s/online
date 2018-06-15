<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
	@keyframes myfirst{
				    0%{color: #d26f6f;}
				    25%{color: #47b33b;}
				    50%{color: #2d3638;}
				    75%{color: #8853da;}
					100%{color: #479cab;}
		}
		h1{
			-webkit-animation: myfirst 10s infinite 1s forwards;
			font-size: 50px;
		}
		.wrap-content{
			width: 1200px;
			margin: 100px auto 0;


		}
		.z{float: left;}
		.y{float: right;}
		.yaocai{
			width: 240px;
			height: 382px;
			margin-top: 64px;
		}
		.yaocai img{
			width: 100%;
			height: 100%;
		}
		.main{

			width: 850px;
		}
		h2{
			font-size: 45px;
   		 padding-bottom: 69px;
		}
		h2,h3,h4{
			text-align: center;
			margin: 0 0 20px;
		}
		.my_js .top_text{
			line-height: 35px;
			font-size: 15px;
		}
		.my_gs .title_left{
			padding-left: 20px;
			border-left: 3px solid #00bc12;
			font-size: 18px;
			color: #00bc12;
		}
		.my_gs .history{
			    line-height: 35px;
    font-size: 15px;
		}
</style>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
	}
</script>
<div class="wrap-content">
		<h2>欢迎访问熊猫中医直播教育管理系统!</h2>	
	<!--左边图片-->
	<div class="yaocai z">
		<img src="../../images/welcome/扁鹊.jpg"/ alt="药材">
	</div>
	<!--右边内容-->
	<div class="main y">

		<h3>中国古代十大名医之</h3><h4>扁鹊</h4>	
		<div class="my_js">
			<p class="top_text">
			战国时医学家，其真实姓名是秦越人，又号卢医。据人考证，约生于周威烈王十九年（公元前四O七年），
			卒于赧王五年（公元前三一O年）。善用“针石”、“服汤”、“熨”等治病，所著《扁鹊内经》、《外经》早佚。
			他为什么被称为“扁鹊”呢？这是他的绰号。扁鹊 [1]  善于运用四诊，尤其是脉诊和望诊来诊断疾病。
			《史记·扁鹊仓公列传》中记述了与他有关的两个医案：一个是用脉诊的方法诊断赵子简的病，
			一个是用望诊的方法诊断齐桓侯的病。因此被称为“脉学之宗”。
			</p>
		</div>
		<div class="my_gs">
		    <span class="title_left">扁鹊换心</span>
			<p class="history">
				鲁公扈、赵齐婴二人有轻病，就一起请扁鹊治病，扁鹊对公扈说：“你的志气强身体却很弱，有计谋却并不果断，齐婴你的志气弱身
			体却很好，没有谋虑却过于执著。如果把你们的心脏互换，就能平衡病也就好了。”扁鹊让二人喝了药酒，他们昏死了很多天，剖开他们前
			胸找到了心脏，将它们互换放置好，然后给他们吃了神药，于是二人过了一会便醒了，就像刚开始一样的健康，后来二人就向扁鹊告辞回家了。
			</p>
			<span class="title_left">起死回生</span>
			<p class="history">
				一次扁鹊到了虢国，听说虢国太子暴亡不足半日，还没有装殓。于是他赶到宫门告诉中庶子，称自己能够让太子复活。中庶子认为他所说是无稽之
			谈，人死哪有复生的道理。扁鹊长叹说：“如果不相信我的话，可试着诊视太子，应该能够听到他耳鸣，看见他的鼻子肿了，并且大腿及至阴部还有
			温热之感。”中庶子闻言赶快入宫禀报，虢君大惊，亲自出来迎接扁鹊。
			扁鹊说：“太子所得的病，就是所谓的‘尸厥’。人接受天地之间的阴阳二气，阳主上主表，阴主下主里，阴阳和合，身体健康；现在太子阴阳二气失
			调，内外不通，上下不通，导致太子气脉纷乱，面色全无，失去知觉，形静如死，其实并没有死。”
			扁鹊命弟子协助用针砭进行急救，刺太子三阳五会诸穴。不久太子果然醒了过来。扁鹊又将方剂加减，使太子坐了起来。又用汤剂调理阴阳，二十多天，太
			子的病就痊愈了。这件事传出后，人们都说扁鹊有起死回生的绝技。
			</p>
		</div>
	</div>
	
</div>


