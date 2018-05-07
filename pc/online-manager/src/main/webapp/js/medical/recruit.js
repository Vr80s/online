var recruitTable;//招聘信息列表
var addRecruitForm;//添加招聘表单
var updateRecruitForm;//修改招聘表单
var years = ["不限", "0-1年", "1-3年", "3-5年", "5-10年", "10年以上"];
$(function () {
    /** 招聘信息列表begin */
    addRecruitForm = $('#addRecruitForm');
    updateRecruitForm = $('#updateRecruitForm');
    var searchCase_P = [];
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    searchCase_P.push('{"tempMatchType":undefined,"propertyName":"hospitalId","propertyValue1":"' + hospitalId + '","tempType":undefined}');
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
        {"title": "职位", "class": "center", "width": "9%", "sortable": false, "data": 'position'},
        {
            "title": "工作经验",
            "class": "center",
            "width": "6%",
            "sortable": false,
            "data": 'years',
            "mRender": function (data, display, row) {
                return getYears(data);
            }
        },
        {"title": "岗位职责", "class": "center", "width": "6%", "sortable": false, "data": 'postDuties', "visible": true},
        {
            "title": "职位要求",
            "class": "center",
            "width": "6%",
            "sortable": false,
            "data": 'jobRequirements',
            "visible": true
        },
        {
            "title": "创建日期",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'createTime',
            'mRender': function (data, display, row) {
                return getLocalTime(data);
            }
        },
        {
            "title": "状态",
            "class": "center",
            "width": "6%",
            "sortable": false,
            "data": 'status',
            "mRender": function (data, display, row) {
                if (data) {
                    return "<span name='zt'>已启用</span>";
                } else {
                    return "<span name='zt'>已禁用</span>";
                }
            }
        },
        {
            "sortable": false,
            "class": "center",
            "width": "5%",
            "title": "排序",
            "mRender": function (data, display, row) {
                if (row.status) {
                    return '<div class="hidden-sm hidden-xs action-buttons">' +
                        '<a class="blue" href="javascript:void(-1);" title="上移1" onclick="upMove(this)" name="up_PX"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>' +
                        '<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)" name="down_PX"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
                } else {
                    return '<div class="hidden-sm hidden-xs action-buttons">' +
                        '<a class="gray" href="javascript:void(-1);" title="上移1" name="up_PX"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>' +
                        '<a class="gray" href="javascript:void(-1);" title="下移" name="down_PX"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
                }
            }
        },
        {
            "sortable": false,
            "class": "center",
            "width": "12%",
            "title": "操作",
            "mRender": function (data, display, row) {
                if (row.status) {
                    return '<div class="hidden-sm hidden-xs action-buttons">' +
                        '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>' +
                        '<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                        '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '
                } else {
                    return '<div class="hidden-sm hidden-xs action-buttons">' +
                        '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>' +
                        '<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                        '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '
                }
            }
        }];

    recruitTable = initTables("recruitTable", basePath + "/medical/recruit/list", objData, true, true, 0, null, searchCase_P, function (data) {
        debugger;
        var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize + 1;//页码
        var $td = $("#recruitTable tbody tr:first td");
        if (currentPage === 1) {//第一页的第一行隐藏向上箭头
            $td.eq(7).find('a').eq(0).css("pointer-events", "none").removeClass("blue").addClass("gray");
        }
        if (countNum / pageSize < 1 || countNum / pageSize === 1) {//数据不足一页隐藏下移箭头
            $td.eq(7).find('a').eq(1).css("pointer-events", "none").removeClass("blue").addClass("gray");
        }
        var countPage;
        if (countNum % pageSize == 0) {
            countPage = parseInt(countNum / pageSize);
        } else {
            countPage = parseInt(countNum / pageSize) + 1;
        }
        if (countPage == currentPage) {//隐藏最后一条数据下移
            $("#recruitTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events", "none").removeClass("blue").addClass("gray");
        }
    });
    /** 招聘信息列表end */


    // 手机号码验证
    jQuery.validator.addMethod("tel", function (value, element) {
        var length = value.length;
        var mobile = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,8})?$)|(^0?1[35]\d{9}$)/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");
    /** 表单验证END */

});

/**
 * 招聘信息上移
 * @param obj
 */
function upMove(obj) {
    var oo = $(obj).parent().parent().parent();
    var aData = recruitTable.fnGetData(oo);
    ajaxRequest(basePath + '/medical/recruit/upMove', {"id": aData.id}, function (res) {
        freshTable(recruitTable);
    });
}

/**
 * 招聘信息下移
 * @param obj
 */
function downMove(obj) {
    var oo = $(obj).parent().parent().parent();
    var aData = recruitTable.fnGetData(oo);
    ajaxRequest(basePath + '/medical/recruit/downMove', {"id": aData.id}, function (res) {
        freshTable(recruitTable);
    });
}

