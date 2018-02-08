import Promise from 'bluebird'
import logger from 'logger'

import processDecorateAll from '../../../lib/decorateMarkdown/processDecorateAll'

const LABEL = 'previewArticle.js'

export default (store, article) => {
  return Promise.try(() => {
    return processDecorateAll(article.content)
  }).then(function (result) {
    logger.debug(LABEL, result)
    return result
  })
}
