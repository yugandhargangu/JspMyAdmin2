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
						<form action="${pageContext.request.contextPath}/table_alter.html"
							accept-charset="utf-8" method="post">
							<input type="hidden" name="token"
								value="${requestScope.command.token}">
							<h3>
								<m:print key="lbl.table_structure" />
								<button type="submit" class="btn" id="alter-btn"
									style="float: right;">
									<m:print key="lbl.alter_table" />
								</button>
							</h3>
						</form>
					</div>
					<form action="#" method="post" accept-charset="utf-8"
						id="column-list-form">
						<input type="hidden" name="token"
							value="${requestScope.command.token}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.columns" />
							</div>
							<div class="group-widget group-content">
								<table class="tbl" id="column-list">
									<thead>
										<tr>
											<th><input type="checkbox" id="check_all"></th>
											<th><m:print key="lbl.column_name" /></th>
											<th><m:print key="lbl.datatype" /></th>
											<th><m:print key="lbl.collation" /></th>
											<th><m:print key="lbl.null" /></th>
											<th><m:print key="lbl.key" /></th>
											<th><m:print key="lbl.default" /></th>
											<th><m:print key="lbl.extra" /></th>
											<th><m:print key="lbl.privileges" /></th>
											<th><m:print key="lbl.comment" /></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th></th>
											<th><m:print key="lbl.total" />:
												${requestScope.command.columns_size}</th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#column_list" scope="command">
											<jma:forLoop items="#column_list" name="columnInfo"
												scope="command">
												<tr>
													<td><input type="checkbox" name="columns"
														value="${columnInfo.field_name}"></td>
													<td>${columnInfo.field_name}</td>
													<td>${columnInfo.field_type}</td>
													<td>${columnInfo.collation}</td>
													<td>${columnInfo.null_yes}</td>
													<td>${columnInfo.key}</td>
													<td>${columnInfo.def_val}</td>
													<td>${columnInfo.extra}</td>
													<td>${columnInfo.privileges}</td>
													<td>${columnInfo.comments}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#column_list" scope="command">
											<tr class="even">
												<td colspan="10"><m:print key="msg.no_columns_found" />
												</td>
											</tr>
										</jma:empty>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-drop">
									<m:print key="lbl.drop" />
								</button>
							</div>
						</div>
					</form>

					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.indexes" />
						</div>
						<div class="group-widget group-content">
							<table class="tbl" id="index-list">
								<thead>
									<tr>
										<th><m:print key="lbl.column_name" /></th>
										<th><m:print key="lbl.key_name" /></th>
										<th><m:print key="lbl.non_unique" /></th>
										<th><m:print key="lbl.null" /></th>
										<th><m:print key="lbl.index_type" /></th>
										<th><m:print key="lbl.collation" /></th>
										<th><m:print key="lbl.cardinality" /></th>
										<th><m:print key="lbl.comment" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th><m:print key="lbl.total" />:
											${requestScope.command.index_size}</th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
								</tfoot>
								<tbody>
									<jma:notEmpty name="#index_list" scope="command">
										<jma:forLoop items="#index_list" name="indexInfo"
											scope="command">
											<tr>
												<td>${indexInfo.column_name}</td>
												<td>${indexInfo.key_name}</td>
												<td>${indexInfo.non_unique}</td>
												<td>${indexInfo.null_yes}</td>
												<td>${indexInfo.index_type}</td>
												<td>${indexInfo.collation}</td>
												<td>${indexInfo.cardinality}</td>
												<td>${indexInfo.comment}</td>
											</tr>
										</jma:forLoop>
									</jma:notEmpty>
									<jma:empty name="#index_list" scope="command">
										<tr class="even">
											<td colspan="8"><m:print key="msg.no_index_found" /></td>
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
	<div class="dialog" id="confirm-dialog" style="display: none;">
		<div class="dialog-widget dialog-normal">
			<div class="close" id="confirm-close">&#10005;</div>
			<div class="dialog-header">
				<m:print key="lbl.alert" />
			</div>
			<div class="dialog-content">
				<p id="confirm-content"></p>
			</div>
			<div class="dialog-footer">
				<button type="button" class="btn" id="yes_btn">
					<m:print key="lbl.yes" />
				</button>
				<button type="button" class="btn" id="no_btn">
					<m:print key="lbl.no" />
				</button>
			</div>
		</div>
	</div>

	<m:store name="msg_drop_column_alert" key="msg.drop_column_alert" />
	<m:store name="msg_select_least_one_column"
		key="msg.select_least_one_column" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(2)").addClass('active');
		applyEvenOdd('#column-list');
		applyEvenOdd('#index-list');

		$(function() {
			$('#check_all').change(function() {
				var status = $(this).prop('checked');
				$('input[name="columns"]').prop('checked', status);
			});
			$('input[name="columns"]').change(function() {
				var length1 = $('input[name="columns"]').length;
				var length2 = $('input[name="columns"]:checked').length;
				if (length1 == length2) {
					$('#check_all').prop('checked', true);
					$('#check_all').prop('indeterminate', false);
				} else if (length2 != 0) {
					$('#check_all').prop('indeterminate', true);
				} else {
					$('#check_all').prop('checked', false);
					$('#check_all').prop('indeterminate', false);
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
			$('#error-close').click(function() {
				$('#error-content').text('');
				$('#error-dialog').hide();
			});

			$('#confirm-close').click(function() {
				$('#confirm-dialog').hide();
			});
			$('#yes_btn').click(function() {
				showWaiting();
				$('#column-list-form').attr('action', Server.root + '/table_column_drop.html');
				$('#column-list-form').submit();
			});
			$('#no_btn').click(function() {
				$('#confirm-dialog').hide();
				$('#confirm-content').text('');
			});
			$('#btn-drop').click(function() {
				if ($('input[name="columns"]:checked').length < 1) {
					$('#error-content').text('${msg_select_least_one_column}');
					$('#error-dialog').show();
					return;
				}
				$('#confirm-content').text('${msg_drop_column_alert}');
				$('#confirm-dialog').show();
			});
		});
	</script>
</body>
</html>
