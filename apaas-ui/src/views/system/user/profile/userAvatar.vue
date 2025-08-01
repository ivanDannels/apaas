<template>
  <div class="user-info-head" @click="editCropper()">
    <img :src="options.img" title="点击上传头像" class="img-circle img-lg" />
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-row>
        <el-col :xs="24" :md="12" :style="{ height: '350px' }">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            @realTime="realTime"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{ height: '350px' }">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button>
              选择
              <el-icon class="el-icon--right"><Upload /></el-icon>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{ span: 1, offset: 2 }" :md="2">
          <el-button icon="Plus" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button icon="Minus" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button icon="RefreshLeft" @click="rotateLeft"></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button icon="RefreshRight" @click="rotateRight"></el-button>
        </el-col>
        <el-col :lg="{ span: 2, offset: 6 }" :md="2">
          <el-button type="primary" @click="uploadImg">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { VueCropper } from 'vue-cropper'
import { Upload, Plus, Minus, RefreshLeft, RefreshRight } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { uploadAvatar } from '@/api/system/user'
import 'vue-cropper/lib/index.css'

// 定义响应式数据
const open = ref(false)
const title = ref('修改头像')
const cropper = ref(null)

// 定义用户信息
const props = defineProps({
  user: {
    type: Object,
    default: () => ({})
  }
})

// 图片裁剪数据
const options = reactive({
  img: props.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng',
  autoCrop: true,
  autoCropWidth: 200,
  autoCropHeight: 200,
  fixedBox: true
})

// 预览数据
const previews = ref({})

// 编辑头像
const editCropper = () => {
  open.value = true
}

// 覆盖默认上传行为
const requestUpload = () => {}

// 向左旋转
const rotateLeft = () => {
  cropper.value.rotateLeft()
}

// 向右旋转
const rotateRight = () => {
  cropper.value.rotateRight()
}

// 图片缩放
const changeScale = (num) => {
  num = num || 1
  cropper.value.changeScale(num)
}

// 上传预处理
const beforeUpload = (file) => {
  if (file.type.indexOf('image/') === -1) {
    ElMessage.error('文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。')
    return false
  }
  const reader = new FileReader()
  reader.readAsDataURL(file)
  reader.onload = () => {
    options.img = reader.result
  }
  return false
}

// 上传图片
const uploadImg = async () => {
  cropper.value.getCropBlob(async (data) => {
    const formData = new FormData()
    formData.append('avatarfile', data)
    const response = await uploadAvatar(formData)
    open.value = false
    options.img = response.data.imgUrl
    ElMessage.success('修改成功')
    // 更新父组件用户信息
    props.user.avatar = response.data.imgUrl
  })
}

// 实时预览
const realTime = (data) => {
  previews.value = data
}
</script>

<style scoped>
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
}

.user-info-head:hover:after {
  content: '+';
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}

.img-circle {
  border-radius: 50%;
}

.img-lg {
  width: 120px;
  height: 120px;
}

.avatar-upload-preview {
  position: absolute;
  top: 50%;
  transform: translate(50%, -50%);
  width: 200px;
  height: 200px;
  border-radius: 50%;
  box-shadow: 0 0 4px #ccc;
  overflow: hidden;
}
</style>