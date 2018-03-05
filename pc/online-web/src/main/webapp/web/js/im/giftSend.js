// XMPP连接
var connection = null;

// 当前状态是否连接
var connected = false;

// 当前登录的JID
var jid = "";

var giftList;

//初始化没有参数的队列
var queue = new Queue();

// 连接状态改变的事件
function onConnect(status) {
    if (status == Strophe.Status.CONNFAIL) {
//        alert("连接失败！");
        autoLogin();
    } else if (status == Strophe.Status.AUTHFAIL) {
//        alert("登录失败！");
        autoLogin();
    } else if (status == Strophe.Status.DISCONNECTED) {
//        alert("连接断开！");
        autoLogin();
        connected = false;
    } else if (status == Strophe.Status.CONNECTED) {
//        alert("连接成功，可以开始聊天了！");
        connected = true;
        
        // 当接收到<message>节，调用onMessage回调函数
        connection.addHandler(onMessage, null, 'message', null, null, null);
        
        // 首先要发送一个<presence>给服务器（initial presence）
        connection.send($pres().tree());

        // 发送<presence>元素，加入房间
		var pres = $pres({
            from: jid,
            to: ROOM_JID + "/" + guId//jid.substring(0,jid.indexOf("@"))
        }).c('x',{xmlns: 'http://jabber.org/protocol/muc'}).tree();
        connection.send(pres);
		//connection.sendIQ(pres);//获取房间列表
	}
}

String.prototype.replaceAll = function (FindText, RepText) {
	regExp = new RegExp(FindText, "g"); 
	return this.replace(regExp, RepText); 
	}

// 接收到<message>
function onMessage(msg) {
    // 解析出<message>的from、type属性，以及body子元素
    var from = msg.getAttribute('from');
    var type = msg.getAttribute('type');
    var elems = msg.getElementsByTagName('body');

    if (type == "groupchat" && elems.length > 0) {
    	try{
	        var body = elems[0];
	        var text = Strophe.getText(body);
	        text = text.replaceAll("&quot;","\"");
        	data = JSON.parse(text);
        	debugger;
        	createGiftList(data);
    	}catch(err){
//        	console.info(err);
    	}
    }
    return true;
}

function repalceAll(str,rstr,arstr){
	while(str.split(rstr).length>1){
		str = str.replace(rstr,arstr);
	}
	return str;
}

$(document).ready(function() {

    // 通过BOSH连接XMPP服务器
    $('#btn-login').click(function() {
        if(!connected) {
            connection = new Strophe.Connection(BOSH_SERVICE);
            connection.connect($("#input-jid").val(), $("#input-pwd").val(), onConnect);
            jid = $("#input-jid").val();
        }
    });
    
    function sendMsg(data){
    	data = JSON.stringify(data);
    	data = JSON.parse(data);
		// 创建一个<message>元素并发送
		var msg = $msg({
			to: ROOM_JID, 
			from: jid,
			type: 'groupchat'
		}).c("body", null, JSON.stringify(data));
		connection.send(msg.tree());
    }
	autoLogin();
	
	var lastGift=null;
	var selectGift=null;
	var lastTime = new Date();
	var isContinuous=false;
	var myid =null;
	//获取右侧底部的礼物数据
	RequestService("/gift/getGift", "GET", {
	}, function(data) {
		for(var i in data.resultObject){
			if(data.resultObject[i].isContinuous){
				data.resultObject[i].isContinuous = 1;
			}else{
				data.resultObject[i].isContinuous = 0;
			}
			if(data.resultObject[i].isFree){
				data.resultObject[i].isFree = 1;
			}else{
				data.resultObject[i].isFree = 0;
			}
		}
		giftList = data.resultObject;
		var html = template('gift_temp',data);
		$('#gifList').html(html)
		
		//右侧送礼物代码
		//点击渲染的图片时
		$('#gifList li').click(function(){
			//获取最新的时间
			var newtime = new Date();
			//下面的小图标获取对应的图片url
			$('.gif-num-small img').attr("src",$(this).find('img').attr('src')).css('opacity','1')
			//判断按钮是充值/发送 
			if(($('.balance').text()-0)<($(this).find('.gif-free').text()-0)){
				$('#chat-submit').text("充值")
			}else{
				$('#chat-submit').text("发送")
			}
			$('.gif-num-small em').text('x')
			//30秒内 /可连击/最后一次发送与本次点击相同
//			if(parseInt(newtime - lastTime)/1000 <=30 && $(this).attr('data-iscontinuous')=='1' && $(this).attr('data-id') == lastGift){
//				$('.gif-num').text($(this).attr('data-countinuousCount'));
//				isContinuous = true;
//			}else{
//				$('.gif-num').text('1')
//				isContinuous = false;
//			}
				$('.gif-num').text('1')
			//获取当前点击的li的id后面使用
			myid =  $(this).attr('data-id');
		})			
	});
	
		//点击发送时候的送礼物效果/充值事件
	$('#chat-submit').click(function(){
		//判断发送和充值时候出发的不同的事件
		if($(this).text()=='发送' ){
			if(myid==null)return;
			for(var i in giftList){
        		if(giftList[i].id == myid){
        			selectGift=giftList[i];
        			if(isContinuous){
        				selectGift.count=selectGift.countinuousCount;
        			}else{
        				selectGift.count=1;
        			}
        		}
			}
			//获取数量
			var gifNumber =Number($('.gif-num').text()); 
			var msgJson = {
					channel:1,
					giftId:selectGift.id,
					count:1,
					clientType:1,
					liveId:course_id,
					receiver:teacherId,
					receiverName:teacherName
			};
			RequestService("/gift/sendGift", "POST", msgJson, function(data) {
				if(data.success==true){
        			sendMsg(data.resultObject);
        			refreshBalance();
				}else{
					if("余额不足"==data.errorMessage){
                        $('.mask3').text('余额不足').fadeIn(400,function(){
                            setTimeout(function(){
                                $('.mask3').fadeOut()
                            },1000)
                        });
					}
				}
			});
//			$("#chat-content").val('');

			//获取当前点击时候的id/点击的时间
			lastGift = myid ;
			lastTime = new Date();
			
		}else if($(this).text() == '充值'){
			//用户的充值事件写在这里/充值状态不能够发送礼物
			price=100/rate;//初始化为10元
			$('.number').text(price);
			$('#main1').addClass('show')
			$('.mask').css({'display':'block'})
		}
	})
	
});

