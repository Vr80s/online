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

<div class="page-header">
		当前位置：权限管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
		</small>
		<span> 角色管理 </span>
</div>

    <div class="mainrighttab tabresourse bordernone">
        <p class="col-xs-4" style="padding: 0;">
            <button class="btn btn-sm btn-success add_bx"><i class="glyphicon glyphicon-plus"></i> 新增角色</button>
            <button class="btn btn-sm btn-success dele_bx"><i class="glyphicon glyphicon-trash"></i> 批量删除</button>
        </p>
        <!-- 
        <div class="col-xs-6 paddingright0px text-right" id="searchDiv">
            <div class="searchTr">
                <span>
                    状态：
                    <select class="propertyValue1" id="" class="secheck1">
	                    <option value="">所有</option>
	                    <option value="0">启用</option>
	                    <option value="1">禁用</option>
	                </select>
	                <input type="hidden" value="is_delete" class="propertyName"/>
                </span>
                <button class="btn-danger btn btn-sm" onclick="search()"><span class="glyphicon glyphicon-search"></span></button>
            </div>
        </div>
         -->
        <div class="row">
           <div class="col-xs-12">
	           <table id="roleTable" class="table table-striped table-bordered table-hover"></table>
          </div>
        </div>
    </div>

<!-- 增加form -->
<div id="dialogRoleDiv"></div>
<div id="roleDialog" class="hide">
    <form action="user/role/add" method="post" class="form-horizontal" role="form" id="role-form">
    		<input type="hidden" name="id" id="id">
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 角色代码: </label>
            <div class="col-sm-9">
                <input type="text" name="code" id="code" maxlength="20" class="col-xs-10 col-sm-8 {required:true,rangelength:[2,20]}">
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 角色名称: </label>
            <div class="col-sm-9">
                <input type="text" name="name" id="name" maxlength="20" class="col-xs-10 col-sm-8 {required:true,rangelength:[2,20]}">
            </div>
        </div>
        <!-- 
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"> 状态: </label>
            <div class="col-sm-9">
                <p style="padding-top:6px">
                    <input type="radio" id="delete_false" name="delete" value="0" class="aa" checked>
                    启用
                    &nbsp;&nbsp;&nbsp;
                    <input type="radio" id="delete_true" name="delete"  value="1">
                    禁用
                </p>
            </div>
        </div>
         -->
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
    
<script type="text/javascript" src="${base}/js/user/role.js?ver=1.2"></script>