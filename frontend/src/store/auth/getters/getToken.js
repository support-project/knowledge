import logger from 'logger'
const LABEL = 'getToken.js'

export default (state) => {
  logger.debug(LABEL, 'call getToken')
  return state.token
}
