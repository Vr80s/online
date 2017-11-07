<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>熊猫中医</title>
<style>
@charset "UTF-8";

@
keyframes arrowAnimotion { 0% {
	transform: translateY(0px);
}

25%
{
transform


:

 

translateY


(50
px
);


            

}
30%
{
transform


:

 

translateY


(40
px
);


            

}
55%
{
transform


:

 

translateY


(50
px
);


            

}
60%
{
transform


:

 

translateY


(40
px
);


            

}
85%
{
transform


:

 

translateY


(50
px
);


            

}
90%
{
transform


:

 

translateY


(40
px
);


            

}
100%
{
transform


:

 

translateY


(0
px
);


            

}
}
@
-moz-keyframes arrowAnimotion {from { transform:translateY(0px);
	
}

to {
	transform: translateY(50px);
}

}
@
-webkit-keyframes arrowAnimotion { 0% {
	transform: translateY(0px);
}

50%
{
transform


:

 

translateY


(30
px
);


            

}
100%
{
transform


:

 

translateY


(0
px
);


            

}
}
@
-o-keyframes arrowAnimotion {from { transform:translateY(0px);
	
}

to {
	transform: translateY(50px);
}

}
@
-ms-keyframes arrowAnimotion {from { transform:translateY(0px);
	
}

to {
	transform: translateY(50px);
}

}
/*titleChange动画*/
@
keyframes titleChange { 0% {
	transform: scale(0, 0);
	opacity: 0;
}

50%
{
transform


:

 

scale


(0
.5
,
0
.5


);
opacity


:

 

0
.5


;
}
100%
{
transform


:

 

scale


(1
,
1);
opacity


:

 

1;
}
}
@
-moz-keyframes titleChange { 0% {
	transform: scale(0, 0);
	opacity: 0;
}

50%
{
transform


:

 

scale


(0
.5
,
0
.5


);
opacity


:

 

0
.5


;
}
100%
{
transform


:

 

scale


(1
,
1);
opacity


:

 

1;
}
}
@
-webkit-keyframes titleChange { 0% {
	transform: scale(0, 0);
	opacity: 0;
}

50%
{
transform


:

 

scale


(0
.5
,
0
.5


);
opacity


:

 

0
.5


;
}
100%
{
transform


:

 

scale


(1
,
1);
opacity


:

 

1;
}
}
@
-o-keyframes titleChange { 0% {
	transform: scale(0, 0);
	opacity: 0;
}

50%
{
transform


:

 

scale


(0
.5
,
0
.5


);
opacity


:

 

0
.5


;
}
100%
{
transform


:

 

scale


(1
,
1);
opacity


:

 

1;
}
}
@
-ms-keyframes titleChange { 0% {
	transform: scale(0, 0);
	opacity: 0;
}

50%
{
transform


:

 

scale


(0
.5
,
0
.5


);
opacity


:

 

0
.5


;
}
100%
{
transform


:

 

scale


(1
,
1);
opacity


:

 

1;
}
}
/*wordsChange动画*/
@
keyframes wordsChange { 0% {
	transform: translateY(3400px);
}

100%
{
transform


:

 

translateY


(0
px
);


            

}
}
@
-moz-keyframes wordsChange { 0% {
	-moz-transform: translateY(3400px);
}

100%
{
-moz-transform


:

 

translateY


(0
px
);


            

}
}
@
-webkit-keyframes wordsChange { 0% {
	-webkit-transform: translateY(3400px);
}

100%
{
-webkit-transform


:

 

translateY


(0
px
);


            

}
}
@
-o-keyframes wordsChange { 0% {
	-o-transform: translateY(3400px);
}

100%
{
-o-transform


:

 

translateY


(0
px
);


            

}
}
@
-ms-keyframes wordsChange { 0% {
	-ms-transform: translateY(3400px);
}

100%
{
-ms-transform


:

 

translateY


(0
px
);


            

}
}
body html {
	margin: 0;
	padding: 0;
	font-family: "Microsoft Yahei";
	font-size: 18px;
}

.page-email {
	width: 550px;
	height: 550px;
	border: 1px solid #EDECEC;
	margin: 0px auto;
	-moz-border-radius: 3px;
	/* Firefox */
	-webkit-border-radius: 3px;
	/* Safari 和 Chrome */
	border-radius: 3px;
	/* Opera 10.5+, 以及使用了IE-CSS3的IE浏览器 */
	padding: 25px;
}

.page-email .header {
	border-bottom: 1px solid #EDECEC;
	margin-top: 5px;
}

.page-email .header p {
	color: #2cb82c;
	width: 100%;
	text-align: center;
	margin: 0px;
}

.page-email .header .p1 {
	font-size: 28px;
}

.page-email .header .p2 {
	font-size: 14px;
	margin-bottom: 30px;
}

.page-email .article .text {
	width: 100%;
	text-align: left;
	color: #333;
	font-size: 14px;
	padding: 30px 0px;
	font-weight: 600;
	margin-bottom: 30px;
}

.page-email .article .link {
	line-height: 25px;
	font-size: 14px;
	padding-bottom: 30px;
	margin: 0;
}

.page-email .article .link .tip {
	display: block;
	font-size: 14px;
	color: #666;
}

.page-email .article .link a {
	color: #55b9e6;
	font-size: 14px;
	text-decoration: none;
}

.page-email .article .footer {
	font-size: 12px;
	color: #999;
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="page-email">
		<div class="header">
			<p class="p1">熊猫中医</p>
			<p class="p2">ixincheng.com</p>
		</div>
		<div class="article">
			<p class="text">${msg}</p>
			<p class="link">
				千万小伙伴想成为你的同学，<a href="/">返回主页</a>
			</p>
		</div>
	</div>
</body>
</html>