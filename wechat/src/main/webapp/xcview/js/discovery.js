//var status;
////	判断是否为游客点击
//var user_cookie = cookie.get("_uc_t_");
//
//if(stringnull(user_cookie)){
//  //获取认证状态
//  requestService("/xczh/medical/applyStatus",{
//  },function(data) {
//      if(data.success==true){
//          status = data.resultObject;
//
//      }else{
//          webToast(data.errorMessage,"middle",3000);
//      }
//  });
//}


	
//点击我要当主播
//function myAnchor() {
//  localStorage.setItem("judgeSkip", "find");
//  if(user_cookie == null || user_cookie == ''){
//      window.location.href="cn_login.html";         //判断是否为游客并跳转登录界面
//  }
//  else if(status==1||status==3||status==5){
//      window.location.href="phy_examine.html";
//  }else if(status==2||status==4||status==6){
//      window.location.href="hos_examine.html";
//  }else{
//      window.location.href="my_anchor.html";
//  }
//
//
//}



//点击下载
function myAnchor(){
	location.href="down_load.html";
}

//点击学习判断游客
var falg =authenticationCooKie();
var url_adress=window.location.href;
function go_study(){
		if (falg==1002){
			localStorage.save_adress=url_adress;
			location.href ="/xcview/html/cn_login.html";		
		}else if (falg==1005) {
			localStorage.save_adress=url_adress;
			location.href ="/xcview/html/evpi.html";
		}else{
			location.href ="/xcview/html/my_study.html";			
		}
}