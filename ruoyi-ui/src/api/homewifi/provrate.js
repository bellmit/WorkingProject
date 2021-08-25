import request from '@/utils/request'

// 查询分省份四率统计列表
export function listProvrate(query) {
  return request({
    url: '/homewifi/provrate/list',
    method: 'get',
    params: query
  })
}

// 查询分省份四率统计详细
export function getProvrate(id) {
  return request({
    url: '/homewifi/provrate/' + id,
    method: 'get'
  })
}

// 新增分省份四率统计
export function addProvrate(data) {
  return request({
    url: '/homewifi/provrate',
    method: 'post',
    data: data
  })
}

// 修改分省份四率统计
export function updateProvrate(data) {
  return request({
    url: '/homewifi/provrate',
    method: 'put',
    data: data
  })
}

// 删除分省份四率统计
export function delProvrate(id) {
  return request({
    url: '/homewifi/provrate/' + id,
    method: 'delete'
  })
}

// 导出分省份四率统计
export function exportProvrate(query) {
  return request({
    url: '/homewifi/provrate/export',
    method: 'get',
    params: query
  })
}