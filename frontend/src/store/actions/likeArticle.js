import api from '../../api'
import logger from 'logger'

const LABEL = 'likeArticle.js'

export default (state, id) => {
  logger.debug(LABEL, 'like article')
  return api.request('post', '/_api/articles/' + id + '/likes', null)
  .then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    logger.debug(LABEL, JSON.stringify(state.state.resources.article))
    state.state.resources.article.likeCount = response.data.count
    state.commit('ADD_ALERT', {
      display: false,
      type: 'succcess',
      title: 'Well done!',
      content: 'You successfully added Like.'
    })
    return response.data
  })
}
