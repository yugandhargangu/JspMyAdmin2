'use strict';
var AjaxHelper = {};
var token = '';
AjaxHelper.generateResponse = function(data, headers, status) {
	var actualData = {};
	if(data != null) {
		actualData = angular.fromJson(data);
	} 
	if( actualData.token && actualData.token != ''){
		token = actualData.token;
	}
	if (status == 200) {
	    var error = false;
	    if(actualData.err !== undefined && actualData.err !== null){
	        $('#sidebar-error-msg').text(actualData.err);
	    	$('#sidebar-error-dialog').show();
	    	error = true;
	    }
	    if(actualData.msg !== undefined && actualData.msg !== null){
	        $('#sidebar-success-msg').text(actualData.msg);
	    	$('#sidebar-success-dialog').show();
	    }
		return {status : 0,error:error, data : actualData};
	}
	return {status : 1,headers : headers};
};
var IdHelper = {
	id : 1,
	name : 'jma_id_'
};
IdHelper.generate = function() {
	this.id++;
	return this.name + this.id;
};

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
	$('#sidebar-error-close').on('click', function() {
		$('#sidebar-error-dialog').hide();
		$('#sidebar-error-msg').text('');
	});

	$('#sidebar-success-close').on('click', function() {
		$('#sidebar-success-dialog').hide();
		$('#sidebar-success-msg').text('');
	});
});

var ErrorDialog = {
    show:function(msg) {
        $('#error-content').text(msg);
        $('#error-dialog').show();
    },
    hide:function() {
        $('#error-dialog').hide();
    }
};
$(function(){
    $('#error-close').on('click', function(){
        ErrorDialog.hide();
    });
});