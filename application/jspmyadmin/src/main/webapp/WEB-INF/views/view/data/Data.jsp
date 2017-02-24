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

input[readonly] {
	background-color: #cccccc;
}

.CodeMirror {
	height: auto !important;
	background: #EEE none repeat scroll 0% 0%;
	margin-bottom: 5px;
}

#console {
	border-top: 1px solid #4390DF;
	border-bottom: 1px solid #4390DF;
	padding: 5px 10px;
	width: calc(100% -20px);
	height: auto;
	max-height: 200px;
	overflow-y: scroll;
	margin-top: 1em;
}

#footer {
	margin-top: 0px;
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
							<m:print key="lbl.view_data" />
						</h3>
					</div>
					<form action="#" accept-charset="utf-8" method="post"
						id="data-form">
						<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
						<input type="hidden" name="request_view" value="${requestScope.command.request_view}">
						<input type="hidden" name="token" id="token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.query" />
								&#40;
								<m:print key="lbl.execution_time" />
								: ${requestScope.command.exec_time}
								<m:print key="lbl.seconds" />
								&#41;
							</div>
							<div class="group-widget group-content">
								<textarea rows="5" cols="100" style="width: 100%;"
									id="sql-editor">${requestScope.command.query};</textarea>
							</div>
							<div class="group-widget group-footer">
								<a id="btn-refresh"
									href="${pageContext.request.contextPath}/view_data.html?token=${requestScope.command.reload_page}">
									<button type="button" class="btn">
										<m:print key="lbl.refresh" />
									</button>
								</a>
							</div>
						</div>

						<div class="group">
							<div class="group-widget group-header">
								<b><m:print key="lbl.selected_rows" /> &#40;<m:print
										key="lbl.page_no" />: ${requestScope.command.current_page}, <m:print
										key="lbl.displaying" />
									${requestScope.command.total_data_count} <m:print
										key="lbl.rows" />&#41;</b>

								<div
									style="display: inline-block; float: right; margin-right: 25px; margin-top: -1px;">
									<select name="limit" id="limit-list-select"
										style="margin-top: -9px;">
										<jma:notEmpty name="#limit_list" scope="command">
											<jma:forLoop items="#limit_list" name="limitItem"
												scope="command">
												<jma:switch>
													<jma:case value="#limitItem" name="#limit"
														scope="command,page">
														<option value="${limitItem}" selected="selected">${limitItem}</option>
													</jma:case>
													<jma:default>
														<option value="${limitItem}">${limitItem}</option>
													</jma:default>
												</jma:switch>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:switch name="#limit" scope="command">
											<jma:case value="0">
												<option value="0" selected="selected"><m:print
														key="lbl.show_all" />
												</option>
											</jma:case>
											<jma:default>
												<option value="0"><m:print key="lbl.show_all" />
												</option>
											</jma:default>
										</jma:switch>
									</select>
									<m:print key="lbl.rows_per_page" />
								</div>
								<div
									style="display: inline-block; float: right; margin-right: 20px;">
									<jma:switch name="#show_search" scope="command">
										<jma:case value="Yes">
											<input type="checkbox" id="show_search" name="show_search"
												value="Yes" checked="checked">

										</jma:case>
										<jma:default>
											<input type="checkbox" id="show_search" name="show_search"
												value="Yes">
										</jma:default>
									</jma:switch>
									<m:print key="lbl.display_search_criteria" />
								</div>
							</div>
							<div class="group-widget group-content">
								<div style="width: 100%; float: left;">
									<jma:notEmpty name="#previous_page" scope="command">
										<a
											href="${pageContext.request.contextPath}/view_data.html?token=${requestScope.command.previous_page}"><button
												type="button" class="btn" style="float: left;">
												&lt;
												<m:print key="lbl.previous" />
											</button></a>
									</jma:notEmpty>
									<jma:notEmpty name="#select_list" scope="command">
										<jma:switch name="#limit" scope="command">
											<jma:case value="0"></jma:case>
											<jma:default>
												<a
													href="${pageContext.request.contextPath}/view_data.html?token=${requestScope.command.next_page}"><button
														type="button" class="btn" style="float: right;">
														<m:print key="lbl.next" />
														&gt;
													</button></a>
											</jma:default>
										</jma:switch>
									</jma:notEmpty>
								</div>
								<div style="width: 100%; margin-top: 10px; overflow-y: auto;">
									<table class="tbl" id="data-rows">
										<thead>
											<tr id="column-header">
												<th></th>
												<jma:forLoop items="#column_name_map" name="sortInfo"
													key="columnName" scope="command">
													<th><a
														href="${pageContext.request.contextPath}/view_data.html?token=${sortInfo}">
															${columnName} <jma:if name="#sort_by" value="#columnName"
																scope="command,page">
																<jma:switch name="#sort_type" scope="command">
																	<jma:case value="DESC">
																		<img alt="" class="icon"
																			src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																	</jma:case>
																	<jma:case value="ASC">
																		<img alt="" class="icon"
																			src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																	</jma:case>
																</jma:switch>
															</jma:if>
													</a></th>
												</jma:forLoop>
											</tr>
											<jma:if name="#show_search" value="Yes" scope="command,">
												<tr id="search-tr">
													<th style="text-align: center;">
														<button type="button" id="search-btn" class="btn"
															title="Search" style="margin: 0px;">
															<img alt="Search" class="icon"
																src="${pageContext.request.contextPath}/components/icons/search-9.png">
														</button>
													</th>
													<jma:forLoop items="#column_name_map" name="sortInfo"
														key="columnName" scope="command" index="columnListIndex">
														<jma:fetch index="#columnListIndex"
															name="search_list_item" key="searchListItem" />
														<th><input type="hidden" name="search_columns"
															value="${columnName}"> <input type="text"
															style="width: 80px;" name="search_list"
															value="${searchListItem}"></th>
													</jma:forLoop>
												</tr>
											</jma:if>
										</thead>
										<tfoot>
											<tr>
												<th></th>
												<jma:forLoop items="#column_name_map" name="sortInfo"
													key="columnName" scope="command">
													<th><a
														href="${pageContext.request.contextPath}/view_data.html?token=${sortInfo}">
															${columnName} <jma:if name="#sort_by" value="#columnName"
																scope="command,page">
																<jma:switch name="#sort_type" scope="command">
																	<jma:case value="DESC">
																		<img alt="" class="icon"
																			src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
																	</jma:case>
																	<jma:case value="ASC">
																		<img alt="" class="icon"
																			src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
																	</jma:case>
																</jma:switch>
															</jma:if>
													</a></th>
												</jma:forLoop>
											</tr>
										</tfoot>
										<tbody>
											<jma:notEmpty name="#select_list" scope="command">
												<jma:forLoop items="#select_list" name="rowData"
													scope="command" index="rowIndex">
													<tr>
														<td></td>
														<jma:forLoop items="#rowData" name="item" scope="page">
															<td class="edit-td">${item}</td>
														</jma:forLoop>
													</tr>
												</jma:forLoop>

											</jma:notEmpty>
											<jma:empty name="#select_list" scope="command">
												<tr>
													<td colspan="${requestScope.command.column_count + 2}"><m:print
															key="msg.no_records_found" /></td>
												</tr>
											</jma:empty>
										</tbody>
									</table>
								</div>
								<div style="width: 100%; float: left;">
									<jma:notEmpty name="#previous_page" scope="command">
										<a
											href="${pageContext.request.contextPath}/view_data.html?token=${requestScope.command.previous_page}"><button
												type="button" class="btn" style="float: left;">
												&lt;
												<m:print key="lbl.previous" />
											</button></a>
									</jma:notEmpty>
									<jma:notEmpty name="#select_list" scope="command">
										<a
											href="${pageContext.request.contextPath}/view_data,html?token=${requestScope.command.next_page}"><button
												type="button" class="btn" style="float: right;">
												<m:print key="lbl.next" />
												&gt;
											</button></a>
									</jma:notEmpty>
								</div>
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

	<script type="text/javascript">
		$("#header-menu li:nth-child(1)").addClass('active');
		applyEvenOdd('#data-rows');

		var id = document.getElementById('sql-editor');
		var codeMirrorObj = CodeMirror.fromTextArea(id, {
			mode : 'text/x-mysql',
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : false,
			lineWrapping : true,
			matchBrackets : true,
			readOnly : true
		});

		$(function() {
			$('#limit-list-select').change(function() {
				showWaiting();
				$('#data-form').prop('action', Server.root + "/view_data.html");
				$('#data-form').submit();
			});

			$('#search-btn').click(function() {
				showWaiting();
				$('#data-form').prop('action', Server.root + "/view_data.html");
				$('#data-form').submit();
			});

			$('#show_search').change(function() {
				showWaiting();
				if ($(this).is(':checked')) {

				} else {
					$('#search-tr').remove();
				}
				$('#data-form').prop('action', Server.root + "/view_data.html");
				$('#data-form').submit();
			});

		});
	</script>

</body>
</html>