var announcementTable;
$(function () {

    var objData = [
        {
            "title": "医馆",
            "class": "center ",
            "width": "4%",
            "sortable": false,
            "data": "hospitalName",
            "mRender": function (data, display, row) {
                return data;
            }
        },
        {"title": "内容", "class": "center J-label-content", "width": "20%", "sortable": false, "data": "content"},
        {
            "title": "创建时间",
            "class": "center",
            "width": "4%",
            "sortable": false,
            "data": 'createTime',
            "mRender": function (data, display, row) {
                return data;
            }
        }
    ];

    announcementTable = initTables("announcementTable", basePath + "/hospital/announcement", objData, true, true, 0, null, null, function (data) {
    });
});