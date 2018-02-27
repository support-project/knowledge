import logger from 'logger'
const LABEL = 'setUserInfo.js'

export default (state, userInfo) => {
  logger.debug(LABEL, 'Set User information:' + JSON.stringify(userInfo))
  state.userInfo = userInfo
}
