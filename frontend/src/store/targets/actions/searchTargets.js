import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'searchTargets.js'

export default (store, params) => {
  logger.debug(LABEL, 'searchTargets')
  store.state.loading = true
  var url = '/_api/targets'
  url += '?keyword=' + params.keyword
  url += '&offset=' + params.offset
  url += '&limit=' + params.limit

  return Promise.try(() => {
    return api.request('get', url)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    store.state.targets = response.data
    // 既に選択済みのものにフラグをたてる
    store.state.targets.forEach(target => {
      target.selected = false
      if (target.type === 'group') {
        store.state.selected.groups.forEach(group => {
          if (target.id === group.id) {
            target.selected = true
          }
        })
      } else {
        store.state.selected.users.forEach(user => {
          if (target.id === user.id) {
            target.selected = true
          }
        })
      }
    })
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('pagestate/addAlert', {
      type: 'warning',
      display: false,
      title: 'Error',
      content: msg
    }, {root: true})
  }).finally(() => {
    store.state.loading = false
  })
}
