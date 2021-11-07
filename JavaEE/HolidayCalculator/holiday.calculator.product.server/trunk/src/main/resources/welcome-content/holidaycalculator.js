var url = "http://" + window.location.host + "/holiday-calculator-web-service/";

var websocket = null;
var canConsider = false;
var canCreateUser = false;

$(document).ready(function() {
	$('#loginBt').click(function() {
		openAuthorizationDialog();
	});
	$('#logoutBt').click(function() {
		logout();
	});
	$('#takeHolidayBt').click(function() {
		openTakeHolidayDialog();
	});
	$('#myStatementsBt').click(function() {
		showStatements("currentUserStatements");
	});
	$('#incomingStatementsBt').click(function() {
		showStatements("incomingStatements");
	});
	$('#createUserBt').click(function() {
		showCreateUserDialog();
	});
	subscribeToServerEvents();
	subscribeToWebSocket();
	checkAndReload();
});

function subscribeToServerEvents() {
	console.log("subscribeToServerEvents");
	if (!window.EventSource) {
		// Internet Explorer или устаревшие браузеры
		alert("Ваш браузер не поддерживает EventSource.");
		return;
	}
	let eventSource = new EventSource(
		"/holiday-calculator-web-service/subscribeToAllEvents");

	eventSource.onmessage = function(event) {
		console.log("Новое сообщение: " + event.data);
		var obj = $.parseJSON(event.data);
		alert(obj["description"] + " (SSE)");
	};

}

function subscribeToWebSocket() {
	var wsProtocol = window.location.protocol == "https:" ? "wss" : "ws";
	var wsURI = wsProtocol + '://' + window.location.host + window.location.pathname + 'holiday-calculator-web-service/websocket';
	console.log("subscribeToWebSocket wsURI= " + wsURI);
	websocket = new WebSocket(wsURI);

	websocket.onopen = function() {
		console.log("websocket open");
	};
	websocket.onmessage = function(event) {
		console.log("Пришло событие по WebSocket " + event.data);
		var obj = $.parseJSON(event.data);
		alert(obj["description"] + " (WebSocket)");
	};
	websocket.onerror = function(event) {
		console.log("websocket onerror " + event);
	};
	websocket.onclose = function() {
		console.log("websocket onclose");
	};
}

function clearStatements() {
	jQuery('#myStatements div').html('');
}

function showStatements(statementType) {
	$.getJSON(url + statementType).done(function(aStatements) {
		clearStatements();
		for (var i = 0; i < aStatements.length; i++) {
			var statement = aStatements[i];
			addStatement(statement);
		}
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getStatementsFailed: type " + statementType + "  " + err + "  " + details);
	});
}

function addStatement(aStatement) {
	var parent = $("#myStatements");
	$.get("statement.html", function(aStatementHTML) {
		console.log("Форма заявления удачно загружена!");
		parent.append(aStatementHTML);
		var nameLb = $('#statementID').find('#name');
		var entry = aStatement.entry;
		var createDate = entry.createDate;
		nameLb[0].innerHTML = "Заявление от " + toString(createDate);
		var statementId = aStatement.statementId.uuid.toString();
		$("#statementID").click(function() {
			openStatementDocument(statementId);
		});
		$('#statementID').attr("id", statementId);
	}, 'html');
}

function openStatementDocument(aStatementID) {
	$.getJSON(url + "getStatementDocument/" + aStatementID).done(function(aDocument) {
		openDocumentDialog(aDocument, aStatementID);
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getStatementDocument: " + err + "  " + details);
	});
}

function openDocumentDialog(aDocument, statementUUID) {
	var dialogParent = $("#dialogParent");

	dialogParent.load("document.html", function(responseTxt, statusTxt, xhr) {
		if (statusTxt == "success") {
			console.log("Форма документа удачно загружена!");
			var contentLb = $('#dialogParent').find('#documentContent');
			contentLb.append(aDocument.content);
		} else if (statusTxt == "error") {
			console.log("Ошибка загрузки формы документа: " + xhr.status + ": " + xhr.statusText);
		} else {
			console.log("Загрузка формы документа: " + xhr.status + ": " + xhr.statusText);
		}
	});

	dialogParent.prop('title', 'Заявление');
	if (canConsider) {
		showConsiderStatementDialog(dialogParent, statementUUID);
	} else {
		showReadOnlyStatementDialog(dialogParent);
	}

}

