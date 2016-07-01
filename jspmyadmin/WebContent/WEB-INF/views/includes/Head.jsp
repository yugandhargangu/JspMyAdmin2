<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<title><m:print key="title" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/components/codemirror/lib/codemirror.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/components/codemirror/addon/hint/show-hint.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">
<style type="text/css">
html {
	font-size: ${sessionScope.fontsize}%;
}
</style>
<m:store name="lbl_new" key="lbl.new" />
<m:store name="err_unable_to_connect_with_server"
	key="err.unable_to_connect_with_server" />
<script type="text/javascript">
	var Server = {
		keyForJS : '${sessionScope.session_key}',
		root : '${pageContext.request.contextPath}'
	};
	var Msgs = {
		msgNew : '${lbl_new}',
		errMsg : '${err_unable_to_connect_with_server}'
	};
	var JspMyadmin = {
		mimeType : 'text/x-mysql'
	};
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/cryptojs/rollups/aes.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/cryptojs/components/enc-base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/cryptojs/components/mode-ecb.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/codemirror/lib/codemirror.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/codemirror/addon/hint/show-hint.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/codemirror/addon/hint/sql-hint.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/codemirror/sql.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jspmyadmin.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#right-menu-btn').click(function() {
			var leftPosition = $('#header-menu li:first-child').position();
			var left = leftPosition.left - $('#topbar').width();
			var rightPosition = $('#header-menu li:last-child').position();
			var right = rightPosition.left - $('#topbar').width();
			if (right < $('#topbar').width()) {
				left = left - right + $('#topbar').width() - ($('#header-menu li:last-child').width() * 2);
			}
			if (left < 0) {
				$('#header-menu').animate({
					'margin-left' : '' + parseInt(left, 10) + 'px',
				});
			}

		});

		$('#left-menu-btn').click(function() {
			var position = $('#header-menu li:first-child').position();
			var left = position.left + ($('#topbar').width() / 2);
			if (left <= 0) {
				$('#header-menu').animate({
					'margin-left' : '' + parseInt(left, 10) + 'px'
				});
			} else {
				$('#header-menu').animate({
					'margin-left' : '0px'
				});
			}
		});
	});
</script>