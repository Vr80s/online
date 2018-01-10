<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/jstl_taglib.jsp"%>
<style>
	#J_load_addl li{margin-bottom:5px!important;diplay:block;}
	
	#J_load_add li .btnupdataBox{margin-top:5px;}
	#J_load_add li a{font-size:20px;}
	.zhi{margin-top:5px}
	.box-buttom{
	margin-top:30px;}
	
	.fla_btn {
		position: relative;
	}
	.fla_btn embed {
		position: absolute;
		z-index: 1;
	}
	#swfDiv{*position:absolute; z-index:2;}
	
	
    #quesTable td {height: 35px!important; text-overflow: ellipsis;overflow:hidden;white-space: nowrap!important;}
</style>
<script type="text/javascript" src="${base}/js/cloudClass/map.js"></script>
<script type="text/javascript" src="${base}/js/cloudClass/map.min.js"></script>
<script src="${base}/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${base}/js/cloudClass/question_type.min.js"></script>
<script type="text/javascript">
		try {
			var sessionId = "${pageContext.session.id}";
//			var uc_t = getCookie('_uc_t_');
			console.log(sessionId);
			var scripts = [ null, null ];
			$('.page-content-area').ace_ajax('loadScripts', scripts,
					function() {
		
					});
		} catch (e) {
			
			alertInfo(e.message);
		}
		var BASE = "${base}";
		 $(function(){
		        $(".divques").on("mouseover","img",function(){
		            var csrc = $(this).attr("src");
		            var y =  $(this).offset().top-80;
		            var x =  $(this).offset().left+120;

		            var imgshow = $("<img class='imgshow' src='"+csrc+"' style='z-index: 9999; position: absolute; top:"+y+"px; left:"+x+"px;height:250px; display: none;' />");
		            $("body").append(imgshow);
		            $(imgshow).fadeIn();
		        });

		        $(".divques").on("mouseleave","img",function(){
		            $(".imgshow").fadeOut().remove();
		        });
		    });
</script>
<%@ include file="../common/excel.jsp"%>
<link rel="stylesheet" href="${base}/js/chosen/chosen.css" />
<script type="text/javascript" src="${base}/js/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="${base}/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/js/cloudClass/videoRes.js?v=6757846"></script>
<script type="text/javascript" src="${base}/js/swfobject.js"></script>
<link href="${base}/js/layer/skin/layer.css" type="text/css" />
<script type="text/javascript">
	//视频上传
	/* swfobj=new SWFObject('http://union.bokecc.com/flash/api/uploader.swf', 'uploadswf', '80', '25', '8');
	swfobj.addVariable("progress_interval" , 1);	//	上传进度通知间隔时长（单位：s）
	var ccCallbackUrl = '${ccCallbackUrl}';
	if(ccCallbackUrl && ccCallbackUrl != 'xxx'){
		swfobj.addVariable("notify_url" , ccCallbackUrl);
	}
	swfobj.addParam('allowFullscreen','true');
	swfobj.addParam('allowScriptAccess','always');
	swfobj.addParam('wmode','transparent');
	swfobj.write('swfDiv'); */
</script>
<div class="page-header">
		<div class="row">
		
		<div style="margin-left:10px">
			当前位置：云课堂管理 
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span>课程管理 </span> 
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small> 
			<span> 资源管理 </span>
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> ${courseName}</span>
			<%-- <span style="float:right;margin-right:10px">  <button type="button" class="btn btn-sm" onclick="goback(${page})"><i class="fa fa-arrow-left">返回</i></button></span> --%>
		</div>
  </div>
</div>

<div>
	<ul class="nav nav-pills tab-pills">
	    <li class="active videoli"><a href="#"  id="video" onclick="showVideo()" data-toggle="tab">视频</a></li>
	    <li class=" pptli"><a href="#" id="ppt" onclick="showPPT()" data-toggle="tab">PPT</a></li>
		<li class=" caseli"><a href="#" onclick="showCase()" data-toggle="tab">教案</a></li>
	    <li class=" questionli"><a href="#" onclick="showQuestion()" data-toggle="tab">题库管理</a></li>
	</ul>
	<div class="lines"></div>
</div>
<div class="contrightbox"
	style="overflow-y: hidden; overflow-x: hidden;height: 800px;width:18%;">
	<div class="zTreeDemoBackground"
		style="overflow-y: auto; overflow-x: auto;height: 800px;">
		<ul id="ztree" class="ztree"
			style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
	</div>
