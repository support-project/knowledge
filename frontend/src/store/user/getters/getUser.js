import logger from 'logger'
const LABEL = 'getUser.js'

export default (state) => {
  logger.debug(LABEL, 'Get User:' + JSON.stringify(state.user))
  return state.user
}
