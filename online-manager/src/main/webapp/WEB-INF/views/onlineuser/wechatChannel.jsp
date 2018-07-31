<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="/js/layer/skin/layer.css" type="text/css" />	
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
        width: 92%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 0px 4px 4px 4px;

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
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：渠道管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 渠道管理 </span>
</div>

<div style="height: 100%;" class="clearfix">
 
    <!-- Tab panes -->
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
        
        	<div class="mainrighttab tabresourse bordernone" id="courseDiv">
				<p class="col-xs-4" style="padding: 0;">
					<button class="btn btn-sm btn-success add_bx" title="新增">
						<i class="glyphicon glyphicon-plus"></i> 新增
					</button>
					<button class="btn btn-sm btn-success dele_bx" title="批量删除">
						<i class="glyphicon glyphicon-trash"></i> 批量删除
					</button>
				</p>
			
			<div class="searchDivClass" id="searchDiv">
			        <div class="profile-info-row" >
			
			            <table frame=void >
			                <tr>
			                
			                	<td>
			                       <div class="profile-info-value searchTr">
			                           <input type="text"  id="contact" class="propertyValue1" style="width:140px;" placeholder="渠道名称/联系人/手机号"/>
									<input type="hidden" value="contact" class="propertyName"/>
			                        </div>
			                    </td>
								<td>
			                       <div class="profile-info-value searchTr">
			                            <select name="search_status" id="search_status" value="" class="propertyValue1" >
						               		<option value="">状态</option>
						                        <option value="0">已禁用</option>
						                        <option value="1">已启用</option>
						               </select>
			                            <input type="hidden" value="search_status" class="propertyName"/>
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
						<table id="courseTable"
							class="table table-striped table-bordered table-hover">
						</table>
					</div>
				</div>
			</div>
        </div>
        <div role="tabpanel" class="tab-pane" id="inbox">
        	<div class="mainrighttab tabresourse bordernone" id="courseRecDiv" style="display:none">
        			
        			<div class="searchDivClass" id="searchDiv">
			        <div class="profile-info-row" >
			
			            <table frame=void >
			                <tr>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" placeholder = "公开课名称" class="propertyValue1" id="search_courseName" style="width: 150px;">
			                            <input type="hidden" value="search_courseName" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                       <div class="profile-info-value searchTr">
			                            <select name="menuName" id="search_menu" value="" class="propertyValue1"  >
						               		<option value="">学科</option>
						               		<c:forEach var="menus" items="${menuVo}">
						                        <option value="${menus.id}">${menus.name}</option>
						                    </c:forEach>
						               </select>
			                            <input type="hidden" value="search_menu" class="propertyName"/>
			                        </div>
			                    </td>
								<td>
									<div class="profile-info-value searchTr">
										<input type="text" placeholder="授课老师" class="propertyValue1" id="search_lecturerName" style="width: 150px;"> 
										<input type="hidden" value="search_lecturerName" class="propertyName" />
									</div>
								</td>
								<td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text"  class="propertyValue1" id="search_startTime"  placeholder = "开始时间" style="width: 150px" readonly="readonly"/>
			                            <input type="hidden" value="search_startTime" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            &nbsp;至 &nbsp;
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text"  class="propertyValue1" id="search_endTime"  placeholder = "结束时间" style="width: 150px" readonly="readonly"/>
			                            <input type="hidden" value="search_endTime" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
			                                onclick="searchCourseRecTable();">
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
							    <col width='17%'></col>
							    <col width='8%'></col>
							    <col width='8%'></col>
							    <col width='6%'></col>
							    <col width='6%'></col>
							    <col width='9%'></col>
							    <col width='8%'></col>
							    <col width='7%'></col>
							    <col width='14%'></col>
							    <col width='14%'></col>
							</colgroup>
						</table>
					</div>
				</div>
			</div>
        </div>
    </div>
 
	<!-- 增加form -->
	<div id="dialogAddCourseDiv"></div>
	<div id="addCourseDialog" class="hide">
		<form id="addCourse-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
	        
	        <div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>渠道名称： </label>
				 <div class="col-sm-6">
				 	<input type="text" name="name"   maxlength="15"  class="col-xs-10 col-sm-12 {required:true}">
	             </div>
			</div>
			
			<div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>联系人: </label>
				 <div class="col-sm-6">
				 	<input type="text" name="contact"   maxlength="15"  class="col-xs-10 col-sm-12 {required:true}">
	             </div>
			</div>
			
			<div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>联系电话: </label>
				 <div class="col-sm-6">
				 	<input type="text" name="mobile" maxlength="15"    class="col-xs-10 col-sm-12 {required:true,mobile:true}">
	             </div>
			</div>
			
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
               <select id="county" name="area" class="clearfix col-xs-10 col-sm-12 {required:true}">
 　　　　　　　　			<option id='chooseCountys' value='-1'>请选择您所在县区</option>
 　　　　		　　</select>
 			  <input  type="hidden" name="realCounty" id="realCounty"/>	
 			  </div>
		</div>
		</form>
	</div>
	
	<!-- 修改form -->
	<div id="dialogEditCourseDiv"></div>
	<div id="EditCourseDialog" class="hide">
		<form class="form-horizontal" id="updateCourse-form" method="post" action="" style="margin-top: 15px;">
			
		   <input type="hidden" id="editChannel_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">
	        
	       <div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>渠道名称： </label>
				 <div class="col-sm-6">
				 	<input type="text" name="name" id="editName_id"   maxlength="9"  class="col-xs-10 col-sm-12 {required:true}">
	             </div>
			</div>
			
			<div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>联系人: </label>
				 <div class="col-sm-6">
				 	<input type="text" name="contact"  id="editContact_id"   maxlength="9"  class="col-xs-10 col-sm-12 {required:true}">
	             </div>
			</div>
			
			<div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>联系电话: </label>
				 <div class="col-sm-6">
				 	<input type="text" name="mobile"  id="editMobile_id"   maxlength="15"  class="col-xs-10 col-sm-12 {required:true,mobile:true}">
	             </div>
			</div>
			
			
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
                <select id="edit_county" name="area" onchange="onchangeCountyEdit();" class="clearfix col-xs-10 col-sm-12 {required:true}">
 　　　　　　　　			<option id='edit_chooseCounty' value='-1'>请选择您所在县区</option>
 　　　　		　　 </select>
 				<input type="hidden" name ="realCounty"  id="edit_realCounty"/>
             </div>
             
		</div>
		
		 <div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right">二维码: </label>
	        <div class="col-sm-6">
	      <!--   <p id="qrCodeImg" class="paddingtop7px padding7"></p> -->
	        <textarea rows="3" style="width:100%" disabled="disabled" cols="20" id="qrCodeImg">			</textarea>
	        </div>
		 </div>
		 
		 <div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right">自定义二维码: </label>
	        <div class="col-sm-6"><p id="customQrCodeUrl" class="paddingtop7px padding7"></p></div>
		 </div>
		 
		 
		 <div class="form-group" style="margin-top: 5px;">
		    <label class="col-sm-3 control-label no-padding-right" style="color: #21201d;font-weight: 800;">备注: </label>
			<div class="col-sm-6"><p id="customQrCodeUrl" style="color: #21201d;font-weight: 800;"
			class="paddingtop7px padding7;">自定义二维码可通过第三方二维码生成器进行生产。将自定义二维码连接生产二维码 </p></div>
		 </div>
		 
		  <div class="form-group" style="margin-left: 26%;text-align: left;margin-top: 5px;">
			 <a href="https://cli.im/" title="草料二维码生成器" 
			 style="color: blue;text-decoration: underline;color: #00c0ef;" target="_blank">草料二维码生成器</a>
		 </div>
		 
		</form>
	</div>
	
	<!-- 设置平台分成form -->
	<div id="dialogEditFcDiv"></div>
	<div id="FcDialog" class="hide">
		<form class="form-horizontal" id="updateBrokerage-form" method="post" action="" style="margin-top: 15px;">
			<input type="hidden" id="ids"  name="ids" class="col-xs-10 col-sm-8 {required:true,isphoneNum:true}">
			<div class="form-group"  style="margin-top: 18px;" >
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>分成比例: </label>
				 <div class="col-sm-6">
				 		<input type="text" name="brokerage" id="brokerage" class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.01,100.00]}">
	             </div>
				 <label class="col-sm-1 control-label no-padding-right" for="courseName">%</label>
			</div>
			<div class="form-group"  style="margin-top: 18px;" >
				 <label class=" control-label no-padding-right" for="courseName">根据用户收到礼物价值，平台将从中征收的分成金额比例 </label>
				
			</div>
			
		</form>
	</div>
</div>

<script type="text/javascript">
			var availableTags = [
			                 	"ActionScript",
			                 	"AppleScript",
			                 	"Asp",
			                 	"BASIC",
			                 	"C",
			                 	"C++"
			                 ];
            $( "#autocomplete" ).autocomplete({
            	source: availableTags
            });			
			
</script>
<script type="text/javascript" src="/js/cloudClass/provinces.js?v=ipandatcm_1.3"></script>
<script type="text/javascript" src="/js/onlineuser/wechatChannel.js?v=ipandatcm_1.3"></script>