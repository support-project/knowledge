import logger from 'logger'

const LABEL = 'actionCommon.js'

var setIcon = (context, article) => {
  logger.trace(LABEL, context.state.serverURI + '/open.account/icon/' + article.insertUser)
  article.insertUserIcon = context.state.serverURI + '/open.account/icon/' + article.insertUser
  article.updateUserIcon = context.state.serverURI + '/open.account/icon/' + article.updateUser
}

export default {
  setIcon: setIcon
}
