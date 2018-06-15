$(function () {

    //性别赋值
    $("#sex").val($("#oldSex").val());
    //学历赋值
    $("#education").val($("#oldEducation").val());

});





//返回
$("#returnbutton").click(function () {
    turnPage(basePath + '/home#apprentice/entryInformation/index');
})