function createGiftList(data){
	if(data.messageType==1){

		debugger;
		
/*		<div class="headImg" style="float: left;">
			<img src="http://attachment-center.ixincheng.com:38080/data/picture/online/2017/09/23/14/0b77783055f44003bb32228eb3549887.png">
		</div>*/
		
		
		//获取最后一次的id
		var li = $('<li style="background-color:#fafafa;margin-bottom: 10px"></li>');
		li.html("<li class='clearfix' style='position: relative;background-color:#fafafa;margin-left:0;'>" +
				"<div class='headImg' style='float: left;'>" +
			 	"  <img style ='width:54px;height:54px;border-radius: 60px;margin-right: 10px;' src='"+data.senderInfo.avatar+"'>" +
			    " </div>" +
				"<div class='sender-gif'>" +
				"<p>"+data.senderInfo.userName+"：</p>" +
				"<span>赠送给主播</span>&nbsp;&nbsp;" +
				"<span>"+data.giftInfo.name+"</span>" +
				"</div>" +
				"<div class='imgNum'><img style='position: absolute;width: 54px;right: 25px;height: 54px;' src="+data.giftInfo.smallimgPath+">" +
				"<span style=' position: absolute; right: 0;top: 20px'>X"+data.giftInfo.continuousCount+"</span>" +
				"</div>" +
		"</li>")
		$('#chat-list').append(li);
		$(".liwu").html(data.giftCount);
		var a = $('#chat-list');
		a.scrollTop(a[0].scrollHeight);
		
		if(data.giftInfo.time==null)return;
		
		if(parseInt(sendTime)>parseInt(data.giftInfo.time))return;
		if($("#"+data.senderInfo.userId+data.giftInfo.giftId).length>0){
            giftShow(data,$("#"+data.senderInfo.userId+data.giftInfo.giftId).attr("xh"),true);
		}else{
            queue.push(data);
            createGiftShow();
		}


	}else if(data.messageType==0){//打赏
	//右侧生成打赏得礼物
	// 		var li = $('<li style="background-color:#fafafa;margin-bottom: 10px"></li>');
	// 		li.html("<li class='clearfix' style='position: relative;background-color:#fafafa;margin-left:0;' >" +
	// 				"<div class='headImg' style='float: left;'>" +
	// 			 	"  <img style ='width:54px;height:54px;border-radius: 60px;margin-right: 10px;' src='"+data.senderInfo.avatar+"'>" +
	// 			    " </div>" +
	// 				"<div class='sender-gif'>" +
	// 				"<p>"+data.senderInfo.userName+"：</p>" +
	// 				"<span>打赏给主播&nbsp;&nbsp;红包</span>&nbsp;&nbsp;" +
	// 				"</div>" +
	// 				"<div class='imgNum'><img src='../../../images/hongbao.png' style='position: absolute;width: 54px;height: 54px;right: 25px;'>" +
	// 				"</div>" +
	// 		"</li>")
	// 		$('#chat-list').append(li);
	// 		var a = $('#chat-list');
	// 		a.scrollTop(a[0].scrollHeight);
	// 	console.info("打赏："+data)
	// 	if(parseInt(sendTime)>parseInt(data.rewardInfo.time))return;
     //    $(".dashang").html(data.rewardTotal);
	// 	queue.push(data);
	// 	createGiftShow();
	// 	if(data.senderInfo.userId==userId){
	// 		rewardClose();
	// 	}
	}
    //console.log(gift)
}


