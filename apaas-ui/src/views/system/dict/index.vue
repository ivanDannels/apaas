<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input
            v-model="queryParams.dictName"
            placeholder="请输入字典名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input
            v-model="queryParams.dictType"
            placeholder="请输入字典类型"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="正常" value="0" />
            <el-option label="停用" value="1" />
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
        :data="dictList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="字典编号" align="center" prop="dictId" />
        <el-table-column label="字典名称" align="center" prop="dictName" />
        <el-table-column label="字典类型" align="center" prop="dictType" />
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
        <el-table-column label="操作" align="center" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">{{ $t('common.delete') }}</el-button>
            <el-button link type="primary" icon="Collection" @click="handleData(scope.row)">数据</el-button>
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
    
    <!-- 添加或修改字典配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="dictFormRef" label-width="80px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="form.dictName" placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="form.dictType" placeholder="请输入字典类型" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    
    <!-- 字典数据对话框 -->
    <el-dialog :title="'字典数据 - ' + dictName" v-model="dataDialogVisible" width="800px" append-to-body>
      <div class="toolbar-container">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="Plus"
              @click="handleAddData"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="Edit"
              :disabled="singleData"
              @click="handleUpdateData"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="Delete"
              :disabled="multipleData"
              @click="handleDeleteData"
            >删除</el-button>
          </el-col>
        </el-row>
      </div>
      
      <div class="table-container">
        <el-table
          :data="dataList"
          style="width: 100%"
          v-loading="dataLoading"
          @selection-change="handleDataSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="字典标签" align="center" prop="dictLabel" />
          <el-table-column label="字典键值" align="center" prop="dictValue" />
          <el-table-column label="显示顺序" align="center" prop="dictSort" />
          <el-table-column label="状态" align="center">
            <template #default="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleDataStatusChange(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
          <el-table-column label="操作" align="center" width="150" fixed="right">
            <template #default="scope">
              <el-button link type="primary" icon="Edit" @click="handleUpdateData(scope.row)">修改</el-button>
              <el-button link type="primary" icon="Delete" @click="handleDeleteData(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      
      <!-- 添加或修改字典数据对话框 -->
      <el-dialog :title="dataTitle" v-model="dataOpen" width="600px" append-to-body>
        <el-form :model="dataForm" :rules="dataRules" ref="dataFormRef" label-width="80px">
          <el-form-item label="字典标签" prop="dictLabel">
            <el-input v-model="dataForm.dictLabel" placeholder="请输入字典标签" />
          </el-form-item>
          <el-form-item label="字典键值" prop="dictValue">
            <el-input v-model="dataForm.dictValue" placeholder="请输入字典键值" />
          </el-form-item>
          <el-form-item label="显示顺序" prop="dictSort">
            <el-input-number v-model="dataForm.dictSort" controls-position="right" :min="0" />
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="dataForm.status">
              <el-radio
                v-for="dict in sys_normal_disable"
                :key="dict.value"
                :label="dict.value"
              >{{ dict.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="dataForm.remark" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="primary" @click="submitDataForm">确 定</el-button>
            <el-button @click="cancelData">取 消</el-button>
          </div>
        </template>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getDictList, getDict, addDict, updateDict, delDict, changeDictStatus } from '@/api/system/dict'
import { getDictDataList, getDictData, addDictData, updateDictData, delDictData, changeDictDataStatus } from '@/api/system/dict/data'

// 定义响应式数据
const dictList = ref([])
const loading = ref(false)
const total = ref(0)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const open = ref(false)
const title = ref('')
const sys_normal_disable = ref([])
const dataDialogVisible = ref(false)
const dictName = ref('')
const dataList = ref([])
const dataLoading = ref(false)
const dataIds = ref([])
const singleData = ref(true)
const multipleData = ref(true)
const dataOpen = ref(false)
const dataTitle = ref('')

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  dictName: '',
  dictType: '',
  status: ''
})

// 表单数据
const form = reactive({
  dictId: undefined,
  dictName: '',
  dictType: '',
  status: '0',
  remark: ''
})

// 表单验证规则
const rules = {
  dictName: [{ required: true, message: '字典名称不能为空', trigger: 'blur' }],
  dictType: [{ required: true, message: '字典类型不能为空', trigger: 'blur' }]
}

// 数据表单数据
const dataForm = reactive({
  dictCode: undefined,
  dictLabel: '',
  dictValue: '',
  dictSort: 0,
  status: '0',
  remark: ''
})

// 数据表单验证规则
const dataRules = {
  dictLabel: [{ required: true, message: '字典标签不能为空', trigger: 'blur' }],
  dictValue: [{ required: true, message: '字典键值不能为空', trigger: 'blur' }],
  dictSort: [{ required: true, message: '字典排序不能为空', trigger: 'blur' }]
}

// 查询表单引用
const queryForm = ref(null)
const dictFormRef = ref(null)
const dataFormRef = ref(null)

