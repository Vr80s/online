<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<style type="text/css">
#showOrderTable td{
	text-align: left;
}
</style>
<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：销售管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 订单管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<!-- <p class="col-xs-2" style="padding: 0;">
		<button class="btn btn-sm btn-success dele_bx" title="批量删除">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
	</p> -->
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void style="width: 100%">
                <tr>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker propertyValue1"  id="startTime" name="startTime" placeholder = "起始时间" />
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
                            <input type="text" class="datetime-picker propertyValue1"  id="stopTime" name="stopTime" placeholder = "结束时间"/>
                            <input type="hidden" value="stopTime" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="search_payType" name="search_payType" class="propertyValue1">
                                <option  value="" >支付方式</option>
                                <option  value="0" >支付宝</option>
                                <option  value="1" >微信支付</option>
                            </select>
                            <input type="hidden" value="search_payType" class="propertyName" />
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="search_order_from" name="order_from" class="propertyValue1">
                                <option  value="" >订单来源</option>
                                <option  value="1" >PC</option>
                                <option  value="2" >H5</option>
                                <option  value="3" >APP</option>
                            </select>
                            <input type="hidden" value="order_from" class="propertyName" />
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value">
                        	关键词&nbsp;
                            <select id="selType" class="propertyValue1">
                                <option  value="1" >订单号</option>
                                <option  value="2" >购买者</option>
                            </select>
                        </div>
                    </td>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_courseName" name="search_courseName"  class="propertyValue1"  placeholder = "课程名称关键字" maxlength="30"/>
                            <input type="hidden" value="search_courseName" class="propertyName"/>
                        </div>
                    </td>
                	<td style="display:none">
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_orderNo" name="search_orderNo" class="propertyValue1"  placeholder = "订单号关键字" maxlength="30"/>
                            <input type="hidden" value="search_orderNo" class="propertyName"/>
                        </div>
                    </td>
                	<td style="display:none">
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_createPersonName" name="search_createPersonName" class="propertyValue1"  placeholder = "购买者关键字" maxlength="30"/>
                            <input type="hidden" value="search_createPersonName" class="propertyName"/>
                        </div>
                    </td>
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
			<table id="orderTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 查看form -->
<div id="showOrderDiv"></div>
<div id="showOrderDialog" class="hide">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#orderInfo" data-toggle="tab">订单信息</a></li>
		<li><a href="#shareOrderInfo" data-toggle="tab">佣金信息</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="orderInfo">
			<table class="table table-striped table-condensed" id="showOrderTable">
			   <tbody>
			     <tr>
			       <td style="width: 30%;">订单号</td>
			       <td id="show_orderNo"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">订单状态</td>
			       <td id="show_orderStatus"></td>
			     </tr>
			    <!--  <tr>
			       <td style="width: 30%;">课程名称</td>
			       <td id="show_courseName"></td>
			     </tr> -->
			     <tr>
			       <td style="width: 30%;">购买者</td>
			       <td id="show_createPersonName"></td>
			     </tr>
			   <!--  <tr>
			       <td style="width: 30%;">订单价格</td>
			       <td id="show_totalPrice"></td>
			     </tr>
			      <tr>
			       <td style="width: 30%;">优惠方式</td>
			       <td id="show_preferentyWay"></td>
			     </tr> 
			     <tr>
			       <td style="width: 30%;">优惠金额</td>
			       <td id="preferenty_money"></td>
			     </tr> 
			     <tr>
			       <td style="width: 30%;">实际支付金额</td>
			       <td id="show_actualPay"></td>
			     </tr>
			     <tr>-->
			       <td style="width: 30%;">支付方式</td>
			       <td id="show_payType"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">支付来源账号</td>
			       <td id="show_payAccount"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">创建时间</td>
			       <td id="show_createTime"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">付款时间</td>
			       <td id="show_payTime"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">特殊操作</td>
			       <td>--</td>
			     </tr>
			   </tbody>
			 </table>
			 
			 <table class="table table-bordered" border="0" id="showCoursePreferenty">
			 
			 </table>
		</div>
		<div class="tab-pane fade" id="shareOrderInfo">
			<table class="table table-striped table-condensed" id="showOrderTable">
				   <tbody>
				     <tr>
				       <td style="width: 30%;">佣金总计</td>
				       <td id="show_totalMoney">0&nbsp;元</td>
				     </tr>
				     <tr>
				       <td style="width: 30%;">佣金归属人</td>
				       <td id="show_firstUser">无</td>
				     </tr>
				   </tbody>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="${base}/js/order/recharge.js?v=1.7"></script>
