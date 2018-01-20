import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'login.js'

export default (context, params) => {
  logger.trace(LABEL, 'start login: ' + JSON.stringify(params))
  context.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    return api.request('post', '/_api/auth', params)
    .then(response => {
      var data = response.data
      logger.debug(LABEL, 'logined: ' + JSON.stringify(data))
      if (data.user) {
        var token = data.token
        data.user.avatar = 'open.account/icon/' + data.user.userId
        context.commit('SET_USER', data.user)
        context.commit('SET_TOKEN', token)
        if (window.localStorage) {
          window.localStorage.setItem('user', JSON.stringify(data.user))
          window.localStorage.setItem('token', token)
        }
      } else {
        context.commit('ADD_ALERT', {
          notify: false,
          type: 'warning',
          title: 'Faild login',
          content: 'Username/Password incorrect. Please try again.'
        })
        throw new Error('invalid Username/Password.')
      }
      return data
    })
  }).catch(error => {
    if (error.response.status === 403) {
      context.commit('ADD_ALERT', {
        notify: false,
        type: 'warning',
        title: 'Faild login',
        content: 'Username/Password incorrect. Please try again.'
      })
    } else {
      context.commit('ADD_ALERT', {
        notify: false,
        type: 'danger',
        title: 'Faild login',
        content: 'Server appears to be offline.'
      })
    }
    throw new Error('invalid Username/Password.')
  }).finally(() => {
    context.commit('SET_PAGE_STATE', {loading: false})
  })
}
