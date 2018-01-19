export default {
  callingAPI: false,
  searching: '',
  serverURI: '',
  user: null,
  token: null,
  requestToken: null,
  userInfo: {
    messages: [{1: 'test', 2: 'test'}],
    notifications: []
  },
  searchConditions: {
    keyword: '',
    tags: '',
    groups: '',
    creators: '',
    templates: ''
  },
  resources: {
    article: {
      type: {id: -100}
    },
    comments: [],
    articles: [],
    drafts: [],
    groups: [],
    tags: [],
    types: [],
    toc: ''
  },
  pagestate: {
    showRightSideBar: true,
    loading: false
  }
}
