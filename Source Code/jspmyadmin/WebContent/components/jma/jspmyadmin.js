/**
 * 
 * @param encryptedValue
 * @returns
 */
function decode(encryptedValue) {
	return encryptedValue;
	// disable for now
	var key = CryptoJS.enc.Base64.parse(Server.keyForJS);
	var decryptedData = CryptoJS.AES.decrypt(encryptedValue, key, {
		mode : CryptoJS.mode.ECB,
		padding : CryptoJS.pad.Pkcs7
	});
	var decryptedText = decryptedData.toString(CryptoJS.enc.Utf8);
	return decryptedText;
}

var load_table = false;

function callDatabase(element, name) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		var html = '<div class="menu-inner">';
		html += '<div class="menu-items">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <img alt="db" class="icon table-icon-img"';
		html += ' onclick="callTable(';
		html += "this,'";
		html += name;
		html += "');";
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> ';
		html += '<div><a href="#"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/pages.png"> <span>';
		html += 'Tables';
		html += '</span></a></div></div></div>';
		html += '<div class="menu-items">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <img alt="db" class="icon"';
		html += ' onclick="callView(';
		html += "this,'";
		html += name;
		html += "');";
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> ';
		html += '<div><a href="#"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/pages.png"> <span>';
		html += 'Views';
		html += '</span></a></div></div></div>';
		html += '<div class="menu-items">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <img alt="db" class="icon"';
		html += 'onclick="callRoutine(';
		html += "this,'";
		html += name;
		html += "');";
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> ';
		html += '<div><a href="#"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/pages.png"> <span>';
		html += 'Routines';
		html += '</span></a></div></div></div>';
		html += '<div class="menu-items">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <img alt="db" class="icon"';
		html += 'onclick="callEvent(';
		html += "this,'";
		html += name;
		html += "');";
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> ';
		html += '<div><a href="#"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/pages.png"> <span>';
		html += 'Events';
		html += '</span></a></div></div></div>';
		html += '<div class="menu-items">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <img alt="db" class="icon"';
		html += 'onclick="callFunction(';
		html += "this,'";
		html += name;
		html += "');";
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> ';
		html += '<div><a href="#"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/pages.png"> <span>';
		html += 'Functions';
		html += '</span></a></div></div></div>';
		html += '<div class="menu-items">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <img alt="db" class="icon"';
		html += 'onclick="callTrigger(';
		html += "this,'";
		html += name;
		html += "');";
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> ';
		html += '<div><a href="#"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/pages.png"> <span>';
		html += 'Triggers';
		html += '</span></a></div></div></div>';
		html += '</div>';
		$(menu_table).append(html);
		src = src.replace('plus-g', 'minus-g');
		$(element).attr('src', src);
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function callTrigger(element, token) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		$.ajax({
			url : Server.root + '/sidebar.text',
			method : 'POST',
			data : {
				'type' : 'callTrigger',
				'token' : token
			},
			success : function(result) {
				if (result == '') {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					return;
				}
				var text = decode(result);
				$(menu_table).append(_callTrigger(text));
				src = src.replace('plus-g', 'minus-g');
				$(element).attr('src', src);
			},
			error : function(result) {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
			}
		});
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function _callTrigger(text) {
	var jsonText = $.parseJSON(text);
	var triggers = jsonText.data;
	var html = '<div class="menu-inner" style="margin-left: 1.2em;">';
	html += '<div class="menu-column">';
	html += '<div class="menu-link">';
	html += '<span>-</span>	<div>';
	html += '<a href="#"><img alt="db" class="icon" src="';
	html += Server.root;
	html += '/components/icons/index-b.png"><span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in triggers) {
		html += '<div class="menu-column">';
		html += '<div class="menu-link">';
		html += '<span>-</span>	<div>';
		html += '<a href="';
		html += triggers[i].token;
		html += '"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/index-b.png"><span> ';
		html += triggers[i].name;
		html += '</span></a></div></div></div>';
	}
	html += '</div>';
	return html;
}

