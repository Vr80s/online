<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<style>
.btnGroup a:hover{
	text-decoration: none;
}
.table-school {
  margin-right: 30px;
  overflow: inherit;
}
.table-school .table-school-title {
  overflow: hidden;
}
.table-school .table-school-title .school-nav {
  font-size: 22px;
  color: #333;
}
.table-school .table-school-title .school-nav span {
  font-size: 14px;
  color: #666;
}
.table-school .table-school-title .school-nav-text {
  font-size: 14px;
  color: #666;
  line-height: 26px;
  margin-top: 15px;
  margin-bottom: 0;
}
.table-school .table-school-body .school-chapter {
  margin-top: 20px;
  margin-bottom: 0;
}
.table-school .table-school-body .school-chapter .bcg {
  padding: 4px;
  background-color: #2cb82c;
  font-size: 0;
  position: relative;
  top: -7px;
}
.table-school .table-school-body .school-chapter .school-chapter-text {
  margin-left: 8px;
  color: #333;
  font-size: 18px!important;
}
.table-school .table-school-body .school-chapter .school-chapter-text span {
  margin-right: 12px;
}
.table-school .table-school-body .details-div {
  overflow: hidden;
}
.table-school .table-school-body .details-div .details-div-title {
  width: 100%;
  background-color: #f8f8f8;
  padding: 10px 16px;
  margin: 20px 0 0;
  border: 1px solid #eee;
  border-bottom: 0;
  font-size: 16px;
  color: #333;
}
.table-school .table-school-body .details-div .details-div-body {
  overflow: hidden;
  border-left: 1px solid #eee;
  border-bottom: 1px solid #eee;
}
.table-school .table-school-body .details-div .details-div-body p {
  float: left;
  width: 407.5px;
  border-right: 1px solid #eee;
  border-top: 1px solid #eee;
  padding: 10px 8px;
  margin: 0;
  font-size: 14px;
  color: #666;
  height: 41px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.questionName {
  color: #333;
  font-size: 16px;
  margin-bottom: 12px;
}
.problem-answers {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
  line-height: 22px;
}
.problem-answers span {
  color: #2cb82c !important;
}
.span4 { background-color: #EEEEEE; }
.span8 { background-color: #EEEEEE; }
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
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：站点信息管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 站点基础信息</span>
</div>
<form id="siteform" method="post">
<div class="tab-content" style="padding-left: 0;">
	<div style="float:left;">
		<p>    网站标题</p>
		<p style="margin-top: 30px;">  seo关键词</p>
		<p style="margin-top: 210px;"> seo描述信息</p>
	</div>
	<div style="float:left;">
	  <div class="clearfix" style="width: 800px;">
		<input type="text" name="title" id="title"  maxlength="30" class="col-xs-12 col-sm-12 {required:true}" >
	  </div><br>
	  <div class="form-group " style="width: 800px;">
	  		<div class="clearfix">
				<textarea class="wysiwyg-editor" id="article_content" style="max-height:200px;height: 400px;width: 800px; overflow: hidden;"></textarea><br>
				<input type="hidden" name="content"  id="content" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
			</div>
	  </div>
	  <div class="form-group " style="width: 800px;margin-top: 17px">
	  	<div class="clearfix">
			<textarea class="wysiwyg-editor" id="information" style="max-height:200px;height: 400px;width: 800px;overflow: hidden;"></textarea><br>
			<input type="hidden" name="information"  id="information" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
		</div>
	  </div>
		<button class="btn btn-sm btn-success add_bx" id="upload">
			<i class="glyphicon glyphicon-upload"></i> 提交
		</button>
	</div>
</div>
</form>
<script type="text/javascript" src="/js/site/site.js?v=4.0"></script>