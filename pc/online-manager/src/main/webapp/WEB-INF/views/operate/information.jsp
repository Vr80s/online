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
  var mname = "${md5UserName}";
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：运营管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 资讯管理</span>
</div>


<div class="mainrighttab tabresourse bordernone">

	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx_info" title="新增资讯">
			<i class="glyphicon glyphicon-plus"></i> 新增
		</button>
		<button class="btn btn-sm btn-success dele_bx" title="批量删除">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
		<button class="btn btn-sm btn-success add_type" title="分类管理">
			<i class="glyphicon glyphicon-plus"></i> 分类管理
		</button>
	</p>

	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void style="width: 100%">
                <tr>
                  <td>
                      <div class="profile-info-value searchTr">
                          <input type="text"   id="nameSearch" class="propertyValue1"  placeholder = "资讯关键字"/>
                          <input type="hidden" value="nameSearch" class="propertyName"/>
                      </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
							<select id="informationTypeSearch" name="informationtype" class="propertyValue1">
								<option value="">分类</option>
								<c:forEach var="informationType" items="${informationTypes}">
									<option value="${informationType.value}">${informationType.name}</option>
								</c:forEach>
							</select>
                        	<input type="hidden" value="informationTypeSearch" class="propertyName"/>
						</div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="statusSearch" name="status" class="propertyValue1">
                                <option  value="" >状态</option>
                                <option  value="0" >已禁用</option>
                                <option  value="1" >已启用</option>
                            </select>
                        	<input type="hidden" value="statusSearch" class="propertyName"/>
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
			<table id="infoTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 增加form -->
<div id="dialogAddInfoDiv"></div>
<div id="addInfoDialog" class="hide">
	<form id="addInfo-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>资讯标题: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="name"  id="name" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:40}">
             </div>
             <div>
            	<label style="cursor: pointer;"><input style="cursor: pointer;margin-top:9px" type="checkbox" name="isHot" id="isHot"><span style="display: inline-block;vertical-align: middle;margin-top: -8px;">设为推荐</span></label>
             </div>
             
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>分类: </label>
			 <div class="col-sm-6">
               <select name="informationtype" id="informationType" value="" class="clearfix col-xs-10 col-sm-12 {required:true}" >
               		<option value="">请选择</option>
               		<c:forEach var="informationType" items="${informationTypes}">
                        <option value="${informationType.value}">${informationType.name}</option>
                    </c:forEach>
               </select>
             </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="hrefAdress"><font color="red">*</font>链接地址: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="hrefAdress" maxlength = "120" id="hrefAdress" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:120}">
             </div>
		</div>
	</form>
</div>	

<!-- 修改form -->
<div id="dialogEditInfoDiv"></div>
<div id="editInfoDialog" class="hide">
	<form id="editInfo-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input  type="hidden" name="id" id="editId">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>资讯标题: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="name"  id="editName" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:40}">
             </div>
             <div>
            	<label style="cursor: pointer;"><input style="cursor: pointer;margin-top:9px" type="checkbox" name="isHot" id="editIsHot"><span style="display: inline-block;vertical-align: middle;margin-top: -8px;">设为推荐</span></label>
             </div>
             
		</div>
		<div class="space-4"></div> 
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>分类: </label>
			 <div class="col-sm-6">
               <select name="informationtype" id="editInformationType" value="" class="clearfix col-xs-10 col-sm-12 {required:true}" >
               		<option value="">请选择</option>
               		<c:forEach var="informationType" items="${informationTypes}">
                        <option value="${informationType.value}">${informationType.name}</option>
                    </c:forEach>
               </select>
             </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="hrefAdress"><font color="red">*</font>链接地址: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="hrefAdress"  maxlength = "120" id="editHrefAdress" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:120}">
             </div>
		</div>
	</form>
</div>	
<!-- 分类管理 -->
<div id="dialogAddTypefoDiv"></div>
<div id="addTypefoDialog" class="hide">
	<button class="btn btn-sm btn-success add_info_type" title="新增资讯分类">
		<i class="glyphicon glyphicon-plus"></i>增加
	</button>
	<form id="addTypefo-form" class="form-horizontal" method="post" style="margin-top: 15px;"></form>
</div>
<script type="text/javascript" src="${base}/js/operate/information.js?v=1.0"></script>