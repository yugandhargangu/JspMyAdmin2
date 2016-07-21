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
		root : '${pageContext.request.contextPath}',
		database : '${sessionScope.session_db}',
		table : '${sessionScope.session_table}'
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
	src="${pageContext.request.contextPath}/components/codemirror/sql.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/codemirror/addon/hint/show-hint.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/codemirror/addon/hint/sql-hint.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jspmyadmin.js"></script>
<m:store name="lbl_select_file" key="lbl.select_file" />
<m:store name="msg_no_file_selected" key="msg.no_file_selected" />
<script type="text/javascript">
	var lbl_select_file = '${lbl_select_file}';
	var msg_no_file_selected = '${msg_no_file_selected}';
	$(document).ready(function() {
		$('#right-menu-btn').click(function() {
			var leftPosition = $('#header-menu li:first-child').position();
			var left = leftPosition.left - $('#topbar').width();
			var rightPosition = $('#header-menu li:last-child').position();
			var right = rightPosition.left - $('#topbar').width();
			if (right < $('#topbar').width()) {
				left = left - right + $('#topbar').width() - ($('#header-menu li:last-child').width() * 3);
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
		$('input[type="file"]').each(function() {
			$(this).hide();
			$(this).wrap(function() {
				return '<div class="div-inline"></div>';
			});
		});
		$('.div-inline').each(function() {
			$(this).append('<button type="button" class="btn btn-file-select">' + lbl_select_file + '</button>');
			$(this).append('<div class="file-name">' + msg_no_file_selected + '</div>');
		});
		$('.btn-file-select').click(function() {
			$(this).parent().find('input[type="file"]').click();
		});
		$('input[type="file"]').change(function() {
			$(this).parent().find('.file-name').text($(this).val());
			$(this).parent().find('.file-name').prop("title", $(this).val());
		});
	});
</script>