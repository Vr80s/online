var mobileBannerForm;
var mobileBannerFormEdit;
var nowTime;
var searchJson = new Array();
var courseArray = new Array();
var banner2Table;
//上传banner图的比例
var goodBili = 0.4;
var minbili = 0.35;
var maxbili = 0.45;

$(function () {
    nowTime = show();

    searchJson.push('{"tempMatchType":undefined,"propertyName":"search_type","propertyValue1":"' + $("#search_type").val() + '","tempType":undefined}');
    loadBanner2List();
});

//列表展示
function loadBanner2List() {
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
        {
            "title": checkbox,
            "class": "center",
            "width": "5%",
            "height": "68px",
            "sortable": false,
            "data": 'id',
            "mRender": function (data, display, row) {
                return '<input type="checkbox" value=' + data + ' class="ace" /><span class="lbl"></span>';
            }
        },
        {
            title: '序号',
            "class": "center",
            "width": "5%",
            "height": "68px",
            "data": 'id',
            datafield: 'xuhao',
            "sortable": false
        },
        {title: 'banner名称', "class": "center", "height": "68px", "data": 'description', "sortable": false},
        {
            title: '缩略图',
            "class": "center",
            "width": "144px",
            "height": "38px",
            "data": 'imgPath',
            "sortable": false,
            "mRender": function (data, display, row) {
                return "<img src='" + data + "' style='width:128px;height:68px;cursor:pointer;' onclick='showImg(\"" + row.id + "\",\"" + row.description + "\",\"" + row.imgPath + "\")'/>";
            }
        },
        {
            title: '跳转类型',
            "class": "center",
            "height": "68px",
            "data": 'routeType',
            "sortable": false,
            "mRender": function (data, display, row) {
                if (data === "COMMON_COURSE_DETAIL_PAGE") {
                    return "课程";
                } else if (data === "ANCHOR_INDEX") {
                    return "主播";
                } else if (data === "PUBLIC_COURSE_LIST_PAGE") {
                    return "课程列表";
                } else if (data === "H5") {
                    return "外部链接";
                }
                return "";
            }
        }, {
            title: '跳转对象',
            "class": "center",
            "height": "68px",
            "data": 'linkDesc',
            "sortable": false,
            "mRender": function (data, display, row) {
                return data;
            }
        },
        {title: '点击量', "class": "center", "width": "6%", "height": "68px", "data": 'clickCount', "sortable": false},
        {
            title: '创建人',
            "class": "center",
            "width": "8%",
            "height": "68px",
            "data": 'createPersonName',
            "sortable": false
        },
        {
            title: '状态',
            "class": "center",
            "width": "6%",
            "height": "68px",
            "data": 'status',
            "sortable": false,
            "mRender": function (data, display, row) {
                var status;
                if (data == 1) {
                    status = "已启用";
                } else if (data == 0) {
                    status = "已禁用";
                }
                return status;
            }
        },
        {
            title: '排序',
            "class": "center",
            "width": "8%",
            "height": "34px",
            "data": 'sort',
            "sortable": false,
            "mRender": function (data, display, row) {
                var str;
                if (row.status == 1) {//如果是禁用
                    str = '<a class="blue" name="upa" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>' +
                        '<a class="blue" href="javascript:void(-1);" name="downa" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
                } else {//如果是不禁用
                    str = '<a class="gray" href="javascript:void(-1);" title="上移"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>' +
                        '<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
                }
                return '<div class="hidden-sm hidden-xs action-buttons">' + str;
            }
        },
        {
            title: "操作",
            "class": "center",
            "width": "8%",
            "height": "34px",
            "data": "id",
            "sortable": false,
            "mRender": function (data, display, row) {

                var buttons = '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="修改" onclick="updateMobileBanner(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
                if (row.status == 1) {
                    buttons += '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
                } else {
                    buttons += '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
                }
                buttons += "</div>";
                return buttons;
            }
        }
    ];

    banner2Table = initTables("banner2Table", basePath + "/operate/banner2/findBanner2List", dataFields, true, true, 2, null, searchJson, function (data) {
        var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize + 1;//页码

        var countPage;

        if (countNum % pageSize == 0) {
            countPage = parseInt(countNum / pageSize);
        } else {
            countPage = parseInt(countNum / pageSize) + 1;
        }

        $("[name='upa']").each(function (index) {
            if (index == 0) {
                $(this).css("pointer-events", "none").removeClass("blue").addClass("gray");
            }
        });
        $("[name='downa']").each(function (index) {
            if (index == $("[name='downa']").size() - 1) {
                $(this).css("pointer-events", "none").removeClass("blue").addClass("gray");
            }
        });
    });

    createImageUpload($('.uploadImg'));//生成图片编辑器


    mobileBannerForm = $("#addMobileBanner-form");

    mobileBannerFormEdit = $("#updateMobileBanner-form");
}

