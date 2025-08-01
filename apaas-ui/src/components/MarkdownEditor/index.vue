<template>
  <div class="markdown-editor">
    <v-md-editor
      v-model="text"
      :mode="mode"
      :height="height"
      :placeholder="placeholder"
      @upload-image="handleUploadImage"
      @save="handleSave"
    ></v-md-editor>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import VMdEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'

// Prism
import Prism from 'prismjs'

VMdEditor.use(vuepressTheme, {
  Prism,
})

// 定义属性
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  mode: {
    type: String,
    default: 'edit' // edit: 编辑模式 preview: 预览模式 editable: 编辑与预览模式
  },
  height: {
    type: String,
    default: '400px'
  },
  placeholder: {
    type: String,
    default: '请输入内容...'
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue', 'save'])

// 定义响应式数据
const text = ref(props.modelValue)

// 监听值变化
watch(
  () => props.modelValue,
  (val) => {
    text.value = val
  }
)

// 监听文本变化
watch(text, (val) => {
  emit('update:modelValue', val)
})

// 处理图片上传
const handleUploadImage = (event, insertImage, files) => {
  // 获取上传的文件
  const file = files[0]
  
  // 创建FormData对象
  const formData = new FormData()
  formData.append('file', file)
  
  // 调用上传接口
  // uploadFile(formData).then(response => {
  //   // 插入图片
  //   insertImage({
  //     url: response.data.url,
  //     desc: file.name,
  //     // width: 'auto',
  //     // height: 'auto',
  //   });
  // });
  
  // 模拟上传结果
  insertImage({
    url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
    desc: file.name,
  })
}

// 处理保存
const handleSave = (text, html) => {
  emit('save', { text, html })
}
</script>

<style lang="scss" scoped>
.markdown-editor {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
}
</style>