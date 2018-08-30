
$(function () {

    appointmentList(1,'down');  /*定义一个方法*/

})
    // 点击头像区域跳转到查看详情
    function orders(id){
        requestGetService("/doctor/treatment/user/appointment",{id:id},function (data) {
            if (data.success == true) {
                location.href ='/xcview/html/physician/my_bookings.html?id='+id;
            }
        });
    };


    function foreshow(id){
        requestGetService("/doctor/treatment/user/appointment",{id:id},function (data) {
            if (data.success == true) {
               
                location.href ='/xcview/html/live_play.html?my_study='+id;
            }
        });
    };

    //列表展示内容
    function appointmentList(pageNumber, downOrUp) {
        requestGetService("/doctor/treatment/user/appointment",{
            page:pageNumber,
            size:10
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                //downOrUp为down时为下拉刷新等于up时为上拉操作
                if(downOrUp=='down'){
                    if(obj.length==0){
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
                    // 预约列表
                    $('.my_bookings_main').html(template('my_bookings', {items: obj}));
                    miniRefresh.endDownLoading(true);// 结束下拉刷新
                } else if(obj.length==0){
                    miniRefresh.endUpLoading(true);// 结束上拉加载
                } else {
                    $(".my_bookings_main").append(template('my_bookings',{items:obj}));
                    miniRefresh.endUpLoading(false);
                }

                // 点击删除按钮
                $(".delete").off("click");
                $(".delete").click(function(){

                    var id=$(this).attr("data-id");

                    requestService("/doctor/treatment/user/appointment/delete",{id:id},function (data) {
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

            }else{
                jqtoast(data.errorMessage);
            }
        });
    }





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







