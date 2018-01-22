import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'likeComment.js'

export default (state, params) => {
  logger.debug(LABEL, 'like comment. ' + JSON.stringify(params))
  return Promise.try(() => {
    return api.request('post', '/_api/articles/' + params.id + '/comments/' + params.commentNo + '/likes', null)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    logger.debug(LABEL, JSON.stringify(state.state.resources.comments))
    state.state.resources.comments.forEach(comment => {
      if (comment.commentNo === params.commentNo) {
        comment.likeCount = response.data.count
        state.commit('ADD_ALERT', {
          display: false,
          type: 'succcess',
          title: 'Well done!',
          content: 'You successfully added Like.'
        })
      }
    })
    return response.data
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    state.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  })
}
