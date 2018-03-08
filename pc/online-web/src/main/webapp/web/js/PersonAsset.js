/**
 * Created by suixin on 2017/8/28.
 */
// 左侧点击变色效果

//最有三个li设置点击事件
//$('#main-left li:nth-last-child(-n+3)').click(function () {
//    //点至之后最后三个的颜色先变成默认样式
//    $('#main-left li:nth-last-child(-n+3)').css({'background-color': '#e4e4e4'})
//    //li里面的a链接变色
//    $('#main-left li:nth-last-child(-n+3)').find('a').css({'color': '#000'})
//    //再使点击的li颜色变色
//    $(this).css({'background-color': 'green'})
//    //li里面的a链接变色
//    $(this).find('a').css({'color': '#fff'})
//})

	// 遮罩层显示方法
	function showMask(){
	   
	    $("#mask").show();
	}
	//遮罩层隐藏方法
	function hideMask(){

	    $("#mask").hide();
	}
	//提现表单数据请求
	function showData(){
		RequestService("/userCoin/balance", "get", null, function(data) {
			// var username = data.resultObject.name;
			// var telnumber = data.resultObject.phone;
			var totalmondy = data.resultObject;
//	    	将数据渲染到提现模态框上面
// 			$('.userName').val(username);
// 			$('.telNumber').val(telnumber)
			$('.toCashMoney').text(totalmondy);
			// //点击全部提现按钮
			// $('.totalTocash').click(function(){
			// 	$('.totalMoney').val(totalmondy)
			// })
		})
	}
	
	
	function getPersonMoney(){
		 //请求个人余额
		RequestService("/userCoin/balance", "get", null, function(data) {
//			console.log(data);
			if(data.success == true){
//				console.log(data.resultObject.balanceTotal)
				$('.restMoney').text(data.resultObject)
			}else{
				$('.restMoney').text('0')
			}
			
		})
	}
	
	
	
	
	
	function initBind(){
		showData();
//		//请求个人余额
//		RequestService("/userCoin/balance", "get", null, function(data) {
//			console.log(data);
//			if(data.success == true){
//				//console.log(data.resultObject.balanceTotal)
//				$('.restMoney').text(data.resultObject.balanceTotal)
//			}else{
//				$('.restMoney').text('0')
//			}
//			
//		})
		getPersonMoney();
		
		
		
		//调用bootstrap的工具提示框
		 $( '.tab-content .yongTu' ).tooltip();
		
		//提现效果
		$('#toCash').click(function () {
			$('.cashwarn').text('');
		    showMask()
		    $('#cash-model').fadeIn()
		    $('#cash-model input').removeClass('red')
		    $('.totalMoney').val('')
		    $('.telNumber').val('')
		    //请求数据渲染提现列表
		    showData();
		})
		//查看兑换比例
		$('.detail').click(function(){
			$('#rmbTopanda').css({'display':'block'})
			showMask();
		})
		//关闭比例
		$('.closeTip').click(function(){
			$('#rmbTopanda').css({'display':'none'})
			hideMask();
		})

		//确定提交
		$('#sure-put').click(function(){
			//初始化验证的表单
			$('.cashipt').removeClass('red');
			$('.cashwarn').text('');
			var name = $('.userName').val();
			var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;
			var phone = $('.telNumber').val();
			var zfb_pay = $('.eaccount').val();
			var totalMoney = $('.totalMoney').val();
			var phone_pass = /^1[3|4|5|7|8][0-9]{9}$/;
//			/^1[3,4,5,7,8]\d{9}$/gi 
			var Zfb_pass1 = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
			var toCashPass = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			//姓名验证
			if($.trim(name) == ""){
				$(".userName").addClass('red')
				$(".userName-warn").text('姓名不能为空');
				return false;
			}
			
			if(!name_pass.test(name)){
//				$(".userName").val('')
				$(".userName").addClass('red')
				$(".userName-warn").text('姓名格式不正确')
				return false;
			}
			
			//手机验证
			if($.trim(phone) == ""){
				$(".telNumber").addClass('red')
				$(".telNumber-warn").text('手机号不能为空')
				return false;
			}
			if(!(phone_pass.test(phone))){
//				$('.telNumber').val('')
				$(".telNumber").addClass('red')
				$(".telNumber-warn").text('手机号格式不正确')
				return false;
			}
			
			//支付宝帐号验证
			if($.trim(zfb_pay) == ""){
				$(".eaccount").addClass('red')
				$(".eaccount-warn").text('支付宝账号不能为空')
				return false;
			}
//			
			if(!phone_pass.test(zfb_pay) && !Zfb_pass1.test(zfb_pay)){
//				$('.eaccount').val('')
				$(".eaccount").addClass('red')
				$(".eaccount-warn").text('支付宝账号格式不正确')
				return false;
			}
			//金额判断
			if($.trim(totalMoney) == ""){
				$(".totalMoney").addClass('red')
				$(".totalMoney-warn").text('提现金额不能为空')
				return false;
			}
			if(!toCashPass.test(totalMoney)){
//				$('.totalMoney').val('')
				$(".totalMoney").addClass('red')
				$(".totalMoney-warn").text('提现金额格式不正确')
				return false;
			}
			
			
			
			
		    RequestService("/online/user/isAlive", "get", null, function(data) {
		      if(data.success == true) {
		    	  RequestService("/enchashment/enchashment", "post", {
				    	realName:$('.userName').val(),
				    	phone:$('.telNumber').val(),
				    	enchashmentSum:$('.totalMoney').val(),
				    	enchashmentAccount:$('.eaccount').val(),
				    	enchashmentAccountType:0
				    }, function(data) {
				    	if(data.success){
				    		$('#cash-model').hide()
				    		$('#cash-success').fadeIn()	
				    		//后面数据自动渲染跳转到提现记录页面
				    		$('.messages').click();
				    		//重新渲染余额
				    		
				    	}else{
//				    		alert(data.errorMessage);
				    		
				    		$('.mask2').html(data.errorMessage).fadeIn(400,function(){
								
								setTimeout(function(){
									$('.mask2').fadeOut()
								},1000)
							});
				    		
				    	}
					})
		      } else {
		        location.href = "../../index.html";
		        localStorage.username = null;
		        localStorage.password = null;
		        $(".login").css("display", "none");
		        $(".logout").css("display", "block");
		        
		      }
		      });
		})

		//继续按钮
		$('#continue').click(function(){
			//请求数据重新渲染
			RequestService("/enchashment/enchashmentData", "get", null, function(data) {
                // var username = data.resultObject.name;
                // var telnumber = data.resultObject.phone;
                var totalmondy = data.resultObject;
//	    	将数据渲染到提现模态框上面
//                 $('.userName').val(username);
//                 $('.telNumber').val(telnumber)
                $('.toCashMoney').text(totalmondy)
                // //点击全部提现按钮
                // $('.totalTocash').click(function(){
                // 	$('.totalMoney').val(totalmondy)
                // })
			})
		    $('#cash-model').fadeIn();
		    $('#cash-success').hide();
		    //重新渲染余额
		    getPersonMoney();
		})

		//点击关闭功能
//		$('.cash-close').click(function(){
//		    hideMask()
//		    $('#cash-model').hide()
//		    $('#cash-success').hide()
//		    showData();
//		    //重新渲染余额
//		    getPersonMoney();
//		    
//		})
		
		
		//默认请求渲染
		window.pageNum = 1;
		getData(pageNum)
		function fenye(currentPage,numberOfPages,totalPages){
			if(numberOfPages>5){
				numberOfPages = 5;
			}
			//分页结构结合数据渲染部分
			$('.pagination').bootstrapPaginator({
	            /*通过参数配置功能*/
	            /*Bootstrap 是2.X 使用div元素，3.X使用ul元素*/
	            bootstrapMajorVersion:3,
	            /*显示小的分页按钮*/
	            size:'small',
	            /*当前页码*/
	            currentPage:currentPage,
	            /*显示多少个按钮*/
	            numberOfPages:numberOfPages,
	            /*一共有多少页*/
	            totalPages:totalPages,
	            /*绑定点击事件*/
	            onPageClicked:function (event, originalEvent, type,page) {
//	                console.log(page)    
	                /*page  当前点击的页面*/
	                window.pageNumber = page;
	                //点击分页部分控制数据请求
	                getData(pageNumber)
	            }
	        });
		}
		function getData(pageNumber){
			//请求消费记录数据
			RequestService("/web/consumptionList?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data) {
//				console.log(data)
				if(data.resultObject.totalPageCount==0){
					$('.pagination').css({'display':'none'});
					$("#xfjl").html(template("list1",{item:data.resultObject.items}));
				}else{
					$('.pagination').css({'display':'block'});
					//渲染到页面中
					 $("#xfjl").html(template("list1",{item:data.resultObject.items}));
					//每次请求完数据就去渲染分页部分
					fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
				}
			})
		}
		
		
		
		
		//消费记录点击
		$('.home').click(function(){
			showData();
			getPersonMoney()
			window.pageNum = 1;
			getData(pageNum)
			function getData(pageNumber){
				RequestService("/web/consumptionList?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data) {
//					console.log(data)
					if(data.resultObject.totalCount==0||data.resultObject.totalCount<=6){
						$('.pagination').css({'display':'none'});
						$("#xfjl").html(template("list1",{item:data.resultObject.items}));
					}else{
						$('.pagination').css({'display':'block'});
						 $("#xfjl").html(template("list1",{item:data.resultObject.items}));
							fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					}
				})
			}
		})
		
		
		//熊猫币消费记录
		$('.panda').click(function(){
			showData();
			getPersonMoney();
			window.pageNum = 1;
			getData(pageNum)
			function fenye(currentPage,numberOfPages,totalPages){
				if(numberOfPages>5){
					numberOfPages = 5;
				}
			
				$('.pagination').bootstrapPaginator({
		            bootstrapMajorVersion:3,
		            size:'small',
		            currentPage:currentPage,
		            numberOfPages:numberOfPages,
		            totalPages:totalPages,
		            onPageClicked:function (event, originalEvent, type,page) {
//		                console.log(page)    
		                window.pageNumber = page;
		                getData(pageNumber)
		            }
		        });
			}
			function getData(pageNumber){
				RequestService("/userCoin/userCoinConsumptionRecord?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data){
//					console.log(data)	
					if(data.resultObject.totalCount==0||data.resultObject.totalCount<=6){
						$('.pagination').css({'display':'none'});
						 $("#pandaMondy").html(template("list6",{item:data.resultObject.items}));
					}else{
						$('.pagination').css({'display':'block'});
						 $("#pandaMondy").html(template("list6",{item:data.resultObject.items}));
							fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					}
					
				})
			}
		})
		
		
		
		
		
		
		//充值记录点击
		$('.profile').click(function(){
			showData();
			getPersonMoney();
			window.pageNum = 1;
			getData(pageNum)
			function fenye(currentPage,numberOfPages,totalPages){
				if(numberOfPages>5){
					numberOfPages = 5;
				}
			
				$('.pagination').bootstrapPaginator({
		            bootstrapMajorVersion:3,
		            size:'small',
		            currentPage:currentPage,
		            numberOfPages:numberOfPages,
		            totalPages:totalPages,
		            onPageClicked:function (event, originalEvent, type,page) {
//		                console.log(page)    
		                window.pageNumber = page;
		                getData(pageNumber)
		            }
		        });
			}
			function getData(pageNumber){
				RequestService("/userCoin/userCoinIncreaseRecord?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data){
//					console.log(data)				
					if(data.resultObject.totalCount==0||data.resultObject.totalCount<=6){
						$('.pagination').css({'display':'none'});
						 $("#payMoney").html(template("list2",{item:data.resultObject.items}));
					}else{
						$('.pagination').css({'display':'block'});
						 $("#payMoney").html(template("list2",{item:data.resultObject.items}));
							fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					}
					
				})
			}
		})
		
		
		//提现记录点击
		$('.messages').click(function(){
			showData();
			getPersonMoney();
			window.pageNum = 1;
			getData(pageNum)
			function fenye(currentPage,numberOfPages,totalPages){
				if(numberOfPages>5){
					numberOfPages = 5;
				}
				$('.pagination').bootstrapPaginator({
		            bootstrapMajorVersion:3,
		            size:'small',
		            currentPage:currentPage,
		            numberOfPages:numberOfPages,
		            totalPages:totalPages,
		            onPageClicked:function (event, originalEvent, type,page) {
//		                console.log(page)    
		                window.pageNumber = page;
		                getData(pageNumber)
		            }
		        });
			}
			function getData(pageNumber){
				RequestService("/enchashment/enchashmentList?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data) {
					for(var i in data.resultObject.items){
						if(data.resultObject.items[i].enchashmentAccountType==0){
							data.resultObject.items[i].enchashmentAccountType="支付宝";
						}else if(data.resultObject.items[i].enchashmentAccountType==1){
							data.resultObject.items[i].enchashmentAccountType="微信";
						}else if(data.resultObject.items[i].enchashmentAccountType==2){
							data.resultObject.items[i].enchashmentAccountType="网银";
						}
						if(data.resultObject.items[i].enchashmentStatus==0){
							data.resultObject.items[i].enchashmentStatus="审核中";
						}else if(data.resultObject.items[i].enchashmentStatus==1){
							data.resultObject.items[i].enchashmentStatus="已打款";
						}else if(data.resultObject.items[i].enchashmentStatus==2){
							data.resultObject.items[i].enchashmentStatus="已驳回";
						}
					}
					if(data.resultObject.totalCount==0||data.resultObject.totalCount<=6){
						$('.pagination').css({'display':'none'});
						 $("#txjl").html(template("list3",{item:data.resultObject.items}));
					}else{
//						console.log(data);
						$('.pagination').css({'display':'block'});
						 $("#txjl").html(template("list3",{item:data.resultObject.items}));
							fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					}
//					
				})
			}
		})
		
		
		//收到礼物点击
		$('.settings').click(function(){
			showData();
			getPersonMoney();
			window.pageNum = 1;
			getData(pageNum)
			function fenye(currentPage,numberOfPages,totalPages){
				if(numberOfPages>5){
					numberOfPages = 5;
				}
				$('.pagination').bootstrapPaginator({
		            bootstrapMajorVersion:3,
		            size:'small',
		            currentPage:currentPage,
		            numberOfPages:numberOfPages,
		            totalPages:totalPages,
		            onPageClicked:function (event, originalEvent, type,page) {
//		                console.log(page)    
		                window.pageNumber = page;
		                getData(pageNumber)
		            }
		        });
			}
			function getData(pageNumber){
				RequestService("/gift/receivedGift?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data) {
//					console.log(data);
					if(data.resultObject.totalCount==0||data.resultObject.totalCount<=6){
						$('.pagination').css({'display':'none'});
						 $("#sdlw").html(template("list4",{item:data.resultObject.items}));
					}else{
						$('.pagination').css({'display':'block'});
						 $("#sdlw").html(template("list4",{item:data.resultObject.items}));
							fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					}
					
				})
			}
		})
		
		
		
		//收到打赏
		$('.reward').click(function(){
			showData();
			getPersonMoney();
			window.pageNum = 1;
			getData(pageNum)
			function fenye(currentPage,numberOfPages,totalPages){
				if(numberOfPages>5){
					numberOfPages = 5;
				}
				$('.pagination').bootstrapPaginator({
		            bootstrapMajorVersion:3,
		            size:'small',
		            currentPage:currentPage,
		            numberOfPages:numberOfPages,
		            totalPages:totalPages,
		            onPageClicked:function (event, originalEvent, type,page) {
//		                console.log(page)    
		                window.pageNumber = page;
		                getData(pageNumber)
		            }
		        });
			}
			function getData(pageNumber){
				RequestService("/gift/receivedReward?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data) {
					console.log(data)
					if(data.resultObject.totalCount==0||data.resultObject.totalCount<=6){
						$('.pagination').css({'display':'none'});
						 $("#dsjl").html(template("list5",{item:data.resultObject.items}));
					}else{
						$('.pagination').css({'display':'block'});
						 $("#dsjl").html(template("list5",{item:data.resultObject.items}));
							fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					}
					
				})
			}
		})
		
		$('.pagination').css({"cursor": 'pointer'});
		
		
		
		
