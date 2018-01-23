import api from '../../api'
import logger from 'logger'

const LABEL = 'setServerURI.js'

export default (store, serverURI) => {
  logger.debug(LABEL, 'set server uri:' + serverURI)
  store.commit('SET_SERVER_URI', serverURI)
  api.setServerURI(serverURI)
}
