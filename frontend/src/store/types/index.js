import loadTypes from './actions/loadTypes'

import setTypes from './mutations/setTypes'

import getTypes from './getters/getTypes'

export default {
  namespaced: true,
  state: {
    types: []
  },
  getters: {
    getTypes: getTypes
  },
  actions: {
    loadTypes: loadTypes
  },
  mutations: {
    setTypes: setTypes
  }
}
