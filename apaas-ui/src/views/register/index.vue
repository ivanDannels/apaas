<template>
  <div class="register">
    <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" class="register-form">
      <div class="title-container">
        <h3 class="title">{{ $t('login.register') }}</h3>
      </div>
      <el-form-item prop="username">
        <el-input
          v-model="registerForm.username"
          :placeholder="$t('login.username')"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="off"
        >
          <template #prefix>
            <svg-icon icon-class="user" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="registerForm.password"
          :placeholder="$t('login.password')"
          name="password"
          tabindex="2"
          autocomplete="off"
          show-password
          @keyup.enter="handleRegister"
        >
          <template #prefix>
            <svg-icon icon-class="password" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input
          v-model="registerForm.confirmPassword"
          :placeholder="$t('login.confirmPassword')"
          name="confirmPassword"
          tabindex="3"
          autocomplete="off"
          show-password
          @keyup.enter="handleRegister"
        >
          <template #prefix>
            <svg-icon icon-class="password" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="registerForm.code"
          :placeholder="$t('login.verificationCode')"
          name="code"
          style="width: 63%"
          tabindex="4"
          autocomplete="off"
          @keyup.enter="handleRegister"
        >
          <template #prefix>
            <svg-icon icon-class="validCode" class="el-input__icon input-icon" />
          </template>
        </el-input>
        <div class="register-code">
          <img :src="codeUrl" @click="getCode" class="register-code-img" />
        </div>
      </el-form-item>
      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click="handleRegister"
      >{{ $t('login.register') }}</el-button>
      <div class="go-login">
        <router-link to="/login">{{ $t('login.hasAccount') }}</router-link>
      </div>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCodeImg, register } from '@/api/login'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const loading = ref<boolean>(false)
const codeUrl = ref<string>('')
const registerFormRef = ref<FormInstance | null>(null)

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  code: '',
  uuid: ''
})

const registerRules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度必须介于 2 和 20 之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 20, message: '密码长度必须介于 5 和 20 之间', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ],
  code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
})

// 验证确认密码
function validateConfirmPassword(rule: any, value: string, callback: Function) {
  if (registerForm.password !== value) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

// 获取验证码
const getCode = async () => {
  const res = await getCodeImg()
  codeUrl.value = `data:image/gif;base64,${res.data.img}`
  registerForm.uuid = res.data.uuid
}

// 处理注册
const handleRegister = () => {
  if (!registerFormRef.value) return
  
  registerFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await register(registerForm)
        // 注册成功后跳转到登录页面
        router.push('/login')
      } catch (error) {
        console.error(error)
        // 注册失败时刷新验证码
        getCode()
      } finally {
        loading.value = false
      }
    }
  })
}

onMounted(() => {
  getCode()
})
</script>

<style lang="scss" scoped>
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../../assets/images/login-background.jpg");
  background-size: cover;
}

.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px; 
    width: 14px; 
    margin-left: 2px;
  }
}

.register-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.go-login {
  margin-top: 15px;
  text-align: center;
  a {
    color: #409eff;
    text-decoration: none;
  }
}
</style>