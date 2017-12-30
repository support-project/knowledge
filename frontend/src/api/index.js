import axios from 'axios'
import logger from 'logger'
const LABEL = 'api.js'
var serverURI = ''

export default {
  setServerURI (uri) {
    serverURI = uri
  },
  request (method, uri, data = null, token) {
    if (!method) {
      console.error('API function call requires method argument')
      return
    }

    if (!uri) {
      console.error('API function call requires uri argument')
      return
    }

    var headers = {}
    if (token) {
      headers = {'PRIVATE-TOKEN': token}
    }

    var url = serverURI + uri
    logger.debug(LABEL, {
      method: method,
      url: url,
      data: data
    })
    return axios({ method, url, data, headers })
  }
}
