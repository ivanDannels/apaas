import request from '@/utils/request'
import { ResponseData } from '@/utils/request'

// 流程定义相关类型定义
export interface ProcessDefinition {
  id: string
  name: string
  key: string
  version: number
  status: number
  createTime: string
  updateTime: string
}

export interface ProcessDefinitionQuery {
  name?: string
  key?: string
  status?: number
  page: number
  size: number
}

export interface ProcessDefinitionForm {
  id?: string
  name: string
  key: string
  status: number
}

// 流程实例相关类型定义
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

export interface ProcessInstanceQuery {
  processDefinitionKey?: string
  businessKey?: string
  status?: number
  page: number
  size: number
}

export interface StartProcessInstanceData {
  processDefinitionKey: string
  businessKey?: string
  variables?: Record<string, any>
}

// 任务相关类型定义
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

export interface TaskQuery {
  processInstanceId?: string
  assignee?: string
  status?: number
  page: number
  size: number
}

export interface CompleteTaskData {
  variables?: Record<string, any>
}

export interface AssignTaskData {
  assignee: string
}

// 流程定义相关API
export function getProcessDefinitions(params: ProcessDefinitionQuery): Promise<ResponseData<ProcessDefinition[]>> {
  return request({
    url: '/flow/engine/definitions',
    method: 'get',
    params
  })
}

export function getProcessDefinitionById(id: string): Promise<ResponseData<ProcessDefinition>> {
  return request({
    url: `/flow/engine/definitions/${id}`,
    method: 'get'
  })
}

export function createProcessDefinition(data: ProcessDefinitionForm): Promise<ResponseData<null>> {
  return request({
    url: '/flow/engine/definitions',
    method: 'post',
    data
  })
}

export function updateProcessDefinition(id: string, data: ProcessDefinitionForm): Promise<ResponseData<null>> {
  return request({
    url: `/flow/engine/definitions/${id}`,
    method: 'put',
    data
  })
}

export function deleteProcessDefinition(id: string): Promise<ResponseData<null>> {
  return request({
    url: `/flow/engine/definitions/${id}`,
    method: 'delete'
  })
}

// 流程实例相关API
export function getProcessInstances(params: ProcessInstanceQuery): Promise<ResponseData<ProcessInstance[]>> {
  return request({
    url: '/flow/execution/instances',
    method: 'get',
    params
  })
}

export function getProcessInstanceById(id: string): Promise<ResponseData<ProcessInstance>> {
  return request({
    url: `/flow/execution/instances/${id}`,
    method: 'get'
  })
}

export function startProcessInstance(data: StartProcessInstanceData): Promise<ResponseData<ProcessInstance>> {
  return request({
    url: '/flow/execution/instances/start',
    method: 'post',
    data
  })
}

// 任务相关API
export function getTasks(params: TaskQuery): Promise<ResponseData<Task[]>> {
  return request({
    url: '/flow/execution/tasks',
    method: 'get',
    params
  })
}

export function getTaskById(id: string): Promise<ResponseData<Task>> {
  return request({
    url: `/flow/execution/tasks/${id}`,
    method: 'get'
  })
}

export function completeTask(id: string, data: CompleteTaskData): Promise<ResponseData<null>> {
  return request({
    url: `/flow/execution/tasks/${id}/complete`,
    method: 'post',
    data
  })
}

export function assignTask(id: string, assignee: string): Promise<ResponseData<null>> {
  return request({
    url: `/flow/execution/tasks/${id}/assign`,
    method: 'post',
    data: { assignee }
  })
}