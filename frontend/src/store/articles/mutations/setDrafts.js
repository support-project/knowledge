import logger from 'logger'
const LABEL = 'setDrafts.js'

export default (state, drafts) => {
  logger.debug(LABEL, 'Set drafts on state.\n' + JSON.stringify(drafts))
  state.drafts = drafts
}
