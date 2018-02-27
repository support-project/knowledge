import logger from 'logger'
const LABEL = 'setToc.js'

export default (state, toc) => {
  logger.debug(LABEL, 'Set toc on state')
  state.toc = toc
}
