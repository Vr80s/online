<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

<link href="/css/medical/doctorInfo.css" type="text/css" />

<script type="text/javascript">

    try {
        var scripts = [ null, null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        });
    } catch (e) {
    }
    var weburl = '${weburl}';
    $(function () {
        $('#vtab li').click(function () {
            tops = $(this).offset().top - $('#vtab').offset().top + $('#vtab').scrollTop() - $('#vtab').height() / 4;
            $('#vtab').animate({
                scrollTop: tops
            }, 'slow');
        });
    });
</script>

<div class="page-header">
	当前位置：医师医馆<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 医馆详情 </span>
</div>

<div class="page-main">

	<div class="container">
		<div class="hospital-name container-left my-setWhite">医馆名称：</div>
		<div class="hospital-name-text my-setWhite-test">${hospital.name}</div>
	</div>

	<div class="container">
		<div class="hospital-tel container-left my-setWhite">联系电话：</div>
		<div class="hospital-tel-text my-setWhite-test">${hospital.tel}</div>
	</div>

	<div class="container">
		<div class="hospital-lal container-left my-setWhite">经纬度：</div>
		<div class="hospital-lal-text my-setWhite-test">${hospital.lal}</div>
	</div>

	<div class="container">
		<div class="hospital-postCode container-left my-setWhite">邮编：</div>
		<div class="hospital-postCode-text my-setWhite-test">${hospital.postCode}</div>
	</div>

	<div class="container">
		<div class="hospital-email container-left my-setWhite">email：</div>
		<div class="hospital-email-text my-setWhite-test">${hospital.email}</div>
	</div>

	<div class="container">
		<div class="hospital-province container-left my-setWhite">所在省：</div>
		<div class="hospital-province-text my-setWhite-test">${hospital.province}</div>
	</div>

	<div class="container">
		<div class="hospital-city container-left my-setWhite">所在市：</div>
		<div class="hospital-city-text my-setWhite-test">${hospital.city}</div>
	</div>

	<div class="container">
		<div class="hospital-detailedAddress container-left my-setWhite">详细地址：</div>
		<div class="hospital-detailedAddress-text my-setWhite-test">${hospital.detailedAddress}</div>
	</div>

	<div class="container">
		<div class="hospital-score container-left my-setWhite">评分：</div>
		<div class="hospital-score-text my-setWhite-test">${hospital.score}</div>
	</div>

	<div class="container">
		<div class="hospital-description container-left my-setWhite">简介：</div>
		<div class="hospital-description-text my-setWhite-test">${hospital.description}</div>
	</div>
</div>



<div class="container">
    <div style="font-size: 18px; width: 120px;display:flex;flex-direction: row;justify-content: flex-end;color: #B22222;">医馆展示图：</div>
</div>

<div class="tab-content" style="padding-left: 0;">
	<div role="tabpanel" class="tab-pane active" id="profile">
		<div id="divKcxq">
			<!-- 增加form -->
			<div id="detailDiv">
				<form class="form-horizontal" id="courseDetailForm" method="post" action="">
					<input type="hidden" name="medicalHospitalId" id="courseId" value="${param.courseId}">
					<input type="hidden" name="weburl" id="weburl" value="${weburl}">
					<input type="hidden" name="page" id="page" value="${param.page}">
					<input type="hidden" name="sourceId" id="sourceId" value="${param.sourceId}">
					<div class="form-group" style="margin-top:18px;">
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file" class="uploadImg"/>
							</div>
							<input name="picture1" id="edit_smallImgPath0" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
						<!-- 设置多个展示图暂时关闭 -->
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file1" class="uploadImg"/>
							</div>
							<input name="picture2" id="edit_smallImgPath1" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file2" class="uploadImg"/>
							</div>
							<input name="picture3" id="edit_smallImgPath2" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
					</div>

					<div class="form-group" style="margin-top:18px;">
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file3" class="uploadImg"/>
							</div>
							<input name="picture4" id="edit_smallImgPath3" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file4" class="uploadImg"/>
							</div>
							<input name="picture5" id="edit_smallImgPath4" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file5" class="uploadImg"/>
							</div>
							<input name="picture6" id="edit_smallImgPath5" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
					</div>

					<div class="form-group" style="margin-top:18px;">
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file6" class="uploadImg"/>
							</div>
							<input name="picture7" id="edit_smallImgPath6" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file7" class="uploadImg"/>
							</div>
							<input name="picture8" id="edit_smallImgPath7" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
						<div class="col-sm-4" style="width: 285px; height: 140px;">
							<div class="clearfix">
								<input type="file" name="smallImgPath_file" id="smallImgPath_file8" class="uploadImg"/>
							</div>
							<input name="picture9" id="edit_smallImgPath8" value="" type="text"  style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
						</div>
					</div>

				</form>
			</div>
			<div style="width:100%; text-align: center;">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success add_bx" id="okbt">
					<i class="glyphicon glyphicon-ok"></i> 确定
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success qx_bx" id="cancelbt">
					<i class="glyphicon glyphicon-remove"></i> 返回
				</button>
			</div>
		</div>
	</div>

</div>
<!-- 预览 -->
<div id="dialogShowCourseDetailDiv"></div>
<div id="showShowCourseDialog" class="hide">
	<div id="ylContent"></div>
</div>



<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" src="/js/medical/hospitalDetail.js?v=ipandatcm_1.3"></script>
