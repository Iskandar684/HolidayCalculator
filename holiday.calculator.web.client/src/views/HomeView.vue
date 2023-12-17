<template>
  <div v-show="isLoggedIn()" class="loginInfo">
    <a href="#" @click="logout">Выйти</a>
  </div>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <UserHolidaysInfo v-if="isLoggedIn()" />
    <Login v-else />
    <NewStatement v-show="isLoggedIn()" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import UserHolidaysInfo from '@/components/UserHolidaysInfo.vue'; // @ is an alias to /src
import Login from '@/components/Login.vue';
import { mapActions } from 'vuex';
import { mapGetters } from 'vuex';
import { URLs } from '@/services/URLs';
import NewStatement from './NewStatement.vue';

export default defineComponent({
  name: 'HomeView',
  data() {
    return {};
  },
  components: {
    UserHolidaysInfo,
    Login,
    NewStatement
  },
  methods: {
    ...mapGetters(['isLoggedIn']),
    ...mapActions(['logout']),
  },
  computed: {},
});
</script>

<style scoped>
a {
  color: #42b983;
}

.loginInfo {
  position: fixed;
  top: 20px;
  right: 10px;
}
</style>
