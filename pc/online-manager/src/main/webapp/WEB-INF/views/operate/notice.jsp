<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	
<style type="text/css">
.wysiwyg-style3{
	text-align: right !important;
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
<script src="/js/layer/layer.js"></script>
<link href="/js/layer/skin/layer.css" type="text/css" />
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：运营管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 公告管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增公告">
			<i class="glyphicon glyphicon-plus"></i> 新增公告
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
                            <input type="text"   id="search_noticeContent" class="propertyValue1"  placeholder = "公告关键字" maxlength="30"/>
                            <input type="hidden" value="search_noticeContent" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="search_status" class="propertyValue1">
                                <option  value="" >状态</option>
                                <option  value="1" >已发布</option>
                                <option  value="2" >已下线</option>
                                <option  value="0" >未发布</option>
                            </select>
                            <input type="hidden" value="search_status" class="propertyName" />
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
			<table id="noticeTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 增加form -->
<div id="dialogAddNoticeDiv"></div>
<div id="addNoticeDialog" class="hide">
	<form id="addNotice-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-1 control-label no-padding-right" for="notice_content" style="width:80px"><font color="red">*</font>公告内容: </label>
			 <div class="col-sm-9" style="width: 570px;">
			 		<div class="wysiwyg-editor" id="notice_content"></div>
					<input type="hidden" name="noticeContent"  id="noticeContent" class="col-xs-10 {required:true,maxlength:50}">
             </div>
			 <div class="col-sm-1" style="margin-top: 274px;">
				  <span id="wordCnt">0</span>/50
             </div>
		</div>
	</form>
</div>

<!-- 修改form -->
<div id="dialogUpdateNoticeDiv"></div>
<div id="updateNoticeDialog" class="hide">
	<form id="updateNotice-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="id" id="update_id">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-2 control-label no-padding-right" for="noticeContent" style="width:80px"><font color="red">*</font>公告内容: </label>
			 <div class="col-sm-9" style="width: 570px;">
			 		<div class="wysiwyg-editor" id="update_notice_content"></div>
					<input type="hidden" name="noticeContent"  id="update_noticeContent" class="col-xs-10 {required:true,maxlength:50}">
             </div>
             <div class="col-sm-1" style="margin-top: 274px;">
				  <span id="wordCnt2">0</span>/50
             </div>
		</div>
	</form>
</div>
<script type="text/javascript" src="/js/operate/notice.js?v=ipandatcm_1.3"></script>
