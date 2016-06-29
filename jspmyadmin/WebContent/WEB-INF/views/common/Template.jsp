<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../includes/Head.jsp" />
</head>
<body>
	<div id="content">
		<div id="sidebar" style="width: 20%;">
			<jsp:include page="../includes/SideBar.jsp" />
		</div>
		<div id="main-content" style="width: calc(80% - 3px);">
			<div id="topbar">
				<jsp:include page="../includes/TopBar.jsp" />
			</div>
			<div id="header-div"><jsp:include page="../server/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>Tables</h3>
					</div>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../includes/Footer.jsp" />
			</div>
		</div>

	</div>
</body>
</html>
<div class="dialog" style="display: none;">
	<div class="dialog-widget dialog-normal">
		<div class="close">&#10005;</div>
		<div class="dialog-header">assadsad</div>
		<div class="dialog-content">
			fsafsafsa<br>fsafsafsa<br>fsafsafsa<br>fsafsafsa<br>fsafsafsa<br>fsafsafsa<br>fsafsafsa<br>
		</div>
		<div class="dialog-footer">
			<button type="button" class="btn">Go</button>
		</div>
	</div>
</div>
<div class="group">
	<div class="group-widget group-header">dcsadsadsad</div>
	<div class="group-widget group-content">dasdsaads</div>
	<div class="group-widget group-footer">asdfsdf</div>
</div>
<li class="active"></li>