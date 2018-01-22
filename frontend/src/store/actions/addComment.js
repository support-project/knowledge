import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

import actionCommon from './actionCommon'
import processDecorateAll from '../../lib/decorateMarkdown/processDecorateAll'

const LABEL = 'likeComment.js'

export default (state, params) => {
  logger.debug(LABEL, 'add comment. ' + JSON.stringify(params))

  // content というプロパティにセットされているテキストを comment にセットする
  var comment = params.comment
  comment.comment = comment.content

  state.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    state.commit('CREAR_ALERTS')
    return api.request('post', '/_api/articles/' + params.id + '/comments', params.comment)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    logger.debug(LABEL, JSON.stringify(state.state.resources.comments))
    comment = response.data
    return processDecorateAll(comment.comment)
  }).then(function (result) {
    logger.debug(LABEL, result)
    comment.displaySafeHtml = result
    actionCommon.setIcon(state, comment)
    state.state.resources.comments.push(comment)
    state.commit('ADD_ALERT', {
      display: false,
      type: 'succcess',
      title: 'Well done!',
      content: 'You successfully added comment.'
    })
    return comment
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    state.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  }).finally(() => {
    state.commit('SET_PAGE_STATE', {loading: false})
  })
}
