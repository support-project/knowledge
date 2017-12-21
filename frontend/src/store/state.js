export default {
  callingAPI: false,
  searching: '',
  serverURI: 'http://localhost:8080',
  user: null,
  token: null,
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
    articles: [],
    groups: [], // 一覧に表示するグループ（上位5件）
    tags: []
  }
}
