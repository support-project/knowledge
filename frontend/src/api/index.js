import Promise from 'bluebird'
import axios from 'axios'
import logger from 'logger'
const LABEL = 'api.js'
var serverURI = ''

import store from '../store'

export default {
  setServerURI (uri) {
    serverURI = uri
  },
  request (method, uri, data = null) {
    if (!method) {
      logger.error(LABEL, 'API function call requires method argument')
      return
    }
    if (!uri) {
      logger.error(LABEL, 'API function call requires uri argument')
      return
    }
    var headers = {}
    var token = store.getters.GET_TOKEN
    if (token) {
      headers['PRIVATE-TOKEN'] = token
    }
    if (method.toLowerCase() !== 'get') {
      var reqToken = store.getters.GET_REQUEST_TOKEN
      if (reqToken) {
        headers['request-token'] = reqToken
      }
    }
    var url = serverURI + uri
    logger.debug(LABEL, {
      method: method,
      url: url,
      headers: headers,
      data: data
    })
    // store.commit('SET_PAGE_STATE', {loading: true})
    logger.debug(LABEL, 'start request')
    return Promise.try(() => {
      return axios({ method, url, data, headers }).then(response => {
        // レスポンスヘッダーに「REQ_TOKEN」があれば、それを保持
        logger.debug(LABEL, JSON.stringify(response, null, '  '))
        if (response.headers['request-token']) {
          store.commit('SET_REQUEST_TOKEN', response.headers['request-token'])
        }
        return response
      })
    }).finally(() => {
      // store.commit('SET_PAGE_STATE', {loading: false})
      logger.debug(LABEL, 'finish request')
    })
  }
}
