import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'
import he from 'he'

import actionCommon from './actionCommon'
import processDecorateAll from '../../lib/decorateMarkdown/processDecorateAll'

const LABEL = 'likeComment.js'

export default (store, params) => {
  logger.debug(LABEL, 'add comment. ' + JSON.stringify(params))

  // content というプロパティにセットされているテキストを comment にセットする
  var comment = params.comment
  comment.comment = comment.content

  store.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    store.commit('CREAR_ALERTS')
    return api.request('post', '/_api/articles/' + params.id + '/comments', params.comment)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    logger.debug(LABEL, JSON.stringify(store.state.resources.comments))
    comment = response.data
    comment.comment = he.decode(comment.comment, {
      'isAttributeValue': true
    })
    return processDecorateAll(comment.comment)
  }).then(function (result) {
    logger.debug(LABEL, result)
    comment.displaySafeHtml = result
    actionCommon.setIcon(store, comment)
    store.state.resources.comments.push(comment)
    store.commit('ADD_ALERT', {
      display: false,
      type: 'succcess',
      title: 'Well done!',
      content: 'You successfully added comment.'
    })
    return comment
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  }).finally(() => {
    store.commit('SET_PAGE_STATE', {loading: false})
  })
}
