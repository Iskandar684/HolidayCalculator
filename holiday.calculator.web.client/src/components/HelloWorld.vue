<template>
  <div class="hello">
    <h1>{{ fullLegalName }}</h1>
    <p>Количество отгулов: {{ holidaysInfo?.holidaysQuantity }}
      {{ '(-' + holidaysInfo?.outgoingHolidaysQuantity + ')' }}
      {{ '(+' + holidaysInfo?.incomingHolidaysQuantity + ')' }}
    </p>
    <p>Количество дней отпуска: {{ holidaysInfo?.leaveCount }}</p>
    <p>Дата начала следующего периода: {{ holidaysInfo == null ? "" : formatDate(holidaysInfo.nextLeaveStartDate) }}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions } from 'vuex';
import { mapGetters } from 'vuex';
import { mapMutations } from 'vuex';
import { User } from '@/types/User'
import { ErrorInfo } from '@/types/ErrorInfo'
import { UserHolidaysInfo } from '@/types/UserHolidaysInfo'
import store from '@/store'

export default defineComponent({
  name: 'Личный кабинет',
  props: {
    msg: String,
  },
  data: function () {
    return {
      holidaysInfo: null as UserHolidaysInfo | null,
    }
  },
  methods: {
    formatDate: function (aDate: Date): string {
      return aDate.getDate + "." + aDate.getMonth + "." + aDate.getFullYear;
    },
    loadUserHolidaysInfo() {
      const api = "http://" + window.location.host + "/holiday-calculator-web-service/userHolidaysInfo";
      fetch(api)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          return response.json().then((info: ErrorInfo) => {
            let message = info.code == 401 ? "Неверный логин или пароль." : info.description;
            throw new Error(message);
          });
        })
        .then((info: UserHolidaysInfo) => {
          console.log("UserHolidaysInfo nextLeaveStartDate " + info.nextLeaveStartDate);
          this.$data.holidaysInfo = info;
        })
        .catch(error => {
          console.log("loadUserHolidaysInfo Err " + error.message);
        })
    },
  },
  computed: {
    ...mapGetters(['getCurrentUser', 'isLoggedIn']),
    fullLegalName(): string {
      let currentUser = <User>this.$store.getters.getCurrentUser;
      return currentUser?.lastName + " " + currentUser?.firstName + " " + currentUser?.patronymic;
    }
  },
  mounted() {
    if (!this.isLoggedIn) {
      return;
    }
    this.loadUserHolidaysInfo();
  }

});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
