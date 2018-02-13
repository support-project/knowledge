import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'
import he from 'he'
import i18n from '../../../lib/i18n'

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
  }).catch(error => {
    if (error.response.status === 404) {
      logger.debug(LABEL, JSON.stringify(error))
      store.commit('pagestate/addAlert', {
        type: 'warning',
        title: 'Error',
        content: i18n.t('Message.NotFound')
      }, {root: true})
    } else {
      logger.error(LABEL, error)
      let msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
      store.commit('pagestate/addAlert', {
        type: 'warning',
        title: 'Error',
        content: msg
      }, {root: true})
    }
    throw error
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
  })
}
