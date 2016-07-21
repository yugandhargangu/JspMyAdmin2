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
						<h3>SQL Query Editor</h3>
					</div>
					<form action="${pageContext.request.contextPath}/server_sql.html"
						accept-charset="utf-8" method="post" id="sql-form">
						<input type="hidden" name="token" id="token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">
								Query
								<jma:notEmpty name="#exec_time" scope="command">
								(Execution Time: 
								${requestScope.command.exec_time}
								<m:print key="lbl.seconds" />
								</jma:notEmpty>
							</div>
							<div class="group-widget group-content">
								<input type="hidden" name="query" id="mysql-query">
								<textarea rows="5" cols="100" style="width: 100%;"
									id="sql-editor">${requestScope.command.query}</textarea>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-run">Run</button>
							</div>
						</div>
						<jma:notEmpty name="#error" scope="command">
							<div class="group">
								<div class="group-widget group-header">MySql Error:</div>
								<div class="group-widget group-normal">
									<p style="color: red;">${requestScope.command.error}</p>
								</div>
							</div>
						</jma:notEmpty>

						<jma:notEmpty name="#result" scope="command">
							<div class="group">
								<div class="group-widget group-header">Query Result</div>
								<div class="group-widget group-content">Query OK,
									${requestScope.command.result} row(s) effected.</div>
							</div>
						</jma:notEmpty>

						<jma:notEmpty name="#column_list" scope="command">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.selected_rows" />
									&#40;
									<m:print key="lbl.displaying" />
									${requestScope.command.total_data_count}
									<m:print key="lbl.rows" />
									&#41;
									<jma:if name="#max_rows" value="1" scope="command,">
										<b style="float: right; margin-right: 10px;">1000+ rows
											returned</b>
									</jma:if>
								</div>
								<div class="group-widget group-content">
									<div style="width: 100%; margin-top: 10px; overflow-y: auto;">
										<table class="tbl" id="data-rows">
											<thead>
												<tr id="column-header">
													<jma:forLoop items="#column_list" name="columnName"
														scope="command">
														<th>${columnName}</th>
													</jma:forLoop>
												</tr>
											</thead>
											<tbody>
												<jma:notEmpty name="#fetch_list" scope="command">
													<jma:forLoop items="#fetch_list" name="rowData"
														scope="command">
														<tr>
															<jma:forLoop items="#rowData" name="item" scope="page">
																<td>${item}</td>
															</jma:forLoop>
														</tr>
													</jma:forLoop>

												</jma:notEmpty>
												<jma:empty name="#fetch_list" scope="command">
													<tr>
														<td colspan="${requestScope.command.column_count}"><m:print
																key="msg.no_records_found" /></td>
													</tr>
												</jma:empty>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</jma:notEmpty>
					</form>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#header-menu li:nth-child(2)").addClass('active');
		applyEvenOdd('#data-rows');

		var id = document.getElementById('sql-editor');
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
			}
		});
		codeMirrorObj.on('keyup', function(instance, event) {
			if (!codeMirrorObj.state.completionActive && (event.keyCode > 64 && event.keyCode < 91)) {
				if (timeout)
					clearTimeout(timeout);
				var timeout = setTimeout(function() {
					CodeMirror.showHint(instance, CodeMirror.hint.clike, {
						completeSingle : true
					});
				}, 40);
			}
		});
	</script>
	<script type="text/javascript">
		$(function() {
			$('#btn-run').click(function() {
				showWaiting();
				$('#mysql-query').val(codeMirrorObj.getValue());
				$('#sql-form').submit();
			});
		});
	</script>
</body>
</html>