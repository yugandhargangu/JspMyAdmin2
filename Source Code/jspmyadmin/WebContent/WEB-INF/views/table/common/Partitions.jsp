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
						<h3>Table Partitions</h3>
					</div>

					<form
						action="${pageContext.request.contextPath}/table_partition_add.html"
						method="post" id="add-form">
						<input type="hidden" name="token" id="token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">Add Partition</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label>Partition By</label> <select name="partition"
										id="partition" class="form-control">
										<jma:forLoop items="#type_list" name="_type" scope="command">
											<option value="${_type}">${_type}</option>
										</jma:forLoop>
									</select>
								</div>
								<div class="form-input">
									<label>Partition Value</label> <input type="text"
										name="partition_val" id="partition_val" class="form-control"
										style="width: 420px;">
								</div>
								<div class="form-input">
									<label>Partition Count</label> <input type="number"
										name="partitions" id="partitions" class="form-control">
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" id="add-btn" class="btn">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>
					</form>

					<form
						action="${pageContext.request.contextPath}/table_partition_drop.html"
						method="post" id="drop-form">
						<input type="hidden" name="token"
							value="${requestScope.command.token}">
						<div class="group">
							<div class="group-widget group-header">Partition List</div>
							<div class="group-widget group-content">
								<table class="tbl" id="partitions-table">
									<thead>
										<tr>
											<th><input type="checkbox" id="check-all"></th>
											<th>Name</th>
											<th>Sub Name</th>
											<th>Method</th>
											<th>Sub Method</th>
											<th>Expression</th>
											<th>Sub Expression</th>
											<th>Description</th>
											<th>Table Rows</th>
											<th>Avg Row Length</th>
											<th>Data Length</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th></th>
											<th colspan="10">Total: ${requestScope.command.p_count}</th>
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#partition_list" scope="command">
											<jma:forLoop items="#partition_list" name="partitionInfo"
												scope="command">
												<tr>
													<td><input type="checkbox" name="names"
														value="${partitionInfo.name}"></td>
													<td>${partitionInfo.name}</td>
													<td>${partitionInfo.subname}</td>
													<td>${partitionInfo.method}</td>
													<td>${partitionInfo.sub_method}</td>
													<td>${partitionInfo.expression}</td>
													<td>${partitionInfo.sub_expression}</td>
													<td>${partitionInfo.description}</td>
													<td>${partitionInfo.table_rows}</td>
													<td>${partitionInfo.avg_row_length}</td>
													<td>${partitionInfo.data_length}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#partition_list" scope="command">
											<tr>
												<td colspan="11">No Partitions Found.</td>
											</tr>
										</jma:empty>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<button type="button" id="drop-btn" class="btn">
									<m:print key="lbl.drop" />
								</button>
							</div>
						</div>
					</form>
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
		$("#header-menu li:nth-child(4)").addClass('active');
		applyEvenOdd('#partitions-table');

		$(function() {
			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#msg-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});
			$('#check-all').change(function() {
				var status = $(this).prop('checked');
				$('input[name="names"]').prop('checked', status);
			});
			$('input[name="names"]').change(function() {
				var length1 = $('input[name="names"]').length;
				var length2 = $('input[name="names"]:checked').length;
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

			$('#add-btn').click(function() {
				if ($('#partition_val').val().trim() == '') {
					$('#sidebar-error-msg').text('Partition Value is Blank.');
					$('#sidebar-error-dialog').show();
					return;
				}
				$('#add-form').submit();
			});

			$('#drop-btn').click(function() {
				if ($('input[name="names"]:checked').length < 1) {
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
