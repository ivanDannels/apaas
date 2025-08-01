<template>
  <div class="dict-tag">
    <template v-for="item in options">
      <el-tag
        v-if="values.includes(item.value)"
        :key="item.value"
        :type="item.elTagType || 'primary'"
        :effect="item.elTagEffect || 'light'"
        :disable-transitions="false"
      >
        {{ item.label }}
      </el-tag>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

// 定义属性
const props = defineProps({
  // 数据选项
  options: {
    type: Array,
    default: () => []
  },
  // 值
  value: [String, Number],
  // 多个值
  values: {
    type: Array,
    default: () => []
  }
})

// 计算属性，处理单个值的情况
const values = computed(() => {
  if (props.value !== undefined && props.value !== null) {
    return [props.value]
  }
  return props.values || []
})
</script>

<style scoped>
.dict-tag {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

.dict-tag :deep(.el-tag) {
  margin-right: 0;
}
</style>