var restrictionTable;
var searchJson;
$(function () {
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';

    var objData = [
        {
            "title": checkbox,
            "class": "center",
            "width": "3%",
            "sortable": false,
            "data": 'id',
            "mRender": function (data, display, row) {
                return '<input type="checkbox" value=' + data + ' class="ace" name="list-radio" "/><span class="lbl"></span>';
            }
        },
        {"title": "ID", "class": "center", "width": "8%", "sortable": false, "data": "id"},
        {"title": "用户", "class": "center", "width": "4%", "sortable": false, "data": "nickname"},
        {"title": "手机号", "class": "center", "width": "4%", "sortable": false, "data": "mobile"},
        {
            "title": "是否禁言",
            "class": "center J-gags",
            "width": "4%",
            "sortable": false,
            "data": 'gags',
            "mRender": function (data, display, row) {
                return data === "1" ? '是' : '否';
            }
        },
        {
            "title": "是否加入黑名单",
            "class": "center J-blacklist",
            "width": "4%",
            "sortable": false,
            "data": 'blacklist',
            "mRender": function (data, display, row) {
                return data === "1" ? '是' : '否';
            }
        }
    ];

    restrictionTable = initTables("restrictionTable", basePath + "/bbs/restriction", objData, true, true, 0, null, null, function (data) {
    });

    /**
     * 禁言
     */
    $('.gags_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        if (ids.length === 0) {
            alertInfo("请至少选择一条数据");
            return false;
        }
        confirmInfo('确认对选中的用户进行禁言？', function () {
            mask();
            $.ajax({
                'url': basePath + "/bbs/restriction/changeGags",
                'method': 'POST',
                'data': {"userIds": ids.join(','), "gags": true},
                'dataType': 'json',
                'success': function (resp) {
                    if (resp.success) {
                        freshTable(restrictionTable);
                        unmask();
                    } else {
                        alertInfo(resp.errorMessage);
                        unmask();
                    }
                }
            })
        })
    });

    /**
     * 取消禁言
     */
    $('.cancel_gags_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        if (ids.length === 0) {
            alertInfo("请至少选择一条数据");
            return false;
        }
        confirmInfo('确认对选中的用户取消禁言？', function () {
            mask();
            $.ajax({
                'url': basePath + "/bbs/restriction/changeGags",
                'method': 'POST',
                'data': {"userIds": ids.join(','), "gags": false},
                'dataType': 'json',
                'success': function (resp) {
                    if (resp.success) {
                        freshTable(restrictionTable);
                        unmask();
                    } else {
                        alertInfo(resp.errorMessage);
                        unmask();
                    }
                }
            })
        })
    });

    /**
     * 拉黑
     */
    $('.blacklist_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        if (ids.length === 0) {
            alertInfo("请至少选择一条数据");
            return false;
        }
        confirmInfo('确认对选中的用户进行拉黑？', function () {
            mask();
            $.ajax({
                'url': basePath + "/bbs/restriction/changeBlacklist",
                'method': 'POST',
                'data': {"userIds": ids.join(','), "blacklist": true},
                'dataType': 'json',
                'success': function (resp) {
                    if (resp.success) {
                        freshTable(restrictionTable);
                        unmask();
                    } else {
                        alertInfo(resp.errorMessage);
                        unmask();
                    }
                }
            })
        })
    });

    /**
     * 取消拉黑
     */
    $('.cancel_blacklist_bx').on('click', function (e) {
        var ids = [];
        $("input[type=checkbox]:checked").each(function (index, e) {
            ids[index] = $(this).val();
        });
        if (ids.length === 0) {
            alertInfo("请至少选择一条数据");
            return false;
        }
        confirmInfo('确认对选中的用户取消拉黑？', function () {
            mask();
            $.ajax({
                'url': basePath + "/bbs/restriction/changeBlacklist",
                'method': 'POST',
                'data': {"userIds": ids.join(','), "blacklist": false},
                'dataType': 'json',
                'success': function (resp) {
                    if (resp.success) {
                        freshTable(restrictionTable);
                        unmask();
                    } else {
                        alertInfo(resp.errorMessage);
                        unmask();
                    }
                }
            })
        })
    });
});

function search() {
    searchButton(restrictionTable, searchJson);
}