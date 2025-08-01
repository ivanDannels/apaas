<template>
  <div class="drawer-container">
    <div class="drawer-title">
      <span>系统布局配置</span>
    </div>
    
    <div class="drawer-item">
      <span>开启 Tags-View</span>
      <el-switch v-model="settingsStore.tagsView" class="drawer-switch" />
    </div>
    
    <div class="drawer-item">
      <span>固定 Header</span>
      <el-switch v-model="settingsStore.fixedHeader" class="drawer-switch" />
    </div>
    
    <div class="drawer-item">
      <span>侧边栏 Logo</span>
      <el-switch v-model="settingsStore.sidebarLogo" class="drawer-switch" />
    </div>
    
    <div class="drawer-item">
      <span>开启 TopNav</span>
      <el-switch v-model="settingsStore.topNav" class="drawer-switch" />
    </div>
    
    <div class="drawer-item">
      <span>开启动态标题</span>
      <el-switch v-model="settingsStore.dynamicTitle" class="drawer-switch" />
    </div>
    
    <el-divider />
    
    <div class="drawer-item">
      <span>主题颜色</span>
      <theme-picker 
        style="float: right; height: 26px; margin: -3px 8px 0 0" 
        @change="themeChange" 
      />
    </div>
    
    <div class="drawer-item">
      <span>导航模式</span>
      <el-radio-group 
        v-model="settingsStore.layout" 
        style="float: right; margin: -3px 8px 0 0"
      >
        <el-radio label="left">左侧</el-radio>
        <el-radio label="top">顶部</el-radio>
      </el-radio-group>
    </div>
    
    <el-divider />
    
    <el-button 
      type="primary" 
      plain 
      icon="DocumentAdd" 
      @click="copyConfig"
    >
      复制配置
    </el-button>
    
    <el-button 
      plain 
      icon="Refresh" 
      @click="resetConfig"
    >
      重置配置
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { ElMessageBox, ElMessage } from 'element-plus'
import { useSettingsStore } from '@/stores/settings'
import type { SettingsState } from '@/stores/settings'
import ThemePicker from '@/components/ThemePicker/index.vue'

const settingsStore = useSettingsStore()

// 主题颜色变化
const themeChange = (val: string) => {
  settingsStore.changeSetting({
    key: 'theme',
    value: val
  })
  // 修改主题颜色后需要刷新页面以应用更改
  location.reload()
}

// 复制配置
const copyConfig = () => {
  const config: Partial<SettingsState> = {
    tagsView: settingsStore.tagsView,
    fixedHeader: settingsStore.fixedHeader,
    sidebarLogo: settingsStore.sidebarLogo,
    topNav: settingsStore.topNav,
    dynamicTitle: settingsStore.dynamicTitle,
    theme: settingsStore.theme,
    layout: settingsStore.layout
  }
  
  const text = JSON.stringify(config, null, 2)
  navigator.clipboard.writeText(text).then(() => {
    ElMessage.success('配置已复制到剪贴板')
  })
}

// 重置配置
const resetConfig = () => {
  ElMessageBox.confirm('确定要重置所有配置吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    settingsStore.resetSetting()
    ElMessage.success('配置已重置')
    // 重置配置后需要刷新页面以应用更改
    location.reload()
  }).catch(() => {})
}
</script>

<style lang="scss" scoped>
.drawer-container {
  padding: 20px;
  
  .drawer-title {
    margin-bottom: 15px;
    color: rgba(0, 0, 0, 0.85);
    font-size: 16px;
    line-height: 22px;
  }
  
  .drawer-item {
    color: rgba(0, 0, 0, 0.65);
    font-size: 14px;
    padding: 10px 0;
    
    .drawer-switch {
      float: right;
    }
  }
  
  .el-button {
    margin-right: 10px;
    margin-top: 10px;
  }
}
</style>