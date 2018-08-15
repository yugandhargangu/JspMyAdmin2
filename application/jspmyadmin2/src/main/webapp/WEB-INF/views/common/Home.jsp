<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div style="padding: 0.2em 1em;">
	<div style="width: 58%; margin: 0; padding: 0; display: inline-block; -moz-box-sizing: border-box; box-sizing: border-box; vertical-align: top;">
    	<div class="group">
			<div class="group-widget group-header"><m:print key="lbl.database_server" /></div>
			<div class="group-widget group-content">
				<p><b><m:print key="lbl.server" />:</b> {{home_data.db_server_name}}</p>
				<p><b><m:print key="lbl.server_type" />:</b> {{home_data.db_server_type}}</p>
				<p><b><m:print key="lbl.server_version" />:</b> {{ome_data.db_server_version}}</p>
				<p><b><m:print key="lbl.protocol" />:</b>	{{home_data.db_server_protocol}}</p>
				<p><b><m:print key="lbl.user" />:</b> {{home_data.db_server_user}}</p>
				<p><b><m:print key="lbl.charset" />:</b> {{home_data.db_server_charset}}</p>
			</div>
		</div>
		<div class="group">
			<div class="group-widget group-header">Web Server</div>
			<div class="group-widget group-content">
			    <p><b><m:print key="lbl.server" />:</b> {{home_data.web_server_name}}</p>
				<p><b><m:print key="lbl.jdbc_version" />:</b> {{home_data.jdbc_version}}</p>
				<p><b><m:print key="lbl.java_version" />:</b> {{home_data.java_version}}</p>
				<p><b><m:print key="lbl.servlet_version" />:</b> {{home_data.servelt_version}}</p>
				<p><b><m:print key="lbl.jsp_version" />:</b> {{home_data.jsp_version}}</p>
			</div>
		</div>
		<div class="group">
			<div class="group-widget group-header"><m:print key="title" /></div>
			<div class="group-widget group-content">
				<p><b><m:print key="lbl.version" />:</b> {{home_data.jma_version}}</p>
			</div>
		</div>
	</div>
	<div style="width: 40%; margin: 0; padding: 0; display: inline-block; -moz-box-sizing: border-box; box-sizing: border-box; vertical-align: top;">
		<div class="group">
			<div class="group-widget group-header"><m:print key="lbl.about_jspmyadmin" /></div>
			<div class="group-widget group-content">
				<p><m:print key="zyx.about" />	</p>
			</div>
		</div>
		<div class="group">
			<div class="group-widget group-header"><m:print key="lbl.settings" /></div>
			<div class="group-widget group-content">
				<div class="form-input" style="display: block;">
					<label><m:print key="lbl.server_collation" /></label>
					<select class="form-control" ng-model="input_data.collation" ng-change="saveCollation()">
						<optgroup ng-repeat="(charsetKey, collationList) in home_data.collation_map" label="{{charsetKey}}">
							<option ng-repeat="collationName in collationList" value="{{collationName}}">{{collationName}}</option>
						</optgroup>
					</select>
				</div>
				<div class="form-input" style="display: block;">
					<label><m:print key="lbl.language" /></label>
					<select class="form-control" ng-model="input_data.language" ng-change="saveLocale()">
						<option value=""><m:print key="lbl.select_language" /></option>
						<option ng-repeat="(languageKey, language) in home_data.language_map" value="{{languageKey}}">{{language}}</option>
					</select>
				</div>
				<div class="form-input">
					<label><m:print key="lbl.font_size" /></label>
					<select class="form-control" ng-model="input_data.fontsize" ng-change="saveFontSize()">
						<option ng-repeat="fontsize in home_data.fontsizes" value="{{fontsize}}">{{fontsize}}%</option>
					</select>
				</div>
			</div>
		</div>
	</div>
</div>