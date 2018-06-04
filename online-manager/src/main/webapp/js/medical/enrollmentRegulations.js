var exotericIntakeTable;//师承列表

$(function(){
	/** 师承列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    searchCase_P.push('{"tempMatchType":undefined,"propertyName":"type","propertyValue1":"'+$("#type").val()+'","tempType":undefined}');
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "师承标题", "class":"center","width":"9%","sortable":false,"data": 'title' },
    { "title": "城市", "class":"center","width":"8%","sortable":false,"data": 'studyAddress'},
	{ "title": "老师", "class":"center","width":"8%","sortable":false,"data": 'doctorName'},
    { "title": "学费", "class":"center","width":"6%", "sortable":false,"data": 'tuition'},
    { "title": "招生人数", "class":"center","width":"6%", "sortable":false,"data": 'countLimit'},
	{ "title": "报名人数", "class":"center","width":"6%", "sortable":false,"data": 'countPeople'},
	{ "title": "报名截止时间", "class":"center","width":"8%","sortable":false,"data": 'deadline'},
	{ "title": "学习时间", "class":"center","width":"12%","sortable":false,"data": 'startTime','mRender':function(data, display, row){
		return data="<span name='zt'>"+row.startTime+"-"+row.endTime+"</span>";}
	},
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data) {
            if(data==1){
                return data="<span name='zt'>已启用</span>";
            }else{
                return data="<span name='zt'>已禁用</span>";
            }
        } },
    { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
            if(row.status){
                return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="viewDetails(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                    '<a class="blue" href="javascript:void(-1);" title="编辑" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                    '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '+
                    '</div>'
            }else {
                return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="viewDetails(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                    '<a class="blue" href="javascript:void(-1);" title="编辑" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                    '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '+
                    '</div>'
            }

	    }
	}];
    exotericIntakeTable = initTables("exotericIntakeTable",basePath+"/apprentice/enrollmentRegulationsList",objData,true,true,0,null,searchCase_P,function(data){

	});
	/** 师承列表end */

	createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
});

/**
 * 医师列表搜索
 */
function search_P(){
    var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
	searchButton(exotericIntakeTable,json);
};

//查看
function  viewDetails(obj){
    var oo = $(obj).parent().parent().parent();
    var row = exotericIntakeTable.fnGetData(oo); // get datarow
    window.location.href = basePath + '/home#apprentice/enrollmentRegulationsDetail?id=' + row.id;

}

//修改页面
function toEdit(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = exotericIntakeTable.fnGetData(oo); // get datarow
    window.location.href = basePath + '/home#apprentice/toEdit?id=' + row.id;
}

//添加页面
function toAdd() {
    window.location.href = basePath + '/home#apprentice/toAdd';
}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj,status){
    var oo = $(obj).parent().parent().parent();
    var row = exotericIntakeTable.fnGetData(oo); // get datarow

    ajaxRequest(basePath+"/apprentice/updateStatus",{"id":row.id},function(data){
        layer.msg(data.resultObject);
        freshTable(exotericIntakeTable);
    });
};