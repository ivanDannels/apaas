<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
        <el-form-item :label="$t('workflow.processName')" prop="name">
          <el-input
            v-model="queryParams.name"
            :placeholder="$t('workflow.processName')"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item :label="$t('workflow.processKey')" prop="key">
          <el-input
            v-model="queryParams.key"
            :placeholder="$t('workflow.processKey')"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">{{ $t('common.search') }}</el-button>
          <el-button icon="Refresh" @click="resetQuery">{{ $t('common.reset') }}</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div class="button-group mb-20">
      <el-button type="primary" icon="Plus" @click="handleAdd">{{ $t('common.add') }}</el-button>
      <el-button type="danger" icon="Delete" :disabled="multipleSelection.length === 0" @click="handleDelete">{{ $t('common.delete') }}</el-button>
    </div>
    
    <div class="table-container">
      <el-table
        :data="processDefinitionList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        v-loading="loading"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column :label="$t('workflow.processName')" prop="name" />
        <el-table-column :label="$t('workflow.processKey')" prop="key" />
        <el-table-column :label="$t('workflow.version')" prop="version" width="80" />
        <el-table-column :label="$t('workflow.deploymentTime')" prop="deploymentTime" width="180" />
        <el-table-column :label="$t('common.status')" prop="status" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="$t('common.operation')" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
            <el-button link type="primary" icon="View" @click="handleView(scope.row)">{{ $t('common.view') }}</el-button>
            <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)">{{ $t('common.delete') }}</el-button>
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
    
    <!-- 流程定义表单 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item :label="$t('workflow.processName')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('workflow.processName')" />
        </el-form-item>
        <el-form-item :label="$t('workflow.processKey')" prop="key">
          <el-input v-model="form.key" :placeholder="$t('workflow.processKey')" />
        </el-form-item>
        <el-form-item label="BPMN文件" prop="bpmnFile">
          <el-upload
            class="upload-demo"
            drag
            action="#"
            :auto-upload="false"
            :on-change="handleFileChange"
            :file-list="fileList"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">
                只能上传 bpmn 文件，且不超过 2MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('system.dictionary.description')" prop="description">
          <el-input v-model="form.description" type="textarea" :placeholder="$t('system.dictionary.description')" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">{{ $t('common.cancel') }}</el-button>
          <el-button type="primary" @click="submitForm">{{ $t('common.submit') }}</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getProcessDefinitions, createProcessDefinition, updateProcessDefinition, deleteProcessDefinition } from '@/api/workflow'
import type { FormInstance, FormRules, UploadFile, UploadFiles } from 'element-plus'
import type { ProcessDefinition } from '@/types/api'
import type { UploadProps } from 'element-plus'

// 定义响应式数据
const processDefinitionList = ref<ProcessDefinition[]>([])
const loading = ref<boolean>(false)
const total = ref<number>(0)
const multipleSelection = ref<ProcessDefinition[]>([])
const dialogVisible = ref<boolean>(false)
const dialogTitle = ref<string>('')
const fileList = ref<UploadFile[]>([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  key: ''
})

// 表单数据
const form = reactive({
  id: undefined as number | undefined,
  name: '',
  key: '',
  bpmnFile: '',
  status: 1,
  description: ''
})

// 表单验证规则
const rules = reactive<FormRules>({
  name: [{ required: true, message: '请输入流程名称', trigger: 'blur' }],
  key: [{ required: true, message: '请输入流程标识', trigger: 'blur' }],
  bpmnFile: [{ required: true, message: '请上传BPMN文件', trigger: 'change' }]
})

// 表单引用
const queryFormRef = ref<FormInstance | null>(null)
const formRef = ref<FormInstance | null>(null)

// 获取流程定义列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getProcessDefinitions(queryParams)
    processDefinitionList.value = response.data.items
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
  queryFormRef.value?.resetFields()
  handleQuery()
}

// 处理多选
const handleSelectionChange = (selection: ProcessDefinition[]) => {
  multipleSelection.value = selection
}

// 处理添加
const handleAdd = () => {
  dialogTitle.value = '添加流程定义'
  resetForm()
  dialogVisible.value = true
}

// 处理编辑
const handleUpdate = (row: ProcessDefinition) => {
  dialogTitle.value = '编辑流程定义'
  resetForm()
  Object.assign(form, row)
  dialogVisible.value = true
}

// 处理查看
const handleView = (row: ProcessDefinition) => {
  // 这里可以跳转到流程设计页面查看详细信息
  console.log('查看流程定义', row)
}

// 处理删除
const handleDelete = (row?: ProcessDefinition) => {
  const ids = row?.id || multipleSelection.value.map(item => item.id).join(',')
  
  ElMessageBox.confirm('确定删除选中的数据吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteProcessDefinition(ids)
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      console.error(error)
      ElMessage.error('删除失败')
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 重置表单
const resetForm = () => {
  Object.assign(form, {
    id: undefined,
    name: '',
    key: '',
    bpmnFile: '',
    status: 1,
    description: ''
  })
  fileList.value = []
  formRef.value?.resetFields()
}

// 处理文件上传
const handleFileChange: UploadProps['onChange'] = (file, fileList) => {
  // 这里可以添加文件验证逻辑
  console.log('文件上传', file, fileList)
}

// 提交表单
const submitForm = () => {
  if (!formRef.value) return
  
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          await updateProcessDefinition(form.id, form)
          ElMessage.success('修改成功')
        } else {
          await createProcessDefinition(form)
          ElMessage.success('新增成功')
        }
        dialogVisible.value = false
        getList()
      } catch (error) {
        console.error(error)
        ElMessage.error('操作失败')
      }
    }
  })
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
})
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
</style>