import logger from 'logger'
const LABEL = 'mutations.js'

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
      logger.info(LABEL, 'Change state.resources.article: ' + JSON.stringify(resources.article))
      state.resources.article = resources.article
    }
    if ('articles' in resources) {
      state.resources.articles = resources.articles
    }
    if ('comments' in resources) {
      state.resources.comments = resources.comments
    }
    if ('groups' in resources) {
      state.resources.groups = resources.groups
    }
    if ('types' in resources) {
      state.resources.types = resources.types
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
    if ('toggleTOC' in pagestate) {
      logger.info(LABEL, 'Change state.pagestate.toggleTOC: ' + JSON.stringify(pagestate.toggleTOC))
      state.pagestate.toggleTOC = pagestate.toggleTOC
    }
    if ('toggleAttributes' in pagestate) {
      logger.info(LABEL, 'Change state.pagestate.toggleAttributes: ' + JSON.stringify(pagestate.toggleAttributes))
      state.pagestate.toggleAttributes = pagestate.toggleAttributes
    }
  }
}
