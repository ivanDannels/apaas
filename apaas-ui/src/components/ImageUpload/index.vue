<template>
  <div class="image-upload">
    <el-upload
      ref="uploadRef"
      :action="uploadUrl"
      :headers="headers"
      :data="data"
      :name="name"
      :accept="accept"
      :multiple="multiple"
      :limit="limit"
      :disabled="disabled"
      :auto-upload="autoUpload"
      :file-list="fileList"
      :on-exceed="handleExceed"
      :on-success="handleSuccess"
      :on-error="handleError"
      :on-progress="handleProgress"
      :on-remove="handleRemove"
      :on-preview="handlePreview"
      :before-upload="beforeUpload"
      :show-file-list="showFileList"
      :list-type="listType"
      :http-request="httpRequest"
      :drag="drag"
      class="upload-item"
    >
      <template v-if="drag">
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将图片拖到此处，或<em>点击上传</em>
        </div>
      </template>
      <template v-else>
        <el-button :type="buttonType" :disabled="disabled">
          <slot name="buttonText">点击上传</slot>
        </el-button>
      </template>
      <template #tip>
        <div v-if="tip" class="el-upload__tip">
          {{ tip }}
        </div>
      </template>
    </el-upload>
    
    <!-- 图片预览对话框 -->
    <el-dialog :title="preview.title" v-model="preview.open" width="800px" append-to-body>
      <img :src="preview.url" style="width: 100%" />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import { getToken } from '@/utils/auth'

// 定义属性
const props = defineProps({
  // 上传地址
  uploadUrl: {
    type: String,
    default: '/common/upload'
  },
  // 上传headers
  headers: {
    type: Object,
    default: () => ({ Authorization: 'Bearer ' + getToken() })
  },
  // 上传附带参数
  data: {
    type: Object,
    default: () => ({})
  },
  // 上传文件字段名
  name: {
    type: String,
    default: 'file'
  },
  // 接受上传的文件类型
  accept: {
    type: String,
    default: 'image/*'
  },
  // 是否支持多选文件
  multiple: {
    type: Boolean,
    default: false
  },
  // 最大允许上传个数
  limit: {
    type: Number,
    default: 0
  },
  // 是否禁用
  disabled: {
    type: Boolean,
    default: false
  },
  // 是否自动上传
  autoUpload: {
    type: Boolean,
    default: true
  },
  // 上传文件列表
  fileList: {
    type: Array,
    default: () => []
  },
  // 是否显示文件列表
  showFileList: {
    type: Boolean,
    default: true
  },
  // 文件列表类型
  listType: {
    type: String,
    default: 'picture-card'
  },
  // 按钮类型
  buttonType: {
    type: String,
    default: 'primary'
  },
  // 是否支持拖拽上传
  drag: {
    type: Boolean,
    default: false
  },
  // 提示信息
  tip: {
    type: String,
    default: '只能上传jpg/png文件，且不超过5MB'
  },
  // 自定义上传
  httpRequest: {
    type: Function,
    default: null
  }
})

// 定义事件
const emit = defineEmits(['success', 'error', 'progress', 'remove', 'preview', 'exceed', 'update:fileList'])

// 定义响应式数据
const uploadRef = ref(null)
const preview = reactive({
  open: false,
  title: '',
  url: ''
})

// 文件超出个数限制时的钩子
const handleExceed = (files, fileList) => {
  if (props.limit > 0) {
    ElMessage.warning(`上传图片数量不能超过 ${props.limit} 个!`)
  }
  emit('exceed', files, fileList)
}

// 文件上传成功时的钩子
const handleSuccess = (response, file, fileList) => {
  if (response.code === 200) {
    ElMessage.success(response.msg || '上传成功')
    emit('update:fileList', fileList)
    emit('success', response, file, fileList)
  } else {
    ElMessage.error(response.msg || '上传失败')
    emit('error', response, file, fileList)
  }
}

// 文件上传失败时的钩子
const handleError = (error, file, fileList) => {
  ElMessage.error('上传失败')
  emit('error', error, file, fileList)
}

// 文件上传时的钩子
const handleProgress = (event, file, fileList) => {
  emit('progress', event, file, fileList)
}

// 文件移除时的钩子
const handleRemove = (file, fileList) => {
  emit('update:fileList', fileList)
  emit('remove', file, fileList)
}

// 点击文件列表中已上传的文件时的钩子
const handlePreview = (file) => {
  preview.open = true
  preview.title = '图片预览'
  preview.url = file.url
  emit('preview', file)
}

// 上传文件之前的钩子
const beforeUpload = (file) => {
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
  const isLt5M = file.size / 1024 / 1024 < 5
  
  if (!isJPG) {
    ElMessage.error('上传头像图片只能是 JPG/PNG 格式!')
  }
  if (!isLt5M) {
    ElMessage.error('上传头像图片大小不能超过 5MB!')
  }
  return isJPG && isLt5M
}

// 手动上传文件
const submitUpload = () => {
  uploadRef.value?.submit()
}

// 清空已上传的文件列表
const clearFiles = () => {
  uploadRef.value?.clearFiles()
}

// 定义暴露的方法
defineExpose({
  submitUpload,
  clearFiles
})
</script>

<style scoped>
.upload-item {
  width: 100%;
}
</style>