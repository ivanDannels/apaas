<template>
  <div class="form-designer">
    <!-- 左侧组件列表 -->
    <div class="components-panel">
      <div class="panel-header">组件列表</div>
      <div class="panel-body">
        <div 
          v-for="component in componentList" 
          :key="component.type" 
          class="component-item"
          draggable="true"
          @dragstart="handleDragStart($event, component)"
        >
          <el-icon><component :is="component.icon" /></el-icon>
          <span>{{ component.label }}</span>
        </div>
      </div>
    </div>
    
    <!-- 中间设计区域 -->
    <div class="design-panel">
      <div 
        class="design-canvas" 
        @dragover="handleDragOver"
        @drop="handleDrop"
        @click="handleCanvasClick"
      >
        <div 
          v-for="(field, index) in formFields" 
          :key="field.key" 
          class="form-field"
          :class="{ active: activeField?.key === field.key }"
          @click.stop="handleFieldClick(field, index)"
        >
          <div class="field-label">
            {{ field.label }}
            <span v-if="field.required" class="required">*</span>
          </div>
          <div class="field-content">
            <component 
              :is="getFieldComponent(field.type)" 
              :field="field"
              :model-value="formData[field.key]"
              @update:model-value="handleFieldChange(field.key, $event)"
            />
          </div>
          <div class="field-actions">
            <el-button 
              type="primary" 
              link 
              size="small" 
              @click.stop="moveUp(index)"
              :disabled="index === 0"
            >
              <el-icon><ArrowUp /></el-icon>
            </el-button>
            <el-button 
              type="primary" 
              link 
              size="small" 
              @click.stop="moveDown(index)"
              :disabled="index === formFields.length - 1"
            >
              <el-icon><ArrowDown /></el-icon>
            </el-button>
            <el-button 
              type="danger" 
              link 
              size="small" 
              @click.stop="removeField(index)"
            >
              <el-icon><Delete /></el-icon>
            </el-button>
          </div>
        </div>
        
        <!-- 空状态 -->
        <div v-if="formFields.length === 0" class="empty-state">
          <el-icon><Document /></el-icon>
          <p>从左侧拖拽组件到此处</p>
        </div>
      </div>
    </div>
    
    <!-- 右侧属性配置面板 -->
    <div class="properties-panel">
      <div class="panel-header">属性配置</div>
      <div class="panel-body">
        <el-form 
          v-if="activeField" 
          label-position="top" 
          :model="activeField"
          @submit.prevent
        >
          <el-form-item label="字段标识">
            <el-input v-model="activeField.key" />
          </el-form-item>
          <el-form-item label="字段名称">
            <el-input v-model="activeField.label" />
          </el-form-item>
          <el-form-item label="是否必填">
            <el-switch v-model="activeField.required" />
          </el-form-item>
          
          <!-- 根据字段类型显示不同的配置项 -->
          <template v-if="activeField.type === 'input'">
            <el-form-item label="占位符">
              <el-input v-model="activeField.placeholder" />
            </el-form-item>
            <el-form-item label="输入类型">
              <el-select v-model="activeField.inputType">
                <el-option label="文本" value="text" />
                <el-option label="密码" value="password" />
                <el-option label="数字" value="number" />
              </el-select>
            </el-form-item>
          </template>
          
          <template v-else-if="activeField.type === 'select'">
            <el-form-item label="选项">
              <el-button @click="addOption">添加选项</el-button>
              <div 
                v-for="(option, index) in activeField.options" 
                :key="index" 
                class="option-item"
              >
                <el-input 
                  v-model="option.label" 
                  placeholder="选项标签" 
                  style="width: 40%"
                />
                <el-input 
                  v-model="option.value" 
                  placeholder="选项值" 
                  style="width: 40%; margin-left: 10px"
                />
                <el-button 
                  type="danger" 
                  link 
                  @click="removeOption(index)"
                  style="margin-left: 10px"
                >
                  <el-icon><Delete /></el-icon>
                </el-button>
              </div>
            </el-form-item>
          </template>
          
          <template v-else-if="activeField.type === 'radio' || activeField.type === 'checkbox'">
            <el-form-item label="选项">
              <el-button @click="addOption">添加选项</el-button>
              <div 
                v-for="(option, index) in activeField.options" 
                :key="index" 
                class="option-item"
              >
                <el-input 
                  v-model="option.label" 
                  placeholder="选项标签" 
                  style="width: 40%"
                />
                <el-input 
                  v-model="option.value" 
                  placeholder="选项值" 
                  style="width: 40%; margin-left: 10px"
                />
                <el-button 
                  type="danger" 
                  link 
                  @click="removeOption(index)"
                  style="margin-left: 10px"
                >
                  <el-icon><Delete /></el-icon>
                </el-button>
              </div>
            </el-form-item>
          </template>
        </el-form>
        
        <div v-else class="no-selection">
          <p>请选择一个字段进行配置</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { 
  Document, 
  Delete, 
  ArrowUp, 
  ArrowDown,
  Promotion,
  Tickets,
  DocumentCopy,
  Collection
} from '@element-plus/icons-vue'

