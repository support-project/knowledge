import api from '../../api'
import logger from 'logger'

const LABEL = 'saveArticle.js'

export default (context) => {
  const article = context.state.resources.article
  if (article.knowledgeId) {
    logger.info(LABEL, 'save articles to api. put data:\n' + JSON.stringify(article, null, '  '))
    return api.request('put', '/_api/articles/' + article.knowledgeId, article)
    .then(response => {
      logger.debug(LABEL, JSON.stringify(response.data))
      return article.knowledgeId
    })
    .catch(error => {
      logger.error(LABEL, error)
    })
  } else {
    logger.info(LABEL, 'save articles to api. post data:\n' + JSON.stringify(article, null, '  '))
    return api.request('post', '/_api/articles', article)
    .then(response => {
      logger.debug(LABEL, JSON.stringify(response.data))
      return response.data.id
    })
    .catch(error => {
      logger.error(LABEL, error)
    })
  }
}
