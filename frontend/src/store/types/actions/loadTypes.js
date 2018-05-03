import api from '../../../api'
import logger from 'logger'

const LABEL = 'loadTypes.js'

export default (store) => {
  logger.debug(LABEL, 'get types from api')
  return api.request('get', '/_api/types', null)
  .then(response => {
    logger.debug(LABEL, response.data)
    var types = response.data
    store.commit('setTypes', types)
    return types
  })
  .catch(error => {
    logger.error(LABEL, error)
  })
}
