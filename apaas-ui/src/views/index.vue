<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 统计卡片 -->
      <el-col :span="6" v-for="item in statistics" :key="item.title">
        <el-card class="statistic-card" shadow="hover">
          <div class="statistic-content">
            <div class="statistic-icon" :style="{ backgroundColor: item.color + '20', color: item.color }">
              <i :class="item.icon"></i>
            </div>
            <div class="statistic-info">
              <div class="statistic-title">{{ item.title }}</div>
              <div class="statistic-value">{{ item.value }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px">
      <!-- 图表区域 -->
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>访问量统计</span>
            </div>
          </template>
          <div class="chart-container">
            <v-chart 
              v-if="chartData.xAxis.length > 0" 
              :option="chartOption" 
              autoresize 
              style="height: 300px"
            />
            <div v-else class="chart-placeholder">暂无数据</div>
          </div>
        </el-card>
      </el-col>
      
      <!-- 最新动态 -->
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>最新动态</span>
            </div>
          </template>
          <div class="activity-list">
            <div 
              v-for="item in activities" 
              :key="item.id" 
              class="activity-item"
            >
              <div class="activity-icon" :style="{ backgroundColor: item.color + '20', color: item.color }">
                <i :class="item.icon"></i>
              </div>
              <div class="activity-content">
                <div class="activity-title">{{ item.title }}</div>
                <div class="activity-time">{{ item.time }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px">
      <!-- 项目介绍 -->
      <el-col :span="24">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>项目介绍</span>
            </div>
          </template>
          <div class="project-info">
            <p>APAAS管理系统是一个基于Vue3 + Element Plus + Vite的后台管理系统解决方案。</p>
            <p>系统具有以下特点：</p>
            <ul>
              <li>基于Vue3 Composition API和Setup语法糖</li>
              <li>使用Element Plus组件库</li>
              <li>采用Vite构建工具，提升开发体验</li>
              <li>完善的权限管理系统</li>
              <li>丰富的组件库和页面模板</li>
              <li>支持国际化多语言</li>
              <li>响应式布局，支持移动端</li>
            </ul>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { LineChart } from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
} from 'echarts/components'
import VChart from 'vue-echarts'

// 注册ECharts组件
use([
  CanvasRenderer,
  LineChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
])

// 定义响应式数据
const statistics = ref([
  {
    title: '用户数',
    value: '128',
    icon: 'User',
    color: '#409eff'
  },
  {
    title: '订单数',
    value: '56',
    icon: 'Document',
    color: '#67c23a'
  },
  {
    title: '销售额',
    value: '¥25,600',
    icon: 'Coin',
    color: '#e6a23c'
  },
  {
    title: '访问量',
    value: '1,234',
    icon: 'DataAnalysis',
    color: '#f56c6c'
  }
])

const activities = ref([
  {
    id: 1,
    title: '张三 创建了新项目',
    time: '2023-05-20 14:30',
    icon: 'Plus',
    color: '#409eff'
  },
  {
    id: 2,
    title: '李四 更新了系统配置',
    time: '2023-05-20 13:45',
    icon: 'Setting',
    color: '#67c23a'
  },
  {
    id: 3,
    title: '王五 完成了任务',
    time: '2023-05-20 12:15',
    icon: 'Check',
    color: '#e6a23c'
  },
  {
    id: 4,
    title: '赵六 提交了bug修复',
    time: '2023-05-20 11:20',
    icon: 'Bug',
    color: '#f56c6c'
  }
])

const chartData = reactive({
  xAxis: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
  series: [120, 200, 150, 80, 70, 110, 130]
})

// 图表配置
const chartOption = {
  title: {
    text: '最近7天访问量',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  xAxis: {
    type: 'category',
    data: chartData.xAxis
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: chartData.series,
      type: 'line',
      smooth: true,
      areaStyle: {}
    }
  ]
}
</script>

<style lang="scss" scoped>
.statistic-card {
  border-radius: 8px;
  
  .statistic-content {
    display: flex;
    align-items: center;
    
    .statistic-icon {
      width: 50px;
      height: 50px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      margin-right: 15px;
    }
    
    .statistic-info {
      .statistic-title {
        font-size: 14px;
        color: #666;
        margin-bottom: 5px;
      }
      
      .statistic-value {
        font-size: 24px;
        font-weight: bold;
        color: #333;
      }
    }
  }
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chart-container {
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  
  .chart-placeholder {
    color: #999;
    font-size: 16px;
  }
}

.activity-list {
  .activity-item {
    display: flex;
    padding: 15px 0;
    border-bottom: 1px solid #eee;
    
    &:last-child {
      border-bottom: none;
    }
    
    .activity-icon {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 16px;
      margin-right: 15px;
      flex-shrink: 0;
    }
    
    .activity-content {
      flex: 1;
      
      .activity-title {
        font-size: 14px;
        color: #333;
        margin-bottom: 5px;
      }
      
      .activity-time {
        font-size: 12px;
        color: #999;
      }
    }
  }
}

.project-info {
  p {
    line-height: 1.8;
    color: #666;
  }
  
  ul {
    padding-left: 20px;
    
    li {
      line-height: 1.8;
      color: #666;
      margin-bottom: 5px;
    }
  }
}
</style>