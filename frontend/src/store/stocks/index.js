import getStocks from './actions/getStocks'

export default {
  namespaced: true,
  state: {
    loading: false,
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
    getStocks: getStocks
  },
  mutations: {
  }
}
