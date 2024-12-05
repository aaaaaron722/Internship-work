import './assets/main.scss'

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//路由
import router from '@/router'
//pinia獲取token
import {createPinia} from 'pinia'
//persist長期取得token
import { createPersistedState } from 'pinia-persistedstate-plugin'
//中文包
import locale from 'element-plus/dist/locale/zh-tw'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist)
app.use(pinia)
app.use(router)
app.use(ElementPlus, {locale});
app.mount('#app')