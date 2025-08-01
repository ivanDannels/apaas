// 本地存储封装

// 存储数据
export function setItem(key: string, value: any): void {
  if (typeof value === 'object') {
    value = JSON.stringify(value)
  }
  localStorage.setItem(key, value)
}

// 获取数据
export function getItem(key: string): any {
  const data = localStorage.getItem(key)
  try {
    return JSON.parse(data)
  } catch (err) {
    return data
  }
}

// 删除数据
export function removeItem(key: string): void {
  localStorage.removeItem(key)
}

// 清空所有数据
export function clear(): void {
  localStorage.clear()
}

// sessionStorage 封装

// 存储数据
export function setSessionItem(key: string, value: any): void {
  if (typeof value === 'object') {
    value = JSON.stringify(value)
  }
  sessionStorage.setItem(key, value)
}

// 获取数据
export function getSessionItem(key: string): any {
  const data = sessionStorage.getItem(key)
  try {
    return JSON.parse(data)
  } catch (err) {
    return data
  }
}

// 删除数据
export function removeSessionItem(key: string): void {
  sessionStorage.removeItem(key)
}

// 清空所有数据
export function clearSession(): void {
  sessionStorage.clear()
}