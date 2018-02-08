import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'
import he from 'he'

import setIcons from '../../../lib/utils/setIcons'

const LABEL = 'getArticleForEdit.js'

export default (store, id) => {
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  store.commit('initArticle')
  if (!id) {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
    return
  }
  var article
  return Promise.try(() => {
    return api.request('get', '/_api/articles/' + id + '?include_draft=true', null)
  }).then(response => {
    article = response.data
    setIcons(store, article)
    logger.debug(LABEL, response)
    return api.request('get', '/_api/articles/' + id + '/items?include_draft=true', null)
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
        logger.debug(LABEL, vals)
        element.itemValue = vals
      }
    })
    article.type = response.data
    article.content = he.decode(article.content, {
      'isAttributeValue': true
    })
    store.commit('setArticle', article)
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
  }).catch(error => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
    logger.error(LABEL, error)
  })
}
