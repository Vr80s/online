/**
 * Created by Elvis on 2015/12/22.
 */

//当前页面tab切换动作
function tabChange(obj) {
    if ($(obj)) {
        $(obj).on("click", "li", function () {
            $(obj).find("li").removeClass("liactive");
            $(this).addClass("liactive");
            $(".divpaper .divtab").hide();
            var liidx = $(this).attr("id").charAt(2);
            $("#div" + liidx).show();
        });
    }
}

//手风琴效果
function accordionChange(obj) {
    $(obj).on("click", "li .sptitle", function () {
        //alertInfo(".sptitle");
        if ($(this).closest("li").find(".divaccordion").css("display") == "none") {
            $(obj).find("li .divaccordion").slideUp();
            $(this).closest("li").find(".divaccordion").slideDown();
        } else {
            $(this).closest("li").find(".divaccordion").slideUp();
        }
    });
}
/*点击显示大图*/
function picturePreview(objstr, npapy) {
    $(objstr).click(function () {
        if (!isnull(npapy)) {
            $("body", document).find(".imgpreshow").remove();
        }
        $('#mbi-outer').fadeIn('slow');
        var tobj = $(this).find('.sacimg'),
            bigsaimg = $('.answer-img');
        bigsaimg.attr('src', tobj.attr('src'));
        //if(bigsaimg.width()>$(window).width()){
        //    var imgw=((bigsaimg.width()/$(window).width()).toFixed(2)).split('.')[1];
        //    bigsaimg.css({'width':imgw+'%'});
        //}
    });
}
/*点击预览出来的大图消失*/
function previewHide(objstr) {
    $(objstr).click(function () {
        $(this).fadeOut('slow');
    });
}
/**
 * 初始化图片鼠标滑轮放大缩小功能
 */
$.fn.initImageViewer = function () {
    this.viewer({
        navbar: false,
        toolbar: false,
        transition: false,
        minZoomRatio: 0.05,//最小可以缩小到5%
        maxZoomRatio: 5//最大可以放大到500%
    });
}