function autoLogin(){
	connection = new Strophe.Connection(BOSH_SERVICE);
	connection.connect(guId+'@'+host, guPwd, onConnect);
	jid = guId+'@'+host;
}

//两个占位 f1 f2
var f1 = true;
var f2 = true;
var f3 = true;
var f4 = true;

function createGiftShow(){

	//若f1位可用，。。。
	if(f1){
		f1=false;
		var gift = queue.pop();
		//将礼物显示出来，设置显示时间。时间到了，f1 = true;，礼物占位结束。
		giftShow(gift,1);
	}else if(f2){//若f2位可用，。。。
		f2=false;
		var gift = queue.pop();
		//将礼物显示出来，设置显示时间。时间到了，f1 = true;，礼物占位结束。
		giftShow(gift,2);
	}else if(f3){//若f2位可用，。。。
        f3=false;
        var gift = queue.pop();
        //将礼物显示出来，设置显示时间。时间到了，f1 = true;，礼物占位结束。
        giftShow(gift,3);
    }else if(f4){//若f2位可用，。。。
        f4=false;
        var gift = queue.pop();
        //将礼物显示出来，设置显示时间。时间到了，f1 = true;，礼物占位结束。
        giftShow(gift,4);
    }else if(!f1&&!f2&&!f3&&queue.size()>0){
		setTimeout(function(){createGiftShow();},1000);
	}
}

//生成礼物
var count = 1;
function countChange(){
	if(count==1){
		count=2;
		return 350;
	}else if(count==2){
        count=3;
        return 300;
    }else if(count==3){
        count=4;
        return 250;
    }else{
		count=1;
		return 200;
	}
}
var gif=[];    
var num=[];	
var min=[];	
// var addn=[];
var sto=[];
function clearGift(f){
    gif[f].remove();
    $("#gift"+f).remove();
    if(f == 1){
        f1 = true;
    }else if(f == 2){
        f2 = true;
    }else if(f == 3){
        f3 = true;
    }else{
        f4 = true;
    }
}
function giftShow(gift,f,continuous){
    if(continuous){
        $("#"+gift.senderInfo.userId+gift.giftInfo.giftId).html(gift.giftInfo.continuousCount);
        // clearTimeout(sto[f]);
        // $("#gift"+f).appendTo($("#boxDom"));
        // sto[f] = setTimeout(function (){
        //     clearGift(f);
        // },3000);
        $('.addnum'+f).data("sto",new Date().getTime())
        return;
    }
	var colors = ["red", "green", "hotpink", "pink", "cyan", "yellowgreen", "purple", "deepskyblue"];
   
    if(gift.messageType==1){
	    var top=countChange()
    	gif[f] = $( "<div class='big' id='gift"+f+"' style='width: 500px;height: 46px;line-height: 46px;background: url(../../../images/456.png) no-repeat;padding-left: 10px;position: absolute;bottom: "+top+"px;'>" +
    			"<div class='left' style='height: 100%;display: inline-block;vertical-align: top;'>" +
    			"<span>"+gift.senderInfo.userName+"</span>&nbsp;" +
    			"<span>送&nbsp;"+gift.giftInfo.name+"</span>" +
    			"</div><img src="+gift.giftInfo.smallimgPath+" style='height: 54px;width:54px;margin-left: 10px;'>" +
    			"<span class='' style='height: 100%;display: inline-block;vertical-align: top;margin-left: 10px;color: white;font-size: 24px;'>x<i class='addnum"+f+"' style='font-size: 30px;font-weight: 700;' id='"+gift.senderInfo.userId+gift.giftInfo.giftId+"' xh='"+f+"'>"+gift.giftInfo.continuousCount+"</i></span>" +
    	"</div>")	
    }else if(gift.messageType==0){
    	var top=countChange()
    	gif[f] = $( "<div class='big' style='width: 500px;height: 46px;line-height: 46px;background: url(../../../images/456.png) no-repeat;padding-left: 10px;position: absolute;bottom: "+top+"px;'>" +
    			"<div class='left' style='height: 100%;display: inline-block;vertical-align: top;'>" +
    			"<span>"+gift.senderInfo.userName+"</span>&nbsp;" +
    			"<span>打赏给主播一个红包</span>" +
    			"</div><img src='../../../images/hongbao.png' style='width: 54px;height: 54px;margin-left: 10px;'>" +
    	"</div>")	
    	
    }

    //礼物弹幕生成效果
    	 gif[f].appendTo($("#boxDom"))
    	   .css("color", colors[Math.floor(Math.random() * 8)])
    	   .css("left", "-500px")//初始未知
    	   .animate({// 设置运动
    	       "left": "50px"
    	     }, 500, "linear", function () {
    	    	 if(f==1){
                     $('.addnum'+f).html(gift.giftInfo.continuousCount);
                     $('.addnum'+f).data("sto",new Date().getTime())
                     // sto[1] = setTimeout(function (){
                     //     clearGift(f);
                     // },3000);
    	    	 }else if(f==2){
                     $('.addnum'+f).html(gift.giftInfo.continuousCount);
                     $('.addnum'+f).data("sto",new Date().getTime())
                     // sto[2] = setTimeout(function (){
                     //     clearGift(f);
                     // },3000);
                 }else if(f==3){
                     $('.addnum'+f).html(gift.giftInfo.continuousCount);
                     $('.addnum'+f).data("sto",new Date().getTime())
                     // sto[3] = setTimeout(function (){
                     //     clearGift(f);
                     // },3000);
                 }else{
                     $('.addnum'+f).html(gift.giftInfo.continuousCount);
                     $('.addnum'+f).data("sto",new Date().getTime())
                     // sto[4] = setTimeout(function (){
                     //     clearGift(f);
                     // },3000);
    	    	 }
    	     });

}
/**
 * [Queue]
 * @param {[Int]} size [队列大小]
 */