// 获取字典列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getDictList(queryParams)
    dictList.value = response.data.items
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
  ids.value = selection.map(item => item.dictId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 字典状态修改
const handleStatusChange = async (row) => {
  let text = row.status === '0' ? '启用' : '停用'
  try {
    await ElMessageBox.confirm(`确认要${text}${row.dictName}字典吗?`, '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await changeDictStatus(row.dictId, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    console.error(error)
    row.status = row.status === '0' ? '1' : '0'
  }
}

// 打开添加字典对话框
const handleAdd = () => {
  reset()
  open.value = true
  title.value = '添加字典'
}

// 打开修改字典对话框
const handleUpdate = (row) => {
  reset()
  const dictId = row.dictId || ids.value[0]
  getDict(dictId).then(response => {
    const data = response.data
    form.dictId = data.dictId
    form.dictName = data.dictName
    form.dictType = data.dictType
    form.status = data.status
    form.remark = data.remark
    
    open.value = true
    title.value = '修改字典'
  })
}

// 提交表单
const submitForm = () => {
  if (!dictFormRef.value) return
  
  dictFormRef.value.validate(async (valid) => {
    if (valid) {
      if (form.dictId) {
        await updateDict(form)
        ElMessage.success('修改成功')
      } else {
        await addDict(form)
        ElMessage.success('新增成功')
      }
      open.value = false
      getList()
    }
  })
}

// 删除字典
const handleDelete = (row) => {
  const dictIds = row.dictId ? [row.dictId] : ids.value
  ElMessageBox.confirm('是否确认删除字典编号为"' + dictIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delDict(dictIds)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 取消按钮
const cancel = () => {
  open.value = false
  reset()
}

// 表单重置
const reset = () => {
  form.dictId = undefined
  form.dictName = ''
  form.dictType = ''
  form.status = '0'
  form.remark = ''
  
  dictFormRef.value?.resetFields()
}

// 打开字典数据对话框
const handleData = (row) => {
  dictName.value = row.dictName
  dataForm.dictType = row.dictType
  getDataList(row.dictType)
  dataDialogVisible.value = true
}

// 获取字典数据列表
const getDataList = async (dictType) => {
  dataLoading.value = true
  try {
    const response = await getDictDataList({ dictType })
    dataList.value = response.data
  } catch (error) {
    console.error(error)
  } finally {
    dataLoading.value = false
  }
}

// 多选框选中数据
const handleDataSelectionChange = (selection) => {
  dataIds.value = selection.map(item => item.dictCode)
  singleData.value = selection.length !== 1
  multipleData.value = !selection.length
}

// 字典数据状态修改
const handleDataStatusChange = async (row) => {
  let text = row.status === '0' ? '启用' : '停用'
  try {
    await ElMessageBox.confirm(`确认要${text}${row.dictLabel}字典数据吗?`, '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await changeDictDataStatus(row.dictCode, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    console.error(error)
    row.status = row.status === '0' ? '1' : '0'
  }
}

// 打开添加字典数据对话框
const handleAddData = () => {
  resetData()
  dataOpen.value = true
  dataTitle.value = '添加字典数据'
}

// 打开修改字典数据对话框
const handleUpdateData = (row) => {
  resetData()
  const dictCode = row.dictCode || dataIds.value[0]
  getDictData(dictCode).then(response => {
    const data = response.data
    dataForm.dictCode = data.dictCode
    dataForm.dictLabel = data.dictLabel
    dataForm.dictValue = data.dictValue
    dataForm.dictSort = data.dictSort
    dataForm.status = data.status
    dataForm.remark = data.remark
    
    dataOpen.value = true
    dataTitle.value = '修改字典数据'
  })
}

// 提交字典数据表单
const submitDataForm = () => {
  if (!dataFormRef.value) return
  
  dataFormRef.value.validate(async (valid) => {
    if (valid) {
      if (dataForm.dictCode) {
        await updateDictData(dataForm)
        ElMessage.success('修改成功')
      } else {
        await addDictData(dataForm)
        ElMessage.success('新增成功')
      }
      dataOpen.value = false
      getDataList(dataForm.dictType)
    }
  })
}

// 删除字典数据
const handleDeleteData = (row) => {
  const dictCodes = row.dictCode ? [row.dictCode] : dataIds.value
  ElMessageBox.confirm('是否确认删除字典数据编号为"' + dictCodes + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delDictData(dictCodes)
    ElMessage.success('删除成功')
    getDataList(dataForm.dictType)
  }).catch(() => {})
}

// 取消按钮
const cancelData = () => {
  dataOpen.value = false
  resetData()
}

// 表单重置
const resetData = () => {
  dataForm.dictCode = undefined
  dataForm.dictLabel = ''
  dataForm.dictValue = ''
  dataForm.dictSort = 0
  dataForm.status = '0'
  dataForm.remark = ''
  
  dataFormRef.value?.resetFields()
}

// 获取字典数据
const getDicts = () => {
  // 模拟字典数据
  sys_normal_disable.value = [
    { value: '0', label: '正常' },
    { value: '1', label: '停用' }
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