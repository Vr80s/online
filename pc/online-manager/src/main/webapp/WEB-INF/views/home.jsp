<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="./common/jstl_taglib.jsp" %>

<!DOCTYPE>
<html>
<head>
    <link rel="shortcut icon" href="${base}/images/logo.ico">
    <title>熊猫中医直播教育管理系统</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/font-awesome.css"/>
    <link rel="stylesheet" href="${base}/css/jquery-ui-timepicker-addon.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/jquery-ui.custom.css"/>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/jquery.gritter.css"/>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/select2.css"/>
    <%-- <link rel="stylesheet" href="${base}/bootstrap/assets/css/datepicker.css" /> --%>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/bootstrap-editable.css"/>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/select2.css"/>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/jquery-ui.css"/>

    <!-- text fonts -->
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-fonts.css"/>

    <!-- ace styles -->
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/bootstrap/assets/css/ace-ie.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${base}/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="${base}/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
    <link rel="stylesheet" href="${base}/css/index.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${base}/js/xcConfirm/css/xcConfirm.css"/>

    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="${base}/bootstrap/assets/js/ace-extra.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="${base}/bootstrap/assets/js/html5shiv.js"></script>
    <script src="${base}/bootstrap/assets/js/respond.js"></script>
    <![endif]-->


    <style>
        #myMenu1 {
            display: none;
            z-index: 9999;
            position: absolute;
            min-width: 120px;
            background-color: #F0F0F0;
            border: solid #0099CC 1px;
            border-radius: 3px;
        }

        #myMenu1 ul li {
            cursor: pointer;
            padding: 4px;
        }

        #myMenu1 ul li:hover {
            background-color: #009966;
            color: #fff;
            font-weight: bold;
        }

        #breadcrumbs {
            background: #f5f5f5 none repeat scroll 0% 0%;
        }

        .lightb > a {
            background-color: none;
            background: none !important;
        }

        #dropdown-myset {
            left: auto;
            right: 4px;
            top: 45px;
        }
    </style>
</head>

<body class="no-skin">
<%-- <jsp:include page="header.jsp" flush="true" /> --%>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <jsp:include page="menu.jsp" flush="true"/>

    <div class="main-content" id="rightDivContent">
        <div class="main-content-inner">
            <div class="breadcrumbs" id="breadcrumbs" style="height:50px;">
                <div class="navbar-header pull-left">
                    <div href="#" class="navbar-brand" style="color:#fff;font-size:20px">
                        <small style="color: #2d3638;">
                            <i class="fa fa-leaf"></i>
                            熊猫中医直播教育管理系统
                        </small>
                    </div>
                </div>
                <div class="navbar-buttons navbar-header pull-right" role="navigation" style="padding-top: 3px;">
                    <ul class="nav ace-nav">
                        <li class="light-blue lightb">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="images/defaultavatar.png" alt="头像"/>
                                <span class="user-info" style="color: #2d3638;">
									<small style="color: #2d3638;">Welcome,</small>
									${_user_.username}
								</span>

                                <i class="ace-icon fa fa-caret-down" style="color: #2d3638;"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close"
                                id="dropdown-myset">
                                <li>
                                    <a href="${base}/logout">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        注销
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <!-- /section:basics/navbar.user_menu -->
                    </ul>
                </div>
            </div>

            <div class="page-content">
                <jsp:include page="setBox.jsp" flush="true"/>

                <div data-ajax-content="true" class="page-content-area" id="pageContentArea" style="opacity: 1;"></div>
            </div>


        </div>
    </div>

    <div id="dialog-confirm" class="hide">
        <div class="alert alert-info bigger-110">请谨慎操作！</div>

        <div class="space-6"></div>

        <p class="bigger-110 bolder center grey">
            <i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
            <span id="confirm_content">确定要删除该条数据吗?</span>
        </p>
    </div>
    <!-- #dialog-confirm -->

    <!-- 存放临时组件的地方，每次切换页面是要清除的 -->
    <div id="tempComponet"></div>

    <div class="contextMenu" id="myMenu1" style="display:none">
        <ul>
            <li id="addTree"><i class="ace-icon fa fa-plus bigger-120 green"></i> <span id="mAdd"></span></li>
            <li id="copyTree"><i class="ace-icon glyphicon glyphicon-copyright-mark bigger-120 green"></i> <span
                    id="mCopy"></span></li>
            <li id="updateTree"><i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i> <span id="mEdit"></span>
            </li>
            <li id="deleteTree"><i class="ace-icon fa fa-trash-o bigger-120 red"></i> <span id="mDel"></span></li>
        </ul>
    </div>

    <jsp:include page="footer.jsp" flush="true"/>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>


