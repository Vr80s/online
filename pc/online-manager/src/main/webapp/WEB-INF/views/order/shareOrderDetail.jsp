<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	

<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
  
  $('#returnbutton').on('click',function(){
		window.location.href=basePath+'/home#order/shareOrder/index?page=${param.page}';
  });
  
  $.ajax({
	  url:"/order/shareOrder/getOrderDetailList",
	  type:"get",
	  data:{targetUserId:"${param.createPerson}"},
	  success:function(result){
		  if(result.success){
			  var  total = 0;
			  var resuts = result.resultObject;
			  for(var i=0;i<resuts.length;i++){
				  /* if(resuts[i].level == 0){
					  total += resuts[i].subsidies;
					  $("#level1").text(resuts[i].subsidies);
				  }else if(resuts[i].level == 1){
					  total += resuts[i].subsidies;
					  $("#level2").text(resuts[i].subsidies);
				  }else if(resuts[i].level == 2){
					  total += resuts[i].subsidies;
					  $("#level3").text(resuts[i].subsidies);
				  } */
				  if(resuts[i].order_status==0){
					  total += resuts[i].subsidies;
				  	  $("#dqye").text(resuts[i].subsidies);
				  }else{
				  	  $("#ytx").text(resuts[i].subsidies);
					  total += resuts[i].subsidies;
				  }
			  }
			  $("#ljyj").text(total);
		  }
	  }
  })
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header row">
 	<div class="col-xs-8" style="padding-left: 0px;padding-right: 0px;">
			  当前位置：销售管理 <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			  <span> 佣金管理 </span><small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			 	${param.createPersonName}佣金记录
	</div>
	<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
		<button class="btn btn-sm btn-success add_bx" id="returnbutton">
			<i class="glyphicon glyphicon-arrow-left"></i>
			返回上级
		</button>
	</div>
</div>


<div class="mainrighttab tabresourse bordernone">
    <p  style="font-size: medium;padding: 0;" class="col-xs-12">
		累计佣金数额：<span style="color: blue;font-weight:bold;font-size: medium;" id="ljyj">0</span> 元 &nbsp;&nbsp;&nbsp;
		<!-- 一级佣金：<span style="color: blue;font-weight:bold;font-size: medium;" id="level1">0</span> 元     &nbsp;&nbsp;&nbsp;   
		二级佣金：<span style="color: blue;font-weight:bold;font-size: medium;" id="level2">0</span> 元    &nbsp;&nbsp;&nbsp;  
		三级佣金：<span style="color: blue;font-weight:bold;font-size: medium;" id="level3">0</span> 元   &nbsp;&nbsp;&nbsp;  -->    
		当前余额：<span style="color: blue;font-weight:bold;font-size: medium;" id="dqye">0</span> 元      &nbsp;&nbsp;&nbsp;
		已提现：<span style="color: blue;font-weight:bold;font-size: medium;" id="ytx">0</span> 元
	</p>
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
                <tr>
               		<td>
                        <div class="profile-info-value searchTr" style="display:none">
                            <input type="text"   id="search_targetUserId" name="search_targetUserId"  class="propertyValue1"  maxlength="30" value="${param.createPerson}"/>
                            <input type="hidden" value="search_targetUserId" class="propertyName"/>
                        </div>
                    </td>
                	<td>
                        <div class="profile-info-value">
                            <select id="selType">
                                <option  value="0" >分销订单号</option>
                                <option  value="1" >课程名称</option>
                                <option  value="2" >购买者(用户名)</option>
                            </select>
                        </div>
                    </td>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_shareOrderNo" name="search_shareOrderNo"  class="propertyValue1"  placeholder = "订单号" maxlength="30"/>
                            <input type="hidden" value="search_shareOrderNo" class="propertyName"/>
                        </div>
                    </td>
                	<td style="display:none">
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_courseName" name="search_courseName" class="propertyValue1"  placeholder = "课程名称" maxlength="30"/>
                            <input type="hidden" value="search_courseName" class="propertyName"/>
                        </div>
                    </td>
                	<td style="display:none">
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_createPersonName" name="search_createPersonName" class="propertyValue1"  placeholder = "购买者(用户名)" maxlength="30"/>
                            <input type="hidden" value="search_createPersonName" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker propertyValue1"  id="startTime" name="startTime" placeholder = "支付时间(起)" />
                            <input type="hidden" value="startTime" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value">
                            &nbsp;至 &nbsp;
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker propertyValue1"  id="stopTime" name="stopTime" placeholder = "支付时间(止)"/>
                            <input type="hidden" value="stopTime" class="propertyName"/>
                        </div>
                    </td>
                   <!--  <td>
                        <div class="profile-info-value searchTr">
                            <select id="level" name="level" class="propertyValue1">
                                <option  value="" >选择佣金级别</option>
                                <option  value="0" >一级</option>
                                <option  value="1" >二级</option>
                                <option  value="2" >三级</option>
                            </select>
                               <input type="hidden" value="level" class="propertyName"/>
                        </div>
                    </td> -->
                    <td>
                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
                                onclick="search();">
                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>


	<div class="row">
		<div class="col-xs-12">
			<table id="shareOrderDetailTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>
<script type="text/javascript" src="${base}/js/order/shareOrderDetail.js?v=1.7"></script>
