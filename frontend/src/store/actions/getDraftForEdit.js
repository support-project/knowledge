import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'
import he from 'he'

import actionCommon from './actionCommon'

const LABEL = 'getDraftForEdit.js'

export default (store, id) => {
  store.commit('SET_PAGE_STATE', {loading: true})
  store.commit('INIT_ARTICLE')
  if (!id) {
    store.commit('SET_PAGE_STATE', {loading: false})
    return
  }
  return Promise.try(() => {
    return api.request('get', '/_api/drafts/' + id + '', null)
  }).then(response => {
    var article = response.data
    actionCommon.setIcon(store, article)
    logger.debug(LABEL, response)
/*
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
*/
    article.content = he.decode(article.content, {
      'isAttributeValue': true
    })
    store.commit('SET_RESOURCES', {article: article})
    store.commit('SET_PAGE_STATE', {loading: false})
  }).catch(error => {
    store.commit('SET_PAGE_STATE', {loading: false})
    logger.error(LABEL, error)
  })
}
