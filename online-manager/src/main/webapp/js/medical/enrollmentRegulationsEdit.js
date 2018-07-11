$(function () {
    imgSenBut();
    createImageUpload($('.uploadImg1'));//生成图片编辑器
    createImageUpload($('.uploadImg2'));//生成图片编辑器
    //图片回显
    reviewImage("add_imgPath", $("#add_imgPath").val())
    reviewImage("posterImg", $("#posterImg").val())
    //回显地址
    var oldStudyAddress = $("#oldStudyAddress").val().split("-");
    var province = oldStudyAddress[0];
    var city = oldStudyAddress[1];
    var county = oldStudyAddress[2];
    //省
    $("#citys").empty();
    $('#edit_province option:contains(' + province + ')').each(function () {
        if ($(this).text() == province) {
            $(this).prop("selected", 'selected');
        }
    });
    //市
    doProvAndCityRelationEdit();
    $('#edit_citys option:contains(' + city + ')').each(function () {
        if ($(this).text() == city) {
            $(this).prop("selected", 'selected');
        }
    });
    //县
    doProvAndCountyRelationEdit();
    debugger;
    $('#edit_county option:contains(' + county + ')').each(function () {
        if ($(this).text() == county) {
            $(this).prop("selected", 'selected');
        }
    });

    var detailedAddress="";
    for(var i=3;i<oldStudyAddress.length;i++){
        detailedAddress += oldStudyAddress[i];
    }
    $("#detailedAddress").val(detailedAddress);
    //时间插件
    $('#startTime').datetimepicker({

        showSecond: true,
        changeMonth: true,
        changeYear: true,
        language: 'zh-CN',
        dateFormat:'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: 'HH:mm:ss',
        minDate:new Date(),
        onSelect: function( startDate ) {
            var $endDate = $('#endTime');
            $endDate.datetimepicker( "option", "minDate", startDate );
            var $startDate = $( "#deadline" );
            $startDate.datetimepicker( "option", "maxDate", startDate);
        }
    });
    $('#endTime').datetimepicker({
        showSecond: true,
        changeMonth: true,
        changeYear: true,
        dateFormat:'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: 'HH:mm:ss',
        minDate:new Date(),
        onSelect: function( endDate ) {
            var $startDate = $( "#startTime" );
            $startDate.datetimepicker( "option", "maxDate", endDate);
        }
    });
    $('#deadline').datetimepicker({
        showSecond: true,
        changeMonth: true,
        changeYear: true,
        dateFormat:'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: 'HH:mm:ss',
        minDate:new Date(),
        onSelect: function( startDate ) {
            var $startDate = $( "#startTime" );
            $startDate.datetimepicker( "option", "minDate", startDate);
        }
    });
});

//上传banner图的比例
var goodBili = 0.56;
var minbili = 0.5;
var maxbili = 0.6;


function imgSenBut() {
    $("#imgAdd").html('<input type="file" name="img" id="imgPath_file" class="uploadImg1"/>');
    $("#imgAddPoster").html('<input type="file" name="img" id="Poster_file" class="uploadImg2"/>');
};

function createImageUpload(obj) {
    obj.ace_file_input({
        style: 'well',
        btn_choose: '点击选择图片',
        btn_change: null,
        no_icon: 'ace-icon fa fa-cloud-upload',
        droppable: true,
        thumbnail: 'small',//large | fit
        preview_error: function (filename, error_code) {
        }
    }).on('change', function () {
    });
    obj.ace_file_input('update_settings',
        {
            'allowExt': ["jpeg", "jpg", "png", "gif" , "bmp"],
            'allowMime': ["image/jpg","image/jpeg", "image/png", "image/gif", "image/bmp"]
        });
    $(".remove").hide();

}

