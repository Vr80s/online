<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	

<style type="text/css">
    .vertical-tab {
        width: 8%;
        height: 100%;
        float: left;
        /* overflow: hidden; */
    }
 
    .vertical-tab > li {
        text-align: center;
    }
    
/*  	.vertical-tab > li > a {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff; 
         border-right: 1px solid #ffffff; 
        border-right: 1px solid #ccc;
        z-index: 2;
    } */
    
    .vertical-tab > li.active > a, .vertical-tab > li.active > a:focus, .vertical-tab > li.active > a:hover {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff;
        border-right: 1px solid #ffffff;
        z-index: 2;
    }
 
    .vertical-tab > li > a {
         /* border-radius: 4px 4px 4px 4px; */
         border-radius: 4px 0px 0px 4px; 
    }
 
    .vertical-tab-content {
        float: left;
        width: 90%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 0px 4px 4px 4px;
        border: solid 1px #ccc;
        color: #666;
    }
 
    .send {
        position: relative;
        width: 90%;
        background: #FFFFFF;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px; /* 圆角 */
        border: 1px solid #ccc;
    }
 
    .tag {
        width: 300px;
        min-height: 30px;
        border: 1px solid #ccc;
        position: relative;
        padding: 10px;
        background-color: #ccc;
        border-radius: 4px;
        margin-left: 30px;
        -moz-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        -webkit-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
    }
 
    .tag:before, .tag:after {
        position:absolute;
        content:"\00a0";
        width:0px;
        height:0px;
        border-width:8px 18px 8px 0;
        border-style:solid;
        border-color:transparent #CCC transparent transparent;
        top:5px;
        left:-18px;
 
    }
 
    .tag:after {
        bottom: -33px;
        border-color: #FFF transparent transparent;
    }
 
    .clearfix:after{
     content:"";
     height:0;
     visibility:hidden;
     display:block;
     clear:both;
    }
 
