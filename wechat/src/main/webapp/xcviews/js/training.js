/**
 * 
 */
 function formatDateTime(inputTime) {
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '.' + m + '.' + d;
    };
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    
    requestService("/bxg/bunch/offLineClassItem",{id : getQueryString("id"),userId:localStorage.userId},
               function(data) {
        result = data.resultObject;
        /**
         * 这里判断是否需要密码确认和是否付费
         */
        $("#lcount").html(result.learndCount);
        /**
         * 判断是直播呢，还是回放呢
         *  0 直播已结束 1 直播还未开始 2 正在直播
         */
        //判断是否可预约
        if(result.watchState == 0){
            $("#buyDiv").show();
            $("#buyPirce").html(result.currentPrice);
            $("#bmspan").html("<div class=\"training_teacher_bto_right_close\" id=\"bmbtn\">已报名</div>");

        }else if(result.watchState == 1){  //goto 付费页面
            $("#buyDiv").show();
            $("#buyPirce").html(result.currentPrice);

            //如果已结束就禁止报名
            /* 点击线下课程出现的弹框，测试提出bug，就给隐藏掉了。*/

			//获取电脑屏幕时间
              requestService("/bxg/common/getSystemTime",{course_id : 1},
                    function(data) {
                  var serverTime=data;
                  var endTime = Date.parse(new Date(result.endTime));
                  if(endTime<serverTime){
                      $(".training_teacher_bto").show();
                      $(".footer_buyDiv").show();
                      $("#bmspan").html("<div class=\"training_teacher_bto_right_close\" id=\"bmbtn\">报名已截止</div>");
                  }/*else{
                      $(".training_teacher_bto").show();
                      $(".footer_buyDiv").show();
                      $("#bmspan").html("<div class=\"training_teacher_bto_right_close\" id=\"bmbtn\">已报名222</div>");
                  }*/
                  
              },false);
            
        }else if(result.watchState == 2){  //goto 付费页面
            $("#buyDiv").show();
            $("#buyPirce").html(result.currentPrice);
            $("#bmspan").html("<div class=\"training_teacher_bto_right_close\" id=\"bmbtn\">已报名</div>");
        }
        /**
         * 为详情页面添加数据
         */
        $("#headImg").attr("src",result.smallImgPath);
        $("#touxiang").attr("src",result.headImg);
         $("#gradeName").text(result.gradeName);
        $(".lname").html(result.name);
        $("#address").html(result.address);
        $("#udescription").html(result.udescription);
        //
        $("#details").text(result.description);
        //$("#date").html(formatDateTime(result.startTime)+"-"+formatDateTime(result.endTime).substr(5));
        $("#date").html( result.startTime.split(" ")[0].replace(/-/g, ".")+"-"+result.endTime.split(" ")[0].substr(5,10).replace(/-/g, "."));
       
     },false);
   

    function showBaseInfoDiv(){
        requestService("/bxg/apply/get",{id:1},
                   function(data) {
            if(data.success){
                var result = data.resultObject;
                $("#phone").val(result.mobile);
                $("#realName").val(result.realName);
            }
        },false);
        $("#baseInfoDiv").show();
    }

    function apply(){
        var phone =$("#phone").val();
        
        
        if($("#phone").val()==''){
            alert("手机号不能为空!");
            return false;
        }
        
        if (!(/^1[34578]\d{9}$/.test(phone))) {
            alert("请输入正确的手机号");
            return false;
        }
        
        
        if($("#realName").val()==''){
            alert("姓名不能为空!");
            return false;
        }


           requestService("/bxg/apply/updateBaseInfo",{phone:$("#phone").val(),realName:$("#realName").val()},
                   function(data) {
             //显示基本报名信息
                        if(data.success){
                            goPay();
                        }
            },false);
        

        

    }

    function  goPay() {

        var btype=   localStorage.getItem("access")
        var orderFrom;
        if(btype=='wx'){
            orderFrom=3;
        }else if(btype=='brower'){
            orderFrom=4;
        }
        requestService("/bxg/order/save", {courseId : getQueryString("id"),orderFrom:orderFrom}, function(data) {
            if (data.success) {
                var result = data.resultObject;
                location.href = "/xcviews/html/payxianxia.html?id="+getQueryString("id")+"&orderNo="+result.orderNo+"&orderId="+result.orderId+"&page=1";
            }else{
                alert("提交订单错误！请稍后再试！");
            }

        });
    }