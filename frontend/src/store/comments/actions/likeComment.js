import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'likeComment.js'

export default (store, params) => {
  logger.debug(LABEL, 'like comment. ' + JSON.stringify(params))
  return Promise.try(() => {
    return api.request('post', '/_api/articles/' + params.id + '/comments/' + params.commentNo + '/likes', null)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    store.state.comments.forEach(comment => {
      if (comment.commentNo === params.commentNo) {
        comment.likeCount = response.data.count
        store.commit('pagestate/addAlert', {
          display: false,
          type: 'succcess',
          title: 'Well done!',
          content: 'You successfully added Like.'
        }, {root: true})
      }
    })
    return response.data
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('pagestate/addAlert', {
      type: 'warning',
      title: 'Error',
      content: msg
    }, {root: true})
    throw error
  })
}
