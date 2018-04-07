import logger from 'logger'
const LABEL = 'setUser.js'

export default (state, user) => {
  logger.info(LABEL, 'Set User:' + JSON.stringify(user))
  state.user = user
}
