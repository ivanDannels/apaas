import { defineStore } from 'pinia'

export interface SettingsState {
  theme: string
  tagsView: boolean
  fixedHeader: boolean
  sidebarLogo: boolean
  topNav: boolean
  dynamicTitle: boolean
  layout: string
}

export const useSettingsStore = defineStore('settings', {
  state: (): SettingsState => ({
    theme: '#409EFF',
    tagsView: true,
    fixedHeader: true,
    sidebarLogo: true,
    topNav: false,
    dynamicTitle: true,
    layout: 'left'
  }),
  
  actions: {
    changeSetting(payload: { key: keyof SettingsState; value: any }) {
      const { key, value } = payload
      // @ts-ignore
      this[key] = value
    },
    
    resetSetting() {
      this.theme = '#409EFF'
      this.tagsView = true
      this.fixedHeader = true
      this.sidebarLogo = true
      this.topNav = false
      this.dynamicTitle = true
      this.layout = 'left'
    }
  }
})