</style>

    <style>
        .caret {
            display: inline-block;
            width: 0;
            height: 0;
            margin-left: 2px;
            vertical-align: middle;
            border-top: 4px dashed;
            border-top: 4px solid \9;
            border-right: 4px solid transparent;
            border-left: 4px solid transparent;
            -webkit-transform: rotate(0deg);
            -moz-transform: rotate(0deg);
            -ms-transform: rotate(0deg);
            -o-transform: rotate(0deg);
            transform: rotate(0deg);
        }
        .rotateCaret{
            display: inline-block;
            width: 0;
            height: 0;
           /*  margin-left: 2px; */
            vertical-align: middle;
            border-top: 4px dashed;
            border-top: 4px solid \9;
            border-right: 4px solid transparent;
            border-left: 4px solid transparent;
            -webkit-transform: rotate(-90deg);
            -moz-transform: rotate(-90deg);
            -ms-transform: rotate(-90deg);
            -o-transform: rotate(-90deg);
            transform: rotate(-90deg);
        }
        .clearfix:after {
            content: '';
            height: 0;
            visibility: hidden;
            display: block;
            clear: both;
        }
        .zhezhao {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: #666;
            opacity: 0.5;
            z-index: 99999;
        }
        #fenpeiTeacher{
            width:520px;
            font-size:12px;
            margin:0 auto;
            border:1px solid #ddd;
            background-color:#fff;
            position:fixed;
            z-index:999999;
            left:50%;
            top:50%;
            transform: translate(-50%,-50%);
        }
        .fenpeiTearchTop{
            width:100%;
            border-bottom:1px solid #ddd;
        }
        .title{
            display:inline-block;
            font-size:16px;
            color:#333;
            font-weight:bold;
            line-height:35px;
            padding-left:10px;
        }
        .fenpeiTearcherClose{
            float:right;
            margin-top:10px;
            padding-right:10px;
            cursor:pointer;
        }
        .fenpeiTeacherAbove{
            padding:15px 30px;
        }
        .fengpeiTeacher-content{

        }
        .fengpeiTeacher-content ul{
            list-style:none;
        }
        .fengpeiSearch{
            position:relative;
            width:242px;
        }
        .fengpeiSearch input{
            width:100%;
            height:28px;
            line-height:20px;
        }
        .fengpeiSearch img{
            position:absolute;
            right:8px;
            top:4px;
        }
        .fenpeiList{
            margin-top:10px;
            width:242px;
            height:395px;
            border:1px solid #ddd;
            padding:10px;
            float:left;
            overflow-y:auto;
        }
        .fenpeiList .xuekename{
            display:inline-block;
            vertical-align: top;
            margin:5px 0;
        }
       .fenpeiList .xuekeleibei{
            display:inline-block;
            margin:0;
            padding:0;
            width:150px;
        }
        .fenpeiList .xuekeleibei span{
            cursor:pointer;
        }
        .list-items2 li{
            cursor:pointer;
        }
        .row li{
            cursor:pointer;
        }
        .fenpeiTeacherImg{
            cursor:pointer;
        }
        .allTeacher span,.allTeacher img{
            cursor:pointer;
        }
        .child-parrent-title .clear{
            cursor:pointer;
        }
        .fenpeiTeacherBtn span{
            cursor:pointer;
        }
        .fenpeiList .xuekeleibei li{
            margin:5px 0;
        }
        .fenpeiList .xuekeleibei .tagname{
            /* margin-left:5px; */
        }
        .fenpeiList .xuekeleibei li::before{

        }
        .fenpeirow{
            float:left;
            margin:0;
            padding:0;
            width:50px;
            margin-left:5px;
        }
       .fenpeirow li{
            width:30px;
            height:30px;
            background-color:#f8f8f8;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            text-align:center;
            line-height:30px;
            margin:80px 10px;
            cursor:pointer;
        }
        .child-parrent{
            width:155px;
            float:right;
            margin-top:10px;
        }
        .child-parrent>div{
            width:100%;
            height:125px;
            border:1px solid #ddd;
            margin-bottom:10px;
        }
        .child-parrent .child-parrent-title{
            height:25px;
            padding:0 10px;
            border-bottom:1px solid #ddd;
        }
        .child-parrent .child-parrent-title span{
            margin-top:5px;
        }
        .child-parrent .child-parrent-title span:nth-child(1){
            float:left;
        }
        .child-parrent .child-parrent-title span:nth-child(2){
            float:right;
        }
        .child-parrent .allTeacher{
            padding:5px 10px;
            height:88px;
            overflow-y:auto;
        }
        .fenpeiTeacherBtn{
            width:200px;
            margin:20px auto;
        }
        .fenpeiTeacherBtn .anniu{
            display:inline-block;
            width:58px;
            height:27px;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            text-align:center;
            line-height:27px;
            background-color:#428bca;
            color:#fff;
            font-size:14px;
        }
        .fenpeiTeacherBtn span{
            margin-left:30px;
        }
     
        .select{
            color:#fff;
            background-color:#428bca;
        }
        .allTeacher span{
            display:inline-block;
            width:80px;
        }
        .allTeacher img{
            vertical-align: bottom;
            display:none;
        }
        .allTeacher div:hover img{
            display:inline-block;
        }
        .list-items1{
            display:none;
        }
        .list-items2{
            display:none;
        }
        
      
       
	.vertical-tab {
        width: 8%;
        height: 100%;
        float: left;
        /* overflow: hidden; */
    }
 
    .vertical-tab > li {
        text-align: center;
    }
    
/*  	.vertical-tab > li > a {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff; 
         border-right: 1px solid #ffffff; 
        border-right: 1px solid #ccc;
        z-index: 2;
    } */
    
    .vertical-tab > li.active > a, .vertical-tab > li.active > a:focus, .vertical-tab > li.active > a:hover {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff;
        border-right: 1px solid #ffffff;
        z-index: 2;
    }
 
    .vertical-tab > li > a {
         /* border-radius: 4px 4px 4px 4px; */
         border-radius: 4px 0px 0px 4px; 
    }
 
    .vertical-tab-content {
        float: left;
        width: 90%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 0px 4px 4px 4px;
        border: solid 1px #ccc;
        color: #666;
    }
 
    .send {
        position: relative;
        width: 90%;
        background: #FFFFFF;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px; /* 圆角 */
        border: 1px solid #ccc;
    }
 
    .tag {
        width: 300px;
        min-height: 30px;
        border: 1px solid #ccc;
        position: relative;
        padding: 10px;
        background-color: #ccc;
        border-radius: 4px;
        margin-left: 30px;
        -moz-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        -webkit-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
    }
 
    .tag:before, .tag:after {
        position:absolute;
        content:"\00a0";
        width:0px;
        height:0px;
        border-width:8px 18px 8px 0;
        border-style:solid;
        border-color:transparent #CCC transparent transparent;
        top:5px;
        left:-18px;
 
    }
 
    .tag:after {
        bottom: -33px;
        border-color: #FFF transparent transparent;
    }
 
    .clearfix:after{
     content:"";
     height:0;
     visibility:hidden;
     display:block;
     clear:both;
    }
    
    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input.valid{
      width: 100%;
    }
    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input{
     width: 100%;
    }
    
    </style>
