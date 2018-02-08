import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'login.js'

export default (store, params) => {
  logger.trace(LABEL, 'start login: ' + JSON.stringify(params))
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  return Promise.try(() => {
    return api.request('post', '/_api/auth', params)
    .then(response => {
      var data = response.data
      logger.debug(LABEL, 'logined: ' + JSON.stringify(data))
      if (data.token) {
        var token = data.token
        store.commit('setToken', token)
        if (window.localStorage) {
          // window.localStorage.setItem('user', JSON.stringify(data.user))
          window.localStorage.setItem('token', token)
        }
        return true
      } else {
        store.commit('pagestate/addAlert', {
          notify: false,
          type: 'warning',
          title: 'Faild login',
          content: 'Username/Password incorrect. Please try again.'
        }, {root: true})
        throw new Error('invalid Username/Password.')
      }
    })
  }).catch(error => {
    if (error.response.status === 403) {
      store.commit('pagestate/addAlert', {
        notify: false,
        type: 'warning',
        title: 'Faild login',
        content: 'Username/Password incorrect. Please try again.'
      }, {root: true})
    } else {
      store.commit('pagestate/addAlert', {
        notify: false,
        type: 'danger',
        title: 'Faild login',
        content: 'Server appears to be offline.'
      }, {root: true})
    }
    throw new Error('invalid Username/Password.')
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
  })
}
