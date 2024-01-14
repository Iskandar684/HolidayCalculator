<template>
    <div id="myModal" v-if="isOpen" class="modal">
        <div class="modal-content">
            <span @click="isOpen = false" class="close">&times;</span>
            <p></p>
            <div class="holidayDate">
                <span>
                    <label for="date">Пожалуйста, выберите рабочий день:</label>
                    <input type="date" id="date" v-model="holidayDate" />
                </span>
            </div>
            <p></p>
            <div class="buttons">
                <button :disabled="!isValidDate()" @click="createHolidayStatement()" class="createHolidayStatementBt">Подать
                    заявление на отгул</button>
                <button @click="isOpen = false" class="closeBt">Закрыть</button>
            </div>
        </div>
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
import { ref } from 'vue'
import { emitter } from '@/main'

export default defineComponent({
    name: "NewHolidayStatementDialog",
    data: function () {
        return {
            isOpen: false,
            holidayDate: String
        }
    },
    mounted() {
        emitter.on("openNewHolidayStatementDialog", () => {
            this.$data.isOpen = true;
        })
    },
    methods: {
        isValidDate(): boolean {
            let date = <String>this.$.data.holidayDate;
            if (date == null || date.length < 8) {
                return false;
            }
            return true;
        },
        createHolidayStatement() {
            if (!this.isValidDate()) {
                this.openDialog("Пожалуйста, выберите дату.");
                return;
            }
            let date = new Date(<string>this.$.data.holidayDate);
            let request = new TakeHolidayRequest([date]);
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
                    this.$.data.isOpen = false
                    this.$router.push(`/statement/${statement.uuid}`);
                })
                .catch(error => {
                    console.log("newHolidayStatementError " + error.message);
                    emitter.emit("showMessageDialog", error.message)
                })
        },
        openDialog(aMessage: String) {
            emitter.emit("showMessageDialog", aMessage)
        }
    }
});
</script>

<style scoped>
.modal {
    position: fixed;
    /* Stay in place */
    z-index: 1;
    /* Sit on top */
    left: 0;
    top: 0;
    width: 100%;
    /* Full width */
    height: 100%;
    /* Full height */
    overflow: auto;
    /* Enable scroll if needed */
    background-color: rgb(0, 0, 0);
    /* Fallback color */
    background-color: rgba(0, 0, 0, 0.4);
    /* Black w/ opacity */
}

.holidayDate {
    text-align: left;
}

p {
    position: relative;
    text-align: left;
}


.modal-content {
    background-color: #fefefe;
    margin: 15% auto;
    /* 15% from the top and centered */
    padding: 20px;
    border: 1px solid #888;
    width: 500px;
    /* Could be more or less, depending on screen size */
    text-align: right;
}


.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}

.createHolidayStatementBt {
    margin-right: 10px;
}

#date {
    margin-left: 10px;
}
</style>