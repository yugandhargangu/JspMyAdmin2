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
							<m:print key="lbl.charset_list" />
						</h3>
					</div>
					<div class="group-widget group-content">
						<table class="tbl" id="charset-list">
							<thead>
								<tr>
									<jma:forLoop items="#columnInfo" name="columnName"
										scope="command" index="columnIndex">
										<th><a
											href="${pageContext.request.contextPath}/server_charsets?token=${requestScope.command.sortInfo[columnIndex]}">
												${columnName}<jma:if name="#field" value="#columnName"
													scope="command,page">
													<jma:switch name="#type" scope="command">
														<jma:case value="true">
															<img alt="" class="icon"
																src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
														</jma:case>
														<jma:default>
															<img alt="" class="icon"
																src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
														</jma:default>
													</jma:switch>
												</jma:if>
										</a></th>
									</jma:forLoop>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<jma:forLoop items="#columnInfo" name="columnName"
										scope="command" index="columnIndex">
										<th><a
											href="${pageContext.request.contextPath}/server_charsets?token=${requestScope.command.sortInfo[columnIndex]}">
												${columnName} <jma:if name="#field" value="#columnName"
													scope="command,page">
													<jma:switch name="#type" scope="command">
														<jma:case value="true">
															<img alt="" class="icon"
																src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
														</jma:case>
														<jma:default>
															<img alt="" class="icon"
																src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
														</jma:default>
													</jma:switch>
												</jma:if>
										</a></th>
									</jma:forLoop>
								</tr>
							</tfoot>
							<tbody>
								<jma:notEmpty name="#data_list" scope="command">
									<jma:forLoop items="#data_list" name="charsetInfo"
										scope="command">
										<tr>
											<jma:forLoop items="#charsetInfo" name="charsetData"
												scope="page">
												<td>${charsetData}</td>
											</jma:forLoop>
										</tr>
									</jma:forLoop>
								</jma:notEmpty>
								<jma:empty name="#data_list" scope="command">
									<tr class="even">
										<td colspan="8"><m:print key="msg.no_collation_found" /></td>
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
	<script type="text/javascript">
		$("#header-menu li:nth-child(8)").addClass('active');
		applyEvenOdd('#charset-list');
	</script>
</body>
</html>
