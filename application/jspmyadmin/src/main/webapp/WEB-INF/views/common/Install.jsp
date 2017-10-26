<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<style type="text/css">
html {
	font-size: ${sessionScope.fontsize}%;
}
small {
	display: block;
	font-weight: normal;
	color: #ff8000;
}
</style>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<title><m:print key="title" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jquery.js"></script>
</head>
<body>
	<div style="width: 100%; text-align: center; margin-top: 20px;">
		<div style="width: 400px; margin-left: auto; margin-right: auto;">
			<img alt="Logo" id="site-logo"
				src="${pageContext.request.contextPath}/components/images/logo.png">
		</div>
	</div>
	<h2 align="center">
		<m:print key="lbl.welcome_to_jspmyadmin" />
	</h2>
	<div class="group" style="width: 400px;">
		<div class="group-widget group-header">
			<h3>
				<m:print key="lbl.language" />
			</h3>
		</div>
		<form action="${pageContext.request.contextPath}/language.html"
			id="language-form" method="post" accept-charset="UTF-8">
			<input type="hidden" name="token"
				value="${requestScope.command.token}"> <input type="hidden"
				name="redirect" value="/install.html">
			<div class="group-widget group-content">
				<select name="language" id="language" style="width: 90%;">
					<option value=""><m:print key="lbl.select_language" /></option>
					<jma:forLoop items="#language_map" name="language"
						key="languageKey" scope="command">
						<jma:switch name="#languageKey" scope="page">
							<jma:case value="#session_locale" scope="session">
								<option value="${languageKey}" selected="selected">${language}</option>
							</jma:case>
							<jma:default>
								<option value="${languageKey}">${language}</option>
							</jma:default>
						</jma:switch>
					</jma:forLoop>
				</select>
			</div>
		</form>
	</div>
	<form action="${pageContext.request.contextPath}/install.html"
		method="post" id="install-form" accept-charset="UTF-8">
		<input type="hidden" name="token"
			value="${requestScope.command.token}">
		<div class="group" style="width: 800px;">
			<div class="group-widget group-header">
				<h3>
					<m:print key="msg.configuration" />
				</h3>
			</div>
			<div class="group-widget group-content">
				<div id="install-part1">
					<div class="form-input">
						<label><m:print key="msg.jspmyadmin_administrator_config" />:</label>
						<br> <label><m:print key="msg.administrator_username" /></label>
						<small>(<m:print key="msg.administrator_username_alert" />)
						</small> <input type="text" name="admin_name" id="admin_name"
							placeholder="Username" class="form-control" maxlength="20"
							value="${requestScope.command.admin_name}">
					</div>
					<div class="form-input">
						<label><m:print key="msg.administrator_password" /> </label> <input
							type="password" placeholder="Password" name="admin_password"
							id="admin_password" class="form-control" maxlength="50"
							value="${requestScope.command.admin_password}">
					</div>
					<div class="form-input">
						<label><m:print key="msg.reenter_password" /></label> <input
							type="password" placeholder="Password" id="reenter_password"
							class="form-control" maxlength="50">
					</div>
				</div>
				<div id="install-part2">
					<div class="form-input">
						<label><m:print key="msg.jspmyadmin_configuration_type" />:</label>
						<br> <label><input checked="checked" type="radio"
							name="config_type" value="config"> <m:print
								key="msg.fixed_configuration" /> <small>(<m:print
									key="msg.fixed_configuration_alert" />)
						</small></label> <br> <label><input type="radio" name="config_type"
							value="half_config"> <m:print
								key="msg.half_fixed_configuration" /> <small>(<m:print
									key="msg.half_fixed_configuration_alert" />)
						</small></label> <br> <label><input type="radio" name="config_type"
							value="login"> <m:print key="msg.login_configuration" />
							<small>(<m:print key="msg.login_configuration_alert" />)
						</small></label>
					</div>
					<div class="form-input" id="host-div">
						<label><m:print key="lbl.hostname" /> </label> <input type="text"
							name="config_host" id="config_host" placeholder="Host Name"
							class="form-control" maxlength="100"
							value="${requestScope.command.config_host}">
					</div>
					<div class="form-input" id="port-div">
						<label><m:print key="lbl.port" /> </label> <input type="text"
							placeholder="Port Number" name="config_port" id="config_port"
							class="form-control" maxlength="10"
							value="${requestScope.command.config_port}">
					</div>
					<div class="form-input" id="mysql-user-div">
						<label><m:print key="lbl.mysql_user" /> </label> <input
							type="text" placeholder="User" name="config_username"
							id="config_username" class="form-control" maxlength="100"
							value="${requestScope.command.config_username}">
					</div>
					<div class="form-input" id="mysql-pass-div">
						<label><m:print key="lbl.mysql_password" /> <small>(<m:print
									key="msg.blank_password_alert" />)
						</small></label> <input type="password" placeholder="Password"
							name="config_password" id="config_password" class="form-control"
							maxlength="100" value="${requestScope.command.config_password}">
					</div>
				</div>
			</div>
			<div class="group-widget group-footer">
				<button type="button" class="btn" id="go_btn">
					<m:print key="lbl.finish" />
				</button>
			</div>
		</div>
	</form>
	<jma:notEmpty name="#err_key" scope="command">
		<div class="dialog">
			<div class="dialog-widget dialog-error">
				<div class="close" id="error-close1">&#10005;</div>
				<div class="dialog-header">
					<m:print key="lbl.errors" />
				</div>
				<div class="dialog-content">
					<p>
						<m:print key="err_key" scope="command" />
					</p>
				</div>
			</div>
		</div>
	</jma:notEmpty>
	<div class="dialog" id="error-dialog" style="display: none;">
		<div class="dialog-widget dialog-error">
			<div class="close" id="error-close">&#10005;</div>
			<div class="dialog-header">
				<m:print key="lbl.errors" />
			</div>
			<div class="dialog-content">
				<p id="error-content"></p>
			</div>
		</div>
	</div>
	<m:store name="msg_admin_username_blank" key="msg.admin_username_blank"/>
	<m:store name="msg_admin_password_blank" key="msg.admin_password_blank"/>
	<m:store name="msg_admin_reenter_blank" key="msg.admin_reenter_blank"/>
	<m:store name="msg_admin_password_not_match" key="msg.admin_password_not_match"/>
	<m:store name="msg_admin_select_config" key="msg.admin_select_config"/>
	<m:store name="err_hostname_is_blank" key="err.hostname_is_blank" />
	<m:store name="err_port_is_blank" key="err.port_is_blank" />
	<m:store name="err_user_is_blank" key="err.user_is_blank" />
	<script type="text/javascript">
		$(function() {

			$('body').keypress(function(e) {
				if (e.which == 13) {
					$('#go_btn').click();
				}
			});

			$('input[name="config_type"]').change(function() {
				if ($(this).val() == 'config') {
					$('#host-div').show();
					$('#port-div').show();
					$('#mysql-user-div').show();
					$('#mysql-pass-div').show();
				} else if ($(this).val() == 'half_config') {
					$('#host-div').show();
					$('#port-div').show();
					$('#mysql-user-div').hide();
					$('#mysql-pass-div').hide();
				} else {
					$('#host-div').hide();
					$('#port-div').hide();
					$('#mysql-user-div').hide();
					$('#mysql-pass-div').hide();
				}
			});

			$('#go_btn').click(function() {
				if ($('#admin_name').val() == '') {
					$('#error-content').text('${msg_admin_username_blank}');
					$('#error-dialog').show();
					return;
				}
				if ($('#admin_password').val() == '') {
					$('#error-content').text('${msg_admin_password_blank}');
					$('#error-dialog').show();
					return;
				}
				if ($('#reenter_password').val() == '') {
					$('#error-content').text('${msg_admin_reenter_blank}');
					$('#error-dialog').show();
					return;
				}
				if ($('#admin_password').val() != $('#reenter_password').val()) {
					$('#error-content').text('${msg_admin_password_not_match}');
					$('#error-dialog').show();
					return;
				}
				if ($('input[name="config_type"]:checked').val() == '') {
					$('#error-content').text('${msg_admin_select_config}');
					$('#error-dialog').show();
					return;
				}

				if ($('input[name="config_type"]:checked').val() == 'config' || $('input[name="config_type"]:checked').val() == 'half_config') {
					if ($('#config_host').val() == '') {
						$('#error-content').text('${err_hostname_is_blank}');
						$('#error-dialog').show();
						return;
					}
					if ($('#config_port').val() == '') {
						$('#error-content').text('${err_port_is_blank}');
						$('#error-dialog').show();
						return;
					}
				}
				if ($('input[name="config_type"]:checked').val() == 'config') {
					if ($('#config_username').val() == '') {
						$('#error-content').text('${err_user_is_blank}');
						$('#error-dialog').show();
						return;
					}
				}
				$('#install-form').submit();
			});

			$('#error-close').click(function() {
				$('#error-dialog').hide();
			});
			$('#language').change(function() {
				$('#language-form').submit();
			});
			$('#error-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});
		});
	</script>
</body>
</html>