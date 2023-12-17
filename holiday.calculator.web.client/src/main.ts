import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import dayjs, { Dayjs } from 'dayjs';
import 'dayjs/locale/ru'
import mitt from 'mitt';

export const emitter = mitt();
const app = createApp(App);
app.config.globalProperties.emitter = emitter;
app.use(store).use(router).mount('#app')
dayjs.locale('ru')
