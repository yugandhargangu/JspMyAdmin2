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
					page="../../database/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.tables" />
						</h3>
					</div>
					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.create_table" />
						</div>
						<div class="group-widget group-content">
							<form id="table-create-form"
								action="${pageContext.request.contextPath}/database_create_table.html"
								method="post" accept-charset="utf-8">
								<input type="hidden" name="token"
									value="${requestScope.command.token}">
								<div class="form-input">
									<label><m:print key="lbl.table_name" /></label> <input
										type="text" name="table_name" id="table-create-name"
										class="form-control">
								</div>
							</form>
						</div>
						<div class="group-widget group-footer">
							<button type="button" class="btn" id="btn-go">
								<m:print key="btn.go" />
							</button>
						</div>
					</div>
					<form action="#" method="post" accept-charset="utf-8"
						id="table-list-form">
						<input type="hidden" name="token"
							value="${requestScope.command.token}"> <input
							type="hidden" name="action" value="/database_structure">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.table_list" />
							</div>
							<div class="group-widget group-content">
								<table class="tbl" id="table-list">
									<thead>
										<tr>
											<th><input type="checkbox" id="check_all"></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.name}">
													<m:print key="lbl.table_name" /> <jma:if name="#sort"
														value="1" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.type}">
													<m:print key="lbl.type" /> <jma:if name="#sort" value="2"
														scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.engine}">
													<m:print key="lbl.engine" /> <jma:if name="#sort"
														value="3" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.rows}">
													<m:print key="lbl.no_of_rows" /> <jma:if name="#sort"
														value="4" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.collation}">
													<m:print key="lbl.collation" /> <jma:if name="#sort"
														value="5" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.size}">
													<m:print key="lbl.data_size" /> <jma:if name="#sort"
														value="6" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.auto_inr}">
													<m:print key="lbl.auto_increment" /> <jma:if name="#sort"
														value="7" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.create_date}">
													<m:print key="lbl.create_date" /> <jma:if name="#sort"
														value="8" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.update_date}">
													<m:print key="lbl.update_date" /> <jma:if name="#sort"
														value="9" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
											<th><a
												href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.sortInfo.comment}">
													<m:print key="lbl.comment" /> <jma:if name="#sort"
														value="10" scope="command,">
														<jma:switch name="#type" scope="command">
															<jma:case value="true">
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
															</jma:case>
															<jma:default>
																<img alt="" class="icon"
																	src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
															</jma:default>
														</jma:switch>
													</jma:if>
											</a></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th></th>
											<th><m:print key="lbl.total" />:
												${requestScope.command.footerInfo.name}</th>
											<th></th>
											<th></th>
											<th>${requestScope.command.footerInfo.rows}</th>
											<th></th>
											<th>${requestScope.command.footerInfo.size}</th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#table_list" scope="command">
											<jma:forLoop items="#table_list" name="tableInfo"
												scope="command">
												<tr>
													<td><input type="checkbox" name="tables"
														value="${tableInfo.name}"></td>
													<td><a
														href="${pageContext.request.contextPath}/table_data.html?token=${tableInfo.action}">${tableInfo.name}</a></td>
													<td>${tableInfo.type}</td>
													<td>${tableInfo.engine}</td>
													<td>${tableInfo.rows}</td>
													<td>${tableInfo.collation}</td>
													<td>${tableInfo.size}</td>
													<td>${tableInfo.auto_inr}</td>
													<td>${tableInfo.create_date}</td>
													<td>${tableInfo.update_date}</td>
													<td>${tableInfo.comment}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#table_list" scope="command">
											<tr class="even">
												<td colspan="11"><m:print key="msg.no_table_found" /></td>
											</tr>
										</jma:empty>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-prefix">
									<m:print key="lbl.prefix" />
								</button>
								<button type="button" class="btn" id="btn-suffix">
									<m:print key="lbl.suffix" />
								</button>
								<button type="button" class="btn" id="btn-copy">
									<m:print key="lbl.copy" />
								</button>
								<button type="button" class="btn" id="btn-duplicate">
									<m:print key="lbl.duplicate" />
								</button>
								<button type="button" class="btn" id="btn-show-create">
									<m:print key="lbl.show_create" />
								</button>
								<button type="button" class="btn" id="btn-export">
									<m:print key="lbl.export" />
								</button>
								<button type="button" class="btn" id="btn-truncate">
									<m:print key="lbl.truncate" />
								</button>
								<button type="button" class="btn" id="btn-drop">
									<m:print key="lbl.drop" />
								</button>
							</div>
						</div>

						<div class="group" id="show-create-group" style="display: none;">
							<div class="close" id="show-create-close">&#10005;</div>
							<div class="group-widget group-header">
								<m:print key="lbl.create_statement" />
							</div>
							<div class="group-widget group-content">
								<div style="width: 100%;">
									<textarea rows="15" cols="100" style="width: 100%;"
										id="show-create-area"></textarea>
								</div>
							</div>
							<span id="show-create-span"></span>
						</div>

						<div class="group" id="duplicate-group" style="display: none;">
							<div class="group-widget group-header">
								<m:print key="lbl.duplicate" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><input type="checkbox" name="enable_checks"
										value="Yes"> <m:print key="lbl.with_foreign_key" /></label>
								</div>
								<div class="form-input">
									<label><input type="checkbox" name="drop_checks"
										value="Yes"> <m:print key="lbl.with_data" /></label>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-duplicate-go"
									value="${pageContext.request.contextPath}/database_structure_duplicate.html">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>

						<div class="group" id="drop-group" style="display: none;">
							<div class="group-widget group-header">
								<m:print key="lbl.drop" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><input type="checkbox" name="enable_checks"
										value="Yes"> <m:print
											key="lbl.enable_foreign_key_checks" /></label>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-drop-go"
									value="${pageContext.request.contextPath}/">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>

						<div class="group" id="truncate-group" style="display: none;">
							<div class="group-widget group-header">
								<m:print key="lbl.truncate" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><input type="checkbox" name="enable_checks"
										value="Yes"> <m:print
											key="lbl.enable_foreign_key_checks" /></label>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-truncate-go"
									value="${pageContext.request.contextPath}/">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>

						<div class="group" id="prefix-group" style="display: none;">
							<div class="group-widget group-header">
								<m:print key="lbl.prefix" />
							</div>
							<div class="group-widget group-content">
								<div style="display: block;" id="prefix-type-div">
									<div style="display: inline-block; margin-right: 2em;">
										<m:print key="lbl.prefix_type" />
										:
									</div>
									<div style="display: inline-block; margin-right: 1em;">
										<label><input type="radio" class="prefix-radio"
											name="type" value="add"> <m:print
												key="lbl.add_prefix" /></label>
									</div>
									<div style="display: inline-block; margin-right: 1em;">
										<label><input type="radio" class="prefix-radio"
											name="type" value="replace"> <m:print
												key="lbl.replace_prefix" /></label>
									</div>
									<div style="display: inline-block; margin-right: 1em;">
										<label><input type="radio" class="prefix-radio"
											name="type" value="remove"> <m:print
												key="lbl.remove_prefix" /></label>
									</div>
								</div>
								<div style="display: block;">
									<div class="form-input">
										<label><m:print key="lbl.prefix" /></label> <input
											type="text" name="prefix" id="prefix-input"
											class="form-control">
									</div>
									<div class="form-input" style="display: none;"
										id="new-prefix-div">
										<label><m:print key="lbl.new_prefix" /></label> <input
											type="text" name="new_prefix" id="new-prefix-input"
											class="form-control">
									</div>
								</div>
								<div class="form-input prefix-copy-div" style="display: none;">
									<div style="display: inline-block;">
										<label><input type="radio" name="sort" value="">
											<m:print key="lbl.structure_only" /></label>
									</div>
									<div style="display: inline-block;">
										<label><input type="radio" name="sort" value="data">
											<m:print key="lbl.structure_and_data" /></label>
									</div>
								</div>
								<div class="form-input prefix-copy-div" style="display: none;">
									<label><input type="checkbox" name="enable_checks"
										value="Yes"> <m:print
											key="lbl.enable_foreign_key_checks" /></label>
								</div>
								<div class="form-input prefix-copy-div" style="display: none;">
									<label><input type="checkbox" name="drop_checks"
										value="Yes"> <m:print key="lbl.add_drops" /></label>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-prefix-go"
									value="${pageContext.request.contextPath}/database_structure_prefix.html">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>

						<div class="group" id="suffix-group" style="display: none;">
							<div class="group-widget group-header">
								<m:print key="lbl.suffix" />
							</div>
							<div class="group-widget group-content">
								<div style="display: block;" id="suffix-type-div">
									<div style="display: inline-block; margin-right: 2em;">
										<m:print key="lbl.suffix_type" />
										:
									</div>
									<div style="display: inline-block; margin-right: 1em;">
										<label><input type="radio" class="suffix-radio"
											name="type" value="add"> <m:print
												key="lbl.add_suffix" /></label>
									</div>
									<div style="display: inline-block; margin-right: 1em;">
										<label><input type="radio" class="suffix-radio"
											name="type" value="replace"> <m:print
												key="lbl.replace_suffix" /></label>
									</div>
									<div style="display: inline-block; margin-right: 1em;">
										<label><input type="radio" class="suffix-radio"
											name="type" value="remove"> <m:print
												key="lbl.remove_suffix" /></label>
									</div>
								</div>
								<div style="display: block;">
									<div class="form-input">
										<label><m:print key="lbl.suffix" /></label> <input
											type="text" name="prefix" id="suffix-input"
											class="form-control">
									</div>
									<div class="form-input" style="display: none;"
										id="new-suffix-div">
										<label><m:print key="lbl.new_suffix" /></label> <input
											type="text" name="new_prefix" id="new-suffix-input"
											class="form-control">
									</div>
								</div>
								<div class="form-input suffix-copy-div" style="display: none;">
									<div style="display: inline-block;">
										<label><input type="radio" name="sort" value="">
											<m:print key="lbl.structure_only" /></label>
									</div>
									<div style="display: inline-block;">
										<label><input type="radio" name="sort" value="data">
											<m:print key="lbl.structure_and_data" /></label>
									</div>
								</div>
								<div class="form-input suffix-copy-div" style="display: none;">
									<label><input type="checkbox" name="enable_checks"
										value="Yes"> <m:print
											key="lbl.enable_foreign_key_checks" /></label>
								</div>
								<div class="form-input suffix-copy-div" style="display: none;">
									<label><input type="checkbox" name="drop_checks"
										value="Yes"> <m:print key="lbl.add_drops" /></label>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-suffix-go"
									value="${pageContext.request.contextPath}/database_structure_suffix.html">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>

						<div class="group" id="export-group" style="display: none;">
							<div class="group-widget group-header">
								<m:print key="lbl.export" />
							</div>
							<div class="group-widget group-content">
								<div>
									<div style="display: inline-block;">
										<label><input type="radio" name="type"> <m:print
												key="lbl.structure_only" /></label>
									</div>
									<div style="display: inline-block;">
										<label><input type="radio" name="type"> <m:print
												key="lbl.structure_and_data" /></label>
									</div>
								</div>
								<div>
									<label><input type="checkbox" name="enable_checks"
										value="Yes"> <m:print
											key="lbl.enable_foreign_key_checks" /></label>
								</div>
								<div>
									<label><input type="checkbox" name="drop_checks"
										value="Yes"> <m:print key="lbl.add_drops" /></label>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-export-go"
									value="${pageContext.request.contextPath}/">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>

						<div class="group" id="copy-group" style="display: none;">
							<div class="group-widget group-header">
								<m:print key="lbl.copy_table" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.select_database" /></label> <select
										name="database_name" id="database-copy-select">
										<option value=""><m:print key="lbl.select_database" /></option>
										<jma:notEmpty name="#database_list" scope="command">
											<jma:forLoop items="#database_list" name="database"
												scope="command">
												<jma:switch name="#session_db" scope="session">
													<jma:case value="#database" scope="page">
														<!-- <option value="${database}" selected="selected">${database}</option> -->
													</jma:case>
													<jma:default>
														<option value="${database}">${database}</option>
													</jma:default>
												</jma:switch>
											</jma:forLoop>
										</jma:notEmpty>
									</select>
								</div>
								<div class="form-input" id="copy-type-div">
									<div style="display: inline-block;">
										<label><input type="radio" name="type" value="">
											<m:print key="lbl.structure_only" /></label>
									</div>
									<div style="display: inline-block;">
										<label><input type="radio" name="type"
											checked="checked" value="data"> <m:print
												key="lbl.structure_and_data" /></label>
									</div>
								</div>
								<div class="form-input">
									<label><input type="checkbox" name="drop_checks"
										value="Yes"> <m:print key="lbl.drop_if_exists" /></label>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-copy-go"
									value="${pageContext.request.contextPath}/database_structure_copy.html">
									<m:print key="btn.go" />
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
					<m:print key="btn.yes" />
				</button>
				<button type="button" class="btn" id="no_btn">
					<m:print key="btn.no" />
				</button>
			</div>
		</div>
	</div>

	<m:store name="msg_drop_table_alert" key="msg.drop_table_alert" />
	<m:store name="msg_truncate_table_alert" key="msg.truncate_table_alert" />
	<m:store name="msg_select_least_one_table"
		key="msg.select_least_one_table" />
	<m:store name="msg_table_name_blank" key="msg.table_name_blank" />
	<m:store name="msg_prefix_type_not_selected"
		key="msg.prefix_type_not_selected" />
	<m:store name="msg_prefix_blank" key="msg.prefix_blank" />
	<m:store name="msg_new_prefix_blank" key="msg.new_prefix_blank" />
	<m:store name="msg_copy_type_not_selected"
		key="msg.copy_type_not_selected" />
	<m:store name="msg_suffix_type_not_selected"
		key="msg.suffix_type_not_selected" />
	<m:store name="msg_new_suffix_blank" key="msg.new_suffix_blank" />
	<m:store name="msg_suffix_blank" key="msg.suffix_blank" />
	<m:store name="msg_select_database" key="msg.select_database" />

	<script type="text/javascript">
		$("#header-menu li:nth-child(1)").addClass('active');
		applyEvenOdd('#table-list');
		// animation time
		var waitTime = 500;
		// type of operation
		var GoType = {
			drop : "drop",
			truncate : "trunacte"
		};
		// confirm text of each operation
		var GoText = {
			drop : '${msg_drop_table_alert}',
			truncate : '${msg_truncate_table_alert}',
			select : '${msg_select_least_one_table}'
		};
		// action for each operation
		var GoAction = {
			drop : "/database_structure_drop.html",
			truncate : "/database_structure_truncate.html"
		};
		// to specify action
		var action = '#';
		// to make empty of all remaining inputs
		var goClean = '';

		// codemirror object
		var id = document.getElementById('show-create-area');
		var codeMirrorObj = CodeMirror.fromTextArea(id, {
			mode : 'text/x-mysql',
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : true,
			lineWrapping : true,
			matchBrackets : true,
			autofocus : true,
			extraKeys : {
				"Ctrl-Space" : "autocomplete"
			},
			readOnly : true
		});

		// to hide all operation div blocks
		function hideAll() {
			$('#show-create-group').hide(waitTime);
			$('#duplicate-group').hide(waitTime);
			$('#drop-group').hide(waitTime);
			$('#truncate-group').hide(waitTime);
			$('#prefix-group').hide(waitTime);
			$('#suffix-group').hide(waitTime);
			$('#export-group').hide(waitTime);
			$('#copy-group').hide(waitTime);
		}

		// to clear all remaining inputs
		function makeEmpty() {
			if (goClean != 'show-create-group') {
				$('#show-create-group').empty();
			}
			if (goClean != 'duplicate-group') {
				$('#duplicate-group').empty();
			}
			if (goClean != 'drop-group') {
				$('#drop-group').empty();
			}
			if (goClean != 'truncate-group') {
				$('#truncate-group').empty();
			}
			if (goClean != 'prefix-group') {
				$('#prefix-group').empty();
			}
			if (goClean != 'suffix-group') {
				$('#suffix-group').empty();
			}
			if (goClean != 'export-group') {
				$('#export-group').empty();
			}
			if (goClean != 'copy-group') {
				$('#copy-group').empty();
			}
		}

		// to check all checkbox status
		function shouldContinue() {
			var length = $('input[name="tables"]:checked').length;
			if (length < 1) {
				$('#error-content').text(GoText.select);
				$('#error-dialog').show();
				return false;
			} else {
				return true;
			}
		}

		// to show confirm block
		function showConfirm(type) {
			switch (type) {
			case GoType.drop:
				$('#confirm-content').text(GoText.drop);
				action = GoAction.drop;
				goClean = 'drop-group';
				break;
			case GoType.truncate:
				$('#confirm-content').text(GoText.truncate);
				action = GoAction.truncate;
				goClean = 'truncate-group';
				break;
			default:
				action = '#';
				break;
			}
			$('#confirm-dialog').show();
		}

		$(function() {
			$('#check_all').change(function() {
				var status = $(this).prop('checked');
				$('input[name="tables"]').prop('checked', status);
			});
			$('input[name="tables"]').change(function() {
				var length1 = $('input[name="tables"]').length;
				var length2 = $('input[name="tables"]:checked').length;
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
				$('#table-list-form').attr('action', Server.root + action);
				makeEmpty();
				$('#table-list-form').submit();
			});
			$('#no_btn').click(function() {
				$('#confirm-dialog').hide();
				action = '#';
				goClean = '';
			});
		});
	</script>

	<script type="text/javascript">
		// show-create
		function showCreateApply(res) {
			if (result == '') {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
				return;
			}
			var data = decode(res);
			var jsonData = $.parseJSON(data);
			if (jsonData.err != '') {
				$('#sidebar-error-msg').text(jsonData.err);
				$('#sidebar-error-dialog').show();
			} else {
				var actData = jsonData.data;
				var actJsonData = $.parseJSON(actData);
				var keys = Object.keys(actJsonData);
				var result = '';
				for (key in actJsonData) {
					result += '\n#------------- Create Table: ';
					result += key;
					result += ' ------------- \n\n';
					result += actJsonData[key];
					result += '\n\n\n';
				}
				$('#show-create-group').show(waitTime);
				setTimeout(function() {
					scrollTo('#show-create-span');
					codeMirrorObj.setValue(result);
					codeMirrorObj.refresh();
					codeMirrorObj.focus();
				}, waitTime);
			}
		}
		$(function() {
			$('#btn-go').click(function() {
				if ($('#table-create-name').val().trim() == '') {
					$('#error-content').text('${msg_table_name_blank}');
					$('#error-dialog').show();
					return;
				}

				$('#table-create-form').submit();
			});

			$('#btn-show-create').click(function() {
				hideAll();
				if (!shouldContinue()) {
					return;
				}
				var url = Server.root + '/database_structure_show_create.text';
				$.ajax({
					url : url,
					data : $('#table-list-form').serialize(),
					method : 'POST',
					success : function(res) {
						showCreateApply(res);
					},
					error : function(res) {
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
					}
				});

			});

			$('#show-create-close').click(function() {
				$('#show-create-group').hide(waitTime);
				codeMirrorObj.setValue('');
				scrollTo('#table-list');
			});
		});
	</script>

	<script type="text/javascript">
		// drop
		$(function() {
			$('#btn-drop').click(function() {
				hideAll();
				if (!shouldContinue()) {
					return;
				}
				$('#drop-group').show(waitTime);
				setTimeout(function() {
					scrollTo('#btn-drop-go');
				}, waitTime);
			});
			$('#btn-drop-go').click(function() {
				if (!shouldContinue()) {
					return;
				}
				showConfirm(GoType.drop);
			});
		});
	</script>

	<script type="text/javascript">
		// truncate
		$(function() {
			$('#btn-truncate').click(function() {
				hideAll();
				if (!shouldContinue()) {
					return;
				}
				$('#truncate-group').show(waitTime);
				setTimeout(function() {
					scrollTo('#btn-truncate-go');
				}, waitTime);
			});
			$('#btn-truncate-go').click(function() {
				if (!shouldContinue()) {
					return;
				}
				showConfirm(GoType.truncate);
			});
		});
	</script>

	<script type="text/javascript">
		// duplicate
		$(function() {
			$('#btn-duplicate').click(function() {
				hideAll();
				if (!shouldContinue()) {
					return;
				}
				$('#duplicate-group').show(waitTime);
				setTimeout(function() {
					scrollTo('#btn-duplicate-go');
				}, waitTime);
			});
			$('#btn-duplicate-go').click(function() {
				showWaiting();
				$('#table-list-form').attr('action', $(this).val());
				goClean = 'duplicate-group';
				makeEmpty();
				$('#table-list-form').submit();
			});
		});
	</script>

	<script type="text/javascript">
		var PrefixType = {
			add : "add",
			replace : "replace",
			remove : "remove",
			copy : "copy"
		}
		var currentPreType = PrefixType.add;
		// prefix
		$(function() {
			$('.prefix-radio').change(function() {
				switch ($(this).val()) {
				case 'add':
					$('.prefix-copy-div').hide();
					$('#new-prefix-div').hide();
					currentPreType = PrefixType.add;
					break;
				case 'replace':
					$('.prefix-copy-div').hide();
					$('#new-prefix-div').show();
					currentPreType = PrefixType.replace;
					break;
				case 'remove':
					$('.prefix-copy-div').hide();
					$('#new-prefix-div').hide();
					currentPreType = PrefixType.remove;
					break;
				case 'copy':
					$('.prefix-copy-div').show();
					$('#new-prefix-div').hide();
					currentPreType = PrefixType.copy;
					break;
				}
			});
			$('#btn-prefix').click(function() {
				hideAll();
				if (!shouldContinue()) {
					return;
				}
				$('#prefix-group').show(waitTime);
				setTimeout(function() {
					scrollTo('#btn-prefix-go');
				}, waitTime);
			});
			$('#btn-prefix-go').click(function() {
				if (!shouldContinue()) {
					return;
				}
				var radioChecked = false;
				$('#prefix-type-div').find('input[type="radio"]').each(function(i) {
					if ($(this).is(':checked')) {
						radioChecked = true;
					}
				});

				if (!radioChecked) {
					$('#error-content').text('${msg_prefix_type_not_selected}');
					$('#error-dialog').show();
					return;
				}

				if ($('#prefix-input').val().trim() == '') {
					$('#error-content').text('${msg_prefix_blank}');
					$('#error-dialog').show();
					return;
				}

				if (currentPreType == PrefixType.replace && $('#new-prefix-input').val().trim() == '') {
					$('#error-content').text('${msg_new_prefix_blank}');
					$('#error-dialog').show();
					return;
				}

				var preLength = $('.prefix-copy-div').find('input[name="sort"]:checked').length;
				if (currentPreType == PrefixType.copy && preLength < 1) {
					$('#error-content').text('${msg_copy_type_not_selected}');
					$('#error-dialog').show();
					return;
				}
				showWaiting();
				$('#table-list-form').attr('action', $(this).val());
				goClean = 'prefix-group';
				makeEmpty();
				$('#table-list-form').submit();
			});
		});
	</script>

	<script type="text/javascript">
		// suffix
		$(function() {
			$('.suffix-radio').change(function() {
				switch ($(this).val()) {
				case 'add':
					$('.suffix-copy-div').hide();
					$('#new-suffix-div').hide();
					currentPreType = PrefixType.add;
					break;
				case 'replace':
					$('.suffix-copy-div').hide();
					$('#new-suffix-div').show();
					currentPreType = PrefixType.replace;
					break;
				case 'remove':
					$('.suffix-copy-div').hide();
					$('#new-suffix-div').hide();
					currentPreType = PrefixType.remove;
					break;
				case 'copy':
					$('.suffix-copy-div').show();
					$('#new-suffix-div').hide();
					currentPreType = PrefixType.copy;
					break;
				}
			});
			$('#btn-suffix').click(function() {
				hideAll();
				if (!shouldContinue()) {
					return;
				}
				$('#suffix-group').show(waitTime);
				setTimeout(function() {
					scrollTo('#btn-suffix-go');
				}, waitTime);
			});
			$('#btn-suffix-go').click(function() {
				if (!shouldContinue()) {
					return;
				}
				var radioChecked = false;
				$('#suffix-type-div').find('input[type="radio"]').each(function(i) {
					if ($(this).is(':checked')) {
						radioChecked = true;
					}
				});

				if (!radioChecked) {
					$('#error-content').text('${msg_suffix_type_not_selected}');
					$('#error-dialog').show();
					return;
				}

				if ($('#suffix-input').val().trim() == '') {
					$('#error-content').text('${msg_suffix_blank}');
					$('#error-dialog').show();
					return;
				}

				if (currentPreType == PrefixType.replace && $('#new-suffix-input').val().trim() == '') {
					$('#error-content').text('${msg_new_suffix_blank}');
					$('#error-dialog').show();
					return;
				}

				var preLength = $('.suffix-copy-div').find('input[name="sort"]:checked').length;
				if (currentPreType == PrefixType.copy && preLength < 1) {
					$('#error-content').text('${msg_copy_type_not_selected}');
					$('#error-dialog').show();
					return;
				}
				showWaiting();
				$('#table-list-form').attr('action', $(this).val());
				goClean = 'suffix-group';
				makeEmpty();
				$('#table-list-form').submit();
			});
		});
	</script>

	<script type="text/javascript">
		// copy
		$(function() {
			$('#btn-copy').click(function() {
				hideAll();
				if (!shouldContinue()) {
					return;
				}
				$('#copy-group').show(waitTime);
				setTimeout(function() {
					scrollTo('#btn-copy-go');
				}, waitTime);
			});
			$('#btn-copy-go').click(function() {
				if (!shouldContinue()) {
					return;
				}

				if ($('#database-copy-select').val().trim() == '') {
					$('#error-content').text('${msg_select_database}');
					$('#error-dialog').show();
					return;
				}
				var radioChecked = false;
				$('#copy-type-div').find('input[type="radio"]').each(function(i) {
					if ($(this).is(':checked')) {
						radioChecked = true;
					}
				});

				if (!radioChecked) {
					$('#error-content').text('${msg_copy_type_not_selected}');
					$('#error-dialog').show();
					return;
				}

				showWaiting();
				$('#table-list-form').attr('action', $(this).val());
				goClean = 'copy-group';
				makeEmpty();
				$('#table-list-form').submit();
			});
		});
	</script>
</body>
</html>
