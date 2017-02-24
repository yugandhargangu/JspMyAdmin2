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
					page="../../server/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.variable_list" />
						</h3>
					</div>
					<div class="group-widget group-content">
						<div>
							<label><m:print key="lbl.search" /> : </label><input type="text"
								id="search" class="form-control">
						</div>
						<table class="tbl" id="variable-list">
							<thead>
								<tr>
									<th><m:print key="lbl.action" /></th>
									<jma:forLoop items="#columnInfo" name="columnName"
										scope="command" index="columnIndex">
										<th>${columnName}</th>
									</jma:forLoop>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th><m:print key="lbl.action" /></th>
									<jma:forLoop items="#columnInfo" name="columnName"
										scope="command" index="columnIndex">
										<th>${columnName}</th>
									</jma:forLoop>
								</tr>
							</tfoot>
							<tbody>
								<jma:notEmpty name="#data_list" scope="command">
									<jma:forLoop items="#data_list" name="variableInfo"
										scope="command">
										<tr>
											<td>
												<button type="button" class="btn" onclick="callEdit(this);">
													<m:print key="lbl.edit" />
												</button>
											</td>
											<jma:forLoop items="#variableInfo" name="variableData"
												scope="page">
												<td>${variableData}</td>
											</jma:forLoop>
										</tr>
									</jma:forLoop>
								</jma:notEmpty>
								<jma:empty name="#data_list" scope="command">
									<tr class="even">
										<td colspan="3"><m:print key="msg.no_variable_found" /></td>
									</tr>
								</jma:empty>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>

	</div>
	<m:store name="btn_edit" key="lbl.edit" />
	<m:store name="btn_cancel" key="lbl.cancel" />
	<m:store name="btn_save" key="lbl.save" />
	<script type="text/javascript">
		$("#header-menu li:nth-child(7)").addClass('active');
		applyEvenOdd('#variable-list');
		function callEdit(element) {
			var action = $(element).parent();
			var tr = $(action).parent();
			var text = $(tr).find('td').eq(2);
			var data = $(text).text();
			var input = '<input type="text" style="width: 90%;" value="';
			input += data;
			input += '">';
			$(text).empty();
			$(text).append(input);
			var btns = '<button type="button" class="btn" onclick="';
			btns += "callCancel(this,'";
			btns += data;
			btns += "');";
			btns += '">';
			btns += '${btn_cancel}';
			btns += '</button><button type="button" class="btn" ';
			btns += 'onclick="callSave(this,';
			btns += "'";
			btns += data;
			btns += "');";
			btns += '">';
			btns += '${btn_save}';
			btns += '</button>';
			$(action).empty();
			$(action).append(btns);
		}
		function callSave(element, val) {
			var action = $(element).parent();
			var tr = $(action).parent();
			var name = $(tr).find('td').eq(1).text();
			var value = $(tr).find('td').eq(2).find('input[type="text"]').val();
			$.ajax({
				url : Server.root + '/server_variable.text',
				method : 'POST',
				data : {
					'name' : name,
					'value' : value
				},
				success : function(result) {
					var text = decode(result);
					var jsonText = $.parseJSON(text);
					if (jsonText.type == 'msg') {
						$('#sidebar-success-msg').text(jsonText.msg);
						$('#sidebar-success-dialog').show();
						var btns = '<button type="button" class="btn" ';
						btns += 'onclick="callEdit(this);">';
						btns += '${btn_edit}';
						btns += '</button>';
						$(action).empty();
						$(action).append(btns);
						var text = $(tr).find('td').eq(2);
						$(text).text(jsonText.column);
					} else if (jsonText.type == 'err') {
						$('#sidebar-error-msg').text(jsonText.msg);
						$('#sidebar-error-dialog').show();
						var text = $(tr).find('td').eq(2);
						$(text).text(val);
						var btns = '<button type="button" class="btn" ';
						btns += 'onclick="callEdit(this);">';
						btns += '${btn_edit}';
						btns += '</button>';
						$(action).empty();
						$(action).append(btns);
					}
				},
				error : function(result) {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					var text = $(tr).find('td').eq(2);
					$(text).text(val);
					var btns = '<button type="button" class="btn" ';
					btns += 'onclick="callEdit(this);">';
					btns += '${btn_edit}';
					btns += '</button>';
					$(action).empty();
					$(action).append(btns);
				}
			});
		}
		function callCancel(element, val) {
			var action = $(element).parent();
			var tr = $(action).parent();
			var text = $(tr).find('td').eq(2);
			$(text).text(val);
			var btns = '<button type="button" class="btn" ';
			btns += 'onclick="callEdit(this);">';
			btns += '${btn_edit}';
			btns += '</button>';
			$(action).empty();
			$(action).append(btns);
		}

		$(function() {
			$('#search').keyup(function() {
				searchTable('#variable-list', [ 1, 2 ], $(this).val());
			});
		});
	</script>
</body>
</html>
