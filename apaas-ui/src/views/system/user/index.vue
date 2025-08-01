<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
        <el-form-item label="用户名称" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input
            v-model="queryParams.phoneNumber"
            placeholder="请输入手机号码"
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
        :data="userList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="用户编号" align="center" prop="userId" />
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="用户昵称" align="center" prop="nickName" />
        <el-table-column label="部门" align="center" prop="deptName" />
        <el-table-column label="手机号码" align="center" prop="phoneNumber" width="120" />
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
    
    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="userFormRef" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门" prop="deptId">
              <el-tree-select
                v-model="form.deptId"
                :data="deptOptions"
                :props="{ value: 'id', label: 'label', children: 'children' }"
                node-key="id"
                placeholder="请选择部门"
                check-strictly
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phoneNumber">
              <el-input v-model="form.phoneNumber" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择">
                <el-option
                  v-for="dict in sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="岗位">
              <el-select v-model="form.postIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { getUserList, getUser, addUser, updateUser, delUser, changeUserStatus } from '@/api/system/user'
import type { FormInstance, FormRules } from 'element-plus'
import type { User, Post, Role } from '@/types/api'

// 定义响应式数据
const userList = ref<User[]>([])
const loading = ref<boolean>(false)
const total = ref<number>(0)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const open = ref<boolean>(false)
const title = ref<string>('')
const deptOptions = ref<any[]>([])
const postOptions = ref<Post[]>([])
const roleOptions = ref<Role[]>([])
const sys_user_sex = ref<any[]>([])
const sys_normal_disable = ref<any[]>([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  userName: '',
  phoneNumber: '',
  status: ''
})

// 表单数据
const form = reactive({
  userId: undefined as number | undefined,
  userName: '',
  nickName: '',
  deptId: undefined as number | undefined,
  phoneNumber: '',
  email: '',
  sex: '',
  status: '0',
  postIds: [] as number[],
  roleIds: [] as number[],
  remark: ''
})

// 表单验证规则
const rules = reactive<FormRules>({
  userName: [{ required: true, message: '用户名称不能为空', trigger: 'blur' }],
  nickName: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }],
  deptId: [{ required: true, message: '所属部门不能为空', trigger: 'change' }],
  phoneNumber: [
    { required: true, message: '手机号码不能为空', trigger: 'blur' },
    { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ]
})

// 查询表单引用
const queryFormRef = ref<FormInstance | null>(null)
const userFormRef = ref<FormInstance | null>(null)

// 获取用户列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getUserList(queryParams)
    userList.value = response.data.items
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

// 多选框选中数据
const handleSelectionChange = (selection: User[]) => {
  ids.value = selection.map(item => item.userId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 用户状态修改
const handleStatusChange = async (row: User) => {
  let text = row.status === '0' ? '启用' : '停用'
  try {
    await ElMessageBox.confirm(`确认要${text}${row.userName}用户吗?`, '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await changeUserStatus(row.userId, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    console.error(error)
    row.status = row.status === '0' ? '1' : '0'
  }
}

// 打开添加用户对话框
const handleAdd = () => {
  reset()
  open.value = true
  title.value = '添加用户'
  // 获取部门、岗位、角色数据（这里模拟数据）
  getTreeselect()
  getPostOptions()
  getRoleOptions()
}

// 打开修改用户对话框
const handleUpdate = (row?: User) => {
  reset()
  const userId = row?.userId || ids.value[0]
  getUser(userId).then(response => {
    const data = response.data
    form.userId = data.userId
    form.userName = data.userName
    form.nickName = data.nickName
    form.deptId = data.deptId
    form.phoneNumber = data.phoneNumber
    form.email = data.email
    form.sex = data.sex
    form.status = data.status
    form.remark = data.remark
    
    // 设置岗位和角色
    form.postIds = data.posts.map((item: Post) => item.postId)
    form.roleIds = data.roles.map((item: Role) => item.roleId)
    
    open.value = true
    title.value = '修改用户'
    
    // 获取部门、岗位、角色数据
    getTreeselect()
    getPostOptions()
    getRoleOptions()
  })
}

// 提交表单
const submitForm = () => {
  if (!userFormRef.value) return
  
  userFormRef.value.validate(async (valid) => {
    if (valid) {
      if (form.userId) {
        await updateUser(form)
        ElMessage.success('修改成功')
      } else {
        await addUser(form)
        ElMessage.success('新增成功')
      }
      open.value = false
      getList()
    }
  })
}

// 删除用户
const handleDelete = (row?: User) => {
  const userIds = row?.userId ? [row.userId] : ids.value
  ElMessageBox.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delUser(userIds)
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
  form.userId = undefined
  form.userName = ''
  form.nickName = ''
  form.deptId = undefined
  form.phoneNumber = ''
  form.email = ''
  form.sex = ''
  form.status = '0'
  form.postIds = []
  form.roleIds = []
  form.remark = ''
  
  userFormRef.value?.resetFields()
}

// 获取部门树结构
const getTreeselect = () => {
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

// 获取岗位选项
const getPostOptions = () => {
  // 模拟岗位数据
  postOptions.value = [
    { postId: 1, postName: '项目经理' },
    { postId: 2, postName: '前端工程师' },
    { postId: 3, postName: '后端工程师' },
    { postId: 4, postName: '测试工程师' }
  ]
}

// 获取角色选项
const getRoleOptions = () => {
  // 模拟角色数据
  roleOptions.value = [
    { roleId: 1, roleName: '管理员' },
    { roleId: 2, roleName: '普通用户' },
    { roleId: 3, roleName: '访客' }
  ]
}

// 获取字典数据
const getDicts = () => {
  // 模拟字典数据
  sys_user_sex.value = [
    { value: '0', label: '男' },
    { value: '1', label: '女' }
  ]
  
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