// 定义属性
const props = defineProps({
  modelValue: {
    type: Array,
    default: () => []
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue'])

// 定义响应式数据
const formFields = ref([...props.modelValue])
const activeField = ref(null)
const formData = reactive({})

// 组件列表
const componentList = [
  { type: 'input', label: '输入框', icon: 'Promotion' },
  { type: 'select', label: '下拉选择', icon: 'DocumentCopy' },
  { type: 'radio', label: '单选框', icon: 'Collection' },
  { type: 'checkbox', label: '多选框', icon: 'Tickets' }
]

// 字段组件映射
const getFieldComponent = (type) => {
  switch (type) {
    case 'input':
      return 'InputField'
    case 'select':
      return 'SelectField'
    case 'radio':
      return 'RadioField'
    case 'checkbox':
      return 'CheckboxField'
    default:
      return 'InputField'
  }
}

// 处理拖拽开始
const handleDragStart = (e, component) => {
  e.dataTransfer.setData('component', JSON.stringify(component))
}

// 处理拖拽过程
const handleDragOver = (e) => {
  e.preventDefault()
}

// 处理放置
const handleDrop = (e) => {
  e.preventDefault()
  const componentData = e.dataTransfer.getData('component')
  if (componentData) {
    const component = JSON.parse(componentData)
    addField(component)
  }
}

// 添加字段
const addField = (component) => {
  const field = {
    key: `${component.type}_${Date.now()}`,
    type: component.type,
    label: component.label,
    required: false
  }
  
  // 根据类型设置默认属性
  switch (component.type) {
    case 'input':
      field.placeholder = ''
      field.inputType = 'text'
      break
    case 'select':
    case 'radio':
    case 'checkbox':
      field.options = [
        { label: '选项1', value: 'option1' },
        { label: '选项2', value: 'option2' }
      ]
      break
  }
  
  formFields.value.push(field)
  emit('update:modelValue', formFields.value)
}

// 处理画布点击
const handleCanvasClick = () => {
  activeField.value = null
}

// 处理字段点击
const handleFieldClick = (field) => {
  activeField.value = field
}

// 处理字段值变化
const handleFieldChange = (key, value) => {
  formData[key] = value
}

// 上移字段
const moveUp = (index) => {
  if (index > 0) {
    const temp = formFields.value[index]
    formFields.value[index] = formFields.value[index - 1]
    formFields.value[index - 1] = temp
    emit('update:modelValue', formFields.value)
  }
}

// 下移字段
const moveDown = (index) => {
  if (index < formFields.value.length - 1) {
    const temp = formFields.value[index]
    formFields.value[index] = formFields.value[index + 1]
    formFields.value[index + 1] = temp
    emit('update:modelValue', formFields.value)
  }
}

// 删除字段
const removeField = (index) => {
  formFields.value.splice(index, 1)
  if (activeField.value && activeField.value.key === formFields.value[index]?.key) {
    activeField.value = null
  }
  emit('update:modelValue', formFields.value)
}

// 添加选项
const addOption = () => {
  if (activeField.value) {
    if (!activeField.value.options) {
      activeField.value.options = []
    }
    activeField.value.options.push({
      label: '',
      value: ''
    })
  }
}

// 删除选项
const removeOption = (index) => {
  if (activeField.value && activeField.value.options) {
    activeField.value.options.splice(index, 1)
  }
}

// 定义字段组件
const InputField = {
  props: ['field', 'modelValue'],
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const handleChange = (value) => {
      emit('update:modelValue', value)
    }
    return {
      handleChange
    }
  },
  template: `
    <el-input
      :model-value="modelValue"
      :placeholder="field.placeholder"
      :type="field.inputType"
      @input="handleChange"
    />
  `
}

const SelectField = {
  props: ['field', 'modelValue'],
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const handleChange = (value) => {
      emit('update:modelValue', value)
    }
    return {
      handleChange
    }
  },
  template: `
    <el-select
      :model-value="modelValue"
      @change="handleChange"
    >
      <el-option
        v-for="option in field.options"
        :key="option.value"
        :label="option.label"
        :value="option.value"
      />
    </el-select>
  `
}

const RadioField = {
  props: ['field', 'modelValue'],
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const handleChange = (value) => {
      emit('update:modelValue', value)
    }
    return {
      handleChange
    }
  },
  template: `
    <el-radio-group
      :model-value="modelValue"
      @change="handleChange"
    >
      <el-radio
        v-for="option in field.options"
        :key="option.value"
        :label="option.value"
      >
        {{ option.label }}
      </el-radio>
    </el-radio-group>
  `
}

