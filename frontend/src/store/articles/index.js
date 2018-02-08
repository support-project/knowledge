import getArticles from './actions/getArticles'
import getDrafts from './actions/getDrafts'

import setArticles from './mutations/setArticles'
import setDrafts from './mutations/setDrafts'

export default {
  namespaced: true,
  state: {
    articles: [],
    drafts: []
  },
  getters: {
  },
  actions: {
    getArticles: getArticles,
    getDrafts: getDrafts
  },
  mutations: {
    setArticles: setArticles,
    setDrafts: setDrafts
  }
}
