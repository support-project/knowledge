import logger from 'logger'
const LABEL = 'getRequestToken.js'

export default (state) => {
  logger.debug(LABEL, 'call getRequestToken')
  return state.requestToken
}
