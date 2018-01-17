import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

import actionCommon from './actionCommon'

const LABEL = 'getArticleForEdit.js'

export default (context, id) => {
  context.commit('SET_PAGE_STATE', {loading: true})
  context.commit('SET_RESOURCES', {article: ''})
  api.request('get', '/_api/articles/' + id + '', null)
  .then(response => {
    var article = response.data
    actionCommon.setIcon(context, article)
    logger.debug(LABEL, response)
    return Promise.try(() => {
      return api.request('get', '/_api/articles/' + id + '/items', null)
    }).then(function (response) {
      logger.debug(LABEL, JSON.stringify(response.data, null, '  '))
      var type = response.data
      type.items.forEach(element => {
        if (element.itemType === 11) {
          // bind checkbox value to array object
          var vals = []
          if (element.itemValue) {
            vals = element.itemValue.split(',')
          }
          element.itemValue = vals
        }
      })
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
