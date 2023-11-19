<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <UserHolidaysInfo v-if="isLoggedIn()" />
    <Login v-else />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import UserHolidaysInfo from '@/components/UserHolidaysInfo.vue'; // @ is an alias to /src
import Login from '@/components/Login.vue';
import { mapMutations } from 'vuex';
import { mapGetters } from 'vuex';

export default defineComponent({
  name: 'HomeView',
  components: {
    UserHolidaysInfo,
    Login
  },
  methods: {
    ...mapGetters(['isLoggedIn'])
  },
  computed: {},
});
</script>
