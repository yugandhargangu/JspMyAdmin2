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
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jquery.js"></script>
</head>
<body>
	<div style="width: 100%; text-align: center; margin-top: 50px;">
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
				value="${requestScope.command.token}"><input type="hidden"
				name="redirect" value="/uninstall.html">
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
	<div class="group" style="width: 400px;">
		<div class="group-widget group-header">
			<h3>
				<m:print key="lbl.uninstall_configuration" />
			</h3>
		</div>
		<form action="${pageContext.request.contextPath}/uninstall.html"
			method="post" id="login-form" accept-charset="UTF-8">
			<input type="hidden" name="token"
				value="${requestScope.command.token}">
			<div class="group-widget group-content">
				<div style="color: red;">
					<m:print key="note.uninstall"/>
				</div>
				<div class="form-input">
					<label><m:print key="lbl.user" /> </label> <input type="text"
						name="admin_name" id="admin_name" class="form-control"
						maxlength="50" value="${requestScope.command.admin_name}">
				</div>
				<div class="form-input">
					<label><m:print key="lbl.password" /></label> <input
						type="password" name="admin_password" id="admin_password"
						class="form-control" maxlength="50">
				</div>
			</div>
			<div class="group-widget group-footer">
				<button type="button" class="btn" id="go_btn">
					<m:print key="lbl.run" />
				</button>
			</div>
		</form>
	</div>
	<div style="margin-left: auto;margin-right: auto;width: 400px;color:#ff8000;">
        <div><b><m:print key="note.manual_remove"/></b></div>
        <ol style="margin-left: 2em;">
            <li><m:print key="note.manual_remove1"/></li>
            <li><m:print key="note.manual_remove2"/></li>
            <li><m:print key="note.manual_remove3"/></li>
        </ol>
	</div>

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
	<m:store name="err_user_is_blank" key="err.user_is_blank" />
	<m:store name="msg_password_blank" key="msg.password_blank"/>
	<script type="text/javascript">
		$(function() {
			$('#go_btn').click(function() {
				var isValid = true;
				if ($('#admin_name').val().trim() == '') {
					$('#error-content').text('${err_user_is_blank}');
					$('#error-dialog').show();
					return;
				}
				if ($('#admin_password').val().trim() == '') {
					$('#error-content').text('${msg_password_blank}');
					$('#error-dialog').show();
					return;
				}
				if (isValid) {
					$('#login-form').submit();
				}
			});

			$('body').keypress(function(e) {
				if (e.which == 13) {
					$('#go_btn').click();
				}
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