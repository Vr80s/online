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
			width: 100%;
			height: 500px;

			text-align: center;
			padding-top: 200px;
			position: relative;
		}
		.yaocai{
		    width: 110px;
			height: 116px;
			position: absolute;
			left: 50%;
			margin-left: 417px;
			top: 50%;
			margin-top: -37px;
		}
		.yaocai img{
			width: 100%;
			height: 100%；
		}
		.yaocai2{
	        width: 291px;
		    height: 396px;
		    position: absolute;
		    left: 50%;
		    margin-left: -670px;
		    top: 50%;
		    margin-top: -110px;
		}
		.yaocai2 img{
			width: 100%;
			height: 100%；
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
			<h1>欢迎访问熊猫中医直播教育管理系统!</h1>	
			<div class="yaocai">
				<img src="../../images/yaocai.png"/ alt="药材">
			</div>
			<div class="yaocai2">
				<img src="../../images/personal02.png"/>
			</div>
			
		</div>


