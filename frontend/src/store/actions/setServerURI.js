import api from '../../api'
import logger from 'logger'

const LABEL = 'setServerURI.js'

export default (state, serverURI) => {
  logger.debug(LABEL, 'set server uri:' + serverURI)
  state.commit('SET_SERVER_URI', serverURI)
  api.setServerURI(serverURI)
}
