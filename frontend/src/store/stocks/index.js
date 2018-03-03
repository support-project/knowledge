import getStocks from './actions/getStocks'
import getStock from './actions/getStock'
import getArticles from './actions/getArticles'
import saveArticle from './actions/saveArticle'

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
    getStock: getStock,
    getArticles: getArticles,
    saveArticle: saveArticle
  },
  mutations: {
  }
}
