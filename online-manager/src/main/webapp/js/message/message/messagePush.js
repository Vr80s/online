var banner2Table;
var nowTime;
var searchJson = new Array();
var reg = /(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/g;
$(function () {
    $("#addMobileSearch").val(1);
    $("#updateMobileSearch").val(1);
    loadBanner2List();
    $(".datetime-picker").datetimepicker({
        showSecond: true,
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: "HH:mm:ss",
        showButtonPanel: false
    });
    $(".datetime-picker").attr({ readonly: 'true' });
    $('input[name="pushType"]').on('click', function (e) {
        var val = $('input[name="pushType"]:checked').val();
        if (val == 0) {
            $('.J-input-user').hide();
        } else {
            $('.J-input-user').show();
        }
    });

    $('#J-menu').on('change', function (e) {
        var menuId = $(this).val();
        ajaxRequest("message/messagePush/course?menuId=" + menuId, null, function (res) {
            $("#J-course").html('');
            if (res.resultObject.length > 0) {
                for (var i = 0; i < res.resultObject.length; i++) {
                    var course = res.resultObject[i];
                    if (course != null) {
                        var appendStr = '<option value=' + course.id + '>' + course.courseName + '</option>';
                        console.log(appendStr);
                        $("#J-course").append(appendStr);
                    }
                }
            }
        });
    });

    $('input[name="routeType"]').on('change', function () {
        var val = $(this).val();
        if (val == 'COMMON_COURSE_DETAIL_PAGE') {
            $('.course-detail').show();
            $('.anchor-detail').hide();
            $('.doctor-detail').hide();
            $('.outer-link').hide();
        } else if (val == 'ANCHOR_INDEX') {
            $('.course-detail').hide();
            $('.anchor-detail').show();
            $('.doctor-detail').hide();
            $('.outer-link').hide();
        } else if (val === 'DOCTOR_POST'){
            $('.course-detail').hide();
            $('.anchor-detail').hide();
            $('.doctor-detail').show();
            $('.outer-link').hide();
        } else if (val == 'H5') {
            $('.course-detail').hide();
            $('.anchor-detail').hide();
            $('.doctor-detail').hide();
            $('.outer-link').show();
        }
    });
});

//列表展示
function loadBanner2List() {
    var dataFields = [
        // {
        //     "title": "推送标题",
        //     "class": "center",
        //     "width": "8%",
        //     "sortable": false,
        //     "data": 'title',
        //     "mRender": function (data, display, row) {
        //         return data;
        //     }
        // },
        {
            "title": "推送内容",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'context',
            "mRender": function (data, display, row) {
                return data;
            }
        },
        {
            "title": "状态",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'status',
            "mRender": function (data, display, row) {
                return row.status &&  row.status != 0 ? "成功" : "待发送";
            }
        },
        {
            "title": "推送时间",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'pushTime',
            "mRender": function (data, display, row) {
                return row.pushTime;
            }
        },
        {
            "title": "跳转类型",
            "class": "center",
            "width": "4%",
            "sortable": false,
            "data": 'routeType',
            "mRender": function (data, display, row) {
                if (row.routeType && row.routeType !== 'NONE') {
                    if (row.routeType === 'H5') {
                        return "外部链接";
                    } else if (row.routeType === 'COMMON_COURSE_DETAIL_PAGE') {
                        return "课程"
                    } else if (row.routeType === "DOCTOR_POST") {
                        return "医师动态";
                    } else if (row.routeType === "ANCHOR_INDEX") {
                        return "主播";
                    } else {
                        return "";
                    }
                } else {
                    return "无";
                }
            }
        },
        {
            "title": "跳转对象",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'course',
            "mRender": function (data, display, row) {
                return row.course;
            }
        },
        {title: '推送用户数', "class": "center", "width": "12%", "height": "68px", "data": 'pushCount',"color":"#307ecc"},
        {
            title: "操作",
            "class": "center",
            "width": "10%",
            "height": "34px",
            "data": "id",
            "sortable": false,
            "mRender": function (data, display, row) {
                var buttons = '<div class="hidden-sm hidden-xs action-buttons">';
                if (row.status === 0) {
                    buttons += '<a class="blue" href="javascript:void(-1);" title="立即推送" onclick="updateStatus(this);">立即推送</i></a> ';
                }
                buttons += "<a class=\"blue\" href=\"javascript:void(-1);\" title=\"删除\" onclick=\"deleteMessage(this);\">删除</i></a></div>";
                return buttons;
            }
        }
    ];

    banner2Table = initTables("messagePushTable", basePath + "/message/messagePush/messageslist", dataFields, true, true, 0, null, searchJson, function (data) {

        var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize + 1;//页码

        var countPage;
        if (countNum % pageSize == 0) {
            countPage = parseInt(countNum / pageSize);
        } else {
            countPage = parseInt(countNum / pageSize) + 1;
        }
    });
}

//条件搜索
function search() {
    searchButton(banner2Table, searchJson);
}

$('.J-no-target').on('click', function(e) {
    e.preventDefault();
    $('input[name="routeType"]:checked').prop('checked', '');
    $('.course-detail').hide();
    $('.anchor-detail').hide();
    $('.outer-link').hide();
});

//新增框
$(".add_bx").click(function () {
    $('.selectpicker').selectpicker('deselectAll');
    var dialog = openDialog("addBanner2Dialog", "dialogAddBanner2Div", "新建推送", 780, 580, true, "确定", function () {

        var content = $('#add_context').val();
        var title = $('#add_title').val();
        var pushTime = $('#add_pushTime').val();
        var pushType = $('#pushType').val();
        var inputUserFile = $('#J-pushUserFile').val();
        var routeType = $('input[name="routeType"]:checked').val();

        if (pushType == 1) {
            if (!inputUserFile) {
                alertInfo("指定用户推送必须上传推送用户文件");
                return false;
            }
        }
        if (title && title.length > 30) {
            alertInfo("标题长度不能超过30");
            return false;
        }

        if (routeType) {

            var detailId;
            if (routeType == 'COMMON_COURSE_DETAIL_PAGE') {
                detailId = $('#J-course').val();
                if (!detailId) {
                    alertInfo("请选择课程!");
                    return false;
                }
            } else if (routeType == 'ANCHOR_INDEX') {
                detailId = $('#J-anchor').val();
                if (!detailId) {
                    alertInfo("请选择医师/主播");
                    return false;
                }
            } else if (routeType === "DOCTOR_POST") {
                detailId = $('#J-doctor').val();
                if (!detailId) {
                    alertInfo("请选择医师/主播");
                    return false;
                }
            } else if (routeType == 'H5') {
                var link = $('#J-link').val();
                if (!link) {
                    alertInfo("请输入外部url地址");
                    return false;
                }
                if (!link.match(reg)) {
                    alertInfo("不合法的url格式(正确格式如:https://www.hao123.com)");
                    return false;
                }
            }
            if (detailId) {
                $('#J-detailId').val(detailId);
            }
        }

        if (!pushTime) {
            alertInfo("推送时间不能为空");
            return false;
        }
        if (new Date(pushTime) < new Date()) {
            alertInfo("推送时间不能早于当前时间");
            return false;
        }
        if (!content) {
            alertInfo("推送内容不能为空");
            return false;
        }

        if (pushType == 0) {
            $("#addBanner2-form").attr("enctype", "application/x-www-form-urlencoded");
        } else {
            $("#addBanner2-form").attr("enctype", "multipart/form-data");
        }
        mask();
        $("#addBanner2-form").attr("action", basePath + "/message/messagePush/save");
        $("#addBanner2-form").ajaxSubmit(function (data) {
                data = getJsonData(data);
                unmask();
                if (data.success) {
                    $("#addBanner2Dialog").dialog("close");
                } else {
                }
                location.reload();
                $("html").eq(0).css("overflow", "scroll");
            }
        );
    });
});


/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = banner2Table.fnGetData(oo); // get datarow
    confirmInfo('确认后消息会提前推送，确认立即发送该消息吗? ', function() {
        ajaxRequest(basePath + "/message/messagePush/updateStatus/" + row.id, null, function (data) {
            freshTable(banner2Table);
        });
    });
}

function deleteMessage(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = banner2Table.fnGetData(oo); // get datarow
    confirmInfo('确认立即删除该消息吗?',  function () {
        ajaxRequest(basePath + "/message/messagePush/delete/" + row.id, null, function (data) {
            freshTable(banner2Table);
        })
    })
}

//获取当前时间
function show() {
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "-";
    str += (mydate.getMonth() + 1) + "-";
    str += mydate.getDate();
    return str;
}