<template>
  <div class="login-container">
    <el-form 
      ref="loginFormRef" 
      :model="loginForm" 
      :rules="loginRules" 
      class="login-form" 
      auto-complete="on" 
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">
          {{ $t('login.title') }}
        </h3>
      </div>

      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          :placeholder="$t('login.username')"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          :placeholder="$t('login.password')"
          name="password"
          tabindex="2"
          auto-complete="on"
          show-password
          @keyup.enter="handleLogin"
        />
      </el-form-item>

      <el-button 
        :loading="loading" 
        type="primary" 
        style="width:100%;margin-bottom:30px;" 
        @click.prevent="handleLogin"
      >
        {{ $t('login.loginButton') }}
      </el-button>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { LoginForm } from '@/types/store'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()

const loginForm = reactive<LoginForm>({
  username: 'admin',
  password: '123456'
})

const loginRules = reactive<FormRules<LoginForm>>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
})

const loading = ref(false)
const loginFormRef = ref<FormInstance | null>(null)

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // 这里应该是调用登录API
        // const response = await login(loginForm)
        // 模拟登录成功
        authStore.setToken('fake-token')
        authStore.setUserInfo({
          id: 1,
          username: loginForm.username,
          name: '管理员'
        })
        
        // 跳转到首页
        router.push({ path: '/' })
      } catch (error) {
        console.error(error)
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: #f0f2f5;
}

.login-form {
  width: 400px;
  padding: 30px;
  background: #fff;
  border-radius: 6px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.title-container {
  margin-bottom: 20px;
  text-align: center;
}

.title {
  font-size: 24px;
  color: #333;
}
</style>