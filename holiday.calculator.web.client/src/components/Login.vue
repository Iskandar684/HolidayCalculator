<template>
    <h1>Вход в личный кабинет</h1>
    <form>
        <div class="form_element">
            <p> <label>Логин:</label></p>
            <p> <input v-model.trim="params.login" /> </p>
            <p> <label>Пароль:</label> </p>
            <p><input v-model.trim="params.password" type="password"> </p>
            <p v-show="hasLoginMessage" id="login_message"> <label>{{ getLoginMessage() }}</label> </p>
        </div>
        <div>
            <button @click="loginPressed" :disabled="cannotLogin" type="button">Войти</button>
        </div>
    </form>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions } from 'vuex';
import { mapGetters } from 'vuex';
import { LoginParams } from '@/types/LoginParams'

export default defineComponent({
    name: 'Вход',
    data: function () {
        return {
            params: { login: '', password: '' } as LoginParams,
        }
    },
    methods: {
        ...mapActions(['login', 'checkAuthentication']),
        ...mapGetters(['getLoginMessage']),
        loginPressed(): void {
            console.log('loginPressed')
            this.login(this.$data.params);
        }
    },
    computed: {
        cannotLogin(): boolean {
            if (this.params.login === null || this.params.password === null) {
                return true;
            }
            if (this.params.login.length === 0 || this.params.password.length === 0) {
                return true;
            }
            return false;
        },

        hasLoginMessage(): boolean {
            let message = this.getLoginMessage();
            return message != null && message.length != 0;
        }
    },
    mounted() {
        this.checkAuthentication();
    },

})

</script>


<style scoped>
.form_element {
    margin: 10px 0;
}

#login_message {
    color: brown;
}
</style>