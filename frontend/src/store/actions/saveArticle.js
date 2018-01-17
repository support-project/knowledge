import api from '../../api'
import logger from 'logger'

const LABEL = 'saveArticle.js'

export default (context) => {
  const article = context.state.resources.article
  if (article.knowledgeId) {
    logger.info(LABEL, 'save articles to api. put data:\n' + JSON.stringify(article, null, '  '))
    api.request('put', '/_api/articles/' + article.knowledgeId, article)
    .then(response => {
      logger.debug(LABEL, response.data)
    })
    .catch(error => {
      logger.error(LABEL, error)
    })
  } else {
    logger.info(LABEL, 'save articles to api. post data:\n' + JSON.stringify(article, null, '  '))
    api.request('post', '/_api/articles', article)
    .then(response => {
      logger.debug(LABEL, response.data)
    })
    .catch(error => {
      logger.error(LABEL, error)
    })
  }
}
