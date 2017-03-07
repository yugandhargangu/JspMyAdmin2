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

#new-table-id tr td {
	text-align: center;
}

#new-table-id tr td .icon {
	padding-left: 3px;
	padding-right: 3px;
	height: 20px;
	width: 20px;
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
						<form action="${pageContext.request.contextPath}/table_structure.html"
							accept-charset="utf-8" method="get">
							<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
							<input type="hidden" name="request_table" value="${requestScope.command.request_table}">
							<h3>
								<m:print key="lbl.alter_table" />
								<button type="submit" class="btn" id="alter-btn"
									style="float: right;">
									<m:print key="lbl.go_back" />
								</button>
							</h3>
						</form>
					</div>
					<form action="#" accept-charset="utf-8" method="post"
						id="alter-table-form">
						<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
						<input type="hidden" name="request_table" value="${requestScope.command.request_table}">
						<input type="hidden" name="token" class="server-token"
							value="${requestScope.command.token}"> <input
							type="hidden" name="action" id="action-input">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.table_structure" />
							</div>
							<div class="group-widget group-content">

								<div style="display: block;">
									<div class="form-input">
										<input type="hidden" name="old_table_name"
											value="${requestScope.command.old_table_name}"> <label><m:print
												key="lbl.table_name" /></label> <input type="text"
											name="new_table_name" class="form-control"
											id="table-name-input"
											value="${requestScope.command.old_table_name}">
									</div>
									<div class="form-input">
										<input type="hidden" name="old_collation"
											value="${requestScope.command.old_collation}"> <label><m:print
												key="lbl.collation" /></label> <select name="new_collation"
											class="form-control">
											<option value=""><m:print key="lbl.default" /></option>
											<jma:notEmpty name="#collation_map" scope="command">
												<jma:forLoop items="#collation_map" name="collationList"
													scope="command" key="collationKey">
													<optgroup label="${collationKey}">
														<jma:forLoop items="#collationList" name="collationValue"
															scope="page">
															<jma:switch name="#collationValue" scope="page">
																<jma:case value="#old_collation" scope="command">
																	<option value="${collationValue}" selected="selected">${collationValue}</option>
																</jma:case>
																<jma:default>
																	<option value="${collationValue}">${collationValue}</option>
																</jma:default>
															</jma:switch>
														</jma:forLoop>
													</optgroup>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<input type="hidden" name="old_engine"
											value="${requestScope.command.old_engine}"> <label><m:print
												key="lbl.storage_engine" /></label> <select name="new_engine"
											class="form-control">
											<jma:notEmpty name="#engine_list" scope="command">
												<jma:forLoop items="#engine_list" name="engineValue"
													scope="command">
													<jma:switch name="#engineValue" scope="page">
														<jma:case value="#old_engine" scope="command">
															<option value="${engineValue}" selected="selected">${engineValue}</option>
														</jma:case>
														<jma:default>
															<option value="${engineValue}">${engineValue}</option>
														</jma:default>
													</jma:switch>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<input type="hidden" name="old_comments"
											value="${requestScope.command.old_comments}"> <label><m:print
												key="lbl.comment" /></label> <input name="new_comments" type="text"
											value="${requestScope.command.old_comments}"
											class="form-control" style="width: 350px;">
									</div>
								</div>

								<table class="tbl" id="new-table-id">
									<thead>
										<tr>
											<th style="min-width: 80px;">&nbsp;</th>
											<th><m:print key="lbl.column_name" /></th>
											<th><m:print key="lbl.datatype" /></th>
											<th><m:print key="lbl.length_value" /></th>
											<th><m:print key="lbl.default" /></th>
											<th><m:print key="lbl.collation" /></th>
											<th>PK</th>
											<th>NN</th>
											<th>UQ</th>
											<th>BIN</th>
											<th>UN</th>
											<th>ZF</th>
											<th>AI</th>
											<th><m:print key="lbl.comment" /></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th colspan="14"><span class="gap-item">
													PK-Primary Key </span> <span class="gap-item"> NN-Not Null
											</span> <span class="gap-item"> UQ-Unique </span> <span
												class="gap-item"> BIN-Binary </span> <span class="gap-item">
													UN-Unsigned </span> <span class="gap-item"> ZF-Zero Fill </span> <span
												class="gap-item"> AI-Auto Increment </span></th>
										</tr>
									</tfoot>
									<tbody>
										<jma:forLoop items="#old_columns" name="column"
											scope="command" index="_index">
											<jma:fetch index="#_index" name="columns" key="column_name" />
											<jma:fetch index="#_index" name="datatypes" key="datatype" />
											<jma:fetch index="#_index" name="lengths" key="length" />
											<jma:fetch index="#_index" name="defaults" key="defaultVal" />
											<jma:fetch index="#_index" name="collations" key="collation" />
											<jma:fetch index="#_index" name="pks" key="pk" />
											<jma:fetch index="#_index" name="nns" key="nn" />
											<jma:fetch index="#_index" name="uqs" key="uq" />
											<jma:fetch index="#_index" name="uns" key="un" />
											<jma:fetch index="#_index" name="zfs" key="zf" />
											<jma:fetch index="#_index" name="ais" key="ai" />
											<jma:fetch index="#_index" name="comments" key="comment" />
											<tr>
												<td><img alt="" class="icon" onclick="moveTrUp(this);"
													src="${pageContext.request.contextPath}/components/icons/arrow-up-24.png"><img
													alt="" class="icon" onclick="moveTrDown(this);"
													src="${pageContext.request.contextPath}/components/icons/arrow-down-24.png"><img
													alt="" class="icon" onclick="removeThisTr(this);"
													src="${pageContext.request.contextPath}/components/icons/minus-r.png">
													<input type="hidden" name="changes"> <input
													type="hidden" name="deletes"> <input type="hidden"
													name="old_columns" value="${column}"></td>
												<td><input type="text" name="columns"
													onchange="callColumnNameChange(this);"
													value="${column_name}" class="form-control"
													style="width: 150px;"></td>
												<td><select name="datatypes" class="form-control"
													onchange="callApplyDatatype(this, true);">
														<jma:notEmpty name="#data_types_map" scope="command">
															<jma:forLoop items="#data_types_map" name="dataTypeList"
																scope="command" key="dataTypeKey">
																<optgroup label="${dataTypeKey}">
																	<jma:forLoop items="#dataTypeList" name="dataTypeValue"
																		scope="page">
																		<jma:switch name="#datatype" scope="page">
																			<jma:case value="#dataTypeValue" scope="page">
																				<option value="${dataTypeValue}" selected="selected">${dataTypeValue}</option>
																			</jma:case>
																			<jma:default>
																				<option value="${dataTypeValue}">${dataTypeValue}</option>
																			</jma:default>
																		</jma:switch>

																	</jma:forLoop>
																</optgroup>
															</jma:forLoop>
														</jma:notEmpty>
												</select></td>
												<td><input type="text" name="lengths" value="${length}"
													class="form-control" style="width: 80px;"></td>
												<td><input type="text" name="defaults"
													class="form-control" value="${defaultVal}"></td>
												<td><select name="collations" class="form-control">
														<option value=""><m:print key="lbl.default" /></option>
														<jma:notEmpty name="#collation_map" scope="command">
															<jma:forLoop items="#collation_map" name="collationList"
																scope="command" key="collationKey">
																<optgroup label="${collationKey}">
																	<jma:forLoop items="#collationList"
																		name="collationValue" scope="page">
																		<jma:switch name="#collation" scope="page">
																			<jma:case value="#collationValue" scope="page">
																				<option value="${collationValue}"
																					selected="selected">${collationValue}</option>
																			</jma:case>
																			<jma:default>
																				<option value="${collationValue}">${collationValue}</option>
																			</jma:default>
																		</jma:switch>
																	</jma:forLoop>
																</optgroup>
															</jma:forLoop>
														</jma:notEmpty>
												</select></td>
												<td><jma:switch name="#pk" scope="page">
														<jma:case value="1">
															<input type="hidden" name="pks" value="1">
															<input type="checkbox" checked="checked"
																onchange="callSetValue(this);callPrimaryKey(this);">
														</jma:case>
														<jma:default>
															<input type="hidden" name="pks" value="0">
															<input type="checkbox"
																onchange="callSetValue(this);callPrimaryKey(this);">
														</jma:default>
													</jma:switch></td>
												<td><jma:switch name="#nn" scope="page">
														<jma:case value="1">
															<input type="hidden" name="nns" value="1">
															<input type="checkbox" checked="checked"
																onchange="callSetValue(this);">
														</jma:case>
														<jma:default>
															<input type="hidden" name="nns" value="0">
															<input type="checkbox" onchange="callSetValue(this);">
														</jma:default>
													</jma:switch></td>
												<td><jma:switch name="#uq" scope="page">
														<jma:case value="1">
															<input type="hidden" name="uqs" value="1">
															<input type="checkbox" checked="checked"
																onchange="callSetValue(this);">
														</jma:case>
														<jma:default>
															<input type="hidden" name="uqs" value="0">
															<input type="checkbox" onchange="callSetValue(this);">
														</jma:default>
													</jma:switch></td>
												<td><input type="hidden" name="bins" value="0">
													<input type="checkbox" onchange="callSetValue(this);"
													disabled="disabled"></td>
												<td><jma:switch name="#un" scope="page">
														<jma:case value="1">
															<input type="hidden" name="uns" value="1">
															<input type="checkbox" checked="checked"
																onchange="callSetValue(this);">
														</jma:case>
														<jma:default>
															<input type="hidden" name="uns" value="0">
															<input type="checkbox" onchange="callSetValue(this);">
														</jma:default>
													</jma:switch></td>
												<td><jma:switch name="#zf" scope="page">
														<jma:case value="1">
															<input type="hidden" name="zfs" value="1">
															<input type="checkbox" checked="checked"
																onchange="callSetValue(this);">
														</jma:case>
														<jma:default>
															<input type="hidden" name="zfs" value="0">
															<input type="checkbox" onchange="callSetValue(this);">
														</jma:default>
													</jma:switch></td>
												<td><jma:switch name="#ai" scope="page">
														<jma:case value="1">
															<input type="hidden" name="ais" value="1">
															<input type="checkbox" checked="checked"
																onchange="callSetValue(this);applyAutoIncrement(this);">
														</jma:case>
														<jma:default>
															<input type="hidden" name="ais" value="0">
															<input type="checkbox"
																onchange="callSetValue(this);applyAutoIncrement(this);">
														</jma:default>
													</jma:switch></td>
												<td><input type="text" name="comments"
													value="${comment}" class="form-control"></td>
											</tr>
										</jma:forLoop>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<input type="number" style="width: 50px;" maxlength="3" min="1"
									max="999" value="1" id="no-of-columns">
								<button type="button" class="btn" id="add-column-btn">
									<m:print key="lbl.add_column" />
								</button>
								<button type="button" class="btn" id="show-create-go">
									<m:print key="lbl.show_create" />
								</button>
								<button type="button" class="btn" id="btn-go">
									<m:print key="lbl.run" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="group">
				<div class="group-widget group-normal">
					<p style="color: red;">
						<m:print key="note.table_create" />
					</p>
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
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>
	<div style="display: none;">
		<form action="${pageContext.request.contextPath}/table_structure.html"
			id="success-form" method="get">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="request_table" value="${requestScope.command.request_table}">
			<input type="hidden" name="token" id="success-token">
		</form>
	</div>

	<m:store name="msg_primary_key_one_column_alert"
		key="msg.primary_key_one_column_alert" />
	<m:store name="msg_auto_increment_one_column_alert"
		key="msg.auto_increment_one_column_alert" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(2)").addClass('active');
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

		var dataTypesJson = eval('(${applicationScope.data_types_info})');
		var img = '<img alt="" class="icon" onclick="moveTrUp(this);" src="' + Server.root
				+ '/components/icons/arrow-up-24.png"><img alt="" class="icon" onclick="moveTrDown(this);"src="' + Server.root
				+ '/components/icons/arrow-down-24.png"><img alt="" class="icon" onclick="removeThisTr(this);" src="' + Server.root
				+ '/components/icons/minus-r.png"><input type="hidden" name="changes" value="1">'
				+ '<input type="hidden" name="deletes"> <input type="hidden" name="old_columns" value="">';
		var columnInfo = '';
		var validInfo = true;

		function setChangeValueHidden(tr) {
			$(tr).find('td input[name="changes"]').val('1');
		}

		function setDeleteValueHidden(tr) {
			$(tr).find('td input[name="changes"]').val('1');
			$(tr).find('td input[name="deletes"]').val('1');
		}

		function callColumnNameChange(selector) {
			setChangeValueHidden($(selector).parent().parent());
		}

		function callSetValue(checkbox) {
			setChangeValueHidden($(checkbox).parent().parent());
			var td = $(checkbox).parent();
			var input = $(td).find('input[type="hidden"]');
			if ($(checkbox).is(':checked')) {
				$(input).val('1');
			} else {
				$(input).val('0');
			}
		}

		function callApplyDatatype(selector, status) {
			if (status) {
				setChangeValueHidden($(selector).parent().parent());
			}
			var val = $(selector).val();
			var tr = $(selector).parent().parent();
			var props = dataTypesJson[val];
			var text = $(tr).find('td').eq(3).find('input[type="text"]');
			if (props.a == 1) {
				// has list
				$(text).prop('readonly', false);
				$(text).focus();
			} else {
				if (props.b < 0) {
					// no length
					$(text).prop('readonly', true);
				} else {
					// has length
					$(text).prop('readonly', false);
				}
			}

			var td = $(tr).find('td').eq(12);
			if (props.c == 1) {
				// ai
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', false);
				$(td).find('input[type="hidden"]').val('0');
			} else {
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', true);
				$(td).find('input[type="hidden"]').val('0');
			}

			var td = $(tr).find('td').eq(10);
			if (props.d == 1) {
				// unsigned
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', false);
				$(td).find('input[type="hidden"]').val('0');
			} else {
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', true);
				$(td).find('input[type="hidden"]').val('0');
			}

			var td = $(tr).find('td').eq(11);
			if (props.e == 1) {
				// zero fill
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', false);
				$(td).find('input[type="hidden"]').val('0');
			} else {
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', true);
				$(td).find('input[type="hidden"]').val('0');
			}

			var td = $(tr).find('td').eq(9);
			if (props.f == 1) {
				// binary
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', false);
				$(td).find('input[type="hidden"]').val('0');
			} else {
				$(td).find('input[type="checkbox"]').prop('checked', false);
				$(td).find('input[type="checkbox"]').prop('disabled', true);
				$(td).find('input[type="hidden"]').val('0');
			}

			if (props.g == '-1') {
				// default
			} else {

			}

			var td = $(tr).find('td').eq(5);
			var select = $(td).find('select');
			if (props.h == 1) {
				// character set
				$(select).val('');
				$(select).find('option').each(function(i, item) {
					if (i > 0) {
						$(this).prop('disabled', false);
					}
				});
			} else {
				$(select).find('option').each(function(i, item) {
					if (i > 0) {
						$(this).prop('disabled', true);
					}
				});
			}
			applyAutoIncrement();
		}

		function callPrimaryKey(selector) {
			setChangeValueHidden($(selector).parent().parent());
			var chkLen = 0;
			var trs = $('#new-table-id').find('tr');
			$(trs).each(function(index, item) {
				if ($(item).find('td').length > 0) {
					var input = $(item).find('td').eq(6).find('input[type="checkbox"]:not(:disabled)');
					if ($(input).is(':checked')) {
						chkLen++;
					}
				}
			});
			if (chkLen > 1) {
				$(selector).prop('checked', false);
				var td = $(selector).parent();
				$(td).find('input[type="hidden"]').val('0');
				$('#sidebar-error-msg').text('${msg_primary_key_one_column_alert}');
				$('#sidebar-error-dialog').show();
				return;
			}

			if ($(selector).is(':checked')) {
				var tr = $(selector).parent().parent();
				var td = $(tr).find('td').eq(7);
				$(td).find('input[type="checkbox"]').prop('checked', true);
				$(td).find('input[type="hidden"]').val('1');
			}
		}

		// to enable or disable AI checkbox values
		function applyAutoIncrement(selector) {
			setChangeValueHidden($(selector).parent().parent());
			var chkLen = 0;
			var trs = $('#new-table-id').find('tr');
			$(trs).each(function(index, item) {
				if ($(item).find('td').length > 0) {
					var input = $(item).find('td').eq(12).find('input[type="checkbox"]:not(:disabled)');
					if ($(input).is(':checked')) {
						chkLen++;
					}
				}
			});
			if (chkLen > 1) {
				$(selector).prop('checked', false);
				var td = $(selector).parent();
				$(td).find('input[type="hidden"]').val('0');
				$('#sidebar-error-msg').text('${msg_auto_increment_one_column_alert}');
				$('#sidebar-error-dialog').show();
			}
		}

		function moveTrUp(selector) {
			var tr = $(selector).parent().parent();
			var length = $('#new-table-id tr').length - 3;
			if ($(tr).index() == 0) {
				return;
			}
			setChangeValueHidden($(tr));
			var row = $(selector).parents("tr:first");
			row.insertBefore(row.prev());
		}

		function moveTrDown(selector) {
			var tr = $(selector).parent().parent();
			var tr = $(selector).parent().parent();
			var length = $('#new-table-id tr').length - 3;
			if ($(tr).index() == length) {
				return;
			}
			setChangeValueHidden($(tr));
			var row = $(selector).parents("tr:first");
			row.insertAfter(row.next());
		}

		function removeThisTr(selector) {
			setDeleteValueHidden($(selector).parent().parent());
			$('#no-of-columns').prop('readonly', false);
			$('#add-column-btn').prop('disabled', false);
			var tr = $(selector).parent().parent();
			$(tr).hide();
		}

		function fetchColumn() {
			$.ajax({
				url : Server.root + '/database_structure_utils.text',
				method : 'POST',
				data : {
					'type' : 'column',
					'prefix' : 'Default',
					'sort' : img
				},
				success : function(result) {
					if (result == '') {
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
						return;
					}
					var text = decode(result);
					if (text == '') {
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
						return;
					}
					var jsonText = $.parseJSON(text);
					$('.server-token').val(jsonText.token);
					if (jsonText.err != '') {
						$('#sidebar-error-msg').text(jsonText.err);
						$('#sidebar-error-dialog').show();
						return;
					}
					columnInfo = jsonText.data;

				},
				error : function(result) {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
				}
			});
		}
	</script>

	<m:store name="msg_table_name_blank" key="msg.table_name_blank" />
	<script type="text/javascript">
		$(function() {

			fetchColumn();
			applyAutoIncrement();
			$('#new-table-id tr td select[name="datatypes"]').each(function() {
				callApplyDatatype($(this), false);
			});
			$('#add-column-btn').click(function() {
				showWaiting();
				setTimeout(function() {
					var trlength = $('#new-table-id').find('tr').length - 2;
					trlength = 999 - trlength;
					var length = 1;
					var data = $('#no-of-columns').val();
					if (data == parseInt(data)) {
						length = parseInt(data);
						if (length > trlength) {
							length = 1;
						}
					}
					for (var i = 0; i < length; i++) {
						$('#new-table-id').append(columnInfo);
					}
					applyAutoIncrement();
					trlength = $('#new-table-id').find('tr').length - 2;
					if (trlength >= 999) {
						$('#no-of-columns').prop('readonly', true);
						$('#add-column-btn').prop('disabled', true);
					}
					hideWaiting();
				}, 20);
			});

			$('#btn-go').click(function() {
				if ($('#table-name-input').val().trim() == '') {
					$('#sidebar-error-msg').text('${msg_table_name_blank}');
					$('#sidebar-error-dialog').show();
					return;
				}
				showWaiting();
				$('#action-input').val('Yes');
				$.ajax({
					url : Server.root + '/table_alter_post.text',
					method : 'POST',
					data : $('#alter-table-form').serialize(),
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
						if (jsonText.err != '') {
							hideWaiting();
							$('#sidebar-error-msg').text(jsonText.err);
							$('#sidebar-error-dialog').show();
							return;
						}
						$('#success-token').val(jsonText.msg);
						$('#success-form').submit();
					},
					error : function(result) {
						hideWaiting();
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
					}
				});
			});

			$('#show-create-go').click(function() {
				if ($('#table-name-input').val().trim() == '') {
					$('#sidebar-error-msg').text('${msg_table_name_blank}');
					$('#sidebar-error-dialog').show();
					return;
				}
				showWaiting();
				$('#action-input').val('No');
				$.ajax({
					url : Server.root + '/table_alter_post.text',
					method : 'POST',
					data : $('#alter-table-form').serialize(),
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
						if (jsonText.err != '') {
							hideWaiting();
							$('#sidebar-error-msg').text(jsonText.err);
							$('#sidebar-error-dialog').show();
							return;
						}
						hideWaiting();
						result = jsonText.data;
						result += ';';
						$('#show-create-group').show(500);
						setTimeout(function() {
							scrollTo('#show-create-span');
							codeMirrorObj.setValue(result);
							codeMirrorObj.refresh();
							codeMirrorObj.focus();
						}, 500);

					},
					error : function(result) {
						hideWaiting();
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
					}
				});
			});

			$('#show-create-close').click(function() {
				$('#show-create-group').hide(500);
			});
		});
	</script>
</body>
</html>