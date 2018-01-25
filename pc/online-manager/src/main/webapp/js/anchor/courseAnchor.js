var P_courseTable;//职业课列表
var courseForm;//添加课程表单

$(function() {
    createDatePicker($(".datetime-picker"),"yy-mm-dd");
    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            return false;
        }
    }
    /** 职业课列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [

        {"title": "主播", "class": "center", "width": "6%", "sortable": false, "data": 'name'},
        {"title": "类型", "class": "center", "width": "8%", "sortable": false, "data": 'type',"mRender": function (data, display, row) {
            if(row.type==1){
                return "医师";
            }else if(row.type==2){
                return "医馆";
            }
        }},
        {"title": "点播分成", "class": "center", "width": "8%", "sortable": false, "data": 'vodDivide'},
        {"title": "直播分成", "class": "center", "width": "8%", "sortable": false, "data": 'liveDivide'},
        {"title": "线下课分成", "class": "center", "width": "8%", "sortable": false, "data": 'offlineDivide'},
        {"title": "礼物分成", "class": "center", "width": "8%", "sortable": false, "data": 'giftDivide'},
        {
            "sortable": false,
            "class": "center",
            "width": "12%",
            "title": "操作",
            "mRender": function (data, display, row) {
                var str = '<div class="hidden-sm hidden-xs action-buttons">' ;
                if (row.status == 0) {
                       str += '<a class="blue" href="javascript:void(-1);" title="关闭主播权限" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
                       } else {
                       str += '<a class="blue" href="javascript:void(-1);" title="打开主播权限" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
                }
                str += '<a class="blue" href="javascript:void(-1);" title="设置分成比例" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
                return str;
            }
        }];

    P_courseTable = initTables("courseTable", basePath + "/anchor/courseAnchor/list", objData, true, true, 0, null, searchCase_P, function (data) {
    });
});
    /** 职业课列表end */

    function showDetailDialog(obj, status) {
        debugger
        var oo = $(obj).parent().parent().parent();
        var aData, page;
        if (status == 1) {
            aData = P_courseTable.fnGetData(oo); // get datarow
            page = getCurrentPageNo(P_courseTable);
        }
        window.location.href = basePath + '/home#cloudclass/courseApply/courseDetail?id=' + aData.id;
    }


    /**
     * 添加职业课
     */
    $(".add_P").click(function () {
        debugger

        createImageUpload($('.uploadImg_add'));//'新增职业课'弹出框的生成图片编辑器

        $("input[name='isFree']").eq(1).attr("checked", "checked");

        courseForm.resetForm();
        $("#classRatedNum").attr("disabled", true);
        $("#gradeStudentSum").hide();
        $("#classQQ").hide();
        $("#gradeQQ").attr("disabled", true);


        $("#addCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
        $("#add_serviceType").val(0);//职业课类型
        var dialog = openDialog("addCourseDialog", "dialogAddCourseDiv", "新增职业课程", 580, 600, true, "确定", function () {
            $("#descriptionHid").val($("#courseDescribe").val());
            if ($("#addCourse-form").valid()) {
                var selectRadio = $("input[name='isFree']:checked").val();
                var courseDesc = $("#courseDescribe").val();
                if (courseDesc == null || courseDesc == "") {
                    layer.msg("请输入课程简介！");
                    return;
                }
                mask();
                $("#addCourse-form").attr("action", basePath + "/cloudclass/course/addCourse");
                $("#addCourse-form").ajaxSubmit(function (data) {
                    try {
                        data = jQuery.parseJSON(jQuery(data).text());
                    } catch (e) {
                        data = data;
                    }
                    unmask();
                    if (data.success) {
                        $(".ace-file-container").remove();
                        $("#addCourseDialog").dialog("close");
                        layer.msg(data.errorMessage);
                        freshTable(P_courseTable);
                        $("html").css("overflow", "auto");
                    } else {
                        layer.msg(data.errorMessage);
                    }
                });
            }
        });
    });


    /**
     * 职业课列表搜索
     */
    function search_P() {
        var json = new Array();
        var startTime = $("#startTime").val(); //开始时间
        var stopTime = $("#stopTime").val(); //结束时间
        if(startTime != "" || stopTime != "") {

            if (startTime != "" && stopTime != "" && startTime > stopTime) {
                alertInfo("开始日期不能大于结束日期");
                return;
            }
            json.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
            json.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
        }
        json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
        searchButton(P_courseTable, json);
    };




    // (function ($) {
    //     $.widget("custom.combobox", {
    //         _create: function () {
    //             this.wrapper = $("<span>")
    //                 .addClass("custom-combobox")
    //                 .insertAfter(this.element);
    //
    //             this.element.hide();
    //             this._createAutocomplete();
    //             this._createShowAllButton();
    //         },
    //
    //         _createAutocomplete: function () {
    //             var selected = this.element.children(":selected"),
    //                 value = selected.val() ? selected.text() : "";
    //
    //             this.input = $("<input>")
    //                 .appendTo(this.wrapper)
    //                 .val(value)
    //                 .attr("title", "")
    //                 .attr("id", "nihao")
    //                 .addClass("custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left")
    //                 .autocomplete({
    //                     delay: 0,
    //                     minLength: 0,
    //                     source: $.proxy(this, "_source")
    //                 })
    //                 .tooltip({
    //                     tooltipClass: "ui-state-highlight"
    //                 });
    //
    //             this._on(this.input, {
    //                 autocompleteselect: function (event, ui) {
    //                     ui.item.option.selected = true;
    //                     this._trigger("select", event, {
    //                         item: ui.item.option
    //                     });
    //                 },
    //
    //                 autocompletechange: "_removeIfInvalid"
    //             });
    //         },
    //
    //         _createShowAllButton: function () {
    //             var input = this.input,
    //                 wasOpen = false;
    //
    //             $("<a>")
    //                 .attr("tabIndex", -1)
    //                 .attr("title", "Show All Items")
    //                 .tooltip()
    //                 .appendTo(this.wrapper)
    //                 .button({
    //                     icons: {
    //                         primary: "ui-icon-triangle-1-s"
    //                     },
    //                     text: false
    //                 })
    //                 .removeClass("ui-corner-all")
    //                 .addClass("custom-combobox-toggle ui-corner-right")
    //                 .mousedown(function () {
    //                     wasOpen = input.autocomplete("widget").is(":visible");
    //                 })
    //                 .click(function () {
    //                     input.focus();
    //
    //                     // 如果已经可见则关闭
    //                     if (wasOpen) {
    //                         return;
    //                     }
    //
    //                     // 传递空字符串作为搜索的值，显示所有的结果
    //                     input.autocomplete("search", "");
    //                 });
    //         },
    //
    //         _source: function (request, response) {
    //             var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
    //             response(this.element.children("option").map(function () {
    //                 var text = $(this).text();
    //                 if (this.value && ( !request.term || matcher.test(text) ))
    //                     return {
    //                         label: text,
    //                         value: text,
    //                         option: this
    //                     };
    //             }));
    //         },
    //
    //         _removeIfInvalid: function (event, ui) {
    //
    //             // 选择一项，不执行其他动作
    //             if (ui.item) {
    //                 return;
    //             }
    //
    //             // 搜索一个匹配（不区分大小写）
    //             var value = this.input.val(),
    //                 valueLowerCase = value.toLowerCase(),
    //                 valid = false;
    //             this.element.children("option").each(function () {
    //                 if ($(this).text().toLowerCase() === valueLowerCase) {
    //                     this.selected = valid = true;
    //                     return false;
    //                 }
    //             });
    //
    //             // 找到一个匹配，不执行其他动作
    //             if (valid) {
    //                 return;
    //             }
    //
    //             // 移除无效的值
    //             this.input
    //                 .val("")
    //                 .attr("title", value + " didn't match any item")
    //                 .tooltip("open");
    //             this.element.val("");
    //             this._delay(function () {
    //                 this.input.tooltip("close").attr("title", "");
    //             }, 2500);
    //             this.input.data("ui-autocomplete").term = "";
    //         },
    //
    //         _destroy: function () {
    //             this.wrapper.remove();
    //             this.element.show();
    //         }
    //     });
    // })(jQuery);

    function updateCourseVideo(id) {
        mask();
        debugger
        ajaxRequest(basePath + "/cloudclass/course/updateCourseVideo", "id=" + id, function (data) {
            unmask();
            if (data.success) {
                if (data.resultObject == 'ok') {
                    freshTable(P_courseTable);
                    layer.msg("操作成功！");
                } else {
                    alertInfo(data.resultObject);
                }
            } else {
                alertInfo(data.errorMessage);
            }
        });
    }



function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}