//条件搜索
function search() {

    var search_status = $("#search_status").val();
    var search_description = $("#search_description").val();

    if (search_status != "") {
        searchJson.push('{"tempMatchType":"8","propertyName":"status","propertyValue1":"' + search_status + '","tempType":"String"}');
    }

    if (search_description != null && search_description != "") {
        searchJson.push('{"tempMatchType":"9","propertyName":"description","propertyValue1":"' + search_description + '","tempType":"String"}');
    }

    searchButton(banner2Table, searchJson);
    searchJson.pop();
    searchJson.pop();

}

$('#J-menu').on('change', function (e) {
    var menuId = $(this).val();
    ajaxRequest("message/messagePush/course?menuId=" + menuId, null, function (res) {
        console.log(res.resultObject.length);
        $("#J-course").html('');
        if (res.resultObject.length > 0) {
            for (var i = 0; i < res.resultObject.length; i++) {
                var course = res.resultObject[i];
                if (course != null) {
                    var appendStr = '<option value=' + course.id + '>' + course.courseName + '</option>';
                    console.log(appendStr);
                    $("#J-course").append(appendStr);
                }
            }
        }
    });
});

$('#J-edit-menu').on('change', function (e) {
    var menuId = $(this).val();
    renderCourseSelect(menuId, null);
});

function renderCourseSelect(menuId, courseId) {
    ajaxRequest("message/messagePush/course?menuId=" + menuId, null, function (res) {
        console.log(res.resultObject.length);
        $("#J-edit-course").html('');
        if (res.resultObject.length > 0) {
            for (var i = 0; i < res.resultObject.length; i++) {
                var course = res.resultObject[i];
                if (course != null) {
                    var appendStr;
                    if (courseId && course.id == courseId) {
                        appendStr = '<option value=' + course.id + ' selected>' + course.courseName + '</option>';
                    } else {
                        appendStr = '<option value=' + course.id + '>' + course.courseName + '</option>';
                    }
                    $("#J-edit-course").append(appendStr);
                }
            }
        }
    });
}


//新增框
$(".add_bx").click(function () {

    $(".J-link").hide();
    $("#yqti_textarea").val("");
    $("#add_url").attr("placeholder", "");

    mobileBannerForm.resetForm();
    //$(".remove").trigger("click");
    $(".clearfixAdd").remove();
    $("#addDiv").prepend("<div class=\"clearfixAdd\">\n" +
        "	<input type=\"file\" name=\"imgPath_file\" id=\"imgPath_file\" class=\"uploadImg\"/>\n" +
        "</div>");
    createImageUpload($('#imgPath_file'));//生成图片编辑器

    var dialog = openDialog("addMobileBannerDialog", "dialogAddMobileBannerDiv", "新增", 580, 500, true, "确定", function () {
        var checked = checkForm();
        if (checked) {
            mask();
            $("#addMobileBanner-form").attr("action", basePath + "/operate/banner2/addBanner2");
            $("#addMobileBanner-form").ajaxSubmit(function (data) {
                data = getJsonData(data);
                unmask();
                if (data.success) {
                    $("#addMobileBannerDialog").dialog("close");
                    freshTable(banner2Table);
                } else {
                    if (data.errorMessage.indexOf("expected") != -1) {
                        alertInfo("加入的信息有误");
                    }
                    alertInfo(data.errorMessage);
                }
            });
        }
    });

    function checkForm() {
        var routeType = $('#linkType').val();
        if (!routeType) {
            alertInfo("请选择链接类型");
            return false;
        }
        var linkParam;
        var linkType = 0;
        if (routeType === "COMMON_COURSE_DETAIL_PAGE") {
            linkParam = $('#J-course').val();
            if (!linkParam) {
                alertInfo("请选择要跳转至的课程");
                return false;
            }
            linkType = 3;
        } else if (routeType === "ANCHOR_INDEX") {
            linkParam = $('#J-anchor').val();
            if (!linkParam) {
                alertInfo("请选择跳转至的主播");
                return false;
            }
            linkType = 4;
        } else if (routeType === "PUBLIC_COURSE_LIST_PAGE") {
            linkParam = $('#J-link-param').val();
            if (!linkParam) {
                alertInfo("请输入跳转至课程列表的条件");
                return false;
            }
            linkType = 5;
        } else if (routeType === 'H5') {
            linkParam = $('#J-link-param').val();
            if (!linkParam) {
                alertInfo("请输入跳转的外部链接");
                return false;
            }
        }

        var imgPath = $('#imgPath_file').val();
        if (!imgPath) {
            alertInfo("请上传图片");
            return false;
        }

        var description = $('#add_description').val();
        if (!description) {
            alertInfo("请输入banner名称");
            return false;
        }

        $('#J-add-linkParam').val(linkParam);
        return true;
    }
});

