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

function updateHolidayCount(aCount) {
	document.getElementById('holidaycount').innerHTML = aCount;
}

$(document).ready(function() {

	// updateFIO () ;
	$.getJSON(url + "user/user1/password1", function(data) {
		var fio = data.lastName + ' ' + data.firstName + ' ' + data.patronymic;
		updateFIO(fio);
	});

	updateHolidayCount(1);

	$.getJSON(url + "HolidaysQuantity/user1/password1", function(data) {
		updateHolidayCount(data);
	});

	$('#callBt').click(function() {
		showFIODialog();
	});

});
