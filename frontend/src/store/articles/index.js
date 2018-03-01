import getArticles from './actions/getArticles'
import getDrafts from './actions/getDrafts'
import selectPage from './actions/selectPage'

import setArticles from './mutations/setArticles'
import setDrafts from './mutations/setDrafts'
import clearSearchCondition from './mutations/clearSearchCondition'
import setCreatorToSearchCondition from './mutations/setCreatorToSearchCondition'

export default {
  namespaced: true,
  state: {
    articles: [],
    drafts: [],
    pagination: {
      limit: 50,
      offst: 0,
      total: 0,
      next: -1,
      prev: -1,
      first: -1,
      last: -1,
      pages: []
    },
    search: {
      search: false,
      keyword: '',
      creators: []
    }
  },
  getters: {
  },
  actions: {
    getArticles: getArticles,
    getDrafts: getDrafts,
    selectPage: selectPage
  },
  mutations: {
    setArticles: setArticles,
    setDrafts: setDrafts,
    clearSearchCondition: clearSearchCondition,
    setCreatorToSearchCondition: setCreatorToSearchCondition
  }
}
