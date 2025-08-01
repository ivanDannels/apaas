<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="公告标题" prop="noticeTitle">
          <el-input
            v-model="queryParams.noticeTitle"
            placeholder="请输入公告标题"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="操作人员" prop="createBy">
          <el-input
            v-model="queryParams.createBy"
            placeholder="请输入操作人员"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="noticeType">
          <el-select v-model="queryParams.noticeType" placeholder="请选择类型" clearable>
            <el-option
              v-for="dict in sys_notice_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
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
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
          >{{ $t('common.add') }}</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
          >{{ $t('common.edit') }}</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
          >{{ $t('common.delete') }}</el-button>
        </el-col>
      </el-row>
    </div>
    
    <div class="table-container">
      <el-table
        :data="noticeList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" prop="noticeId" />
        <el-table-column label="公告标题" align="center" prop="noticeTitle" />
        <el-table-column label="公告类型" align="center" prop="noticeType">
          <template #default="scope">
            <dict-tag :options="sys_notice_type" :value="scope.row.noticeType" />
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="创建者" align="center" prop="createBy" />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
        <el-table-column label="操作" align="center" width="150" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
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
    
    <!-- 添加或修改公告对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form :model="form" :rules="rules" ref="noticeForm" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公告标题" prop="noticeTitle">
              <el-input v-model="form.noticeTitle" placeholder="请输入公告标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公告类型" prop="noticeType">
              <el-select v-model="form.noticeType" placeholder="请选择公告类型">
                <el-option
                  v-for="dict in sys_notice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in sys_notice_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="内容">
              <el-input
                v-model="form.noticeContent"
                type="textarea"
                placeholder="请输入内容"
                :autosize="{ minRows: 4, maxRows: 8 }"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getNoticeList, getNotice, addNotice, updateNotice, delNotice } from '@/api/system/notice'

// 定义响应式数据
const noticeList = ref([])
const loading = ref(false)
const total = ref(0)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const open = ref(false)
const title = ref('')
const sys_notice_type = ref([])
const sys_notice_status = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  noticeTitle: '',
  createBy: '',
  noticeType: ''
})

// 表单数据
const form = reactive({
  noticeId: undefined,
  noticeTitle: '',
  noticeType: '',
  noticeContent: '',
  status: '0'
})

// 表单校验规则
const rules = {
  noticeTitle: [{ required: true, message: '公告标题不能为空', trigger: 'blur' }],
  noticeType: [{ required: true, message: '公告类型不能为空', trigger: 'change' }]
}

// 查询表单引用
const queryForm = ref(null)
const noticeForm = ref(null)

// 获取通知公告列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getNoticeList(queryParams)
    noticeList.value = response.data.items
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
  queryForm.value?.resetFields()
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.noticeId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 状态修改
const handleStatusChange = (row) => {
  let text = row.status === '0' ? '启用' : '停用'
  ElMessageBox.confirm('确认要"' + text + '""' + row.noticeTitle + '"公告吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await updateNotice(row)
    ElMessage.success(text + '成功')
  }).catch(() => {
    row.status = row.status === '0' ? '1' : '0'
  })
}

// 新增按钮操作
const handleAdd = () => {
  open.value = true
  title.value = '添加公告'
  Object.assign(form, {
    noticeId: undefined,
    noticeTitle: '',
    noticeType: '',
    noticeContent: '',
    status: '0'
  })
}

// 修改按钮操作
const handleUpdate = (row) => {
  const noticeId = row.noticeId || ids.value[0]
  getNotice(noticeId).then(response => {
    Object.assign(form, response.data)
    open.value = true
    title.value = '修改公告'
  })
}

// 提交表单
const submitForm = () => {
  noticeForm.value?.validate(async (valid) => {
    if (valid) {
      if (form.noticeId) {
        await updateNotice(form)
        ElMessage.success('修改成功')
      } else {
        await addNotice(form)
        ElMessage.success('新增成功')
      }
      open.value = false
      getList()
    }
  })
}

// 取消按钮
const cancel = () => {
  open.value = false
  reset()
}

// 表单重置
const reset = () => {
  Object.assign(form, {
    noticeId: undefined,
    noticeTitle: '',
    noticeType: '',
    noticeContent: '',
    status: '0'
  })
  noticeForm.value?.resetFields()
}

// 删除按钮操作
const handleDelete = (row) => {
  const noticeIds = row.noticeId ? [row.noticeId] : ids.value
  ElMessageBox.confirm('是否确认删除公告编号为"' + noticeIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delNotice(noticeIds)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 获取字典数据
const getDicts = () => {
  // 模拟字典数据
  sys_notice_type.value = [
    { value: '1', label: '通知' },
    { value: '2', label: '公告' }
  ]
  
  sys_notice_status.value = [
    { value: '0', label: '正常' },
    { value: '1', label: '关闭' }
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