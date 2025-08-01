<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="岗位编码" prop="postCode">
          <el-input
            v-model="queryParams.postCode"
            placeholder="请输入岗位编码"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="岗位名称" prop="postName">
          <el-input
            v-model="queryParams.postName"
            placeholder="请输入岗位名称"
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
        :data="postList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="岗位编号" align="center" prop="postId" />
        <el-table-column label="岗位编码" align="center" prop="postCode" />
        <el-table-column label="岗位名称" align="center" prop="postName" />
        <el-table-column label="岗位排序" align="center" prop="postSort" />
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
        <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
        <el-table-column label="操作" align="center" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">{{ $t('common.delete') }}</el-button>
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
    
    <!-- 添加或修改岗位配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="postFormRef" label-width="80px">
        <el-form-item label="岗位名称" prop="postName">
          <el-input v-model="form.postName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="岗位编码" prop="postCode">
          <el-input v-model="form.postCode" placeholder="请输入编码名称" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="postSort">
          <el-input-number v-model="form.postSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="岗位状态">
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getPostList, getPost, addPost, updatePost, delPost, changePostStatus } from '@/api/system/post'

// 定义响应式数据
const postList = ref([])
const loading = ref(false)
const total = ref(0)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const open = ref(false)
const title = ref('')
const sys_normal_disable = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  postCode: '',
  postName: '',
  status: ''
})

// 表单数据
const form = reactive({
  postId: undefined,
  postCode: '',
  postName: '',
  postSort: 0,
  status: '0',
  remark: ''
})

// 表单验证规则
const rules = {
  postName: [{ required: true, message: '岗位名称不能为空', trigger: 'blur' }],
  postCode: [{ required: true, message: '岗位编码不能为空', trigger: 'blur' }],
  postSort: [{ required: true, message: '岗位顺序不能为空', trigger: 'blur' }]
}

// 查询表单引用
const queryForm = ref(null)
const postFormRef = ref(null)

// 获取岗位列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getPostList(queryParams)
    postList.value = response.data.items
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
  ids.value = selection.map(item => item.postId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 岗位状态修改
const handleStatusChange = async (row) => {
  let text = row.status === '0' ? '启用' : '停用'
  try {
    await ElMessageBox.confirm(`确认要${text}${row.postName}岗位吗?`, '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await changePostStatus(row.postId, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    console.error(error)
    row.status = row.status === '0' ? '1' : '0'
  }
}

// 打开添加岗位对话框
const handleAdd = () => {
  reset()
  open.value = true
  title.value = '添加岗位'
}

// 打开修改岗位对话框
const handleUpdate = (row) => {
  reset()
  const postId = row.postId || ids.value[0]
  getPost(postId).then(response => {
    const data = response.data
    form.postId = data.postId
    form.postCode = data.postCode
    form.postName = data.postName
    form.postSort = data.postSort
    form.status = data.status
    form.remark = data.remark
    
    open.value = true
    title.value = '修改岗位'
  })
}

// 提交表单
const submitForm = () => {
  if (!postFormRef.value) return
  
  postFormRef.value.validate(async (valid) => {
    if (valid) {
      if (form.postId) {
        await updatePost(form)
        ElMessage.success('修改成功')
      } else {
        await addPost(form)
        ElMessage.success('新增成功')
      }
      open.value = false
      getList()
    }
  })
}

// 删除岗位
const handleDelete = (row) => {
  const postIds = row.postId ? [row.postId] : ids.value
  ElMessageBox.confirm('是否确认删除岗位编号为"' + postIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delPost(postIds)
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
  form.postId = undefined
  form.postCode = ''
  form.postName = ''
  form.postSort = 0
  form.status = '0'
  form.remark = ''
  
  postFormRef.value?.resetFields()
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