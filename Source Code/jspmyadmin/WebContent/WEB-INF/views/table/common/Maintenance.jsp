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

.group-spl {
	width: auto;
	display: inline-block;
	margin-left: 1%;
}

.group-spl .group-widget {
	width: auto;
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
							<m:print key="lbl.table_maintenance" />
						</h3>
					</div>
					<div class="group group-spl">
						<form
							action="${pageContext.request.contextPath}/table_maintenance.html"
							accept-charset="utf-8" method="post">
							<input type="hidden" name="token" id="token"
								value="${requestScope.command.token}"><input
								type="hidden" name="action" value="1">
							<div class="group-widget group-header">ANALYZE TABLE</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.select_option" /></label> <select
										name="option" class="form-control">
										<option value=""><m:print key="lbl.select" /></option>
										<jma:notEmpty name="#analize_op_list" scope="command">
											<jma:forLoop items="#analize_op_list" name="item"
												scope="command">
												<option value="${item}">${item}</option>
											</jma:forLoop>
										</jma:notEmpty>
									</select>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" class="btn">
									<m:print key="lbl.run" />
								</button>
							</div>
						</form>
					</div>
					<div class="group group-spl">
						<form
							action="${pageContext.request.contextPath}/table_maintenance.html"
							accept-charset="utf-8" method="post">
							<input type="hidden" name="token" id="token"
								value="${requestScope.command.token}"><input
								type="hidden" name="action" value="2">
							<div class="group-widget group-header">CHECK TABLE</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.select_option" /></label> <select
										name="option" class="form-control">
										<option value=""><m:print key="lbl.select" /></option>
										<jma:notEmpty name="#check_op_list" scope="command">
											<jma:forLoop items="#check_op_list" name="item"
												scope="command">
												<option value="${item}">${item}</option>
											</jma:forLoop>
										</jma:notEmpty>
									</select>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" class="btn">
									<m:print key="lbl.run" />
								</button>
							</div>
						</form>
					</div>
					<div class="group group-spl">
						<form
							action="${pageContext.request.contextPath}/table_maintenance.html"
							accept-charset="utf-8" method="post">
							<input type="hidden" name="token" id="token"
								value="${requestScope.command.token}"><input
								type="hidden" name="action" value="3">
							<div class="group-widget group-header">CHECKSUM TABLE</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.select_option" /></label> <select
										name="option" class="form-control">
										<option value=""><m:print key="lbl.select" /></option>
										<jma:notEmpty name="#checksum_op_list" scope="command">
											<jma:forLoop items="#checksum_op_list" name="item"
												scope="command">
												<option value="${item}">${item}</option>
											</jma:forLoop>
										</jma:notEmpty>
									</select>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" class="btn">
									<m:print key="lbl.run" />
								</button>
							</div>
						</form>
					</div>
					<div class="group group-spl">
						<form
							action="${pageContext.request.contextPath}/table_maintenance.html"
							accept-charset="utf-8" method="post">
							<input type="hidden" name="token" id="token"
								value="${requestScope.command.token}"><input
								type="hidden" name="action" value="4">
							<div class="group-widget group-header">OPTIMIZE TABLE</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.select_option" /></label> <select
										name="option" class="form-control">
										<option value=""><m:print key="lbl.select" /></option>
										<jma:notEmpty name="#analize_op_list" scope="command">
											<jma:forLoop items="#analize_op_list" name="item"
												scope="command">
												<option value="${item}">${item}</option>
											</jma:forLoop>
										</jma:notEmpty>
									</select>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" class="btn">
									<m:print key="lbl.run" />
								</button>
							</div>
						</form>
					</div>
					<div class="group group-spl">
						<form
							action="${pageContext.request.contextPath}/table_maintenance.html"
							accept-charset="utf-8" method="post">
							<input type="hidden" name="token" id="token"
								value="${requestScope.command.token}"><input
								type="hidden" name="action" value="5">
							<div class="group-widget group-header">REPAIR TABLE</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.select_option" /></label> <select
										name="option" class="form-control">
										<option value=""><m:print key="lbl.select" /></option>
										<jma:notEmpty name="#analize_op_list" scope="command">
											<jma:forLoop items="#analize_op_list" name="item"
												scope="command">
												<option value="${item}">${item}</option>
											</jma:forLoop>
										</jma:notEmpty>
									</select>
								</div>
								<jma:notEmpty name="#repair_op_list" scope="command">
									<jma:forLoop items="#repair_op_list" name="item"
										scope="command">
										<div class="form-input">
											<label><input type="checkbox" name="repair_options"
												value="${item}"> ${item}</label>
										</div>
									</jma:forLoop>
								</jma:notEmpty>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" class="btn">
									<m:print key="lbl.run" />
								</button>
							</div>
						</form>
					</div>
					<jma:notEmpty name="#column_names" scope="command">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.result" />
							</div>
							<div class="group-widget group-content">
								<div style="width: 100%; margin-top: 10px; overflow-y: auto;">
									<table class="tbl" id="data-rows">
										<thead>
											<tr id="column-header">
												<jma:forLoop items="#column_names" name="columnName"
													scope="command">
													<th>${columnName}</th>
												</jma:forLoop>
											</tr>
										</thead>
										<tbody>
											<jma:notEmpty name="#data_list" scope="command">
												<jma:forLoop items="#data_list" name="rowData"
													scope="command">
													<tr>
														<jma:forLoop items="#rowData" name="item" scope="page">
															<td>${item}</td>
														</jma:forLoop>
													</tr>
												</jma:forLoop>

											</jma:notEmpty>
											<jma:empty name="#data_list" scope="command">
												<tr>
													<td colspan="${requestScope.command.column_count}"><m:print
															key="msg.no_records_found" /></td>
												</tr>
											</jma:empty>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</jma:notEmpty>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#header-menu li:nth-child(10)").addClass('active');
	</script>
	<jma:notEmpty name="#column_names" scope="command">
		<script type="text/javascript">
			applyEvenOdd('#data-rows');
		</script>
	</jma:notEmpty>
</body>
</html>