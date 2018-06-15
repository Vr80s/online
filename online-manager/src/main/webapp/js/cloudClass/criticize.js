var criticizeTable;
var searchJson = new Array();
var courseArray = new Array();
var labelArray = ['很赞', '干货很多', '超值推荐', '喜欢', '买对了'];
$(function () {
    loadCriticizeList();
});

//列表展示
function loadCriticizeList() {
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
        {"title": "课程名称", "class": "center", "width": "8%", "sortable": false, "data": 'courseName'},
        {"title": "主播名称", "class": "center", "width": "6%", "sortable": false, "data": 'anchorName'},
        {"title": "评论人", "class": "center", "width": "6%", "sortable": false, "data": 'createPerson'},
        {
            "title": "评论时间",
            "class": "center",
            "width": "7%",
            "sortable": false,
            "data": 'createTime'
        },
        {
            'title': "评论标签",
            "class": "center",
            "width": "10%",
            "data": "label",
            "sortable": false,
            "mRender": function (data, display, row) {
                var labelNames = '';
                if (data) {
                    var labels = data.split(",");
                    for (var i = 0; i< labels.length; i++) {
                        if (labelNames) {
                            labelNames = labelNames + "  " + labelArray[i];
                        } else {
                            labelNames = labelNames + labelArray[i];
                        }
                    }
                }
                return labelNames;
            }
        }
    ];

    criticizeTable = initTables("criticizeTable", basePath + "/cloudClass/criticize/findCriticizeList", dataFields, true, true, 0, null, searchJson, function (data) {
    });
}

//条件搜索
function search() {
    searchButton(criticizeTable, searchJson);
}

function deleteCriticizes() {
    var ids = [];
    $("input[type=checkbox]:checked").each(function (index, e) {
        ids[index] = $(this).val();
    });
    if (ids.length > 0) {
        confirmInfo('确认进行此操作？', function () {
            mask();
            $.ajax({
                'url': basePath + "/cloudClass/criticize/deletes",
                'method': 'POST',
                'data': {"ids": ids.join(',')},
                'dataType': 'json',
                'success': function (resp) {
                    freshTable(criticizeTable);
                    unmask();
                }
            })
        })
    } else {
        alertInfo("至少选择一条数据");
    }
}