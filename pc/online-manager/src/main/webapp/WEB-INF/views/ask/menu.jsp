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
  当前位置：博问答管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 学科标签管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
  <p class="col-xs-4" style="padding:0px">
   <!--  <button class="btn btn-sm btn-success add_bx" title="新增学科"><i class="glyphicon glyphicon-plus"></i> 新增学科</button>
    <button class="btn btn-sm btn-success deletes_bx" title="批量删除"  onclick="deleteBatch();" ><i class="glyphicon glyphicon-trash"></i> 批量删除</button> -->
  </p>
    <div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
                <tr>
                    <%--<td>
                        <div class="profile-info-value searchTr">
                            <select class="propertyValue1" id="courseCount">
                                <option value="all">包含课程数</option>
                                <option value="desc">升序</option>
                                <option value="asc">降序</option>
                            </select>
                            <input type="hidden" value="courseCount" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "创建人" class="propertyValue1" id="createPerson" style="width: 150px;">
                            <input type="hidden" value="createPerson" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker" name="time_start" id="time_start"  placeholder = "开始创建时间"/>
                            <input type="hidden" value="time_start" class="propertyValue1"/>

                        </div>
                    </td>
                    <td style="font-size: 12px">
                        &nbsp;&nbsp;至
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker" name="time_end" id="time_end" placeholder = "结束创建时间"/>
                            <input type="hidden" value="time_end" class="propertyValue1"/>
                        </div>
                    </td>--%>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "学科名称" class="propertyValue1" id="menuName" style="width: 150px;">
                            <input type="hidden" value="menuName" class="propertyName"/>
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
      <table id="cloudClassTable" class="table table-striped table-bordered table-hover"></table>
    </div>
  </div>
</div>

<!-- 增加form -->
<div id="dialogRoleDiv"></div>
<div id="roleDialog" class="hide">
  <form  method="post" class="form-horizontal" role="form" id="role-form" style="margin-top: 15px;" >
    <input type="hidden" name="id" id="id">
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 学科名称: </label>
      <div class="col-xs-12 col-sm-9">
	      <div class="clearfix">
	        <input type="text" name="menuName" style="margin-right: 15px;"  class="col-xs-10 col-sm-8 {required:true,minlength:2,maxlength:7}">
	      </div>
      </div>
    </div>
    <div class="space-4"></div>
    <div class="form-group">
      <label class="col-xs-12 col-sm-3 control-label no-padding-right"><i class="text-danger"></i> 备注: </label>
      <div class="col-xs-12 col-sm-9">
	      <div class="clearfix">
	        <textarea class="col-xs-10 col-sm-8 " name="remark" id="remark" maxlength = "100" style="margin-top:10px" rows="6"></textarea>
      	 </div>
      </div>
    </div>
  </form>
</div>


<!-- 修改form -->
<div id="updateDialogDiv"></div>
<div id="updateDialog" class="hide">
    <form action="user/role/add" method="post" class="form-horizontal" role="form" id="update-form" style="margin-top: 15px;">
        <input type="hidden" name="id" id="update_id">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 学科名称: </label>
            <div class="col-sm-9">
                <input type="text" id="update_menuName" name="menuName"   style="margin-right: 15px;" class="col-xs-10 col-sm-8 {required:true,minlength:2,maxlength:7}">
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger"></i> 备注: </label>
            <div class="col-sm-9">
                <textarea class="col-xs-10 col-sm-8" name="remark" id="update_remark" maxlength = "100" style="margin-top:10px"  rows="6"></textarea>
            </div>
        </div>
    </form>
</div>


