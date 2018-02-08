import getComments from './actions/getComments'
import addComment from './actions/addComment'
import toggleDisplayComment from './actions/toggleDisplayComment'
import likeComment from './actions/likeComment'

import setComments from './mutations/setComments'

export default {
  namespaced: true,
  state: {
    comments: []
  },
  getters: {
  },
  actions: {
    getComments: getComments,
    addComment: addComment,
    toggleDisplayComment: toggleDisplayComment,
    likeComment: likeComment
  },
  mutations: {
    setComments: setComments
  }
}