function showConsiderStatementDialog(dialogParent, statementUUID) {
	dialogParent.dialog({
		resizable: true,
		modal: true,
		width: 'auto',
		height: 550,
		buttons: [{
			text: "Принять",
			click: function() {
				approve(statementUUID)
				dialogParent.dialog("close");
			}
		},
		{
			text: "Отклонить",
			click: function() {
				reject(statementUUID)
				dialogParent.dialog("close");
			}
		},
		{
			text: "Закрыть",
			click: function() {
				dialogParent.dialog("close");
			}
		}
		],
	});
}

function showReadOnlyStatementDialog(dialogParent) {
	dialogParent.dialog({
		resizable: true,
		modal: true,
		width: 'auto',
		height: 550,
		buttons: [{
			text: "Закрыть",
			click: function() {
				dialogParent.dialog("close");
			}
		}
		],
	});
}


function showCreateUserDialog() {
	var dialogParent = $("#dialogParent");

	dialogParent.load("newUser.html", function(responseTxt, statusTxt, xhr) {
		if (statusTxt == "success") {
			console.log("Форма добавления пользователя удачно загружена!");
		} else if (statusTxt == "error") {
			console.log("Ошибка загрузки формы добавления пользователя: " + xhr.status + ": " + xhr.statusText);
		} else {
			console.log("Загрузка формы добавления пользователя: " + xhr.status + ": " + xhr.statusText);
		}
	});

	dialogParent.prop('title', 'Добавление пользователя');
	dialogParent.dialog({
		resizable: true,
		modal: true,
		width: 'auto',
		height: 350,
		buttons: [{
			text: "Добавить",
			click: function() {
				createUser(buildNewUser());
				dialogParent.dialog("close");
			}
		},
		{
			text: "Закрыть",
			click: function() {
				dialogParent.dialog("close");
			}
		}
		],
	});
}

$.postJSON = function(url, data, callback) {
	return jQuery.ajax({
		'type': 'POST',
		'url': url,
		'contentType': 'application/json; charset=utf-8',
		'data': JSON.stringify(data),
		'dataType': 'json',
		'success': callback,
		'error': function(jqXHR, textStatus, errorThrown) {
			console.log("postJSON error: jqXHR: " + jqXHR + " textStatus " + textStatus + " " + errorThrown);
			showErr(jqXHR);
		}
	});
};

function showErr(aJqXHR) {
	var obj = $.parseJSON(aJqXHR.responseText);
	alert(obj["message"] + "\n" + obj["description"]);
}


function buildNewUser() {
	var firstName = document.getElementById('firstName').value;
	var lastName = document.getElementById('lastName').value;
	var patronymic = document.getElementById('patronymic').value;
	var login = document.getElementById('login').value;
	var password = document.getElementById('password').value;
	var newUser = { firstName: firstName, lastName: lastName, patronymic: patronymic, login: login, password: password, note: "Добавлено из web-клиента." };
	return newUser;
}

function createUser(aUser) {
	$.postJSON(url + "createUser/", aUser).done(function(aUser) {
		console.log("User created " + aUser);
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("createUser Failed: " + err + "  " + jqxhr);
	});
}


function approve(statementUUID) {
	$.postJSON(url + "approve/" + statementUUID).done(function(aUser) {
		updateAfterConsider();
	});
}

function reject(statementUUID) {
	$.postJSON(url + "reject/" + statementUUID).done(function(aUser) {
		updateAfterConsider();
	});
}

function updateAfterConsider() {
	checkAndReload();
	showStatements("incomingStatements");
}

