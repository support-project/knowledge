import setServerURI from './actions/setServerURI'

import login from './actions/login'
import logout from './actions/logout'

import getArticles from './actions/getArticles'
import getArticle from './actions/getArticle'
import getArticleForEdit from './actions/getArticleForEdit'
import getTypes from './actions/getTypes'
import getDrafts from './actions/getDrafts'

import previewArticle from './actions/previewArticle'
import toggleRightSideBar from './actions/toggleRightSideBar'

import saveArticle from './actions/saveArticle'

import likeArticle from './actions/likeArticle'
import likeComment from './actions/likeComment'

export default {
  setServerURI: setServerURI,
  login: login,
  logout: logout,
  getArticles: getArticles,
  getArticle: getArticle,
  getArticleForEdit: getArticleForEdit,
  getTypes: getTypes,
  getDrafts: getDrafts,
  previewArticle: previewArticle,
  toggleRightSideBar: toggleRightSideBar,
  saveArticle: saveArticle,
  likeArticle: likeArticle,
  likeComment: likeComment
}