function routeTypeChange(obj) {
    var routeTypeValue = $(obj).val();
    if (routeTypeValue) {
        if (routeTypeValue === "COMMON_COURSE_DETAIL_PAGE") {
            $('.J-link').hide();
            $('.J-course-detail').show();
            $('.J-anchor-detail').hide();
        } else if (routeTypeValue === 'ANCHOR_INDEX') {
            $('.J-link').hide();
            $('.J-course-detail').hide();
            $('.J-anchor-detail').show();
        } else if (routeTypeValue === 'PUBLIC_COURSE_LIST_PAGE' || routeTypeValue === 'H5') {
            var placeholder = "";
            if (routeTypeValue === 'PUBLIC_COURSE_LIST_PAGE') {
                placeholder = "请输入课程列表条件(提示：在文件管理中，下载连接说明文档。如有疑问，询问开发)";
            } else {
                placeholder = "请输入外部链接";
            }
            $('#J-link-param').prop("placeholder", placeholder);
            $('.J-link').show();
            $('.J-course-detail').hide();
            $('.J-anchor-detail').hide();
        }
    } else {
        $('.J-link').hide();
        $('.J-course-detail').hide();
        $('.J-anchor-detail').hide();
    }
}

function routeTypeChangeEdit(obj) {
    var $editLink = $('.J-edit-link');
    var $editCourseLink = $('.J-edit-course-detail');
    var $editAnchorLink = $('.J-edit-anchor-detail');
    var routeTypeValue = $(obj).val();
    if (routeTypeValue) {
        if (routeTypeValue === "COMMON_COURSE_DETAIL_PAGE") {
            $editLink.hide();
            $editCourseLink.show();
            $editAnchorLink.hide();
        } else if (routeTypeValue === 'ANCHOR_INDEX') {
            $editLink.hide();
            $editCourseLink.hide();
            $editAnchorLink.show();
        } else if (routeTypeValue === 'PUBLIC_COURSE_LIST_PAGE' || routeTypeValue === 'H5') {
            var placeholder = "";
            if (routeTypeValue === 'PUBLIC_COURSE_LIST_PAGE') {
                placeholder = "请输入课程列表条件(提示：在文件管理中，下载连接说明文档。如有疑问，询问开发)";
            } else {
                placeholder = "请输入外部链接";
            }
            $('#J-edit-link-param').prop("placeholder", placeholder);
            $editLink.show();
            $editCourseLink.hide();
            $editAnchorLink.hide();
        }
    } else {
        $editLink.hide();
        $editCourseLink.hide();
        $editAnchorLink.hide();
    }
}