<!--[if !IE]> -->
<script type="text/javascript" src="${base}/bootstrap/assets/js/jquery.js"></script>
<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${base}/bootstrap/assets/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='${base}/bootstrap/assets/js/jquery.mobile.custom.js'>" + "<" + "/script>");

</script>
<script src="${base}/bootstrap/assets/js/bootstrap.js"></script>
<!--[if lte IE 8]>
<script src="${base}/bootstrap/assets/js/excanvas.js"></script>
<![endif]-->
<!-- 兼容高版本jquery下过时的api -->
<script type="text/javascript">
    var basePath = "${base}";
    jQuery.browser = {};
    (function () {
        jQuery.browser.msie = false;
        jQuery.browser.version = 0;
        if (navigator.userAgent.match(/MSIE ([0-9]+)./)) {
            jQuery.browser.msie = true;
            jQuery.browser.version = RegExp.$1;
        }
    })();

    //文件上传的服务器地址
    var editorUploadUrl = '${base}/attachmentCenter/upload?projectName=kcenter&fileType=1';

</script>

<script src="${base}/bootstrap/assets/js/jquery-ui.js"></script>
<script src="${base}/bootstrap/assets/js/jquery-ui.custom.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.ui.touch-punch.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.gritter.js"></script>
<script src="${base}/bootstrap/assets/js/bootbox.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.easypiechart.js"></script>
<%-- <script src="${base}/bootstrap/assets/js/date-time/moment.js"></script> --%>
<script src="${base}/js/jquery-ui-timepicker-addon.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.hotkeys.js"></script>
<script src="${base}/bootstrap/assets/js/bootstrap-wysiwyg.js"></script>
<script src="${base}/bootstrap/assets/js/select2.js"></script>
<script src="${base}/bootstrap/assets/js/fuelux/fuelux.spinner.js"></script>
<script src="${base}/bootstrap/assets/js/x-editable/bootstrap-editable.js"></script>
<script src="${base}/bootstrap/assets/js/x-editable/ace-editable.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.maskedinput.js"></script>
<script src="${base}/bootstrap/assets/js/fuelux/fuelux.wizard.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.validate.js"></script>
<!-- ace scripts -->
<script src="${base}/bootstrap/assets/js/ace/elements.scroller.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.colorpicker.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.fileinput.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.typeahead.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.wysiwyg.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.spinner.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.treeview.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.wizard.js"></script>
<script src="${base}/bootstrap/assets/js/ace/elements.aside.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.ajax-content.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.touch-drag.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.sidebar.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.submenu-hover.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.widget-box.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.settings.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.settings-rtl.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.settings-skin.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.widget-on-reload.js"></script>
<script src="${base}/bootstrap/assets/js/ace/ace.searchbox-autocomplete.js"></script>
<script src="${base}/js/jquery.ztree.core-3.5.js"></script>
<script src="${base}/js/jquery.ztree.excheck-3.5.js"></script>
<script src="${base}/js/jquery.ztree.exedit-3.5.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.dataTables.js"></script>
<script src="${base}/bootstrap/assets/js/jquery.dataTables.bootstrap.js"></script>

<script src="${base}/js/jquery.validate.min.js"></script>
<script src="${base}/js/jquery.metadata.js"></script>

<script src="${base}/js/jquery.cookie.js"></script>

<script src="${base}/bootstrap/assets/js/jquery.form.js"></script>
<script src="${base}/js/ajaxfileupload.js"></script>

<script src="${base}/js/messages_cn.js"></script>
<script src="${base}/js/mask.js" type="text/javascript"></script>
<script src="${base}/js/xcConfirm/xcConfirm.js" type="text/javascript"></script>

<script type="text/javascript" src="${base}/js/kindeditor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/js/kindeditor/lang/zh_CN.js"></script>

<script src="${base}/js/global.js"></script>
<script src="${base}/js/ztree.js"></script>

<script type="text/javascript">
    $(function () {
        setTimeout(function () {
            //$('#skin-colorpicker').find("option[value='#222A2D']").click();
            $('#skin-colorpicker').find("option[value='#222A2D']").change();
        }, 10);
    });

    $('#sidebar').find('ul li a').click(function () {
        if ($(this).attr("class") != "dropdown-toggle") {
            syncRequest("check", {}, function (data) {
                if (!data) {
                    window.location.reload();
                }
            });
            location.href = "#";
            location.href = "${base}/home#" + $(this).attr("role");
        }
    });

    function turnPage(url) {
        location.href = url;
    }
</script>
</body>
</html>