function callTable(element, token) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		$.ajax({
			url : Server.root + '/sidebar.text',
			method : 'POST',
			data : {
				'type' : 'callTable',
				'token' : token
			},
			success : function(result) {
				if (result == '') {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					return;
				}
				var text = decode(result);
				$(menu_table).append(_callTable(text));
				src = src.replace('plus-g', 'minus-g');
				$(element).attr('src', src);
				if (load_table) {
					load_table = false;
					expandTable($(menu_table));
				}
			},
			error : function(result) {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
			}
		});
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function _callTable(text) {

	var jsonText = $.parseJSON(text);
	var tables = jsonText.data;
	var html = '<div class="menu-inner" style="margin-left: 1.2em;">';
	html += '<div class="menu-table">';
	html += '<div class="menu-link">';
	html += '<span>-</span> &nbsp;<img alt="" class="icon" src="';
	html += Server.root;
	html += '/components/icons/blank.png"><div> ';
	html += ' <a href=""><img alt="" class="icon" src="';
	html += Server.root;
	html += '/components/icons/newspaper-b.png"><span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in tables) {
		html += '<div class="menu-table">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <img alt="" class="icon icon-column-img" onclick="callColumn(';
		html += "this,'";
		html += tables[i].token;
		html += "');"
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> <div>';
		html += '<a href="' + Server.root + '/table_data.html?token=' + tables[i].token + '"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/newspaper-b.png"><span> ';
		html += tables[i].name;
		html += '</span></a></div></div></div>';
	}
	html += '</div>';
	return html;
}

function callView(element, token) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		$.ajax({
			url : Server.root + '/sidebar.text',
			method : 'POST',
			data : {
				'type' : 'callView',
				'token' : token
			},
			success : function(result) {
				if (result == '') {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					return;
				}
				var text = decode(result);
				$(menu_table).append(_callView(text));
				src = src.replace('plus-g', 'minus-g');
				$(element).attr('src', src);
			},
			error : function(result) {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
			}
		});
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function _callView(text) {
	var jsonText = $.parseJSON(text);
	var views = jsonText.data;
	var html = '<div class="menu-inner" style="margin-left: 1.2em;" >';
	html += '<div class="menu-column">';
	html += '<div class="menu-link">';
	html += '<span>-</span> ';
	html += '<div><a href="#"><img alt="db" class="icon" src="';
	html += Server.root;
	html += '/components/icons/newspaper-b.png"><span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in views) {
		html += '<div class="menu-column">';
		html += '<div class="menu-link">';
		html += '<span>-</span> ';
		html += '<div><a href="';
		html += views[i].token;
		html += '"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/newspaper-b.png"> <span> ';
		html += views[i].name;
		html += '</span></a></div></div></div>';
	}
	html += '</div>';
	return html;
}

function callRoutine(element, token) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		$.ajax({
			url : Server.root + '/sidebar.text',
			method : 'POST',
			data : {
				'type' : 'callRoutine',
				'token' : token
			},
			success : function(result) {
				if (result == '') {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					return;
				}
				var text = decode(result);
				$(menu_table).append(_callRoutine(text));
				src = src.replace('plus-g', 'minus-g');
				$(element).attr('src', src);
			},
			error : function(result) {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
			}
		});
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function _callRoutine(text) {
	var jsonText = $.parseJSON(text);
	var routines = jsonText.data;
	var html = '<div class="menu-inner" style="margin-left: 1.2em;">';
	html += '<div class="menu-column">';
	html += '<div class="menu-link">';
	html += '<span>-</span> ';
	html += '<div><a href="#"><img alt="db" class="icon" src="';
	html += Server.root;
	html += '/components/icons/historical-b.png"><span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in routines) {
		html += '<div class="menu-column">';
		html += '<div class="menu-link">';
		html += '<span>-</span> ';
		html += '<div><a href="';
		html += routines[i].token;
		html += '"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/historical-b.png"><span> ';
		html += routines[i].name;
		html += '</span></a></div></div></div>';
	}
	html += '</div>';
	return html;
}

