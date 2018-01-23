import logger from 'logger'

const LABEL = 'actionCommon.js'

var setIcon = (store, article) => {
  logger.trace(LABEL, store.state.serverURI + '/open.account/icon/' + article.insertUser)
  article.insertUserIcon = store.state.serverURI + '/open.account/icon/' + article.insertUser
  article.updateUserIcon = store.state.serverURI + '/open.account/icon/' + article.updateUser
}

export default {
  setIcon: setIcon
}
