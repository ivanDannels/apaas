import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import routes from './router/routes'

// 引入Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 引入全局样式
import './styles/index.css'
import './styles/variables.module.scss'

// 引入国际化
import i18n from './i18n'

// 引入Element Plus图标
import setupElementPlus from './plugins/element'

// 引入权限store
import { usePermissionStore } from './stores/permission'

const app = createApp(App)

// 创建pinia实例
const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.use(i18n)

// 配置Element Plus图标
setupElementPlus(app);

// 初始化权限store，设置路由信息
const permissionStore = usePermissionStore()
permissionStore.setRoutes(routes.filter(route => !route.meta?.hidden))

app.mount('#app')