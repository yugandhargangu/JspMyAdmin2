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
			<div id="header-div"><jsp:include page="../Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.view_structure" />
						</h3>
					</div>
					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.columns" />
						</div>
						<div class="group-widget group-content">
							<table class="tbl" id="column-list">
								<thead>
									<tr>
										<th><m:print key="lbl.column_name" /></th>
										<th><m:print key="lbl.datatype" /></th>
										<th><m:print key="lbl.collation" /></th>
										<th><m:print key="lbl.null" /></th>
										<th><m:print key="lbl.key" /></th>
										<th><m:print key="lbl.default" /></th>
										<th><m:print key="lbl.extra" /></th>
										<th><m:print key="lbl.privileges" /></th>
										<th><m:print key="lbl.comment" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th><m:print key="lbl.total" />:
											${requestScope.command.columns_size}</th>
										<th></th>
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
									<jma:notEmpty name="#column_list" scope="command">
										<jma:forLoop items="#column_list" name="columnInfo"
											scope="command">
											<tr>
												<td>${columnInfo.field_name}</td>
												<td>${columnInfo.field_type}</td>
												<td>${columnInfo.collation}</td>
												<td>${columnInfo.null_yes}</td>
												<td>${columnInfo.key}</td>
												<td>${columnInfo.def_val}</td>
												<td>${columnInfo.extra}</td>
												<td>${columnInfo.privileges}</td>
												<td>${columnInfo.comments}</td>
											</tr>
										</jma:forLoop>
									</jma:notEmpty>
									<jma:empty name="#column_list" scope="command">
										<tr class="even">
											<td colspan="9"><m:print key="msg.no_columns_found" />
											</td>
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
	</div>

	<script type="text/javascript">
		$("#header-menu li:nth-child(2)").addClass('active');
		applyEvenOdd('#column-list');
	</script>
</body>
</html>
