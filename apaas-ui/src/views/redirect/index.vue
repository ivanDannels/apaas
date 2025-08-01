<template>
  <div />
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const { path } = route.params

router.replace({ path: '/' + path, query: route.query })
</script>

<style scoped>

</style>