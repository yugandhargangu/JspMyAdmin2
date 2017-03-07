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

.CodeMirror {
	background: #FFF none repeat scroll 0% 0%;
	margin-bottom: 5px;
	min-height: 200px;
	max-height: 500px;
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
						<h3>
							<m:print key="lbl.import_result" />
						</h3>
					</div>
					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.script" />
							<jma:notEmpty name="#exec_time" scope="command">
								&#40;<m:print key="lbl.execution_time" />: 
								${requestScope.command.exec_time}
								<m:print key="lbl.seconds" />&#41;
								</jma:notEmpty>
						</div>
						<div class="group-widget group-content">
							<input type="hidden" name="query" id="mysql-query">
							<textarea rows="5" cols="100" style="width: 100%;"
								id="sql-editor">${requestScope.command.query}</textarea>
						</div>
					</div>
					<jma:notEmpty name="#error" scope="command">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.mysql_error" />
								:
							</div>
							<div class="group-widget group-normal">
								<p style="color: red;">${requestScope.command.error}</p>
							</div>
						</div>
					</jma:notEmpty>
					<jma:notEmpty name="#success_count" scope="command">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.success_result" />
							</div>
							<div class="group-widget group-content">
								<div style="color: green;">
									<m:print key="lbl.total_number_queries_process" />
									: ${requestScope.command.success_count}
								</div>
							</div>
						</div>
					</jma:notEmpty>
					<jma:notEmpty name="#error_count" scope="command">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.error_result" />
							</div>
							<div class="group-widget group-content">
								<div style="color: red;">
									<m:print key="lbl.total_number_error_queries" />
									: ${requestScope.command.error_count}
								</div>
								<jma:notEmpty name="#error_queries" scope="command">
									<textarea rows="5" cols="100" style="width: 100%;"
										id="error-sql">${requestScope.command.error_queries}</textarea>
								</jma:notEmpty>
							</div>
						</div>
					</jma:notEmpty>
					<jma:notEmpty name="#ignore_count" scope="command">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.ignore_result" />
							</div>
							<div class="group-widget group-content">
								<div style="color: #FFCC00;">
									<m:print key="lbl.total_number_queries_ignore" />
									: ${requestScope.command.ignore_count}
								</div>
							</div>
						</div>
					</jma:notEmpty>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#header-menu li:nth-child(5)").addClass('active');

		var id = document.getElementById('sql-editor');
		var codeMirrorObj = CodeMirror.fromTextArea(id, {
			mode : 'text/x-mysql',
			indentWithTabs : true,
			smartIndent : true,
			lineNumbers : true,
			lineWrapping : true,
			matchBrackets : true,
			autofocus : true,
			readOnly : true
		});
	</script>
	<jma:notEmpty name="#error_queries" scope="command">
		<script type="text/javascript">
			var id1 = document.getElementById('error-sql');
			var codeMirrorObj1 = CodeMirror.fromTextArea(id1, {
				mode : 'text/x-mysql',
				indentWithTabs : true,
				smartIndent : true,
				lineNumbers : true,
				lineWrapping : true,
				matchBrackets : true,
				autofocus : true,
				readOnly : true
			});
		</script>
	</jma:notEmpty>
</body>
</html>