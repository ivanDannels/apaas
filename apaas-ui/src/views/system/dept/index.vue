<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="部门名称" prop="deptName">
          <el-input
            v-model="queryParams.deptName"
            placeholder="请输入部门名称"
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
        :data="deptList"
        style="width: 100%"
        row-key="deptId"
        v-loading="loading"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        @selection-change="handleSelectionChange"
        default-expand-all
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="deptName" label="部门名称" width="200" />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
              {{ scope.row.status === '0' ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" width="180" />
        <el-table-column label="操作" align="center" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)">{{ $t('common.add') }}</el-button>
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">{{ $t('common.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    
    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="deptFormRef" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级部门">
              <el-tree-select
                v-model="form.parentId"
                :data="deptOptions"
                :props="{ value: 'deptId', label: 'deptName', children: 'children' }"
                node-key="deptId"
                placeholder="选择上级部门"
                check-strictly
                :render-after-expand="false"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="deptName">
              <el-input v-model="form.deptName" placeholder="请输入部门名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="sort">
              <el-input-number v-model="form.sort" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负责人">
              <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
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
import { getDeptList, getDept, addDept, updateDept, delDept } from '@/api/system/dept'

// 定义响应式数据
const deptList = ref([])
const loading = ref(false)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const open = ref(false)
const title = ref('')
const deptOptions = ref([])
const sys_normal_disable = ref([])

// 查询参数
const queryParams = reactive({
  deptName: '',
  status: ''
})

// 表单数据
const form = reactive({
  deptId: undefined,
  parentId: undefined,
  deptName: '',
  sort: 0,
  leader: '',
  phone: '',
  email: '',
  status: '0'
})

// 表单验证规则
const rules = {
  deptName: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '显示排序不能为空', trigger: 'blur' }],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ]
}

// 查询表单引用
const queryForm = ref(null)
const deptFormRef = ref(null)

// 获取部门列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getDeptList(queryParams)
    deptList.value = response.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 查询
const handleQuery = () => {
  getList()
}

// 重置查询
const resetQuery = () => {
  queryForm.value?.resetFields()
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.deptId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 打开添加部门对话框
const handleAdd = (row) => {
  reset()
  getTreeselect()
  
  if (row && row.deptId) {
    form.parentId = row.deptId
  } else {
    form.parentId = undefined
  }
  
  open.value = true
  title.value = '添加部门'
}

// 打开修改部门对话框
const handleUpdate = (row) => {
  reset()
  getTreeselect()
  
  getDept(row.deptId).then(response => {
    const data = response.data
    form.deptId = data.deptId
    form.parentId = data.parentId
    form.deptName = data.deptName
    form.sort = data.sort
    form.leader = data.leader
    form.phone = data.phone
    form.email = data.email
    form.status = data.status
    
    open.value = true
    title.value = '修改部门'
  })
}

// 提交表单
const submitForm = () => {
  if (!deptFormRef.value) return
  
  deptFormRef.value.validate(async (valid) => {
    if (valid) {
      if (form.deptId) {
        await updateDept(form)
        ElMessage.success('修改成功')
      } else {
        await addDept(form)
        ElMessage.success('新增成功')
      }
      open.value = false
      getList()
    }
  })
}

// 删除部门
const handleDelete = (row) => {
  const deptIds = row.deptId ? [row.deptId] : ids.value
  ElMessageBox.confirm('是否确认删除部门编号为"' + deptIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delDept(deptIds)
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
  form.deptId = undefined
  form.parentId = undefined
  form.deptName = ''
  form.sort = 0
  form.leader = ''
  form.phone = ''
  form.email = ''
  form.status = '0'
  
  deptFormRef.value?.resetFields()
}

// 获取部门下拉树结构
const getTreeselect = () => {
  getDeptList().then(response => {
    deptOptions.value = response.data
  })
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