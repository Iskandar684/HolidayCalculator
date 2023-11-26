import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import dayjs, { Dayjs } from 'dayjs';
import 'dayjs/locale/ru'

createApp(App).use(store).use(router).mount('#app')
dayjs.locale('ru')
