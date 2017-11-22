var url = "http://192.168.196.129:8080/holiday-calculator-web-service/";

$(document).ready(function () {
    $('#loginBt').click(function () {
        openAuthorizationDialog();
    });
    $('#logoutBt').click(function () {
        logout();
    });
    $('#takeHolidayBt').click(function () {
        openTakeHolidayDialog();
    });
    $('#myStatementsBt').click(function () {
        openMyStatements();
    });
    checkAndReload();
});

function openMyStatements() {
    $.getJSON(url + "currentUserStatements").done(function (aStatements) {
        for (var i = 0; i < aStatements.length; i++) {
            var statement = aStatements[i];
            addStatement(statement);
        }
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getCurrentUserStatementsFailed: " + err + "  " + details);
    });
}

function addStatement(aStatement) {
    var parent = $("#myStatements");
    $.get("statement.html", function (aStatementHTML) {
        console.log("Форма заявления удачно загружена!");
        parent.append(aStatementHTML);
        var nameLb = $('#statementID').find('#name');
        var entry = aStatement.entry;
        var createDate = entry.createDate;
        nameLb[0].innerHTML = "Заявление от " + toString(createDate);
        var statementId = aStatement.statementId.uuid.toString();
        $("#statementID").dblclick(function () {
            openStatementDocument(statementId);
        });
        $('#statementID').attr("id", statementId);
    }, 'html');
}

function openStatementDocument(aStatementID) {
    $.getJSON(url + "getStatementDocument/" + aStatementID).done(function (aDocument) {
        openDocumentDialog(aDocument);
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getStatementDocument: " + err + "  " + details);
    });
}

function openDocumentDialog(aDocument) {
    var dialogParent = $("#dialogParent");

    dialogParent.load("document.html", function (responseTxt, statusTxt, xhr) {
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
    //не обязательно, что на отгул. Надо смотреть по типу
    dialogParent.prop('title', 'Заявление на отгул');

    dialogParent.dialog({
        resizable: true,
        modal: true,
        width: 'auto',
        height: 550,
        buttons: [{
            text: "Закрыть",
            click: function () {
                dialogParent.dialog("close");
            }
        }
        ],
    });
}

function updateFIO() {
    $.getJSON(url + "user").done(function (aUser) {
        var fio = aUser.lastName + ' ' + aUser.firstName + ' ' + aUser.patronymic;
        document.getElementById('fio').innerHTML = fio;
    }).fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getUser Failed: " + err + "  " + jqxhr);
    });
}

function updateHolidayCount() {
    $.getJSON(url + "HolidaysQuantity").done(function (aCount) {
        document.getElementById('holidaycount').innerHTML = aCount;
    }).fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getHolidaysQuantity Failed: " + err + "  " + jqxhr);
    });
}

function updateOutgoingHolidaysQuantity() {
    $.getJSON(url + "OutgoingHolidaysQuantity").done(function (aQuantity) {
        var text = "";
        if (aQuantity != 0) {
            text = '(-' + aQuantity + ')';
        }
        document.getElementById('OutgoingHolidaysQuantity').innerHTML = text;
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getOutgoingHolidaysQuantity Failed: " + err + "  " + details);
    });
}


function updateIncomingHolidaysQuantity() {
    $.getJSON(url + "IncomingHolidaysQuantity").done(function (aQuantity) {
        var text = "";
        if (aQuantity != 0) {
            text = '(+' + aQuantity + ')';
        }
        document.getElementById('IncomingHolidaysQuantity').innerHTML = text;
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getIncomingHolidaysQuantity Failed: " + err + "  " + details);
    });
}

function updateLeaveCount() {
    $.getJSON(url + "LeaveCount").done(function (aCount) {
        document.getElementById('LeaveCount').innerHTML = aCount;
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getLeaveCount Failed: " + err + "  " + details);
    });
}

function updateOutgoingLeaveCount() {
    $.getJSON(url + "OutgoingLeaveCount").done(function (aCount) {
        var text = "";
        if (aCount != 0) {
            text = '(-' + aCount + ')';
        }
        document.getElementById('OutgoingLeaveCount').innerHTML = text;
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getOutgoingLeaveCount Failed: " + err + "  " + details);
    });
}

function updateNextLeaveStartDate() {
    $.getJSON(url + "NextLeaveStartDate").done(function (aDate) {
        var date = new Date(aDate);
        var text = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear();
        document.getElementById('NextLeaveStartDate').innerHTML = text;
    }).fail(function (details, textStatus, error) {
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

    dialogParent.load("login.html", function (responseTxt, statusTxt, xhr) {
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
            click: function () {
                dialogParent.dialog("close");
                login();
            }
        }, {
            text: "Закрыть",
            click: function () {
                dialogParent.dialog("close");
            }
        }
        ],
    });
    document.getElementById('dialog_auth').style.visibility = 'visible';
}

function login() {
    var userName = document.getElementById('user_name').value;
    var password = document.getElementById('user_password').value;
    $.getJSON(url + "login/" + userName + "/" + password).done(function (aIsLogged) {
        console.log("Вход в систему. Удачно: " + aIsLogged);
        reload(aIsLogged);
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("LoggedIn Failed: " + err + "  " + details);
    });
}

function checkAndReload() {
    console.log("call isLoggedIn");
    $.getJSON(url + "isLoggedIn").done(function (aIsLogged) {
        console.log("isLoggedIn: " + aIsLogged);
        reload(aIsLogged);
    }).fail(function (jqxhr, textStatus, error) {
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
    }
}

function loadContentByLoggedUser() {
    updateFIO();
    updateHolidayCount();
    updateOutgoingHolidaysQuantity();
    updateIncomingHolidaysQuantity();
    updateLeaveCount();
    updateOutgoingLeaveCount();
    updateNextLeaveStartDate();
}

function openTakeHolidayDialog() {
    var dialogParent = $("#takeHolidayParent");
    var loaded = $("#holidayDateChooser").length;
    if (!loaded) {
        dialogParent.load("takeHoliday.html", function (responseTxt, statusTxt, xhr) {
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
            click: function () {
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
            click: function () {
                dialogParent.dialog("close");
            }
        }
        ],
    });
    document.getElementById('takeHolidayParent').style.visibility = 'visible';
}

function takeHoliday(aDates) {
    $.getJSON(url + "takeHoliday/" + aDates).done(function () {
        updateHolidayCount();
        updateOutgoingHolidaysQuantity();
        alert('Заявление на отгул успешно подано.');
    }).fail(function (response, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("takeHoliday Failed: " + err + "  " + response);
        if ('conflict'.toUpperCase() == error.toUpperCase()) {
            alert('Заявление на указанную дату (даты) уже было подано Вами ранее.');
        }
    });
}

function logout() {
    $.getJSON(url + "logout").done(function () {
        updateLoginControls(false);

    }).fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("logout Failed: " + err + "  " + jqxhr);
    });
}
