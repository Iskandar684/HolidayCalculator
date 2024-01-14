<template>
  <nav v-show="isLoggedIn">
    <div class="loginInfo">
      <a href="#" @click="logoutAndNavToRoot">Выйти</a>
    </div>
    <router-link to="/">Главная</router-link> |
    <router-link to="/my-statements">Мои заявления</router-link>
  </nav>
  <router-view />
  <MessageDialog></MessageDialog>
</template>

<script lang="ts">
import store from '@/store';
import { defineComponent } from 'vue';
import { mapActions } from 'vuex';
import { mapGetters } from 'vuex';
import { mapMutations } from 'vuex';
import MessageDialog from '@/components/MessageDialog.vue';

export default defineComponent({
  name: 'App',
  data: () => ({}),

  computed: {
    ...mapGetters(['getCurrentUser', 'isLoggedIn'])
  },
  methods: {
    ...mapActions(['logout']),
    logoutAndNavToRoot() {
      this.logout();
      this.$router.push(`/`);
    }
  },
  components: { MessageDialog }
});


</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

.loginInfo {
  position: fixed;
  top: 20px;
  right: 10px;
}
</style>
