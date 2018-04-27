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

    let params = {}
    if (store.state.pagination.offset) {
      params.offset = store.state.pagination.offset
    }
    if (store.state.search.keyword) {
      params.keyword = store.state.search.keyword
    }
    if (store.state.search.typeIds && store.state.search.typeIds.length > 0) {
      params.typeIds = store.state.search.typeIds
    } else if (store.state.search.types && store.state.search.types.length > 0) {
      // id指定があれば、それを優先
      params.types = store.state.search.types
    }
    if (store.state.search.creatorIds && store.state.search.creatorIds.length > 0) {
      if (!params.creatorIds) params.creatorIds = []
      store.state.search.creators.forEach(c => {
        params.creatorIds.push(c)
      })
    }
    let query = ''
    Object.entries(params).forEach(e => {
      logger.info(LABEL, e)
      if (query) query += '&'
      query += e[0] + '='
      if (Array.isArray(e[1])) {
        query += e[1].join(',')
      } else {
        query += e[1]
      }
    })
    let uri = '/_api/articles'
    if (query) {
      uri += '?' + query
    }
    logger.info(LABEL, uri)
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
