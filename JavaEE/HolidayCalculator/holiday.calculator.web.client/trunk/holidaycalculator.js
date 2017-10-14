var url = "http://192.168.196.129:8080/holiday-calculator-web-service/";

function showFIODialog() {
	$.getJSON(url + "user", function(data) {
		alert(data.lastName + ' ' + data.firstName + ' ' + data.patronymic);
	});
}

function updateFIO() {
	$.getJSON(url + "user", function(data) {
		var fio = data.lastName + ' ' + data.firstName + ' ' + data.patronymic;
		updateFIO(fio);
	});
}

function updateFIO(aFIO) {
	document.getElementById('fio').innerHTML = aFIO;
}

$(document).ready(function() {

//	updateFIO () ;
	$.getJSON(url + "user", function(data) {
		var fio = data.lastName + ' ' + data.firstName + ' ' + data.patronymic;
		updateFIO(fio);
	});

	$('#callBt').click(function() {
		showFIODialog();
	});

});
