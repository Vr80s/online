<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<style type="text/css">
    .vertical-tab {
        width: 8%;
        height: 100%;
        float: left;
        /* overflow: hidden; */
    }
 
    .vertical-tab > li {
        text-align: center;
    }
    
    .vertical-tab > li.active > a, .vertical-tab > li.active > a:focus, .vertical-tab > li.active > a:hover {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff;
        border-right: 1px solid #ffffff;
        z-index: 2;
    }
 
    .vertical-tab > li > a {
         /* border-radius: 4px 4px 4px 4px; */
         border-radius: 4px 0px 0px 4px; 
    }
 
    .vertical-tab-content {
        float: left;
        width: 92%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 0px 4px 4px 4px;
        border: solid 1px #ccc;
        color: #666;
    }
 
    .send {
        position: relative;
        width: 90%;
        background: #FFFFFF;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px; /* 圆角 */
        border: 1px solid #ccc;
    }
 
    .tag {
        width: 300px;
        min-height: 30px;
        border: 1px solid #ccc;
        position: relative;
        padding: 10px;
        background-color: #ccc;
        border-radius: 4px;
        margin-left: 30px;
        -moz-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        -webkit-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
    }
 
    .tag:before, .tag:after {
        position:absolute;
        content:"\00a0";
        width:0px;
        height:0px;
        border-width:8px 18px 8px 0;
        border-style:solid;
        border-color:transparent #CCC transparent transparent;
        top:5px;
        left:-18px;
 
    }
 
    .tag:after {
        bottom: -33px;
        border-color: #FFF transparent transparent;
    }
 
    .clearfix:after{
     content:"";
     height:0;
     visibility:hidden;
     display:block;
     clear:both;
    }
 
    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input.valid{
      width: 100%;
    }
    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input{
     width: 100%;
    }
 
</style>
<script type="text/javascript">

	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
	
	$(function () {
        $('#vtab li').click(function () {
            tops = $(this).offset().top - $('#vtab').offset().top + $('#vtab').scrollTop() - $('#vtab').height() / 4;
            console.log(tops);
            $('#vtab').animate({
                scrollTop: tops
            }, 'slow');
        });
    });
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：销售管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 提现管理 </span>
</div>

<div style="height: 100%;" class="clearfix">
    <!-- Tab panes -->
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
        	<div class="mainrighttab tabresourse bordernone">
	<!-- <p class="col-xs-2" style="padding: 0;">
		<button class="btn btn-sm btn-success dele_bx" title="批量删除">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
	</p> -->
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
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
                            <select id="search_orderStatus" name="search_orderStatus" class="propertyValue1">
                              <option  value="" >提现状态</option>
                                <option  value="0" >未处理</option>
                                <option  value="1" >已打款</option>
                                <option  value="2" >已驳回</option>
                            </select>
                             <input type="hidden" value="search_orderStatus" class="propertyName" />
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="search_payType" name="search_payType" class="propertyValue1">
                                <option  value="" >提现方式</option>
                                <option  value="0" >支付宝</option>
                                <option  value="1" >微信支付</option>
                                <option  value="2" >网银支付</option>
                            </select>
                            <input type="hidden" value="search_payType" class="propertyName" />
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="search_order_from" name="order_from" class="propertyValue1">
                                <option  value="" >提现来源</option>
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
                                <option  value="1" >提现申请单号</option>
                                <option  value="2" >提现者</option>
                            </select>
                        </div>
                    </td>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_orderNo" name="search_orderNo"  class="propertyValue1"  placeholder = "提现申请单号" maxlength="30"/>
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
        </div>
    </div>
	</div>
	
	<!-- 打款form -->
	<div id="dialogEditCourseDiv"></div>
	<div id="EditCourseDialog" class="hide">
		<form class="form-horizontal" id="dakuan-form" method="post" action="" style="margin-top: 15px;">
			<input type="hidden" id="dakuan_id" name="id" class="col-xs-10 col-sm-8 {required:true}">
			<input type="hidden"  name="enchashmentStatus" value="1" class="col-xs-10 col-sm-8 {required:true}">
				<div class="space-4"></div>
		        <div class="form-group" style="margin-top: 18px;">
		            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 打款时间: </label>
		            <div class="col-sm-9">
		                <input type="text" name="ticklingTime"  id="startTime_edit" maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
		            </div>
		        </div>
		       <div class="form-group" style="margin-top: 18px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseDescribe"><font color="red">*</font>备注: </label>
				 <div class="col-sm-6">
				 	<textarea class="form-control {required:true}" name="operateRemark"  rows="3"></textarea>
	             </div>
			</div>
		</form>
	</div>
	
	
	<!-- 驳回form -->
	<div id="dialogbohuiDiv"></div>
	<div id="EditbohuiDialog" class="hide">
		<form class="form-horizontal" id="bohui-form" method="post" action="" style="margin-top: 15px;">
			<input type="hidden" id="bohui_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">
			<input type="hidden"  name="enchashmentStatus" value="2" class="col-xs-10 col-sm-8 {required:true}">
		       <div class="space-4"></div>
				<div class="form-group"  style="margin-top: 18px;" >
					<label class="col-sm-3 control-label no-padding-right" for="menuNameSecond"><font color="red">*</font>驳回理由: </label>
		            <div class="col-sm-6">
		               <select name="causeType" class="clearfix col-xs-10 col-sm-12 {required:true}" >
		               		<option value="0">未能与提现人取得联系</option>
		               		<option value="1">支付宝账号有误</option>
		               		<option value="2">微信账号有误</option>
		               		<option value="3">手机号有误</option>
		               		<option value="4">姓名有误</option>
		               		<option value="5">其他</option>
		               </select>
		            </div>
				</div>
		     <div class="form-group" style="margin-top: 18px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseDescribe"><font color="red">*</font>补充: </label>
				 <div class="col-sm-6">
				 	<textarea class="form-control {required:true}" name="operateRemark"  rows="3"></textarea>
	             </div>
			</div>
		</form>
	</div>
	
