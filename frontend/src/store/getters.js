export default {
  IS_LOGINED (state) {
    if (state.token) {
      return true
    }
    return false
  },
  GET_USER (state) {
    return state.user
  },
  GET_TOKEN (state) {
    return state.token
  },
  GET_REQUEST_TOKEN (state) {
    return state.requestToken
  },
  GET_USER_INFO (state) {
    return state.userInfo
  },
  GET_SEARCH_CONDITIONS (state) {
    return state.searchConditions
  },
  GET_RESOURCES (state) {
    return state.resources
  }
}
