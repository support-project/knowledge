import logger from 'logger'
const LABEL = 'isLogined.js'

export default (state) => {
  logger.debug(LABEL, 'call isLogined')
  if (state.token) {
    return true
  }
  return false
}
