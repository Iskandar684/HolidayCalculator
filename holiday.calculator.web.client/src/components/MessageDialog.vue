<template>
    <div id="myModal" v-if="isOpen" class="modal">
        <div class="modal-content">
            <span @click="isOpen = false" class="close">&times;</span>
            <p>{{ message }}</p>
            <button @click="isOpen = false" class="closeBt">Закрыть</button>
        </div>
    </div>
</template>

<script lang="ts">
import { emitter } from '@/main'
import { defineComponent } from 'vue';

export default defineComponent({
    name: "MessageDialog",
    data: function () {
        return {
            isOpen: false,
            message: ""
        }
    },
    mounted() {
        emitter.on("showMessageDialog", message => {
            this.$data.isOpen = true;
            this.$data.message = <string>message;
        })
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
    width: 600px;
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
</style>