<script type="text/javascript">

	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
	
	$(function () {
        $('#vtab li').click(function () {
            tops = $(this).offset().top - $('#vtab').offset().top + $('#vtab').scrollTop() - $('#vtab').height() / 4;
            console.log(tops);
            $('#vtab').animate({
                scrollTop: tops
            }, 'slow');
        });
    });
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 线下课管理 </span>
</div>

<div style="height: 100%;" class="clearfix">
    <!-- Nav tabs -->
     <ul class="nav nav-tab vertical-tab" role="tablist" id="vtab">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" class="xxpxb_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">线下课管理</a>
        </li>
		<li role="presentation">
			<a href="#box_m" aria-controls="box_m" class="xxtj_bx" role="tab"
			   data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">线下课推荐管理</a>
		</li>
        <li role="presentation">
            <a href="#city_m" aria-controls="city_m" class="city_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">城市推荐管理</a>
        </li>
    </ul>
 
    <!-- Tab panes -->
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
        	<div class="mainrighttab tabresourse bordernone" id="courseDiv">
				<p class="col-xs-4" style="padding: 0;">
					<%--<button class="btn btn-sm btn-success add_P" title="新增">--%>
						<%--<i class="glyphicon glyphicon-plus"></i> 新增--%>
					<%--</button>--%>
					<button class="btn btn-sm btn-success dele_P" title="批量删除">
						<i class="glyphicon glyphicon-trash"></i> 批量删除
					</button>
					 <button class="btn btn-sm btn-success rec_P" title="设为推荐">
						<i class="glyphicon glyphicon-cog"></i> 设为推荐 
					</button>
					<!-- <button class="btn btn-sm btn-success rec_jp" title="设为精品推荐">
						<i class="glyphicon glyphicon-cog"></i> 设为精品推荐 
					</button> -->
				</p>
			
			<div class="searchDivClass" id="searchDiv">
			        <div class="profile-info-row" >
			            <table frame=void >
			                <tr>
			                	<td>
			                		<div class="profile-info-value searchTr">
										<select name="search_city" id="search_city" value="" class="propertyValue1" >
											<option value="">所在城市</option>
						               		<c:forEach var="city" items="${cityVo}">
						                        <option value="${city.cityName}">${city.cityName}</option>
						                    </c:forEach>
										</select>
										<input type="hidden" value="search_city" class="propertyName"/>
									</div>
			                	</td>
			                	
			                	<!-- "tempMatchType":undefined,"propertyName":search_courseName,"propertyValue1":"年后中国","tempType":undefined},
			                	{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"} -->
			                	
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" placeholder = "培训班名称" class="propertyValue1" id="search_courseName" style="width: 150px;">
			                            <input type="hidden" value="search_courseName" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
			                                onclick="search_P();">
			                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
			                        </button>
			                    </td>
			                </tr>
			            </table>
			        </div>
			    </div>
				<div class="row">
					<div class="col-xs-12">
						<table id="courseTable"
							class="table table-striped table-bordered table-hover" style="width: 100%;">
						</table>
					</div>
				</div>
			</div>
        </div>
		<div role="tabpanel" class="tab-pane active" id="box_m">
			<div class="mainrighttab tabresourse bordernone" id="courseRecDiv" style="display:none">
				<div class="searchDivClass" id="search_P">
					<div class="profile-info-row" >
						<table frame=void >
							<tr>
								<td>
									<!-- <div class="profile-info-value searchTr">
										<select name="search_status" id="search_status_PX" value="" class="propertyValue1" >
											<option value="">课程状态</option>
											<option value="1">已启用</option>
											<option value="0">已禁用</option>
										</select>
										<input type="hidden" value="search_status" class="propertyName"/>
									</div> -->
									
									<div class="profile-info-value searchTr">
										<select name="search_city" id="search_rec" value="" class="propertyValue1" >
											<option value="">所在城市</option>
						               		<c:forEach var="city" items="${cityVo}">
						                        <option value="${city.cityName}">${city.cityName}</option>
						                    </c:forEach>
										</select>
										<input type="hidden" value="search_status" class="propertyName"/>
									</div>
								</td>
								<td>
									<button id="searchBtn_rec" type="button" class="btn btn-sm  btn-primary "
											onclick="search_rec();">
										<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
									</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<table id="courseRecTable"
							   class="table table-striped table-bordered table-hover">
							<colgroup>
								<col width='5%'></col>
								<col width='12%'></col>
								<col width='10%'></col>
								<col width='12%'></col>
								<col width='8%'></col>
								<col width='8%'></col>
								<col width='8%'></col>
								<col width='10%'></col>
								<col width='8%'></col>
								<col width='10%'></col>
							</colgroup>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 城市推荐管理 -->
		<div role="tabpanel" class="tab-pane active" id="city_m">
			<div class="mainrighttab tabresourse bordernone" id="courseCityDiv" style="display:none">
				<p class="col-xs-4" style="padding: 0;">
					<button class="btn btn-sm btn-success city_rec" title="设为推荐">
						<i class="glyphicon glyphicon-cog"></i> 设为推荐
					</button>
					<button class="btn btn-sm btn-success city_qx_rec" title="设为推荐">
						<i class="glyphicon glyphicon-cog"></i> 取消推荐
					</button>
				</p>
				<%-- <div class="searchDivClass" id="searchCityDiv_PX">
					<div class="profile-info-row" >
						<table frame=void style="width: 100%">
							<tr>
								<td>
									<div class="profile-info-value searchTr">
										<select name="search_city" id="search_city" value="" class="propertyValue1" >
											<option value="">所在城市</option>
						               		<c:forEach var="city" items="${cityVo}">
						                        <option value="${city.cityName}">${city.cityName}</option>
						                    </c:forEach>
										</select>
										<input type="hidden" value="search_status" class="propertyName"/>
									</div>
								</td>
								<td>
									<button id="searchBtn_city" type="button" class="btn btn-sm  btn-primary "
											onclick="search_City();">
										<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
									</button>
								</td>
							</tr>
						</table>
					</div>
				</div> --%>
				<div class="row">
					<div class="col-xs-12">
						<table id="courseCityTable"
							   class="table table-striped table-bordered table-hover">
							<colgroup>
								<col width='5%'></col>
								<col width='12%'></col>
								<col width='10%'></col>
								<col width='12%'></col>
								<col width='8%'></col>
								<col width='8%'></col>
								<col width='8%'></col>
								<col width='10%'></col>
								<col width='8%'></col>
								<col width='10%'></col>
							</colgroup>
						</table>
					</div>
				</div>
			</div>
		</div>
    </div>
</div>

<!-- 增加form -->
<div id="dialogAddCourseDiv"></div>
<div id="addCourseDialog" class="hide">
	<form id="addCourse-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" id="add_serviceType"  name="serviceType">

		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-3 control-label no-padding-right"><font color="red">*</font>课程展示图:</label>
			<div class="col-sm-6">
				<div class="clearfix"  id="imgAdd">
					<input type="file" name="smallImgPath_file" id="smallImgPath_file" class="uploadImg uploadImg_add" />
				</div>
				<input name="smallimgPath" id="smallImgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>

		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>课程名称: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="courseName"  id="courseName" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
             </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>副标题: </label>
			<div class="col-sm-6">
				<input type="text" name="subtitle"  id="subtitle" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>所属学科: </label>
			 <div class="col-sm-6">
               <select name="menuId" id="menuName" value="" class="clearfix col-xs-10 col-sm-12 {required:true}" >
               		<option value="">请选择</option>
               		<c:forEach var="menus" items="${menuVo}">
                        <option value="${menus.id}">${menus.name}</option>
                    </c:forEach>
               </select>
             </div>
		</div>
	    <div class="space-4"></div>
	    <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>授课所在省市: </label>
			 
			 <div class="col-sm-3">
               <select id="province" name="province" onchange="doProvAndCityRelation();" 
                        class="clearfix col-xs-10 col-sm-12 {required:true}" >
  　　　　　　　　			<option id="choosePro"value="-1">请选择您所在省份</option>
  　　　　　　	   </select>
  			   <input  type="hidden" name="realProvince" id="realProvince"/>	
             </div>
              
              <div class="col-sm-3">
               <select id="citys" name="city" onchange="doCityAndCountyRelation();" class="clearfix col-xs-10 col-sm-12 {required:true}">
 　　　　　　　　			<option id='chooseCity' value='-1'>请选择您所在城市</option>
 　　　　		　　</select>
 			  <input  type="hidden" name="realCitys" id="realCitys"/>	
 			  </div>
 			  
 			  <div class="col-sm-3" onchange="choosAddCounty();">
               <select id="county" name="countys" class="clearfix col-xs-10 col-sm-12 {required:true}">
 　　　　　　　　			<option id='chooseCountys' value='-1'>请选择您所在县区</option>
 　　　　		　　</select>
 
 			  <input  type="hidden" name="realCounty" id="realCounty"/>	
 			  </div>
		</div>
	    
	    <div class="space-4"></div>
		<div class="form-group" id="add-currentPrice"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>授课详细地址: </label>
			 <div class="col-sm-6">
			 	<input type="text" name="address" value="" id="address" maxlength="200" class="col-xs-10 col-sm-12 {required:true}">
             </div> 
		</div>
	    
		<div class="space-4"></div>
	        <div class="form-group" style="margin-top: 18px;">
	            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 开始时间: </label>
	            <div class="col-sm-9">
	                <input type="text" name="startTime"  id="startTime" maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
	            </div>
	        </div>
	       <div class="space-4"></div>
	        <div class="form-group" style="margin-top: 18px;">
	            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 结束时间: </label>
	            <div class="col-sm-9">
	                <input type="text" name="endTime" id="endTime"  maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
	            </div>
	        </div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>课程时长: </label>
			<div class="col-sm-3" style="width: 43%;">
				<input type="text" name="courseLength"  id="courseLength" maxlength="4"  class="col-xs-10 col-sm-12 {required:true,number:true}">
			</div>
			<div class="col-sm-1 control-label no-padding-left" style="text-align: left;">
				分钟
			</div>
		</div>
		<div class="form-group"  style="margin-top: 18px;" >
            <label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>作者：</label>
		    <div class="ui-widget col-sm-6">
				  <select  name="userLecturerId" id="combobox" class="clearfix col-xs-10 col-sm-12 {required:true}">
				    <option value="">请选择...</option>
				    <c:forEach var="map" items="${mapList}">
	                        <option  value="${map.id}">${map.name}</option>
	                </c:forEach>
	              </select>  
		     </div>
		</div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>主播: </label>
			<div class="col-sm-6">
				<input type="text" name="lecturer"  id="lecturer" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
			</div>
		</div>

		<div class="space-4"></div>
		<div class="form-group" id="add-currentPrice"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>价格: </label>
			 <div class="col-sm-6">
			 	<input type="text" name="currentPrice" value="" id="currentPrice" maxlength="9"  class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.01,99999.99]}">
             </div>
		</div>
		<%--<div class="space-4"></div>--%>
		<%--<div class="form-group"  style="margin-top: 18px;" >--%>
			 <%--<label class="col-sm-3 control-label no-padding-right" for="courseDescribe"><font color="red">*</font>课程简介: </label>--%>
			 <%--<div class="col-sm-6">--%>
			 	<%--<!-- <input type="text" name="courseDescribe"  id="courseDescribe" maxlength="20"  class="col-xs-10 col-sm-12 {required:true,rangelength:[2,20]}"> -->--%>
			 	<%--<textarea class="form-control" name="description" id="courseDescribe"  rows="3" class="col-xs-10 col-sm-12 {required:true,rangelength:[1,170]}"></textarea>--%>
			 	<%--<input type="hidden" name="descriptionHid" id="descriptionHid" class="col-xs-10 col-sm-12 {required:true,rangelength:[1,170]}">--%>
             <%--</div>--%>
		<%--</div>--%>
		
	</form>
