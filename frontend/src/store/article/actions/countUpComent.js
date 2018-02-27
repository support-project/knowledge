import Promise from 'bluebird'
import logger from 'logger'
const LABEL = 'countUpComent.js'

export default (store) => {
  logger.debug(LABEL, 'countUpComent')
  return Promise.try(() => {
    store.state.article.commentCount++
  })
}
