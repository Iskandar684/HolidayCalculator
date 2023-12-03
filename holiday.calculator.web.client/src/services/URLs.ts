export class URLs {

    /** Корневой путь к серверу */
    //static APP_URL = "http://192.168.0.230:8080/holiday-calculator-web-service/";
    static APP_URL = "http://" + window.location.host + "/holiday-calculator-web-service/";

    /** Путь к входу в систему */
    static LOGIN_URL = this.APP_URL + "login/"

    /** Путь к выходу из системы */
    static LOGOUT_URL = this.APP_URL + "logout/"

    /** Путь получения текущего пользователя */
    static CURRENT_USER_URL = this.APP_URL + "user"

    /** Путь получения информации об отгулах текущего пользователя */
    static CURRENT_USER_HOLIDAYS_INFO_URL = this.APP_URL + "userHolidaysInfo"
}