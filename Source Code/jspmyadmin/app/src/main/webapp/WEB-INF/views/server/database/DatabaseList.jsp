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
							<m:print key="lbl.database_list" />
						</h3>
					</div>
					<form method="post" accept-charset="UTF-8" id="create_db"
						action="${pageContext.request.contextPath}/server_database_create.html">
						<input type="hidden" name="token"
							value="${requestScope.command.token}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.create_database" />
							</div>
							<div class="group-widget gorup-content">
								<div class="form-input">
									<label><m:print key="lbl.database_name" /></label> <input
										type="text" name="database" id="database" class="form-control"
										maxlength="50">
								</div>
								<div class="form-input">
									<label><m:print key="lbl.collation" /></label> <select
										name="collation" id="server_collation" class="form-control">
										<option value=""><m:print key="lbl.default_collation" /></option>
										<jma:forLoop items="#collation_map" name="charset"
											key="charsetKey" scope="command">
											<optgroup label="${charsetKey}">
												<jma:forLoop items="#charset" name="collationName"
													scope="page">
													<option value="${collationName}">${collationName}</option>
												</jma:forLoop>
											</optgroup>
										</jma:forLoop>
									</select>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="create_btn">
									<m:print key="lbl.run" />
								</button>
							</div>
						</div>
					</form>
					<form method="post" accept-charset="UTF-8" id="server_db"
						action="${pageContext.request.contextPath}/server_databases.html">
						<input type="hidden" name="token"
							value="${requestScope.command.token}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.database_list" />
							</div>
							<div class="group-widget group-content">
								<table class="tbl" id="database-list">
									<thead>
										<tr>
											<th><input type="checkbox" id="check-all"></th>
											<th><jma:switch name="#sort" scope="command">
													<jma:case value="0">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.database}">
																	<m:print key="lbl.database_name" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																</a>
															</jma:case>
															<jma:default>
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.database}">
																	<m:print key="lbl.database_name" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																</a>
															</jma:default>
														</jma:switch>
													</jma:case>
													<jma:default>
														<a
															href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.database}">
															<m:print key="lbl.database_name" />
														</a>
													</jma:default>
												</jma:switch></th>
											<th><jma:switch name="#sort" scope="command">
													<jma:case value="1">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.collation}">
																	<m:print key="lbl.collation" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																</a>
															</jma:case>
															<jma:default>
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.collation}">
																	<m:print key="lbl.collation" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																</a>
															</jma:default>
														</jma:switch>
													</jma:case>
													<jma:default>
														<a
															href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.collation}">
															<m:print key="lbl.collation" />
														</a>
													</jma:default>
												</jma:switch></th>
											<th><jma:switch name="#sort" scope="command">
													<jma:case value="2">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.tables}">
																	<m:print key="lbl.no_of_tables" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																</a>
															</jma:case>
															<jma:default>
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.tables}">
																	<m:print key="lbl.no_of_tables" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																</a>
															</jma:default>
														</jma:switch>
													</jma:case>
													<jma:default>
														<a
															href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.tables}">
															<m:print key="lbl.no_of_tables" />
														</a>
													</jma:default>
												</jma:switch></th>
											<th><jma:switch name="#sort" scope="command">
													<jma:case value="3">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.rows}">
																	<m:print key="lbl.no_of_rows" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																</a>
															</jma:case>
															<jma:default>
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.rows}">
																	<m:print key="lbl.no_of_rows" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																</a>
															</jma:default>
														</jma:switch>
													</jma:case>
													<jma:default>
														<a
															href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.rows}">
															<m:print key="lbl.no_of_rows" />
														</a>
													</jma:default>
												</jma:switch></th>
											<th><jma:switch name="#sort" scope="command">
													<jma:case value="4">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.data}">
																	<m:print key="lbl.data_size" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																</a>
															</jma:case>
															<jma:default>
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.data}">
																	<m:print key="lbl.data_size" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																</a>
															</jma:default>
														</jma:switch>
													</jma:case>
													<jma:default>
														<a
															href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.data}">
															<m:print key="lbl.data_size" />
														</a>
													</jma:default>
												</jma:switch></th>
											<th><jma:switch name="#sort" scope="command">
													<jma:case value="5">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.indexes}">
																	<m:print key="lbl.index_size" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																</a>
															</jma:case>
															<jma:default>
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.indexes}">
																	<m:print key="lbl.index_size" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																</a>
															</jma:default>
														</jma:switch>
													</jma:case>
													<jma:default>
														<a
															href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.indexes}">
															<m:print key="lbl.index_size" />
														</a>
													</jma:default>
												</jma:switch></th>
											<th><jma:switch name="#sort" scope="command">
													<jma:case value="6">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.total}">
																	<m:print key="lbl.total_size" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																</a>
															</jma:case>
															<jma:default>
																<a
																	href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.total}">
																	<m:print key="lbl.total_size" /> <img alt=""
																	class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																</a>
															</jma:default>
														</jma:switch>
													</jma:case>
													<jma:default>
														<a
															href="${pageContext.request.contextPath}/server_databases.html?token=${requestScope.command.sortInfo.total}">
															<m:print key="lbl.total_size" />
														</a>
													</jma:default>
												</jma:switch></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th></th>
											<th><m:print key="lbl.total" />:
												${requestScope.command.footerInfo.database}</th>
											<th></th>
											<th>${requestScope.command.footerInfo.tables}</th>
											<th>${requestScope.command.footerInfo.rows}</th>
											<th>${requestScope.command.footerInfo.data}</th>
											<th>${requestScope.command.footerInfo.indexes}</th>
											<th>${requestScope.command.footerInfo.total}</th>
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#database_list" scope="command">
											<jma:forLoop items="#database_list" name="databaseInfo"
												scope="command">
												<tr>
													<td><input type="checkbox" name="databases"
														value="${databaseInfo.database}"></td>
													<td><a
														href="${pageContext.request.contextPath}/database_structure.html?token=${databaseInfo.action}">
															${databaseInfo.database} </a></td>
													<td>${databaseInfo.collation}</td>
													<td>${databaseInfo.tables}</td>
													<td>${databaseInfo.rows}</td>
													<td>${databaseInfo.data}</td>
													<td>${databaseInfo.indexes}</td>
													<td>${databaseInfo.total}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#database_list" scope="command">
											<tr class="even">
												<td colspan="8"><m:print key="msg.no_database_found" /></td>
											</tr>
										</jma:empty>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="drop_btn"
									value="${pageContext.request.contextPath}/server_database_drop.html">
									<img alt="" class="icon"
										src="${pageContext.request.contextPath}/components/icons/delete-database.png">
									<m:print key="lbl.drop" />
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
				<p id="confirm-content">
					<m:print key="msg.drop_db_alert" />
				</p>
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
	<m:store name="msg_database_is_blank" key="msg.database_is_blank" />
	<m:store name="msg_select" key="msg.select_least_one_database" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(1)").addClass('active');
		applyEvenOdd('#database-list');
		var chkSelector = "input[type='checkbox'][name='databases']";
		$(function() {

			$('#create_btn').click(function() {
				if ($('#database').val().trim() == '') {
					$('#error-content').text('${msg_database_is_blank}');
					$('#error-dialog').show();
				} else {
					$('#create_db').submit();
				}
			});

			$('#drop_btn').click(function() {
				var length = $(chkSelector + ":checked").length;
				if (length > 0) {
					$('#confirm-dialog').show();
				} else {
					$('#error-content').text('${msg_select}');
					$('#error-dialog').show();
				}
			});

			$('#yes_btn').click(function() {
				$('#server_db').attr('action', $('#drop_btn').val());
				$('#server_db').submit();
			});

			$(chkSelector).change(function() {
				var length1 = $(chkSelector).length;
				var length2 = $(chkSelector + ":checked").length;
				if (length1 == length2) {
					$('#check-all').prop('checked', true);
				} else {
					$('#check-all').prop('checked', false);
				}
			});

			$('#check-all').change(function() {
				$(chkSelector).prop('checked', $(this).prop("checked"));
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

			$('#no_btn').click(function() {
				$('#confirm-dialog').hide();
			});
		});
	</script>
</body>
</html>
