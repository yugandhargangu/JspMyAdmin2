<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../includes/Head.jsp" />
</head>
<body>
	<div id="content">
		<div id="sidebar" style="width: 20%;">
			<jsp:include page="../includes/SideBar.jsp" />
		</div>
		<div id="main-content" style="width: 80%;">
			<div id="topbar">
				<jsp:include page="../includes/TopBar.jsp" />
			</div>
			<div id="header-div"><jsp:include page="../server/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 1em;">
					<div
						style="width: 58%; margin: 0; padding: 0; display: inline-block; -moz-box-sizing: border-box; box-sizing: border-box; vertical-align: top;">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.database_server" />
							</div>
							<div class="group-widget group-content">
								<p>
									<b><m:print key="lbl.server" />:</b>
									${requestScope.command.db_server_name}
								</p>
								<p>
									<b><m:print key="lbl.server_type" />:</b>
									${requestScope.command.db_server_type}
								</p>
								<p>
									<b><m:print key="lbl.server_version" />:</b>
									${requestScope.command.db_server_version}
								</p>
								<p>
									<b><m:print key="lbl.protocol" />:</b>
									${requestScope.command.db_server_protocol}
								</p>
								<p>
									<b><m:print key="lbl.user" />:</b>
									${requestScope.command.db_server_user}
								</p>
								<p>
									<b><m:print key="lbl.charset" />:</b>
									${requestScope.command.db_server_charset}
								</p>
							</div>
						</div>

						<div class="group">
							<div class="group-widget group-header">Web Server</div>
							<div class="group-widget group-content">
								<p>
									<b><m:print key="lbl.server" />:</b>
									${requestScope.command.web_server_name}
								</p>
								<p>
									<b><m:print key="lbl.jdbc_version" />:</b>
									${requestScope.command.jdbc_version}
								</p>
								<p>
									<b><m:print key="lbl.java_version" />:</b>
									${requestScope.command.java_version}
								</p>
								<p>
									<b><m:print key="lbl.servlet_version" />:</b>
									${requestScope.command.servelt_version}
								</p>
								<p>
									<b><m:print key="lbl.jsp_version" />:</b>
									${requestScope.command.jsp_version}
								</p>
							</div>
						</div>

						<div class="group">
							<div class="group-widget group-header">
								<m:print key="title" />
							</div>
							<div class="group-widget group-content">
								<p>
									<b><m:print key="lbl.version" />:</b>
									${requestScope.command.jma_version}
								</p>
							</div>
						</div>
					</div>

					<div
						style="width: 40%; margin: 0; padding: 0; display: inline-block; -moz-box-sizing: border-box; box-sizing: border-box; vertical-align: top;">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.about_jspmyadmin" />
							</div>
							<div class="group-widget group-content">
								<p>JspMyAdmin 2.0 is the software for MySql database
									administration over Web. It provides a user friendly design to
									manage most of the MySql database administration. Create and
									Manage database objects (Database, Table, View, User etc..)
									with simple mouse clicks.</p>
							</div>
						</div>

						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.settings" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input" style="display: block;">
									<label><m:print key="lbl.server_collation" /></label> <select
										name="collation" id="server_collation" class="form-control">
										<jma:forLoop items="#collation_map" name="charset"
											key="charsetKey" scope="command">
											<optgroup label="${charsetKey}">
												<jma:forLoop items="#charset" name="collationName"
													scope="page">
													<jma:switch name="#collationName" scope="page">
														<jma:case value="#collation" scope="command">
															<option value="${collationName}" selected="selected">${collationName}</option>
														</jma:case>
														<jma:default>
															<option value="${collationName}">${collationName}</option>
														</jma:default>
													</jma:switch>
												</jma:forLoop>
											</optgroup>
										</jma:forLoop>
									</select>
								</div>

								<div class="form-input" style="display: block;">
									<label><m:print key="lbl.language" /></label> <select
										name="language" id="server_language" class="form-control">
										<jma:forLoop items="#language_map" name="language"
											key="languageKey" scope="command">
											<option value=""><m:print key="lbl.select_language" /></option>
											<jma:switch name="#languageKey" scope="page">
												<jma:case value="#language" scope="command">
													<option value="${languageKey}" selected="selected">${language}</option>
												</jma:case>
												<jma:default>
													<option value="${languageKey}">${language}</option>
												</jma:default>
											</jma:switch>
										</jma:forLoop>
									</select>
								</div>

								<div class="form-input">
									<label><m:print key="lbl.font_size" /></label> <select
										name="fontsize" id="fontsize" class="form-control">
										<jma:forLoop items="#fontsize_map" name="fontsize"
											key="fontsizeKey" scope="command">
											<jma:switch name="#fontsizeKey" scope="page">
												<jma:case value="#fontsize" scope="command">
													<option value="${fontsizeKey}" selected="selected">${fontsize}</option>
												</jma:case>
												<jma:default>
													<option value="${fontsizeKey}">${fontsize}</option>
												</jma:default>
											</jma:switch>
										</jma:forLoop>
									</select>
								</div>

							</div>
						</div>

					</div>

				</div>
			</div>
			<div id="footer">
				<jsp:include page="../includes/Footer.jsp" />
			</div>
		</div>

	</div>
</body>
</html>