//requestService
// $(function(){

    // 点击去审核跳转详情
    function orders(infoId){
        requestGetService("/doctor/treatment/list",{infoId:infoId},function (data) {
            if (data.success == true) {
               
                location.href ='/xcview/html/treatment_details.html?infoId='+infoId;
            }
        });

    };


    // 列表
    requestGetService("/doctor/treatment/list",{page:1,size:1000},function (data) {
        if (data.success == true) {

            if (isNotBlank(data.resultObject)) {
                // 预约列表
                $(".baseimagenumber").hide();
                $('.my_bookings').html(template('my_bookings', {items: data.resultObject}));
            }else{
                $(".my_bookings").show();
                $(".baseimagenumber").show();
                $("body").css("background","#fff");
            };
    
             // 点击取消预约按钮
            $(".delete_btn").click(function(){
                // alert(12312);
                var id=$(this).attr("data-id");

                requestService("/doctor/treatment/cancel/appointment",{id:id},function (data) {
                    if (data.success == true) {

                        var deletes=$(".delete_"+id);

                        deletes.parent().parent().parent().parent(".main").remove();
                        

                        jqtoast("取消成功");
                    }else{
                        jqtoast(data.resultObject.errorMessage);
                    }
                });

            });


            $(".delete_delete").click(function(){
                // alert(12312);
                var id=$(this).attr("data-id");

                requestService("/doctor/treatment/list/delete",{id:id},function (data) {
                    if (data.success == true) {

                        var deletes=$(".delete_"+id);

                        deletes.parent().parent().parent().parent(".main").remove();
                        jqtoast("删除成功");
                    }else{
                        jqtoast(data.resultObject.errorMessage);
                    }
                });

            });


            // 点击开始诊疗
            $(".starting_treatment").click(function(){
                $(".tooltip").show();
            });
            // 点击取消
            $(".call_off").click(function(){
                $(".tooltip").hide();
            });
            
        	// 点击去下载
            $(".determine").click(function(){
        //          安卓路径 
                var androidURL ="http://sj.qq.com/myapp/detail.htm?apkName=com.bj.healthlive";  
                var browser = {  
               versions: function() {  
                     var u = navigator.userAgent,  
                         app = navigator.appVersion;  
                     return {  
                             android: u.indexOf("Android") > -1 || u.indexOf("Linux") >-1,  
                             iPhone: u.indexOf("iPhone") > -1 ,  
                             iPad: u.indexOf("iPad") >-1,  
                             iPod: u.indexOf("iPod") > -1,  
                           };  
                     } (),  
                language: (navigator.browserLanguage || navigator.language).toLowerCase()  
             }  

             if (browser.versions.iPhone||browser.versions.iPad||browser.versions.iPod)  
                 { 
                    //如果是ios系統，直接跳转至appstore该应用首頁，传递参数为该应用在appstroe的id号  
        //              window.location.href="itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=123456";  
                    window.location.href="itms-apps://itunes.apple.com/cn/app/id1279187788";
        //              https://itunes.apple.com/cn/app/id1279187788
                 }
                 else if(browser.versions.android)  
                 {  
                    window.location.href = androidURL;  
                 }  
                    
            });
        	
        }
    });


//刷新
// 初始化页码
var page = 1;

// miniRefresh 对象
var miniRefresh = new MiniRefresh({
    container: '#minirefresh',
    down: {
        //isLock: true,//是否禁用下拉刷新
        callback: function () {
                page = 1;
            doctorStatus(page,'down');
        }
    },
    up: {
        isAuto: false,
        callback: function () {
                page++;
            doctorStatus(page,'up');
        }
    }
});

    

// });





