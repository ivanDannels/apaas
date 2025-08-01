<template>
  <el-form ref="userRef" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="用户昵称" prop="nickName">
      <el-input v-model="user.nickName" maxlength="30" />
    </el-form-item>
    <el-form-item label="手机号码" prop="phonenumber">
      <el-input v-model="user.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">男</el-radio>
        <el-radio label="1">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">保存</el-button>
      <el-button type="danger" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { updateUserProfile } from '@/api/system/user'

// 定义响应式数据
const userRef = ref(null)

// 定义用户信息
const props = defineProps({
  user: {
    type: Object,
    default: () => ({})
  }
})

// 用户数据
const user = reactive({
  nickName: '',
  phonenumber: '',
  email: '',
  sex: '0'
})

// 表单校验规则
const rules = {
  nickName: [
    { required: true, message: '用户昵称不能为空', trigger: 'blur' }
  ],
  phonenumber: [
    { required: true, message: '手机号码不能为空', trigger: 'blur' },
    { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ]
}

// 初始化用户数据
const initUser = () => {
  user.nickName = props.user.nickName
  user.phonenumber = props.user.phonenumber
  user.email = props.user.email
  user.sex = props.user.sex
}

// 提交按钮
const submit = () => {
  userRef.value?.validate(async (valid) => {
    if (valid) {
      await updateUserProfile(user)
      ElMessage.success('修改成功')
    }
  })
}

// 关闭按钮
const close = () => {
  ElMessageBox.confirm('确认要关闭吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 关闭当前标签页
    // 这里应该调用关闭标签页的方法
  }).catch(() => {})
}

// 初始化数据
initUser()
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
</style>