function updateMobileBanner(obj) {
    $("#yqti_textarea_edit").val("");

    $('.ace-file-input .remove').trigger('click');
    var oo = $(obj).parent().parent().parent();
    var row = banner2Table.fnGetData(oo); // get datarow
    mobileBannerFormEdit.resetForm();
    $("#update_name").val(row.description);
    $("#update_imgPath").val(row.imgPath);
    $("#update_id").val(row.id);

    var routeTypeValue = row.routeType;
    var linkParam = row.linkParam;
    var menuId = row.menuId;

    var $editLink = $('.J-edit-link');
    var $editCourseLink = $('.J-edit-course-detail');
    var $editAnchorLink = $('.J-edit-anchor-detail');

    if (routeTypeValue) {
        console.log(routeTypeValue);
        console.log(linkParam);
        if (routeTypeValue === "COMMON_COURSE_DETAIL_PAGE") {
            $editLink.hide();
            $editCourseLink.show();
            $editAnchorLink.hide();
            $('#J-edit-menu').val(menuId);
            renderCourseSelect(menuId, linkParam);
            $('#J-edit-course').val(linkParam);
        } else if (routeTypeValue === 'ANCHOR_INDEX') {
            $editLink.hide();
            $editCourseLink.hide();
            $editAnchorLink.show();
            $('#J-edit-anchor').val(linkParam);
        } else if (routeTypeValue === 'PUBLIC_COURSE_LIST_PAGE' || routeTypeValue === 'H5') {
            var placeholder = "";
            if (routeTypeValue === 'PUBLIC_COURSE_LIST_PAGE') {
                placeholder = "请输入课程列表条件(提示：在文件管理中，下载连接说明文档。如有疑问，询问开发)";
            } else {
                placeholder = "请输入外部链接";
            }
            $('#J-edit-link-param').prop("placeholder", placeholder);
            $('#J-edit-link-param').val(linkParam);
            $editLink.show();
            $editCourseLink.hide();
            $editAnchorLink.hide();
        }
    } else {
        $editLink.hide();
        $editCourseLink.hide();
        $editAnchorLink.hide();
    }

    $('#update_routeType').val(routeTypeValue);
console.log(row.imgPath);
    reviewImage("update_imgPath_file", row.imgPath);

    var dialog = openDialog("updateMobileBannerDialog", "dialogUpdateMobileBannerDiv", "修改", 580, 500, true, "确定", function () {
        if (checkEditForm()) {
            mask();
            $("#updateMobileBanner-form").attr("action", basePath + "/operate/banner2/updateBanner2ById");
            $("#updateMobileBanner-form").ajaxSubmit(function (data) {
                data = getJsonData(data);
                unmask();
                if (data.success) {
                    $("#updateMobileBannerDialog").dialog("close");
                    freshTable(banner2Table);
                } else {
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
}

function checkEditForm() {
    var routeType = $('#update_routeType').val();
    if (!routeType) {
        alertInfo("请选择链接类型");
        return false;
    }
    var linkParam;
    var linkType = 0;
    if (routeType === "COMMON_COURSE_DETAIL_PAGE") {
        linkParam = $('#J-edit-course').val();
        console.log(linkParam);
        if (!linkParam) {
            alertInfo("请选择要跳转至的课程");
            return false;
        }
        linkType = 3;
    } else if (routeType === "ANCHOR_INDEX") {
        linkParam = $('#J-edit-anchor').val();
        if (!linkParam) {
            alertInfo("请选择跳转至的主播");
            return false;
        }
        linkType = 4;
    } else if (routeType === "PUBLIC_COURSE_LIST_PAGE") {
        linkParam = $('#J-edit-link-param').val();
        if (!linkParam) {
            alertInfo("请输入跳转至课程列表的条件");
            return false;
        }
        linkType = 5;
    } else if (routeType === 'H5') {
        linkParam = $('#J-edit-link-param').val();
        if (!linkParam) {
            alertInfo("请输入跳转的外部链接");
            return false;
        }
    }

    var imgPath = $('#update_imgPath').val();
    if (!imgPath) {
        alertInfo("请上传图片");
        return false;
    }

    var description = $('#update_name').val();
    if (!description) {
        alertInfo("请输入banner名称");
        return false;
    }

    $('#J-edit-linkParam').val(linkParam);
    return true;
}

//图片上传统一上传到附件中心---- 修改  列表页
$("#addMobileBanner-form").on("change", "#imgPath_file", function () {
    
	var $this = $(this);
    
    var v = this.value.split(".")[1].toUpperCase();
    if (v != 'BMP' && v != 'GIF' && v != 'JPEG' && v != 'PNG' && v != 'SVG' && v != 'JPG') {
        layer.alert("图片格式错误,请重新选择.");
        this.value = "";
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
                $("#addDiv").prepend("<div class=\"clearfixAdd\">\n" +
                    "   <input type=\"file\" name=\"imgPath_file\" id=\"imgPath_file\" class=\"uploadImg\"/>\n" +
                    "</div>");
                   
                createImageUpload($('#imgPath_file'));//生成图片编辑器       
                    
                $this.value = "";
                alertInfo("推荐使用尺寸860*346。banner图片最佳比例：高/宽在"+goodBili+"左右。" +
                        "此比例限制在："+minbili+"~"+maxbili+"之间。");
                return;
            }
            
            var id = $this.attr("id");
            ajaxFileUpload(id, basePath + "/attachmentCenter/upload?projectName=online&fileType=1", function (data) {
                if (data.error == 0) {
                    $("#" + id).parent().find(".ace-file-name img").attr("src", data.url);
                    $("#" + id).parent().find(".ace-file-name img").attr("style", "width: 250px; height: 140px;");
        
                    $("#add_imgPath").val(data.url);
                    document.getElementById("imgPath_file").focus();
                    document.getElementById("imgPath_file").blur();
                    $(".remove").hide();
                } else {
                    alert(data.message);
                }
            })
        }
    }
      //开始读取
    reader.readAsDataURL(myTest);
    
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#updateMobileBanner-form").on("change", "#update_imgPath_file", function () {
    
     var $this = $(this);
     
    var v = this.value.split(".")[1].toUpperCase();
    if (v != 'BMP' && v != 'GIF' && v != 'JPEG' && v != 'PNG' && v != 'SVG' && v != 'JPG') {
        layer.alert("图片格式错误,请重新选择.");
        this.value = "";
        return;
    }
    
         //限制上传的图片尺寸比例
    var myTest = document.getElementById("update_imgPath_file").files[0];
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
                
                $(".clearfixUpdate").remove();
                $("#editDiv").prepend("<div class=\"clearfixUpdate\">\n" +
                    "   <input type=\"file\" name=\"update_imgPath_file\" id=\"update_imgPath_file\" class=\"uploadImg\"/>\n" +
                    "</div>");
                   
                createImageUpload($('#update_imgPath_file'));//生成图片编辑器       
                    
                $this.value = "";
                alertInfo("推荐使用尺寸860*346。banner图片最佳比例：高/宽在"+goodBili+"左右。" +
                        "此比例限制在："+minbili+"~"+maxbili+"之间。");
                return;
            }
            
            var id = $this.attr("id");
            ajaxFileUpload(id, basePath + "/attachmentCenter/upload?projectName=online&fileType=1", function (data) {
                if (data.error == 0) {
                    $("#" + id).parent().find(".ace-file-name img").attr("src", data.url);
                    $("#" + id).parent().find(".ace-file-name img").attr("style", "width: 250px; height: 140px;");
        
                    $("#update_imgPath").val(data.url);
                    document.getElementById("update_imgPath_file").focus();
                    document.getElementById("update_imgPath_file").blur();
                    $(".remove").hide();
                } else {
                    alert(data.message);
                }
            })
        }
    }
      //开始读取
    reader.readAsDataURL(myTest);
    
    
    
/*    var id = $(this).attr("id");
    ajaxFileUpload(this.id, basePath + "/attachmentCenter/upload?projectName=online&fileType=1", function (data) {
        if (data.error == 0) {
            $("#" + id).parent().find(".ace-file-name img").attr("src", data.url);
            $("#" + id).parent().find(".ace-file-name img").attr("style", "width: 250px; height: 140px;");

            $("#update_imgPath").val(data.url);
            document.getElementById("update_imgPath_file").focus();
            document.getElementById("update_imgPath_file").blur();
            $(".remove").hide();
        } else {
            alert(data.message);
        }
    })*/
});

$('.ace-file-input .remove').on('click', function() {
    console.log("hhhhh");
    $('input[name="imgPath"]').val("");
});

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = banner2Table.fnGetData(oo); // get datarow
    ajaxRequest(basePath + "/operate/banner2/updateStatus", {"id": row.id}, function (data) {
        layer.msg(data.resultObject);
        freshTable(banner2Table);
    });
};


