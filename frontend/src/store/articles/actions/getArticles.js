import Promise from 'bluebird'
import logger from 'logger'
import api from '../../../api'
import setIcons from '../../../lib/utils/setIcons'
import loadPaginationInformation from '../../../lib/utils/loadPaginationInformation'

const LABEL = 'getArticles.js'

const restoreHighlight = (str) => {
  if (str) {
    logger.trace(LABEL, str)
    str = str.split('&lt;span class=&quot;mark&quot;&gt;').join('<span class="mark">')
    str = str.split('&lt;/span&gt;').join('</span>')
    logger.trace(LABEL, str)
  }
  return str
}

export default (store) => {
  store.commit('pagestate/setPageState', {loading: true}, {root: true}, {root: true})
  return Promise.try(() => {
    logger.debug(LABEL, 'get articles from api')
    var uri = '/_api/articles'
    var add = false
    if (store.state.pagination.offset) {
      uri += '?offset=' + store.state.pagination.offset
      add = true
    }
    if (store.state.search.keyword) {
      if (add) {
        uri += '&'
      } else {
        uri += '?'
      }
      uri += 'keyword=' + store.state.search.keyword
      add = true
    }
    if (store.state.search.creators && store.state.search.creators.length > 0) {
      if (add) {
        uri += '&'
      } else {
        uri += '?'
      }
      uri += 'creatorIds=' + store.state.search.creators.join(',')
      add = true
    }
    store.commit('setArticles', [])
    return api.request('get', uri, null)
  }).then(response => {
    logger.debug(LABEL, response.data)
    var articles = response.data
    articles.forEach(article => {
      setIcons(store, article)
      article.highlightedTitle = restoreHighlight(article.highlightedTitle)
      article.highlightedContents = restoreHighlight(article.highlightedContents)
    })
    store.commit('setArticles', articles)
    return loadPaginationInformation(response)
  }).then(pagination => {
    store.state.pagination = pagination
  }).catch(error => {
    logger.error(LABEL, error)
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true}, {root: true})
  })
}
