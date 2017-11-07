var multipleMany = '<div class="form-group multipleOption1">\
    <label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span>选项: </label>\
    <div class="col-sm-10" id="multiple_answer_option">\
    	<div class="answer_add paddingtop7px">\
           <p class="tip">（以下选中的选项为正确答案）</p>\
        </div>\
        <ul class="answer_set_choose" id="multipleOption1_ul">\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
               <input type="checkbox" class="question_options_check" value="A"\
                       name="multipleOption"><span>A：</span>\
                <input name="multipleOptionValue1" type="text" style="width:81%" value="" >\
				</div><div class="form-group">\
				<div class="col-sm-2">\
					<input type="hidden" name="uploadify1" id="uploadify1"  >\
					<input type="file"   name="attachment" id="imgFile1" class="uploadImg" value="" />\
				</div>\
				</div>\
			</li>\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
            	<input type="checkbox" class="question_options_check" value="B"\
                       name="multipleOption"><span>B：</span>\
                <input name="multipleOptionValue2" type="text" style="width:81%"  value="" >\
	           </div><div class="form-group">\
				<div class="col-sm-2">\
				<input type="hidden" name="uploadify2" id="uploadify2"  >\
				<input type="file"   name="attachment" id="imgFile2" class="uploadImg" value="" />\
				</div>\
				</div>\
			</li>\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
            	<input type="checkbox" class="question_options_check" value="C"\
                       name="multipleOption"><span>C：</span>\
                <input name="multipleOptionValue3" type="text" style="width:81%" value="" >\
				</div><div class="form-group">\
				<div class="col-sm-1">\
				<input type="hidden" name="uploadify3" id="uploadify3"  >\
				<input type="file"   name="attachment" id="imgFile3" class="uploadImg" value="" />\
				</div>\
				 <div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">     <input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)"\
				</div>\
				</div>\
			</li>\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
            	<input type="checkbox" class="question_options_check" value="D"\
                       name="multipleOption"><span>D：</span>\
                <input name="multipleOptionValue4" type="text" style="width:80%" value="" >\
				</div><div class="form-group">\
				<div class="col-sm-1">\
				<input type="hidden" name="uploadify4" id="uploadify4"  >\
				<input type="file"   name="attachment" id="imgFile4" class="uploadImg" value="" />\
				</div>\
				 <div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)"\
				</div>\
				</div>\
			</li>\
        </ul>\
        <div class="clear"></div>\
        <div>\
            <input type="button" class="answer_add_btn" onclick="addCheckbox();"\
                   value="+ 增加选项或答案">\
        </div>\
		<div id="multipleHintDiv"></div>\
    </div>\
</div>';

var multipleOne = '<div class="form-group multipleOption">\
    <label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span>选项: </label>\
    <div class="col-sm-10" id="choose_answer_option">\
    	<div class="answer_add paddingtop7px">\
           <p class="tip">（以下选中的选项为正确答案）</p>\
        </div>\
        <ul class="answer_set_choose" id="chooseOption1_ul">\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
            	<input type="radio" value="A"\
                       name="radioOption"><span>A：</span>\
                <input name="radioOptionValue1" type="text" style="width:81%;" value="" >\
               </div><div class="form-group">\
				<div class="col-sm-1 ">\
						<input type="hidden" name="uploadify1" id="uploadify1"  >\
						<input type="file"   name="attachment" id="imgFile1" class="uploadImg" value="" />\
				</div>\
				</div></div>\
			</li>\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
                    <input type="radio" value="B"\
                       name="radioOption"><span>B：</span>\
                <input name="radioOptionValue2" type="text" value="" style="width:81%;" >\
                </div><div class="form-group">\
				<div class="col-sm-1 ">\
						<input type="hidden" name="uploadify2" id="uploadify2"  >\
						<input type="file"   name="attachment" id="imgFile2" class="uploadImg" value="" />\
				</div>\
				</div></div>\
			</li>\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
                <input type="radio" value="C"\
                       name="radioOption"><span>C：</span>\
                <input name="radioOptionValue3" type="text" style="width:81%;" value="" >\
				</div><div class="form-group">\
				<div class="col-xs-1 ">\
						<input type="hidden" name="uploadify3" id="uploadify3"  >\
						<input type="file"   name="attachment" id="imgFile3" class="uploadImg" value="" />\
				</div>\
				<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
				</div>\
				</div>\
			</li>\
            <li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
                <input type="radio" value="D"\
                   name="radioOption"><span>D：</span>\
           		<input name="radioOptionValue4" type="text" style="width:80%;" value="" >\
				</div><div class="form-group">\
				<div class="col-xs-1 ">\
						<input type="hidden" name="uploadify4" id="uploadify4"  >\
						<input type="file"   name="attachment" id="imgFile4" class="uploadImg" value="" />\
			  	</div>\
			  	<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"> <input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
				</div>\
				</div>\
			</li>\
        </ul>\
        <div class="clear"></div>\
        <div>\
            <input type="button" class="answer_add_btn" onclick="addRadio();"\
                   value="+ 增加选项或答案">\
        </div>\
		<div id="singleHintDiv"></div>\
    </div>\
