import Promise from 'bluebird'
import logger from 'logger'
const LABEL = 'countUpComent.js'

export default (store) => {
  logger.info(LABEL, 'countUpComent')
  return Promise.try(() => {
    store.state.article.commentCount++
  })
}
