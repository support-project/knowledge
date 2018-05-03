import getArticles from './actions/getArticles'
import getDrafts from './actions/getDrafts'
import selectPage from './actions/selectPage'
import getTypesInformation from './actions/getTypesInformation'

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
      available: false,
      keyword: '',
      types: [],
      typeIds: [],
      creators: []
    }
  },
  getters: {
  },
  actions: {
    getArticles: getArticles,
    getDrafts: getDrafts,
    selectPage: selectPage,
    getTypesInformation: getTypesInformation
  },
  mutations: {
    setArticles: setArticles,
    setDrafts: setDrafts,
    clearSearchCondition: clearSearchCondition,
    setCreatorToSearchCondition: setCreatorToSearchCondition
  }
}