</div>

<!-- 查看form -->
<div id="showCourseDiv"></div>
<div id="showCourseDialog" class="hide">
	<form id="addCourse-form1"  class="form-horizontal" method="post" action="">
		<input type="hidden" id="edit_id"  name="id" class="col-xs-10 col-sm-8 {required:true}" style="margin-top: 15px;">
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>课程名称:</b> </label>
			 <div class="col-sm-6">
			  	<p id="show_courseName" class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="menuName"><font color="red">*</font><b>所属学科:</b> </label>
			 <div class="col-sm-6">
			 	<p id="show_menuName" class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-4 control-label no-padding-right" for="menuNameSecond"><font color="red">*</font><b>课程类别:</b> </label>
            <div class="col-sm-6">
           	 	<p id=show_menuNameSecond class="paddingtop7px padding7"></p>
            </div>
		</div>
		
		
		<%--<div class="form-group">
			<label class="col-sm-4 control-label no-padding-right" for="menuNameSecond"><font color="red">*</font><b>授课方式: </b></label>
            <div class="col-sm-6">
            	<p id=show_courseType class="paddingtop7px padding7"></p>
            </div>
		</div>--%>
		
		<!-- <div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>课程时长:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_courseLength class="paddingtop7px padding7"></p>
             </div>
		</div> -->
		
	    <%-- <div class="form-group">
	     <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>讲师:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_userLecturerId class="paddingtop7px padding7"></p>
             </div>
             <select  name="userLecturerId" id="view_mapList" class="hide" >
				    <option value="">请选择...</option>
				    <c:forEach var="map" items="${mapList}">
	                        <option  value="${map.id}">${map.name}</option>
	                </c:forEach>
	         </select>  
		</div> --%>
		
		<!-- 新增密码 -->
		<!-- <div class="form-group">
             <label class="col-sm-4 control-label no-padding-right" for="coursePwd"><b>密码:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_coursePwd class="paddingtop7px padding7"></p>
             </div>
		</div> -->
		
		<div class="form-group" id="classGradeQQ">
			<label class="col-sm-4 control-label no-padding-right" for="show_gradeQQ"><font color="red">*</font><b>班级QQ群:</b> </label>
			<div class="col-sm-6">
				<p id=show_gradeQQ class="paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>咨询QQ:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_qqno class="paddingtop7px padding7"></p>
             </div>
		</div>
		<!-- <div class="form-group"  >
		 	 <label class="col-sm-4 control-label no-padding-right" for="courseName"><b>课程链接:</b> </label>
			 <div class="col-sm-6" >
			 	<p id=show_cloudClassroom class="paddingtop7px padding7"></p>
             </div>
		</div> -->
        <div class="form-group" id="show_classRatedNum">
            <label class="col-sm-4 control-label no-padding-right" for="show_gradeStudentSum"><font color="red">*</font><b>班级额定人数: </b></label>
            <div class="col-sm-6" >
                <p id="show_gradeStudentSum" class="paddingtop7px padding7"></p>
            </div>
        </div>
        
        <!-- <div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>累计学习人数:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_sum class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>默认学习人数:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_learndCount class="paddingtop7px padding7"></p>
             </div>
		</div> -->
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>实际学习人数:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_actCount class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 讲师: </span></label>
            <div class="col-sm-6">
                <label class="paddingtop7px padding7"  id="role_type1_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <!-- <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 班主任 : </span></label>
            <div class="col-sm-6">
                <label class="paddingtop7px padding7"  id="role_type2_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 助教 :</span> </label>
            <div class="col-sm-6">
                <label class="paddingtop7px padding7"  name="name" id="role_type3_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div> -->
		
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>创建时间:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_createTime class="paddingtop7px padding7"></p>
             </div>
		</div>

		<div class="form-group">
			<label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>开始时间:</b> </label>
			<div class="col-sm-6">
				<p id=show_startTime class="paddingtop7px padding7"></p>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>结束时间:</b> </label>
			<div class="col-sm-6">
				<p id=show_endTime class="paddingtop7px padding7"></p>
			</div>
		</div>

		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>状态:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_status class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<!-- <div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>是否收费:</b> </label>
			 <div class="col-sm-3">
				<p class='paddingtop7px padding7'>收费<input type="radio" style="vertical-align:text-top;margin-top:2px;margin-left:2px;margin-right:5px" name="isFree"  id="show_is_free" disabled="disabled" value="1">免费<input type="radio" style="vertical-align:text-top;margin-top:2px;margin-left:2px" name="isFree"  id="show_no_free" disabled="disabled" value="0"></p>
             </div>
             <div class="col-sm-3">
             </div>
		</div> -->
		
		<div class="form-group" id="show-originalCost">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>原价格:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_originalCost class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group" id="show-currentPrice">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>现价格:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_currentPrice class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="address"><font color="red">*</font><b>授课地址:</b> </label>
			 <div class="col-sm-6">
			 	<p id="show_address" class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseDescribe"><font color="red">*</font><b>课程简介:</b> </label>
			 <div class="col-sm-6">
			 	<!-- <input type="text" name="courseDescribe"  id="show_courseDescribe" disabled="disabled" maxlength="20"  class="col-xs-10 col-sm-12 {required:true,rangelength:[2,20]}"> -->
			 	<p id=show_courseDescribe class="paddingtop7px padding7" style="word-break:break-all;word-wrap:break-word;width:250px"></p>
             </div>
		</div>
	</form>
