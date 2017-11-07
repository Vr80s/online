<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="treeGrid"></div>

<link rel="stylesheet" href="js/jqwidgets/styles/jqx.base.css" type="text/css" />
<link rel="stylesheet" href="js/jqwidgets/styles/jqx.energyblue.css" type="text/css" />
<link rel="stylesheet" href="js/jqwidgets/styles/jqx.darkblue.css" type="text/css" />
<link rel="stylesheet" href="js/jqwidgets/styles/jqx.blackberry.css" type="text/css" />
<link rel="stylesheet" href="js/jqwidgets/styles/jqx.mobile.css" type="text/css" />
<script type="text/javascript" src="js/jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="js/jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="js/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="js/jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="js/jqwidgets/jqxdatatable.js"></script>
<script type="text/javascript" src="js/jqwidgets/jqxtreegrid.js"></script>
<script type="text/javascript">
/**
 * @param url  请求后台的url
 * @param dataFields 映射对象
 * @param columns  列对象
 * @param isExpand  是否展开树
 * @param isPage  是否显示分页
 */
function loadTreeGrid(url,dataFields,columns,isExpand,isPage){
	if(isnull(isPage)){
		isPage = false;
	}
	var children = { name : 'children', type : 'array' };
	dataFields.push(children);
	if(isExpand){
		var expanded = { name : 'expanded', type : 'bool' };
		dataFields.push(expanded);
	}
	var source = {
			dataType : "json",
			dataFields : dataFields,
			hierarchy : {
				root : 'children'
			},
			id : 'id',
			url : url
		};
	var dataAdapter = new $.jqx.dataAdapter(source);
	$("#treeGrid").jqxTreeGrid({
		altRows: true,
		width : '100%',
		height : '80%',
		source : dataAdapter,
		sortable : true,
		pageable : isPage,
		pageSize : 15,
		pageSizeOptions : [ '15', '20', '30' ],
		pagerHeight : 52,
		checkboxes : true,
		columnsResize : true,
		theme : 'energyblue',
		columns : columns
	});
}
</script>