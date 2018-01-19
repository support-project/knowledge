import Promise from 'bluebird'
// import api from '../../api'
import logger from 'logger'

const LABEL = 'loadUserInformation.js'

export default (context, params) => {
  logger.trace(LABEL, 'Load user information')
  context.commit('SET_PAGE_STATE', {loading: true})
  // params.i18n.locale = 'en'
  return Promise.try(() => {
    // Check local storage to handle refreshes
    if (window.localStorage) {
      var localUserString = window.localStorage.getItem('user') || 'null'
      var localUser = JSON.parse(localUserString)
      logger.debug(LABEL, localUser)
      if (localUser && context.state.user !== localUser) {
        // TODO Tokenが有効かどうかのチェック（無効になっていれば、ログアウト）
        context.commit('SET_USER', localUser)
        context.commit('SET_TOKEN', window.localStorage.getItem('token'))
      } else {
        context.commit('SET_USER', {
          avatar: 'open.account/icon/',
          userName: 'anonymous'
        })
        context.commit('SET_TOKEN', '')
      }
    }
  }).finally(() => {
    context.commit('SET_PAGE_STATE', {loading: false})
  })
}
