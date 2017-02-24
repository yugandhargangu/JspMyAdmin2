<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<jsp:include page="../includes/FontSize.jsp" />
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/components/images/favicon.ico">
<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/components/images/favicon.ico">
<title><m:print key="title" /></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">
<style type="text/css">
html {
	overflow: auto;
}
</style>
</head>
<body ng-app="JspMyAdmin">
	<div style="width: 100%; text-align: center; margin-top: 50px;">
		<div style="width: 400px; margin-left: auto; margin-right: auto;">
			<img alt="Logo" id="site-logo" src="${pageContext.request.contextPath}/components/images/logo.png">
		</div>
	</div>
	<h2 align="center">
		<m:print key="lbl.welcome_to_jspmyadmin" />
	</h2>
	<div class="group" style="width: 400px;" ng-controller="LanguageController as ctrl">
		<div class="group-widget group-header">
			<h3>
				<m:print key="lbl.language" />
			</h3>
		</div>
		<form action="#" name="languageForm" id="language-form" method="post" accept-charset="UTF-8">
			<div class="group-widget group-content">
				<select name="language" id="language" style="width: 90%;" ng-model="ctrl.languageForm.language" ng-change="ctrl.changeLanguage()">
					<option value=""><m:print key="lbl.select_language" /></option>
					<option ng-repeat="(key, value) in ctrl.language_map" value="{{key}}">{{value}}</option>
				</select>
			</div>
		</form>
	</div>
	<div class="group" style="width: 400px;" ng-controller="LoginController as ctrl">
		<div class="group-widget group-header">
			<h3>
				<m:print key="lbl.login" />
			</h3>
		</div>
		<form action="#" method="post" name="login_form" id="login-form" accept-charset="UTF-8">
			<div class="group-widget group-content">
				<div class="form-input" ng-show="!ctrl.half_config">
					<label><m:print key="lbl.hostname" /></label> <input type="text" name="hostname" class="form-control" maxlength="50"
						ng-model="ctrl.loginForm.hostname" ng-required="!ctrl.half_config">
					<div class="valid-err" ng-show="ctrl.showError && login_form.hostname.$error.required">
						<m:print key="err.hostname_is_blank" />
					</div>
				</div>
				<div class="form-input" ng-show="!ctrl.half_config">
					<label><m:print key="lbl.port" /></label> <input type="text" name="portnumber" class="form-control" maxlength="50"
						ng-model="ctrl.loginForm.portnumber" ng-required="!ctrl.half_config">
					<div class="valid-err" ng-show="ctrl.showError && login_form.portnumber.$error.required">
						<m:print key="err.port_is_blank" />
					</div>
				</div>
				<div class="form-input">
					<label><m:print key="lbl.user" /></label> <input type="text" name="username" class="form-control" maxlength="50"
						ng-model="ctrl.loginForm.username" ng-required="true">
					<div class="valid-err" ng-show="ctrl.showError && login_form.username.$error.required">
						<m:print key="err.user_is_blank" />
					</div>
				</div>
				<div class="form-input">
					<label><m:print key="lbl.password" /></label> <input type="password" name="password" maxlength="50" class="form-control"
						ng-model="ctrl.loginForm.password">
				</div>
			</div>
			<div class="group-widget group-footer">
				<button type="button" class="btn" id="go_btn" ng-click="ctrl.submitLoginForm(login_form.$valid)">
					<m:print key="lbl.run" />
				</button>
			</div>
		</form>
	</div>
	<jsp:include page="../includes/ErrorDialog.jsp" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/components/jma/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/components/angular/angular.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/components/angular/angular-resource.min.js"></script>

	<script type="text/javascript">
		var contextPath = '${pageContext.request.contextPath}';
		var token = '${command.token}';
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/app/main.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/app/common/login.js"></script>
</body>
</html>