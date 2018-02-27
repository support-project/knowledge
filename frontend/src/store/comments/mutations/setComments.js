import logger from 'logger'
const LABEL = 'setComments.js'

export default (state, comments) => {
  logger.debug(LABEL, 'Set comments on state.\n' + JSON.stringify(comments))
  state.comments = comments
}
