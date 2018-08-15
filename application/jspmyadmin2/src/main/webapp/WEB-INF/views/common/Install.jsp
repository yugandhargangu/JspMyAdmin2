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
<style type="text/css">
small {
	display: block;
	font-weight: normal;
	color: #ff8000;
}
</style>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/components/images/favicon.ico">
<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/components/images/favicon.ico">
<title><m:print key="title" /></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">
<style type="text/css">
html {
	overflow: auto;
}

#install-part1 {
	display: inline-block;
	float: left;
	width: 380px;
	vertical-align: top;
}

#install-part2 {
	display: inline-block;
	float: right;
	width: 380px;
	vertical-align: top;
}
</style>
</head>
<body ng-app="JspMyAdmin">
	<div style="width: 100%; text-align: center; margin-top: 20px;">
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
	<div class="group" style="width: 800px;" ng-controller="InstallController as ctrl">
		<form action="#" method="post" name="install_form" id="install-form" accept-charset="UTF-8" novalidate="novalidate">
			<div class="group-widget group-header">
				<h3>
					<m:print key="msg.configuration" />
				</h3>
			</div>
			<div class="group-widget group-content">
				<div id="install-part1">
					<div class="form-input">
						<label><m:print key="msg.jspmyadmin_administrator_config" />:</label> <br> <label><m:print key="msg.administrator_username" /></label> <small>(<m:print
								key="msg.administrator_username_alert" />)
						</small> <input type="text" name="admin_name" id="admin_name" placeholder="Username" class="form-control" maxlength="20"
							ng-model="ctrl.installForm.admin_name" ng-required="true">
						<div class="valid-err" ng-show="ctrl.showError && install_form.admin_name.$invalid">
							<m:print key="msg.admin_username_blank" />
						</div>
					</div>
					<div class="form-input">
						<label><m:print key="msg.administrator_password" /> </label> <input type="password" placeholder="Password" name="admin_password"
							id="admin_password" class="form-control" maxlength="50" ng-model="ctrl.installForm.admin_password" ng-required="true">
						<div class="valid-err" ng-show="ctrl.showError && install_form.admin_password.$invalid">
							<m:print key="msg.admin_password_blank" />
						</div>
					</div>
					<div class="form-input">
						<label><m:print key="msg.reenter_password" /></label> <input type="password" placeholder="Password" id="reenter_password" class="form-control"
							name="reenter_password" maxlength="50" ng-model="ctrl.installForm.reenter_password" ng-required="true"
							pw-match="{{ctrl.installForm.admin_password}}">
						<div class="valid-err" ng-show="ctrl.showError && install_form.reenter_password.$error.required">
							<m:print key="msg.admin_reenter_blank" />
						</div>
						<div class="valid-err"
							ng-show="ctrl.showError && !install_form.reenter_password.$error.required && install_form.reenter_password.$error.pwmatch">
							<m:print key="msg.admin_password_not_match" />
						</div>
					</div>
				</div>
				<div id="install-part2">
					<div class="form-input" ng-init="ctrl.installForm.config_type='config'">
						<label><m:print key="msg.jspmyadmin_configuration_type" />:</label>
						<div class="valid-err" ng-show="ctrl.showError && install_form.config_type.$invalid">
							<m:print key="msg.admin_select_config" />
						</div>
						<br> <label><input type="radio" name="config_type" value="config" ng-model="ctrl.installForm.config_type" ng-required="true"
							ng-change="ctrl.configChange()"> <m:print key="msg.fixed_configuration" /> <small>(<m:print
									key="msg.fixed_configuration_alert" />)
						</small></label> <br> <label><input type="radio" name="config_type" value="half_config" ng-model="ctrl.installForm.config_type" ng-required="true"
							ng-change="ctrl.configChange()"> <m:print key="msg.half_fixed_configuration" /> <small>(<m:print
									key="msg.half_fixed_configuration_alert" />)
						</small></label> <br> <label><input type="radio" name="config_type" value="login" ng-model="ctrl.installForm.config_type" ng-required="true"
							ng-change="ctrl.configChange()"> <m:print key="msg.login_configuration" /> <small>(<m:print
									key="msg.login_configuration_alert" />)
						</small></label>
					</div>
					<div class="form-input" id="host-div" ng-show="ctrl.validation.host">
						<label><m:print key="lbl.hostname" /> </label> <input type="text" name="config_host" id="config_host" placeholder="Host Name"
							class="form-control" maxlength="50" ng-model="ctrl.installForm.config_host" ng-required="ctrl.validation.host">
						<div class="valid-err" ng-show="ctrl.showError && ctrl.validation.host && install_form.config_host.$invalid">
							<m:print key="err.hostname_is_blank" />
						</div>
					</div>
					<div class="form-input" id="port-div" ng-show="ctrl.validation.port">
						<label><m:print key="lbl.port" /> </label> <input type="text" placeholder="Port Number" name="config_port" id="config_port"
							class="form-control" maxlength="10" ng-model="ctrl.installForm.config_port" ng-required="ctrl.validation.port">
						<div class="valid-err" ng-show="ctrl.showError && ctrl.validation.port && install_form.config_port.$invalid">
							<m:print key="err.port_is_blank" />
						</div>
					</div>
					<div class="form-input" id="mysql-user-div" ng-show="ctrl.validation.user">
						<label><m:print key="lbl.mysql_user" /> </label> <input type="text" placeholder="User" name="config_username" id="config_username"
							class="form-control" maxlength="50" ng-model="ctrl.installForm.config_username" ng-required="ctrl.validation.user">
						<div class="valid-err" ng-show="ctrl.showError && ctrl.validation.port && install_form.config_port.$invalid">
							<m:print key="err.user_is_blank" />
						</div>
					</div>
					<div class="form-input" id="mysql-pass-div" ng-show="ctrl.validation.user">
						<label><m:print key="lbl.mysql_password" /> <small>(<m:print key="msg.blank_password_alert" />)
						</small></label> <input type="password" placeholder="Password" name="config_password" id="config_password" class="form-control" maxlength="50"
							ng-model="ctrl.installForm.config_password">
					</div>
				</div>
			</div>
			<div class="group-widget group-footer">
				<button type="button" class="btn" id="go_btn" ng-click="ctrl.submitInstallForm(install_form.$valid)">
					<m:print key="lbl.finish" />
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/app/utils.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/app/common/install.js"></script>
</body>
</html>