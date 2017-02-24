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
			<div id="header-div"><jsp:include page="../../table/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.foreign_keys" />
						</h3>
					</div>

					<form action="${pageContext.request.contextPath}/table_fk_add.html"
						method="post">
						<input type="hidden" name="request_db"
							value="${requestScope.command.request_db}"> <input
							type="hidden" name="request_table"
							value="${requestScope.command.request_table}"> <input
							type="hidden" name="token" id="token" class="server-token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.add_foreign_key" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.column_name" /></label> <select
										name="column_name" id="column_name" class="form-control">
										<jma:forLoop items="#column_list" name="column"
											scope="command">
											<option value="${column}">${column}</option>
										</jma:forLoop>
									</select>
								</div>
								<div class="form-input">
									<label><m:print key="lbl.reference_table_name" /></label> <select
										name="ref_table_name" id="ref_table_name" class="form-control">
										<jma:forLoop items="#ref_table_list" name="ref_table"
											scope="command">
											<option value="${ref_table}">${ref_table}</option>
										</jma:forLoop>
									</select>
								</div>
								<div class="form-input">
									<label><m:print key="lbl.reference_column_name" /></label> <select
										name="ref_column_name" id="ref_column_name"
										class="form-control">
									</select>
								</div>
								<div class="form-input">
									<label><m:print key="lbl.on_update" /></label> <select
										name="update_action" id="update_action" class="form-control">
										<option value=""><m:print key="lbl.default" /></option>
										<jma:forLoop items="#action_list" name="action"
											scope="command">
											<option value="${action}">${action}</option>
										</jma:forLoop>
									</select>
								</div>
								<div class="form-input">
									<label><m:print key="lbl.on_delete" /></label> <select
										name="delete_action" id="delete_action" class="form-control">
										<option value=""><m:print key="lbl.default" /></option>
										<jma:forLoop items="#action_list" name="action"
											scope="command">
											<option value="${action}">${action}</option>
										</jma:forLoop>
									</select>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" id="add-btn" class="btn">
									<m:print key="lbl.run" />
								</button>
							</div>
						</div>
					</form>

					<form
						action="${pageContext.request.contextPath}/table_fk_drop.html"
						method="post" id="drop-form">
						<input type="hidden" name="request_db"
							value="${requestScope.command.request_db}"> <input
							type="hidden" name="request_table"
							value="${requestScope.command.request_table}"> <input
							type="hidden" name="token" class="server-token"
							value="${requestScope.command.token}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.foreign_key_list" />
							</div>
							<div class="group-widget group-content">
								<table class="tbl" id="keys-table">
									<thead>
										<tr>
											<th><input type="checkbox" id="check-all"></th>
											<th><m:print key="lbl.key_name" /></th>
											<th><m:print key="lbl.column_name" /></th>
											<th><m:print key="lbl.reference_table_name" /></th>
											<th><m:print key="lbl.reference_column_name" /></th>
											<th><m:print key="lbl.update_rule" /></th>
											<th><m:print key="lbl.delete_rule" /></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th></th>
											<th colspan="6">Total: ${requestScope.command.key_count}</th>
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#foreign_key_info_list" scope="command">
											<jma:forLoop items="#foreign_key_info_list" name="keyInfo"
												scope="command">
												<tr>
													<td><input type="checkbox" name="keys"
														value="${keyInfo.key_name}"></td>
													<td>${keyInfo.key_name}</td>
													<td>${keyInfo.column_name}</td>
													<td>${keyInfo.ref_table_name}</td>
													<td>${keyInfo.ref_column_name}</td>
													<td>${keyInfo.update_rule}</td>
													<td>${keyInfo.delete_rule}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#foreign_key_info_list" scope="command">
											<tr>
												<td colspan="6"><m:print
														key="msg.no_foreign_keys_found" /></td>
											</tr>
										</jma:empty>
									</tbody>
								</table>
							</div>
							<jma:notEmpty name="#foreign_key_info_list" scope="command">
								<div class="group-widget group-footer">
									<button type="button" id="drop-btn" class="btn">
										<m:print key="lbl.drop" />
									</button>
								</div>
							</jma:notEmpty>
						</div>
					</form>

					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.referenced_table_list" />
						</div>
						<div class="group-widget group-content">
							<table class="tbl" id="ref-table">
								<thead>
									<tr>
										<th><m:print key="lbl.key_name" /></th>
										<th><m:print key="lbl.column_name" /></th>
										<th><m:print key="lbl.referenced_table_name" /></th>
										<th><m:print key="lbl.referenced_column_name" /></th>
										<th><m:print key="lbl.update_rule" /></th>
										<th><m:print key="lbl.delete_rule" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="6">Total: ${requestScope.command.ref_count}</th>
									</tr>
								</tfoot>
								<tbody>
									<jma:notEmpty name="#reference_key_info_list" scope="command">
										<jma:forLoop items="#reference_key_info_list" name="keyInfo"
											scope="command">
											<tr>
												<td>${keyInfo.key_name}</td>
												<td>${keyInfo.column_name}</td>
												<td>${keyInfo.ref_table_name}</td>
												<td>${keyInfo.ref_column_name}</td>
												<td>${keyInfo.update_rule}</td>
												<td>${keyInfo.delete_rule}</td>
											</tr>
										</jma:forLoop>
									</jma:notEmpty>
									<jma:empty name="#reference_key_info_list" scope="command">
										<tr>
											<td colspan="6"><m:print key="msg.no_records_found" /></td>
										</tr>
									</jma:empty>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div id="footer">
					<jsp:include page="../../includes/Footer.jsp" />
				</div>
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
	<m:store name="msg_select_least_one_key" key="msg.select_least_one_key" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(3)").addClass('active');
		applyEvenOdd('#keys-table');
		applyEvenOdd('#ref-table');

		function fetchColumns() {
			showWaiting();
			$.ajax({
				url : Server.root + '/table_fetch_columns.text',
				method : 'POST',
				data : {
					'ref_table_name' : $('#ref_table_name').val(),
					'request_db' : Server.database,
					'request_table' : Server.table,
					'token' : $('#token').val()
				},
				success : function(result) {
					if (result == '') {
						hideWaiting();
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
						return;
					}
					var text = decode(result);
					if (text == '') {
						hideWaiting();
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
						return;
					}
					var jsonText = $.parseJSON(text);
					$('.server-token').val(jsonText.token);
					var columns = jsonText.data;
					$('#ref_column_name').empty();
					for ( var i in columns) {
						$('#ref_column_name').append('<option value="'+columns[i]+'">' + columns[i] + '</option>');
					}
					hideWaiting();
				},
				error : function(result) {
					$(td).text(originalData);
					hideWaiting();
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
				}
			});
		}

		$(function() {
			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#msg-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});
			$('#check-all').change(function() {
				var status = $(this).prop('checked');
				$('input[name="keys"]').prop('checked', status);
			});
			$('input[name="keys"]').change(function() {
				var length1 = $('input[name="keys"]').length;
				var length2 = $('input[name="keys"]:checked').length;
				if (length1 == length2) {
					$('#check-all').prop('checked', true);
					$('#check-all').prop('indeterminate', false);
				} else if (length2 != 0) {
					$('#check-all').prop('indeterminate', true);
				} else {
					$('#check-all').prop('checked', false);
					$('#check-all').prop('indeterminate', false);
				}
			});

			fetchColumns();
			$('#ref_table_name').change(function() {
				fetchColumns();
			});

			$('#drop-btn').click(function() {
				if ($('input[name="keys"]:checked').length < 1) {
					$('#sidebar-error-msg').text('${msg_select_least_one_key}');
					$('#sidebar-error-dialog').show();
					return;
				}
				$('#drop-form').submit();
			});
		});
	</script>
</body>
</html>
