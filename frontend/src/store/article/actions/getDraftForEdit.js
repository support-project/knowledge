import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'
import he from 'he'

import setIcons from '../../../lib/utils/setIcons'

const LABEL = 'getDraftForEdit.js'

export default (store, id) => {
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  store.commit('initArticle')
  if (!id) {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
    return
  }
  return Promise.try(() => {
    return api.request('get', '/_api/drafts/' + id + '', null)
  }).then(response => {
    var article = response.data
    setIcons(store, article)
    logger.debug(LABEL, response)
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
