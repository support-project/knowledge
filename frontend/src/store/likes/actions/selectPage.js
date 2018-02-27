import Promise from 'bluebird'
import logger from 'logger'
const LABEL = 'selectPage.js'

export default (store, offset) => {
  logger.debug(LABEL, 'selectPage:' + offset)
  if (offset === -1) {
    return
  }
  store.state.pagination.offset = offset
  return Promise.try(() => {
    return store.dispatch('getLikes', store.state.articleId)
  }).finally(() => {
  })
}
