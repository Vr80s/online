var postTable;
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
        {"title": "标题", "class": "center J-label-title", "width": "8%", "sortable": false, "data": "title"},
        {"title": "内容", "class": "center J-label-content", "width": "20%", "sortable": false, "data": "content"},
        {"title": "用户", "class": "center J-post-nickname", "width": "4%", "sortable": false, "data": "nickname"},
        {
            "title": "是否置顶",
            "class": "center ",
            "width": "4%",
            "sortable": false,
            "data": "top",
            "mRender": function (data, display, row) {
                return data ? '置顶' : '非置顶';
            }
        },
        {
            "title": "是否精品",
            "class": "center",
            "width": "4%",
            "sortable": false,
            "data": 'good',
            "mRender": function (data, display, row) {
                return data ? '精品' : '非精品';
            }
        },
        {
            "title": "是否热门",
            "class": "center",
            "width": "4%",
            "sortable": false,
            "data": 'hot',
            "mRender": function (data, display, row) {
                return data ? '热门' : '非热门';
            }
        },
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

    postTable = initTables("postTable", basePath + "/bbs/post", objData, true, true, 0, null, null, function (data) {
    });

    /**
     * 置顶修改
     */
    $('.top_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        confirmInfo('确认进行此操作？', function () {
            if (ids.length > 0) {
                mask();
                $.ajax({
                    'url': basePath + "/bbs/post/changeTop",
                    'method': 'POST',
                    'data': {"ids": ids.join(',')},
                    'dataType': 'json',
                    'success': function (resp) {
                        freshTable(postTable);
                        unmask();
                    }
                })
            }
        })
    });

    /**
     * 加精修改
     */
    $('.good_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        confirmInfo('确认进行此操作？', function () {
            if (ids.length > 0) {
                mask();
                $.ajax({
                    'url': basePath + "/bbs/post/changeGood",
                    'method': 'POST',
                    'data': {"ids": ids.join(',')},
                    'dataType': 'json',
                    'success': function (resp) {
                        freshTable(postTable);
                        unmask();
                    }
                })
            }
        })
    });

    /**
     * 删除修改
     */
    $('.delete_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        confirmInfo('确认进行此操作？', function () {
            if (ids.length > 0) {
                mask();
                $.ajax({
                    'url': basePath + "/bbs/post/changeDelete",
                    'method': 'POST',
                    'data': {"ids": ids.join(','), "deleted" : true},
                    'dataType': 'json',
                    'success': function (resp) {
                        freshTable(postTable);
                        unmask();
                    }
                })
            }
        })
    });

    /**
     * 热门修改
     */
    $('.hot_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        confirmInfo('确认进行此操作？', function () {
            if (ids.length > 0) {
                mask();
                $.ajax({
                    'url': basePath + "/bbs/post/changeHot",
                    'method': 'POST',
                    'data': {"ids": ids.join(',')},
                    'dataType': 'json',
                    'success': function (resp) {
                        freshTable(postTable);
                        unmask();
                    }
                })
            }
        })
    });

});
function search() {
    searchButton(postTable, searchJson);
}