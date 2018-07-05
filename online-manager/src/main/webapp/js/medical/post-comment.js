var postCommentTable;
var searchJson = [];
$(function () {
    loadPostCommentList();
});

//列表展示
function loadPostCommentList() {
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
        {
            "title": checkbox,
            "class": "center",
            "width": "3%",
            "sortable": false,
            "data": 'id',
            "mRender": function (data, display, row) {
                return '<input type="checkbox" value=' + data + ' class="ace" data-sort="' + row.sort + '"/><span class="lbl"></span>';
            }
        },
        {"title": "评论内容", "class": "center", "width": "20%", "sortable": false, "data": 'content'},
        {"title": "医师名称", "class": "center", "width": "6%", "sortable": false, "data": 'doctorName'},
        {"title": "用户昵称", "class": "center", "width": "6%", "sortable": false, "data": 'nickname'},
        {
            "title": "评论时间",
            "class": "center",
            "width": "7%",
            "sortable": false,
            "data": 'createTime'
        }
    ];

    postCommentTable = initTables("postCommentTable", basePath + "/doctorPost/comment/list", dataFields, true, true, 0, null, searchJson, function (data) {
    });
}

//条件搜索
function search() {
    searchButton(postCommentTable, searchJson);
}

function deleteComment() {
    var ids = [];
    $("input[type=checkbox]:checked").each(function (index, e) {
        ids[index] = $(this).val();
    });
    if (ids.length > 0) {
        confirmInfo('确认进行此操作？', function () {
            mask();
            $.ajax({
                'url': basePath + "/doctorPost/comment/delete",
                'method': 'POST',
                'data': {"ids": ids.join(',')},
                'dataType': 'json',
                'success': function (resp) {
                    freshTable(postCommentTable);
                    unmask();
                }
            })
        })
    } else {
        alertInfo("至少选择一条数据");
    }
}