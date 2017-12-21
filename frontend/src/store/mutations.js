export default {
  TOGGLE_LOADING (state) {
    state.callingAPI = !state.callingAPI
  },
  TOGGLE_SEARCHING (state) {
    state.searching = (state.searching === '') ? 'loading' : ''
  },
  SET_USER (state, user) {
    state.user = user
  },
  SET_TOKEN (state, token) {
    state.token = token
  },
  SET_USER_INFO (state, userInfo) {
    state.userInfo = userInfo
  },
  SET_SEARCH_CONDITIONS (state, searchConditions) {
    state.searchConditions = searchConditions
  },
  SET_RESOURCES (state, resources) {
    if (resources.articles) {
      state.resources.articles = resources.articles
    }
    if (resources.groups) {
      state.resources.groups = resources.groups
    }
    if (resources.tags) {
      state.resources.tags = resources.tags
    }
  }
}
