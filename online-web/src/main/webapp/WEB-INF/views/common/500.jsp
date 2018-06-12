<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<!DOCTYPE html>
<html>
<head>
	<title>迷路了</title>
	<style>
		/*--
        Author: W3layouts
        Author URL: http://w3layouts.com
        License: Creative Commons Attribution 3.0 Unported
        License URL: http://creativecommons.org/licenses/by/3.0/
    --*/

		/*-- Reset-Code --*/
		html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,dl,dt,dd,ol,nav ul,nav li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font-size:100%;font:inherit;vertical-align:baseline;}
		article, aside, details, figcaption, figure,footer, header, hgroup, menu, nav, section {display: block;}
		ol,ul{list-style:none;margin:0px;padding:0px;}
		blockquote,q{quotes:none;}
		blockquote:before,blockquote:after,q:before,q:after{content:'';content:none;}
		table{border-collapse:collapse;border-spacing:0;}
		/* start editing from here */
		a{text-decoration:none;}
		.txt-rt{text-align:right;}/* text align right */
		.txt-lt{text-align:left;}/* text align left */
		.txt-center{text-align:center;}/* text align center */
		.float-rt{float:right;}/* float right */
		.float-lt{float:left;}/* float left */
		.clear{clear:both;}/* clear float */
		.pos-relative{position:relative;}/* Position Relative */
		.pos-absolute{position:absolute;}/* Position Absolute */
		.vertical-base{vertical-align:baseline;}/* vertical align baseline */
		.vertical-top{vertical-align:top;}/* vertical align top */
		nav.vertical ul li{display:block;}/* vertical menu */
		nav.horizontal ul li{display: inline-block;}/* horizontal menu */
		img{max-width:100%;}
		/*-- //Reset-Code --*/

		body {
			font-family: 'Josefin Sans', sans-serif;
			background: #2b82ad;
			padding: 60px 80px 20px;
		}
		.clear{
			clear:both;
		}
		h1 {
			font-size: 70px;
			text-align: left;
			margin-bottom: 20px;
			margin-top: 0px;
			color: #ffffff;
			font-weight: 700;
		}
		p {
			font-weight: 600;
			font-size: 20px;
			text-align: left;
			color: #ffffff;
			line-height: 32px;
			padding-top: 10px;
		}
		.agile {
			width: 80%;
			margin: 0 auto;
		}
		.image {
			width: 30%;
			float: left;
			margin-top:40px;
		}
		.image img{
			width:100%;
		}
		.text {
			width: 64%;
			float: left;
			padding: 5em 0em 3em 0em;
		}
		.text a{
			color:#000;
			text-decoration:undrline;
		}
		.text a:hover{
			color:#003b64;
			text-decoration:underline;
		}
		.agileits_main_grid_left h1 {
			font-size: 2.5em;
			color: #000;
		}
		.agileits_main_grid_right {
			float: right;
			margin: 1.2em 0 0;
		}
		.agileits_main_grid_left {
			float: left;
		}
		.footer {
			padding: 1em 1em;
		}
		.footer p {
			font-size: 17px;
			color:#ffffff;
			letter-spacing: 1px;
			text-align: center;
			margin: 20px 0;
			padding: 0;
		}
		.footer a{
			color:#000000;
			text-decoration:none;
		}
		.footer a:hover{
			text-decoration:underline;
			color:#00337f;
		}
		.menu {
			display: inline-block;
			position: relative;
		}
		.menu a {
			display: block;
		}
		span.menu-icon,span.menu-icon1 {
			display: block;
			cursor: pointer;
			margin: 0 0 0.5em 0;
			position: relative;
		}

		ul.nav1,ul.nav {
			z-index: 999;
			width: 102px;
			background-color: #FFF;
			padding: 0.5em 1em;
			position: absolute;
			right:0;
		}
		ul.nav1{
			display: none;
		}
		ul.nav1 li ,ul.nav li {
			display: block;
			margin: 0.5em 0;
		}
		ul.nav1 li a,ul.nav li a{
			font-size: 1em;
			color: #31386F;
			font-weight: 800;
		}
		ul.nav1 li a:hover,ul.nav li a:hover{
			color: #FF7FE5;
		}
		.social-icons {
			float: right;
		}
		.social-icons ul li {
			display: inline-block;
			margin: 0 5px;
			vertical-align: middle;
			color:#fff;
		}
		.social-icons ul li a:hover{
			opacity:0.5;
		}
		.back {
			float: left;
		}
		.wthree {
			background: #000;
			padding: 1em;
		}
		.back a {
			display: block;
			font-size: 1.2em;
			color: #fff;
			text-decoration: none;
			padding-top: 6px;
		}
		.w3l {
			margin: 4em 0;
		}
		/*---- responsive-design -----*/
		@media(max-width:1336px){
			h1 {
				font-size: 66px;
			}
			p {
				font-size: 18px;
			}
		}
		@media(max-width:1280px){
			h1 {
				font-size: 62px;
			}
		}
		@media(max-width:1024px){
			.agile {
				width: 90%;
			}
			body {
				padding: 53px 53px 20px;
			}
			h1 {
				font-size: 56px;
			}
			.w3l {
				margin: 1em 0;
			}
		}
		@media(max-width:800px){
			.agile {
				width: 100%;
			}
			.text {
				width: 70%;
				padding: 3em 0em 3em 0em;
			}
			h1 {
				font-size: 52px;
			}
			p {
				font-size: 16px;
			}
			.footer {
				padding: 1em 0em;
			}
		}
		@media(max-width:768px){

		}
		@media(max-width:736px){
			.footer p{
				font-size:16px;

			}
			h1 {
				font-size: 48px;
			}
			.agileits_main_grid_left h1 {
				font-size: 2em;
			}
			.agileits_main_grid_right {
				margin: 0.8em 0 0;
			}
		}
		@media(max-width:667px){
			.footer p{
				font-size:15px;
			}
			h1 {
				font-size: 43px;
			}
			.text {
				padding: 3em 0em 1em 0em;
			}
		}
		@media(max-width:640px){
			h1 {
				font-size: 40px;
			}
			p {
				line-height: 26px;
			}
			.wthree {
				background: #000;
				padding: 0.8em;
			}
		}
		@media(max-width:600px){
			h1 {
				font-size: 37px;
			}
			.footer p{
				font-size:15px;
			}
		}
		@media(max-width:568px){
			p{
				font-size:16px;
			}
			.footer p{
				font-size:14px;
			}
			h1 {
				font-size: 34px;
			}
			.back a {
				font-size: 1em;
				padding-top: 8px;
			}
		}
		@media(max-width:480px){
			body {
				padding: 30px;
			}
			h1 {
				font-size: 32px;
			}
			.text {
				padding: 3em 0em 0em 0em;
			}
		}
		@media(max-width:414px){
			.text{
				width:100%;
				float: none;
			}
			.footer p{
				font-size:14px;
			}
			.text {
				padding: 2em 0em 0em 0em;
			}
			body {
				padding: 24px;
			}
			.image {
				width: 60%;
				float: none;
				margin: 16px auto;
			}
			.footer {
				padding: 0em 0em;
			}
		}
		@media(max-width:384px){
			.footer p{
				font-size:14px;
				margin: 0px 0 10px;
			}
			.social-icons ul li {
				margin: 0 0px;
			}
			.wthree {
				padding: 0.5em;
			}
		}
		@media(max-width:375px){

			.footer p{
				font-size:14px;
			}
		}
		@media(max-width:320px){
			body {
				padding: 16px 16px;
			}
			.agileits_main_grid_left h1 {
				font-size: 1.8em;
			}
			h1 {
				font-size: 30px;
				margin-bottom:0;
			}
			p {
				font-size: 14px;
				line-height:24px;
			}
			.footer p {
				font-size: 14px;
				padding-top: 0px;
			}
			.back {
				float: none;
				width: 100%;
				text-align: center;
			}
			.social-icons {
				float: none;
				width: 100%;
				text-align: center;
				margin-top: 12px;
			}
			.back a {
				padding-top: 0px;
			}
			.wthree {
				padding: 0.8em;
			}
			.text {
				padding: 1em 0em 0em 0em;
			}
		}
	</style>

	<!-- For-Mobile-Apps-and-Meta-Tags -->
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="Flat Error Page Widget Responsive, Login Form Web Template, Flat Pricing Tables, Flat Drop-Downs, Sign-Up Web Templates, Flat Web Templates, Login Sign-up Responsive Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design" />
	<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
	<!-- //For-Mobile-Apps-and-Meta-Tags -->
</head>
<body>
<div class="main">
	<div class="agile">
		<div class="agileits_main_grid">
			<div class="agileits_main_grid_left">
				<h1>熊猫中医</h1>
			</div>

			<div class="clear"> </div>
		</div>
		<div class="w3l">
			<div class="text">
				<h1>页面找不到</h1>

				<p>你所访问的资源没找到或者我们服务被外星劫持啦！点击 <a href="/">主页</a> ，先出去逛逛</p>
			</div>
			<div class="image">
				<img src="/web/images/smile404.png">
			</div>
			<div class="clear"></div>
		</div>

	</div>
</div>
</body>
</html>