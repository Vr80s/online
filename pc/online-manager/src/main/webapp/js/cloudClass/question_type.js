var multipleMany = '<div class="form-group multipleOption1">\
    <label class="col-sm-2 control-label no-padding-right"><span style="color: red;">*</span>选项: </label>\
    <div class="col-sm-10" id="multiple_answer_option">\
    	<div class="answer_add">\
           <span class="tip">（以下选中的选项为正确答案）</span>\
        </div>\
        <ul class="answer_set_choose" id="multipleOption1_ul">\
            <li><input type="checkbox" class="question_options_check" value="A"\
                       name="multipleOption" checked><span>A：</span>\
                <input name="multipleOptionValue1" type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify1" id="imgFile1" class="col-xs-10 uploadImg" />\
		</div>\
	</div>\
	</div>\
			</li>\
            <li><input type="checkbox" class="question_options_check" value="Bw"\
                       name="multipleOption" checked><span>B：</span>\
                <input name="multipleOptionValue2" type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify2" id="imgFile2" class="col-xs-10 uploadImg" />\
		</div>\
	</div>\
	</div>\
			</li>\
            <li><input type="checkbox" class="question_options_check" value="C"\
                       name="multipleOption"><span>C：</span>\
                <input name="multipleOptionValue3" type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify3" id="imgFile3" class="col-xs-10 uploadImg" />\
	<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)"\
		</div></div>\
	</div>\
	</div>\
			</li>\
            <li><input type="checkbox" class="question_options_check" value="D"\
                       name="multipleOption"><span>D：</span>\
                <input name="multipleOptionValue4"  type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify4" id="imgFile4" class="col-xs-10 uploadImg" />\
	<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)"\
		</div></div>\
	</div>\
	</div>\
			</li>\
        </ul>\
        <div class="clear"></div>\
        <div>\
            <input type="button" class="answer_add_btn" onclick="addCheckbox();"\
                   value="+ 增加选项或答案">\
        </div>\
    </div>\
</div>';

var multipleOne = '<div class="form-group multipleOption">\
    <label class="col-sm-2 control-label no-padding-right"><span style="color: red;">*</span>选项: </label>\
    <div class="col-sm-10" id="choose_answer_option">\
    	<div class="answer_add">\
           <span class="tip">（以下选中的选项为正确答案）</span>\
        </div>\
        <ul class="answer_set_choose" id="chooseOption1_ul">\
            <li><input type="radio" value="A"\
                       name="radioOption" checked><span>A：</span>\
                <input name="radioOptionValue1" type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify1" id="imgFile1" class="col-xs-10 uploadImg" />\
		</div>\
	</div>\
	</div>\
			</li>\
            <li><input type="radio" value="B"\
                       name="radioOption"><span>B：</span>\
                <input name="radioOptionValue2" type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify2" id="imgFile2" class="col-xs-10 uploadImg" />\
		</div>\
	</div>\
	</div>\
			</li>\
            <li><input type="radio" value="C"\
                       name="radioOption"><span>C：</span>\
                <input name="radioOptionValue3" type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify3" id="imgFile3" class="col-xs-10 uploadImg" />\
	<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
		</div></div>\
	</div>\
	</div>\
			</li>\
            <li><input type="radio" value="D"\
                       name="radioOption"><span>D：</span>\
                <input name="radioOptionValue4" type="text" class="{required:true}" value="" maxlength="30">\
	<div class="form-group">\
	<label class="control-label col-xs-12 col-sm-1 no-padding-right"\
		for="comment">图片：</label>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix">\
			<input type="file" name="uploadify4" id="imgFile4" class="col-xs-10 uploadImg" />\
	<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
		</div></div>\
	</div>\
	</div>\
			</li>\
        </ul>\
        <div class="clear"></div>\
        <div>\
            <input type="button" class="answer_add_btn" onclick="addRadio();"\
                   value="+ 增加选项或答案">\
        </div>\
    </div>\
</div>';

var contentTypeFilling = '<div class="form-group">\
    <label class="col-sm-2 control-label no-padding-right"><span style="color: red;">*</span> 题干与答案: </label>\
	<div class="col-xs-5 col-sm-10">\
		<label class="col-sm-10 control-label no-padding-right">(请将答案处用"【】"替代，"【】"内是正确答案，并且答案中不包含"【"、"】"、"^"特殊符号。)</label>\
	</div>\
	<div class="col-xs-12 col-sm-9">\
		<div class="clearfix" id="wysiwygEditorFilling">\
			<div class="wysiwyg-editor" style="width:850px"></div>\
	    </div>\
		<input type="hidden" name="questionHead" id="questionHeadFilling" class="{required:true}" maxlength="2000"/>\
	</div>\
</div>';

var contentTypeNoFilling = '<div class="form-group">\
    <label class="col-sm-2 control-label no-padding-right" id="notFillingType"><span style="color: red;">*</span> 题干: </label>\
    <div class="col-xs-12 col-sm-9">\
		<div class="clearfix" id="wysiwygEditorNoFilling">\
			<div class="wysiwyg-editor" style="width:850px"></div>\
        </div>\
		<input type="hidden" name="questionHead" id="questionHeadNoFilling" class="{required:true}" maxlength="2000"/>\
    </div>\
</div>';

var answerJudge = '<div class="form-group">\
	<label class="col-sm-2 control-label no-padding-right"> 正确答案: </label>\
<div class="col-sm-5 paddingtop7px">\
    <p >\
        <input type="radio" name="judge" value="true" id="rigth" class="aa" checked><label for="rigth">对</label>&nbsp &nbsp\
        <input type="radio" name="judge" value="false" id="wrong"><label for="wrong">错</label>\
    </p>\
</div>\
</div>';

var answerNomal = '<div class="form-group">\
    <label class="col-sm-2 control-label no-padding-right"><span style="color: red;">*</span>正确答案: </label>\
	<div class="col-xs-12 col-sm-9">\
	<div class="clearfix" id="wysiwygEditorAnswer">\
		<div class="wysiwyg-editor" style="width:850px"></div>\
    </div>\
	<input type="hidden" name="answer" id="answer" class="{required:true}" maxlength="2000"/>\
</div>';
	

var solution = '<div class="form-group">\
    <label class="col-sm-2 control-label no-padding-right"> 答案说明: </label>\
<div class="col-xs-12 col-sm-9">\
    <div class="clearfix" id="wysiwygEditorSolution">\
		<div class="wysiwyg-editor" style="width:850px"></div>\
	</div>\
	<input type="hidden" name="solution" id="solution" maxlength="2000"/>\
</div>\
</div>';