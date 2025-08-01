<template>
  <div class="app-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>基本信息</span>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button @click="handleClose">关闭</el-button>
        </div>
      </template>
      <el-form :model="tableInfo" :rules="rules" ref="tableForm" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="表名称" prop="tableName">
              <el-input v-model="tableInfo.tableName" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="表描述" prop="tableComment">
              <el-input v-model="tableInfo.tableComment" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实体类名称" prop="className">
              <el-input v-model="tableInfo.className" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="作者" prop="functionAuthor">
              <el-input v-model="tableInfo.functionAuthor" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="tableInfo.remark" type="textarea" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    
    <el-card class="box-card mt10">
      <template #header>
        <div class="card-header">
          <span>字段信息</span>
        </div>
      </template>
      <el-table :data="tableInfo.columns" style="width: 100%" :row-class-name="tableRowClassName">
        <el-table-column label="序号" type="index" width="50" align="center" />
        <el-table-column label="字段列名" prop="columnName" width="120" />
        <el-table-column label="字段描述" prop="columnComment" width="150">
          <template #default="scope">
            <el-input v-model="scope.row.columnComment" />
          </template>
        </el-table-column>
        <el-table-column label="物理类型" prop="columnType" width="120" />
        <el-table-column label="Java类型" prop="javaType" width="150">
          <template #default="scope">
            <el-select v-model="scope.row.javaType">
              <el-option label="Long" value="Long" />
              <el-option label="String" value="String" />
              <el-option label="Integer" value="Integer" />
              <el-option label="Double" value="Double" />
              <el-option label="BigDecimal" value="BigDecimal" />
              <el-option label="Date" value="Date" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="Java属性" prop="javaField" width="150">
          <template #default="scope">
            <el-input v-model="scope.row.javaField" />
          </template>
        </el-table-column>
        <el-table-column label="插入" prop="isInsert" width="80" align="center">
          <template #default="scope">
            <el-checkbox v-model="scope.row.isInsert" true-value="1" false-value="0" />
          </template>
        </el-table-column>
        <el-table-column label="编辑" prop="isEdit" width="80" align="center">
          <template #default="scope">
            <el-checkbox v-model="scope.row.isEdit" true-value="1" false-value="0" />
          </template>
        </el-table-column>
        <el-table-column label="列表" prop="isList" width="80" align="center">
          <template #default="scope">
            <el-checkbox v-model="scope.row.isList" true-value="1" false-value="0" />
          </template>
        </el-table-column>
        <el-table-column label="查询" prop="isQuery" width="80" align="center">
          <template #default="scope">
            <el-checkbox v-model="scope.row.isQuery" true-value="1" false-value="0" />
          </template>
        </el-table-column>
        <el-table-column label="查询方式" prop="queryType" width="120">
          <template #default="scope">
            <el-select v-model="scope.row.queryType">
              <el-option label="=" value="EQ" />
              <el-option label="like" value="LIKE" />
              <el-option label=">=" value="GE" />
              <el-option label="<=" value="LE" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="必填" prop="isRequired" width="80" align="center">
          <template #default="scope">
            <el-checkbox v-model="scope.row.isRequired" true-value="1" false-value="0" />
          </template>
        </el-table-column>
        <el-table-column label="显示类型" prop="htmlType" width="150">
          <template #default="scope">
            <el-select v-model="scope.row.htmlType">
              <el-option label="文本框" value="input" />
              <el-option label="文本域" value="textarea" />
              <el-option label="下拉框" value="select" />
              <el-option label="单选框" value="radio" />
              <el-option label="复选框" value="checkbox" />
              <el-option label="日期控件" value="datetime" />
              <el-option label="图片上传" value="imageUpload" />
              <el-option label="文件上传" value="fileUpload" />
              <el-option label="富文本控件" value="editor" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="字典类型" prop="dictType" width="150">
          <template #default="scope">
            <el-select v-model="scope.row.dictType" clearable>
              <el-option
                v-for="dict in dictOptions"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <el-card class="box-card mt10">
      <template #header>
        <div class="card-header">
          <span>生成信息</span>
        </div>
      </template>
      <el-form :model="tableInfo" :rules="rules" ref="genForm" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="生成模板" prop="tplCategory">
              <el-select v-model="tableInfo.tplCategory">
                <el-option label="单表（增删改查）" value="crud" />
                <el-option label="树表（增删改查）" value="tree" />
                <el-option label="主子表（增删改查）" value="sub" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生成包路径" prop="packageName">
              <el-input v-model="tableInfo.packageName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生成模块名" prop="moduleName">
              <el-input v-model="tableInfo.moduleName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生成业务名" prop="businessName">
              <el-input v-model="tableInfo.businessName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生成功能名" prop="functionName">
              <el-input v-model="tableInfo.functionName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级菜单" prop="parentMenuId">
              <el-tree-select
                v-model="tableInfo.parentMenuId"
                :data="menuOptions"
                :props="{ value: 'id', label: 'label', children: 'children' }"
                node-key="id"
                placeholder="请选择上级菜单"
                check-strictly
                filterable
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="tableInfo.tplCategory === 'tree'">
            <h4 class="form-header">其他信息</h4>
          </el-col>
          <el-col :span="12" v-if="tableInfo.tplCategory === 'tree'">
            <el-form-item label="树编码字段" prop="treeCode">
              <el-select v-model="tableInfo.treeCode">
                <el-option
                  v-for="column in tableInfo.columns"
                  :key="column.columnName"
                  :label="column.columnName + '：' + column.columnComment"
                  :value="column.columnName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="tableInfo.tplCategory === 'tree'">
            <el-form-item label="树父编码字段" prop="treeParentCode">
              <el-select v-model="tableInfo.treeParentCode">
                <el-option
                  v-for="column in tableInfo.columns"
                  :key="column.columnName"
                  :label="column.columnName + '：' + column.columnComment"
                  :value="column.columnName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="tableInfo.tplCategory === 'tree'">
            <el-form-item label="树名称字段" prop="treeName">
              <el-select v-model="tableInfo.treeName">
                <el-option
                  v-for="column in tableInfo.columns"
                  :key="column.columnName"
                  :label="column.columnName + '：' + column.columnComment"
                  :value="column.columnName"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getGenTable, updateGenTable } from '@/api/tool/gen'

