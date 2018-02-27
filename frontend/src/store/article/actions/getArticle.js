import Promise from 'bluebird'
import api from '../../../api'
import i18n from '../../../lib/i18n'

import logger from 'logger'

import setIcons from '../../../lib/utils/setIcons'

import processDecorateAll from '../../../lib/decorateMarkdown/processDecorateAll'
import processToc from '../../../lib/decorateMarkdown/processToc'
import processTemplateItemView from '../../../lib/displayParts/processTemplateItemView'

const LABEL = 'getArticle.js'

export default (store, id) => {
  let article = {}
  logger.debug(LABEL, 'start getArticle')
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  return Promise.try(() => {
    store.commit('initArticle')
    store.commit('pagestate/clearAlerts', null, {root: true})
    return api.request('get', '/_api/articles/' + id + '?check_draft=true', null)
  }).then(response => {
    article = response.data
    setIcons(store, article)
    logger.debug(LABEL, response)
    return processDecorateAll(article.content)
  }).then(function (result) {
    logger.debug(LABEL, result)
    article.displaySafeHtml = result
    return processToc(result)
  }).then(function (toc) {
    logger.debug(LABEL, toc)
    store.commit('setToc', toc)
    return api.request('get', '/_api/articles/' + id + '/items', null)
  }).then(function (response) {
    return processTemplateItemView(response.data)
  }).then(function (itemsHtml) {
    logger.debug(itemsHtml)
    article.itemsHtml = itemsHtml
    store.commit('setArticle', article)
    return store.dispatch('comments/getComments', id, {root: true})
  }).catch(error => {
    if (error.response.status === 404) {
      logger.debug(LABEL, JSON.stringify(error))
      store.commit('pagestate/addAlert', {
        type: 'warning',
        title: 'Error',
        content: i18n.t('Message.NotFound')
      }, {root: true})
    } else {
      logger.error(LABEL, JSON.stringify(error))
      const msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
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
