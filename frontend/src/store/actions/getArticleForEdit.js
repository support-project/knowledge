import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

import actionCommon from './actionCommon'

const LABEL = 'getArticleForEdit.js'

export default (context, id) => {
  context.commit('SET_PAGE_STATE', {loading: true})
  context.commit('SET_RESOURCES', {article: ''})
  api.request('get', '/_api/articles/' + id + '', null, context.state.token)
  .then(response => {
    var article = response.data
    actionCommon.setIcon(context, article)
    logger.debug(LABEL, response)
    return Promise.try(() => {
      return api.request('get', '/_api/articles/' + id + '/items', null, context.state.token)
    }).then(function (response) {
      article.type = response.data
      context.commit('SET_RESOURCES', {article: article})
      context.commit('SET_PAGE_STATE', {loading: false})
    })
  })
  .catch(error => {
    context.commit('SET_PAGE_STATE', {loading: false})
    logger.error(LABEL, error)
  })
}
