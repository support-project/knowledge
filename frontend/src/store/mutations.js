import setResources from './mutations/setResources'
import setPageState from './mutations/setPageState'
import changeArticleType from './mutations/changeArticleType'
import addAlert from './mutations/addAlert'

import logger from 'logger'
const LABEL = 'mutations.js'

export default {
  SET_SERVER_URI (state, serverURI) {
    logger.info(LABEL, 'Set Server URI:' + JSON.stringify(serverURI))
    state.serverURI = serverURI
  },
  SET_USER (state, user) {
    logger.info(LABEL, 'Set User:' + JSON.stringify(user))
    state.user = user
  },
  SET_TOKEN (state, token) {
    logger.info(LABEL, 'Set Token:' + token)
    state.token = token
  },
  SET_REQUEST_TOKEN (state, requestToken) {
    logger.info(LABEL, 'Set Request Token:' + requestToken)
    state.requestToken = requestToken
  },
  SET_USER_INFO (state, userInfo) {
    logger.info(LABEL, 'Set User information:' + JSON.stringify(userInfo))
    state.userInfo = userInfo
  },
  SET_SEARCH_CONDITIONS (state, searchConditions) {
    logger.info(LABEL, 'Set search conditions:' + JSON.stringify(searchConditions))
    state.searchConditions = searchConditions
  },
  SET_RESOURCES (state, resources) {
    setResources(state, resources)
  },
  SET_PAGE_STATE (state, pagestate) {
    setPageState(state, pagestate)
  },
  CHANGE_ARTICLE_TYPE (state, type) {
    changeArticleType(state, type)
  },
  INIT_ARTICLE (state) {
    state.resources.article = {
      title: '',
      content: '',
      type: {
        id: -100,
        items: []
      }
    }
  },
  ADD_ALERT (state, params) {
    addAlert(state, params)
  },
  CREAR_ALERTS (state, params) {
    state.pagestate.alerts = []
  }
}