function updateFIO() {
	$.getJSON(url + "user").done(function(aUser) {
		var fio = aUser.lastName + ' ' + aUser.firstName + ' ' + aUser.patronymic;
		document.getElementById('fio').innerHTML = fio;
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getUser Failed: " + err + "  " + jqxhr);
	});
}

function updateHolidayCount() {
	$.getJSON(url + "HolidaysQuantity").done(function(aCount) {
		document.getElementById('holidaycount').innerHTML = aCount;
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getHolidaysQuantity Failed: " + err + "  " + jqxhr);
	});
}

function updateOutgoingHolidaysQuantity() {
	$.getJSON(url + "OutgoingHolidaysQuantity").done(function(aQuantity) {
		var text = "";
		if (aQuantity != 0) {
			text = '(-' + aQuantity + ')';
		}
		document.getElementById('OutgoingHolidaysQuantity').innerHTML = text;
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getOutgoingHolidaysQuantity Failed: " + err + "  " + details);
	});
}


function updateIncomingHolidaysQuantity() {
	$.getJSON(url + "IncomingHolidaysQuantity").done(function(aQuantity) {
		var text = "";
		if (aQuantity != 0) {
			text = '(+' + aQuantity + ')';
		}
		document.getElementById('IncomingHolidaysQuantity').innerHTML = text;
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getIncomingHolidaysQuantity Failed: " + err + "  " + details);
	});
}

function updateLeaveCount() {
	$.getJSON(url + "LeaveCount").done(function(aCount) {
		document.getElementById('LeaveCount').innerHTML = aCount;
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getLeaveCount Failed: " + err + "  " + details);
	});
}

function updateOutgoingLeaveCount() {
	$.getJSON(url + "OutgoingLeaveCount").done(function(aCount) {
		var text = "";
		if (aCount != 0) {
			text = '(-' + aCount + ')';
		}
		document.getElementById('OutgoingLeaveCount').innerHTML = text;
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getOutgoingLeaveCount Failed: " + err + "  " + details);
	});
}

function updateNextLeaveStartDate() {
	$.getJSON(url + "NextLeaveStartDate").done(function(aDate) {
		var date = new Date(aDate);
		var text = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear();
		document.getElementById('NextLeaveStartDate').innerHTML = text;
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("getNextLeaveStartDate Failed: " + err + "  " + details);
	});
}

function toString(aDate) {
	var date = new Date(aDate);
	var text = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear();
	return text;
}

function openAuthorizationDialog() {
	var dialogParent = $("#dialog_auth");

	dialogParent.load("login.html", function(responseTxt, statusTxt, xhr) {
		if (statusTxt == "success") {
			console.log("Форма авторизации удачно загружена!");
		} else if (statusTxt == "error") {
			console.log("Ошибка загрузки формы авторизации: " + xhr.status + ": " + xhr.statusText);
		} else {
			console.log("Загрузка формы авторизации: " + xhr.status + ": " + xhr.statusText);
		}
	});

	dialogParent.dialog({
		resizable: false,
		modal: true,
		width: 'auto',
		buttons: [{
			text: "Войти",
			click: function() {
				dialogParent.dialog("close");
				login();
			}
		}, {
			text: "Закрыть",
			click: function() {
				dialogParent.dialog("close");
			}
		}
		]
	});
	document.getElementById('dialog_auth').style.visibility = 'visible';
}

function login() {
	var userName = document.getElementById('user_name').value;
	var password = document.getElementById('user_password').value;
	$.getJSON(url + "login/" + userName + "/" + password).done(function(aIsLogged) {
		console.log("Вход в систему. Удачно: " + aIsLogged);
		reload(aIsLogged);
	}).fail(function(details, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("LoggedIn Failed: " + err + "  " + details);
	});
}

function checkAndReload() {
	console.log("call isLoggedIn");
	$.getJSON(url + "isLoggedIn").done(function(aIsLogged) {
		console.log("isLoggedIn: " + aIsLogged);
		reload(aIsLogged);
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("isLoggedIn Failed: " + err + "  " + jqxhr);
	});
}