</div>

<!-- 修改form -->
<div id="dialogEditCourseDiv"></div>
<div id="EditCourseDialog" class="hide">
	<form class="form-horizontal" id="updateCourse-form" method="post" action="" style="margin-top: 15px;">
		<input type="hidden" id="editCourse_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">

		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-3 control-label no-padding-right"><font color="red">*</font>课程展示图:</label>
			<div class="col-sm-6">
				<div class="clearfix"  id="imgEdit">
					<input type="file" name="smallImgPathFileEdit" id="smallImgPathFileEdit" class="uploadImg uploadImg_edit" />
				</div>
				<input name="smallimgPath" id="edid_smallImgPath" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>

		<div class="form-group" style="margin-top: 18px;">
			 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>课程名称: </label>
			 <div class="col-sm-6">
			 	<input type="text" name="courseName"  id="edid_courseName" maxlength="20"  class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
             </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>副标题: </label>
			<div class="col-sm-6">
				<input type="text" name="subtitle"  id="subtitle_edit" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>所属学科: </label>
			 <div class="col-sm-6">
               <select name="menuId" id="edid_menuName" value="" class="clearfix col-xs-10 col-sm-12 {required:true}" >
               		<option value="">请选择</option>
               		<c:forEach var="menus" items="${menuVo}">
                        <option value="${menus.id}">${menus.name}</option>
                    </c:forEach>
               </select>
             </div>
		</div>

		
	    <div class="space-4"></div>
	    <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>授课所在省市: </label>
			 <div class="col-sm-3">
               <select id="edit_province" name="province" onchange="doProvAndCityRelationEdit();" 
                        class="clearfix col-xs-10 col-sm-12 {required:true}" >
  　　　　　　　　			<option id="edit_choosePro"value="-1">请选择您所在省份</option>
  　　　　　　	   </select>
  				<input type="hidden" name = "realProvince"  id="edit_realProvince"/>
  			 </div>
  			 <div class="col-sm-3">	
                <select id="edit_citys" name="city" onchange="doProvAndCountyRelationEdit();" class="clearfix col-xs-10 col-sm-12 {required:true}">
 　　　　　　　　			<option id='edit_chooseCity' value='-1'>请选择您所在城市</option>
 　　　　		　　 </select>
 				<input type="hidden" name = "realCitys"  id="edit_realCitys"/>
             </div>
             
              <div class="col-sm-3">	
                <select id="edit_county" name="county" onchange="onchangeCountyEdit();" class="clearfix col-xs-10 col-sm-12 {required:true}">
 　　　　　　　　			<option id='edit_chooseCounty' value='-1'>请选择您所在县区</option>
 　　　　		　　 </select>
 				<input type="hidden" name ="realCounty"  id="edit_realCounty"/>
             </div>
		</div>
		
		<div class="form-group" id="add-currentPrice"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>授课详细地址: </label>
			 <div class="col-sm-6">
			 	<input type="text" name="address" value="" id="edit_address"  class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
		
		<div class="space-4"></div>
	        <div class="form-group" style="margin-top: 18px;">
	            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 开始时间: </label>
	            <div class="col-sm-9">
	                <input type="text" name="startTime"  id="edit_startTime" maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
	            </div>
	        </div>
	       <div class="space-4"></div>
	        <div class="form-group" style="margin-top: 18px;">
	            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 结束时间: </label>
	            <div class="col-sm-9">
	                <input type="text" name="endTime" id="edit_endTime"  maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
	            </div>
	        </div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>课程时长: </label>
			<div class="col-sm-3" style="width: 43%;">
				<input type="text" name="courseLength"  id="courseLength_edit" maxlength="4"  class="col-xs-10 col-sm-12 {required:true,number:true}">
			</div>
			<div class="col-sm-1 control-label no-padding-left" style="text-align: left;">
				分钟
			</div>
		</div>
		<div class="form-group"  style="margin-top: 18px;" >
            <label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>作者：</label>
		    <div class="ui-widget col-sm-6">
				  <%--<select  name="userLecturerId" id="combobox1" class="clearfix col-xs-10 col-sm-12 {required:true}">
				    <option value="">请选择...</option>
				    <c:forEach var="map" items="${mapList}">
	                        <option  value="${map.id}">${map.name}</option>
	                </c:forEach>
	              </select>  --%>
					  <input type="text" id="edit_userLecturerId" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}" readonly="readonly">
			</div>
		</div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>主播: </label>
			<div class="col-sm-6">
				<input type="text" name="lecturer"  id="lecturer_edit" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
			</div>
		</div>

		<div class="form-group" id="edit-currentPrice" style="margin-top: 18px;">
			 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>价格: </label>
			 <div class="col-sm-6">
			 	<input type="text" name="currentPrice"  id="edid_currentPrice" maxlength="9"  class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.01,99999.99]}">
             </div>
		</div>
		<%--<div class="form-group" style="margin-top: 18px;">--%>
			 <%--<label class="col-sm-3 control-label no-padding-right" for="courseDescribe"><font color="red">*</font>课程简介: </label>--%>
			 <%--<div class="col-sm-6">--%>
			 	<%--<textarea class="form-control" name="description" id="edid_courseDescribe"  rows="3" class = "{required:true}"></textarea>--%>
			 	<%--<input type="hidden" name="descriptionHid"  id="edid_descriptionHid" class="col-xs-10 col-sm-12 {required:true,rangelength:[1,170]}">--%>
             <%--</div>--%>
		<%--</div>--%>
	</form>
