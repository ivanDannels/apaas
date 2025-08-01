<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="4">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>接口分组</span>
            </div>
          </template>
          <div class="group-container">
            <el-menu
              :default-active="activeGroup"
              class="el-menu-vertical-demo"
              @select="handleGroupSelect"
            >
              <el-menu-item
                v-for="group in groupList"
                :key="group.url"
                :index="group.url"
              >
                <template #title>
                  <el-icon><Folder /></el-icon>
                  <span>{{ group.name }}</span>
                </template>
              </el-menu-item>
            </el-menu>
          </div>
        </el-card>
      </el-col>
      <el-col :span="20">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>API文档</span>
              <el-button type="primary" @click="handleRefresh">刷新</el-button>
            </div>
          </template>
          <div class="swagger-container">
            <iframe
              :src="swaggerUrl"
              frameborder="0"
              width="100%"
              height="800px"
            ></iframe>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Folder } from '@element-plus/icons-vue'

// 定义响应式数据
const groupList = ref([])
const activeGroup = ref('')
const swaggerUrl = ref('')

// 获取接口分组列表
const getGroupList = () => {
  // 模拟分组数据
  groupList.value = [
    { name: '默认接口', url: '/swagger-ui/index.html' },
    { name: '系统接口', url: '/swagger-ui/index.html?group=system' },
    { name: '流程接口', url: '/swagger-ui/index.html?group=flow' }
  ]
  
  // 默认选中第一个分组
  if (groupList.value.length > 0) {
    activeGroup.value = groupList.value[0].url
    swaggerUrl.value = groupList.value[0].url
  }
}

// 分组选择
const handleGroupSelect = (index) => {
  activeGroup.value = index
  swaggerUrl.value = index
}

// 刷新
const handleRefresh = () => {
  // 重新加载iframe
  const iframe = document.querySelector('iframe')
  if (iframe) {
    iframe.src = iframe.src
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getGroupList()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.group-container {
  height: calc(100vh - 200px);
  overflow-y: auto;
}

.swagger-container {
  height: calc(100vh - 200px);
  overflow: hidden;
}
</style>