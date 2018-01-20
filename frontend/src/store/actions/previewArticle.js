import Promise from 'bluebird'
import logger from 'logger'

import processDecorateAll from '../../lib/decorateMarkdown/processDecorateAll'

const LABEL = 'previewArticle.js'

export default (context, article) => {
  return Promise.try(() => {
    return processDecorateAll(article.content)
  }).then(function (result) {
    logger.info(LABEL, result)
    return result
    // article.displaySafeHtml = result
    // context.commit('SET_RESOURCES', {article: article})
  })
}
