$(document).ready(function() {

	$('#callBt').click(function() {
		callWS();
	});

});

function callWS() {
	$.getJSON(
			"http://192.168.196.129:8080/holiday-calculator-web-service/user",
			function(data) {
				alert(data.lastName +' '+ data.firstName+' '+data.patronymic);
			});
}
