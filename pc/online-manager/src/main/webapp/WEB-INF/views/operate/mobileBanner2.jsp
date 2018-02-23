<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	

<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：移动端管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span>移动端banner </span>
</div>


<div class="mainrighttab tabresourse bordernone">
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
                            <input type="text"   id="search_name" class="propertyValue1"  placeholder = "banner关键字" maxlength="30"/>
                            <input type="hidden" value="search_name" class="propertyName"/>
                           </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="search_status" class="propertyValue1">
                                <option  value="" >状态</option>
                                <option  value="1" >启用</option>
                                <option  value="0" >禁用</option>
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
			<table id="mobileBannerTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 增加form -->
<div id="dialogAddMobileBannerDiv"></div>
<div id="addMobileBannerDialog" class="hide">
	<form id="addMobileBanner-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="description"><font color="red">*</font>banner名称: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="name"  id="add_name" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgPath"><font color="red">*</font>banner图片: </label>
			 <div class="col-sm-6" id="addDiv">
			 		<div class="clearfixAdd">
						<input type="file" name="imgPath_file" id="imgPath_file" class="uploadImg"/>
					</div>
					<!-- （图片尺寸上传限制：926*386） -->
					<input name="imgPath" id="add_imgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>链接地址: </label>
			 <div class="col-sm-6">
               		<input type="text" name="url"  id="add_url" class="col-xs-10 col-sm-12 {required:true,maxlength:225}">
             </div>
		</div>
	</form>
</div>

<!-- 修改form -->
<div id="dialogUpdateMobileBannerDiv"></div>
<div id="updateMobileBannerDialog" class="hide">
	<form id="updateMobileBanner-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="Id" id="update_id">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>banner名称: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="name"  id="update_name" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgPath"><font color="red">*</font>banner图片: </label>
			 <div class="col-sm-6">
			 		<div class="clearfixUpdate">
						<input type="file" name="update_imgPath_file" id="update_imgPath_file" class="uploadImg"/>
					</div>
					<!-- （图片尺寸上传限制：926*386） -->
					<input name="imgPath" id="update_imgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgHref"><font color="red">*</font>链接地址: </label>
			 <div class="col-sm-6">
               		<input type="text" name="url"  id="update_url" class="col-xs-10 col-sm-12 {required:true,maxlength:225}">
             </div>
		</div>
	</form>
</div>
<script type="text/javascript" src="${base}/js/operate/mobileBanner.js?v=ipandatcm_1.3"></script>
