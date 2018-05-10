var articleTable;
var searchCase;
var searchJson = new Array();
var typeSearch = '{"tempMatchType":"9","propertyName":"search_type","propertyValue1":"7","tempType":"String"}';
$(function () {
    createDatePicker($(".datetime-picker"), "yy-mm-dd");
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [{
        "title": checkbox,
        "class": "center",
        "width": "4%",
        "sortable": false,
        "data": 'id',
        "mRender": function (data) {
            return '<input type="checkbox" value=' + data + ' class="ace" /><span class="lbl"></span>';
        }
    },
        {"title": "ID", "class": "center", "width": "4%", "sortable": false, "data": "id"},
        {
            "title": "文章标题",
            "class": "center",
            "width": "15%",
            "sortable": false,
            "data": "title",
            "mRender": function (data) {
                return data.replace(/</g, '&lt;').replace(/>/g, '&gt;');
            }
        },
        {"title": "分类", "class": "center", "width": "8%", "sortable": false, "data": "typeName"},
        {"title": "分类ID", "class": "center", "width": "15%", "sortable": false, "data": "typeId", "visible": false},
        {"title": "引用的标签", "class": "center", "width": "10%", "sortable": false, "data": "tagName"},
        {"title": "引用的标签ID", "class": "center", "width": "10%", "sortable": false, "data": "tagId", "visible": false},
        {"title": "作者", "class": "center", "width": "8%", "sortable": false, "data": "author"},
        {"title": "阅读量", "class": "center", "width": "6%", "sortable": false, "data": "browseSum"},
        {"title": "点赞数", "class": "center", "width": "6%", "sortable": false, "data": "praiseSum"},
        {"title": "评论数", "class": "center", "width": "6%", "sortable": false, "data": "commentSum"},
        {"title": '更新时间', "class": "center", "width": "9%", "data": 'createTime', "sortable": false},
        {"title": '报道医师', "class": "center", "width": "9%", "data": 'reportDoctor', "sortable": false},
        {
            "title": "状态",
            "class": "center",
            "width": "7%",
            "sortable": false,
            "data": "status",
            "mRender": function (data) {
                if (data == 1) {
                    return "已启用";
                } else {
                    return "已禁用";
                }
            }
        },

        {
            "title": "是否推荐",
            "class": "center",
            "width": "7%",
            "sortable": false,
            "data": "isRecommend",
            "mRender": function (data) {
                if (data) {
                    return "推荐";
                } else {
                    return "未推荐";
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
                if (row.status == "1") {
                    str += '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a>';
                } else {
                    str += '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a>';
                }
                if (!row.isRecommend) {
                    str += '<a class="blue" href="javascript:void(-1);" title="推荐" onclick="recommendDialog(this)"><i class="ace-icon glyphicon glyphicon-fire bigger-130"></i></a>';
                } else {
                    str += '<a class="gray" href="javascript:void(-1);" title="取消推荐" onclick="recommendDialog(this)"><i class="ace-icon glyphicon glyphicon-fire bigger-130"></i></a>';
                }
                return str;
            }
        }
    ];

    articleTable = initTables("articleTable", basePath + "/headline/article/list", objData, true, true, 0, null, [typeSearch], function (data) {

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

function recommendDialog(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = articleTable.fnGetData(oo); // get datarow
    ajaxRequest(basePath + "/headline/article/recommend", {"id": row.id}, function (res) {
        if (res.success) {
            layer.msg(res.resultObject);
            freshTable(articleTable);
        } else {
            layer.msg(res.errorMessage);
        }

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
    searchJson.push(typeSearch);
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
        checckboxSingle();
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