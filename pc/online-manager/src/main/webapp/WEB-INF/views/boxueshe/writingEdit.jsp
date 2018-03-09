<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	
<script type="text/javascript" src="js/boxueshe/writingEdit.js"></script>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
		
	}
	var weburl = '${weburl}';
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：头条管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
			</small> 
			著作管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
		</small>
  <span>编辑著作 </span>
</div>
<!-- 修改 form -->
<div id="dialogArticleDiv"></div>
<div id="addArticleDialog" >
	<form id="addArticle-form" class="form-horizontal"  method="post" action="" >
	 <input type="hidden" name="id" value="${writing.id}" id="id" >
	 <input type="hidden" name="articleId" value="${writing.articleId}" id="id" >
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>著作图片:</label>
			<div class="col-sm-3" >
				<div class="clearfix" id="imgAdd" style="width: 260px;">
					<!-- <input type="file" name="imgPath_file" id="imgPath_file" class="uploadImg"/> -->
				</div>
				<input name="imgPath" id="add_imgPath" value="${writing.imgPath}" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>
		
	<%-- 	<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>所属分类:</label>
			<div class="col-sm-3" >
				<div class="clearfix" style="width: 240px;">
					<select   name="typeId"   id="typeId"   class="propertyValue1 col-xs-12 col-sm-12  {required:true}" >
                        <option  value="" >请选择</option>
						<c:forEach var="m" items="${articleTypes}">
							<c:choose>
								<c:when test="${m.id eq article.typeId}">
									<option value="${m.id}" selected="selected">${m.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${m.id}">${m.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
		</div> --%>
		
		<%-- <div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>所属标签:</label>
			<div class="col-sm-3" >
				<div class="clearfix" style="width: 240px;">
					 <input type="text" id="tagName" readonly="readonly" value="${article.tagName}"  onclick="openTagDiv()" class="col-xs-12 col-sm-12 {required:true}" style="cursor: pointer;">
					 <input type="hidden" name="tagId" value="${article.tagId}" id="tagId" >
				</div>
			</div>
		</div> --%>
		
		
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>作者:</label>
			<div class="col-sm-3" >
				<div class="clearfix" style="width: 240px;">
					 <input type="text" name="author" id="author" value="${writing.author}"  maxlength="30" class="col-xs-12 col-sm-12 {required:true}" >
				</div>
			</div>
		</div>
		
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>书名:</label>
			<div class="col-sm-3" >
				<div class="clearfix" style="width: 240px;">
					 <input type="text" name="title" id="title" value="${writing.title}" maxlength="30" class="col-xs-12 col-sm-12 {required:true}" >
				</div>
			</div>
		</div>
		
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>购买连接:</label>
			<div class="col-sm-3" >
				<div class="clearfix" style="width: 240px;">
					 <input type="text" name="buyLink" id="buyLink" value="${writing.buyLink}"  maxlength="100" class="col-xs-12 col-sm-12 {required:true}" >
				</div>
			</div>
		</div>
		
		<div class="form-group " style="margin-top:18px;margin-bottom:60px">
			<label class="col-sm-1 control-label no-padding-right" for="courseDetail_content"><font color="red">*</font>内容:</label>
			<div class="col-lg-10 " style="height:250px">
				<div>
					<script id="editor" type="text/plain" style="width:1024px;height:300px;"></script></div>
				<input type="hidden" name="content"  id="content" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
				
			</div>
		</div>
	
	</form>
	<div class="col-xs-7" style="text-align: right;margin-top:50px;">
        <!--   <button class="btn btn-sm btn-success" id="previewSaveBtn">
	                       预览
          </button> -->
          <button class="btn btn-sm btn-success" id="saveBtn">
	                       保存
          </button>
          <button class="btn btn-sm btn-success" id="returnbutton">
	                      返回
          </button>
  </div>
</div>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor',{
        toolbars:[['source', //源代码
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'forecolor', //字体颜色
            'backcolor', //背景色
            'indent', //首行缩进
            'removeformat',//清除格式
            'formatmatch', //格式刷
            'blockquote', //引用
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'superscript', //上标
            'subscript', //下标
            'touppercase', //字母大写
            'tolowercase', //字母小写
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'justifyjustify',//两端对齐
            'link', //超链接
            'unlink', //取消链接
            'simpleupload', //单图上传
            // 'insertimage', //多图上传
            'emotion', //表情
            'fullscreen'
        ] ],
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave:false,
        imagePopup:false
    });
    ue.ready(function() {
        UE.getEditor('editor').setContent('${writing.content}');
    })

    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }

    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        var url = '/ueditor/upload'
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return url;
        } else if (action == 'uploadvideo') {//视频上传：
            return url;
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
</script>
