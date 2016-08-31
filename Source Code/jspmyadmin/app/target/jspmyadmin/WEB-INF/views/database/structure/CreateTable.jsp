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
			<div id="header-div"><jsp:include
					page="../../database/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.1em 0.2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.create_table" />
						</h3>
					</div>
					<form action="#" accept-charset="utf-8" method="post"
						id="create-table-form">
						<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
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
										<label><m:print key="lbl.table_name" /></label> <input
											type="text" name="table_name" class="form-control"
											id="table-name-input"
											value="${requestScope.command.table_name}">
									</div>
									<div class="form-input">
										<label><m:print key="lbl.collation" /></label> <select
											name="collation" class="form-control">
											<option value=""><m:print key="lbl.default" /></option>
											<jma:notEmpty name="#collation_map" scope="command">
												<jma:forLoop items="#collation_map" name="collationList"
													scope="command" key="collationKey">
													<optgroup label="${collationKey}">
														<jma:forLoop items="#collationList" name="collationValue"
															scope="page">
															<option value="${collationValue}">${collationValue}</option>
														</jma:forLoop>
													</optgroup>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label><m:print key="lbl.storage_engine" /></label> <select
											name="engine" class="form-control">
											<jma:notEmpty name="#engine_list" scope="command">
												<jma:forLoop items="#engine_list" name="engineValue"
													scope="command">
													<option value="${engineValue}">${engineValue}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label><m:print key="lbl.comment" /></label> <input
											name="comment" type="text" class="form-control"
											style="width: 350px;">
									</div>
								</div>

								<table class="tbl" id="new-table-id">
									<thead>
										<tr>
											<th></th>
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
										<tr>
											<td></td>
											<td><input type="text" name="columns"
												class="form-control" style="width: 150px;"></td>
											<td><select name="datatypes" class="form-control"
												onchange="callApplyDatatype(this);">
													<jma:notEmpty name="#data_types_map" scope="command">
														<jma:forLoop items="#data_types_map" name="dataTypeList"
															scope="command" key="dataTypeKey">
															<optgroup label="${dataTypeKey}">
																<jma:forLoop items="#dataTypeList" name="dataTypeValue"
																	scope="page">
																	<option value="${dataTypeValue}">${dataTypeValue}</option>
																</jma:forLoop>
															</optgroup>
														</jma:forLoop>
													</jma:notEmpty>
											</select></td>
											<td><input type="text" name="lengths"
												class="form-control" style="width: 80px;"></td>
											<td><input type="text" name="defaults"
												class="form-control"></td>
											<td><select name="collations" class="form-control">
													<option value=""><m:print key="lbl.default" /></option>
													<jma:notEmpty name="#collation_map" scope="command">
														<jma:forLoop items="#collation_map" name="collationList"
															scope="command" key="collationKey">
															<optgroup label="${collationKey}">
																<jma:forLoop items="#collationList"
																	name="collationValue" scope="page">
																	<option value="${collationValue}" disabled="disabled">${collationValue}</option>
																</jma:forLoop>
															</optgroup>
														</jma:forLoop>
													</jma:notEmpty>
											</select></td>
											<td><input type="hidden" name="pks" value="0"> <input
												type="checkbox"
												onchange="callSetValue(this);callPrimaryKey(this);"></td>
											<td><input type="hidden" name="nns" value="0"> <input
												type="checkbox" onchange="callSetValue(this);"></td>
											<td><input type="hidden" name="uqs" value="0"> <input
												type="checkbox" onchange="callSetValue(this);"></td>
											<td><input type="hidden" name="bins" value="0">
												<input type="checkbox" onchange="callSetValue(this);"
												disabled="disabled"></td>
											<td><input type="hidden" name="uns" value="0"> <input
												type="checkbox" onchange="callSetValue(this);"></td>
											<td><input type="hidden" name="zfs" value="0"> <input
												type="checkbox" onchange="callSetValue(this);"></td>
											<td><input type="hidden" name="ais" value="0"> <input
												type="checkbox"
												onchange="callSetValue(this);applyAutoIncrement(this);"></td>
											<td><input type="text" name="comments"
												class="form-control"></td>
										</tr>
									</tbody>
								</table>
								<jma:if name="#partition_support" value="true" scope="command,">
									<div style="display: block;">
										<div class="form-input">
											<label><m:print key="lbl.partition" /></label> <select
												name="partition" class="form-control">
												<option value=""><m:print
														key="lbl.select_partition" /></option>
												<jma:notEmpty name="#partition_list" scope="command">
													<jma:forLoop items="#partition_list" name="partitionValue"
														scope="command">
														<option value="${partitionValue}">${partitionValue}</option>
													</jma:forLoop>
												</jma:notEmpty>
											</select>
										</div>
										<div class="form-input">
											<label><m:print key="lbl.partition_value" /></label> <input
												name="partition_val" type="text" class="form-control">
										</div>
										<div class="form-input">
											<label><m:print key="lbl.partitions" /></label> <input
												type="number" name="partitions" class="form-control">
										</div>
									</div>
								</jma:if>
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
		<form action="${pageContext.request.contextPath}/database_structure.html"
			id="success-form" method="get">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token" id="success-token">
		</form>
	</div>

	<m:store name="msg_primary_key_one_column_alert"
		key="msg.primary_key_one_column_alert" />
	<m:store name="msg_auto_increment_one_column_alert"
		key="msg.auto_increment_one_column_alert" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(1)").addClass('active');
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
		var img = '<img alt="" class="icon" onclick="removeThisTr(this);" src="' + Server.root + '/components/icons/minus-r.png">';
		var columnInfo = '';
		var validInfo = true;

		function callSetValue(checkbox) {
			var td = $(checkbox).parent();
			var input = $(td).find('input[type="hidden"]');
			if ($(checkbox).is(':checked')) {
				$(input).val('1');
			} else {
				$(input).val('0');
			}
		}

		function callApplyDatatype(selector) {
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

		function removeThisTr(selector) {
			$('#no-of-columns').prop('readonly', false);
			$('#add-column-btn').prop('disabled', false);
			var tr = $(selector).parent().parent();
			$(tr).empty();
			$(tr).remove();
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
					url : Server.root + '/database_create_table_post.text',
					method : 'POST',
					data : $('#create-table-form').serialize(),
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
					url : Server.root + '/database_create_table_post.text',
					method : 'POST',
					data : $('#create-table-form').serialize(),
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