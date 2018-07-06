function loadURL(height) {
    var iFrame;
    iFrame = document.createElement("iframe");
    iFrame.setAttribute("src", "wvjbscheme://__WVJB_QUEUE_MESSAGE__?height=" + height);
    iFrame.setAttribute("style", "display:none;");
    iFrame.setAttribute("height", "0px");
    iFrame.setAttribute("width", "0px");
    iFrame.setAttribute("frameborder", "0");
    document.body.appendChild(iFrame);
    // 发起请求后这个iFrame就没用了，所以把它从dom上移除掉
    //iFrame.parentNode.removeChild(iFrame);
    //iFrame = null;
}

/**
 * 截取url传递的参数
 * @param name 传递 key  得到value
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

var id = getQueryString('id');

//ajax统一请求
function requestService(url, param, callback, ac) {
    if (ac == null)
        ac = true;// 默认异步
//    if(document.location.host.indexOf('dev.ixincheng.com')!=-1){
//        url = "/apis"+url;
//    }
    mui.ajax({
        url: url,
        type: "GET",
        data: param,
        async: ac,
        success: function (msg) {
            if (callback) {
                callback(msg);
            }
        },
        error: function (msg) {
            // alert(msg);
        }
    });
}

requestService("/xczh/article/view?id=" + id, {}, function (data) {
    $(".no").hide();
    $(".user_mywrite").html(data.success ? data.resultObject.content : "");
    var aa = $(".user_mywrite").css("height");
    var scrollHeight = document.getElementById("aaaaa").scrollHeight;

    var testEle = document.getElementById("aaaaa")
    testEle.setAttribute("test", scrollHeight); // 设置
    var aaa = testEle.getAttribute("test"); //获取
    var aa = $("#jieshao").height();
    loadURL(aa);

})

