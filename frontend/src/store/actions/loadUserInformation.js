import Promise from 'bluebird'
// import api from '../../api'
import logger from 'logger'

const LABEL = 'loadUserInformation.js'

export default (state, params) => {
  logger.trace(LABEL, 'Load user information')
  state.commit('SET_PAGE_STATE', {loading: true})
  // params.i18n.locale = 'en'
  return Promise.try(() => {
    // Check local storage to handle refreshes
    if (window.localStorage) {
      var localUserString = window.localStorage.getItem('user') || 'null'
      var localUser = JSON.parse(localUserString)
      logger.debug(LABEL, localUser)
      if (localUser && state.state.user !== localUser) {
        // TODO Tokenが有効かどうかのチェック（無効になっていれば、ログアウト）
        state.commit('SET_USER', localUser)
        state.commit('SET_TOKEN', window.localStorage.getItem('token'))
      } else {
        state.commit('SET_USER', {
          avatar: 'open.account/icon/',
          userName: 'anonymous'
        })
        state.commit('SET_TOKEN', '')
      }
    }
  }).finally(() => {
    state.commit('SET_PAGE_STATE', {loading: false})
  })
}
