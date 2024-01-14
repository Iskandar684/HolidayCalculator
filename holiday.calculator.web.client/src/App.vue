<template>
  <nav v-show="isLoggedIn">
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
</style>
