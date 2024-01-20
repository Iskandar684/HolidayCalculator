<template>
    <h1>Вход в личный кабинет</h1>
    <div class="login">
        <div class="params">
            <p>Логин:</p>
            <input v-model.trim="params.login" />
            <p>Пароль:</p>
            <input v-model.trim="params.password" type="password">
            <p v-show="hasLoginMessage" id="login_message"> <label>{{ getLoginMessage() }}</label></p>
        </div>
        <div class="button">
            <button @click="loginPressed" :disabled="cannotLogin" type="button">Войти</button>
        </div>
    </div>
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
        },
    },
    mounted() {
        this.checkAuthentication();
    },

})

</script>


<style scoped>
.login {
    position: relative;
    margin-left: auto;
    margin-right: auto;
}

.params {
    margin-left: auto;
    margin-right: auto;
    display: block;
    text-align: left;
    width: 200px;
}

p {
    margin-bottom: 2px;
}

input {
    display: block;
    width: 100%;
    box-sizing: border-box;
}

#login_message {
    color: brown;
}

.button {
    margin-top: 10px;
    margin-left: auto;
    margin-right: auto;
}

button {
    margin-left: auto;
    margin-right: auto;
    display: block;
}
</style>