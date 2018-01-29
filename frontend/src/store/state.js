import user from './state/user'
import userInfo from './state/userInfo'
import searchConditions from './state/searchConditions'
import pagestate from './state/pagestate'

import article from './state/article'
import articles from './state/articles'

export default {
  serverURI: '',
  token: null,
  requestToken: null,
  user: user,
  userInfo: userInfo,
  searchConditions: searchConditions,
  pagestate: pagestate,
  // resources
  article: article,
  articles: articles,
  resources: {
    comments: [],
    drafts: [],
    groups: [],
    tags: [],
    types: [],
    toc: ''
  }
}
