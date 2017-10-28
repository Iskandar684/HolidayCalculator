var url = "http://192.168.196.129:8080/holiday-calculator-web-service/";
var userName;
var password;

function updateFIO() {

	$.getJSON(url + "user/" + login + "/" + password, function(data) {
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

function openAuthorizationDialog() {
	$('#dialog_auth').dialog('open');
}

function initAuthorizationDialog() {
	$("#dialog_auth").dialog({
		buttons : {
			"Вход" : function() {
				$(this).dialog("close");
				login();
			},
			"Закрыть" : function() {
				$(this).dialog("close");
			}
		}
	});
}

function login() {
	userName = document.getElementById('user_name').value;
	password = document.getElementById('user_password').value;
	$.getJSON(url + "login/" + userName + "/" + password, function(logged) {
		if (logged) {
			loadContentByLoggedUser();
			document.getElementById('user_auth').style.display = 'none';
			document.getElementById("userinfo").style.display = 'block';
		} else {
			openAuthorizationDialog()
		}

	});
}

function loadContentByLoggedUser() {
	$.getJSON(url + "HolidaysQuantity", function(data) {
		updateHolidayCount(data);
	});

	$.getJSON(url + "user", function(data) {
		var fio = data.lastName + ' ' + data.firstName + ' ' + data.patronymic;
		updateFIO(fio);
	});
}

$(document).ready(function() {
	initAuthorizationDialog();
	$('#user_auth').click(function() {
		openAuthorizationDialog();
	});
});
