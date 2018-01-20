import api from '../../api'
import logger from 'logger'

const LABEL = 'likeArticle.js'

export default (context, id) => {
  logger.debug(LABEL, 'like article')
  return api.request('post', '/_api/articles/' + id + '/likes', null)
  .then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    logger.debug(LABEL, JSON.stringify(context.state.resources.article))
    context.state.resources.article.likeCount = response.data.count
    context.commit('ADD_ALERT', {
      display: false,
      type: 'succcess',
      title: 'Well done!',
      content: 'You successfully added Like.'
    })
    return response.data
  })
}
