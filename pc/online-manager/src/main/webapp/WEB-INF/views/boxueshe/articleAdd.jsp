<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<script type="text/javascript" src="js/boxueshe/articleAdd.js"></script>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
		
	}
	var weburl = '${weburl}';
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：博学社管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
			</small> 
			文章管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
		</small>
  <span>新增文章 </span>
</div>
<!-- 新增 form -->
<div id="dialogArticleDiv"></div>
<div id="addArticleDialog" >
	<form id="addArticle-form" class="form-horizontal"  method="post" action="" >
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>文章图片:</label>
			<div class="col-sm-3" >
				<div class="clearfix" id="imgAdd" style="width: 240px;">
					<!-- <input type="file" name="imgPath_file" id="imgPath_file" class="uploadImg"/> -->
				</div>
				<input name="imgPath" id="add_imgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>
		
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>所属分类:</label>
			<div class="col-sm-3" >
				<div class="clearfix " style="width: 240px;">
					<select   name="typeId"   id="typeId"   class="col-xs-12 col-sm-12 {required:true}" >
                        <option  value="" >请选择</option>
                       	<c:forEach var="m" items="${articleTypes}">
                            <option value="${m.id}">${m.name}</option>
                         </c:forEach> 
                    </select>
				</div>
			</div>
		</div>
		
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>所属标签:</label>
			<div class="col-sm-3" >
				<div class="clearfix" style="width: 240px;">
					 <input type="text" id="tagName" readonly="readonly"  onclick="openTagDiv()" class="col-xs-12 col-sm-12 {required:true}" style="cursor: pointer;">
					 <input type="hidden" name="tagId" id="tagId" >
				</div>
			</div>
		</div>
		
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>文章标题:</label>
			<div class="col-sm-3" >
				<div class="clearfix" style="width: 240px;">
					 <input type="text" name="title" id="title"  maxlength="30" class="col-xs-12 col-sm-12 {required:true}" >
				</div>
			</div>
		</div>
		
		<div class="form-group " style="margin-top:18px;margin-bottom:60px">
			<label class="col-sm-1 control-label no-padding-right" for="courseDetail_content"><font color="red">*</font>内容:</label>
			<div class="col-lg-10 " style="height:250px">
				<div class="clearfix">
					<div class="wysiwyg-editor" id="article_content" style="max-height:400px;height: 400px;"></div><br>
					<input type="hidden" name="content"  id="content" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
				</div>
			</div>
		</div>
	
	</form>
	
	<div class="col-xs-7" style="text-align: right;margin-top:150px;">
		<button class="btn btn-sm btn-success" id="previewSaveBtn">
			预览
		</button>
		<button class="btn btn-sm btn-success" id="saveBtn">
			保存
		</button>
		<button class="btn btn-sm btn-success" id="returnbutton">
			返回
		</button>
	</div>
</div>
<!-- 标签浮框 -->
<div id="dialogTagDiv"></div>
<div id="addTagDialog" class="hide">
	<div id="tagDiv" style="margin:0 auto;width:480px">
		<c:forEach var="tag" items="${tags}" varStatus="status" >
			<input style="cursor: pointer;" type="checkbox" id="tag${tag.id}" data-tagName="${tag.name}" value="${tag.id}"><label style="cursor: pointer;" for="tag${tag.id}" >${tag.name}</label>&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${(status.index+1)%6==0}"></br></c:if>   
		</c:forEach> 
	</div>	
</div>