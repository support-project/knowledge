import api from '../../api'
import logger from 'logger'

const LABEL = 'setServerURI.js'

export default (context, serverURI) => {
  logger.info(LABEL, 'set server uri:' + serverURI)
  context.commit('SET_SERVER_URI', serverURI)
  api.setServerURI(serverURI)
}
