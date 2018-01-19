import api from '../../api'
import logger from 'logger'

const LABEL = 'likeComment.js'

export default (context, params) => {
  logger.debug(LABEL, 'like comment. ' + JSON.stringify(params))
  return api.request('post', '/_api/articles/' + params.id + '/comments/' + params.commentNo + '/likes', null)
  .then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    logger.debug(LABEL, JSON.stringify(context.state.resources.comments))
    context.state.resources.comments.forEach(comment => {
      if (comment.commentNo === params.commentNo) {
        comment.likeCount = response.data.count
      }
    })
    return response.data
  })
}
