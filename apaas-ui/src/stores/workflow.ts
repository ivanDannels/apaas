import { defineStore } from 'pinia'

// 流程定义类型定义
export interface ProcessDefinition {
  id: string
  name: string
  key: string
  version: number
  status: number
  createTime: string
  updateTime: string
}

// 流程实例类型定义
export interface ProcessInstance {
  id: string
  processDefinitionId: string
  processDefinitionKey: string
  processDefinitionName: string
  businessKey: string
  status: number
  startTime: string
  endTime: string
  duration: number
}

// 任务类型定义
export interface Task {
  id: string
  name: string
  processInstanceId: string
  processDefinitionId: string
  assignee: string
  status: number
  createTime: string
  endTime: string
}

export const useWorkflowStore = defineStore('workflow', {
  state: () => ({
    // 流程定义相关
    processDefinitions: [] as ProcessDefinition[],
    currentProcessDefinition: null as ProcessDefinition | null,
    
    // 流程实例相关
    processInstances: [] as ProcessInstance[],
    currentProcessInstance: null as ProcessInstance | null,
    
    // 任务相关
    tasks: [] as Task[],
    currentTask: null as Task | null
  }),
  
  actions: {
    // 流程定义相关操作
    setProcessDefinitions(definitions: ProcessDefinition[]) {
      this.processDefinitions = definitions
    },
    
    setCurrentProcessDefinition(definition: ProcessDefinition) {
      this.currentProcessDefinition = definition
    },
    
    // 流程实例相关操作
    setProcessInstances(instances: ProcessInstance[]) {
      this.processInstances = instances
    },
    
    setCurrentProcessInstance(instance: ProcessInstance) {
      this.currentProcessInstance = instance
    },
    
    // 任务相关操作
    setTasks(tasks: Task[]) {
      this.tasks = tasks
    },
    
    setCurrentTask(task: Task) {
      this.currentTask = task
    },
    
    // 重置状态
    reset() {
      this.processDefinitions = []
      this.currentProcessDefinition = null
      this.processInstances = []
      this.currentProcessInstance = null
      this.tasks = []
      this.currentTask = null
    }
  }
})