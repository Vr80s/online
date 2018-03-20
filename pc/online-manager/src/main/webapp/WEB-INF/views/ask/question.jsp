<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	
<style type="text/css">
   .select-type {
       background: white;
       width: 117px;
       height: 32px;
       float: left;
       text-indent: 11px;
       font-size: 15px;
       line-height: 32px;
       cursor: pointer;
       position: absolute;
       border: 1px solid #e4e4e4;
       left:0;
       top:28px;
   }
    .select-type .menu-title {
       width: 85px;
       white-space: nowrap;
       overflow: hidden;
       text-overflow: ellipsis;
       float: left;
       font-size: 14px;
       color: #333;
   }
   .menuItem{
   		white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
   }
   .select-type .menu-title1 {
       float: right;
       width: 10px;
       height: 5px;
       background: url("/images/cy-23.png") no-repeat;
       background-position: 0 0;
       margin-top: 15px;
       margin-left: 10px;
       margin-right: 10px;
   }
  .select-type .select-type-ul {
       z-index: 3;
       display: none;
       margin: 0;
       margin-left: 1px;
       padding: 0;
       width: 117px;
       position: absolute;
       list-style: none;
       text-align: left;
       background-color: #ffffff;
       border-top: 1px solid #e5e5e5;
       top: 26px;
       left: -2px;
       border-left: 1px solid #e5e5e5;
       font-size: 14px;
   }
    .select-type .select-type-ul li {
       height: 36px;
       /*border: 1px solid #FFFFFF;*/
       border-left: 0px solid #e5e5e5;
       border-bottom: 1px solid #e5e5e5;
       line-height: 34px;
       border-right: 1px solid #e5e5e5;
       position: relative;
       left: 0;
       font-size: 12px;
       background: url("/images/cy-33.png") no-repeat;
       background-position: 99px 12px;
   }
    .select-type .select-type-ul li .select-type-ul-dv {
       padding-bottom: 25px;
       /*min-height: 100px;*/
       box-shadow: 1px 1px 6px rgba(0, 0, 0, 0.13);
       display: none;
       position: absolute;
       width: 345px;
       top: 0;
       left: 115px;
       background-color: #ffffff;
       z-index: 3;
       cursor: default;
   }
    .select-type .select-type-ul li .select-type-ul-dv .select-type-ul-dv-d {
       width: 315px;
       margin: 0 auto;
       text-indent: 0;
       color: #333;
       border-bottom: 1px solid #e5e5e5;
       cursor: default;
   }
    .select-type .select-type-ul li .select-type-ul-dv .select-type-ul-dv-p {
       width: 330px;
       margin: 0 auto;
   }
    .select-type .select-type-ul li .select-type-ul-dv .select-type-ul-dv-p a {
       position: relative;
       float: left;
       color: #333;
       font-size: 12px;
       height: 14px;
       line-height: 14px;
       margin-top: 15px;
       padding-right: 10px;
       padding-left: 10px;
       text-indent: 0;
       white-space:pre;
   }
    .select-type .select-type-ul li .select-type-ul-dv .select-type-ul-dv-p a:after {
       content: "|";
       position: absolute;
       right: 0px;
       top: 0px;
       color: #eeeeee;
   }
    .select-type .select-type-ul li .select-type-ul-dv .select-type-ul-dv-p a:hover {
       color: #2CB82C;
       text-decoration: none;
   }
    .select-type .select-type-ul li:hover {
       color: #2cb82c;
       /* border-right: 1px solid #ffffff; */
       background-position: 143px 37px;
   }
    .select-type .select-type-ul li:hover .select-type-ul-dv {
       display: block;
   }
    .select-type:hover .menu-title1 {
       -moz-transform: rotate(180deg);
       -webkit-transform: rotate(180deg);
       transform: rotate(180deg);
   }
    .select-type:hover .select-type-ul {
       display: block;
       margin-top: 4px;
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
  var mname = "${md5UserName}";
  var weburl = '${weburl}';
</script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：问道管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 问答管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone" style="position:relative;margin-top: -26px;">
	<p class="col-xs-4" style="padding: 0;">
			<div class="select-type">
		        <span class="menu-title">全部分类</span>
		        <span class="menu-title1"></span>
		        <ul class="select-type-ul">
		        </ul>
		    </div>
		&nbsp;
		<input class="btn btn-sm  btn-primary" type="button" value="回答数↓" onclick="search(1);" style="position:absolute;left:130px;top:26px" title="回答数从高到底排列">&nbsp;
		<input class="btn btn-sm  btn-primary" type="button" value="浏览数↓" onclick="search(2);" style="position:absolute;left:215px;top:26px" title="浏览数从高到底排列">
		<input type="hidden"   id="answerSum" class="propertyValue1"/>
        <input type="hidden" value="answerSum" class="propertyName"/>
        <input type="hidden"   id="browseSum" class="propertyValue1"/>
        <input type="hidden" value="browseSum" class="propertyName"/>
	</p>
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="status" class="propertyValue1" >
                                <option  value="" >问题状态</option>
                                <option  value="1" >未解决</option>
                                <option  value="2" >已解决</option>
                            </select>
                        </div>
                        <input type="hidden" value="status" class="propertyName"/>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="title" class="propertyValue1"  placeholder = "标题关键字"/>
                            <input type="hidden" value="title" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="name" class="propertyValue1"  placeholder = "用户名"/>
                            <input type="hidden" value="name" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="userForm" class="propertyValue1" >
                                <option  value="" >用户来源</option>
                                <option  value="online" >熊猫中医</option>
                                <option  value="bxg_2" >院校老师</option>
                                <option  value="bxg_1" >院校学生</option>
                                <option  value="dual_2" >双元老师</option>
                                <option  value="dual_1" >双元学生</option>
                            </select>
                            <input type="hidden" value="userForm" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker"  id="startTime"  placeholder = "开始日期" />
                            <input type="hidden" value="startTime" class="propertyValue1"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            &nbsp;至 &nbsp;
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker"  id="stopTime"  placeholder = "结束日期"/>
                            <input type="hidden" value="stopTime" class="propertyValue1"/>
                        </div>
                    </td>
                    <td>
                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
                                onclick="search();">
                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>


	<div class="row">
		<div class="col-xs-12">
			<table id="questionTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="/js/ask/question.js?v=ipandatcm_1.3"></script>