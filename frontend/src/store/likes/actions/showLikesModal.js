/* global $ */
import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'showLikes.js'

export default (store, id) => {
  logger.debug(LABEL, 'showLikes')
  store.state.articleId = id
  return Promise.try(() => {
    return store.dispatch('getLikes')
  }).then(() => {
    $('#show-liked-users-dialog').modal('show')
  }).finally(() => {
  })
}
