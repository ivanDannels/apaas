<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input
            v-model="queryParams.roleName"
            placeholder="请输入角色名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="权限字符" prop="roleKey">
          <el-input
            v-model="queryParams.roleKey"
            placeholder="请输入权限字符"
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
        :data="roleList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="角色编号" align="center" prop="roleId" />
        <el-table-column label="角色名称" align="center" prop="roleName" />
        <el-table-column label="权限字符" align="center" prop="roleKey" />
        <el-table-column label="显示顺序" align="center" prop="roleSort" />
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
            <el-button link type="primary" icon="Lock" @click="handleAuth(scope.row)">数据权限</el-button>
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
    
    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="roleFormRef" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="权限字符" prop="roleKey">
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
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
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选</el-checkbox>
          <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'menu')">父子联动</el-checkbox>
          <el-tree
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            ref="menuTreeRef"
            node-key="id"
            :props="{ label: 'label', children: 'children' }"
            :default-checked-keys="menuCheckedKeys"
          />
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
    
    <!-- 数据权限配置对话框 -->
    <el-dialog :title="'分配数据权限 - ' + deptName" v-model="authDialogVisible" width="500px" append-to-body>
      <el-form :model="authForm" ref="authFormRef" label-width="80px">
        <el-form-item label="数据权限">
          <el-select v-model="authForm.dataScope" @change="dataScopeSelectChange">
            <el-option
              v-for="item in dataScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="部门权限" v-show="authForm.dataScope === 2">
          <el-checkbox v-model="deptExpand" @change="handleCheckedTreeExpand($event, 'dept')">展开/折叠</el-checkbox>
          <el-checkbox v-model="deptNodeAll" @change="handleCheckedTreeNodeAll($event, 'dept')">全选/全不选</el-checkbox>
          <el-checkbox v-model="authForm.deptCheckStrictly" @change="handleCheckedTreeConnect($event, 'dept')">父子联动</el-checkbox>
          <el-tree
            class="tree-border"
            :data="deptOptions"
            show-checkbox
            default-expand-all
            ref="deptTreeRef"
            node-key="id"
            :props="{ label: 'label', children: 'children' }"
            :default-checked-keys="deptCheckedKeys"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitAuthForm">确 定</el-button>
          <el-button @click="cancelAuth">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getRoleList, getRole, addRole, updateRole, delRole, changeRoleStatus, updateAuthDataScope } from '@/api/system/role'

// 定义响应式数据
const roleList = ref([])
const loading = ref(false)
const total = ref(0)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const open = ref(false)
const title = ref('')
const menuExpand = ref(false)
const menuNodeAll = ref(false)
const deptExpand = ref(true)
const deptNodeAll = ref(false)
const authDialogVisible = ref(false)
const deptName = ref('')
const menuOptions = ref([])
const deptOptions = ref([])
const menuCheckedKeys = ref([])
const deptCheckedKeys = ref([])
const sys_normal_disable = ref([])
const dataScopeOptions = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  roleName: '',
  roleKey: '',
  status: ''
})

// 表单数据
const form = reactive({
  roleId: undefined,
  roleName: '',
  roleKey: '',
  roleSort: 0,
  status: '0',
  menuCheckStrictly: true,
  remark: ''
})

// 数据权限表单数据
const authForm = reactive({
  roleId: undefined,
  roleName: '',
  dataScope: '1',
  deptCheckStrictly: true
})

// 表单验证规则
const rules = {
  roleName: [{ required: true, message: '角色名称不能为空', trigger: 'blur' }],
  roleKey: [{ required: true, message: '权限字符不能为空', trigger: 'blur' }],
  roleSort: [{ required: true, message: '显示顺序不能为空', trigger: 'blur' }]
}

// 查询表单引用
const queryForm = ref(null)
const roleFormRef = ref(null)
const authFormRef = ref(null)
const menuTreeRef = ref(null)
const deptTreeRef = ref(null)

