<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    $(document).ready(function () {
//        initUserModule();
        initListModule();
    })

    var searchCase;
    var questionTable;

//    function initUserModule() {
//        ajaxRequest("message/user", null, function (data) {
//            $("#message_user").html(data);
//        });
//    }

    function initListModule() {
        ajaxRequest("message/list", null, function (data) {
            $("#message_list").html(data);
        });
    }

</script>

<div class="page-header">
	当前位置：消息管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 系统消息 </span>
</div>

    <!-- 右侧内容开始 -->
    <%--<div id="message_user"></div>--%>
	<!-- 右侧内容开始 -->
	<div id="message_list"></div>

