//requestService
// $(function(){

    function orders(id){
        // var id=$(this).attr("data-id");

        requestGetService("/doctor/treatment/user/appointment",{id:id},function (data) {
            if (data.success == true) {
               
                location.href ='/xcview/html/treatment_details.html?id='+id;

            }
        });
    };


    requestGetService("/doctor/treatment/list",{page:1,size:10},function (data) {
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

            // 点击我的预约

            /*$(".head_portrait").click(function(){
                alert(11111);
            });*/
    
             // 点击删除按钮
            $(".delete").click(function(){

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
                    //如果是ios系統，直接跳轉至appstore該應用首頁，傳遞参數为該應用在appstroe的id號  
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


   

    

// });





