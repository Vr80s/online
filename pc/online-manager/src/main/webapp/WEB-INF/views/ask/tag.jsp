<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
 <style>
        .yingyongshu{
            position:relative;
            width:100%;
            cursor:pointer;
        }
        #yinyongshulist{
            display:none;
            padding:0;
            margin:0;
            list-style: none;
            position:absolute;
            left:143px;
            top:24px;
            border:1px solid #eee;
            position:absolute;
            z-index:10;
            background-color:#fff;
        }
        #yinyongshulist li{
            width:90px;
            border:1px solid #eee;
            line-height:30px;
            color:#000;
            cursor:pointer;
        }
        .sanjiao{
            width:8px;
            height:5px;
            display:inline-block;
            background:url("${base}/images/cy-23.png")no-repeat;
            -webkit-transform: rotate(0deg);
            -moz-transform: rotate(0deg);
            -ms-transform: rotate(0deg);
            -o-transform: rotate(0deg);
            transform: rotate(0deg);
            margin-left:5px;
        }
        .sanjiao2 {
            width:8px;
            height:5px;
            display:inline-block;
            margin-left:5px;
            background:url("${base}/images/cy-23.png")no-repeat;
            -webkit-transform: rotate(180deg);
            -moz-transform: rotate(180deg);
            -ms-transform: rotate(180deg);
            -o-transform: rotate(180deg);
            transform: rotate(180deg);
        }
    </style>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<script src="${base}/js/layer/layer.js"></script>
<link href="${base}/js/layer/skin/layer.css" type="text/css" />
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
	当前位置：博问答管理<small> <i class="ace-icon fa fa-angle-double-right"></i></small> 
	<span> 学科标签管理 </span>
	
		<div class="mainrighttab tabresourse bordernone" style="text-align: right;width:50%;margin-top:-14px" >
	<button class="btn btn-sm btn-success " id="returnbutton" >
		<i class="glyphicon glyphicon-arrow-left"></i>
		返回上级
	</button>
	</div>
	
</div>
<!-- <div class="mainrighttab tabresourse bordernone"
	style="text-align: right; width: 100%;">
	<button class="btn btn-sm btn-success add_bx" id="returnbutton">
		<i class="glyphicon glyphicon-arrow-left"></i> 返回上级
	</button>
</div> -->
<div class="form-group">
	<p style="display:inline-block;">
		<label style="font-size:16px;font-weight:bold">学科:</label> <label style="font-size:16px;font-weight:bold">${MenuName}</label>
	</p>
</div>

	
<input type="hidden" id="page" value="${page}" />
<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增标签"><i class="glyphicon glyphicon-plus"></i>新增标签</button>
        <button class="btn btn-sm btn-success deletes_bx" title="批量删除"  onclick="deleteBatch();" ><i class="glyphicon glyphicon-trash"></i> 批量删除</button>
        <button class="btn btn-sm btn-primary " title="问题数从高到低排列"  onclick="quesSort();" ><i ></i> 问题数↓</button>
        <button class="btn btn-sm btn-primary " title="引用数从高到低排列"  onclick="citesSort();" ><i ></i> 引用数↓</button>
	</p>

	<div class="searchDivClass" id="searchDiv">
		<div class="profile-info-row">

			<table frame=void >
				<input type="hidden" id="menuId" value="${menuId}" />
				<input type="hidden" id="quesSort" value="quesSort" />
				<input type="hidden" id="citesSort" value="citesSort" />
				<input type="hidden" id="monthSort"  />
				<%-- <input type="hidden" id="gradeName" value="${gradeName}" />
				<input type="hidden" id="page" value="${page}" /> --%>
				<tr>
					<td>
						<div class="profile-info-value searchTr">
							<input type="text" placeholder="标签名称"
								class="propertyValue1" id="NameSearch" style="width: 150px;">
							<input type="hidden" value="NameSearch" class="propertyName" />
						</div>
					</td>
					
					<td>
						<button id="searchBtn" type="button"
							class="btn btn-sm  btn-primary " onclick="search();">
							<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
						</button>
					</td>
				</tr>
			</table>
		</div>
	</div>


	<div class="row">
		<div class="col-xs-12">
			<table id="tagTable" class="table table-striped table-bordered table-hover">
			  
				
			</table>
		</div>
	</div>
</div>

<!-- 新增标签 -->
<div id="dialogAddTagDiv"></div>
<div id="addTagDialog" class="hide">
	 <form  method="post" class="form-horizontal" role="form" id="addTag-form" style="margin-top: 15px;" >
	   <input type="hidden" id="addTagMenuId" name="menuId" value="${menuId}" />
	   <div class="form-group">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 标签名称: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <input type="text" placeholder="*不同标签之间请用英文状态逗号隔开" name="name" style="margin-right: 15px;" maxlength="120"  class="col-xs-10 col-sm-8 {required:true}">
		      </div>
	      </div>
	    </div>
	 </form>
</div>

<!-- 修改标签 -->
<div id="dialogEditTagDiv"></div>
<div id="editTagDialog" class="hide">
	 <form  method="post" class="form-horizontal" role="form" id="update-form" style="margin-top: 15px;" >
	   <input type="hidden" id="editId" name="id" />
	   <div class="form-group">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 标签名称: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <input type="text" id="editName" placeholder="*不同标签之间请用英文状态逗号隔开" name="name" style="margin-right: 15px;" maxlength="120"  class="col-xs-10 col-sm-8 {required:true}">
		      </div>
	      </div>
	    </div>
	 </form>
</div>


<script type="text/javascript" src="${base}/js/ask/tag.js?v=ipandatcm_1.3"></script>