import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'
import he from 'he'

import setIcons from '../../../lib/utils/setIcons'
import processDecorateAll from '../../../lib/decorateMarkdown/processDecorateAll'

const LABEL = 'addComment.js'

export default (store, params) => {
  logger.debug(LABEL, 'add comment. ' + JSON.stringify(params))

  // content というプロパティにセットされているテキストを comment にセットする
  var comment = params.comment
  comment.comment = comment.content

  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  return Promise.try(() => {
    store.commit('pagestate/clearAlerts', null, {root: true})
    return api.request('post', '/_api/articles/' + params.id + '/comments', params.comment)
  }).tap(() => {
    return store.dispatch('user/loadUserInformation', null, {root: true})
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    comment = response.data
    comment.comment = he.decode(comment.comment, {
      'isAttributeValue': true
    })
    return processDecorateAll(comment.comment)
  }).then(function (result) {
    logger.debug(LABEL, result)
    comment.displaySafeHtml = result
    setIcons(store, comment)
    store.state.comments.push(comment)
    store.commit('pagestate/addAlert', {
      display: false,
      type: 'succcess',
      title: 'Well done!',
      content: 'You successfully added comment.'
    }, {root: true})
    return store.dispatch('article/countUpComent', null, {root: true})
  }).then(function () {
    return comment
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = 'Error Occurred'
    if (error.response) {
      msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    }
    store.commit('pagestate/addAlert', {
      type: 'warning',
      title: 'Error',
      content: msg
    }, {root: true})
    throw error
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
  })
}
