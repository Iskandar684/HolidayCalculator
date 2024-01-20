<template>
    <div class="parent">
        <div v-html="statementDocument" class="doc"></div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { User } from '@/types/User'
import { StatementEntry } from "@/types/StatementEntry";
import { Statement } from '@/types/Statement'
import { StatementType } from '@/types/StatementType'
import { ErrorInfo } from '@/types/ErrorInfo'
import dayjs from 'dayjs';
import { URLs } from '@/services/URLs'
import { mapGetters } from 'vuex';
import { StatementStatus } from '@/types/StatementStatus';
import { StatementDocument } from '@/types/StatementDocument';

export default defineComponent({
    name: 'StatementDocument',
    data: function () {
        return { statementDocument: "" }
    },
    methods: {
        loadStatement(aUUID: string) {
            fetch(URLs.STATEMENT_DOCUMENT_URL + aUUID)
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    return response.json().then((info: ErrorInfo) => {
                        let message = info.code == 401 ? "Неверный логин или пароль." : info.description;
                        throw new Error(message);
                    });
                })
                .then((document: StatementDocument) => {
                    this.$data.statementDocument = document.content;
                    console.log("document " + document);
                })
                .catch(error => {
                    console.log("load statementDocument Error" + error.message);
                })
        }
    },
    async mounted() {
        let statementUUID = <string>this.$route.params.uuid;
        console.log("statementUUID " + statementUUID)
        this.loadStatement(statementUUID);
    }
})


</script>

<style scoped>
.parent {
    background-color: #aaa;
}

.doc {
    background-color: #fefefe;
    border: 1px solid #888;
    width: 800px;
    margin-left: auto;
    margin-right: auto;
}
</style>