</div>';

var contentTypeFilling = '<div class="form-group">\
    <label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span> 题干答案: </label>\
	<div class="col-xs-5 col-sm-10">\
		<label class="col-sm-10">(请将答案处用"【】"替代，"【】"内是正确答案，并且答案中不包含"【"、"】"、"^"特殊符号。)</label>\
	</div>\
	<div class="col-xs-12 col-sm-9" id="fillingError">\
		<div class="clearfix" id="wysiwygEditorFilling">\
	<textarea class="kindeditor" style="width:98.7%;height:300px;word-wrap: break-word;visibility:hidden;"></textarea>\
	    </div>\
		<input type="hidden" name="questionHead" id="questionHeadFilling" class="{required:true}" />\
	    <input type="hidden" name="questionHeadText" id="questionHeadFillingText" class="" />\
	</div>\
</div>';

var contentTypeNoFilling = '<div class="form-group">\
    <label class="col-sm-1 control-label no-padding-right" id="notFillingType"><span style="color: red;">*</span> 题干: </label>\
    <div class="col-xs-12 col-sm-9">\
		<div class="clearfix" id="wysiwygEditorNoFilling">\
	<textarea class="kindeditor" style="width:98.7%;height:300px;word-wrap: break-word;visibility:hidden;"></textarea>\
        </div>\
		<input type="hidden" name="questionHead" id="questionHeadNoFilling" class="{required:true}" />\
	    <input type="hidden" name="questionHeadText" id="questionHeadNoFillingText" class="" />\
    </div>\
</div>';

var answerJudge = '<div class="form-group">\
	<label class="col-sm-1 control-label no-padding-right"> 正确答案: </label>\
<div class="col-sm-5 paddingtop7px">\
    <p >\
        <input type="radio" name="judge" value="对" class="aa" id="rigth" checked><label for="rigth">对</label>&nbsp &nbsp\
        <input type="radio" name="judge" value="错" id="wrong"><label for="wrong">错</label>\
    </p>\
</div>\
</div>';

var answerNomal = '<div class="form-group">\
    <label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span>正确答案: </label>\
	<div class="col-xs-12 col-sm-9">\
	<div class="clearfix" id="wysiwygEditorAnswer">\
	<textarea class="kindeditor" style="width:98.7%;height:300px;word-wrap: break-word;visibility:hidden;"></textarea>\
    </div>\
	<input type="hidden" name="answer" id="answer" class="{required:true}" />\
	<input type="hidden" name="answerText" id="answerText" class="" />\
</div>';
	
var answerAttachment = '<div class="form-group">\
    <label class="col-sm-1 control-label no-padding-right">正确答案: </label>\
	<div class="col-xs-12 col-sm-9">\
	<div class="clearfix" id="wysiwygEditorAnswer">\
		<input type="file" name="attachment" id="answerNomalAttachment" class="" value="" />\
		<input type="hidden" name="answerAttachment" id="answerAttachment" value="" />\
		<input type="hidden" name="attachmentUrl" id="attachmentUrl" value="" />\
    </div>\
</div>';

var practical = '<div class="form-group">\
    <label class="col-sm-1 control-label no-padding-right">素材: </label>\
	<div class="col-xs-12 col-sm-9">\
	<div class="clearfix" id="wysiwygEditorAnswer">\
		<input type="file" name="attachment" id="practicalNomalAttachment" class="" value="" />\
		<input type="hidden" name="answerAttachment" id="practicalAttachment" value="" />\
		<input type="hidden" name="attachmentUrl" id="practicalAttachmentUrl" value="" />\
    </div>\
</div>';

var solution = '<div class="form-group">\
    <label class="col-sm-1 control-label no-padding-right"> 答案说明: </label>\
<div class="col-xs-12 col-sm-9">\
    <div class="clearfix" id="wysiwygEditorSolution">\
	<textarea class="kindeditor" style="width:98.7%;height:300px;word-wrap: break-word;visibility:hidden;"></textarea>\
	</div>\
	<input type="hidden" name="solution" id="solution" />\
	<input type="hidden" name="solutionText" id="solutionText" />\
</div>\
</div>';

var fillingError            = '<label for="questionHeadFilling" generated="true" class="error" style="display: inline-block;">请填写填空题的正确答案</label>';
var blankError              = '<label for="" generated="true" class="error" style="display: inline-block;">正确答案请不要输入空格</label>';
var singleError             = '<label for="" generated="true" class="error" style="display: inline-block;">请选择正确答案</label>';
var optionBlankError        = '<label for="" generated="true" class="error" style="display: inline-block;">选项内容和选项图片不能同时为空</label>';
var multiplError            = '<label for="" generated="true" class="error" style="display: inline-block;">请至少选择两个正确答案</label>';
var OptionRepeatError       = '<label for="" generated="true" class="error" style="display: inline-block;">选项内容不能重复</label>';
var OptionMaxError          = '<label for="" generated="true" class="error" style="display: inline-block;">哎呦,亲,不能再增加更多的选项了</label>';