const CheckboxField = {
  props: ['field', 'modelValue'],
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const handleChange = (value) => {
      emit('update:modelValue', value)
    }
    return {
      handleChange
    }
  },
  template: `
    <el-checkbox-group
      :model-value="modelValue"
      @change="handleChange"
    >
      <el-checkbox
        v-for="option in field.options"
        :key="option.value"
        :label="option.value"
      >
        {{ option.label }}
      </el-checkbox>
    </el-checkbox-group>
  `
}
</script>

<style lang="scss" scoped>
.form-designer {
  display: flex;
  height: 100%;
  
  .components-panel,
  .properties-panel {
    width: 250px;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    display: flex;
    flex-direction: column;
    
    .panel-header {
      padding: 10px;
      border-bottom: 1px solid #dcdfe6;
      font-weight: bold;
      background-color: #f5f7fa;
    }
    
    .panel-body {
      flex: 1;
      padding: 10px;
      overflow-y: auto;
    }
  }
  
  .design-panel {
    flex: 1;
    padding: 0 10px;
    
    .design-canvas {
      height: 100%;
      border: 1px dashed #dcdfe6;
      border-radius: 4px;
      padding: 20px;
      position: relative;
      min-height: 400px;
      
      .form-field {
        display: flex;
        align-items: center;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        background-color: #fff;
        
        &.active {
          border-color: #409eff;
          box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
        }
        
        .field-label {
          width: 120px;
          font-weight: bold;
          
          .required {
            color: #f56c6c;
          }
        }
        
        .field-content {
          flex: 1;
          margin: 0 10px;
        }
        
        .field-actions {
          display: flex;
          gap: 5px;
        }
      }
      
      .empty-state {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        text-align: center;
        color: #909399;
        
        .el-icon {
          font-size: 48px;
          margin-bottom: 10px;
        }
      }
    }
  }
  
  .component-item {
    display: flex;
    align-items: center;
    padding: 10px;
    margin-bottom: 5px;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    cursor: move;
    background-color: #fff;
    
    &:hover {
      border-color: #409eff;
      background-color: #ecf5ff;
    }
    
    .el-icon {
      margin-right: 8px;
      color: #409eff;
    }
  }
  
  .option-item {
    display: flex;
    align-items: center;
    margin-top: 10px;
  }
  
  .no-selection {
    text-align: center;
    color: #909399;
    margin-top: 50px;
  }
}
</style>