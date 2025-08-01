<template>
  <div class="app-container">
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
        :data="menuList"
        style="width: 100%"
        row-key="id"
        v-loading="loading"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="menuName" label="菜单名称" :show-overflow-tooltip="true" width="150" />
        <el-table-column prop="icon" label="图标" align="center" width="100">
          <template #default="scope">
            <svg-icon :icon-class="scope.row.icon" />
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" align="center" width="80" />
        <el-table-column prop="perms" label="权限标识" :show-overflow-tooltip="true" width="200" />
        <el-table-column prop="component" label="组件路径" :show-overflow-tooltip="true" width="200" />
        <el-table-column label="状态" align="center" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
              {{ scope.row.status === '0' ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
        <el-table-column label="操作" align="center" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)">{{ $t('common.add') }}</el-button>
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">{{ $t('common.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    
    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form :model="form" :rules="rules" ref="menuFormRef" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <el-tree-select
                v-model="form.parentId"
                :data="menuOptions"
                :props="{ value: 'id', label: 'menuName', children: 'children' }"
                node-key="id"
                placeholder="选择上级菜单"
                check-strictly
                :render-after-expand="false"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="form.menuType">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单状态">
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
        <el-row>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
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
            <el-form-item label="路由地址" prop="path">
              <el-input v-model="form.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组件路径" prop="component">
              <el-input v-model="form.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="权限字符">
              <el-input v-model="form.perms" placeholder="请输入权限字符" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单图标">
              <el-input v-model="form.icon" placeholder="请输入菜单图标" maxlength="100">
                <template #append>
                  <el-button icon="Search" @click="showSelectIcon" />
                </template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否外链">
              <el-radio-group v-model="form.isFrame">
                <el-radio label="0">是</el-radio>
                <el-radio label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否缓存">
              <el-radio-group v-model="form.isCache">
                <el-radio label="0">缓存</el-radio>
                <el-radio label="1">不缓存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否显示">
              <el-radio-group v-model="form.visible">
                <el-radio label="0">显示</el-radio>
                <el-radio label="1">隐藏</el-radio>
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
    
    <!-- 选择图标对话框 -->
    <el-dialog :title="'图标选择'" v-model="iconDialogVisible" width="800px" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="系统图标" name="system">
          <div class="icon-list">
            <div class="icon-item" v-for="item in iconList" :key="item" @click="selectIcon(item)">
              <svg-icon :icon-class="item" />
              <span>{{ item }}</span>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="自定义图标" name="custom">
          <div class="icon-list">
            <div class="icon-item" v-for="item in customIconList" :key="item" @click="selectIcon(item)">
              <svg-icon :icon-class="item" />
              <span>{{ item }}</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getMenuList, getMenu, addMenu, updateMenu, delMenu } from '@/api/system/menu'

// 定义响应式数据
const menuList = ref([])
const loading = ref(false)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const open = ref(false)
const title = ref('')
const menuOptions = ref([])
const iconDialogVisible = ref(false)
const activeTab = ref('system')
const iconList = ref([])
const customIconList = ref([])
const sys_normal_disable = ref([])

// 表单数据
const form = reactive({
  id: undefined,
  parentId: 0,
  menuName: '',
  icon: '',
  menuType: 'M',
  sort: 0,
  path: '',
  component: '',
  perms: '',
  status: '0',
  isFrame: '1',
  isCache: '0',
  visible: '0'
})

// 表单验证规则
const rules = {
  menuName: [{ required: true, message: '菜单名称不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '菜单顺序不能为空', trigger: 'blur' }],
  path: [{ required: true, message: '路由地址不能为空', trigger: 'blur' }]
}

// 表单引用
const menuFormRef = ref(null)

// 获取菜单列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getMenuList()
    menuList.value = response.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 打开添加菜单对话框
const handleAdd = (row) => {
  reset()
  getMenuTreeselect()
  
  if (row && row.id) {
    form.parentId = row.id
  } else {
    form.parentId = 0
  }
  
  open.value = true
  title.value = '添加菜单'
}

// 打开修改菜单对话框
const handleUpdate = (row) => {
  reset()
  getMenuTreeselect()
  
  getMenu(row.id).then(response => {
    const data = response.data
    form.id = data.id
    form.parentId = data.parentId
    form.menuName = data.menuName
    form.icon = data.icon
    form.menuType = data.menuType
    form.sort = data.sort
    form.path = data.path
    form.component = data.component
    form.perms = data.perms
    form.status = data.status
    form.isFrame = data.isFrame
    form.isCache = data.isCache
    form.visible = data.visible
    
    open.value = true
    title.value = '修改菜单'
  })
}

// 提交表单
const submitForm = () => {
  if (!menuFormRef.value) return
  
  menuFormRef.value.validate(async (valid) => {
    if (valid) {
      if (form.id) {
        await updateMenu(form)
        ElMessage.success('修改成功')
      } else {
        await addMenu(form)
        ElMessage.success('新增成功')
      }
      open.value = false
      getList()
    }
  })
}

// 删除菜单
const handleDelete = (row) => {
  const menuIds = row.id ? [row.id] : ids.value
  ElMessageBox.confirm('是否确认删除菜单编号为"' + menuIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delMenu(menuIds)
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
  form.id = undefined
  form.parentId = 0
  form.menuName = ''
  form.icon = ''
  form.menuType = 'M'
  form.sort = 0
  form.path = ''
  form.component = ''
  form.perms = ''
  form.status = '0'
  form.isFrame = '1'
  form.isCache = '0'
  form.visible = '0'
  
  menuFormRef.value?.resetFields()
}

// 获取菜单下拉树结构
const getMenuTreeselect = () => {
  getMenuList().then(response => {
    const data = response.data
    const menu = { id: 0, menuName: '主类目', children: [] }
    menu.children = data
    menuOptions.value = [menu]
  })
}

// 显示图标选择对话框
const showSelectIcon = () => {
  // 模拟图标数据
  iconList.value = [
    'dashboard', 'user', 'peoples', 'setting', 'tree', 'tree-table', 'form', 'nested',
    'list', 'edit', 'add', 'delete', 'search', 'refresh', 'more', 'fullscreen', 'exit-fullscreen',
    'lock', 'unlock', 'eye', 'eye-open', 'link', 'unlink', 'chart', 'tab'
  ]
  
  customIconList.value = [
    'icon-home', 'icon-user', 'icon-role', 'icon-menu', 'icon-dept', 'icon-dict', 'icon-log',
    'icon-monitor', 'icon-job', 'icon-notice', 'icon-gen', 'icon-druid', 'icon-file', 'icon-sys',
    'icon-tools', 'icon-doc', 'icon-github', 'icon-gitee'
  ]
  
  iconDialogVisible.value = true
}

// 选择图标
const selectIcon = (name) => {
  form.icon = name
  iconDialogVisible.value = false
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
.icon-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.icon-item:hover {
  border-color: #409eff;
  background-color: #ecf5ff;
}

.icon-item svg {
  font-size: 24px;
  margin-bottom: 5px;
}

.icon-item span {
  font-size: 12px;
}
</style>