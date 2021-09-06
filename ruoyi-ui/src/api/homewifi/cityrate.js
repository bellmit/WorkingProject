import request from '@/utils/request'

// 查询分地市四率统计列表
export function listCityrate(query) {
  return request({
    url: '/homewifi/cityrate/list',
    method: 'get',
    params: query
  })
}

// 查询分地市四率统计详细
export function getCityrate(id) {
  return request({
    url: '/homewifi/cityrate/' + id,
    method: 'get'
  })
}

// 新增分地市四率统计
export function addCityrate(data) {
  return request({
    url: '/homewifi/cityrate',
    method: 'post',
    data: data
  })
}

// 修改分地市四率统计
export function updateCityrate(data) {
  return request({
    url: '/homewifi/cityrate',
    method: 'put',
    data: data
  })
}

// 删除分地市四率统计
export function delCityrate(id) {
  return request({
    url: '/homewifi/cityrate/' + id,
    method: 'delete'
  })
}

// 导出分地市四率统计
export function exportCityrate(query) {
  return request({
    url: '/homewifi/cityrate/export',
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