$(".add-recruit").click(function () {
    /**
     * 添加招聘信息
     */
    addRecruitForm.resetForm();

    var dialog = openDialog("addRecruitDialog", "addRecruitDiv", "新增招聘信息", 580, 600, true, "确定", function () {
        if (addRecruitForm.valid()) {
            var $postDuty = $("#postDuties");
            if ($postDuty.val().length > 500) {
                layer.msg("岗位职责长度不能超过500字");
                return;
            } else if (!$postDuty.val()) {
                layer.msg("岗位职责不能为空");
                return;
            }
            var jobRequirements = $("#jobRequirements");
            if (jobRequirements.val().length > 500) {
                layer.msg("职位要求长度不能超过500字");
                return;
            } else if (!jobRequirements.val()) {
                layer.msg("职位要求不能为空");
                return;
            }
            mask();
            addRecruitForm.attr("action", basePath + "/medical/recruit/add");
            addRecruitForm.ajaxSubmit(function (data) {
                data = getJsonData(data);
                unmask();
                if (data.success) {
                    $("#addRecruitDialog").dialog("close");
                    layer.msg(data.errorMessage);
                    freshTable(recruitTable);
                    $("html").css("overflow", "auto");
                } else {
                    layer.msg(data.errorMessage);
                }
            });
        }
    });
});

/**
 * 招聘信息列表搜索
 */
function search_P() {
    var json = [];
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    json.push('{"tempMatchType":undefined,"propertyName":"hospitalId","propertyValue1":"' + hospitalId + '","tempType":undefined}');
    searchButton(recruitTable, json);
}

/**
 * 查看信息
 * @param obj
 * @param status（1，招聘信息）
 */
function previewDialog(obj) {
    var oo = $(obj).parent().parent().parent();
    var row;
    row = recruitTable.fnGetData(oo); // get datarow

    $.get(basePath + "/medical/recruit/findMedicalHospitalRecruitById", {id: row.id}, function (result) {

        $("#show_years").text(getYears(result.years));
        $("#show_position").text(result.position);
        $("#show_postDuties").text(result.postDuties);
        $("#show_jobRequirements").text(result.jobRequirements);
    });
    var prev_title = "查看招聘信息";
    var dialog = openDialogNoBtnName("showRecruitDialog", "showRecruitDiv", prev_title, 530, 600, false, "确定", null);
}

/**
 * 修改表单
 * @param obj
 * @param status
 */
function toEdit(obj) {
    updateRecruitForm.resetForm();

    var oo = $(obj).parent().parent().parent();
    var row;
    row = recruitTable.fnGetData(oo); // get datarow
    $.get(basePath + "/medical/recruit/findMedicalHospitalRecruitById", {id: row.id}, function (result) {
        $("#edit_id").val(row.id);

        $("#edit_position").val(result.position);
        $("#edit_years").val(result.years);
        $("#edit_postDuties").html(result.postDuties);
        $("#edit_jobRequirements").html(result.jobRequirements);


        var edit_title = "修改招聘信息";
        var dialog = openDialog("editRecruitDialog", "editRecruitDiv", edit_title, 580, 650, true, "确定", function () {
            if (updateRecruitForm.valid()) {
                if ($("#edit_postDuties").val().length > 500) {
                    layer.msg("岗位职责长度不能超过500字");
                    return;
                } else if ($("#edit_postDuties").val() == null || $("#edit_postDuties").val() == '') {
                    layer.msg("岗位职责不能为空");
                    return;
                }
                if ($("#edit_jobRequirements").val().length > 500) {
                    layer.msg("职位要求长度不能超过500字");
                    return;
                } else if ($("#edit_jobRequirements").val() == null || $("#edit_jobRequirements").val() == '') {
                    layer.msg("职位要求不能为空");
                    return;
                }
                mask();
                updateRecruitForm.attr("action", basePath + "/medical/recruit/updateMedicalHospitalRecruitById");
                updateRecruitForm.ajaxSubmit(function (data) {
                    data = getJsonData(data);

                    unmask();
                    if (data.success) {
                        $("#editRecruitDialog").dialog("close");
                        freshTable(recruitTable);
                    } else {
                        layer.msg(data.errorMessage);
                    }
                });
            }
        });
    });

}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj) {
    var oo = $(obj).parent().parent().parent();
    var row;
    row = recruitTable.fnGetData(oo); // get datarow
    ajaxRequest(basePath + "/medical/recruit/updateRecruitStatus", {"id": row.id}, function () {
        freshTable(recruitTable);
    });
}

/**
 * 逻辑删除
 * @param obj
 */
function delDialog(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = recruitTable.fnGetData(oo); // get datarow
    showDelDialog(function () {
        mask();
        ajaxRequest(basePath + "/medical/recruit/deleteCourseById", {"id": row.id}, function (data) {
            if (!data.success) {
                layer.msg(data.errorMessage);
            } else {
                layer.msg(data.resultObject);
                freshDelTable(recruitTable);
            }
            unmask();

        });
    });
}

/**
 * 招聘信息批量逻辑删除
 *
 */
$(".dele_P").click(function () {
    deleteAll(basePath + "/medical/recruit/delete", recruitTable, null, "删除操作不可逆，是否确认删除该招聘信息？");
});

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/, ' ');
}

function getYears(data) {
    return years[parseInt(data)];
}
