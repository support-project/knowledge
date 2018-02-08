import logger from 'logger'

import store from '../../store'

const LABEL = 'actionCommon.js'

var setIcon = (store2, article) => {
  var serverURI = store.state.system.serverURI
  logger.trace(LABEL, serverURI + '/open.account/icon/' + article.insertUser)
  article.insertUserIcon = serverURI + '/open.account/icon/' + article.insertUser
  article.updateUserIcon = serverURI + '/open.account/icon/' + article.updateUser
}

export default setIcon
