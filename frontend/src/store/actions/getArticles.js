import api from '../../api'
import logger from 'logger'

import actionCommon from './actionCommon'

const LABEL = 'getArticles.js'

export default (context) => {
  logger.debug(LABEL, 'get articles from api')
  api.request('get', '/_api/articles', null, context.state.token)
  .then(response => {
    logger.debug(LABEL, response.data)
    var articles = response.data
    articles.forEach(article => {
      actionCommon.setIcon(context, article)
    })
    context.commit('SET_RESOURCES', {
      articles: articles
    })
  })
  .catch(error => {
    logger.error(LABEL, error)
  })
}
