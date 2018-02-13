import toggleRightSideBar from './actions/toggleRightSideBar'
import triggerResize from './actions/triggerResize'

import addAlert from './mutations/addAlert'
import clearAlerts from './mutations/clearAlerts'
import setPageState from './mutations/setPageState'

export default {
  namespaced: true,
  state: {
    showRightSideBar: true,
    loading: false,
    alerts: []
  },
  getters: {
  },
  actions: {
    toggleRightSideBar: toggleRightSideBar,
    triggerResize: triggerResize
  },
  mutations: {
    addAlert: addAlert,
    clearAlerts: clearAlerts,
    setPageState: setPageState
  }
}