////		
////		//直播列表数据获取
//		function getData2(pageNumber){
//			//请求消费记录数据
//			RequestService("/gift/getLiveCourseByUserId?pageNumber="+pageNumber+"&pageSize=6", "POST", null, function(data) {
//				console.log(data)
//				if(data.resultObject.totalPageCount==0){
//					$('.pagination').css({'display':'none'});
//				}else{
//					//渲染到页面中
//					 $("#jiangshi").html(template("list7",{item:data.resultObject.items}));
//					//每次请求完数据就去渲染分页部分
//					fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
//				}
//			})
//		}
////		
		
		
		//直播列表
		$('.lecturer').click(function(){
			showData();
			getPersonMoney();
			window.pageNum = 1;
			getData(pageNum)
			function fenye(currentPage,numberOfPages,totalPages){
				if(numberOfPages>5){
					numberOfPages = 5;
				}
				$('.pagination').bootstrapPaginator({
		            bootstrapMajorVersion:3,
		            size:'small',
		            currentPage:currentPage,
		            numberOfPages:numberOfPages,
		            totalPages:totalPages,
		            onPageClicked:function (event, originalEvent, type,page) {
//		                console.log(page)    
		                window.pageNumber = page;
		                getData(pageNumber)
		            }
		        });
			}
			function getData(pageNumber){
				RequestService("/gift/getLiveCourseByUserId?pageNumber="+pageNumber+"&pageSize=6", "get", null, function(data) {
//					console.log(data);
					for (var i =0;i<data.resultObject.items.length;i++) {
								data.resultObject.items[i].startTime = data.resultObject.items[i].startTime.substring(0,19);
							}
					if(data.resultObject.totalCount==0||data.resultObject.totalCount<=6){
						$('.pagination').css({'display':'none'});
						
						 $("#jiangshi").html(template("list7",{item:data.resultObject.items}));
					}else{
						$('.pagination').css({'display':'block'});
						 $("#jiangshi").html(template("list7",{item:data.resultObject.items}));
							fenye(data.resultObject.currentPage,data.resultObject.totalPageCount,data.resultObject.totalPageCount);
					}
					
				})
			}	
		})
		
				
		
		
		
		
		//开启
		$('#recharge').click(function(){
            RequestService("/userCoin/userDataForRecharge", "GET", {
            }, function (data) {
                rate = data.resultObject.rate;
                account = data.resultObject.account;
                balanceTotal = data.resultObject.balanceTotal;
                env = data.resultObject.env;

                $("#account").html(account);
                $(".balanceTotal").html(balanceTotal);
            },false);
//            console.log(1)
            price=100/rate;//初始化为10元
            $('.number').text(price);
            $('#main1').addClass('show')

			$('#mask').css({'display':'block'})
		})
		//关闭按钮
		$('.close').click(function(){
	$('#main1').removeClass('show')
	$('#mask').css({'display':'none'})
	$('.erweima').css({'display':'none'})
	$('.content_two li:first-child').click()
	$('.content_three li:first-child').click()
	
	 hideMask()
		    $('#cash-model').hide()
		    $('#cash-success').hide()
		    showData();
		    //重新渲染余额
		    getPersonMoney();
})
		
	


//点击查询得类型存储localStorage
$('.nav-tabs a').click(function(){
	var aa = $(this).prop('class');
	window.localStorage.findStyle = aa;
//	return $(this).parent().find("a").click();
})


if(window.localStorage.findStyle!=""&&window.localStorage.findStyle!="undefined"){
	//跳转充值页面
	$(".nav-tabs  ." + window.localStorage.findStyle).click();
}


	}
	
	
	