function callFunction(element, token) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		$.ajax({
			url : Server.root + '/sidebar.text',
			method : 'POST',
			data : {
				'type' : 'callFunction',
				'token' : token
			},
			success : function(result) {
				if (result == '') {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					return;
				}
				var text = decode(result);
				$(menu_table).append(_callFunction(text));
				src = src.replace('plus-g', 'minus-g');
				$(element).attr('src', src);
			},
			error : function(result) {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
			}
		});
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function _callFunction(text) {
	var jsonText = $.parseJSON(text);
	var functions = jsonText.data;
	var html = '<div class="menu-inner" style="margin-left: 1.2em;">';
	html += '<div class="menu-column">';
	html += '<div class="menu-link">';
	html += '<span>-</span> ';
	html += '<div><a href="#"><img alt="db" class="icon" src="';
	html += Server.root;
	html += '/components/icons/historical-b.png"><span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in functions) {
		html += '<div class="menu-column">';
		html += '<div class="menu-link">';
		html += '<span>-</span> ';
		html += '<div><a href="';
		html += functions[i].token;
		html += '"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/historical-b.png"><span> ';
		html += functions[i].name;
		html += '</span></a></div></div></div>';
	}
	html += '</div>';
	return html;
}

function callEvent(element, token) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		$.ajax({
			url : Server.root + '/sidebar.text',
			method : 'POST',
			data : {
				'type' : 'callEvent',
				'token' : token
			},
			success : function(result) {
				if (result == '') {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					return;
				}
				var text = decode(result);
				$(menu_table).append(_callEvent(text));
				src = src.replace('plus-g', 'minus-g');
				$(element).attr('src', src);
			},
			error : function(result) {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
			}
		});
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function _callEvent(text) {
	var jsonText = $.parseJSON(text);
	var events = jsonText.data;
	var html = '<div class="menu-inner" style="margin-left: 1.2em;">';
	html += '<div class="menu-column">';
	html += '<div class="menu-link">';
	html += '<span>-</span> ';
	html += '<div><a href="#"> <img alt="db" class="icon" src="';
	html += Server.root;
	html += '/components/icons/time-b.png"> <span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in events) {
		html += '<div class="menu-column">';
		html += '<div class="menu-link">';
		html += '<span>-</span> ';
		html += '<div><a href="';
		html += events[i].token;
		html += '"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/time-b.png"><span> ';
		html += events[i].name;
		html += '</span></a></div></div></div>';
	}
	html += '</div>';
	return html;
}

function callColumn(element, token) {
	var menu_link = $(element).parent();
	var menu_table = $(menu_link).parent();
	var src = $(element).attr('src');
	if (src.indexOf('plus-g') >= 0) {
		$.ajax({
			url : Server.root + '/sidebar.text',
			method : 'POST',
			data : {
				'type' : 'callColumn',
				'token' : token
			},
			success : function(result) {
				if (result == '') {
					$('#sidebar-error-msg').text(Msgs.errMsg);
					$('#sidebar-error-dialog').show();
					return;
				}
				var text = decode(result);
				$(menu_table).append(_callColumn(text));
				src = src.replace('plus-g', 'minus-g');
				$(element).attr('src', src);
			},
			error : function(result) {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
			}
		});
	} else if (src.indexOf('minus-g') >= 0) {
		var menu_inner = $(menu_table).find('.menu-inner');
		$(menu_inner).empty();
		$(menu_inner).remove();
		src = src.replace('minus-g', 'plus-g');
		$(element).attr('src', src);
	}
}

function _callColumn(text) {
	var jsonText = $.parseJSON(text);
	var columns = jsonText.data;
	var html = '<div class="menu-inner" style="margin-left: 1.2em;">';
	html += '<div class="menu-column">';
	html += '<div class="menu-link">';
	html += '<span>-</span> <div>';
	html += '<a href="#"><img alt="column" class="icon" src="';
	html += Server.root;
	html += '/components/icons/column-view.png"><span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in columns) {
		html += '<div class="menu-column">';
		html += '<div class="menu-link">';
		html += '<span>-</span> <div> ';
		html += '<a href="#';
		// html += columns[i].token;
		html += '"><img alt="column" class="icon" src="';
		html += Server.root;
		html += '/components/icons/column-view.png"><span> ';
		html += columns[i].name;
		html += '</span></a></div></div></div>';
	}

	html += '</div>';
	return html;
}

