$(function () {

    //性别赋值
    $("#sex").val($("#oldSex").val());
    //学历赋值
    $("#education").val($("#oldEducation").val());

    //籍贯赋值
    var oldNativePlace = $("#oldNativePlace").val();



    //时间插件
    $('#startTime').datetimepicker({

        showSecond: true,
        changeMonth: true,
        changeYear: true,
        language: 'zh-CN',
        dateFormat:'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: 'HH:mm:ss',
        onSelect: function( startDate ) {
            var $endDate = $('#endTime');
            $endDate.datetimepicker( "option", "minDate", startDate );
        }
    });
    $('#endTime').datetimepicker({
        showSecond: true,
        changeMonth: true,
        changeYear: true,
        dateFormat:'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: 'HH:mm:ss',
        onSelect: function( endDate ) {
            var $startDate = $( "#startTime" );
            $startDate.datetimepicker( "option", "maxDate", endDate);
        }
    });
    createDatetimePicker2($("#deadline"),"yy-mm-dd","HH:mm:ss");
});







//保存修改
$("#saveBtn").click(function () {
    debugger
    //判断是否已选择地址
    if($("#choosePro").val()=="-1"){
        $("#choosePro").val("");
    }
    if($("#chooseCity").val()=="请选择您所在城市"){
        $("#chooseCity").val("");
    }
    if($("#chooseCounty").val()=="-1"){
        $("#chooseCounty").val("");
    }
    //学习地址赋值
    var province = $("#province").find("option:selected").text();
    var city = $("#citys").find("option:selected").text();
    var county = $("#county").find("option:selected").text();
    //var detailedAddress = $("#detailedAddress").val();
    $("#nativePlace").val(province+"-"+city+"-"+county);


    if ($("#update-form").valid()) {
        mask();
        $("#update-form").attr("action", basePath + "/apprentice/updateEntryInformation");
        $("#update-form").ajaxSubmit(function (data) {
            data = getJsonData(data);
            if (data.success) {
                layer.msg(data.resultObject);
                turnPage(basePath + '/home#apprentice/entryInformation/index');
            } else {
                layer.alert(data.errorMessage);
            }
            unmask();
        })
    }
});

//返回
$("#returnbutton").click(function () {
    turnPage(basePath + '/home#apprentice/entryInformation/index');
})



