import api from '../api'

export default {
  getArticles: (context) => {
    console.log(context.state)
    api.request('get', '/_api/articles', null, context.state.token)
    .then(response => {
      console.log(response.data)

      var articles = response.data

      articles.forEach(element => {
        element.insertUserIcon = 'open.account/icon/' + element.insertUser
        element.updateUserIcon = 'open.account/icon/' + element.updateUser
      })

      context.commit('SET_RESOURCES', {
        articles: articles
      })
    })
    .catch(error => {
      console.error(JSON.stringify(error))
    })
  }
}
