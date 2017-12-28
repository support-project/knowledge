import Promise from 'bluebird'
import api from '../api'
import logger from 'logger'
import processDecorateAll from '../lib/decorateMarkdown/processDecorateAll'
import processToc from '../lib/decorateMarkdown/processToc'

const LABEL = 'action.js'

export default {
  getArticles: (context) => {
    logger.debug(LABEL, context.state)
    api.request('get', '/_api/articles', null, context.state.token)
    .then(response => {
      logger.debug(LABEL, response.data)
      var articles = response.data
      articles.forEach(element => {
        element.insertUserIcon = '/open.account/icon/' + element.insertUser
        element.updateUserIcon = '/open.account/icon/' + element.updateUser
      })
      context.commit('SET_RESOURCES', {
        articles: articles
      })
    })
    .catch(error => {
      logger.error(LABEL, error)
    })
  },
  getArticle: (context, id) => {
    context.commit('SET_PAGE_STATE', {loading: true})
    context.commit('SET_RESOURCES', {article: ''})
    api.request('get', '/_api/articles/' + id + '', null, context.state.token)
    .then(response => {
      var article = response.data
      logger.debug(LABEL, response)
      return Promise.try(() => {
        return processDecorateAll(response.data.content)
      }).then(function (result) {
        logger.debug(LABEL, result)
        article.displaySafeHtml = result
        context.commit('SET_RESOURCES', {article: article})
        context.commit('SET_PAGE_STATE', {loading: false})
        return processToc(result)
      }).then(function (toc) {
        logger.info(LABEL, toc)
        context.commit('SET_RESOURCES', {toc: toc})
      })
    })
    .catch(error => {
      context.commit('SET_PAGE_STATE', {loading: false})
      logger.error(LABEL, error)
    })
  }
}
