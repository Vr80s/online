$(function(){
    imgSenBut();
    createImageUpload($('.uploadImg1'));//生成图片编辑器
    createImageUpload($('.uploadImg2'));//生成图片编辑器
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


function imgSenBut(){
    $("#imgAdd").html('<input type="file" name="img" id="imgPath_file" class="uploadImg1"/>');
    $("#imgAddPoster").html('<input type="file" name="img" id="Poster_file" class="uploadImg2"/>');

};
function createImageUpload(obj){
    obj.ace_file_input({
        style:'well',
        btn_choose:'点击选择图片',
        btn_change:null,
        no_icon:'ace-icon fa fa-cloud-upload',
        droppable:true,
        thumbnail:'small',//large | fit
        preview_error : function(filename, error_code) {
        }
    }).on('change', function(){
    });
    obj.ace_file_input('update_settings',
        {
            'allowExt': ["jpeg", "jpg", "png", "gif" , "bmp"],
            'allowMime': ["image/jpg","image/jpeg", "image/png", "image/gif", "image/bmp"]
        });
    $(".remove").hide();

}
//图片上传统一上传到附件中心---- 修改  列表页
$("#add-form").on("change","#imgPath_file",function(){
    debugger
    
    var $this = $(this);
     
    var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
    if(v!='BMP' && v!='GIF'&& v!='PNG'&& v!='JPG'){
        alert("图片格式错误,请重新选择.");
        $("#imgPath_file").parent().parent().find('.ace-file-container').remove();
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
            ajaxFileUpload(id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
                if (data.error == 0) {
                    $("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
                    $("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
                    $("#"+id).parent().find(".ace-file-name img").attr("style","width: 228px; height: 110px;");
                    $("#add_imgPath").val(data.url);
                    document.getElementById("imgPath_file").focus();
                    document.getElementById("imgPath_file").blur();
                    $(".remove").hide();
                }else {
                    layer.msg(data.message);
                }
            })
        }
    }
      //开始读取
    reader.readAsDataURL(myTest);
    

});

//上传海报     统一上传到附件中心----
$("#add-form").on("change","#Poster_file",function(){
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


//新增
$("#saveBtn").click(function(){
    //学习地址赋值
    var province = $("#edit_province").find("option:selected").text();
    var city = $("#edit_citys").find("option:selected").text();
    var county = $("#edit_county").find("option:selected").text();
    var detailedAddress = $("#detailedAddress").val();
    $("#studyAddress").val(province+"-"+city+"-"+county+"-"+detailedAddress);
    //富文本框
    $("#ceremonyAddress").val(UE.getEditor('editor').getContent());
    $("#regulations").val(UE.getEditor('editor1').getContent());

    if($("#add-form").valid()){
        mask();
        $("#add-form").attr("action", basePath+"/apprentice/addEnrollmentRegulations");
        $("#add-form").ajaxSubmit(function(data){
            data = getJsonData(data);
            if(data.success){
                layer.msg(data.resultObject);
                turnPage(basePath+'/home#apprentice/enrollmentRegulations/index');
            }else{
                layer.msg(data.errorMessage);
            }
            unmask();
        })
    }
});

//返回
$("#returnbutton").click(function(){
    turnPage(basePath+'/home#apprentice/enrollmentRegulations/index');
})


//附件上传统一上传到附件中心---- 修改  列表页
$("#add-form").on("change","#attachment_file",function(){
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