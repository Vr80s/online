<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />
<style>
    .tiContent p{
        display: inline-block;
    }
	html,body,ul,li,div,span,ol,p{
		margin:0;
		padding:0;
	}
	ul,ol,li{
		list-style:none;
	}
	.clearfix:after{
		content:'';
		height:0;
		display:block;
		visibility:hidden;
		clear:both;
	}
	#shiJuanBox{
		width:100%;
	}
	#shiJuanBox li .tiType{
		line-height:45px;
		width:100%;
		border:1px solid #eee;
		background-color:#f2f2f2;
		cursor:pointer;
	}
	#shiJuanBox li .tiType span{
		display:inline-block;
		margin-right:15px;
		vertical-align: middle;
	}
	#shiJuanBox li .tiType .tiType1{
		float:left;
	}
    #shiJuanBox li .tiType .tiType1 span{
        font-size: small;
    }
	#shiJuanBox li .tiType .tiType1 .bottom{
		width:18px;
		height:10px;
		display:inline-block;
		background:url("${base}/images/bottom.png");
		margin:0 10px 0 5px;
		vertical-align: middle;
	}
	#shiJuanBox li .tiType .tiType1 .top{
		width:18px;
		height:10px;
		display:inline-block;
		background:url("${base}/images/top.png");
		margin:0 10px 0 5px;
		vertical-align: middle;
	}
	#shiJuanBox li .tiType .tiType2{
		float:right;
        font-weight: bold;
	}
	#shiJuanBox .tiContent{
		padding:20px 10px;
		border-bottom:1px solid #eee;
		display:block;
	}
	.xuanxiang li{
		padding: 20px 30px;
	}
	.xuanxiang li input{
		margin-bottom: 5px;
	}
	.xuanxiang li span{
		display:inline-block;
		margin-right:15px;
		vertical-align: middle;
		margin-top:-9px;
	}
    .xuanxiang li img{
        margin-left:36px;
        display:inline-block;
        max-width: 500px;
    }
	.tiInfo{
		padding: 20px 50px;
	}
	.tiInfo img{
		display:inline-block;
		vertical-align: middle;
		margin-top:-4px;
	}
	.xuanxiang li div,.tiInfo div{
		margin-bottom:10px;
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
	<div class="row">
		<div style="margin-left:10px">
			当前位置：学习闯关管理 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
			批阅试卷 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
			${grade_name} <small><i class="ace-icon fa fa-angle-double-right"></i></small>
			${barrier_name} <small><i class="ace-icon fa fa-angle-double-right"></i></small>
			${user_name} <small><i class="ace-icon fa fa-angle-double-right"></i></small>
			闯关试卷
			<span style="float:right;margin-right:10px">
                <button type="button" class="btn btn-sm" onclick="goBack()">
                    <i class="fa fa-arrow-left">返回</i>
                </button>
            </span>
		</div>
	</div>
</div>


<div class="mainrighttab tabresourse bordernone">
	<input type="hidden" value="${grade_id}" id="grade_id">
    <input type="hidden" value="${barrier_id}" id="barrier_id">
	<input type="hidden" value="${barrier_name}" id="barrier_name">
	<input type="hidden" value="${grade_name}" id="grade_name">

	<div class="searchDivClass" id="searchDiv" style="float: left">
		<div class="profile-info-row" >
			<table style="width: 100%">
				<tr>
					<td>
						<div class="profile-info-value searchTr">
							<label for="examNum" style="font-size: medium;">闯关次数查询：</label>
							<select id="examNum" name="examNum" style="width:100px;font-size: small;" onchange="change(this);">
								<c:forEach var="c" items="${examNum}">
									<option value="${c.id}">${c.index}</option>
								</c:forEach>
							</select>
						</div>
						<span id="examScore" style="font-weight: bold;font-size: medium;"></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<div><span style="font-size: large;border-bottom:2px solid blue;padding: 0 20px;">客观题</span></div>
			<ul id="shiJuanBox">

			</ul>
		</div>
	</div>
</div>

<script type="text/javascript" src="${base}/js/markexampapers/exampaper.js?v=ipandatcm_1.3"></script>