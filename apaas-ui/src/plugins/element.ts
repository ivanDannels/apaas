import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { App } from 'vue'

export default function setupElementPlus(app: App<Element>): void {
  // 注册所有图标
  for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
  
  // 可以在这里添加其他Element Plus配置
  // 例如设置全局组件大小、z-index等
}