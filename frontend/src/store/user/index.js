import user from './state/user'
import userInfo from './state/userInfo'

import loadUserInformation from './actions/loadUserInformation'
import setLanguage from './actions/setLanguage'

import setUser from './mutations/setUser'
import setUserInfo from './mutations/setUserInfo'

import getUser from './getters/getUser'
import getUserInfo from './getters/getUserInfo'

export default {
  namespaced: true,
  state: {
    user: user,
    userInfo: userInfo
  },
  getters: {
    getUser: getUser,
    getUserInfo: getUserInfo
  },
  actions: {
    loadUserInformation: loadUserInformation,
    setLanguage: setLanguage
  },
  mutations: {
    setUser: setUser,
    setUserInfo: setUserInfo
  }
}
