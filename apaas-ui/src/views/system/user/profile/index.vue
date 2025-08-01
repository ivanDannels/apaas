<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>个人信息</span>
            </div>
          </template>
          <div class="text-center">
            <userAvatar :user="user" />
          </div>
          <ul class="list-group list-group-striped">
            <li class="list-group-item">
              <svg-icon icon-class="user" />
              用户名称
              <div class="pull-right">{{ user.userName }}</div>
            </li>
            <li class="list-group-item">
              <svg-icon icon-class="phone" />
              手机号码
              <div class="pull-right">{{ user.phonenumber }}</div>
            </li>
            <li class="list-group-item">
              <svg-icon icon-class="email" />
              用户邮箱
              <div class="pull-right">{{ user.email }}</div>
            </li>
            <li class="list-group-item">
              <svg-icon icon-class="tree" />
              所属部门
              <div class="pull-right" v-if="user.dept">{{ user.dept.deptName }}</div>
            </li>
            <li class="list-group-item">
              <svg-icon icon-class="peoples" />
              所属角色
              <div class="pull-right">{{ roleName }}</div>
            </li>
            <li class="list-group-item">
              <svg-icon icon-class="date" />
              创建日期
              <div class="pull-right">{{ user.createTime }}</div>
            </li>
          </ul>
        </el-card>
      </el-col>
      
      <el-col :span="18" :xs="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>基本资料</span>
            </div>
          </template>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd />
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import userAvatar from './userAvatar.vue'
import userInfo from './userInfo.vue'
import resetPwd from './resetPwd.vue'
import { getUserProfile } from '@/api/system/user'

// 定义响应式数据
const user = ref({})
const roleName = ref('')
const activeTab = ref('userinfo')

// 获取用户信息
const getUser = async () => {
  try {
    const response = await getUserProfile()
    user.value = response.data
    roleName.value = response.roleGroup
  } catch (error) {
    console.error(error)
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getUser()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text-center {
  text-align: center;
  margin-bottom: 20px;
}

.list-group {
  padding-left: 0;
  margin-bottom: 20px;
}

.list-group-item {
  border-bottom: 1px solid #e7eaec;
  border-top: 1px solid #e7eaec;
  margin-bottom: -1px;
  padding: 11px 0;
  font-size: 13px;
}

.pull-right {
  float: right;
}

.list-group-item:last-child {
  border-bottom: none;
}

.list-group-item svg {
  margin-right: 5px;
}
</style>