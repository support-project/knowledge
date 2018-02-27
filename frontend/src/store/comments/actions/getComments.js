import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

import setIcons from '../../../lib/utils/setIcons'

import processDecorateAll from '../../../lib/decorateMarkdown/processDecorateAll'

const LABEL = 'getComments.js'

export default (store, id) => {
  logger.debug(LABEL, 'start getComments')
  store.commit('setComments', [])
  return Promise.try(() => {
    return api.request('get', '/_api/articles/' + id + '/comments', null)
  }).then(function (response) {
    logger.debug(LABEL, response.data)
    return Promise.each(response.data, function (comment) {
      return processDecorateAll(comment.comment).then(function (result) {
        comment.displaySafeHtml = result
        setIcons(store, comment)
      })
    })
  }).then(function (comments) {
    store.commit('setComments', comments)
  })
}
