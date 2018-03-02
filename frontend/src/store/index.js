import Vue from 'vue'
import Vuex from 'vuex'

import system from './system'
import pagestate from './pagestate'
import auth from './auth'
import user from './user'
import search from './search'
import articles from './articles'
import article from './article'
import comments from './comments'
import types from './types'
import targets from './targets'
import likes from './likes'
import stocks from './stocks'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    system: system,
    pagestate: pagestate,
    auth: auth,
    user: user,
    search: search,
    articles: articles,
    article: article,
    comments: comments,
    types: types,
    targets: targets,
    likes: likes,
    stocks: stocks
  }
})
