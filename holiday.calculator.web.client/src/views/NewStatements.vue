<template>
    <div id="buttons">
        <button @click="takeHoliday">Взять отгул</button>
    </div>
    <NewHolidayStatementDialog></NewHolidayStatementDialog>
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
import NewHolidayStatementDialog from '@/components/NewHolidayStatementDialog.vue';

export default defineComponent({
    name: 'NewStatements',
    data: function () {
        return {}
    },
    methods: {
        takeHoliday() {
            emitter.emit("openNewHolidayStatementDialog");
        },
        openDialog(aMessage: String) {
            emitter.emit("showMessageDialog", aMessage)
        }
    },
    computed: {},
    components: {
        NewHolidayStatementDialog
    }
});

</script>