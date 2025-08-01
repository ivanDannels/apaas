<template>
  <el-drawer
    ref="drawerRef"
    v-model="drawerVisible"
    :title="title"
    :direction="direction"
    :size="size"
    :with-header="withHeader"
    :modal="modal"
    :lock-scroll="lockScroll"
    :open-delay="openDelay"
    :close-delay="closeDelay"
    :close-on-click-modal="closeOnClickModal"
    :close-on-press-escape="closeOnPressEscape"
    :show-close="showClose"
    :before-close="beforeClose"
    :destroy-on-close="destroyOnClose"
    :modal-class="modalClass"
    v-bind="$attrs"
    @open="handleOpen"
    @opened="handleOpened"
    @close="handleClose"
    @closed="handleClosed"
  >
    <!-- 抽屉内容 -->
    <div class="drawer-content">
      <slot></slot>
    </div>
    
    <!-- 抽屉底部 -->
    <template #footer v-if="showFooter || $slots.footer">
      <slot name="footer">
        <div class="drawer-footer">
          <el-button @click="cancel" v-if="showCancel">{{ cancelText }}</el-button>
          <el-button 
            type="primary" 
            @click="confirm" 
            :loading="confirmLoading" 
            v-if="showConfirm"
          >
            {{ confirmText }}
          </el-button>
        </div>
      </slot>
    </template>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'

// 定义属性
const props = defineProps({
  // 是否显示抽屉
  modelValue: {
    type: Boolean,
    default: false
  },
  // 抽屉标题
  title: {
    type: String,
    default: '提示'
  },
  // 抽屉打开的方向
  direction: {
    type: String,
    default: 'rtl', // rtl / ltr / ttb / btt
    validator: (value) => ['rtl', 'ltr', 'ttb', 'btt'].includes(value)
  },
  // 抽屉尺寸
  size: {
    type: [String, Number],
    default: '30%'
  },
  // 是否显示 header
  withHeader: {
    type: Boolean,
    default: true
  },
  // 是否需要遮罩层
  modal: {
    type: Boolean,
    default: true
  },
  // 是否在 Drawer 出现时将 body 滚动锁定
  lockScroll: {
    type: Boolean,
    default: true
  },
  // Drawer 打开的延时时间，单位毫秒
  openDelay: {
    type: Number,
    default: 0
  },
  // Drawer 关闭的延时时间，单位毫秒
  closeDelay: {
    type: Number,
    default: 0
  },
  // 是否可以通过点击 modal 关闭 Drawer
  closeOnClickModal: {
    type: Boolean,
    default: true
  },
  // 是否可以通过按下 ESC 关闭 Drawer
  closeOnPressEscape: {
    type: Boolean,
    default: true
  },
  // 是否显示关闭按钮
  showClose: {
    type: Boolean,
    default: true
  },
  // 关闭前的回调，会暂停 Drawer 的关闭
  beforeClose: {
    type: Function,
    default: null
  },
  // 关闭时销毁 Drawer 中的元素
  destroyOnClose: {
    type: Boolean,
    default: false
  },
  // 遮罩层的自定义类名
  modalClass: {
    type: String,
    default: ''
  },
  // 是否显示底部
  showFooter: {
    type: Boolean,
    default: true
  },
  // 是否显示取消按钮
  showCancel: {
    type: Boolean,
    default: true
  },
  // 是否显示确认按钮
  showConfirm: {
    type: Boolean,
    default: true
  },
  // 取消按钮文字
  cancelText: {
    type: String,
    default: '取消'
  },
  // 确认按钮文字
  confirmText: {
    type: String,
    default: '确定'
  },
  // 确认按钮是否加载中
  confirmLoading: {
    type: Boolean,
    default: false
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue', 'open', 'opened', 'close', 'closed', 'confirm', 'cancel'])

// 定义响应式数据
const drawerRef = ref(null)
const drawerVisible = ref(props.modelValue)

// 监听modelValue变化
watch(
  () => props.modelValue,
  (val) => {
    drawerVisible.value = val
  }
)

// 监听drawerVisible变化
watch(
  () => drawerVisible.value,
  (val) => {
    emit('update:modelValue', val)
  }
)

// 处理打开事件
const handleOpen = () => {
  emit('open')
}

// 处理打开完成事件
const handleOpened = () => {
  emit('opened')
}

// 处理关闭事件
const handleClose = () => {
  // 如果有beforeClose回调，则调用
  if (props.beforeClose) {
    props.beforeClose(done)
  } else {
    done()
  }
}

// 完成关闭
const done = () => {
  drawerVisible.value = false
  emit('close')
}

// 处理关闭完成事件
const handleClosed = () => {
  emit('closed')
}

// 确认
const confirm = () => {
  emit('confirm')
}

// 取消
const cancel = () => {
  drawerVisible.value = false
  emit('cancel')
}

// 定义暴露给父组件的方法
const open = () => {
  drawerVisible.value = true
}

const close = () => {
  handleClose()
}

defineExpose({
  open,
  close
})
</script>

<style lang="scss" scoped>
.drawer-content {
  padding: 20px;
  height: 100%;
  box-sizing: border-box;
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 10px;
  padding: 0 20px 20px;
  
  .el-button {
    min-width: 80px;
  }
}
</style>