import request from './request'

export function getScoreList(params) {
  return request({
    url: '/scores',
    method: 'get',
    params
  })
}

export function getScoreById(id) {
  return request({
    url: `/scores/${id}`,
    method: 'get'
  })
}

export function createScore(data) {
  return request({
    url: '/scores',
    method: 'post',
    data
  })
}

export function updateScore(data) {
  return request({
    url: `/scores/${data.id}`,
    method: 'put',
    data
  })
}

export function deleteScore(id) {
  return request({
    url: `/scores/${id}`,
    method: 'delete'
  })
}
