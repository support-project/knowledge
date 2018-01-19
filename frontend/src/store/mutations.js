import setResources from './mutations/setResources'
import setPageState from './mutations/setPageState'
import changeArticleType from './mutations/changeArticleType'
import addAlert from './mutations/addAlert'

export default {
  SET_SERVER_URI (state, serverURI) {
    state.serverURI = serverURI
  },
  SET_USER (state, user) {
    state.user = user
  },
  SET_TOKEN (state, token) {
    state.token = token
  },
  SET_REQUEST_TOKEN (state, requestToken) {
    state.requestToken = requestToken
  },
  SET_USER_INFO (state, userInfo) {
    state.userInfo = userInfo
  },
  SET_SEARCH_CONDITIONS (state, searchConditions) {
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
  ADD_ALERT (state, params) {
    addAlert(state, params)
  },
  CREAR_ALERTS (state, params) {
    state.pagestate.alerts = []
  }
}
