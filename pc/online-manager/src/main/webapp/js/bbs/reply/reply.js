var replyTable;
var searchJson;
$(function () {

    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [{
        "title": checkbox,
        "class": "center",
        "width": "3%",
        "sortable": false,
        "data": 'id',
        "mRender": function (data, display, row) {
            return '<input type="checkbox" value=' + data + ' class="ace" data-sort="' + row.sort + '"/><span class="lbl"></span>';
        }
    },
        {"title": "ID", "class": "center", "width": "4%", "sortable": false, "data": "id"},
        {"title": "内容", "class": "center", "width": "20%", "sortable": false, "data": "content"},
        {"title": "创建时间", "class": "center", "width": "8%", "sortable": false, "data": "initTime"},
        {"title": "用户", "class": "center", "width": "4%", "sortable": false, "data": "nickname"},
        {"title": "用户ID", "class": "center", "width": "4%", "sortable": false, "data": "userId"},
        {
            "title": "是否有效",
            "class": "center",
            "width": "4%",
            "sortable": false,
            "data": 'deleted',
            "mRender": function (data, display, row) {
                return data ? '无效' : '有效';
            }
        }
    ];

    replyTable = initTables("replyTable", basePath + "/bbs/reply", objData, true, true, 0, null, null, function (data) {
    });

    /**
     * 删除修改
     */
    $('.delete_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        if (ids.length > 0) {
            confirmInfo('确认进行此操作？', function () {
                mask();
                $.ajax({
                    'url': basePath + "/bbs/reply/changeDelete",
                    'method': 'POST',
                    'data': {"ids": ids.join(','), "deleted": true},
                    'dataType': 'json',
                    'success': function (resp) {
                        freshTable(replyTable);
                        unmask();
                    }
                })
            })
        } else {
            alertInfo("至少选择一条数据");
        }
    });
});

function search() {
    searchButton(replyTable, searchJson);
}