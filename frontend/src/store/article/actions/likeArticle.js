import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'likeArticle.js'

export default (store, id) => {
  logger.debug(LABEL, 'like article')
  return Promise.try(() => {
    return api.request('post', '/_api/articles/' + id + '/likes', null)
  }).tap(() => {
    return store.dispatch('user/loadUserInformation', null, {root: true})
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    logger.debug(LABEL, JSON.stringify(store.state.article))
    store.state.article.likeCount = response.data.count
    store.commit('pagestate/addAlert', {
      display: false,
      type: 'succcess',
      title: 'Well done!',
      content: 'You successfully added Like.'
    }, {root: true})
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
