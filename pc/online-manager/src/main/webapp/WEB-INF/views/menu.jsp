<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="./common/jstl_taglib.jsp"%>

<div id="sidebar" class="sidebar responsive">
	<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>
	
	<!-- /.sidebar-shortcuts -->
	<!-- <div><h1 style="color:white;text-align: center">LOGO</h1></div> -->

	<ul class="nav nav-list">

			<div href="javascript:void(-1)" style="padding:2px 2px 0px 5px">
			<span><img src="${contextPath}/images/logo.png" alt="" style="margin-left:7px;width: 80;"> </span></div>

		<li id="homeLi" >
			<a href="javascript:void(-1)" role="welcome">
			<i class="menu-icon fa fa-tachometer"></i> <span class="menu-text">首页 </span></a> <b class="arrow"></b>
		</li>
		
		<c:forEach var="menu" items="${menus}" varStatus="status">
			<li class="">
				<a href="#" class="dropdown-toggle" title="${menu.name}">
					<i class="menu-icon fa ${menu.icon}"></i> 
					<span class="menu-text">${menu.name}</span> 
					<b class="arrow fa fa-angle-down"></b>
				</a> 
				<b class="arrow"></b>
				<c:if test="${menu.children != null && fn:length(menu.children) > 0}">
					<ul class="submenu">
						<c:forEach var="subMenu" items="${menu.children}" varStatus="status">
							<li>
								<a href="javascript:void(-1)" role="${subMenu.url}"> 
									<i class="menu-icon fa fa-caret-right"></i> ${subMenu.name}
							    </a> 
							    <b class="arrow"></b>
							</li>
						</c:forEach>
					</ul>
				</c:if>
			</li>
		</c:forEach>
	</ul>



	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left"
			data-icon1="ace-icon fa fa-angle-double-left"
			data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
	<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
</div>