var articleTable;
var searchCase;
var searchJson = new Array();
$(function () {
    searchCase = [];
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
            "title": "文章标题",
            "class": "center",
            "width": "12%",
            "sortable": false,
            "data": "title",
            "mRender": function (data, display, row) {
                return data.replace(/</g, '&lt;').replace(/>/g, '&gt;');
            }
        },
        {"title": "分类", "class": "center", "width": "6%", "sortable": false, "data": "typeName"},
        {"title": "引用的标签", "class": "center", "width": "10%", "sortable": false, "data": "tagName"},
        {"title": "作者", "class": "center", "width": "8%", "sortable": false, "data": "author"},
        {"title": "阅读量", "class": "center", "width": "6%", "sortable": false, "data": "browseSum"},
        {"title": "点赞数", "class": "center", "width": "6%", "sortable": false, "data": "praiseSum"},
        {"title": "评论数", "class": "center", "width": "6%", "sortable": false, "data": "commentSum"},
        {"title": '更新时间', "class": "center", "width": "8%", "data": 'createTime', "sortable": false},
        {"title": '医师作者', "class": "center", "width": "8%", "data": 'doctorAuthor', "sortable": false},
        {"title": '报道医师', "class": "center", "width": "8%", "data": 'reportDoctor', "sortable": false},
        {"title": '推荐值', "class": "center", "width": "6%", "data": 'sort', "sortable": false},
        {"title": '推荐时效', "class": "center", "width": "6%", "data": 'recommendTime', "sortable": false},
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
            "width": "15%",
            "title": "操作",
            "mRender": function (data, display, row) {
                var str = "<div class=\"hidden-sm hidden-xs action-buttons\">";
                if (row.typeName == '大家专栏' && !row.userCreated) {
                    str += '<a class="blue" href="javascript:void(-1);" title="医师作者" onclick="openAuthorManage(this)"><i class="glyphicon glyphicon-user"></i></a>';
                } else {
                    str += '<a class="gray" href="javascript:void(-1);" title="医师作者" ><i class="glyphicon glyphicon-user"></i></a>';
                }
                if (row.typeName == '名医报道' && !row.userCreated) {
                    str += '<a class="blue" href="javascript:void(-1);" title="报道医师" onclick="openReportManage(this)"><i class="glyphicon glyphicon-camera"></i></a>';
                } else {
                    str += '<a class="gray" href="javascript:void(-1);" title="报道医师" ><i class="glyphicon glyphicon-camera"></i></a>';
                }
                str += '<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
                if (row.status == "1") {
                    str += '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a>';
                } else {
                    str += '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a>';
                }
                str += '<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this)">设置推荐值</a>';

                return str;
            }
        }
    ];

    articleTable = initTables("articleTable", basePath + "/headline/article/list", objData, true, true, 0, null, searchCase, function (data) {

    })
});

//新增框
$(".add_bx").click(function () {
    turnPage(basePath + '/home#headline/article/toAdd');
});

//修改页面
function toEdit(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    turnPage(basePath + '/home#headline/article/toEdit?id=' + row.id + "&typeId=" + row.typeId + "&typeName=" + row.typeName + "&tagId=" + row.tagId + "&author=" + row.author + "&tagName=" + encodeURIComponent(row.tagName));
}

/**
 * 批量删除
 *
 */

$(".dele_bx").click(function () {
    deleteAll(basePath + "/headline/article/deletes", articleTable);
});

function updateStatus(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    ajaxRequest(basePath + "/headline/article/updateStatus", {"id": row.id}, function () {
        freshTable(articleTable);
    });
}

/**
 * Description：设置推荐值
 * @Date: 2018/3/9 14:11
 **/
function updateRecommendSort(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    $("#UpdateRecommendSort_id").val(row.id);
    var dialog = openDialog("UpdateRecommendSortDialog", "dialogUpdateRecommendSortDiv", "修改推荐值", 350, 300, true, "确定", function () {
        if ($("#UpdateRecommendSortFrom").valid()) {
            mask();
            $("#UpdateRecommendSortFrom").attr("action", basePath + "/headline/article/updateRecommendSort");
            $("#UpdateRecommendSortFrom").ajaxSubmit(function (data) {
                data = getJsonData(data);
                unmask();
                if (data.success) {
                    $("#recommendSort").val("");
                    $("#recommendTime").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                    freshTable(articleTable);
                } else {
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};

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
};

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
        checckboxSingle();
    }
}

function drawReportPage(data) {
    $("#childMenus").html("");
    for (var i = 0; i < data.length; i++) {
        var rowData = "<tr id='childMenus_tr_" + data[i].id + "'><td> ";
        if (data[i].has) {
            rowData += "<input style='margin-top:-1px;cursor: pointer;' type='checkbox' name='doctorIds'  checked='checked'' value='" + data[i].id + "' id='childMenuNames_" + i + "' /></td><td><label style='cursor: pointer;' for='childMenuNames_" + i + "'>" + data[i].name + "</label></td>";
        } else {
            rowData += "<input style='margin-top:-1px;cursor: pointer;' type='checkbox' name='doctorIds'  value='" + data[i].id + "' id='childMenuNames_" + i + "' /></td><td><label style='cursor: pointer;' for='childMenuNames_" + i + "'>" + data[i].name + "</label></td>";
        }
        rowData += "</td>";
        rowData += "<td>";
        rowData += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        rowData += "</td>";
        rowData += "</tr>";
        $("#childMenus").append(rowData);
        checckboxSingle();
    }
}

function openAuthorManage(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    rowId = row.id;
    $("#parentId").val(row.id);
    $("#child_MenuName").html(row.title);
    ajaxRequest(basePath + "/medical/doctor/allListForArticle", {'articleId': row.id}, function (data) {
        drawMenusPage(data);

        $("#childMenu-form").attr("action", basePath + "/medical/doctor/addDoctorAuthorArticle");
        openDialog("childMenuDialog", "childMenuDialogDiv", "关联医师作者", 580, 450, true, "提交", function () {
            $("input:checkbox").removeAttr("disabled");
            mask();

            $("#childMenu-form").ajaxSubmit(function (data) {
                unmask();
                data = getJsonData(data);
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

function openReportManage(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    rowId = row.id;
    $("#parentId").val(row.id);
    $("#child_MenuName").html(row.title);
    var courseCount = row.courseCount
    ajaxRequest(basePath + "/medical/doctor/allListForReport", {'articleId': row.id}, function (data) {
        drawReportPage(data);

        $("#childMenu-form").attr("action", basePath + "/medical/doctor/addDoctorReport");
        openDialog("childMenuDialog", "childMenuDialogDiv", "关联报道医师", 580, 450, true, "提交", function () {
            $("input:checkbox").removeAttr("disabled");
            mask();

            $("#childMenu-form").ajaxSubmit(function (data) {
                unmask();
                data = getJsonData(data);
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