// 获取路由和路由器实例
const route = useRoute()
const router = useRouter()

// 定义响应式数据
const tableInfo = reactive({
  tableId: undefined,
  tableName: '',
  tableComment: '',
  className: '',
  functionAuthor: '',
  remark: '',
  tplCategory: 'crud',
  packageName: 'com.ruoyi.project',
  moduleName: '',
  businessName: '',
  functionName: '',
  parentMenuId: '',
  treeCode: '',
  treeParentCode: '',
  treeName: '',
  columns: []
})

const dictOptions = ref([])
const menuOptions = ref([])

// 表单校验规则
const rules = {
  tableComment: [{ required: true, message: '请输入表描述', trigger: 'blur' }],
  className: [{ required: true, message: '请输入实体类名称', trigger: 'blur' }],
  functionAuthor: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  packageName: [{ required: true, message: '请输入生成包路径', trigger: 'blur' }],
  moduleName: [{ required: true, message: '请输入生成模块名', trigger: 'blur' }],
  businessName: [{ required: true, message: '请输入生成业务名', trigger: 'blur' }],
  functionName: [{ required: true, message: '请输入生成功能名', trigger: 'blur' }]
}

// 表单引用
const tableForm = ref(null)
const genForm = ref(null)

// 获取表详细信息
const getTableInfo = async (tableId) => {
  try {
    const response = await getGenTable(tableId)
    const data = response.data
    Object.assign(tableInfo, data.info)
    tableInfo.columns = data.rows
    
    // 设置默认值
    if (!tableInfo.packageName) {
      tableInfo.packageName = 'com.ruoyi.project'
    }
  } catch (error) {
    console.error(error)
  }
}

// 表格行样式
const tableRowClassName = ({ row, rowIndex }) => {
  if (row.isEdit === '1') {
    return 'warning-row'
  }
  return ''
}

// 保存按钮
const handleSubmit = () => {
  Promise.all([
    tableForm.value?.validate(),
    genForm.value?.validate()
  ]).then(async () => {
    await updateGenTable(tableInfo)
    ElMessage.success('保存成功')
    router.push('/tool/gen')
  }).catch(() => {
    ElMessage.error('请完善必填项')
  })
}

// 关闭按钮
const handleClose = () => {
  router.push('/tool/gen')
}

// 获取字典和菜单数据
const getDictsAndMenus = () => {
  // 模拟字典数据
  dictOptions.value = [
    { value: 'sys_normal_disable', label: '系统开关' },
    { value: 'sys_user_sex', label: '用户性别' }
  ]
  
  // 模拟菜单数据
  menuOptions.value = [
    {
      id: 1,
      label: '系统管理',
      children: [
        { id: 100, label: '用户管理' },
        { id: 101, label: '角色管理' }
      ]
    },
    {
      id: 2,
      label: '系统监控',
      children: [
        { id: 200, label: '操作日志' },
        { id: 201, label: '登录日志' }
      ]
    }
  ]
}

// 组件挂载时获取数据
onMounted(() => {
  const tableId = route.params && route.params.tableId
  if (tableId) {
    getTableInfo(tableId)
  }
  getDictsAndMenus()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.mt10 {
  margin-top: 10px;
}

.form-header {
  font-size: 15px;
  color: #6379bb;
  border-bottom: 1px solid #ddd;
  margin: 8px 10px 25px 10px;
  padding-bottom: 5px;
}

.warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
</style>