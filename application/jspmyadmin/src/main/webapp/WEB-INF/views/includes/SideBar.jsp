<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div style="padding: 1em 0.5em;">
	<div style="width: 100%; text-align: center;">
		<img alt="Logo" id="site-logo"
			src="${pageContext.request.contextPath}/components/images/logo.png">
	</div>
	<div style="width: 100%; text-align: center; margin-top: 0.5em;">
		<div
			style="padding-left: 0.5em; padding-right: 0.5em; display: inline-block;">
			<a href="${pageContext.request.contextPath}/home.html"><img
				alt="Home" class="icon-l"
				src="${pageContext.request.contextPath}/components/icons/home.png"></a>
		</div>
		<div
			style="padding-left: 0.5em; padding-right: 0.5em; display: inline-block;">
			<a href="http://jspmyadmin.com"><img class="icon-l"
				alt="JspMyAdmin documentation" title="JspMyAdmin documentation"
				src="${pageContext.request.contextPath}/components/icons/report-g.png"></a>
		</div>
		<div
			style="padding-left: 0.5em; padding-right: 0.5em; display: inline-block;">
			<a href="https://dev.mysql.com/doc/" target="_blank"><img
				class="icon-l" alt="MySql documentation" title="MySql documentation"
				src="${pageContext.request.contextPath}/components/icons/mysql.png"></a>
		</div>
		<div
			style="padding-left: 0.5em; padding-right: 0.5em; display: inline-block;">
			<a href="javaScript:menubarMain();"> <img alt="Refresh"
				class="icon-l" title="Refresh"
				src="${pageContext.request.contextPath}/components/icons/reload.png"></a>
		</div>
	</div>
	<div style="margin-left: 1em;" id="menubar-main"></div>
</div>

<div class="dialog" id="sidebar-success-dialog" style="display: none;">
	<div class="dialog-widget dialog-success">
		<div class="close" id="sidebar-success-close">&#10005;</div>
		<div class="dialog-header">
			<m:print key="lbl.message" />
		</div>
		<div class="dialog-content">
			<p id="sidebar-success-msg"></p>
		</div>
	</div>
</div>

<div class="dialog" id="sidebar-error-dialog" style="display: none;">
	<div class="dialog-widget dialog-error">
		<div class="close" id="sidebar-error-close">&#10005;</div>
		<div class="dialog-header">
			<m:print key="lbl.errors" />
		</div>
		<div class="dialog-content">
			<p id="sidebar-error-msg"></p>
		</div>
	</div>
</div>

<div class="dialog" id="sidebar-loading-dialog">
	<div class="dialog-widget dialog-normal">
		<div class="dialog-header">&nbsp;</div>
		<div class="dialog-content">
			<p>Loading...</p>
		</div>
	</div>
</div>