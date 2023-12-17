<template>
    <div id="buttons">
        <button @click="takeHoliday">Взять отгул</button>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ErrorInfo } from '@/types/ErrorInfo'
import dayjs from 'dayjs';
import { URLs } from '@/services/URLs'
import { Statement } from '@/types/Statement'
import { TakeHolidayRequest } from '@/types/TakeHolidayRequest';
import store from '@/store'

export default defineComponent({
    name: 'NewStatement',
    data: () => ({}),
    components: {
    },
    methods: {
        takeHoliday() {
            let request = new TakeHolidayRequest([new Date()]);
            let vm = this;
            fetch(URLs.TAKE_HOLIDAY_URL, {
                method: 'POST',
                body: JSON.stringify(request),
                headers: { 'Content-Type': 'application/json; charset=UTF-8' }
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    return response.json().then((info: ErrorInfo) => {
                        let message = info.code == 401 ? "Неверный логин или пароль." : info.description;
                        store.dispatch("checkAuthentication")
                        throw new Error(message);
                    });
                })
                .then((statement: Statement) => {
                    console.log("newHolidayStatement " + statement);
                })
                .catch(error => {
                    console.log("newHolidayStatementError " + error.message);
                    alert(error)
                })
        },
    },
    computed: {},
});

</script>