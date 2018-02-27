import Promise from 'bluebird'
import logger from 'logger'
const LABEL = 'removeTag.js'

export default (store, tag) => {
  return Promise.try(() => {
    logger.debug(LABEL, 'removeTag')
    let array = store.state.article.tags
    let arrayNew = array.filter(function (v, i) {
      return (v !== tag)
    })
    store.state.article.tags = arrayNew
  })
}