</div>


<!-- 修改推荐图片-->
<div id="dialogUpdateRecImgDiv"></div>
<div id="updateRecImgDialog" class="hide">
	<form id="updateRecImg-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="id" id="update_id">
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgPath"><font color="red">*</font>课程图片: </label>
			 <div class="col-sm-6" id="updateRecImg">
			 		<div class="clearfixUpdate">
						<input type="file" name="update_recImgPath_file" id="update_recImgPath_file" class="uploadImg"/>
					</div>
					（图片尺寸上传限制：252*97）
					<input name="icon" id="update_recImgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
             </div>
		</div>
	</form>
</div>

<!-- 设置学习计划天数 -->
<div id="setStudyDayDiv"></div>
<div id="setStudyDayDialog" class="hide">
	<form action="" method="post" class="form-horizontal" role="form" id="studyDay-form">
		<input type="hidden" name="courseId" id="courseId" value="">
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-5 control-label no-padding-right"><i class="text-danger">*</i> 学习计划模板天数: </label>
			<div class="col-sm-7">
				<input type="text" name="totalDay" id="totalDay_update" class="col-xs-3 col-sm-3 {required:true,digits:true,range:[1,365]}">
				<label class="col-sm-2 control-label no-padding-left">天</label>
			</div>
		</div>
	</form>
