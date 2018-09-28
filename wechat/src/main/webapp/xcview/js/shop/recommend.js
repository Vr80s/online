var productId = getQueryString("productId");
requestService("/xczh/shop/goods/recommends",{
	productId:productId,
	pageNumber:1,
	pageSize:10
},function (data) {
    if (data.success == true) {

        
    }else{
        jqtoast(data.errorMessage);
    }
});