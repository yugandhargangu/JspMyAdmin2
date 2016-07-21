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
							<m:print key="lbl.functions" />
						</h3>
					</div>
					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.create_function" />
						</div>
						<div class="group-widget group-content">
							<form id="procedure-create-form"
								action="${pageContext.request.contextPath}/database_function_create.html"
								method="post" accept-charset="utf-8">
								<input type="hidden" name="token"
									value="${requestScope.command.token}">
								<div class="form-input">
									<label><m:print key="lbl.function_name" /></label> <input
										type="text" name="name" id="procedure-create-name"
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
						id="function-list-form">
						<input type="hidden" name="token"
							value="${requestScope.command.token}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.function_list" />
							</div>
							<div class="group-widget group-content">
								<table class="tbl" id="function-list">
									<thead>
										<tr>
											<th><input type="checkbox" id="check_all"></th>
											<th><m:print key="lbl.name" /></th>
											<th><m:print key="lbl.routine_body" /></th>
											<th><m:print key="lbl.returns" /></th>
											<th><m:print key="lbl.is_deterministic" /></th>
											<th><m:print key="lbl.sql_data_access" /></th>
											<th><m:print key="lbl.server_type" /></th>
											<th><m:print key="lbl.definer" /></th>
											<th><m:print key="lbl.comment" /></th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th></th>
											<th><m:print key="lbl.total" />:
												${requestScope.command.total}</th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#routine_info_list" scope="command">
											<jma:forLoop items="#routine_info_list" name="routineInfo"
												scope="command">
												<tr>
													<td><input type="checkbox" name="routines"
														value="${routineInfo.name}"></td>
													<td>${routineInfo.name}</td>
													<td>${routineInfo.routine_body}</td>
													<td>${routineInfo.returns}</td>
													<td>${routineInfo.deterministic}</td>
													<td>${routineInfo.data_access}</td>
													<td>${routineInfo.security_type}</td>
													<td>${routineInfo.definer}</td>
													<td>${routineInfo.comments}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#routine_info_list" scope="command">
											<tr class="even">
												<td colspan="9"><m:print key="msg.no_function_found" /></td>
											</tr>
										</jma:empty>
									</tbody>
								</table>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-execute">
									<m:print key="lbl.execute" />
								</button>
								<button type="button" class="btn" id="btn-edit">
									<m:print key="lbl.edit" />
								</button>
								<button type="button" class="btn" id="btn-show-create">
									<m:print key="lbl.show_create" />
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

	<m:store name="msg_drop_function_alert" key="msg.drop_function_alert" />
	<m:store name="msg_select_least_one_function"
		key="msg.select_least_one_function" />
	<m:store name="msg_function_name_blank" key="msg.function_name_blank" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(4)").addClass('active');
		applyEvenOdd('#function-list');
		// animation time
		var waitTime = 500;
		// type of operation
		var GoType = {
			drop : "drop",
		};
		// confirm text of each operation
		var GoText = {
			drop : "${msg_drop_function_alert}",
			select : "${msg_select_least_one_function}"
		};
		// action for each operation
		var GoAction = {
			drop : "/database_function_drop.html",
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

		// to check all checkbox status
		function shouldContinue() {
			var length = $('input[name="routines"]:checked').length;
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
				break;
			default:
				action = '#';
				break;
			}
			$('#confirm-dialog').show();
		}

		$(function() {
			$('#btn-go').click(function() {
				if ($('#procedure-create-name').val().trim() == '') {
					$('#error-content').text('${msg_function_name_blank}');
					$('#error-dialog').show();
					return;
				}

				$('#procedure-create-form').submit();
			});
			$('#check_all').change(function() {
				var status = $(this).prop('checked');
				$('input[name="routines"]').prop('checked', status);
			});
			$('input[name="routines"]').change(function() {
				var length1 = $('input[name="routines"]').length;
				var length2 = $('input[name="routines"]:checked').length;
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
				$('#function-list-form').attr('action', Server.root + action);
				$('#function-list-form').submit();
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
			if (res == '') {
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
					result += '\n#------------- Create Function: ';
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
			$('#btn-show-create').click(function() {
				if (!shouldContinue()) {
					return;
				}
				var url = Server.root + '/database_function_show_create.text';
				$.ajax({
					url : url,
					data : $('#function-list-form').serialize(),
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
				scrollTo('#function-list');
			});
		});
	</script>

	<script type="text/javascript">
		// drop
		$(function() {
			$('#btn-drop').click(function() {
				if (!shouldContinue()) {
					return;
				}
				showConfirm(GoType.drop);
			});
		});
	</script>
	<script type="text/javascript">
		// drop
		$(function() {
			$('#btn-execute').click(function() {
				if (!shouldContinue()) {
					return;
				}
				var url = Server.root + '/database_procedure_params.text';
				$.ajax({
					url : url,
					data : $('#function-list-form').serialize(),
					method : 'POST',
					success : function(res) {
						if (res == '') {
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
							if (actData != '') {
								var params = actData.split(",");
								for (var i = 0; params.length; i++) {
									var types = params[i].split(" ");
									if (types[0] == 'IN' || types[0] == 'INOUT') {
										// TODO
									}
								}
							}
						}

					},
					error : function(res) {
						$('#sidebar-error-msg').text(Msgs.errMsg);
						$('#sidebar-error-dialog').show();
					}
				});
			});
		});
	</script>
</body>
</html>
