<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../includes/Head.jsp" />
<style type="text/css">
.gap-item {
	margin-right: 2em;
	display: inline-block;
}

#footer {
	margin-top: 0px;
}

.group-spl .group-normal {
	display: none;
}

.icon-more {
	float: right;
	margin-right: 1em;
}
</style>
</head>
<body>
	<div id="content">
		<div id="sidebar" style="width: 20%;">
			<jsp:include page="../../includes/SideBar.jsp" />
		</div>
		<div id="main-content" style="width: calc(80% - 3px);">
			<div id="topbar">
				<jsp:include page="../../includes/TopBar.jsp" />
			</div>
			<div id="header-div"><jsp:include page="../Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.1em 0.2em;">
					<div class="page-head">
						<h3>
							User and Privileges
							<jma:empty name="#error" scope="command">
								<button type="button" class="btn" style="float: right;"
									onclick="callUserInfo('');">Add User</button>
							</jma:empty>
						</h3>

					</div>

					<jma:notEmpty name="#error" scope="command">
						<div class="group">
							<div class="group-widget group-header">MySql Error:</div>
							<div class="group-widget group-normal">
								<p style="color: red;">The account you are currently using
									does not have sufficient privileges to make changes to MySql
									users and privileges.</p>
							</div>
						</div>
					</jma:notEmpty>

					<jma:notEmpty name="#user_info_list" scope="command">
						<jma:forLoop items="#user_info_list" name="userInfo"
							scope="command">
							<div class="group group-spl">
								<div class="group-widget group-header">${userInfo.user}
									<img alt="Show More" title="Show More"
										class="icon icon-more show-more"
										src="${pageContext.request.contextPath}/components/icons/arrow-down.png">
								</div>
								<div class="group-widget group-normal">
									<div style="display: inline-block;">
										<table class="tbl">
											<thead>
												<tr>
													<th>Item</th>
													<th>value</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>Max Questions</td>
													<td>${userInfo.max_questions}</td>
												</tr>
												<tr>
													<td>Max Updates</td>
													<td>${userInfo.max_updates}</td>
												</tr>
												<tr>
													<td>Max Connections</td>
													<td>${userInfo.max_connections}</td>
												</tr>
												<tr>
													<td>Max User Connections</td>
													<td>${userInfo.max_user_connections}</td>
												</tr>
												<tr>
													<td>Plugin</td>
													<td>${userInfo.plugin}</td>
												</tr>
											</tbody>
										</table>
									</div>
									<jma:notEmpty name="#userInfo.grant_list" scope="page">
										<div style="display: inline-block; vertical-align: top;">
											<table class="tbl">
												<thead>
													<tr>
														<th>Grants for ${userInfo.user}</th>
													</tr>
												</thead>
												<tbody>
													<jma:forLoop items="#userInfo.grant_list" name="grant_item"
														scope="page">
														<tr>
															<td>${grant_item}</td>
														</tr>
													</jma:forLoop>
												</tbody>
											</table>
										</div>
									</jma:notEmpty>
								</div>
								<div class="group-widget group-footer">
									<button type="button" class="btn"
										onclick="callSchemaPrivileges('${userInfo.token}');">Schema
										Privileges</button>
									<button type="button" class="btn"
										onclick="callGlobalPrivileges('${userInfo.token}');s">Global
										Privileges</button>
									<button type="button" class="btn"
										onclick="callUserInfo('${userInfo.token}');">Alter
										User</button>
									<button type="button" class="btn"
										onclick="callDropUser('${userInfo.token}');">Drop
										User</button>
								</div>
							</div>
						</jma:forLoop>
					</jma:notEmpty>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>
	<jma:notEmpty name="#err" scope="command">
		<div class="dialog">
			<div class="dialog-widget dialog-error">
				<div class="close" id="error-close2">&#10005;</div>
				<div class="dialog-header">
					<m:print key="lbl.errors" />
				</div>
				<div class="dialog-content">
					<p>${requestScope.command.err}</p>
				</div>
			</div>
		</div>
	</jma:notEmpty>
	<jma:notEmpty name="#err_key" scope="command">
		<div class="dialog">
			<div class="dialog-widget dialog-error">
				<div class="close" id="error-close1">&#10005;</div>
				<div class="dialog-header">
					<m:print key="lbl.success" />
				</div>
				<div class="dialog-content">
					<p>
						<m:print key="err_key" scope="command" />
					</p>
				</div>
			</div>
		</div>
	</jma:notEmpty>
	<jma:notEmpty name="#msg_key" scope="command">
		<div class="dialog">
			<div class="dialog-widget dialog-success">
				<div class="close" id="msg-close1">&#10005;</div>
				<div class="dialog-header">
					<m:print key="lbl.success" />
				</div>
				<div class="dialog-content">
					<p>
						<m:print key="msg_key" scope="command" />
					</p>
				</div>
			</div>
		</div>
	</jma:notEmpty>
	<div class="dialog" id="confirm-dialog" style="display: none;">
		<div class="dialog-widget dialog-normal">
			<div class="close" id="confirm-close">&#10005;</div>
			<div class="dialog-header">
				<m:print key="lbl.alert" />
			</div>
			<div class="dialog-content">
				<p id="confirm-content">You are about drop user. Are you really
					want drop user?</p>
			</div>
			<div class="dialog-footer">
				<button type="button" class="btn" id="yes_btn">
					<m:print key="btn.yes" />
				</button>
				<button type="button" class="btn" id="no_btn">
					<m:print key="btn.no" />
				</button>
			</div>
		</div>
	</div>
	<div style="display: none;">
		<form
			action="${pageContext.request.contextPath}/server_user_drop.html"
			method="get" id="drop-form">
			<input type="hidden" name="token" id="drop-token">
		</form>
		<form
			action="${pageContext.request.contextPath}/server_global_privileges.html"
			method="get" id="global-p-form">
			<input type="hidden" name="token" id="global-p-token">
		</form>
		<form
			action="${pageContext.request.contextPath}/server_schema_privileges.html"
			method="get" id="schema-p-form">
			<input type="hidden" name="token" id="schema-p-token">
		</form>
		<form
			action="${pageContext.request.contextPath}/server_user_info.html"
			method="get" id="info-p-form">
			<input type="hidden" name="token" id="info-p-token">
		</form>
	</div>
	<script type="text/javascript">
		$("#header-menu li:nth-child(4)").addClass('active');
		applyEvenOdd('.tbl');

		function callDropUser(token) {
			$('#drop-token').val(token);
			$('#confirm-dialog').show();
		}

		function callGlobalPrivileges(token) {
			showWaiting();
			$('#global-p-token').val(token);
			$('#global-p-form').submit();
		}

		function callSchemaPrivileges(token) {
			showWaiting();
			$('#schema-p-token').val(token);
			$('#schema-p-form').submit();
		}

		function callUserInfo(token) {
			showWaiting();
			$('#info-p-token').val(token);
			$('#info-p-form').submit();
		}

		$(function() {

			$('.icon-more').click(function() {
				if ($(this).hasClass('show-more')) {
					$(this).parent().parent().find('.group-normal').show();
					$(this).removeClass('show-more');
					$(this).addClass('show-less');
					$(this).prop('src', Server.root + '/components/icons/arrow-up.png');
				} else {
					$(this).parent().parent().find('.group-normal').hide();
					$(this).removeClass('show-less');
					$(this).addClass('show-more');
					$(this).prop('src', Server.root + '/components/icons/arrow-down.png');
				}
			});

			$('#error-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#msg-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});
			$('#yes_btn').click(function() {
				showWaiting();
				$('#drop-form').submit();
			});
			$('#no_btn,#confirm-dialog').click(function() {
				$('#drop-token').val('');
				$('#confirm-dialog').hide();
			});
		});
	</script>
</body>
</html>