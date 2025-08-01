<template>
  <div class="code-editor">
    <div ref="editorRef" class="editor-container"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch, nextTick } from 'vue'
import * as monaco from 'monaco-editor'

// 定义属性
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  language: {
    type: String,
    default: 'javascript'
  },
  theme: {
    type: String,
    default: 'vs'
  },
  height: {
    type: [String, Number],
    default: '400px'
  },
  readOnly: {
    type: Boolean,
    default: false
  },
  options: {
    type: Object,
    default: () => ({})
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue'])

// 定义响应式数据
const editorRef = ref(null)
let editor = null

// 默认配置
const defaultOptions = {
  language: props.language,
  theme: props.theme,
  readOnly: props.readOnly,
  automaticLayout: true,
  minimap: {
    enabled: true
  },
  scrollBeyondLastLine: false,
  scrollbar: {
    vertical: 'auto',
    horizontal: 'auto'
  }
}

// 初始化编辑器
const initEditor = () => {
  if (!editorRef.value) return
  
  // 销毁已存在的编辑器
  if (editor) {
    editor.dispose()
  }
  
  // 合并配置
  const options = Object.assign({}, defaultOptions, props.options, {
    value: props.modelValue
  })
  
  // 创建编辑器
  editor = monaco.editor.create(editorRef.value, options)
  
  // 设置高度
  if (props.height) {
    editorRef.value.style.height = 
      typeof props.height === 'number' ? props.height + 'px' : props.height
  }
  
  // 监听值变化
  editor.onDidChangeModelContent(() => {
    const value = editor.getValue()
    emit('update:modelValue', value)
  })
}

// 监听属性变化
watch(
  () => [props.language, props.theme, props.readOnly],
  () => {
    if (editor) {
      // 更新语言
      monaco.editor.setModelLanguage(editor.getModel(), props.language)
      
      // 更新主题
      monaco.editor.setTheme(props.theme)
      
      // 更新只读状态
      editor.updateOptions({ readOnly: props.readOnly })
    }
  }
)

// 监听值变化
watch(
  () => props.modelValue,
  (val) => {
    if (editor && editor.getValue() !== val) {
      editor.setValue(val)
    }
  }
)

// 组件挂载后初始化编辑器
onMounted(() => {
  nextTick(() => {
    initEditor()
  })
})

// 组件卸载前销毁编辑器
onBeforeUnmount(() => {
  if (editor) {
    editor.dispose()
  }
})

// 定义暴露给父组件的方法
const getValue = () => {
  return editor ? editor.getValue() : ''
}

const setValue = (value) => {
  if (editor) {
    editor.setValue(value)
  }
}

defineExpose({
  getValue,
  setValue
})
</script>

<style lang="scss" scoped>
.code-editor {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
  
  .editor-container {
    width: 100%;
    height: v-bind('props.height');
  }
}
</style>