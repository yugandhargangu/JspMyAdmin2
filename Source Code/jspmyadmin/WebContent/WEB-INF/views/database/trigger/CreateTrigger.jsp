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
						<h3>Create Trigger</h3>
					</div>
					<form action="#" accept-charset="utf-8" method="post"
						id="create-trigger-form">
						<input type="hidden" name="token"
							value="${requestScope.command.token}"> <input
							type="hidden" name="action" id="action-input">
						<div class="group">
							<div class="group-widget group-header">Trigger Structure</div>
							<div class="group-widget group-content">
								<div>
									<div class="form-input">
										<label>Trigger Name</label> <input type="text"
											name="trigger_name" id="trigger_name" class="form-control"
											value="${requestScope.command.trigger_name}">
									</div>
									<div class="form-input">
										<label><m:print key="lbl.definer" /></label> <select
											name="definer" id="definer-select" class="form-control">
											<option value=""></option>
											<jma:notEmpty name="#definer_list" scope="command">
												<jma:forLoop items="#definer_list" name="definerVal"
													scope="command">
													<option value="${definerVal}">${definerVal}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input" id="definer-name"
										style="display: inline-block; display: none;">
										<label><m:print key="lbl.definer_name" /></label> <input
											type="text" name="definer_name" class="form-control">
									</div>
									<div class="form-input">
										<label>Trigger Time</label> <select name="trigger_time"
											class="form-control">
											<jma:notEmpty name="#trigger_time_list" scope="command">
												<jma:forLoop items="#trigger_time_list"
													name="triggerTimeVal" scope="command">
													<option value="${triggerTimeVal}">${triggerTimeVal}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>Trigger Event</label> <select name="trigger_event"
											class="form-control">
											<jma:notEmpty name="#trigger_event_list" scope="command">
												<jma:forLoop items="#trigger_event_list"
													name="triggerEventVal" scope="command">
													<option value="${triggerEventVal}">${triggerEventVal}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>Database Name</label> <select name="database_name"
											id="database_name" class="form-control">
											<jma:notEmpty name="#database_name_list" scope="command">
												<jma:forLoop items="#database_name_list" name="databaseVal"
													scope="command">
													<jma:switch>
														<jma:case name="#database_name" value="#databaseVal"
															scope="command,page">
															<option value="${databaseVal}" selected="selected">${databaseVal}</option>
														</jma:case>
														<jma:default>
															<option value="${databaseVal}">${databaseVal}</option>
														</jma:default>
													</jma:switch>

												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>Table Name</label> <select name="table_name"
											id="table_name" class="form-control">

										</select>
									</div>
									<div class="form-input">
										<label>Trigger Order</label> <select name="trigger_order"
											class="form-control">
											<option value="">Select Order</option>
											<jma:notEmpty name="#trigger_order_list" scope="command">
												<jma:forLoop items="#trigger_order_list"
													name="triggerOrderVal" scope="command">
													<option value="${triggerOrderVal}">${triggerOrderVal}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>Other Trigger Name</label> <select
											name="other_trigger_name" class="form-control">
											<option value="">Select Trigger Name</option>
											<jma:notEmpty name="#other_trigger_name_list" scope="command">
												<jma:forLoop items="#other_trigger_name_list"
													name="triggerNameVal" scope="command">
													<option value="${triggerNameVal}">${triggerNameVal}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
								</div>
								<div>
									<div class="form-input" style="display: block;">
										<label>Trigger Body</label> <input type="hidden"
											name="trigger_body" id="definition">
									</div>
									<div>
										<textarea rows="15" cols="50" id="definition-area">UPDATE ;</textarea>
									</div>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="show-create-go">
									<m:print key="lbl.show_create" />
								</button>
								<button type="button" class="btn" id="btn-go">
									<m:print key="btn.go" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="group">
				<div class="group-widget group-normal">
					<p style="color: red;">
						<m:print key="note.view_create" />
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
		<form action="${pageContext.request.contextPath}/database_triggers.html"
			id="success-form" method="get">
			<input type="hidden" name="token" id="success-token">
		</form>
	</div>
	<script type="text/javascript">
		$("#header-menu li:nth-child(6)").addClass('active');
		// codemirror object
		var show_textarea = document.getElementById('show-create-area');
		var codeMirrorObj = CodeMirror.fromTextArea(show_textarea, {
			mode : JspMyadmin.mimeType,
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

		var definition_area = document.getElementById('definition-area');
		var codeMirrorDefinition = CodeMirror.fromTextArea(definition_area, {
			mode : JspMyadmin.mimeType,
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : true,
			lineWrapping : true,
			matchBrackets : true,
			autofocus : true,
			extraKeys : {
				"Ctrl-Space" : "autocomplete"
			}
		});
	</script>
	<script type="text/javascript">
		function listoutTables() {
			$('#table_name').empty();
			$('#table_name').append('<option value="">' + 'Select' + '</option>');
			$.ajax({
				url : Server.root + '/database_trigger_table_list.text',
				method : 'POST',
				data : $('#create-trigger-form').serialize(),
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

					var list = jsonText.data;
					if (list != null && list.toString() != '') {
						var list = list.toString().split(',');
						for (var i = 0; i < list.length; i++) {
							$('#table_name').append('<option value="'+list[i]+'">' + list[i] + '</option>');
						}
					}
				},
				error : function(result) {
					hideWaiting();
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
				}
			});
		}

		$(function() {
			listoutTables();
			$('#database_name').change(function() {
				listoutTables();
			});
		});
	</script>
	<script type="text/javascript">
		// save
		function isValid() {
			if ($('#trigger_name').val().trim() == '') {
				$('#sidebar-error-msg').text('Trigger Name is Blank.');
				$('#sidebar-error-dialog').show();
				return false;
			}
			if ($('#table_name').val().trim() == '') {
				$('#sidebar-error-msg').text('Table Name is Blank.');
				$('#sidebar-error-dialog').show();
				return false;
			}

			if (codeMirrorDefinition.getValue().trim() == '') {
				$('#sidebar-error-msg').text('Trigger Body is Blank.');
				$('#sidebar-error-dialog').show();
				return false;
			}
			return true;
		}

		$(function() {

			$('#definer-select').change(function() {
				if ($(this).val() == 'OTHER') {
					$('#definer-name').show();
				} else {
					$('#definer-name').hide();
				}
			});

			$('#btn-go').click(function() {
				if (!isValid()) {
					return;
				}
				showWaiting();
				$('#action-input').val('Yes');
				$('#definition').val(codeMirrorDefinition.getValue());
				$.ajax({
					url : Server.root + '/database_trigger_create_post.text',
					method : 'POST',
					data : $('#create-trigger-form').serialize(),
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
				if (!isValid()) {
					return;
				}
				showWaiting();
				$('#action-input').val('No');
				$('#definition').val(codeMirrorDefinition.getValue());
				$.ajax({
					url : Server.root + '/database_trigger_create_post.text',
					method : 'POST',
					data : $('#create-trigger-form').serialize(),
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