<!-- 查看 -->
<div id="previewCloudClasMenuDialogDiv"></div>
<div id="previewCloudClasMenuDialog" class="hide" >
    <form class="form-horizontal"  method="post" action="" style="width:500px;margin-top: 15px;">
        <div class="form-group row">
            <label class="col-sm-6 control-label no-padding-right"><span style="font-weight: bold;">学科名称:</span> </label>
            <div class="col-sm-5">
               <p id="show_menuName" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-6 control-label no-padding-right"><span style="font-weight: bold;">状态:</span> </label>
            <div class="col-sm-5">
               <p id="show_status" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-6 control-label no-padding-right"><span style="font-weight: bold;">排序:</span> </label>
            <div class="col-sm-5">
               <p id="show_sort" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-6 control-label no-padding-right"><span style="font-weight: bold;">创建人:</span> </label>
            <div class="col-sm-5">
               <p id="show_createPerson" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-6 control-label no-padding-right"><span style="font-weight: bold;">创建时间:</span> </label>
            <div class="col-sm-5">
               <p id="show_createTime" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-6 control-label no-padding-right"><span style="font-weight: bold;">包含的课程类别:</span> </label>
            <div class="col-sm-5">
               <p id="show_childMenuNames" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-6 control-label no-padding-right"><span style="font-weight: bold;">包含的课程:</span> </label>
            <div class="col-sm-5">
               <p id="show_courseName" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-6 control-label no-padding-right"><i class="text-danger"></i><span style="font-weight: bold;"> 备注:</span> </label>
            <div class="col-sm-5">
            	<p id="show_remark" class="paddingtop7px padding7" style="width: 400px;overflow: hidden;white-space: normal;word-wrap: break-word;"></p>
            </div>
        </div>
    </form>
</div>



<!-- 查看 -->
<div id="childMenuDialogDiv"></div>
<div id="childMenuDialog" class="hide" >
       
    <form class='form-horizontal' id="childMenu-form"  method="post"  action="">
     <input type="hidden" name="parentId" id="parentId"/>
    <div class='form-group'>
    <div class='col-sm-3 control-label no-padding-right'><b>学科名称:</b></div>
    <div id="child_MenuName" class='col-sm-8 paddingtop7px padding7'>ui</div>
    </div>
        <div class='form-group'>
            <div class='col-sm-3 control-label no-padding-right'><b>关联课程类别:</b></div>
            <div class='col-sm-8'>
                <table id="childMenus">

                </table>
            </div>
        </div>
   </form>
</div>


<!-- 博问答学科权限控制form -->
<div id="dialogLimitDiv"></div>
<div id="LimitDialog" class="hide">
  <form  method="post" class="form-horizontal" role="form" id="limit-form" style="margin-top: 15px;" >
    <input type="hidden" name="id" id="LimitMenuid">
    <div class="form-group">
   
       <label class="col-sm-5 control-label no-padding-right"><span style="font-weight: bold;">学科名称:</span> </label>
       <div class="col-sm-5">
          <p id="limitMenu" class="paddingtop7px padding7"></p>
       </div>
    </div>
    <div class="space-4"></div>
    <div class="form-group">
     	<label class="col-sm-5 control-label no-padding-right"><span style="font-weight: bold;">问答权限设置: </span></label>
		<div class="col-sm-3">
			<input type="hidden" name="nouse" id="nouse" value="nouse" autofocus="autofocus">
		 	<p class="paddingtop7px padding7">
		 		<label style="cursor: pointer;"><input type="radio" id="allLimit" name="askLimit"   value="1" style="cursor: pointer;vertical-align:text-top;margin-top:2px;margin-left:2px;margin-right:5px">全部公开</label>
		 	</p>
        	<p class="paddingtop7px padding7">
        		<label style="cursor: pointer;"><input type="radio" id="partLimit" name="askLimit"  value="0" style="cursor: pointer;vertical-align:text-top;margin-top:2px;margin-left:2px;margin-right:5px">只对付费课程公开</label>
        	</p>
        </div>
     	
    </div>
  </form>
</div>

<!--权限配置  -->
<div id="dialogConfigDiv"></div>
<div id="configDialog" class="hide">
  <table style="width:100%">
    <tr>
      <td style="vertical-align: top;">
        <div style="text-align: center"><label for="form-field-select-2">选择资源</label></div>
        <div class="contrightbox"
             style="overflow-y: auto; overflow-x: auto;height: 300px;min-width:240px;">
          <div class="zTreeDemoBackground">
            <ul id="resource" class="ztree"
                style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
          </div>
        </div>
      </td>
      <td style="vertical-align: top;">
        <div>
          <div style="text-align: center"><label for="form-field-select-2">已选择资源</label></div>
          <div>
            <div class="contrightbox"
                 style="overflow-y: auto; overflow-x: auto;height: 300px;min-width:240px;">

              <div class="zTreeDemoBackground">
                <ul id="resource2" class="ztree"
                    style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
              </div>
            </div>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>

<script type="text/javascript" src="${base}/js/ask/menu.js?ver=1.0"></script>