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
							<m:print key="lbl.events" />
						</h3>
					</div>
					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.create_event" />
						</div>
						<div class="group-widget group-content">
							<form id="event-create-form"
								action="${pageContext.request.contextPath}/database_event_create"
								method="post" accept-charset="utf-8">
								<input type="hidden" name="token"
									value="${requestScope.command.token}">
								<div class="form-input">
									<label><m:print key="lbl.event_name" /></label> <input
										type="text" name="event_name" id="event-create-name"
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
						id="event-list-form">
						<input type="hidden" name="token"
							value="${requestScope.command.token}">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.event_list" />
							</div>
							<div class="group-widget group-content">

								<table class="tbl" id="event-list">
									<thead>
										<tr>
											<th><input type="checkbox" id="check_all"></th>
											<th><m:print key="lbl.event_name" /></th>
											<th><m:print key="lbl.definer" /></th>
											<th><m:print key="lbl.type" /></th>
											<th><m:print key="lbl.status" /></th>
											<th><m:print key="lbl.create_date" /></th>
											<th><m:print key="lbl.last_altered_date" /></th>
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
										</tr>
									</tfoot>
									<tbody>
										<jma:notEmpty name="#event_list" scope="command">
											<jma:forLoop items="#event_list" name="eventInfo"
												scope="command">
												<tr>
													<td><input type="checkbox" name="events"
														value="${eventInfo.name}"></td>
													<td>${eventInfo.name}</td>
													<td>${eventInfo.definer}</td>
													<td>${eventInfo.type}</td>
													<td>${eventInfo.status}</td>
													<td>${eventInfo.create_date}</td>
													<td>${eventInfo.alter_date}</td>
													<td>${eventInfo.comments}</td>
												</tr>
											</jma:forLoop>
										</jma:notEmpty>
										<jma:empty name="#event_list" scope="command">
											<tr class="even">
												<td colspan="8"><m:print key="msg.no_event_found" /></td>
											</tr>
										</jma:empty>
									</tbody>
								</table>

							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-enable">
									<m:print key="lbl.enable" />
								</button>
								<button type="button" class="btn" id="btn-disable">
									<m:print key="lbl.disable" />
								</button>
								<button type="button" class="btn" id="btn-edit">
									<m:print key="lbl.alter" />
								</button>
								<button type="button" class="btn" id="btn-rename">
									<m:print key="lbl.rename" />
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

						<div class="group" id="rename-group" style="display: none;">
							<div class="close" id="rename-close">&#10005;</div>
							<div class="group-widget group-header">
								<m:print key="lbl.rename_event" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input" style="width: 300px;">
									<label><m:print key="lbl.new_event_name" /></label> <input
										type="text" name="new_event" id="new-event-name"
										class="form-control">
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-rename-go">
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

	<script type="text/javascript">
		$("#header-menu li:nth-child(5)").addClass('active');
		applyEvenOdd('#event-list');
		// animation time
		var waitTime = 500;
		// type of operation
		var GoType = {
			drop : "drop",
			rename : "rename",
			enable : "enable",
			disable : "disable"
		};
		// confirm text of each operation
		var GoText = {
			drop : "Do You Really Want to Drop Event(s)?",
			select : "Please Select at least One Event",
			rename : "Please Select only One Event to Rename"
		};
		// action for each operation
		var GoAction = {
			drop : "/database_event_drop",
			rename : "/database_event_rename",
			enable : "/database_event_enable",
			disable : "/database_event_disable"
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
		function shouldContinue(checks) {
			var length = $('input[name="events"]:checked').length;
			if (length < 1) {
				$('#error-content').text(GoText.select);
				$('#error-dialog').show();
				return false;
			} else {
				if (checks) {
					return true;
				} else {
					if (length > 1) {
						$('#error-content').text(GoText.rename);
						$('#error-dialog').show();
						return false;
					} else {
						return true;
					}
				}
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
				if ($('#event-create-name').val().trim() == '') {
					$('#error-content').text('Event Name is Required.');
					$('#error-dialog').show();
					return;
				}

				$('#event-create-form').submit();
			});
			$('#check_all').change(function() {
				var status = $(this).prop('checked');
				$('input[name="events"]').prop('checked', status);
			});
			$('input[name="events"]').change(function() {
				var length1 = $('input[name="events"]').length;
				var length2 = $('input[name="events"]:checked').length;
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
				$('#event-list-form').attr('action', Server.root + action);
				$('#event-list-form').submit();
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
					result += '\n#------------- Create Event: ';
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
				if (!shouldContinue(true)) {
					return;
				}
				$('#rename-group').hide(waitTime);
				var url = Server.root + '/database_event_show_create.text';
				$.ajax({
					url : url,
					data : $('#event-list-form').serialize(),
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
				scrollTo('#event-list');
			});
			$('#rename-close').click(function() {
				$('#rename-group').hide(waitTime);
				codeMirrorObj.setValue('');
				scrollTo('#event-list');
			});
		});
	</script>

	<script type="text/javascript">
		// drop
		$(function() {
			$('#btn-drop').click(function() {
				if (!shouldContinue(true)) {
					return;
				}
				showConfirm(GoType.drop);
			});
		});
	</script>

	<script type="text/javascript">
		// enable
		$(function() {
			$('#btn-enable').click(function() {
				if (!shouldContinue(true)) {
					return;
				}
				showWaiting();
				$('#event-list-form').attr('action', Server.root + GoAction.enable);
				$('#event-list-form').submit();
			});
		});
	</script>

	<script type="text/javascript">
		// enable
		$(function() {
			$('#btn-disable').click(function() {
				if (!shouldContinue(true)) {
					return;
				}
				showWaiting();
				$('#event-list-form').attr('action', Server.root + GoAction.disable);
				$('#event-list-form').submit();
			});
		});
	</script>

	<script type="text/javascript">
		// rename
		$(function() {

			$('#btn-rename').click(function() {
				if (!shouldContinue(false)) {
					return;
				}
				$('#show-create-group').hide(waitTime);
				$('#rename-group').show(waitTime);
			});

			$('#btn-rename-go').click(function() {
				if (!shouldContinue(false)) {
					return;
				}
				if ($('#new-event-name').val().trim() == '') {
					$('#sidebar-error-msg').text('New Event Name is Blank.');
					$('#sidebar-error-dialog').show();
					return;
				}
				showWaiting();
				$('#event-list-form').attr('action', Server.root + GoAction.rename);
				$('#event-list-form').submit();
			});
		});
	</script>
</body>
</html>