function reload(aIsLogged) {
	updateLoginControls(aIsLogged);
	if (aIsLogged) {
		loadContentByLoggedUser();
	} else {
		openAuthorizationDialog();
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
		clearStatements();
	}
}

function loadContentByLoggedUser() {
	updateConsiderButton();
	updateCreateUserButton();
	updateFIO();
	updateHolidayCount();
	updateOutgoingHolidaysQuantity();
	updateIncomingHolidaysQuantity();
	updateLeaveCount();
	updateOutgoingLeaveCount();
	updateNextLeaveStartDate();
}

function updateCreateUserButton() {
	console.log("call canCreateUser");
	$.getJSON(url + "canCreateUser").done(function(aCanCreateUser) {
		console.log("canCreateUser: " + aCanCreateUser);
		canCreateUser = aCanCreateUser;
		if (aCanCreateUser) {
			document.getElementById('createUserBt').style.visibility = 'visible';
		} else {
			document.getElementById('createUserBt').style.visibility = 'hidden';
		}
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("canCreateUser Failed: " + err + "  " + jqxhr);
		document.getElementById('createUserBt').style.visibility = 'hidden';
	});
}

function updateConsiderButton() {
	console.log("call canConsider");
	$.getJSON(url + "canConsider").done(function(aCanConsider) {
		console.log("canConsider: " + aCanConsider);
		canConsider = aCanConsider;
		if (aCanConsider) {
			document.getElementById('incomingStatementsBt').style.visibility = 'visible';
		} else {
			document.getElementById('incomingStatementsBt').style.visibility = 'hidden';
		}
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("canConsider Failed: " + err + "  " + jqxhr);
		document.getElementById('incomingStatementsBt').style.visibility = 'hidden';
	});
}

function openTakeHolidayDialog() {
	var dialogParent = $("#takeHolidayParent");
	var loaded = $("#holidayDateChooser").length;
	if (!loaded) {
		dialogParent.load("takeHoliday.html", function(responseTxt, statusTxt, xhr) {
			if (statusTxt == "success") {
				dialogParent.datepicker();
				loaded = true;
				console.log("Форма подачи заявления на отгул удачно загружена!");
			} else if (statusTxt == "error") {
				console.log("Ошибка загрузки формы подачи заявления на отгул: " + xhr.status + ": " + xhr.statusText);
			} else {
				console.log("Загрузка формы подачи заявления на отгул: " + xhr.status + ": " + xhr.statusText);
			}
		});
	}
	dialogParent.dialog({
		resizable: false,
		modal: true,
		width: 'auto',
		buttons: [{
			text: "Подать",
			click: function() {
				var date = $(this).datepicker('getDate');
				date = new Date(date);
				if (isNaN(date)) {
					alert('Пожалуйста, выберите дату.');
				} else {
					console.info("Подача заявления на отгул на " + date);
					var dates = [date];
					takeHoliday(dates);
					dialogParent.dialog("close");
				}
			}
		}, {
			text: "Закрыть",
			click: function() {
				dialogParent.dialog("close");
			}
		}
		],
	});
	document.getElementById('takeHolidayParent').style.visibility = 'visible';
}

function takeHoliday(aDates) {
	// FIXME должен быть POST, а не GET
	$.getJSON(url + "takeHoliday/" + aDates).done(function() {
		updateHolidayCount();
		updateOutgoingHolidaysQuantity();
		alert('Заявление на отгул успешно подано.');
	}).fail(function(response, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("takeHoliday Failed: " + err + "  " + response);
		if ('conflict'.toUpperCase() == error.toUpperCase()) {
			alert('Заявление на указанную дату (даты) уже было подано Вами ранее.');
		}
	});
}

function logout() {
	$.getJSON(url + "logout").done(function() {
		updateLoginControls(false);

	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("logout Failed: " + err + "  " + jqxhr);
	});
}
