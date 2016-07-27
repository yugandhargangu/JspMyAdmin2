<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../includes/Head.jsp" />
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
			<div id="header-div"><jsp:include
					page="../../server/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.global_privileges" />
							<button type="button" class="btn" id="btn-back"
								style="float: right;">
								<m:print key="lbl.go_back" />
							</button>
						</h3>
					</div>
					<form method="post" accept-charset="UTF-8" id="privilege-form"
						action="${pageContext.request.contextPath}/server_global_privileges.html">
						<input type="hidden" name="token"
							value="${requestScope.command.token}"> <input
							id="revoke-all" type="hidden" name="revoke_all" value="">
						<input type="hidden" name="user"
							value="${requestScope.command.user}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.privileges" />
								(${requestScope.command.user})
							</div>
							<div class="group-widget group-content">
								<table class="tbl" id="privilege-list">
									<thead>
										<tr>
											<th><input type="checkbox" id="check-all"></th>
											<th><m:print key="lbl.privilege" /></th>
											<th><m:print key="lbl.context" /></th>
											<th><m:print key="lbl.comment" /></th>
										</tr>
									</thead>
									<tbody>
										<jma:notEmpty name="#privilege_info_list" scope="command">
											<jma:forLoop items="#privilege_info_list"
												name="privilegeInfo" scope="command">
												<tr>
													<td><jma:switch name="#privilegeInfo.value"
															scope="page">
															<jma:case value="1">
																<input type="checkbox" name="privileges"
																	checked="checked" value="${privilegeInfo.privilege}">
															</jma:case>
															<jma:default>
																<input type="checkbox" name="privileges"
																	value="${privilegeInfo.privilege}">
															</jma:default>
														</jma:switch></td>
													<td>${privilegeInfo.privilege}</td>
													<td>${privilegeInfo.context}</td>
													<td>${privilegeInfo.comment}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#privilege_info_list" scope="command">
											<tr class="even">
												<td colspan="4"><m:print key="msg.no_privileges_found" />
												</td>
											</tr>
										</jma:empty>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-revoke-all">
									<m:print key="lbl.revoke_all_privileges" />
								</button>
								<button type="button" class="btn" id="btn-run">
									<m:print key="lbl.run" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>
	<div class="dialog" id="confirm-dialog" style="display: none;">
		<div class="dialog-widget dialog-normal">
			<div class="close" id="confirm-close">&#10005;</div>
			<div class="dialog-header">
				<m:print key="lbl.alert" />
			</div>
			<div class="dialog-content">
				<p id="confirm-content">
					<m:print key="msg.revoke_all_alert" />
				</p>
			</div>
			<div class="dialog-footer">
				<button type="button" class="btn" id="yes_btn">
					<m:print key="lbl.ok" />
				</button>
				<button type="button" class="btn" id="no_btn">
					<m:print key="lbl.cancel" />
				</button>
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
	<form action="${pageContext.request.contextPath}/server_users.html"
		method="get" id="back-form"></form>
	<script type="text/javascript">
		$("#header-menu li:nth-child(4)").addClass('active');
		applyEvenOdd('#privilege-list');

		function checkForCheckbox() {
			var length1 = $('input[name="privileges"]').length;
			var length2 = $('input[name="privileges"]:checked').length;
			if (length1 == length2) {
				$('#check-all').prop('checked', true);
			} else if (length2 == 0) {
				$('#check-all').prop('checked', false);
			} else {
				$('#check-all').prop('indeterminate', true);
			}
		}

		$(function() {

			checkForCheckbox();
			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#error-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#yes_btn').click(function() {
				$('#revoke-all').val('1');
				$('#privilege-form').submit();
			});

			$('input[name="privileges"]').change(function() {
				checkForCheckbox();
			});

			$('#check-all').change(function() {
				$('input[name="privileges"]').prop('checked', $(this).prop("checked"));
			});

			$('#confirm-close').click(function() {
				$('#confirm-dialog').hide();
			});

			$('#no_btn').click(function() {
				$('#confirm-dialog').hide();
			});

			$('#btn-revoke-all').click(function() {
				$('#confirm-dialog').show();
			});
			$('#btn-run').click(function() {
				$('#revoke-all').val('');
				$('#privilege-form').submit();
			});
			$('#btn-back').click(function() {
				showWaiting();
				$('#back-form').submit();
			});
		});
	</script>
</body>
</html>
