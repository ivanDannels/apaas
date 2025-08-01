<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="系统模块" prop="title">
          <el-input
            v-model="queryParams.title"
            placeholder="请输入系统模块"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="操作人员" prop="operName">
          <el-input
            v-model="queryParams.operName"
            placeholder="请输入操作人员"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="businessType">
          <el-select v-model="queryParams.businessType" placeholder="请选择类型" clearable>
            <el-option
              v-for="dict in sys_oper_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="正常" value="0" />
            <el-option label="异常" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="操作时间" style="width: 300px">
          <el-date-picker
            v-model="dateRange"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :shortcuts="dateShortcuts"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">{{ $t('common.search') }}</el-button>
          <el-button icon="Refresh" @click="resetQuery">{{ $t('common.reset') }}</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div class="toolbar-container">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
          >{{ $t('common.delete') }}</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="Delete"
            @click="handleClean"
          >清空</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
          >导出</el-button>
        </el-col>
      </el-row>
    </div>
    
    <div class="table-container">
      <el-table
        :data="logList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="日志编号" align="center" prop="operId" />
        <el-table-column label="系统模块" align="center" prop="title" />
        <el-table-column label="操作类型" align="center" prop="businessType">
          <template #default="scope">
            <dict-tag :options="sys_oper_type" :value="scope.row.businessType" />
          </template>
        </el-table-column>
        <el-table-column label="请求方式" align="center" prop="requestMethod" />
        <el-table-column label="操作人员" align="center" prop="operName" />
        <el-table-column label="主机" align="center" prop="operIp" width="130" />
        <el-table-column label="操作状态" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
              {{ scope.row.status === '0' ? '正常' : '异常' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作日期" align="center" prop="operTime" width="180" />
        <el-table-column label="操作" align="center" width="150" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="View" @click="handleView(scope.row)">详细</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    
    <!-- 操作日志详细 -->
    <el-dialog title="操作日志详细" v-model="open" width="800px" append-to-body>
      <el-form :model="form" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作模块：">{{ form.title }} / {{ typeFormat(form) }}</el-form-item>
            <el-form-item label="登录信息：">{{ form.operName }} / {{ form.operIp }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求地址：">{{ form.operUrl }}</el-form-item>
            <el-form-item label="请求方式：">{{ form.requestMethod }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作方法：">{{ form.method }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求参数：">{{ form.operParam }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="返回参数：">{{ form.jsonResult }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：">
              <div v-if="form.status === '0'">正常</div>
              <div v-else>异常</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作时间：">{{ form.operTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="异常信息：" v-if="form.status === '1'">{{ form.errorMsg }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="open = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getOperLogList, delOperLog, cleanOperLog } from '@/api/system/log'

// 定义响应式数据
const logList = ref([])
const loading = ref(false)
const total = ref(0)
const ids = ref([])
const multiple = ref(true)
const open = ref(false)
const dateRange = ref([])
const sys_oper_type = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  title: '',
  operName: '',
  businessType: '',
  status: '',
  orderByColumn: 'operTime',
  isAsc: 'descending'
})

// 表单数据
const form = reactive({
  operId: undefined,
  title: '',
  businessType: '',
  method: '',
  requestMethod: '',
  operName: '',
  operUrl: '',
  operIp: '',
  operParam: '',
  jsonResult: '',
  status: '',
  errorMsg: '',
  operTime: ''
})

// 查询表单引用
const queryForm = ref(null)

// 日期快捷选项
const dateShortcuts = [
  {
    text: '最近一周',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    }
  },
  {
    text: '最近一个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    }
  },
  {
    text: '最近三个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    }
  }
]

// 获取操作日志列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getOperLogList(queryParams)
    logList.value = response.data.items
    total.value = response.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 查询
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

// 重置查询
const resetQuery = () => {
  dateRange.value = []
  queryForm.value?.resetFields()
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.operId)
  multiple.value = !selection.length
}

// 删除日志
const handleDelete = (row) => {
  const operIds = row.operId ? [row.operId] : ids.value
  ElMessageBox.confirm('是否确认删除日志编号为"' + operIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delOperLog(operIds)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 清空日志
const handleClean = () => {
  ElMessageBox.confirm('是否确认清空所有操作日志数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await cleanOperLog()
    ElMessage.success('清空成功')
    getList()
  }).catch(() => {})
}

// 导出日志
const handleExport = () => {
  ElMessageBox.confirm('是否确认导出所有操作日志数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    // 这里应该调用导出API
    ElMessage.success('导出成功')
  }).catch(() => {})
}

// 打开详细信息
const handleView = (row) => {
  Object.assign(form, row)
  open.value = true
}

// 操作类型字典翻译
const typeFormat = (row) => {
  const typeMap = {
    '1': '新增',
    '2': '修改',
    '3': '删除',
    '4': '授权',
    '5': '导出',
    '6': '导入',
    '7': '强退',
    '8': '生成代码',
    '9': '清空数据'
  }
  return typeMap[row.businessType] || '其他'
}

// 获取字典数据
const getDicts = () => {
  // 模拟字典数据
  sys_oper_type.value = [
    { value: '1', label: '新增' },
    { value: '2', label: '修改' },
    { value: '3', label: '删除' },
    { value: '4', label: '授权' },
    { value: '5', label: '导出' },
    { value: '6', label: '导入' },
    { value: '7', label: '强退' },
    { value: '8', label: '生成代码' },
    { value: '9', label: '清空数据' }
  ]
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
  getDicts()
})
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
</style>