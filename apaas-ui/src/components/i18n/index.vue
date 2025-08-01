<template>
  <div class="i18n-container">
    <el-dropdown @command="handleLanguageChange">
      <span class="el-dropdown-link">
        <svg-icon icon-class="language" />
        <span class="language-text">{{ currentLanguage }}</span>
        <i-ep-arrow-down class="el-icon-arrow-down" />
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item 
            v-for="item in languages" 
            :key="item.value" 
            :command="item.value"
            :disabled="item.value === locale"
          >
            {{ item.label }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useI18n } from 'vue-i18n'

// 定义语言选项
const languages = [
  { value: 'zh-CN', label: '中文' },
  { value: 'en', label: 'English' }
]

// 使用 i18n
const { locale } = useI18n()

// 计算属性
const currentLanguage = computed(() => {
  const lang = languages.find(item => item.value === locale.value)
  return lang ? lang.label : '中文'
})

// 语言切换
const handleLanguageChange = (lang) => {
  locale.value = lang
  // 保存到 localStorage
  localStorage.setItem('language', lang)
  // 刷新页面以应用语言设置
  location.reload()
}
</script>

<style scoped>
.i18n-container {
  display: inline-block;
  margin-right: 20px;
}

.el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
  color: #303133;
}

.language-text {
  margin: 0 5px;
}

.el-icon-arrow-down {
  font-size: 12px;
}
</style>