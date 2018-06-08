var lineStudentTable;
var searchJson = [];
$(function () {

    var dataFields = [
        {"title": "姓名", "class": "center", "width": "20%", "sortable": false, "data": 'realName'},
        {"title": "课程名称", "class": "center", "width": "8%", "sortable": false, "data": 'courseName'},
        {"title": "主播名称", "class": "center", "width": "6%", "sortable": false, "data": 'anchorName'},
        {
            'title': "是否已上课",
            "class": "center",
            "width": "10%",
            "data": "learned",
            "sortable": false,
            "mRender": function (data, display, row) {
                if (data) {
                    return "是";
                } else {
                    return "否";
                }
            }
        },
        {"title": "微信号", "class": "center", "width": "6%", "sortable": false, "data": 'wechatNo'},
        {"title": "手机", "class": "center", "width": "6%", "sortable": false, "data": 'mobile'},
        {
            "title": "报名时间",
            "class": "center",
            "width": "7%",
            "sortable": false,
            "data": 'createTime'
        },
        {
            'title': "性别",
            "class": "center",
            "width": "10%",
            "data": "sex",
            "sortable": false,
            "mRender": function (data, display, row) {
                if (!data) {
                    return '未知';
                }
                if (data == 1) {
                    return '男';
                }
                if (data == 2) {
                    return '女';
                }
            }
        },
        {
            "sortable": false,
            "class": "center",
            "width": "9%",
            "title": "操作",
            "mRender": function (data, display, row) {
                var id = row.id;
                var learned = row.learned;
                var label = learned ? "设为未上课" : "设为上课";
                return '<div class="hidden-sm hidden-xs action-buttons">' +
                    '<a class="blue" href="javascript:void(-1);" onclick="updateLearned(this);" data-id="' + id + '"' + ' data-learned="' + !learned + '">'
                    +
                    label
                    +
                    '</a>' +
                    '</div> ';
            }
        }

    ];

    lineStudentTable = initTables("lineStudentTable", basePath + "/anchor/student", dataFields, true, true, 0, null, searchJson, function (data) {
    });
});

function search() {
    searchButton(lineStudentTable, searchJson);
}

function updateLearned(obj) {
    var id = $(obj).data('id');
    var learned = $(obj).data('learned');
    ajaxRequest(basePath + "/anchor/student/updateLearned/" + id + "/" + learned, null, function (data) {
        console.log(data);
        if (data.success == false) {
            layer.msg(data.errorMessage);
        }
        freshTable(lineStudentTable);
    });
}