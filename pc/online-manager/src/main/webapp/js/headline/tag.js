var searchCase = new Array();
var tagTable;
var addTagForm;


$(function () {

    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            return false;
        }
    }


    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [{
        "title": checkbox,
        "class": "center",
        "width": "3%",
        "sortable": false,
        "data": 'id',
        "mRender": function (data, display, row) {
            return '<input type="checkbox" value=' + data + ' class="ace" /><span class="lbl"></span>';
        }
    },
        {"title": "标签名称", "class": "center", "width": "20%", "sortable": false, "data": "name"},
        {"title": "文章数", "class": "center", "width": "4%", "sortable": false, "data": "articleCnt"},
        {
            "title": "<div class='yingyongshu'><span>引用数(</span><span class='yingyongshuValue'>最近一个月</span><span>)</span><span id='yinyongshuSanjiao' class='sanjiao'></span><ul id='yinyongshulist'><li data-month='1' >最近一个月</li><li data-month='3'>最近三个月</li><li data-month='6'>最近六个月</li></ul></div>",
            "class": "center", "width": "8%", "sortable": false, "data": "articleCntLately"
        },
        {"title": "创建日期", "class": "center", "width": "5%", "sortable": false, "data": 'createTime'},
        {
            "title": "状态",
            "class": "center",
            "width": "5%",
            "sortable": false,
            "data": 'status',
            "mRender": function (data, display, row) {
                return row.status == 1 ? "已启用" : "已禁用";
            }
        },
        {
            "sortable": false,
            "data": "id",
            "class": "center",
            "width": "4%",
            "title": "操作",
            "mRender": function (data, display, row) {
                var buttons = '<div class="hidden-sm hidden-xs action-buttons">'

                buttons += '<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
                if (row.status == 1) {
                    buttons += '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
                } else {
                    buttons += '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
                }
                return buttons;
            }
        }];

    tagTable = initTables("tagTable", basePath + "/headline/tag/findTagList", objData, true, true, 0, null, searchCase, function (data) {

    });

    addTagForm = $("#addTag-form").validate({
        messages: {
            name: {
                required: "标签不能为空！"
            },
        }
    });

    $(document).click(function () {
        $("#yinyongshulist").css("display", "none");
        $("#yinyongshuSanjiao").toggleClass("sanjiao sanjiao2");
        if ($("#yinyongshulist").css("display") == "none") {
            $("#yinyongshuSanjiao").removeClass("sanjiao2").addClass("sanjiao");
        }
    });
});

$(".yingyongshu").click(function (event) {
    event.stopPropagation();
    $("#yinyongshulist").toggle();
    $("#yinyongshuSanjiao").toggleClass("sanjiao sanjiao2");

});

$("#yinyongshulist li").on("click", function (event) {
    event.stopPropagation();
    var yingyongshuTarget = $(this);
    $(".yingyongshuValue").text($(this).text());

    $("#yinyongshulist").css("display", "none");
    var month = $(yingyongshuTarget).attr("data-month");
    $("#monthSort").val(month);
    searchCase.push('{"tempMatchType":"10","propertyName":"monthSort","propertyValue1":"' + month + '","tempType":"String"}');

    searchButton(tagTable, searchCase);

});

//新增框
$(".add_bx").click(function () {
    addTagForm.resetForm();
    var dialog = openDialog("addTagDialog", "dialogAddTagDiv", "新增标签", 580, 200, true, "确定", function () {
        if ($("#addTag-form").valid()) {
            mask();
            $("#addTag-form").attr("action", basePath + "/headline/tag/addTag");
            $("#addTag-form").ajaxSubmit(function (data) {
                try {
                    data = jQuery.parseJSON(jQuery(data).text());
                } catch (e) {
                    data = data;
                }
                unmask();
                if (data.success) {
                    $("#addTagDialog").dialog("close");
                    layer.msg(data.errorMessage);
                    freshTable(tagTable);
                } else {
                    layer.msg(data.errorMessage);
                }
            });
        }
    });
});


function deleteBatch() {
    debugger
    var aa = $("#tagTable tbody input[type=checkbox]:checked").length;
    for (var i = 0; i < $("#tagTable tbody input[type=checkbox]:checked").length; i++) {
        var oo = $("#tagTable tbody input[type=checkbox]:checked").eq(i).parent().parent();
        var aData = tagTable.fnGetData(oo);
        if (aData.articleCnt > 0) {
            alertInfo("标签[" + aData.name + "]被引用，不能删除！");
            return false;
        }
    }
    deleteAll(basePath + "/headline/tag/deletes", tagTable);
}

function search(sortType) {
    searchCase.push('{"tempMatchType":"7","propertyName":"sortType","propertyValue1":"' + sortType + '","tempType":"String"}');

    searchCase.push('{"tempMatchType":"10","propertyName":"monthSort","propertyValue1":"' + $("#monthSort").val() + '","tempType":"String"}');

    searchButton(tagTable, searchCase);
    searchCase.pop();
}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj) {
    var oo = $(obj).parent().parent().parent();
    var aData = tagTable.fnGetData(oo);
    ajaxRequest(basePath + "/headline/tag/updateStatus", {"id": aData.id}, function () {
        freshTable(tagTable);
    });
}

//修改标签
function toEdit(obj) {
    var oo = $(obj).parent().parent().parent();
    var aData = tagTable.fnGetData(oo); // get datarow
    $("#editId").val(aData.id);
    $("#editName").val(aData.name);
    openDialog("editTagDialog", "dialogEditTagDiv", "修改标签", 580, 200, true, "提交", function () {
        if ($("#update-form").valid()) {
            mask();
            $("#update-form").attr("action", basePath + "/headline/tag/update");
            $("#update-form").ajaxSubmit(function (data) {
                unmask();
                if (data.success) {
                    $("#editTagDialog").dialog("close");

                    layer.msg(data.errorMessage);
                    freshTable(tagTable);
                } else {
                    layer.msg(data.errorMessage);
                }
            });
        }
    });
}


