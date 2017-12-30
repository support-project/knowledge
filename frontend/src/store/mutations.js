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
  SET_USER_INFO (state, userInfo) {
    state.userInfo = userInfo
  },
  SET_SEARCH_CONDITIONS (state, searchConditions) {
    state.searchConditions = searchConditions
  },
  SET_RESOURCES (state, resources) {
    if ('article' in resources) {
      state.resources.article = resources.article
    }
    if ('articles' in resources) {
      state.resources.articles = resources.articles
    }
    if ('groups' in resources) {
      state.resources.groups = resources.groups
    }
    if ('tags' in resources) {
      state.resources.tags = resources.tags
    }
    if ('toc' in resources) {
      state.resources.toc = resources.toc
    }
  },
  SET_PAGE_STATE (state, pagestate) {
    if ('loading' in pagestate) {
      state.pagestate.loading = pagestate.loading
    }
  }
}
