<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld v-if="isLoggedIn()" msg="Welcome to Your Vue.js + TypeScript App" />
    <Login v-else />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src
import Login from '@/components/Login.vue';
import { mapMutations } from 'vuex';
import { mapGetters } from 'vuex';

export default defineComponent({
  name: 'HomeView',
  components: {
    HelloWorld,
    Login
  },
  methods: {
    ...mapGetters(['isLoggedIn'])
  },
  computed: {},
});
</script>
