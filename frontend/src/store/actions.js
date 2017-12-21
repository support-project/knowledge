import api from '../api'

export default {
  getArticles: (context) => {
    console.log(context.state)
    api.request('get', '/_api/articles')
    .then(response => {
      console.log(response.data)
      context.commit('SET_RESOURCES', {
        articles: response.data
      })
    })
    .catch(error => {
      console.log(error.response.data)
    })
  }
}
