// JavaScript Document
jQuery(document).ready(function() {
	$("input[name='starttime']").datetimepicker({
		dateFormat : 'yy-mm-dd',
		timeFormat : 'HH:mm:ss'
	});
	$("input[name='finishtime']").datetimepicker({
		dateFormat : 'yy-mm-dd',
		timeFormat : 'HH:mm:ss'
	});

});
