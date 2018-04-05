import Promise from 'bluebird'
import api from '../../../api'

import logger from 'logger'
const LABEL = 'setLanguage.js'

export default (store, params) => {
  logger.trace(LABEL, 'call setLanguage.')
  // 変更がなければ終了
  if (params.$i18n.locale === params.lang) {
    return
  }
  // 表示言語切替
  params.$i18n.locale = params.lang

  if (store.rootGetters['auth/isLogined']) {
    store.commit('pagestate/setPageState', {loading: true}, {root: true})
    return Promise.try(() => {
      return api.request('put', '/_api/me/lang/' + params.lang)
    }).then(response => {
      logger.debug(LABEL, response.data)
    }).catch(error => {
      logger.warn(LABEL, JSON.stringify(error))
      return false
    }).finally(() => {
      store.commit('pagestate/setPageState', {loading: false}, {root: true})
    })
  }
}