//图片上传统一上传到附件中心---- 修改  列表页
$("#update-form").on("change", "#imgPath_file", function () {
	
	var $this = $(this);
	
    var v = this.value.split(".")[this.value.split(".").length - 1].toUpperCase();
    if (v!='BMP' && v!='GIF'&& v!='PNG' && v!='JPG') {
        $("#imgPath_file").parent().parent().find('.ace-file-container').remove();
        alert("图片格式错误,请重新选择.");
        createImageUpload($('.uploadImg1'));//生成图片编辑器
        this.value="";
        $("#add_imgPath").val("");
        return;
    }
    var imgSize = document.getElementById("imgPath_file").files[0].size;
    if(imgSize>2*1024*1024){
        alert("上传的图片的大于2M,请重新选择.");
        $("#imgPath_file").parent().parent().find('.ace-file-container').remove();
        createImageUpload($('.uploadImg1'));//生成图片编辑器
        this.value="";
        $("#add_imgPath").val("");
        return;
    }
    
    
          //限制上传的图片尺寸比例
    var myTest = document.getElementById("imgPath_file").files[0];
    var reader = new FileReader();
    reader.onerror = function(){
        console.log("加载成功")
        alertInfo("读取失败");
    }
    reader.onabort = function(){
        console.log("读取被中止")
        alertInfo("读取被中止");
    }
    //文件读取成功完成时触发
    reader.onload = function(theFile){
        var image = new Image();
        image.src = theFile.target.result;
        image.onload = function(){
            var height = this.height;
            var width = this.width;
            var bili = parseFloat(height/width);
            if(bili<minbili || bili > maxbili){
            	
               $(".clearfixAdd").remove();
                //$("#imgDivAdd").prepend("<input type=\"file\" name=\"img\" id=\"imgPath_file\" class=\"uploadImg1\"/>");
                $("#imgDivAdd").prepend("<div class=\"clearfixAdd\">\n" +
                    "   <input type=\"file\" name=\"img\" id=\"imgPath_file\" class=\"uploadImg1\"/>\n" +
                    "</div>");

                  createImageUpload($('#imgPath_file'));//生成图片编辑器       
                    
                $this.value = "";
                alertInfo("推荐使用尺寸750*425。banner图片最佳比例：高/宽在"+goodBili+"左右。" +
                        "此比例限制在："+minbili+"~"+maxbili+"之间。");
                return;
            }
            
                var id = $this.attr("id");
                ajaxFileUpload(id, basePath + "/attachmentCenter/upload?projectName=online&fileType=1", function (data) {
                    if (data.error == 0) {
                        $("#" + id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
                        $("#" + id).parent().find(".ace-file-name img").attr("src", data.url);
                        $("#" + id).parent().find(".ace-file-name img").attr("style", "width: 250px; height: 140px;");
            
                        $("#add_imgPath").val(data.url);
                        document.getElementById("imgPath_file").focus();
                        document.getElementById("imgPath_file").blur();
                        $(".remove").hide();
                    } else {
                        layer.msg(data.message);
                    }
                })
        }
    }
      //开始读取
    reader.readAsDataURL(myTest);
});

//上传海报     统一上传到附件中心----
$("#update-form").on("change","#Poster_file",function(){
    var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
    if(v!='BMP' && v!='GIF'&& v!='PNG' && v!='JPG'){
        $("#Poster_file").parent().parent().find('.ace-file-container').remove();
        alert("图片格式错误,请重新选择.");
        createImageUpload($('.uploadImg2'));//生成图片编辑器
        this.value="";
        $("#posterImg").val("");
        return;
    }
    var imgSize = document.getElementById("Poster_file").files[0].size;
    if(imgSize>2*1024*1024){
        alert("上传的图片的大于2M,请重新选择.");
        $("#Poster_file").parent().parent().find('.ace-file-container').remove();
        createImageUpload($('.uploadImg2'));//生成图片编辑器
        this.value="";
        $("#posterImg").val("");
        return;
    }
    var id = $(this).attr("id");
    ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
        if (data.error == 0) {
            $("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
            $("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
            $("#"+id).parent().find(".ace-file-name img").attr("style","width: 228px; height: 110px;");
            $("#posterImg").val(data.url);
            document.getElementById("Poster_file").focus();
            document.getElementById("Poster_file").blur();
            $(".remove").hide();
        }else {
            layer.msg(data.message);

        }
    })
});

//保存修改
$("#saveBtn").click(function () {
debugger
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();

    var d1 = new Date(startTime.replace(/\-/g, "\/"));
    var d2 = new Date(endTime.replace(/\-/g, "\/"));

    if(startTime!=""&&endTime!=""&&d1 >=d2)
    {
        alert("学习开始时间不能大于结束时间！");
        return false;
    }
    //学习地址赋值
    var province = $("#edit_province").find("option:selected").text();
    var city = $("#edit_citys").find("option:selected").text();
    var county = $("#edit_county").find("option:selected").text();
    var detailedAddress = $("#detailedAddress").val();
    $("#studyAddress").val(province+"-"+city+"-"+county+"-"+detailedAddress);
    //富文本框
    $("#ceremonyAddress").val(UE.getEditor('editor').getContent());
    $("#regulations").val(UE.getEditor('editor1').getContent());

    if ($("#update-form").valid()) {
        mask();
        $("#update-form").attr("action", basePath + "/apprentice/updateEnrollmentRegulations");
        $("#update-form").ajaxSubmit(function (data) {
            data = getJsonData(data);
            if (data.success) {
                layer.msg(data.resultObject);
                turnPage(basePath + '/home#apprentice/enrollmentRegulations/index');
            } else {
                layer.msg(data.errorMessage);
            }
            unmask();
        })
    }
});

//返回
$("#returnbutton").click(function () {
    turnPage(basePath + '/home#apprentice/enrollmentRegulations/index');
})

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImage(inputId, imgSrc) {
    if (imgSrc == null || imgSrc == "") return;
    var fileName = imgSrc;
    if (imgSrc.indexOf("/") > -1) {
        fileName = imgSrc.substring(imgSrc.lastIndexOf("/") + 1);
    }
    $("#" + inputId).parent().find('.ace-file-name').remove();
    $("#" + inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
        .addClass('selected').html('<span class="ace-file-name" data-title="' + fileName + '">'
        + ('<img class="middle" style="width: 250px; height: 140px;" src="' + imgSrc + '"><i class="ace-icon fa fa-picture-o file-image"></i>')
        + '</span>');
}

//附件上传统一上传到附件中心---- 修改  列表页
$("#update-form").on("change","#attachment_file",function(){
    var id = $(this).attr("id");
    var v = this.value.split(".")[1].toUpperCase();
    if(v!='DOCX'){
        alert("文件格式错误,请重新选择.docx 结尾的文档");
        this.value="";
        return;
    }
    ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
        if (data.error == 0) {
            $("#entryFormAttachment").val(data.url);
            console.log(data.url)
            $(".remove").hide();
        }else {
            layer.msg(data.message);
        }
    })
});