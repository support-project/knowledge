import showDialog from './actions/showDialog'
import selectTarget from './actions/selectTarget'
import searchTargets from './actions/searchTargets'
import removeTarget from './actions/removeTarget'

export default {
  namespaced: true,
  state: {
    loading: false,
    params: {
      keyword: '',
      limit: 10,
      offset: 0
    },
    selected: {
      groups: [],
      users: []
    },
    targets: []
  },
  getters: {
  },
  actions: {
    showDialog: showDialog,
    selectTarget: selectTarget,
    searchTargets: searchTargets,
    removeTarget: removeTarget
  },
  mutations: {
  }
}
