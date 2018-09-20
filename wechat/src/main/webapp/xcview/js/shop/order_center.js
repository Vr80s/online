$(function() {

    
    $(".affirm").click(function(){
        $(".cancel_order").each(function(){
    
            // var postage = parseFloat($(".express_price").find("span").text()); //获取邮费
        })
        $(".removeitem").show();
        $(this).parent().parent().parent().parent(".main").remove();
    });

});