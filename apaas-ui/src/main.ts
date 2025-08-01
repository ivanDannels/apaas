import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// 引入Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 引入全局样式
import './assets/styles/global.scss'

// 引入国际化
import i18n from './i18n'

// 引入Element Plus图标
import setupElementPlus from './plugins/element'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.use(i18n)

// 配置Element Plus图标
setupElementPlus(app);

app.mount('#app')