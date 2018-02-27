import Promise from 'bluebird'
import lang from 'lang'
import logger from 'logger'
const LABEL = 'addTag.js'

export default (store, tag) => {
  if (!lang.isString(tag)) {
    tag = tag.tagName
  }
  return Promise.try(() => {
    logger.debug(LABEL, 'addTag')
    if (!tag) {
      return true
    }
    if (!store.state.article.tags.includes(tag)) {
      store.state.article.tags.push(tag)
      return true
    }
    return false
  })
}
