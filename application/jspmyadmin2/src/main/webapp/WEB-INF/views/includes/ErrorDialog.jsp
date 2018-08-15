<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
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