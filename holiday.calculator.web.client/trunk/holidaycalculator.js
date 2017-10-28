var url = "http://192.168.196.129:8080/holiday-calculator-web-service/";

function updateFIO(aFIO) {
	document.getElementById('fio').innerHTML = aFIO;
}

function updateHolidayCount(aCount) {
	document.getElementById('holidaycount').innerHTML = aCount;
}

function openAuthorizationDialog() {
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

	document.getElementById("dialog_auth").style.visibility = 'visible';
}

function login() {
	var userName = document.getElementById('user_name').value;
	var password = document.getElementById('user_password').value;
	$.getJSON(url + "login/" + userName + "/" + password, function(aIsLogged) {
		reload(aIsLogged);
	});
}

function reload(aIsLogged) {
	updateLoginControls(aIsLogged);
	if (aIsLogged) {
		loadContentByLoggedUser();
	} else {
		openAuthorizationDialog()
	}
}

function updateLoginControls(aIsLogged) {
	if (aIsLogged) {
		document.getElementById('login_holder').style.visibility = 'hidden';
		document.getElementById("userinfo").style.display = 'block';
		document.getElementById('logout_holder').style.visibility = 'visible';
	} else {
		document.getElementById('login_holder').style.visibility = 'visible';
		document.getElementById("userinfo").style.display = 'none';
		document.getElementById('logout_holder').style.visibility = 'hidden';
	}
}

function loadContentByLoggedUser() {
	$.getJSON(url + "user").done(function(data) {
		var fio = data.lastName + ' ' + data.firstName + ' ' + data.patronymic;
		updateFIO(fio);
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getUser Failed: " + err + "  " + jqxhr);
	});

	$.getJSON(url + "HolidaysQuantity").done(function(data) {
		updateHolidayCount(data);
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getHolidaysQuantity Failed: " + err + "  " + jqxhr);
	});
}

$(document).ready(function() {
	$('#loginBt').click(function() {
		openAuthorizationDialog();
	});
	$('#logoutBt').click(function() {
		logout();
	});
	$.getJSON(url + "isLoggedIn").done(function(aIsLogged) {
		reload(aIsLogged);

	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("isLoggedIn Failed: " + err + "  " + jqxhr);
	});
});

function logout() {
	$.getJSON(url + "logout").done(function() {
		updateLoginControls(false);

	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("logout Failed: " + err + "  " + jqxhr);
	});
}
