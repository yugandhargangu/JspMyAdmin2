<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<m:open />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/components/images/favicon.ico">
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/components/images/favicon.ico">
        <title><m:print key="title" /></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/components/codemirror/lib/codemirror.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/components/codemirror/addon/hint/show-hint.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">
        <style type="text/css">
            html {
	            font-size: ${sessionScope.fontsize}%;
            }
        </style>
    </head>
    <body ng-app="JspMyAdminApp">
	    <div id="content">
    		<div id="sidebar" style="width: 20%;" ng-controller="SideBarController as ctrl">
    			<jsp:include page="./includes/SideBar.jsp" />
    		</div>
    		<div id="main-content" style="width: 80%;">
    			<div id="topbar" ng-controller="TopBarController as ctrl">
    				<jsp:include page="./includes/TopBar.jsp" />
    			</div>
    			<div id="header-div" ng-controller="HeaderController as ctrl">
    			    <div id="header-menu-container">
                	    <ul id="header-menu">
    			            <jsp:include page="./server/Header.jsp" />
    			            <jsp:include page="./database/Header.jsp" />
    			            <jsp:include page="./table/Header.jsp" />
    			            <jsp:include page="./view/Header.jsp" />
    			        </ul>
    			    </div>
    			</div>
    			<div id="main-body">
    			    <div style="padding: 0.2em 2em;" ui-view="">
    			    <!-- Data will render here-->
    			    </div>
    			</div>
    		    <div id="footer">
            	    <div style="padding: 0.2em 2em;">
                	    <m:print key="footer" />
                    </div>
                </div>
    	    </div>
    	</div>
	    <script type="text/javascript">
    	    var contextPath = '${pageContext.request.contextPath}';
    	    var aaaaaaaa = '${sessionScope.session_key}';
    	</script>
    	<script type="text/javascript" src="${pageContext.request.contextPath}/components/jma/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/components/jma/aes.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/components/jma/base64.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/components/codemirror/lib/codemirror.js"></script>
        <script type="text/javascript" 	src="${pageContext.request.contextPath}/components/codemirror/sql.js"></script>
        <script type="text/javascript" 	src="${pageContext.request.contextPath}/components/codemirror/addon/hint/show-hint.js"></script>
        <script type="text/javascript" 	src="${pageContext.request.contextPath}/components/codemirror/addon/hint/sql-hint.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/components/angular/angular.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/components/angular/angular-resource.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/components/angular/angular-ui-router.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/app/utils.js"></script>
        <script type="text/javascript"	src="${pageContext.request.contextPath}/app/config.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/app/common/home.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/app/server/databases.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/app/server/common.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/app/database/structure.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/app/database/create_table.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/app/database/create_view.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/app/database/routines.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/app/database/create_routine.js"></script>
    </body>
</html>