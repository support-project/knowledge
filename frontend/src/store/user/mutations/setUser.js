import logger from 'logger'
const LABEL = 'setUser.js'

export default (state, user) => {
  logger.debug(LABEL, 'Set User:' + JSON.stringify(user))
  state.user = user
}