</div>

<!-- teacher -->
<div id="dialogTeacherGradeDiv" class="clearfix"></div>
<div id="gradeTeacherDialog" class="hide" style="width:550px;height:455px"">
    <form action="" method="post" class="form-horizontal" role="form" id="grade-teacher-form">
        <input type="hidden" id="teachers_grade_id" name="gradeId" />
        <input type="hidden" id="teachers_course_id" name="courseId" />
        <input type='hidden' id="jiangshiInput" name='roleType1' class='{required:true}' > 
        <input type='hidden' id="banzhurenInput" name='roleType2' class='{required:true}' > 
         <input type='hidden' id="zhujiaoInput" name='roleType3' class='{required:true}' >

		<div class="fenpeiTeacherAbove">
			<div class="fengpeiTeacher-content clearfix">

				<div class="fenpeiList">
					<div class="fenpeiList-content">
						<span class="xuekename">学科：</span>
						<ul class="xuekeleibei">
							<li><div class="xuekeleibeiID"><em id="xuekeleibeiId" class="rotateCaret"></em><span id="teachers_courseName" class="tagname">UI</span></div>
								<ul class="list-items1">
									<li><span class="tagname"><span class="rotateCaret" id="jiaoshiSanjiao"></span>讲师</span>
										<ul id="jiangshiLi" class="list-items2">
											
										</ul></li>
									<li><span class="tagname"><span class="rotateCaret" id="zhujiaoSanjiao"></span>助教</span>
										<ul id="zhujiaoLi" class="list-items2">
											
										</ul></li>
									<li><span class="tagname"><span class="rotateCaret" id="banzhurenSanjiao"></span>班主任</span>
										<ul id="banzhurenLi" class="list-items2">
											
										</ul></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<ul class="fenpeirow">
					<li class="row-first">-></li>
					<li class="row-second">-></li>
					<li class="row-third">-></li>
				</ul>
				<div class="child-parrent clearfix">
					<div class="teacher">
						<div class="child-parrent-title">
							<span>讲师</span> <span class="clear">清空</span>
						</div>
						<div class="allTeacher"></div>
					</div>
					<div class="zhujiao">
						<div class="child-parrent-title">
							<span>助教</span> <span class="clear">清空</span>
						</div>
						<div class="allTeacher"></div>
					</div>
					<div class="banzhuren">
						<div class="child-parrent-title">
							<span>班主任</span> <span class="clear">清空</span>
						</div>
						<div class="allTeacher"></div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript" src="${base}/js/cloudClass/provinces.js?v=ipandatcm_1.3"></script>
<script type="text/javascript" src="${base}/js/cloudClass/realcourse.js?v=ipandatcm_1.3"></script>