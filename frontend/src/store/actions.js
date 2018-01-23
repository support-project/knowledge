import setServerURI from './actions/setServerURI'

import login from './actions/login'
import logout from './actions/logout'
import loadUserInformation from './actions/loadUserInformation'

import getArticles from './actions/getArticles'
import getArticle from './actions/getArticle'
import getArticleForEdit from './actions/getArticleForEdit'
import getTypes from './actions/getTypes'
import getDrafts from './actions/getDrafts'

import saveArticle from './actions/saveArticle'
import saveDraft from './actions/saveDraft'
import addComment from './actions/addComment'

import likeArticle from './actions/likeArticle'
import likeComment from './actions/likeComment'

import previewArticle from './actions/previewArticle'
import toggleRightSideBar from './actions/toggleRightSideBar'
import toggleDisplayComment from './actions/toggleDisplayComment'

export default {
  setServerURI: setServerURI,
  login: login,
  logout: logout,
  loadUserInformation: loadUserInformation,
  getArticles: getArticles,
  getArticle: getArticle,
  getArticleForEdit: getArticleForEdit,
  getTypes: getTypes,
  getDrafts: getDrafts,
  saveArticle: saveArticle,
  saveDraft: saveDraft,
  addComment: addComment,
  likeArticle: likeArticle,
  likeComment: likeComment,
  previewArticle: previewArticle,
  toggleRightSideBar: toggleRightSideBar,
  toggleDisplayComment: toggleDisplayComment
}
