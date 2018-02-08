import api from '../../../api'
import logger from 'logger'

const LABEL = 'getDrafts.js'

export default (store) => {
  logger.debug(LABEL, 'get drafts from api')
  return api.request('get', '/_api/drafts', null)
  .then(response => {
    logger.debug(LABEL, response.data)
    var drafts = response.data
    drafts.forEach(draft => {
      if (draft.knowledgeId) {
        draft.editPage = '/articles/' + draft.knowledgeId + '/edit'
      } else {
        draft.editPage = '/drafts/' + draft.draftId + '/edit'
      }
    })
    store.commit('setDrafts', drafts)
  })
}
