<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="welcome-card">
          <div class="welcome-content">
            <h2>{{ $t('dashboard.welcome') }} {{ username }}!</h2>
            <p>{{ $t('dashboard.description') }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="mt-20">
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40" color="#409EFF"><Document /></el-icon>
            </div>
            <div class="stat-info">
              <h3>{{ processCount }}</h3>
              <p>{{ $t('dashboard.processCount') }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40" color="#67C23A"><Check /></el-icon>
            </div>
            <div class="stat-info">
              <h3>{{ completedTaskCount }}</h3>
              <p>{{ $t('dashboard.completedTasks') }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40" color="#E6A23C"><User /></el-icon>
            </div>
            <div class="stat-info">
              <h3>{{ userCount }}</h3>
              <p>{{ $t('dashboard.userCount') }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="mt-20">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>{{ $t('dashboard.processStatistics') }}</span>
            </div>
          </template>
          <div class="chart-container">
            <!-- 这里将放置流程统计图表 -->
            <div ref="processChart" class="chart" style="height: 300px;"></div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
          <div class="card-header">
            <span>{{ $t('dashboard.systemMonitor') }}</span>
          </div>
          </template>
          <div class="chart-container">
            <!-- 这里将放置系统监控图表 -->
            <div ref="systemChart" class="chart" style="height: 300px;"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import * as echarts from 'echarts'
import type { EChartsType } from 'echarts'

const authStore = useAuthStore()

// 用户名
const username = computed(() => authStore.userInfo?.name || 'Admin')

// 统计数据
const processCount = ref<number>(128)
const completedTaskCount = ref<number>(342)
const userCount = ref<number>(42)

// 图表引用
const processChart = ref<HTMLDivElement | null>(null)
const systemChart = ref<HTMLDivElement | null>(null)

// 初始化图表
onMounted(() => {
  initProcessChart()
  initSystemChart()
})

// 初始化流程统计图表
const initProcessChart = () => {
  if (processChart.value) {
    const chart: EChartsType = echarts.init(processChart.value)
    const option = {
      title: {
        text: '流程执行统计'
      },
      tooltip: {},
      xAxis: {
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: [120, 200, 150, 80, 70, 110, 130],
        type: 'bar'
      }]
    }
    chart.setOption(option)
  }
}

// 初始化系统监控图表
const initSystemChart = () => {
  if (systemChart.value) {
    const chart: EChartsType = echarts.init(systemChart.value)
    const option = {
      title: {
        text: '系统资源使用率'
      },
      tooltip: {},
      xAxis: {
        type: 'category',
        data: ['CPU', '内存', '磁盘', '网络']
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: [80, 65, 45, 30],
        type: 'bar'
      }]
    }
    chart.setOption(option)
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.welcome-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 8px;
}

.welcome-content h2 {
  margin-bottom: 10px;
}

.welcome-content p {
  opacity: 0.9;
}

.stat-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.stat-content {
  display: flex;
  align-items: center;
}

.stat-icon {
  margin-right: 20px;
}

.stat-info h3 {
  margin: 0 0 10px 0;
}
</style>