<template>
  <div class="error-container">
    <div class="error-content">
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="error-info">
            <h1 class="error-title">404</h1>
            <h2 class="error-subtitle">抱歉，您访问的页面不存在</h2>
            <p class="error-description">
              您正在寻找的页面已被移动、删除或不存在。
              <br />
              请检查您输入的URL是否正确，或点击下面的按钮返回首页。
            </p>
            <el-button 
              type="primary" 
              @click="goHome"
              icon="HomeFilled"
            >
              返回首页
            </el-button>
            <el-button 
              @click="goBack"
              icon="ArrowLeft"
            >
              返回上一页
            </el-button>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="error-image">
            <img 
              src="@/assets/images/error/404.png" 
              alt="404" 
              class="error-img"
            />
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}

const goBack = () => {
  router.go(-1)
}
</script>

<style lang="scss" scoped>
.error-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: #f0f2f5;
  
  .error-content {
    max-width: 1000px;
    margin: 0 auto;
    padding: 20px;
    
    .error-info {
      text-align: left;
      padding: 50px 0;
      
      .error-title {
        font-size: 72px;
        font-weight: 700;
        color: #409eff;
        margin-bottom: 20px;
      }
      
      .error-subtitle {
        font-size: 24px;
        font-weight: 400;
        color: #333;
        margin-bottom: 20px;
      }
      
      .error-description {
        font-size: 16px;
        color: #666;
        line-height: 1.6;
        margin-bottom: 30px;
      }
      
      .el-button {
        margin-right: 15px;
      }
    }
    
    .error-image {
      text-align: center;
      
      .error-img {
        max-width: 100%;
        max-height: 400px;
      }
    }
  }
}
</style>