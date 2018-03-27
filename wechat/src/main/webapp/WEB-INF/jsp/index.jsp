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
					
		if(param_openId !=null && param_openId!="" ){
			 localStorage.setItem("openid", param_openId);
		}			
	}
</script>
<jsp:forward page="/xcview/html/home_page.html"/>
