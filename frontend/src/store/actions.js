import setServerURI from './actions/setServerURI'
import getArticles from './actions/getArticles'
import getArticle from './actions/getArticle'
import getArticleForEdit from './actions/getArticleForEdit'
import previewArticle from './actions/previewArticle'
import toggleTOC from './actions/toggleTOC'
import toggleAttributes from './actions/toggleAttributes'
import getTypes from './actions/getTypes'
import saveArticle from './actions/saveArticle'

export default {
  setServerURI: setServerURI,
  getArticles: getArticles,
  getArticle: getArticle,
  getArticleForEdit: getArticleForEdit,
  previewArticle: previewArticle,
  toggleTOC: toggleTOC,
  toggleAttributes: toggleAttributes,
  getTypes: getTypes,
  saveArticle: saveArticle
}
