<template>
  <div id="statmentsTable">
    <table>
      <thead>
        <th>Тип заявления</th>
        <th>Дата подачи</th>
        <th>Статус</th>
        <th>Кто рассмотрел</th>
        <th>Дата рассмотрения</th>
      </thead>
      <tbody v-for="statement in statements">
        <tr>
          <td> <router-link :to="`statement/${statement.uuid}`">{{
            getStatementTypeDescription(statement.entry.statementType)
          }}</router-link></td>
          <td>{{ formatDate(statement.entry.createDate) }}</td>
          <td>{{ getStatementStatusDescription(statement.entry.status) }}</td>
          <td>{{ fullLegalName(statement.entry.consider) }}</td>
          <td>{{ formatDate(statement.entry.considerDate) }}</td>
        </tr>
      </tbody>
    </table>

  </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue';
import { User } from '@/types/User'
import { StatementEntry } from "@/types/StatementEntry";
import { Statement } from '@/types/Statement'
import { StatementType } from '@/types/StatementType'
import { ErrorInfo } from '@/types/ErrorInfo'
import dayjs from 'dayjs';
import { URLs } from '@/services/URLs'
import { mapGetters } from 'vuex';
import { StatementStatus } from '@/types/StatementStatus';


export default defineComponent({
  name: 'CurrentUserStatements',
  props: {
    msg: String,
  },
  data: function () {
    return {
      statements: [] as Statement[]
    };
  },
  methods: {
    loadCurrentUserStatements() {
      fetch(URLs.CURRENT_USER_STATEMENTS_URL)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          return response.json().then((info: ErrorInfo) => {
            let message = info.code == 401 ? "Неверный логин или пароль." : info.description;
            throw new Error(message);
          });
        })
        .then((statements: Statement[]) => {
          this.$data.statements = statements;
          console.log("currentUserStatementsCount " + statements.length);
        })
        .catch(error => {
          console.log("loadCurrentUserStatementsError " + error.message);
        })
    },
    fullLegalName(aUser: User | null): string {
      if (aUser == null) {
        return "-";
      }
      return aUser.lastName + " " + aUser.firstName + " " + aUser.patronymic;
    },
    formatDate: function (aDate: Date): string {
      if (aDate == null) {
        return "-";
      }
      return dayjs(aDate).format('DD.MM.YYYY');
    },
    getStatementTypeDescription(aType: StatementType): string {
      const indexOfType = Object.keys(StatementType).indexOf(aType);
      if (indexOfType < 0) {
        console.error("Для типа заявления " + aType + " отсутствует описание.");
        return aType;
      }
      return Object.values(StatementType)[indexOfType];
    },
    getStatementStatusDescription(aStatus: StatementStatus): string {
      const indexOfStatus = Object.keys(StatementStatus).indexOf(aStatus);
      if (indexOfStatus < 0) {
        console.error("Для статуса заявления " + aStatus + " отсутствует описание.");
        return aStatus;
      }
      return Object.values(StatementStatus)[indexOfStatus];
    }
  },
  computed: {

  },
  mounted() {
    this.loadCurrentUserStatements();
  }

});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body,
input {
  /* Настройка шрифта */
  font-family: Helvetica Neue, Arial, sans-serif;
  font-size: 14px;
  color: #106070;
}

table {
  /* Параметры таблицы */
  width: 100%;
  margin: auto;
  border: 2px solid #308090;
  border-radius: 3px;
  background-color: #fff;
}

th,
td {
  /* Общие параметры заголовка и строк */
  min-width: 30px;
  padding: 10px 10px;
}

th {
  /* Параметры заголовка */
  background-color: #42b983;
  color: #ffffff;
  cursor: default;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

td {
  /* Параметры строк */
  background-color: #f3f8f9;
}
</style>
