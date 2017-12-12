<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
    /*
     */
    function isWeiXin() {
   		var ua = window.navigator.userAgent.toLowerCase();
   		console.log(ua);//mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko)version/9.0 mobile/13b143 safari/601.1
   		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
   			return true;
   		} else {
   			return false;
   		}
   	}
    var wxOrbrower,param_code,param_openId;
 	if (isWeiXin()) {
 		console.log(" 是来自微信内置浏览器")
 		wxOrbrower = "wx";
 	} else {
 		wxOrbrower = "brower";
 	}
 	localStorage.setItem("access", wxOrbrower);
	if(wxOrbrower == "wx"){
		 param_code = '<%=(request.getAttribute("code") == null || request
					.getAttribute("code").equals("null")) ? "" : request
					.getAttribute("code")%>';
		 param_openId = '<%=(request.getAttribute("openId") == null || request
					.getAttribute("openId").equals("null")) ? "" : request
					.getAttribute("openId")%>';
	}
	
    if(wxOrbrower == "wx" && stringnull(param_openId)){
    	/* 如果是微信公众号进入页面时，没有给他返回token。所以这里他在请求下呢  */
	    localStorage.setItem("openid", param_openId);
		var ccontrollerAddress = "/bxg/user/isLogined";
		requestService(ccontrollerAddress, null, function(data) {
			if (data.success) {
				/*
				 * 存储session
				 */
				commonLocalStorageSetItem(data);
			}else{
				alert("网络异常");
			}
		})	
    }
	
	
	
	
</script>
<%-- <%@ include file="../../Views/h5/index.html"%> --%>
<%@ include file="../../xcviews/html/newindex.html"%>
