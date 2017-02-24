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
							<m:print key="lbl.create_procedure" />
						</h3>
					</div>
					<form action="#" accept-charset="utf-8" method="post"
						id="create-procedure-form">
						<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
						<input type="hidden" name="token" class="server-token"
							value="${requestScope.command.token}"> <input
							type="hidden" name="action" id="action-input">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.procedure_structure" />
							</div>
							<div class="group-widget group-content">
								<div
									style="display: inline-block; margin-right: 3em; width: 42%;">
									<div style="display: block;">
										<div class="form-input" style="width: 300px;">
											<label>Procedure Name</label> <input type="text" name="name"
												id="procedure-name" class="form-control"
												value="${requestScope.command.name}">
										</div>
										<div class="form-input">
											<label><m:print key="lbl.comment" /></label> <input
												type="text" name="comment" class="form-control">
										</div>
									</div>
									<div style="display: block;">
										<div class="form-input"
											style="display: inline-block; width: 300px;">
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
									</div>
									<div>
										<div class="form-input" style="width: 300px;">
											<label><m:print key="lbl.sql_type" /></label> <select
												name="sql_type" class="form-control">
												<option value=""></option>
												<jma:notEmpty name="#sql_type_list" scope="command">
													<jma:forLoop items="#sql_type_list" name="securityVal"
														scope="command">
														<option value="${securityVal}">${securityVal}</option>
													</jma:forLoop>
												</jma:notEmpty>
											</select>
										</div>

										<div class="form-input">
											<label><m:print key="lbl.security_type" /></label> <select
												name="sql_security" class="form-control">
												<option value=""></option>
												<jma:notEmpty name="#security_type_list" scope="command">
													<jma:forLoop items="#security_type_list" name="checkVal"
														scope="command">
														<option value="${checkVal}">${checkVal}</option>
													</jma:forLoop>
												</jma:notEmpty>
											</select>
										</div>
									</div>
									<div>
										<div class="form-input" style="width: 300px;">
											<label><input type="checkbox" name="lang_sql"
												value="LANGUAGE SQL"> LANGUAGE SQL</label>
										</div>
										<div class="form-input">
											<label><m:print key="lbl.deterministic" /></label> <select
												name="deterministic" class="form-control">
												<option value=""></option>
												<option value="DETERMINISTIC">DETERMINISTIC</option>
												<option value="NOT DETERMINISTIC">NOT DETERMINISTIC</option>
											</select>
										</div>
									</div>
									<div class="form-input" style="display: block; width: 100%;">
										<label><m:print key="lbl.procedure_body" /></label> <input
											type="hidden" name="body" id="definition">
										<textarea rows="15" cols="100" style="width: 100%;"
											id="definition-area">SELECT ;</textarea>
									</div>
								</div>
								<div style="display: inline-block; vertical-align: top;">
									<div class="form-input" id="columns-div"
										style="display: block;">
										<div style="display: block;">
											<label style="display: inline-block;"><m:print
													key="lbl.parameters" /></label>
										</div>
										<table style="width: 100%;" class="tbl" id="param-table">
											<thead>
												<tr>
													<th><m:print key="lbl.type" /></th>
													<th><m:print key="lbl.name" /></th>
													<th><m:print key="lbl.datatype" /></th>
													<th><m:print key="lbl.length" /></th>
													<th>
														<button type="button" class="btn" id="add-column">
															<m:print key="lbl.add" />
														</button>
													</th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>
								</div>

							</div>
							<div class="group-widget group-footer">
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
		<form action="${pageContext.request.contextPath}/database_procedures.html"
			id="success-form" method="get">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token" id="success-token">
		</form>
	</div>
	<script type="text/javascript">
		$("#header-menu li:nth-child(3)").addClass('active');
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
		function removeThisTr(selector) {
			$(selector).parent().parent().remove();
		}
		var newColumn = '${requestScope.command.new_column}';
	</script>
	<m:store name="msg_procedure_name_blank" key="msg.procedure_name_blank" />
	<m:store name="msg_procedure_body_blank" key="msg.procedure_body_blank" />
	<script type="text/javascript">
		$(function() {

			$('#definer-select').change(function() {
				if ($(this).val() == 'OTHER') {
					$('#definer-name').show();
				} else {
					$('#definer-name').hide();
				}
			});

			$('#add-column').click(function() {
				$('#param-table').append(newColumn);
			});

			$('#btn-go').click(function() {
				if ($('#procedure-name').val().trim() == '') {
					$('#sidebar-error-msg').text('${msg_procedure_name_blank}');
					$('#sidebar-error-dialog').show();
					return;
				}
				if (codeMirrorDefinition.getValue().trim() == '') {
					$('#sidebar-error-msg').text('${msg_procedure_body_blank}');
					$('#sidebar-error-dialog').show();
					return;
				}
				showWaiting();
				$('#action-input').val('Yes');
				$('#definition').val(codeMirrorDefinition.getValue());
				$.ajax({
					url : Server.root + '/database_create_procedure_post.text',
					method : 'POST',
					data : $('#create-procedure-form').serialize(),
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
				if ($('#procedure-name').val().trim() == '') {
					$('#sidebar-error-msg').text('${msg_procedure_name_blank}');
					$('#sidebar-error-dialog').show();
					return;
				}
				if (codeMirrorDefinition.getValue().trim() == '') {
					$('#sidebar-error-msg').text('${msg_procedure_body_blank}');
					$('#sidebar-error-dialog').show();
					return;
				}
				showWaiting();
				$('#action-input').val('No');
				$('#definition').val(codeMirrorDefinition.getValue());
				$.ajax({
					url : Server.root + '/database_create_procedure_post.text',
					method : 'POST',
					data : $('#create-procedure-form').serialize(),
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
						result = result + ';';
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