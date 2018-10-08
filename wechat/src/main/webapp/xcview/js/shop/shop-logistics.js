
//requestPostService('xczh/shop/order/transitStep',{OrderId:180914-321011712891119}, function (data) {
requestGetService('/xczh/shop/order/transitStep',{OrderId:10102}, function (data) {
    if (data.success === true) {
        alert(9893104634032);
    }
});