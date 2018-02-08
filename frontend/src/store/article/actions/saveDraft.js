import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'
import lang from 'lang'

const LABEL = 'saveDraft.js'

export default (store) => {
  store.commit('pagestate/setPageState', {loading: true})
  store.commit('pagestate/clearAlerts', null, {root: true})
  return Promise.try(() => {
    return lang.deepClone(store.state.article)
  }).then((article) => {
    article.type.items.forEach(element => {
      if (element.itemType === 11) {
        if (!lang.isString(element.itemValue)) {
          var vals = element.itemValue.join(',')
          element.itemValue = vals
        }
      }
    })
    logger.debug(LABEL, 'draft save articles to api. put data:\n' + JSON.stringify(article, null, '  '))
    return api.request('post', '/_api/drafts', article)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    store.commit('pagestate/addAlert', {
      display: false,
      type: 'success',
      title: 'Well done!',
      content: 'You successfully save draft.'
    }, {root: true})
    store.state.article.draftId = response.data.id
    store.commit('setArticle', store.state.article)
    return response.data.id
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('pagestate/addAlert', {
      type: 'warning',
      title: 'Error',
      content: msg
    }, {root: true})
    throw error
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false})
  })
}
