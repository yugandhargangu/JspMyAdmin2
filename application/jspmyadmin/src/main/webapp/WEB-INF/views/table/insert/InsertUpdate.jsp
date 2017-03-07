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
							<jma:switch name="#update" scope="command">
								<jma:case value="1">
									<m:print key="lbl.update_table_data" />
								</jma:case>
								<jma:default>
									<m:print key="lbl.insert_data_into_table" />
								</jma:default>
							</jma:switch>
						</h3>
					</div>
					<form
						action="${pageContext.request.contextPath}/table_data_insert_update.html"
						method="post" accept-charset="utf-8" enctype="multipart/form-data"
						id="table-data-form">
						<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
						<input type="hidden" name="request_table" value="${requestScope.command.request_table}">
						<input type="hidden" name="token"
							value="${requestScope.command.token}"> <input
							type="hidden" name="pk_column"
							value="${requestScope.command.pk_column}"> <input
							type="hidden" name="pk_value"
							value="${requestScope.command.pk_value}"><input
							type="hidden" name="update"
							value="${requestScope.command.update}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.data" />
							</div>
							<div class="group-widget group-content">
								<table class="tbl" id="table-data">
									<thead>
										<tr>
											<th>#</th>
											<th><m:print key="lbl.column_name" /></th>
											<th><m:print key="lbl.datatype" /></th>
											<th><m:print key="lbl.null" /></th>
											<th><m:print key="lbl.extra" /></th>
											<th>Use Function</th>
											<th><m:print key="lbl.value" /></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>#</th>
											<th><m:print key="lbl.column_name" /></th>
											<th><m:print key="lbl.datatype" /></th>
											<th><m:print key="lbl.null" /></th>
											<th><m:print key="lbl.extra" /></th>
											<th>Use Function</th>
											<th><m:print key="lbl.value" /></th>
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#info_list" scope="command">
											<jma:forLoop items="#info_list" name="insertInfo"
												scope="command" index="_index">
												<tr>
													<td>${_index +1}</td>
													<td>${insertInfo.column}<input type="hidden"
														name="columns" value="${insertInfo.column}">
													</td>
													<td>${insertInfo.dataType}<jma:switch
															name="#insertInfo.dataType" scope="page">
															<jma:case value="tinyblob">(256B)</jma:case>
															<jma:case value="blob">(64KiB)</jma:case>
															<jma:case value="mediumblob">(16MiB)</jma:case>
															<jma:case value="longblob">(4GiB)</jma:case>
														</jma:switch>
													</td>
													<td>${insertInfo.canBeNull}</td>
													<td>${insertInfo.extra}</td>
													<td style="text-align: center;"><input type="hidden"
														name="functions" value=""> <jma:switch
															name="#insertInfo.file_type" scope="page">
															<jma:case value="1">
															</jma:case>
															<jma:default>
																<input type="checkbox" class="function-check">
															</jma:default>
														</jma:switch></td>
													<td><jma:switch name="#insertInfo.file_type"
															scope="page">
															<jma:case value="1">
																<input type="file" name="values">
															</jma:case>
															<jma:default>
																<input type="text" name="values" style="width: 320px;"
																	value="${insertInfo.value}">
															</jma:default>
														</jma:switch></td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" class="btn" id="btn-save">
									<m:print key="lbl.run" />
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
	<m:store name="msg_drop_column_alert" key="msg.drop_column_alert" />
	<m:store name="msg_select_least_one_column"
		key="msg.select_least_one_column" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(6)").addClass('active');
		applyEvenOdd('#table-data');
		$(function() {
			$('.function-check').change(function() {
				if ($(this).is(':checked')) {
					$(this).parent().find('input[name="functions"]').val('1');
				} else {
					$(this).parent().find('input[name="functions"]').val('');
				}
			});

			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});
		});
	</script>
</body>
</html>
