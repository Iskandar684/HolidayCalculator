var url = "http://192.168.196.129:8080/holiday-calculator-web-service/";
var login;
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

function authorization() {

	$(function() {
		$("#dialog_auth")
				.dialog(
						{
							resizable : false,
							bgiframe : true,
							autoOpen : false,
							modal : true,
							width : "400px",
							position : [ "center", "center" ],
							buttons : {
								"Вход" : function() {
									login = document
											.getElementById('user_name').value;
									password = document
											.getElementById('user_password').value;

									$.getJSON(url + "user/" + login + "/"
											+ password, function(data) {
										var fio = data.lastName + ' '
												+ data.firstName + ' '
												+ data.patronymic;
										updateFIO(fio);
									});

									$.getJSON(url + "HolidaysQuantity/" + login
											+ "/" + password, function(data) {
										updateHolidayCount(data);
									});

									document.getElementById('user_auth').innerHTML='Сменить пользователя';
									document.getElementById("userinfo").style.display = 'block'; 
									
									$(this).dialog("close");
								},
								"Закрыть" : function() {
									$(this).dialog("close");
								}
							}
						});
		$('#user_auth').click(function() {
			$('#dialog_auth').dialog('open');
		});
	});

}

$(document).ready(function() {
	authorization();
});