/**
 * 批量逻辑删除
 *
 */
$(".dele_bx").click(function () {
    deleteAll(basePath + "/operate/banner2/deletes", banner2Table);
});

/**
 * 上移
 * @param obj
 */
function upMove(obj) {
    var oo = $(obj).parent().parent().parent();
    var aData = banner2Table.fnGetData(oo);
    ajaxRequest(basePath + '/operate/banner2/upMove', {"id": aData.id}, function (res) {
        if (res.success) {
            freshTable(banner2Table);
        } else {
            layer.msg(res.errorMessage);
        }
    });
};

/**
 * 下移
 * @param obj
 */
function downMove(obj) {
    var oo = $(obj).parent().parent().parent();
    var aData = banner2Table.fnGetData(oo);
    ajaxRequest(basePath + '/operate/banner2/downMove', {"id": aData.id}, function (res) {
        if (res.success) {
            freshTable(banner2Table);
        } else {
            layer.msg(res.errorMessage);
        }
    });
};


//获取当前时间
function show() {
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "-";
    str += (mydate.getMonth() + 1) + "-";
    str += mydate.getDate();
    return str;
}

//字符串转成Time(dateDiff)所需方法
function stringToTime(string) {
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
        parseInt(d[0], 10) || null,
        (parseInt(d[1], 10) || 1) - 1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null
    )).getTime();
}

function dateDiff(date1, date2) {
    var type1 = typeof date1, type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();

    return (date2 - date1) / (1000 * 60 * 60 * 24); //结果是秒
}