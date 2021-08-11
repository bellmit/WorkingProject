import request from '@/utils/request'

// 查询数据湖工单下发列表
export function listInstallationlisti(query) {
  return request({
    url: '/homewifi/installationlisti/list',
    method: 'get',
    params: query
  })
}

// 查询数据湖工单下发详细
export function getInstallationlisti(id) {
  return request({
    url: '/homewifi/installationlisti/' + id,
    method: 'get'
  })
}

// 新增数据湖工单下发
export function addInstallationlisti(data) {
  return request({
    url: '/homewifi/installationlisti',
    method: 'post',
    data: data
  })
}

// 修改数据湖工单下发
export function updateInstallationlisti(data) {
  return request({
    url: '/homewifi/installationlisti',
    method: 'put',
    data: data
  })
}

// 删除数据湖工单下发
export function delInstallationlisti(id) {
  return request({
    url: '/homewifi/installationlisti/' + id,
    method: 'delete'
  })
}

// 导出数据湖工单下发
export function exportInstallationlisti(query) {
  return request({
    url: '/homewifi/installationlisti/export',
    method: 'get',
    params: query
  })
}