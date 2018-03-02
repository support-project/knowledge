import getStocks from './actions/getStocks'
import getArticles from './actions/getArticles'

export default {
  namespaced: true,
  state: {
    loading: false,
    items: [],
    item: {},
    pagination: {
      limit: 10,
      offst: 0,
      total: 0,
      next: -1,
      prev: -1,
      pages: []
    },
    articles: [],
    articlePagination: {
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
    getStocks: getStocks,
    getArticles: getArticles
  },
  mutations: {
  }
}
