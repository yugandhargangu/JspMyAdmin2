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
.CodeMirror {
	height: auto !important;
	background: #FFF none repeat scroll 0% 0%;
	margin-bottom: 5px;
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
			<div id="header-div"><jsp:include page="../../table/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<form action="${pageContext.request.contextPath}/table_alter.html"
							accept-charset="utf-8" method="post">
							<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
							<input type="hidden" name="request_table" value="${requestScope.command.request_table}">
							<input type="hidden" name="token"
								value="${requestScope.command.token}">
							<h3>
								<m:print key="lbl.table_information" />
							</h3>
						</form>
					</div>
					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.information" />
						</div>
						<div class="group-widget group-content">
							<table class="tbl" id="info-table" style="display: inline-block;">
								<thead>
									<tr>
										<th colspan="3"><m:print key="lbl.table_status" /></th>
									</tr>
									<tr>
										<th>#</th>
										<th><m:print key="lbl.item" /></th>
										<th><m:print key="lbl.value" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th>#</th>
										<th><m:print key="lbl.item" /></th>
										<th><m:print key="lbl.value" /></th>
									</tr>
								</tfoot>
								<tbody>
									<jma:notEmpty name="#info_map" scope="command">
										<jma:forLoop items="#info_map" name="infoValue"
											scope="command" key="infoKey" index="_index">
											<tr>
												<td>${_index + 1}</td>
												<td>${infoKey}</td>
												<td>${infoValue}</td>
											</tr>
										</jma:forLoop>
									</jma:notEmpty>
								</tbody>
							</table>

							<table class="tbl"
								style="display: inline-block; vertical-align: top;">
								<tr>
									<th><m:print key="lbl.create_syntax" />
										<button type="button" id="create-syn-btn" class="btn"
											style="float: right; margin-top: -20px; background-color: #EEE; color: #2086BF;">
											<m:print key="lbl.copy_to_clipboard" />
										</button></th>
								</tr>
								<tr>
									<td><textarea rows="3" cols="5" id="create-syn">${requestScope.command.create_syn}</textarea></td>
								</tr>
								<tr>
									<th><m:print key="lbl.insert_syntax" />
										<button type="button" id="insert-syn-btn" class="btn"
											style="float: right; margin-top: -20px; background-color: #EEE; color: #2086BF;">
											<m:print key="lbl.copy_to_clipboard" />
										</button></th>
								</tr>
								<tr>
									<td><textarea rows="3" cols="5" id="insert-syn">${requestScope.command.insert_syn}</textarea></td>
								</tr>
								<tr>
									<th><m:print key="lbl.update_syntax" />
										<button type="button" id="update-syn-btn" class="btn"
											style="float: right; margin-top: -20px; background-color: #EEE; color: #2086BF;">
											<m:print key="lbl.copy_to_clipboard" />
										</button></th>
								</tr>
								<tr>
									<td><textarea rows="3" cols="5" id="update-syn">${requestScope.command.update_syn}</textarea></td>
								</tr>
								<tr>
									<th><m:print key="lbl.delete_syntax" />
										<button type="button" id="delete-syn-btn" class="btn"
											style="float: right; margin-top: -20px; background-color: #EEE; color: #2086BF;">
											<m:print key="lbl.copy_to_clipboard" />
										</button></th>
								</tr>
								<tr>
									<td><textarea rows="3" cols="5" id="delete-syn">${requestScope.command.delete_syn}</textarea></td>
								</tr>
							</table>
							<input type="text" style="display: none;" id="copy-text">
						</div>
					</div>
				</div>
				<div id="footer">
					<jsp:include page="../../includes/Footer.jsp" />
				</div>
			</div>
		</div>
	</div>
	<m:store name="msg_copied_text_clipboard"
		key="msg.copied_text_clipboard" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(7)").addClass('active');
		applyEvenOdd('#info-table');

		var create_syn = document.getElementById('create-syn');
		var codeMirrorObj1 = CodeMirror.fromTextArea(create_syn, {
			mode : 'text/x-mysql',
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : false,
			lineWrapping : true,
			matchBrackets : true,
			readOnly : true
		});

		var insert_syn = document.getElementById('insert-syn');
		var codeMirrorObj2 = CodeMirror.fromTextArea(insert_syn, {
			mode : 'text/x-mysql',
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : false,
			lineWrapping : true,
			matchBrackets : true,
			readOnly : true
		});

		var update_syn = document.getElementById('update-syn');
		var codeMirrorObj3 = CodeMirror.fromTextArea(update_syn, {
			mode : 'text/x-mysql',
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : false,
			lineWrapping : true,
			matchBrackets : true,
			readOnly : true
		});

		var delete_syn = document.getElementById('delete-syn');
		var codeMirrorObj4 = CodeMirror.fromTextArea(delete_syn, {
			mode : 'text/x-mysql',
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : false,
			lineWrapping : true,
			matchBrackets : true,
			readOnly : true
		});

		$('#create-syn-btn').click(function() {
			var $temp = $("<input>");
			$("body").append($temp);
			$temp.val(create_syn.value).select();
			document.execCommand("copy");
			$temp.remove();
			$('#sidebar-success-msg').text('${msg_copied_text_clipboard}');
			$('#sidebar-success-dialog').show();
		});
		$('#insert-syn-btn').click(function() {
			var $temp = $("<input>");
			$("body").append($temp);
			$temp.val(insert_syn.value).select();
			document.execCommand("copy");
			$temp.remove();
			$('#sidebar-success-msg').text('${msg_copied_text_clipboard}');
			$('#sidebar-success-dialog').show();
		});
		$('#update-syn-btn').click(function() {
			var $temp = $("<input>");
			$("body").append($temp);
			$temp.val(update_syn.value).select();
			document.execCommand("copy");
			$temp.remove();
			$('#sidebar-success-msg').text('${msg_copied_text_clipboard}');
			$('#sidebar-success-dialog').show();
		});
		$('#delete-syn-btn').click(function() {
			var $temp = $("<input>");
			$("body").append($temp);
			$temp.val(delete_syn.value).select();
			document.execCommand("copy");
			$temp.remove();
			$('#sidebar-success-msg').text('${msg_copied_text_clipboard}');
			$('#sidebar-success-dialog').show();
		});
	</script>
</body>
</html>