</div>

<!-- 查看form -->
<div id="showOrderDiv"></div>
<div id="showOrderDialog" class="hide">
	<div class="tab-content">
		<div class="tab-pane fade in active" id="orderInfo">
			<table class="table table-striped table-condensed" id="showOrderTable">
			   <tbody>
			     <tr>
			       <td style="width: 30%;">姓名</td>
			       <td id="s_name"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">手机号</td>
			       <td id="s_phone"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">支付宝账号</td>
			       <td id="s_zfb"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">提现金额</td>
			       <td id="s_price"></td>
			     </tr>
			   </tbody>
			 </table>
			 
			 <table class="table table-bordered" border="0" id="showCoursePreferenty">
			 
			 </table>
		</div>
		
	</div>
</div>

<!-- 已打款form -->
<div id="showyfkDiv"></div>
<div id="showyfkDialog" class="hide">
	<div class="tab-content">
		<div class="tab-pane fade in active" id="orderInfo">
			<table class="table table-striped table-condensed" id="showOrderTable">
			   <tbody>
			     <tr>
			       <td style="width: 30%;">支付方式</td>
			       <td id="s_payType"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">支付账号</td>
			       <td id="s_payAccount"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">支付金额</td>
			       <td id="s_payPrice"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">支付时间</td>
			       <td id="s_payTime"></td>
			     </tr>
			   </tbody>
			 </table>
			 
			 <table class="table table-bordered" border="0" id="showCoursePreferenty">
			 
			 </table>
		</div>
		
	</div>
</div>

<!-- 已驳回form -->
<div id="showybhDiv"></div>
<div id="showybhDialog" class="hide">
	<div class="tab-content">
		<div class="tab-pane fade in active" id="orderInfo">
			<table class="table table-striped table-condensed" id="showOrderTable">
			   <tbody>
			     <tr>
			       <td style="width: 30%;">驳回理由</td>
			       <td id="s_bhcause"></td>
			     </tr>
			     <tr>
			       <td style="width: 30%;">补充</td>
			       <td id=""><textarea class="form-control" name="description" id="s_operateRemark" readonly="readonly" rows="5" class="col-xs-10 col-sm-12 {required:true,rangelength:[1,170]}"></textarea></td>
			     </tr>
			 </table>
			 
			 <table class="table table-bordered" border="0" id="showCoursePreferenty">
			 
			 </table>
		</div>
		
	</div>
</div>
<script type="text/javascript">
			var availableTags = [
			                 	"ActionScript",
			                 	"AppleScript",
			                 	"Asp",
			                 	"BASIC",
			                 	"C",
			                 	"C++"
			                 ];
            $( "#autocomplete" ).autocomplete({
            	source: availableTags
            });			
			
</script>
<script type="text/javascript" src="${base}/js/order/enchashmentManager.js?v=1.7"></script>