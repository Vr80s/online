<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	

<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：移动端管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span>app版本管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增">
			<i class="glyphicon glyphicon-plus"></i> 新增安卓版本
		</button>
		<button class="btn btn-sm btn-success add_ios_bx" title="新增">
			<i class="glyphicon glyphicon-plus"></i> 新增ios版本
		</button>
		<button class="btn btn-sm btn-success dele_bx" title="批量删除">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
	</p>
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
                <tr>
                	<!-- <td>
               			<div class="profile-info-value searchTr">
                            <input type="text"   id="search_name" class="propertyValue1"  placeholder = "banner关键字" maxlength="30"/>
                            <input type="hidden" value="search_name" class="propertyName"/>
                           </div>
                    </td> -->
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="search_type" class="propertyValue1">
                                <option  value="" >app类型</option>
                                <option  value="1" >IOS</option>
                                <option  value="2" >安卓</option>
                            </select>
                            <input type="hidden" value="search_type" class="propertyName"/>
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
	
		<input type="hidden" value = "2" name="type"  maxlength="50">
	
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="description"><font color="red">*</font>版本号: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="version"  id="add_name" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  id ="ad_apk" style="margin-top: 18px;">
			 <label class="col-sm-3 control-label no-padding-right" f><font color="red">*</font>安装包: </label>
			 <div class="col-sm-6">
					 <input type="file" name="filename1" id="imgPath_file"/>
					 <input type="hidden" name="filename" id="jia_imgPath_file"/>
					 <div id="kewudeie" style="padding-top:20px;">
					 <p><span>原始文件名：</span><span id="ys_filename"></span></p>
					 <p><span>下载地址：</span><p id="xz_fileurl" style="word-wrap: break-word;word-break: normal;"></p></p>
					 </div>
					 <input name="downUrl" id="add_imgPath" value="" type="hidden" class="{required:true}" >
             </div>
		</div>
		
		
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>强制更新: </label>
			 <div class="col-sm-6">
               		<input type="radio" value="true" name="mustUpdate" class="{required:true}">是
               		<input type="radio" value="0" name="mustUpdate" class="{required:true}" checked="checked">否
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>更新介绍: </label>
			 <div class="col-sm-6">
               		<div class="clearfix" >
	                	<textarea name="describe" id="description"class="{required:true}" cols="39" rows="5"></textarea>
	                </div>
             </div>
		</div>
	</form>
</div>



<!-- 新增iosform -->
<div id="dialogAddIosDiv"></div>
<div id="dialogAddIosDivDialog" class="hide">
	<form id="dialogAddIosDiv-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
	
		<input type="hidden" value = "1" name="type"  maxlength="50">
	
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="description"><font color="red">*</font>版本号: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="version"  id="add_name" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
		
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>强制更新: </label>
			 <div class="col-sm-6">
               		<input type="radio" value="true" name="mustUpdate" class="{required:true}" >是
               		<input type="radio" value="0" name="mustUpdate" class="{required:true}" checked="checked">否
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>更新介绍: </label>
			 <div class="col-sm-6">
               		<div class="clearfix" >
	                	<textarea name="describe" id="description"class="{required:true}" cols="39" rows="5"></textarea>
	                </div>
             </div>
		</div>
	</form>
</div>






<!-- 修改form -->
<div id="dialogUpdateMobileBannerDiv"></div>
<div id="updateMobileBannerDialog" class="hide">
	<form id="updateMobileBanner-form" class="form-horizontal" method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="Id" id="update_id">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="description"><font color="red">*</font>版本号: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="version"  id="edit_name" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<!-- <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" f><font color="red">*</font>安装包: </label>
			 <div class="col-sm-6">
						<input type="file" name="filename" id="edit_path_file"/>
             </div>
					<input name="downUrl" id="edit_path" value="" type="hidden" class="{required:true}" >
		</div> -->
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>强制更新: </label>
			 <div class="col-sm-6">
               		<input type="radio" value="true" id="r1" name="mustUpdate" class="{required:true}">是
               		<input type="radio" value="false" id="r2" name="mustUpdate" class="{required:true}" checked="checked">否
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>更新介绍: </label>
			 <div class="col-sm-6">
               		<div class="clearfix" >
	                	<textarea name="describe" id="edit_describe" class="{required:true}" cols="39" rows="5"></textarea>
	                </div>
             </div>
		</div>
	</form>
</div>
<script type="text/javascript" src="/js/mobile/appManager.js?v=ipandatcm_1.3"></script>
