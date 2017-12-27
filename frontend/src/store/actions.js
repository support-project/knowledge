import Promise from 'bluebird'
import api from '../api'
import processDecorateAll from '../lib/decorateMarkdown/processDecorateAll'

export default {
  getArticles: (context) => {
    console.log(context.state)
    api.request('get', '/_api/articles', null, context.state.token)
    .then(response => {
      console.log(response.data)
      var articles = response.data
      articles.forEach(element => {
        element.insertUserIcon = '/open.account/icon/' + element.insertUser
        element.updateUserIcon = '/open.account/icon/' + element.updateUser
      })
      context.commit('SET_RESOURCES', {
        articles: articles
      })
    })
    .catch(error => {
      console.error(error)
    })
  },
  getArticle: (context, id) => {
    context.commit('SET_PAGE_STATE', {loading: true})
    context.commit('SET_RESOURCES', {article: ''})
    api.request('get', '/_api/articles/' + id + '?parse=true', null, context.state.token)
    .then(response => {
      var article = response.data
//      console.log(response)
      return Promise.try(() => {
        return processDecorateAll(response.data.displaySafeHtml)
      }).then(function (result) {
//        console.log(result)
        article.displaySafeHtml = result
        context.commit('SET_PAGE_STATE', {loading: false})
        context.commit('SET_RESOURCES', {article: article})
      })
    })
    .catch(error => {
      context.commit('SET_PAGE_STATE', {loading: false})
      console.error(error)
    })
  }
}