</div>
<div id="knowledgeList">
	<div>
		<div style="width: 81%; float: right">

			<p  class="col-xs-7" style="padding: 0;">
				<button class="btn btn-sm btn-success async_video_button" title="同步CC视频">
					<i class="glyphicon glyphicon-plus"></i> 同步CC视频
				</button>
				<button class="btn btn-sm btn-success async_category_button" title="同步CC分类">
					<i class="glyphicon glyphicon-plus"></i> 同步CC分类
				</button>
				<button style="display: none;" class="btn btn-sm btn-success add_bx add_video_button" title="新增视频">
						<i class="glyphicon glyphicon-plus"></i> 新增视频
				</button>
				<button  class="btn btn-sm btn-danger dele_bx" title="批量删除">
					<i class="glyphicon glyphicon-trash"></i> 批量删除
				</button>
				<button  class="btn btn-sm btn-success enable_bx" id="video_enable" title="批量启用">
					<i class="glyphicon "></i> 批量启用
				</button>
				<button  class="btn btn-sm btn-danger disable_bx" id="video_disable" title="批量禁用">
					<i class="glyphicon "></i> 批量禁用
				</button>
				<button style="display: none;" class="btn btn-sm btn-success add_ppt_button" title="新增PPT">
						<i class="glyphicon glyphicon-plus"></i> 新增PPT
				</button>
				<button style="display: none;" class="btn btn-sm btn-danger dele_bx_ppt" title="批量删除">
					<i class="glyphicon glyphicon-trash"></i> 批量删除
				</button>
				<button style="display: none;" class="btn btn-sm btn-success add_case_button" title="新增教案">
						<i class="glyphicon glyphicon-plus"></i> 新增教案
				</button>
				<button style="display: none;" class="btn btn-sm btn-danger dele_bx_case" title="批量删除">
					<i class="glyphicon glyphicon-trash"></i> 批量删除
				</button>
				<button style="display: none;" class="btn btn-sm btn-success add_ques_button" title="新增教案">
						<i class="glyphicon glyphicon-plus"></i> 新增题目
				</button>
				<button style="display: none;" class="btn btn-sm btn-danger dele_bx_ques" title="批量删除">
					<i class="glyphicon glyphicon-trash"></i> 批量删除
				</button>
				<button style="display: none;" class="btn btn-sm btn-primary daoru_bx" onclick="impStu();" title="批量导入">
					<i class="glyphicon glyphicon-plus"></i> 批量导入
				</button>
				<button style="display: none;" class="btn btn-sm btn-success enable_bx" id="ques_enable" title="批量启用">
					<i class="glyphicon "></i> 批量启用
				</button>
				<button style="display: none;" class="btn btn-sm btn-danger disable_bx" id="ques_disable" title="批量禁用">
					<i class="glyphicon "></i> 批量禁用
				</button>
			</p>

			<input type="hidden" value="${param.activeQues}" id="active" />
			<input type="hidden"  id="tId" />
			<input type="hidden" id="currentNodeId" />
			<input type="hidden" id="currentNodeName" />
			<div class="col-sm-5 padding0px" id="searchDiv">
				<div class="fright">
					<div class="profile-info-row">
					   <table style="display: none;" id="searchTable" frame=void >
					   		<tr>
			                    <td>
			                    	<div class="profile-info-value searchTr">
										<select class="form-control propertyValue1" id="search_questionType" >
						                    <option value="">全部</option>
						                    <option value="0">单选</option>
						                    <option value="1">多选</option>
						                    <option value="2">判断</option>
						                    <option value="3">填空</option>
						                    <option value="4">简答</option>
						                    <option value="5">代码</option>
						                    <option value="6">实操</option> 
										</select>
										<input type="hidden" value="search_questionType" class="propertyName"/>
									</div>
			                    </td>
			                    <td>
			                   		<div class="profile-info-value searchTr">
				                    	<input type="text" placeholder = "请输入题干" class="propertyValue1" id="search_questionHeadText" maxlength="20" style="width:200px">
			                            <input type="hidden" value="search_questionHeadText" class="propertyName"/>
		                            </div>
			                    </td>	
			                    <td>
			                    	<button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
			                                onclick="searchques();">
			                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
			                        </button>
			                    </td>
			                 </tr>
					   </table>
						<input type="hidden" value="${courseId}" name="courseId" id="courseId" />
						<input type="hidden" value="${courseName}" name="courseName" id="courseName" />
						<input type="hidden"  id="search_chapterIds" />
					</div>
				</div>
			</div>

			<table id="videoTable"
				class="table table-striped table-bordered table-hover">
			</table>
			
			<table id="pptTable"
				class="table table-striped table-bordered table-hover">
				<colgroup>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='20%'></col>
				    <col width='20%'></col>
				</colgroup>
			</table>
			
			<table id="caseTable"
				class="table table-striped table-bordered table-hover">
				<colgroup>
					<col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='20%'></col>
				</colgroup>
			</table>
			
			<table id="quesTable"
				class="table table-striped table-bordered table-hover">
				<colgroup>
					<col width='5%'></col>
				    <col width='5%'></col>
				    <col width='10%'></col>
				    <col width='30%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				    <col width='10%'></col>
				</colgroup>
			</table>
			
			
		</div>
	</div>
	<input type="hidden" id="currentNodeLevel" />
	<div id="dialogAddVideoDiv"></div>
	<div id="addVideoDialog" class="hide">
		<form class="form-horizontal" id="addVideo-form"  method="post" action="">
			<input type="hidden" value="${courseId}" name="courseId" id="addcourseName" />
			<input type="hidden"  name="chapterId" id="addchapterId" />
			<!-- <input type="hidden" name="videoId" id="addvideoId"> -->
			<!-- 视频更换为微吼，注释掉cc视频相关  20170720---yuruixin-->
			<!-- <div class="form-group fla_btn">
				<label class="col-sm-3 control-label no-padding-right"  for="addVideoSize">视频文件:</label>
				<div class="col-sm-6">
					<table>
						<tr>
							<td><input id="videofile" readonly="readonly" class="col-xs-10 col-sm-12"/></td>
							<td>
								<div id="swfDiv"></div>
								<button style="display: none;" class="btn btn-sm btn-success add_bx" title="新增视频">
									<i class="glyphicon glyphicon-search">选择</i>
								</button>
							</td>
						</tr>
					</table>
				</div>
			</div> -->
			
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="addname"><font color="red">*</font>视频名称: </label>
				<div class="col-sm-6">
					<input type="text" name="name"  id="addname" class="col-xs-10 col-sm-12 {required:true,minlength:0,maxlength:100}">
				</div>
			</div>
			
			<!-- 
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="addVideoTime">时长: </label>
				<div class="col-sm-6">
					<input type="text" placeholder="输入格式00：00：00" name="videoTime" id="addVideoTime" class="col-xs-10 col-sm-12 {maxlength:20}">
				</div>
			</div>
			 -->
			 <div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="editvideoId"><font color="red">*</font>视频ID: </label>
				<div class="col-sm-6">
					<input type="text" name="videoId"  class="col-xs-10 col-sm-12">
				</div>
			</div>
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="addvideoVersion"><font color="red">*</font>版本: </label>
				<div class="col-sm-6">
					<input type="text" placeholder="输入格式：V1.0" value="V1.0" name="videoVersion" id="addvideoVersion" class="col-xs-10 col-sm-12 {required:true,minlength:1,maxlength:20}">
				</div>
			</div>
			
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="addVideoSize">视频大小: </label>
				<div class="col-sm-6">
					<input type="text" placeholder="" name="videoSize" id="addVideoSize" class="col-xs-10 col-sm-12 {maxlength:20}">
				</div>
			</div>
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="addVideoSize">是否设为试看: </label>
				<div class="col-sm-6">
					<input type="checkbox" style="margin-top: 10px;cursor: pointer;"  name="isTryLearn" id="addisTryLearn" ><p style="margin-top: -15px;margin-left:18px;"><label for="addisTryLearn" style="cursor: pointer;">是</label> </p>
				</div>
			</div>
			<!-- <div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="addVideoSize">CC视频分类: </label>
				<div class="col-sm-6">
					<select name="category" id="category"></select>
					<select name="sub-category" id="sub-category"></select>
				</div>
			</div>
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="addVideoSize">上传进度: </label>
				<div class="col-sm-6">
					<input style="border: none;width:360px;" type="text" id="uploadProgress" class="col-xs-10 col-sm-12">
				</div>
			</div> -->
		</form>
	</div>
	
	<div id="dialogEditVideoDiv"></div>
	<div id="editVideoDialog" class="hide">
		<form class="form-horizontal" id="editVideo-form"  method="post" action="">
			
			<input type="hidden"  name="id" id="editId" />
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="editname"><font color="red">*</font>注意: </label>
				<div class="col-sm-6">
					<input style="border: none;color: red;" type="text" class="col-xs-10 col-sm-12" value="新视频上传30分钟后才可以修改！">
				</div>
			</div>
			
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="editname"><font color="red">*</font>视频名称: </label>
				<div class="col-sm-6">
					<input type="text" name="name"  id="editname" class="col-xs-10 col-sm-12 {required:true,minlength:0,maxlength:50}">
				</div>
			</div>
			
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="editvideoId"><font color="red">*</font>视频ID: </label>
				<div class="col-sm-6">
					<input type="text" name="videoId" id="editvideoId" class="col-xs-10 col-sm-12">
				</div>
			</div>
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="editvideoVersion"><font color="red">*</font>版本: </label>
				<div class="col-sm-6">
					<input type="text" placeholder="输入格式：V1.0" name="videoVersion" id="editvideoVersion" class="col-xs-10 col-sm-12 {required:true,minlength:1,maxlength:20}">
				</div>
			</div>
			
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" >视频大小: </label>
				<div class="col-sm-6">
					<input type="text" name="videoSize" id="editVideoSize" class="col-xs-10 col-sm-12" >
				</div>
			</div>
			
			<div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" >是否设为试看: </label>
				<div class="col-sm-6">
					<input type="checkbox" style="margin-top: 10px;cursor: pointer;"  name="isTryLearn" id="editisTryLearn" ><p style="margin-top: -15px;margin-left:18px;"><label for="editisTryLearn" style="cursor: pointer;">是</label> </p>
				</div>
				
			</div>
		</form>
	</div>

	<div id="dialogCourseSystemDiv"></div>
	<div id="knowledgeDialog" class="hide">
		<div class="row">
			<div class="col-xs-12">
				<input type="hidden" id="level" value="">
				<input type="hidden" id="treeId" value="">
				<input type="hidden" id="courseId_copy" value="${courseId}">
				<jsp:include page="../common/ztree.jsp" flush="true" />
			</div>
		</div>
	</div>
	
	
	<!-- 新增PPT  -->
	<div id="dialogUserDiv"></div>
	<div id="userDialog" class="hide">
		<!-- <div class="hr hr-12 hr-double"></div> -->
		<form class="form-horizontal" id="ppt-form" method="post" action="resource/ppt/add">
		<input type="hidden" value="${courseId}" name="courseId" id="addPPTcourseName" />
		<input type="hidden"  name="chapterId" id="addPPTchapterId" />
		<input type="hidden" id="suffix" name="suffix"/>
		<input type="hidden" autofocus="autofocus"/>
		<div class="form-group " style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>上传PPT： </label>
			<div class="col-sm-6">
				<div class="clearfix" id="fileDiv" id="pptFileDiv" >
					<input type="file" id="pptFile" name="pptFile"    class="uploadFile" onchange="fileUpload('.pptx','.ppt')" />
				</div>	
				<input type="hidden" name="fileUrl" id="path">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>名称： </label>
			<div class="col-sm-9">
				<div class="clearfix" >
					<input type="hidden"  id="fileName"/>
			
					<input type="text" id="name" name="name" class="col-sm-9 {required:true }" maxlength="30" />
				</div>	
			</div>
		</div>
		  <div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="comment"><span style="color: red">*</span>版本号：</label>
				<div class="col-sm-9">
					<div class="clearfix" >
						<input type="text" placeholder="输入格式：v1.0" name="version" id="version" class="col-sm-9 {required:false,maxlength:11}" />
					</div>
				</div>
		  </div>
		  <div class="form-group row" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" >说明：</label>
	            <div class="col-sm-6">
	            	<div class="clearfix" >
	                	<textarea name="description" id="description" placeholder="限制200字符"  maxlength="200" class=" textarealimit {maxlength:200}" cols="39" rows="5"></textarea>
	                </div>
	                
	            </div>
	        </div>
	     
		</form>
	</div>

	<!-- 修改PPT  -->
	<div id="dialogUserEditDiv"></div>
	<div id="userEditDialog" class="hide">
		<!-- <div class="hr hr-12 hr-double"></div> -->
		<form class="form-horizontal" id="pptEdit-form" method="post" action="resource/ppt/add">
		
		<input type="hidden"  name="id" id="editPPTId" />
		<input type="hidden" id="editsuffix" name="suffix"/>
		<input type="hidden" autofocus="autofocus"/>
		<div class="form-group " style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>上传PPT： </label>
			<div class="col-sm-6">
				<div class="clearfix" id="editfileDiv" id="pptFileDiv" >
					<input type="file" id="editpptFile" name="pptFile"    class="uploadFile" onchange="fileUpload_edit('.pptx','.ppt')" />
				</div>	
				<input type="hidden" name="fileUrl" id="editpath">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>名称： </label>
			<div class="col-sm-9">
				<div class="clearfix" >
					<input type="hidden"  id="editfileName"/>
			
					<input type="text" id="editPPTname" name="name" class="col-sm-9 {required:true }" maxlength="30" />
				</div>	
			</div>
		</div>
		  <div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="comment"><span style="color: red">*</span>版本号：</label>
				<div class="col-sm-9">
					<div class="clearfix" >
						<input type="text" name="version" placeholder="输入格式：v1.0" id="editversion" class="col-sm-9 {required:false,maxlength:11}" />
					</div>
				</div>
		  </div>
		  <div class="form-group row" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" >说明：</label>
	            <div class="col-sm-6">
	            	<div class="clearfix" >
	                	<textarea name="description" id="editdescription" placeholder="限制200字符" maxlength="200" class=" textarealimit {maxlength:200}" cols="39" rows="5"></textarea>
	                </div>
	                
	            </div>
	        </div>
	     
		</form>
	</div>
	
	
	<!-- 新增教案  -->
	<div id="dialogUserCaseDiv"></div>
	<div id="userCaseDialog" class="hide">
		<!-- <div class="hr hr-12 hr-double"></div> -->
		<form class="form-horizontal" id="case-form" method="post" action="resource/ppt/add">
		<input type="hidden" value="${courseId}" name="courseId" id="addCasecourseName" />
		<input type="hidden"  name="chapterId" id="addCasechapterId" />
		<input type="hidden" id="suffixCase" name="suffix"/>
		<input type="hidden" autofocus="autofocus"/>
		<div class="form-group " style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>上传教案： </label>
			<div class="col-sm-6">
				<div class="clearfix" id="fileDiv" id="caseFileDiv" >
					<input type="file" id="caseFile" name="caseFile"    class="uploadFile" onchange="fileUploadCase('.doc','.docx','.txt','.pdf')" />
				</div>	
				<input type="hidden" name="fileUrl" id="casepath">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>名称： </label>
			<div class="col-sm-9">
				<div class="clearfix" >
					<input type="hidden"  id="fileName"/>
			
					<input type="text" id="casename" name="name" class="col-sm-9 {required:true }" maxlength="30" />
				</div>	
			</div>
		</div>
		  <div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="comment"><span style="color: red">*</span>版本号：</label>
				<div class="col-sm-9">
					<div class="clearfix" >
						<input type="text" name="version" placeholder="输入格式：v1.0" id="caseversion" class="col-sm-9 {required:false,maxlength:11}" />
					</div>
				</div>
		  </div>
		  <div class="form-group row" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" >说明：</label>
	            <div class="col-sm-6">
	            	<div class="clearfix" >
	                	<textarea name="description" id="casedescription" placeholder="限制200字符" maxlength="200" class=" textarealimit {maxlength:200}" cols="39" rows="5"></textarea>
	                </div>
	                
	            </div>
	        </div>
	     
		</form>
	</div>
	
	<!-- 修改教案  -->
	<div id="dialogUserEditCaseDiv"></div>
	<div id="userCaseEditDialog" class="hide">
		<!-- <div class="hr hr-12 hr-double"></div> -->
		<form class="form-horizontal" id="caseEdit-form" method="post" action="resource/ppt/add">
		<input type="hidden"  name="id" id="editCaseId" />
		<input type="hidden" id="suffixCaseEdit" name="suffix"/>
		<input type="hidden" autofocus="autofocus"/>
		<div class="form-group " style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>上传教案： </label>
			<div class="col-sm-6">
				<div class="clearfix" id="fileDiv" id="caseFileDiv" >
					<input type="file" id="caseFileEdit" name="caseFile"    class="uploadFile" onchange="fileUploadCaseEdit('.doc','.docx','.txt','.pdf')" />
				</div>	
				<input type="hidden" name="fileUrl" id="casepathEdit">
				<!-- 是否修改了文件 -->
				<input type="hidden" name="isuploadFile" id="isuploadFile">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><span style="color: red">*</span>名称： </label>
			<div class="col-sm-9">
				<div class="clearfix" >
					<input type="hidden"  id="fileNameEdit"/>
			
					<input type="text" id="casenameEdit" name="name" class="col-sm-9 {required:true }" maxlength="30" />
				</div>	
			</div>
		</div>
		  <div class="form-group" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" for="comment"><span style="color: red">*</span>版本号：</label>
				<div class="col-sm-9">
					<div class="clearfix" >
						<input type="text" name="version" id="caseversionEdit" placeholder="输入格式：v1.0" class="col-sm-9 {required:false,maxlength:11}" />
					</div>
				</div>
		  </div>
		  <div class="form-group row" style="margin-top: 18px;">
				<label class="col-sm-3 control-label no-padding-right" >说明：</label>
	            <div class="col-sm-6">
	            	<div class="clearfix" >
	                	<textarea name="description" id="casedescriptionEdit" placeholder="限制200字符" maxlength="200" class=" textarealimit {maxlength:200}" cols="39" rows="5"></textarea>
	                </div>
	                
	            </div>
	        </div>
	     
		</form>
	</div>
	<!-- 新增题目  -->
	<div id="dialogQuestionDivAdd"></div>
    <div id="questionDialog" class="hide">
       
    </div>
    <!-- 查看题目  -->
    <div id="previewQuestionDiv"></div>
	<div id="previewDialog" class="hide">
		<div class="wn">
	        <div class="tisix">
	            <label class="col-lg-2">课程名称:</label>
	            <div class="col-lg-10 marginbottom10px">
	                <span id="ksystemName">&nbsp;</span>
	            </div>
	        </div>
	        <div class="tisix">
	            <label class="col-lg-2">知识点:</label>
	            <div class="col-lg-10 marginbottom10px">
	                <span id="knowledgeName">&nbsp;</span>
	            </div>
	        </div>
	        <!-- <div class="tisix">
	            <label class="col-lg-2">关键字:</label>
	            <div class="col-lg-10 marginbottom10px">
	                <span id="qKeyword">&nbsp;</span>
	            </div>
	        </div> -->
	        <div class="tisix">
	            <label class="col-lg-2">难度:</label>
	            <div class="col-lg-10 marginbottom10px">
	                <span id="questionDifficulty"></span>
	            </div>
	        </div>
	        <div class="tisix">
	            <label class="col-lg-2">类型:</label>
	            <div class="col-lg-10 marginbottom10px">
	                <span id="questionType"></span>
	            </div>
	        </div>
	        <div class="caption">
	            <div class="panel-body paddingright0px paddingleft0px">
	                <ul class="nav nav-pills taj" style="margin-left: 57px;">
	                    <li class=""><a href="#home-pills" data-toggle="tab">题干</a>
	                    </li>
	                    <li class=""><a href="#home-pis" data-toggle="tab" id="home-pis-head">答案</a>
	                    </li>
	                    <li class=""><a href="#home_solution" data-toggle="tab" id="home_solution_head">解析</a>
	                    </li>
	                </ul>
	
	                <div class="tab-content" style="margin-left: 57px;">
	                    <div class="tab-pane fade active in autowrap divques" id="home-pills">
	                    	
	                	</div>
	                    <div class="tab-pane fade autowrap divques" id="home-pis">
	                    	
	                    </div>
	                    <div class="tab-pane fade autowrap divques" id="home_solution">
	                     	
	                    </div>
	                 </div>
	                </div>
	            </div>
	
	            <div class="form-group">
	            <label class="col-sm-5"></label> 
	            <div>
	                <a class="btn btn-primary btn-sm" style="margin-top:130px;"  id="close"> 关闭 </a>
	            </div>
	        </div>
	    </div>
	</div>
</div>
