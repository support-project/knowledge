import api from '../../api'
import logger from 'logger'

const LABEL = 'getDrafts.js'

export default (state) => {
  logger.debug(LABEL, 'get drafts from api')
  return api.request('get', '/_api/drafts', null)
  .then(response => {
    logger.debug(LABEL, response.data)
    var drafts = response.data
    state.commit('SET_RESOURCES', {
      drafts: drafts
    })
  })
}
