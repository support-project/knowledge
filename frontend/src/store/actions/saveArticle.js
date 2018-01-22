import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'saveArticle.js'

export default (state) => {
  state.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    state.commit('CREAR_ALERTS')
    const article = state.state.resources.article
    if (article.knowledgeId) {
      logger.debug(LABEL, 'save articles to api. put data:\n' + JSON.stringify(article, null, '  '))
      return api.request('put', '/_api/articles/' + article.knowledgeId, article)
      .then(response => {
        logger.debug(LABEL, JSON.stringify(response.data))
        return article.knowledgeId
      })
    } else {
      logger.debug(LABEL, 'save articles to api. post data:\n' + JSON.stringify(article, null, '  '))
      return api.request('post', '/_api/articles', article)
      .then(response => {
        logger.debug(LABEL, JSON.stringify(response.data))
        return response.data.id
      })
    }
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    state.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  }).finally(() => {
    state.commit('SET_PAGE_STATE', {loading: false})
  })
}
