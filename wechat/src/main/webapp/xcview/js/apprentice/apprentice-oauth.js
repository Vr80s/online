function isWeixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        localStorage.setItem("access","wx");
        return true;
    } else {
        localStorage.setItem("access","brower");
        return false;
    }
}

$(function() {
	debugger;
    if(data.token==null||data.appUniqueId==null){
        data.wechat = isWeixn();
        if(authenticationCooKie() == 1000){
            sessionStorage.setItem("jump4oauth",location.href);
            requestService("/xczh/set/isLogined", data, function(data) {
                if (data.success) {
                    commonLocalStorageSetItem(data);
                }else{
                    if(data.code=="10010"){
                        // alert(data.errorMessage)
                        location.href = data.errorMessage;
                        window.navigate(data.errorMessage);
                        debugger
                    }else{
                        location.href ="/xcview/html/cn_login.html";
                    }
                }
            },false)
        }
    }
})
