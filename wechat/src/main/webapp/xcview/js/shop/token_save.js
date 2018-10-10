var getParam = function(name) {
    var search = document.location.search;
    //alert(search);
    var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if(null != matcher) {
        try {
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        } catch(e) {
            try {
                items = decodeURIComponent(matcher[1]);
            } catch(e) {
                items = matcher[1];
            }
        }
    }
    return items;
};

var wv = getParam("wv");
if(wv == null){
    wv = localStorage.getItem("wv");
}else{
    localStorage.setItem("wv",wv);
}
/*if(wv==null){
    $(".footer").show();
}else{
    $(".footer").hide();
}*/
if(wv==null){
	$(".footer").show();
	$(".minirefresh-wrap").css("bottom","7.6rem");
	$(".minirefresh-wrap").css("bottom",".98rem");
}else{
	$(".footer").hide();
	$(".minirefresh-wrap").css("bottom","0");
	$(".minirefresh-wrap").css("width","7.5rem");
}
	
var token = getParam("token");
var appUniqueId = getParam("appUniqueId");
var APP_TOKEN = "app.token";
var APP_AppUniqueId = "app.appUniqueId";
if(token != null && appUniqueId != null){
    localStorage.setItem(APP_TOKEN,token);
    localStorage.setItem(APP_AppUniqueId,appUniqueId);
}else{
    localStorage.removeItem(APP_TOKEN);
    localStorage.removeItem(APP_AppUniqueId);
}