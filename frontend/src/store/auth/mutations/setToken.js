import logger from 'logger'
const LABEL = 'setToken.js'

export default (state, token) => {
  logger.debug(LABEL, 'set token')
  state.token = token
}
