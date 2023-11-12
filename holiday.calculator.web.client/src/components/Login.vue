<template>
    <h1>Вход в личный кабинет</h1>
    <form>
        <div class="form_element">
            <p> <label>Логин:</label></p>
            <p> <input /> </p>
            <p> <label>Пароль:</label> </p>
            <p><input type="password"> </p>
        </div>
        <div>
            <button @click="loginPressed" type="button">Войти</button>
        </div>
    </form>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions } from 'vuex';

export default defineComponent({
    name: 'Вход',
    methods: {
        ...mapActions(['login']),
        loginPressed() {
            console.log('loginPressed')
            let params: LoginParams;
            params = { login: "admin", password: "admin" };
            this.login(params);
        }
    }

})

</script>


<style scoped>
.form_element {
    margin: 10px 0;
}
</style>