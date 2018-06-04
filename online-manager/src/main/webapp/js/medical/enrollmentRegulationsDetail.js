$(function () {
    imgSenBut();
    createImageUpload($('.uploadImg'));//生成图片编辑器
    document.onkeydown = function (event) {
    }
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
    onchangeCityEdit();
    $('#edit_County option:contains(' + county + ')').each(function () {
        if ($(this).text() == county) {
            $(this).prop("selected", 'selected');
        }
    });

    var detailedAddress="";
    for(var i=3;i<oldStudyAddress.length;i++){
        detailedAddress += oldStudyAddress[i];
    }
    $("#detailedAddress").val(detailedAddress);
});

function imgSenBut() {
    $("#imgAdd").html('<input type="file" name="img" id="imgPath_file"  class="uploadImg"/>');
    $("#imgAddPoster").html('<input type="file" name="img" id="Poster_file" class="uploadImg"/>');
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
            'allowExt': ["jpeg", "jpg", "png", "gif", "bmp"],
            'allowMime': ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"]
        });
    $(".remove").hide();

}


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

