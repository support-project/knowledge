import showLikesModal from './actions/showLikesModal'
import getLikes from './actions/getLikes'
import selectPage from './actions/selectPage'

export default {
  namespaced: true,
  state: {
    articleId: -1,
    items: [],
    pagination: {
      limit: 10,
      offst: 0,
      total: 0,
      next: -1,
      prev: -1,
      pages: []
    }
  },
  getters: {
  },
  actions: {
    showLikesModal: showLikesModal,
    getLikes: getLikes,
    selectPage: selectPage
  },
  mutations: {
  }
}
