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
							<m:print key="lbl.create_event" />
						</h3>
					</div>
					<form action="#" accept-charset="utf-8" method="post"
						id="create-event-form">
						<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
						<input type="hidden" name="token" class="server-token"
							value="${requestScope.command.token}"> <input
							type="hidden" name="action" id="action-input">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.event_structure" />
							</div>
							<div class="group-widget group-content">
								<div>
									<div class="form-input">
										<label><m:print key="lbl.event_name" /></label> <input
											type="text" name="event_name" id="event-name"
											class="form-control"
											value="${requestScope.command.event_name}">
									</div>
									<div class="form-input">
										<label><input type="checkbox" name="not_exists"
											value="Yes"> IF NOT EXISTS</label>
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
										<label><m:print key="lbl.comment" /></label> <input
											type="text" name="comment" class="form-control">
									</div>
								</div>
								<div>
									<div class="form-input">
										<label style="display: inline-block;"><m:print
												key="lbl.schedule_type" />: </label> <label
											style="display: inline-block; font-weight: normal;"><input
											type="radio" checked="checked" name="schedule_type"
											value="AT"> AT</label> <label
											style="display: inline-block; font-weight: normal;"><input
											type="radio" name="schedule_type" value="EVERY">
											EVERY</label>
									</div>
									<div id="every-interval-block"
										style="border: 1px solid #ffffff; display: none;">
										<div>
											<div class="form-input">
												<label>EVERY</label>
											</div>
											<div class="form-input">
												<label>INTERVAL</label>
											</div>
											<div class="form-input">
												<label><m:print key="lbl.interval" /></label> <select
													class="form-control" name="interval" id="every-interval">
													<jma:notEmpty name="#interval_list" scope="command">
														<jma:forLoop items="#interval_list" name="intervalVal"
															scope="command">
															<option value="${intervalVal}">${intervalVal}</option>
														</jma:forLoop>
													</jma:notEmpty>
												</select>
											</div>
											<div class="form-input">
												<label><m:print key="lbl.quantity" /></label> <input
													type="text" name="interval_quantity"
													id="every-interval-qunatity" class="form-control">
											</div>
										</div>

									</div>
									<div>&nbsp;</div>
									<div style="border: 1px solid #ffffff;">
										<div
											style="display: inline-block; width: 45%; vertical-align: top;">
											<div class="form-input">
												<label id="label-start">AT</label> <select
													id="start_date_type" class="form-control"
													name="start_date_type">
													<option value=""></option>
													<option value="CURRENT_TIMESTAMP">CURRENT_TIMESTAMP</option>
													<option value="OTHER">OTHER</option>
												</select>
											</div>
											<div class="form-input" id="start-date-text"
												style="display: none;">
												<label>TIME STAMP</label> <input type="text"
													id="start-date-text-id" class="form-control"
													name="start_date">
											</div>
											<div class="form-input">
												<button type="button" id="start-interval-add" class="btn">
													<m:print key="lbl.add_interval" />
												</button>
											</div>
										</div>
										<div style="display: inline-block;" id="start-interval-block">

										</div>
									</div>
									<div>&nbsp;</div>
									<div style="border: 1px solid #ffffff; display: none;"
										id="end-interval-block-main">
										<div
											style="display: inline-block; width: 45%; vertical-align: top;">
											<div class="form-input">
												<label>ENDS</label> <select class="form-control"
													id="end_date_type" name="end_date_type">
													<option value=""></option>
													<option value="CURRENT_TIMESTAMP">CURRENT_TIMESTAMP</option>
													<option value="OTHER">OTHER</option>
												</select>
											</div>
											<div class="form-input" style="display: none;"
												id="end-date-text">
												<label>TIME STAMP</label> <input type="text"
													class="form-control" name="end_date">
											</div>
											<div class="form-input">
												<button type="button" id="end-interval-add" class="btn">
													<m:print key="lbl.add_interval" />
												</button>
											</div>
										</div>
										<div style="display: inline-block;" id="end-interval-block">

										</div>
									</div>
								</div>
								<div style="display: inline-block; width: 45%;">
									<div class="form-input">
										<label>ON COMPLETION: </label>
									</div>
									<div class="form-input">
										<label style="font-weight: normal;"><input
											checked="checked" type="radio" name="preserve" value="">
											<m:print key="lbl.none" /></label>
									</div>
									<div class="form-input">
										<label style="font-weight: normal;"><input
											type="radio" name="preserve" value="PRESERVE">
											PRESERVE</label>
									</div>
									<div class="form-input">
										<label style="font-weight: normal;"><input
											type="radio" name="preserve" value="NOT PRESERVE">
											NOT PRESERVE</label>
									</div>
								</div>
								<div style="display: inline-block;">
									<div class="form-input">
										<label><m:print key="lbl.status" />: </label>
									</div>
									<div class="form-input">
										<label style="font-weight: normal;"><input
											checked="checked" type="radio" name="status" value="">
											<m:print key="lbl.none" /></label>
									</div>
									<div class="form-input">
										<label style="font-weight: normal;"><input
											type="radio" name="status" value="ENABLE"> ENABLE</label>
									</div>
									<div class="form-input">
										<label style="font-weight: normal;"><input
											type="radio" name="status" value="DISABLE "> DISABLE
										</label>
									</div>
									<div class="form-input">
										<label style="font-weight: normal;"><input
											type="radio" name="status" value="DISABLE ON SLAVE">
											DISABLE ON SLAVE</label>
									</div>
								</div>
								<div>
									<div class="form-input" style="display: block;">
										<label><m:print key="lbl.event_body" /></label> <input
											type="hidden" name="body" id="definition">
									</div>
									<div>
										<textarea rows="15" cols="50" id="definition-area">SELECT ;</textarea>
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
		<form action="${pageContext.request.contextPath}/database_events.html"
			id="success-form" method="get">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token" id="success-token">
		</form>
	</div>
	<script type="text/javascript">
		$("#header-menu li:nth-child(5)").addClass('active');
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
		var startInterval = '${requestScope.command.start_interval}';
		var endInterval = '${requestScope.command.end_interval}';
	</script>
	<script type="text/javascript">
		function removeThisInterval(selector) {
			$(selector).parent().parent().remove();
		}
		// schedule
		$(function() {
			$('input[name="schedule_type"]').change(function() {
				$('#start-interval-block').empty();
				$('#end-interval-block').empty();
				$('#start_date_type').val('');
				$('#start-date-text').hide();
				$('#end_date_type').val('');
				$('#end-date-text').hide();
				$('#every-interval-qunatity').val('');
				if ($(this).val() == 'EVERY') {
					$('#label-start').text('STARTS');
					$('#every-interval-block').show();
					$('#end-interval-block-main').show();
				} else {
					$('#label-start').text('AT');
					$('#every-interval-block').hide();
					$('#end-interval-block-main').hide();
				}
			});
			$('#start-interval-add').click(function() {
				$('#start-interval-block').append(startInterval);
			});
			$('#end-interval-add').click(function() {
				$('#end-interval-block').append(endInterval);
			});
			$('#start_date_type').change(function() {
				if ($(this).val() == 'OTHER') {
					$('#start-date-text').show();
				} else {
					$('#start-date-text').hide();
				}
			});
			$('#end_date_type').change(function() {
				if ($(this).val() != 'OTHER') {
					$('#end-date-text').show();
				} else {
					$('#end-date-text').hide();
				}
			});
		});
	</script>
	<m:store name="msg_event_name_blank" key="msg.event_name_blank" />
	<m:store name="msg_schedule_at_blank" key="msg.schedule_at_blank" />
	<m:store name="msg_schedule_at_value_blank"
		key="msg.schedule_at_value_blank" />
	<m:store name="msg_every_interval_qunatity_blank"
		key="msg.every_interval_qunatity_blank" />
	<m:store name="msg_event_body_blank" key="msg.event_body_blank" />
	<script type="text/javascript">
		// save
		function isValid() {
			if ($('#event-name').val().trim() == '') {
				$('#sidebar-error-msg').text('${msg_event_name_blank}');
				$('#sidebar-error-dialog').show();
				return false;
			}
			var schedule_type = '';
			$('input[name="schedule_type"]').each(function() {
				if ($(this).is(':checked')) {
					schedule_type = $(this).val();
					return false;
				}
			});

			if (schedule_type == 'AT') {
				if ($('#start_date_type').val() == '') {
					$('#sidebar-error-msg').text('${msg_schedule_at_blank}');
					$('#sidebar-error-dialog').show();
					return false;
				} else {
					if ($('#start_date_type').val() == 'OTHER' && $('#start-date-text-id').val().trim() == '') {
						$('#sidebar-error-msg').text('${msg_schedule_at_value_blank}');
						$('#sidebar-error-dialog').show();
						return false;
					}
				}
			} else if (schedule_type == 'EVERY') {
				if ($('#every-interval-qunatity').val().trim() == '') {
					$('#sidebar-error-msg').text('${msg_every_interval_qunatity_blank}');
					$('#sidebar-error-dialog').show();
					return false;
				}
			}
			if (codeMirrorDefinition.getValue().trim() == '') {
				$('#sidebar-error-msg').text('${msg_event_body_blank}');
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
					url : Server.root + '/database_event_create_post.text',
					method : 'POST',
					data : $('#create-event-form').serialize(),
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
				if (!isValid()) {
					return;
				}
				showWaiting();
				$('#action-input').val('No');
				$('#definition').val(codeMirrorDefinition.getValue());
				$.ajax({
					url : Server.root + '/database_event_create_post.text',
					method : 'POST',
					data : $('#create-event-form').serialize(),
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