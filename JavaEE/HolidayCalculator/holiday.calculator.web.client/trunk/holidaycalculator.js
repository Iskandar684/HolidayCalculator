var url = "http://192.168.196.129:8080/holiday-calculator-web-service/";

function updateFIO(aFIO) {
    document.getElementById('fio').innerHTML = aFIO;
}

function updateHolidayCount(aCount) {
    document.getElementById('holidaycount').innerHTML = aCount;
}

function updateOutgoingHolidaysQuantity() {
    $.getJSON(url + "OutgoingHolidaysQuantity").done(function (aData) {
        var text = "";
        if (aData != 0) {
            text = '(-' + aData + ')';
        }
        document.getElementById('OutgoingHolidaysQuantity').innerHTML = text;
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getOutgoingHolidaysQuantity Failed: " + err + "  " + details);
    });
}


function updateIncomingHolidaysQuantity() {
    $.getJSON(url + "IncomingHolidaysQuantity").done(function (aData) {
        var text = "";
        if (aData != 0) {
            text = '(+' + aData + ')';
        }
        document.getElementById('IncomingHolidaysQuantity').innerHTML = text;
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getIncomingHolidaysQuantity Failed: " + err + "  " + details);
    });
}

function updateLeaveCount() {
    $.getJSON(url + "LeaveCount").done(function (aData) {
        document.getElementById('LeaveCount').innerHTML = aData;
    }).fail(function (details, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getLeaveCount Failed: " + err + "  " + details);
    });
}

function updateOutgoingLeaveCount() {
    $.getJSON(url + "OutgoingLeaveCount").done(function (aData) {
        var text = "";
        if (aData != 0) {
            text = '(-' + aData + ')';
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
    $.getJSON(url + "login/" + userName + "/" + password, function (aIsLogged) {
        reload(aIsLogged);
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
    $.getJSON(url + "user").done(function (data) {
        var fio = data.lastName + ' ' + data.firstName + ' ' + data.patronymic;
        updateFIO(fio);
    }).fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getUser Failed: " + err + "  " + jqxhr);
    });

    $.getJSON(url + "HolidaysQuantity").done(function (data) {
        updateHolidayCount(data);
    }).fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("getHolidaysQuantity Failed: " + err + "  " + jqxhr);
    });
    updateOutgoingHolidaysQuantity();
    updateIncomingHolidaysQuantity();
    updateLeaveCount();
    updateOutgoingLeaveCount();
    updateNextLeaveStartDate();
}

$(document).ready(function () {
    $('#loginBt').click(function () {
        openAuthorizationDialog();
    });
    $('#logoutBt').click(function () {
        logout();
    });
    $.getJSON(url + "isLoggedIn").done(function (aIsLogged) {
        reload(aIsLogged);

    }).fail(function (jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("isLoggedIn Failed: " + err + "  " + jqxhr);
    });
    $('#takeHolidayBt').click(function () {
        openTakeHolidayDialog();
    });
});


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