function Queue(size) {
    var list = [];

    //向队列中添加数据
    this.push = function(data) {
        if (data==null) {
            return false;
        }
        //如果传递了size参数就设置了队列的大小
        if (size != null && !isNaN(size)) {
            if (list.length == size) {
                this.pop();
            }
        }
        list.unshift(data);
        return true;
    }

    //从队列中取出数据
    this.pop = function() {
        return list.pop();
    }

    //返回队列的大小
    this.size = function() {
        return list.length;
    }

    //返回队列的内容
    this.quere = function() {
        return list;
    }
}
//编辑送礼物的数量
$(document).on('click','.gif-num',function(){
	console.log(11)
	//遮罩层出现
	$(".mask2").css({'display':'block'});
	//列表出现
	$('.send-gif-num').css({'display':'block'});
	//点击切换数量
	$('.send-gif-num li:nth-last-child(-n+5)').click(function(){
		var a = $(this).attr('data-value')
		$('.gif-num').text(a) ;
		$(".mask2").css({'display':'none'});
		$('.send-gif-num').css({'display':'none'});
		$('.gif-num').attr('contenteditable','false')
	})
	//点击其他数量进行编辑
	$('.send-gif-num li:first-child()').click(function(){
		$(".mask2").css({'display':'none'});
		$('.send-gif-num').css({'display':'none'});
		$('.gif-num').attr('contenteditable','true')
		$('.gif-num').text('')
		$('.gif-num').focus();
		//输入数字
		$('.gif-num').keyup(function(e){
			$(this).selectionEnd = $(this).text().length;
			//限制输入整数 不能又小数点
			 if((!Number($(this).text()*1))||($(this).text()).indexOf('.')!==-1){
				 $(this).text('')
			 }
			 //输入回车
			var keycode = e.which;
			if(keycode == 13&&($(this).text()!='')){
				 $(this).text( $(this).text())
//				 $('.sub').click();
				 $('.gif-num').attr('contenteditable','false')
				 
			}
			 //限制输入4位数
			 if($(this).text().length>4){
				 $(this).text('')
			 }
			 
			 //将输入的数量传到后台的代码可以写在这里
			 
			 
		})
	
		
	})
	
	
})
$(function () {
    setInterval(function(){
        for(var i=1;i<5;i++){
            var t = new Date().getTime()-$('.addnum'+i).data("sto");
            if(t>3000){
            	var f = $('.addnum'+i).attr("xh");
                if(f == 1){
                    f1 = true;
                }else if(f == 2){
                    f2 = true;
                }else if(f == 3){
                    f3 = true;
                }else{
                    f4 = true;
                }
                $("#gift"+i).remove();
			}
        }
    },500)
});
