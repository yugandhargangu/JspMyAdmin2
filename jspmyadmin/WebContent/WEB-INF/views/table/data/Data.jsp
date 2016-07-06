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
				<div style="padding: 0.2em 0.2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.table_data" />
						</h3>
					</div>
					<form action="#" accept-charset="utf-8" method="post"
						id="data-form">
						<input type="hidden" name="token" id="token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.query_execution_time" />
								${requestScope.command.exec_time}
								<m:print key="lbl.seconds" />
							</div>
							<div class="group-widget group-content">
								<textarea rows="5" cols="100" style="width: 100%;"
									id="sql-editor">${requestScope.command.query};</textarea>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="go_to_console">
									<m:print key="lbl.go_to_console" />
								</button>
								<a id="btn-refresh"
									href="${pageContext.request.contextPath}/table_data?token=${requestScope.command.reload_page}">
									<button type="button" class="btn">
										<m:print key="lbl.refresh" />
									</button>
								</a>
							</div>
						</div>

						<jma:empty name="#primary_key" scope="command">
							<div class="group">
								<div class="group-widget group-normal">
									<p style="color: red;">
										<m:print key="note.table_data" />
									</p>
								</div>
							</div>
						</jma:empty>

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
											href="${pageContext.request.contextPath}/table_data?token=${requestScope.command.previous_page}"><button
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
													href="${pageContext.request.contextPath}/table_data?token=${requestScope.command.next_page}"><button
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
												<th style="text-align: center;"><input type="checkbox"
													id="check-all-1"></th>
												<th></th>
												<jma:forLoop items="#column_name_map" name="sortInfo"
													key="columnName" scope="command">
													<th><a
														href="${pageContext.request.contextPath}/table_data?token=${sortInfo}">
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
													<th></th>
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
												<th style="text-align: center;"><input type="checkbox"
													id="check-all-2"></th>
												<th></th>
												<jma:forLoop items="#column_name_map" name="sortInfo"
													key="columnName" scope="command">
													<th><a
														href="${pageContext.request.contextPath}/table_data?token=${sortInfo}">
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
														<td style="text-align: center;"><jma:notEmpty
																name="#primary_key" scope="command">
																<jma:fetch index="#rowIndex" name="primary_key_item"
																	key="pkValue" />
																<input type="checkbox" name="ids" class="record-id"
																	value="${pkValue}">
															</jma:notEmpty></td>
														<td><jma:notEmpty name="#primary_key" scope="command">
																<m:store name="lbl_delete" key="lbl.delete" />
																<m:store name="lbl_edit" key="lbl.edit" />
																<img alt="${lbl_delete}" title="${lbl_delete}"
																	class="icon delete-btn"
																	src="${pageContext.request.contextPath}/components/icons/minus-r.png">
																<img alt="${lbl_edit}" title="${lbl_edit}"
																	class="icon edit-btn"
																	src="${pageContext.request.contextPath}/components/icons/edit-24.png">
															</jma:notEmpty></td>
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
											href="${pageContext.request.contextPath}/table_data?token=${requestScope.command.previous_page}"><button
												type="button" class="btn" style="float: left;">
												&lt;
												<m:print key="lbl.previous" />
											</button></a>
									</jma:notEmpty>
									<jma:notEmpty name="#select_list" scope="command">
										<a
											href="${pageContext.request.contextPath}/table_data?token=${requestScope.command.next_page}"><button
												type="button" class="btn" style="float: right;">
												<m:print key="lbl.next" />
												&gt;
											</button></a>
									</jma:notEmpty>
								</div>
							</div>
							<div class="group-widget group-footer">
								<jma:notEmpty name="#primary_key" scope="command">
									<button type="button" class="btn" id="btn-delete">
										<m:print key="lbl.delete" />
									</button>
								</jma:notEmpty>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="console">
				<b style="color: #0072C6;"><m:print key="lbl.console" />:</b>
				<textarea rows="50" cols="4" style="width: 100%;" id="console-text"></textarea>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>
	<jma:notEmpty name="#primary_key" scope="command">
		<div class="dialog" id="info-dialog" style="display: none;">
			<div class="dialog-widget dialog-success">
				<div class="dialog-header">
					<m:print key="lbl.success" />
				</div>
				<div class="dialog-content">
					<p id="info-content"></p>
				</div>
				<div class="dialog-footer">
					<button type="button" class="btn" id="ok_btn">OK</button>
				</div>
			</div>

		</div>
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
						<m:print key="msg.delete_table_data_alert" />
					</p>
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
	</jma:notEmpty>

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
			extraKeys : {
				"Ctrl-Space" : "autocomplete"
			},
			readOnly : true
		});

		$(function() {
			$('#limit-list-select').change(function() {
				showWaiting();
				$('#data-form').prop('action', Server.root + "/table_data");
				$('#data-form').submit();
			});

			$('#search-btn').click(function() {
				showWaiting();
				$('#data-form').prop('action', Server.root + "/table_data");
				$('#data-form').submit();
			});

			$('#show_search').change(function() {
				showWaiting();
				if ($(this).is(':checked')) {

				} else {
					$('#search-tr').remove();
				}
				$('#data-form').prop('action', Server.root + "/table_data");
				$('#data-form').submit();
			});

			$('#check-all-1').change(function() {
				if ($(this).is(':checked')) {
					$('#check-all-2').prop('indeterminate', false);
					$('#check-all-2').prop('checked', true);
					$('.record-id').prop('checked', true);
				} else {
					$('#check-all-2').prop('indeterminate', false);
					$('#check-all-2').prop('checked', false);
					$('.record-id').prop('checked', false);
				}
			});

			$('#check-all-2').change(function() {
				if ($(this).is(':checked')) {
					$('#check-all-1').prop('indeterminate', false);
					$('#check-all-1').prop('checked', true);
					$('.record-id').prop('checked', true);
				} else {
					$('#check-all-1').prop('indeterminate', false);
					$('#check-all-1').prop('checked', false);
					$('.record-id').prop('checked', false);
				}
			});
			$('.record-id').change(function() {
				if ($('.record-id:checked').length == 0) {
					$('#check-all-1').prop('indeterminate', false);
					$('#check-all-2').prop('indeterminate', false);
					$('#check-all-1').prop('checked', false);
					$('#check-all-2').prop('checked', false);
				} else if ($('.record-id:checked').length == $('.record-id').length) {
					$('#check-all-1').prop('indeterminate', false);
					$('#check-all-2').prop('indeterminate', false);
					$('#check-all-1').prop('checked', true);
					$('#check-all-2').prop('checked', true);
				} else {
					$('#check-all-1').prop('indeterminate', true);
					$('#check-all-2').prop('indeterminate', true);
				}
			});
		});
	</script>

	<jma:notEmpty name="#primary_key" scope="command">
		<m:store name="msg_row_affected" key="msg.row_affected" />
		<m:store name="msg_select_least_one_record"
			key="msg.select_least_one_record" />
		<script type="text/javascript">
			var row_msg = '${msg_row_affected}';
			var alert_msg = '${msg_select_least_one_record}';
			var originalState = false;
			var checkbox = null;
			$(function() {
				$('.delete-btn').click(function() {
					$('.record-id:checked').each(function() {
						$(this).prop('checked', false);
					});
					checkbox = $(this).parent().parent().find('td input[name="ids"]');
					originalState = $(checkbox).is(':checked');
					$(checkbox).prop('checked', true);
					$('#confirm-dialog').show();
				});

				$('#btn-delete').click(function() {
					if ($('.record-id:checked').length == 0) {
						$('#error-content').text(alert_msg);
						$('#error-dialog').show();
					} else {
						$('#confirm-dialog').show();
					}
				});

				$('#edit-btn').click(function() {
					// XXX
				});

				$('#yes_btn').click(function() {
					$('#confirm-dialog').hide();
					showWaiting();
					$.ajax({
						url : Server.root + '/table_data_delete.text',
						method : 'POST',
						data : $('#data-form').serialize(),
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
							if (jsonText.err != '') {
								hideWaiting();
								$('#sidebar-error-msg').text(jsonText.err);
								$('#sidebar-error-dialog').show();
								return;
							}
							hideWaiting();
							result = jsonText.data;
							$('#info-content').text(result + row_msg);
							$('#info-dialog').show();
						},
						error : function(result) {
							hideWaiting();
							$('#sidebar-error-msg').text(Msgs.errMsg);
							$('#sidebar-error-dialog').show();
						}
					});
				});

				$('#ok_btn').click(function() {
					$('#info-dialog').hide();
					showWaiting();
					window.location.href = $('#btn-refresh').attr('href');
				});

				$('#error-close').click(function() {
					$('#error-content').text('');
					$('#error-dialog').hide();
				});

				$('#confirm-close').click(function() {
					$('#confirm-dialog').hide();
				});

				$('#no_btn').click(function() {
					if (checkbox != null) {
						$(checkbox).prop('checked', originalState);
						checkbox = null;
					}
					$('#confirm-dialog').hide();
				});
			});
		</script>

		<script type="text/javascript">
			var originalData = '';
			var columns = [];
			$('#column-header th').each(function() {
				columns.push($(this).text());
			});

			var id1 = document.getElementById('console-text');
			var codeMirrorObjConsole = CodeMirror.fromTextArea(id1, {
				mode : 'text/x-mysql',
				indentWithTabs : true,
				smartIndent : true,
				lineNumbers : true,
				lineWrapping : true,
				matchBrackets : true,
				extraKeys : {
					"Ctrl-Space" : "autocomplete"
				},
				readOnly : true
			});

			function callUpdate(input, td) {
				showWaiting();
				var columnName = columns[$(td).index()];
				var tr = $(td).parent();
				var primaryKey = $(tr).find('td input[name="ids"]').val();
				$.ajax({
					url : Server.root + '/table_data_update.text',
					method : 'POST',
					data : {
						'column_name' : columnName,
						'column_value' : $(input).val(),
						'primary_key' : primaryKey,
						'token' : $('#token').val()
					},
					success : function(result) {
						if (result == '') {
							$(td).text(originalData);
							hideWaiting();
							$('#sidebar-error-msg').text(Msgs.errMsg);
							$('#sidebar-error-dialog').show();
							return;
						}
						var text = decode(result);
						if (text == '') {
							$(td).text(originalData);
							hideWaiting();
							$('#sidebar-error-msg').text(Msgs.errMsg);
							$('#sidebar-error-dialog').show();
							return;
						}
						var jsonText = $.parseJSON(text);
						if (jsonText.err != '') {
							$(td).text(originalData);
							hideWaiting();
							$('#sidebar-error-msg').text(jsonText.err);
							$('#sidebar-error-dialog').show();
							return;
						}
						$(td).text($(input).val());
						hideWaiting();
						result = jsonText.data;
						result = codeMirrorObjConsole.getValue() + result + row_msg + '\n';
						codeMirrorObjConsole.setValue(result);
						codeMirrorObjConsole.refresh();
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
				$('#go_to_console').click(function() {
					scrollTo('#console');
				});

				$('.edit-td').dblclick(function() {
					if ($(this).find('a').length > 0) {
						return;
					}
					originalData = $(this).text();
					var $this = $(this);
					var $input = $('<input>', {
						value : $this.text(),
						type : 'text',
						blur : function() {
							callUpdate($input, $this);
						},
						keyup : function(e) {
							if (e.which === 13) {
								$input.blur();
							} else if (e.which == 27) {
								$this.text(originalData);
							}
						}
					}).appendTo($this.empty()).focus();
				});
			});

			$(document).ready(function() {
				$(window).keydown(function(event) {
					if (event.keyCode == 13) {
						event.preventDefault();
						return false;
					}
				});
			});
		</script>
	</jma:notEmpty>
</body>
</html>