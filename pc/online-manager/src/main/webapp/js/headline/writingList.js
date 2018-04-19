var articleTable;
var searchCase;
var searchJson = new Array();
$(function () {
    createDatePicker($(".datetime-picker"), "yy-mm-dd");

    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [{
        "title": checkbox,
        "class": "center",
        "width": "4%",
        "sortable": false,
        "data": 'id',
        "mRender": function (data, display, row) {
            return '<input type="checkbox" value=' + data + ' class="ace" /><span class="lbl"></span>';
        }
    },
        {
            "title": "书名",
            "class": "center",
            "width": "15%",
            "sortable": false,
            "data": "title",
            "mRender": function (data, display, row) {
                return data.replace(/</g, '&lt;').replace(/>/g, '&gt;');
            }
        },
        {"title": "作者", "class": "center", "width": "8%", "sortable": false, "data": "author"},
        {"title": "关联医师", "class": "center", "width": "8%", "sortable": false, "data": "doctorName"},
        {"title": "评论数", "class": "center", "width": "6%", "sortable": false, "data": "commentSum"},
        {"title": '更新时间', "class": "center", "width": "9%", "data": 'createTime', "sortable": false},
        {
            "title": "状态",
            "class": "center",
            "width": "7%",
            "sortable": false,
            "data": "status",
            "mRender": function (data, display, row) {
                if (data == 1) {
                    return data = "已启用";
                } else {
                    return data = "已禁用";
                }
            }
        },
        {
            "sortable": false,
            "class": "center",
            "width": "8%",
            "title": "操作",
            "mRender": function (data, display, row) {
                var str = "<div class=\"hidden-sm hidden-xs action-buttons\">";
                str += '<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
                if (!row.userCreated) {//用户创建的文章不允许在后台关联医师
                    str += '<a class="blue" href="javascript:void(-1);" title="关联医师" onclick="openDoctorManage(this)"><i class="glyphicon glyphicon-camera"></i></a>';
                }

                if (row.status == "1") {
                    str += '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a>';
                } else {
                    str += '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a>';
                }
                return str;
            }
        }
    ];

    articleTable = initTables("articleTable", basePath + "/headline/writing/list", objData, true, true, 0, null, searchCase, function (data) {

    })
});

//新增框
$(".add_bx").click(function () {
    turnPage(basePath + '/home#headline/writing/toAdd');
});

//修改页面
function toEdit(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    turnPage(basePath + '/home#headline/writing/toEdit?id=' + row.id + "");
}

/**
 * 著作关联医师
 * @param obj
 */
function openDoctorManage(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    rowId = row.id;
    $("#parentId").val(row.id);
    $("#child_MenuName").html(row.title);
    ajaxRequest(basePath + "/medical/doctor/getMedicalDoctor", {'writingsId': row.id, 'type': 2}, function (data) {
        drawMenusPage(data);

        $("#childMenu-form").attr("action", basePath + "/headline/writing/updateMedicalDoctorWritings");

        openDialog("childMenuDialog", "childMenuDialogDiv", "关联的医师", 580, 450, true, "提交", function () {
            $("input:checkbox").removeAttr("disabled");
            mask();

            $("#childMenu-form").ajaxSubmit(function (data) {
                unmask();
                try {
                    data = jQuery.parseJSON(jQuery(data).text());
                } catch (e) {
                    data = data;
                }
                if (data.success) {
                    $("#childMenuDialog").dialog("close");
                    layer.msg(data.resultObject);
                    freshTable(articleTable);
                } else {
                    layer.msg(data.errorMessage);
                }
            });

        });
    });
}

/**
 * 批量删除
 *
 */
$(".dele_bx").click(function () {
    deleteAll(basePath + "/headline/writing/deletes", articleTable);
});

/**
 * 更改转台
 * @param obj
 */
function updateStatus(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    ajaxRequest(basePath + "/headline/writing/updateStatus", {"id": row.id}, function () {
        freshTable(articleTable);
    });
}

function search() {
    var startTime = $("#startTime").val(); //开始时间
    var stopTime = $("#stopTime").val(); //结束时间
    if (startTime != "" || stopTime != "") {

        if (startTime != "" && stopTime != "" && startTime > stopTime) {
            alertInfo("开始日期不能大于结束日期");
            return;
        }
        searchJson.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
        searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
    }
    searchButton(articleTable, searchJson);
}

function drawMenusPage(data) {
    $("#childMenus").html("");
    for (var i = 0; i < data.length; i++) {
        var rowData = "<tr id='childMenus_tr_" + data[i].id + "'><td> ";
        if (data[i].has) {
            rowData += "<input style='margin-top:-1px;cursor: pointer;' type='checkbox' name='doctorId'  checked='checked'' value='" + data[i].id + "' id='childMenuNames_" + i + "' /></td><td><label style='cursor: pointer;' for='childMenuNames_" + i + "'>" + data[i].name + "</label></td>";
        } else {
            rowData += "<input style='margin-top:-1px;cursor: pointer;' type='checkbox' name='doctorId'  value='" + data[i].id + "' id='childMenuNames_" + i + "' /></td><td><label style='cursor: pointer;' for='childMenuNames_" + i + "'>" + data[i].name + "</label></td>";
        }
        rowData += "</td>";
        rowData += "<td>";
        rowData += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        rowData += "</td>";
        rowData += "</tr>";
        $("#childMenus").append(rowData);
    }
}

function checckboxSingle() {
    $(':checkbox[name=doctorId]').each(function () {
        $(this).click(function () {
            if (this.checked) {
                $(':checkbox[name=doctorId]').removeAttr('checked');
                $(this).prop('checked', 'checked');
            }
        });
    });
}