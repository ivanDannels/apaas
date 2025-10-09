import { createI18n } from 'vue-i18n'
import { createI18n } from 'vue-i18n'
import zh from './locales/zh-CN.json'
import en from './locales/en-US.json'

interface MessageSchema {
  [key: string]: string | MessageSchema
}

const messages: { 'zh-CN': MessageSchema; 'en-US': MessageSchema } = {
  'zh-CN': zh,
  'en-US': en
}

const i18n = createI18n({
  legacy: false, // you must set `false`, to use Composition API
  locale: localStorage.getItem('language') || 'zh-CN', // set locale
  fallbackLocale: 'en-US', // set fallback locale
  messages // set locale messages
})

export default i18n