import logger from 'logger'

const LABEL = 'actionCommon.js'

var setIcon = (state, article) => {
  logger.trace(LABEL, state.state.serverURI + '/open.account/icon/' + article.insertUser)
  article.insertUserIcon = state.state.serverURI + '/open.account/icon/' + article.insertUser
  article.updateUserIcon = state.state.serverURI + '/open.account/icon/' + article.updateUser
}

export default {
  setIcon: setIcon
}
