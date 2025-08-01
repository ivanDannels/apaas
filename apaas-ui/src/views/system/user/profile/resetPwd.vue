<template>
  <el-form ref="pwdRef" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="旧密码" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="请输入旧密码" type="password" show-password />
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="请输入新密码" type="password" show-password />
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="请确认新密码" type="password" show-password />
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
import { updateUserPwd } from '@/api/system/user'

// 定义响应式数据
const pwdRef = ref(null)

// 用户密码数据
const user = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 表单校验规则
const rules = {
  oldPassword: [
    { required: true, message: '旧密码不能为空', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '新密码不能为空', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '确认密码不能为空', trigger: 'blur' },
    { required: true, validator: equalToPassword, trigger: 'blur' }
  ]
}

// 验证确认密码
function equalToPassword(rule, value, callback) {
  if (user.newPassword !== value) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

// 提交按钮
const submit = () => {
  pwdRef.value?.validate(async (valid) => {
    if (valid) {
      await updateUserPwd(user.oldPassword, user.newPassword)
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
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
</style>