// 获取角色列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getRoleList(queryParams)
    roleList.value = response.data.items
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
  ids.value = selection.map(item => item.roleId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 角色状态修改
const handleStatusChange = async (row) => {
  let text = row.status === '0' ? '启用' : '停用'
  try {
    await ElMessageBox.confirm(`确认要${text}${row.roleName}角色吗?`, '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await changeRoleStatus(row.roleId, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    console.error(error)
    row.status = row.status === '0' ? '1' : '0'
  }
}

// 打开添加角色对话框
const handleAdd = () => {
  reset()
  open.value = true
  title.value = '添加角色'
  getMenuTreeselect()
}

// 打开修改角色对话框
const handleUpdate = (row) => {
  reset()
  const roleId = row.roleId || ids.value[0]
  getRole(roleId).then(response => {
    const data = response.data
    form.roleId = data.roleId
    form.roleName = data.roleName
    form.roleKey = data.roleKey
    form.roleSort = data.roleSort
    form.status = data.status
    form.remark = data.remark
    
    open.value = true
    title.value = '修改角色'
    
    getMenuTreeselect()
    // 设置菜单选中
    menuCheckedKeys.value = data.menuIds
  })
}

// 提交表单
const submitForm = () => {
  if (!roleFormRef.value) return
  
  roleFormRef.value.validate(async (valid) => {
    if (valid) {
      // 获取选中的菜单
      const menuIds = menuTreeRef.value?.getCheckedKeys() || []
      const halfMenuIds = menuTreeRef.value?.getHalfCheckedKeys() || []
      form.menuIds = [...menuIds, ...halfMenuIds]
      
      if (form.roleId) {
        await updateRole(form)
        ElMessage.success('修改成功')
      } else {
        await addRole(form)
        ElMessage.success('新增成功')
      }
      open.value = false
      getList()
    }
  })
}

// 删除角色
const handleDelete = (row) => {
  const roleIds = row.roleId ? [row.roleId] : ids.value
  ElMessageBox.confirm('是否确认删除角色编号为"' + roleIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delRole(roleIds)
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
  form.roleId = undefined
  form.roleName = ''
  form.roleKey = ''
  form.roleSort = 0
  form.status = '0'
  form.menuCheckStrictly = true
  form.remark = ''
  
  menuExpand.value = false
  menuNodeAll.value = false
  
  roleFormRef.value?.resetFields()
}

// 获取菜单树结构
const getMenuTreeselect = () => {
  // 模拟菜单数据
  menuOptions.value = [
    {
      id: 1,
      label: '系统管理',
      children: [
        {
          id: 2,
          label: '用户管理'
        },
        {
          id: 3,
          label: '角色管理'
        },
        {
          id: 4,
          label: '菜单管理'
        }
      ]
    },
    {
      id: 5,
      label: '流程管理',
      children: [
        {
          id: 6,
          label: '流程定义'
        },
        {
          id: 7,
          label: '流程实例'
        },
        {
          id: 8,
          label: '任务管理'
        }
      ]
    }
  ]
}

// 所有菜单节点数据
const getMenuAllCheckedKeys = () => {
  // 这里应该返回所有选中的菜单ID
  return []
}

// 树权限（展开/折叠）
const handleCheckedTreeExpand = (value, type) => {
  if (type === 'menu') {
    const treeList = menuOptions.value
    for (let i = 0; i < treeList.length; i++) {
      menuTreeRef.value?.setChecked(treeList[i].id, value, true)
    }
  } else if (type === 'dept') {
    const treeList = deptOptions.value
    for (let i = 0; i < treeList.length; i++) {
      deptTreeRef.value?.setChecked(treeList[i].id, value, true)
    }
  }
}

// 树权限（全选/全不选）
const handleCheckedTreeNodeAll = (value, type) => {
  if (type === 'menu') {
    const treeList = menuOptions.value
    for (let i = 0; i < treeList.length; i++) {
      menuTreeRef.value?.setChecked(treeList[i].id, value, true)
    }
  } else if (type === 'dept') {
    const treeList = deptOptions.value
    for (let i = 0; i < treeList.length; i++) {
      deptTreeRef.value?.setChecked(treeList[i].id, value, true)
    }
  }
}

// 树权限（父子联动）
const handleCheckedTreeConnect = (value, type) => {
  if (type === 'menu') {
    form.menuCheckStrictly = value ? true : false
  } else if (type === 'dept') {
    authForm.deptCheckStrictly = value ? true : false
  }
}

// 打开数据权限对话框
const handleAuth = (row) => {
  authForm.roleId = row.roleId
  authForm.roleName = row.roleName
  deptName.value = row.roleName
  
  // 获取部门数据
  getDeptTree()
  
  // 获取当前角色的数据权限
  getRole(row.roleId).then(response => {
    const data = response.data
    authForm.dataScope = data.dataScope
    
    // 设置部门选中
    deptCheckedKeys.value = data.deptIds
    
    authDialogVisible.value = true
  })
}

// 获取部门树结构
const getDeptTree = () => {
  // 模拟部门数据
  deptOptions.value = [
    {
      id: 1,
      label: '研发部门',
      children: [
        {
          id: 3,
          label: '前端团队'
        },
        {
          id: 4,
          label: '后端团队'
        }
      ]
    },
    {
      id: 2,
      label: '测试部门'
    }
  ]
}

// 数据权限选择变化
const dataScopeSelectChange = (value) => {
  if (value !== 2) {
    deptCheckedKeys.value = []
  }
}

// 提交数据权限
const submitAuthForm = () => {
  if (!authFormRef.value) return
  
  // 获取选中的部门
  if (authForm.dataScope === 2) {
    const deptIds = deptTreeRef.value?.getCheckedKeys() || []
    const halfDeptIds = deptTreeRef.value?.getHalfCheckedKeys() || []
    authForm.deptIds = [...deptIds, ...halfDeptIds]
  } else {
    authForm.deptIds = []
  }
  
  updateAuthDataScope(authForm).then(() => {
    ElMessage.success('数据权限修改成功')
    authDialogVisible.value = false
    getList()
  })
}

// 关闭数据权限对话框
const cancelAuth = () => {
  authDialogVisible.value = false
  resetAuthForm()
}

// 表单重置
const resetAuthForm = () => {
  authForm.roleId = undefined
  authForm.roleName = ''
  authForm.dataScope = '1'
  authForm.deptCheckStrictly = true
  
  deptExpand.value = true
  deptNodeAll.value = false
  
  authFormRef.value?.resetFields()
}

// 获取字典数据
const getDicts = () => {
  // 模拟字典数据
  sys_normal_disable.value = [
    { value: '0', label: '正常' },
    { value: '1', label: '停用' }
  ]
  
  dataScopeOptions.value = [
    { value: '1', label: '全部数据权限' },
    { value: '2', label: '自定数据权限' },
    { value: '3', label: '本部门数据权限' },
    { value: '4', label: '本部门及以下数据权限' },
    { value: '5', label: '仅本人数据权限' }
  ]
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
  getDicts()
})
</script>

<style scoped>
.tree-border {
  margin-top: 5px;
  border: 1px solid #e5e6e7;
  border-radius: 4px;
  padding: 10px;
}
</style>