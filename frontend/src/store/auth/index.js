import checkTokenOnLocalStrage from './actions/checkTokenOnLocalStrage'
import login from './actions/login'
import logout from './actions/logout'

import setRequestToken from './mutations/setRequestToken'
import setToken from './mutations/setToken'

import isLogined from './getters/isLogined'
import getToken from './getters/getToken'
import getRequestToken from './getters/getRequestToken'

export default {
  namespaced: true,
  state: {
    token: null,
    requestToken: null
  },
  getters: {
    isLogined: isLogined,
    getToken: getToken,
    getRequestToken: getRequestToken
  },
  actions: {
    checkTokenOnLocalStrage: checkTokenOnLocalStrage,
    login: login,
    logout: logout
  },
  mutations: {
    setRequestToken: setRequestToken,
    setToken: setToken
  }
}
