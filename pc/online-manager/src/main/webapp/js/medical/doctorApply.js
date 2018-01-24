var P_courseTable;//职业课列表
var courseForm;//添加课程表单

$(function() {
    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            return false;
        }
    }
    /** 职业课列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    // searchCase_P.push('{"tempMatchType":undefined,"propertyName":"type","propertyValue1":"' + $("#type").val() + '","tempType":undefined}');
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [{
        "title": checkbox,
        "class": "center",
        "width": "5%",
        "sortable": false,
        "data": 'id',
        "mRender": function (data, display, row) {
            return '<input type="checkbox" value=' + data + ' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>' + row.teachMethodName + '</span>';
        }
    },
        {"title": "医师名字", "class": "center", "width": "6%", "sortable": false, "data": 'name'},
        {"title": "职称", "class": "center", "width": "6%", "sortable": false, "data": 'title'},
        {"title": "身份证号", "class": "center", "width": "8%", "sortable": false, "data": 'cardNum'},
        // {"title": "科室", "class": "center", "width": "12%", "sortable": false, "data": 'departments'},
        {"title": "擅长", "class": "center", "width": "8%", "sortable": false, "data": 'field'},
        {
            "title": "申请时间",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'createTime',
            "mRender": function (data) {
                return getLocalTime(data);
            }
        },
        {
            "title": "审核状态",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'status',
            "mRender": function (data, display, row) {
                if (data == 2) {
                    return "待审核";
                } else if (data == 1) {
                    return '<span style="color: #13da08;">已通过</span>';
                }
                return '<span style="color: #da3346;">未通过</span>';
            }
        },
        {
            "sortable": false,
            "class": "center",
            "width": "12%",
            "title": "操作",
            "mRender": function (data, display, row) {
                if (row.status == 0) {
                    return '<div class="hidden-sm hidden-xs action-buttons">' +
                        '<a class="blue" href="javascript:void(-1);" title="审核" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
                } else {
                    return '<div class="hidden-sm hidden-xs action-buttons">' +
                        '<a class="blue" href="javascript:void(-1);" title="审核" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
                }
            }
        }];

    P_courseTable = initTables("courseTable", basePath + "/medical/doctor/apply/list", objData, true, true, 0, null, searchCase_P, function (data) {
    });
});
/** 职业课列表end */

function showDetailDialog(obj, status) {
    var oo = $(obj).parent().parent().parent();
    var aData;
    if (status == 1) {
        aData = P_courseTable.fnGetData(oo); // get datarow
        // page = getCurrentPageNo(P_courseTable);
    }
    window.location.href = basePath + '/home#medical/doctor/apply/' + aData.id;
}



function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}