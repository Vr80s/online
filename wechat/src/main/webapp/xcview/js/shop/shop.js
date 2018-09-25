$(function () {

    // 商品列表
    // var DESC = 'RECOMMEND_DESC';
    requestGetService("/xczh/shop/goods/list",{
        pageNumber:1,
        pageSize:80
    },function (data) {
        if (data.success == true) {
            $(".body").html(template('body', {items: data.resultObject}));
        }
    });



})

