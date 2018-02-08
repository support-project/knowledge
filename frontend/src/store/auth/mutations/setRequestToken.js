import logger from 'logger'
const LABEL = 'setRequestToken.js'

export default (state, requestToken) => {
  logger.debug(LABEL, 'set request-token')
  state.requestToken = requestToken
}
