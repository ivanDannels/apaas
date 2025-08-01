<template>
  <div class="editor">
    <div ref="editorRef" class="editor-container"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import WangEditor from 'wangeditor'

// 定义属性
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  height: {
    type: [Number, String],
    default: 300
  },
  placeholder: {
    type: String,
    default: '请输入内容...'
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue'])

// 定义响应式数据
const editorRef = ref(null)
let editor = null

// 初始化编辑器
const initEditor = () => {
  editor = new WangEditor(editorRef.value)
  
  // 配置编辑器
  editor.config.placeholder = props.placeholder
  editor.config.height = typeof props.height === 'number' ? props.height : parseInt(props.height)
  
  // 绑定事件
  editor.config.onchange = (newHtml) => {
    emit('update:modelValue', newHtml)
  }
  
  // 创建编辑器
  editor.create()
  
  // 设置初始内容
  editor.txt.html(props.modelValue)
}

// 监听值变化
watch(() => props.modelValue, (newVal) => {
  if (editor && editor.txt.html() !== newVal) {
    editor.txt.html(newVal)
  }
})

// 组件挂载时初始化编辑器
onMounted(() => {
  initEditor()
})

// 组件卸载前销毁编辑器
onBeforeUnmount(() => {
  if (editor) {
    editor.destroy()
    editor = null
  }
})

// 定义暴露的方法
const getHtml = () => {
  return editor ? editor.txt.html() : ''
}

const getText = () => {
  return editor ? editor.txt.text() : ''
}

const setHtml = (html) => {
  if (editor) {
    editor.txt.html(html)
  }
}

defineExpose({
  getHtml,
  getText,
  setHtml
})
</script>

<style scoped>
.editor-container {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
}
</style>