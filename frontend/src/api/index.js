import axios from 'axios'
import config from '../config'

export default {
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

    var url = config.serverURI + uri
    return axios({ method, url, data, headers })
  }
}