function menubarMain() {
	$.ajax({
		url : Server.root + '/sidebar.text',
		method : 'POST',
		data : {
			'type' : 'menubarMain'
		},
		success : function(result) {
			if (result == '') {
				$('#sidebar-error-msg').text(Msgs.errMsg);
				$('#sidebar-error-dialog').show();
				return;
			}
			var text = decode(result);
			_menubarMain(text);
			expandDatabase();
		},
		error : function(result) {
			$('#sidebar-error-msg').text(Msgs.errMsg);
			$('#sidebar-error-dialog').show();
		}
	});
}

function _menubarMain(text) {
	var jsonData = $.parseJSON(text);
	var databases = jsonData.data;
	$('#menubar-main').empty();
	var html = '<div class="menu-database">';
	html += '<div class="menu-link">';
	html += '<img alt="" class="icon" src="';
	html += Server.root;
	html += '/components/icons/blank.png"> ';
	html += '<div><a href="#"><img alt="db" class="icon" src="';
	html += Server.root;
	html += '/components/icons/add-database.png"> <span> ';
	html += Msgs.msgNew;
	html += '</span></a></div></div></div>';
	for ( var i in databases) {
		html += '<div class="menu-database">';
		html += '<div class="menu-link">';
		html += '<img alt="db" class="icon db-icon-img" onclick="callDatabase(';
		html += "this,'";
		html += databases[i].token;
		html += "');";
		html += '" src="';
		html += Server.root;
		html += '/components/icons/plus-g.png"> ';
		html += '<div><a href="';
		html += Server.root;
		html += '/database_structure.html?token=';
		html += databases[i].token;
		html += '"><img alt="db" class="icon" src="';
		html += Server.root;
		html += '/components/icons/database.png"> <span>';
		html += databases[i].name;
		html += '</span></a></div></div></div>';
		i++;
	}
	$('#menubar-main').append(html);
}

function applyEvenOdd(id) {
	var even = true;
	$(id).find('tr').each(function(index) {
		if ($(this).find('td').length > 0) {
			if (even) {
				$(this).addClass('even');
			} else {
				$(this).addClass('odd');
			}
			even = !even;
		}
	});
}

function searchTable(table, columns, text) {
	$(table).find('tr').each(function(indexTr, tr) {
		var tds = $(tr).find('td');
		if (tds.length > 0) {
			var found = false;
			tds.each(function(index, td) {
				var regExp = new RegExp(text, 'i');
				var is0 = columns.length < 1;
				var isN0 = columns.length > 0;
				isN0 = isN0 && $.inArray(index, columns) > -1;
				if (is0 || (isN0 && regExp.test($(td).text()))) {
					found = true;
					return false;
				}
			});
			if (found == true) {
				$(tr).show();
			} else {
				$(tr).hide();
			}
		}
	});
}

function expandDatabase() {
	if (Server.database != '') {
		$('#menubar-main').find('.menu-database').each(function() {
			var database_name = $(this).find('.menu-link').find('div').find('span').text().trim();
			if (Server.database == database_name) {
				$(this).find('.menu-link').find('.db-icon-img').click();
				if (Server.table != '') {
					$(this).find('.menu-inner').find('.menu-items').each(function() {
						var itemNames = $(this).find('.menu-link').find('div').find('span').text().trim();
						if ('Tables' == itemNames) {
							load_table = true;
							$(this).find('.menu-link').find('.table-icon-img').click();
						}
					});
				}
			}
		});
	}
}

function expandTable(element) {
	$(element).find('.menu-inner').find('.menu-table').each(function() {
		var tableItems = $(this).find('.menu-link').find('div').find('span').text().trim();
		if (Server.table == tableItems) {
			$(this).find('.menu-link').find('.icon-column-img').click();
		}
	});
}

function scrollTo(selector) {
	$('#main-content').animate({
		scrollTop : $(selector).offset().top
	}, 'slow');
}

function showWaiting() {
	$('#sidebar-loading-dialog').show();
}

function hideWaiting() {
	$('#sidebar-loading-dialog').hide();
}

$(function() {
	menubarMain();
	$('#sidebar-error-close').click(function() {
		$('#sidebar-error-dialog').hide();
		$('#sidebar-error-msg').text('');
	});

	$('#sidebar-success-close').click(function() {
		$('#sidebar-success-dialog').hide();
		$('#sidebar-success-msg').text('');
	});

	$('#header-menu li a, .breadcrumb li a').click(function() {
		showWaiting();
	});
});