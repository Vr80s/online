<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
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
<link href="${base}/js/layer/skin/layer.css" type="text/css" />

<div class="page-header">
  当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 精品推荐管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
  <%--<p class="col-xs-4" style="padding:0px">
    <button class="btn btn-sm btn-success add_jp" id="add_button" title="设为精品课程">
        <i class="glyphicon glyphicon-plus"></i> 设为精品课程</button>
    <button class="btn btn-sm btn-success deletes_jp"  onclick="deleteBatch();" title="取消精品课程">
        <i class="glyphicon glyphicon-trash"></i> 取消精品课程</button>
  </p>--%>
    <div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

        </div>
    </div>
  <div class="row">
    <div class="col-xs-12">
      <table id="scoreTypeTable" class="table table-striped table-bordered table-hover"></table>
    </div>
  </div>
</div>




<script type="text/javascript" src="${base}/js/cloudClass/essenceRecommend.js?ver=1.2 "></script>