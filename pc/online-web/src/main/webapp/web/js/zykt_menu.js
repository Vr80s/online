// $(document).ready(function(){
 function initXX() {
    $("#select1 dd").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
        if ($(this).hasClass("select-all")) {
            $("#selectA").remove();
        } else {
            var copyThisA = $(this).clone();
            if ($("#selectA").length > 0) {
                $("#selectA a").html($(this).text());
                $("#selectA").attr("sid", $(this).attr("sid"))
            } else {
                $(".select-result dl").append(copyThisA.attr("id", "selectA"));
            }
        }
    });

    $("#select2 dd").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
        if ($(this).hasClass("select-all")) {
            $("#selectB").remove();
        } else {
            var copyThisB = $(this).clone();
            if ($("#selectB").length > 0) {
                $("#selectB a").html($(this).text());
                $("#selectB").attr("sid", $(this).attr("sid"))
            } else {
                $(".select-result dl").append(copyThisB.attr("id", "selectB"));
            }
        }
    });

    $("#select3 dd").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
        if ($(this).hasClass("select-all")) {
            $("#selectC").remove();
        } else {
            var copyThisC = $(this).clone();
            if ($("#selectC").length > 0) {
                $("#selectC a").html($(this).text());
                $("#selectC").attr("sid", $(this).attr("sid"))
            } else {
                $(".select-result dl").append(copyThisC.attr("id", "selectC"));
            }
        }
    });

    $("#select4 dd").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
        if ($(this).hasClass("select-all")) {
            $("#selectD").remove();
        } else {
            var copyThisC = $(this).clone();
            if ($("#selectD").length > 0) {
                $("#selectD a").html($(this).text());
                $("#selectD").attr("sid", $(this).attr("sid"))
            } else {
                $(".select-result dl").append(copyThisC.attr("id", "selectD"));
            }
        }
    });

    $(document).on("click","#selectA", function () {
//    $("#selectA").on("click", function () {
        $(this).remove();
        $("#select1 .select-all").addClass("selected").siblings().removeClass("selected");
    });

    $(document).on("click","#selectB", function () {
//    $("#selectB").on("click", function () {
        $(this).remove();
        $("#select2 .select-all").addClass("selected").siblings().removeClass("selected");
    });

    $(document).on("click","#selectC", function () {
//    $("#selectC").on("click", function () {
        $(this).remove();
        $("#select3 .select-all").addClass("selected").siblings().removeClass("selected");
    });

    $(document).on("click","#selectD", function () {
//    $("#selectD").on("click", function () {
        $(this).remove();
        $("#select4 .select-all").addClass("selected").siblings().removeClass("selected");
    });


    $(document).on("click",".select dd", function () {
//    	$(".select dd").on("click", function () {

        if ($(".select-result dd").length > 1) {
            $(".select-no").hide();
        } else {
            $(".select-no").show();
        }
        var param = {
            pageSize: 12,
            pageNumber: 1
        };
        secondAjax(param, getSearchStr());

    });

    function orderBy(orderType,orderBy){
        var param = {
            pageSize: 12,
            pageNumber: 1
        };
        var searchStr=getSearchStr();
        if(searchStr!=''&&searchStr!=null){
            searchStr+="&";
        }
        searchStr+="orderType="+orderType;
        if(searchStr!=''&&searchStr!=null&&orderBy!=null&&orderBy!=''){
            searchStr+="&orderBy="+orderBy;
        }
        secondAjax(param, searchStr);
    }
    //综合排序部分
     $(".tab_menu_price").mouseover(function(){
         $(".current_ul").show();
     });
     $(".current_ul").mouseout(function(){
         $(".current_ul").hide();
     });
     $(".current_ul_li1").click(function(){
         $('#price').html('价格从低到高');
         $('#tab_menu_price').css('background','#fff');
         orderBy("4","asc");
     });
     $(".current_ul_li2").click(function(){
         $('#price').html('价格从高到低');
         $('#tab_menu_price').css('background','#fff');
         orderBy("4","desc");
     });
     $(".tab_menu_li1").click(function(){
         $('#price').html('价格');
         orderBy("1",null);
     });
     $(".tab_menu_li2").click(function(){
         $('#price').html('价格');
         orderBy("2",null);
     });
     $(".tab_menu_li3").click(function(){
         $('#price').html('价格');
         orderBy("3",null);
     });

    function getSearchStr() {
        var searchStr = "";
        $(".select-result dd").each(function (i) {
            var sid = $(this).attr("sid");
            if (sid != null) {
                searchStr += sid + "&";
            }
        });
        if (searchStr.length > 0) {
            searchStr = s = searchStr.substring(0, searchStr.length - 1);
        }
        return searchStr;
    }
    
    $(".t").click(function(){
    	$(".t").removeClass("current");
    	$(this).addClass("current");
    })
 }
//});