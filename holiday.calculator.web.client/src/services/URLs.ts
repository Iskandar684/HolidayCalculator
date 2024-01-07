export class URLs {

    /** Корневой путь к серверу */
    static APP_URL = "http://" + window.location.host + "/holiday-calculator-web-service/";

    /** Путь к входу в систему */
    static LOGIN_URL = this.APP_URL + "login/"

    /** Путь к выходу из системы */
    static LOGOUT_URL = this.APP_URL + "logout/"

    /** Путь получения текущего пользователя */
    static CURRENT_USER_URL = this.APP_URL + "user"

    /** Путь получения информации об отгулах текущего пользователя */
    static CURRENT_USER_HOLIDAYS_INFO_URL = this.APP_URL + "userHolidaysInfo"

    /** Путь получения заявлений текущего пользователя */
    static CURRENT_USER_STATEMENTS_URL = this.APP_URL + "currentUserStatements"

    /**Путь к ресурсу подачи заявления на отгул */
    static TAKE_HOLIDAY_URL = this.APP_URL + "takeHoliday"

    /**Путь к ресурсу получения документа (печатной формы) заявления */
    static STATEMENT_DOCUMENT_URL = this.APP_URL + "statementDocument/"
}