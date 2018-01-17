import setResources from './mutations/setResources'
import setPageState from './mutations/setPageState'
import changeArticleType from './mutations/changeArticleType'

export default {
  TOGGLE_LOADING (state) {
    state.callingAPI = !state.callingAPI
  },
  TOGGLE_SEARCHING (state) {
    state.searching = (state.searching === '') ? 'loading' : ''
  },
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
  }
}
