//requestService
// $(function(){

    $(function () {

        appointmentList(1,'down');  /*定义一个方法*/

    })

    // 点击去审核跳转详情
    function orders(infoId){
        requestGetService("/doctor/treatment/list",{infoId:infoId},function (data) {
            if (data.success == true) {
               
                location.href ='/xcview/html/treatment_details.html?infoId='+infoId;
            }
        });
    };

    function appointmentList(pageNumber, downOrUp){
            // 列表
        requestGetService("/doctor/treatment/list",{
            page:pageNumber,
            size:10
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                //downOrUp为down时为下拉刷新等于up时为上拉操作
                if (downOrUp=='down') {

                    if (obj.length==0) {
                        $(".my_bookings").show();
                        $(".baseimagenumber").show();
                        $("body").css("background","#fff");
                        $(".downwrap-content").hide();  /*隐藏刷新提示*/
                        $(".minirefresh-upwrap").hide(); //隐藏刷新提示
                    }else{
                        $(".downwrap-content").show();    //显示刷新提示
                        $(".minirefresh-upwrap").show();  //显示刷新提示
                        $(".baseimagenumber").hide();
                    }
                    // 预约列表---显示列表
                    $('.my_bookings_main').html(template('my_bookings', {items: obj}));
                    miniRefresh.endDownLoading(true);// 结束下拉刷新
                }else if (obj.length==0) {
                    miniRefresh.endUpLoading(true);// 结束上拉加载
                }else{
                    // 追加  append
                    $('.my_bookings_main').append(template('my_bookings', {items: obj}));
                    miniRefresh.endUpLoading(false);
                };

            
                 // 点击取消预约按钮 
                $(".delete_btn").off("click");
                $(".delete_btn").click(function(){
                    var id=$(this).attr("data-id");
                    $(".cancel_yes").attr("title",id);

                    // $(".cancel_yes").addClass("cancel_yes_"+id);

                    $(".cancel").show();
                });

                /*function cancelOff(off){
                    var id = $(".cancel_yes").attr("title");
                    requestService("/doctor/treatment/cancel/appointment",{id:id},function (data) {
                        if (data.success == true) {

                            // var deletes=$(".delete_"+id);

                            deletes.parent().parent().parent().parent(".main").remove();
                            

                            jqtoast("取消成功");
                        }else{
                            jqtoast(data.errorMessage);
                        }
                    });
                };*/

                // 点击否
                $(".cancel_no").off("click");
                $(".cancel_no").click(function(){
                    $(".cancel").hide();
                });

                // 点击“是”取消预约
                $(".cancel_yes").off("click");
                /*var id = $(".cancel_yes").attr("title");*/
                $(".cancel_yes").click(function(){
                    
                    var id=$(this).attr("title");
                    
                    requestService("/doctor/treatment/cancel/appointment",{id:id},function (data) {
                        if (data.success == true) {
                            $(".cancel").hide();

                            var deletes=$(".delete_"+id);
                            deletes.parent().parent().parent().parent(".main").remove();
                            
                            jqtoast("取消成功");
                            
                            $(".downwrap-content").hide();
                            $(".minirefresh-upwrap").hide();
                            $(".baseimagenumber").show();
                            $("body").css("background","#fff");

                        }else{
                            jqtoast(data.errorMessage);
                        }
                    });

                });

                $(".delete_delete").off("click");
                $(".delete_delete").click(function(){
                    // alert(12312);
                    var id=$(this).attr("data-id");

                    requestService("/doctor/treatment/list/delete",{id:id},function (data) {
                        if (data.success == true) {

                            var deletes=$(".delete_"+id);

                            deletes.parent().parent().parent().parent(".main").remove();
                            jqtoast("删除成功");
                        }else{
                            jqtoast(data.errorMessage);
                        }
                    });

                });


                // 点击开始诊疗
                $(".starting_treatment").off("click");
                $(".starting_treatment").click(function(){
                    $(".tooltip").show();
                });
                // 点击取消
                $(".call_off").off("click");
                $(".call_off").click(function(){
                    $(".tooltip").hide();
                });
                
                // 点击去下载
                $(".determine").off("click");
                $(".determine").click(function(){
                    //安卓路径 
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
                
            }else{
                // jqtoast("这个弹框出现了说下");
                jqtoast(data.errorMessage);
            }
        });
    };

    


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
            appointmentList(page,'down');

        }
    },
    up: {
        isAuto: false,
        callback: function () {
            page++;
            appointmentList(page,'up');
        }
    }
});

    

// });
