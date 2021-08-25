import request from '@/utils/request'

// 查询竣工报告查询列表
export function listReport(query) {
  return request({
    url: '/homewifi/report/list',
    method: 'get',
    params: query
  })
}

// 查询省份
export function getProv() {
  return request({
    url: '/homewifi/report/provlist',
    method: 'get',
  })
}

// 查询地市
export function getCity(prov) {
  return request({
    url: '/homewifi/report/citylist',
    method: 'get',
    params:{
      provId: prov
    }
  })
}

// 查询竣工报告查询详细
export function getReport(id) {
  return request({
    url: '/homewifi/report/' + id,
    method: 'get'
  })
}

// 新增竣工报告查询
export function addReport(data) {
  return request({
    url: '/homewifi/report',
    method: 'post',
    data: data
  })
}

// 修改竣工报告查询
export function updateReport(data) {
  return request({
    url: '/homewifi/report',
    method: 'put',
    data: data
  })
}

// 删除竣工报告查询
export function delReport(id) {
  return request({
    url: '/homewifi/report/' + id,
    method: 'delete'
  })
}

// 导出竣工报告查询
export function exportReport(query) {
  return request({
    url: '/homewifi/report/export',
    method: 'get',
    params: query
  })
}
