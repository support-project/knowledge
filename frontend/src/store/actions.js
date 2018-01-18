import setServerURI from './actions/setServerURI'

import getArticles from './actions/getArticles'
import getArticle from './actions/getArticle'
import getArticleForEdit from './actions/getArticleForEdit'
import getTypes from './actions/getTypes'
import getDrafts from './actions/getDrafts'

import previewArticle from './actions/previewArticle'
import toggleTOC from './actions/toggleTOC'
import toggleAttributes from './actions/toggleAttributes'

import saveArticle from './actions/saveArticle'

export default {
  setServerURI: setServerURI,
  getArticles: getArticles,
  getArticle: getArticle,
  getArticleForEdit: getArticleForEdit,
  getTypes: getTypes,
  getDrafts: getDrafts,
  previewArticle: previewArticle,
  toggleTOC: toggleTOC,
  toggleAttributes: toggleAttributes,
  saveArticle: saveArticle
}
