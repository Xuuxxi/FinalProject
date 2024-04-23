import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// import Cropper from "vue3-cropper";
// import 'vue3-cropper/lib/vue3-cropper.css'; .use(Cropper)

createApp(App).use(store).use(router).mount('#app')
