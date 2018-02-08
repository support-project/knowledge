import logger from 'logger'
const LABEL = 'getUserInfo.js'

export default (state) => {
  logger.debug(LABEL, 'Get User information:' + JSON.stringify(state.userInfo))
  return state.userInfo
}
