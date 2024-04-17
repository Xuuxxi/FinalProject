import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Cropper from "vue3-cropper";
import 'vue3-cropper/lib/vue3-cropper.css';

createApp(App).use(Cropper).use(store).use(router).mount('#app')
