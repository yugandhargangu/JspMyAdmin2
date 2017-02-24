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
							<m:print key="lbl.status_variables_list" />
						</h3>
					</div>
					<div class="group-widget group-content">
						<div style="padding: 0.2em;">
							<label><m:print key="lbl.server_start_date" /> : <b>${requestScope.command.start_date}</b></label>
							<label style="float: right;"><m:print
									key="lbl.server_running_time" /> : <b>${requestScope.command.run_time}</b></label>
						</div>
						<hr style="margin-top: 0.5em; margin-bottom: 0.5em; color: #FFF;">
						<div style="padding: 0.2em;">
							<label><m:print key="lbl.received" /> : <b>${requestScope.command.received}</b></label>
							| <label><m:print key="lbl.sent" /> : <b>${requestScope.command.sent}</b></label>
							<label style="float: right;"><m:print key="lbl.total" />
								: <b>${requestScope.command.total}</b> </label>
						</div>
						<hr style="margin-top: 0.5em; margin-bottom: 0.5em; color: #FFF;">
						<div>
							<label><m:print key="lbl.search" /> : </label><input type="text"
								id="search" class="form-control">
						</div>
						<table class="tbl" id="variable-list">
							<thead>
								<tr>
									<jma:forLoop items="#columnInfo" name="columnName"
										scope="command" index="columnIndex">
										<th>${columnName}</th>
									</jma:forLoop>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<jma:forLoop items="#columnInfo" name="columnName"
										scope="command" index="columnIndex">
										<th>${columnName}</th>
									</jma:forLoop>
								</tr>
							</tfoot>
							<tbody>
								<jma:notEmpty name="#data_list" scope="command">
									<jma:forLoop items="#data_list" name="variableInfo"
										scope="command">
										<tr>
											<jma:forLoop items="#variableInfo" name="variableData"
												scope="page">
												<td>${variableData}</td>
											</jma:forLoop>
										</tr>
									</jma:forLoop>
								</jma:notEmpty>
								<jma:empty name="#data_list" scope="command">
									<tr class="even">
										<td colspan="3"><m:print
												key="msg.no_status_variable_found" /></td>
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
	<script type="text/javascript">
		$("#header-menu li:nth-child(3)").addClass('active');
		applyEvenOdd('#variable-list');

		$(function() {
			$('#search').keyup(function() {
				searchTable('#variable-list', [ 0, 1 ], $(this).val());
			});
		});
	</script>
</body>
</html>
